package com.http.util;

import java.io.File;
import java.util.Map;

public class Global {

	public static Map<String, String> global;

	static {
		ParseXml px = new ParseXml(System.getProperty("user.dir") + File.separator + "testdata/global.xml");
		global = px.getChildrenInfoByXpath("/*");
	}
	

}
