package com.bdtd.card.registration.modular.inhospital.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.bdtd.card.registration.common.model.EnumInhospitalTakeMedicalStatus;
import com.bdtd.card.registration.common.model.EnumInhospitalTakeMedicalType;
import com.bdtd.card.registration.common.model.EnumOutpatientType;
import com.bdtd.card.registration.modular.inhospital.model.InhospitalTakeMedicalVo;
import com.bdtd.card.registration.modular.inhospital.service.IInHospitalLongtermMedicineInfoService;
import com.bdtd.card.registration.modular.inhospital.service.IInHospitalTemporaryMedicineInfoService;
import com.bdtd.card.registration.modular.inhospital.service.IInhospitalTakeMedicalService;
import com.bdtd.card.registration.modular.inhospital.service.IPatientInHospitalService;
import com.bdtd.card.registration.modular.inventory.service.IMedicalInventoryPharmacyService;
import com.bdtd.card.registration.modular.treatment.service.IPatientInfoService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.common.annotion.DictEntity;
import com.stylefeng.guns.core.common.annotion.DictHandler;
import com.stylefeng.guns.core.common.annotion.EnumEntity;
import com.stylefeng.guns.core.common.annotion.EnumEntityList;
import com.stylefeng.guns.core.consts.Consts;
import com.stylefeng.guns.core.consts.DictConsts;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.DictCacheFactory;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.core.wrapper.DictWrapperEntity;
import com.stylefeng.guns.modular.system.model.InHospitalLongtermMedicineInfo;
import com.stylefeng.guns.modular.system.model.InHospitalTemporaryMedicineInfo;
import com.stylefeng.guns.modular.system.model.InhospitalTakeMedical;
import com.stylefeng.guns.modular.system.model.PatientInHospital;
import com.stylefeng.guns.modular.system.model.PatientInfo;
import com.stylefeng.guns.modular.system.model.PatientPrescriptionMedicineInfo;

/**
 * 控制器
 *
 * @author 
 * @Date 2018-12-04 14:13:10
 */
@Controller
@RequestMapping("/inhospitalTakeMedical")
public class InhospitalTakeMedicalController extends BaseController {

    private String PREFIX = "/inhospital/inhospitalTakeMedical/";
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IInhospitalTakeMedicalService inhospitalTakeMedicalService;
    @Autowired
    private IPatientInfoService patientInfoService;
    @Autowired
    private IPatientInHospitalService inHospitalService;
    @Autowired
    private IInHospitalLongtermMedicineInfoService longtermMedicineInfoService;
    @Autowired
    private IInHospitalTemporaryMedicineInfoService temporaryMedicineInfoService;
    @Autowired
    private IMedicalInventoryPharmacyService medicalInventoryPharmacyService;
    @Autowired
    private DictCacheFactory dictCacheFactory;

    /**
     * 跳转到首页
     */
    @RequestMapping("/{queryType}")
    public String index(@PathVariable Integer queryType, Model model) {
        model.addAttribute("queryType", queryType);
        return PREFIX + "inhospitalTakeMedical.html";
    }

    @RequestMapping("/openTakeMedical/{id}/{type}")
    public String openTakeMedical(Model model, @PathVariable Integer id, @PathVariable Integer type) {
        InhospitalTakeMedical data = this.inhospitalTakeMedicalService.selectById(id);
        model.addAttribute("id", id);
        model.addAttribute("type", type);
        model.addAttribute("status", data.getStatus());
        return PREFIX + "inhospitalTakeMedical_takeMedical.html";
    }
    
    /**
     * 跳转到添加
     */
    @RequestMapping("/inhospitalTakeMedical_add")
    public String inhospitalTakeMedicalAdd() {
        return PREFIX + "inhospitalTakeMedical_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/inhospitalTakeMedical_update/{inhospitalTakeMedicalId}")
    public String inhospitalTakeMedicalUpdate(@PathVariable Integer inhospitalTakeMedicalId, Model model) {
        InhospitalTakeMedical inhospitalTakeMedical = inhospitalTakeMedicalService.selectById(inhospitalTakeMedicalId);
        model.addAttribute("item",inhospitalTakeMedical);
        LogObjectHolder.me().set(inhospitalTakeMedical);
        return PREFIX + "inhospitalTakeMedical_edit.html";
    }
    
