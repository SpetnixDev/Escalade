package com.escalade.dao.sector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.model.Sector;
import com.escalade.util.DBUtils;

public class SectorDAOImpl implements SectorDAO {
	private DAOFactory daoFactory;
	
	public SectorDAOImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public int registerSector(int siteId, String name, String description) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = daoFactory.getConnection();
			
			String query = "INSERT INTO sector (site_id, name, description) VALUES (?, ?, ?)";
			
			preparedStatement = connection.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, siteId);
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
	public List<Sector> requestSectorsBySite(int siteId) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Sector> sectors = null; 
		
		try {			
			connection = daoFactory.getConnection();
			
			String query = "SELECT * FROM sector WHERE site_id = ?";
            
			preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, siteId);
            resultSet = preparedStatement.executeQuery();
            
            sectors = new ArrayList<>();
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                
                sectors.add(new Sector(id, name, description));
            }
    		
    		return sectors;
		} catch (SQLException e) {
	        throw new DAOException("Impossible de communiquer avec la base de données");
	    } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
	    }
	}
	
}
