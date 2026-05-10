package com.example.ambulancedispatch.controller;

import com.example.ambulancedispatch.model.DispatchHistory;
import com.example.ambulancedispatch.repository.DispatchHistoryRepository;
import com.example.ambulancedispatch.service.AccessControlService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dispatch")
public class DispatchController {

    @Autowired
    private DispatchHistoryRepository historyRepo;

    @Autowired
    private AccessControlService accessControlService;

    @GetMapping("/history")
    public List<DispatchHistory> getHistory(HttpSession session) {
        if (accessControlService.isDriver(session)) {
            String assignedAmbulanceId = accessControlService.getAssignedAmbulanceId(session);
            return historyRepo.findAll().stream()
                    .filter(history -> assignedAmbulanceId != null
                            && assignedAmbulanceId.equalsIgnoreCase(history.getAmbulanceId()))
                    .toList();
        }

        accessControlService.requireAdmin(session);
        return historyRepo.findAll();
    }

    @GetMapping("/reassigned")
    public List<DispatchHistory> getReassignedOnly(HttpSession session) {
        List<DispatchHistory> reassigned = historyRepo.findByExplanationContainingIgnoreCase("auto-reassignment");
        if (accessControlService.isDriver(session)) {
            String assignedAmbulanceId = accessControlService.getAssignedAmbulanceId(session);
            return reassigned.stream()
                    .filter(history -> assignedAmbulanceId != null
                            && assignedAmbulanceId.equalsIgnoreCase(history.getAmbulanceId()))
                    .toList();
        }

        accessControlService.requireAdmin(session);
        return reassigned;
    }
}
