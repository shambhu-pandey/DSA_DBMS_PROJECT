package com.example.ambulancedispatch.controller;

import com.example.ambulancedispatch.model.AppUser;
import com.example.ambulancedispatch.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") != null) {
            return "redirect:/";
        }

        model.addAttribute("demoUsername", "admin");
        model.addAttribute("demoPassword", "admin12345");
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        AppUser user = userService.findAuthenticatedUser(username, password).orElse(null);
        if (user != null) {
            session.setAttribute("loggedInUser", user.getUsername());
            session.setAttribute("loggedInFullName", user.getFullName());
            session.setAttribute("userRole", user.getRole());
            session.setAttribute("assignedAmbulanceId", user.getAssignedAmbulanceId());
            return "redirect:/";
        }

        redirectAttributes.addFlashAttribute("error", "Invalid username or password.");
        return "redirect:/login";
    }

    @GetMapping("/signup")
    public String signupPage(HttpSession session) {
        if (session.getAttribute("loggedInUser") != null) {
            return "redirect:/";
        }

        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String fullName,
                         @RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String confirmPassword,
                         RedirectAttributes redirectAttributes) {
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match.");
            return "redirect:/signup";
        }

        try {
            userService.register(username, password, fullName);
            redirectAttributes.addFlashAttribute("success", "Account created. You can log in now.");
            return "redirect:/login";
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/signup";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
