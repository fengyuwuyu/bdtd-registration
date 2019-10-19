package com.bdtd.card.registration.modular.personal;

import java.io.File;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.bdtd.card.registration.common.model.EnumHandleTypeMask;
import com.bdtd.card.registration.common.model.EnumMarried;
import com.bdtd.card.registration.common.model.EnumPatientInfoStatus;
import com.bdtd.card.registration.common.model.EnumSickRestDay;
import com.bdtd.card.registration.common.model.EnumSickRestType;
import com.bdtd.card.registration.common.model.EnumUpgrouthSituation;
import com.bdtd.card.registration.common.utils.EnumAdapterFactory;
import com.bdtd.card.registration.common.utils.model.EnumAdapterEntity;
import com.bdtd.card.registration.modular.comprehensive.service.IPatientDisabilityRatingService;
import com.bdtd.card.registration.modular.examination.service.IExaminationHealthService;
import com.bdtd.card.registration.modular.inhospital.service.IPatientInHospitalService;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryPharmacyService;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryStairService;
import com.bdtd.card.registration.modular.treatment.controller.WebsocketTreatmentServer;
import com.bdtd.card.registration.modular.treatment.service.IPatientClinicDisposisService;
import com.bdtd.card.registration.modular.treatment.service.IPatientInfoService;
import com.bdtd.card.registration.modular.treatment.service.IPatientPrescriptionInfoService;
import com.bdtd.card.registration.modular.treatment.service.IPatientSickRestService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.common.annotion.DictEntity;
import com.stylefeng.guns.core.common.annotion.DictHandler;
import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.DictCacheFactory;
import com.stylefeng.guns.core.util.DownLoadUtil;
import com.stylefeng.guns.core.util.IpUtil;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.core.wrapper.DictWrapperEntity;
import com.stylefeng.guns.modular.system.model.ExaminationHealth;
import com.stylefeng.guns.modular.system.model.MedicalInventoryPharmacy;
import com.stylefeng.guns.modular.system.model.MedicalInventoryStair;
import com.stylefeng.guns.modular.system.model.PatientClinicDisposis;
import com.stylefeng.guns.modular.system.model.PatientDisabilityRating;
import com.stylefeng.guns.modular.system.model.PatientInfo;
import com.stylefeng.guns.modular.system.model.PatientPrescriptionInfo;
import com.stylefeng.guns.modular.system.model.PatientSickRest;
import com.stylefeng.guns.scmmain.model.DtPhoto;
import com.stylefeng.guns.scmmain.model.DtUser;
import com.stylefeng.guns.scmmain.service.IDtPhotoService;
import com.stylefeng.guns.scmmain.service.IDtUserService;

@RequestMapping("/personal")
@Controller
public class PersonalController extends BaseController {

    private String PREFIX = "/personal/";
    private Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private IDtUserService userServiceImpl;
    @Autowired
    private IDtPhotoService dtPhotoService;
    
    @Autowired
    private IExaminationHealthService examinationHealthService;
    @Autowired
    private IPatientInfoService patientInfoService;
    @Autowired
    private IPatientInHospitalService inHospitalService;
    @Autowired
    private IPatientDisabilityRatingService disabilityRatingService;
    @Autowired
    private DictCacheFactory dictCacheFactory;
    @Autowired
    private IPatientPrescriptionInfoService prescriptionInfoService;
    @Autowired
    private IPatientClinicDisposisService clinicDisposisService;
    @Autowired
    private IPatientSickRestService sickRestService;
    @Autowired
    private IMedicalInventoryPharmacyService medicalInventoryPharmacyService;
    @Autowired
    private IMedicalInventoryStairService medicalInventoryStairService;
    
    @Value("${server.port}")
    private int port;
    @Value("${server.context-path}")
    private String contextPath;
    
    @Autowired
    private WebsocketTreatmentServer websocketTreatmentController;
    
    @Value("${bdtd.photoPath}")
    private String photoPath;
    
