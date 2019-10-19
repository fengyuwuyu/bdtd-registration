package com.bdtd.card.registration.common.model.chart;

import java.util.Arrays;
import java.util.List;

public class CylinderShapeEntity<T> {

    private String title;
    private String subTitle;
    private List<String> legend;
    private String xAxisType = "category";
    private String xAxisName = "单位";
    private List<String> xAxisData;
    private String yAxisType = "value";
    private String yAxisName = "人数";
    private List<SeriesEntity<T>> series;

    private boolean toolBoxShow = true;
    private boolean featureMarkShow = true;
    private boolean featureDataViewShow = true;
    private boolean featureDataViewReadOnly = false;
    private boolean featureMagicTypeShow = true;
    private List<String> featureMagicTypeType = Arrays.asList("line", "bar");
    private boolean featureRestoreShow = true;
    private boolean featureSaveAsImageShow = true;
    private boolean calculable = true;

    public CylinderShapeEntity() {
    }

    public CylinderShapeEntity(String title, String subTitle, List<String> legend, List<String> xAxisData,
            List<SeriesEntity<T>> series) {
        this.title = title;
        this.subTitle = subTitle;
        this.legend = legend;
        this.xAxisData = xAxisData;
        this.series = series;
    }

    public CylinderShapeEntity(String title, String subTitle, List<String> legend, String xAxisType,
            List<String> xAxisData, String yAxisType, List<SeriesEntity<T>> series) {
        this.title = title;
        this.subTitle = subTitle;
        this.legend = legend;
        this.xAxisType = xAxisType;
        this.xAxisData = xAxisData;
        this.yAxisType = yAxisType;
        this.series = series;
    }

    public boolean isCalculable() {
        return calculable;
    }

    public void setCalculable(boolean calculable) {
        this.calculable = calculable;
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

    public List<String> getLegend() {
        return legend;
    }

    public void setLegend(List<String> legend) {
        this.legend = legend;
    }

    public String getxAxisType() {
        return xAxisType;
    }

    public void setxAxisType(String xAxisType) {
        this.xAxisType = xAxisType;
    }

    public List<String> getxAxisData() {
        return xAxisData;
    }

    public void setxAxisData(List<String> xAxisData) {
        this.xAxisData = xAxisData;
    }

    public String getyAxisType() {
        return yAxisType;
    }

    public void setyAxisType(String yAxisType) {
        this.yAxisType = yAxisType;
    }

    public List<SeriesEntity<T>> getSeries() {
        return series;
    }

    public void setSeries(List<SeriesEntity<T>> series) {
        this.series = series;
    }

    public boolean getToolBoxShow() {
        return toolBoxShow;
    }

    public void setToolBoxShow(boolean toolBoxShow) {
        this.toolBoxShow = toolBoxShow;
    }

    public boolean getFeatureMarkShow() {
        return featureMarkShow;
    }

    public void setFeatureMarkShow(boolean featureMarkShow) {
        this.featureMarkShow = featureMarkShow;
    }

    public boolean getFeatureDataViewShow() {
        return featureDataViewShow;
    }

    public void setFeatureDataViewShow(boolean featureDataViewShow) {
        this.featureDataViewShow = featureDataViewShow;
    }

    public boolean getFeatureDataViewReadOnly() {
        return featureDataViewReadOnly;
    }

    public void setFeatureDataViewReadOnly(boolean featureDataViewReadOnly) {
        this.featureDataViewReadOnly = featureDataViewReadOnly;
    }

    public boolean getFeatureMagicTypeShow() {
        return featureMagicTypeShow;
    }

    public void setFeatureMagicTypeShow(boolean featureMagicTypeShow) {
        this.featureMagicTypeShow = featureMagicTypeShow;
    }

    public List<String> getFeatureMagicTypeType() {
        return featureMagicTypeType;
    }

    public void setFeatureMagicTypeType(List<String> featureMagicTypeType) {
        this.featureMagicTypeType = featureMagicTypeType;
    }

    public boolean getFeatureRestoreShow() {
        return featureRestoreShow;
    }

    public void setFeatureRestoreShow(boolean featureRestoreShow) {
        this.featureRestoreShow = featureRestoreShow;
    }

    public boolean getFeatureSaveAsImageShow() {
        return featureSaveAsImageShow;
    }

    public void setFeatureSaveAsImageShow(boolean featureSaveAsImageShow) {
        this.featureSaveAsImageShow = featureSaveAsImageShow;
    }

    public String getxAxisName() {
        return xAxisName;
    }

    public void setxAxisName(String xAxisName) {
        this.xAxisName = xAxisName;
    }

    public String getyAxisName() {
        return yAxisName;
    }

    public void setyAxisName(String yAxisName) {
        this.yAxisName = yAxisName;
    }

    @Override
    public String toString() {
        return "CylinderShapeEntity [title=" + title + ", subTitle=" + subTitle + ", legend=" + legend + ", xAxisType="
                + xAxisType + ", xAxisName=" + xAxisName + ", xAxisData=" + xAxisData + ", yAxisType=" + yAxisType
                + ", yAxisName=" + yAxisName + ", series=" + series + ", toolBoxShow=" + toolBoxShow
                + ", featureMarkShow=" + featureMarkShow + ", featureDataViewShow=" + featureDataViewShow
                + ", featureDataViewReadOnly=" + featureDataViewReadOnly + ", featureMagicTypeShow="
                + featureMagicTypeShow + ", featureMagicTypeType=" + featureMagicTypeType + ", featureRestoreShow="
                + featureRestoreShow + ", featureSaveAsImageShow=" + featureSaveAsImageShow + ", calculable="
                + calculable + "]";
    }

}
