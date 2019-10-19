package com.bdtd.card.registration.modular.examination.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.bdtd.card.registration.modular.examination.service.IExaminationHealthService;
import com.stylefeng.guns.modular.system.dao.ExaminationHealthMapper;
import com.stylefeng.guns.modular.system.model.ExaminationHealth;

/**
 * <p>
 * 体检信息 服务实现类
 * </p>
 *
 * @author lilei123
 * @since 2018-07-10
 */
@Service
public class ExaminationHealthServiceImpl extends ServiceImpl<ExaminationHealthMapper, ExaminationHealth> implements IExaminationHealthService {

    @Override
    public Map<String, Object> findMapById(Integer examinationHealthId) {
        return this.baseMapper.findMapById(examinationHealthId);
    }

}
