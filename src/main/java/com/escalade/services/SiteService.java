package com.escalade.services;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.escalade.model.Site;

public class SiteService {
	private ArrayList<Site> sites;
	
	public SiteService() {
		generateSites();
	}
	
	private void generateSites() {
		sites = new ArrayList<>(Arrays.asList(SitesList.sites));
	}
	
	public ArrayList<Site> requestSites(String[] regions, String[] keywords, boolean official) {
		ArrayList<Site> results = new ArrayList<>();
		
		if (regions == null && keywords == null && !official) return sites;
		
		for (Site site : sites) {
			if (official && !site.isOfficial()) continue;
			
			boolean hasRegion = true, hasKeywords = true;
			
			if (regions != null) {
				hasRegion = false;
				
				for (String region : regions) {
					System.out.println(site.getRegion() + " - " + region);
					
					if (site.getRegion().equalsIgnoreCase(region)) {
						hasRegion = true;
						
						break;
					}
				}
			}
			
			if (keywords != null) {
				for (String keyword : keywords) {
					String normalizedKeyword = normalizeString(keyword);
					String normalizedTitle = normalizeString(site.getName());
			        
					if (!(normalizedTitle.contains(normalizedKeyword))) {
						hasKeywords = false;
						
						break;
					}
				}
			}
			
			if (hasRegion && hasKeywords) results.add(site);
		}
		
		return results;
	}
	
	public List<String> requestRegions() {
		return new ArrayList<String>(sites.stream().map(Site::getRegion).distinct().sorted().collect(Collectors.toList()));
	}
	
	private String normalizeString(String string) {
	    return Normalizer.normalize(string, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
	}

	public Site getSiteByID(int siteId) {
		for (Site site : sites) {
			if (site.getId() == siteId) return site;
		}
		
		return null;
	}
}
