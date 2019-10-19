package com.bdtd.card.registration.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum EnumMedicalInventoryCategory {

    DRUG_STORAGE(1, "药库"), PHARMACY(2, "药房")
    ;
    
    private int category;
    private String desc;
    private EnumMedicalInventoryCategory(int category, String desc) {
        this.category = category;
        this.desc = desc;
    }
    public int getCategory() {
        return category;
    }
    public String getDesc() {
        return desc;
    }
    
    public static List<Map<String, Object>> select() {
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        for (EnumMedicalInventoryCategory storageType : EnumMedicalInventoryCategory.values()) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", storageType.getCategory());
            map.put("name", storageType.getDesc());
            result.add(map);
        }
        return result;
    }
    
}
