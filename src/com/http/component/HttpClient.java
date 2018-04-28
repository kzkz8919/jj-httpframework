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
import org.apache.http.entity.StringEntity;
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
			httpClient = HttpClients.createDefault();//实例化HttpClient对象
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(Config.timeout).setConnectTimeout(Config.timeout)
					.build();//构建RequestConfig对象
			httpGet = new HttpGet(url);//构建HttpGet对象
			httpGet.setConfig(requestConfig);//设置RequestConfig
			CloseableHttpResponse response = httpClient.execute(httpGet);//执行请求并获取response对象
			HttpEntity entity = response.getEntity();//获取响应正文对象
			return EntityUtils.toString(entity, "utf-8");//按编码输出响应正文
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
			httpClient = HttpClients.createDefault();//实例化HttpClient对象
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000)
					.build();//构建RequestConfig对象  
			httpPost = new HttpPost(url);//构建HttpPost对象
			httpPost.setConfig(requestConfig);//设置RequestConfig
			 /**
	         * 开始构建表单参数，表单参数的数据格式是：name=zhangsan&age=18,
	         * 其中，name与age为key,分别对应的value为zhangsan与18
	         * 正因为表单参数的数据格式为key-value的键值对形式，所以入参为一个Map<String, String> params
	         */
			List<NameValuePair> pl = new ArrayList<NameValuePair>();
			for (String key : map.keySet()) {
				pl.add(new BasicNameValuePair(key, map.get(key)));
			}
			//构建表单参数完毕，产生一个List<NameValuePair>的对象,其中BasicNameValuePair是产生一个key-value的键值对对象
	        /**
	         * new UrlEncodedFormEntity(ps)中的UrlEncodedFormEntity对象是将List<NameValuePair>转换为表单参数的数据格式，
	         * 并将Content-Type设置为application/x-www-form-urlencoded
	         * 所以不需要我们自已设置Content-Type了
	         */
			httpPost.setEntity(new UrlEncodedFormEntity(pl, "UTF-8"));//设置请求正文，也就是设置表单参数
			CloseableHttpResponse response = httpClient.execute(httpPost);//执行请求并获取response对象
			HttpEntity httpEntity = response.getEntity();//获取响应正文对象
			return EntityUtils.toString(httpEntity, "utf-8");//按编码输出响应正文
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

	
	public String post(String url,String body) {
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = null;
		try {
			httpClient = HttpClients.createDefault();//实例化HttpClient对象
			RequestConfig requestConfig = RequestConfig.custom().
			setSocketTimeout(2000).setConnectTimeout(2000).build();//构建RequestConfig对象
			httpPost = new HttpPost(url);//构建HttpPost对象
			httpPost.setConfig(requestConfig);//设置RequestConfig
	        /**
	         * 在请求正文中，可以随意的提交一段字符串给服务器，现在为了数据交换的统一性，所以大家都是提交的一段json串，
	         * 所以body的数据格式{"name":"zhangsan","age":18}，如果body是该数据格式，则Content-Type要设置为application/json
	         * new StringEntity(body)是指利用body构建一个请求正文对象，且StringEntity是支持Content-Type为application/json的，
	         * 所以，如果用了StringEntity对象且body的数据格式为json串，则无需我们再设置Content-Type
	         */		
			httpPost.setEntity(new StringEntity(body));//设置请求正文，也就是设置json串
			CloseableHttpResponse response = httpClient.execute(httpPost);
			HttpEntity httpEntity= response.getEntity();
			return EntityUtils.toString(httpEntity,"utf-8");	
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}catch (IOException e) {
	        e.printStackTrace();
		}finally {
			if (httpPost!=null) {
				httpPost.releaseConnection();
			}
			if (httpClient!=null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
//	public static void main(String[] args) {
//		HttpClient hc = new HttpClient();
//		Map<String, String> map = new HashMap<String,String>();
//		map.put("format", "json");
//		map.put("ip", "218.4.255.255");
//		System.out.println(hc.get("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=218.4.255.255"));
//		String url = "http://int.dpool.sina.com.cn/iplookup/iplookup.php";
//		System.out.println(map);
//		System.out.println(hc.post(url, map));
//		
//	}
}
