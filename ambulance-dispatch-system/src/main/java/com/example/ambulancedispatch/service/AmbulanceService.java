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

    public List<Ambulance> getAllAmbulances() {
        return ambulanceRepository.findAll();
    }

    public Optional<Ambulance> getAmbulanceById(String ambulanceId) {
        return ambulanceRepository.findByAmbulanceId(ambulanceId);
    }

    public Ambulance saveAmbulance(Ambulance ambulance) {
        return ambulanceRepository.save(ambulance);
    }
}
