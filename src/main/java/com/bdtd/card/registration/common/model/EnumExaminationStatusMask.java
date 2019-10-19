package com.bdtd.card.registration.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum EnumExaminationStatusMask {

    COMMON(1, "一般检查"), SURGERY(1 << 1, "外科"), INTERNAL(1 << 2, "内科"), B_ULTRASONIC(1 << 3, "B超"), ASSAY(1 << 4,
            "化验"), OPHTHALMOLOGY(1 << 5, "眼科"), ENT(1 << 6, "耳鼻喉科"), G_O(1 << 7, "妇产科"), STOMATOLOGY(1 << 8, "口腔科");

    private int type;
    private String desc;

    private EnumExaminationStatusMask(int type, String desc) {
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
        for (EnumExaminationStatusMask mask : EnumExaminationStatusMask.values()) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", mask.getType());
            map.put("name", mask.getDesc());
            result.add(map);
        }
        return result;
    }

}
