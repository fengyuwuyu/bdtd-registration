package com.bdtd.card.registration.modular.inventory.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.bdtd.card.registration.common.model.EnumMedicalInventoryStorageType;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryDrugStorageService;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryPharmacyService;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryStairService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.common.annotion.DictEntity;
import com.stylefeng.guns.core.common.annotion.DictHandler;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.DictCacheFactory;
import com.stylefeng.guns.core.util.DownLoadUtil;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.core.wrapper.DictWrapperEntity;
import com.stylefeng.guns.modular.system.model.MedicalInventoryDrugStorage;
import com.stylefeng.guns.modular.system.model.MedicalInventoryPharmacy;
import com.stylefeng.guns.modular.system.model.MedicalInventoryStair;
import com.stylefeng.guns.scmmain.model.DtDep;
import com.stylefeng.guns.scmmain.service.IDtDepService;

/**
 * 药房管理控制器
 *
 * @author
 * @Date 2018-06-28 11:42:45
 */
@Controller
@RequestMapping("/medicalInventoryPharmacy")
public class MedicalInventoryPharmacyController extends BaseController {

    private String PREFIX = "/inventory/medicalInventoryPharmacy/";

    @Autowired
    private IMedicalInventoryPharmacyService medicalInventoryPharmacyService;
    @Autowired
    private IMedicalInventoryStairService medicalInventoryStairService;
    @Autowired
    private IDtDepService dtDepService;
    @Autowired
    private IMedicalInventoryDrugStorageService medicalInventoryDrugStorageService;
    @Autowired
    private DictCacheFactory dictCacheFactory;
    @Value("${guns.file-upload-path}")
    private String tmpPath;

    /**
     * 跳转到药房管理首页
     */
    @RequestMapping("")
    @DictHandler(dictModels = { DictConsts.MEDICAL_INBOUND_CHANNEL }, dictKeys = {
            DictConsts.MEDICAL_INBOUND_CHANNEL_KEY })
    public String index(Model model) {
        return PREFIX + "medicalInventoryPharmacy.html";
    }
    
    /**
     * 跳转到药房管理首页
     */
    @RequestMapping("/patient")
    @DictHandler(dictModels = { DictConsts.MEDICAL_INBOUND_CHANNEL }, dictKeys = {
            DictConsts.MEDICAL_INBOUND_CHANNEL_KEY })
    public String patient(Model model) {
        return PREFIX + "medicalInventoryPharmacy_patient.html";
    }

    /**
     * 跳转到添加药房管理
     */
    @RequestMapping("/medicalInventoryPharmacy_add")
    @DictHandler(dictModels = { DictConsts.MEDICAL_INBOUND_CHANNEL }, dictKeys = {
            DictConsts.MEDICAL_INBOUND_CHANNEL_KEY })
    public String medicalInventoryPharmacyAdd(Model model) {
        return PREFIX + "medicalInventoryPharmacy_add.html";
    }

    /**
     * 跳转到添加药库管理
     */
    @RequestMapping("/medicalInventoryPharmacy_storageDetail/{medicalInventoryPharmacyId}")
    @DictHandler(dictModels = { DictConsts.MEDICAL_INBOUND_CHANNEL }, dictKeys = {
            DictConsts.MEDICAL_INBOUND_CHANNEL_KEY })
    public String medicalInventoryPharmacyStorageDetail(@PathVariable Integer medicalInventoryPharmacyId, Model model) {
        model.addAttribute("parentId", medicalInventoryPharmacyId);
        List<Map<String, Object>> inventoryLogTypeList = EnumMedicalInventoryStorageType.select();
        model.addAttribute("inventoryLogTypeList", inventoryLogTypeList);
        return PREFIX + "medicalInventoryPharmacy_storageDetail.html";
    }

