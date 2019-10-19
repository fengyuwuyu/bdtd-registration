package com.bdtd.card.registration.modular.alert.controller;

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
import com.bdtd.card.registration.cache.AlertDepCache;
import com.bdtd.card.registration.modular.alert.service.IAlertDepService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.modular.system.model.AlertDep;
import com.stylefeng.guns.scmmain.model.DtDep;
import com.stylefeng.guns.scmmain.service.IDtDepService;

/**
 * 报警部门控制器
 *
 * @author 
 * @Date 2018-10-29 12:22:47
 */
@Controller
@RequestMapping("/alertDep")
public class AlertDepController extends BaseController {

    private String PREFIX = "/alert/alertDep/";

    @Autowired
    private IAlertDepService alertDepService;
    @Autowired
    private IDtDepService dtDepService;

    /**
     * 跳转到报警部门首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "alertDep.html";
    }

    /**
     * 跳转到添加报警部门
     */
    @RequestMapping("/alertDep_add")
    public String alertDepAdd() {
        return PREFIX + "alertDep_add.html";
    }

    /**
     * 跳转到修改报警部门
     */
    @RequestMapping("/alertDep_update/{alertDepId}")
    public String alertDepUpdate(@PathVariable Integer alertDepId, Model model) {
        AlertDep alertDep = alertDepService.selectById(alertDepId);
        model.addAttribute("item",alertDep);
        LogObjectHolder.me().set(alertDep);
        return PREFIX + "alertDep_edit.html";
    }

    /**
     * 获取报警部门列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition, Integer offset, Integer limit) {
    	Wrapper<AlertDep> wrapper = new EntityWrapper<>();
    	Page<Map<String, Object>> page = alertDepService.selectMapsPage(new Page<>(offset, limit, Consts.DEFAULT_SORT_FIELD, Consts.DEFAULT_SORT_ORDER_IS_ASC), wrapper);
		return MapUtil.createSuccessMap("rows", page.getRecords(), "total", page.getTotal());
    }

    /**
     * 新增报警部门
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(AlertDep alertDep) {
        updateValue(alertDep);
        
        Date createDate = new Date();
        alertDep.setCreateDate(createDate);
        alertDep.setUpdateDate(createDate);
        alertDepService.insert(alertDep);
        AlertDepCache.addCache(alertDep);
        return SUCCESS_TIP;
    }

    /**
     * 删除报警部门
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer alertDepId) {
        alertDepService.deleteById(alertDepId);
        AlertDepCache.delete(alertDepId);
        return SUCCESS_TIP;
    }

    /**
     * 修改报警部门
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AlertDep alertDep) {
        Wrapper<AlertDep> wrapper = new EntityWrapper<>();
        wrapper.eq("dep_serial", alertDep.getDepSerial());
        AlertDep entity = this.alertDepService.selectOne(wrapper);
        if (entity != null) {
            return new Tip(500L, "已添加该部门！");
        }
        updateValue(alertDep);
        
        alertDep.setUpdateDate(new Date());
        alertDepService.updateById(alertDep);
        AlertDepCache.updateCache(alertDep);
        return SUCCESS_TIP;
    }
    
    private void updateValue(AlertDep alertDep) {
        DtDep dep = dtDepService.findById(alertDep.getDepSerial());
        alertDep.setDepName(dep.getDepName());
        alertDep.setDepNo(dep.getDepNo());
        DtDep parent = this.dtDepService.findById(dep.getDepParent().longValue());
        if (parent != null) {
            alertDep.setParentName(parent.getDepName());
        }
    }

    /**
     * 报警部门详情
     */
    @RequestMapping(value = "/detail/{alertDepId}")
    @ResponseBody
    public Object detail(@PathVariable("alertDepId") Integer alertDepId) {
        return alertDepService.selectById(alertDepId);
    }
}
