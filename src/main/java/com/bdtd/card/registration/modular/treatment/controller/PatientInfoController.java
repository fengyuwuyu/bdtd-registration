package com.bdtd.card.registration.modular.treatment.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.bdtd.card.registration.common.model.EnumHandleTypeMask;
import com.bdtd.card.registration.common.model.EnumOriginMask;
import com.bdtd.card.registration.common.model.EnumOutpatientType;
import com.bdtd.card.registration.common.model.EnumPatientInfoStatus;
import com.bdtd.card.registration.common.model.EnumSickRestDay;
import com.bdtd.card.registration.common.model.EnumSickRestType;
import com.bdtd.card.registration.common.utils.EnumAdapterFactory;
import com.bdtd.card.registration.common.utils.model.EnumAdapterEntity;
import com.bdtd.card.registration.modular.alert.model.AlertFever;
import com.bdtd.card.registration.modular.alert.service.IAlertConfigService;
import com.bdtd.card.registration.modular.alert.service.IAlertFeverService;
import com.bdtd.card.registration.modular.common.CommonPatientInfoService;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryPharmacyService;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryStairService;
import com.bdtd.card.registration.modular.transfer.service.IPatientTransferTreatmentService;
import com.bdtd.card.registration.modular.treatment.service.IIrritabilityHistoryService;
import com.bdtd.card.registration.modular.treatment.service.IPatientClinicDisposisService;
import com.bdtd.card.registration.modular.treatment.service.IPatientInfoService;
import com.bdtd.card.registration.modular.treatment.service.IPatientPrescriptionInfoService;
import com.bdtd.card.registration.modular.treatment.service.IPatientPrescriptionMedicineInfoService;
import com.bdtd.card.registration.modular.treatment.service.IPatientSickRestService;
import com.stylefeng.guns.config.properties.BdtdProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.common.annotion.DictEntity;
import com.stylefeng.guns.core.common.annotion.DictHandler;
import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.model.EnumOperator;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.CommonUtils;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.DictCacheFactory;
import com.stylefeng.guns.core.util.DownLoadUtil;
import com.stylefeng.guns.core.util.IpUtil;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.core.util.QueryHelper;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.core.wrapper.DictWrapperEntity;
import com.stylefeng.guns.core.wrapper.MybatisCustomWrapper;
import com.stylefeng.guns.modular.system.model.AlertDep;
import com.stylefeng.guns.modular.system.model.IrritabilityHistory;
import com.stylefeng.guns.modular.system.model.MedicalInventoryPharmacy;
import com.stylefeng.guns.modular.system.model.MedicalInventoryStair;
import com.stylefeng.guns.modular.system.model.PatientClinicDisposis;
import com.stylefeng.guns.modular.system.model.PatientInfo;
import com.stylefeng.guns.modular.system.model.PatientPrescriptionInfo;
import com.stylefeng.guns.modular.system.model.PatientPrescriptionMedicineInfo;
import com.stylefeng.guns.modular.system.model.PatientSickRest;
import com.stylefeng.guns.scmmain.service.IDtDepService;

/**
 * 病患信息控制器
 *
 * @author
 * @Date 2018-06-21 14:56:04
 */
@Controller
@RequestMapping("/patientInfo")
@ConditionalOnBean(value=DictCacheFactory.class)
public class PatientInfoController extends BaseController {

    private String PREFIX = "/treatment/patientInfo/";
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IPatientInfoService patientInfoService;
    @Autowired
    private IPatientPrescriptionInfoService prescriptionInfoService;
    @Autowired
    private IPatientClinicDisposisService clinicDisposisService;
    @Autowired
    IPatientTransferTreatmentService transferTreatmentService;
    @Autowired
    private IPatientSickRestService sickRestService;
    @Autowired
    private IMedicalInventoryPharmacyService medicalInventoryPharmacyService;
    @Autowired
    private IMedicalInventoryStairService medicalInventoryStairService;
    @Autowired
    private IPatientPrescriptionMedicineInfoService prescriptionMedicineInfoService;
    @Autowired
    private IIrritabilityHistoryService irritabilityHistoryService;
    @Autowired
    private CommonPatientInfoService commonPatientInfoService;

