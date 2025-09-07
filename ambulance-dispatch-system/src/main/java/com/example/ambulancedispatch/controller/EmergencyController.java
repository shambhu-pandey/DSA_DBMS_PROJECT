package com.example.ambulancedispatch.controller;

import com.example.ambulancedispatch.model.EmergencyRequest;
import com.example.ambulancedispatch.repository.EmergencyRequestRepository;
import com.example.ambulancedispatch.service.DispatcherService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/emergency")
public class EmergencyController {

    @Autowired
    private DispatcherService dispatchService;

    @Autowired
    private EmergencyRequestRepository repo;

    @PostMapping("/new")
    public EmergencyRequest newRequest(@RequestBody EmergencyRequest request) {
        // Use pincode-based assignment for better accuracy
        return dispatchService.dispatchEmergencyWithPincode(request);
    }

    @GetMapping("/active")
    public List<EmergencyRequest> activeRequests() {
        return repo.findAll().stream()
                .filter(r -> "ACTIVE".equals(r.getStatus()))
                .toList();
    }

    @GetMapping("/history")
    public List<EmergencyRequest> history() {
        return repo.findAll();
    }
}