    @RequestMapping("/callNumber")
    public String callNumber(Model model) {
        model.addAttribute("contextPath", "http://" + IpUtil.getLocalAddress() + ":" + port + contextPath);
        return PREFIX + "callNumber.html";
    }
    
    @RequestMapping("/callNumberData")
    @ResponseBody
    public Object callNumberData() {
        Wrapper<PatientInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("status", EnumPatientInfoStatus.REGISTRATION.getType());
        Date[] queryDate = DateUtil.getQueryDate(new Date());
        wrapper.and().between("create_date", queryDate[0], queryDate[1]);
        
        Page<Map<String, Object>> page = patientInfoService.selectMapsPage(
            new Page<>(1, 7, "create_date", true), wrapper);
        

        return MapUtil.createMap("currPatient", patientInfoService.getCurrPatient(), "list", page.getRecords());
    }
    
    @RequestMapping("/homePage")
    public String personalMainPage() {
        return PREFIX + "homePage.html";
    }
    
    @RequestMapping("/userImg")
    public void download(HttpServletRequest request, HttpServletResponse response, String userNo) {
        DtUser user = this.userServiceImpl.findByUserNo(userNo);
        if (user != null) {
            DtPhoto photo = this.dtPhotoService.findByUserSerial(user.getUserSerial());
            if (photo != null) {
                String path = photoPath + photo.getPhotoPath().replace("..", "");
                File file = new File(path + photo.getPhotoName());
                DownLoadUtil.download(file, response, request, false, photo.getPhotoName());
            }
        }
    }
    
    @RequestMapping("/userInfoByUserNo/{userNo}")
    public String userInfoByUserNo(@PathVariable String userNo, Model model) {
        DtUser user = this.userServiceImpl.findByUserNo(userNo);
        if (user == null) {
            model.addAttribute("code", 1);
            return PREFIX + "registrationFail.html";
        }
        model.addAttribute("user", user);
        return PREFIX + "userInfoDetail.html";
    }
    
    @RequestMapping("/userInfo/{userCard}")
    public String userInfo(@PathVariable String userCard, Model model) {
        DtUser user = this.userServiceImpl.findByUserCard(userCard);
        if (user == null) {
            model.addAttribute("code", 1);
            return PREFIX + "registrationFail.html";
        }
        model.addAttribute("user", user);
        return PREFIX + "userInfo.html";
    }
    
    @RequestMapping("/personalQuery/{userNo}")
    public String personalQuery(@PathVariable String userNo, Model model) {
        model.addAttribute("userNo", userNo);
        return PREFIX + "personalQueryChoose.html";
    }
    
    @RequestMapping("/doRegistration/{outpatient}/{userNo}")
    public String doRegistration(@PathVariable String userNo, @PathVariable Integer outpatient, Model model) {
        DtUser user = this.userServiceImpl.findByUserNo(userNo);
        if (user == null) {
            model.addAttribute("code", 1);
            return PREFIX + "registrationFail.html";
        }
        Tip tip = this.patientInfoService.canRegistration(userNo);
        if (tip != null) {
            if (tip.getMessage().startsWith("您当前已挂过号")) {
                model.addAttribute("code", 2);
                return PREFIX + "registrationFail.html";
            } else if (tip.getMessage().startsWith("当前存在转诊未回报记录")) {
                model.addAttribute("code", 3);
                return PREFIX + "registrationFail.html";   
            }
            model.addAttribute("code", 1);
            return PREFIX + "registrationFail.html";
        }
        PatientInfo patientInfo = new PatientInfo();
        patientInfo.setUserNo(userNo);
        patientInfo.setUserName(user.getUserLname());
        patientInfo.setAge(user.getUserBirthday() == null ? 0 : Integer.valueOf(DateUtil.getYear()) - Integer.valueOf(DateUtil.getYear(user.getUserBirthday())));
        patientInfo.setUserSex(user.getUserSex());
        patientInfo.setUserCard(user.getUserCard());
        patientInfo.setUserDuty(user.getUserDuty());
        patientInfo.setOrgId(Long.valueOf(user.getUserDep()));
        patientInfo.setOrgName(user.getUserDepname());
        patientInfo.setEnrolDate(user.getUserWorkday() == null ? null : new java.sql.Date(user.getUserWorkday().getTime()));
        patientInfo.setOutpatient(outpatient);
        
        patientInfo.setCreateDate(new Date());
        patientInfo.setStatus(EnumPatientInfoStatus.REGISTRATION.getType());
        patientInfo.setUpdateDate(patientInfo.getCreateDate());
        patientInfo.setHandleType(0L);
        patientInfoService.insert(patientInfo);

        websocketTreatmentController.sendMessage(patientInfo.getOutpatient(), EnumPatientInfoStatus.REGISTRATION.getType());
        
        model.addAttribute("userNo", userNo);
        return PREFIX + "registrationSuccess.html";
        
    }
    
