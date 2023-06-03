package com.localisation.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.localisation.services.PermissionService;
import com.localisation.services.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
import com.localisation.entities.User;
import com.localisation.entities.Zone;
import com.localisation.repositories.ZoneRepository;
import com.localisation.services.PharmacyService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/pharmacies")
public class PharmacyController {

    @Autowired
    PharmacyService pharmacyService;

    @Autowired
    private ZoneRepository zoneService;
    @Autowired
    private UserService userService;
    @Autowired
    private final PermissionService permissionService;

    public PharmacyController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/save/{user_id}")
    public Pharmacy save(@RequestBody Pharmacy p, @PathVariable long user_id) {
        p.setState(0);
        //User user = userService.getUserById(user_id);
        //p.setUser(user);
        p.setZone(zoneService.findById(1).get()); // the default zone id is 1
        return pharmacyService.save(p);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody Pharmacy p, BindingResult bindingResult) {
        // Check for validation errors
        Map<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
        }
        // Check permission before updating
        User currentUser = permissionService.getAuthenticatedUser();
        if (permissionService.isAdmin(currentUser) || permissionService.isFirstOwner(currentUser)) {
            p.setUser(currentUser);
            // Check for zone ID validation errors
            Integer zoneId = p.getZone().getId();
            Optional<Zone> optionalZone = zoneService.findById(zoneId);
            if (optionalZone.isEmpty()) {
                errors.put("zone", "Zone not found");
            } else {
                p.setZone(optionalZone.get());
            }

            // Check if there are any errors
            if (!errors.isEmpty()) {
                return ResponseEntity.badRequest().body(errors);
            }

            Pharmacy newpharmacy = pharmacyService.save(p);
            return ResponseEntity.ok(newpharmacy);
        } else {
            // User does not have permission
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }

    // Update pharmacy
    @PutMapping("/{id}/update")
    public ResponseEntity<?> updatePharmacy(@RequestBody @Valid Pharmacy p, @PathVariable int id, @NotNull BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors())
                errors.put(error.getField(), error.getDefaultMessage());
            return ResponseEntity.badRequest().body(errors);
        }
        // Retrieve the pharmacy by ID
        if(id !=0){
            Pharmacy pharmacy = pharmacyService.findById(id);
            // Check permission before updating
            User currentUser = permissionService.getAuthenticatedUser();
            if (permissionService.isAdmin(currentUser) || permissionService.isOwner(currentUser, pharmacy)) {
                Pharmacy updatedPharmacy = pharmacyService.update(p, id);
                return ResponseEntity.ok(updatedPharmacy);
            } else {
                // User does not have permission
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }


    @DeleteMapping("/deletePharmacy/id={id}")
    public void deletePharmacie(@PathVariable int id) {
        Pharmacy ph = pharmacyService.findById(id);
        pharmacyService.delete(ph);
    }

    @GetMapping("/{id}")
    public Pharmacy findById(@PathVariable String id) {
        return pharmacyService.findById(Integer.parseInt(id));
    }

    @GetMapping("/user/{email}")
    public Pharmacy getPharmacyByUserEmail(@PathVariable String email) {

        Optional<User> user = userService.findByEmail(email);
        return user.map(value -> pharmacyService.findPharmacieByUser(value)).orElse(null);
    }

    /*
     * @GetMapping("/pharmacie/ville={id}") public List<Pharmacie>
     * findPharmacieByVille(@PathVariable int id) { return
     * pharmacieService.findAllPharmacieByVille(id); }
     */

    @GetMapping("/zone/id={id}")
    public List<Pharmacy> findPharmacieByZone(@PathVariable int id) {
        return pharmacyService.findAllPharmacieByZone(id);
    }

    @GetMapping("")
    public List<Pharmacy> findAllPharmacie() {
        return pharmacyService.findAll();
    }

    @GetMapping("/allWaitList")
    public Optional<List<Pharmacy>> findWaitlistPharmacies() {
        return pharmacyService.findWaitlistPharmacies();
    }

    @GetMapping("/allAccepted")
    public Optional<List<Pharmacy>> findAcceptedPharmacies() {
        return pharmacyService.findAcceptedPharmacies();
    }

    @GetMapping("/allRefused")
    public Optional<List<Pharmacy>> findRefusedPharmacies() {
        return pharmacyService.findRefusedPharmacies();
    }

    @PutMapping("/acceptPharmacy/id={id}")
    public Pharmacy acceptePharmacie(@PathVariable int id) {

        return pharmacyService.acceptPharmacie(id);
    }

    @PutMapping("/refusePharmacy/id={id}")
    public Pharmacy refusPharmacie(@PathVariable int id) {

        return pharmacyService.refusePharmacie(id);
    }

    @PutMapping("/{id}/state/{new_state}")
    public ResponseEntity<String> updatePharmacyState(@PathVariable int id, @PathVariable int new_state) {
        Pharmacy pharmacy = pharmacyService.findById(id);
        if (pharmacy != null) {
            pharmacy.setState(new_state);
            pharmacyService.save(pharmacy);
            return ResponseEntity.ok("Pharmacy state updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/pharmacy/garde={id}")
    public List<Pharmacy> findAllPharmacieByGarde(@PathVariable int id) {
        return pharmacyService.findAllPharmacieByGarde(id);
    }

    @GetMapping("/{id}/itineraire")
    public ResponseEntity<?> getItineraire(@PathVariable String id, @RequestParam("depart") String depart) throws IOException {
        Pharmacy optionalPharmacie = pharmacyService.findById(Integer.parseInt(id));
        // if (!optionalPharmacie.isPresent()) {
        // return ResponseEntity.notFound().build();
        // }
        Pharmacy pharmacie = optionalPharmacie;

        return ResponseEntity.ok(pharmacyService.getItinerary2(pharmacie, depart));
        // return pharmacyService.getItinerary(pharmacie, depart);
    }

    @GetMapping("/getAddress")
    public double[] myaddress(@RequestParam("depart") String depart) throws IOException {

        return pharmacyService.GeoCoding(depart);
    }

}
