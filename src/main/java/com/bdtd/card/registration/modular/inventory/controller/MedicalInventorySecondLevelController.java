package com.bdtd.card.registration.modular.inventory.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventorySecondLevelService;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryStairService;
import com.bdtd.card.registration.modular.system.model.MedicalInventorySecondLevel;
import com.bdtd.card.registration.modular.system.model.MedicalInventoryStair;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.cache.DictCacheFactory;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.module.BdtdError;
import com.stylefeng.guns.core.wrapper.DictWrapperEntity;
import com.stylefeng.guns.modular.system.model.Dict;

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
    @Autowired
    private IMedicalInventoryStairService medicalInventoryStairService;
	@Autowired
	private DictCacheFactory dictCacheFactory;

    /**
     * 跳转到药品二级库存管理首页
     */
    @RequestMapping("/{medicalInventoryStairId}")
    public String index(@PathVariable Integer medicalInventoryStairId, Model model) {
    	model.addAttribute("medicalInventoryStairId", medicalInventoryStairId);
        return PREFIX + "medicalInventorySecondLevel.html";
    }

    /**
     * 跳转到添加药品二级库存管理
     */
    @RequestMapping("/medicalInventorySecondLevel_add/{medicalInventoryStairId}")
    public String medicalInventorySecondLevelAdd(@PathVariable Integer medicalInventoryStairId, Model model) {
    	List<Dict> unitDicts = dictCacheFactory.getDictListByParentName(DictConsts.MEDICAL_UNIT);
		model.addAttribute("unitDictList", unitDicts);
		List<Dict> inboundChannels = dictCacheFactory.getDictListByParentName(DictConsts.INBOUND_CHANNEL);
		model.addAttribute("inboundChannelDictList", inboundChannels);
		model.addAttribute("medicalInventoryStairId", medicalInventoryStairId);
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
        
        MedicalInventoryStair medicalInventoryStair = medicalInventoryStairService.selectById(medicalInventorySecondLevel.getParentId());
        model.addAttribute("medicalName", medicalInventoryStair.getMedicalName());
    	List<Dict> unitDicts = dictCacheFactory.getDictListByParentName(DictConsts.MEDICAL_UNIT);
		model.addAttribute("unitDictList", unitDicts);
		List<Dict> inboundChannels = dictCacheFactory.getDictListByParentName(DictConsts.INBOUND_CHANNEL);
		model.addAttribute("inboundChannelDictList", inboundChannels);
        return PREFIX + "medicalInventorySecondLevel_edit.html";
    }


    /**
     * 跳转到修改药品二级库存管理
     */
    @RequestMapping("/medicalInventorySecondLevel_putInStorage/{medicalInventorySecondLevelId}")
    public String medicalInventorySecondLevelPutInStorage(@PathVariable Integer medicalInventorySecondLevelId, Model model) {
        MedicalInventorySecondLevel medicalInventorySecondLevel = medicalInventorySecondLevelService.selectById(medicalInventorySecondLevelId);
        model.addAttribute("item",medicalInventorySecondLevel);
        LogObjectHolder.me().set(medicalInventorySecondLevel);
        
        MedicalInventoryStair medicalInventoryStair = medicalInventoryStairService.selectById(medicalInventorySecondLevel.getParentId());
        model.addAttribute("medicalName", medicalInventoryStair.getMedicalName());
    	List<Dict> unitDicts = dictCacheFactory.getDictListByParentName(DictConsts.MEDICAL_UNIT);
		model.addAttribute("unitDictList", unitDicts);
		List<Dict> inboundChannels = dictCacheFactory.getDictListByParentName(DictConsts.INBOUND_CHANNEL);
		model.addAttribute("inboundChannelDictList", inboundChannels);
        return PREFIX + "medicalInventorySecondLevel_putInStorage.html";
    }


    /**
     * 跳转到修改药品二级库存管理
     */
    @RequestMapping("/medicalInventorySecondLevel_outOfStorage/{medicalInventorySecondLevelId}")
    public String medicalInventorySecondLevelOutOfStorage(@PathVariable Integer medicalInventorySecondLevelId, Model model) {
        MedicalInventorySecondLevel medicalInventorySecondLevel = medicalInventorySecondLevelService.selectById(medicalInventorySecondLevelId);
        model.addAttribute("item",medicalInventorySecondLevel);
        LogObjectHolder.me().set(medicalInventorySecondLevel);
        
        MedicalInventoryStair medicalInventoryStair = medicalInventoryStairService.selectById(medicalInventorySecondLevel.getParentId());
        model.addAttribute("medicalName", medicalInventoryStair.getMedicalName());
    	List<Dict> unitDicts = dictCacheFactory.getDictListByParentName(DictConsts.MEDICAL_UNIT);
		model.addAttribute("unitDictList", unitDicts);
		List<Dict> inboundChannels = dictCacheFactory.getDictListByParentName(DictConsts.INBOUND_CHANNEL);
		model.addAttribute("inboundChannelDictList", inboundChannels);
        return PREFIX + "medicalInventorySecondLevel_outOfStorage.html";
    }
    
    

    /**
     * 获取药品二级库存管理列表
     */
    @RequestMapping(value = "/list/{medicalInventoryStairId}")
    @ResponseBody
    public Object list(@PathVariable Integer medicalInventoryStairId, String condition) {
    	if (medicalInventoryStairId == null) {
    		return Collections.emptyList();
    	}
    	
    	//判断该药品是否存在
    	MedicalInventoryStair medicalInventoryStair = medicalInventoryStairService.selectById(medicalInventoryStairId);
    	if (medicalInventoryStair == null) {
    		return new Tip(BdtdError.DELETE_NOT_EXIST_ERROR);
    	}
    	
        Wrapper<MedicalInventorySecondLevel> wrapper = new EntityWrapper<MedicalInventorySecondLevel>();
        wrapper.eq("parent_id", medicalInventoryStairId);
        List<Map<String, Object>> maps = medicalInventorySecondLevelService.selectMaps(wrapper);
        maps.forEach((map) -> {
        	map.put("medicalName", medicalInventoryStair.getMedicalName());
        });
        
        List<DictWrapperEntity> dictwrapperEntities = new ArrayList<DictWrapperEntity>();
		dictwrapperEntities.add(new DictWrapperEntity(DictConsts.MEDICAL_UNIT, "unit", "unitStr"));
		dictwrapperEntities.add(new DictWrapperEntity(DictConsts.INBOUND_CHANNEL, "inboundChannel", "inboundChannelStr"));
		dictCacheFactory.wrapper(maps, dictwrapperEntities);
		return maps;
    }

    /**
     * 新增药品二级库存管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MedicalInventorySecondLevel medicalInventorySecondLevel, Integer medicalInventoryStairId) {
    	medicalInventorySecondLevel.setParentId(medicalInventoryStairId);
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
     * 新增药品二级库存管理
     */
    @RequestMapping(value = "/storage")
    @ResponseBody
    public Object storage(Integer id, Long inventoryNum) {
        medicalInventorySecondLevelService.storage(id, inventoryNum);
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
