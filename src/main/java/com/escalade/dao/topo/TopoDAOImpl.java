/*package com.escalade.dao.topo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.model.Topo;
import com.escalade.util.DBUtils;
import com.escalade.util.StringUtils;

public class TopoDAOImpl implements TopoDAO {
	private DAOFactory daoFactory;

	public TopoDAOImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public ArrayList<Topo> requestTopos() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
        ArrayList<Topo> topos = null;
		
		try {			
			connection = daoFactory.getConnection();
			
			String topoQuery = "SELECT * FROM Topo ORDER BY title ASC";
			
            preparedStatement = connection.prepareStatement(topoQuery);
            resultSet = preparedStatement.executeQuery();
            
            topos = new ArrayList<>();
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String location = resultSet.getString("location");
                Date releaseDate = resultSet.getDate("release_date");
                boolean available = resultSet.getBoolean("available");
                
                topos.add(new Topo(id, title, description, location, releaseDate, userId, available));
            }
    		
    		return topos;
		} catch (SQLException e) {
        	throw new DAOException("Impossible de communiquer avec la base de données");
        } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
        }
	}

	@Override
	public ArrayList<Topo> requestTopos(String[] regions, String[] keywords) throws DAOException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Topo> topos = null;

        try {
        	connection = daoFactory.getConnection();
        	
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM topo WHERE 1 = 1");
            ArrayList<Object> queryParams = new ArrayList<>();

            if (regions != null && regions.length > 0) {
                queryBuilder.append(" AND (");
                
                for (int i = 0; i < regions.length; i++) {
                    if (i > 0) {
                        queryBuilder.append(" OR ");
                    }
                    
                    queryBuilder.append("location LIKE ?");
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
                    
                    queryBuilder.append("(unaccent(LOWER(title)) LIKE ? OR unaccent(LOWER(description)) LIKE ?)");
                    queryParams.add("%" + StringUtils.normalizeString(keywords[i]) + "%");
                    queryParams.add("%" + StringUtils.normalizeString(keywords[i]) + "%");
                }
                
                queryBuilder.append(")");
            }

            preparedStatement = connection.prepareStatement(queryBuilder.toString());
            
            for (int i = 0; i < queryParams.size(); i++) {
                preparedStatement.setObject(i + 1, queryParams.get(i));
            }

            resultSet = preparedStatement.executeQuery();
            
            topos = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String location = resultSet.getString("location");
                Date releaseDate = resultSet.getDate("release_date");
                boolean available = resultSet.getBoolean("available");
                
                topos.add(new Topo(id, title, description, location, releaseDate, userId, available));
            }
            
            return topos;
        } catch (SQLException e) {
        	throw new DAOException("Impossible de communiquer avec la base de données");
        } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
        }
	}

	@Override
	public boolean registerNewTopo(int userId, String title, String description, String location, Date releaseDate) throws DAOException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    
	    try {
	        connection = daoFactory.getConnection();
	        
	        String query = "INSERT INTO topo (user_id, title, description, location, release_date) VALUES (?, ?, ?, ?, ?)";
	        
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, userId);
	        preparedStatement.setString(2, title);
	        preparedStatement.setString(3, description);
	        preparedStatement.setString(4, location);
	        preparedStatement.setDate(5, releaseDate);
	        
	        int affectedRows = preparedStatement.executeUpdate();
	        
	        connection.commit();
	        
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        try {
	            if (connection != null) connection.rollback();
	        } catch (SQLException rollbackException) {}
	        
	        throw new DAOException("Impossible de communiquer avec la base de données");
	    } finally {
            DBUtils.closeQuietly(connection, null, preparedStatement);
	    }
	}


	@Override
	public ArrayList<Topo> requestToposByUser(int userId) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Topo> topos = null; 
		
		try {			
			connection = daoFactory.getConnection();
			
			String topoQuery = "SELECT * FROM topo WHERE user_id = ? ORDER BY title ASC";
            
			preparedStatement = connection.prepareStatement(topoQuery);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            
            topos = new ArrayList<>();
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String location = resultSet.getString("location");
                Date releaseDate = resultSet.getDate("release_date");
                boolean available = resultSet.getBoolean("available");
                
                topos.add(new Topo(id, title, description, location, releaseDate, userId, available));
            }
    		
    		return topos;
		} catch (SQLException e) {
	        throw new DAOException("Impossible de communiquer avec la base de données");
	    } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
	    }
	}

	@Override
	public boolean isTopoAvailable(int topoId) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    
	    try {
	    	connection = daoFactory.getConnection();
	    	
	        String query = "SELECT available FROM topo WHERE id = ?";
	        
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, topoId);
	        
	        ResultSet resultSet = preparedStatement.executeQuery();
	        
	        if (resultSet.next()) return resultSet.getBoolean("available");
		    
			return false;
	    } catch (SQLException e) {
	        throw new DAOException("Impossible de communiquer avec la base de données");
	    } finally {
            DBUtils.closeQuietly(connection, null, preparedStatement);
	    }
	}

	@Override
	public Topo requestTopoById(int topoId) throws DAOException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Topo topo = null;

        try {
        	connection = daoFactory.getConnection();
        	
            String query = "SELECT * FROM topo WHERE id = ?";
            
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, topoId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String location = resultSet.getString("location");
                Date releaseDate = resultSet.getDate("release_date");
                boolean available = resultSet.getBoolean("available");
                
                topo = new Topo(id, title, description, location, releaseDate, userId, available);
            }

            return topo;
        } catch (SQLException e) {
	        throw new DAOException("Impossible de communiquer avec la base de données");
	    } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
	    }
	}

	@Override
	public boolean removeTopo(int topoId) throws DAOException {
		Connection connection = null;
	    PreparedStatement deleteReservationStatement = null;
	    PreparedStatement deleteTopoStatement = null;
	    
	    try {
	    	connection = daoFactory.getConnection();
	    	
	        String deleteReservationQuery = "DELETE FROM reservation WHERE topo_id = ?";
	        
	        deleteReservationStatement = connection.prepareStatement(deleteReservationQuery);
	        deleteReservationStatement.setInt(1, topoId);
	        deleteReservationStatement.executeUpdate();
	        
	        String deleteTopoQuery = "DELETE FROM topo WHERE id = ?";
	        
	        deleteTopoStatement = connection.prepareStatement(deleteTopoQuery);
	        deleteTopoStatement.setInt(1, topoId);
	        
	        int affectedRows = deleteTopoStatement.executeUpdate();
	        
	        connection.commit();
	        
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        try {
	            if (connection != null) connection.rollback();
	        } catch (SQLException rollbackException) {}
	        
	        throw new DAOException("Impossible de communiquer avec la base de données");
	    } finally {
            DBUtils.closeQuietly(connection, null, deleteReservationStatement, deleteTopoStatement);
	    }
	}

	@Override
	public ArrayList<Integer> requestReservedToposByUser(int userId) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Integer> topos = null;
		
		try {
			connection = daoFactory.getConnection();
			
			String query = "SELECT * FROM topo WHERE id IN (SELECT topo_id FROM reservation WHERE status = 'pending') AND user_id = ?";
            
			preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            
            topos = new ArrayList<>();
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                
                topos.add(id);
            }
    		
    		return topos;
		} catch (SQLException e) {
	        throw new DAOException("Impossible de communiquer avec la base de données");
	    } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
	    }
	}

	@Override
	public ArrayList<String> requestLocations() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = daoFactory.getConnection();
			
			String query = "SELECT DISTINCT location, unaccent(LOWER(location)) AS location_order FROM topo ORDER BY location_order";
			
			preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            
            ArrayList<String> locations = new ArrayList<>();
            
	        while (resultSet.next()) {
	            String location = resultSet.getString("location");
	            
	            locations.add(location);
	        }
    		
    		return locations;
		} catch (SQLException e) {
        	throw new DAOException("Impossible de communiquer avec la base de données");
        } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
        }
	}

	@Override
	public Topo updateTopoAvailability(int topoId, boolean available) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    Topo topo = null;
	    
	    try {
	    	connection = daoFactory.getConnection();
	    	
	        String updateQuery = "UPDATE topo SET available = ? WHERE id = ?";
	        
	        preparedStatement = connection.prepareStatement(updateQuery);
	        preparedStatement.setBoolean(1, available);
	        preparedStatement.setInt(2, topoId);
	        
	        int rowsAffected = preparedStatement.executeUpdate();
	        
	        connection.commit();
	        
	        if (rowsAffected > 0) topo = requestTopoById(topoId);
			
			return topo;
	    } catch (SQLException e) {
	        try {
	            if (connection != null) connection.rollback();
	        } catch (SQLException rollbackException) {}
	        
	        throw new DAOException("Impossible de communiquer avec la base de données");
	    } finally {
            DBUtils.closeQuietly(connection, null, preparedStatement);
	    }
	}
}*/

