package com.bdtd.card.registration.modular.inventory.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.bdtd.card.registration.common.model.EnumMedicalInventoryCategory;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryPharmacyService;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryStairService;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryStorageLogService;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.modular.system.dao.MedicalInventoryPharmacyMapper;
import com.stylefeng.guns.modular.system.model.MedicalInventoryDrugStorage;
import com.stylefeng.guns.modular.system.model.MedicalInventoryPharmacy;
import com.stylefeng.guns.modular.system.model.MedicalInventoryStair;
import com.stylefeng.guns.modular.system.model.PatientPrescriptionMedicineInfo;

/**
 * <p>
 * 药房管理 服务实现类
 * </p>
 *
 * @author lilei123
 * @since 2018-06-28
 */
@Service
public class MedicalInventoryPharmacyServiceImpl extends ServiceImpl<MedicalInventoryPharmacyMapper, MedicalInventoryPharmacy> implements IMedicalInventoryPharmacyService {

    @Autowired
    private IMedicalInventoryStorageLogService storageLogService;
    @Autowired
    private IMedicalInventoryStairService inventoryStairService;

    @Override
    public long selectCountMaps(Map<String, Object> paramMap) {
        return this.baseMapper.selectCountMaps(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectPagedMaps(Map<String, Object> paramMap) {
        return this.baseMapper.selectPagedMaps(paramMap);
    }


    @Override
    public synchronized Tip updateInventoryCount(Integer id, Integer count, String orgName) {
        MedicalInventoryPharmacy entity = selectById(id);
        if (entity == null || entity.getInventoryNum() + count < 0) {
            return new Tip(500, "库存不足！");
        }
        this.baseMapper.updateInventoryCount(id, count);
        storageLogService.logStorage(id, count, EnumMedicalInventoryCategory.PHARMACY, orgName);
        return new Tip(200, "操作成功！");
    }

    @Override
    public synchronized int putInStorage(MedicalInventoryDrugStorage entity, Integer count, String orgName) {
        Wrapper<MedicalInventoryPharmacy> wrapper = new EntityWrapper<>();
        wrapper.eq("produce_batch_num", entity.getProduceBatchNum());
        wrapper.eq("parent_id", entity.getParentId());
        MedicalInventoryPharmacy medicalInventoryPharmacy = this.selectOne(wrapper);
        if (medicalInventoryPharmacy == null) {
            //药房不存在此药品
            medicalInventoryPharmacy = new MedicalInventoryPharmacy(entity);
            medicalInventoryPharmacy.setInventoryNum(Long.valueOf(count));
            this.insert(medicalInventoryPharmacy);
            storageLogService.logStorage(medicalInventoryPharmacy.getId(), count, EnumMedicalInventoryCategory.PHARMACY, orgName);
        } else {
            this.updateInventoryCount(medicalInventoryPharmacy.getId(), count, orgName);
        }
        
        return 0; 
    }

    @Override
    @Transactional
    public synchronized Tip takeMedicalByPresrciption(List<PatientPrescriptionMedicineInfo> prescriptionMedicalList, String orgName) {
        String medicalNames = checkHasEnoughMedicine(prescriptionMedicalList);
        if ( medicalNames!= null) {
            return new Tip(500, "药房库存不足！库存不足的药品是： " + medicalNames );
        }
        
        prescriptionMedicalList.forEach((item) -> {
            this.updateInventoryCount(item.getMedicineId(), item.getAmount() * -1, orgName);
        });
        return null;
    }

    /**
     * 检查库存是否充足，若不足，则返回不足药品的名称列表
     * @param prescriptionMedicalList
     * @return
     */
    public String checkHasEnoughMedicine(List<PatientPrescriptionMedicineInfo> prescriptionMedicalList) {
        List<Integer> medicalIdList = prescriptionMedicalList.stream().map((item) -> {
            return item.getMedicineId();
        }).collect(Collectors.toList());
        
        List<MedicalInventoryPharmacy> inventoryPharmacies = selectBatchIds(medicalIdList);
        if (inventoryPharmacies == null || inventoryPharmacies.size() == 0) {
            return "所有药品均未找到！";
        }
        Map<Integer, MedicalInventoryPharmacy> map = new HashMap<>(inventoryPharmacies.size());
        inventoryPharmacies.forEach((item) -> {
            map.put(item.getId(), item);
        });
        
        List<Integer> storageNotEnough = new ArrayList<>(inventoryPharmacies.size());
        prescriptionMedicalList.forEach((item) -> {
            MedicalInventoryPharmacy medical = map.get(item.getMedicineId());
            if (medical == null || medical.getInventoryNum() < item.getAmount()) {
                storageNotEnough.add(medical.getParentId());
            }
        });
        
        if (storageNotEnough.size() > 0) {
            List<MedicalInventoryStair> stairList =  inventoryStairService.selectBatchIds(storageNotEnough);
            StringBuilder sb = new StringBuilder("[ ");
            int size = stairList.size();
            for (int i = 0; i < size; i++) {
                sb.append(stairList.get(i).getMedicalName());
                if (i != size -1) {
                    sb.append(", ");
                }
            }
            sb.append(" ]");
            return sb.toString();
        }
        return null;
        
    }

    @Override
    public Tip checkMedicalInventory(Integer medicalId, Integer amount) {
        MedicalInventoryPharmacy medicalInventoryPharmacy =  this.selectById(medicalId);
        if (medicalInventoryPharmacy == null) {
            return new Tip(500L, "该药品已被删除！");
        }
        
        if (medicalInventoryPharmacy.getInventoryNum().intValue() < amount) {
            return new Tip(500L, "库存不足！");
        }
        return null;
    }
}
