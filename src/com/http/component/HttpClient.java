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
			httpClient = HttpClients.createDefault();//ʵ����HttpClient����
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(Config.timeout).setConnectTimeout(Config.timeout)
					.build();//����RequestConfig����
			httpGet = new HttpGet(url);//����HttpGet����
			httpGet.setConfig(requestConfig);//����RequestConfig
			CloseableHttpResponse response = httpClient.execute(httpGet);//ִ�����󲢻�ȡresponse����
			HttpEntity entity = response.getEntity();//��ȡ��Ӧ���Ķ���
			return EntityUtils.toString(entity, "utf-8");//�����������Ӧ����
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
			httpClient = HttpClients.createDefault();//ʵ����HttpClient����
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000)
					.build();//����RequestConfig����  
			httpPost = new HttpPost(url);//����HttpPost����
			httpPost.setConfig(requestConfig);//����RequestConfig
			 /**
	         * ��ʼ�����������������������ݸ�ʽ�ǣ�name=zhangsan&age=18,
	         * ���У�name��ageΪkey,�ֱ��Ӧ��valueΪzhangsan��18
	         * ����Ϊ�����������ݸ�ʽΪkey-value�ļ�ֵ����ʽ���������Ϊһ��Map<String, String> params
	         */
			List<NameValuePair> pl = new ArrayList<NameValuePair>();
			for (String key : map.keySet()) {
				pl.add(new BasicNameValuePair(key, map.get(key)));
			}
			//������������ϣ�����һ��List<NameValuePair>�Ķ���,����BasicNameValuePair�ǲ���һ��key-value�ļ�ֵ�Զ���
	        /**
	         * new UrlEncodedFormEntity(ps)�е�UrlEncodedFormEntity�����ǽ�List<NameValuePair>ת��Ϊ�����������ݸ�ʽ��
	         * ����Content-Type����Ϊapplication/x-www-form-urlencoded
	         * ���Բ���Ҫ������������Content-Type��
	         */
			httpPost.setEntity(new UrlEncodedFormEntity(pl, "UTF-8"));//�����������ģ�Ҳ�������ñ�����
			CloseableHttpResponse response = httpClient.execute(httpPost);//ִ�����󲢻�ȡresponse����
			HttpEntity httpEntity = response.getEntity();//��ȡ��Ӧ���Ķ���
			return EntityUtils.toString(httpEntity, "utf-8");//�����������Ӧ����
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
			httpClient = HttpClients.createDefault();//ʵ����HttpClient����
			RequestConfig requestConfig = RequestConfig.custom().
			setSocketTimeout(2000).setConnectTimeout(2000).build();//����RequestConfig����
			httpPost = new HttpPost(url);//����HttpPost����
			httpPost.setConfig(requestConfig);//����RequestConfig
	        /**
	         * �����������У�����������ύһ���ַ�����������������Ϊ�����ݽ�����ͳһ�ԣ����Դ�Ҷ����ύ��һ��json����
	         * ����body�����ݸ�ʽ{"name":"zhangsan","age":18}�����body�Ǹ����ݸ�ʽ����Content-TypeҪ����Ϊapplication/json
	         * new StringEntity(body)��ָ����body����һ���������Ķ�����StringEntity��֧��Content-TypeΪapplication/json�ģ�
	         * ���ԣ��������StringEntity������body�����ݸ�ʽΪjson��������������������Content-Type
	         */		
			httpPost.setEntity(new StringEntity(body));//�����������ģ�Ҳ��������json��
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
