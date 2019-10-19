package com.bdtd.card.registration.modular.alert.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bdtd.card.registration.modular.alert.service.IAlertConfigService;
import com.bdtd.card.registration.modular.alert.service.IAlertDepService;
import com.bdtd.card.registration.modular.alert.service.IAlertFeverService;
import com.stylefeng.guns.core.common.annotion.DictEntity;
import com.stylefeng.guns.core.common.annotion.DictHandler;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.modular.system.model.AlertConfig;

@Controller
@RequestMapping("/alertFever")
public class AlertFeverController {

    private static final String PREFIX = "/alert/fever/";
    
    @Autowired
    private IAlertFeverService alertFeverService;
    @Autowired
    private IAlertDepService alertDepService;
    @Autowired
    private IAlertConfigService alertConfigService;
    
    @RequestMapping("/openUpdateConfig")
    public String openUpdateConfig(Model model) {
        model.addAttribute("item", this.alertConfigService.getConfig());
        return PREFIX + "updateConfig.html";
    }
    
    @RequestMapping("/openDetail/{depSerial}")
    public String openDetail(@PathVariable Long depSerial, Model model) {
        model.addAttribute("depSerial", depSerial);
        return PREFIX + "patientInfoFever.html";
    }
    

    @RequestMapping("/checkFeverDepCount")
    @ResponseBody
    public Object checkFeverDepCount(AlertConfig alertConfig) {
        Map<String, Object> map = this.alertFeverService.list(null, 0, 1);
        return MapUtil.createSuccessMap("total", map.get("total"));
    }
    
    @RequestMapping("/updateConfig")
    @ResponseBody
    public Object updateConfig(AlertConfig alertConfig) {
        this.alertConfigService.updateById(alertConfig);
        this.alertConfigService.setConfig(alertConfig);
        return MapUtil.createSuccessMap();
    }
    
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("alertDepItemList", alertDepService.select());
        return PREFIX + "fever.html";
    }
    
    @RequestMapping("/list")
    @ResponseBody
    public Object list(Long depSerial, Integer offset, Integer limit) {
        return alertFeverService.list(depSerial, offset, limit);
    }
    
    @RequestMapping("/detail")
    @ResponseBody
    @DictHandler(dictWrappers = {
            @DictEntity(parentId = DictConsts.REGISTRATION_MAIN_DIAGNOSIS, fieldName = DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME) })
    public Object detail(@RequestParam(required=true) Long depSerial, String userName, Date beginDate, Date endDate, Integer offset, Integer limit) {
        return alertFeverService.feverDetail(depSerial, userName, beginDate, endDate, offset, limit);
    }
}
