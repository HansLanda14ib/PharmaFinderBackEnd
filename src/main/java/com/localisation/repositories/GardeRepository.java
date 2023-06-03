package com.localisation.repositories;

import com.localisation.entities.City;
import com.localisation.entities.Garde;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GardeRepository  extends JpaRepository<Garde, Integer> {

    Garde findById(int gardeId);
}
