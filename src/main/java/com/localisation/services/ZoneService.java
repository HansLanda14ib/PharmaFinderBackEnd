package com.localisation.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.localisation.dao.IDao;
import com.localisation.entities.Pharmacy;
import com.localisation.entities.Zone;
import com.localisation.repositories.ZoneRepository;

@Service
public class ZoneService implements IDao<Zone> {
	@Autowired
	private ZoneRepository repository;

	@Override
	public Zone save(Zone t) {

		return repository.save(t);
	}

	@Override
	public Zone update(Zone t, int id) {
		if (!repository.existsById(id)) {
			// Handle the case where the id does not exist
			throw new IllegalArgumentException("Invalid id: " + id);
		}

		return repository.findById(id).map(zone -> {
			zone.setName(t.getName());
			zone.setCity(t.getCity());
			return repository.save(zone);
		}).orElse(null);
	}

	@Override
	public List<Zone> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public void delete(Zone t) {
		if (t == null) {
			throw new IllegalArgumentException("Zone cannot be null");
		}
		if (!repository.existsById(t.getId())) {
			// Handle the case where the id does not exist
			throw new IllegalArgumentException("Invalid id: " + t.getId());
		}
		repository.delete(t);

	}

	// Liste des zones par ville donnee
	public List<Zone> findAllZoneByVille(int id) {
		return repository.findByCity_Id(id);
	}

	// Nombre de pharmacies par zone donne
	public int findNbrPharmacieZone(int id) {
		return repository.findById(id).get().getPharmacies().size();
	}
	// 
	public List<Pharmacy> findAllPharmaciesByZone(int id) {
		return repository.findById(id).get().getPharmacies();
	}
	public List<Pharmacy> findAcceptedPharmaciesByZone(int id) {
		List<Pharmacy> pharmacies=repository.findById(id).get().getPharmacies();
		return pharmacies.stream().filter(p -> p.getState() == 1).collect(Collectors.toList());
	}

	@Override
	public Zone findById(int id) {
		// TODO Auto-generated method stub
		return repository.findById(id).get();
	}
	public long countNbrOfPharmacie(int id_zone) {
		
		return repository.findById(id_zone).get().getPharmacies().size();
	
	}

}
