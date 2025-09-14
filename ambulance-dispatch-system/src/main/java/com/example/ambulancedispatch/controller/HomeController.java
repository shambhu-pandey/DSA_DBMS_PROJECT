// package com.example.ambulancedispatch.controller;

// import com.example.ambulancedispatch.model.*;
// import com.example.ambulancedispatch.repository.*;
// import com.example.ambulancedispatch.service.DispatcherService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.boot.CommandLineRunner;

// import java.util.List;

// @RestController
// @RequestMapping("/api")  // Optional: Adds a base URL prefix for all endpoints
// public class HomeController implements CommandLineRunner {

//     @Autowired
//     private HospitalRepository hospitalRepo;

//     @Autowired
//     private AmbulanceRepository ambulanceRepo;

//     @Autowired
//     private EmergencyRequestRepository requestRepo;

//     @Autowired
//     private DispatchHistoryRepository historyRepo;

//     @Autowired
//     private DispatcherService dispatcherService;

//     // -------------------- SEED HELPERS --------------------
//     private void seedHospital(String hospitalId, String name, String location,
//                               String pincode, String contact, int beds) {
//         if (hospitalRepo.findById(hospitalId).isEmpty()) {
//             Hospital hospital = new Hospital(name, location, contact, beds);
//             hospital.setId(hospitalId);
//             hospital.setPincode(pincode);
//             hospitalRepo.save(hospital);
//         }
//     }

//     private void seedAmbulance(String ambulanceId, String hospitalId, String location,
//                                String pincode, String driverName, String driverPhone) {
//         if (ambulanceRepo.findByAmbulanceId(ambulanceId).isEmpty()) { 
//             Ambulance ambulance = new Ambulance(ambulanceId, hospitalId, location, "AVAILABLE");
//             ambulance.setPincode(pincode);
//             ambulance.setDriverName(driverName);
//             ambulance.setDriverPhone(driverPhone);
//             ambulanceRepo.save(ambulance);
//         }
//     }

 

//     // -------------------- INITIAL DATA --------------------
//     @Override
//     public void run(String... args) {
//         // Hospitals
//         seedHospital("H1", "Apollo Hospitals", "Greams Road", "600006", "044-28293333", 20);
//         seedHospital("H2", "Fortis Malar", "Adyar", "600020", "044-42892222", 15);
//         seedHospital("H3", "MIOT Hospital", "Manapakkam", "600089", "044-42002288", 25);
//         seedHospital("H4", "Kauvery Hospital", "Alwarpet", "600018", "044-40006000", 10);
//         seedHospital("H5", "SRM Hospital", "Kattankulathur", "603203", "044-47432222", 30);
//         seedHospital("H6", "Government General Hospital", "Park Town", "600003", "044-25305000", 50);
//         seedHospital("H7", "SIMS Hospital", "Vadapalani", "600026", "044-20002000", 18);
//         seedHospital("H8", "Global Hospital", "Perumbakkam", "600100", "044-44777000", 22);

//         // Ambulances
//         seedAmbulance("A1", "H1", "Greams Road", "600006", "Rajesh Kumar", "9876543210");
//         seedAmbulance("A2", "H1", "Greams Road", "600006", "Suresh Reddy", "9876543211");
//         seedAmbulance("A3", "H2", "Adyar", "600020", "Karthik Subramani", "9876543212");
//         seedAmbulance("A4", "H3", "Manapakkam", "600089", "Dinesh Babu", "9876543213");
//         seedAmbulance("A5", "H4", "Alwarpet", "600018", "Prakash Iyer", "9876543214");
//         seedAmbulance("A6", "H5", "Kattankulathur", "603203", "Arjun Varma", "9876543215");
//         seedAmbulance("A7", "H6", "Park Town", "600003", "Manoj Krishnan", "9876543216");
//         seedAmbulance("A8", "H7", "Vadapalani", "600026", "Vivek Nair", "9876543217");
//         seedAmbulance("A9", "H8", "Perumbakkam", "600100", "Ashok Selvan", "9876543218");
//         seedAmbulance("A10", "H8", "Perumbakkam", "600100", "Mahesh Chandran", "9876543219");
//     }

