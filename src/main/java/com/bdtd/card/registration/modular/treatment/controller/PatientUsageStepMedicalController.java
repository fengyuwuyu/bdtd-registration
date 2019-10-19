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

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.bdtd.card.registration.modular.treatment.service.IPatientMedicalUsageStepService;
import com.bdtd.card.registration.modular.treatment.service.IPatientPrescriptionMedicineInfoService;
import com.bdtd.card.registration.modular.treatment.service.IPatientUsageStepMedicalService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.annotion.DictEntity;
import com.stylefeng.guns.core.common.annotion.DictHandler;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.CommonUtils;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.modular.system.model.PatientMedicalUsageStep;
import com.stylefeng.guns.modular.system.model.PatientUsageStepMedical;

/**
 * 步骤药品详情控制器
 *
 * @author 
 * @Date 2018-07-01 22:33:11
 */
@Controller
@RequestMapping("/patientUsageStepMedical")
public class PatientUsageStepMedicalController extends BaseController {

    private String PREFIX = "/treatment/patientUsageStepMedical/";

    @Autowired
    private IPatientUsageStepMedicalService patientUsageStepMedicalService;
    @Autowired
    private IPatientPrescriptionMedicineInfoService prescriptionMedicineInfoService;
    
    @Autowired
    private IPatientMedicalUsageStepService medicalUsageStepService;

    /**
     * 跳转到步骤药品详情首页
     */
    @RequestMapping("/{usageStepId}")
    public String index(@PathVariable Integer usageStepId, Model model) {
        model.addAttribute("usageStepId", usageStepId);
        return PREFIX + "patientUsageStepMedical.html";
    }

    /**
     * 跳转到添加步骤药品详情
     */
    @RequestMapping("/patientUsageStepMedical_add/{usageStepId}")
    @DictHandler(dictModels = { DictConsts.DOSE_WAY, DictConsts.TREATMENT_DOSAGE, DictConsts.TREATMENT_PERIOD}, 
    dictKeys = { DictConsts.DOSE_WAY_KEY, DictConsts.TREATMENT_DOSAGE_KEY, DictConsts.TREATMENT_PERIOD_KEY})
    public String patientUsageStepMedicalAdd(@PathVariable Integer usageStepId, Model model) {
        model.addAttribute("usageStepId", usageStepId);
        model.addAttribute("prescriptionMedicalItemList", findByUsageStepId(usageStepId));
        return PREFIX + "patientUsageStepMedical_add.html";
    }
    
    private List<Map<String, Object>> findByUsageStepId(Integer usageStepId) {
        PatientMedicalUsageStep patientMedicalUsageStep = medicalUsageStepService.selectById(usageStepId);
        Integer patientInfoId = patientMedicalUsageStep.getPatientInfoId();
        return prescriptionMedicineInfoService.findByPatientInfoId(patientInfoId);
    }

    /**
     * 跳转到修改步骤药品详情
     */
    @RequestMapping("/patientUsageStepMedical_update/{patientUsageStepMedicalId}")
    @DictHandler(dictModels = { DictConsts.DOSE_WAY, DictConsts.TREATMENT_DOSAGE, DictConsts.TREATMENT_PERIOD}, 
    dictKeys = { DictConsts.DOSE_WAY_KEY, DictConsts.TREATMENT_DOSAGE_KEY, DictConsts.TREATMENT_PERIOD_KEY})
    public String patientUsageStepMedicalUpdate(@PathVariable Integer patientUsageStepMedicalId, Model model) {
        PatientUsageStepMedical patientUsageStepMedical = patientUsageStepMedicalService.selectById(patientUsageStepMedicalId);
        model.addAttribute("item",patientUsageStepMedical);
        model.addAttribute("prescriptionMedicalItemList", findByUsageStepId(patientUsageStepMedical.getUsageStepId()));
        LogObjectHolder.me().set(patientUsageStepMedical);
        return PREFIX + "patientUsageStepMedical_edit.html";
    }

    /**
     * 获取步骤药品详情列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @DictHandler(dictWrappers= {
            @DictEntity(parentId= DictConsts.MEDICAL_SPECIFICATION, fieldName=DictConsts.MEDICAL_SPECIFICATION_FIELD_NAME),
            @DictEntity(parentId= DictConsts.DOSE_WAY, fieldName=DictConsts.DOSE_WAY_FIELD_NAME),
            @DictEntity(parentId= DictConsts.TREATMENT_DOSAGE, fieldName=DictConsts.TREATMENT_DOSAGE_FIELD_NAME),
            @DictEntity(parentId= DictConsts.TREATMENT_PERIOD, fieldName=DictConsts.TREATMENT_PERIOD_FIELD_NAME)
    })
    public Object list(Integer usageStepId) {
    	List<Map<String, Object>> result = patientUsageStepMedicalService.findByMaps(MapUtil.createMap("usageStepId", usageStepId));
    	if (result == null || result.size() == 0) {
    	    return MapUtil.createMap("total", 0, "rows", Collections.emptyList());
    	}
    	return MapUtil.createMap("total", result.size(), "rows", result);
    }

    /**
     * 新增步骤药品详情
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PatientUsageStepMedical patientUsageStepMedical) {
        patientUsageStepMedicalService.insert(patientUsageStepMedical);
        return SUCCESS_TIP;
    }

    /**
     * 删除步骤药品详情
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer patientUsageStepMedicalId) {
        patientUsageStepMedicalService.deleteById(patientUsageStepMedicalId);
        return SUCCESS_TIP;
    }

    /**
     * 修改步骤药品详情
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PatientUsageStepMedical patientUsageStepMedical) {
        patientUsageStepMedicalService.updateById(patientUsageStepMedical);
        return SUCCESS_TIP;
    }

    /**
     * 步骤药品详情详情
     */
    @RequestMapping(value = "/detail/{patientUsageStepMedicalId}")
    @ResponseBody
    public Object detail(@PathVariable("patientUsageStepMedicalId") Integer patientUsageStepMedicalId) {
        return patientUsageStepMedicalService.selectById(patientUsageStepMedicalId);
    }
}
