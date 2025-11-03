Absolutely, Shambhu! Here's a complete `.md` (Markdown) file containing **all MongoDB queries from Level 0 to Level 5**, formatted for easy reference, sharing, or inclusion in your report or viva notes.

---

```markdown
# 🚑 MongoDB Query Bank – Ambulance Dispatch System

This file contains MongoDB queries organized by level, from basic to advanced, used in the Ambulance Dispatch System project.

---

## 🟢 Level 0 – Basic Queries

### Show all collections
```javascript
show collections
```

### View all emergency requests
```javascript
db.emergency_requests.find()
```

### Count assigned requests
```javascript
db.emergency_requests.countDocuments({ status: "ASSIGNED" })
```

### Find requests by pincode
```javascript
db.emergency_requests.find({ patientPincode: "600119" })
```

### Find available ambulances
```javascript
db.ambulances.find({ status: "AVAILABLE" })
```

### Find hospitals with available beds
```javascript
db.hospitals.find({ availableBeds: { $gt: 0 } })
```

---

## 🟡 Level 1 – Comparison Operators

### Hospitals with beds > 0
```javascript
db.hospitals.find({ availableBeds: { $gt: 0 } })
```

### Requests with ETA < 15
```javascript
db.emergency_requests.find({ eta: { $lt: 15 } })
```

### Ambulances in specific pincode
```javascript
db.ambulances.find({ locationPincode: { $eq: "600119" } })
```

### Requests with ETA between 10 and 20
```javascript
db.emergency_requests.find({ eta: { $gte: 10, $lte: 20 } })
```

### Hospitals with beds < 5
```javascript
db.hospitals.find({ availableBeds: { $lt: 5 } })
```

### Requests not assigned
```javascript
db.emergency_requests.find({ status: { $ne: "ASSIGNED" } })
```

---

## 🟠 Level 2 – Logical Operators

### Requests that are ASSIGNED and from pincode 600119
```javascript
db.emergency_requests.find({
  $and: [
    { status: "ASSIGNED" },
    { patientPincode: "600119" }
  ]
})
```

### Requests that are ASSIGNED or REASSIGNED
```javascript
db.emergency_requests.find({
  $or: [
    { status: "ASSIGNED" },
    { status: "REASSIGNED" }
  ]
})
```

### Requests with status in multiple values
```javascript
db.emergency_requests.find({
  status: { $in: ["ASSIGNED", "REASSIGNED", "PENDING"] }
})
```

### Requests not assigned (using $not)
```javascript
db.emergency_requests.find({
  status: { $not: { $eq: "ASSIGNED" } }
})
```

### Hospitals in pincode 600119 with beds > 5
```javascript
db.hospitals.find({
  $and: [
    { locationPincode: "600119" },
    { availableBeds: { $gt: 5 } }
  ]
})
```

---

## 🔵 Level 3 – Sorting and Limiting

### Sort requests by ETA (ascending)
```javascript
db.emergency_requests.find().sort({ eta: 1 })
```

### Sort requests by ETA (descending)
```javascript
db.emergency_requests.find().sort({ eta: -1 })
```

### Sort by most recent (if createdAt exists)
```javascript
db.emergency_requests.find().sort({ createdAt: -1 })
```

### Limit results to top 5
```javascript
db.emergency_requests.find().limit(5)
```

### Top 3 fastest assigned requests
```javascript
db.emergency_requests.find({ status: "ASSIGNED" }).sort({ eta: 1 }).limit(3)
```

### Sort hospitals by available beds (descending)
```javascript
db.hospitals.find().sort({ availableBeds: -1 })
```

---

## 🟣 Level 4 – Projection (Field Selection)

### Show only patientName and status
```javascript
db.emergency_requests.find({}, { patientName: 1, status: 1, _id: 0 })
```

### Show hospital name and available beds
```javascript
db.hospitals.find({}, { name: 1, availableBeds: 1, _id: 0 })
```

### Show ambulance driver and location
```javascript
db.ambulances.find({}, { driverName: 1, locationPincode: 1, _id: 0 })
```

### Show ETA and assigned hospital
```javascript
db.emergency_requests.find({}, { eta: 1, assignedHospitalId: 1, _id: 0 })
```

### Assigned requests with patient name and ETA
```javascript
db.emergency_requests.find(
  { status: "ASSIGNED" },
  { patientName: 1, eta: 1, _id: 0 }
)
```

---

## 🔴 Level 5 – Aggregation Framework

### Group requests by status and count them
```javascript
db.emergency_requests.aggregate([
  { $group: { _id: "$status", count: { $sum: 1 } } }
])
```

### Average ETA for assigned requests
```javascript
db.emergency_requests.aggregate([
  { $match: { status: "ASSIGNED" } },
  { $group: { _id: null, avgETA: { $avg: "$eta" } } }
])
```

### Count hospitals by pincode
```javascript
db.hospitals.aggregate([
  { $group: { _id: "$locationPincode", totalHospitals: { $sum: 1 } } }
])
```

### Total available beds by area
```javascript
db.hospitals.aggregate([
  { $group: { _id: "$locationPincode", totalBeds: { $sum: "$availableBeds" } } }
])
```

### Top 3 fastest assigned requests
```javascript
db.emergency_requests.aggregate([
  { $match: { status: "ASSIGNED" } },
  { $sort: { eta: 1 } },
  { $limit: 3 },
  { $project: { patientName: 1, eta: 1, _id: 0 } }
])
```

### Count ambulances by status
```javascript
db.ambulances.aggregate([
  { $group: { _id: "$status", count: { $sum: 1 } } }
])
```

### Requests per emergency type
```javascript
db.emergency_requests.aggregate([
  { $group: { _id: "$emergencyType", total: { $sum: 1 } } }
])
```

---

*Prepared by Shambhu Pandey for MCA Final Project – Ambulance Dispatch System*
```

---

Let me know if you'd like help inserting this into your report or converting it into a Word or PDF format manually. You're fully viva-ready now!