//     // -------------------- ENDPOINTS --------------------
//     @GetMapping("/")
//     public String home() {
//         return "🚑 Ambulance Dispatch System Running!";
//     }

//     @PostMapping("/hospital/add")
//     public Hospital addHospital(@RequestBody Hospital hospital) {
//         return hospitalRepo.save(hospital);
//     }

//     @PostMapping("/ambulance/add")
//     public Ambulance addAmbulance(@RequestBody Ambulance ambulance) {
//         return ambulanceRepo.save(ambulance);
//     }

//     @GetMapping("/hospital/all")
//     public List<Hospital> getAllHospitals() {
//         return hospitalRepo.findAll();
//     }

//     @GetMapping("/ambulance/all")
//     public List<Ambulance> getAllAmbulances() {
//         return ambulanceRepo.findAll();
//     }

//     @PostMapping("/emergency/new")
//     public EmergencyRequest newEmergency(@RequestBody EmergencyRequest request) {
//         return dispatcherService.dispatchEmergencyWithPincode(request);
//     }

//     @GetMapping("/emergency/active")
//     public List<EmergencyRequest> getActiveRequests() {
//         return requestRepo.findAll();
//     }

//     @GetMapping("/dispatch/history")
//     public List<DispatchHistory> getDispatchHistory() {
//         return historyRepo.findAll();
//     }

//     @PostMapping("/ambulance/update-status")
//     public String updateAmbulanceStatus(@RequestParam String ambulanceId,
//                                         @RequestParam String status) {
//         return dispatcherService.updateAmbulanceStatus(ambulanceId, status);
//     }
// }





package com.example.ambulancedispatch.controller;

