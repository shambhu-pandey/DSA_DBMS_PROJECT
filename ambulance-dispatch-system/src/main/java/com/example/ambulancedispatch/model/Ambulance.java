// package com.example.ambulancedispatch.model;

// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;

// @Document(collection = "ambulances")
// public class Ambulance {

//     @Id
//     private String id;   // MongoDB internal _id (used by Mongo only)

//     private String ambulanceId;  // ✅ Business ID like A1, A2, etc.
//     private String hospitalId;
//     private String location;
//     private String pincode;
//     private String driverName;
//     private String driverPhone;
//     private String status; // AVAILABLE / BUSY / MAINTENANCE

//     public Ambulance() {}

//     public Ambulance(String ambulanceId, String hospitalId, String location, String status) {
//         this.ambulanceId = ambulanceId;
//         this.hospitalId = hospitalId;
//         this.location = location;
//         this.status = status;
//     }

//     // --- Mongo _id ---
//     public String getId() {
//         return id;
//     }
//     public void setId(String id) {
//         this.id = id;
//     }

//     // --- Business AmbulanceId ---
//     public String getAmbulanceId() {
//         return ambulanceId;
//     }
//     public void setAmbulanceId(String ambulanceId) {
//         this.ambulanceId = ambulanceId;
//     }

//     public String getHospitalId() {
//         return hospitalId;
//     }
//     public void setHospitalId(String hospitalId) {
//         this.hospitalId = hospitalId;
//     }

//     public String getLocation() {
//         return location;
//     }
//     public void setLocation(String location) {
//         this.location = location;
//     }

//     public String getPincode() {
//         return pincode;
//     }
//     public void setPincode(String pincode) {
//         this.pincode = pincode;
//     }

//     public String getDriverName() {
//         return driverName;
//     }
//     public void setDriverName(String driverName) {
//         this.driverName = driverName;
//     }

//     public String getDriverPhone() {
//         return driverPhone;
//     }
//     public void setDriverPhone(String driverPhone) {
//         this.driverPhone = driverPhone;
//     }

//     public String getStatus() {
//         return status;
//     }
//     public void setStatus(String status) {
//         this.status = status;
//     }
// }



package com.example.ambulancedispatch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ambulances")
public class Ambulance {

    @Id
    private String id;

    private String ambulanceId;
    private String hospitalId;
    private String location;
    private String pincode;
    private String driverName;
    private String driverPhone;
    private String status; // AVAILABLE / BUSY / MAINTENANCE

    public Ambulance() {}

    public Ambulance(String ambulanceId, String hospitalId, String location, String status) {
        this.ambulanceId = ambulanceId;
        this.hospitalId = hospitalId;
        this.location = location;
        this.status = status;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAmbulanceId() { return ambulanceId; }
    public void setAmbulanceId(String ambulanceId) { this.ambulanceId = ambulanceId; }

    public String getHospitalId() { return hospitalId; }
    public void setHospitalId(String hospitalId) { this.hospitalId = hospitalId; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }

    public String getDriverPhone() { return driverPhone; }
    public void setDriverPhone(String driverPhone) { this.driverPhone = driverPhone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
