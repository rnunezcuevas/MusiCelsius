package com.chillapps.musicelsius.Controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.chillapps.musicelsius.Entity.Coordinates;
import com.chillapps.musicelsius.Invoker.SpotifyAPIInvoker;
import com.chillapps.musicelsius.Invoker.WeatherAPIInvoker;
import com.chillapps.musicelsius.Invoker.WeatherAPIInvokerFactory;
import com.chillapps.musicelsius.Repository.ServiceCallRepository;

@SpringBootTest
public class ResponseControllerTest {

    @Mock
    WeatherAPIInvoker<String> mockWeatherInvokerString;

    @Mock
    WeatherAPIInvoker<Coordinates> mockWeatherInvokerCoord;
    
    @Mock
    WeatherAPIInvokerFactory mockWeatherAPIInvokerFactory;

    @Mock
    SpotifyAPIInvoker mockSpotify;

    @Mock
    PersistenceThread mockPersistenceThread;
    
    @Mock
    ServiceCallRepository mockServiceCallRepository;

    @InjectMocks
    ResponseController responseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getSongsByCityRock() {
    	when(mockWeatherAPIInvokerFactory.createWeatherAPIInvokerString()).thenReturn(mockWeatherInvokerString);
        when(mockWeatherInvokerString.getTemperature(any(String.class))).thenReturn(13.0);
        when(mockSpotify.getSongs("Rock")).thenReturn(new String[]{"Song1", "Song2"});
        
        String[] result = responseController.getSongs("valuehere");
        assertArrayEquals(new String[]{"Song1", "Song2"}, result);
    }

    @Test
    void getSongsByCityClassic() {
    	when(mockWeatherAPIInvokerFactory.createWeatherAPIInvokerString()).thenReturn(mockWeatherInvokerString);
        when(mockWeatherInvokerString.getTemperature(any(String.class))).thenReturn(5.0);
        when(mockSpotify.getSongs("Classic")).thenReturn(new String[]{"Song1", "Song2"});
        
        String[] result = responseController.getSongs("valuehere");
        assertArrayEquals(new String[]{"Song1", "Song2"}, result);
    }


    
    @Test
    void getSongsByCityParty() {
    	when(mockWeatherAPIInvokerFactory.createWeatherAPIInvokerString()).thenReturn(mockWeatherInvokerString);
        when(mockWeatherInvokerString.getTemperature(any(String.class))).thenReturn(31.0);
        when(mockSpotify.getSongs("Party")).thenReturn(new String[]{"Song1", "Song2"});
        
        String[] result = responseController.getSongs("hotcakes");
        assertArrayEquals(new String[]{"Song1", "Song2"}, result);
    }
    


    @Test
    void getSongsByCityPop() {
    	when(mockWeatherAPIInvokerFactory.createWeatherAPIInvokerString()).thenReturn(mockWeatherInvokerString);
        when(mockWeatherInvokerString.getTemperature(any(String.class))).thenReturn(25.0);
        when(mockSpotify.getSongs("Pop")).thenReturn(new String[]{"Song1", "Song2"});
        
        String[] result = responseController.getSongs("waffles");
        assertArrayEquals(new String[]{"Song1", "Song2"}, result);
    }

    @Test
    void getSongsByCoordinatesRock() {
    	when(mockWeatherAPIInvokerFactory.createWeatherAPIInvokerCoord()).thenReturn(mockWeatherInvokerCoord);
    	when(mockWeatherInvokerCoord.getTemperature(any(Coordinates.class))).thenReturn(13.0);
        when(mockSpotify.getSongs("Rock")).thenReturn(new String[]{"Song1", "Song2"});
        
        Coordinates inputCoord = new Coordinates();
        inputCoord.setLatitude(50);
        inputCoord.setLongitude(-50);
        
        String[] result = responseController.getSongs(inputCoord);
        assertArrayEquals(new String[]{"Song1", "Song2"}, result);
    }

    @Test
    void getSongsByCoordinatesClassic() {
    	when(mockWeatherAPIInvokerFactory.createWeatherAPIInvokerCoord()).thenReturn(mockWeatherInvokerCoord);
    	when(mockWeatherInvokerCoord.getTemperature(any(Coordinates.class))).thenReturn(9.0);
        when(mockSpotify.getSongs("Classic")).thenReturn(new String[]{"Song1", "Song2"});
        
        Coordinates inputCoord = new Coordinates();
        inputCoord.setLatitude(50);
        inputCoord.setLongitude(-50);
        String[] result = responseController.getSongs(inputCoord);
        assertArrayEquals(new String[]{"Song1", "Song2"}, result);
    }
    
    @Test
    void getSongsByCoordinatesParty() {
    	when(mockWeatherAPIInvokerFactory.createWeatherAPIInvokerCoord()).thenReturn(mockWeatherInvokerCoord);
    	when(mockWeatherInvokerCoord.getTemperature(any(Coordinates.class))).thenReturn(31.0);
        when(mockSpotify.getSongs("Party")).thenReturn(new String[]{"Song1", "Song2"});
        
        Coordinates inputCoord = new Coordinates();
        inputCoord.setLatitude(50);
        inputCoord.setLongitude(-50);
        
        String[] result = responseController.getSongs(inputCoord);
        assertArrayEquals(new String[]{"Song1", "Song2"}, result);
    }
    
    @Test
    void getSongsByCoordinatesPop() {
    	when(mockWeatherAPIInvokerFactory.createWeatherAPIInvokerCoord()).thenReturn(mockWeatherInvokerCoord);
    	when(mockWeatherInvokerCoord.getTemperature(any(Coordinates.class))).thenReturn(25.0);
        when(mockSpotify.getSongs("Pop")).thenReturn(new String[]{"Song1", "Song2"});
        
        Coordinates inputCoord = new Coordinates();
        inputCoord.setLatitude(50);
        inputCoord.setLongitude(-50);
        
        String[] result = responseController.getSongs(inputCoord);
        assertArrayEquals(new String[]{"Song1", "Song2"}, result);
    }
    




}