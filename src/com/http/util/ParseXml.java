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
	/**
	 * ����xml�ļ���������Ҫ֪��xml�ļ���·����Ȼ�������·������xml�ļ�������һ��Document�Ķ���
	 * ���������ȶ�����������String filePath,Document document
	 * Ȼ���ٶ���һ��load���������������������xml�ļ����Ӷ�����document����
	 */
	private String filePath;
	
	private Document document;
	
	/**
	 * 	����������new ParseXml����ʱ����һ��filePath�Ĳ�������,�Ӷ���ʼ��filePath��ֵ
	 * ����load�������Ӷ���ParseXml�������ʱ���ͻ����һ��document�Ķ���
	 */
	public ParseXml(String filePath) {
		this.filePath = filePath;
		this.load(this.filePath);
	}
	
	/**
	 * ��������xml�ļ������Ҳ���һ��document�Ķ���
	 */	
	private void load(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			SAXReader saxReader = new SAXReader();
			try {
				document = saxReader.read(filePath);
			} catch (DocumentException e) {
				throw new RuntimeException("�ļ������쳣");
			}
		} else {
			throw new RuntimeException("�ļ�������");
		}
	}

	/**
	 * ��xpath���õ�һ��Ԫ�ؽڵ����
	 * @param elementPath elementPath��һ��xpath·��,����"/config/driver"
	 * @return ���ص���һ���ڵ�Element����
	 */
	public Element getElementObjectByXpath(String elementPath) {
		return (Element) document.selectSingleNode(elementPath);
	}

	@SuppressWarnings("unchecked")
	public List<Element> getElementObjectsByXpath(String elementPath) {
		return document.selectNodes(elementPath);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getChildrenInfoByXpath(String elementPath) {
		Element element = this.getElementObjectByXpath(elementPath);
		HashMap<String, String> map = new HashMap<String, String>();
		List<Element> children = element.elements();
		for (Element e : children) {
			map.put(e.getName(), e.getText());
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getChildrenInfoByElement(Element element) {
		Map<String, String> map = new HashMap<String , String>();
		List<Element> children = element.elements();
		for (Element e : children) {
			map.put(e.getName(), e.getText());
		}
		return map;
	}

	/**
	 * ��xpath��ȡ��һ���������ֵ
	 */
	public String getElementText(String xPath) {
		Element element = this.getElementObjectByXpath(xPath);
		return element.getText().trim();
	}
	
	/**
	 * ��xpath��ȡ��һ�������������
	 */
	public String getElementName(String xPath) {
		Element element = this.getElementObjectByXpath(xPath);
		return element.getName().trim();
	}

//	public static void main(String[] args) {
//		ParseXml parseXml = new ParseXml("config/config.xml");
//		// List<Map<String, String>> list = new ArrayList<Map<String,
//		// String>>();
//		Map<String, String> map = new HashMap<String, String>();
//		map = parseXml.getChildrenInfoByXpath("/config/db/test");
//		System.out.println(map);
//		System.out.println(parseXml.getElementText("/config/timeout"));
//		// Element element =
//		// parseXml.getChildren("/config/db/test/host");
//		// System.out.println(element.getText());
//		// System.out.println(element.getName());
//
//	}

}
