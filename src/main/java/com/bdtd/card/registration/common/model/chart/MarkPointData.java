package com.bdtd.card.registration.common.model.chart;

public class MarkPointData {

    private String type;
    private String name;

    public MarkPointData() {
    }

    public MarkPointData(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MarkPointData [type=" + type + ", name=" + name + "]";
    }

}
