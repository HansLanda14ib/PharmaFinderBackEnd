package com.localisation.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Pharmacy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	@NotBlank
	@Size(max = 250)
	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only letters")
	private String name;
	@NotNull
	@NotBlank
	@Size(max = 250)
	private String address;


	@Pattern(regexp ="(^$|[0-9]{10})", message = "Phone must contain only 10 numbers")
	private String phone;
	@NotNull(message = "altitude cannot be null")
	private double altitude;
	@NotNull(message = "longitude cannot be null")
	private double longitude;
	
	private int state;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

	@ManyToOne
	@JoinColumn(name = "zone_id")
	@NotNull(message = "Zone cannot be null")
	private Zone zone;

	@OneToMany(mappedBy = "pharmacy")
	@JsonIgnore
	private List<PharmacieGarde> pharmaciegarde;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public List<PharmacieGarde> getPharmaciegarde() {
		return pharmaciegarde;
	}

	public void setPharmaciegarde(List<PharmacieGarde> pharmaciegarde) {
		this.pharmaciegarde = pharmaciegarde;
	}

}