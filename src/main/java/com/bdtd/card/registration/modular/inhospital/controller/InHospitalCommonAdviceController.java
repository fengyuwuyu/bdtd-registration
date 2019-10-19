package com.bdtd.card.registration.modular.inhospital.controller;

import java.util.Date;
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
import com.bdtd.card.registration.modular.inhospital.service.IInHospitalCommonAdviceService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.annotion.DictEntity;
import com.stylefeng.guns.core.common.annotion.DictHandler;
import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.modular.system.model.InHospitalCommonAdvice;

/**
 * 常规医嘱控制器
 *
 * @author 
 * @Date 2018-08-13 11:18:59
 */
@Controller
@RequestMapping("/inHospitalCommonAdvice")
public class InHospitalCommonAdviceController extends BaseController {

    private String PREFIX = "/inhospital/inHospitalCommonAdvice/";

    @Autowired
    private IInHospitalCommonAdviceService inHospitalCommonAdviceService;

    /**
     * 跳转到常规医嘱首页
     */
    @RequestMapping("/{inHospitalId}")
    public String index(@PathVariable Integer inHospitalId, Model model) {
        model.addAttribute("inHospitalId", inHospitalId);
        return PREFIX + "inHospitalCommonAdvice.html";
    }
    
    /**
     * 跳转到常规医嘱首页
     */
    @RequestMapping("/inHospitalCommonAdviceDetail/{inHospitalId}")
    public String inHospitalCommonAdviceDetail(@PathVariable Integer inHospitalId, Model model) {
        model.addAttribute("inHospitalId", inHospitalId);
        return PREFIX + "inHospitalCommonAdviceDetail.html";
    }

    /**
     * 跳转到添加常规医嘱
     */
    @RequestMapping("/inHospitalCommonAdvice_add/{inHospitalId}")
    @DictHandler(dictModels= {DictConsts.COMMON_ADVICE, DictConsts.DOCTOR}, dictKeys= {DictConsts.COMMON_ADVICE_KEY, DictConsts.DOCTOR_KEY})
    public String inHospitalCommonAdviceAdd(@PathVariable Integer inHospitalId, Model model) {
        model.addAttribute("physicianName", ShiroKit.getUser().getDtUser().getUserLname());
        model.addAttribute("inHospitalId", inHospitalId);
        model.addAttribute("now", new Date());
        return PREFIX + "inHospitalCommonAdvice_add.html";
    }

    /**
     * 跳转到修改常规医嘱
     */
    @RequestMapping("/inHospitalCommonAdvice_update/{inHospitalCommonAdviceId}")
    @DictHandler(dictModels= {DictConsts.COMMON_ADVICE, DictConsts.DOCTOR}, dictKeys= {DictConsts.COMMON_ADVICE_KEY, DictConsts.DOCTOR_KEY})
    public String inHospitalCommonAdviceUpdate(@PathVariable Integer inHospitalCommonAdviceId, Model model) {
        InHospitalCommonAdvice inHospitalCommonAdvice = inHospitalCommonAdviceService.selectById(inHospitalCommonAdviceId);
        model.addAttribute("item",inHospitalCommonAdvice);
        LogObjectHolder.me().set(inHospitalCommonAdvice);
        return PREFIX + "inHospitalCommonAdvice_edit.html";
    }

    /**
     * 获取常规医嘱列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @DictHandler(dictWrappers= {@DictEntity(parentId=DictConsts.COMMON_ADVICE, fieldName=DictConsts.COMMON_ADVICE_FIELD_NAME)})
    public Object list(@RequestParam(required=true) Integer inHospitalId, Integer offset, Integer limit) {
    	Wrapper<InHospitalCommonAdvice> wrapper = new EntityWrapper<>();
    	wrapper.eq("in_hospital_id", inHospitalId);
    	Page<Map<String, Object>> page = inHospitalCommonAdviceService.selectMapsPage(new Page<>(offset, limit, Consts.DEFAULT_SORT_FIELD, Consts.DEFAULT_SORT_ORDER_IS_ASC), wrapper);
		return MapUtil.createSuccessMap("rows", page.getRecords(), "total", page.getTotal());
    }

    /**
     * 新增常规医嘱
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(String commonAdviceDateStr, InHospitalCommonAdvice inHospitalCommonAdvice) {
        Date createDate = new Date();
        inHospitalCommonAdvice.setCreateDate(createDate);
        inHospitalCommonAdvice.setUpdateDate(createDate);
        inHospitalCommonAdvice.setCommonAdviceDate(DateUtil.parse(commonAdviceDateStr, Consts.DATE_TIME_PATTERN));
        inHospitalCommonAdviceService.insert(inHospitalCommonAdvice);
        return SUCCESS_TIP;
    }

    /**
     * 删除常规医嘱
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer inHospitalCommonAdviceId) {
        inHospitalCommonAdviceService.deleteById(inHospitalCommonAdviceId);
        return SUCCESS_TIP;
    }

    /**
     * 修改常规医嘱
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(String commonAdviceDateStr, InHospitalCommonAdvice inHospitalCommonAdvice) {
        inHospitalCommonAdvice.setUpdateDate(new Date());
        inHospitalCommonAdvice.setCommonAdviceDate(DateUtil.parse(commonAdviceDateStr, Consts.DATE_TIME_PATTERN));
        inHospitalCommonAdviceService.updateById(inHospitalCommonAdvice);
        return SUCCESS_TIP;
    }

    /**
     * 常规医嘱详情
     */
    @RequestMapping(value = "/detail/{inHospitalCommonAdviceId}")
    @ResponseBody
    public Object detail(@PathVariable("inHospitalCommonAdviceId") Integer inHospitalCommonAdviceId) {
        return inHospitalCommonAdviceService.selectById(inHospitalCommonAdviceId);
    }
}
