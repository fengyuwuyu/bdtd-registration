package com.bdtd.card.registration.modular.treatment.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.bdtd.card.registration.modular.treatment.service.IPatientMedicalUsageStepService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.CommonUtils;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.modular.system.model.PatientMedicalUsageStep;

/**
 * 药品使用步骤控制器
 *
 * @author 
 * @Date 2018-06-26 09:09:25
 */
@Controller
@RequestMapping("/patientMedicalUsageStep")
public class PatientMedicalUsageStepController extends BaseController {

    private String PREFIX = "/treatment/patientMedicalUsageStep/";
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IPatientMedicalUsageStepService patientMedicalUsageStepService;

    /**
     * 跳转到药品使用步骤首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "patientMedicalUsageStep.html";
    }

    /**
     * 跳转到添加药品使用步骤
     */
    @RequestMapping("/patientMedicalUsageStep_add")
    public String patientMedicalUsageStepAdd() {
        return PREFIX + "patientMedicalUsageStep_add.html";
    }

    /**
     * 跳转到修改药品使用步骤
     */
    @RequestMapping("/patientMedicalUsageStep_update/{patientMedicalUsageStepId}")
    public String patientMedicalUsageStepUpdate(@PathVariable Integer patientMedicalUsageStepId, Model model) {
        PatientMedicalUsageStep patientMedicalUsageStep = patientMedicalUsageStepService.selectById(patientMedicalUsageStepId);
        model.addAttribute("item",patientMedicalUsageStep);
        LogObjectHolder.me().set(patientMedicalUsageStep);
        return PREFIX + "patientMedicalUsageStep_edit.html";
    }

    /**
     * 获取药品使用步骤列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(Integer patientInfoId) {
        if (patientInfoId == null) {
            return MapUtil.createSuccessMap("total", 0, "rows", Collections.emptyList());
        }
    	Wrapper<PatientMedicalUsageStep> wrapper = new EntityWrapper<>();
    	CommonUtils.handleRequestParams(wrapper, "patient_info_id", patientInfoId, null);
    	List<Map<String, Object>> result= patientMedicalUsageStepService.selectMaps(wrapper);
    	if (result == null || result.size() == 0) {
    	    return MapUtil.createSuccessMap("total", 0, "rows", Collections.emptyList());
    	}
		return MapUtil.createSuccessMap("total", result.size(), "rows", result);
    }

    /**
     * 新增药品使用步骤
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PatientMedicalUsageStep patientMedicalUsageStep) {
        if (patientMedicalUsageStep.getPatientInfoId() == null) {
            log.error("patientInfoId is null");
            return new Tip(500, "服务器异常！");
        }
        patientMedicalUsageStepService.insert(patientMedicalUsageStep);
        return MapUtil.createSuccessMap("id", patientMedicalUsageStep.getId());
    }

    /**
     * 删除药品使用步骤
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer patientMedicalUsageStepId) {
        patientMedicalUsageStepService.deleteById(patientMedicalUsageStepId);
        return SUCCESS_TIP;
    }

    /**
     * 修改药品使用步骤
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PatientMedicalUsageStep patientMedicalUsageStep) {
        patientMedicalUsageStepService.updateById(patientMedicalUsageStep);
        return SUCCESS_TIP;
    }

    /**
     * 药品使用步骤详情
     */
    @RequestMapping(value = "/detail/{patientMedicalUsageStepId}")
    @ResponseBody
    public Object detail(@PathVariable("patientMedicalUsageStepId") Integer patientMedicalUsageStepId) {
        return patientMedicalUsageStepService.selectById(patientMedicalUsageStepId);
    }
}
