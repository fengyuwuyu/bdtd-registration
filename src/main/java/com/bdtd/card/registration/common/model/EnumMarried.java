package com.bdtd.card.registration.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum EnumMarried {
    
    YES(1, "已婚"), NO(0, "未婚");

    private int type;
    private String desc;
    private EnumMarried(int type, String desc) {
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
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        for (EnumMarried storageType : EnumMarried.values()) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", storageType.getType());
            map.put("name", storageType.getDesc());
            result.add(map);
        }
        return result;
    }
    
}
