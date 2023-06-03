package com.localisation.entities;




import jakarta.persistence.*;

@Entity
public class City {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;

	
	//@OneToMany(mappedBy = "city", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	//private List<Zone> Zones;

	public City() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String nom) {
		this.name = nom;
	}

	/*public List<Zone> getZones() {
		return Zones;
	}

	public void setZones(List<Zone> zones) {
		Zones = zones;
	} */

}
