package com.example.ambulancedispatch.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        Object username = session.getAttribute("loggedInUser");
        if (username == null) {
            return "redirect:/login";
        }

        model.addAttribute("username", username.toString());
        model.addAttribute("fullName", session.getAttribute("loggedInFullName"));
        model.addAttribute("role", session.getAttribute("userRole"));
        model.addAttribute("assignedAmbulanceId", session.getAttribute("assignedAmbulanceId"));
        return "home";
    }
}
