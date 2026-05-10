package com.example.ambulancedispatch.controller;

import com.example.ambulancedispatch.model.Notification;
import com.example.ambulancedispatch.service.AccessControlService;
import com.example.ambulancedispatch.service.NotificationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService service;
    private final AccessControlService accessControlService;

    public NotificationController(NotificationService service, AccessControlService accessControlService) {
        this.service = service;
        this.accessControlService = accessControlService;
    }

    @GetMapping("/unread")
    public List<Notification> getUnread(HttpSession session) {
        accessControlService.requireAnyRole(session, "ADMIN", "DRIVER");
        return service.getUnread();
    }

    @PostMapping("/{id}/read")
    public void markRead(@PathVariable String id, HttpSession session) {
        accessControlService.requireAnyRole(session, "ADMIN", "DRIVER");
        service.markRead(id);
    }
}