    /**
     * 跳转到处方页面
     */@DictHandler(dictModels = { DictConsts.DOCTOR }, dictKeys = { DictConsts.DOCTOR_KEY })
    @RequestMapping("/openPrescriptionDetail/{id}/{type}")
    public String openPrescriptionDetail(@PathVariable Integer id, @PathVariable Integer type, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("type", type);
        return PREFIX + "inhospitalPrescriptionDetail.html";
    }
    
     /**
      * 跳转到处方页面
      */
      @RequestMapping("/openPrescriptionPrint/{id}/{type}")
      public String openPrescriptionPrint(@PathVariable Integer id, @PathVariable Integer type, Model model) {
          Wrapper<InhospitalTakeMedical> wrapper = new EntityWrapper<>();
          wrapper.eq("id", id);
          Map<String, Object> patientInfo = this.inhospitalTakeMedicalService.selectMap(wrapper);
          patientInfo.put("outpatient", EnumOutpatientType.fromType((Integer)patientInfo.get("outpatient")).getDesc());
          dictCacheFactory.wrapper(Arrays.asList(patientInfo), Arrays.asList(
                  new DictWrapperEntity(DictConsts.REGISTRATION_MAIN_DIAGNOSIS, DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME)));
          model.addAttribute("patientInfo", patientInfo);
          
          String remark = (String) patientInfo.get("remark");
          patientInfo.put("remark", StringUtil.isNullEmpty(remark) ? "" : remark);
          
          List<Map<String, Object>> medicalList = null;
          Map<String, Object> params = MapUtil.createMap("inhospitalTakeMedicalId", id);
          if (type == EnumInhospitalTakeMedicalType.LONGTERM.getType()) {
              medicalList = this.longtermMedicineInfoService.findMaps(params);
          } else if (type == EnumInhospitalTakeMedicalType.TEMPORARY.getType()) {
              medicalList = this.temporaryMedicineInfoService.findMaps(params);
          }
          if (medicalList != null && medicalList.size() > 0) {
              dictCacheFactory.wrapper(medicalList, Arrays.asList(
                      new DictWrapperEntity(DictConsts.MEDICAL_UNIT, DictConsts.MEDICAL_UNIT_FIELD_NAME),
                      new DictWrapperEntity(DictConsts.TREATMENT_USAGE, DictConsts.TREATMENT_USAGE_FIELD_NAME),
                      new DictWrapperEntity(DictConsts.TREATMENT_DOSAGE, DictConsts.TREATMENT_DOSAGE_FIELD_NAME),
                      new DictWrapperEntity(DictConsts.TREATMENT_PERIOD, DictConsts.TREATMENT_PERIOD_FIELD_NAME)
                      ));
              model.addAttribute("medicalList", medicalList);
          } else {
              model.addAttribute("medicalList", Collections.emptyList());
          }
          
          double money = 0L;
          if (medicalList != null && medicalList.size() > 0) {
              Iterator<Map<String, Object>> iterator = medicalList.iterator();
              while(iterator.hasNext()) {
                  Map<String, Object> map = iterator.next();
                  Double price = (Double) map.get("price");
                  if (price == null) {
                      iterator.remove();
                  } else {
                      Integer amount = (Integer)map.get("amount");
                      money += price * amount;
                  }
              }
          }
          BigDecimal b = new BigDecimal(money); 
          money = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
          model.addAttribute("money", money);
          model.addAttribute("fontFace", "@font-face");
          model.addAttribute("page", "@page");
          
          Date clinicDate = (Date) patientInfo.get("createDate");
          model.addAttribute("clinicDate", clinicDate == null ? "" : new java.sql.Date(clinicDate.getTime()));
          model.addAttribute("now", DateUtil.format(new Date(), Consts.DATE_PATTERN));
          
          Integer status = (Integer) patientInfo.get("status");
          if (status == EnumInhospitalTakeMedicalStatus.PRESCRIPTIONED.getType()) {
            InhospitalTakeMedical takeMedical = new InhospitalTakeMedical();
            takeMedical.setId(id);
            takeMedical.setStatus(EnumInhospitalTakeMedicalStatus.HAS_BILLING.getType());
            this.inhospitalTakeMedicalService.updateById(takeMedical);
            
            if (type == EnumInhospitalTakeMedicalType.LONGTERM.getType()) {
                InHospitalLongtermMedicineInfo entity = new InHospitalLongtermMedicineInfo();
                entity.setStatus(EnumInhospitalTakeMedicalStatus.HAS_BILLING.getType());
                Wrapper<InHospitalLongtermMedicineInfo> longtermWrapper = new EntityWrapper<>();
                longtermWrapper.eq("inhospital_take_medical_id", id);
                this.longtermMedicineInfoService.update(entity, longtermWrapper);
            } else {
                InHospitalTemporaryMedicineInfo entity = new InHospitalTemporaryMedicineInfo();
                entity.setStatus(EnumInhospitalTakeMedicalStatus.HAS_BILLING.getType());
                Wrapper<InHospitalTemporaryMedicineInfo> temporaryWrapper = new EntityWrapper<>();
                temporaryWrapper.eq("inhospital_take_medical_id", id);
                this.temporaryMedicineInfoService.update(entity, temporaryWrapper);
            }
          }
          
          return PREFIX + "prescription.html";
      }
    /**
     * 跳转到处方页面
     */@DictHandler(dictModels = { DictConsts.DOCTOR }, dictKeys = { DictConsts.DOCTOR_KEY })
    @RequestMapping("/openPrescription/{inhospitalId}/{ids}/{type}")
    public String openPrescription(@PathVariable Integer inhospitalId, @PathVariable String ids, @PathVariable Integer type, Model model) {
        model.addAttribute("inhospitalId", inhospitalId);
        model.addAttribute("ids", ids);
        model.addAttribute("type", type);
        model.addAttribute("physicianName", ShiroKit.getUser().getDtUser().getUserLname());
        return PREFIX + "inhospitalPrescription.html";
    }
     
