package com.bdtd.card.registration.modular.common;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bdtd.card.registration.common.model.pie.PieEntity;
import com.bdtd.card.registration.common.model.pie.SeriesDataEntity;
import com.bdtd.card.registration.common.model.pie.SeriesEntity;
import com.bdtd.card.registration.modular.inhospital.service.IPatientInHospitalService;
import com.bdtd.card.registration.modular.treatment.service.IPatientInfoService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.scmmain.service.IDtUserService;

/**
 * 总览信息
 *
 * @author 
 * @Date 2017年3月4日23:05:54
 */
@Controller
@RequestMapping("/blackboard")
public class BlackboardController extends BaseController {

    @Autowired
    private IPatientInfoService patientInfoService;
    @Autowired
    private IPatientInHospitalService inHospitalService;
    @Autowired
    private IDtUserService dtUserService;
    
    /**
     * 跳转到黑板
     */
    @RequestMapping("")
    public String blackboard(Model model) {
        return "/blackboard.html";
    }
    
    @RequestMapping("/pieChart")
    @ResponseBody
    public Object pieChart() {
        Date currDayBeginDate = new Date(System.currentTimeMillis());
        Date currDayEndDate = new Date(System.currentTimeMillis() + DateUtil.ONE_DAY_TIME);
        long currDayInTreament = patientInfoService.countByTimeBetween(currDayBeginDate, currDayEndDate);
        long yesterdayInTreament = patientInfoService.countByTimeBetween(new Date(System.currentTimeMillis() - DateUtil.ONE_DAY_TIME), currDayBeginDate);
        
        Date[] queryDate = DateUtil.getCurrMonthDay(0);
        Date currMonthBeginDate = queryDate[0];
        Date currMonthEndDate = queryDate[1];
        long currMonthInTreament = patientInfoService.countByTimeBetween(currMonthBeginDate, currMonthEndDate);
        
        queryDate = DateUtil.getCurrMonthDay(-1);
        currMonthBeginDate = queryDate[0];
        currMonthEndDate = queryDate[1];
        long preMonthInTreament = patientInfoService.countByTimeBetween(currMonthBeginDate, currMonthEndDate);
        
        long totalInTreament = patientInfoService.countByTimeBetween(null, null);
        
        return MapUtil.createMap("currDay", currDayInTreament, "preDay", yesterdayInTreament, "currMonth", currMonthInTreament, "preMonth", preMonthInTreament, "total", totalInTreament);
    }
}
