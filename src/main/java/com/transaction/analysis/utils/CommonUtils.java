package com.transaction.analysis.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class CommonUtils {
	public static DateTimeFormatter utcFormatter = DateTimeFormatter.ISO_DATE_TIME;

	public static LocalDateTime parseFromString(String date) {
		if (!date.isEmpty()) {
			return LocalDateTime.parse(date, utcFormatter);
		}
		return null;
	}

}