import com.example.ambulancedispatch.model.*;
import com.example.ambulancedispatch.repository.*;
import com.example.ambulancedispatch.service.DispatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HomeController implements CommandLineRunner {

    @Autowired
    private HospitalRepository hospitalRepo;

    @Autowired
    private AmbulanceRepository ambulanceRepo;

    @Autowired
    private EmergencyRequestRepository requestRepo;

    @Autowired
    private DispatchHistoryRepository historyRepo;

    @Autowired
    private DispatcherService dispatcherService;

    // -------------------- SEED HELPERS --------------------
    private void seedHospital(String hospitalId, String name, String location,
                              String pincode, String contact, int beds) {
        if (hospitalRepo.findById(hospitalId).isEmpty()) {
            Hospital hospital = new Hospital(name, location, contact, beds);
            hospital.setId(hospitalId);
            hospital.setPincode(pincode);
            hospitalRepo.save(hospital);
        }
    }

    private void seedAmbulance(String ambulanceId, String hospitalId, String location,
                               String pincode, String driverName, String driverPhone) {
        if (ambulanceRepo.findByAmbulanceId(ambulanceId).isEmpty()) {
            Ambulance ambulance = new Ambulance();
            ambulance.setAmbulanceId(ambulanceId);
            ambulance.setHospitalId(hospitalId);
            ambulance.setLocation(location);
            ambulance.setStatus("AVAILABLE");
            ambulance.setPincode(pincode);
            ambulance.setDriverName(driverName);
            ambulance.setDriverPhone(driverPhone);
            ambulanceRepo.save(ambulance);
        }
    }

    // -------------------- INITIAL DATA --------------------
    @Override
    public void run(String... args) {
        // Clear old data
        // ambulanceRepo.deleteAll();
        // hospitalRepo.deleteAll();

        // Seed hospitals
        seedHospital("H1", "Apollo Hospitals", "Greams Road", "600006", "044-28293333", 20);
        seedHospital("H2", "Fortis Malar", "Adyar", "600020", "044-42892222", 15);
        seedHospital("H3", "MIOT Hospital", "Manapakkam", "600089", "044-42002288", 25);
        seedHospital("H4", "Kauvery Hospital", "Alwarpet", "600018", "044-40006000", 10);
        seedHospital("H5", "SRM Hospital", "Kattankulathur", "603203", "044-47432222", 30);
        seedHospital("H6", "Government General Hospital", "Park Town", "600003", "044-25305000", 50);
        seedHospital("H7", "SIMS Hospital", "Vadapalani", "600026", "044-20002000", 18);
        seedHospital("H8", "Global Hospital", "Perumbakkam", "600100", "044-44777000", 22);

        // Seed ambulances
        seedAmbulance("A1", "H1", "Greams Road", "600006", "Rajesh Kumar", "9876543210");
        seedAmbulance("A2", "H1", "Greams Road", "600006", "Suresh Reddy", "9876543211");
        seedAmbulance("A3", "H2", "Adyar", "600020", "Karthik Subramani", "9876543212");
        seedAmbulance("A4", "H3", "Manapakkam", "600089", "Dinesh Babu", "9876543213");
        seedAmbulance("A5", "H4", "Alwarpet", "600018", "Prakash Iyer", "9876543214");
        seedAmbulance("A6", "H5", "Kattankulathur", "603203", "Arjun Varma", "9876543215");
        seedAmbulance("A7", "H6", "Park Town", "600003", "Manoj Krishnan", "9876543216");
        seedAmbulance("A8", "H7", "Vadapalani", "600026", "Vivek Nair", "9876543217");
        seedAmbulance("A9", "H8", "Perumbakkam", "600100", "Ashok Selvan", "9876543218");
        seedAmbulance("A10", "H8", "Perumbakkam", "600100", "Mahesh Chandran", "9876543219");
    }

    // -------------------- ENDPOINTS --------------------
    @GetMapping("/")
    public String home() {
        return "🚑 Ambulance Dispatch System Running!";
    }

    @PostMapping("/hospital/add")
    public Hospital addHospital(@RequestBody Hospital hospital) {
        return hospitalRepo.save(hospital);
    }

    @PostMapping("/ambulance/add")
    public Ambulance addAmbulance(@RequestBody Ambulance ambulance) {
        return ambulanceRepo.save(ambulance);
    }

    @GetMapping("/hospital/all")
    public List<Hospital> getAllHospitals() {
        return hospitalRepo.findAll();
    }

    @GetMapping("/ambulance/all")
    public List<Ambulance> getAllAmbulances() {
        return ambulanceRepo.findAll();
    }

    @PostMapping("/emergency/new")
    public EmergencyRequest newEmergency(@RequestBody EmergencyRequest request) {
        return dispatcherService.dispatchEmergencyWithPincode(request);
    }

    @GetMapping("/emergency/active")
    public List<EmergencyRequest> getActiveRequests() {
        return requestRepo.findAll();
    }

    @GetMapping("/dispatch/history")
    public List<DispatchHistory> getDispatchHistory() {
        return historyRepo.findAll();
    }

    @PostMapping("/ambulance/update-status")
    public String updateAmbulanceStatus(@RequestParam String ambulanceId,
                                        @RequestParam String status) {
        return dispatcherService.updateAmbulanceStatus(ambulanceId, status);
    }
}


// package com.example.ambulancedispatch.controller;

// import com.example.ambulancedispatch.model.*;
// import com.example.ambulancedispatch.repository.*;
// import com.example.ambulancedispatch.service.DispatcherService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.boot.CommandLineRunner;

// import java.util.List;

// @RestController
// @RequestMapping("/api")
// public class HomeController implements CommandLineRunner {

//     @Autowired
//     private HospitalRepository hospitalRepo;

//     @Autowired
//     private AmbulanceRepository ambulanceRepo;

