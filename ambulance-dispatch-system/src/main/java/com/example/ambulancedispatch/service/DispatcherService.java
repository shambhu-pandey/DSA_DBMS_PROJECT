





// package com.example.ambulancedispatch.service;

// import com.example.ambulancedispatch.model.*;
// import com.example.ambulancedispatch.repository.*;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.*;

// @Service
// public class DispatcherService {

//     @Autowired
//     private AmbulanceRepository ambulanceRepository;

//     @Autowired
//     private HospitalRepository hospitalRepository;

//     @Autowired
//     private EmergencyRequestRepository emergencyRequestRepository;

//     @Autowired
//     private DispatchHistoryRepository dispatchHistoryRepository;
      
//     private final PriorityQueue<EmergencyRequest> waitingQueue =
//             new PriorityQueue<>(Comparator.comparingInt(this::getPriority));

//     private int getPriority(EmergencyRequest req) {
//         if (req.getEmergencyType() == null) return 4;
//         switch (req.getEmergencyType().toUpperCase()) {
//             case "CRITICAL": return 1;
//             case "MAJOR": return 2;
//             case "MINOR": return 3;
//             default: return 4;
//         }
//     }

//     private int calculatePincodeDistance(String patientPincode, String ambulancePincode) {
//         try {
//             int patient = Integer.parseInt(patientPincode.trim());
//             int ambulance = Integer.parseInt(ambulancePincode.trim());
//             return Math.abs(patient - ambulance) * 2;
//         } catch (Exception e) {
//             return 50;
//         }
//     }

     
//     public EmergencyRequest dispatchEmergencyWithPincode(EmergencyRequest request) {
//         if (!request.getPincode().startsWith("600")) {
//             request.setStatus("OUT_OF_COVERAGE");
//             emergencyRequestRepository.save(request);
//             return request;
//         }

//         List<Ambulance> ambulances = ambulanceRepository.findAll();
//         List<Hospital> hospitals = hospitalRepository.findAll();

//         List<Ambulance> candidates = new ArrayList<>();
//         for (Ambulance amb : ambulances) {
//             if ("AVAILABLE".equalsIgnoreCase(amb.getStatus())) {
//                 candidates.add(amb);
//             }
//         }

//         if (candidates.isEmpty()) {
//             request.setStatus("WAITING");
//             emergencyRequestRepository.save(request);
//             waitingQueue.add(request);
//             return request;
//         }

//         Ambulance nearestAmbulance = null;
//         int minDist = Integer.MAX_VALUE;
//         for (Ambulance amb : candidates) {
//             int dist = calculatePincodeDistance(request.getPincode(), amb.getPincode());
//             if (dist < minDist) {
//                 minDist = dist;
//                 nearestAmbulance = amb;
//             }
//         }
       

        
//         Hospital nearestHospital = null;
//         int minHospitalDist = Integer.MAX_VALUE;
//         for (Hospital h : hospitals) {
//             if (h.getAvailableBeds() > 0) {
//                 int dist = calculatePincodeDistance(request.getPincode(), h.getPincode());
//                 if (dist < minHospitalDist) {
//                     minHospitalDist = dist;
//                     nearestHospital = h;
//                 }
//             }
//         }

//         if (nearestHospital == null) {
//             request.setStatus("NO_HOSPITAL_AVAILABLE");
//             emergencyRequestRepository.save(request);
//             return request;
//         }

//         // ✅ Assign ambulance & hospital
//         request.setStatus("ASSIGNED");
//         request.setAssignedAmbulance(nearestAmbulance);
//         request.setAssignedHospital(nearestHospital);
//         emergencyRequestRepository.save(request);

//         nearestAmbulance.setStatus("BUSY");
//         ambulanceRepository.save(nearestAmbulance);

//         nearestHospital.setAvailableBeds(nearestHospital.getAvailableBeds() - 1);
//         hospitalRepository.save(nearestHospital);

//         // ✅ Save Dispatch History with explanation
//         DispatchHistory history = new DispatchHistory();
//         history.setRequestId(request.getId());
//         history.setAmbulanceId(nearestAmbulance.getAmbulanceId());
//         history.setHospitalId(nearestHospital.getId());
//         history.setHospitalName(nearestHospital.getName());
//         history.setHospitalPincode(nearestHospital.getPincode());
//         history.setPatientName(request.getPatientName());
//         history.setPatientLocation(request.getPatientLocation());
//         history.setPatientPincode(request.getPincode());
//         history.setEmergencyType(request.getEmergencyType());
//         history.setAmbulancePincode(nearestAmbulance.getPincode());
//         history.setDriverName(nearestAmbulance.getDriverName());
//         history.setDriverPhone(nearestAmbulance.getDriverPhone());
//         history.setOutcome("ASSIGNED");
//         history.setExplanation("Ambulance " + nearestAmbulance.getAmbulanceId() +
//                 " (Driver: " + nearestAmbulance.getDriverName() + ", Phone: " + nearestAmbulance.getDriverPhone() + ")" +
//                 " was chosen as it is nearest to patient pincode " + request.getPincode() +
//                 ". Hospital " + nearestHospital.getName() + " selected since it has available beds and nearest distance.");
//         dispatchHistoryRepository.save(history);

