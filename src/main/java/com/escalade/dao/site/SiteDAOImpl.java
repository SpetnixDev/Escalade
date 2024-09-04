/*package com.escalade.dao.site;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.model.Site;
import com.escalade.util.DBUtils;
import com.escalade.util.StringUtils;

public class SiteDAOImpl implements SiteDAO {
	private DAOFactory daoFactory;
	
	public SiteDAOImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public int registerSite(String name, String description, String region, String rating) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = daoFactory.getConnection();
			
			String query = "INSERT INTO site (name, description, region, rating, official) VALUES (?, ?, ?, ?, ?)";
			
			preparedStatement = connection.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, description);
			preparedStatement.setString(3, region);
			preparedStatement.setString(4, rating);
			preparedStatement.setBoolean(5, false);
			preparedStatement.executeUpdate();
			
			connection.commit();
			
			resultSet = preparedStatement.getGeneratedKeys();
			
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new DAOException("Un problème est survenu lors de la création du site");
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
                String rating = resultSet.getString("rating");
                boolean official = resultSet.getBoolean("official");
                
                site = new Site(id, name, description, region, rating, official);
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
                String rating = resultSet.getString("rating");
                boolean official = resultSet.getBoolean("official");
                
                sites.add(new Site(id, name, description, region, rating, official));
            }

            return sites;
        } catch (SQLException e) {
	        throw new DAOException("Impossible de communiquer avec la base de données");
	    } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
	    }
	}

	@Override
	public ArrayList<Site> requestSites(String[] regions, boolean sectorsNumbersEnabled, int sectorsNumbers,
			boolean ratingEnabled, String rating, boolean official, String[] keywords) throws DAOException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Site> sites = null;

        try {
        	connection = daoFactory.getConnection();
        	
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM site WHERE 1 = 1");
            ArrayList<Object> queryParams = new ArrayList<>();

            if (regions != null && regions.length > 0) {
                queryBuilder.append(" AND (");
                
                for (int i = 0; i < regions.length; i++) {
                    if (i > 0) {
                        queryBuilder.append(" OR ");
                    }
                    
                    queryBuilder.append("region LIKE ?");
                    queryParams.add("%" + regions[i] + "%");
                }
                
                queryBuilder.append(")");
            }

            if (keywords != null && keywords.length > 0) {
                queryBuilder.append(" AND (");
                
                for (int i = 0; i < keywords.length; i++) {
                    if (i > 0) {
                        queryBuilder.append(" AND ");
                    }
                    
                    queryBuilder.append("(unaccent(LOWER(name)) LIKE ? OR unaccent(LOWER(description)) LIKE ?)");
                    queryParams.add("%" + StringUtils.normalizeString(keywords[i]) + "%");
                    queryParams.add("%" + StringUtils.normalizeString(keywords[i]) + "%");
                }
                
                queryBuilder.append(")");
            }
            
            if (sectorsNumbersEnabled) {
            	queryBuilder.append(" AND id IN (");
                queryBuilder.append("SELECT site_id FROM sector GROUP BY site_id HAVING COUNT(*) = ?)");
                
                queryParams.add(sectorsNumbers);
            }
            
            if (ratingEnabled) {
            	queryBuilder.append(" AND rating = ?");
            	
            	queryParams.add(rating);
            }
            
            if (official) {
            	queryBuilder.append(" AND official = true");
            }

            preparedStatement = connection.prepareStatement(queryBuilder.toString());
            
            for (int i = 0; i < queryParams.size(); i++) {
                preparedStatement.setObject(i + 1, queryParams.get(i));
            }
            
            resultSet = preparedStatement.executeQuery();

            sites = new ArrayList<>();
            
            while (resultSet.next()) {
            	int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String region = resultSet.getString("region");
                String siteRating = resultSet.getString("rating");
                boolean officialSite = resultSet.getBoolean("official");
                
                sites.add(new Site(id, name, description, region, siteRating, officialSite));
            }

            return sites;
        } catch (SQLException e) {
	        throw new DAOException("Impossible de communiquer avec la base de données");
	    } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
	    }
	}

	@Override
	public ArrayList<String> requestRegions() throws DAOException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<String> regions = null;

        try {
        	connection = daoFactory.getConnection();
        	
            String query = "SELECT DISTINCT region, unaccent(LOWER(region)) AS region_order FROM site ORDER BY region_order";
            
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            regions = new ArrayList<>();
            
            while (resultSet.next()) {            	
            	String region = resultSet.getString("region");
            	
                regions.add(region);
            }

            return regions;
        } catch (SQLException e) {
	        throw new DAOException("Impossible de communiquer avec la base de données");
	    } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
	    }
	}

	@Override
	public boolean updateOfficialStatus(int siteId) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    
	    try {
	    	connection = daoFactory.getConnection();
	    	
	        String updateQuery = "UPDATE site SET official = true WHERE id = ?";
	        
	        preparedStatement = connection.prepareStatement(updateQuery);
	        preparedStatement.setInt(1, siteId);
	        
	        int rowsAffected = preparedStatement.executeUpdate();
	        
	        connection.commit();
	        
	        if (rowsAffected > 0) return true;
			
			return false;
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
	public ArrayList<Site> requestMostRecentSites() throws DAOException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Site> sites = null;

        try {
        	connection = daoFactory.getConnection();
        	
            String query = "SELECT * FROM site ORDER BY id DESC LIMIT 5";
            
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            sites = new ArrayList<>();
            
            while (resultSet.next()) {
            	int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String region = resultSet.getString("region");
                String rating = resultSet.getString("rating");
                boolean official = resultSet.getBoolean("official");
                
                sites.add(new Site(id, name, description, region, rating, official));
            }

            return sites;
        } catch (SQLException e) {
	        throw new DAOException("Impossible de communiquer avec la base de données");
	    } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
	    }
	}

}*/

