package com.bdtd.card.registration.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum EnumPatientTransferStatus {

    TRANSFERING(1, "待处理"), BILLED(2, "已开单"), COLLECTED(3, "已汇总"), REPORTED(4, "已回报")
    ;
    
    private int type;
    private String desc;
    private EnumPatientTransferStatus(int type, String desc) {
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
        List<Map<String, Object>> result = new ArrayList<>(EnumPatientTransferStatus.values().length);
        for (EnumPatientTransferStatus type : EnumPatientTransferStatus.values()) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", type.getType());
            map.put("name", type.getDesc());
            result.add(map);
        }
        return result;
    }

}
