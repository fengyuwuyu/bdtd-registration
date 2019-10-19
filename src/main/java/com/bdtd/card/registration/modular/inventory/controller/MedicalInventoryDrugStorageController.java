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
import com.bdtd.card.registration.common.consts.InventoryConsts;
import com.bdtd.card.registration.common.model.EnumMedicalInventoryCategory;
import com.bdtd.card.registration.common.model.EnumMedicalInventoryStorageType;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryDrugStorageService;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryPharmacyService;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryStairService;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryStorageLogService;
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
import com.stylefeng.guns.modular.system.model.MedicalInventoryStair;

/**
 * 药库管理控制器
 *
 * @author
 * @Date 2018-06-28 11:42:31
 */
@Controller
@RequestMapping("/medicalInventoryDrugStorage")
public class MedicalInventoryDrugStorageController extends BaseController {

    private String PREFIX = "/inventory/medicalInventoryDrugStorage/";

    @Autowired
    private IMedicalInventoryDrugStorageService medicalInventoryDrugStorageService;
    @Autowired
    private IMedicalInventoryStairService medicalInventoryStairService;
    @Autowired
    private IMedicalInventoryStorageLogService storageLogService;

    @Autowired
    private IMedicalInventoryPharmacyService medicalInventoryPharmacyService;
    @Autowired
    private DictCacheFactory dictCacheFactory;
    @Value("${guns.file-upload-path}")
    private String tmpPath;

    /**
     * 跳转到药库管理首页
     */
    @RequestMapping("")
    @DictHandler(dictModels = { DictConsts.MEDICAL_INBOUND_CHANNEL }, dictKeys = {
            DictConsts.MEDICAL_INBOUND_CHANNEL_KEY })
    public String index(Model model) {
        return PREFIX + "medicalInventoryDrugStorage.html";
    }

    /**
     * 跳转到添加药库管理
     */
    @RequestMapping("/medicalInventoryDrugStorage_add")
    @DictHandler(dictModels = { DictConsts.MEDICAL_INBOUND_CHANNEL }, dictKeys = {
            DictConsts.MEDICAL_INBOUND_CHANNEL_KEY })
    public String medicalInventoryDrugStorageAdd(Model model) {
        return PREFIX + "medicalInventoryDrugStorage_add.html";
    }

    /**
     * 跳转到添加药库管理
     */
    @RequestMapping("/medicalInventoryDrugStorage_storageDetail/{medicalInventoryDrugStorageId}")
    @DictHandler(dictModels = { DictConsts.MEDICAL_INBOUND_CHANNEL }, dictKeys = {
            DictConsts.MEDICAL_INBOUND_CHANNEL_KEY })
    public String medicalInventoryDrugStorageStorageDetail(@PathVariable Integer medicalInventoryDrugStorageId,
            Model model) {
        model.addAttribute("parentId", medicalInventoryDrugStorageId);
        List<Map<String, Object>> inventoryLogTypeList = EnumMedicalInventoryStorageType.select();
        model.addAttribute("inventoryLogTypeList", inventoryLogTypeList);
        return PREFIX + "medicalInventoryDrugStorage_storageDetail.html";
    }

    /**
     * 跳转到修改药库管理
     */
    @RequestMapping("/medicalInventoryDrugStorage_update/{medicalInventoryDrugStorageId}")
    @DictHandler(dictModels = { DictConsts.MEDICAL_INBOUND_CHANNEL }, dictKeys = {
            DictConsts.MEDICAL_INBOUND_CHANNEL_KEY })
    public String medicalInventoryDrugStorageUpdate(@PathVariable Integer medicalInventoryDrugStorageId, Model model) {
        Wrapper<MedicalInventoryDrugStorage> wrapper = new EntityWrapper<>();
        wrapper.eq("id", medicalInventoryDrugStorageId);
        Map<String, Object> result = medicalInventoryDrugStorageService.selectMap(wrapper);

        Integer parentId = (Integer) result.get("parentId");
        MedicalInventoryStair parent = medicalInventoryStairService.selectById(parentId);
        result.put("medicalName", parent.getMedicalName());
        model.addAttribute("item", result);
        LogObjectHolder.me().set(result);
        return PREFIX + "medicalInventoryDrugStorage_edit.html";
    }