    /**
     * 跳转到修改药房管理
     */
    @RequestMapping("/medicalInventoryPharmacy_update/{medicalInventoryPharmacyId}")
    @DictHandler(dictModels = { DictConsts.MEDICAL_INBOUND_CHANNEL }, dictKeys = {
            DictConsts.MEDICAL_INBOUND_CHANNEL_KEY })
    public String medicalInventoryPharmacyUpdate(@PathVariable Integer medicalInventoryPharmacyId, Model model) {
        Wrapper<MedicalInventoryPharmacy> wrapper = new EntityWrapper<>();
        wrapper.eq("id", medicalInventoryPharmacyId);
        Map<String, Object> result = medicalInventoryPharmacyService.selectMap(wrapper);

        Integer parentId = (Integer) result.get("parentId");
        MedicalInventoryStair parent = medicalInventoryStairService.selectById(parentId);
        result.put("medicalName", parent.getMedicalName());
        model.addAttribute("item", result);
        LogObjectHolder.me().set(result);
        return PREFIX + "medicalInventoryPharmacy_edit.html";
    }

    /**
     * 跳转到修改药库管理
     */
    @RequestMapping("/medicalInventoryPharmacy_putInStorage/{medicalInventoryPharmacyId}")
    @DictHandler(dictModels = { DictConsts.MEDICAL_INBOUND_CHANNEL }, dictKeys = {
            DictConsts.MEDICAL_INBOUND_CHANNEL_KEY })
    public String medicalInventoryPharmacyPutInStorage(@PathVariable Integer medicalInventoryPharmacyId, Model model) {
        Wrapper<MedicalInventoryPharmacy> wrapper = new EntityWrapper<>();
        wrapper.eq("id", medicalInventoryPharmacyId);
        Map<String, Object> result = medicalInventoryPharmacyService.selectMap(wrapper);

        Integer parentId = (Integer) result.get("parentId");
        MedicalInventoryStair parent = medicalInventoryStairService.selectById(parentId);
        result.put("medicalName", parent.getMedicalName());
        model.addAttribute("item", result);
        LogObjectHolder.me().set(result);
        return PREFIX + "medicalInventoryPharmacy_putInStorage.html";
    }
    
    /**
     * 跳转到修改药库管理
     */
    @RequestMapping("/openPutInDrugStorage/{medicalInventoryPharmacyId}")
    @DictHandler(dictModels = { DictConsts.MEDICAL_INBOUND_CHANNEL }, dictKeys = {
            DictConsts.MEDICAL_INBOUND_CHANNEL_KEY })
    public String openPutInDrugStorage(@PathVariable Integer medicalInventoryPharmacyId, Model model) {
        Wrapper<MedicalInventoryPharmacy> wrapper = new EntityWrapper<>();
        wrapper.eq("id", medicalInventoryPharmacyId);
        Map<String, Object> result = medicalInventoryPharmacyService.selectMap(wrapper);
        
        Integer parentId = (Integer) result.get("parentId");
        MedicalInventoryStair parent = medicalInventoryStairService.selectById(parentId);
        result.put("medicalName", parent.getMedicalName());
        model.addAttribute("item", result);
        LogObjectHolder.me().set(result);
        return PREFIX + "medicalInventoryPharmacy_putInDrugStorage.html";
    }

    /**
     * 跳转到修改药库管理
     */
    @RequestMapping("/medicalInventoryPharmacy_putOffStorage/{medicalInventoryPharmacyId}")
    @DictHandler(dictModels = { DictConsts.MEDICAL_INBOUND_CHANNEL }, dictKeys = {
            DictConsts.MEDICAL_INBOUND_CHANNEL_KEY })
    public String medicalInventoryPharmacyPutOffStorage(@PathVariable Integer medicalInventoryPharmacyId, Model model) {
        Wrapper<MedicalInventoryPharmacy> wrapper = new EntityWrapper<>();
        wrapper.eq("id", medicalInventoryPharmacyId);
        Map<String, Object> result = medicalInventoryPharmacyService.selectMap(wrapper);

        Integer parentId = (Integer) result.get("parentId");
        MedicalInventoryStair parent = medicalInventoryStairService.selectById(parentId);
        result.put("medicalName", parent.getMedicalName());
        model.addAttribute("item", result);
        LogObjectHolder.me().set(result);
        model.addAttribute("dtDepItemList", this.dtDepService.select());
        return PREFIX + "medicalInventoryPharmacy_putOffStorage.html";
    }

