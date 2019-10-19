package com.bdtd.card.registration.modular.treatment.controller;

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
import com.bdtd.card.registration.modular.treatment.service.IPatientClinicDisposisService;
import com.bdtd.card.registration.modular.treatment.service.IPatientInfoService;
import com.bdtd.card.registration.modular.treatment.service.IPatientMedicalUsageStepService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.modular.system.model.PatientClinicDisposis;
import com.stylefeng.guns.modular.system.model.PatientMedicalUsageStep;

/**
 * 门诊处置控制器
 *
 * @author 
 * @Date 2018-06-26 09:08:18
 */
@Controller
@RequestMapping("/patientClinicDisposis")
public class PatientClinicDisposisController extends BaseController {

    private String PREFIX = "/treatment/patientClinicDisposis/";

    @Autowired
    private IPatientClinicDisposisService patientClinicDisposisService;
    
    @Autowired
    private IPatientInfoService patientInfoService; 
    @Autowired
    private IPatientMedicalUsageStepService patientMedicalUsageStepService;

    /**
     * 跳转到门诊处置首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "patientClinicDisposis.html";
    }

    /**
     * 跳转到添加门诊处置
     */
    @RequestMapping("/patientClinicDisposis_add")
    public String patientClinicDisposisAdd() {
        return PREFIX + "patientClinicDisposis_add.html";
    }

    /**
     * 跳转到修改门诊处置
     */
    @RequestMapping("/patientClinicDisposis_update/{patientClinicDisposisId}")
    public String patientClinicDisposisUpdate(@PathVariable Integer patientClinicDisposisId, Model model) {
        PatientClinicDisposis patientClinicDisposis = patientClinicDisposisService.selectById(patientClinicDisposisId);
        model.addAttribute("item",patientClinicDisposis);
        LogObjectHolder.me().set(patientClinicDisposis);
        return PREFIX + "patientClinicDisposis_edit.html";
    }

    /**
     * 获取门诊处置列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer offset, Integer limit) {
    	Wrapper<PatientClinicDisposis> wrapper = new EntityWrapper<>();
    	Page<Map<String, Object>> page = patientClinicDisposisService.selectMapsPage(new Page<>(offset / limit + 1, limit, Consts.DEFAULT_SORT_FIELD, Consts.DEFAULT_SORT_ORDER_IS_ASC), wrapper);
		return MapUtil.createSuccessMap("rows", page.getRecords(), "total", page.getTotal());
    }
    
    @RequestMapping(value = "/findByPatientInfoId")
    @ResponseBody
    public Object findByPatientInfoId(@RequestParam(required=true) Integer patientInfoId) {
        Wrapper<PatientClinicDisposis> wrapper = new EntityWrapper<>();
        wrapper.eq("patient_info_id", patientInfoId);
        return MapUtil.createSuccessMap("data", patientClinicDisposisService.selectMap(wrapper));
    }
    
    

    /**
     * 新增门诊处置
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PatientClinicDisposis patientClinicDisposis) {
        Wrapper<PatientMedicalUsageStep> wrapper = new EntityWrapper<>();
        wrapper.eq("patient_info_id", patientClinicDisposis.getPatientInfoId());
        long count = patientMedicalUsageStepService.selectCount(wrapper);
        
        if (patientClinicDisposis.getSkinTestMedicalId() != null && patientClinicDisposis.getSkinTestMedicalId() == 0) {
            patientClinicDisposis.setSkinTestMedicalId(null);
        }
        if (patientClinicDisposis.getSkinTestMedicalId() == null && count == 0) {
            return MapUtil.createSuccessMap("id", patientClinicDisposis.getId(), "message", "没有需要提交的门诊处置信息！");
        }
        if (patientClinicDisposis.getSkinTestMedicalId() != null) {
            patientClinicDisposisService.insert(patientClinicDisposis);
        }
        patientInfoService.updateHandleType(patientClinicDisposis.getPatientInfoId(), EnumHandleTypeMask.DISPOSE);
        return MapUtil.createSuccessMap("id", patientClinicDisposis.getId(), "message", "操作成功！");
    }

    /**
     * 删除门诊处置
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer patientClinicDisposisId) {
        patientClinicDisposisService.deleteById(patientClinicDisposisId);
        return SUCCESS_TIP;
    }
    
    @RequestMapping(value = "/cancelDisposis")
    @ResponseBody
    @Transactional
    public Object cancelDisposis(Integer patientClinicDisposisId, @RequestParam(required=true) Integer patientInfoId) {
        if (patientClinicDisposisId != null) {
            patientClinicDisposisService.deleteById(patientClinicDisposisId);
        }
        patientMedicalUsageStepService.deleteByMap(MapUtil.createMap("patient_info_id", patientInfoId));
        return SUCCESS_TIP;
    }

    /**
     * 修改门诊处置
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PatientClinicDisposis patientClinicDisposis) {
        if (patientClinicDisposis.getSkinTestMedicalId() == null || patientClinicDisposis.getSkinTestMedicalId() == 0) {
            patientClinicDisposisService.deleteById(patientClinicDisposis.getId());
        } else {
            patientClinicDisposisService.updateById(patientClinicDisposis);
        }
        return SUCCESS_TIP;
    }

    /**
     * 门诊处置详情
     */
    @RequestMapping(value = "/detail/{patientClinicDisposisId}")
    @ResponseBody
    public Object detail(@PathVariable("patientClinicDisposisId") Integer patientClinicDisposisId) {
        return patientClinicDisposisService.selectById(patientClinicDisposisId);
    }
}
