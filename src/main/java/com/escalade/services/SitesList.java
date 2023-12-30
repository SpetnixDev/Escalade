package com.escalade.services;

import java.util.ArrayList;
import java.util.Arrays;

import com.escalade.model.Sector;
import com.escalade.model.Site;

public class SitesList {
	public static Site[] sites = {
			new Site(1, "Site 1", "Occitanie", new ArrayList<>(Arrays.asList(
					new Sector(1, "Secteur 1", null, 1),
					new Sector(2, "Secteur 2", null, 1),
					new Sector(3, "Secteur 3", null, 1)))),
			new Site(2, "Site 2", "Provence-Alpes-Côte d'Azur", new ArrayList<>(Arrays.asList(
					new Sector(4, "Secteur 4", null, 2),
					new Sector(5, "Secteur 5", null, 2)))),
			new Site(3, "Site 3", "Corse", new ArrayList<>(Arrays.asList(
					new Sector(6, "Secteur 6", null, 3),
					new Sector(7, "Secteur 7", null, 3),
					new Sector(8, "Secteur 8", null, 3)))),
			new Site(4, "Site 4", "Occitanie", new ArrayList<>(Arrays.asList(
					new Sector(9, "Secteur 9", null, 4),
					new Sector(10, "Secteur 10", null, 4),
					new Sector(11, "Secteur 11", null, 4),
					new Sector(12, "Secteur 12", null, 4)))),
			new Site(5, "Site 5", "Auvergne-Rhône-Alpes", new ArrayList<>(Arrays.asList(
					new Sector(13, "Secteur 13", null, 5),
					new Sector(14, "Secteur 14", null, 5),
					new Sector(15, "Secteur 15", null, 5)))),
			new Site(6, "Site 6", "Provence-Alpes-Côte d'Azur", new ArrayList<>(Arrays.asList(
					new Sector(16, "Secteur 16", null, 6),
					new Sector(17, "Secteur 17", null, 6)))),
			new Site(7, "Site 7", "Auvergne-Rhône-Alpes", new ArrayList<>(Arrays.asList(
					new Sector(18, "Secteur 18", null, 7),
					new Sector(19, "Secteur 19", null, 7),
					new Sector(20, "Secteur 20", null, 7),
					new Sector(21, "Secteur 21", null, 7),
					new Sector(22, "Secteur 22", null, 7)))),
			new Site(8, "Site 8", "Bretagne", new ArrayList<>(Arrays.asList(
					new Sector(23, "Secteur 23", null, 8),
					new Sector(24, "Secteur 24", null, 8),
					new Sector(25, "Secteur 25", null, 8)))),
			new Site(9, "Site 9", "Occitanie", new ArrayList<>(Arrays.asList(
					new Sector(26, "Secteur 26", null, 9),
					new Sector(27, "Secteur 27", null, 9),
					new Sector(28, "Secteur 28", null, 9),
					new Sector(29, "Secteur 29", null, 9)))),
			new Site(10, "Site 10", "Corse", new ArrayList<>(Arrays.asList(
					new Sector(30, "Secteur 30", null, 10),
					new Sector(31, "Secteur 31", null, 10),
					new Sector(32, "Secteur 32", null, 10),
					new Sector(33, "Secteur 33", null, 10)))),
			new Site(11, "Site 11", "Normandie", new ArrayList<>(Arrays.asList(
					new Sector(34, "Secteur 34", null, 11),
					new Sector(35, "Secteur 35", null, 11)))),
			new Site(12, "Site 12", "Corse", new ArrayList<>(Arrays.asList(
					new Sector(36, "Secteur 36", null, 12),
					new Sector(37, "Secteur 37", null, 12),
					new Sector(38, "Secteur 38", null, 12))))
	};
}
