package com.http.testcase;

import java.util.List;
import java.util.Map;

import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.http.assertion.Assertion;
import com.http.base.TestBase;
import com.http.report.DefinedReport;
import com.zf.zson.ZSON;
import com.zf.zson.result.ZsonResult;

@Listeners(DefinedReport.class)
public class TestIP3 extends TestBase{
	@Test(dataProvider="providerMethod",description="测试DEMO")
	public void testIP3(Map<String, String> param){
		Reporter.log("this is demo!");
		String result = httpClient.get(param.get("url"));
		ZsonResult re = ZSON.parseJson(result);
		List<Object> list = re.getValues("/userId");
		Object userId = list.get(0);
		Assertion.assertEquals(userId, param.get("userId"));
	}
	

}
