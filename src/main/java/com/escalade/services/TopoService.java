package com.escalade.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.escalade.model.Topo;

public class TopoService {
	private ArrayList<Topo> topos;
	
	public TopoService() {
		generateTopos();
	}
	
	private void generateTopos() {
		topos = new ArrayList<>(Arrays.asList(ToposList.topos));
	}
	
	public ArrayList<Topo> requestTopos(String[] regions) {
		ArrayList<Topo> results = new ArrayList<>();
		
		if (regions == null) return topos;
		
		for (Topo topo : topos) {
			for (String region : regions) {
				if (topo.getRegion().equalsIgnoreCase(region)) {
					results.add(topo);
					break;
				}
			}	
		}
		
		return results;
	}
	
	public List<String> requestRegions() {
		return new ArrayList<String>(topos.stream().map(Topo::getRegion).collect(Collectors.toSet()));
	}
}
