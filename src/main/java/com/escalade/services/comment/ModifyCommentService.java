package com.escalade.services.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escalade.dao.DAOException;
import com.escalade.dao.comment.CommentDAO;
import com.escalade.services.ServiceException;

@Service
public class ModifyCommentService {
	@Autowired
	private CommentDAO commentDAO;
	
	public boolean modifyComment(int commentId, String newValue) throws ServiceException {
		try {
			return commentDAO.modifyComment(commentId, newValue);
		} catch (DAOException e) {
			throw new ServiceException("Une erreur est survenue, veuillez réessayer plus tard.");
		}
	}
}
