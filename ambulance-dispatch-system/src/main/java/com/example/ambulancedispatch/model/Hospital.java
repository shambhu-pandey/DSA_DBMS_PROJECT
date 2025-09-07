package com.example.ambulancedispatch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "hospitals")
public class Hospital {
    @Id
    private String id;
    private String name;
    private String location;
    private String pincode;
    private String contact;
    private int availableBeds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Hospital() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Hospital(String name, String location, int availableBeds) {
        this.name = name;
        this.location = location;
        this.availableBeds = availableBeds;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Hospital(String name, String location, String contact, int availableBeds) {
        this.name = name;
        this.location = location;
        this.contact = contact;
        this.availableBeds = availableBeds;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public int getAvailableBeds() { return availableBeds; }
    public void setAvailableBeds(int availableBeds) {
        this.availableBeds = availableBeds;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
