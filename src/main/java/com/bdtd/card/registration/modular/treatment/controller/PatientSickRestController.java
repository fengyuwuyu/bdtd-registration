package com.bdtd.card.registration.modular.treatment.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.bdtd.card.registration.common.model.EnumHandleTypeMask;
import com.bdtd.card.registration.common.utils.EnumAdapterFactory;
import com.bdtd.card.registration.common.utils.model.EnumAdapterEntity;
import com.bdtd.card.registration.modular.treatment.service.IPatientInfoService;
import com.bdtd.card.registration.modular.treatment.service.IPatientSickRestService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.common.annotion.DictEntity;
import com.stylefeng.guns.core.common.annotion.DictHandler;
import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.CommonUtils;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.DictCacheFactory;
import com.stylefeng.guns.core.util.DownLoadUtil;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.core.util.QueryHelper;
import com.stylefeng.guns.core.wrapper.DictWrapperEntity;
import com.stylefeng.guns.modular.system.model.PatientSickRest;

/**
 * 病休管理控制器
 *
 * @author
 * @Date 2018-06-26 09:12:44
 */
@Controller
@RequestMapping("/patientSickRest")
@ConditionalOnBean(value=DictCacheFactory.class)
public class PatientSickRestController extends BaseController {

    private String PREFIX = "/treatment/patientSickRest/";
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IPatientSickRestService patientSickRestService;

    @Autowired
    private IPatientInfoService patientInfoService;
    
    @Autowired
    private DictCacheFactory dictCacheFactory;
    @Autowired
    private QueryHelper queryHelper;

    @Value("${guns.file-upload-path}")
    private String tmpPath;

    /**
     * 跳转到病休管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "patientSickRest.html";
    }

    /**
     * 跳转到添加病休管理
     */
    @RequestMapping("/patientSickRest_add")
    public String patientSickRestAdd() {
        return PREFIX + "patientSickRest_add.html";
    }

    /**
     * 跳转到修改病休管理
     */
    @RequestMapping("/patientSickRest_update/{patientSickRestId}")
    public String patientSickRestUpdate(@PathVariable Integer patientSickRestId, Model model) {
        PatientSickRest patientSickRest = patientSickRestService.selectById(patientSickRestId);
        model.addAttribute("item", patientSickRest);
        LogObjectHolder.me().set(patientSickRest);
        return PREFIX + "patientSickRest_edit.html";
    }

