package com.bdtd.card.registration.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 评残等级
 * 
 * @author lilei
 *
 */
public enum EnumDisabilityRateResult {

    ONE(1, "一级"), TWO(2, "二级"), THREE(3, "三级"), FOUR(4, "四级"), FIVE(5, "五级"), SIX(6, "六级"), SEVEN(7, "七级"), EIGHT(8, "八级"), NINE(9,
            "九级"), TEN(10, "十级"), NO_PASS(11, "未通过"), HEALTH_RETIREMENT(12, "病退");
    private int type;
    private String desc;

    private EnumDisabilityRateResult(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    private static Map<Integer, EnumDisabilityRateResult> map = new HashMap<>(EnumDisabilityRateResult.values().length);
    
    static {
        for (EnumDisabilityRateResult type : EnumDisabilityRateResult.values()) {
            map.put(type.getType(), type);
        }
    }
    
    public static EnumDisabilityRateResult fromType(Integer type) {
        return map.get(type);
    }


    public static List<Map<String, Object>> select() {
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        for (EnumDisabilityRateResult mask : EnumDisabilityRateResult.values()) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", mask.getType());
            map.put("name", mask.getDesc());
            result.add(map);
        }
        return result;
    }
}
