package com.bdtd.card.registration.modular.alert.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.system.model.AlertConfig;

/**
 * <p>
 * 报警配置 服务类
 * </p>
 *
 * @author lilei123
 * @since 2018-10-29
 */
public interface IAlertConfigService extends IService<AlertConfig> {

    AlertConfig getConfig();
    
    void setConfig(AlertConfig alertConfig);
}
