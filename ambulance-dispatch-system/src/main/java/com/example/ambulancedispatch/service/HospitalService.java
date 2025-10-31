// package com.example.ambulancedispatch.service;

// import com.example.ambulancedispatch.model.Hospital;
// import com.example.ambulancedispatch.repository.HospitalRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class HospitalService {

//     @Autowired
//     private HospitalRepository hospitalRepository;

//     public Hospital addHospital(Hospital hospital) {
//         return hospitalRepository.save(hospital);
//     }

//     public List<Hospital> getAllHospitals() {
//         return hospitalRepository.findAll();
//     }
// }

// package com.example.ambulancedispatch.service;

// import com.example.ambulancedispatch.model.Hospital;
// import com.example.ambulancedispatch.repository.HospitalRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;

// @Service
// public class HospitalService {

//     @Autowired
//     private HospitalRepository hospitalRepository;

//     public Hospital addHospital(Hospital hospital) {
//         return hospitalRepository.save(hospital);
//     }

//     public List<Hospital> getAllHospitals() {
//         return hospitalRepository.findAll();
//     }

//     public boolean deleteHospital(String id) {
//         Optional<Hospital> hospital = hospitalRepository.findById(id);
//         if (hospital.isPresent()) {
//             hospitalRepository.deleteById(id);
//             return true;
//         }
//         return false;
//     }
// }


package com.example.ambulancedispatch.service;

import com.example.ambulancedispatch.model.Hospital;
import com.example.ambulancedispatch.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    public Hospital addHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public boolean deleteHospital(String id) {
        Optional<Hospital> hospital = hospitalRepository.findById(id);
        if (hospital.isPresent()) {
            hospitalRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