     @RequestMapping("/medicalList")
     @ResponseBody
     @DictHandler(dictWrappers= {
             @DictEntity(parentId= DictConsts.MEDICAL_UNIT, fieldName=DictConsts.MEDICAL_UNIT_FIELD_NAME),
             @DictEntity(parentId= DictConsts.TREATMENT_USAGE, fieldName=DictConsts.TREATMENT_USAGE_FIELD_NAME),
             @DictEntity(parentId= DictConsts.TREATMENT_DOSAGE, fieldName=DictConsts.TREATMENT_DOSAGE_FIELD_NAME),
             @DictEntity(parentId= DictConsts.MEDICAL_SPECIFICATION, fieldName=DictConsts.MEDICAL_SPECIFICATION_FIELD_NAME),
             @DictEntity(parentId= DictConsts.TREATMENT_PERIOD, fieldName=DictConsts.TREATMENT_PERIOD_FIELD_NAME)
     })
     @EnumEntityList(entityList= {
             @EnumEntity(fieldName="status", enumName="EnumInhospitalTakeMedicalStatus", replaceName="statusStr")
     })
     public Map<String, Object> medicalList(@RequestParam(required=true) Integer id, @RequestParam(required=true) Integer type, Integer offset, Integer limit) {
         if (type == EnumInhospitalTakeMedicalType.LONGTERM.getType()) {
             Wrapper<InHospitalLongtermMedicineInfo> wrapper = new EntityWrapper<>();
             wrapper.eq("inhospital_take_medical_id", id);
             Page<Map<String, Object>> page = this.longtermMedicineInfoService.selectMapsPage(new Page<>(offset / limit + 1, limit), wrapper);
             return MapUtil.createSuccessMap("rows", page.getRecords(), "total", page.getTotal());
         } else if (type == EnumInhospitalTakeMedicalType.TEMPORARY.getType()) {
             Wrapper<InHospitalTemporaryMedicineInfo> wrapper = new EntityWrapper<>();
             wrapper.eq("inhospital_take_medical_id", id);
             Page<Map<String, Object>> page = this.temporaryMedicineInfoService.selectMapsPage(new Page<>(offset / limit + 1, limit), wrapper);
             return MapUtil.createSuccessMap("rows", page.getRecords(), "total", page.getTotal());
         } 
         return MapUtil.createSuccessMap("total", 0, "rows", Collections.emptyList());
     }
     
