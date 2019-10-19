package com.bdtd.card.registration.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum EnumInhospitalTakeMedicalStatus {
    
    CREATED(1, "未处方"), PRESCRIPTIONED(2, "已处方"), HAS_BILLING(3, "已开单"), HAS_TAKE_MEDICINE(4, "已取药")
    ; 

    private int type;
    private String desc;

    private EnumInhospitalTakeMedicalStatus(int type, String desc) {
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
        List<Map<String, Object>> result = new ArrayList<>(EnumInhospitalTakeMedicalStatus.values().length);
        for (EnumInhospitalTakeMedicalStatus type : EnumInhospitalTakeMedicalStatus.values()) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", type.getType());
            map.put("name", type.getDesc());
            result.add(map);
        }
        return result;
    }

}
