package com.bdtd.card.registration.modular.inventory.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.bdtd.card.registration.common.model.EnumMedicalInventoryCategory;
import com.bdtd.card.registration.common.model.EnumMedicalInventoryStorageType;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryDrugStorageService;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryPharmacyService;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryStairService;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryStorageLogService;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.system.dao.MedicalInventoryStorageLogMapper;
import com.stylefeng.guns.modular.system.model.MedicalInventoryDrugStorage;
import com.stylefeng.guns.modular.system.model.MedicalInventoryPharmacy;
import com.stylefeng.guns.modular.system.model.MedicalInventoryStair;
import com.stylefeng.guns.modular.system.model.MedicalInventoryStorageLog;

/**
 * <p>
 * 出入库记录 服务实现类
 * </p>
 *
 * @author lilei123
 * @since 2018-06-28
 */
@Service
public class MedicalInventoryStorageLogServiceImpl extends ServiceImpl<MedicalInventoryStorageLogMapper, MedicalInventoryStorageLog> implements IMedicalInventoryStorageLogService {
    
    @Autowired
    private IMedicalInventoryStairService medicalInventoryStairService;
    
    @Autowired
    private IMedicalInventoryDrugStorageService medicalInventoryDrugStorageService;
    
    @Autowired
    private IMedicalInventoryPharmacyService medicalInventoryPharmacyService;
    

    @Override
    public void logStorage(Integer id, Integer count, EnumMedicalInventoryCategory category, String orgName) {
        int parentId = 0;
        String produceBatchNum = null;
        Date produceDate = null;
        Date expireDate = null;
        Double price = null;
        Integer inboundChannel = null;
        if (category == EnumMedicalInventoryCategory.DRUG_STORAGE) {
            MedicalInventoryDrugStorage drugStorage = medicalInventoryDrugStorageService.selectById(id);
            parentId = drugStorage.getParentId();
            produceBatchNum = drugStorage.getProduceBatchNum();
            produceDate = drugStorage.getProduceDate();
            expireDate = drugStorage.getExpireDate();
            price = drugStorage.getPrice();
            inboundChannel = drugStorage.getInboundChannel();
            
        } else if (category == EnumMedicalInventoryCategory.PHARMACY) {
            MedicalInventoryPharmacy inventoryPharmacy = medicalInventoryPharmacyService.selectById(id);
            parentId = inventoryPharmacy.getParentId();
            produceBatchNum = inventoryPharmacy.getProduceBatchNum();
            produceDate = inventoryPharmacy.getProduceDate();
            expireDate = inventoryPharmacy.getExpireDate();
            price = inventoryPharmacy.getPrice();
            inboundChannel = inventoryPharmacy.getInboundChannel();
        }
        
        MedicalInventoryStair inventoryStair = medicalInventoryStairService.selectById(parentId);
        Integer medicalId = id;
        String medicalName = inventoryStair.getMedicalName();
        String spell = inventoryStair.getSpell();
        String producer = inventoryStair.getProducer();
        Integer type = null; 
        if (category == EnumMedicalInventoryCategory.DRUG_STORAGE) {
            if (count > 0) {
                type = EnumMedicalInventoryStorageType.IN_DRUG_STORAGE.getType();
            } else {
                type = EnumMedicalInventoryStorageType.IN_PYARMACY.getType();
            }
        } else {
            if (count > 0) {
                type = EnumMedicalInventoryStorageType.IN_PYARMACY.getType();
            } else {
                type = EnumMedicalInventoryStorageType.OUT_PYARMACY.getType();
            }
        }
        Integer specification = inventoryStair.getSpecification();
        Integer unit = inventoryStair.getUnit();
        Long amount = Long.valueOf(count);
        String operatorNo = ShiroKit.getUser().getDtUser().getUserNo();
        String operatorName = ShiroKit.getUser().getName();
        Date logDate = new Date();
        MedicalInventoryStorageLog entity = new MedicalInventoryStorageLog(medicalId, medicalName, spell, producer,
                type, specification, unit, produceBatchNum, produceDate, expireDate, price, amount, inboundChannel, operatorNo, operatorName, logDate , category.getCategory(), orgName);
        this.baseMapper.insert(entity);
    }

}
