package com.bdtd.card.registration.modular.alert.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.bdtd.card.registration.cache.AlertDepCache;
import com.bdtd.card.registration.cache.DepCache;
import com.bdtd.card.registration.common.model.EnumHandleTypeMask;
import com.bdtd.card.registration.common.model.EnumPatientInfoStatus;
import com.bdtd.card.registration.common.utils.EnumAdapterFactory;
import com.bdtd.card.registration.common.utils.model.EnumAdapterEntity;
import com.bdtd.card.registration.modular.alert.model.AlertFever;
import com.bdtd.card.registration.modular.alert.service.IAlertConfigService;
import com.bdtd.card.registration.modular.alert.service.IAlertFeverService;
import com.bdtd.card.registration.modular.treatment.service.IPatientInfoService;
import com.stylefeng.guns.core.model.EnumOriginMask;
import com.stylefeng.guns.core.util.CommonUtils;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.modular.system.model.AlertConfig;
import com.stylefeng.guns.modular.system.model.AlertDep;
import com.stylefeng.guns.modular.system.model.PatientInfo;

@Service
public class AlertFeverServiceImpl implements IAlertFeverService {
    
    @Autowired
    private IPatientInfoService patientInfoService;
    @Autowired
    private IAlertConfigService alertConfigService;

    @Override
    public Map<String, Object> list(Long depSerial, Integer offset, Integer limit) {
        AlertConfig alertConfig = this.alertConfigService.getConfig();
        List<AlertFever> result = new ArrayList<>();
        
        List<AlertDep> alertDeps = null;
        if (depSerial != null) {
            alertDeps = Arrays.asList(AlertDepCache.getCache(depSerial));
        } else {
            alertDeps = AlertDepCache.getCache();
        }
        
        alertDeps.forEach((alertDep) -> {
            AlertFever alertFever = getAlertFeverByDepSerial(alertDep, alertConfig);
            if (alertFever != null) {
                result.add(alertFever);
            }
        });
        return MapUtil.createSuccessMap("total", result.size(), "rows", result);
    }

    @Override
    public AlertFever getAlertFeverByDepSerial(AlertDep alertDep, AlertConfig alertConfig) {
        Set<Long> depSerials = DepCache.getChildren(alertDep.getDepSerial());
        Wrapper<PatientInfo> wrapper = new EntityWrapper<>();
        wrapper.in("org_id", depSerials);
        Date[] queryDate = dealQueryTimeSection(wrapper, alertConfig.getDay());
        wrapper.eq("fever", EnumOriginMask.YES.getType());
        wrapper.gt("status", EnumPatientInfoStatus.DIAGNOSISING.getType());
        int count = patientInfoService.selectCount(wrapper);
        if (count >= alertConfig.getCount()) {
            return new AlertFever(alertDep.getDepSerial(), alertDep.getDepName(), count, new java.sql.Date(queryDate[0].getTime()), new java.sql.Date(queryDate[1].getTime()));
        }
        return null;
    }

    @Override
    public Map<String, Object> feverDetail(Long depSerial, String userName, Date beginDate, Date endDate, Integer offset, Integer limit) {
        AlertConfig alertConfig = this.alertConfigService.getConfig();
        
        Wrapper<PatientInfo> wrapper = new EntityWrapper<>();
        Set<Long> depSerials = DepCache.getChildren(depSerial);
        if (beginDate == null && endDate == null) {
            dealQueryTimeSection(wrapper, alertConfig.getDay());
        }
        wrapper.in("org_id", depSerials);
        wrapper.eq("fever", EnumOriginMask.YES.getType());
        wrapper.gt("status", EnumPatientInfoStatus.DIAGNOSISING.getType());
        if (beginDate != null) {
            wrapper.ge("clinic_date", beginDate);
        }
        if (endDate != null) {
            wrapper.le("clinic_date", endDate);
        }
        if (!StringUtil.isNullEmpty(userName)) {
            wrapper.like("user_name", userName);
        }
        List<Map<String, Object>> result = patientInfoService.selectMaps(wrapper);
        result = CommonUtils.localPagination(result, offset, limit);
        

        result.forEach((item) -> {
            Long handleTypeVal = (Long) item.get("handleType");
            if ((handleTypeVal & EnumHandleTypeMask.IN_HOSPITAL.getType()) == EnumHandleTypeMask.IN_HOSPITAL.getType()) {
                item.put("inhospital", "是");
            } else {
                item.put("inhospital", "否");
            }

            if ((handleTypeVal & EnumHandleTypeMask.SICK_REST.getType()) == EnumHandleTypeMask.SICK_REST.getType()) {
                item.put("sickRest", "是");
            } else {
                item.put("sickRest", "否");
            }

            if ((handleTypeVal & EnumHandleTypeMask.TRANSFER_TREATMENT.getType()) == EnumHandleTypeMask.TRANSFER_TREATMENT.getType()) {
                item.put("transfer", "是");
            } else {
                item.put("transfer", "否");
            }
        });
        
        List<EnumAdapterEntity> enumAdapters = Arrays.asList(
                new EnumAdapterEntity("handleType", "EnumHandleTypeMask", (item) -> {
                    return EnumHandleTypeMask.getDescs((Long)item);
                }),
                new EnumAdapterEntity("trainWound", "EnumOriginMask"),
                new EnumAdapterEntity("fever", "EnumOriginMask")
                );
        EnumAdapterFactory.adapterRows(result, enumAdapters);
        
        return MapUtil.createSuccessMap("total", result.size(), "rows", result);
    }
    
    private Date[] dealQueryTimeSection(Wrapper<PatientInfo> wrapper, Integer day) {
        Date endDate = new Date();
        Date beginDate = DateUtil.getAfterDayDate(endDate, -(day - 1));
        beginDate = DateUtil.getOneDayBegin(beginDate);
        endDate = DateUtil.getOneDayEnd(endDate);
        wrapper.between("clinic_date", beginDate, endDate);
        return new Date[] {beginDate, endDate};
    }

    @Override
    public AlertDep getByChildDepSerial(Long depSerial) {
        List<AlertDep> alertDeps = AlertDepCache.getCache();
        for (AlertDep alertDep : alertDeps) {
            if (depSerial.longValue() == alertDep.getDepSerial().longValue()) {
                return alertDep;
            }
            
            Set<Long> depSerials = DepCache.getChildren(alertDep.getDepSerial());
            if (depSerials.contains(depSerial)) {
                return alertDep;
            }
        }
        return null;
    }

}
