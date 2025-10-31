package com.example.ambulancedispatch.repository;

import com.example.ambulancedispatch.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByReadFlagFalseOrderByCreatedAtDesc();
}
