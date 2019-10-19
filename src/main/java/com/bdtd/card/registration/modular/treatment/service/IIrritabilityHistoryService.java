package com.bdtd.card.registration.modular.treatment.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.system.model.IrritabilityHistory;

/**
 * <p>
 * 过敏史 服务类
 * </p>
 *
 * @author lilei123
 * @since 2018-08-03
 */
public interface IIrritabilityHistoryService extends IService<IrritabilityHistory> {

    void saveOrUpdate(String userNo, String irritabilityHistory);
}
