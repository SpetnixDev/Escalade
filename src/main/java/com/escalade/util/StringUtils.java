package com.escalade.util;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringUtils {
	public static String normalizeString(String string) {
	    return Normalizer.normalize(string, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
	}
	
	public static Date parseDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
		return Date.valueOf(LocalDate.parse(date, formatter));
	}
	
	public static String formatLength(double length) {
		if (length > 1000) {
			DecimalFormat df = new DecimalFormat("0.00");
			
			return df.format(length / 1000) + " km";
		}
		
		return ((int) length) + " mètres";
	}
}