//         return request;
//     }

//     public String updateAmbulanceStatus(String ambulanceId, String status) {
//         String cleanId = ambulanceId.trim().toUpperCase();

//         Optional<Ambulance> optionalAmb = ambulanceRepository.findByAmbulanceId(cleanId);
//         if (optionalAmb.isPresent()) {
//             Ambulance amb = optionalAmb.get();
//             amb.setStatus(status.toUpperCase());
//             ambulanceRepository.save(amb);

//             if ("AVAILABLE".equalsIgnoreCase(status)) {
//                 assignFromQueue(amb);
//             }

//             return "✅ Ambulance " + cleanId + " status updated to " + status.toUpperCase();
//         } else {
//             return "❌ Ambulance not found with ID: " + cleanId;
//         }
//     }

//     private void assignFromQueue(Ambulance freedAmbulance) {
//         if (waitingQueue.isEmpty()) return;

//         EmergencyRequest nextRequest = waitingQueue.poll();
//         if (nextRequest != null) {
//             nextRequest.setStatus("ASSIGNED");
//             nextRequest.setAssignedAmbulance(freedAmbulance);

//             Hospital nearestHospital = null;
//             int minDist = Integer.MAX_VALUE;
//             for (Hospital h : hospitalRepository.findAll()) {
//                 if (h.getAvailableBeds() > 0) {
//                     int dist = calculatePincodeDistance(nextRequest.getPincode(), h.getPincode());
//                     if (dist < minDist) {
//                         minDist = dist;
//                         nearestHospital = h;
//                     }
//                 }
//             }
//             if (nearestHospital != null) {
//                 nextRequest.setAssignedHospital(nearestHospital);
//                 nearestHospital.setAvailableBeds(nearestHospital.getAvailableBeds() - 1);
//                 hospitalRepository.save(nearestHospital);
//             }

