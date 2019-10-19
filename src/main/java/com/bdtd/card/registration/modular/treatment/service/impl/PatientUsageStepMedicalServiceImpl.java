package com.bdtd.card.registration.modular.treatment.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.bdtd.card.registration.modular.treatment.service.IPatientUsageStepMedicalService;
import com.stylefeng.guns.modular.system.dao.PatientUsageStepMedicalMapper;
import com.stylefeng.guns.modular.system.model.PatientUsageStepMedical;

/**
 * <p>
 * 处方使用步骤药品详情表 服务实现类
 * </p>
 *
 * @author lilei123
 * @since 2018-07-01
 */
@Service
public class PatientUsageStepMedicalServiceImpl extends ServiceImpl<PatientUsageStepMedicalMapper, PatientUsageStepMedical> implements IPatientUsageStepMedicalService {

    @Override
    public List<Map<String, Object>> findByMaps(Map<String, Object> params) {
        return this.baseMapper.findByMaps(params);
    }

}
