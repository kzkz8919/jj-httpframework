package com.http.testcase;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.http.assertion.Assertion;
import com.http.base.TestBase;
import com.zf.zson.ZSON;
import com.zf.zson.result.ZsonResult;

public class TestIP2 extends TestBase{
	@Test(dataProvider="providerMethod")
	public void testIP2(Map<String, String> param){
		String result = httpClient.get(param.get("url"));
		ZsonResult re = ZSON.parseJson(result);
		List<Object> list = re.getValues("/userId");
		Object userId = list.get(0);
		Assertion.assertEquals(userId, param.get("userId"));
	}
	

}
