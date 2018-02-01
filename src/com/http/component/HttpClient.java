package com.http.component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.http.base.Config;

public class HttpClient {

	public String get(String url) {
		CloseableHttpClient httpClient = null;
		HttpGet httpGet = null;
		try {
			httpClient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(Config.timeout).setConnectTimeout(Config.timeout)
					.build();
			httpGet = new HttpGet(url);
			httpGet.setConfig(requestConfig);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
		} finally {
			try {
				if (httpGet != null) {
					httpGet.releaseConnection();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return null;

	}

	public String post(String url, Map<String, String> map) {
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = null;
		try {
			httpClient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000)
					.build();
			httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			List<NameValuePair> pl = new ArrayList<NameValuePair>();
			for (String key : map.keySet()) {
				pl.add(new BasicNameValuePair(key, map.get(key)));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pl, "UTF-8"));
			CloseableHttpResponse response = httpClient.execute(httpPost);
			HttpEntity httpEntity = response.getEntity();
			return EntityUtils.toString(httpEntity, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpPost != null) {
					httpPost.releaseConnection();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	public static void main(String[] args) {
		HttpClient hc = new HttpClient();
		Map<String, String> map = new HashMap<String,String>();
		map.put("format", "json");
		map.put("ip", "218.4.255.255");
		System.out.println(hc.get("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=218.4.255.255"));
		String url = "http://int.dpool.sina.com.cn/iplookup/iplookup.php";
		System.out.println(map);
		System.out.println(hc.post(url, map));
		
	}
}
