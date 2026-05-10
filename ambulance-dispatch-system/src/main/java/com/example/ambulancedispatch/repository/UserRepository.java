package com.example.ambulancedispatch.repository;

import com.example.ambulancedispatch.model.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<AppUser, String> {
    Optional<AppUser> findByUsername(String username);

    boolean existsByUsername(String username);
}
