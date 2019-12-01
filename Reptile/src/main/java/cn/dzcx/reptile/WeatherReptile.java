package cn.dzcx.reptile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Couchbase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dzcx.pojo.DayWeather;
@RestController
public class WeatherReptile {

	@SuppressWarnings({ "static-access", "rawtypes" })
	@RequestMapping(value="weather")
	public Map<String,Object> weather(String name) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.getClass().getClassLoader().getResource("static/zipcode.txt").getPath()));
			String line = null;
			String code = null;
			while((line=reader.readLine())!=null){
				if(line.split(",")[0].equals(name) ) {
					code = line.split(",")[2];
					break;
				}
			}
			reader.close();
			Map<String,Object> mapdata = new HashMap<String,Object>();
			List<DayWeather> list = new ArrayList<DayWeather>();
			
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet("http://api.weatherunlocked.com/api/forecast/us."+name+"?app_id=9f7a442c&app_key=5a37830ef10f82a4b94e487a11f2340f");
			CloseableHttpResponse httpResponse = client.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode() == 200){
				String data = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
				JSONObject jsStr = JSONObject.parseObject(data);
				JSONArray array = jsStr.getJSONArray("Days");
				
				JSONObject json2 = array.getJSONObject(0); 
				JSONArray TimeFrames = json2.getJSONArray("Timeframes");
				for(int j=0;j<TimeFrames.size();j++) {
					JSONObject timeframes = TimeFrames.getJSONObject(j);
					String time = timeframes.getString("time");
					String temp = timeframes.getString("temp_f");
					String info = timeframes.getString("wx_desc");
					String compass = timeframes.getString("winddir_compass");
					String mph = timeframes.getString("windspd_mph");
		
					DayWeather dayWeather = new DayWeather(time,info,temp,compass,mph);
					
					list.add(dayWeather);
				
				}
				
				String tianqi = "<ul class=\"t clearfix\">";
				
				for(int k=0;k<array.size();k++) {
					JSONObject dinfo = array.getJSONObject(k);
					JSONArray tf = dinfo.getJSONArray("Timeframes");
					String date = dinfo.getString("date");
					JSONObject timeframes = tf.getJSONObject(0);
					String weather = timeframes.getString("wx_desc");
					String maxF = dinfo.getString("temp_max_f");
					String minF = dinfo.getString("temp_min_f");
					String wind = timeframes.getString("winddir_compass");
					String windSpd = dinfo.getString("windspd_max_mph");
					tianqi += "<li class=\"on\"> <h1> "+date+"</h1> <big class=\"png40\"></big> <big class=\"png40 n00\"></big> <p title=\"\" class=\"wea\">"+weather+"</p> <p class=\"tem\"> <span>"+minF+"F</span>/<i>"+maxF+"F</i> </p> <p class=\"win\"> <em> <span title=\"\" class=\""+wind+"\"></span> </em> <i>&lt; "+windSpd+"</i> </p> \r\n" + 
							"  <div class=\"slid\"></div> </li> ";
				} 
				tianqi =tianqi+ "</ul>";
				
				mapdata.put("tianqi", tianqi);
				mapdata.put("list", list);
				mapdata.put("cityName", code);
				return mapdata;
			}
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(value="news")
	public static Map<String,List<String>> news(){
		try {
			Map<String,List<String>> map = new HashMap<String,List<String>>();
			List<String> list = new ArrayList<String>();
			List<String> url = new ArrayList<String>();
			
			CloseableHttpClient createDefault = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet("https://newsapi.org/v2/top-headlines?sources=google-news&apiKey=1401dc1d469c4fc6b4db02c304219c27");
			CloseableHttpResponse httpResponse = createDefault.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode() == 200){
				String string = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
				JSONObject jsStr = JSONObject.parseObject(string);
				JSONArray array = jsStr.getJSONArray("articles");
				for(int j=0; j<array.size();j++) {
					JSONObject source = array.getJSONObject(j);
					String title = source.getString("title");
					String content = source.getString("content");
					list.add(title);
					url.add(content);
				}
				
			}
			map.put("title", list);
			map.put("url", url);
			return map;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="getNewsText")
	public Map<String,Object> getNewsText(String url){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			List<String> conent = new ArrayList<String>();
			
			CloseableHttpClient createDefault = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet("https://newsapi.org/v2/top-headlines?sources=google-news&apiKey=1401dc1d469c4fc6b4db02c304219c27");
			CloseableHttpResponse httpResponse = createDefault.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode() == 200){
				String string = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
				JSONObject jsStr = JSONObject.parseObject(string);
				JSONArray array = jsStr.getJSONArray("articles");
				for(int j=0; j<array.size();j++) {
					JSONObject source = array.getJSONObject(j);
					String title = source.getString("title");
					String content = source.getString("content");
					String dateString = source.getString("publishedAt");
					map.put("title",title);
					map.put("date", dateString);
					conent.add(content);
				}
			}
			map.put("conent", conent);
			return map;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
