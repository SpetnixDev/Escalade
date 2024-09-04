/*package com.escalade.dao.reservation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.model.DetailedReservation;
import com.escalade.model.Reservation;
import com.escalade.model.Topo;
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

	@Override
	public ArrayList<Reservation> getReservationsByUser(int userId) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    ArrayList<Reservation> reservations = null;

	    try {
	    	connection = daoFactory.getConnection();
	    	
	        String query = "SELECT * FROM user_reservations WHERE user_id = ?";
	        
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, userId);
	        
	        resultSet = preparedStatement.executeQuery();
	        reservations = new ArrayList<>();
	        
	        while (resultSet.next()) {
	            int id = resultSet.getInt("id");
	            int topoId = resultSet.getInt("topo_id");
	            int reservationUserId = resultSet.getInt("user_id");
	            String status = resultSet.getString("status");
	            String title = resultSet.getString("title");
	            String desc = resultSet.getString("description");
	            Date date = resultSet.getDate("release_date");
	            String location = resultSet.getString("location");
	            String email = resultSet.getString("owner_email");
	            
	            Topo topo = new Topo(title, desc, location, date);
	            Reservation reservation = new DetailedReservation(id, reservationUserId, topoId, status, topo, email);
	            
	            reservations.add(reservation);
	        }
	        
	        return reservations;
	    } catch (SQLException e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
		} finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
        }
	}

}*/

package com.escalade.dao.reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.escalade.dao.DAOException;
import com.escalade.model.DetailedReservation;
import com.escalade.model.Reservation;
import com.escalade.model.Topo;

@Repository
public class ReservationDAOImpl implements ReservationDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Reservation createReservation(int userId, int topoId) throws DAOException {
        final String query = "INSERT INTO reservation (user_id, topo_id, status) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                
                ps.setInt(1, userId);
                ps.setInt(2, topoId);
                ps.setString(3, "pending");
                
                return ps;
            }, keyHolder);

            int id = keyHolder.getKey().intValue();
            
            return new Reservation(id, userId, topoId, "pending");
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public ArrayList<Reservation> getReservationsConcerningUser(int userId) throws DAOException {
        String query = "SELECT * FROM reservation WHERE topo_id IN (SELECT id FROM topo WHERE user_id = ?) AND status <> 'completed' AND status <> 'refused'";

        try {
            return (ArrayList<Reservation>) jdbcTemplate.query(query, new ReservationRowMapper(), new Object[]{userId});
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public Reservation updateReservation(int resId, String newStatus) throws DAOException {
        String updateQuery = "UPDATE reservation SET status = ? WHERE id = ?";

        try {
            int rowsAffected = jdbcTemplate.update(updateQuery, newStatus, resId);
            
            if (rowsAffected > 0) {
                return getReservationById(resId);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public Reservation getReservationById(int resId) throws DAOException {
        String query = "SELECT * FROM reservation WHERE id = ?";

        try {
            List<Reservation> reservations = jdbcTemplate.query(query, new ReservationRowMapper(), new Object[]{resId});
            
            if (reservations.size() == 1) {
                return reservations.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public ArrayList<DetailedReservation> getReservationsByUser(int userId) throws DAOException {
        String query = "SELECT * FROM user_reservations WHERE user_id = ?";

        try {
            return (ArrayList<DetailedReservation>) jdbcTemplate.query(query, new DetailedReservationRowMapper(), new Object[]{userId});
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    private static class ReservationRowMapper implements RowMapper<Reservation> {
        @Override
        public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            int userId = rs.getInt("user_id");
            int topoId = rs.getInt("topo_id");
            String status = rs.getString("status");
            
            return new Reservation(id, userId, topoId, status);
        }
    }

    private static class DetailedReservationRowMapper implements RowMapper<DetailedReservation> {
        @Override
        public DetailedReservation mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            int userId = rs.getInt("user_id");
            int topoId = rs.getInt("topo_id");
            String status = rs.getString("status");
            String title = rs.getString("title");
            String description = rs.getString("description");
            Date releaseDate = rs.getDate("release_date");
            String location = rs.getString("location");
            String ownerEmail = rs.getString("owner_email");

            Topo topo = new Topo(title, description, location, releaseDate);
            return new DetailedReservation(id, userId, topoId, status, topo, ownerEmail);
        }
    }
}

