package com.bdtd.card.registration.modular.transfer.controller;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.bdtd.card.registration.common.model.EnumHandleTypeMask;
import com.bdtd.card.registration.common.model.EnumPatientTransferStatus;
import com.bdtd.card.registration.common.utils.EnumAdapterFactory;
import com.bdtd.card.registration.common.utils.model.EnumAdapterEntity;
import com.bdtd.card.registration.modular.transfer.service.IPatientTransferTreatmentService;
import com.bdtd.card.registration.modular.treatment.service.IPatientInfoService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.common.annotion.DictEntity;
import com.stylefeng.guns.core.common.annotion.DictHandler;
import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.util.CommonUtils;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.DictCacheFactory;
import com.stylefeng.guns.core.util.DownLoadUtil;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.core.util.QueryHelper;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.core.wrapper.DictWrapperEntity;
import com.stylefeng.guns.modular.system.model.PatientInfo;
import com.stylefeng.guns.modular.system.model.PatientTransferTreatment;
import com.stylefeng.guns.scmmain.service.IDtDepService;

/**
 * 转诊控制器
 *
 * @author
 * @Date 2018-06-26 09:14:13
 */
@Controller
@RequestMapping("/patientTransferReport")
public class PatientTransferReportController extends BaseController {

    private String PREFIX = "/transfer/patientTransferReport/";
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IPatientTransferTreatmentService patientTransferTreatmentService;
    @Autowired
    private IPatientInfoService patientInfoService;
    @Autowired
    private QueryHelper queryHelper;
    @Autowired
    private DictCacheFactory dictCacheFactory;
    @Autowired
    private IDtDepService depService;
    
    @Value("${guns.file-upload-path}")
    private String tmpPath;

    /**
     * 跳转到转诊首页
     */
    @RequestMapping("")
    @DictHandler(dictKeys = { DictConsts.DISPOSITION_KEY }, dictModels = { DictConsts.DISPOSITION })
    public String index(Model model) {
        model.addAttribute("dtDepItemList", this.depService.select());
        return PREFIX + "patientTransferReport.html";
    }

    @RequestMapping("/openReportDetail/{id}")
    @DictHandler(dictKeys = { DictConsts.DISPOSITION_KEY }, dictModels = { DictConsts.DISPOSITION })
    public String openReportDetail(@PathVariable Integer id, Model model) {
        PatientTransferTreatment transferTreatment = patientTransferTreatmentService.selectById(id);
        model.addAttribute("item", transferTreatment);
        if (transferTreatment != null) {
            PatientInfo info = patientInfoService.selectById(transferTreatment.getPatientInfoId());
            model.addAttribute("patientInfo", info);
        }
        return PREFIX + "patientTransferReport_reportDetail.html";
    }
    
    @RequestMapping("/openReportDetail1/{id}")
    @DictHandler(dictKeys = { DictConsts.DISPOSITION_KEY }, dictModels = { DictConsts.DISPOSITION })
    public String openReportDetail1(@PathVariable Integer id, Model model) {
        PatientTransferTreatment transferTreatment = patientTransferTreatmentService.selectById(id);
        model.addAttribute("item", transferTreatment);
        if (transferTreatment != null) {
            PatientInfo info = patientInfoService.selectById(transferTreatment.getPatientInfoId());
            model.addAttribute("patientInfo", info);
        }
        return PREFIX + "patientTransferReport_reportDetail1.html";
    }

    @RequestMapping("/openInitialDiagnosis/{patientInfoId}")
    @DictHandler(dictKeys = { DictConsts.REGISTRATION_MAIN_DIAGNOSIS_KEY }, dictModels = { DictConsts.REGISTRATION_MAIN_DIAGNOSIS })
    public String openInitialDiagnosis(@PathVariable Integer patientInfoId, Model model) {
        Wrapper<PatientInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("id", patientInfoId);
        Map<String, Object> map = patientInfoService.selectMap(wrapper);
        Integer mainDiagnosis = (Integer) map.get("mainDiagnosis");
        Map<String, Object> dict = dictCacheFactory.getDictMapByParentNameAndNum(46, mainDiagnosis);
        map.put("mainDiagnosis", dict.get("name"));
        model.addAttribute("item", map);
        return PREFIX + "patientTransferReport_initialDiagnosis.html";
    }

    @RequestMapping("/openBillDetail/{id}")
    public String openBillDetail(@PathVariable Integer id, Model model) {
        PatientTransferTreatment transferTreatment = patientTransferTreatmentService.selectById(id);
        model.addAttribute("item", transferTreatment);
        return PREFIX + "patientTransferReport_billingDetail.html";
    }

