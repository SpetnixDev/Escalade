package com.escalade.dao.pitch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.model.Pitch;
import com.escalade.util.DBUtils;

public class PitchDAOImpl implements PitchDAO {
	private DAOFactory daoFactory;
	
	public PitchDAOImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void registerPitch(int routeId, String name, String description, double length, String rating) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = daoFactory.getConnection();
			
			String query = "INSERT INTO pitch (route_id, name, description, length, rating) VALUES (?, ?, ?, ?, ?)";
			
			preparedStatement = connection.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, routeId);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, description);
			preparedStatement.setDouble(4, length);
			preparedStatement.setString(5, rating);
			preparedStatement.executeUpdate();
			
			connection.commit();
			
			resultSet = preparedStatement.getGeneratedKeys();
			
			if (!(resultSet.next())) {
				throw new DAOException("Un problème est survenu lors de la création de l'utilisateur");
			}
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
	public List<Pitch> requestPitchesByRoute(int routeId) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Pitch> pitches = null; 
		
		try {			
			connection = daoFactory.getConnection();
			
			String query = "SELECT * FROM pitch WHERE route_id = ?";
            
			preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, routeId);
            resultSet = preparedStatement.executeQuery();
            
            pitches = new ArrayList<>();
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double length = resultSet.getDouble("length");
                String rating = resultSet.getString("rating");
                
                pitches.add(new Pitch(id, name, description, length, rating));
            }
    		
    		return pitches;
		} catch (SQLException e) {
	        throw new DAOException("Impossible de communiquer avec la base de données");
	    } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
	    }
	}
}