    /**
     * 跳转到修改药库管理
     */
    @RequestMapping("/medicalInventoryDrugStorage_putInStorage/{medicalInventoryDrugStorageId}")
    @DictHandler(dictModels = { DictConsts.MEDICAL_INBOUND_CHANNEL }, dictKeys = {
            DictConsts.MEDICAL_INBOUND_CHANNEL_KEY })
    public String medicalInventoryDrugStoragePutInStorage(@PathVariable Integer medicalInventoryDrugStorageId,
            Model model) {
        Wrapper<MedicalInventoryDrugStorage> wrapper = new EntityWrapper<>();
        wrapper.eq("id", medicalInventoryDrugStorageId);
        Map<String, Object> result = medicalInventoryDrugStorageService.selectMap(wrapper);

        Integer parentId = (Integer) result.get("parentId");
        MedicalInventoryStair parent = medicalInventoryStairService.selectById(parentId);
        result.put("medicalName", parent.getMedicalName());
        model.addAttribute("item", result);
        LogObjectHolder.me().set(result);
        return PREFIX + "medicalInventoryDrugStorage_putInStorage.html";
    }

    /**
     * 跳转到修改药库管理
     */
    @RequestMapping("/medicalInventoryDrugStorage_putOffStorage/{medicalInventoryDrugStorageId}")
    @DictHandler(dictModels = { DictConsts.MEDICAL_INBOUND_CHANNEL }, dictKeys = {
            DictConsts.MEDICAL_INBOUND_CHANNEL_KEY })
    public String medicalInventoryDrugStoragePutOffStorage(@PathVariable Integer medicalInventoryDrugStorageId,
            Model model) {
        Wrapper<MedicalInventoryDrugStorage> wrapper = new EntityWrapper<>();
        wrapper.eq("id", medicalInventoryDrugStorageId);
        Map<String, Object> result = medicalInventoryDrugStorageService.selectMap(wrapper);

        Integer parentId = (Integer) result.get("parentId");
        MedicalInventoryStair parent = medicalInventoryStairService.selectById(parentId);
        result.put("medicalName", parent.getMedicalName());
        model.addAttribute("item", result);
        LogObjectHolder.me().set(result);
        return PREFIX + "medicalInventoryDrugStorage_putOffStorage.html";
    }

