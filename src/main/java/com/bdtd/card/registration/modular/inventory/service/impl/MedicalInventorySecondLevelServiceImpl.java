package com.bdtd.card.registration.modular.inventory.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventorySecondLevelService;
import com.stylefeng.guns.modular.system.dao.MedicalInventorySecondLevelMapper;
import com.stylefeng.guns.modular.system.model.MedicalInventorySecondLevel;

/**
 * <p>
 * 药品二级库存管理 服务实现类
 * </p>
 *
 * @author lilei123
 * @since 2018-06-14
 */
@Service
@Transactional
public class MedicalInventorySecondLevelServiceImpl extends ServiceImpl<MedicalInventorySecondLevelMapper, MedicalInventorySecondLevel> implements IMedicalInventorySecondLevelService {

	@Resource
	private MedicalInventorySecondLevelMapper medicalInventorySecondLevelMapper;
	
	public void storage(Integer id, Long count) {
		this.baseMapper.selectById(id);
		medicalInventorySecondLevelMapper.storage(id, count);
	}

}
