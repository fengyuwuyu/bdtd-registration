package com.bdtd.card.registration.modular.inhospital.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.bdtd.card.registration.common.model.EnumTemperatureRecord;
import com.bdtd.card.registration.common.model.chart.CylinderShapeEntity;
import com.bdtd.card.registration.modular.inhospital.service.IInHospitalTemperatureRecordService;
import com.bdtd.card.registration.modular.inhospital.service.IPatientInHospitalService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.DownLoadUtil;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.modular.system.model.InHospitalTemperatureRecord;
import com.stylefeng.guns.modular.system.model.PatientInHospital;
import com.stylefeng.guns.scmmain.model.DtUser;
import com.stylefeng.guns.scmmain.service.IDtUserService;

/**
 * 体温记录控制器
 *
 * @author 
 * @Date 2018-07-08 17:07:23
 */
@Controller
@RequestMapping("/inHospitalTemperatureRecord")
public class InHospitalTemperatureRecordController extends BaseController {

    private String PREFIX = "/inhospital/inHospitalTemperatureRecord/";

    @Autowired
    private IInHospitalTemperatureRecordService inHospitalTemperatureRecordService;
    @Autowired
    private IPatientInHospitalService patientInHospitalService;
    @Autowired
    private IDtUserService dtUserService;

    @Value("${guns.file-upload-path}")
    private String tmpPath;

    /**
     * 跳转到体温记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "inHospitalTemperatureRecord.html";
    }

    @RequestMapping(value = "/exportExcel")
    public void exportExcel(@RequestParam(required=true) Integer inHospitalId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Wrapper<PatientInHospital> wrapper = new EntityWrapper<>();
        wrapper.eq("id", inHospitalId);
        Map<String, Object> inHospitalInfo = this.patientInHospitalService.selectMap(wrapper);
        if (inHospitalInfo == null) {
            return ;
        }
        
        Wrapper<InHospitalTemperatureRecord> temperatureRecordWrapper = new EntityWrapper<>();
        temperatureRecordWrapper.eq("in_hospital_id", inHospitalId);
        List<Map<String, Object>> rows = this.inHospitalTemperatureRecordService.selectMaps(temperatureRecordWrapper);

        String userNo = (String) inHospitalInfo.get("userNo");
        DtUser dtUser = dtUserService.findByUserNo(userNo);
        String title = "体温记录";
        if (dtUser != null) {
            title = title + "-" + dtUser.getUserDepname() + "-" + dtUser.getUserLname() + "-" + dtUser.getUserDuty();
        }
        String fileName = title + ".xls";
        String[] headers = { "体温", "记录日期", "时段", "操作员" };
        String[] fields = { "animalHeat", "recordDate", "time", "operatorName"};
        DownLoadUtil.exportExcelAndDownload("体温记录", headers, fields, rows, response, request, tmpPath, fileName);
    }

    /**
     * 跳转到添加体温记录
     */
    @RequestMapping("/openTemperatureRecord/{inHospitalId}")
    public String openTemperatureRecord(@PathVariable Integer inHospitalId, Model model) {
        model.addAttribute("inHospitalId", inHospitalId);
        model.addAttribute("now", new java.sql.Date(System.currentTimeMillis()));
        model.addAttribute("temperatureItemList", EnumTemperatureRecord.select());
        return PREFIX + "inHospitalTemperatureRecord_add.html";
    }

    @RequestMapping("/openTemperatureRecordDetail/{inHospitalId}")
    public String openTemperatureRecordDetail(@PathVariable Integer inHospitalId, Model model) {
        Map<String, Object> result = this.patientInHospitalService.findById(inHospitalId);
        model.addAttribute("item", result);
        return PREFIX + "inHospitalTemperatureRecord_showChart.html";
    }
    
    /**
     * 跳转到修改体温记录
     */
    @RequestMapping("/inHospitalTemperatureRecord_update/{inHospitalTemperatureRecordId}")
    public String inHospitalTemperatureRecordUpdate(@PathVariable Integer inHospitalTemperatureRecordId, Model model) {
        InHospitalTemperatureRecord inHospitalTemperatureRecord = inHospitalTemperatureRecordService.selectById(inHospitalTemperatureRecordId);
        model.addAttribute("item",inHospitalTemperatureRecord);
        LogObjectHolder.me().set(inHospitalTemperatureRecord);
        return PREFIX + "inHospitalTemperatureRecord_edit.html";
    }

    /**
     * 获取体温记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(Integer inHospitalId, Integer offset, Integer limit) {
        if (inHospitalId == null) {
            return MapUtil.createSuccessMap("rows", Collections.emptyList(), "total", 0);
        }
    	Wrapper<InHospitalTemperatureRecord> wrapper = new EntityWrapper<>();
        wrapper.eq("in_hospital_id", inHospitalId);
    	Page<Map<String, Object>> page = inHospitalTemperatureRecordService.selectMapsPage(new Page<>(offset / limit + 1, limit, Consts.DEFAULT_SORT_FIELD, Consts.DEFAULT_SORT_ORDER_IS_ASC), wrapper);
		return MapUtil.createSuccessMap("rows", page.getRecords(), "total", page.getTotal());
    }

    /**
     * 获取体温记录列表
     */
    @RequestMapping(value = "/chartList")
    @ResponseBody
    public Object chartList(@RequestParam(required=true) Integer inHospitalId) {
        CylinderShapeEntity<Float> shapeEntity = inHospitalTemperatureRecordService.chartList(inHospitalId);
        return MapUtil.createSuccessMap("data", shapeEntity);
    }

    /**
     * 新增体温记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(InHospitalTemperatureRecord inHospitalTemperatureRecord) {
        if (inHospitalTemperatureRecord.getInHospitalId() == null) {
            return new Tip(500, "非法参数！");
        }
        try {
            inHospitalTemperatureRecord.setOperatorName(ShiroKit.getUser().getDtUser().getUserLname());
            inHospitalTemperatureRecord.setOperatorNo(ShiroKit.getUser().getDtUser().getUserNo());
            inHospitalTemperatureRecordService.insert(inHospitalTemperatureRecord);
        } catch (Exception e) {
            return new Tip(500, String.format("%s %d点已记录体温", inHospitalTemperatureRecord.getRecordDate(), inHospitalTemperatureRecord.getTime()));
        }
        return SUCCESS_TIP;
    }

    /**
     * 删除体温记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer inHospitalTemperatureRecordId) {
        inHospitalTemperatureRecordService.deleteById(inHospitalTemperatureRecordId);
        return SUCCESS_TIP;
    }

    /**
     * 修改体温记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(InHospitalTemperatureRecord inHospitalTemperatureRecord) {
        inHospitalTemperatureRecord.setOperatorName(ShiroKit.getUser().getDtUser().getUserLname());
        inHospitalTemperatureRecord.setOperatorNo(ShiroKit.getUser().getDtUser().getUserNo());
        inHospitalTemperatureRecordService.updateById(inHospitalTemperatureRecord);
        return SUCCESS_TIP;
    }

    /**
     * 体温记录详情
     */
    @RequestMapping(value = "/detail/{inHospitalTemperatureRecordId}")
    @ResponseBody
    public Object detail(@PathVariable("inHospitalTemperatureRecordId") Integer inHospitalTemperatureRecordId) {
        return inHospitalTemperatureRecordService.selectById(inHospitalTemperatureRecordId);
    }
}
