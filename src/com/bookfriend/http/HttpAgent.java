package com.bookfriend.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class HttpAgent {

	private String ip = "182.92.103.191";
	private String port = "8080";
	private HttpPost postMethod;
	private HttpGet getMethod;
	private DefaultHttpClient httpClient;
	private static String jSessionId = null;
	private HttpResponse response;
	private UrlEncodedFormEntity urlEntity;
	// 创建接收返回数据的对象
	private HttpEntity entity;
	// 创建流对象
	private InputStream inputstream;

	/**
	 * 
	 * @param url
	 *            服务器资源定位
	 * @param paras
	 *            传入的参数localhost:8080/login_ad
	 * @return result 返回的Json
	 */
	public String request(String url, Map<String, Object> paras, String sessionId) {
		String result = null;
		StringBuffer sb = new StringBuffer();
		postMethod = new HttpPost("http://" + ip + ":" + port + "/" + url);

		// 创建默认的客户端对象
		httpClient = HttpConnectionPool.getHttpClient();

		// 用list封装要向服务器端发送的参数
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();

		if (paras != null)
			for (String key : paras.keySet())
				pairs.add(new BasicNameValuePair(key, (String) paras.get(key)));

		try {
			// 用UrlEncodedFormEntity来封装List对象
			urlEntity = new UrlEncodedFormEntity(pairs, "utf-8");
			// 设置使用的Entity
			postMethod.setEntity(urlEntity);

			if (jSessionId != null)
				postMethod.addHeader("Cookie", "JSESSIONID=" + jSessionId);

			response = httpClient.execute(postMethod);

			CookieStore mCookieStore = httpClient.getCookieStore();
			List<Cookie> cookies = mCookieStore.getCookies();

			for (Cookie cookie : cookies)
				if (cookie.getName().equals("JSESSIONID"))
					jSessionId = cookie.getValue();
			entity = response.getEntity();
			inputstream = entity.getContent();
			// 下面是读取数据的过程
			BufferedReader br = new BufferedReader(new InputStreamReader(inputstream));

			while ((result = br.readLine()) != null) {
				sb.append(result);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		return sb.toString();
	}

	public String request(String url, HashMap<String, String> heads, HashMap<String, Object> paras) {
		String result = null;
		StringBuffer sb = new StringBuffer();
		postMethod = new HttpPost(url);
		if (heads != null)
			for (String key : heads.keySet()) {
				postMethod.addHeader(key, heads.get(key));
			}
		httpClient = new DefaultHttpClient();

		// 用list封装要向服务器端发送的参数
		List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		if (paras != null)
			for (String key : paras.keySet())
				pairs.add(new BasicNameValuePair(key, (String) paras.get(key)));
		// 用list封装要向服务器端发送的参数
		try {
			urlEntity = new UrlEncodedFormEntity(pairs, "utf-8");
			postMethod.setEntity(urlEntity);
			response = httpClient.execute(postMethod);
			entity = response.getEntity();
			inputstream = entity.getContent();
			// 下面是读取数据的过程
			BufferedReader br = new BufferedReader(new InputStreamReader(inputstream));

			while ((result = br.readLine()) != null) {
				sb.append(result);
			}
		} catch (Exception e) {
			Log.d("book", e.getMessage(), e);
		}
		return sb.toString();
	}

	public String requestForGet(String url, HashMap<String, String> heads) {
		
		String result = null;
		StringBuffer sb = new StringBuffer();
		getMethod = new HttpGet(url);
		
		if (heads != null)
			for (String key : heads.keySet()) {
				getMethod.addHeader(key, heads.get(key));
			}
		httpClient = new DefaultHttpClient();
		// 用list封装要向服务器端发送的参数
		try {
			response = httpClient.execute(getMethod);
			entity = response.getEntity();
			inputstream = entity.getContent();
			// 下面是读取数据的过程
			BufferedReader br = new BufferedReader(new InputStreamReader(inputstream));

			while ((result = br.readLine()) != null) {
				sb.append(result);
			}
		} catch (Exception e) {
			Log.d("book", e.getMessage(), e);
		}
		return sb.toString();
	}
}