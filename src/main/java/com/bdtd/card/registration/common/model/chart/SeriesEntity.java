package com.bdtd.card.registration.common.model.chart;

import java.util.List;

public class SeriesEntity<T> {

    private String name;
    private String type = "bar";
    private List<T> data;
    private MarkPoint markPoint;

    public SeriesEntity() {
    }

    public SeriesEntity(String name, List<T> data) {
        this.name = name;
        this.data = data;
    }

    public SeriesEntity(String name, List<T> data, MarkPoint markPoint) {
        this.name = name;
        this.data = data;
        this.markPoint = markPoint;
    }

    public SeriesEntity(String name, String type, List<T> data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
    
    public SeriesEntity(String name, String type, List<T> data, MarkPoint markPoint) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.markPoint = markPoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public MarkPoint getMarkPoint() {
        return markPoint;
    }

    public void setMarkPoint(MarkPoint markPoint) {
        this.markPoint = markPoint;
    }

    @Override
    public String toString() {
        return "SeriesEntity [name=" + name + ", type=" + type + ", data=" + data + ", markPoint=" + markPoint + "]";
    }

}
