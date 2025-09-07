package com.example.ambulancedispatch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "dispatch_history")
public class DispatchHistory {
    @Id
    private String id;
    private String requestId;
    private String ambulanceId;
    private String hospitalId;
    private String hospitalName;      // added
    private String hospitalPincode;   // added
    private String patientName;
    private String patientLocation;
    private String patientPincode;
    private String emergencyType;
    private String ambulancePincode;
    private String driverName;
    private String driverPhone;
    private String outcome; // e.g. COMPLETED, CANCELLED
    private LocalDateTime dispatchTime;
    private LocalDateTime completionTime;

    public DispatchHistory() {
        this.dispatchTime = LocalDateTime.now();
    }

    public DispatchHistory(String requestId, String ambulanceId, String hospitalId,
                          String patientName, String patientLocation, String emergencyType) {
        this.requestId = requestId;
        this.ambulanceId = ambulanceId;
        this.hospitalId = hospitalId;
        this.patientName = patientName;
        this.patientLocation = patientLocation;
        this.emergencyType = emergencyType;
        this.dispatchTime = LocalDateTime.now();
        this.outcome = "DISPATCHED";
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getAmbulanceId() { return ambulanceId; }
    public void setAmbulanceId(String ambulanceId) { this.ambulanceId = ambulanceId; }

    public String getHospitalId() { return hospitalId; }
    public void setHospitalId(String hospitalId) { this.hospitalId = hospitalId; }

    // NEW: hospital name & pincode for easier reporting (optional)
    public String getHospitalName() { return hospitalName; }
    public void setHospitalName(String hospitalName) { this.hospitalName = hospitalName; }

    public String getHospitalPincode() { return hospitalPincode; }
    public void setHospitalPincode(String hospitalPincode) { this.hospitalPincode = hospitalPincode; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getPatientLocation() { return patientLocation; }
    public void setPatientLocation(String patientLocation) { this.patientLocation = patientLocation; }

    public String getPatientPincode() { return patientPincode; }
    public void setPatientPincode(String patientPincode) { this.patientPincode = patientPincode; }

    public String getEmergencyType() { return emergencyType; }
    public void setEmergencyType(String emergencyType) { this.emergencyType = emergencyType; }

    public String getAmbulancePincode() { return ambulancePincode; }
    public void setAmbulancePincode(String ambulancePincode) { this.ambulancePincode = ambulancePincode; }

    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }

    public String getDriverPhone() { return driverPhone; }
    public void setDriverPhone(String driverPhone) { this.driverPhone = driverPhone; }

    public String getOutcome() { return outcome; }
    public void setOutcome(String outcome) {
        this.outcome = outcome;
        if ("COMPLETED".equals(outcome) || "CANCELLED".equals(outcome)) {
            this.completionTime = LocalDateTime.now();
        }
    }

    public LocalDateTime getDispatchTime() { return dispatchTime; }
    public void setDispatchTime(LocalDateTime dispatchTime) { this.dispatchTime = dispatchTime; }

    public LocalDateTime getCompletionTime() { return completionTime; }
    public void setCompletionTime(LocalDateTime completionTime) { this.completionTime = completionTime; }
}
