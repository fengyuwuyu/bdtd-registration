package com.bdtd.card.registration.modular.transfer.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bdtd.card.registration.common.model.EnumHandleTypeMask;
import com.bdtd.card.registration.common.model.EnumPatientTransferStatus;
import com.bdtd.card.registration.modular.transfer.service.IPatientTransferTreatmentService;
import com.bdtd.card.registration.modular.treatment.service.IPatientInfoService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.common.annotion.DictEntity;
import com.stylefeng.guns.core.common.annotion.DictHandler;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.modular.system.model.PatientTransferTreatment;
import com.stylefeng.guns.scmmain.service.IDtDepService;

/**
 * 转诊控制器
 *
 * @author
 * @Date 2018-06-26 09:14:13
 */
@Controller
@RequestMapping("/patientTransferTreatment")
public class PatientTransferTreatmentController extends BaseController {

    private String PREFIX = "/transfer/patientTransferTreatment/";
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IPatientTransferTreatmentService patientTransferTreatmentService;
    @Autowired
    private IPatientInfoService patientInfoService;
    @Autowired
    private IDtDepService depService;

    /**
     * 跳转到转诊首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("dtDepItemList", this.depService.select());
        return PREFIX + "patientTransferTreatment.html";
    }

    /**
     * 跳转到修改转诊
     */
    @RequestMapping("/openBillingDetail")
    @DictHandler(dictModels = { DictConsts.TRANSFER_HOSPITAL }, dictKeys = { DictConsts.TRANSFER_HOSPITAL_KEY })
    public String patientTransferTreatmentUpdate(Model model) {
        model.addAttribute("now", new java.sql.Date(System.currentTimeMillis()));
        return PREFIX + "patientTransferTreatment_billing.html";
    }
    
    /**
     * 跳转到修改转诊
     */
    @RequestMapping("/openUpdateRemark/{id}")
    public String patientTransferTreatmentUpdate(@PathVariable Integer id, Model model) {
        PatientTransferTreatment transferTreatment = this.patientTransferTreatmentService.selectById(id);
        model.addAttribute("transferTreatment", transferTreatment);
        return PREFIX + "patientTransferTreatment_updateRemark.html";
    }
    
    @RequestMapping("/updateRemark")
    @ResponseBody
    public Map<String, Object> updateRemark(PatientTransferTreatment transferTreatment) {
        this.patientTransferTreatmentService.updateById(transferTreatment);
        return MapUtil.createSuccessMap();
    }

    /**
     * 获取转诊列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @DictHandler(dictWrappers = {
            @DictEntity(parentId = DictConsts.REGISTRATION_MAIN_DIAGNOSIS, fieldName = DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME) })
    public Object list(String userName, Long orgId, String preTransferDate, Integer offset, Integer limit) {
        Map<String, Object> params = MapUtil.createMap("userName", userName, "orgId", orgId,
                "preTransferDate", preTransferDate, "type", 1, "status",
                Arrays.asList(EnumPatientTransferStatus.TRANSFERING.getType()), "offset", offset, "limit", limit);
        long total = patientTransferTreatmentService.countByMap(params);
        if (total == 0) {
            return MapUtil.createSuccessMap("rows", Collections.emptyList(), "total", total);
        }
        List<Map<String, Object>> result = patientTransferTreatmentService.findByMap(params);
        return MapUtil.createSuccessMap("rows", result, "total", total);
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
        
        int count = this.patientTransferTreatmentService.countByPatientInfoId(MapUtil.createMap("patientInfoId", patientTransferTreatment.getPatientInfoId()));
        if (count > 0) {
            return new Tip(500, "该患者已转诊！");
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
     * 修改转诊
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Integer hospital, String billingDate, String ids) {
        List<Integer> idList = Convert.stringToIntegerList(ids);
        patientTransferTreatmentService.updateByMap(MapUtil.createMap("status",
                EnumPatientTransferStatus.BILLED.getType(), "idList", idList, "hospital", hospital, "billingDate",
                DateUtil.parse(billingDate, "yyyy-MM-dd"), "billingNo", ShiroKit.getUser().getDtUser().getUserNo(),
                "billingName", ShiroKit.getUser().getDtUser().getUserLname()));
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
