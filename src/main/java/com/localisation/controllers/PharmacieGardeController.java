package com.localisation.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.localisation.entities.Garde;
import com.localisation.entities.Pharmacy;
import com.localisation.entities.User;
import com.localisation.repositories.GardeRepository;
import com.localisation.services.PharmacyService;
import com.localisation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

//import com.localisation.Services.PharmacieService;
import com.localisation.entities.PharmacieGarde;
import com.localisation.services.PharmacieGardeService;
import org.webjars.NotFoundException;

@RestController
@CrossOrigin (origins ="*")
@RequestMapping("/api/v1/pharmaciesgarde")
public class PharmacieGardeController {
    @Autowired
    private PharmacieGardeService service;
    @Autowired
    private PharmacyService pharmacyService;
    @Autowired
    private GardeRepository gardeRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PharmacyController pharmacyController;

    @PutMapping("/add/{pharmacyId}")
    public ResponseEntity<Object> addOndutyPharmacy(
            @PathVariable("pharmacyId") String pharmacyId,
            @RequestParam("gardeId") int gardeId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Map<String, String> errors = new HashMap<>();
        Pharmacy pharmacy = pharmacyService.findById(Integer.parseInt(pharmacyId));
        Garde garde = gardeRepository.findById(gardeId);
        if(pharmacy.getState() != 1){
            errors.put("Approbation", "You are unable to perform this action as your pharmacy has not been approved yet. Please contact the administration for further assistance.");
        }
        if(pharmacy == null){
            errors.put("pharmacy", "Pharmacy not found");
        }
        if(garde == null){
            errors.put("garde", "Garde not found");
        }
        if (startDate.isBefore(LocalDate.now())) {
            errors.put("startDate", "Start date cannot be before the current date");
        }
        if (endDate.isBefore(startDate)) {
            errors.put("endDate", "End date must be after the start date");
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        PharmacieGarde pg = service.addOndutyPharmacy(Integer.parseInt(pharmacyId), gardeId, startDate, endDate);
        return ResponseEntity.ok().body(pg);
    }

    @GetMapping("/all")
    public List<PharmacieGarde> getAllPharmaciesDeGarde() {
        return service.getAllPharmaciesDeGarde();
    }
    @GetMapping("/pharmacy/{email}")
    public List<PharmacieGarde> getAllPharmaciesDeGardeByOwner(@PathVariable String email) {
        Pharmacy pharmacy=pharmacyController.getPharmacyByUserEmail(email);
        return  service.getAllPharmaciesDeGardeByPharmacie(pharmacy);
    }

    @GetMapping("/allDispoPharmacies")
    public List<PharmacieGarde> getAllPharmaciesDeGardeDispo() {
        return service.getAllPharmaciesDeGardeDispo();

    }
    @GetMapping("/allDispoPharmacies2")
    public List<Pharmacy> getAllPharmaciesDeGardeDispo2() {
        return service.getAllPharmaciesDeGardeDispo2();

    }

    @GetMapping("/allDispoPharmacies/garde/{garde_id}")
    public List<PharmacieGarde> getAllPharmaciesDeGardeDispoByGarde(@PathVariable String garde_id) {
        return service.getAllPharmaciesDeGardeDispoByGarde(Integer.parseInt(garde_id));

    }
}
