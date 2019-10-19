package com.bdtd.card.registration.modular.inventory.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.bdtd.card.registration.common.model.EnumMedicalInventoryCategory;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryDrugStorageService;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryStorageLogService;
import com.stylefeng.guns.modular.system.dao.MedicalInventoryDrugStorageMapper;
import com.stylefeng.guns.modular.system.model.MedicalInventoryDrugStorage;

/**
 * <p>
 * 药库管理 服务实现类
 * </p>
 *
 * @author lilei123
 * @since 2018-06-28
 */
@Service
public class MedicalInventoryDrugStorageServiceImpl extends ServiceImpl<MedicalInventoryDrugStorageMapper, MedicalInventoryDrugStorage> implements IMedicalInventoryDrugStorageService {
    
    @Autowired
    private IMedicalInventoryStorageLogService storageLogService;

    @Override
    public List<Map<String, Object>> selectPagedMaps(Map<String, Object> paramMap) {
        return this.baseMapper.selectPagedMaps(paramMap);
    }

    @Override
    public long selectCountMaps(Map<String, Object> paramMap) {
        return this.baseMapper.selectCountMaps(paramMap);
    }

    @Override
    public synchronized int updateInventoryCount(Integer id, Integer count, String orgName) {
        int modifyCount = this.baseMapper.updateInventoryCount(id, count);
        if (modifyCount > 0) {
            storageLogService.logStorage(id, count, EnumMedicalInventoryCategory.DRUG_STORAGE, orgName);
        }
        return modifyCount;
    }

}