    @RequestMapping("/openDispositionDetail/{id}")
    @DictHandler(dictKeys = { DictConsts.DISPOSITION_KEY }, dictModels = { DictConsts.DISPOSITION })
    public String openDispositionDetail(@PathVariable Integer id, Model model) {
        PatientTransferTreatment transferTreatment = patientTransferTreatmentService.selectById(id);
        model.addAttribute("item", transferTreatment);
        return PREFIX + "patientTransferReport_dispositionDetail.html";
    }

    /**
     * 获取转诊列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @DictHandler(dictWrappers = {
            @DictEntity(parentId = DictConsts.REGISTRATION_MAIN_DIAGNOSIS, fieldName = DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME),
            @DictEntity(parentId = DictConsts.DISPOSITION, fieldName = DictConsts.DISPOSITION_FIELD_NAME),
            @DictEntity(parentId = DictConsts.TRANSFER_HOSPITAL, fieldName = DictConsts.TRANSFER_HOSPITAL_FIELD_NAME) })
    public Object list(String userName, Long orgId, Integer status, String transferBeginDate, String transferEndDate, String leaveHospitalBeginDate, String leaveHospitalEndDate, 
            String userDuty, String enrolDate, Integer disposition, Integer offset, Integer limit) {
        Map<String, Object> params = MapUtil.createMap("userName", userName, "userDuty",
                userDuty, "enrolDate", StringUtil.isNullEmpty(enrolDate) ? null : DateUtil.parse(enrolDate, Consts.DATE_PATTERN), "status",
                status == null ? Arrays.asList(EnumPatientTransferStatus.REPORTED.getType(),
                        EnumPatientTransferStatus.COLLECTED.getType()) : Arrays.asList(status), "type", 3, "disposition", disposition,
                "offset", offset, "limit", limit);
        CommonUtils.handleQueryDateSection("transferBeginDate", "transferEndDate", transferBeginDate, transferEndDate,
                params, Consts.DATE_PATTERN);
        CommonUtils.handleQueryDateSection("leaveHospitalBeginDate", "leaveHospitalEndDate", leaveHospitalBeginDate, leaveHospitalEndDate,
                params, Consts.DATE_PATTERN);
        queryHelper.wrapperQueryByRole(params, orgId);
        long total = patientTransferTreatmentService.countByMap(params);
        if (total == 0) {
            return MapUtil.createSuccessMap("rows", Collections.emptyList(), "total", total);
        }
        List<Map<String, Object>> result = patientTransferTreatmentService.findByMap(params);
        return MapUtil.createSuccessMap("rows", result, "total", total);
    }

    /**
     * 获取转诊列表
     * @throws Exception 
     */
    @RequestMapping(value = "/exportExcel")
    @DictHandler(dictWrappers = {
            @DictEntity(parentId = DictConsts.REGISTRATION_MAIN_DIAGNOSIS, fieldName = DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME),
            @DictEntity(parentId = DictConsts.TRANSFER_HOSPITAL, fieldName = DictConsts.TRANSFER_HOSPITAL_FIELD_NAME) })
    public void exportExcel(String userName, Long orgId, Integer status, String transferBeginDate, String transferEndDate,
            String userDuty, String enrolDate, HttpServletRequest request, HttpServletResponse response, Integer disposition) throws Exception {
        Map<String, Object> params = MapUtil.createMap("userName", userName, "userDuty",
                userDuty, "enrolDate", StringUtil.isNullEmpty(enrolDate) ? null : DateUtil.parse(enrolDate, Consts.DATE_PATTERN), "disposition", disposition, "status",
                status == null ? Arrays.asList(EnumPatientTransferStatus.REPORTED.getType(),
                        EnumPatientTransferStatus.COLLECTED.getType()) : Arrays.asList(status), "type", 3, "orgId", orgId);
        CommonUtils.handleQueryDateSection("transferBeginDate", "transferEndDate", transferBeginDate, transferEndDate, params, Consts.DATE_PATTERN);
        List<Map<String, Object>> rows = patientTransferTreatmentService.findByMap(params);
        List<DictWrapperEntity> dictwrapperEntities = Arrays.asList(
                new DictWrapperEntity(DictConsts.REGISTRATION_MAIN_DIAGNOSIS, DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME),
                new DictWrapperEntity(DictConsts.DISPOSITION, DictConsts.DISPOSITION_FIELD_NAME),
                new DictWrapperEntity(DictConsts.TRANSFER_HOSPITAL, DictConsts.TRANSFER_HOSPITAL_FIELD_NAME)
                );
        this.dictCacheFactory.wrapper(rows, dictwrapperEntities);
        
        List<EnumAdapterEntity> enumAdapters = Arrays.asList(
                new EnumAdapterEntity("fever", "EnumOriginMask"), 
                new EnumAdapterEntity("status", "EnumPatientTransferStatus"), 
                new EnumAdapterEntity("trainWound", "EnumOriginMask"));
        EnumAdapterFactory.adapterRows(rows, enumAdapters);
        
        String fileName = "转诊信息.xls";
        String[] headers = {"状态", "编号", "姓名", "单位", "身份", "入伍时间", "就诊时间", "过敏史", "病情摘要", "主要诊断", "次要诊断", "发热", "训练伤", "诊断军医", "转诊医院", "开单人", "开单时间", 
                "开单备注", "拟转时间", "转诊时间", "汇总人", "汇总时间", "医院诊断", "手术名称", 
                "去向", "治疗随访", "科室", "病区", "床位", "医师", "住院备注", "出院日期", "回报日期"};
        String[] fields = {"status", "userNo", "userName", "orgName", "userDuty", "enrolDate", "clinicDate", "irritabilityHistory", "abstractIllness", "mainDiagnosis", 
                "minorDiagnosis", "fever", "trainWound", "physicianName", "hospital", "billingName", "billingDate", "billingRemark", "preTransferDate", 
                "transferDate", "collectName", "collectDate", "hospitalDiagnosis", "operationName", "disposition", "treatmentVisit", "inHospitalInfoDept", 
                "inHospitalInfoSection", "inHospitalInfoBed", "inHospitalInfoPhysician", "inHospitalInfoRemark", "leaveHospitalDate", "reportDate"};
        DownLoadUtil.exportExcelAndDownload("转诊信息", headers, fields, rows, response, request, tmpPath, fileName);
    }

