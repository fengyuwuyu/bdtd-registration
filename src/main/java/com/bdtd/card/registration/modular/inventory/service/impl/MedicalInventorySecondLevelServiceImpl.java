package com.bdtd.card.registration.modular.inventory.service.impl;

import com.bdtd.card.registration.modular.system.model.MedicalInventorySecondLevel;
import com.bdtd.card.registration.modular.system.dao.MedicalInventorySecondLevelMapper;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventorySecondLevelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 药品二级库存管理 服务实现类
 * </p>
 *
 * @author lilei123
 * @since 2018-06-14
 */
@Service
public class MedicalInventorySecondLevelServiceImpl extends ServiceImpl<MedicalInventorySecondLevelMapper, MedicalInventorySecondLevel> implements IMedicalInventorySecondLevelService {

	@Autowired
	private MedicalInventorySecondLevelMapper medicalInventorySecondLevelMapper;
	
	@Override
	public void storage(Integer id, Long count) {
		medicalInventorySecondLevelMapper.storage(id, count);
	}

}