    /**
     * 获取药房管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @DictHandler(dictWrappers = {
            @DictEntity(parentId = DictConsts.MEDICAL_UNIT, fieldName = DictConsts.MEDICAL_UNIT_FIELD_NAME),
            @DictEntity(parentId = DictConsts.MEDICAL_INBOUND_CHANNEL, fieldName = DictConsts.MEDICAL_INBOUND_CHANNEL_FIELD_NAME),
            @DictEntity(parentId = DictConsts.MEDICAL_SPECIFICATION, fieldName = DictConsts.MEDICAL_SPECIFICATION_FIELD_NAME) })
    public Map<String, Object> list(String medicalName, String producer, Integer inboundChannel, java.sql.Date expiredDate,
            Integer offset, Integer limit) {
        long total = medicalInventoryPharmacyService.selectCountMaps(MapUtil.createMap("medicalName", medicalName,
                "producer", producer, "inboundChannel", inboundChannel, "expiredDate", expiredDate));
        if (total == 0) {
            return MapUtil.createSuccessMap("rows", Collections.emptyList(), "total", total);
        }
        List<Map<String, Object>> list = medicalInventoryPharmacyService
                .selectPagedMaps(MapUtil.createMap("medicalName", medicalName, "producer", producer, "inboundChannel",
                        inboundChannel, "expiredDate", expiredDate, "offset", offset, "limit", limit));
        return MapUtil.createSuccessMap("rows", list, "total", total);
    }

    
    @SuppressWarnings("unchecked")
    @RequestMapping("/exportExcel")
    public void exportExcel(String medicalName, String producer, Integer inboundChannel, java.sql.Date expiredDate, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = this.list(medicalName, producer, inboundChannel, expiredDate, 0, Integer.MAX_VALUE);
        List<Map<String, Object>> rows = (List<Map<String, Object>>) map.get("rows");
        List<DictWrapperEntity> dictwrapperEntities = Arrays.asList(
                new DictWrapperEntity(DictConsts.MEDICAL_INBOUND_CHANNEL, DictConsts.MEDICAL_INBOUND_CHANNEL_FIELD_NAME),
                new DictWrapperEntity(DictConsts.MEDICAL_UNIT, DictConsts.MEDICAL_UNIT_FIELD_NAME),
                new DictWrapperEntity(DictConsts.MEDICAL_SPECIFICATION, DictConsts.MEDICAL_SPECIFICATION_FIELD_NAME)
                );
        this.dictCacheFactory.wrapper(rows, dictwrapperEntities);
        String fileName = "药房药品.xls";
        String[] headers = { "药品名称", "生产企业", "生产批号", "生产日期", "有效期至", "价格", "在库数量", "单位", "规格", "进货渠道", "备注"};
        String[] fields = { "medicalName", "producer", "produceBatchNum", "produceDate", "expireDate", "price", "inventoryNum", "unit", "specification", "inboundChannel", "remark"};
        DownLoadUtil.exportExcelAndDownload("药房药品", headers, fields, rows, response, request, tmpPath, fileName);
    }


    /**
     * 新增药房管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MedicalInventoryPharmacy medicalInventoryPharmacy) {
        if (medicalInventoryPharmacy.getParentId() == null) {
            return new Tip(500, "请选择药品！");
        }

//        Tip tip = countByProduceBatchNum(medicalInventoryPharmacy.getProduceBatchNum());
//        if (tip != null) {
//            return tip;
//        }

        Date createDate = new Date();
        medicalInventoryPharmacy.setCreateDate(createDate);
        medicalInventoryPharmacy.setUpdateDate(createDate);
        medicalInventoryPharmacyService.insert(medicalInventoryPharmacy);
        return SUCCESS_TIP;
    }

    public Tip countByProduceBatchNum(String batchNum) {
        Wrapper<MedicalInventoryPharmacy> wrapper = new EntityWrapper<>();
        wrapper.eq("produce_batch_num", batchNum);
        int count = medicalInventoryPharmacyService.selectCount(wrapper);
        if (count != 0) {
            return new Tip(500, "已存在该批号的药品！");
        }
        return null;
    }

    /**
     * 删除药房管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer medicalInventoryPharmacyId) {
        medicalInventoryPharmacyService.deleteById(medicalInventoryPharmacyId);
        return SUCCESS_TIP;
    }

    /**
     * 修改药房管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(MedicalInventoryPharmacy medicalInventoryPharmacy) {
        medicalInventoryPharmacy.setUpdateDate(new Date());
        medicalInventoryPharmacyService.updateById(medicalInventoryPharmacy);
        return SUCCESS_TIP;
    }

    /**
     * 药房管理详情
     */
    @RequestMapping(value = "/detail/{medicalInventoryPharmacyId}")
    @ResponseBody
    public Object detail(@PathVariable("medicalInventoryPharmacyId") Integer medicalInventoryPharmacyId) {
        return medicalInventoryPharmacyService.selectById(medicalInventoryPharmacyId);
    }

