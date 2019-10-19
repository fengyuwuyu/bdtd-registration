package com.bdtd.card.registration.modular.comprehensive;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bdtd.card.registration.common.model.EnumHandleTypeMask;
import com.bdtd.card.registration.common.model.EnumPatientInfoStatus;
import com.bdtd.card.registration.common.model.EnumPatientTransferStatus;
import com.bdtd.card.registration.common.model.EnumSickRestDay;
import com.bdtd.card.registration.common.model.EnumSickRestType;
import com.bdtd.card.registration.common.model.chart.CylinderShapeEntity;
import com.bdtd.card.registration.modular.inhospital.service.IPatientInHospitalService;
import com.bdtd.card.registration.modular.transfer.service.IPatientTransferTreatmentService;
import com.bdtd.card.registration.modular.treatment.service.IPatientInfoService;
import com.bdtd.card.registration.modular.treatment.service.IPatientSickRestService;
import com.stylefeng.guns.core.common.annotion.DictEntity;
import com.stylefeng.guns.core.common.annotion.DictHandler;
import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.DictCacheFactory;
import com.stylefeng.guns.core.util.DownLoadUtil;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.core.util.QueryHelper;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.core.wrapper.DictWrapperEntity;
import com.stylefeng.guns.scmmain.service.IDtDepService;

@RequestMapping("/comprehensive")
@Controller
public class ComprehensiveQueryController {

    private static final String PREFIX = "/comprehensive/";
    
    @Autowired
    private IPatientInHospitalService patientInHospitalService;
    @Autowired
    private IPatientSickRestService sickRestService;
    @Autowired
    private IPatientTransferTreatmentService transferTreatmentService;
    @Autowired
    private IPatientInfoService patientInfoService;
    @Autowired
    private QueryHelper queryHelper;
    @Autowired
    private IDtDepService depService;
    @Autowired
    private DictCacheFactory dictCacheFactory;
    
    @Value("${guns.file-upload-path}")
    private String tmpPath;

    @RequestMapping("/patientInfo")
    @DictHandler(dictModels = { DictConsts.REGISTRATION_MAIN_DIAGNOSIS, DictConsts.USER_DUTY }, dictKeys = {
            DictConsts.REGISTRATION_MAIN_DIAGNOSIS_KEY, DictConsts.USER_DUTY_KEY })
    public String patientInfo(Model model) {
        model.addAttribute("handleTypeItemList", EnumHandleTypeMask.select2());
        model.addAttribute("dtDepItemList", this.depService.select());
        return PREFIX + "patientInfo/patientInfo.html";
    }
    
    @RequestMapping("/trainWound")
    @DictHandler(dictModels = { DictConsts.REGISTRATION_MAIN_DIAGNOSIS, DictConsts.USER_DUTY }, dictKeys = {
            DictConsts.REGISTRATION_MAIN_DIAGNOSIS_KEY, DictConsts.USER_DUTY_KEY })
    public String trainWound(Model model) {
        model.addAttribute("dtDepItemList", this.depService.select());
        model.addAttribute("handleTypeItemList", EnumHandleTypeMask.select());
        return PREFIX + "patientInfo/patientInfoTrainWound.html";
    }
    
    @RequestMapping("/fever")
    @DictHandler(dictModels = { DictConsts.REGISTRATION_MAIN_DIAGNOSIS, DictConsts.USER_DUTY }, dictKeys = {
            DictConsts.REGISTRATION_MAIN_DIAGNOSIS_KEY, DictConsts.USER_DUTY_KEY })
    public String fever(Model model) {
        model.addAttribute("dtDepItemList", this.depService.select());
        model.addAttribute("inhospitalStatusDictList", Arrays.asList(
                MapUtil.createMap("id", "待住院", "name", "待住院"),
                MapUtil.createMap("id", "住院", "name", "住院"),
                MapUtil.createMap("id", "出院", "name", "出院"),
                MapUtil.createMap("id", "其他", "name", "其他")
                ));
        model.addAttribute("handleTypeItemList", EnumHandleTypeMask.select());
        return PREFIX + "patientInfo/patientInfoFever.html";
    }
    
