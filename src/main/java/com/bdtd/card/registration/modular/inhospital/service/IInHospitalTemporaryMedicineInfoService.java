package com.bdtd.card.registration.modular.inhospital.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.bdtd.card.registration.common.model.EnumInhospitalTakeMedicalStatus;
import com.stylefeng.guns.modular.system.model.InHospitalTemporaryMedicineInfo;

/**
 * <p>
 * 临时医嘱药详情表 服务类
 * </p>
 *
 * @author lilei123
 * @since 2018-07-05
 */
public interface IInHospitalTemporaryMedicineInfoService extends IService<InHospitalTemporaryMedicineInfo> {

    void updatePrescription(Long[] ids, Integer inhospitalTakeMedicalId, EnumInhospitalTakeMedicalStatus inhospitalTakeMedicalStatus);

    void deletePrescription(Map<String, Object> params);

    List<Map<String, Object>> findMaps(Map<String, Object> params);

}
