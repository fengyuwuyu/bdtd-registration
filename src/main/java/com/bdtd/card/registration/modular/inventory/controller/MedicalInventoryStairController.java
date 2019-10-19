package com.bdtd.card.registration.modular.inventory.controller;

import java.util.Collections;
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
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryStairService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.annotion.DictEntity;
import com.stylefeng.guns.core.common.annotion.DictHandler;
import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.model.EnumOperator;
import com.stylefeng.guns.core.util.CommonUtils;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.modular.system.model.MedicalInventoryStair;

/**
 * 药品目录管理控制器
 *
 * @author 
 * @Date 2018-06-28 11:42:09
 */
@Controller
@RequestMapping("/medicalInventoryStair")
public class MedicalInventoryStairController extends BaseController {

    private String PREFIX = "/inventory/medicalInventoryStair/";

    @Autowired
    private IMedicalInventoryStairService medicalInventoryStairService;

    /**
     * 跳转到药品目录管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "medicalInventoryStair.html";
    }

    /**
     * 跳转到添加药品目录管理
     */
    @RequestMapping("/medicalInventoryStair_add")
	@DictHandler(dictModels = { DictConsts.MEDICAL_UNIT, DictConsts.MEDICAL_SPECIFICATION }, dictKeys = { 
					DictConsts.MEDICAL_UNIT_KEY, DictConsts.MEDICAL_SPECIFICATION_KEY })
    public String medicalInventoryStairAdd(Model model) {
        return PREFIX + "medicalInventoryStair_add.html";
    }

    /**
     * 跳转到修改药品目录管理
     */
    @RequestMapping("/medicalInventoryStair_update/{medicalInventoryStairId}")
	@DictHandler(dictModels = { DictConsts.MEDICAL_UNIT, DictConsts.MEDICAL_SPECIFICATION }, dictKeys = { 
					DictConsts.MEDICAL_UNIT_KEY, DictConsts.MEDICAL_SPECIFICATION_KEY })
    public String medicalInventoryStairUpdate(@PathVariable Integer medicalInventoryStairId, Model model) {
        MedicalInventoryStair medicalInventoryStair = medicalInventoryStairService.selectById(medicalInventoryStairId);
        model.addAttribute("item",medicalInventoryStair);
        LogObjectHolder.me().set(medicalInventoryStair);
        return PREFIX + "medicalInventoryStair_edit.html";
    }

    /**
     * 获取药品目录管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @DictHandler(dictWrappers = {
			@DictEntity(parentId = DictConsts.MEDICAL_UNIT, fieldName = DictConsts.MEDICAL_UNIT_FIELD_NAME),
			@DictEntity(parentId = DictConsts.MEDICAL_SPECIFICATION, fieldName = DictConsts.MEDICAL_SPECIFICATION_FIELD_NAME)
	})
	public Object list(String medicalName, String producer, String remark, Integer offset, Integer limit) {
    	Wrapper<MedicalInventoryStair> wrapper = new EntityWrapper<>();
    	CommonUtils.handleRequestParams(wrapper, "medical_name", medicalName, EnumOperator.LIKE, "producer", producer, EnumOperator.LIKE, "remark", remark, EnumOperator.LIKE);
    	Page<Map<String, Object>> page = medicalInventoryStairService.selectMapsPage(new Page<>(offset / limit + 1, limit, Consts.DEFAULT_SORT_FIELD, Consts.DEFAULT_SORT_ORDER_IS_ASC), wrapper);
		return MapUtil.createSuccessMap("rows", page.getRecords(), "total", page.getTotal());
    }

    /**
     * 新增药品目录管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MedicalInventoryStair medicalInventoryStair) {
        Date createDate = new Date();
        medicalInventoryStair.setCreateDate(createDate);
        medicalInventoryStair.setUpdateDate(createDate);
        medicalInventoryStairService.insert(medicalInventoryStair);
        return SUCCESS_TIP;
    }

    /**
     * 删除药品目录管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer medicalInventoryStairId) {
        medicalInventoryStairService.deleteById(medicalInventoryStairId);
        return SUCCESS_TIP;
    }

    /**
     * 修改药品目录管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(MedicalInventoryStair medicalInventoryStair) {
        medicalInventoryStair.setUpdateDate(new Date());
        medicalInventoryStairService.updateById(medicalInventoryStair);
        return SUCCESS_TIP;
    }

    /**
     * 药品目录管理详情
     */
    @RequestMapping(value = "/detail/{medicalInventoryStairId}")
    @ResponseBody
    public Object detail(@PathVariable("medicalInventoryStairId") Integer medicalInventoryStairId) {
        return medicalInventoryStairService.selectById(medicalInventoryStairId);
    }

    /**
     * 药品目录管理详情
     */
    @RequestMapping(value = "/findByMedicalSpell")
    @ResponseBody
    @DictHandler(dictWrappers = {
            @DictEntity(parentId = DictConsts.MEDICAL_UNIT, fieldName = DictConsts.MEDICAL_UNIT_FIELD_NAME),
            @DictEntity(parentId = DictConsts.MEDICAL_SPECIFICATION, fieldName = DictConsts.MEDICAL_SPECIFICATION_FIELD_NAME)
    })
    public Object findByMedicalSpell(String q) {
        if (StringUtil.isNullEmpty(q)) {
            return Collections.emptyList();
        }
        Wrapper<MedicalInventoryStair> wrapper = new EntityWrapper<>();
        wrapper.like("spell", q.toUpperCase());
        wrapper.or().like("medical_name", q);
        return medicalInventoryStairService.selectMaps(wrapper);
    }
    
}
