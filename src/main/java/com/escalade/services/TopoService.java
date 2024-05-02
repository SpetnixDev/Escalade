package com.escalade.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.escalade.config.DBConnector;
import com.escalade.model.Topo;
import com.escalade.util.StringUtils;

public class TopoService {
	public ArrayList<Topo> requestTopos(String[] regions, String[] keywords) {
        Connection connection = DBConnector.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Topo> topos = new ArrayList<>();

        try {
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

        return topos;
    }
	
	public List<String> requestLocations() {
		Connection connection = DBConnector.getDBConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String query = "SELECT DISTINCT location FROM topo";
			preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            
            ArrayList<String> locations = new ArrayList<>();
            
	        while (resultSet.next()) {
	            String location = resultSet.getString("location");
	            locations.add(location);
	        }
    		
    		return locations;
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
		
		return null;
	}

	public boolean registerNewTopo(int userId, String title, String description, String location, Date releaseDate) {
		Connection connection = DBConnector.getDBConnection();
		PreparedStatement preparedStatement = null;
		
		try {
			String query = "INSERT INTO topo (user_id, title, description, location, release_date) VALUES (?, ?, ?, ?, ?)";
			
			preparedStatement = connection.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, userId);
			preparedStatement.setString(2, title);
			preparedStatement.setString(3, description);
			preparedStatement.setString(4, location);
			preparedStatement.setDate(5, releaseDate);
			
			int affectedRows = preparedStatement.executeUpdate();
			
			if (affectedRows == 0) return false;
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
		
		return true;
	}
	
	public List<Topo> requestToposFromUser(int userId) {
		Connection connection = DBConnector.getDBConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {			
			String topoQuery = "SELECT * FROM Topo WHERE user_id = ? ORDER BY title ASC";
            preparedStatement = connection.prepareStatement(topoQuery);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            
            ArrayList<Topo> topos = new ArrayList<>();
            
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
		
		return null;
	}
	
	public List<Topo> requestTopos() {
		Connection connection = DBConnector.getDBConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {			
			String topoQuery = "SELECT * FROM Topo ORDER BY title ASC";
            preparedStatement = connection.prepareStatement(topoQuery);
            resultSet = preparedStatement.executeQuery();
            
            ArrayList<Topo> topos = new ArrayList<>();
            
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
		
		return null;
	}
	
	public Topo updateTopoAvailability(int topoId, boolean available) {
		Connection connection = DBConnector.getDBConnection();
	    PreparedStatement preparedStatement = null;
	    Topo topo = null;
	    
	    try {
	        String updateQuery = "UPDATE topo SET available = ? WHERE id = ?";
	        preparedStatement = connection.prepareStatement(updateQuery);
	        
	        preparedStatement.setBoolean(1, available);
	        preparedStatement.setInt(2, topoId);
	        
	        int rowsAffected = preparedStatement.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            topo = getTopoFromId(topoId);
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
		
		return topo;
	}
	
	public boolean isTopoAvailable(int topoId) {
		Connection connection = DBConnector.getDBConnection();
	    PreparedStatement preparedStatement = null;
	    boolean isAvailable = false;
	    
	    try {
	        String query = "SELECT available FROM topo WHERE id = ?";
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, topoId);
	        
	        ResultSet resultSet = preparedStatement.executeQuery();
	        
	        if (resultSet.next()) {
	            isAvailable = resultSet.getBoolean("available");
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
	    
		return isAvailable;
	}

	public Topo getTopoFromId(int topoId) {
		Connection connection = DBConnector.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Topo topo = null;

        try {
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

        return topo;
	}

	public List<Integer> requestReservedToposFromUser(int userId) {
		Connection connection = DBConnector.getDBConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {			
			String query = "SELECT * FROM topo WHERE id IN (SELECT topo_id FROM reservation WHERE status = 'pending') AND user_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            
            List<Integer> topos = new ArrayList<>();
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                
                topos.add(id);
            }
    		
    		return topos;
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
		
		return null;
	}
	/*
	private boolean isTopoReserved(int topoId) {
	    Connection connection = DBConnector.getDBConnection();
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        String query = "SELECT COUNT(*) AS count FROM reservation WHERE topo_id = ? AND status = 'pending'";
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, topoId);
	        resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            int count = resultSet.getInt("count");
	            return count > 0;
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

	    return false;
	}*/

	public boolean removeTopo(int topoId) {
		Connection connection = DBConnector.getDBConnection();
	    PreparedStatement deleteReservationStatement = null;
	    PreparedStatement deleteTopoStatement = null;
	    
	    try {
	        String deleteReservationQuery = "DELETE FROM reservation WHERE topo_id = ?";
	        
	        deleteReservationStatement = connection.prepareStatement(deleteReservationQuery);
	        deleteReservationStatement.setInt(1, topoId);
	        deleteReservationStatement.executeUpdate();
	        
	        String deleteTopoQuery = "DELETE FROM topo WHERE id = ?";
	        
	        deleteTopoStatement = connection.prepareStatement(deleteTopoQuery);
	        deleteTopoStatement.setInt(1, topoId);
	        
	        int affectedRows = deleteTopoStatement.executeUpdate();
	        
	        if (affectedRows == 0) return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
            try {
                if (deleteReservationStatement != null) deleteReservationStatement.close();
                if (deleteTopoStatement != null) deleteTopoStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		
		return true;
	}
}
