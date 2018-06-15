package com.bdtd.card.registration.modular.system.dao;

import com.bdtd.card.registration.modular.system.model.MedicalInventorySecondLevel;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 药品二级库存管理 Mapper 接口
 * </p>
 *
 * @author lilei123
 * @since 2018-06-14
 */
public interface MedicalInventorySecondLevelMapper extends BaseMapper<MedicalInventorySecondLevel> {

	void storage(@Param(value = "id") Integer id, @Param(value = "count") Long count);

}
