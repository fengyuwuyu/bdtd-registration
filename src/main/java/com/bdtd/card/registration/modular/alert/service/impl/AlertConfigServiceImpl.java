package com.bdtd.card.registration.modular.alert.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.bdtd.card.registration.modular.alert.service.IAlertConfigService;
import com.stylefeng.guns.modular.system.dao.AlertConfigMapper;
import com.stylefeng.guns.modular.system.model.AlertConfig;

/**
 * <p>
 * 报警配置 服务实现类
 * </p>
 *
 * @author lilei123
 * @since 2018-10-29
 */
@Service
public class AlertConfigServiceImpl extends ServiceImpl<AlertConfigMapper, AlertConfig> implements IAlertConfigService {
    
    private AlertConfig alertConfig;

    @Override
    public AlertConfig getConfig() {
        if (alertConfig == null) {
            alertConfig = this.selectById(1);
        }
        return alertConfig;
    }

    @Override
    public void setConfig(AlertConfig alertConfig) {
        this.alertConfig = alertConfig;
    }

}
