package com.localisation.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.localisation.calcul.GeoConfig;
import com.localisation.calcul.GeoResponse2;
import com.localisation.dao.IDao;
import com.localisation.entities.Pharmacy;
import com.localisation.entities.PharmacieGarde;
import com.localisation.entities.User;

import com.localisation.repositories.PharmacieRepository;


import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@Service
public class PharmacyService implements IDao<Pharmacy> {

	@Autowired
	PharmacieRepository pharmacieRepo;
	@Autowired
	CityService villeService;
	@Autowired
	ZoneService zoneService;
	@Autowired
	PharmacieGardeService pharmaciegardeService;

	@Override
	public Pharmacy save(Pharmacy t) {
		// en attente
		return pharmacieRepo.save(t);
	}

	@Override
	public Pharmacy update(Pharmacy p, int id) {
	    Pharmacy updatedPharmacy = pharmacieRepo.findById(id).get();
	           
	    updatedPharmacy.setName(p.getName());
	    updatedPharmacy.setAddress(p.getAddress());
		updatedPharmacy.setPhone(p.getPhone());
	    updatedPharmacy.setAltitude(p.getAltitude());
	    updatedPharmacy.setLongitude(p.getLongitude());
	    updatedPharmacy.setZone(p.getZone());
	    return pharmacieRepo.save(updatedPharmacy);
	}


	@Override
	public List<Pharmacy> findAll() {
		return pharmacieRepo.findAll();
	}

	@Override
	public void delete(Pharmacy t) {
		pharmacieRepo.deleteById(t.getId());

	}

	public Pharmacy findById(int id) {

		return pharmacieRepo.findById(id).orElse(null);
	}

	public Pharmacy findPharmacieByUser(User user) {
		return pharmacieRepo.findByUser(user);
	}

	/*public List<Pharmacie> findAllPharmacieByVille(int id) {
		Ville ville = villeService.findById(id);
		List<Zone> zones = villeService.getZonesByCity(ville.getNom());
		List<Pharmacie> result = new ArrayList<>();
		for (Zone z : zones) {
			result.addAll(z.getPharmacies());
		}
		return result;
	} */

	public List<Pharmacy> findAllPharmacieByZone(int id) {
		return pharmacieRepo.findAllPharmacyByZone_Id(id);
	}

	public Pharmacy acceptPharmacie(int id) {
		Optional<Pharmacy> result_pharmacie = pharmacieRepo.findById(id);
		if (result_pharmacie.isPresent()) {
			Pharmacy pharmacie = result_pharmacie.get();
			pharmacie.setState(1);
			return pharmacieRepo.save(pharmacie);
		} else
			return null;

	}

	public Pharmacy refusePharmacie(int id) {
		Optional<Pharmacy> result_pharmacie = pharmacieRepo.findById(id);
		if (result_pharmacie.isPresent()) {
			Pharmacy pharmacie = result_pharmacie.get();
			pharmacie.setState(2);
			return pharmacieRepo.save(pharmacie);
		} else
			return null;

	}

	public List<Pharmacy> findAllPharmacieByGarde(int id) {

		List<Pharmacy> pharmacies = new ArrayList<>();
		List<PharmacieGarde> pharmacieDeGardes = pharmaciegardeService.getAllPharmaciesDeGardeDispoByGarde(id);
		for (PharmacieGarde p : pharmacieDeGardes) {
			pharmacies.add(p.getPharmacy());
		}
		return pharmacies;
	}

	public Optional<List<Pharmacy>> findWaitlistPharmacies() {
		return pharmacieRepo.findByState(0);
	}

	public Optional<List<Pharmacy>> findAcceptedPharmacies() {
		return pharmacieRepo.findByState(1);
	}

	public Optional<List<Pharmacy>> findRefusedPharmacies() {
		return pharmacieRepo.findByState(2);
	}

	public List<Pharmacy> findAllPharmacieDispoByVille(int id) {
		// TO DO (pharmacies qui sont dispo : etat=1 and sonts pas en garde
		return null;
	}

	public List<Pharmacy> findAllPharmacieDispoByZone(int id) {
		// TO DO (pharmacies qui sont dispo : etat=1 and sonts pas en garde
				return null;
	}

	/*
	 * public GeoResponse GeoCoding(String depart) {
	 * 
	 * OkHttpClient client = new OkHttpClient();
	 * 
	 * 
	 * Request request = new Request.Builder()
	 * .url("https://google-maps-geocoding.p.rapidapi.com/geocode/json?address=" +
	 * depart + "&language=en") .get().addHeader("X-RapidAPI-Key",
	 * "4ef7a024c0mshf581e2edc329658p17777djsnbaa43bc58637")
	 * .addHeader("X-RapidAPI-Host",
	 * "google-maps-geocoding.p.rapidapi.com").build();
	 * 
	 * try { Response response = client.newCall(request).execute(); String
	 * responseBody = response.body().string(); ObjectMapper mapper = new
	 * ObjectMapper(); GeoResponse geocodeResponse = mapper.readValue(responseBody,
	 * GeoResponse.class); return geocodeResponse; } catch (IOException e) {
	 * e.printStackTrace(); return null;
	 * 
	 * }
	 * 
	 * }
	 */
	public double[] GeoCoding(String depart) {
		GeoConfig geoConfig = new GeoConfig(depart);
		GeoResponse2 geoResponse = geoConfig.getAdress();
		double[] xy = geoResponse.getFeatures().get(0).getGeometry().getCoordinates();

		return xy;

	}

	public ResponseEntity<?> getItinerary(Pharmacy pharmacie, String Addresse) {
		double pharmacy_lat = pharmacie.getAltitude();
		double pharmacy_lng = pharmacie.getLongitude();
		double coor[] = GeoCoding(Addresse);

		OkHttpClient client = new OkHttpClient();

		HttpUrl url = new HttpUrl.Builder().scheme("https").host("api.openrouteservice.org")
				.addPathSegments("v2/directions/driving-car")
				.addQueryParameter("api_key", "5b3ce3597851110001cf6248999b7768655a4b41b10606ba60a7a7cb")
				.addQueryParameter("start", coor[0] + "," + coor[1])
				.addQueryParameter("end", pharmacy_lng + "," + pharmacy_lat).build();

		Request request = new Request.Builder().url(url).build();

		try {
			Response response = client.newCall(request).execute();
			String responseBody = response.body().string();

			return ResponseEntity.ok(responseBody);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	public String getItinerary2(Pharmacy pharmacie, String Addresse) throws IOException {
		double pharmacy_lat = pharmacie.getAltitude();
		double pharmacy_lng = pharmacie.getLongitude();
		double coor[] = GeoCoding(Addresse);

		OkHttpClient client = new OkHttpClient();

		HttpUrl url = new HttpUrl.Builder().scheme("https").host("api.openrouteservice.org")
				.addPathSegments("v2/directions/driving-car")
				.addQueryParameter("api_key", "5b3ce3597851110001cf6248999b7768655a4b41b10606ba60a7a7cb")
				.addQueryParameter("start", coor[0] + "," + coor[1])
				.addQueryParameter("end", pharmacy_lng + "," + pharmacy_lat).build();

		Request request = new Request.Builder().url(url).build();

		Response response = client.newCall(request).execute();
		String responseBody = response.request().url().toString();

		return responseBody;

	}

}
