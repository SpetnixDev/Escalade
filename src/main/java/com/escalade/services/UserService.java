package com.escalade.services;

import java.util.ArrayList;
import java.util.List;

import com.escalade.model.Topo;
import com.escalade.model.User;

public class UserService {
	private List<User> users;
	
	public UserService() {
		users = new ArrayList<>();
	}
	
	public User createUser(String username) {
		if (users.stream().allMatch(u -> !u.getUsername().equals(username))) {
			List<Topo> topos = new ArrayList<>();
			
			for (Topo topo : ToposList.topos) {
				if (topo.getOwner().equals(username)) {
					topos.add(topo);
				}
			}
			
			Topo[] toposArray = new Topo[topos.size()];
			toposArray = topos.toArray(toposArray);
			
			User user = new User(1, username, toposArray);
			
			return user;
		}
		
		return null;
	}
}
