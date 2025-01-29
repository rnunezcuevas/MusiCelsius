package com.chillapps.musicelsius.Controller;


import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chillapps.musicelsius.Entity.Coordinates;
import com.chillapps.musicelsius.Invoker.SpotifyAPIInvoker;
import com.chillapps.musicelsius.Invoker.WeatherAPIInvoker;
import com.chillapps.musicelsius.Repository.ServiceCallRepository;

@RestController
@RequestMapping("/musicelsius/playlist")
public class responseController {

	private final ServiceCallRepository serviceCallRepository;

	responseController(ServiceCallRepository serviceCallRepository) {
		this.serviceCallRepository = serviceCallRepository;
	}

	@RequestMapping(value = "/bycity/{city}", method = RequestMethod.GET)
	public String[] getTemperature(@PathVariable String city) {
		
		WeatherAPIInvoker<String> weatherInvoker = new WeatherAPIInvoker<String>();
		double temperature = weatherInvoker.getTemperature(city);
		SpotifyAPIInvoker spotify = new SpotifyAPIInvoker(); 
		
		if(temperature>30)
		{
			serviceCallRepository.insert_service_call("Party", "city_name", LocalDateTime.now(), 
					temperature, city);
			return spotify.getSongs("Party");
		}
		else if(temperature>=15)
		{
			serviceCallRepository.insert_service_call("Pop", "city_name", LocalDateTime.now(), 
					temperature, city);
			return spotify.getSongs("Pop");
		}
		else
		{
			serviceCallRepository.insert_service_call("Rock", "city_name", LocalDateTime.now(), 
					temperature, city);
			return spotify.getSongs("Rock");
		}
	}
	
	@RequestMapping(value = "/bycoordinates/", method = RequestMethod.GET)
	public String[] getTemperature(@RequestBody Coordinates coord) {
		
		WeatherAPIInvoker<Coordinates> weatherInvoker = new WeatherAPIInvoker<Coordinates>();
		double temperature = weatherInvoker.getTemperature(coord);
		SpotifyAPIInvoker spotify = new SpotifyAPIInvoker(); 
		if(temperature>30)
		{
			serviceCallRepository.insert_service_call("Party", "coordinates", LocalDateTime.now(), 
					temperature, coord.getLatitude() + ", " + coord.getLongitude());
			return spotify.getSongs("Party");
		}
		else if(temperature>=15)
		{
			serviceCallRepository.insert_service_call("Pop", "coordinates", LocalDateTime.now(), 
					temperature, coord.getLatitude() + ", " + coord.getLongitude());
			return spotify.getSongs("Pop");
		}
		else
		{
			serviceCallRepository.insert_service_call("Rock", "coordinates", LocalDateTime.now(), 
					temperature, coord.getLatitude() + ", " + coord.getLongitude());
			return spotify.getSongs("Rock");
		}
	}
}
