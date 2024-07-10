package com.escalade.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.escalade.model.Site;
import com.escalade.util.StringUtils;

public class SiteService {
	private ArrayList<Site> sites;
	
	public SiteService() {
		generateSites();
	}
	
	private void generateSites() {
		sites = null;
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
					if (site.getRegion().equalsIgnoreCase(region)) {
						hasRegion = true;
						
						break;
					}
				}
			}
			
			if (keywords != null) {
				for (String keyword : keywords) {
					String normalizedKeyword = StringUtils.normalizeString(keyword);
					String normalizedTitle = StringUtils.normalizeString(site.getName());
			        
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

	public Site getSiteByID(int siteId) {
		for (Site site : sites) {
			if (site.getId() == siteId) return site;
		}
		
		return null;
	}
	
	public int getSmallestSectorsAmount() {
		return sites.stream().mapToInt(site -> site.getSectors().size()).min().orElse(0);
	}
	
	public int getGreatestSectorsAmount() {
		return sites.stream().mapToInt(site -> site.getSectors().size()).max().orElse(0);
	}
}
