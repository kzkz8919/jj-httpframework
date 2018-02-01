package com.http.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ParseXml {

	private String filePath;
	private Document document;

	public ParseXml(String filePath) {
		this.filePath = filePath;
		this.load(this.filePath);
	}

	private void load(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			SAXReader saxReader = new SAXReader();
			try {
				document = saxReader.read(filePath);
			} catch (DocumentException e) {
				throw new RuntimeException("文件加载异常");
			}
		} else {
			throw new RuntimeException("文件不存在");
		}
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getChildren(String xPath) {
		Element element = this.getElementObjectByXpath(xPath);
		HashMap<String, String> map = new HashMap<String, String>();
		List<Element> children = element.elements();
		for (Element e : children) {
			map.put(e.getName(), e.getText());
		}
		return map;
	}

	public Element getElementObjectByXpath(String xPath) {
		return (Element) document.selectSingleNode(xPath);
	}

	@SuppressWarnings("unchecked")
	public List<Element> getElementObjectsByXpath(String xPath) {
		return document.selectNodes(xPath);
	}

	public String getElementText(String xPath) {
		Element element = this.getElementObjectByXpath(xPath);
		return element.getText();
	}
	
	public String getElementName(String xPath) {
		Element element = this.getElementObjectByXpath(xPath);
		return element.getName();
	}
	
	public static void main(String[] args) {
		ParseXml parseXml = new ParseXml("config/config.xml");
		// List<Map<String, String>> list = new ArrayList<Map<String,
		// String>>();
		Map<String, String> map = new HashMap<String, String>();
		map = parseXml.getChildren("/config/db/test");
		System.out.println(map);
		System.out.println(parseXml.getElementText("/config/timeout"));
		// Element element =
		// parseXml.getChildren("/config/db/test/host");
		// System.out.println(element.getText());
		// System.out.println(element.getName());

	}

}
