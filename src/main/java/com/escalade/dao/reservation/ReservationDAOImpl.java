package com.escalade.dao.reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.model.Reservation;
import com.escalade.util.DBUtils;

public class ReservationDAOImpl implements ReservationDAO {
	private DAOFactory daoFactory;
	
	public ReservationDAOImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public Reservation createReservation(int userId, int topoId) throws DAOException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Reservation reservation = null;
        
        try {
        	connection = daoFactory.getConnection();
        	
        	String query = "INSERT INTO reservation (user_id, topo_id, status) VALUES (?, ?, ?)";
        	
        	preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        	preparedStatement.setInt(1, userId);
        	preparedStatement.setInt(2, topoId);
        	preparedStatement.setString(3, "pending");
        	
        	preparedStatement.executeUpdate();
        	
        	connection.commit();
			
			resultSet = preparedStatement.getGeneratedKeys();
			
			if (resultSet.next()) {
				int id = resultSet.getInt(1);
				
				reservation = new Reservation(id, userId, topoId, "pending");
			} else {
				throw new DAOException("Un problème est survenu lors de la création de la réservation");
			}
	        
	        return reservation;
        } catch (SQLException e) {
			try {
                if (connection != null) connection.rollback();
            } catch (SQLException e2) {}
			
            throw new DAOException("Impossible de communiquer avec la base de données");
		} finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
        }
	}

	@Override
	public ArrayList<Reservation> getReservationsConcerningUser(int userId) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    ArrayList<Reservation> reservations = null;

	    try {
	    	connection = daoFactory.getConnection();
	    	
	        String query = "SELECT * FROM reservation WHERE topo_id IN (SELECT id FROM topo WHERE user_id = ?) AND status <> 'completed' AND status <> 'refused'";
	        
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, userId);
	        
	        resultSet = preparedStatement.executeQuery();
	        reservations = new ArrayList<>();
	        
	        while (resultSet.next()) {
	            int id = resultSet.getInt("id");
	            int topoId = resultSet.getInt("topo_id");
	            int reservationUserId = resultSet.getInt("user_id");
	            String status = resultSet.getString("status");
	            Reservation reservation = new Reservation(id, reservationUserId, topoId, status);
	            reservations.add(reservation);
	        }
	        
	        return reservations;
	    } catch (SQLException e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
		} finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
        }
	}

	@Override
	public Reservation updateReservation(int resId, String newStatus) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    Reservation reservation = null;
	    
	    try {
	    	connection = daoFactory.getConnection();
	    	
	        String updateQuery = "UPDATE reservation SET status = ? WHERE id = ?";
	        
	        preparedStatement = connection.prepareStatement(updateQuery);
	        preparedStatement.setString(1, newStatus);
	        preparedStatement.setInt(2, resId);
	        
	        int rowsAffected = preparedStatement.executeUpdate();
	        
	        connection.commit();
	        
	        if (rowsAffected > 0) reservation = getReservationById(resId);
			
			return reservation;
	    } catch (SQLException e) {
			try {
                if (connection != null) connection.rollback();
            } catch (SQLException e2) {}
			
            throw new DAOException("Impossible de communiquer avec la base de données");
		} finally {
            DBUtils.closeQuietly(connection, null, preparedStatement);
        }
	}

	@Override
	public Reservation getReservationById(int resId) throws DAOException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Reservation reservation = null;

        try {
        	connection = daoFactory.getConnection();
        	
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

            return reservation;
        } catch (SQLException e) {
        	throw new DAOException("Impossible de communiquer avec la base de données");
        } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
        }
	}

}
