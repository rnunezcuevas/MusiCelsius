package com.chillapps.musicelsius.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.chillapps.musicelsius.Invoker.WeatherAPIInvokerFactory;
import com.chillapps.musicelsius.Repository.ServiceCallRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/musicelsius/playlist")
public class ResponseController {
	
	private static final Logger logger = LogManager.getLogger(ResponseController.class);
    @Autowired
    private CircuitBreakerCloser circuitBreakerCloser;
	
	
	@Autowired
	private CircuitBreakerRegistry circuitBreakerRegistry;

	private final ServiceCallRepository serviceCallRepository;
	private SpotifyAPIInvoker spotify;
	WeatherAPIInvoker<String> weatherAPIInvokerString;
	WeatherAPIInvoker<Coordinates> weatherAPIInvokerCoord;
	WeatherAPIInvokerFactory weatherAPIInvokerFactory;

	ResponseController(SpotifyAPIInvoker spotify,
			ServiceCallRepository serviceCallRepository,
			WeatherAPIInvoker<String> weatherAPIInvokerString,
			WeatherAPIInvoker<Coordinates> weatherAPIInvokerCoord,
			WeatherAPIInvokerFactory weatherAPIInvokerFactory) 
	{
		this.serviceCallRepository = serviceCallRepository;
		this.spotify = spotify;
		this.weatherAPIInvokerString = weatherAPIInvokerString;
		this.weatherAPIInvokerCoord = weatherAPIInvokerCoord;
		this.weatherAPIInvokerFactory = weatherAPIInvokerFactory;
	}


	@RequestMapping(value = "/bycity/{city}", method = RequestMethod.GET)
	@CircuitBreaker(name = "byCityName", fallbackMethod = "fallbackMethodCity")
	public String[] getSongs(@PathVariable String city) {
		
		weatherAPIInvokerString = weatherAPIInvokerFactory.createWeatherAPIInvokerString();
		double temperature = weatherAPIInvokerString.getTemperature(city);
		
		logger.info("Received Weather: " + temperature);
		logger.info("Received City: " + city);
		
		PersistenceThread persistenceThread;
		if(temperature>30)
		{
			logger.info("Persisting " + city + " for Party Genre.");
			persistenceThread = new PersistenceThread(serviceCallRepository, "Party", "city_name", temperature, city);
			persistenceThread.start();
			return spotify.getSongs("Party");
		}
		else if(temperature>=15)
		{
			logger.info("Persisting " + city + " for Pop Genre.");
			persistenceThread = new PersistenceThread(serviceCallRepository, "Pop", "city_name", temperature, city);
			persistenceThread.start();
			return spotify.getSongs("Pop");
		}
		else if(temperature>=10)
		{
			logger.info("Persisting " + city + " for Rock Genre.");
			persistenceThread = new PersistenceThread(serviceCallRepository, "Rock", "city_name", temperature, city);
			persistenceThread.start();
			return spotify.getSongs("Rock");
		}
		else
		{
			logger.info("Persisting " + city + " for Classic Genre.");
			persistenceThread = new PersistenceThread(serviceCallRepository, "Classic", "city_name", temperature, city);
			persistenceThread.start();
			return spotify.getSongs("Classic");
		}
	}
	
	@RequestMapping(value = "/bycoordinates/", method = RequestMethod.GET)
	@CircuitBreaker(name = "byCoordinates", fallbackMethod = "fallbackMethodCoord")
	public String[] getSongs(@RequestBody Coordinates coord) {
		weatherAPIInvokerCoord = weatherAPIInvokerFactory.createWeatherAPIInvokerCoord();
		double temperature = weatherAPIInvokerCoord.getTemperature(coord);
		
		logger.info("Received Weather: " + temperature);
		logger.info("Received Coordinates: " + coord);
		
		PersistenceThread persistenceThread;
		
		if(temperature>30)
		{
			logger.info("Persisting " + coord + " for Party Genre.");
			persistenceThread = new PersistenceThread(serviceCallRepository, 
					"Party", "coordinates", temperature, coord.toString());
			persistenceThread.start();
			return spotify.getSongs("Party");
		}
		else if(temperature>=15)
		{
			logger.info("Persisting " + coord + " for Pop Genre.");
			persistenceThread = new PersistenceThread(serviceCallRepository, 
					"Pop", "coordinates", temperature, coord.toString());
			persistenceThread.start();
			return spotify.getSongs("Pop");
		}
		else if(temperature>=10)
		{
			logger.info("Persisting " + coord + " for Rock Genre.");
			persistenceThread = new PersistenceThread(serviceCallRepository, 
					"Rock", "coordinates", temperature, coord.toString());
			persistenceThread.start();
			return spotify.getSongs("Rock");
		}
		else
		{
			logger.info("Persisting " + coord + " for Classic Genre.");
			persistenceThread = new PersistenceThread(serviceCallRepository, 
					"Classic", "city_name", temperature, coord.toString());
			persistenceThread.start();
			return spotify.getSongs("Classic");
		}
	}
	
