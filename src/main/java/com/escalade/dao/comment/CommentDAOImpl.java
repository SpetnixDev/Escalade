package com.escalade.dao.comment;

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
			
			String query = "INSERT INTO comment (user_id, site_id, content, date) VALUES (?, ?, ?, ?)";
			
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
				throw new DAOException("Un problème est survenu lors de la création de l'utilisateur");
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeComment(int commentId) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
