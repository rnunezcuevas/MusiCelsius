package com.chillapps.musicelsius.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

//Adding this class in case Hibernate's save feature needs to be used.

@jakarta.persistence.Entity
@jakarta.persistence.Table(name = "call_history")
public class ServiceCall {


	private String genre;
	private String method_used;
	@Id
	private int id;
	@Column(name = "time_of_call")
    private LocalDateTime time_of_call;
	
	public ServiceCall()
	{
		
	}
	public ServiceCall(String genre, String method_used, LocalDateTime time_of_call, int id)
	{
		this.genre = genre;
		this.method_used = method_used;
		this.time_of_call = time_of_call;
		this.id = id;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getMethod_used() {
		return method_used;
	}
	public void setMethod_used(String method_used) {
		this.method_used = method_used;
	}
	public LocalDateTime getTime_of_call() {
		return time_of_call;
	}
	public void setTime_of_call(LocalDateTime time_of_call) {
		this.time_of_call = time_of_call;
	}
}
