package com.bdtd.card.registration.modular.treatment.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.bdtd.card.registration.common.model.chart.CylinderShapeEntity;
import com.stylefeng.guns.modular.system.model.PatientSickRest;

/**
 * <p>
 * 病休管理 服务类
 * </p>
 *
 * @author lilei123
 * @since 2018-06-26
 */
public interface IPatientSickRestService extends IService<PatientSickRest> {

    long countByMap(Map<String, Object> params);

    List<Map<String, Object>> findByMap(Map<String, Object> params);

    CylinderShapeEntity<Integer> sickRestChart(Map<String, Object> param);

}
