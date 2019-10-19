package com.bdtd.card.registration.modular.comprehensive.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.bdtd.card.registration.common.model.EnumCrippleNature;
import com.bdtd.card.registration.common.model.EnumDisabilityRate;
import com.bdtd.card.registration.common.model.EnumDisabilityRateResult;
import com.bdtd.card.registration.common.model.EnumOriginMask;
import com.bdtd.card.registration.common.utils.EnumAdapterFactory;
import com.bdtd.card.registration.common.utils.model.EnumAdapterEntity;
import com.bdtd.card.registration.modular.comprehensive.service.IPatientDisabilityRatingService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.annotion.DictHandler;
import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.model.EnumOperator;
import com.stylefeng.guns.core.util.CommonUtils;
import com.stylefeng.guns.core.util.DictCacheFactory;
import com.stylefeng.guns.core.util.DownLoadUtil;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.core.util.QueryHelper;
import com.stylefeng.guns.modular.system.model.PatientDisabilityRating;
import com.stylefeng.guns.scmmain.service.IDtDepService;

/**
 * 评残信息控制器
 *
 * @author 
 * @Date 2018-07-10 10:45:26
 */
@Controller
@RequestMapping("/patientDisabilityRating")
@ConditionalOnBean(value=DictCacheFactory.class)
public class PatientDisabilityRatingController extends BaseController {

    private String PREFIX = "/comprehensive/patientDisabilityRating/";

    @Autowired
    private IPatientDisabilityRatingService patientDisabilityRatingService;
    @Autowired
    private DictCacheFactory dictCacheFactory;
    @Autowired
    private IDtDepService depService;
    @Autowired
    private QueryHelper queryHelper;

    @Value("${guns.file-upload-path}")
    private String tmpPath;

    /**
     * 跳转到评残信息首页
     */
    @RequestMapping("")
    @DictHandler(dictModels= {DictConsts.USER_DUTY}, dictKeys= {DictConsts.USER_DUTY_KEY})
    public String index(Model model) {
        model.addAttribute("applyItemList", EnumOriginMask.select());
        model.addAttribute("disabilityRateItemList", EnumDisabilityRateResult.select());
        model.addAttribute("dtDepItemList", this.depService.select());
        return PREFIX + "patientDisabilityRating.html";
    }

    /**
     * 跳转到添加评残信息
     */
    @RequestMapping("/patientDisabilityRating_add")
    @DictHandler(dictModels= {DictConsts.USER_DUTY}, dictKeys= {DictConsts.USER_DUTY_KEY})
    public String patientDisabilityRatingAdd(Model model) {
        model.addAttribute("applyItemList", EnumOriginMask.select());
        model.addAttribute("disabilityRateItemList", EnumDisabilityRate.select());
        model.addAttribute("disabilityRateResultItemList", EnumDisabilityRateResult.select());
        model.addAttribute("crippleNatureItemList", EnumCrippleNature.select());
        return PREFIX + "patientDisabilityRating_add.html";
    }

    /**
     * 跳转到修改评残信息
     */
    @RequestMapping("/patientDisabilityRating_update/{patientDisabilityRatingId}")
    @DictHandler(dictModels= {DictConsts.USER_DUTY}, dictKeys= {DictConsts.USER_DUTY_KEY})
    public String patientDisabilityRatingUpdate(@PathVariable Integer patientDisabilityRatingId, Model model) {
        PatientDisabilityRating patientDisabilityRating = patientDisabilityRatingService.selectById(patientDisabilityRatingId);
        model.addAttribute("item",patientDisabilityRating);
        LogObjectHolder.me().set(patientDisabilityRating);
        model.addAttribute("applyItemList", EnumOriginMask.select());
        model.addAttribute("disabilityRateItemList", EnumDisabilityRate.select());
        model.addAttribute("disabilityRateResultItemList", EnumDisabilityRateResult.select());
        model.addAttribute("crippleNatureItemList", EnumCrippleNature.select());
        return PREFIX + "patientDisabilityRating_edit.html";
    }

