package com.http.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {

	public String fomatDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		return formatter.format(date);
	}

	public String fomatDate(long date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		return formatter.format(date);
	}

	public static void main(String[] args) {
		DateFormat dateFormat = new DateFormat();
		System.out.println(dateFormat.fomatDate(new Date()));
		System.out.println(dateFormat.fomatDate(System.currentTimeMillis()));
	}

}