     @RequestMapping("/takeMedical")
     @ResponseBody
     public Map<String, Object> takeMedical(@RequestParam(required=true) Integer id, @RequestParam(required=true) Integer type) {
         InhospitalTakeMedical data = this.inhospitalTakeMedicalService.selectById(id);
         if (data.getStatus() == EnumInhospitalTakeMedicalStatus.HAS_TAKE_MEDICINE.getType()) {
             return MapUtil.createFailedMap("msg", "该处方已取药！");
         }
         
         List<Map<String, Object>> medicalList = null;
         Map<String, Object> params = MapUtil.createMap("inhospitalTakeMedicalId", id);
         if (type == EnumInhospitalTakeMedicalType.LONGTERM.getType()) {
             medicalList = this.longtermMedicineInfoService.findMaps(params);
         } else if (type == EnumInhospitalTakeMedicalType.TEMPORARY.getType()) {
             medicalList = this.temporaryMedicineInfoService.findMaps(params);
         }
         
         if (medicalList == null || medicalList.size() == 0) {
             return MapUtil.createFailedMap("msg", "没有药品可取！");
         }
         
         List<PatientPrescriptionMedicineInfo> prescriptionMedicalList = medicalList.stream().map((item) -> {
             Integer medicineId = (Integer) item.get("medicineId");
            Integer amount = (Integer) item.get("amount");
            return new PatientPrescriptionMedicineInfo(medicineId, amount);
         }).collect(Collectors.toList());
         Tip result = medicalInventoryPharmacyService.takeMedicalByPresrciption(prescriptionMedicalList, "住院");
         if (result != null) {
             return MapUtil.createFailedMap("msg", result.getMessage());
         }
         
         data.setTakeMedicalDate(new Date());
         data.setStatus(EnumInhospitalTakeMedicalStatus.HAS_TAKE_MEDICINE.getType());
         this.inhospitalTakeMedicalService.updateById(data);
         
         if (type == EnumInhospitalTakeMedicalType.LONGTERM.getType()) {
             InHospitalLongtermMedicineInfo entity = new InHospitalLongtermMedicineInfo();
             entity.setStatus(EnumInhospitalTakeMedicalStatus.HAS_TAKE_MEDICINE.getType());
             Wrapper<InHospitalLongtermMedicineInfo> longtermWrapper = new EntityWrapper<>();
             longtermWrapper.eq("inhospital_take_medical_id", id);
             this.longtermMedicineInfoService.update(entity, longtermWrapper);
         } else {
             InHospitalTemporaryMedicineInfo entity = new InHospitalTemporaryMedicineInfo();
             entity.setStatus(EnumInhospitalTakeMedicalStatus.HAS_TAKE_MEDICINE.getType());
             Wrapper<InHospitalTemporaryMedicineInfo> temporaryWrapper = new EntityWrapper<>();
             temporaryWrapper.eq("inhospital_take_medical_id", id);
             this.temporaryMedicineInfoService.update(entity, temporaryWrapper);
         }
         return MapUtil.createSuccessMap("msg", "取药成功！");
     }

