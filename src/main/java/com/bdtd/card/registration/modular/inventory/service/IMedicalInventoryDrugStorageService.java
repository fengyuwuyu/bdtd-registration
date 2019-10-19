package com.bdtd.card.registration.modular.inventory.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.system.model.MedicalInventoryDrugStorage;

/**
 * <p>
 * 药库管理 服务类
 * </p>
 *
 * @author lilei123
 * @since 2018-06-28
 */
public interface IMedicalInventoryDrugStorageService extends IService<MedicalInventoryDrugStorage> {

    List<Map<String, Object>> selectPagedMaps(Map<String, Object> paramMap);

    long selectCountMaps(Map<String, Object> paramMap);

    int updateInventoryCount(Integer id, Integer count, String orgName);

}
