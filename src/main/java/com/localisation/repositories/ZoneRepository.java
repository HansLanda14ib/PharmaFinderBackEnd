package com.localisation.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.localisation.entities.Zone;

public interface ZoneRepository extends JpaRepository<Zone, Integer> {
	Optional<Zone> findByName(String nomZone);

	Optional<Zone> findByNameAndCity_Name(String nomZone, String nomVille);
	
	List<Zone> findByCity_Id(int id);
	//long countByPharmacies(String lastname);
	
	List<Zone> findByCityId(Long villeId);
}
