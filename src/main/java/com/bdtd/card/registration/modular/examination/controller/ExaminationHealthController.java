package com.bdtd.card.registration.modular.examination.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.bdtd.card.registration.common.model.EnumBloodType;
import com.bdtd.card.registration.common.model.EnumMarried;
import com.bdtd.card.registration.common.model.EnumUpgrouthSituation;
import com.bdtd.card.registration.modular.common.CommonPatientInfoService;
import com.bdtd.card.registration.modular.examination.service.IExaminationAssayService;
import com.bdtd.card.registration.modular.examination.service.IExaminationBUltrasonicService;
import com.bdtd.card.registration.modular.examination.service.IExaminationCommonService;
import com.bdtd.card.registration.modular.examination.service.IExaminationEntService;
import com.bdtd.card.registration.modular.examination.service.IExaminationGynaecologyAndObstetricsService;
import com.bdtd.card.registration.modular.examination.service.IExaminationHealthService;
import com.bdtd.card.registration.modular.examination.service.IExaminationInternalMedicineService;
import com.bdtd.card.registration.modular.examination.service.IExaminationOphthalmologyService;
import com.bdtd.card.registration.modular.examination.service.IExaminationStomatologyService;
import com.bdtd.card.registration.modular.examination.service.IExaminationSurgeryService;
import com.bdtd.card.registration.modular.treatment.service.IIrritabilityHistoryService;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.annotion.DictEntity;
import com.stylefeng.guns.core.common.annotion.DictHandler;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.model.EnumOperator;
import com.stylefeng.guns.core.util.CommonUtils;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.FileUtil;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.core.util.QueryHelper;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.modular.system.model.ExaminationAssay;
import com.stylefeng.guns.modular.system.model.ExaminationBUltrasonic;
import com.stylefeng.guns.modular.system.model.ExaminationCommon;
import com.stylefeng.guns.modular.system.model.ExaminationEnt;
import com.stylefeng.guns.modular.system.model.ExaminationGynaecologyAndObstetrics;
import com.stylefeng.guns.modular.system.model.ExaminationHealth;
import com.stylefeng.guns.modular.system.model.ExaminationInternalMedicine;
import com.stylefeng.guns.modular.system.model.ExaminationOphthalmology;
import com.stylefeng.guns.modular.system.model.ExaminationStomatology;
import com.stylefeng.guns.modular.system.model.ExaminationSurgery;
import com.stylefeng.guns.modular.system.model.IrritabilityHistory;
import com.stylefeng.guns.scmmain.service.IDtDepService;

/**
 * 体检信息控制器
 *
 * @author
 * @Date 2018-07-10 15:36:48
 */
@Controller
@RequestMapping("/examinationHealth")
public class ExaminationHealthController extends BaseController {

    private String PREFIX = "/examination/examinationHealth/";

    @Autowired
    private IExaminationHealthService examinationHealthService;
    @Autowired
    private IExaminationAssayService assayService;
    @Autowired
    private IExaminationBUltrasonicService bUltrasonicService;
    @Autowired
    private IExaminationCommonService commonService;
    @Autowired
    private IExaminationEntService entService;
    @Autowired
    private IExaminationGynaecologyAndObstetricsService goService;
    @Autowired
    private IExaminationInternalMedicineService internalMedicineService;
    @Autowired
    private IExaminationOphthalmologyService ophthalmologyService;
    @Autowired
    private IExaminationStomatologyService stomatologyService;
    @Autowired
    private IExaminationSurgeryService surgeryService;
    @Autowired
    private IIrritabilityHistoryService iIrritabilityHistoryService;
    @Autowired
    private CommonPatientInfoService commonPatientInfoService;
    @Autowired
    private IDtDepService depService;
    
    @Autowired
    private QueryHelper queryHelper;

    @Autowired
    private GunsProperties gunsProperties;
    
    /**
     * 上传图片(上传到项目的webapp/static/img)
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public String upload(@RequestPart("ecgPath") MultipartFile picture, String oldFile) {
        String originFileName = picture.getOriginalFilename().substring(0, picture.getOriginalFilename().lastIndexOf("."));
        String pictureName = originFileName + Consts.UPLOAD_FILE_SEPERATOR + UUID.randomUUID().toString() + ".jpg";
        try {
            String fileSavePath = gunsProperties.getFileUploadPath();
            picture.transferTo(new File(fileSavePath + pictureName));
        } catch (Exception e) {
            throw new GunsException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return pictureName;
    }
    

    /**
     * 跳转到体检信息首页
     */
    @RequestMapping("")
    @DictHandler(dictKeys = { DictConsts.EXAMINATION_TYPE_KEY, DictConsts.USER_DUTY_KEY }, dictModels = {
            DictConsts.EXAMINATION_TYPE, DictConsts.USER_DUTY })
    public String index(Model model) {
        model.addAttribute("dtDepItemList", this.depService.select());
        return PREFIX + "examinationHealth.html";
    }

