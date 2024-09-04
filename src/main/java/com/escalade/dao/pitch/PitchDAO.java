package com.escalade.dao.pitch;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.escalade.dao.DAOException;
import com.escalade.model.Pitch;

@Repository
public interface PitchDAO {
	public void registerPitch(int routeId, String name, String description, double length, String difficulty) throws DAOException;
	
	List<Pitch> requestPitchesByRoute(int routeId) throws DAOException;
}