//             freedAmbulance.setStatus("BUSY");
//             ambulanceRepository.save(freedAmbulance);
//             emergencyRequestRepository.save(nextRequest);
//         }
//     }
// }









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

    @Autowired
    private NotificationService notificationService;

    private final PriorityQueue<EmergencyRequest> waitingQueue =
            new PriorityQueue<>(Comparator.comparingInt(this::getPriority));

    private int getPriority(EmergencyRequest req) {
        if (req.getEmergencyType() == null) return 4;
        switch (req.getEmergencyType().toUpperCase()) {
            case "CRITICAL": return 1;
            case "MAJOR": return 2;
            case "MINOR": return 3;
            default: return 4;
        }
    }

    private int calculatePincodeDistance(String patientPincode, String ambulancePincode) {
        try {
            int patient = Integer.parseInt(patientPincode.trim());
            int ambulance = Integer.parseInt(ambulancePincode.trim());
            return Math.abs(patient - ambulance) * 2;
        } catch (Exception e) {
            return 50;
        }
    }

    // ✅ ETA Prediction Algorithm
    private int estimateETA(String ambulancePincode, String patientPincode) {
        int distanceKm = calculatePincodeDistance(patientPincode, ambulancePincode);
        int averageSpeedKmph = 60;
        return (int) Math.ceil((double) distanceKm / averageSpeedKmph * 60); // ETA in minutes
    }

    public EmergencyRequest dispatchEmergencyWithPincode(EmergencyRequest request) {
        if (!request.getPincode().startsWith("600")) {
            request.setStatus("OUT_OF_COVERAGE");
            emergencyRequestRepository.save(request);
            return request;
        }

        List<Ambulance> ambulances = ambulanceRepository.findAll();
        List<Hospital> hospitals = hospitalRepository.findAll();

        List<Ambulance> candidates = new ArrayList<>();
        for (Ambulance amb : ambulances) {
            if ("AVAILABLE".equalsIgnoreCase(amb.getStatus())) {
                candidates.add(amb);
            }
        }

        if (candidates.isEmpty()) {
            request.setStatus("WAITING");
            emergencyRequestRepository.save(request);
            waitingQueue.add(request);
            return request;
        }

        Ambulance nearestAmbulance = null;
        int minDist = Integer.MAX_VALUE;
        for (Ambulance amb : candidates) {
            int dist = calculatePincodeDistance(request.getPincode(), amb.getPincode());
            if (dist < minDist) {
                minDist = dist;
                nearestAmbulance = amb;
            }
        }

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

        int etaMinutes = estimateETA(nearestAmbulance.getPincode(), request.getPincode());
        request.setEtaMinutes(etaMinutes); // ➕ Add this field to EmergencyRequest.java

        request.setStatus("ASSIGNED");
        request.setAssignedAmbulance(nearestAmbulance);
        request.setAssignedHospital(nearestHospital);
        emergencyRequestRepository.save(request);

        nearestAmbulance.setStatus("BUSY");
        ambulanceRepository.save(nearestAmbulance);

        nearestHospital.setAvailableBeds(nearestHospital.getAvailableBeds() - 1);
        hospitalRepository.save(nearestHospital);

        DispatchHistory history = new DispatchHistory();
        history.setRequestId(request.getId());
        history.setAmbulanceId(nearestAmbulance.getAmbulanceId());
        history.setHospitalId(nearestHospital.getId());
        history.setHospitalName(nearestHospital.getName());
        history.setHospitalPincode(nearestHospital.getPincode());
        history.setPatientName(request.getPatientName());
        history.setPatientLocation(request.getPatientLocation());
        history.setPatientPincode(request.getPincode());
        history.setEmergencyType(request.getEmergencyType());
        history.setAmbulancePincode(nearestAmbulance.getPincode());
        history.setDriverName(nearestAmbulance.getDriverName());
        history.setDriverPhone(nearestAmbulance.getDriverPhone());
        history.setOutcome("ASSIGNED");
        history.setExplanation("Ambulance " + nearestAmbulance.getAmbulanceId() +
                " (Driver: " + nearestAmbulance.getDriverName() + ", Phone: " + nearestAmbulance.getDriverPhone() + ")" +
                " was chosen as it is nearest to patient pincode " + request.getPincode() +
                ". Hospital " + nearestHospital.getName() + " selected since it has available beds and nearest distance.");
        dispatchHistoryRepository.save(history);

        return request;
    }

    public String updateAmbulanceStatus(String ambulanceId, String status) {
        String cleanId = ambulanceId.trim().toUpperCase();

        Optional<Ambulance> optionalAmb = ambulanceRepository.findByAmbulanceId(cleanId);
        if (optionalAmb.isPresent()) {
            Ambulance amb = optionalAmb.get();
            amb.setStatus(status.toUpperCase());
            ambulanceRepository.save(amb);

            if ("AVAILABLE".equalsIgnoreCase(status)) {
                assignFromQueue(amb);
            }

            return "✅ Ambulance " + cleanId + " status updated to " + status.toUpperCase();
        } else {
            return "❌ Ambulance not found with ID: " + cleanId;
        }
    }

    private void assignFromQueue(Ambulance freedAmbulance) {
        if (waitingQueue.isEmpty()) return;

        EmergencyRequest nextRequest = waitingQueue.poll();
        if (nextRequest != null) {
            nextRequest.setStatus("ASSIGNED");
            nextRequest.setAssignedAmbulance(freedAmbulance);

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

            int etaMinutes = estimateETA(freedAmbulance.getPincode(), nextRequest.getPincode());
            nextRequest.setEtaMinutes(etaMinutes);

            freedAmbulance.setStatus("BUSY");
            ambulanceRepository.save(freedAmbulance);
            emergencyRequestRepository.save(nextRequest);

            DispatchHistory history = new DispatchHistory();
            history.setRequestId(nextRequest.getId());
            history.setAmbulanceId(freedAmbulance.getAmbulanceId());
            history.setHospitalId(nearestHospital != null ? nearestHospital.getId() : "N/A");
            history.setHospitalName(nearestHospital != null ? nearestHospital.getName() : "N/A");
            history.setHospitalPincode(nearestHospital != null ? nearestHospital.getPincode() : "N/A");
            history.setPatientName(nextRequest.getPatientName());
            history.setPatientLocation(nextRequest.getPatientLocation());
            history.setPatientPincode(nextRequest.getPincode());
            history.setEmergencyType(nextRequest.getEmergencyType());
            history.setAmbulancePincode(freedAmbulance.getPincode());
            history.setDriverName(freedAmbulance.getDriverName());
            history.setDriverPhone(freedAmbulance.getDriverPhone());
            history.setOutcome("ASSIGNED");
            history.setExplanation("Auto-reassignment: Ambulance " + freedAmbulance.getAmbulanceId() +
                    " assigned to waiting request " + nextRequest.getId() + ". Hospital: " +
                    (nearestHospital != null ? nearestHospital.getName() : "N/A"));
            dispatchHistoryRepository.save(history);

            notificationService.create(
                "Auto Reassignment",
                "Incident #" + nextRequest.getId() + " assigned to Ambulance #" + freedAmbulance.getAmbulanceId()
            );
        }
    }
}
