package com.bdtd.card.registration.modular.alert.service;

import java.util.Date;
import java.util.Map;

import com.bdtd.card.registration.modular.alert.model.AlertFever;
import com.stylefeng.guns.modular.system.model.AlertConfig;
import com.stylefeng.guns.modular.system.model.AlertDep;

public interface IAlertFeverService {

    Map<String, Object> list(Long depSerial, Integer offset, Integer limit);
    
    Map<String, Object> feverDetail(Long depSerial, String userName, Date beginDate, Date endDate, Integer offset, Integer limit);
    
    AlertFever getAlertFeverByDepSerial(AlertDep alertDep, AlertConfig alertConfig);
    
    AlertDep getByChildDepSerial(Long depSerial);
}
