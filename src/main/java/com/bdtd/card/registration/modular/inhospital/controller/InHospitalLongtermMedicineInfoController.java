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
import com.bdtd.card.registration.modular.inhospital.service.IInHospitalLongtermMedicineInfoService;
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
import com.stylefeng.guns.modular.system.model.InHospitalLongtermMedicineInfo;

/**
 * 长期医嘱药详情控制器
 *
 * @author 
 * @Date 2018-07-05 17:44:05
 */
@Controller
@RequestMapping("/inHospitalLongtermMedicineInfo")
public class InHospitalLongtermMedicineInfoController extends BaseController {

    private String PREFIX = "/inhospital/inHospitalLongtermMedicineInfo/";

    @Autowired
    private IInHospitalLongtermMedicineInfoService inHospitalLongtermMedicineInfoService;
    @Autowired
    private IMedicalInventoryPharmacyService medicalInventoryPharmacyService;
    
    /**
     * 跳转到长期医嘱药详情首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "inHospitalLongtermMedicineInfo.html";
    }

    /**
     * 跳转到添加长期医嘱药详情
     */
    @RequestMapping("/inHospitalLongtermMedicineInfo_add/{inHospitalId}")
    @DictHandler(dictModels = { DictConsts.TREATMENT_USAGE, DictConsts.TREATMENT_PERIOD, DictConsts.TREATMENT_DOSAGE, DictConsts.DOCTOR }, dictKeys = { 
            DictConsts.TREATMENT_USAGE_KEY, DictConsts.TREATMENT_PERIOD_KEY, DictConsts.TREATMENT_DOSAGE_KEY, DictConsts.DOCTOR_KEY })
    public String inHospitalLongtermMedicineInfoAdd(@PathVariable Integer inHospitalId, Model model) {
        model.addAttribute("inHospitalId", inHospitalId);
        model.addAttribute("groupLevelItemList", EnumLongTermAdviceGroup.select());
        model.addAttribute("physicianName", ShiroKit.getUser().getDtUser().getUserLname());
        return PREFIX + "inHospitalLongtermMedicineInfo_add.html";
    }

    /**
     * 跳转到修改长期医嘱药详情
     */
    @RequestMapping("/inHospitalLongtermMedicineInfo_update/{inHospitalLongtermMedicineInfoId}")
    @DictHandler(dictModels = { DictConsts.TREATMENT_USAGE, DictConsts.TREATMENT_PERIOD, DictConsts.TREATMENT_DOSAGE, DictConsts.DOCTOR }, dictKeys = { 
            DictConsts.TREATMENT_USAGE_KEY, DictConsts.TREATMENT_PERIOD_KEY, DictConsts.TREATMENT_DOSAGE_KEY, DictConsts.DOCTOR_KEY })
    public String inHospitalLongtermMedicineInfoUpdate(@PathVariable Integer inHospitalLongtermMedicineInfoId, Model model) {
        InHospitalLongtermMedicineInfo inHospitalLongtermMedicineInfo = inHospitalLongtermMedicineInfoService.selectById(inHospitalLongtermMedicineInfoId);
        model.addAttribute("item",inHospitalLongtermMedicineInfo);
        LogObjectHolder.me().set(inHospitalLongtermMedicineInfo);
        
        model.addAttribute("groupLevelItemList", EnumLongTermAdviceGroup.select());
        return PREFIX + "inHospitalLongtermMedicineInfo_edit.html";
    }

    /**
     * 获取长期医嘱药详情列表
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
    	Wrapper<InHospitalLongtermMedicineInfo> wrapper = new EntityWrapper<>();
    	wrapper.eq("in_hospital_id", inHospitalId);
    	Page<Map<String, Object>> page = inHospitalLongtermMedicineInfoService.selectMapsPage(new Page<>(offset / limit + 1, limit, "begin_date", Consts.DEFAULT_SORT_ORDER_IS_ASC), wrapper);
		return MapUtil.createSuccessMap("rows", page.getRecords(), "total", page.getTotal());
    }

    /**
     * 新增长期医嘱药详情
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(InHospitalLongtermMedicineInfo inHospitalLongtermMedicineInfo) {
        Tip tip = this.medicalInventoryPharmacyService.checkMedicalInventory(inHospitalLongtermMedicineInfo.getMedicineId(), inHospitalLongtermMedicineInfo.getAmount());
        if (tip != null) {
            return tip;
        }
        inHospitalLongtermMedicineInfo.setOperatorName(ShiroKit.getUser().getDtUser().getUserLname());
        inHospitalLongtermMedicineInfo.setOperatorNo(ShiroKit.getUser().getDtUser().getUserNo());
        inHospitalLongtermMedicineInfoService.insert(inHospitalLongtermMedicineInfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除长期医嘱药详情
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer inHospitalLongtermMedicineInfoId) {
        inHospitalLongtermMedicineInfoService.deleteById(inHospitalLongtermMedicineInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改长期医嘱药详情
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(InHospitalLongtermMedicineInfo inHospitalLongtermMedicineInfo) {
        Tip tip = this.medicalInventoryPharmacyService.checkMedicalInventory(inHospitalLongtermMedicineInfo.getMedicineId(), inHospitalLongtermMedicineInfo.getAmount());
        if (tip != null) {
            return tip;
        }
        inHospitalLongtermMedicineInfo.setOperatorName(ShiroKit.getUser().getDtUser().getUserLname());
        inHospitalLongtermMedicineInfo.setOperatorNo(ShiroKit.getUser().getDtUser().getUserNo());
        inHospitalLongtermMedicineInfoService.updateById(inHospitalLongtermMedicineInfo);
        return SUCCESS_TIP;
    }

    /**
     * 长期医嘱药详情详情
     */
    @RequestMapping(value = "/detail/{inHospitalLongtermMedicineInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("inHospitalLongtermMedicineInfoId") Integer inHospitalLongtermMedicineInfoId) {
        return inHospitalLongtermMedicineInfoService.selectById(inHospitalLongtermMedicineInfoId);
    }
}