    @Autowired
    private WebsocketTreatmentServer websocketTreatmentController;
    @Autowired
    private WebsocketAlertFeverServer alertFeverServer;
    
    
    @Autowired
    private DictCacheFactory dictCacheFactory;
    @Autowired
    private QueryHelper queryHelper;
    @Autowired
    private IDtDepService depService;
    @Autowired
    private IAlertFeverService alertFeverService;
    @Autowired
    private IAlertConfigService alertConfigService;

    @Value("${guns.file-upload-path}")
    private String tmpPath;
    
    @Value("${server.port}")
    private int port;
    @Value("${server.context-path}")
    private String contextPath;
    @Autowired
    private BdtdProperties bdtdProperties;

    /**
     * 跳转到病患信息首页
     */
    @RequestMapping("")
    @DictHandler(dictModels = { DictConsts.REGISTRATION_MAIN_DIAGNOSIS }, dictKeys = {
            DictConsts.REGISTRATION_MAIN_DIAGNOSIS_KEY })
    public String index(Model model) {
        model.addAttribute("wsServer", "ws://" + bdtdProperties.getIp() == null ? IpUtil.getLocalAddress() : bdtdProperties.getIp() + ":" + port + contextPath);
        model.addAttribute("skinTestMedicalItemList", Collections.emptyList());
        model.addAttribute("sickRestTypeItemList", EnumSickRestType.select());
        return PREFIX + "patientInfo.html";
    }

    @RequestMapping("/findCanInHospital")
    @ResponseBody
    @DictHandler(dictWrappers = {
            @DictEntity(parentId = DictConsts.REGISTRATION_MAIN_DIAGNOSIS, fieldName = DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME) })
    public Object findCanInHospital(String q) {
        return patientInfoService.findCanInHospitalMap(q);
    }

    @RequestMapping("/findCanInHospitalTable")
    @ResponseBody
    @DictHandler(dictWrappers = {
            @DictEntity(parentId = DictConsts.REGISTRATION_MAIN_DIAGNOSIS, fieldName = DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME) })
    public Object findCanInHospitalTable(String q) {
        List<Map<String, Object>> list = patientInfoService.findCanInHospitalMap(q);
        return MapUtil.createMap("rows", list, "total", list.size());
    }

    @RequestMapping("/takeMedical")
    public String takeMedical(Model model) {
        model.addAttribute("patientInfoStatusItemList", EnumPatientInfoStatus.select2());
        model.addAttribute("dtDepItemList", this.depService.select());
        return "/inventory/takeMedical/takeMedical.html";
    }

    @RequestMapping("/openPrescriptionMedicalDetail/{patientInfoId}")
    public String openPrescriptionMedicalDetail(@PathVariable Integer patientInfoId, Model model) {
        model.addAttribute("patientInfoId", patientInfoId);
        model.addAttribute("status", patientInfoService.selectById(patientInfoId).getStatus());
        return "/inventory/takeMedical/patientPrescriptionMedicineInfo.html";
    }

    @RequestMapping("/confirmTakeMedical")
    @ResponseBody
    @Transactional
    public Object confirmTakeMedical(@RequestParam(required = true) Integer patientInfoId, Model model) {
        PatientInfo patientInfo = patientInfoService.selectById(patientInfoId);
        if (patientInfo.getStatus() == EnumPatientInfoStatus.HAS_TAKE_MEDICINE.getType()) {
            return new Tip(500, "该处方已取药！");
        }
        List<PatientPrescriptionMedicineInfo> prescriptionMedicalList = prescriptionMedicineInfoService
                .selectByMap(MapUtil.createMap("patient_info_id", patientInfoId));
        if (prescriptionMedicalList == null || prescriptionMedicalList.size() == 0) {
            return new Tip(500, "该处方没有药品可取！");
        }

        Tip result = medicalInventoryPharmacyService.takeMedicalByPresrciption(prescriptionMedicalList, "处方");
        if (result != null) {
            return result;
        }

        // 更新状态为已取药
        patientInfo.setStatus(EnumPatientInfoStatus.HAS_TAKE_MEDICINE.getType());
        patientInfo.setUpdateDate(new Date());
        patientInfoService.updateById(patientInfo);
        return SUCCESS_TIP;
    }

    @RequestMapping("/openPrescription/{patientInfoId}")
    public String openPrescription(@PathVariable Integer patientInfoId, Model model) {
        model.addAttribute("patientInfoId", patientInfoId);
        return "/inventory/takeMedical/patientPrescriptionMedicineInfo.html";
    }

