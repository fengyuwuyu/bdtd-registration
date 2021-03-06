package com.bdtd.card.registration.modular.inhospital.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.bdtd.card.registration.common.model.EnumInhospitalTakeMedicalStatus;
import com.bdtd.card.registration.modular.inhospital.service.IInHospitalLongtermMedicineInfoService;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.modular.system.dao.InHospitalLongtermMedicineInfoMapper;
import com.stylefeng.guns.modular.system.model.InHospitalLongtermMedicineInfo;

/**
 * <p>
 * 长期医嘱药详情表 服务实现类
 * </p>
 *
 * @author lilei123
 * @since 2018-07-05
 */
@Service
public class InHospitalLongtermMedicineInfoServiceImpl extends ServiceImpl<InHospitalLongtermMedicineInfoMapper, InHospitalLongtermMedicineInfo> implements IInHospitalLongtermMedicineInfoService {

    @Override
    public void updatePrescription(Long[] ids, Integer inhospitalTakeMedicalId,
            EnumInhospitalTakeMedicalStatus inhospitalTakeMedicalStatus) {
        this.baseMapper.updatePrescription(MapUtil.createMap("ids", ids, "inhospitalTakeMedicalId", inhospitalTakeMedicalId, "status", inhospitalTakeMedicalStatus.getType()));
    }

    @Override
    public void deletePrescription(Map<String, Object> params) {
        this.baseMapper.deletePrescription(params);
    }

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> params) {
        return this.baseMapper.findMaps(params);
    }

}
