package com.escalade.dao.comment;

import java.sql.Date;
import java.util.ArrayList;

import com.escalade.dao.DAOException;
import com.escalade.model.Comment;

public interface CommentDAO {
	ArrayList<Comment> createComment(int userId, int siteId, String content, Date date) throws DAOException;
	
	ArrayList<Comment> getCommentsBySite(int siteId) throws DAOException;
	
	boolean removeComment(int commentId); 
}
