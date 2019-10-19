package com.bdtd.card.registration.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum EnumPrescriptionStatus {
    
    INIT(0, "未取药"), TAKE_MEDICINE(1, "已取药")
    ;

    private int type;
    private String desc;

    private EnumPrescriptionStatus(int type, String desc) {
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
        List<Map<String, Object>> result = new ArrayList<>(EnumPrescriptionStatus.values().length);
        for (EnumPrescriptionStatus type : EnumPrescriptionStatus.values()) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", type.getType());
            map.put("name", type.getDesc());
            result.add(map);
        }
        return result;
    }


}
