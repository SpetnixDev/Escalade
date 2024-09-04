package com.escalade.services.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalade.dao.DAOException;
import com.escalade.dao.comment.CommentDAO;
import com.escalade.services.ServiceException;

@Service
public class DeleteCommentService {
	@Autowired
	private CommentDAO commentDAO;
	
	public boolean deleteComment(int commentId) throws ServiceException {
		try {
			return commentDAO.deleteComment(commentId);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
