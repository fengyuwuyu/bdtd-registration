package com.bdtd.card.registration.common.model.pie;

import java.util.List;

public class PieEntity {

    private List<String> data;
    private String title;
    private String subTitle;
    private String xAlign = "center";
    private String legendXAlign = "center";
    private String legendYAlign = "center";
    private List<SeriesEntity> series;

    public PieEntity() {
    }

    public PieEntity(List<String> data, String title, String subTitle, List<SeriesEntity> series) {
        this.data = data;
        this.title = title;
        this.subTitle = subTitle;
        this.series = series;
    }

    public PieEntity(List<String> data, String title, String subTitle, String xAlign, String legendXAlign,
            String legendYAlign) {
        this.data = data;
        this.title = title;
        this.subTitle = subTitle;
        this.xAlign = xAlign;
        this.legendXAlign = legendXAlign;
        this.legendYAlign = legendYAlign;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getxAlign() {
        return xAlign;
    }

    public void setxAlign(String xAlign) {
        this.xAlign = xAlign;
    }

    public String getLegendXAlign() {
        return legendXAlign;
    }

    public void setLegendXAlign(String legendXAlign) {
        this.legendXAlign = legendXAlign;
    }

    public String getLegendYAlign() {
        return legendYAlign;
    }

    public void setLegendYAlign(String legendYAlign) {
        this.legendYAlign = legendYAlign;
    }

    public List<SeriesEntity> getSeries() {
        return series;
    }

    public void setSeries(List<SeriesEntity> series) {
        this.series = series;
    }

    @Override
    public String toString() {
        return "PieEntity [data=" + data + ", title=" + title + ", subTitle=" + subTitle + ", xAlign=" + xAlign
                + ", legendXAlign=" + legendXAlign + ", legendYAlign=" + legendYAlign + ", series=" + series + "]";
    }

}
