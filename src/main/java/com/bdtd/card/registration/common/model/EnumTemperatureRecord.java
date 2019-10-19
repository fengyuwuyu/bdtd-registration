package com.bdtd.card.registration.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum EnumTemperatureRecord {

    TWO(2, "2"), SIX(6, "6"), TEN(10, "10"), FOURTEEN(14, "14"), EIGHTEEN(18, "18"), TWENTY_TWO(22, "22");

    private int type;
    private String desc;

    private EnumTemperatureRecord(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
    
    public static List<Map<String, Object>> select() {
        List<Map<String, Object>> result = new ArrayList<>(EnumTemperatureRecord.values().length);
        for (EnumTemperatureRecord type : EnumTemperatureRecord.values()) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", type.getType());
            map.put("name", type.getDesc());
            result.add(map);
        }
        return result;
    }

}
