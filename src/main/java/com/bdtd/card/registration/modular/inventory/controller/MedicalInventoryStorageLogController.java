package com.bdtd.card.registration.modular.inventory.controller;

import java.util.Arrays;
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
import com.bdtd.card.registration.common.model.EnumMedicalInventoryStorageType;
import com.bdtd.card.registration.common.utils.EnumAdapterFactory;
import com.bdtd.card.registration.common.utils.model.EnumAdapterEntity;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryStorageLogService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.annotion.DictEntity;
import com.stylefeng.guns.core.common.annotion.DictHandler;
import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.model.EnumOperator;
import com.stylefeng.guns.core.util.CommonUtils;
import com.stylefeng.guns.core.util.DictCacheFactory;
import com.stylefeng.guns.core.util.DownLoadUtil;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.core.wrapper.DictWrapperEntity;
import com.stylefeng.guns.modular.system.model.MedicalInventoryStorageLog;

/**
 * 出入库记录控制器
 *
 * @author 
 * @String 2018-06-28 11:43:01
 */
@Controller
@RequestMapping("/medicalInventoryStorageLog")
public class MedicalInventoryStorageLogController extends BaseController {

    private String PREFIX = "/inventory/medicalInventoryStorageLog/";

    @Autowired
    private IMedicalInventoryStorageLogService medicalInventoryStorageLogService;
    
    @Autowired
    private DictCacheFactory dictCacheFactory;
    @Value("${guns.file-upload-path}")
    private String tmpPath;

    /**
     * 跳转到出入库记录首页
     */
    @RequestMapping("")
    public String index(Model model) {
        List<Map<String, Object>> inventoryLogTypeList = EnumMedicalInventoryStorageType.select();
        model.addAttribute("inventoryLogTypeList", inventoryLogTypeList);
        return PREFIX + "medicalInventoryStorageLog.html";
    }

    /**
     * 跳转到添加出入库记录
     */
    @RequestMapping("/medicalInventoryStorageLog_add")
    public String medicalInventoryStorageLogAdd() {
        return PREFIX + "medicalInventoryStorageLog_add.html";
    }

    /**
     * 跳转到修改出入库记录
     */
    @RequestMapping("/medicalInventoryStorageLog_update/{medicalInventoryStorageLogId}")
    public String medicalInventoryStorageLogUpdate(@PathVariable Integer medicalInventoryStorageLogId, Model model) {
        MedicalInventoryStorageLog medicalInventoryStorageLog = medicalInventoryStorageLogService.selectById(medicalInventoryStorageLogId);
        model.addAttribute("item",medicalInventoryStorageLog);
        LogObjectHolder.me().set(medicalInventoryStorageLog);
        return PREFIX + "medicalInventoryStorageLog_edit.html";
    }

    /**
     * 获取出入库记录列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @DictHandler(dictWrappers = {
            @DictEntity(parentId = DictConsts.MEDICAL_UNIT, fieldName = DictConsts.MEDICAL_UNIT_FIELD_NAME),
            @DictEntity(parentId = DictConsts.MEDICAL_SPECIFICATION, fieldName = DictConsts.MEDICAL_SPECIFICATION_FIELD_NAME),
            @DictEntity(parentId = DictConsts.MEDICAL_INBOUND_CHANNEL, fieldName = DictConsts.MEDICAL_INBOUND_CHANNEL_FIELD_NAME)})
    public Map<String, Object> list(String medicalName, String orgName, Integer parentId, Integer category, Integer type, String operatorName, String beginDate, String endDate, Integer offset, Integer limit) {
    	Wrapper<MedicalInventoryStorageLog> wrapper = new EntityWrapper<>();
    	if (parentId != null) {
    	    wrapper.allEq(MapUtil.createMap("medical_id", parentId, "category", category));
    	}
    	if (type != null) {
    	    wrapper.and().eq("type", type);
    	}
    	
    	CommonUtils.handleRequestParams(wrapper, "medical_name", medicalName, EnumOperator.LIKE, "org_name", orgName, EnumOperator.LIKE, "log_date", Arrays.asList(beginDate, endDate), EnumOperator.BETWEEN);
    	if (!StringUtil.isNullEmpty(operatorName)) {
    	    wrapper.and().like("operator_name", operatorName);
    	}
    	
    	Page<Map<String, Object>> page = medicalInventoryStorageLogService.selectMapsPage(new Page<>(offset / limit + 1, limit, "log_date", Consts.DEFAULT_SORT_ORDER_IS_ASC), wrapper);
		return MapUtil.createSuccessMap("rows", page.getRecords(), "total", page.getTotal());
    }
    
    /**
     * 获取出入库记录列表
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(String medicalName, String orgName, Integer parentId, Integer category, Integer type, String operatorName, String beginDate, String endDate, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = this.list(medicalName, orgName, parentId, category, type, operatorName, beginDate, endDate, 0, Integer.MAX_VALUE);
        List<Map<String, Object>> rows = (List<Map<String, Object>>) map.get("rows");
        List<DictWrapperEntity> dictwrapperEntities = Arrays.asList(
                new DictWrapperEntity(DictConsts.MEDICAL_INBOUND_CHANNEL, DictConsts.MEDICAL_INBOUND_CHANNEL_FIELD_NAME),
                new DictWrapperEntity(DictConsts.MEDICAL_UNIT, DictConsts.MEDICAL_UNIT_FIELD_NAME),
                new DictWrapperEntity(DictConsts.MEDICAL_SPECIFICATION, DictConsts.MEDICAL_SPECIFICATION_FIELD_NAME)
                );
        this.dictCacheFactory.wrapper(rows, dictwrapperEntities);
        
        List<EnumAdapterEntity> enumAdapters = Arrays.asList(new EnumAdapterEntity("type", "EnumMedicalInventoryStorageType"));
        EnumAdapterFactory.adapterRows(rows, enumAdapters );
        String fileName = "出入库记录.xls";
        String[] headers = { "药品名称", "生产批号", "状态", "规格", "单位", "去向", "价格", "数量", "操作员", "时间"};
        String[] fields = { "medicalName", "produceBatchNum", "type", "specification", "unit", "orgName", "price", "amount", "operatorName", "logDate"};
        DownLoadUtil.exportExcelAndDownload("出入库记录", headers, fields, rows, response, request, tmpPath, fileName);
    }

    /**
     * 新增出入库记录
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(MedicalInventoryStorageLog medicalInventoryStorageLog) {
        medicalInventoryStorageLogService.insert(medicalInventoryStorageLog);
        return SUCCESS_TIP;
    }

    /**
     * 删除出入库记录
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer medicalInventoryStorageLogId) {
        medicalInventoryStorageLogService.deleteById(medicalInventoryStorageLogId);
        return SUCCESS_TIP;
    }

    /**
     * 修改出入库记录
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(MedicalInventoryStorageLog medicalInventoryStorageLog) {
        medicalInventoryStorageLogService.updateById(medicalInventoryStorageLog);
        return SUCCESS_TIP;
    }

    /**
     * 出入库记录详情
     */
    @RequestMapping(value = "/detail/{medicalInventoryStorageLogId}")
    @ResponseBody
    public Object detail(@PathVariable("medicalInventoryStorageLogId") Integer medicalInventoryStorageLogId) {
        return medicalInventoryStorageLogService.selectById(medicalInventoryStorageLogId);
    }
}
