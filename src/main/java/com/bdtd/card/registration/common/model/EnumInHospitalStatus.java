package com.bdtd.card.registration.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum EnumInHospitalStatus {

    PRE(1, "待住院"), IN_HOSPITAL(4, "住院中"), OUT_HOSPITAL(9, "已出院"),
    ;
    
    private int type;
    private String desc;
    private EnumInHospitalStatus(int type, String desc) {
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
        for (EnumInHospitalStatus storageType : EnumInHospitalStatus.values()) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", storageType.getType());
            map.put("name", storageType.getDesc());
            result.add(map);
        }
        return result;
    }
    
    public static List<Map<String, Object>> select2() {
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        for (EnumInHospitalStatus status : EnumInHospitalStatus.values()) {
            if (status == PRE) {
                continue;
            }
            Map<String, Object> map = new TreeMap<>();
            map.put("id", status.getType());
            map.put("name", status.getDesc());
            result.add(map);
        }
        return result;
    }
    
}