	@RequestMapping(value = "/topgenre/", method = RequestMethod.GET)
	@CircuitBreaker(name = "topGenre", fallbackMethod = "fallbackMethodTopGenre")
    public String getTopGenre() {
        Pageable pageable = PageRequest.of(0, 1);
        List<Object[]> result = serviceCallRepository.findTopGenre(pageable);
        if (!result.isEmpty()) {
            Object[] topGenreData = result.get(0); 
            String genre = (String) topGenreData[0];
            Long numberOfCalls = ((Number) topGenreData[1]).longValue();

            
            String displayString = "Most Popular Genre is " + genre 
            		+ ", with a number of calls: " + numberOfCalls;
            return displayString; 
        }

        return "Service Call History Table returned no results."; 
    }
	
	@RequestMapping(value = "/toplocation/", method = RequestMethod.GET)
	@CircuitBreaker(name = "topLocation", fallbackMethod = "fallbackMethodTopLocation")
    public String getTopLocation() {
        Pageable pageable = PageRequest.of(0, 1);
        List<Object[]> result = serviceCallRepository.findTopLocation(pageable);
        if (!result.isEmpty()) {
            Object[] topLocationData = result.get(0); 
            String location = (String) topLocationData[0];
            Long numberOfCalls = ((Number) topLocationData[1]).longValue();

            
            String displayString = "Most Popular Location is " + location 
            		+ ", with a number of calls: " + numberOfCalls;
            return displayString; 
        }

        return "Service Call History Table returned no results."; 
    }
	
    public String[] fallbackMethodCity(String city, Exception e) {
    	
    	logger.info("Executing fallback method for city name: " + e.getMessage());
    	
    	closeCircuitBreakerIfOpen("byCityName");
    	
    	return new String[] { "Service not available when fetching temperature"
    			+ " for city " + city + ". Please make sure it's a valid city and try again later." };
    }
    
    public String[] fallbackMethodCoord(Coordinates cord, Exception e) {
    	
    	logger.info("Executing fallback method for Coordinates: " + e.getMessage());
    	
    	closeCircuitBreakerIfOpen("byCoordinates");
    	
    	return new String[] { "Service not available when fetching temperature"
    			+ " for the set of coordinates: " + cord.getLatitude()+ ", " 
    			+ cord.getLongitude() + ". Please try again later." };
    }
    
    public String fallbackMethodTopGenre(Exception e) {
    	
    	logger.info("Executing fallback method for Top Genre Service : " + e.getMessage());
    	
    	closeCircuitBreakerIfOpen("topGenre");
    	
    	return "Service not available when fetching the top genre. Please try again later.";
    }
    
    public String fallbackMethodTopLocation(Exception e) {
    	
    	logger.info("Executing fallback method for Top Location Service: " + e.getMessage());
    	
    	closeCircuitBreakerIfOpen("topLocation");
    	
    	return "Service not available when fetching the top location. Please try again later.";
    }
    
public void closeCircuitBreakerIfOpen(String name)
{
	
		//Using complete import here since we're using @CircuitBreaker and
		//there's conflicts.
		io.github.resilience4j.circuitbreaker.CircuitBreaker circuitBreaker 
		= circuitBreakerRegistry.circuitBreaker(name);
		
		if(circuitBreaker!=null)
		{
			if(circuitBreaker.getState() == 
					io.github.resilience4j.circuitbreaker.CircuitBreaker.State.OPEN)
					{
					circuitBreakerCloser.closeCircuitBreaker(circuitBreaker);
					}
		}
		else
			logger.info("CircuitBreaker " + name + " not found.");
}

}
