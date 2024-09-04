/*package com.escalade.dao.comment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.model.Comment;
import com.escalade.util.DBUtils;

public class CommentDAOImpl implements CommentDAO {
	private DAOFactory daoFactory;

	public CommentDAOImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public ArrayList<Comment> createComment(int userId, int siteId, String content, Date date) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Comment> comments = null;
		
		try {
			connection = daoFactory.getConnection();
			
			String query = "INSERT INTO comments (user_id, site_id, content, date) VALUES (?, ?, ?, ?)";
			
			preparedStatement = connection.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, siteId);
			preparedStatement.setString(3, content);
			preparedStatement.setDate(4, date);
			preparedStatement.executeUpdate();
			
			connection.commit();
			
			resultSet = preparedStatement.getGeneratedKeys();
			
			if (resultSet.next()) {
				comments = getCommentsBySite(siteId);
			} else {
				throw new DAOException("Un problème est survenu lors de l'envoi du commentaire");
			}
			
			return comments;
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
	public ArrayList<Comment> getCommentsBySite(int siteId) throws DAOException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Comment> comments = null;

        try {
        	connection = daoFactory.getConnection();
        	
            String query = "SELECT * FROM comments WHERE site_id = ? ORDER BY id DESC";
            
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, siteId);
            resultSet = preparedStatement.executeQuery();

            comments = new ArrayList<>();
            
            while (resultSet.next()) {
            	int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                String content = resultSet.getString("content");
                Date date = resultSet.getDate("date");
                
                comments.add(new Comment(id, userId, siteId, content, date));
            }

            return comments;
        } catch (SQLException e) {
	        throw new DAOException("Impossible de communiquer avec la base de données");
	    } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
	    }
	}

	@Override
	public boolean deleteComment(int commentId) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    
	    try {
	    	connection = daoFactory.getConnection();
	        
	        String query = "DELETE FROM comments WHERE id = ?";
	        
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, commentId);
	        
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
	public boolean modifyComment(int commentId, String newValue) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connection = daoFactory.getConnection();
	        
	        String query = "UPDATE comments SET content = ? WHERE id = ?";
	        
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, newValue);
	        preparedStatement.setInt(2, commentId);
	        
	        int affectedRows = preparedStatement.executeUpdate();
	        
	        connection.commit();
	        
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        try {
	            if (connection != null) connection.rollback();
	        } catch (SQLException e2) {}
	        
	        throw new DAOException("Impossible de communiquer avec la base de données");
	    } finally {
	        DBUtils.closeQuietly(connection, null, preparedStatement);
	    }
	}
	
}
*/

package com.escalade.dao.comment;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.escalade.dao.DAOException;
import com.escalade.model.Comment;

@Repository
public class CommentDAOImpl implements CommentDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ArrayList<Comment> createComment(int userId, int siteId, String content, Date date) throws DAOException {
        final String query = "INSERT INTO comments (user_id, site_id, content, date) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                
                ps.setInt(1, userId);
                ps.setInt(2, siteId);
                ps.setString(3, content);
                ps.setDate(4, date);
                
                return ps;
            }, keyHolder);

            return getCommentsBySite(siteId);
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public ArrayList<Comment> getCommentsBySite(int siteId) throws DAOException {
        String query = "SELECT * FROM comments WHERE site_id = ? ORDER BY id DESC";

        try {
            return (ArrayList<Comment>) jdbcTemplate.query(query, new CommentRowMapper(), new Object[]{siteId});
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public boolean deleteComment(int commentId) throws DAOException {
        String query = "DELETE FROM comments WHERE id = ?";

        try {
            int affectedRows = jdbcTemplate.update(query, commentId);
            
            return affectedRows > 0;
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public boolean modifyComment(int commentId, String newValue) throws DAOException {
        String query = "UPDATE comments SET content = ? WHERE id = ?";

        try {
            int affectedRows = jdbcTemplate.update(query, newValue, commentId);
            
            return affectedRows > 0;
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    private static class CommentRowMapper implements RowMapper<Comment> {
        @Override
        public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            int userId = rs.getInt("user_id");
            int siteId = rs.getInt("site_id");
            String content = rs.getString("content");
            Date date = rs.getDate("date");
            
            return new Comment(id, userId, siteId, content, date);
        }
    }
}
