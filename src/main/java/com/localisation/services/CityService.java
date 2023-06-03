package com.localisation.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.localisation.dao.IDao;
import com.localisation.entities.Pharmacy;
import com.localisation.entities.PharmacieGarde;
import com.localisation.entities.City;
import com.localisation.entities.Zone;
import com.localisation.repositories.PharmacieGardeRepository;
import com.localisation.repositories.CityRepository;
import com.localisation.repositories.ZoneRepository;

@Service
public class CityService implements IDao<City> {

	@Autowired
	CityRepository villeRepo;

	@Autowired
	ZoneRepository zoneRepo;

	@Autowired
	PharmacieGardeRepository pharmaciegardeRepo;

	

	/* public List<Zone> getZonesByCity(String nomVille) {
		Optional<Ville> ville = villeRepo.findByNom(nomVille);
		if (ville.isPresent()) {
			return ville.get().getZones();
		} else {
			return Collections.emptyList();
		} 

	} */

	public City save(City ville) {
		Optional<City> existingVille = villeRepo.findByName(ville.getName());

		/*
		 * if (existingVille.isPresent()) { return
		 * ResponseEntity.badRequest().body("Ville already exists"); } else { Ville
		 * newVille = villeRepo.save(ville); return ResponseEntity.ok(newVille); }
		 */
		if (!existingVille.isPresent()) {
			return villeRepo.save(ville);
		} else
			return null;

	}

	public List<Pharmacy> getPharmaciesByCityAndZone(String nomVille, String nomZone) {

		Optional<Zone> matchedZone = zoneRepo.findByNameAndCity_Name(nomZone, nomVille);
		if (matchedZone.isPresent()) {
			return matchedZone.get().getPharmacies();
		}

		else
			return Collections.emptyList();
	}

	public List<Pharmacy> getPharmaciesByCityAndZoneAccepted(String nomVille, String nomZone) {
		List<Pharmacy> pharmacies = getPharmaciesByCityAndZone(nomVille, nomZone);
		List<Pharmacy> result = pharmacies.stream().filter(p -> p.getState() == 1).collect(Collectors.toList());
		return (result);
	}
	

	public ResponseEntity<?> getPharmaciesDeGardeByVilleAndZoneAndPeriode(String nomVille, String nomZone,
			String periode) {

		List<Pharmacy> result_pharmacies = new ArrayList<>();
		List<PharmacieGarde> pharmaciesgarde = pharmaciegardeRepo.findByGarde_Type(periode);
		Optional<Zone> matchedZone = zoneRepo.findByNameAndCity_Name(nomZone, nomVille);

		if (matchedZone.isPresent()) {
			for (PharmacieGarde pg : pharmaciesgarde) {
				Pharmacy result_pharmacie = pg.getPharmacy();
				if (result_pharmacie.getZone() == matchedZone.get()) {
					result_pharmacies.add(result_pharmacie);

				}
			}
			return ResponseEntity.ok(result_pharmacies);
		}

		else
			return ResponseEntity.badRequest().body("Error, city or zone ");

	}

	public ResponseEntity<?> getPharmaciesDeGardeByVilleAndZoneAndPeriode2(String nomVille, String nomZone,
			String periode) {
		// get the zone
		Optional<Zone> matchedZone = zoneRepo.findByNameAndCity_Name(nomZone, nomVille);

		// get les pharmacies de garde par periode + par zone souhaite
		List<PharmacieGarde> result_pharmaciesGardes = pharmaciegardeRepo.findAllByGardeType(periode).stream()
				.filter(p -> p.getPharmacy().getZone() == matchedZone.get()).collect(Collectors.toList());

		List<Pharmacy> finalResult = new ArrayList<>();
		for (PharmacieGarde p : result_pharmaciesGardes) {
			finalResult.add(p.getPharmacy());

		}

		return ResponseEntity.ok(finalResult);
	}

	@Override
	public City update(City t, int id) {
		Objects.requireNonNull(t, "");
		Objects.requireNonNull(t.getName(), "");
		Objects.requireNonNull(t.getId(), "");

		return villeRepo.findById(id).map((City ville) -> {
			ville.setName(t.getName());

			return villeRepo.save(ville);
		}).orElse(null);

	}

	@Override
	public List<City> findAll() {
		return villeRepo.findAll();
	}

	@Override
	public void delete(City t) {
		villeRepo.delete(t);

	}

	@Override
	public City findById(int id) {
		// TODO Auto-generated method stub
		return villeRepo.findById(id).get();
	}

	/*public long findNbrPharmacieVille(int id) {
		// TODO Auto-generated method stub
		Ville ville = villeRepo.findById(id).get();
		List<Zone> zones = ville.getZones();
		int count = 0;
		for (Zone z : zones) {
			count = count + z.getPharmacies().size();
		}
		return count;
	} */

}