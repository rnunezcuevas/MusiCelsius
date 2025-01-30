package com.chillapps.musicelsius.Controller;


import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chillapps.musicelsius.Entity.Coordinates;
import com.chillapps.musicelsius.Invoker.SpotifyAPIInvoker;
import com.chillapps.musicelsius.Invoker.WeatherAPIInvoker;
import com.chillapps.musicelsius.Repository.ServiceCallRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/musicelsius/playlist")
public class responseController {

	private final ServiceCallRepository serviceCallRepository;

	responseController(ServiceCallRepository serviceCallRepository) {
		this.serviceCallRepository = serviceCallRepository;
	}

	@RequestMapping(value = "/bycity/{city}", method = RequestMethod.GET)
	@CircuitBreaker(name = "myService", fallbackMethod = "fallbackMethodCity")
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
	@CircuitBreaker(name = "myService", fallbackMethod = "fallbackMethodCoord")
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
	
	@RequestMapping(value = "/topgenre/", method = RequestMethod.GET)
	@CircuitBreaker(name = "myService", fallbackMethod = "fallbackMethodTopGenre")
    public String getTopGenre() {
        Pageable pageable = PageRequest.of(0, 1);
        List<Object[]> result = serviceCallRepository.findTopGenre(pageable);
        if (!result.isEmpty()) {
            Object[] topGenreData = result.get(0); 
            String genre = (String) topGenreData[0];
            Long numberOfCalls = ((Number) topGenreData[1]).longValue();

            
            String displayString = "Most Popular Genre is " + genre + ", with a number of calls: " + numberOfCalls;
            return displayString; 
        }

        return "Service Call History Table returned no results."; 
    }
	
	@RequestMapping(value = "/toplocation/", method = RequestMethod.GET)
	@CircuitBreaker(name = "myService", fallbackMethod = "fallbackMethodTopLocation")
    public String getTopLocation() {
        Pageable pageable = PageRequest.of(0, 1);
        List<Object[]> result = serviceCallRepository.findTopLocation(pageable);
        if (!result.isEmpty()) {
            Object[] topLocationData = result.get(0); 
            String location = (String) topLocationData[0];
            Long numberOfCalls = ((Number) topLocationData[1]).longValue();

            
            String displayString = "Most Popular Location is " + location + ", with a number of calls: " + numberOfCalls;
            return displayString; 
        }

        return "Service Call History Table returned no results."; 
    }
	
    public String[] fallbackMethodCity(Exception e) {
    	return new String[] { "Service not available when fetching temperature"
    			+ " for a city. Please try again later." };
    }
    
    public String[] fallbackMethodCoord(Exception e) {
    	return new String[] { "Service not available when fetching temperature"
    			+ " for a set of coordinates. Please try again later." };
    }
    
    public String fallbackMethodTopGenre(Exception e) {
    	return "Service not available when fetching the top genre. Please try again later.";
    }
    
    public String fallbackMethodTopLocation(Exception e) {
    	return "Service not available when fetching the top location. Please try again later.";
    }
}
