package com.hrs;

public class Response {
    private String id;
    private String type;
    private  String subType;
    private  String subTypeValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getSubTypeValue() {
        return subTypeValue;
    }

    public void setSubTypeValue(String subTypeValue) {
        this.subTypeValue = subTypeValue;
    }
}