    @RequestMapping("/search")
    @DictHandler(dictModels = { DictConsts.REGISTRATION_MAIN_DIAGNOSIS }, dictKeys = {
            DictConsts.REGISTRATION_MAIN_DIAGNOSIS_KEY })
    public String search(Model model) {
        model.addAttribute("handleTypeItemList", EnumHandleTypeMask.select());
        model.addAttribute("patientInfoStatusItemList", EnumPatientInfoStatus.select());
        model.addAttribute("dtDepItemList", this.depService.select());
        return PREFIX + "patientInfo_search.html";
    }

    @RequestMapping("/usageStepDetail/{usageStepId}")
    public String index(@PathVariable Integer usageStepId, Model model) {
        model.addAttribute("usageStepId", usageStepId);
        return PREFIX + "patientUsageStepMedical.html";
    }

    /**
     * 跳转到添加病患信息
     */
    @RequestMapping("/patientInfo_add")
    public String patientInfoAdd(Model model) {
        model.addAttribute(DictConsts.REGISTRATION_OUTPATIENT_KEY, EnumOutpatientType.select());
        return PREFIX + "patientInfo_add.html";
    }

    @RequestMapping("/patientInfo_detail/{id}")
    @DictHandler(dictModels = { DictConsts.REGISTRATION_MAIN_DIAGNOSIS }, dictKeys = {
            DictConsts.REGISTRATION_MAIN_DIAGNOSIS_KEY })
    public String patientInfoDetail(@PathVariable Integer id, Model model) {
        PatientInfo patientInfo = patientInfoService.selectById(id);
        if (patientInfo == null) {
            log.error("该患者就诊信息已被删除！");
            return ERROR;
        }

        Integer patientInfoId = patientInfo.getId();

        long handleType = patientInfo.getHandleType();
        if (ToolUtil.checkHandleType(handleType, EnumHandleTypeMask.PRESCRIPTION.getType())) {
            Wrapper<PatientPrescriptionInfo> prescriptionWrapper = new EntityWrapper<>();
            prescriptionWrapper.eq("patient_info_id", patientInfoId);
            PatientPrescriptionInfo prescriptionInfo = prescriptionInfoService.selectOne(prescriptionWrapper);
            if (prescriptionInfo == null) {
                prescriptionInfo = new PatientPrescriptionInfo();
            }
            model.addAttribute("prescriptionInfo", prescriptionInfo);
        }

        if (ToolUtil.checkHandleType(handleType, EnumHandleTypeMask.DISPOSE.getType())) {
            Wrapper<PatientClinicDisposis> clinicDisposisWrapper = new EntityWrapper<>();
            clinicDisposisWrapper.eq("patient_info_id", patientInfoId);
            PatientClinicDisposis clinicDisposis = clinicDisposisService.selectOne(clinicDisposisWrapper);
            if (clinicDisposis == null) {
                clinicDisposis = new PatientClinicDisposis();
            }
            model.addAttribute("clinicDisposis", clinicDisposis);
            if (clinicDisposis != null && clinicDisposis.getSkinTestMedicalId() != null) {
                MedicalInventoryPharmacy inventoryPharmacy = medicalInventoryPharmacyService
                        .selectById(clinicDisposis.getSkinTestMedicalId());
                MedicalInventoryStair medicalInventoryStair = medicalInventoryStairService
                        .selectById(inventoryPharmacy.getParentId());
                model.addAttribute("clinicDisposisMedicalName", medicalInventoryStair.getMedicalName());
            } else {
                model.addAttribute("clinicDisposisMedicalName", "");
            }
        }

        if (ToolUtil.checkHandleType(handleType, EnumHandleTypeMask.SICK_REST.getType())) {
            Wrapper<PatientSickRest> sickRestWrapper = new EntityWrapper<>();
            sickRestWrapper.eq("patient_info_id", patientInfoId);
            PatientSickRest sickRest = sickRestService.selectOne(sickRestWrapper);
            if (sickRest == null) {
                sickRest = new PatientSickRest();
            }
            model.addAttribute("sickRest", sickRest);
        }

        int count = patientInfoService.findByInTreatmentCount(patientInfo.getUserNo());
        model.addAttribute("inTreatmentCount", count);
        model.addAttribute("item", patientInfo);
        model.addAttribute("PRESCRIPTION", EnumHandleTypeMask.PRESCRIPTION.getType());
        model.addAttribute("skinTestMedicalItemList", Collections.emptyList());
        model.addAttribute("sickRestTypeItemList", EnumSickRestType.select());
        model.addAttribute("sickRestDayItemList", EnumSickRestDay.select());
        model.addAttribute("originMaskItemList", EnumOriginMask.select());
        return PREFIX + "patientInfo_detail.html";
    }

