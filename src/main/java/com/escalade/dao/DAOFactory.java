package com.escalade.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.escalade.dao.comment.CommentDAO;
import com.escalade.dao.comment.CommentDAOImpl;
import com.escalade.dao.pitch.PitchDAO;
import com.escalade.dao.pitch.PitchDAOImpl;
import com.escalade.dao.reservation.ReservationDAO;
import com.escalade.dao.reservation.ReservationDAOImpl;
import com.escalade.dao.route.RouteDAO;
import com.escalade.dao.route.RouteDAOImpl;
import com.escalade.dao.sector.SectorDAO;
import com.escalade.dao.sector.SectorDAOImpl;
import com.escalade.dao.site.SiteDAO;
import com.escalade.dao.site.SiteDAOImpl;
import com.escalade.dao.topo.TopoDAO;
import com.escalade.dao.topo.TopoDAOImpl;
import com.escalade.dao.user.UserDAO;
import com.escalade.dao.user.UserDAOImpl;

public class DAOFactory {
	private String url;
	private String username;
	private String password;
	
	public DAOFactory(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public static DAOFactory getInstance() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		DAOFactory instance = new DAOFactory("jdbc:postgresql://localhost:5432/amis_escalade", "admin_pizza", "pizza");
		
		return instance;
	}
	
	public Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(url, username, password);
		
		connection.setAutoCommit(false);
		
		return connection;
	}
	
	public UserDAO getUserDAO() {
		return new UserDAOImpl(this);
	}
	
	public TopoDAO getTopoDAO() {
		return new TopoDAOImpl(this);
	}
	
	public ReservationDAO getReservationDAO() {
		return new ReservationDAOImpl(this);
	}
	
	public CommentDAO getCommentDAO() {
		return new CommentDAOImpl(this);
	}
	
	public SiteDAO getSiteDAO() {
		return new SiteDAOImpl(this);
	}
	
	public SectorDAO getSectorDAO() {
		return new SectorDAOImpl(this);
	}
	
	public RouteDAO getRouteDAO() {
		return new RouteDAOImpl(this);
	}
	
	public PitchDAO getPitchDAO() {
		return new PitchDAOImpl(this);
	}
}
