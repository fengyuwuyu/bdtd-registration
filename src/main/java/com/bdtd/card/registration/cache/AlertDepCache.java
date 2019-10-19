package com.bdtd.card.registration.cache;

import java.util.List;

import com.stylefeng.guns.modular.system.dao.AlertDepMapper;
import com.stylefeng.guns.modular.system.model.AlertDep;

public class AlertDepCache {
    private static List<AlertDep> cache;

    public synchronized static void init(AlertDepMapper mapper) {
        if (cache == null) {
            cache = mapper.selectList(null);
        }
    }

    public static List<AlertDep> getCache() {
        return cache;
    }
    
    public static AlertDep getCache(Long depSerial) {
        for (AlertDep alertDep : cache) {
            if (alertDep.getDepSerial().longValue() == depSerial.longValue()) {
                return alertDep;
            }
        }
        return null;
    }
    
    public synchronized static void addCache(AlertDep alertDep) {
        cache.add(alertDep);
    }
    
    public synchronized static void updateCache(AlertDep alertDep) {
        for (AlertDep item : cache) {
            if (item.getId().intValue() == alertDep.getId().intValue()) {
                item.setDepSerial(alertDep.getDepSerial());
                item.setDepNo(alertDep.getDepNo());
                item.setDepName(alertDep.getDepName());
                item.setParentName(alertDep.getParentName());
                item.setCreateDate(alertDep.getCreateDate());
                item.setUpdateDate(alertDep.getUpdateDate());
                break;
            }
        }
    }

    public synchronized static void delete(Integer alertDepId) {
        int index = -1;
        for (int i = 0; i < cache.size(); i++) {
            if (cache.get(i).getId().intValue() == alertDepId.intValue()) {
                index = i;
                break;
            }
        }
        
        if (index != -1) {
            cache.remove(index);
        }
    }

}
