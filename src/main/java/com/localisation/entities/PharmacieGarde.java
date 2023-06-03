package com.localisation.entities;


import java.time.LocalDate;



//import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;



@Entity
@IdClass(PharmacieGardeId.class)
public class PharmacieGarde {
	@Id
	@ManyToOne
	
	@JoinColumn(name = "pharmacyId")
	private Pharmacy pharmacy;

	@Id
	@ManyToOne
	
	@JoinColumn(name = "gardeId")
	private Garde garde;

	// additional attributes
	@Id
	private LocalDate  startDate;
	
	
	private LocalDate  endDate;

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public Garde getGarde() {
		return garde;
	}

	public void setGarde(Garde garde) {
		this.garde = garde;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate date_debut) {
		this.startDate = date_debut;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate date_fin) {
		this.endDate = date_fin;
	}

}