package com.bdtd.card.registration.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public enum EnumMedicalInventoryStorageType {

	IN_DRUG_STORAGE(1, "入药库"), IN_PYARMACY(2, "入药房"), OUT_PYARMACY(3, "出药房");

	private Integer type;
	private String desc;

	private EnumMedicalInventoryStorageType(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public Integer getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}

	public static List<Map<String, Object>> select() {
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        for (EnumMedicalInventoryStorageType storageType : EnumMedicalInventoryStorageType.values()) {
            Map<String, Object> map = new TreeMap<>();
            map.put("id", storageType.getType());
            map.put("name", storageType.getDesc());
            result.add(map);
        }
        return result;
    }
}
