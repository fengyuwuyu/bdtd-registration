package com.bdtd.card.registration.common.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum EnumHandleTypeMask {

    PRESCRIPTION(1L << 0, "处方"), DISPOSE(1L << 1, "门诊处置"), RADIATE_EXAMINE(1L << 2, "放射检查"), IN_HOSPITAL(1L << 3, "住院"), 
    TRANSFER_TREATMENT(1L << 4, "转诊"), SICK_REST(1L << 5, "病休"), PHYSICAL_THERAPY(1L << 6, "理疗"), HAS_IN_HOSPITAL(1L << 7, "已住院");

    private long type;
    private String desc;

    private EnumHandleTypeMask(long type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public long getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
    
    public static String getDescs(Long handleType) {
        if (handleType == null) {
            return null;
        }
        
        StringBuilder sb = new StringBuilder();
        for (EnumHandleTypeMask type : EnumHandleTypeMask.values()) {
            if ((handleType & type.getType()) == type.getType()) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(type.getDesc());
            }
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        System.out.println(EnumHandleTypeMask.getDescs(127L));;
    }
    
    public static List<Map<String, Object>> select() {
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        for (EnumHandleTypeMask mask : EnumHandleTypeMask.values()) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", mask.getType());
            map.put("name", mask.getDesc());
            result.add(map);
        }
        return result;
    }
    
    public static List<Map<String, Object>> select2() {
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        List<EnumHandleTypeMask> masks = Arrays.asList(IN_HOSPITAL, TRANSFER_TREATMENT, SICK_REST);
        for (EnumHandleTypeMask mask : masks) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", mask.getType());
            map.put("name", mask.getDesc());
            result.add(map);
        }
        return result;
    }

}