    /**
     * 跳转到体检信息列表页
     */
    @RequestMapping("/examinationHealth/{userNo}")
    public String examinationHealth(@PathVariable String userNo, Model model) {
        if (StringUtil.isNullEmpty(userNo)) {
            userNo = ShiroKit.getUser().getDtUser().getUserNo();
        }
        DtUser user = userServiceImpl.findByUserNo(userNo);
        model.addAttribute("user", user);
        
        Wrapper<ExaminationHealth> wrapper = new EntityWrapper<>();
        wrapper.eq("user_no", userNo);
        List<ExaminationHealth> list = examinationHealthService.selectList(wrapper);
        model.addAttribute("list", list);
        model.addAttribute("userNo", userNo);
        return PREFIX + "personalExaminationHealth.html";
    }
    
    /**
     * 跳转到体检信息列表页
     */
    @RequestMapping("/examinationHealthList")
    @ResponseBody
    @DictHandler(dictWrappers = {
            @DictEntity(parentId = DictConsts.EXAMINATION_TYPE, fieldName = DictConsts.EXAMINATION_TYPE_FIELD_NAME) })
    public Object examinationHealthList(String userNo) {
        if (StringUtil.isNullEmpty(userNo)) {
            userNo = ShiroKit.getUser().getDtUser().getUserNo();
        }
        
        Wrapper<ExaminationHealth> wrapper = new EntityWrapper<>();
        wrapper.eq("user_no", userNo);
        List<Map<String, Object>> list = examinationHealthService.selectMaps(wrapper);

        dictCacheFactory.wrapper(list, Arrays.asList(new DictWrapperEntity(DictConsts.EXAMINATION_TYPE, "examinationType")));
        for (int i = 0; i < list.size(); i++) {
            list.get(i).put("index", i + 1);
        }
        return MapUtil.createMap("list", list);
    }
    
    /**
     * 跳转到体检信息列表页
     */
    @RequestMapping("/patientInfo/{userNo}")
    public String patientInfo(@PathVariable String userNo, Model model) {
        if (StringUtil.isNullEmpty(userNo)) {
            userNo = ShiroKit.getUser().getDtUser().getUserNo();
        }
        DtUser user = userServiceImpl.findByUserNo(userNo);
        if (user == null) {
            return PREFIX + "homePage.html";
        }
        model.addAttribute("user", user);
        model.addAttribute("userNo", userNo);
        return PREFIX + "personalPatientInfo.html";
    }
    
