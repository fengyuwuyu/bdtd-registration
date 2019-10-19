package com.bdtd.card.registration.modular.treatment.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.system.model.PatientUsageStepMedical;

/**
 * <p>
 * 处方使用步骤药品详情表 服务类
 * </p>
 *
 * @author lilei123
 * @since 2018-07-01
 */
public interface IPatientUsageStepMedicalService extends IService<PatientUsageStepMedical> {

    List<Map<String, Object>> findByMaps(Map<String, Object> params);

}
