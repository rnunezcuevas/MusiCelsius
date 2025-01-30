package com.chillapps.musicelsius.Invoker;

import okhttp3.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SpotifyAPIInvoker {
	
	private static final Logger logger = LogManager.getLogger(SpotifyAPIInvoker.class);
	
    private static final String CLIENT_ID = "3cb04550976b47198f64b4c37c066a37";
    private static final String CLIENT_SECRET = "c905499b96d44db1bda09760eb0dfde5";
    private static final String TOKEN_URL = "https://accounts.spotify.com/api/token";
    private static final String PLAYLIST_URL = "https://api.spotify.com/v1/playlists/{playlist_id}";
    private static final String ROCK_PLAYLIST = "7EmlmN4hPwzhvRzo5o2Fjj";
    private static final String PARTY_PLAYLIST = "6OGIeuozdAXY3Iq8WvhQCx";
    private static final String POP_PLAYLIST = "4XUnwSKQ4tpzrfyYC0BPjT";
    private static final String CLASSIC_PLAYLIST = "3rCykffDnQgcYC39V7zist";
    
    public String[] getSongs(String genre) {
        try {
        	String accessToken = getAccessToken();
        	if(genre.equals("Rock"))
        	{
        		return getPlaylistTracks(accessToken, ROCK_PLAYLIST);
        	}
        	else if(genre.equals("Pop"))
        	{
        		return getPlaylistTracks(accessToken, POP_PLAYLIST);
        	}
        	else if(genre.equals("Classic"))
        	{
        		return getPlaylistTracks(accessToken, CLASSIC_PLAYLIST);
        	}
        	else
        	{
        		return getPlaylistTracks(accessToken, PARTY_PLAYLIST);
        	}
            
        } catch (IOException e) {
            logger.error("An error ocurred during spotify API operations: "
            		+e.getMessage());
            return null;
        }
    }

    private static String getAccessToken() throws IOException {
        OkHttpClient client = new OkHttpClient();
        String credentials = Credentials.basic(CLIENT_ID, CLIENT_SECRET);
        RequestBody body = new FormBody.Builder().add("grant_type", "client_credentials").build();

        Request request = new Request.Builder()
                .url(TOKEN_URL)
                .post(body)
                .addHeader("Authorization", credentials)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String responseData = response.body().string();
            JSONObject jsonObject = new JSONObject(responseData);
            return jsonObject.getString("access_token");
        } else {
            throw new IOException("Error when getting the token: " + response.code());
        }
    }

    private static String[] getPlaylistTracks(String accessToken, String playlistId) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = PLAYLIST_URL.replace("{playlist_id}", playlistId);
        
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String responseData = response.body().string();
            
            try {
                JSONObject jsonObject = new JSONObject(responseData);
                JSONObject tracks = jsonObject.getJSONObject("tracks");
                JSONArray items = tracks.getJSONArray("items");

                // Creating an array in order to store tracks
                String[] songNames = new String[items.length()];

                for (int i = 0; i < items.length(); i++) {
                    JSONObject track = items.getJSONObject(i).getJSONObject("track");
                    songNames[i] = track.getString("name"); // Get track name
                }

                // Printing tracks
                for (String songName : songNames) {
                    logger.info("Retrieved Song: " + songName);
                }
                
                return songNames;

            } catch (JSONException err) {
            	logger.error("An error ocurred during the conversion to JSON object "
            			+ "with the response data. " + err.getMessage());
                return null;
            }
        } else {
            logger.error("Something went wrong with the response from"
            		+ " the Spotify API: " + response.code());
            return null;
        }
    }
}