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
	 * 解析xml文件，我们需要知道xml文件的路径，然后根据其路径加载xml文件后，生成一个Document的对象， 于是我们先定义两个变量String
	 * filePath,Document document 然后再定义一个load方法，这个方法用来加载xml文件，从而产生document对象。
	 */
	private String filePath;

	private Document document;

	/**
	 * 构造器用来new ParseXml对象时，传一个filePath的参数进来,从而初始化filePath的值
	 * 调用load方法，从而在ParseXml对象产生时，就会产生一个document的对象。
	 */
	public ParseXml(String filePath) {
		this.filePath = filePath;
		this.load(this.filePath);
	}

	/**
	 * 用来加载xml文件，并且产生一个document的对象
	 */
	private void load(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			SAXReader saxReader = new SAXReader();
			try {
				document = saxReader.read(filePath);
			} catch (DocumentException e) {
				throw new RuntimeException("文件加载异常：" + filePath);
			}
		} else {
			throw new RuntimeException("文件不存在：" + filePath);
		}
	}

	/**
	 * 用xpath来得到一个元素节点对象
	 * 
	 * @param elementPath
	 *            elementPath是一个xpath路径,比如"/config/driver"
	 * @return 返回的是一个节点Element对象
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
		Map<String, String> map = new HashMap<String, String>();
		List<Element> children = element.elements();
		for (Element e : children) {
			map.put(e.getName(), e.getText());
		}
		return map;
	}

	/**
	 * 用xpath来取得一个结点对象的值ֵ
	 */
	public String getElementText(String xPath) {
		Element element = this.getElementObjectByXpath(xPath);
		return element.getText().trim();
	}

	/**
	 * 用xpath来取得一个结点对象的名字
	 */
	public String getElementName(String xPath) {
		Element element = this.getElementObjectByXpath(xPath);
		return element.getName().trim();
	}

	// public static void main(String[] args) {
	// ParseXml parseXml = new ParseXml("config/config.xml");
	// // List<Map<String, String>> list = new ArrayList<Map<String,
	// // String>>();
	// Map<String, String> map = new HashMap<String, String>();
	// map = parseXml.getChildrenInfoByXpath("/config/db/test");
	// System.out.println(map);
	// System.out.println(parseXml.getElementText("/config/timeout"));
	// // Element element =
	// // parseXml.getChildren("/config/db/test/host");
	// // System.out.println(element.getText());
	// // System.out.println(element.getName());
	//
	// }

}
