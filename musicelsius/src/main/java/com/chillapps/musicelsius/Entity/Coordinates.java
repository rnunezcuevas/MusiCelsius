package com.chillapps.musicelsius.Entity;

public class Coordinates {
	private double latitude;
	private double longitude;
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double d) {
		this.latitude = d;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double altitude) {
		this.longitude = altitude;
	}
	
	@Override 
	public String toString()
	{
		return "Lat:" + latitude + ", long:" + longitude;
	}
}
