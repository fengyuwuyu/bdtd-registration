package com.bdtd.card.registration.common.model.pie;

public class SeriesDataEntity {

    private String name;
    private Long value;
    private String itemStyle;
    
    public SeriesDataEntity() {
    }

    public SeriesDataEntity(String name, Long value, String itemStyle) {
        this.name = name;
        this.value = value;
        this.itemStyle = itemStyle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getItemStyle() {
        return itemStyle;
    }

    public void setItemStyle(String itemStyle) {
        this.itemStyle = itemStyle;
    }

    @Override
    public String toString() {
        return "SeriesEntity [name=" + name + ", value=" + value + ", itemStyle=" + itemStyle + "]";
    }

}
