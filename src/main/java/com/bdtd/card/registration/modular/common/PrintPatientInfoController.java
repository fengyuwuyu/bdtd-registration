package com.bdtd.card.registration.modular.common;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.bdtd.card.registration.common.model.EnumOutpatientType;
import com.bdtd.card.registration.common.model.EnumSickRestType;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryPharmacyService;
import com.bdtd.card.registration.modular.treatment.service.IPatientClinicDisposisService;
import com.bdtd.card.registration.modular.treatment.service.IPatientInfoService;
import com.bdtd.card.registration.modular.treatment.service.IPatientMedicalUsageStepService;
import com.bdtd.card.registration.modular.treatment.service.IPatientPhysicalTherapyService;
import com.bdtd.card.registration.modular.treatment.service.IPatientPrescriptionInfoService;
import com.bdtd.card.registration.modular.treatment.service.IPatientPrescriptionMedicineInfoService;
import com.bdtd.card.registration.modular.treatment.service.IPatientRadiateExamineService;
import com.bdtd.card.registration.modular.treatment.service.IPatientSickRestService;
import com.bdtd.card.registration.modular.treatment.service.IPatientUsageStepMedicalService;
import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.util.CommonUtils;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.DictCacheFactory;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.core.wrapper.DictWrapperEntity;
import com.stylefeng.guns.modular.system.model.PatientClinicDisposis;
import com.stylefeng.guns.modular.system.model.PatientInfo;
import com.stylefeng.guns.modular.system.model.PatientMedicalUsageStep;
import com.stylefeng.guns.modular.system.model.PatientPhysicalTherapy;
import com.stylefeng.guns.modular.system.model.PatientPrescriptionInfo;
import com.stylefeng.guns.modular.system.model.PatientRadiateExamine;

@RequestMapping("/printPatientInfo") 
@Controller
@ConditionalOnBean(value=DictCacheFactory.class)
public class PrintPatientInfoController {
    
    public static final String PREFIX = "/print/patient/";
    @Autowired
    private IPatientInfoService patientInfoService;
    @Autowired
    private IPatientPrescriptionMedicineInfoService prescriptionMedicineInfoService;
    @Autowired
    private IPatientPrescriptionInfoService prescriptionInfoService;
    @Autowired
    private IPatientSickRestService sickRestService;
    @Autowired
    private IPatientPhysicalTherapyService physicalTherapyService;
    @Autowired
    private IPatientRadiateExamineService radiateExamineService;
    @Autowired
    private IPatientMedicalUsageStepService medicalUsageStepService;
    @Autowired
    private IPatientUsageStepMedicalService usageStepMedicalService;
    @Autowired
    private IPatientClinicDisposisService clinicDisposisService;
    @Autowired
    private IMedicalInventoryPharmacyService pharmacyService;
    @Autowired
    private DictCacheFactory dictCacheFactory;
    
    @RequestMapping("/openPrescription/{patientInfoId}")
    public String openPrescription(@PathVariable Integer patientInfoId, Model model) {
        Wrapper<PatientInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", patientInfoId);
        Map<String, Object> patientInfo = patientInfoService.selectMap(wrapper);
        patientInfo.put("outpatient", EnumOutpatientType.fromType((Integer)patientInfo.get("outpatient")).getDesc());
        dictCacheFactory.wrapper(Arrays.asList(patientInfo), Arrays.asList(
                new DictWrapperEntity(DictConsts.REGISTRATION_MAIN_DIAGNOSIS, DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME)));
        model.addAttribute("patientInfo", patientInfo);
        
        Wrapper<PatientPrescriptionInfo> prescriptionWrapper = new EntityWrapper<>();
        prescriptionWrapper.eq("patient_info_id", patientInfoId);
        PatientPrescriptionInfo patientPrescriptionInfo = prescriptionInfoService.selectOne(prescriptionWrapper );
        patientInfo.put("remark", patientPrescriptionInfo == null ? "" : patientPrescriptionInfo.getRemark());
        
        List<Map<String, Object>> medicalList = prescriptionMedicineInfoService.findByMap(MapUtil.createMap("patientInfoId", patientInfoId));
        dictCacheFactory.wrapper(medicalList, Arrays.asList(
                new DictWrapperEntity(DictConsts.MEDICAL_UNIT, DictConsts.MEDICAL_UNIT_FIELD_NAME),
                new DictWrapperEntity(DictConsts.TREATMENT_USAGE, DictConsts.TREATMENT_USAGE_FIELD_NAME),
                new DictWrapperEntity(DictConsts.TREATMENT_DOSAGE, DictConsts.TREATMENT_DOSAGE_FIELD_NAME),
                new DictWrapperEntity(DictConsts.TREATMENT_PERIOD, DictConsts.TREATMENT_PERIOD_FIELD_NAME)
                ));
        model.addAttribute("medicalList", medicalList);
        
        double money = 0L;
        if (medicalList != null && medicalList.size() > 0) {
            for (Map<String, Object> map : medicalList) {
                Double price = (Double) map.get("price");
                Integer amount = (Integer)map.get("amount");
                money += price * amount;
            }
        }
        BigDecimal b = new BigDecimal(money); 
        money = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        model.addAttribute("money", money);
        model.addAttribute("fontFace", "@font-face");
        model.addAttribute("page", "@page");
        
        Date clinicDate = (Date) patientInfo.get("clinicDate");
        model.addAttribute("clinicDate", clinicDate == null ? "" : new java.sql.Date(clinicDate.getTime()));
        model.addAttribute("now", DateUtil.format(new Date(), Consts.DATE_PATTERN));
        return PREFIX + "prescription.html";
    }
    
