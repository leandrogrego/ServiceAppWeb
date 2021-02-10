package br.net.serviceapp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Location implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	protected Long time;
	protected double bearing;
	protected double speed;
	protected double altitude;
	protected double longitude;
	protected double latitude;
	protected String provider;
	
	public Location(
			Long time,
			double bearing,
			double speed,
			double altitude,
			double longitude,
			double latitude,
			String provider
		){
		this.time = time;
		this.bearing = bearing;
		this.speed = speed;
		this.altitude = altitude;
		this.longitude = longitude;
		this.latitude = latitude;
		this.provider = provider;
	};
	public Location(){};
	
	public Long getTime() {
		return time;
	}
	
	public double getBearing() {
		return bearing;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public double getAltitude() {
		return altitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public String getprovider() {
		return provider;
	}
}
