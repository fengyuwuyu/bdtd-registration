package com.bdtd.card.registration.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum EnumFeverStatus {

    PRE(1, "待住院"), IN_HOSPITAL(4, "住院"), OUT_HOSPITAL(9, "出院"), OTHER(99, "其他"),
    ;
    
    private int type;
    private String desc;
    private EnumFeverStatus(int type, String desc) {
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
        for (EnumFeverStatus storageType : EnumFeverStatus.values()) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", storageType.getType());
            map.put("name", storageType.getDesc());
            result.add(map);
        }
        return result;
    }
    
}
