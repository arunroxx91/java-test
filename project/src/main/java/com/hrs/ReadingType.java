package com.hrs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReadingType {
    @JsonProperty("systolic")
    private String systolic;
    @JsonProperty("diastolic")
    private String diastolic;
    @JsonProperty("heartRate")
    private String heartRate;
    @JsonProperty("bloodSugarLevel")
    private String bloodSugarLevel;
    @JsonProperty("weight")
    private String weight;
    @JsonProperty("type")
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
