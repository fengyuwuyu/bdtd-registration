package com.bdtd.card.registration.modular.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryLogService;
import com.bdtd.card.registration.modular.system.model.MedicalInventoryLog;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;

/**
 * 药品出入库记录控制器
 *
 * @author fengshuonan
 * @Date 2018-06-14 12:38:16
 */
@Controller
@RequestMapping("/medicalInventoryLog")
public class MedicalInventoryLogController extends BaseController {

    private String PREFIX = "/inventory/medicalInventoryLog/";

    @Autowired
    private IMedicalInventoryLogService medicalInventoryLogService;

    /**
     * 跳转到药品出入库记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "medicalInventoryLog.html";
    }

    /**
     * 跳转到添加药品出入库记录
     */
    @RequestMapping("/medicalInventoryLog_add")
    public String medicalInventoryLogAdd() {
        return PREFIX + "medicalInventoryLog_add.html";
    }

    /**
     * 跳转到修改药品出入库记录
     */
    @RequestMapping("/medicalInventoryLog_update/{medicalInventoryLogId}")
    public String medicalInventoryLogUpdate(@PathVariable Integer medicalInventoryLogId, Model model) {
        MedicalInventoryLog medicalInventoryLog = medicalInventoryLogService.selectById(medicalInventoryLogId);
        model.addAttribute("item",medicalInventoryLog);
        LogObjectHolder.me().set(medicalInventoryLog);
        return PREFIX + "medicalInventoryLog_edit.html";
    }

    /**
     * 获取药品出入库记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return medicalInventoryLogService.selectList(null);
    }

    /**
     * 新增药品出入库记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MedicalInventoryLog medicalInventoryLog) {
        medicalInventoryLogService.insert(medicalInventoryLog);
        return SUCCESS_TIP;
    }

    /**
     * 删除药品出入库记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer medicalInventoryLogId) {
        medicalInventoryLogService.deleteById(medicalInventoryLogId);
        return SUCCESS_TIP;
    }

    /**
     * 修改药品出入库记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(MedicalInventoryLog medicalInventoryLog) {
        medicalInventoryLogService.updateById(medicalInventoryLog);
        return SUCCESS_TIP;
    }

    /**
     * 药品出入库记录详情
     */
    @RequestMapping(value = "/detail/{medicalInventoryLogId}")
    @ResponseBody
    public Object detail(@PathVariable("medicalInventoryLogId") Integer medicalInventoryLogId) {
        return medicalInventoryLogService.selectById(medicalInventoryLogId);
    }
}
