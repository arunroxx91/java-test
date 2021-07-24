package com.hrs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Type {
    @JsonProperty("bloodPressure")
    private SubType bloodPressure;
    @JsonProperty("glucose")
    private SubType glucose;
    @JsonProperty("weight")
    private SubType weight;

    public SubType getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(SubType bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public SubType getGlucose() {
        return glucose;
    }

    public void setGlucose(SubType glucose) {
        this.glucose = glucose;
    }

    public SubType getWeight() {
        return weight;
    }

    public void setWeight(SubType weight) {
        this.weight = weight;
    }
}
