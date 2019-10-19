package com.bdtd.card.registration.modular.inhospital.controller;

import java.util.Collections;
import java.util.Map;

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
import com.bdtd.card.registration.common.model.EnumLongTermAdviceGroup;
import com.bdtd.card.registration.modular.inhospital.service.IInHospitalTemporaryMedicineInfoService;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryPharmacyService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.common.annotion.DictEntity;
import com.stylefeng.guns.core.common.annotion.DictHandler;
import com.stylefeng.guns.core.common.annotion.EnumEntity;
import com.stylefeng.guns.core.common.annotion.EnumEntityList;
import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.modular.system.model.InHospitalTemporaryMedicineInfo;

/**
 * 临时医嘱药详情控制器
 *
 * @author 
 * @Date 2018-07-05 17:44:20
 */
@Controller
@RequestMapping("/inHospitalTemporaryMedicineInfo")
public class InHospitalTemporaryMedicineInfoController extends BaseController {

    private String PREFIX = "/inhospital/inHospitalTemporaryMedicineInfo/";

    @Autowired
    private IInHospitalTemporaryMedicineInfoService inHospitalTemporaryMedicineInfoService;
    @Autowired
    private IMedicalInventoryPharmacyService medicalInventoryPharmacyService;

    /**
     * 跳转到临时医嘱药详情首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "inHospitalTemporaryMedicineInfo.html";
    }

    /**
     * 跳转到添加临时医嘱药详情
     */
    @RequestMapping("/inHospitalTemporaryMedicineInfo_add/{inHospitalId}")
    @DictHandler(dictModels = { DictConsts.TREATMENT_USAGE, DictConsts.TREATMENT_PERIOD, DictConsts.TREATMENT_DOSAGE, DictConsts.DOCTOR }, dictKeys = { 
            DictConsts.TREATMENT_USAGE_KEY, DictConsts.TREATMENT_PERIOD_KEY, DictConsts.TREATMENT_DOSAGE_KEY, DictConsts.DOCTOR_KEY })
    public String inHospitalTemporaryMedicineInfoAdd(@PathVariable Integer inHospitalId, Model model) {
        model.addAttribute("inHospitalId", inHospitalId);
        model.addAttribute("groupLevelItemList", EnumLongTermAdviceGroup.select());
        model.addAttribute("physicianName", ShiroKit.getUser().getDtUser().getUserLname());
        return PREFIX + "inHospitalTemporaryMedicineInfo_add.html";
    }

    /**
     * 跳转到修改临时医嘱药详情
     */
    @RequestMapping("/inHospitalTemporaryMedicineInfo_update/{inHospitalTemporaryMedicineInfoId}")
    @DictHandler(dictModels = { DictConsts.TREATMENT_USAGE, DictConsts.TREATMENT_PERIOD, DictConsts.TREATMENT_DOSAGE, DictConsts.DOCTOR }, dictKeys = { 
            DictConsts.TREATMENT_USAGE_KEY, DictConsts.TREATMENT_PERIOD_KEY, DictConsts.TREATMENT_DOSAGE_KEY, DictConsts.DOCTOR_KEY })
    public String inHospitalTemporaryMedicineInfoUpdate(@PathVariable Integer inHospitalTemporaryMedicineInfoId, Model model) {
        InHospitalTemporaryMedicineInfo inHospitalTemporaryMedicineInfo = inHospitalTemporaryMedicineInfoService.selectById(inHospitalTemporaryMedicineInfoId);
        model.addAttribute("item",inHospitalTemporaryMedicineInfo);
        LogObjectHolder.me().set(inHospitalTemporaryMedicineInfo);
        
        model.addAttribute("groupLevelItemList", EnumLongTermAdviceGroup.select());
        return PREFIX + "inHospitalTemporaryMedicineInfo_edit.html";
    }

    /**
     * 获取临时医嘱药详情列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @DictHandler(dictWrappers= {
            @DictEntity(parentId= DictConsts.MEDICAL_UNIT, fieldName=DictConsts.MEDICAL_UNIT_FIELD_NAME),
            @DictEntity(parentId= DictConsts.MEDICAL_SPECIFICATION, fieldName=DictConsts.MEDICAL_SPECIFICATION_FIELD_NAME),
            @DictEntity(parentId= DictConsts.TREATMENT_USAGE, fieldName=DictConsts.TREATMENT_USAGE_FIELD_NAME),
            @DictEntity(parentId= DictConsts.TREATMENT_DOSAGE, fieldName=DictConsts.TREATMENT_DOSAGE_FIELD_NAME),
            @DictEntity(parentId= DictConsts.TREATMENT_PERIOD, fieldName=DictConsts.TREATMENT_PERIOD_FIELD_NAME)
    })
    @EnumEntityList(entityList= {
            @EnumEntity(fieldName="status", enumName="EnumInhospitalTakeMedicalStatus", replaceName="statusStr")
    })
    public Object list(Integer inHospitalId, Integer offset, Integer limit) {
        if (inHospitalId == null) {
            return MapUtil.createSuccessMap("rows", Collections.emptyList(), "total", 0);
        }
    	Wrapper<InHospitalTemporaryMedicineInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("in_hospital_id", inHospitalId);
    	Page<Map<String, Object>> page = inHospitalTemporaryMedicineInfoService.selectMapsPage(new Page<>(offset / limit + 1, limit, "advice_date", Consts.DEFAULT_SORT_ORDER_IS_ASC), wrapper);
		return MapUtil.createSuccessMap("rows", page.getRecords(), "total", page.getTotal());
    }

    /**
     * 新增临时医嘱药详情
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(InHospitalTemporaryMedicineInfo inHospitalTemporaryMedicineInfo) {
        Tip tip = this.medicalInventoryPharmacyService.checkMedicalInventory(inHospitalTemporaryMedicineInfo.getMedicineId(), inHospitalTemporaryMedicineInfo.getAmount());
        if (tip != null) {
            return tip;
        }
        inHospitalTemporaryMedicineInfo.setOperatorName(ShiroKit.getUser().getDtUser().getUserLname());
        inHospitalTemporaryMedicineInfo.setOperatorNo(ShiroKit.getUser().getDtUser().getUserNo());
        inHospitalTemporaryMedicineInfoService.insert(inHospitalTemporaryMedicineInfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除临时医嘱药详情
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer inHospitalTemporaryMedicineInfoId) {
        inHospitalTemporaryMedicineInfoService.deleteById(inHospitalTemporaryMedicineInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改临时医嘱药详情
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(InHospitalTemporaryMedicineInfo inHospitalTemporaryMedicineInfo) {
        Tip tip = this.medicalInventoryPharmacyService.checkMedicalInventory(inHospitalTemporaryMedicineInfo.getMedicineId(), inHospitalTemporaryMedicineInfo.getAmount());
        if (tip != null) {
            return tip;
        }
        inHospitalTemporaryMedicineInfo.setOperatorName(ShiroKit.getUser().getDtUser().getUserLname());
        inHospitalTemporaryMedicineInfo.setOperatorNo(ShiroKit.getUser().getDtUser().getUserNo());
        inHospitalTemporaryMedicineInfoService.updateById(inHospitalTemporaryMedicineInfo);
        return SUCCESS_TIP;
    }

    /**
     * 临时医嘱药详情详情
     */
    @RequestMapping(value = "/detail/{inHospitalTemporaryMedicineInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("inHospitalTemporaryMedicineInfoId") Integer inHospitalTemporaryMedicineInfoId) {
        return inHospitalTemporaryMedicineInfoService.selectById(inHospitalTemporaryMedicineInfoId);
    }
}
