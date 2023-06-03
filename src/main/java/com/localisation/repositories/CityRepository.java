package com.localisation.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.localisation.entities.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
	Optional<City> findByName(String nomVille);

	// Optional<Ville> findByNomAndZonesNomContainingIgnoreCase(String nom, String
	// zoneNom);

}