    @RequestMapping("/openDiagnosisProve/{patientInfoId}")
    public String openDiagnosisProve(@PathVariable Integer patientInfoId, Model model) {
        Wrapper<PatientInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", patientInfoId);
        Map<String, Object> patientInfo = patientInfoService.selectMap(wrapper);
        patientInfo.put("outpatient", EnumOutpatientType.fromType((Integer)patientInfo.get("outpatient")).getDesc());
        dictCacheFactory.wrapper(Arrays.asList(patientInfo), Arrays.asList(
                new DictWrapperEntity(DictConsts.REGISTRATION_MAIN_DIAGNOSIS, DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME)));
        model.addAttribute("patientInfo", patientInfo);
        
        List<Map<String, Object>> sickRestList = sickRestService.findByMap(MapUtil.createMap("patientInfoId", patientInfoId));
        Map<String, Object> sickRestObj = sickRestList.size() > 0 ? sickRestList.get(0) : null;
        if (sickRestObj != null) {
            sickRestObj.put("sickRestType", EnumSickRestType.fromType((Integer) sickRestObj.get("sickRestType")).getDesc());
            model.addAttribute("sickRest", sickRestObj);
        } else {
            model.addAttribute("sickRest", MapUtil.createMap());
        }
        
        Date clinicDate = (Date) patientInfo.get("clinicDate");
        model.addAttribute("clinicDate", new java.sql.Date(clinicDate.getTime()));
        model.addAttribute("now", DateUtil.format(new Date(), Consts.DATE_PATTERN));
        return PREFIX + "diagnosisProve.html";
    }
    
    @RequestMapping("/openPhysicalTherapy/{patientInfoId}")
    public String openPhysicalTherapy(@PathVariable Integer patientInfoId, Model model) {
        Wrapper<PatientInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", patientInfoId);
        Map<String, Object> patientInfo = patientInfoService.selectMap(wrapper);
        patientInfo.put("outpatient", EnumOutpatientType.fromType((Integer)patientInfo.get("outpatient")).getDesc());
        dictCacheFactory.wrapper(Arrays.asList(patientInfo), Arrays.asList(
                new DictWrapperEntity(DictConsts.REGISTRATION_MAIN_DIAGNOSIS, DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME)));
        model.addAttribute("patientInfo", patientInfo);
        
        Wrapper<PatientPhysicalTherapy> physicalTherapyWrapper = new EntityWrapper<>();
        physicalTherapyWrapper.eq("patient_info_id", patientInfoId);
        List<Map<String, Object>> physicalTherapyList = physicalTherapyService.selectMaps(physicalTherapyWrapper);
        dictCacheFactory.wrapper(physicalTherapyList, Arrays.asList(new DictWrapperEntity(DictConsts.PHYSICAL_THERAPY, DictConsts.PHYSICAL_THERAPY_FIELD_NAME)));
        model.addAttribute("physicalTherapyList", physicalTherapyList);
        int count = 0;
       for (Map<String, Object> map : physicalTherapyList) {
           Integer countPerDay = (Integer) map.get("countPerDay");
           Integer dayCount = (Integer) map.get("dayCount");
           count += countPerDay * dayCount;
       }
       model.addAttribute("count", count);
        
        Date clinicDate = (Date) patientInfo.get("clinicDate");
        model.addAttribute("clinicDate", new java.sql.Date(clinicDate.getTime()));
        model.addAttribute("now", DateUtil.format(new Date(), Consts.DATE_PATTERN));
        return PREFIX + "physicalTherapy.html";
    }
    
