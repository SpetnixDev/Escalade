package com.escalade.dao.pitch;

import java.util.List;

import com.escalade.dao.DAOException;
import com.escalade.model.Pitch;

public interface PitchDAO {
	public void registerPitch(int routeId, String name, String description, double length, String difficulty) throws DAOException;
	
	List<Pitch> requestPitchesByRoute(int routeId) throws DAOException;
}
