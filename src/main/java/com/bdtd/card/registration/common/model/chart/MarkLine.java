package com.bdtd.card.registration.common.model.chart;

import java.util.List;

public class MarkLine {

    private List<MarkPointData> data;

    public MarkLine() {
    }

    public MarkLine(List<MarkPointData> data) {
        this.data = data;
    }

    public List<MarkPointData> getData() {
        return data;
    }

    public void setData(List<MarkPointData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MarkLine [data=" + data + "]";
    }

}
