package com.bdtd.card.registration.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum EnumOutpatientType {

    COMMON(1, "普通门诊"), FEVER(2, "发热门诊")
    ;
    
    private int type;
    private String desc;
    private EnumOutpatientType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
    public int getType() {
        return type;
    }
    public String getDesc() {
        return desc;
    }
    private static Map<Integer, EnumOutpatientType> map = new HashMap<>(EnumOutpatientType.values().length);
    
    static {
        for (EnumOutpatientType type : EnumOutpatientType.values()) {
            map.put(type.getType(), type);
        }
    }
    
    public static EnumOutpatientType fromType(Integer type) {
        return map.get(type);
    }
    
    public static List<Map<String, Object>> select() {
        List<Map<String, Object>> result = new ArrayList<>(EnumOutpatientType.values().length);
        for (EnumOutpatientType type : EnumOutpatientType.values()) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", type.getType());
            map.put("name", type.getDesc());
            result.add(map);
        }
        return result;
    }
    
}