    @RequestMapping("/transferInfo")
    @DictHandler(dictKeys= {DictConsts.DISPOSITION_KEY, DictConsts.USER_DUTY_KEY}, dictModels= {DictConsts.DISPOSITION, DictConsts.USER_DUTY})
    public String transferInfo(Model model) {
        model.addAttribute("dtDepItemList", this.depService.select());
        return PREFIX + "patientTransferTreatment/transferInfo.html";
    }
    
    @RequestMapping("/openTransferChart")
    public String openTransferChart(Model model) {
        return PREFIX + "patientTransferTreatment/transferChart.html";
    }
    
    @RequestMapping("/openPatientInfoChart")
    public String openPatientInfoChart(Model model) {
        return PREFIX + "patientInfo/patientInfoChart.html";
    }
    
    @RequestMapping("/sickRestInfo")
    @DictHandler(dictKeys= {DictConsts.USER_DUTY_KEY, DictConsts.REGISTRATION_MAIN_DIAGNOSIS_KEY}, dictModels= {DictConsts.USER_DUTY, DictConsts.REGISTRATION_MAIN_DIAGNOSIS})
    public String sickRestInfo(Model model) {
        model.addAttribute("sickRestTypeItemList", EnumSickRestType.select());
        model.addAttribute("sickRestDayItemList", EnumSickRestDay.select());
        model.addAttribute("dtDepItemList", this.depService.select());
        return PREFIX + "patientSickRest/patientSickRest.html";
    }
    
    @RequestMapping("/openSickRestChart")
    public String openSickRestChart(Model model) {
        return PREFIX + "patientSickRest/sickRestChart.html";
    }
    
    @RequestMapping("/openTrainWoundChart")
    public String openTrainWoundChart(Model model) {
        return PREFIX + "patientInfo/trainWoundChart.html";
    }
    
    @RequestMapping("/openFeverChart")
    public String openFeverChart(Model model) {
        return PREFIX + "patientInfo/feverChart.html";
    }
    
    @RequestMapping("/openInhospitalChart")
    public String openInhospitalChart(Model model) {
        return PREFIX + "patientInHospital/inhospitalChart.html";
    }
    
    @RequestMapping(value = "/monthlyWoundChart")
    @ResponseBody
    public Object monthlyWoundChart() {
        List<String> recentlyMonth = DateUtil.getRecentlyMonthDayStr(12, null);
        String beginDate = recentlyMonth.get(0);
        String endDate =  recentlyMonth.get(1);
        return patientInHospitalService.monthlyWoundChart(beginDate, endDate);
    }
    
    @RequestMapping(value = "/woundAbsentChart")
    @ResponseBody
    public Object woundAbsentChart() {
        return patientInHospitalService.woundAbsentChart();
    }
    
    @RequestMapping("/inhospitalInfo")
    @DictHandler(dictKeys= {DictConsts.USER_DUTY_KEY}, dictModels= {DictConsts.USER_DUTY})
    public String inhospitalInfo(Model model) {
        List<Map<String, Object>> statusItemList = Arrays.asList(MapUtil.createMap("id", "本院", "name", "本院"), MapUtil.createMap("id", "外院", "name", "外院"));
        model.addAttribute("statusItemList", statusItemList);
        model.addAttribute("dtDepItemList", this.depService.select());
        return PREFIX + "patientInHospital/patientInHospital.html";
    }
    
