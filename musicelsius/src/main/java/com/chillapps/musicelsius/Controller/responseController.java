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
		PersistenceThread persistenceThread;
		if(temperature>30)
		{
			persistenceThread = new PersistenceThread(serviceCallRepository, "Party", "city_name", temperature, city);
			persistenceThread.start();
			return spotify.getSongs("Party");
		}
		else if(temperature>=15)
		{
			persistenceThread = new PersistenceThread(serviceCallRepository, "Pop", "city_name", temperature, city);
			persistenceThread.start();
			return spotify.getSongs("Pop");
		}
		else if(temperature>=10)
		{
			persistenceThread = new PersistenceThread(serviceCallRepository, "Rock", "city_name", temperature, city);
			persistenceThread.start();
			return spotify.getSongs("Rock");
		}
		else
		{
			persistenceThread = new PersistenceThread(serviceCallRepository, "Classic", "city_name", temperature, city);
			persistenceThread.start();
			return spotify.getSongs("Classic");
		}
	}
	
	@RequestMapping(value = "/bycoordinates/", method = RequestMethod.GET)
	public String[] getTemperature(@RequestBody Coordinates coord) {
		
		WeatherAPIInvoker<Coordinates> weatherInvoker = new WeatherAPIInvoker<Coordinates>();
		double temperature = weatherInvoker.getTemperature(coord);
		SpotifyAPIInvoker spotify = new SpotifyAPIInvoker(); 
		PersistenceThread persistenceThread;
		
		if(temperature>30)
		{
			persistenceThread = new PersistenceThread(serviceCallRepository, "Party", "coordinates", temperature, coord.toString());
			persistenceThread.start();
			return spotify.getSongs("Party");
		}
		else if(temperature>=15)
		{
			persistenceThread = new PersistenceThread(serviceCallRepository, "Pop", "coordinates", temperature, coord.toString());
			persistenceThread.start();
			return spotify.getSongs("Pop");
		}
		else if(temperature>=10)
		{
			persistenceThread = new PersistenceThread(serviceCallRepository, "Rock", "coordinates", temperature, coord.toString());
			persistenceThread.start();
			return spotify.getSongs("Rock");
		}
		else
		{
			persistenceThread = new PersistenceThread(serviceCallRepository, "Classic", "city_name", temperature, coord.toString());
			persistenceThread.start();
			return spotify.getSongs("Classic");
		}
	}
}
