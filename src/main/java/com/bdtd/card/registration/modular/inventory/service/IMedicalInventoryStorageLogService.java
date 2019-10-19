package com.bdtd.card.registration.modular.inventory.service;

import com.baomidou.mybatisplus.service.IService;
import com.bdtd.card.registration.common.model.EnumMedicalInventoryCategory;
import com.stylefeng.guns.modular.system.model.MedicalInventoryStorageLog;

/**
 * <p>
 * 出入库记录 服务类
 * </p>
 *
 * @author lilei123
 * @since 2018-06-28
 */
public interface IMedicalInventoryStorageLogService extends IService<MedicalInventoryStorageLog> {

    void logStorage(Integer id, Integer count, EnumMedicalInventoryCategory category, String orgName);

}
