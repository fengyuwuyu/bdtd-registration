package com.bdtd.card.registration.modular.inventory.service;

import com.bdtd.card.registration.modular.system.model.MedicalInventorySecondLevel;
import com.baomidou.mybatisplus.service.IService;

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
