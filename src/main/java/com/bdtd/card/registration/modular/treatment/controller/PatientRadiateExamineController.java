package com.bdtd.card.registration.modular.treatment.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.bdtd.card.registration.modular.treatment.service.IPatientRadiateExamineService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.modular.system.model.PatientRadiateExamine;

/**
 * 放射检查控制器
 *
 * @author 
 * @Date 2018-06-26 09:12:18
 */
@Controller
@RequestMapping("/patientRadiateExamine")
public class PatientRadiateExamineController extends BaseController {

    private String PREFIX = "/treatment/patientRadiateExamine/";

    @Autowired
    private IPatientRadiateExamineService patientRadiateExamineService;
    
    @Autowired
    private IPatientInfoService patientInfoService;

    /**
     * 跳转到放射检查首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "patientRadiateExamine.html";
    }

    /**
     * 跳转到添加放射检查
     */
    @RequestMapping("/patientRadiateExamine_add")
    public String patientRadiateExamineAdd() {
        return PREFIX + "patientRadiateExamine_add.html";
    }

    /**
     * 跳转到修改放射检查
     */
    @RequestMapping("/patientRadiateExamine_update/{patientRadiateExamineId}")
    public String patientRadiateExamineUpdate(@PathVariable Integer patientRadiateExamineId, Model model) {
        PatientRadiateExamine patientRadiateExamine = patientRadiateExamineService.selectById(patientRadiateExamineId);
        model.addAttribute("item",patientRadiateExamine);
        LogObjectHolder.me().set(patientRadiateExamine);
        return PREFIX + "patientRadiateExamine_edit.html";
    }

    /**
     * 获取放射检查列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(Integer patientInfoId) {
        if (patientInfoId == null) {
            return MapUtil.createSuccessMap("rows", Collections.emptyList(), "total", 0);
        }
    	Wrapper<PatientRadiateExamine> wrapper = new EntityWrapper<>();
    	wrapper.eq("patient_info_id", patientInfoId);
    	List<Map<String, Object>> result = patientRadiateExamineService.selectMaps(wrapper);
    	if (result == null || result.size() == 0) {
    	    return MapUtil.createSuccessMap("rows", Collections.emptyList(), "total", 0);
    	}
		return MapUtil.createSuccessMap("rows", result, "total", result.size());
    }

    /**
     * 新增放射检查
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PatientRadiateExamine patientRadiateExamine) {
        patientRadiateExamineService.insert(patientRadiateExamine);
        return SUCCESS_TIP;
    }

    /**
     * 删除放射检查
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer patientRadiateExamineId) {
        patientRadiateExamineService.deleteById(patientRadiateExamineId);
        return SUCCESS_TIP;
    }
    
    @RequestMapping(value = "/deleteByPatientInfoId")
    @ResponseBody
    @Transactional
    public Object deleteByPatientInfoId(@RequestParam Integer patientInfoId) {
        patientRadiateExamineService.deleteByMap(MapUtil.createMap("patient_info_id", patientInfoId));
        patientInfoService.cancelHandleType(patientInfoId, EnumHandleTypeMask.RADIATE_EXAMINE);
        return SUCCESS_TIP;
    }

    /**
     * 修改放射检查
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PatientRadiateExamine patientRadiateExamine) {
        patientRadiateExamineService.updateById(patientRadiateExamine);
        return SUCCESS_TIP;
    }

    /**
     * 放射检查详情
     */
    @RequestMapping(value = "/detail/{patientRadiateExamineId}")
    @ResponseBody
    public Object detail(@PathVariable("patientRadiateExamineId") Integer patientRadiateExamineId) {
        return patientRadiateExamineService.selectById(patientRadiateExamineId);
    }
}
