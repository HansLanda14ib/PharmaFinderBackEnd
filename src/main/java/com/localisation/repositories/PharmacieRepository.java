package com.localisation.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.localisation.entities.Pharmacy;
import com.localisation.entities.User;

public interface PharmacieRepository extends JpaRepository<Pharmacy, Integer> {
	public Optional<Pharmacy> findById(int id);

	Pharmacy findByUser(User user);

	public Optional<List<Pharmacy>> findByState(int etat);

	public List<Pharmacy> findAllByZone_Id(int id);

	public List<Pharmacy> findAllPharmacyByZone_Id(int id);

}
