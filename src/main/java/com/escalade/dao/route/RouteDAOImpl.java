package com.escalade.dao.route;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.model.Route;
import com.escalade.util.DBUtils;

public class RouteDAOImpl implements RouteDAO {
	private DAOFactory daoFactory;
	
	public RouteDAOImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public int registerRoute(int sectorId, String name, String description) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = daoFactory.getConnection();
			
			String query = "INSERT INTO route (sector_id, name, description) VALUES (?, ?, ?)";
			
			preparedStatement = connection.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, sectorId);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, description);
			preparedStatement.executeUpdate();
			
			connection.commit();
			
			resultSet = preparedStatement.getGeneratedKeys();
			
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
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
	public List<Route> requestRoutesBySector(int sectorId) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Route> routes = null; 
		
		try {			
			connection = daoFactory.getConnection();
			
			String query = "SELECT * FROM route WHERE sector_id = ?";
            
			preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, sectorId);
            resultSet = preparedStatement.executeQuery();
            
            routes = new ArrayList<>();
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                
                routes.add(new Route(id, name, description));
            }
    		
    		return routes;
		} catch (SQLException e) {
	        throw new DAOException("Impossible de communiquer avec la base de données");
	    } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
	    }
	}

}
