package com.bdtd.card.registration.modular.inhospital.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.bdtd.card.registration.common.model.EnumInHospitalStatus;
import com.bdtd.card.registration.common.model.EnumOriginMask;
import com.bdtd.card.registration.common.utils.EnumAdapterFactory;
import com.bdtd.card.registration.common.utils.model.EnumAdapterEntity;
import com.bdtd.card.registration.modular.inhospital.service.IPatientInHospitalService;
import com.bdtd.card.registration.modular.treatment.service.IPatientInfoService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.common.annotion.DictEntity;
import com.stylefeng.guns.core.common.annotion.DictHandler;
import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.CommonUtils;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.DictCacheFactory;
import com.stylefeng.guns.core.util.DownLoadUtil;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.core.wrapper.DictWrapperEntity;
import com.stylefeng.guns.modular.system.model.PatientInHospital;
import com.stylefeng.guns.modular.system.model.PatientInfo;
import com.stylefeng.guns.scmmain.model.DtUser;
import com.stylefeng.guns.scmmain.service.IDtDepService;
import com.stylefeng.guns.scmmain.service.IDtUserService;

/**
 * 住院控制器
 *
 * @author
 * @Date 2018-06-26 09:08:59
 */
@Controller
@RequestMapping("/patientInHospital")
@ConditionalOnBean(value=DictCacheFactory.class)
public class PatientInHospitalController extends BaseController {

    private String PREFIX = "/inhospital/patientInHospital/";

    @Autowired
    private IPatientInHospitalService patientInHospitalService;
    @Autowired
    private IPatientInfoService patientInfoService;
    @Autowired
    private IDtUserService dtUserService;
    @Autowired
    private DictCacheFactory dictCacheFactory;
    @Autowired
    private IDtDepService depService;