    /**
     * 获取住院列表
     */
    @RequestMapping(value = "/inhospitalList")
    @ResponseBody
    @DictHandler(dictWrappers= {@DictEntity(parentId=DictConsts.REGISTRATION_MAIN_DIAGNOSIS, fieldName=DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME)})
    public Object list(String status, String userName, Long orgId, String userDuty, String enrolDate, Integer offset, Integer limit) {
        Map<String, Object> params = MapUtil.createMap("status", status, "userName", userName, "userDuty", userDuty, 
                "enrolDate", StringUtil.isNullEmpty(enrolDate) ? null : DateUtil.parse(enrolDate, Consts.DATE_PATTERN), "offset", offset, "limit", limit);

        queryHelper.wrapperQueryByRole(params, orgId);
        long total = patientInHospitalService.countInhospitalByMap(params);
        if (total == 0) {
            return MapUtil.createSuccessMap("rows", Collections.emptyList(), "total", total);
        }

        List<Map<String, Object>> list = patientInHospitalService.findInhospitalByMap(params);
        return MapUtil.createSuccessMap("rows", list, "total", total);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(String status, String userName, Long orgId, String userDuty, String enrolDate, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = (Map<String, Object>) this.list(status, userName, orgId, userDuty, enrolDate, null, null);

        List<Map<String, Object>> rows = (List<Map<String, Object>>) resultMap.get("rows");
        List<DictWrapperEntity> dictwrapperEntities = Arrays.asList(
                new DictWrapperEntity(DictConsts.REGISTRATION_MAIN_DIAGNOSIS, DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME)
                );
        this.dictCacheFactory.wrapper(rows, dictwrapperEntities);

        String fileName = "住院信息.xls";
        String[] headers = { "状态", "编号", "姓名", "性别", "身份", "单位", "入伍时间", "主要诊断", "次要诊断", "入院时间", "出院时间"};
        String[] fields = { "status", "userNo", "userName", "userSex", "userDuty", "orgName", "enrolDate", "mainDiagnosis", "minorDiagnosis", "inHospitalDate", "leaveHospitalDate"};
        DownLoadUtil.exportExcelAndDownload("住院信息", headers, fields, rows, response, request, tmpPath, fileName);
    }

    @RequestMapping(value = "/inhospitalChart")
    @ResponseBody
    public Object inhospitalChart(Date beginDate, Date endDate) {
        Map<String, Object> param = MapUtil.createMap("beginDate", beginDate, "endDate", endDate);
        queryHelper.wrapperQueryByRole(param, null);
        CylinderShapeEntity<Long> entity = patientInHospitalService.inhospitalChart(param);
        return MapUtil.createSuccessMap("data", entity);
    }
    
    @RequestMapping(value = "/patientInfoChart")
    @ResponseBody
    public Object patientInfoChart(Date beginDate, Date endDate) {
        Map<String, Object> param = MapUtil.createMap("beginDate", beginDate, "endDate", endDate, "status", Arrays.asList(EnumPatientInfoStatus.HAS_TAKE_MEDICINE.getType(), EnumPatientInfoStatus.DIAGNOSISED.getType()));
        queryHelper.wrapperQueryByRole(param, null);
        CylinderShapeEntity<Integer> entity = this.patientInfoService.patientInfoChart(param);
        return MapUtil.createSuccessMap("data", entity);
    }
    
    @RequestMapping(value = "/transferChart")
    @ResponseBody
    public Object transferChart(Date beginDate, Date endDate) {
        Map<String, Object> param = MapUtil.createMap("beginDate", beginDate, "endDate", endDate, "status", Arrays.asList(EnumPatientTransferStatus.REPORTED.getType(), EnumPatientTransferStatus.COLLECTED.getType()));
        queryHelper.wrapperQueryByRole(param, null);
        CylinderShapeEntity<Integer> entity = this.transferTreatmentService.transferChart(param);
        return MapUtil.createSuccessMap("data", entity);
    }
    
    @RequestMapping(value = "/sickRestChart")
    @ResponseBody
    public Object sickRestChart(Date beginDate, Date endDate) {
        Map<String, Object> param = MapUtil.createMap("beginDate", beginDate, "endDate", endDate);
        queryHelper.wrapperQueryByRole(param, null);
        CylinderShapeEntity<Integer> entity = sickRestService.sickRestChart(param);
        return MapUtil.createSuccessMap("data", entity);
    }
    
    @RequestMapping(value = "/trainWoundChart")
    @ResponseBody
    public Object trainWoundChart(Date beginDate, Date endDate) {
        Map<String, Object> param = MapUtil.createMap("beginDate", beginDate, "endDate", endDate, "trainWound", 1);
        queryHelper.wrapperQueryByRole(param, null);
        CylinderShapeEntity<Integer> entity = this.patientInfoService.trainWoundChart(param);
        return MapUtil.createSuccessMap("data", entity);
    }
    
    @RequestMapping(value = "/feverChart")
    @ResponseBody
    public Object feverChart(Date beginDate, Date endDate) {
        Map<String, Object> param = MapUtil.createMap("beginDate", beginDate, "endDate", endDate, "fever", 1);
        queryHelper.wrapperQueryByRole(param, null);
        CylinderShapeEntity<Integer> entity = this.patientInfoService.feverChart(param);
        return MapUtil.createSuccessMap("data", entity);
    }
    
}
