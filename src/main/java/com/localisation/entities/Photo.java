package com.localisation.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Photo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String url;
	@OneToOne
	@JoinColumn(name = "pharmacyId")
	private Pharmacy pharmacie;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Pharmacy getPharmacie() {
		return pharmacie;
	}
	public void setPharmacie(Pharmacy pharmacie) {
		this.pharmacie = pharmacie;
	}

}
