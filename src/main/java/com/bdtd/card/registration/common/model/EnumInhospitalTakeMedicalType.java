package com.bdtd.card.registration.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum EnumInhospitalTakeMedicalType {
    
    LONGTERM(1, "长期医嘱"), TEMPORARY(2, "临时医嘱")
    ; 

    private int type;
    private String desc;

    private EnumInhospitalTakeMedicalType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
    
    private static Map<Integer, EnumInhospitalTakeMedicalType> CACHE = new HashMap<>(EnumInhospitalTakeMedicalType.values().length);
    
    static {
        for (EnumInhospitalTakeMedicalType type : EnumInhospitalTakeMedicalType.values()) {
            CACHE.put(type.getType(), type);
        }
    }
    
    public static EnumInhospitalTakeMedicalType typeOf(Integer type) {
        return CACHE.get(type);
    }
    
    public static List<Map<String, Object>> select() {
        List<Map<String, Object>> result = new ArrayList<>(EnumInhospitalTakeMedicalType.values().length);
        for (EnumInhospitalTakeMedicalType type : EnumInhospitalTakeMedicalType.values()) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", type.getType());
            map.put("name", type.getDesc());
            result.add(map);
        }
        return result;
    }
    
}
