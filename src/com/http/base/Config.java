package com.http.base;

import java.io.File;

import com.http.util.Log;
import com.http.util.ParseXml;

public class Config {

	private static Log log = new Log(Config.class);
	public static int timeout;

	static {
		ParseXml parseXml = new ParseXml(System.getProperty("user.dir") + File.separator + "config/config.xml");
		timeout = Integer.parseInt(parseXml.getElementText("/config/timeout")) * 1000;
		log.info("timeout:" + timeout);
	}
}
