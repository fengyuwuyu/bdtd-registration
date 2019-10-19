package com.bdtd.card.registration.common.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum EnumPatientInfoStatus {
    
    REGISTRATION(1, "挂号"), DIAGNOSISING(2, "诊断中"), DIAGNOSISED(3, "未取药"), HAS_TAKE_MEDICINE(4, "已取药")
    ; 

    private int type;
    private String desc;

    private EnumPatientInfoStatus(int type, String desc) {
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
        List<Map<String, Object>> result = new ArrayList<>(EnumPatientInfoStatus.values().length);
        for (EnumPatientInfoStatus type : EnumPatientInfoStatus.values()) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", type.getType());
            map.put("name", type.getDesc());
            result.add(map);
        }
        return result;
    }
    

    
    public static List<Map<String, Object>> select2() {
        List<Map<String, Object>> result = new ArrayList<>(EnumPatientInfoStatus.values().length);
        for (EnumPatientInfoStatus type : Arrays.asList(DIAGNOSISED, HAS_TAKE_MEDICINE)) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", type.getType());
            map.put("name", type.getDesc());
            result.add(map);
        }
        return result;
    }

}
