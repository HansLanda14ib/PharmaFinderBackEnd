package com.localisation.entities;

import java.io.Serializable;
import java.time.LocalDate;




public class PharmacieGardeId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pharmacy pharmacy;
	private Garde garde;
	private LocalDate startDate;

	public PharmacieGardeId() {
		super();
		// TODO Auto-generated constructor stub
	}

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

	public LocalDate GetStartDate() {
		return startDate;
	}

	public void SetStartDate(LocalDate date_debut) {
		this.startDate = date_debut;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



}