//     @Autowired
//     private EmergencyRequestRepository requestRepo;

//     @Autowired
//     private DispatchHistoryRepository historyRepo;

//     @Autowired
//     private DispatcherService dispatcherService;

//     // -------------------- SEED HELPERS --------------------
//     private void seedHospital(String hospitalId, String name, String location,
//                               String pincode, String contact, int beds) {
//         if (hospitalRepo.findById(hospitalId).isEmpty()) {
//             Hospital hospital = new Hospital(name, location, contact, beds);
//             hospital.setId(hospitalId);
//             hospital.setPincode(pincode);
//             hospitalRepo.save(hospital);
//         }
//     }

// private void seedAmbulance(String ambulanceId, String hospitalId, String location,
//                            String pincode, String driverName, String driverPhone) {
//     if (ambulanceRepo.findByAmbulanceId(ambulanceId).isEmpty()) {
//         Ambulance ambulance = new Ambulance();
//         ambulance.setAmbulanceId(ambulanceId);
//         ambulance.setHospitalId(hospitalId);
//         ambulance.setLocation(location);
//         ambulance.setStatus("AVAILABLE");
//         ambulance.setPincode(pincode);
//         ambulance.setDriverName(driverName);
//         ambulance.setDriverPhone(driverPhone);
//         ambulanceRepo.save(ambulance);
//     }
// }




//     // -------------------- INITIAL DATA --------------------
//     @Override
// public void run(String... args) {
//     // 🚨 Clean old data first
//     ambulanceRepo.deleteAll();
//     hospitalRepo.deleteAll();

//     // Hospitals
//     seedHospital("H1", "Apollo Hospitals", "Greams Road", "600006", "044-28293333", 20);
//     seedHospital("H2", "Fortis Malar", "Adyar", "600020", "044-42892222", 15);
//     seedHospital("H3", "MIOT Hospital", "Manapakkam", "600089", "044-42002288", 25);
//     seedHospital("H4", "Kauvery Hospital", "Alwarpet", "600018", "044-40006000", 10);
//     seedHospital("H5", "SRM Hospital", "Kattankulathur", "603203", "044-47432222", 30);
//     seedHospital("H6", "Government General Hospital", "Park Town", "600003", "044-25305000", 50);
//     seedHospital("H7", "SIMS Hospital", "Vadapalani", "600026", "044-20002000", 18);
//     seedHospital("H8", "Global Hospital", "Perumbakkam", "600100", "044-44777000", 22);

//     // Ambulances
//     seedAmbulance("A1", "H1", "Greams Road", "600006", "Rajesh Kumar", "9876543210");
//     seedAmbulance("A2", "H1", "Greams Road", "600006", "Suresh Reddy", "9876543211");
//     seedAmbulance("A3", "H2", "Adyar", "600020", "Karthik Subramani", "9876543212");
//     seedAmbulance("A4", "H3", "Manapakkam", "600089", "Dinesh Babu", "9876543213");
//     seedAmbulance("A5", "H4", "Alwarpet", "600018", "Prakash Iyer", "9876543214");
//     seedAmbulance("A6", "H5", "Kattankulathur", "603203", "Arjun Varma", "9876543215");
//     seedAmbulance("A7", "H6", "Park Town", "600003", "Manoj Krishnan", "9876543216");
//     seedAmbulance("A8", "H7", "Vadapalani", "600026", "Vivek Nair", "9876543217");
//     seedAmbulance("A9", "H8", "Perumbakkam", "600100", "Ashok Selvan", "9876543218");
//     seedAmbulance("A10", "H8", "Perumbakkam", "600100", "Mahesh Chandran", "9876543219");


