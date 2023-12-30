package com.escalade.services;

import java.text.Normalizer;
import java.text.Normalizer.Form;
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
					if (topo.getRegion().equalsIgnoreCase(region)) {
						hasRegion = true;
						
						break;
					}
				}
			}
			
			if (keywords != null) {
				for (String keyword : keywords) {
					String normalizedKeyword = normalizeString(keyword);
					String normalizedTitle = normalizeString(topo.getTitle());
			        String normalizedDescription = normalizeString(topo.getDescription());
			        
					if (!(normalizedTitle.contains(normalizedKeyword) || normalizedDescription.contains(normalizedKeyword))) {
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
		return new ArrayList<String>(topos.stream().map(Topo::getRegion).distinct().sorted().collect(Collectors.toList()));
	}
	
	private String normalizeString(String string) {
	    return Normalizer.normalize(string, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
	}
}
