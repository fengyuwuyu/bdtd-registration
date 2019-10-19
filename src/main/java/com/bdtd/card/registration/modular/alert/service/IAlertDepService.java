package com.bdtd.card.registration.modular.alert.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.system.model.AlertDep;

/**
 * <p>
 * 报警部门 服务类
 * </p>
 *
 * @author lilei123
 * @since 2018-10-29
 */
public interface IAlertDepService extends IService<AlertDep> {
    List<Map<String, Object>> select();
}
