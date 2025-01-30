package com.chillapps.musicelsius.Invoker;

import org.springframework.stereotype.Component;

import com.chillapps.musicelsius.Entity.Coordinates;

@Component
public class WeatherAPIInvokerFactory {

    public WeatherAPIInvoker<String> createWeatherAPIInvokerString() {
        return new WeatherAPIInvoker<>();
    }
    
    public WeatherAPIInvoker<Coordinates> createWeatherAPIInvokerCoord() {
        return new WeatherAPIInvoker<>();
    }
}
