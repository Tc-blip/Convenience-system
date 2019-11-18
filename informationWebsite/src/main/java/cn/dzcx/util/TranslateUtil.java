package cn.dzcx.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class TranslateUtil {

	@SuppressWarnings("unchecked")
	public static String zn_en(String temp){
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://fy.iciba.com/ajax.php?a=fy");
			
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.36");
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("f", "zh"));
			params.add(new BasicNameValuePair("t", "en"));
			params.add(new BasicNameValuePair("w", temp));
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
			httpPost.setEntity(entity);
			CloseableHttpResponse httpResponse = client.execute(httpPost);
			if(httpResponse.getStatusLine().getStatusCode() == 200){
				HttpEntity httpEntity = httpResponse.getEntity();
				String data = EntityUtils.toString(httpEntity, "UTF-8");
				ObjectMapper mapper = new ObjectMapper();
				Map<String,Map<String,String>> dataMap = mapper.readValue(data, Map.class);
				Map<String, String> valueMap = dataMap.get("content");
				String string = valueMap.get("out");
				if(string.contains(",")){
					return string.substring(0,string.indexOf(","));
				}
				if(string.contains("）")){
					return string.substring(string.indexOf("）")+1);
				}
				return string;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static String zn_en_(String temp){
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://fy.iciba.com/ajax.php?a=fy");
			
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.36");
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("f", "zh"));
			params.add(new BasicNameValuePair("t", "en"));
			params.add(new BasicNameValuePair("w", temp));
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
			httpPost.setEntity(entity);
			CloseableHttpResponse httpResponse = client.execute(httpPost);
			if(httpResponse.getStatusLine().getStatusCode() == 200){
				HttpEntity httpEntity = httpResponse.getEntity();
				String data = EntityUtils.toString(httpEntity, "UTF-8");
				ObjectMapper mapper = new ObjectMapper();
				Map<String,Map<String,String>> dataMap = mapper.readValue(data, Map.class);
				Map<String, String> valueMap = dataMap.get("content");
				String string = valueMap.get("out");
				return string;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String zn_en_html(String html){
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://open.iciba.com/huaci_new/dict.php");
			
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.36");
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("word", html));
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
			httpPost.setEntity(entity);
			CloseableHttpResponse httpResponse = client.execute(httpPost);
			if(httpResponse.getStatusLine().getStatusCode() == 200){
				HttpEntity httpEntity = httpResponse.getEntity();
				String data = EntityUtils.toString(httpEntity, "UTF-8");
				String[] split = data.split(";");
				String a = split[1];
				return a.substring(95, a.length()-13);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(zn_en("东风"));
	}
	
}
