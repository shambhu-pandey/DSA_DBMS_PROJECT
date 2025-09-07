package com.example.ambulancedispatch.repository;

import com.example.ambulancedispatch.model.DispatchHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispatchHistoryRepository extends MongoRepository<DispatchHistory, String> {
}
