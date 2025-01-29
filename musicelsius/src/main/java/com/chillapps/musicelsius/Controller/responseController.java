package com.chillapps.musicelsius.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chillapps.musicelsius.Entity.Coordinates;
import com.chillapps.musicelsius.Invoker.SpotifyAPIInvoker;
import com.chillapps.musicelsius.Invoker.WeatherAPIInvoker;

@RestController
@RequestMapping("/musicelsius/playlist")
public class responseController {

	//private final ArtistRepository artistRepository;

	//artistController(ArtistRepository artistRepository) {
		//this.artistRepository = artistRepository;
	//}

	@RequestMapping(value = "/bycity/{city}", method = RequestMethod.GET)
	public String[] getTemperature(@PathVariable String city) {
		WeatherAPIInvoker<String> weatherInvoker = new WeatherAPIInvoker<String>();
		Double temperature = weatherInvoker.getTemperature(city);
		SpotifyAPIInvoker spotify = new SpotifyAPIInvoker(); 
		if(temperature>30)
		{
			return spotify.getSongs("Party");
		}
		else if(temperature>=15)
		{
			return spotify.getSongs("Pop");
		}
		else
		{
			return spotify.getSongs("Rock");
		}
	}
	
	@RequestMapping(value = "/bycoordinates/", method = RequestMethod.GET)
	public String[] getTemperature(@RequestBody Coordinates coord) {
		
		Coordinates inputCoords = new Coordinates();
		
		WeatherAPIInvoker<Coordinates> weatherInvoker = new WeatherAPIInvoker<Coordinates>();
		Double temperature = weatherInvoker.getTemperature(coord);
		SpotifyAPIInvoker spotify = new SpotifyAPIInvoker(); 
		if(temperature>30)
		{
			return spotify.getSongs("Party");
		}
		else if(temperature>=15)
		{
			return spotify.getSongs("Pop");
		}
		else
		{
			return spotify.getSongs("Rock");
		}
	}
	
	/*@RequestMapping(value = "testingartistresponsebody/{id}", method = RequestMethod.POST)
	@ResponseBody
	Artists testingRest(@PathVariable Integer id,
			@RequestBody Artists newArtist ) {
		Artists responseArtist = new Artists();
		responseArtist.setName(newArtist.getName());
		responseArtist.setGenre(99);
		responseArtist.setGrammys(99);
		
		return responseArtist;
		
	}*/
}
