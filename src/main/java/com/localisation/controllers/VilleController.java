package com.localisation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.localisation.entities.Pharmacy;
import com.localisation.entities.City;

import com.localisation.services.CityService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "le controlleur de -Ville-")
@RestController
@CrossOrigin (origins ="*")
@RequestMapping("/api/v1/cities")

public class VilleController {

	@Autowired
	CityService villeService;

	@Operation(summary = "Ajouter une ville")
	@PostMapping("/save")
	public ResponseEntity<?> addville(@RequestBody City ville) {
		ResponseEntity<?> response;
		try {
			City newVille = villeService.save(ville);
			response = ResponseEntity.ok(newVille);
		} catch (Exception e) {
			response = ResponseEntity.badRequest().body("Failed to add ville, already exist");
		}

		return response;
	}

	@Operation(summary = "Lister des villes")
	@GetMapping("")
	public List<City> listAll() {

		return villeService.findAll();

	}

	@GetMapping("/ville/id={id}")
	public City findVilleById(@PathVariable int id) {

		return villeService.findById(id);
	}

	@PutMapping("/{id}")
	
	public City updateVille(@RequestBody City v, @PathVariable int id) {

		return villeService.update(v, id);
	}

	@DeleteMapping("/{id}")
	
	public void deleteVille(@PathVariable int id) {
		City ville = villeService.findById(id);
		villeService.delete(ville);
	}

	/* @Operation(summary = "Lister des zones par ville donnée")
	@GetMapping("/{ville}/zones")
	public List<Zone> getZonesByVille(
			@Parameter(example = "Kenitra", name = "Nom de la ville") @PathVariable(required = true) String ville) {

		return villeService.getZonesByCity(ville);

	} */

	@Operation(summary = "Lister des pharmacies par zone/ville donnée")
	@GetMapping("/{ville}/zones/{zone}/pharmacies")
	public ResponseEntity<List<Pharmacy>> getPharmaciesByCityAndZone(
			@Parameter(example = "Rabat", name = "Nom de la ville") @PathVariable(required = true) String ville,
			@Parameter(example = "Gueliz", name = "Nom de la zone") @PathVariable(required = true) String zone) {

		List<Pharmacy> pharmacies = villeService.getPharmaciesByCityAndZone(ville, zone);
		if (pharmacies.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(pharmacies);
		}
	}

	@Operation(summary = "Lister des pharmacies par zone donnée et acceptée par l'admin")
	@GetMapping("/{ville}/zones/{zone}/accpetedpharmacies")
	public ResponseEntity<List<Pharmacy>> getPharmaciesByCityAndZoneAccepted(
			@Parameter(example = "Casablanca", name = "Nom de la ville") @PathVariable(required = true) String ville,
			@Parameter(example = "Ain chok", name = "Nom de la zone") @PathVariable(required = true) String zone) {

		List<Pharmacy> pharmacies = villeService.getPharmaciesByCityAndZoneAccepted(ville, zone);
		if (pharmacies.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(pharmacies);
		}
	}

	@Operation(summary = "Lister des pharmacies par zone donnée par type de garde")
	@GetMapping("/{ville}/zones/{zone}/pharmacies/garde")
	public ResponseEntity<?> getPharmaciesDeGardeByVilleAndZoneAndPeriode(
			@Parameter(example = "Casablanca", name = "Nom de la ville") @PathVariable(required = true) String ville,
			@Parameter(example = "Ain chok", name = "Nom de la zone") @PathVariable(required = true) String zone,
			@RequestParam("periode") String periode) {
		ResponseEntity<?> pharmacies = villeService.getPharmaciesDeGardeByVilleAndZoneAndPeriode(ville, zone, periode);
		return ResponseEntity.ok(pharmacies);
	}

	//methode 2
	@Operation(summary = "Lister des pharmacies par zone donnée par type de garde")
	@GetMapping("/{ville}/zones/{zone}/pharmacies/garde2")
	public ResponseEntity<?> getPharmaciesDeGardeByVilleAndZoneAndPeriode2(@PathVariable(required = true) String ville,
			@PathVariable(required = true) String zone, @RequestParam("periode") String periode) {
		ResponseEntity<?> pharmacies = villeService.getPharmaciesDeGardeByVilleAndZoneAndPeriode2(ville, zone, periode);
		return ResponseEntity.ok(pharmacies);
	}
 /* @Operation(summary = "Nombre de pharmacies dans une ville donnée")
	@GetMapping("/Countbyville/id={id}")
	public long findNbrPharmacieVille(@PathVariable(required = true) int id) {
		return villeService.findNbrPharmacieVille(id);
	} */

}
