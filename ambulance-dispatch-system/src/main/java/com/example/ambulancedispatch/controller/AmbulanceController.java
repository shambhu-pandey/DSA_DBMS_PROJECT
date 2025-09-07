package com.example.ambulancedispatch.controller;

import com.example.ambulancedispatch.model.Ambulance;
import com.example.ambulancedispatch.repository.AmbulanceRepository;
import com.example.ambulancedispatch.service.DispatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ambulance")
public class AmbulanceController {

    @Autowired
    private AmbulanceRepository ambulanceRepo;

    @Autowired
    private DispatcherService dispatcherService;

    // ➕ Add Ambulance
    @PostMapping("/add")
    public Ambulance addAmbulance(@RequestBody Ambulance ambulance) {
        // If frontend forgets to set business ID, fix it
        if (ambulance.getAmbulanceId() == null || ambulance.getAmbulanceId().isEmpty()) {
            ambulance.setAmbulanceId("AMB-" + System.currentTimeMillis()); // fallback unique ID
        }
        return ambulanceRepo.save(ambulance);
    }

    // 📋 Get All Ambulances
    @GetMapping("/all")
    public List<Ambulance> getAllAmbulances() {
        return ambulanceRepo.findAll();
    }

    // Update Ambulance Status
    @PostMapping("/update-status")
    public String updateAmbulanceStatus(@RequestParam String ambulanceId, @RequestParam String status) {
        return dispatcherService.updateAmbulanceStatus(ambulanceId, status);
    }
}
