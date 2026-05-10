package com.example.ambulancedispatch.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@Service
public class AccessControlService {
    public boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(getRole(session));
    }

    public boolean isDriver(HttpSession session) {
        return "DRIVER".equals(getRole(session));
    }

    public boolean isUser(HttpSession session) {
        return "USER".equals(getRole(session));
    }

    public String getRole(HttpSession session) {
        Object role = session == null ? null : session.getAttribute("userRole");
        return role == null ? "USER" : role.toString().trim().toUpperCase();
    }

    public String getAssignedAmbulanceId(HttpSession session) {
        Object ambulanceId = session == null ? null : session.getAttribute("assignedAmbulanceId");
        return ambulanceId == null ? null : ambulanceId.toString();
    }

    public void requireAnyRole(HttpSession session, String... roles) {
        String currentRole = getRole(session);
        boolean allowed = Arrays.stream(roles)
                .map(role -> role.trim().toUpperCase())
                .anyMatch(role -> role.equals(currentRole));

        if (!allowed) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission for this action.");
        }
    }

    public void requireAdmin(HttpSession session) {
        requireAnyRole(session, "ADMIN");
    }

    public void requireAmbulanceManager(HttpSession session, String ambulanceId) {
        if (isAdmin(session)) {
            return;
        }

        String assignedAmbulanceId = getAssignedAmbulanceId(session);
        if (isDriver(session)
                && assignedAmbulanceId != null
                && assignedAmbulanceId.equalsIgnoreCase(ambulanceId)) {
            return;
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Drivers can only manage their assigned ambulance.");
    }
}
