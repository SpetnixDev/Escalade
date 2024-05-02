package com.escalade.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.escalade.dao.reservation.ReservationDAO;
import com.escalade.dao.reservation.ReservationDAOImpl;
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
}
