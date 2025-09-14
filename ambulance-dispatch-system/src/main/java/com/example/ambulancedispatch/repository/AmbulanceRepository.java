// package com.example.ambulancedispatch.repository;

// import com.example.ambulancedispatch.model.Ambulance;
// import org.springframework.data.mongodb.repository.MongoRepository;
// import java.util.Optional;

// public interface AmbulanceRepository extends MongoRepository<Ambulance, String> {
//     Optional<Ambulance> findByAmbulanceId(String ambulanceId);
// }

// package com.example.ambulancedispatch.repository;

// import com.example.ambulancedispatch.model.Ambulance;
// import org.springframework.data.mongodb.repository.MongoRepository;
// import org.springframework.data.mongodb.repository.Query;
// import org.springframework.stereotype.Repository;

// import java.util.Optional;

// @Repository
// public interface AmbulanceRepository extends MongoRepository<Ambulance, String> {

//     // Explicitly search by the "ambulanceId" field
//     @Query("{ 'ambulanceId': ?0 }")
//     Optional<Ambulance> findByAmbulanceId(String ambulanceId);
// }

package com.example.ambulancedispatch.repository;

import com.example.ambulancedispatch.model.Ambulance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AmbulanceRepository extends MongoRepository<Ambulance, String> {

    // Explicitly search by the "ambulanceId" field
    @Query("{ 'ambulanceId': ?0 }")
    Optional<Ambulance> findByAmbulanceId(String ambulanceId);
}

// public interface AmbulanceRepository extends MongoRepository<Ambulance, String> {
//     Ambulance findByAmbulanceId(String ambulanceId);
// }
