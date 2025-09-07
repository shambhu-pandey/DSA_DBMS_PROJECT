package com.example.ambulancedispatch.repository;

import com.example.ambulancedispatch.model.Ambulance;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface AmbulanceRepository extends MongoRepository<Ambulance, String> {
    Optional<Ambulance> findByAmbulanceId(String ambulanceId);
}