    @RequestMapping("/openDiagnosis/{patientInfoId}")
    public String openDiagnosis(@PathVariable Integer patientInfoId, Model model) {
        Wrapper<PatientInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", patientInfoId);
        Map<String, Object> patientInfo = patientInfoService.selectMap(wrapper);
        patientInfo.put("outpatient", EnumOutpatientType.fromType((Integer)patientInfo.get("outpatient")).getDesc());
        dictCacheFactory.wrapper(Arrays.asList(patientInfo), Arrays.asList(
                new DictWrapperEntity(DictConsts.REGISTRATION_MAIN_DIAGNOSIS, DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME)));
        model.addAttribute("patientInfo", patientInfo);
        
        Wrapper<PatientClinicDisposis> clinicWrapper = new EntityWrapper<>();
        clinicWrapper.eq("patient_info_id", patientInfoId);
        Map<String, Object> skinTest = clinicDisposisService.selectMap(clinicWrapper);
        model.addAttribute("skinTestRemark", skinTest == null ? "" : skinTest.get("remark"));
        Integer medicalId = skinTest == null ? null : (Integer) skinTest.get("skinTestMedicalId");
        if (medicalId == null) {
            model.addAttribute("skinTestMedical", ""); 
        } else {
            List<Map<String, Object>> medical = pharmacyService.selectPagedMaps(MapUtil.createMap("id", medicalId));
            if (medical.size() > 0) {
                model.addAttribute("skinTestMedical", medical.get(0).get("medicalName"));
            } else {
                model.addAttribute("skinTestMedical", "");
            }
        }
        
        int count = 0;
        Wrapper<PatientMedicalUsageStep> medicalWrapper = new EntityWrapper<>();
        medicalWrapper.eq("patient_info_id", patientInfoId);
        List<PatientMedicalUsageStep> medicalUsageSteps= medicalUsageStepService.selectList(medicalWrapper);
        Map<String, List<Map<String, Object>>> map = new HashMap<>();
        if (medicalUsageSteps.size() > 0) {
            for (int i = 0; i < medicalUsageSteps.size(); i++) {
                List<Map<String, Object>> result = usageStepMedicalService.findByMaps(MapUtil.createMap("usageStepId", medicalUsageSteps.get(i).getId()));
                count += result.size();
                
                dictCacheFactory.wrapper(result, Arrays.asList(
                        new DictWrapperEntity(DictConsts.MEDICAL_SPECIFICATION, DictConsts.MEDICAL_SPECIFICATION_FIELD_NAME),
                        new DictWrapperEntity(DictConsts.MEDICAL_UNIT, DictConsts.MEDICAL_UNIT_FIELD_NAME),
                        new DictWrapperEntity(DictConsts.DOSE_WAY, DictConsts.DOSE_WAY_FIELD_NAME),
                        new DictWrapperEntity(DictConsts.TREATMENT_DOSAGE, DictConsts.TREATMENT_DOSAGE_FIELD_NAME),
                        new DictWrapperEntity(DictConsts.TREATMENT_PERIOD, DictConsts.TREATMENT_PERIOD_FIELD_NAME)
                        ));
                
                map.put(String.valueOf(i + 1), result);
            }
            model.addAttribute("map", map);
        }
        model.addAttribute("size", medicalUsageSteps.size());
        model.addAttribute("count", count);
        
        Date clinicDate = (Date) patientInfo.get("clinicDate");
        model.addAttribute("clinicDate", new java.sql.Date(clinicDate.getTime()));
        model.addAttribute("now", DateUtil.format(new Date(), Consts.DATE_PATTERN));
        return PREFIX + "diagnosis.html";
    }
    
    @RequestMapping("/openRadiateCheck/{patientInfoId}")
    public String openRadioCheck(@PathVariable Integer patientInfoId, Model model) {
        Wrapper<PatientInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", patientInfoId);
        Map<String, Object> patientInfo = patientInfoService.selectMap(wrapper);
        patientInfo.put("outpatient", EnumOutpatientType.fromType((Integer)patientInfo.get("outpatient")).getDesc());
        dictCacheFactory.wrapper(Arrays.asList(patientInfo), Arrays.asList(
                new DictWrapperEntity(DictConsts.REGISTRATION_MAIN_DIAGNOSIS, DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME)));
        model.addAttribute("patientInfo", patientInfo);
        
        Wrapper<PatientRadiateExamine> radiateCheckWrapper = new EntityWrapper<>();
        radiateCheckWrapper.eq("patient_info_id", patientInfoId);
        List<Map<String, Object>> result = radiateExamineService.selectMaps(radiateCheckWrapper);
        model.addAttribute("radiateCheck", result);
        
        Date clinicDate = (Date) patientInfo.get("clinicDate");
        model.addAttribute("clinicDate", new java.sql.Date(clinicDate.getTime()));
        model.addAttribute("now", DateUtil.format(new Date(), Consts.DATE_PATTERN));
        return PREFIX + "radiateCheck.html";
    }
    
}
