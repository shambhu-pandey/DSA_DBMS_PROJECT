package com.example.ambulancedispatch.controller;

import com.example.ambulancedispatch.model.Ambulance;
import com.example.ambulancedispatch.repository.AmbulanceRepository;
import com.example.ambulancedispatch.service.AccessControlService;
import com.example.ambulancedispatch.service.DispatcherService;
import jakarta.servlet.http.HttpSession;
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

    @Autowired
    private AccessControlService accessControlService;

    @PostMapping("/add")
    public Ambulance addAmbulance(@RequestBody Ambulance ambulance, HttpSession session) {
        accessControlService.requireAdmin(session);
        if (ambulance.getAmbulanceId() == null || ambulance.getAmbulanceId().isEmpty()) {
            ambulance.setAmbulanceId("AMB-" + System.currentTimeMillis());
        }
        return ambulanceRepo.save(ambulance);
    }

    @GetMapping("/all")
    public List<Ambulance> getAllAmbulances(HttpSession session) {
        if (accessControlService.isDriver(session)) {
            String assignedAmbulanceId = accessControlService.getAssignedAmbulanceId(session);
            return ambulanceRepo.findAll().stream()
                    .filter(ambulance -> assignedAmbulanceId != null
                            && assignedAmbulanceId.equalsIgnoreCase(ambulance.getAmbulanceId()))
                    .toList();
        }
        if (accessControlService.isUser(session)) {
            return List.of();
        }

        return ambulanceRepo.findAll();
    }

    @PostMapping("/update-status")
    public String updateAmbulanceStatus(@RequestParam String ambulanceId,
                                        @RequestParam String status,
                                        HttpSession session) {
        accessControlService.requireAmbulanceManager(session, ambulanceId);
        return dispatcherService.updateAmbulanceStatus(ambulanceId, status);
    }
}