    /**
     * 跳转到体检信息列表页
     */
    @RequestMapping("/patientInfoList")
    @ResponseBody
    public Object patientInfoList(String userNo) {
        if (StringUtil.isNullEmpty(userNo)) {
            userNo = ShiroKit.getUser().getDtUser().getUserNo();
        }
        
        Wrapper<PatientInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("user_no", userNo);
        wrapper.gt("status", 2);
        List<Map<String, Object>> list = patientInfoService.selectMaps(wrapper);
        list.stream().forEach((item) -> {
            Long handleType = (Long) item.get("handleType");
            handleType = handleType == null ? 0 : handleType;
            String inHospital = "否";
            String transferTreament = "否";
            String sickRest = "否";
            if ((handleType & EnumHandleTypeMask.IN_HOSPITAL.getType()) == EnumHandleTypeMask.IN_HOSPITAL.getType()) {
                inHospital = "是";
            }
            if ((handleType & EnumHandleTypeMask.TRANSFER_TREATMENT.getType()) == EnumHandleTypeMask.TRANSFER_TREATMENT.getType()) {
                transferTreament = "是";
            }
            if ((handleType & EnumHandleTypeMask.SICK_REST.getType()) == EnumHandleTypeMask.SICK_REST.getType()) {
                sickRest = "是";
            }
            
            item.put("inHospital", inHospital);
            item.put("transferTreament", transferTreament);
            item.put("sickRest", sickRest);
            item.put("clinicDate", DateUtil.format((Date)item.get("clinicDate"), Consts.DATE_PATTERN));
        });
        
        dictCacheFactory.wrapper(list, Arrays.asList(new DictWrapperEntity(DictConsts.REGISTRATION_MAIN_DIAGNOSIS, "mainDiagnosis")));
        EnumAdapterFactory.adapterRows(list, Arrays.asList(new EnumAdapterEntity("fever", "EnumOriginMask"), new EnumAdapterEntity("trainWound", "EnumOriginMask")));
        for (int i = 0; i < list.size(); i++) {
            list.get(i).put("index", i + 1);
        }
        return MapUtil.createMap("list", list);
    }
    
    @RequestMapping("/inHospital/{userNo}")
    public String inHospital(@PathVariable String userNo, Model model) {
        if (StringUtil.isNullEmpty(userNo)) {
            userNo = ShiroKit.getUser().getDtUser().getUserNo();
        }
        DtUser user = userServiceImpl.findByUserNo(userNo);
        if (user == null) {
            return PREFIX + "homePage.html";
        }
        model.addAttribute("user", user);
        
        model.addAttribute("userNo", userNo);
        return PREFIX + "personalInHospitalInfo.html";
    }
    
    @RequestMapping("/inHospitalList")
    @ResponseBody
    public Object inHospitalList(String userNo) {
        if (StringUtil.isNullEmpty(userNo)) {
            userNo = ShiroKit.getUser().getDtUser().getUserNo();
        }

        Map<String, Object> params = MapUtil.createMap("userNo", userNo);
        List<Map<String, Object>> list = inHospitalService.findByMap(params);
        EnumAdapterFactory.adapterRows(list, Arrays.asList(new EnumAdapterEntity("status", "EnumInHospitalStatus")));
        dictCacheFactory.wrapper(list, Arrays.asList(new DictWrapperEntity(DictConsts.REGISTRATION_MAIN_DIAGNOSIS, "mainDiagnosis")));
        for (int i = 0; i < list.size(); i++) {
            list.get(i).put("index", i + 1);
        }
        return MapUtil.createMap("list", list);
    }
    
    @RequestMapping("/disabilityRating/{userNo}")
    @DictHandler(dictWrappers = {
            @DictEntity(parentId = DictConsts.REGISTRATION_MAIN_DIAGNOSIS, fieldName = DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME) })
    public String disabilityRating(@PathVariable String userNo, Integer pageNumber, Integer pageSize, Model model) {
        if (StringUtil.isNullEmpty(userNo)) {
            userNo = ShiroKit.getUser().getDtUser().getUserNo();
        }
        DtUser user = userServiceImpl.findByUserNo(userNo);
        if (user == null) {
            return PREFIX + "homePage.html";
        }
        model.addAttribute("user", user);
        model.addAttribute("userNo", userNo);
        return PREFIX + "personaDisabilityRatingInfo.html";
    }
    
