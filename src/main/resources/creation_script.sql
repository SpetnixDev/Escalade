CREATE EXTENSION IF NOT EXISTS unaccent;

CREATE SEQUENCE public.users_id_seq;

CREATE TABLE public.users (
                id INTEGER NOT NULL DEFAULT nextval('public.users_id_seq'),
                last_name VARCHAR NOT NULL,
                password VARCHAR NOT NULL,
                email VARCHAR NOT NULL,
                first_name VARCHAR NOT NULL,
                member BOOLEAN NOT NULL,
                CONSTRAINT users_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;

CREATE SEQUENCE public.topo_id_seq;

CREATE TABLE public.topo (
                id INTEGER NOT NULL DEFAULT nextval('public.topo_id_seq'),
                user_id INTEGER NOT NULL,
                title VARCHAR NOT NULL,
                description VARCHAR NOT NULL,
                release_date DATE NOT NULL,
                available BOOLEAN DEFAULT false NOT NULL,
                location VARCHAR NOT NULL,
                CONSTRAINT topo_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.topo_id_seq OWNED BY public.topo.id;

CREATE SEQUENCE public.reservation_id_seq;

CREATE TABLE public.reservation (
                id INTEGER NOT NULL DEFAULT nextval('public.reservation_id_seq'),
                user_id INTEGER NOT NULL,
                topo_id INTEGER NOT NULL,
                status VARCHAR NOT NULL,
                CONSTRAINT reservation_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.reservation_id_seq OWNED BY public.reservation.id;

CREATE SEQUENCE public.site_id_seq;

CREATE TABLE public.site (
                id INTEGER NOT NULL DEFAULT nextval('public.site_id_seq'),
                official BOOLEAN NOT NULL,
                name VARCHAR NOT NULL,
                region VARCHAR NOT NULL,
                description VARCHAR NOT NULL,
                rating VARCHAR NOT NULL,
                CONSTRAINT site_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.site_id_seq OWNED BY public.site.id;

CREATE SEQUENCE public.comments_id_seq;

CREATE TABLE public.comments (
                id INTEGER NOT NULL DEFAULT nextval('public.comments_id_seq'),
                content VARCHAR NOT NULL,
                date DATE NOT NULL,
                user_id INTEGER NOT NULL,
                site_id INTEGER NOT NULL,
                CONSTRAINT comments_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.comments_id_seq OWNED BY public.comments.id;

CREATE SEQUENCE public.sector_id_seq;

CREATE TABLE public.sector (
                id INTEGER NOT NULL DEFAULT nextval('public.sector_id_seq'),
                site_id INTEGER NOT NULL,
                name VARCHAR NOT NULL,
                description VARCHAR NOT NULL,
                CONSTRAINT sector_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.sector_id_seq OWNED BY public.sector.id;

CREATE SEQUENCE public.route_id_seq;

CREATE TABLE public.route (
                id INTEGER NOT NULL DEFAULT nextval('public.route_id_seq'),
                sector_id INTEGER NOT NULL,
                description VARCHAR NOT NULL,
                name VARCHAR NOT NULL,
                CONSTRAINT route_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.route_id_seq OWNED BY public.route.id;

CREATE SEQUENCE public.pitch_id_seq;

CREATE TABLE public.pitch (
                id INTEGER NOT NULL DEFAULT nextval('public.pitch_id_seq'),
                route_id INTEGER NOT NULL,
                length DOUBLE PRECISION NOT NULL,
                rating VARCHAR NOT NULL,
                description VARCHAR NOT NULL,
                name VARCHAR NOT NULL,
                CONSTRAINT pitch_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.pitch_id_seq OWNED BY public.pitch.id;

ALTER TABLE public.comments ADD CONSTRAINT user_comment_fk
FOREIGN KEY (user_id)
REFERENCES public.users (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.topo ADD CONSTRAINT user_topo_fk
FOREIGN KEY (user_id)
REFERENCES public.users (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.reservation ADD CONSTRAINT user_reservation_fk
FOREIGN KEY (user_id)
REFERENCES public.users (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.reservation ADD CONSTRAINT topo_reservation_fk
FOREIGN KEY (topo_id)
REFERENCES public.topo (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.sector ADD CONSTRAINT site_sector_fk
FOREIGN KEY (site_id)
REFERENCES public.site (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.comments ADD CONSTRAINT site_comment_fk
FOREIGN KEY (site_id)
REFERENCES public.site (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.route ADD CONSTRAINT sector_route_fk
FOREIGN KEY (sector_id)
REFERENCES public.sector (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.pitch ADD CONSTRAINT route_pitch_fk
FOREIGN KEY (route_id)
REFERENCES public.route (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

CREATE OR REPLACE FUNCTION update_topo_available()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.status IN ('completed', 'refused') THEN
        UPDATE topo
        SET available = NOT EXISTS (
            SELECT 1
            FROM reservation
            WHERE reservation.topo_id = NEW.topo_id
                AND reservation.status IN ('pending', 'ongoing')
        )
        WHERE id = NEW.topo_id;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER reservation_status_update_trigger
AFTER UPDATE ON reservation
FOR EACH ROW
EXECUTE FUNCTION update_topo_available();

CREATE OR REPLACE FUNCTION set_topo_unavailable()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE topo
    SET available = FALSE
    WHERE id = NEW.topo_id;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_topo_unavailable_trigger
AFTER INSERT ON reservation
FOR EACH ROW
EXECUTE FUNCTION set_topo_unavailable();

CREATE VIEW user_reservations AS
SELECT 
	r.id,
    r.user_id,
    r.topo_id,
	r.status,
    t.title,
    t.description,
    t.release_date,
    t.location,
    CASE 
        WHEN r.status = 'ongoing' THEN u.email
        ELSE NULL
    END AS owner_email
FROM 
    reservation r
JOIN 
    topo t ON r.topo_id = t.id
LEFT JOIN 
    users u ON t.user_id = u.id
WHERE 
    r.status IN ('pending', 'ongoing');