    /**
     * 获取病休管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @DictHandler(dictWrappers= {@DictEntity(parentId=DictConsts.REGISTRATION_MAIN_DIAGNOSIS, fieldName=DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME)})
    public Object list(String userName, Long orgId, String userDuty, String enrolDate, Integer sickRestType,
            Integer sickRestDay, Integer mainDiagnosis, String beginDate, String endDate, Integer status, Integer offset, Integer limit) {
        Map<String, Object> params = MapUtil.createMap("userName", userName, "userDuty", userDuty, "mainDiagnosis", mainDiagnosis,
                "enrolDate", enrolDate, "sickRestType", sickRestType, "sickRestDay", sickRestDay, "status", status, "offset", offset, "limit", limit);
        CommonUtils.handleQueryDateSection("beginDate", "endDate", beginDate, endDate, params, Consts.DATE_PATTERN);
        queryHelper.wrapperQueryByRole(params, orgId);
        long total = this.patientSickRestService.countByMap(params);
        if (total == 0) {
            return MapUtil.createSuccessMap("rows", Collections.emptyList(), "total", total);
        }
        List<Map<String, Object>> list = this.patientSickRestService.findByMap(params);
//        list.forEach((item) -> {
//            java.sql.Date beginDay = (java.sql.Date) item.get("beginDate");
//            Integer sickRestDay1 = (Integer) item.get("sickRestDay");
//            beginDay = new java.sql.Date(DateUtil.getAfterDayDate(beginDay, sickRestDay1).getTime());
//            java.sql.Date now = new java.sql.Date(DateUtil.getOneDayBegin(new Date()).getTime());
//            if (beginDay.getTime() >= now.getTime()) {
//                item.put("status", "已康复");
//            } else {
//                item.put("status", "病休中");
//            }
//        });
        return MapUtil.createSuccessMap("rows", list, "total", total);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(String userName, Long orgId, String userDuty, String enrolDate, Integer sickRestType, Integer mainDiagnosis,
            Integer sickRestDay, String beginDate, String endDate, Integer status, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = (Map<String, Object>) this.list(userName, orgId, userDuty, enrolDate, sickRestType, sickRestDay, mainDiagnosis, beginDate, endDate, status, null, null);

        List<Map<String, Object>> rows = (List<Map<String, Object>>) resultMap.get("rows");
        List<DictWrapperEntity> dictwrapperEntities = Arrays.asList(new DictWrapperEntity(
                DictConsts.REGISTRATION_MAIN_DIAGNOSIS, DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME));
        dictCacheFactory.wrapper(rows, dictwrapperEntities);

        EnumAdapterFactory.adapterRows(rows, Arrays.asList(new EnumAdapterEntity("sickRestType", "EnumSickRestType")));
        
        String fileName = "病休信息.xls";
        String[] headers = { "编号", "姓名", "性别", "身份", "单位", "入伍时间", "诊断", "病休方式", "开始时间", "天数", "状态", "注意事项", "医生" };
        String[] fields = { "userNo", "userName", "userSex", "userDuty", "orgName", "enrolDate",
                "mainDiagnosis", "sickRestType", "beginDate", "sickRestDay", "status", "noticeInfo", "physicianName" };
        DownLoadUtil.exportExcelAndDownload("病休信息", headers, fields, rows, response, request, tmpPath, fileName);
    }

    @RequestMapping(value = "/findByPatientInfoId")
    @ResponseBody
    public Object findByPatientInfoId(Integer patientInfoId) {
        if (patientInfoId == null) {
            return MapUtil.createSuccessMap();
        }

        Wrapper<PatientSickRest> wrapper = new EntityWrapper<>();
        wrapper.eq("patient_info_id", patientInfoId);
        Map<String, Object> result = patientSickRestService.selectMap(wrapper);
        return MapUtil.createSuccessMap("data", result);
    }

    /**
     * 新增病休管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @Transactional
    public Object add(PatientSickRest patientSickRest) {
        if (patientSickRest.getPatientInfoId() == null) {
            log.error("patientInfoId is null, patientSickRest = " + patientSickRest);
            return new Tip(500, "服务器内部错误！");
        }
        Date createDate = new Date();
        patientSickRest.setCreateDate(createDate);
        patientSickRest.setUpdateDate(createDate);
        patientSickRestService.insert(patientSickRest);
        patientInfoService.updateHandleType(patientSickRest.getPatientInfoId(), EnumHandleTypeMask.SICK_REST);
        return MapUtil.createSuccessMap("id", patientSickRest.getId());
    }

    /**
     * 删除病休管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer patientSickRestId) {
        patientSickRestService.deleteById(patientSickRestId);
        return SUCCESS_TIP;
    }

    /**
     * 修改病休管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PatientSickRest patientSickRest) {
        patientSickRestService.updateById(patientSickRest);
        return SUCCESS_TIP;
    }

    /**
     * 病休管理详情
     */
    @RequestMapping(value = "/detail/{patientSickRestId}")
    @ResponseBody
    public Object detail(@PathVariable("patientSickRestId") Integer patientSickRestId) {
        return patientSickRestService.selectById(patientSickRestId);
    }

    @RequestMapping(value = "/cancelSickRest")
    @ResponseBody
    @Transactional
    public Object cancelSickRest(@RequestParam(required = true) Integer patientInfoId) {
        patientSickRestService.deleteByMap(MapUtil.createMap("patient_info_id", patientInfoId));
        patientInfoService.cancelHandleType(patientInfoId, EnumHandleTypeMask.SICK_REST);
        return SUCCESS_TIP;
    }
}
