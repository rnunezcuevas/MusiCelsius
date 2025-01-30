package com.chillapps.musicelsius.Invoker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.chillapps.musicelsius.Entity.Coordinates;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component
public class WeatherAPIInvoker <T>{

	private static final Logger logger = LogManager.getLogger(WeatherAPIInvoker.class);
	
	final String APIKEY = "&appid=7da5b758b2f9176fa6aedf47f21b1a03";
	String units = "&units=metric";
	StringBuilder urlString = new StringBuilder("http://api.openweathermap.org/data/2.5/weather?");

	URL url;
	
	public Double getTemperature(T location)
	{
		StringBuilder result = new StringBuilder();
		Map<String, Object> respMap = null;
		Map<String, Object> mainMap = null;
		try
		{
			if(location instanceof String)
			{
				url = new URL(urlString.append("q=")
						.append(location)
						.append(APIKEY)
						.append(units).toString());
			}
			else
			{
				Coordinates input = (Coordinates)location;
				url = new URL(urlString.append("lat=" + input.getLatitude())
						.append("&lon=" + input.getLongitude())
						.append(APIKEY)
						.append(units).toString());
			}
			
			URLConnection conn = url.openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String line;
			while((line = rd.readLine())!=null)
			{
				result.append(line);
			}
			
			rd.close();
			respMap = jsonToMap(result.toString());
			mainMap = jsonToMap(respMap.get("main").toString()); 
		}
		catch(Exception e)
		{
			logger.error("There has been an error when getting the temperature.");
		}
		return (double) mainMap.get("temp");
	}

		public static Map<String, Object> jsonToMap(String str)
		{
			Map<String, Object> map = 
					new Gson().fromJson(str, new TypeToken<HashMap<String, Object>>() {}.getType());
			return map;
		}
}
