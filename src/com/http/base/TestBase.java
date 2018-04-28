package com.http.base;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

import com.http.component.HttpClient;
import com.http.util.Global;
import com.http.util.Log;
import com.http.util.ParseXml;

@Listeners({ com.http.assertion.AssertionListener.class })
public class TestBase {

	protected Log log = new Log(this.getClass());

	public String testCase;

	protected HttpClient httpClient;

	private Map<String, String> commonMap;

	private ParseXml px;

	@BeforeClass(alwaysRun = true)
	public void getTestCase() {
		testCase = this.getClass().getSimpleName();
		httpClient = new HttpClient();
	}

	public boolean testDateisExist() {
		File file = new File(System.getProperty("user.dir") + File.separator + "testdata" + File.separator + testCase +".xml");
		if (!file.exists()) {
			return false;
		}
		return true;
	}

	public void initParseXml() {
		if (px == null) {
			px = new ParseXml(System.getProperty("user.dir") + File.separator + "testdata" + File.separator + testCase +".xml");
		}
	}

	public void getCommonMap() {
		if (commonMap == null) {
			commonMap = px.getChildrenInfoByXpath("/*/common");

		}
	}

	public Map<String, String> getMergeMap(Map<String, String> map1, Map<String, String> map2) {
		Iterator<String> it = map2.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = map2.get(key);
			if (!map1.containsKey(key)) {
				map1.put(key, value);
			}

		}
		return map1;
	}

	@DataProvider
	public Object[][] providerMethod(Method method) {
		List<Element> elements = null;
		if (testDateisExist()) {
			this.initParseXml();
			this.getCommonMap();
			String methodName = method.getName();
			elements = px.getElementObjectsByXpath("/*/" + methodName);
		}

		int index = 0;
		if (elements != null) {
			index = elements.size();
		}
		if (index == 0) {
			index++;
		}

		Object[][] objects = new Object[index][];

		for (int i = 0; i < index; i++) {
			Map<String, String> mergerMap = new HashMap<String, String>();
			if(this.testDateisExist() && elements.size()>0){
				mergerMap = this.getMergeMap(px.getChildrenInfoByElement(elements.get(i)), commonMap);
			}else if(this.testDateisExist() && elements.size()==0){
				mergerMap = this.getMergeMap(new HashMap<String, String>(), commonMap);
			}
//			Map<String, String> mergerGlobal = new HashMap<String, String>();
			Map<String, String> mergerGlobal = this.getMergeMap(mergerMap, Global.global);
			Object[] temp = new Object[] { mergerGlobal };
			objects[i] = temp;
		}

		return objects;
	}

}
