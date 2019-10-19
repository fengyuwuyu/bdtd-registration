package com.bdtd.card.registration.modular.inhospital.service;

import com.baomidou.mybatisplus.service.IService;
import com.bdtd.card.registration.common.model.chart.CylinderShapeEntity;
import com.stylefeng.guns.modular.system.model.InHospitalTemperatureRecord;

/**
 * <p>
 * 体温记录 服务类
 * </p>
 *
 * @author lilei123
 * @since 2018-07-08
 */
public interface IInHospitalTemperatureRecordService extends IService<InHospitalTemperatureRecord> {

    CylinderShapeEntity<Float> chartList(Integer inHospitalId);

}
