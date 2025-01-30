package com.chillapps.musicelsius.Controller;

import java.time.LocalDateTime;

import com.chillapps.musicelsius.Repository.ServiceCallRepository;

public class PersistenceThread extends Thread{

	ServiceCallRepository serviceCallRepository;
	String methodUsed;
	double temperature;
	String location;
	String genre;
	
	public PersistenceThread(ServiceCallRepository serviceCallRepository, 
			String genre, String methodUsed, double temperature, String location)
	{
		this.serviceCallRepository = serviceCallRepository;
		this.methodUsed = methodUsed;
		this.temperature = temperature;
		this.location = location;
		this.genre = genre;
	}
	
	@Override
	public void run()
	{
		serviceCallRepository.insert_service_call(genre, methodUsed, LocalDateTime.now(), 
				temperature, location);
	}
}
