package com.bdtd.card.registration.modular.examination.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.system.model.ExaminationHealth;

/**
 * <p>
 * 体检信息 服务类
 * </p>
 *
 * @author lilei123
 * @since 2018-07-10
 */
public interface IExaminationHealthService extends IService<ExaminationHealth> {

    Map<String, Object> findMapById(Integer examinationHealthId);

}
