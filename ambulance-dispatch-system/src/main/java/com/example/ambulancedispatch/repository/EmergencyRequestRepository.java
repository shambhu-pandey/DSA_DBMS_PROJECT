package com.example.ambulancedispatch.repository;

import com.example.ambulancedispatch.model.EmergencyRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyRequestRepository extends MongoRepository<EmergencyRequest, String> {
}