//         for (Ambulance amb : ambulanceRepo.findAll()) {
//     if (amb.getAmbulanceId() == null || amb.getAmbulanceId().isBlank()) {
//         // If location/pincode match a seeded ambulance, fix manually
//         if (amb.getLocation().equalsIgnoreCase("Greams Road") && amb.getPincode().equals("600006")) {
//             amb.setAmbulanceId("A1");
//         } else if (amb.getLocation().equalsIgnoreCase("Adyar") && amb.getPincode().equals("600020")) {
//             amb.setAmbulanceId("A3");
//         }
//         // … add mappings for A2–A10 …
//         else {
//             // fallback: keep Mongo _id if no match
//             amb.setAmbulanceId(amb.getId());
//         }
//         ambulanceRepo.save(amb);
//     }
// }
//     }

//     // -------------------- ENDPOINTS --------------------
//     @GetMapping("/")
//     public String home() {
//         return "🚑 Ambulance Dispatch System Running!";
//     }

//     @PostMapping("/hospital/add")
//     public Hospital addHospital(@RequestBody Hospital hospital) {
//         return hospitalRepo.save(hospital);
//     }

//     @PostMapping("/ambulance/add")
//     public Ambulance addAmbulance(@RequestBody Ambulance ambulance) {
//         return ambulanceRepo.save(ambulance);
//     }

//     @GetMapping("/hospital/all")
//     public List<Hospital> getAllHospitals() {
//         return hospitalRepo.findAll();
//     }

//     @GetMapping("/ambulance/all")
//     public List<Ambulance> getAllAmbulances() {
//         return ambulanceRepo.findAll();
//     }

//     @PostMapping("/emergency/new")
//     public EmergencyRequest newEmergency(@RequestBody EmergencyRequest request) {
//         return dispatcherService.dispatchEmergencyWithPincode(request);
//     }

//     @GetMapping("/emergency/active")
//     public List<EmergencyRequest> getActiveRequests() {
//         return requestRepo.findAll();
//     }

//     @GetMapping("/dispatch/history")
//     public List<DispatchHistory> getDispatchHistory() {
//         return historyRepo.findAll();
//     }

//     @PostMapping("/ambulance/update-status")
//     public String updateAmbulanceStatus(@RequestParam String ambulanceId,
//                                         @RequestParam String status) {
//         return dispatcherService.updateAmbulanceStatus(ambulanceId, status);
//     }
// }







// package com.example.ambulancedispatch.controller;

// import com.example.ambulancedispatch.model.*;
// import com.example.ambulancedispatch.repository.*;
// import com.example.ambulancedispatch.service.DispatcherService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.boot.CommandLineRunner;

// import java.util.List;

// @RestController
// @RequestMapping("/api")
// public class HomeController implements CommandLineRunner {

//     @Autowired
//     private HospitalRepository hospitalRepo;

//     @Autowired
//     private AmbulanceRepository ambulanceRepo;

//     @Autowired
//     private EmergencyRequestRepository requestRepo;

//     @Autowired
//     private DispatchHistoryRepository historyRepo;

//     @Autowired
//     private DispatcherService dispatcherService;

//     // -------------------- SEED HELPERS --------------------
//     private void seedHospital(String hospitalId, String name, String location,
//                               String pincode, String contact, int beds) {
//         if (hospitalRepo.findById(hospitalId).isEmpty()) {
//             Hospital hospital = new Hospital(name, location, contact, beds);
//             hospital.setId(hospitalId);
//             hospital.setPincode(pincode);
//             hospitalRepo.save(hospital);
//         }
//     }

//     private void seedAmbulance(String ambulanceId, String hospitalId, String location,
//                                String pincode, String driverName, String driverPhone) {
//         if (ambulanceRepo.findByAmbulanceId(ambulanceId) == null) {
//             Ambulance ambulance = new Ambulance(ambulanceId, hospitalId, location, "AVAILABLE");
//             ambulance.setPincode(pincode);
//             ambulance.setDriverName(driverName);
//             ambulance.setDriverPhone(driverPhone);
//             ambulanceRepo.save(ambulance);
//         }
//     }