    /**
     * 获取药库管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @DictHandler(dictWrappers = {
            @DictEntity(parentId = DictConsts.MEDICAL_INBOUND_CHANNEL, fieldName = DictConsts.MEDICAL_INBOUND_CHANNEL_FIELD_NAME),
            @DictEntity(parentId = DictConsts.MEDICAL_UNIT, fieldName = DictConsts.MEDICAL_UNIT_FIELD_NAME),
            @DictEntity(parentId = DictConsts.MEDICAL_SPECIFICATION, fieldName = DictConsts.MEDICAL_SPECIFICATION_FIELD_NAME) })
    public Map<String, Object> list(String medicalName, String producer, Integer inboundChannel, java.sql.Date expiredDate,
            Integer offset, Integer limit) {
        long total = medicalInventoryDrugStorageService.selectCountMaps(MapUtil.createMap("medicalName", medicalName,
                "producer", producer, "inboundChannel", inboundChannel, "expiredDate", expiredDate));
        if (total == 0) {
            return MapUtil.createSuccessMap("rows", Collections.emptyList(), "total", total);
        }
        List<Map<String, Object>> list = medicalInventoryDrugStorageService
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
        String fileName = "药库药品.xls";
        String[] headers = { "药品名称", "生产企业", "生产批号", "生产日期", "有效期至", "价格", "在库数量", "单位", "规格", "进货渠道", "备注"};
        String[] fields = { "medicalName", "producer", "produceBatchNum", "produceDate", "expireDate", "price", "inventoryNum", "unit", "specification", "inboundChannel", "remark"};
        DownLoadUtil.exportExcelAndDownload("药库药品", headers, fields, rows, response, request, tmpPath, fileName);
    }

    /**
     * 新增药库管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MedicalInventoryDrugStorage medicalInventoryDrugStorage) {
        if (medicalInventoryDrugStorage.getParentId() == null) {
            return new Tip(500, "请选择药品！");
        }

        Tip tip = checkExist(medicalInventoryDrugStorage.getParentId(), medicalInventoryDrugStorage.getProduceBatchNum());
        if (tip != null) {
            return tip;
        }

        Date createDate = new Date();
        medicalInventoryDrugStorage.setCreateDate(createDate);
        medicalInventoryDrugStorage.setUpdateDate(createDate);
        medicalInventoryDrugStorageService.insert(medicalInventoryDrugStorage);

        if (medicalInventoryDrugStorage.getInventoryNum() != null
                && medicalInventoryDrugStorage.getInventoryNum() > 0) {
            storageLogService.logStorage(medicalInventoryDrugStorage.getId(),
                    medicalInventoryDrugStorage.getInventoryNum().intValue(), EnumMedicalInventoryCategory.DRUG_STORAGE,
                    InventoryConsts.INVENTORY_IN_STORAGE);
        }
        return SUCCESS_TIP;
    }

    /**
     * 修改药库管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(MedicalInventoryDrugStorage medicalInventoryDrugStorage) {
        Wrapper<MedicalInventoryDrugStorage> wrapper = new EntityWrapper<>();
        wrapper.eq("parent_id", medicalInventoryDrugStorage.getParentId());
        wrapper.eq("produce_batch_num", medicalInventoryDrugStorage.getProduceBatchNum());
        MedicalInventoryDrugStorage sameBatchNumMedical = medicalInventoryDrugStorageService.selectOne(wrapper);
        if (sameBatchNumMedical != null && sameBatchNumMedical.getId().intValue() != medicalInventoryDrugStorage.getId().intValue()) {
            return new Tip(500, "已存在该批号的药品！");
        }
        
        medicalInventoryDrugStorage.setUpdateDate(new Date());
        medicalInventoryDrugStorageService.updateById(medicalInventoryDrugStorage);
        return SUCCESS_TIP;
    }

    public Tip checkExist(Integer parentId, String batchNum) {
        Wrapper<MedicalInventoryDrugStorage> wrapper = new EntityWrapper<>();
        wrapper.eq("parent_id", parentId);
        wrapper.eq("produce_batch_num", batchNum);
        int count = medicalInventoryDrugStorageService.selectCount(wrapper);
        if (count != 0) {
            return new Tip(500, "已存在该批号的药品！");
        }
        return null;
    }

    /**
     * 删除药库管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer medicalInventoryDrugStorageId) {
        medicalInventoryDrugStorageService.deleteById(medicalInventoryDrugStorageId);
        return SUCCESS_TIP;
    }

    /**
     * 删除药库管理
     */
    @RequestMapping(value = "/putInStorage")
    @ResponseBody
    @Transactional
    public Object putInStorage(Integer id, Integer count) {
        int modifyNum = medicalInventoryDrugStorageService.updateInventoryCount(id, count, "药库");
        if (modifyNum == 0) {
            return new Tip(500, "该药品已被删除！");
        }
        return SUCCESS_TIP;
    }

    /**
     * 删除药库管理
     */
    @RequestMapping(value = "/putOffStorage")
    @ResponseBody
    @Transactional
    public Object putOffStorage(Integer id, Integer count) {
        MedicalInventoryDrugStorage entity = medicalInventoryDrugStorageService.selectById(id);
        if (entity.getInventoryNum() - count < 0) {
            return new Tip(500, "库存不足！");
        }
        int modifyNum = medicalInventoryDrugStorageService.updateInventoryCount(id, count * -1, "药房");
        if (modifyNum == 0) {
            return new Tip(500, "该药品已被删除！");
        }
        medicalInventoryPharmacyService.putInStorage(entity, count, "药房");
        return SUCCESS_TIP;
    }

    /**
     * 药库管理详情
     */
    @RequestMapping(value = "/detail/{medicalInventoryDrugStorageId}")
    @ResponseBody
    public Object detail(@PathVariable("medicalInventoryDrugStorageId") Integer medicalInventoryDrugStorageId) {
        return medicalInventoryDrugStorageService.selectById(medicalInventoryDrugStorageId);
    }
}
