package com.escalade.utils;

import java.text.Normalizer;
import java.text.Normalizer.Form;

public class StringUtils {
	public static String normalizeString(String string) {
	    return Normalizer.normalize(string, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
	}
}