    /**
     * 跳转到病患诊断详情页
     */
    @RequestMapping("/patientInfo_diagnosis")
    @DictHandler(dictModels = { DictConsts.REGISTRATION_OUTPATIENT }, dictKeys = {
            DictConsts.REGISTRATION_OUTPATIENT_KEY })
    public String patientInfoDIagnosis(Model model) {
        return PREFIX + "patientInfo_diagnosis.html";
    }

    /**
     * 跳转到修改病患信息
     */
    @RequestMapping("/patientInfo_update/{patientInfoId}")
    @DictHandler(dictModels = { DictConsts.REGISTRATION_OUTPATIENT }, dictKeys = {
            DictConsts.REGISTRATION_OUTPATIENT_KEY })
    public String patientInfoUpdate(@PathVariable Integer patientInfoId, Model model) {
        PatientInfo patientInfo = patientInfoService.selectById(patientInfoId);
        model.addAttribute("item", patientInfo);
        LogObjectHolder.me().set(patientInfo);
        return PREFIX + "patientInfo_edit.html";
    }

    /**
     * 获取病患信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @DictHandler(dictWrappers = {
            @DictEntity(parentId = DictConsts.REGISTRATION_MAIN_DIAGNOSIS, fieldName = DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME) })
    public Object list(String userName, Long orgId, String userDuty, String enrolDate, Integer trainWound, Integer mainDiagnosis, 
            Integer fever, Long handleType, String beginDate, String endDate, Integer status, Boolean diagnosisComplete, Boolean comprehensiveQuery,
            Integer offset, Integer limit) {
        MybatisCustomWrapper<PatientInfo> wrapper = new MybatisCustomWrapper<>();
        CommonUtils.handleRequestParams(wrapper, "user_name", userName, EnumOperator.LIKE,
                "train_wound", trainWound, null, "fever", fever, null, "handle_type", handleType, EnumOperator.BIT, "main_diagnosis", mainDiagnosis, null,
                "status", status, null, "user_duty", userDuty, null, "enrol_date",
                StringUtil.isNullEmpty(enrolDate) ? null : DateUtil.parse(enrolDate, Consts.DATE_PATTERN),
                EnumOperator.GE);
        if (diagnosisComplete != null && diagnosisComplete) {
            CommonUtils.handleRequestParams(wrapper, "status", Arrays.asList(3, 4), EnumOperator.IN);
            wrapper.bit("handle_type", 1);
        }
        if (comprehensiveQuery != null && comprehensiveQuery) {
            CommonUtils.handleRequestParams(wrapper, "status", Arrays.asList(3, 4), EnumOperator.IN);
        }
        CommonUtils.handleQueryDateSection("clinic_date", beginDate, endDate, wrapper, Consts.DATE_TIME_PATTERN);

        queryHelper.wrapperQueryByRole(wrapper, orgId);
        Page<Map<String, Object>> page = patientInfoService.selectMapsPage(
                new Page<>(offset / limit + 1, limit, Consts.DEFAULT_SORT_FIELD, Consts.DEFAULT_SORT_ORDER_IS_ASC), wrapper);
        
        commonPatientInfoService.replaceIrritabilityHistory(page.getRecords());

        page.getRecords().forEach((item) -> {
            Long handleTypeVal = (Long) item.get("handleType");
            if ((handleTypeVal & EnumHandleTypeMask.IN_HOSPITAL.getType()) == EnumHandleTypeMask.IN_HOSPITAL
                    .getType()) {
                item.put("inhospital", "是");
            } else {
                item.put("inhospital", "否");
            }

            if ((handleTypeVal & EnumHandleTypeMask.SICK_REST.getType()) == EnumHandleTypeMask.SICK_REST.getType()) {
                item.put("sickRest", "是");
            } else {
                item.put("sickRest", "否");
            }

            if ((handleTypeVal
                    & EnumHandleTypeMask.TRANSFER_TREATMENT.getType()) == EnumHandleTypeMask.TRANSFER_TREATMENT
                            .getType()) {
                item.put("transfer", "是");
            } else {
                item.put("transfer", "否");
            }
        });
        
        List<EnumAdapterEntity> enumAdapters = Arrays.asList(
                new EnumAdapterEntity("handleType", "EnumHandleTypeMask", (item) -> {
                    return EnumHandleTypeMask.getDescs((Long)item);
                }),
                new EnumAdapterEntity("trainWound", "EnumOriginMask"),
                new EnumAdapterEntity("fever", "EnumOriginMask")
                );
        EnumAdapterFactory.adapterRows(page.getRecords(), enumAdapters);
        return MapUtil.createMap("total", page.getTotal(), "rows", page.getRecords());
    }


    /**
     * 获取病患信息列表
     */
    @RequestMapping(value = "/trainWoundList")
    @ResponseBody
    @DictHandler(dictWrappers = {
            @DictEntity(parentId = DictConsts.REGISTRATION_MAIN_DIAGNOSIS, fieldName = DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME) })
    public Object trainWoundList(String userName, Long orgId, String userDuty, String enrolDate, Integer trainWound, Integer mainDiagnosis, 
            Integer fever, Long handleType, String beginDate, String endDate, Integer status, Boolean diagnosisComplete, Boolean comprehensiveQuery,
            Integer offset, Integer limit) {
        return this.list(userName, orgId, userDuty, enrolDate, trainWound, mainDiagnosis, fever, handleType, beginDate, endDate, status, 
                diagnosisComplete, comprehensiveQuery, offset, limit);
    }

