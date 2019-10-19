package com.bdtd.card.registration.modular.treatment.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryPharmacyService;
import com.bdtd.card.registration.modular.treatment.service.IPatientPrescriptionMedicineInfoService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.common.annotion.DictEntity;
import com.stylefeng.guns.core.common.annotion.DictHandler;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.modular.system.model.PatientPrescriptionMedicineInfo;

/**
 * 处方药详情控制器
 *
 * @author 
 * @Date 2018-06-26 09:11:57
 */
@Controller
@RequestMapping("/patientPrescriptionMedicineInfo")
public class PatientPrescriptionMedicineInfoController extends BaseController {

    private String PREFIX = "/treatment/patientPrescriptionMedicineInfo/";

    @Autowired
    private IPatientPrescriptionMedicineInfoService patientPrescriptionMedicineInfoService;
    
    @Autowired
    private IMedicalInventoryPharmacyService pharmacyService;

    /**
     * 跳转到处方药详情首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "patientPrescriptionMedicineInfo.html";
    }

    /**
     * 跳转到添加处方药详情
     */
    @RequestMapping("/patientPrescriptionMedicineInfo_add")
    @DictHandler(dictModels = { DictConsts.TREATMENT_USAGE, DictConsts.TREATMENT_DOSAGE, DictConsts.TREATMENT_PERIOD}, 
    dictKeys = { DictConsts.TREATMENT_USAGE_KEY, DictConsts.TREATMENT_DOSAGE_KEY, DictConsts.TREATMENT_PERIOD_KEY})
    public String patientPrescriptionMedicineInfoAdd(Model model) {
        return PREFIX + "patientPrescriptionMedicineInfo_add.html";
    }

    /**
     * 跳转到修改处方药详情
     */
    @RequestMapping("/patientPrescriptionMedicineInfo_update/{patientPrescriptionMedicineInfoId}")
    @DictHandler(dictModels = { DictConsts.TREATMENT_USAGE, DictConsts.TREATMENT_DOSAGE, DictConsts.TREATMENT_PERIOD}, 
    dictKeys = { DictConsts.TREATMENT_USAGE_KEY, DictConsts.TREATMENT_DOSAGE_KEY, DictConsts.TREATMENT_PERIOD_KEY})
    public String patientPrescriptionMedicineInfoUpdate(@PathVariable Integer patientPrescriptionMedicineInfoId, Model model) {
        PatientPrescriptionMedicineInfo patientPrescriptionMedicineInfo = patientPrescriptionMedicineInfoService.selectById(patientPrescriptionMedicineInfoId);
        model.addAttribute("item",patientPrescriptionMedicineInfo);
        LogObjectHolder.me().set(patientPrescriptionMedicineInfo);
        return PREFIX + "patientPrescriptionMedicineInfo_edit.html";
    }

    /**
     * 获取处方药详情列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @DictHandler(dictWrappers= {
            @DictEntity(parentId= DictConsts.MEDICAL_UNIT, fieldName=DictConsts.MEDICAL_UNIT_FIELD_NAME),
            @DictEntity(parentId= DictConsts.TREATMENT_USAGE, fieldName=DictConsts.TREATMENT_USAGE_FIELD_NAME),
            @DictEntity(parentId= DictConsts.TREATMENT_DOSAGE, fieldName=DictConsts.TREATMENT_DOSAGE_FIELD_NAME),
            @DictEntity(parentId= DictConsts.MEDICAL_SPECIFICATION, fieldName=DictConsts.MEDICAL_SPECIFICATION_FIELD_NAME),
            @DictEntity(parentId= DictConsts.TREATMENT_PERIOD, fieldName=DictConsts.TREATMENT_PERIOD_FIELD_NAME)
    })
    public Object list(Integer patientInfoId) {
        if (patientInfoId == null) {
            return MapUtil.createSuccessMap("rows", Collections.emptyList(), "total", 0);
        }
        
        long total = patientPrescriptionMedicineInfoService.countByMap(MapUtil.createMap("patientInfoId", patientInfoId));
        if (total == 0) {
            return MapUtil.createSuccessMap("rows", Collections.emptyList(), "total", total);
        }
        List<Map<String, Object>> list = patientPrescriptionMedicineInfoService.findByMap(MapUtil.createMap("patientInfoId", patientInfoId));
		return MapUtil.createSuccessMap("rows", list, "total", total);
    }

    /**
     * 新增处方药详情
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PatientPrescriptionMedicineInfo patientPrescriptionMedicineInfo) {
        Tip tip = this.pharmacyService.checkMedicalInventory(patientPrescriptionMedicineInfo.getMedicineId(), patientPrescriptionMedicineInfo.getAmount());
        if (tip != null) {
            return tip;
        }
        
        patientPrescriptionMedicineInfoService.insert(patientPrescriptionMedicineInfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除处方药详情
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer patientPrescriptionMedicineInfoId) {
        patientPrescriptionMedicineInfoService.deleteById(patientPrescriptionMedicineInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改处方药详情
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PatientPrescriptionMedicineInfo patientPrescriptionMedicineInfo) {
        Tip tip = this.pharmacyService.checkMedicalInventory(patientPrescriptionMedicineInfo.getMedicineId(), patientPrescriptionMedicineInfo.getAmount());
        if (tip != null) {
            return tip;
        }
        
        patientPrescriptionMedicineInfoService.updateById(patientPrescriptionMedicineInfo);
        return SUCCESS_TIP;
    }

    /**
     * 处方药详情详情
     */
    @RequestMapping(value = "/detail/{patientPrescriptionMedicineInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("patientPrescriptionMedicineInfoId") Integer patientPrescriptionMedicineInfoId) {
        return patientPrescriptionMedicineInfoService.selectById(patientPrescriptionMedicineInfoId);
    }

    /**
     * 根据patientInfoId计算处方药数量
     */
    @RequestMapping(value = "/countByPatientInfoId")
    @ResponseBody
    public Object countByPatientInfoId(@RequestParam(required=true) Integer patientInfoId) {
        long count = patientPrescriptionMedicineInfoService.countByMap(MapUtil.createMap("patientInfoId", patientInfoId));
        return MapUtil.createSuccessMap("count", count);
    }
    


    /**
     * 根据patientInfoId查询处方药列表
     */
    @RequestMapping(value = "/findByPatientInfoId")
    @ResponseBody
    public Object findByPatientInfoId(Integer patientInfoId) {
        if (patientInfoId == null) {
            return Collections.emptyList();
        }
        List<Map<String, Object>> result = patientPrescriptionMedicineInfoService.findByPatientInfoId(patientInfoId);
        if (result.size() != 0) {
            result.add(0, MapUtil.createMap("id", 0, "name", ""));
        }
        return result;
    }
}
