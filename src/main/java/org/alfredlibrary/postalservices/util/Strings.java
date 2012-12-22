package org.alfredlibrary.postalservices.util;

public class Strings {

	public static String firstToUpper(final String string) {
		StringBuffer buffer = new StringBuffer();

		for (String part : string.split(" ")) {
			if (part.length() > 2) {
				buffer.append(part.substring(0, 1).toUpperCase() + part.substring(1).toLowerCase());
			} else {
				buffer.append(part.toLowerCase());
			}

			buffer.append(" ");
		}

		return buffer.toString().trim();
	}

}