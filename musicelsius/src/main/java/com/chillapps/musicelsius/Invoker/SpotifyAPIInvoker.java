package com.chillapps.musicelsius.Invoker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SpotifyAPIInvoker {

	StringBuffer response = new StringBuffer();
	URL url;

	public String getData()
	{
		try {
			url = new URL("https://api.spotify.com/v1/tracks/11dFghVXANMlKmJXsNCbNl");
			HttpURLConnection myURLConnection = (HttpURLConnection)url.openConnection();
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
		    for (String line; (line = reader.readLine()) != null;) {
		        System.out.println(line);
		        response.append(line);
		    }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response.toString();
	}

	
	
	
	
}
