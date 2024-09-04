package com.escalade.dao.comment;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.escalade.dao.DAOException;
import com.escalade.model.Comment;

@Repository
public interface CommentDAO {
	ArrayList<Comment> createComment(int userId, int siteId, String content, Date date) throws DAOException;
	
	ArrayList<Comment> getCommentsBySite(int siteId) throws DAOException;
	
	boolean deleteComment(int commentId) throws DAOException;
	
	boolean modifyComment(int commentId, String newValue) throws DAOException;
}
