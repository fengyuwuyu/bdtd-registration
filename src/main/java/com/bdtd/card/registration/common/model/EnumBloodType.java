package com.bdtd.card.registration.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum EnumBloodType {

    A(1, "A型"), 
    B(2, "B型"), 
    AB(3, "AB型"), 
    O(4, "O型"), 
    ;
    
    private int type;
    private String desc;
    
    private EnumBloodType(int type, String desc) {
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

    public static List<Map<String, Object>> select() {
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        for (EnumBloodType mask : EnumBloodType.values()) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", mask.getType());
            map.put("name", mask.getDesc());
            result.add(map);
        }
        return result;
    }
    
}
