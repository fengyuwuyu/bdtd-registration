package com.bdtd.card.registration.modular.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bdtd.card.registration.modular.inventory.service.IMedicalInventorySecondLevelService;
import com.bdtd.card.registration.modular.system.model.MedicalInventorySecondLevel;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;

/**
 * 药品二级库存管理控制器
 *
 * @author fengshuonan
 * @Date 2018-06-14 12:36:54
 */
@Controller
@RequestMapping("/medicalInventorySecondLevel")
public class MedicalInventorySecondLevelController extends BaseController {

    private String PREFIX = "/inventory/medicalInventorySecondLevel/";

    @Autowired
    private IMedicalInventorySecondLevelService medicalInventorySecondLevelService;

    /**
     * 跳转到药品二级库存管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "medicalInventorySecondLevel.html";
    }

    /**
     * 跳转到添加药品二级库存管理
     */
    @RequestMapping("/medicalInventorySecondLevel_add")
    public String medicalInventorySecondLevelAdd() {
        return PREFIX + "medicalInventorySecondLevel_add.html";
    }

    /**
     * 跳转到修改药品二级库存管理
     */
    @RequestMapping("/medicalInventorySecondLevel_update/{medicalInventorySecondLevelId}")
    public String medicalInventorySecondLevelUpdate(@PathVariable Integer medicalInventorySecondLevelId, Model model) {
        MedicalInventorySecondLevel medicalInventorySecondLevel = medicalInventorySecondLevelService.selectById(medicalInventorySecondLevelId);
        model.addAttribute("item",medicalInventorySecondLevel);
        LogObjectHolder.me().set(medicalInventorySecondLevel);
        return PREFIX + "medicalInventorySecondLevel_edit.html";
    }

    /**
     * 获取药品二级库存管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return medicalInventorySecondLevelService.selectList(null);
    }

    /**
     * 新增药品二级库存管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MedicalInventorySecondLevel medicalInventorySecondLevel) {
        medicalInventorySecondLevelService.insert(medicalInventorySecondLevel);
        return SUCCESS_TIP;
    }

    /**
     * 删除药品二级库存管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer medicalInventorySecondLevelId) {
        medicalInventorySecondLevelService.deleteById(medicalInventorySecondLevelId);
        return SUCCESS_TIP;
    }

    /**
     * 修改药品二级库存管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(MedicalInventorySecondLevel medicalInventorySecondLevel) {
        medicalInventorySecondLevelService.updateById(medicalInventorySecondLevel);
        return SUCCESS_TIP;
    }

    /**
     * 药品二级库存管理详情
     */
    @RequestMapping(value = "/detail/{medicalInventorySecondLevelId}")
    @ResponseBody
    public Object detail(@PathVariable("medicalInventorySecondLevelId") Integer medicalInventorySecondLevelId) {
        return medicalInventorySecondLevelService.selectById(medicalInventorySecondLevelId);
    }
}
