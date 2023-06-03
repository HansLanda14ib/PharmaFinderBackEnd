package com.localisation.services;

import java.time.LocalDate;
import java.util.List;

import com.localisation.entities.Garde;
import com.localisation.repositories.GardeRepository;
import com.localisation.repositories.PharmacieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.localisation.entities.Pharmacy;
import com.localisation.entities.PharmacieGarde;
import com.localisation.repositories.PharmacieGardeRepository;
import org.webjars.NotFoundException;

@Service
public class PharmacieGardeService {

    @Autowired
    private PharmacieGardeRepository repository;

    @Autowired
    private PharmacieRepository pharmacieRepository;

    @Autowired
    private GardeRepository gardeRepository;

    public PharmacieGarde addOndutyPharmacy(int pharmacyId, int gardeId, LocalDate startDate, LocalDate endDate) {

        Pharmacy pharmacy = pharmacieRepository.findById(pharmacyId).get();
        Garde garde = gardeRepository.findById(gardeId);

        PharmacieGarde pg = new PharmacieGarde();
        pg.setPharmacy(pharmacy);
        pg.setGarde(garde);
        pg.setStartDate(startDate);
        pg.setEndDate(endDate);

        return repository.save(pg);


    }

    public List<PharmacieGarde> getAllPharmaciesDeGarde() {
        return repository.findAll();
    }

    public List<PharmacieGarde> getAllPharmaciesDeGardeByPharmacie(Pharmacy pharmacie) {
        return repository.findAllByPharmacy(pharmacie);
    }

    public List<PharmacieGarde> getAllPharmaciesDeGardeDispo() {
        return repository.findAllPharmacieDispo();
    }

    public List<Pharmacy> getAllPharmaciesDeGardeDispo2() {
        return repository.findAllPharmacieDispo2();
    }

    public List<PharmacieGarde> getAllPharmaciesDeGardeDispoByGarde(int id_garde) {


        return repository.findAllPharmacieDispoByGarde(id_garde);
    }
}