package com.escalade.dao.site;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.escalade.dao.DAOException;
import com.escalade.model.Site;
import com.escalade.util.StringUtils;

@Repository
public class SiteDAOImpl implements SiteDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int registerSite(String name, String description, String region, String rating) throws DAOException {
        final String query = "INSERT INTO site (name, description, region, rating, official) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(query, new String[] {"id"});
                
                ps.setString(1, name);
                ps.setString(2, description);
                ps.setString(3, region);
                ps.setString(4, rating);
                ps.setBoolean(5, false);
                
                return ps;
            }, keyHolder);

            if (keyHolder.getKey() != null) {
                return keyHolder.getKey().intValue();
            } else {
                throw new DAOException("Un problème est survenu lors de la création du site");
            }
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public Site requestSiteById(int siteId) throws DAOException {
        String query = "SELECT * FROM site WHERE id = ?";
        
        try {
            List<Site> sites = jdbcTemplate.query(query, new SiteRowMapper(), new Object[]{siteId});
            
            if (sites.size() == 1) {
                return sites.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public ArrayList<Site> requestSites() throws DAOException {
        String query = "SELECT * FROM site ORDER BY name ASC";
        
        try {
            return (ArrayList<Site>) jdbcTemplate.query(query, new SiteRowMapper());
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public ArrayList<Site> requestSites(String[] regions, boolean sectorsNumbersEnabled, int sectorsNumbers,
                                   boolean ratingEnabled, String rating, boolean official, String[] keywords) throws DAOException {
        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM site WHERE 1 = 1");
            List<Object> queryParams = new ArrayList<>();

            if (regions != null && regions.length > 0) {
                queryBuilder.append(" AND (");
                
                for (int i = 0; i < regions.length; i++) {
                    if (i > 0) {
                        queryBuilder.append(" OR ");
                    }
                    
                    queryBuilder.append("region LIKE ?");
                    
                    queryParams.add("%" + regions[i] + "%");
                }
                
                queryBuilder.append(")");
            }

            if (keywords != null && keywords.length > 0) {
                queryBuilder.append(" AND (");
                
                for (int i = 0; i < keywords.length; i++) {
                    if (i > 0) {
                        queryBuilder.append(" AND ");
                    }
                    queryBuilder.append("(unaccent(LOWER(name)) LIKE ? OR unaccent(LOWER(description)) LIKE ?)");
                    
                    queryParams.add("%" + StringUtils.normalizeString(keywords[i]) + "%");
                    queryParams.add("%" + StringUtils.normalizeString(keywords[i]) + "%");
                }
                
                queryBuilder.append(")");
            }

            if (sectorsNumbersEnabled) {
                queryBuilder.append(" AND id IN (");
                queryBuilder.append("SELECT site_id FROM sector GROUP BY site_id HAVING COUNT(*) = ?)");
                queryParams.add(sectorsNumbers);
            }

            if (ratingEnabled) {
                queryBuilder.append(" AND rating = ?");
                queryParams.add(rating);
            }

            if (official) {
                queryBuilder.append(" AND official = true");
            }

            return (ArrayList<Site>) jdbcTemplate.query(queryBuilder.toString(), new SiteRowMapper(), queryParams.toArray());
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public ArrayList<String> requestRegions() throws DAOException {
        String query = "SELECT DISTINCT region, unaccent(LOWER(region)) AS region_order FROM site ORDER BY region_order";
        
        try {
            return (ArrayList<String>) jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("region"));
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public boolean updateOfficialStatus(int siteId) throws DAOException {
        String updateQuery = "UPDATE site SET official = true WHERE id = ?";
        
        try {
            int rowsAffected = jdbcTemplate.update(updateQuery, siteId);
            
            return rowsAffected > 0;
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public ArrayList<Site> requestMostRecentSites() throws DAOException {
        String query = "SELECT * FROM site ORDER BY id DESC LIMIT 5";
        
        try {
            return (ArrayList<Site>) jdbcTemplate.query(query, new SiteRowMapper());
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    private static class SiteRowMapper implements RowMapper<Site> {
        @Override
        public Site mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            String region = rs.getString("region");
            String rating = rs.getString("rating");
            boolean official = rs.getBoolean("official");
            
            return new Site(id, name, description, region, rating, official);
        }
    }
}

