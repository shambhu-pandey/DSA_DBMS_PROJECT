package com.example.ambulancedispatch.repository;

import com.example.ambulancedispatch.model.DispatchHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface DispatchHistoryRepository extends MongoRepository<DispatchHistory, String> {

    // ✅ Existing method
    List<DispatchHistory> findAll();

    // ✅ New method for reassignment filtering
    List<DispatchHistory> findByExplanationContainingIgnoreCase(String keyword);
}
