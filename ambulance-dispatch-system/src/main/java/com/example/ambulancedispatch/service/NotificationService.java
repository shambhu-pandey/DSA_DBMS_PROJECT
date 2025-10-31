package com.example.ambulancedispatch.service;

import com.example.ambulancedispatch.model.Notification;
import com.example.ambulancedispatch.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repo;

    public NotificationService(NotificationRepository repo) {
        this.repo = repo;
    }

    public Notification create(String title, String message) {
        Notification n = new Notification();
        n.setTitle(title);
        n.setMessage(message);
        return repo.save(n);
    }

    public List<Notification> getUnread() {
        return repo.findByReadFlagFalseOrderByCreatedAtDesc();
    }

    public void markRead(String id) {
        repo.findById(id).ifPresent(n -> {
            n.setReadFlag(true);
            repo.save(n);
        });
    }
}