    /**
     * 跳转到添加体检信息
     */
    @DictHandler(dictKeys = { DictConsts.EXAMINATION_TYPE_KEY, DictConsts.DOCTOR_KEY, DictConsts.RECHECK_CONCLUSION_KEY }, dictModels = { DictConsts.EXAMINATION_TYPE, DictConsts.DOCTOR, DictConsts.RECHECK_CONCLUSION })
    @RequestMapping("/examinationHealth_add")
    public String examinationHealthAdd(Model model) {
        model.addAttribute("upgrouthSituationItemList", EnumUpgrouthSituation.select());
        model.addAttribute("marriedItemList", EnumMarried.select());
        model.addAttribute("bloodTypeItemList", EnumBloodType.select());
        model.addAttribute("now", new java.sql.Date(System.currentTimeMillis()));
        return PREFIX + "examinationHealth_add.html";
    }

    /**
     * 跳转到修改体检信息
     */
    @RequestMapping("/examinationHealth_update/{examinationHealthId}")
    @DictHandler(dictKeys = { DictConsts.EXAMINATION_TYPE_KEY, DictConsts.DOCTOR_KEY, DictConsts.RECHECK_CONCLUSION_KEY }, dictModels = { DictConsts.EXAMINATION_TYPE, DictConsts.DOCTOR, DictConsts.RECHECK_CONCLUSION })
    public String examinationHealthUpdate(@PathVariable Integer examinationHealthId, Model model) {
        Map<String, Object> examinationHealth = examinationHealthService.findMapById(examinationHealthId);
        model.addAttribute("item", examinationHealth);
        model.addAttribute("upgrouthSituationItemList", EnumUpgrouthSituation.select());
        model.addAttribute("marriedItemList", EnumMarried.select());
        model.addAttribute("bloodTypeItemList", EnumBloodType.select());
        LogObjectHolder.me().set(examinationHealth);
        
        String userNo = (String) examinationHealth.get("userNo");
        IrritabilityHistory irritabilityHistory = this.iIrritabilityHistoryService.selectById(userNo);
        if (irritabilityHistory != null) {
            examinationHealth.put("irritabilityHistory", irritabilityHistory.getIrritabilityHistory());
        }
        return PREFIX + "examinationHealth_edit.html";
    }