    /**
     * 新增转诊
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @Transactional
    public Object add(PatientTransferTreatment patientTransferTreatment) {
        if (patientTransferTreatment == null || patientTransferTreatment.getPatientInfoId() == null) {
            return new Tip(500, "非法请求！");
        }
        patientTransferTreatment.setStatus(EnumPatientTransferStatus.TRANSFERING.getType());
        Date createDate = new Date();
        patientTransferTreatment.setCreateDate(createDate);
        patientTransferTreatment.setUpdateDate(createDate);
        patientTransferTreatment.setPreTransferDate(new java.sql.Date(createDate.getTime() + DateUtil.ONE_DAY_TIME));
        patientTransferTreatmentService.insert(patientTransferTreatment);
        patientInfoService.updateHandleType(patientTransferTreatment.getPatientInfoId(),
                EnumHandleTypeMask.TRANSFER_TREATMENT);
        return SUCCESS_TIP;
    }

    /**
     * 删除转诊
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @Transactional
    public Object delete(@RequestParam(required = true) Integer id) {
        PatientTransferTreatment patientTransferTreatment = patientTransferTreatmentService.selectById(id);
        if (patientTransferTreatment != null) {
            patientInfoService.cancelHandleType(patientTransferTreatment.getPatientInfoId(),
                    EnumHandleTypeMask.TRANSFER_TREATMENT);
        }
        patientTransferTreatmentService.deleteById(id);
        return SUCCESS_TIP;
    }

    /**
     * 删除转诊
     */
    @RequestMapping(value = "/cancelTransfer")
    @ResponseBody
    @Transactional
    public Object deleteByPatientInfoId(@RequestParam(required = true) Integer patientInfoId) {
        patientTransferTreatmentService.deleteByMap(MapUtil.createMap("patient_info_id", patientInfoId));
        patientInfoService.cancelHandleType(patientInfoId, EnumHandleTypeMask.TRANSFER_TREATMENT);
        return SUCCESS_TIP;
    }

    /**
     * 删除转诊
     */
    @RequestMapping(value = "/toPreTransferStatus")
    @ResponseBody
    @Transactional
    public Object toPreTransferStatus(@RequestParam(required = true) String ids) {
        List<Integer> idList = Convert.stringToIntegerList(ids);
        patientTransferTreatmentService.toPreTransferStatus(
                MapUtil.createMap("status", EnumPatientTransferStatus.TRANSFERING.getType(), "idList", idList));
        return SUCCESS_TIP;
    }

    /**
     * 修改转诊
     * 
     * @param entity
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PatientTransferTreatment entity) {
        entity.setStatus(EnumPatientTransferStatus.REPORTED.getType());
        Date now = new Date();
        entity.setUpdateDate(now);
        entity.setReportDate(now);
        patientTransferTreatmentService.updateById(entity);
        return SUCCESS_TIP;
    }

    /**
     * 转诊详情
     */
    @RequestMapping(value = "/detail/{patientTransferTreatmentId}")
    @ResponseBody
    public Object detail(@PathVariable("patientTransferTreatmentId") Integer patientTransferTreatmentId) {
        return patientTransferTreatmentService.selectById(patientTransferTreatmentId);
    }
}