    /**
     * 获取评残信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String userName, Long orgId, String userDuty, String enrolDate, String applyDate, Integer applyRating, String resultDate, Integer disabilityResult, Integer offset, Integer limit) {
    	Wrapper<PatientDisabilityRating> wrapper = new EntityWrapper<>();
    	CommonUtils.handleRequestParams(wrapper, "user_name", userName, EnumOperator.LIKE, "user_duty", userDuty, null, "apply_rating", applyRating, null, "disability_result", disabilityResult, null);
    	CommonUtils.handleDateRequestParams(wrapper, "enrol_date", enrolDate, null, EnumOperator.GE);
    	CommonUtils.handleDateRequestParams(wrapper, "apply_date", applyDate, null, EnumOperator.EQ);
    	CommonUtils.handleDateRequestParams(wrapper, "result_date", resultDate, null, EnumOperator.EQ);
    	queryHelper.wrapperQueryByRole(wrapper, orgId);
    	Page<Map<String, Object>> page = patientDisabilityRatingService.selectMapsPage(new Page<>(offset / limit + 1, limit, "apply_date", Consts.DEFAULT_SORT_ORDER_IS_ASC), wrapper);
		return MapUtil.createSuccessMap("rows", page.getRecords(), "total", page.getTotal());
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(String userName, Long orgId, String userDuty, String enrolDate, String applyDate, Integer applyRating, String resultDate, Integer disabilityResult, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> resultMap = (Map<String, Object>) this.list(userName, orgId, userDuty, enrolDate, applyDate, applyRating, resultDate, disabilityResult, 0, Integer.MAX_VALUE);

        List<Map<String, Object>> rows = (List<Map<String, Object>>) resultMap.get("rows");

        EnumAdapterFactory.adapterRows(rows, Arrays.asList(
                new EnumAdapterEntity("apply", "EnumOriginMask"),
                new EnumAdapterEntity("applyRating", "EnumDisabilityRate"),
                new EnumAdapterEntity("crippleNature", "EnumCrippleNature"),
                new EnumAdapterEntity("disabilityResult", "EnumDisabilityRateResult")
            ));

        String fileName = "评残信息.xls";
        String[] headers = { "编号", "姓名", "性别", "身份", "单位", "入伍时间", "疾病名称", "是否申请", "申请时间", "申请等级", "评残结果", "评残性质", "结果时间" };
        String[] fields = { "userNo", "userName", "userSex", "userDuty", "orgName", "enrolDate",
                "disabilityName", "apply", "applyDate", "applyRating", "disabilityResult", "crippleNature", "resultDate" };
        DownLoadUtil.exportExcelAndDownload("评残信息", headers, fields, rows, response, request, tmpPath, fileName);
    }

    /**
     * 新增评残信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(PatientDisabilityRating patientDisabilityRating) {
        Date now = new Date();
        patientDisabilityRating.setCreateDate(now);
        patientDisabilityRating.setUpdateDate(now);
        patientDisabilityRatingService.insert(patientDisabilityRating);
        return SUCCESS_TIP;
    }

    /**
     * 删除评残信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer patientDisabilityRatingId) {
        patientDisabilityRatingService.deleteById(patientDisabilityRatingId);
        return SUCCESS_TIP;
    }

    /**
     * 修改评残信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(PatientDisabilityRating patientDisabilityRating) {
        Date now = new Date();
        patientDisabilityRating.setUpdateDate(now);
        patientDisabilityRatingService.updateById(patientDisabilityRating);
        return SUCCESS_TIP;
    }

    /**
     * 评残信息详情
     */
    @RequestMapping(value = "/detail/{patientDisabilityRatingId}")
    @ResponseBody
    public Object detail(@PathVariable("patientDisabilityRatingId") Integer patientDisabilityRatingId) {
        return patientDisabilityRatingService.selectById(patientDisabilityRatingId);
    }
}
