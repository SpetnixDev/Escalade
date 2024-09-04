package com.escalade.services.comment;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalade.dao.DAOException;
import com.escalade.dao.comment.CommentDAO;
import com.escalade.model.Comment;
import com.escalade.services.ServiceException;
import com.escalade.services.user.RequestUserService;

@Service
public class SendCommentService {
	@Autowired
	private CommentDAO commentDAO;
	
	@Autowired
	private RequestUserService requestUserService;
	
	public ArrayList<Comment> sendComment(int userId, int siteId, String content, Date date) throws ServiceException {
		try {
			ArrayList<Comment> comments = commentDAO.createComment(userId, siteId, content, date);;
			
			for (Comment comment : comments) {
				comment.setUser(requestUserService.requestUserById(comment.getUserId()));
			}
			
			return comments;
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
		
	}
}
