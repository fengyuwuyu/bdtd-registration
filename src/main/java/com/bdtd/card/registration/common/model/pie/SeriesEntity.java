package com.bdtd.card.registration.common.model.pie;

import java.util.List;

public class SeriesEntity {

    private String type = "pie";
    private List<String> center;
    private List<Integer> radius;
    private String x;
    private List<SeriesDataEntity> seriesDatas;

    public SeriesEntity() {
    }

    public SeriesEntity(List<String> center, List<Integer> radius, String x,
            List<SeriesDataEntity> seriesDatas) {
        this.center = center;
        this.radius = radius;
        this.x = x;
        this.seriesDatas = seriesDatas;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getCenter() {
        return center;
    }

    public void setCenter(List<String> center) {
        this.center = center;
    }

    public List<Integer> getRadius() {
        return radius;
    }

    public void setRadius(List<Integer> radius) {
        this.radius = radius;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public List<SeriesDataEntity> getSeriesDatas() {
        return seriesDatas;
    }

    public void setSeriesDatas(List<SeriesDataEntity> seriesDatas) {
        this.seriesDatas = seriesDatas;
    }

    @Override
    public String toString() {
        return "SeriesEntity [type=" + type + ", center=" + center + ", radius=" + radius + ", x=" + x
                + ", seriesDatas=" + seriesDatas + "]";
    }

}
