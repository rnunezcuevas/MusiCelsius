package com.chillapps.musicelsius.Configuration;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

@Service
public class SpotifyConfiguration {
	
	@Value("${redirect.server.ip}")
	private String customIp;
	
	public SpotifyApi getSpotifyObject() {
		 URI redirectedURL =  SpotifyHttpManager.makeUri(customIp + "/api/get-user-code/");
		 
		 return new SpotifyApi
				 .Builder()
				 .setClientId("3cb04550976b47198f64b4c37c066a37")
				 .setClientSecret("c905499b96d44db1bda09760eb0dfde5")
				 .setRedirectUri(redirectedURL)
				 .build();
	}
}