    /**
     * 获取列表
     */
    @DictHandler(dictWrappers= {@DictEntity(parentId = DictConsts.REGISTRATION_MAIN_DIAGNOSIS, fieldName = DictConsts.REGISTRATION_MAIN_DIAGNOSIS_FIELD_NAME) })
    @EnumEntityList(entityList= {
            @EnumEntity(fieldName="status", enumName="EnumInhospitalTakeMedicalStatus", replaceName="statusStr"),
            @EnumEntity(fieldName="type", enumName="EnumInhospitalTakeMedicalType", replaceName="typeStr")
    })
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(Integer queryType, String condition, Integer offset, Integer limit) {
    	Wrapper<InhospitalTakeMedical> wrapper = new EntityWrapper<>();
    	if (queryType == 2) {
    	    wrapper.in("status", Arrays.asList(EnumInhospitalTakeMedicalStatus.HAS_BILLING.getType(), EnumInhospitalTakeMedicalStatus.HAS_TAKE_MEDICINE.getType()));
    	}
    	Page<Map<String, Object>> page = inhospitalTakeMedicalService.selectMapsPage(new Page<>(offset / limit + 1, limit, Consts.CREATE_DATE_SORT_FIELD, Consts.DEFAULT_SORT_ORDER_IS_ASC), wrapper);
		return MapUtil.createSuccessMap("rows", page.getRecords(), "total", page.getTotal());
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(InhospitalTakeMedical inhospitalTakeMedical) {
        Date createDate = new Date();
        inhospitalTakeMedical.setCreateDate(createDate);
        inhospitalTakeMedicalService.insert(inhospitalTakeMedical);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/deleteMedical")
    @ResponseBody
    public Object deleteMedical(@RequestParam(required=true) Integer id, @RequestParam(required=true) Integer type) {
        if (type == EnumInhospitalTakeMedicalType.LONGTERM.getType()) {
            InHospitalLongtermMedicineInfo info = this.longtermMedicineInfoService.selectById(id);
            if (info == null) {
                return new Tip(500, "该记录已不存在，请刷新页面！");
            }
            if (info.getStatus() == EnumInhospitalTakeMedicalStatus.HAS_TAKE_MEDICINE.getType()) {
                return new Tip(500, "已取药记录不允许删除!");
            }
            this.longtermMedicineInfoService.deletePrescription(MapUtil.createMap("id", id, "status", EnumInhospitalTakeMedicalStatus.CREATED.getType()));
        } else {
            InHospitalTemporaryMedicineInfo info = this.temporaryMedicineInfoService.selectById(id);
            if (info == null) {
                return new Tip(500, "该记录已不存在，请刷新页面！");
            }
            if (info.getStatus() == EnumInhospitalTakeMedicalStatus.HAS_TAKE_MEDICINE.getType()) {
                return new Tip(500, "已取药记录不允许删除!");
            }
            this.temporaryMedicineInfoService.deletePrescription(MapUtil.createMap("id", id, "status", EnumInhospitalTakeMedicalStatus.CREATED.getType()));
        }
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer inhospitalTakeMedicalId) {
        InhospitalTakeMedical inhospitalTakeMedical = this.inhospitalTakeMedicalService.selectById(inhospitalTakeMedicalId);
        if (inhospitalTakeMedical == null) {
            return new Tip(500L, "该记录已不存在，请刷新页面！");
        }
        
        if (inhospitalTakeMedical.getStatus() == EnumInhospitalTakeMedicalStatus.HAS_TAKE_MEDICINE.getType()) {
            return new Tip(500L, "已取药记录不允许删除!");
        }
        if (inhospitalTakeMedical.getType() == EnumInhospitalTakeMedicalType.LONGTERM.getType()) {
            InHospitalLongtermMedicineInfo entity = new InHospitalLongtermMedicineInfo();
            entity.setStatus(EnumInhospitalTakeMedicalStatus.CREATED.getType());
            Wrapper<InHospitalLongtermMedicineInfo> wrapper = new EntityWrapper<>();
            wrapper.eq("inhospital_take_medical_id", inhospitalTakeMedicalId);
            this.longtermMedicineInfoService.update(entity, wrapper);
        } else {
            InHospitalTemporaryMedicineInfo entity = new InHospitalTemporaryMedicineInfo();
            entity.setStatus(EnumInhospitalTakeMedicalStatus.CREATED.getType());
            Wrapper<InHospitalTemporaryMedicineInfo> wrapper = new EntityWrapper<>();
            wrapper.eq("inhospital_take_medical_id", inhospitalTakeMedicalId);
            this.temporaryMedicineInfoService.update(entity, wrapper);
        }
        inhospitalTakeMedicalService.deleteById(inhospitalTakeMedicalId);
        return SUCCESS_TIP;
    }
    
    @RequestMapping("/prescription")
    @ResponseBody
    public Map<String, Object> prescription(InhospitalTakeMedicalVo vo) {
        if (vo == null || vo.getIds() == null || vo.getInhospitalId() == null || vo.getType() == null || vo.getIds().length == 0) {
            return MapUtil.createFailedMap("msg", "非法请求！");
        }

        EnumInhospitalTakeMedicalType takeMedicalType = EnumInhospitalTakeMedicalType.typeOf(vo.getType());
        if (takeMedicalType == null) {
            return MapUtil.createFailedMap("msg", "非法请求！");
        }
        
        PatientInHospital patientInHospital = this.inHospitalService.selectById(vo.getInhospitalId());
        if (patientInHospital == null) {
            return MapUtil.createFailedMap("msg", "该记录已删除！");
        }
        PatientInfo info = this.patientInfoService.selectById(patientInHospital.getPatientInfoId());
        if (info == null) {
            log.warn("PatientInfo not exist, patientInfoId = {}", vo.getInhospitalId());
            return MapUtil.createFailedMap("msg", "就诊记录已删除！");
        }
        
        if (takeMedicalType == EnumInhospitalTakeMedicalType.LONGTERM) {
            List<InHospitalLongtermMedicineInfo> longtermMedicineInfoList = this.longtermMedicineInfoService.selectBatchIds(Arrays.asList(vo.getIds()));
            for (InHospitalLongtermMedicineInfo longterm : longtermMedicineInfoList) {
                if (longterm.getStatus() != EnumInhospitalTakeMedicalStatus.CREATED.getType()) {
                    return MapUtil.createFailedMap("msg", "只能选择未处方的记录！");
                }
            }
        } else {
            List<InHospitalTemporaryMedicineInfo> temporaryMedicineInfoList = this.temporaryMedicineInfoService.selectBatchIds(Arrays.asList(vo.getIds()));
            for (InHospitalTemporaryMedicineInfo temporaryMedicineInfo : temporaryMedicineInfoList) {
                if (temporaryMedicineInfo.getStatus() != EnumInhospitalTakeMedicalStatus.CREATED.getType()) {
                    return MapUtil.createFailedMap("msg", "只能选择未处方的记录！");
                }
            }
        }
        
        String userNo = info.getUserNo();
        String userName = info.getUserName();
        String userSex = info.getUserSex();
        String userCard = info.getUserCard();
        Integer age = info.getAge();
        String userDuty = info.getUserDuty();
        Long orgId = info.getOrgId();
        String orgName = info.getOrgName();
        Date enrolDate = info.getEnrolDate();
        Integer type = vo.getType();
        Integer status = EnumInhospitalTakeMedicalStatus.PRESCRIPTIONED.getType();
        Integer mainDiagnosis = info.getMainDiagnosis();
        String physicianName = vo.getPhysicianName();
        Date createDate = new Date();
        String operatorName = ShiroKit.getUser().getDtUser().getUserLname();
        String operatorNo = ShiroKit.getUser().getDtUser().getUserNo();
        String remark = vo.getRemark();
        Integer outpatient = info.getOutpatient();
        InhospitalTakeMedical entity = new InhospitalTakeMedical(userNo, userName, userSex, userCard, age, userDuty, orgId, orgName, enrolDate, type, status, mainDiagnosis, 
                physicianName, createDate, operatorName, operatorNo, remark, outpatient );
        this.inhospitalTakeMedicalService.insert(entity);
        
        if (takeMedicalType == EnumInhospitalTakeMedicalType.LONGTERM) {
            this.longtermMedicineInfoService.updatePrescription(vo.getIds(), entity.getId(), EnumInhospitalTakeMedicalStatus.PRESCRIPTIONED);
        } else {
            this.temporaryMedicineInfoService.updatePrescription(vo.getIds(), entity.getId(), EnumInhospitalTakeMedicalStatus.PRESCRIPTIONED);
        }
        
        return MapUtil.createSuccessMap("msg", "操作成功！");
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(InhospitalTakeMedical inhospitalTakeMedical) {
        inhospitalTakeMedicalService.updateById(inhospitalTakeMedical);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{inhospitalTakeMedicalId}")
    @ResponseBody
    public Object detail(@PathVariable("inhospitalTakeMedicalId") Integer inhospitalTakeMedicalId) {
        return inhospitalTakeMedicalService.selectById(inhospitalTakeMedicalId);
    }
}
