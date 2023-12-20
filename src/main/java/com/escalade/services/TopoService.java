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
	
	public ArrayList<Topo> requestTopos(String[] regions, String[] keywords) {
		ArrayList<Topo> results = new ArrayList<>();
		
		if (regions == null && keywords == null) return topos;
		
		for (Topo topo : topos) {
			boolean hasRegion = true, hasKeywords = true;
			
			if (regions != null) {
				hasRegion = false;
				
				for (String region : regions) {
					System.out.println(topo.getRegion() + " - " + region);
					
					if (topo.getRegion().equalsIgnoreCase(region)) {
						hasRegion = true;
						
						break;
					}
				}
			}
			
			if (keywords != null) {
				for (String keyword : keywords) {
					if (!(topo.getTitle().contains(keyword) || topo.getDescription().contains(keyword))) {
						hasKeywords = false;
						
						break;
					}
				}
			}
			
			if (hasRegion && hasKeywords) results.add(topo);
		}
		
		return results;
	}
	
	public List<String> requestRegions() {
		return new ArrayList<String>(topos.stream().map(Topo::getRegion).collect(Collectors.toSet()));
	}
}
