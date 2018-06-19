package com.bdtd.card.registration.modular.inventory.controller;

import java.util.ArrayList;
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
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventorySecondLevelService;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryStairService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.cache.DictCacheFactory;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.module.BdtdError;
import com.stylefeng.guns.core.wrapper.DictWrapperEntity;
import com.stylefeng.guns.modular.system.model.Dict;
import com.stylefeng.guns.modular.system.model.MedicalInventorySecondLevel;
import com.stylefeng.guns.modular.system.model.MedicalInventoryStair;

/**
 * 药品管理控制器
 *
 * @author fengshuonan
 * @Date 2018-06-14 12:56:09
 */
@Controller
@RequestMapping("/medicalInventoryStair")
public class MedicalInventoryStairController extends BaseController {

	private String PREFIX = "/inventory/medicalInventoryStair/";

	@Autowired
	private IMedicalInventoryStairService medicalInventoryStairService;
	@Autowired
	private DictCacheFactory dictCacheFactory;
	@Autowired
	private IMedicalInventorySecondLevelService medicalInventorySecondLevelService;
	/**
	 * 跳转到药品管理首页
	 */
	@RequestMapping("")
	public String index() {
		return PREFIX + "medicalInventoryStair.html";
	}

	/**
	 * 跳转到添加药品管理
	 */
	@RequestMapping("/medicalInventoryStair_add")
	public String medicalInventoryStairAdd(Model model) {
		List<Dict> unitDicts = dictCacheFactory.getDictListByParentName(DictConsts.MEDICAL_UNIT);
		model.addAttribute("unitDictList", unitDicts);
		List<Dict> specificationDicts = dictCacheFactory.getDictListByParentName(DictConsts.MEDICAL_SPECIFICATION);
		model.addAttribute("specificationDictList", specificationDicts);
		List<Dict> producerDicts = dictCacheFactory.getDictListByParentName(DictConsts.MEDICAL_PRODUCER);
		model.addAttribute("producerDictList", producerDicts);
		return PREFIX + "medicalInventoryStair_add.html";
	}

	/**
	 * 跳转到修改药品管理
	 */
	@RequestMapping("/medicalInventoryStair_update/{medicalInventoryStairId}")
	public String medicalInventoryStairUpdate(@PathVariable Integer medicalInventoryStairId, Model model) {
		MedicalInventoryStair medicalInventoryStair = medicalInventoryStairService.selectById(medicalInventoryStairId);
		model.addAttribute("item", medicalInventoryStair);
		LogObjectHolder.me().set(medicalInventoryStair);
		List<Dict> unitDicts = dictCacheFactory.getDictListByParentName(DictConsts.MEDICAL_UNIT);
		model.addAttribute("unitDictList", unitDicts);
		List<Dict> specificationDicts = dictCacheFactory.getDictListByParentName(DictConsts.MEDICAL_SPECIFICATION);
		model.addAttribute("specificationDictList", specificationDicts);
		List<Dict> producerDicts = dictCacheFactory.getDictListByParentName(DictConsts.MEDICAL_PRODUCER);
		model.addAttribute("producerDictList", producerDicts);
		return PREFIX + "medicalInventoryStair_edit.html";
	}

	/**
	 * 获取药品管理列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(String condition) {
		List<Map<String, Object>> dictMap = medicalInventoryStairService.selectMaps(null);
		List<DictWrapperEntity> dictwrapperEntities = new ArrayList<DictWrapperEntity>();
		dictwrapperEntities.add(new DictWrapperEntity(DictConsts.MEDICAL_UNIT, "unit", "unitStr"));
		dictwrapperEntities.add(new DictWrapperEntity(DictConsts.MEDICAL_SPECIFICATION, "specification", "specificationStr"));
		dictwrapperEntities.add(new DictWrapperEntity(DictConsts.MEDICAL_PRODUCER, "producer", "producerStr"));
		dictCacheFactory.wrapper(dictMap, dictwrapperEntities);
		return dictMap;
	}

	/**
	 * 新增药品管理
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Object add(MedicalInventoryStair medicalInventoryStair) {
		medicalInventoryStairService.insert(medicalInventoryStair);
		return SUCCESS_TIP;
	}

	/**
	 * 删除药品管理
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public Object delete(@RequestParam Integer medicalInventoryStairId) {
		EntityWrapper<MedicalInventorySecondLevel> wrapper = new EntityWrapper<MedicalInventorySecondLevel>();
		wrapper.eq("parent_id", medicalInventoryStairId);
		int count = medicalInventorySecondLevelService.selectCount(wrapper);
		if (count > 0) {
			return new Tip(BdtdError.DELETE_CASCADE_ERROR);
		}
		medicalInventoryStairService.deleteById(medicalInventoryStairId);
		return SUCCESS_TIP;
	}

	/**
	 * 修改药品管理
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Object update(MedicalInventoryStair medicalInventoryStair) {
		medicalInventoryStairService.updateById(medicalInventoryStair);
		return SUCCESS_TIP;
	}

	/**
	 * 药品管理详情
	 */
	@RequestMapping(value = "/detail/{medicalInventoryStairId}")
	@ResponseBody
	public Object detail(@PathVariable("medicalInventoryStairId") Integer medicalInventoryStairId) {
		return medicalInventoryStairService.selectById(medicalInventoryStairId);
	}
}
