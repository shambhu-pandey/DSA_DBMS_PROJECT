package com.example.ambulancedispatch.controller;

import com.example.ambulancedispatch.model.EmergencyRequest;
import com.example.ambulancedispatch.repository.EmergencyRequestRepository;
import com.example.ambulancedispatch.service.AccessControlService;
import com.example.ambulancedispatch.service.DispatcherService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emergency")
public class EmergencyController {

    @Autowired
    private DispatcherService dispatchService;

    @Autowired
    private EmergencyRequestRepository repo;

    @Autowired
    private AccessControlService accessControlService;

    @PostMapping("/new")
    public EmergencyRequest newRequest(@RequestBody EmergencyRequest request, HttpSession session) {
        accessControlService.requireAnyRole(session, "ADMIN", "USER");
        return dispatchService.dispatchEmergencyWithPincode(request);
    }

    @GetMapping("/active")
    public List<EmergencyRequest> activeRequests(HttpSession session) {
        accessControlService.requireAdmin(session);
        return repo.findAll().stream()
                .filter(r -> "ACTIVE".equals(r.getStatus()))
                .toList();
    }

    @GetMapping("/history")
    public List<EmergencyRequest> history(HttpSession session) {
        accessControlService.requireAdmin(session);
        return repo.findAll();
    }
}
