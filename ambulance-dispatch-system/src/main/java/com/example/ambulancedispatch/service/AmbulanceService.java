// package com.example.ambulancedispatch.service;

// import com.example.ambulancedispatch.model.Ambulance;
// import com.example.ambulancedispatch.repository.AmbulanceRepository;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;

// @Service
// public class AmbulanceService {

//     private final AmbulanceRepository ambulanceRepository;

//     public AmbulanceService(AmbulanceRepository ambulanceRepository) {
//         this.ambulanceRepository = ambulanceRepository;
//     }

//     public List<Ambulance> getAllAmbulances() {
//         return ambulanceRepository.findAll();
//     }

//     public Optional<Ambulance> getAmbulanceById(String ambulanceId) {
//         return ambulanceRepository.findByAmbulanceId(ambulanceId);
//     }

//     public Ambulance saveAmbulance(Ambulance ambulance) {
//         return ambulanceRepository.save(ambulance);
//     }
// }




// package com.example.ambulancedispatch.service;

// import com.example.ambulancedispatch.model.Ambulance;
// import com.example.ambulancedispatch.repository.AmbulanceRepository;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class AmbulanceService {

//     private final AmbulanceRepository ambulanceRepository;

//     public AmbulanceService(AmbulanceRepository ambulanceRepository) {
//         this.ambulanceRepository = ambulanceRepository;
//     }

//     public List<Ambulance> getAllAmbulances() {
//         return ambulanceRepository.findAll();
//     }

//     public Ambulance getAmbulanceById(String ambulanceId) {
//         return ambulanceRepository.findByAmbulanceId(ambulanceId);
//     }

//     public Ambulance saveAmbulance(Ambulance ambulance) {
//         return ambulanceRepository.save(ambulance);
//     }
// }




package com.example.ambulancedispatch.service;

import com.example.ambulancedispatch.model.Ambulance;
import com.example.ambulancedispatch.repository.AmbulanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AmbulanceService {

    private final AmbulanceRepository ambulanceRepository;

    public AmbulanceService(AmbulanceRepository ambulanceRepository) {
        this.ambulanceRepository = ambulanceRepository;
    }

    // ✅ Get all ambulances
    public List<Ambulance> getAllAmbulances() {
        return ambulanceRepository.findAll();
    }

    // ✅ Get ambulance by its business ID (A1, A2, etc.)
    public Optional<Ambulance> getAmbulanceById(String ambulanceId) {
        return ambulanceRepository.findByAmbulanceId(ambulanceId);
    }

    // ✅ Save or update an ambulance
    public Ambulance saveAmbulance(Ambulance ambulance) {
        return ambulanceRepository.save(ambulance);
    }

    // ✅ Update ambulance status
    public String updateAmbulanceStatus(String ambulanceId, String status) {
        return ambulanceRepository.findByAmbulanceId(ambulanceId.trim().toUpperCase())
                .map(amb -> {
                    amb.setStatus(status.toUpperCase());
                    ambulanceRepository.save(amb);
                    return "✅ Ambulance " + ambulanceId + " updated to " + status.toUpperCase();
                })
                .orElse("❌ Ambulance not found with ID: " + ambulanceId);
    }
}
