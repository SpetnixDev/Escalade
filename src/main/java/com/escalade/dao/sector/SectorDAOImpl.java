/*package com.escalade.dao.sector;

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
	
	@Override
	public int getMinSectorCount() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            
            String query = "WITH SectorCounts AS (" +
                           "    SELECT site_id, COUNT(*) AS sector_count" +
                           "    FROM sector" +
                           "    GROUP BY site_id" +
                           ")" +
                           "SELECT MIN(sector_count) AS min_sectors FROM SectorCounts";

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("min_sectors");
            } else {
                throw new DAOException("Un problème est survenu");
            }
        } catch (SQLException e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
        }
    }

	@Override
    public int getMaxSectorCount() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            
            String query = "WITH SectorCounts AS (" +
                           "    SELECT site_id, COUNT(*) AS sector_count" +
                           "    FROM sector" +
                           "    GROUP BY site_id" +
                           ")" +
                           "SELECT MAX(sector_count) AS max_sectors FROM SectorCounts";

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("max_sectors");
            } else {
                throw new DAOException("Un problème est survenu");
            }
        } catch (SQLException e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
        }
    }
	
}
*/

package com.escalade.dao.sector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.escalade.dao.DAOException;
import com.escalade.model.Sector;

@Repository
public class SectorDAOImpl implements SectorDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int registerSector(int siteId, String name, String description) throws DAOException {
        final String query = "INSERT INTO sector (site_id, name, description) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(query, new String[] {"id"});
                ps.setInt(1, siteId);
                ps.setString(2, name);
                ps.setString(3, description);
                return ps;
            }, keyHolder);

            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public List<Sector> requestSectorsBySite(int siteId) throws DAOException {
        String query = "SELECT * FROM sector WHERE site_id = ?";

        try {
            return jdbcTemplate.query(query, new SectorRowMapper(), new Object[]{siteId});
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public int getMinSectorCount() throws DAOException {
        String query = "WITH SectorCounts AS (" +
                       "    SELECT site_id, COUNT(*) AS sector_count" +
                       "    FROM sector" +
                       "    GROUP BY site_id" +
                       ")" +
                       "SELECT MIN(sector_count) AS min_sectors FROM SectorCounts";

        try {
            Integer minSectors = jdbcTemplate.queryForObject(query, Integer.class);
            if (minSectors != null) {
                return minSectors;
            } else {
                throw new DAOException("Un problème est survenu");
            }
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public int getMaxSectorCount() throws DAOException {
        String query = "WITH SectorCounts AS (" +
                       "    SELECT site_id, COUNT(*) AS sector_count" +
                       "    FROM sector" +
                       "    GROUP BY site_id" +
                       ")" +
                       "SELECT MAX(sector_count) AS max_sectors FROM SectorCounts";

        try {
            Integer maxSectors = jdbcTemplate.queryForObject(query, Integer.class);
            if (maxSectors != null) {
                return maxSectors;
            } else {
                throw new DAOException("Un problème est survenu");
            }
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    private static class SectorRowMapper implements RowMapper<Sector> {
        @Override
        public Sector mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            return new Sector(id, name, description);
        }
    }
}