    /**
     * 获取体检信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @DictHandler(dictWrappers = {
            @DictEntity(parentId = DictConsts.EXAMINATION_TYPE, fieldName = DictConsts.EXAMINATION_TYPE_FIELD_NAME) ,
            @DictEntity(parentId = DictConsts.RECHECK_CONCLUSION, fieldName = DictConsts.RECHECK_CONCLUSION_FIELD_NAME) 
            })
    public Object list(String userName, Long orgId, String userDuty, String enrolDate, Integer examinationType, String beginDate, String endDate, Integer offset, Integer limit) {
        Wrapper<ExaminationHealth> wrapper = new EntityWrapper<>();
        CommonUtils.handleRequestParams(wrapper, "user_name", userName, EnumOperator.LIKE, 
                "user_duty", userDuty, null, "enrol_date", StringUtil.isNullEmpty(enrolDate) ? null : DateUtil.parse(enrolDate, Consts.DATE_PATTERN),
                        EnumOperator.GE, "examination_type", examinationType, EnumOperator.EQ);
        CommonUtils.handleQueryDateSection("examination_date", beginDate, endDate, wrapper, Consts.DATE_PATTERN);
        queryHelper.wrapperQueryByRole(wrapper, orgId);
        Page<Map<String, Object>> page = examinationHealthService.selectMapsPage(
                new Page<>(offset / limit + 1, limit, Consts.DEFAULT_SORT_FIELD, Consts.DEFAULT_SORT_ORDER_IS_ASC), wrapper);
        
        commonPatientInfoService.replaceIrritabilityHistory(page.getRecords());
        
        return MapUtil.createSuccessMap("rows", page.getRecords(), "total", page.getTotal());
    }

    @RequestMapping(value = "/findEcgPathById")
    @ResponseBody
    public Object findEcgPathById(@RequestParam(required=true) Integer id) {
        Wrapper<ExaminationInternalMedicine> wrapper = new EntityWrapper<>();
        wrapper.eq("health_examination_id", id);
        ExaminationInternalMedicine examinationInternalMedicine = this.internalMedicineService.selectOne(wrapper);
        return MapUtil.createSuccessMap("data", examinationInternalMedicine);
    }
    
    /**
     * 新增体检信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ExaminationHealth examinationHealth, ExaminationAssay assay, ExaminationBUltrasonic bUltrasonic,
            ExaminationCommon common, ExaminationEnt ent, ExaminationGynaecologyAndObstetrics go,
            ExaminationInternalMedicine internalMedicine, ExaminationOphthalmology ophthalmology,
            ExaminationStomatology stomatology, ExaminationSurgery surgery, String oldFile) {
        Date now = new Date();
        examinationHealth.setCreateDate(now);
        examinationHealth.setUpdateDate(now);
        examinationHealthService.insert(examinationHealth);
        
        int healthExaminationId = examinationHealth.getId();
        assay.setHealthExaminationId(healthExaminationId);
        assayService.insert(assay);
        
        bUltrasonic.setHealthExaminationId(healthExaminationId);
        this.bUltrasonicService.insert(bUltrasonic);
        
        common.setHealthExaminationId(healthExaminationId);
        this.commonService.insert(common);
        
        ent.setHealthExaminationId(healthExaminationId);
        this.entService.insert(ent);
        
        go.setHealthExaminationId(healthExaminationId);
        this.goService.insert(go);
        
        internalMedicine.setHealthExaminationId(healthExaminationId);
        internalMedicine.setEcgPath(oldFile);
        this.internalMedicineService.insert(internalMedicine);
        
        ophthalmology.setHealthExaminationId(healthExaminationId);
        this.ophthalmologyService.insert(ophthalmology);
        
        stomatology.setHealthExaminationId(healthExaminationId);
        this.stomatologyService.insert(stomatology);
        
        surgery.setHealthExaminationId(healthExaminationId);
        this.surgeryService.insert(surgery);
        
        //更新过敏史表
        this.iIrritabilityHistoryService.saveOrUpdate(examinationHealth.getUserNo(), common.getIrritabilityHistory());
        
        return SUCCESS_TIP;
    }

    /**
     * 删除体检信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer examinationHealthId) {
        examinationHealthService.deleteById(examinationHealthId);
        return SUCCESS_TIP;
    }

    /**
     * 修改体检信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ExaminationHealth examinationHealth, ExaminationAssay assay, ExaminationBUltrasonic bUltrasonic,
            ExaminationCommon common, ExaminationEnt ent, ExaminationGynaecologyAndObstetrics go,
            ExaminationInternalMedicine internalMedicine, ExaminationOphthalmology ophthalmology,
            ExaminationStomatology stomatology, ExaminationSurgery surgery, String oldFile) {
        examinationHealth.setUpdateDate(new Date());
        examinationHealthService.updateById(examinationHealth);
        
        int healthExaminationId = examinationHealth.getId();
        assay.setHealthExaminationId(healthExaminationId);
        assayService.updateAllColumnById(assay);
        
        bUltrasonic.setHealthExaminationId(healthExaminationId);
        this.bUltrasonicService.updateAllColumnById(bUltrasonic);
        
        common.setHealthExaminationId(healthExaminationId);
        this.commonService.updateAllColumnById(common);
        
        ent.setHealthExaminationId(healthExaminationId);
        this.entService.updateAllColumnById(ent);
        
        go.setHealthExaminationId(healthExaminationId);
        this.goService.updateAllColumnById(go);
        
        internalMedicine.setHealthExaminationId(healthExaminationId);
        ExaminationInternalMedicine tmp = this.internalMedicineService.selectById(internalMedicine.getInternalId());
        if (tmp != null && !StringUtil.isNullEmpty(tmp.getEcgPath())) {
            try {
                String fileSavePath = gunsProperties.getFileUploadPath();
                FileUtil.deleteFile(new File(fileSavePath + tmp.getEcgPath()));                    
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        internalMedicine.setEcgPath(oldFile);
        this.internalMedicineService.updateAllColumnById(internalMedicine);
        
        ophthalmology.setHealthExaminationId(healthExaminationId);
        this.ophthalmologyService.updateAllColumnById(ophthalmology);
        
        stomatology.setHealthExaminationId(healthExaminationId);
        this.stomatologyService.updateAllColumnById(stomatology);
        
        surgery.setHealthExaminationId(healthExaminationId);
        this.surgeryService.updateAllColumnById(surgery);
        
        //更新过敏史表
        this.iIrritabilityHistoryService.saveOrUpdate(examinationHealth.getUserNo(), common.getIrritabilityHistory());
        return SUCCESS_TIP;
    }

    /**
     * 体检信息详情
     */
    @RequestMapping(value = "/detail/{examinationHealthId}")
    @ResponseBody
    public Object detail(@PathVariable("examinationHealthId") Integer examinationHealthId) {
        return examinationHealthService.selectById(examinationHealthId);
    }
}
