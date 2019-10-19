package com.bdtd.card.registration.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 评残性质
 * @author lilei
 *
 */
public enum EnumCrippleNature {

    CAUSE_SICK(1, "因病"), 
    CAUSE_(2, "因公"), 
    ;
    
    private int type;
    private String desc;
    
    private EnumCrippleNature(int type, String desc) {
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
        for (EnumCrippleNature mask : EnumCrippleNature.values()) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", mask.getType());
            map.put("name", mask.getDesc());
            result.add(map);
        }
        return result;
    }
    
}
