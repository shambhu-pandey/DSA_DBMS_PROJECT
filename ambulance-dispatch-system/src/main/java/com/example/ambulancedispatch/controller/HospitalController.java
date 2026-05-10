package com.example.ambulancedispatch.controller;

import com.example.ambulancedispatch.model.Hospital;
import com.example.ambulancedispatch.service.AccessControlService;
import com.example.ambulancedispatch.service.HospitalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private AccessControlService accessControlService;

    @PostMapping("/add")
    public Hospital addHospital(@RequestBody Hospital hospital, HttpSession session) {
        accessControlService.requireAdmin(session);
        return hospitalService.addHospital(hospital);
    }

    @GetMapping("/all")
    public List<Hospital> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }
}
