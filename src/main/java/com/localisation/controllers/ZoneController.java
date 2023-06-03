package com.localisation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.localisation.entities.City;
import com.localisation.entities.Zone;
import com.localisation.services.CityService;
import com.localisation.services.ZoneService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Zone Controller")
@RestController
@CrossOrigin (origins ="*")
@RequestMapping("/api/v1/zones")
public class ZoneController {
	@Autowired
	ZoneService zoneService;
	@Autowired
	CityService villeService;

	@Operation(summary = "add new zone")
	@PostMapping("/save")
		public ResponseEntity<?> addZone(@RequestParam("name") String name, @RequestParam("cityId") int cityId) {
			ResponseEntity<?> response;
			try {
				City city = villeService.findById(cityId);
				Zone zone = new Zone();
				zone.setName(name);
				zone.setCity(city);
				zoneService.save(zone);
				response = ResponseEntity.ok(zone);
			} catch (Exception e) {
				response = ResponseEntity.badRequest().body("Failed to add this Zone");
			}

			return response;
		}


	@GetMapping("")
	public List<Zone> findAll() {

		return zoneService.findAll();
	}

	@GetMapping("/zone/id={id}")
	public Zone findZoneById(@PathVariable int id) {

		return zoneService.findById(id);
	}

	@GetMapping("/zone/city={id}")
	public List<Zone> findAllZoneByVille(@PathVariable int id) {

		return zoneService.findAllZoneByVille(id);
	}

	@PutMapping("/{id_zone}")
	public Zone updateZone(@RequestParam("name") String newNom, @RequestParam("cityId") int newVille_id,
			@PathVariable int id_zone) {

		City newVille = villeService.findById(newVille_id);

		// zone with new fields
		Zone updatedZone = new Zone();
		updatedZone.setName(newNom);
		updatedZone.setCity(newVille);
		return zoneService.update(updatedZone, id_zone); 

	}

	@DeleteMapping("/deleteZone/id={id}")
	public void deleteZone(@PathVariable int id) {
		Zone zone=zoneService.findById(id);
		zoneService.delete(zone);
	}
	@GetMapping("/Countbyzone/zone={id}")
	public long findNbrPharmacieZone(@PathVariable int id){
		return zoneService.countNbrOfPharmacie(id);
	}
}
