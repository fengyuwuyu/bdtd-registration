package com.bdtd.card.registration.modular.transfer.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.bdtd.card.registration.common.model.chart.CylinderShapeEntity;
import com.stylefeng.guns.modular.system.model.PatientTransferTreatment;

/**
 * <p>
 * 转诊 服务类
 * </p>
 *
 * @author lilei123
 * @since 2018-06-26
 */
public interface IPatientTransferTreatmentService extends IService<PatientTransferTreatment> {

    List<Map<String, Object>> findByMap(Map<String, Object> createMap);

    long countByMap(Map<String, Object> createMap);

    int updateByMap(Map<String, Object> createMap);

    int toPreTransferStatus(Map<String, Object> createMap);

    CylinderShapeEntity<Integer> transferChart(Map<String, Object> param);

    int countByPatientInfoId(Map<String, Object> param);

}
