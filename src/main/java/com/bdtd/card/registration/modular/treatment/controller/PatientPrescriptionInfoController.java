package com.bdtd.card.registration.modular.treatment.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.bdtd.card.registration.modular.treatment.service.IPatientInfoService;
import com.bdtd.card.registration.modular.treatment.service.IPatientPrescriptionInfoService;
import com.bdtd.card.registration.modular.treatment.service.IPatientPrescriptionMedicineInfoService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.modular.system.model.PatientPrescriptionInfo;

/**
 * 患者处方控制器
 *
 * @author 
 * @Date 2018-06-26 09:09:51
 */
@Controller
@RequestMapping("/patientPrescriptionInfo")
public class PatientPrescriptionInfoController extends BaseController {

    private String PREFIX = "/treatment/patientPrescriptionInfo/";

    @Autowired
    private IPatientPrescriptionInfoService patientPrescriptionInfoService;
    @Autowired
    private IPatientPrescriptionMedicineInfoService prescriptionMedicineInfoService;
    
    @Autowired
    private IPatientInfoService patientInfoService;

    /**
     * 跳转到患者处方首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "patientPrescriptionInfo.html";
    }

    /**
     * 跳转到添加患者处方
     */
    @RequestMapping("/patientPrescriptionInfo_add")
    public String patientPrescriptionInfoAdd() {
        return PREFIX + "patientPrescriptionInfo_add.html";
    }

    /**
     * 跳转到修改患者处方
     */
    @RequestMapping("/patientPrescriptionInfo_update/{patientPrescriptionInfoId}")
    public String patientPrescriptionInfoUpdate(@PathVariable Integer patientPrescriptionInfoId, Model model) {
        PatientPrescriptionInfo patientPrescriptionInfo = patientPrescriptionInfoService.selectById(patientPrescriptionInfoId);
        model.addAttribute("item",patientPrescriptionInfo);
        LogObjectHolder.me().set(patientPrescriptionInfo);
        return PREFIX + "patientPrescriptionInfo_edit.html";
    }

    /**
     * 获取患者处方列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer offset, Integer limit) {
    	Wrapper<PatientPrescriptionInfo> wrapper = new EntityWrapper<>();
    	Page<Map<String, Object>> page = patientPrescriptionInfoService.selectMapsPage(new Page<>(offset / limit + 1, limit, Consts.DEFAULT_SORT_FIELD, Consts.DEFAULT_SORT_ORDER_IS_ASC), wrapper);
		return MapUtil.createSuccessMap("rows", page.getRecords(), "total", page.getTotal());
    }

    /**
     * 新增患者处方
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PatientPrescriptionInfo patientPrescriptionInfo) {
        long count = prescriptionMedicineInfoService.countByMap(MapUtil.createMap("patientInfoId", patientPrescriptionInfo.getPatientInfoId()));
        if (count == 0 && StringUtil.isNullEmpty(patientPrescriptionInfo.getRemark())) {
            return new Tip(500, "无可提交的处方信息！");
        }
        
        Date createDate = new Date();
        patientPrescriptionInfo.setCreateDate(createDate );
        patientPrescriptionInfo.setUpdateDate(createDate);
        patientPrescriptionInfoService.insert(patientPrescriptionInfo);
        patientInfoService.updateHandleType(patientPrescriptionInfo.getPatientInfoId(), EnumHandleTypeMask.PRESCRIPTION);
        return MapUtil.createSuccessMap("id", patientPrescriptionInfo.getId());
    }

    /**
     * 删除患者处方
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer patientPrescriptionInfoId) {
        patientPrescriptionInfoService.deleteById(patientPrescriptionInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改患者处方
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PatientPrescriptionInfo patientPrescriptionInfo) {
        patientPrescriptionInfo.setUpdateDate(new Date());
        patientPrescriptionInfoService.updateById(patientPrescriptionInfo);
        return SUCCESS_TIP;
    }

    /**
     * 修改患者处方
     */
    @RequestMapping(value = "/findByPatientInfoId")
    @ResponseBody
    public Object findByPatientInfoId(@RequestParam(required=true) Integer patientInfoId) {
        Wrapper<PatientPrescriptionInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("patient_info_id", patientInfoId);
        return MapUtil.createSuccessMap("data", patientPrescriptionInfoService.selectMap(wrapper));
    }

    /**
     * 患者处方详情
     */
    @RequestMapping(value = "/detail/{patientPrescriptionInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("patientPrescriptionInfoId") Integer patientPrescriptionInfoId) {
        return patientPrescriptionInfoService.selectById(patientPrescriptionInfoId);
    }

    /**
     * 患者处方详情
     */
    @RequestMapping(value = "/cancelPrescription")
    @ResponseBody
    @Transactional
    public Object cancelPrescription(Integer patientInfoId) {
        Tip tip = patientInfoService.cancelHandleType(patientInfoId, EnumHandleTypeMask.PRESCRIPTION);
        patientPrescriptionInfoService.deleteByMap(MapUtil.createMap("patient_info_id", patientInfoId));
        prescriptionMedicineInfoService.deleteByMap(MapUtil.createMap("patient_info_id", patientInfoId));
        if (tip != null) {
            return tip;
        }
        return SUCCESS_TIP;
    }
}
