package com.localisation.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Garde {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String type;
	
	@OneToMany(mappedBy = "garde")
	@JsonIgnore
	private List<PharmacieGarde> pharmaciegarde;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<PharmacieGarde> getPharmaciegarde() {
		return pharmaciegarde;
	}

	public void setPharmaciegarde(List<PharmacieGarde> pharmaciegarde) {
		this.pharmaciegarde = pharmaciegarde;
	}

}