    /**
     * 跳转到住院首页
     */
    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("feverItemList", EnumOriginMask.select());
        model.addAttribute("dtDepItemList", this.depService.select());
        return PREFIX + "patientInHospital.html";
    }
    
    /**
     * 跳转到住院首页
     */
    @RequestMapping("/query")
    public String query(Model model) {
        model.addAttribute("feverItemList", EnumOriginMask.select());
        model.addAttribute("statusItemList", EnumInHospitalStatus.select2());
        model.addAttribute("dtDepItemList", this.depService.select());
        return PREFIX + "patientInHospital_query.html";
    }
    
    @RequestMapping("/queryDetail/{patientInfoId}")
    @DictHandler(dictModels= {DictConsts.REGISTRATION_MAIN_DIAGNOSIS, DictConsts.COMMON_ADVICE}, dictKeys= {DictConsts.REGISTRATION_MAIN_DIAGNOSIS_KEY, DictConsts.COMMON_ADVICE_KEY})
    public String queryDetail(@PathVariable Integer patientInfoId, Model model) {
        Map<String, Object> result = this.patientInHospitalService.findById(patientInfoId);
        model.addAttribute("item", result);
        model.addAttribute("feverItemList", EnumOriginMask.select());
        return PREFIX + "patientInHospitalQuery_detail.html";
    }

    @RequestMapping("/openUserInfo/{userNo}")
    public String openUserInfo(@PathVariable String userNo, Model model) {
        DtUser dtUser = this.dtUserService.findByUserNo(userNo);
        Integer age = dtUser.getUserBirthday() == null ? 0
                : Integer.valueOf(DateUtil.getYear()) - Integer.valueOf(DateUtil.getYear(dtUser.getUserBirthday()));
        model.addAttribute("item", dtUser);
        model.addAttribute("age", age);
        return PREFIX + "patientInHospital_userInfo.html";
    }

    @RequestMapping("/openDiagnosisDetail/{patientInfoId}")
    public String openDiagnosisDetail(@PathVariable Integer patientInfoId, Model model) {
        PatientInfo info = this.patientInfoService.selectById(patientInfoId);
        model.addAttribute("item", info);

        Map<String, Object> dict = dictCacheFactory.getDictMapByParentNameAndNum(DictConsts.REGISTRATION_MAIN_DIAGNOSIS,
                info.getMainDiagnosis());
        if (dict != null) {
            model.addAttribute("mainDiagnosis", dict.get("name"));
        }
        model.addAttribute("feverItemList", EnumOriginMask.select());
        return PREFIX + "patientInHospital_diagnosisDetail.html";
    }

    /**
     * 跳转到添加住院
     */
    @RequestMapping("/patientInHospital_add")
    public String patientInHospitalAdd() {
        return PREFIX + "patientInHospital_add.html";
    }

    @RequestMapping("/openInHospital")
    public String openInHospital() {
        return PREFIX + "patientInHospital_inHospital.html";
    }

    @RequestMapping("/openInHospitalDetail/{id}")
    @DictHandler(dictModels= {DictConsts.DOCTOR}, dictKeys= {DictConsts.DOCTOR_KEY})
    public String openInHospitalDetail(@PathVariable Integer id, Model model) {
        Map<String, Object> result = this.patientInHospitalService.findById(id);
        if (StringUtil.isNullEmpty((String)result.get("physicianName"))) {
            result.put("physicianName", ShiroKit.getUser().getDtUser().getUserLname());
        }
        model.addAttribute("item", result);
        return PREFIX + "patientInHospital_inHospitalDetail.html";
    }

    @RequestMapping("/openLeaveHospital/{id}")
    @DictHandler(dictModels= {DictConsts.DOCTOR}, dictKeys= {DictConsts.DOCTOR_KEY})
    public String openLeaveHospital(@PathVariable Integer id, Model model) {
        model.addAttribute("id", id);
        PatientInHospital item = this.patientInHospitalService.selectById(id);
        model.addAttribute("item", item);
        return PREFIX + "patientInHospital_leaveHospital.html";
    }

    @RequestMapping("/openCommonAdvice/{id}")
    @DictHandler(dictModels= {DictConsts.COMMON_ADVICE, DictConsts.DOCTOR}, dictKeys= {DictConsts.COMMON_ADVICE_KEY, DictConsts.DOCTOR_KEY})
    public String openCommonAdvice(@PathVariable Integer id, Model model) {
        PatientInHospital info = this.patientInHospitalService.selectById(id);
        model.addAttribute("item", info);
        return PREFIX + "patientInHospital_commonAdvice.html";
    }

    @RequestMapping("/openCommonAdviceDetail/{id}")
    @DictHandler(dictModels= {DictConsts.COMMON_ADVICE, DictConsts.DOCTOR}, dictKeys= {DictConsts.COMMON_ADVICE_KEY, DictConsts.DOCTOR_KEY})
    public String openCommonAdviceDetail(@PathVariable Integer id, Model model) {
        PatientInHospital info = this.patientInHospitalService.selectById(id);
        model.addAttribute("item", info);
        return PREFIX + "patientInHospital_commonAdviceDetail.html";
    }

    @RequestMapping("/openLongTermAdvice/{id}")
    public String openLongTermAdvice(@PathVariable Integer id, Model model) {
        model.addAttribute("inHospitalId", id);
        return PREFIX + "patientInHospital_longTermAdvice.html";
    }
    
    @RequestMapping("/openLongTermAdviceDetail/{id}")
    public String openLongTermAdviceDetail(@PathVariable Integer id, Model model) {
        model.addAttribute("inHospitalId", id);
        return PREFIX + "patientInHospital_longTermAdviceDetail.html";
    }

    @RequestMapping("/openTemporaryAdvice/{id}")
    public String openTemporaryAdvice(@PathVariable Integer id, Model model) {
        model.addAttribute("inHospitalId", id);
        return PREFIX + "patientInHospital_temporaryAdvice.html";
    }
    
    @RequestMapping("/openTemporaryAdviceDetail/{id}")
    @DictHandler(dictModels= {DictConsts.DOCTOR}, dictKeys= {DictConsts.DOCTOR_KEY})
    public String openTemporaryAdviceDetail(@PathVariable Integer id, Model model) {
        model.addAttribute("inHospitalId", id);
        return PREFIX + "patientInHospital_temporaryAdviceDetail.html";
    }

    /**
     * 跳转到修改住院
     */
    @RequestMapping("/patientInHospital_update/{patientInHospitalId}")
    public String patientInHospitalUpdate(@PathVariable Integer patientInHospitalId, Model model) {
        PatientInHospital patientInHospital = patientInHospitalService.selectById(patientInHospitalId);
        model.addAttribute("item", patientInHospital);
        LogObjectHolder.me().set(patientInHospital);
        return PREFIX + "patientInHospital_edit.html";
    }

    /**
     * 获取住院列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @DictHandler(dictWrappers = {
            @DictEntity(parentId = DictConsts.REGISTRATION_MAIN_DIAGNOSIS, fieldName = DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME) })
    public Object list(Integer status, String userName, Long orgId, Integer fever, String physicianName,
            String userDuty, String enrolDate, String inHospitalBeginDate, String inHospitalEndDate, String leaveHospitalBeginDate, String leaveHospitalEndDate,
            String inHospitalDate, Integer offset, Integer limit) {
        Map<String, Object> params = MapUtil.createMap("status", status, "userName", userName, "orgId", orgId, "fever",
                fever, "physicianName", physicianName, "inHospitalDate",
                StringUtil.isNullEmpty(inHospitalDate) ? null : DateUtil.parse(inHospitalDate, Consts.DATE_PATTERN),
                "userDuty", userDuty, "enrolDate", StringUtil.isNullEmpty(enrolDate) ? null : DateUtil.parse(enrolDate, Consts.DATE_PATTERN),
                "inHospitalBeginDate", inHospitalBeginDate, "inHospitalEndDate", inHospitalEndDate,
                "leaveHospitalBeginDate", leaveHospitalBeginDate, "leaveHospitalEndDate", leaveHospitalEndDate,
                "offset", offset, "limit", limit);
        long total = patientInHospitalService.countByMap(params);
        if (total == 0) {
            return MapUtil.createSuccessMap("rows", Collections.emptyList(), "total", total);
        }

        List<Map<String, Object>> list = patientInHospitalService.findByMap(params);
        return MapUtil.createSuccessMap("rows", list, "total", total);
    }

    /**
     * 新增住院
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PatientInHospital patientInHospital) {
        if (patientInHospital == null || patientInHospital.getPatientInfoId() == null) {
            return new Tip(500, "非法请求！");
        }
        PatientInfo info = patientInfoService.selectById(patientInHospital.getPatientInfoId());
        if (info == null) {
            return new Tip(500, "不存在该患者记录！");
        }
        
        Wrapper<PatientInHospital> selectWrapper = new EntityWrapper<>();
        selectWrapper.eq("user_no", info.getUserNo());
        selectWrapper.and().eq("status", EnumInHospitalStatus.IN_HOSPITAL.getType());
        long count = this.patientInHospitalService.selectCount(selectWrapper );
        if (count != 0L) {
            return new Tip(500, "该患者正在住院！");
        }
        Wrapper<PatientInHospital> wrapper = new EntityWrapper<>();
        CommonUtils.handleRequestParams(wrapper, "user_no", info.getUserNo(), null, "status", EnumInHospitalStatus.IN_HOSPITAL.getType(), null);
        PatientInHospital inHospital = patientInHospitalService.selectOne(wrapper);
        if (inHospital != null) {
            return new Tip(500, "该患者已住院！");
        }
        patientInHospital.setUserNo(info.getUserNo());
        Date now = new Date();
        patientInHospital.setCreateDate(now);
        patientInHospital.setUpdateDate(now);
        patientInHospital.setStatus(EnumInHospitalStatus.PRE.getType());
        patientInHospitalService.insert(patientInHospital);
        patientInfoService.updateHandleType(patientInHospital.getPatientInfoId(), EnumHandleTypeMask.IN_HOSPITAL);
        return SUCCESS_TIP;
    }

    /**
     * 删除住院
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @Transactional
    public Object delete(@RequestParam(required = true) String ids) {
        List<Integer> idList = Convert.stringToIntegerList(ids);
        if (idList.size() == 0) {
            return new Tip(500, "非法参数， ids" + ids);
        }
        List<PatientInHospital> list = patientInHospitalService.selectBatchIds(idList);
        List<Integer> patientInfoIdList = list.stream().map((item) -> {
            return item.getPatientInfoId();
        }).collect(Collectors.toList());
        patientInfoService.cancelHandleType(patientInfoIdList, EnumHandleTypeMask.IN_HOSPITAL);
        patientInHospitalService.deleteBatchIds(idList);
        return SUCCESS_TIP;
    }

    /**
     * 住院出院批量更新
     */
    @RequestMapping(value = "/updateStatusByIds")
    @ResponseBody
    @Transactional
    public Object updateStatusByIds(@RequestParam(required = true) String ids,
            @RequestParam(required = true) Integer status) {
        List<Integer> idList = Convert.stringToIntegerList(ids);
        Wrapper<PatientInHospital> wrapper = new EntityWrapper<>();
        wrapper.in("id", idList);
        PatientInHospital entity = new PatientInHospital();
        entity.setStatus(EnumInHospitalStatus.IN_HOSPITAL.getType());
        Date now = new Date();
        entity.setUpdateDate(now);
        entity.setInHospitalDate(new java.sql.Date(now.getTime()));
        entity.setOperatorName(ShiroKit.getUser().getDtUser().getUserLname());
        entity.setOperatorNo(ShiroKit.getUser().getDtUser().getUserNo());
        patientInHospitalService.update(entity, wrapper);
        return SUCCESS_TIP;

    }

    /**
     * 删除住院
     */
    @RequestMapping(value = "/cancelInHospital")
    @ResponseBody
    @Transactional
    public Object deleteByPatientInfoId(@RequestParam(required = true) Integer patientInfoId) {
        patientInHospitalService.deleteByMap(MapUtil.createMap("patient_info_id", patientInfoId));
        patientInfoService.cancelHandleType(patientInfoId, EnumHandleTypeMask.IN_HOSPITAL);
        return SUCCESS_TIP;
    }

    /**
     * 修改住院
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PatientInHospital patientInHospital) {
        patientInHospital.setUpdateDate(new Date());
        patientInHospitalService.updateById(patientInHospital);
        return SUCCESS_TIP;
    }

    @RequestMapping(value = "/transferToInHospital")
    @ResponseBody
    public Object transferToInHospital(@RequestParam(required = true) Integer patientInfoId) {
        PatientInfo info = patientInfoService.selectById(patientInfoId);
        if (info == null) {
            return new Tip(500, "该记录已被删除！");
        }
        //判断患者是否已住院
        Wrapper<PatientInHospital> wrapper = new EntityWrapper<>();
        CommonUtils.handleRequestParams(wrapper, "user_no", info.getUserNo(), null, "status", EnumInHospitalStatus.IN_HOSPITAL.getType(), null);
        PatientInHospital inHospital = patientInHospitalService.selectOne(wrapper);
        if (inHospital != null) {
            return new Tip(500, "该患者已住院！");
        }
        
        // 更新该患者住院和已住院状态
        patientInfoService.updateHandleType(patientInfoId,
                (EnumHandleTypeMask.IN_HOSPITAL.getType() | EnumHandleTypeMask.HAS_IN_HOSPITAL.getType()));

        // insert
        PatientInHospital entity = new PatientInHospital();
        entity.setUserNo(info.getUserNo());
        Date now = new Date();
        entity.setPatientInfoId(patientInfoId);
        entity.setCreateDate(now);
        entity.setUpdateDate(now);
        entity.setInHospitalDate(new java.sql.Date(now.getTime()));
        entity.setStatus(EnumInHospitalStatus.IN_HOSPITAL.getType());
        entity.setOperatorName(ShiroKit.getUser().getDtUser().getUserLname());
        entity.setOperatorNo(ShiroKit.getUser().getDtUser().getUserNo());
        patientInHospitalService.insert(entity);
        return SUCCESS_TIP;
    }

    /**
     * 住院详情
     */
    @RequestMapping(value = "/detail/{patientInHospitalId}")
    @ResponseBody
    public Object detail(@PathVariable("patientInHospitalId") Integer patientInHospitalId) {
        return patientInHospitalService.selectById(patientInHospitalId);
    }
}
