package com.bdtd.card.registration.modular.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bdtd.card.registration.modular.treatment.service.IIrritabilityHistoryService;
import com.stylefeng.guns.modular.system.model.IrritabilityHistory;

@Controller
@RequestMapping("/commonPatientInfo")
public class CommonPatientInfoController {
    
    @Autowired
    private IIrritabilityHistoryService irritabilityHistoryService;

    @RequestMapping("/irritabilityHistory")
    @ResponseBody
    public Object irritabilityHistory(@RequestParam(required=true) String userNo) {
        IrritabilityHistory irritabilityHistory = irritabilityHistoryService.selectById(userNo);
        if (irritabilityHistory != null) {
            return irritabilityHistory.getIrritabilityHistory();
        }
        return null;
    }
}
