package com.bdtd.card.registration.modular.inventory.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.modular.system.model.MedicalInventoryDrugStorage;
import com.stylefeng.guns.modular.system.model.MedicalInventoryPharmacy;
import com.stylefeng.guns.modular.system.model.PatientPrescriptionMedicineInfo;

/**
 * <p>
 * 药房管理 服务类
 * </p>
 *
 * @author lilei123
 * @since 2018-06-28
 */
public interface IMedicalInventoryPharmacyService extends IService<MedicalInventoryPharmacy> {

    long selectCountMaps(Map<String, Object> paramMap);

    List<Map<String, Object>> selectPagedMaps(Map<String, Object> paramMap);

    Tip updateInventoryCount(Integer id, Integer count, String orgName);
    
    int putInStorage(MedicalInventoryDrugStorage entity, Integer count, String orgName);

    Tip takeMedicalByPresrciption(List<PatientPrescriptionMedicineInfo> prescriptionMedicalList, String orgName);
    
    Tip checkMedicalInventory(Integer medicalId, Integer amount);

}
