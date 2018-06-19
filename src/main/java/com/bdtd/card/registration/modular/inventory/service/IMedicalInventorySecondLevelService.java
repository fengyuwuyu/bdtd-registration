package com.bdtd.card.registration.modular.inventory.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.system.model.MedicalInventorySecondLevel;

/**
 * <p>
 * 药品二级库存管理 服务类
 * </p>
 *
 * @author lilei123
 * @since 2018-06-14
 */
public interface IMedicalInventorySecondLevelService extends IService<MedicalInventorySecondLevel> {

	void storage(Integer id, Long count);

}
