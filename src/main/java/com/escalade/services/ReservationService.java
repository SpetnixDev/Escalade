package com.escalade.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.escalade.config.DBConnector;
import com.escalade.model.Reservation;

public class ReservationService {
	public Reservation createReservation(int userId, int topoId) {
		Connection connection = DBConnector.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Reservation reservation = null;
        
        try {
        	String query = "INSERT INTO reservation (user_id, topo_id, status) VALUES (?, ?, ?)";
        	
        	preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        	preparedStatement.setInt(1, userId);
        	preparedStatement.setInt(2, topoId);
        	preparedStatement.setString(3, "pending");
        	
        	int affectedRows = preparedStatement.executeUpdate();
			
			if (affectedRows == 0) {
				throw new SQLException("Error creating a new user.");
			}
			
			resultSet = preparedStatement.getGeneratedKeys();
			
			if (resultSet.next()) {
				int id = resultSet.getInt(1);
				reservation = new Reservation(id, userId, topoId, "pending");
			} else {
				throw new SQLException("Creating user failed.");
			}
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return reservation;
	}
	
	public List<Reservation> getReservationsConcerningUser(int userId) {
		Connection connection = DBConnector.getDBConnection();
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    List<Reservation> reservations = new ArrayList<>();

	    try {
	        String query = "SELECT * FROM reservation WHERE topo_id IN (SELECT id FROM topo WHERE user_id = ?) AND status <> 'completed' AND status <> 'refused'";
	        
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, userId);
	        
	        resultSet = preparedStatement.executeQuery();
	        
	        while (resultSet.next()) {
	            int id = resultSet.getInt("id");
	            int topoId = resultSet.getInt("topo_id");
	            int reservationUserId = resultSet.getInt("user_id");
	            String status = resultSet.getString("status");
	            Reservation reservation = new Reservation(id, reservationUserId, topoId, status);
	            reservations.add(reservation);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (resultSet != null) resultSet.close();
	            if (preparedStatement != null) preparedStatement.close();
	            if (connection != null) connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return reservations;
	}

	public Reservation updateReservation(int resId, String newStatus) {
		Connection connection = DBConnector.getDBConnection();
	    PreparedStatement preparedStatement = null;
	    Reservation reservation = null;
	    
	    try {
	        String updateQuery = "UPDATE reservation SET status = ? WHERE id = ?";
	        preparedStatement = connection.prepareStatement(updateQuery);
	        
	        preparedStatement.setString(1, newStatus);
	        preparedStatement.setInt(2, resId);
	        
	        int rowsAffected = preparedStatement.executeUpdate();
	        
	        if (rowsAffected > 0) {
	        	reservation = getReservationFromId(resId);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
		} finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		
		return reservation;
	}
	
	public Reservation getReservationFromId(int resId) {
		Connection connection = DBConnector.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Reservation reservation = null;

        try {
            String query = "SELECT * FROM reservation WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, resId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int topoId = resultSet.getInt("topo_id");
                String status = resultSet.getString("status");
                
                reservation = new Reservation(id, userId, topoId, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return reservation;
	}
}
