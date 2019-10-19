package com.bdtd.card.registration.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum EnumSickRestDay {

    ONE(1, "1"),  TWO(2, "2"),
    THREE(3, "3"),  FOUR(4, "4"),
    FIVE(5, "5"),  SIX(6, "6"),
    SEVEN(7, "7"),  
    ;
    
    private int type;
    private String desc;
    
    private EnumSickRestDay(int type, String desc) {
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
        List<Map<String, Object>> result = new ArrayList<>(EnumSickRestDay.values().length);
        for (EnumSickRestDay type : EnumSickRestDay.values()) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", type.getType());
            map.put("name", type.getDesc());
            result.add(map);
        }
        return result;
    }
    
}