    // /**
    // * 删除药库管理
    // */
    // @RequestMapping(value = "/putInStorage")
    // @ResponseBody
    // @Transactional
    // public Object putInStorage(Integer id, Integer count) {
    // int modifyNum = medicalInventoryPharmacyService.updateInventoryCount(id,
    // count);
    // if (modifyNum == 0) {
    // return new Tip(500, "该药品已被删除！");
    // }
    // return SUCCESS_TIP;
    // }
    
    /**
     * 出药房
     */
    @RequestMapping(value = "/putInDrugStorage")
    @ResponseBody
    @Transactional
    public Object putInDrugStorage( @RequestParam(required=true)Integer id, @RequestParam(required=true) Integer count) {
        if (count <= 0) {
            return new Tip(500L, "非法的数量！");
        }
        MedicalInventoryPharmacy inventoryPharmacy = this.medicalInventoryPharmacyService.selectById(id);
        if (inventoryPharmacy == null) {
            return new Tip(500L, "该药品已被删除！");
        }
        
        if (inventoryPharmacy.getInventoryNum().intValue() < count) {
            return new Tip(500L, "药品库存不足！");
        }
        
        Wrapper<MedicalInventoryDrugStorage> wrapper = new EntityWrapper<>();
        wrapper.eq("produce_batch_num", inventoryPharmacy.getProduceBatchNum());
        MedicalInventoryDrugStorage drugStorage = this.medicalInventoryDrugStorageService.selectOne(wrapper );
        if (drugStorage == null) {
            return new Tip(500L, "药库中不存在该药品！");
        }
        
        medicalInventoryPharmacyService.updateInventoryCount(id, count * -1, "药库");
        
        this.medicalInventoryDrugStorageService.updateInventoryCount(drugStorage.getId(), count, "药库");
        return SUCCESS_TIP;
    }

    /**
     * 出药房
     */
    @RequestMapping(value = "/putOffStorage")
    @ResponseBody
    @Transactional
    public Object putOffStorage( @RequestParam(required=true)Integer id, @RequestParam(required=true) Integer count, @RequestParam(required=true) Long orgId) {
        if (count <= 0) {
            return new Tip(500L, "非法的数量！");
        }
        DtDep dep = dtDepService.findById(orgId);
        String orgName = dep.getDepName();
        return medicalInventoryPharmacyService.updateInventoryCount(id, count * -1, orgName);
    }
    
    @RequestMapping(value = "/findByMedicalSpell")
    @ResponseBody
    @DictHandler(dictWrappers = {
            @DictEntity(parentId = DictConsts.MEDICAL_UNIT, fieldName = DictConsts.MEDICAL_UNIT_FIELD_NAME, replaceFieldName=DictConsts.MEDICAL_UNIT_REPLACE_FIELD_NAME),
            @DictEntity(parentId = DictConsts.MEDICAL_SPECIFICATION, fieldName = DictConsts.MEDICAL_SPECIFICATION_FIELD_NAME, replaceFieldName="specificationStr"),
            @DictEntity(parentId = DictConsts.MEDICAL_INBOUND_CHANNEL, fieldName = DictConsts.MEDICAL_INBOUND_CHANNEL_FIELD_NAME) })
    public Object findByMedicalSpell(String q) {
        return medicalInventoryPharmacyService.selectPagedMaps(MapUtil.createMap("medicalName", q, "inventoryNum", 0, "offset", 0, "limit", 1000));
    }
    
}
