package com.example.ambulancedispatch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "emergency_requests")
public class EmergencyRequest {
    @Id
    private String id;
    private String patientName;
    private String contactNo;
    private String emergencyType;
    private String patientLocation;
    private String pincode;
    private int priority; // 1 = Normal, 2 = Emergency
    private String status; // e.g. PENDING, ASSIGNED, COMPLETED
    private Ambulance assignedAmbulance;
    private Hospital assignedHospital;
    private LocalDateTime requestTime;
    private LocalDateTime updateTime;

    public EmergencyRequest() {
        this.requestTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.status = "PENDING";
    }

    public EmergencyRequest(String patientName, String contactNo, String emergencyType, String patientLocation) {
        this.patientName = patientName;
        this.contactNo = contactNo;
        this.emergencyType = emergencyType;
        this.patientLocation = patientLocation;
        this.requestTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.status = "PENDING";
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getContactNo() { return contactNo; }
    public void setContactNo(String contactNo) { this.contactNo = contactNo; }

    public String getEmergencyType() { return emergencyType; }
    public void setEmergencyType(String emergencyType) { this.emergencyType = emergencyType; }

    public String getPatientLocation() { return patientLocation; }
    public void setPatientLocation(String patientLocation) { this.patientLocation = patientLocation; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public String getStatus() { return status; }
    public void setStatus(String status) {
        this.status = status;
        this.updateTime = LocalDateTime.now();
    }

    public Ambulance getAssignedAmbulance() { return assignedAmbulance; }
    public void setAssignedAmbulance(Ambulance assignedAmbulance) { this.assignedAmbulance = assignedAmbulance; }

    public Hospital getAssignedHospital() { return assignedHospital; }
    public void setAssignedHospital(Hospital assignedHospital) { this.assignedHospital = assignedHospital; }

    public LocalDateTime getRequestTime() { return requestTime; }
    public void setRequestTime(LocalDateTime requestTime) { this.requestTime = requestTime; }

    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
}
