package com.bdtd.card.registration.modular.treatment.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.bdtd.card.registration.modular.treatment.service.IPatientPrescriptionMedicineInfoService;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.modular.system.dao.PatientPrescriptionMedicineInfoMapper;
import com.stylefeng.guns.modular.system.model.PatientPrescriptionMedicineInfo;

/**
 * <p>
 * 处方药详情表 服务实现类
 * </p>
 *
 * @author lilei123
 * @since 2018-06-26
 */
@Service
public class PatientPrescriptionMedicineInfoServiceImpl extends ServiceImpl<PatientPrescriptionMedicineInfoMapper, PatientPrescriptionMedicineInfo> implements IPatientPrescriptionMedicineInfoService {

    @Override
    public long countByMap(Map<String, Object> map) {
        return this.baseMapper.countByMap(map);
    }

    @Override
    public List<Map<String, Object>> findByMap(Map<String, Object> map) {
        return this.baseMapper.findByMap(map);
    }

    @Override
    public List<Map<String, Object>> findByPatientInfoId(Integer patientInfoId) {
        List<PatientPrescriptionMedicineInfo> list = selectByMap(MapUtil.createMap("patient_info_id", patientInfoId));
        if (list == null || list.size() == 0) {
            return Collections.emptyList();
        }
        
        return list.stream().map((medicineInfo)-> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", medicineInfo.getMedicineId());
            map.put("name", medicineInfo.getMedicalName());
            return map;
        }).collect(Collectors.toList());
    }

}
