package com.bdtd.card.registration.modular.treatment.controller;

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

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.bdtd.card.registration.common.model.EnumHandleTypeMask;
import com.bdtd.card.registration.modular.treatment.service.IPatientInfoService;
import com.bdtd.card.registration.modular.treatment.service.IPatientPhysicalTherapyService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.common.annotion.DictEntity;
import com.stylefeng.guns.core.common.annotion.DictHandler;
import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.modular.system.model.PatientPhysicalTherapy;

/**
 * 理疗单控制器
 *
 * @author 
 * @Date 2018-06-26 09:09:39
 */
@Controller
@RequestMapping("/patientPhysicalTherapy")
public class PatientPhysicalTherapyController extends BaseController {

    private String PREFIX = "/treatment/patientPhysicalTherapy/";
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IPatientPhysicalTherapyService patientPhysicalTherapyService;
    
    @Autowired
    private IPatientInfoService patientInfoService;

    /**
     * 跳转到理疗单首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "patientPhysicalTherapy.html";
    }

    /**
     * 跳转到添加理疗单
     */
    @RequestMapping("/patientPhysicalTherapy_add")
    @DictHandler(dictModels = { DictConsts.PHYSICAL_THERAPY}, dictKeys = { DictConsts.PHYSICAL_THERAPY_KEY})
    public String patientPhysicalTherapyAdd(Model model) {
        return PREFIX + "patientPhysicalTherapy_add.html";
    }

    /**
     * 跳转到修改理疗单
     */
    @RequestMapping("/patientPhysicalTherapy_update/{patientPhysicalTherapyId}")
    @DictHandler(dictModels = { DictConsts.PHYSICAL_THERAPY}, dictKeys = { DictConsts.PHYSICAL_THERAPY_KEY})
    public String patientPhysicalTherapyUpdate(@PathVariable Integer patientPhysicalTherapyId, Model model) {
        PatientPhysicalTherapy patientPhysicalTherapy = patientPhysicalTherapyService.selectById(patientPhysicalTherapyId);
        model.addAttribute("item",patientPhysicalTherapy);
        LogObjectHolder.me().set(patientPhysicalTherapy);
        return PREFIX + "patientPhysicalTherapy_edit.html";
    }

    /**
     * 获取理疗单列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @DictHandler(dictWrappers= {@DictEntity(parentId= DictConsts.PHYSICAL_THERAPY, fieldName=DictConsts.PHYSICAL_THERAPY_FIELD_NAME)})
    public Object list(String condition, Integer offset, Integer limit) {
    	Wrapper<PatientPhysicalTherapy> wrapper = new EntityWrapper<>();
    	Page<Map<String, Object>> page = patientPhysicalTherapyService.selectMapsPage(new Page<>(offset / limit + 1, limit, Consts.DEFAULT_SORT_FIELD, Consts.DEFAULT_SORT_ORDER_IS_ASC), wrapper);
		return MapUtil.createSuccessMap("rows", page.getRecords(), "total", page.getTotal());
    }
    /**
     * 获取理疗单列表
     */
    @RequestMapping(value = "/findByPatientInfoId")
    @ResponseBody
    @DictHandler(dictWrappers= {@DictEntity(parentId= DictConsts.PHYSICAL_THERAPY, fieldName=DictConsts.PHYSICAL_THERAPY_FIELD_NAME)})
    public Object findByPatientInfoId(Integer patientInfoId) {
        if (patientInfoId == null) {
            return MapUtil.createSuccessMap("rows", Collections.emptyList(), "total", 0);
        }
        Wrapper<PatientPhysicalTherapy> wrapper = new EntityWrapper<>();
        wrapper.eq("patient_info_id", patientInfoId);
        List<Map<String, Object>> result = patientPhysicalTherapyService.selectMaps(wrapper);
        return MapUtil.createSuccessMap("rows", result, "total", result.size());
    }

    /**
     * 新增理疗单
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PatientPhysicalTherapy patientPhysicalTherapy) {
        Date createDate = new Date();
        patientPhysicalTherapy.setCreateDate(createDate);
        patientPhysicalTherapy.setUpdateDate(createDate);
        patientPhysicalTherapyService.insert(patientPhysicalTherapy);
        return SUCCESS_TIP;
    }

    /**
     * 删除理疗单
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer patientPhysicalTherapyId) {
        patientPhysicalTherapyService.deleteById(patientPhysicalTherapyId);
        return SUCCESS_TIP;
    }

    /**
     * 修改理疗单
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PatientPhysicalTherapy patientPhysicalTherapy) {
        patientPhysicalTherapyService.updateById(patientPhysicalTherapy);
        return SUCCESS_TIP;
    }
    
    @RequestMapping(value = "/cancelPhysicalTherapy")
    @ResponseBody
    @Transactional
    public Object cancelPhysicalTherapy(@RequestParam(required=true) Integer patientInfoId) {
        patientPhysicalTherapyService.deleteByMap(MapUtil.createMap("patient_info_id", patientInfoId));
        patientInfoService.cancelHandleType(patientInfoId, EnumHandleTypeMask.PHYSICAL_THERAPY);
        return SUCCESS_TIP;
    }
    
    @RequestMapping(value = "/submitPhysicalTherapy")
    @ResponseBody
    @Transactional
    public Object submitPhysicalTherapy(@RequestParam(required=true) Integer patientInfoId) {
        Wrapper<PatientPhysicalTherapy> wrapper = new EntityWrapper<>();
        wrapper.eq("patient_info_id", patientInfoId);
        long count = patientPhysicalTherapyService.selectCount(wrapper);
        if (count == 0) {
            return new Tip(500, "没有可提交的理疗！");
        }

        patientInfoService.updateHandleType(patientInfoId, EnumHandleTypeMask.PHYSICAL_THERAPY);
        return SUCCESS_TIP;
    }
    
    /**
     * 理疗单详情
     */
    @RequestMapping(value = "/detail/{patientPhysicalTherapyId}")
    @ResponseBody
    public Object detail(@PathVariable("patientPhysicalTherapyId") Integer patientPhysicalTherapyId) {
        return patientPhysicalTherapyService.selectById(patientPhysicalTherapyId);
    }
}
