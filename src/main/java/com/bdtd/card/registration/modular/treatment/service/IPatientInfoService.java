package com.bdtd.card.registration.modular.treatment.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.bdtd.card.registration.common.model.EnumHandleTypeMask;
import com.bdtd.card.registration.common.model.chart.CylinderShapeEntity;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.modular.system.model.PatientInfo;

/**
 * <p>
 * 病患信息 服务类
 * </p>
 *
 * @author lilei123
 * @since 2018-06-26
 */
public interface IPatientInfoService extends IService<PatientInfo> {

    void updateHandleType(Integer id, EnumHandleTypeMask handleType);

    void updateHandleType(Integer id, Long handleType);

    int findByInTreatmentCount(String userNo);

    Tip cancelHandleType(Integer id, EnumHandleTypeMask handleType);
    
    Tip cancelHandleType(Integer id, Long handleType);

    List<PatientInfo> findCanInHospital(String userName);
    
    List<Map<String, Object>> findCanInHospitalMap(String userName);

    int cancelHandleType(List<Integer> patientInfoIdList, EnumHandleTypeMask inHospital);
    
    Tip canRegistration(String userNo);

    CylinderShapeEntity<Integer> trainWoundChart(Map<String, Object> param);

    CylinderShapeEntity<Integer> feverChart(Map<String, Object> param);

    long countByTimeBetween(java.sql.Date beginDate, java.sql.Date endDate);
    
    /**
     * 返回最后一个被叫号的人
     * @return
     */
    PatientInfo getCurrPatient();
    
    void setCurrPatient(PatientInfo patientInfo);

    int resetStatus(Integer id);

    long total(Map<String, Object> params);

    List<Map<String, Object>> findMaps(Map<String, Object> params);

    CylinderShapeEntity<Integer> patientInfoChart(Map<String, Object> params);

}
