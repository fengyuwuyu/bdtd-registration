package com.bdtd.card.registration.modular.treatment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bdtd.card.registration.modular.treatment.service.IPrescriptionMedicineInfoService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.system.model.PrescriptionMedicineInfo;

/**
 * 就诊管理控制器
 *
 * @author 
 * @Date 2018-06-20 16:09:56
 */
@Controller
@RequestMapping("/prescriptionMedicineInfo")
public class PrescriptionMedicineInfoController extends BaseController {

    private String PREFIX = "/treatment/prescriptionMedicineInfo/";

    @Autowired
    private IPrescriptionMedicineInfoService prescriptionMedicineInfoService;

    /**
     * 跳转到就诊管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "prescriptionMedicineInfo.html";
    }

    /**
     * 跳转到添加就诊管理
     */
    @RequestMapping("/prescriptionMedicineInfo_add")
    public String prescriptionMedicineInfoAdd() {
        return PREFIX + "prescriptionMedicineInfo_add.html";
    }

    /**
     * 跳转到修改就诊管理
     */
    @RequestMapping("/prescriptionMedicineInfo_update/{prescriptionMedicineInfoId}")
    public String prescriptionMedicineInfoUpdate(@PathVariable Integer prescriptionMedicineInfoId, Model model) {
        PrescriptionMedicineInfo prescriptionMedicineInfo = prescriptionMedicineInfoService.selectById(prescriptionMedicineInfoId);
        model.addAttribute("item",prescriptionMedicineInfo);
        LogObjectHolder.me().set(prescriptionMedicineInfo);
        return PREFIX + "prescriptionMedicineInfo_edit.html";
    }

    /**
     * 获取就诊管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return prescriptionMedicineInfoService.selectList(null);
    }

    /**
     * 新增就诊管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PrescriptionMedicineInfo prescriptionMedicineInfo) {
        prescriptionMedicineInfoService.insert(prescriptionMedicineInfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除就诊管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer prescriptionMedicineInfoId) {
        prescriptionMedicineInfoService.deleteById(prescriptionMedicineInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改就诊管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PrescriptionMedicineInfo prescriptionMedicineInfo) {
        prescriptionMedicineInfoService.updateById(prescriptionMedicineInfo);
        return SUCCESS_TIP;
    }

    /**
     * 就诊管理详情
     */
    @RequestMapping(value = "/detail/{prescriptionMedicineInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("prescriptionMedicineInfoId") Integer prescriptionMedicineInfoId) {
        return prescriptionMedicineInfoService.selectById(prescriptionMedicineInfoId);
    }
}
