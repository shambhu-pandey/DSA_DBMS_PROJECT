package com.example.ambulancedispatch.service;

import com.example.ambulancedispatch.model.*;
import com.example.ambulancedispatch.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DispatcherService {

    @Autowired
    private AmbulanceRepository ambulanceRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private EmergencyRequestRepository emergencyRequestRepository;

    @Autowired
    private DispatchHistoryRepository dispatchHistoryRepository;

    // ✅ Priority Queue for waiting requests
    private PriorityQueue<EmergencyRequest> waitingQueue =
            new PriorityQueue<>(Comparator.comparingInt(this::getPriority));

    // Emergency type priority mapping
    private int getPriority(EmergencyRequest req) {
        switch (req.getEmergencyType().toUpperCase()) {
            case "CRITICAL": return 1; // Highest priority
            case "MAJOR": return 2;
            case "MINOR": return 3;
            default: return 4;
        }
    }

    // ✅ Pincode-based distance calculation
    private int calculatePincodeDistance(String patientPincode, String ambulancePincode) {
        try {
            int patient = Integer.parseInt(patientPincode.trim());
            int ambulance = Integer.parseInt(ambulancePincode.trim());
            return Math.abs(patient - ambulance) * 2; // ~2km per unit difference
        } catch (Exception e) {
            return 50; // fallback
        }
    }

    // ✅ Dispatch method with Chennai restriction & Waiting Queue
    public EmergencyRequest dispatchEmergencyWithPincode(EmergencyRequest request) {
        // Restrict to Chennai pincodes (600xxx)
        if (!request.getPincode().startsWith("600")) {
            request.setStatus("OUT_OF_COVERAGE");
            emergencyRequestRepository.save(request);
            return request;
        }

        List<Ambulance> ambulances = ambulanceRepository.findAll();
        List<Hospital> hospitals = hospitalRepository.findAll();

        // filter only available ambulances
        List<Ambulance> candidates = new ArrayList<>();
        for (Ambulance amb : ambulances) {
            if ("AVAILABLE".equalsIgnoreCase(amb.getStatus())) {
                candidates.add(amb);
            }
        }

        // ✅ If no ambulances free → Add to waiting queue
        if (candidates.isEmpty()) {
            request.setStatus("WAITING");
            emergencyRequestRepository.save(request);
            waitingQueue.add(request);
            return request;
        }

        // find nearest ambulance
        Ambulance nearestAmbulance = null;
        int minDist = Integer.MAX_VALUE;
        for (Ambulance amb : candidates) {
            int dist = calculatePincodeDistance(request.getPincode(), amb.getPincode());
            if (dist < minDist) {
                minDist = dist;
                nearestAmbulance = amb;
            }
        }

        // find nearest hospital with available beds
        Hospital nearestHospital = null;
        int minHospitalDist = Integer.MAX_VALUE;
        for (Hospital h : hospitals) {
            if (h.getAvailableBeds() > 0) {
                int dist = calculatePincodeDistance(request.getPincode(), h.getPincode());
                if (dist < minHospitalDist) {
                    minHospitalDist = dist;
                    nearestHospital = h;
                }
            }
        }

        if (nearestHospital == null) {
            request.setStatus("NO_HOSPITAL_AVAILABLE");
            emergencyRequestRepository.save(request);
            return request;
        }

        // ✅ Assign ambulance + hospital
        request.setStatus("ASSIGNED");
        request.setAssignedAmbulance(nearestAmbulance);
        request.setAssignedHospital(nearestHospital);
        emergencyRequestRepository.save(request);

        // update ambulance & hospital
        nearestAmbulance.setStatus("BUSY");
        ambulanceRepository.save(nearestAmbulance);
        nearestHospital.setAvailableBeds(nearestHospital.getAvailableBeds() - 1);
        hospitalRepository.save(nearestHospital);

        // log history (use ambulanceId instead of Mongo _id)
        DispatchHistory history = new DispatchHistory();
        history.setRequestId(request.getId());
        history.setAmbulanceId(nearestAmbulance.getAmbulanceId()); // ✅ FIXED
        history.setHospitalId(nearestHospital.getId());
        history.setPatientName(request.getPatientName());
        history.setPatientLocation(request.getPatientLocation());
        history.setPatientPincode(request.getPincode());
        history.setEmergencyType(request.getEmergencyType());
        history.setAmbulancePincode(nearestAmbulance.getPincode());
        history.setDriverName(nearestAmbulance.getDriverName());
        history.setDriverPhone(nearestAmbulance.getDriverPhone());
        history.setOutcome("ASSIGNED");
        dispatchHistoryRepository.save(history);

        return request;
    }

    // ✅ Update ambulance status
    public String updateAmbulanceStatus(String ambulanceId, String status) {
        return ambulanceRepository.findByAmbulanceId(ambulanceId)
                .map(amb -> {
                    amb.setStatus(status.toUpperCase());
                    ambulanceRepository.save(amb);

                    // if set to AVAILABLE, assign from queue
                    if ("AVAILABLE".equalsIgnoreCase(status)) {
                        assignFromQueue(amb);
                    }

                    return "✅ Ambulance " + ambulanceId + " status updated to " + status;
                })
                .orElse("❌ Ambulance not found with ID: " + ambulanceId);
    }

    // ✅ Assign highest-priority waiting request
    public void assignFromQueue(Ambulance freedAmbulance) {
        if (waitingQueue.isEmpty()) return;

        EmergencyRequest nextRequest = waitingQueue.poll(); // get highest priority
        if (nextRequest != null) {
            nextRequest.setStatus("ASSIGNED");
            nextRequest.setAssignedAmbulance(freedAmbulance);

            // assign nearest hospital again
            Hospital nearestHospital = null;
            int minDist = Integer.MAX_VALUE;
            for (Hospital h : hospitalRepository.findAll()) {
                if (h.getAvailableBeds() > 0) {
                    int dist = calculatePincodeDistance(nextRequest.getPincode(), h.getPincode());
                    if (dist < minDist) {
                        minDist = dist;
                        nearestHospital = h;
                    }
                }
            }
            if (nearestHospital != null) {
                nextRequest.setAssignedHospital(nearestHospital);
                nearestHospital.setAvailableBeds(nearestHospital.getAvailableBeds() - 1);
                hospitalRepository.save(nearestHospital);
            }

            freedAmbulance.setStatus("BUSY");
            ambulanceRepository.save(freedAmbulance);
            emergencyRequestRepository.save(nextRequest);
        }
    }

    // ✅ Route explanation (Ambulance → Patient → Hospital)
    public List<String> getRoute(String ambulanceLoc, String patientLoc, String hospitalLoc) {
        List<String> route = new ArrayList<>();
        route.add("Ambulance at " + ambulanceLoc);
        route.add("Picking patient at " + patientLoc);
        route.add("Heading to hospital at " + hospitalLoc);
        route.add("This route is chosen because it is shortest in terms of pincode distance.");
        return route;
    }
}
