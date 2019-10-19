package com.bdtd.card.registration.modular.treatment.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.system.model.PatientPrescriptionMedicineInfo;

/**
 * <p>
 * 处方药详情表 服务类
 * </p>
 *
 * @author lilei123
 * @since 2018-06-26
 */
public interface IPatientPrescriptionMedicineInfoService extends IService<PatientPrescriptionMedicineInfo> {

    long countByMap(Map<String, Object> map);

    List<Map<String, Object>> findByMap(Map<String, Object> map);

    List<Map<String, Object>> findByPatientInfoId(Integer patientInfoId);

}
