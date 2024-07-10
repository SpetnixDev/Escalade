package com.escalade.dao.site;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.model.Site;
import com.escalade.util.DBUtils;

public class SiteDAOImpl implements SiteDAO {
	private DAOFactory daoFactory;
	
	public SiteDAOImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public int registerSite(String name, String description, String region) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = daoFactory.getConnection();
			
			String query = "INSERT INTO site (name, description, region, official) VALUES (?, ?, ?, ?)";
			
			preparedStatement = connection.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, description);
			preparedStatement.setString(3, region);
			preparedStatement.setBoolean(4, false);
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
	public Site requestSiteById(int siteId) throws DAOException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Site site = null;

        try {
        	connection = daoFactory.getConnection();
        	
            String query = "SELECT * FROM site WHERE id = ?";
            
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, siteId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String region = resultSet.getString("region");
                boolean official = resultSet.getBoolean("official");
                
                site = new Site(id, name, description, region, official);
            }

            return site;
        } catch (SQLException e) {
	        throw new DAOException("Impossible de communiquer avec la base de données");
	    } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
	    }
	}

	@Override
	public ArrayList<Site> requestSites() throws DAOException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Site> sites = null;

        try {
        	connection = daoFactory.getConnection();
        	
            String query = "SELECT * FROM site ORDER BY name ASC";
            
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            sites = new ArrayList<>();
            
            while (resultSet.next()) {
            	int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String region = resultSet.getString("region");
                boolean official = resultSet.getBoolean("official");
                
                sites.add(new Site(id, name, description, region, official));
            }

            return sites;
        } catch (SQLException e) {
	        throw new DAOException("Impossible de communiquer avec la base de données");
	    } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
	    }
	}

}
