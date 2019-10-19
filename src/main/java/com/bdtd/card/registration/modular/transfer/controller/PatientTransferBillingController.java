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
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.CommonUtils;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.DictCacheFactory;
import com.stylefeng.guns.core.util.DownLoadUtil;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.core.wrapper.DictWrapperEntity;
import com.stylefeng.guns.modular.system.model.PatientTransferTreatment;
import com.stylefeng.guns.scmmain.service.IDtDepService;

/**
 * 转诊控制器
 *
 * @author
 * @Date 2018-06-26 09:14:13
 */
@Controller
@RequestMapping("/patientTransferBilling")
@ConditionalOnBean(value=DictCacheFactory.class)
public class PatientTransferBillingController extends BaseController {

    private String PREFIX = "/transfer/patientTransferBilling/";
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IPatientTransferTreatmentService patientTransferTreatmentService;
    @Autowired
    private IPatientInfoService patientInfoService;
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
    public String index(Model model) {
        model.addAttribute("dtDepItemList", this.depService.select());
        return PREFIX + "patientTransferBilling.html";
    }

    /**
     * 获取转诊列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @DictHandler(dictWrappers = {
            @DictEntity(parentId = DictConsts.REGISTRATION_MAIN_DIAGNOSIS, fieldName = DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME),
            @DictEntity(parentId = DictConsts.TRANSFER_HOSPITAL, fieldName = DictConsts.TRANSFER_HOSPITAL_FIELD_NAME) })
    public Object list(String userName, Long orgId, Integer status, String billBeginDate, String billEndDate,
            String collectBeginDate, String collectEndDate, Integer offset, Integer limit) {
        Map<String, Object> params = MapUtil.createMap("userName", userName, "orgId", orgId, "type", 2, "status",
                status == null ? Arrays.asList(EnumPatientTransferStatus.BILLED.getType(),
                        EnumPatientTransferStatus.COLLECTED.getType()) : Arrays.asList(status),
                "offset", offset, "limit", limit);
        CommonUtils.handleQueryDateSection("billBeginDate", "billEndDate", billBeginDate, billEndDate, params,
                Consts.DATE_PATTERN);
        CommonUtils.handleQueryDateSection("collectBeginDate", "collectEndDate", collectBeginDate, collectEndDate,
                params, Consts.DATE_PATTERN);
        long total = patientTransferTreatmentService.countByMap(params);
        if (total == 0) {
            return MapUtil.createSuccessMap("rows", Collections.emptyList(), "total", total);
        }
        List<Map<String, Object>> result = patientTransferTreatmentService.findByMap(params);
        return MapUtil.createSuccessMap("rows", result, "total", total);
    }

    @RequestMapping("/exportExcel")
    @SuppressWarnings("unchecked")
    public void exportExcel(String userName, Long orgId, Integer status, String billBeginDate, String billEndDate,
            String collectBeginDate, String collectEndDate, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = (Map<String, Object>) this.list(userName, orgId, status, billBeginDate,
                billEndDate, collectBeginDate, collectEndDate, null, null);
        List<Map<String, Object>> rows = (List<Map<String, Object>>) resultMap.get("rows");
        List<DictWrapperEntity> dictwrapperEntities = Arrays.asList(
                new DictWrapperEntity(DictConsts.REGISTRATION_MAIN_DIAGNOSIS, DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME), 
                new DictWrapperEntity(DictConsts.TRANSFER_HOSPITAL, DictConsts.TRANSFER_HOSPITAL_FIELD_NAME)
                );
        dictCacheFactory.wrapper(rows, dictwrapperEntities);
        
        EnumAdapterFactory.adapterRows(rows, Arrays.asList(new EnumAdapterEntity("status", "EnumPatientTransferStatus")));

        String fileName = "开单汇总.xls";
        String[] headers = {"状态", "编号", "姓名", "单位", "身份", "初步诊断", "初诊军医", "转往医院", "开单人", "开单时间", "汇总人", "汇总时间"};
        String[] fields = {"status", "userNo", "userName", "orgName", "userDuty", "mainDiagnosis", "physicianName", "hospital", "billingName", "billingDate", "collectName", "collectDate"};
        log.debug(rows.toString());
        DownLoadUtil.exportExcelAndDownload("开单汇总", headers, fields, rows, response, request, tmpPath, fileName);
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
    public Object delete(@RequestParam(required = true) String ids) {
        List<Integer> idList = Convert.stringToIntegerList(ids);
        List<PatientTransferTreatment> patientTransferTreatmentList = patientTransferTreatmentService
                .selectBatchIds(idList);
        for (PatientTransferTreatment patientTransferTreatment : patientTransferTreatmentList) {
            if (patientTransferTreatment != null) {
                patientInfoService.cancelHandleType(patientTransferTreatment.getPatientInfoId(),
                        EnumHandleTypeMask.TRANSFER_TREATMENT);
            }
        }
        patientTransferTreatmentService.deleteBatchIds(idList);
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
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(String ids) {
        List<Integer> idList = Convert.stringToIntegerList(ids);
        patientTransferTreatmentService
                .updateByMap(MapUtil.createMap("status", EnumPatientTransferStatus.COLLECTED.getType(), "idList",
                        idList, "collectNo", ShiroKit.getUser().getDtUser().getUserNo(), "collectDate",
                        new java.sql.Date(System.currentTimeMillis()), "collectName",
                        ShiroKit.getUser().getDtUser().getUserLname(), "transferDate",
                        new java.sql.Date(System.currentTimeMillis() + DateUtil.ONE_DAY_TIME)));
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
