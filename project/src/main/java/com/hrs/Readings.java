package com.hrs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Readings {
    @JsonProperty("id")
    private int id;
    @JsonProperty("readings")
    private List<ReadingType> readings;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ReadingType> getReadings() {
        return readings;
    }

    public void setReadings(List<ReadingType> readings) {
        this.readings = readings;
    }
}
