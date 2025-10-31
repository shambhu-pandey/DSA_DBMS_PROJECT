package com.example.ambulancedispatch.controller;

import com.example.ambulancedispatch.model.Notification;
import com.example.ambulancedispatch.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping("/unread")
    public List<Notification> getUnread() {
        return service.getUnread();
    }

    @PostMapping("/{id}/read")
    public void markRead(@PathVariable String id) {
        service.markRead(id);
    }
}