    /**
     * 获取病患信息列表
     */
    @RequestMapping(value = "/feverList")
    @ResponseBody
    @DictHandler(dictWrappers = {
            @DictEntity(parentId = DictConsts.REGISTRATION_MAIN_DIAGNOSIS, fieldName = DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME) })
    public Object feverList(String userName, Long orgId, String userDuty, String enrolDate, Integer trainWound, Integer mainDiagnosis, 
            Integer fever, Long handleType, String beginDate, String endDate, Integer status, String inhospitalStatus, Integer offset, Integer limit) {
        Map<String, Object> params = MapUtil.createMap("user_name", userName, "train_wound", trainWound, "fever", fever, "handle_type", handleType, "org_id", orgId,
                "main_diagnosis", mainDiagnosis, "status", status, "user_duty", userDuty, "enrol_date",
                StringUtil.isNullEmpty(enrolDate) ? null : DateUtil.parse(enrolDate, Consts.DATE_PATTERN), "inhospitalStatus", inhospitalStatus, "offset", offset, "limit", limit);
        CommonUtils.handleQueryDateSection("clinicBeginDate", "clinicEndDate", beginDate, endDate, params, Consts.DATE_TIME_PATTERN);

        queryHelper.wrapperQueryByRole(params, orgId);
        
        long total = this.patientInfoService.total(params);
        if (total == 0) {
            return MapUtil.createMap("total", total, "rows", Collections.emptyList());
        }
        
        List<Map<String, Object>> list = this.patientInfoService.findMaps(params);
        List<EnumAdapterEntity> enumAdapters = Arrays.asList(new EnumAdapterEntity("handleType", "EnumHandleTypeMask", (item) -> {
            return EnumHandleTypeMask.getDescs((Long)item);
        }) );
        EnumAdapterFactory.adapterRows(list, enumAdapters);
        return MapUtil.createMap("total", total, "rows", list);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(String userName, Long orgId, String userDuty, String enrolDate, Integer trainWound, Integer mainDiagnosis,
            Integer fever, Long handleType, String beginDate, String endDate, Integer status, Boolean diagnosisComplete, Boolean comprehensiveQuery,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = (Map<String, Object>) this.list(userName, orgId, userDuty, enrolDate,
                trainWound, mainDiagnosis, fever, handleType, beginDate, endDate, status, diagnosisComplete, comprehensiveQuery, 0, Integer.MAX_VALUE);

        List<Map<String, Object>> rows = (List<Map<String, Object>>) resultMap.get("rows");
        List<DictWrapperEntity> dictwrapperEntities = Arrays.asList(new DictWrapperEntity(
                DictConsts.REGISTRATION_MAIN_DIAGNOSIS, DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME));
        dictCacheFactory.wrapper(rows, dictwrapperEntities);

        String fileName = "门诊信息.xls";
        String[] headers = { "编号", "姓名", "性别", "身份", "单位", "入伍时间", "就诊时间", "主要诊断", "次要诊断", "住院", "训练伤", "发热", "病休",
                "转诊", "医生" };
        String[] fields = { "userNo", "userName", "userSex", "userDuty", "orgName", "enrolDate", "clinicDate",
                "mainDiagnosis", "minorDiagnosis", "inhospital", "trainWound", "fever", "sickRest", "transfer",
                "physicianName" };
        DownLoadUtil.exportExcelAndDownload("门诊信息", headers, fields, rows, response, request, tmpPath, fileName);
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/exportTrainWoundExcel")
    public void exportTrainWoundExcel(String userName, Long orgId, String userDuty, String enrolDate, Integer trainWound, Integer mainDiagnosis,
            Integer fever, Long handleType, String beginDate, String endDate, Integer status, Boolean diagnosisComplete, Boolean comprehensiveQuery,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = (Map<String, Object>) this.list(userName, orgId, userDuty, enrolDate,
                trainWound, mainDiagnosis, fever, handleType, beginDate, endDate, status, diagnosisComplete, comprehensiveQuery, 0, Integer.MAX_VALUE);

        List<Map<String, Object>> rows = (List<Map<String, Object>>) resultMap.get("rows");
        List<DictWrapperEntity> dictwrapperEntities = Arrays.asList(new DictWrapperEntity(
                DictConsts.REGISTRATION_MAIN_DIAGNOSIS, DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME));
        dictCacheFactory.wrapper(rows, dictwrapperEntities);

        String fileName = "训练伤信息.xls";
        String[] headers = { "编号", "姓名", "性别", "身份", "单位", "入伍时间", "主要诊断", "训练伤", "处理方式", "医生", "就诊时间" };
        String[] fields = { "userNo", "userName", "userSex", "userDuty", "orgName", "enrolDate",
                "mainDiagnosis", "trainWound", "handleType", "physicianName", "clinicDate" };
        DownLoadUtil.exportExcelAndDownload("训练伤信息", headers, fields, rows, response, request, tmpPath, fileName);
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/exportFeverExcel")
    public void exportFeverExcel(String userName, Long orgId, String userDuty, String enrolDate, Integer trainWound, Integer mainDiagnosis,
            Integer fever, Long handleType, String beginDate, String endDate, Integer status, String inhospitalStatus,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = (Map<String, Object>) this.feverList(userName, orgId, userDuty, enrolDate,
                trainWound, mainDiagnosis, fever, handleType, beginDate, endDate, status, inhospitalStatus, null, null);

        List<Map<String, Object>> rows = (List<Map<String, Object>>) resultMap.get("rows");
        List<DictWrapperEntity> dictwrapperEntities = Arrays.asList(
                new DictWrapperEntity(DictConsts.REGISTRATION_MAIN_DIAGNOSIS, DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME)
                );
        dictCacheFactory.wrapper(rows, dictwrapperEntities);
        
        List<EnumAdapterEntity> enumAdapters = Arrays.asList(new EnumAdapterEntity("fever", "EnumOriginMask"));
        EnumAdapterFactory.adapterRows(rows, enumAdapters);

        String fileName = "发热信息.xls";
        String[] headers = { "编号", "姓名", "性别", "身份", "单位", "入伍时间", "主要诊断", "发热", "状态", "医生", "就诊时间" };
        String[] fields = { "userNo", "userName", "userSex", "userDuty", "orgName", "enrolDate",
                "mainDiagnosis", "fever", "inhospitalStatus", "physicianName", "clinicDate" };
        DownLoadUtil.exportExcelAndDownload("发热信息", headers, fields, rows, response, request, tmpPath, fileName);
    }


    @RequestMapping(value = "/inTreatmentCount")
    @ResponseBody
    public Object inTreatmentCount(String userNo) {
        int count = patientInfoService.findByInTreatmentCount(userNo);
        return MapUtil.createSuccessMap("inTreatmentCount", count);
    }

    /**
     * 获取病患信息列表
     */
    @RequestMapping(value = "/listQueue")
    @ResponseBody
    public Object listQueue(@RequestParam(required = true) Integer outpatient,
            @RequestParam(required = true) Integer status, Integer asc, Integer page, Integer rows) {
        Wrapper<PatientInfo> wrapper = new EntityWrapper<>();
        if (status == 1) {
            wrapper.eq("outpatient", outpatient).and().in("status", Arrays.asList(
                    EnumPatientInfoStatus.REGISTRATION.getType(), EnumPatientInfoStatus.DIAGNOSISING.getType()));
        } else {
            wrapper.allEq(MapUtil.createMap("outpatient", outpatient, "status", status));
        }

        Date[] queryDate = DateUtil.getQueryDate(new Date());
        wrapper.and().between("create_date", queryDate[0], queryDate[1]);
        Page<Map<String, Object>> result = patientInfoService.selectMapsPage(
                new Page<>(1, rows == null ? 5 : rows, "create_date", asc != null ? true : false), wrapper);
        return MapUtil.createSuccessMap("total", result.getTotal(), "rows", result.getRecords());
    }

    /**
     * 新增病患信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PatientInfo patientInfo) {
        Tip tip = this.patientInfoService.canRegistration(patientInfo.getUserNo());
        if (tip != null) {
            return tip;
        }
        patientInfo.setCreateDate(new Date());
        patientInfo.setStatus(EnumPatientInfoStatus.REGISTRATION.getType());
        patientInfo.setUpdateDate(patientInfo.getCreateDate());
        patientInfo.setHandleType(0L);
        patientInfoService.insert(patientInfo);

        websocketTreatmentController.sendMessage(patientInfo.getOutpatient(),
                EnumPatientInfoStatus.REGISTRATION.getType());
        return SUCCESS_TIP;
    }

    /**
     * 删除病患信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer patientInfoId) {
        patientInfoService.deleteById(patientInfoId);
        if (patientInfoService.getCurrPatient() != null && patientInfoService.getCurrPatient().getId().intValue() == patientInfoId.intValue()) {
            patientInfoService.setCurrPatient(null);
        }
        this.websocketTreatmentController.sendMessage(EnumOutpatientType.COMMON.getType(), CALL_NUMBER_STATUS);
        return SUCCESS_TIP;
    }

    /**
     * 修改病患信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PatientInfo patientInfo) {
        Date now = new Date();
        Integer mainDiagnosis = patientInfo.getMainDiagnosis();
        if (dictCacheFactory.getDictMapByParentNameAndNum(DictConsts.REGISTRATION_MAIN_DIAGNOSIS, mainDiagnosis) == null) {
            return new Tip(500, "不存在的主要诊断！");
        }
        
        this.patientInfoService.setCurrPatient(null);
        
        patientInfo.setUpdateDate(now);
        patientInfo.setClinicDate(now);
        patientInfo.setPhysicianName(ShiroKit.getUser().getDtUser().getUserLname());
        patientInfo.setPhysicianNo(ShiroKit.getUser().getDtUser().getUserNo());
        patientInfoService.updateById(patientInfo);
        if (patientInfo.getStatus() != null) {
            websocketTreatmentController.sendMessage(patientInfo.getOutpatient(), CALL_NUMBER_STATUS);
        }
        
        irritabilityHistoryService.saveOrUpdate(patientInfo.getUserNo(), patientInfo.getIrritabilityHistory());
        
        //处理发热报警
        PatientInfo entity = this.patientInfoService.selectById(patientInfo.getId());
        Long depSerial = entity.getOrgId();
        AlertDep alertDep = this.alertFeverService.getByChildDepSerial(depSerial);
        if (alertDep != null) {
            AlertFever alertFever = this.alertFeverService.getAlertFeverByDepSerial(alertDep, this.alertConfigService.getConfig());
            if (alertFever != null) {
                alertFeverServer.sendMessage(alertFever);
            }
        }
        return SUCCESS_TIP;
    }

    /**
     * 修改病患信息
     */
    @RequestMapping(value = "/updateStatus")
    @ResponseBody
    public Object updateStatus(@RequestParam(required = true) Integer id, @RequestParam(required = true) Integer status,
            @RequestParam(required = true) Integer outpatient) {
        PatientInfo patientInfo = new PatientInfo();
        patientInfo.setId(id);
        patientInfo.setStatus(status);
        patientInfo.setUpdateDate(new Date());
        patientInfoService.updateById(patientInfo);

        websocketTreatmentController.sendMessage(outpatient, CALL_NUMBER_STATUS);
        return SUCCESS_TIP;
    }

    /**
     * 修改病患信息
     */
    @RequestMapping(value = "/findByUserName")
    @ResponseBody
    public Object findByUserName(PatientInfo patientInfo) {
        Wrapper<PatientInfo> wrapper = new EntityWrapper<>();
        wrapper.like("user_name", patientInfo.getUserName());
        return patientInfoService.selectMaps(wrapper);
    }

    /**
     * 病患信息详情
     */
    @RequestMapping(value = "/detail/{patientInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("patientInfoId") Integer patientInfoId) {
        return patientInfoService.selectById(patientInfoId);
    }

    @RequestMapping("/updateHandleType")
    @ResponseBody
    public Object updateHandleType(@RequestParam(required = true) Integer id,
            @RequestParam(required = true) Long handleType) {
        PatientInfo patientInfo = patientInfoService.selectById(id);
        if (patientInfo == null) {
            return new Tip(500, "该记录不存在！");
        }
        patientInfo.setHandleType(patientInfo.getHandleType() | handleType);
        patientInfo.setUpdateDate(new Date());
        patientInfoService.updateById(patientInfo);
        return MapUtil.createSuccessMap("message", "保存成功！");
    }

    @RequestMapping("/cancelHandleType")
    @ResponseBody
    public Object cancelHandleType(@RequestParam(required = true) Integer id,
            @RequestParam(required = true) Long handleType) {
        Tip tip = patientInfoService.cancelHandleType(id, handleType);
        if (tip != null) {
            return tip;
        }
        return MapUtil.createSuccessMap("message", "保存成功！");
    }

    private static final Integer CALL_NUMBER_STATUS = 1000;

    @RequestMapping("/callNumber")
    @ResponseBody
    public synchronized Object callNumber(Integer currId, @RequestParam(required = true) Integer outpatient,
            String userNo) {
        Date updateDate = new Date();
        int count = 0;
        String irritabilityHistory = "";

        if (currId != null) {
            PatientInfo currPatientInfo = patientInfoService.selectById(currId);
            if (currPatientInfo.getStatus().intValue() != EnumPatientInfoStatus.REGISTRATION.getType()
                    && currPatientInfo.getStatus().intValue() != EnumPatientInfoStatus.DIAGNOSISING.getType()) {
                return new Tip(500, "该患者已诊断完成！");
            }
            
            currPatientInfo.setStatus(EnumPatientInfoStatus.DIAGNOSISING.getType());
            this.patientInfoService.setCurrPatient(currPatientInfo);

            PatientInfo currUpdateInfo = new PatientInfo();
            currUpdateInfo.setId(currId);
            currUpdateInfo.setUpdateDate(updateDate);
            currUpdateInfo.setStatus(EnumPatientInfoStatus.DIAGNOSISING.getType());
            currUpdateInfo.setPhysicianName(ShiroKit.getUser().getDtUser().getUserLname());
            currUpdateInfo.setPhysicianNo(ShiroKit.getUser().getDtUser().getUserNo());
            currPatientInfo.setClinicDate(updateDate);
            
            userNo = currPatientInfo.getUserNo();
            patientInfoService.updateById(currUpdateInfo);
            if (!StringUtil.isNullEmpty(userNo)) {
                count = patientInfoService.findByInTreatmentCount(userNo);
            }
        }
        
        if (!StringUtil.isNullEmpty(userNo)) {
            IrritabilityHistory irritability = this.irritabilityHistoryService.selectById(userNo);
            if (irritability != null) {
                irritabilityHistory = irritability.getIrritabilityHistory();
            }
        }

        websocketTreatmentController.sendMessage(outpatient, CALL_NUMBER_STATUS);
        return MapUtil.createSuccessMap("inTreatmentCount", count, "irritabilityHistory", irritabilityHistory);
    }
    
    @RequestMapping("/resetStatus/{id}")
    @ResponseBody
    public Object resetStatus(@PathVariable Integer id) {
        int count = this.patientInfoService.resetStatus(id);
        if (count > 0) {
            websocketTreatmentController.sendMessage(1, CALL_NUMBER_STATUS);
        }
        return SUCCESS_TIP;
    }
}