package com.escalade.dao.topo;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.escalade.dao.DAOException;
import com.escalade.model.Topo;
import com.escalade.util.StringUtils;

@Repository
public class TopoDAOImpl implements TopoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ArrayList<Topo> requestTopos() throws DAOException {
        String query = "SELECT * FROM Topo ORDER BY title ASC";
        
        try {
            return (ArrayList<Topo>) jdbcTemplate.query(query, new TopoRowMapper());
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public ArrayList<Topo> requestTopos(String[] regions, String[] keywords) throws DAOException {
        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM topo WHERE 1 = 1");
            List<Object> queryParams = new ArrayList<>();

            if (regions != null && regions.length > 0) {
                queryBuilder.append(" AND (");
                
                for (int i = 0; i < regions.length; i++) {
                    if (i > 0) {
                        queryBuilder.append(" OR ");
                    }
                    
                    queryBuilder.append("location LIKE ?");
                    
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
                    
                    queryBuilder.append("(unaccent(LOWER(title)) LIKE ? OR unaccent(LOWER(description)) LIKE ?)");
                    
                    queryParams.add("%" + StringUtils.normalizeString(keywords[i]) + "%");
                    queryParams.add("%" + StringUtils.normalizeString(keywords[i]) + "%");
                }
                
                queryBuilder.append(")");
            }

            return (ArrayList<Topo>) jdbcTemplate.query(queryBuilder.toString(), new TopoRowMapper(), queryParams.toArray());
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public boolean registerNewTopo(int userId, String title, String description, String location, Date releaseDate) throws DAOException {
        String query = "INSERT INTO topo (user_id, title, description, location, release_date) VALUES (?, ?, ?, ?, ?)";
        
        try {
            return jdbcTemplate.update(query, userId, title, description, location, releaseDate) > 0;
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public ArrayList<Topo> requestToposByUser(int userId) throws DAOException {
        String query = "SELECT * FROM topo WHERE user_id = ? ORDER BY title ASC";
        
        try {
            return (ArrayList<Topo>) jdbcTemplate.query(query, new TopoRowMapper(), new Object[]{userId});
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public boolean isTopoAvailable(int topoId) throws DAOException {
        String query = "SELECT available FROM topo WHERE id = ?";
        
        try {
            Boolean available = jdbcTemplate.queryForObject(query, Boolean.class, new Object[]{topoId});
            
            return available != null && available;
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public Topo requestTopoById(int topoId) throws DAOException {
        String query = "SELECT * FROM topo WHERE id = ?";
        
        try {
            List<Topo> topos = jdbcTemplate.query(query, new TopoRowMapper(), new Object[]{topoId});
            
            if (topos.size() == 1) {
                return topos.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public boolean removeTopo(int topoId) throws DAOException {
        String deleteReservationQuery = "DELETE FROM reservation WHERE topo_id = ?";
        String deleteTopoQuery = "DELETE FROM topo WHERE id = ?";
        
        try {
            jdbcTemplate.update(deleteReservationQuery, topoId);
            
            return jdbcTemplate.update(deleteTopoQuery, topoId) > 0;
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public ArrayList<Integer> requestReservedToposByUser(int userId) throws DAOException {
        String query = "SELECT id FROM topo WHERE id IN (SELECT topo_id FROM reservation WHERE status = 'pending') AND user_id = ?";
        
        try {
            return (ArrayList<Integer>) jdbcTemplate.queryForList(query, Integer.class, new Object[]{userId});
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public ArrayList<String> requestLocations() throws DAOException {
        String query = "SELECT DISTINCT location, unaccent(LOWER(location)) AS location_order FROM topo ORDER BY location_order";
        
        try {
            return (ArrayList<String>) jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("location"));
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public Topo updateTopoAvailability(int topoId, boolean available) throws DAOException {
        String updateQuery = "UPDATE topo SET available = ? WHERE id = ?";
        
        try {
            jdbcTemplate.update(updateQuery, available, topoId);
            
            return requestTopoById(topoId);
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    private static class TopoRowMapper implements RowMapper<Topo> {
        @Override
        public Topo mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            int userId = rs.getInt("user_id");
            String title = rs.getString("title");
            String description = rs.getString("description");
            String location = rs.getString("location");
            Date releaseDate = rs.getDate("release_date");
            boolean available = rs.getBoolean("available");
            
            return new Topo(id, title, description, location, releaseDate, userId, available);
        }
    }
}

