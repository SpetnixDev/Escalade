INSERT INTO public.users (last_name, password, email, first_name, member) 
VALUES 
('Dupont', 'bKE9UspwyIPg8LsQHkJaiehiTeUdstI5JZOvaoQRgJA=', 'dupont@example.com', 'Jean', true),
('Martin', 'bKE9UspwyIPg8LsQHkJaiehiTeUdstI5JZOvaoQRgJA=', 'martin@example.com', 'Marie', true),
('Durand', 'bKE9UspwyIPg8LsQHkJaiehiTeUdstI5JZOvaoQRgJA=', 'durand@example.com', 'Pierre', false),
('Lefevre', 'bKE9UspwyIPg8LsQHkJaiehiTeUdstI5JZOvaoQRgJA=', 'lefevre@example.com', 'Lucie', true);

INSERT INTO public.site (official, name, region, description, rating)
VALUES 
(true, 'Site d''Escalade Montagne', 'Provence-Alpes-Côte d''Azur', 'Un site d''escalade incroyable dans les montagnes', '6b+'),
(false, 'Paroi des Aventures', 'Occitanie', 'Paroi sauvage avec des voies difficiles', '7a');

INSERT INTO public.sector (site_id, name, description)
VALUES 
(1, 'Secteur des Aigles', 'Secteur avec des voies de difficulté intermédiaire'),
(1, 'Secteur du Rocher', 'Secteur avec des voies courtes mais techniques'),
(2, 'Secteur du Grand Canyon', 'Secteur très technique pour grimpeurs expérimentés'),
(2, 'Secteur des Cascades', 'Secteur avec des voies longues et variées');

INSERT INTO public.topo (user_id, title, description, release_date, available, location) 
VALUES 
(1, 'Topo Montagne', 'Guide complet pour le site d''escalade en montagne', '2023-05-01', true, 'Provence-Alpes-Côte d''Azur'),
(2, 'Topo Aventures', 'Guide des voies sur la Paroi des Aventures', '2022-06-10', true, 'Occitanie');

INSERT INTO public.route (sector_id, description, name) 
VALUES 
(1, 'Voie en dièdre avec quelques passages en dalle', 'Dièdre du Dragon'),
(1, 'Voie avec des surplombs et des sections en fissures', 'Surplomb des Aigles'),
(2, 'Voie courte mais intense avec une dalle technique', 'Dalle du Rocher'),
(2, 'Voie exigeante avec des petits réglettes', 'Fissure Cachée'),
(3, 'Voie longue avec des prises naturelles', 'Grande Traversée'),
(3, 'Voie avec un mix de dalles et surplombs', 'Éperon du Canyon'),
(4, 'Voie aérienne avec des passages en traversée', 'Voie des Cascades'),
(4, 'Voie variée avec des sections en dièdre', 'Dièdre des Cascades');

INSERT INTO public.pitch (route_id, length, rating, description, name)
VALUES 
(1, 30.5, '6b', 'Belle longueur avec un bon mix de difficultés', 'Premier Étage'),
(1, 28.0, '6a+', 'Section de dalle avec quelques prises cachées', 'Deuxième Étage'),
(2, 35.0, '7a', 'Surplomb technique à franchir', 'Surplomb Initial'),
(2, 32.0, '6c+', 'Fissure fine avec des mouvements délicats', 'Fissure Final'),
(3, 22.0, '5c', 'Dalle technique pour l''échauffement', 'Dalle de Rocher'),
(4, 25.0, '6a', 'Section en fissure avec réglettes', 'Fissure Ascendante'),
(5, 45.0, '6b+', 'Traversée avec des prises larges', 'Traversée Facile'),
(5, 40.0, '6c', 'Final en dalle avec un bon grip', 'Traversée Final'),
(6, 50.0, '6c+', 'Voie technique avec des surplombs en bas', 'Surplomb Initial'),
(6, 45.0, '7a', 'Final long avec des prises larges', 'Final de l''Éperon'),
(7, 55.0, '6a+', 'Passages aériens en traversée avec bonnes prises', 'Traversée Aérienne'),
(7, 48.0, '6b', 'Section variée avec des mouvements complexes', 'Traversée Finale'),
(8, 40.0, '6b+', 'Belle longueur avec un bon grip', 'Premier Dièdre'),
(8, 38.0, '6c', 'Section technique en dièdre', 'Dièdre Final');

INSERT INTO public.comments (content, date, user_id, site_id) 
VALUES 
('Super site, très bien équipé !', '2023-05-20', 1, 1),
('Magnifique endroit, mais voies un peu difficiles', '2023-06-15', 2, 2),
('Secteur magnifique avec de belles longueurs aériennes', '2023-07-10', 3, 1),
('Grimpe très technique, surtout dans les voies surplombantes', '2023-08-05', 4, 2);

INSERT INTO public.reservation (user_id, topo_id, status) 
VALUES 
(1, 1, 'pending'),
(2, 2, 'ongoing'),
(3, 1, 'completed'),
(4, 2, 'refused');