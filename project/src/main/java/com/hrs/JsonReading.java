package com.hrs;

import java.util.List;

public class JsonReading {
    public String id;
    public List<Reading> readings;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Reading> getReadings() {
        return readings;
    }

    public void setReadings(List<Reading> readings) {
        this.readings = readings;
    }

    public class Reading {
        public String type;
        public String systolic;
        public String diastolic;
        public String heartRate;
        public String bloodSugarLevel;
        public String weight;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSystolic() {
            return systolic;
        }

        public void setSystolic(String systolic) {
            this.systolic = systolic;
        }

        public String getDiastolic() {
            return diastolic;
        }

        public void setDiastolic(String diastolic) {
            this.diastolic = diastolic;
        }

        public String getHeartRate() {
            return heartRate;
        }

        public void setHeartRate(String heartRate) {
            this.heartRate = heartRate;
        }

        public String getBloodSugarLevel() {
            return bloodSugarLevel;
        }

        public void setBloodSugarLevel(String bloodSugarLevel) {
            this.bloodSugarLevel = bloodSugarLevel;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

    }
}