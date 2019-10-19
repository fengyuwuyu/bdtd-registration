package com.bdtd.card.registration.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum EnumSickRestType {

    WHOLE_REST(1, "全休"),  HALF_REST(2, "半休");
    
    private int type;
    private String desc;
    
    private EnumSickRestType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    private static Map<Integer, EnumSickRestType> map = new HashMap<>(EnumSickRestType.values().length);
    
    static {
        for (EnumSickRestType type : EnumSickRestType.values()) {
            map.put(type.getType(), type);
        }
    }
    
    public static EnumSickRestType fromType(Integer type) {
        return map.get(type);
    }
    
    public static List<Map<String, Object>> select() {
        List<Map<String, Object>> result = new ArrayList<>(EnumSickRestType.values().length);
        for (EnumSickRestType type : EnumSickRestType.values()) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", type.getType());
            map.put("name", type.getDesc());
            result.add(map);
        }
        return result;
    }
    
}
