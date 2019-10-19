package com.bdtd.card.registration.modular.inhospital.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.bdtd.card.registration.common.model.chart.CylinderShapeEntity;
import com.stylefeng.guns.modular.system.model.PatientInHospital;

/**
 * <p>
 * 住院 服务类
 * </p>
 *
 * @author lilei123
 * @since 2018-06-26
 */
public interface IPatientInHospitalService extends IService<PatientInHospital> {

    List<Map<String, Object>> findByMap(Map<String, Object> params);

    long countByMap(Map<String, Object> params);

    Map<String, Object> findById(Integer id);

    long countInhospitalByMap(Map<String, Object> params);

    List<Map<String, Object>> findInhospitalByMap(Map<String, Object> params);

    CylinderShapeEntity<Long> inhospitalChart(Map<String, Object> param);

    Map<String, Object> monthlyWoundChart(String beginDate, String endDate);

    Map<String, Object> woundAbsentChart();

}
