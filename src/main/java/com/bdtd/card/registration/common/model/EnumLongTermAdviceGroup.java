package com.bdtd.card.registration.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum EnumLongTermAdviceGroup {

    ONE(1, "1"), TWO(2, "2"), THREE(3, "3"), FOUR(4, "4"), FIVE(5, "5");

    private int type;
    private String desc;

    private EnumLongTermAdviceGroup(int type, String desc) {
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
        for (EnumLongTermAdviceGroup storageType : EnumLongTermAdviceGroup.values()) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", storageType.getType());
            map.put("name", storageType.getDesc());
            result.add(map);
        }
        return result;
    }
}