    @RequestMapping("/disabilityRatingList")
    @ResponseBody
    public Object disabilityRatingList(String userNo, Model model) {
        if (StringUtil.isNullEmpty(userNo)) {
            userNo = ShiroKit.getUser().getDtUser().getUserNo();
        }
        DtUser user = userServiceImpl.findByUserNo(userNo);
        if (user == null) {
            return MapUtil.createFailedMap();
        }
        model.addAttribute("user", user);
        
        Wrapper<PatientDisabilityRating> wrapper = new EntityWrapper<>();
        wrapper.eq("user_no", userNo);
        wrapper.orderBy("result_date", Consts.DEFAULT_SORT_ORDER_IS_ASC);
        List<Map<String, Object>> list = this.disabilityRatingService.selectMaps(wrapper);

        EnumAdapterFactory.adapterRows(list, Arrays.asList(new EnumAdapterEntity("apply", "EnumOriginMask")));
        for (int i = 0; i < list.size(); i++) {
            list.get(i).put("index", i + 1);
        }
        return MapUtil.createSuccessMap("list", list);
    }

    
    /**
     * 跳转到体检信息详情页
     */
    @RequestMapping("/personalExaminationHealth_detail/{examinationHealthId}/{userNo}")
    @DictHandler(dictKeys = { DictConsts.EXAMINATION_TYPE_KEY, DictConsts.DOCTOR_KEY }, dictModels = { DictConsts.EXAMINATION_TYPE, DictConsts.DOCTOR })
    public String examinationHealthUpdate(@PathVariable Integer examinationHealthId, @PathVariable String userNo, Model model) {
        Map<String, Object> examinationHealth = examinationHealthService.findMapById(examinationHealthId);
        model.addAttribute("item", examinationHealth);
        model.addAttribute("upgrouthSituationItemList", EnumUpgrouthSituation.select());
        model.addAttribute("marriedItemList", EnumMarried.select());
        model.addAttribute("userNo", userNo);
        LogObjectHolder.me().set(examinationHealth);
        return PREFIX + "detail/personalExaminationHealthDetail.html";
    }

    @RequestMapping("/personalPatientInfo_detail/{id}/{userNo}")
    @DictHandler(dictModels = { DictConsts.REGISTRATION_MAIN_DIAGNOSIS }, dictKeys = {
            DictConsts.REGISTRATION_MAIN_DIAGNOSIS_KEY })
    public String patientInfoDetail(@PathVariable Integer id, @PathVariable String userNo, Model model) {
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
            model.addAttribute("sickRest", sickRest);
        }

        int count = patientInfoService.findByInTreatmentCount(patientInfo.getUserNo());
        model.addAttribute("inTreatmentCount", count);
        model.addAttribute("item", patientInfo);
        model.addAttribute("PRESCRIPTION", EnumHandleTypeMask.PRESCRIPTION.getType());
        model.addAttribute("skinTestMedicalItemList", Collections.emptyList());
        model.addAttribute("sickRestTypeItemList", EnumSickRestType.select());
        model.addAttribute("sickRestDayItemList", EnumSickRestDay.select());
        model.addAttribute("userNo", userNo);
        return PREFIX + "detail/patientInfo_detail.html";
    }
    
    /**
     * 跳转到挂号界面
     */
    @RequestMapping("/registration/{userNo}")
    public String registration(@PathVariable String userNo, Model model) {
        model.addAttribute("userNo", userNo);
        return PREFIX + "registration.html";
    }

    /**
     * 跳转到挂号界面
     */
    @RequestMapping("/registrationSuccess/{userNo}")
    public String registrationSuccess(@PathVariable String userNo, Model model) {
        model.addAttribute("userNo", userNo);
        return PREFIX + "registrationSuccess.html";
    }

    
    /**
     * 跳转到挂号界面
     */
    @RequestMapping("/registrationFail")
    public String registrationFail(Model model) {
        model.addAttribute("code", 1);
        return PREFIX + "registrationFail.html";
    }
}