//     // -------------------- INITIAL DATA --------------------
//     @Override
//     public void run(String... args) {
//         // Clean old data first (optional, for fresh start)
//         ambulanceRepo.deleteAll();
//         hospitalRepo.deleteAll();

//         // Hospitals
//         seedHospital("H1", "Apollo Hospitals", "Greams Road", "600006", "044-28293333", 20);
//         seedHospital("H2", "Fortis Malar", "Adyar", "600020", "044-42892222", 15);
//         seedHospital("H3", "MIOT Hospital", "Manapakkam", "600089", "044-42002288", 25);
//         seedHospital("H4", "Kauvery Hospital", "Alwarpet", "600018", "044-40006000", 10);
//         seedHospital("H5", "SRM Hospital", "Kattankulathur", "603203", "044-47432222", 30);
//         seedHospital("H6", "Government General Hospital", "Park Town", "600003", "044-25305000", 50);
//         seedHospital("H7", "SIMS Hospital", "Vadapalani", "600026", "044-20002000", 18);
//         seedHospital("H8", "Global Hospital", "Perumbakkam", "600100", "044-44777000", 22);

//         // Ambulances
//         seedAmbulance("A1", "H1", "Greams Road", "600006", "Rajesh Kumar", "9876543210");
//         seedAmbulance("A2", "H1", "Greams Road", "600006", "Suresh Reddy", "9876543211");
//         seedAmbulance("A3", "H2", "Adyar", "600020", "Karthik Subramani", "9876543212");
//         seedAmbulance("A4", "H3", "Manapakkam", "600089", "Dinesh Babu", "9876543213");
//         seedAmbulance("A5", "H4", "Alwarpet", "600018", "Prakash Iyer", "9876543214");
//         seedAmbulance("A6", "H5", "Kattankulathur", "603203", "Arjun Varma", "9876543215");
//         seedAmbulance("A7", "H6", "Park Town", "600003", "Manoj Krishnan", "9876543216");
//         seedAmbulance("A8", "H7", "Vadapalani", "600026", "Vivek Nair", "9876543217");
//         seedAmbulance("A9", "H8", "Perumbakkam", "600100", "Ashok Selvan", "9876543218");
//         seedAmbulance("A10", "H8", "Perumbakkam", "600100", "Mahesh Chandran", "9876543219");
//     }

//     // -------------------- ENDPOINTS --------------------
//     @GetMapping("/")
//     public String home() {
//         return "🚑 Ambulance Dispatch System Running!";
//     }

//     @PostMapping("/hospital/add")
//     public Hospital addHospital(@RequestBody Hospital hospital) {
//         return hospitalRepo.save(hospital);
//     }

//     @PostMapping("/ambulance/add")
//     public Ambulance addAmbulance(@RequestBody Ambulance ambulance) {
//         return ambulanceRepo.save(ambulance);
//     }

//     @GetMapping("/hospital/all")
//     public List<Hospital> getAllHospitals() {
//         return hospitalRepo.findAll();
//     }

//     @GetMapping("/ambulance/all")
//     public List<Ambulance> getAllAmbulances() {
//         return ambulanceRepo.findAll();
//     }

//     @PostMapping("/emergency/new")
//     public EmergencyRequest newEmergency(@RequestBody EmergencyRequest request) {
//         return dispatcherService.dispatchEmergencyWithPincode(request);
//     }

//     @GetMapping("/emergency/active")
//     public List<EmergencyRequest> getActiveRequests() {
//         return requestRepo.findAll();
//     }

//     @GetMapping("/dispatch/history")
//     public List<DispatchHistory> getDispatchHistory() {
//         return historyRepo.findAll();
//     }

//     @PostMapping("/ambulance/update-status")
//     public String updateAmbulanceStatus(@RequestParam String ambulanceId,
//                                         @RequestParam String status) {
//         return dispatcherService.updateAmbulanceStatus(ambulanceId, status);
//     }
// }