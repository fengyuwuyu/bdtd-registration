package com.bdtd.card.registration.modular.registration.controller;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bdtd.card.registration.cache.DepCache;
import com.bdtd.card.registration.modular.registration.service.IPatientRegistrationService;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.common.annotion.DictHandler;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.util.DownLoadUtil;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.scmmain.model.DtUser;
import com.stylefeng.guns.scmmain.service.IDtDepService;

@Controller
@RequestMapping("/registration")
public class PatientRegistrationController {

    @Autowired
    private IPatientRegistrationService patientRegistrationService;
    @Autowired
    private IDtDepService depService;

    @Autowired
    private GunsProperties gunsProperties;
    
    @RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response, String fileName) {
        File file = new File(gunsProperties.getFileUploadPath() + fileName);
        DownLoadUtil.download(file, response, request, false, fileName);
    }

    @RequestMapping("/userInfo")
    @DictHandler(dictKeys= {DictConsts.USER_DUTY_KEY}, dictModels= {DictConsts.USER_DUTY})
    public String userInfo(Model model) {
        model.addAttribute("dtDepItemList", this.depService.select());
        return "/comprehensive/dtUser/dtUser.html";
    }
    
    @RequestMapping("/patientInfo")
    @ResponseBody
    public Object patientInfo(String userCard) {
        return patientRegistrationService.patientInfoMap(userCard);
    }
    
    @RequestMapping("/userList")
    @ResponseBody
    public Object userList(String userLname, Integer orgId, String userDuty, String userWorkday, Integer offset, Integer limit) {
        List<DtUser> list = patientRegistrationService.userList(userLname, orgId, userDuty, userWorkday, offset, limit);
        return MapUtil.createSuccessMap("rows", list.stream().skip(offset).limit(limit).collect(Collectors.toList()), "total", list.size());
    }

    @RequestMapping("/findByUserName")
    @ResponseBody
    public Object findByUserName(String q) {
        if (StringUtil.isNullEmpty(q)) {
            return Collections.emptyList();
        }
        return patientRegistrationService.findByUserName(q);
    }

    @RequestMapping("/depTree")
    @ResponseBody
    public Object depTree(Long id) {
        return DepCache.select(id);
    }

    @RequestMapping("/depSelect")
    @ResponseBody
    public Object depSelect() {
        return patientRegistrationService.depSelect();
    }

    @RequestMapping("/findDoctorByUserName")
    @ResponseBody
    public Object findDoctorByUserName(String q) {
        return patientRegistrationService.findDoctorByUserName(q);
    }
}
