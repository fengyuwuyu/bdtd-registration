package com.bdtd.card.registration.modular.treatment.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.bdtd.card.registration.common.model.EnumHandleTypeMask;
import com.bdtd.card.registration.common.model.EnumPatientInfoStatus;
import com.bdtd.card.registration.common.model.EnumPatientTransferStatus;
import com.bdtd.card.registration.common.model.chart.CylinderShapeEntity;
import com.bdtd.card.registration.common.utils.ChartEntityUtil;
import com.bdtd.card.registration.modular.transfer.service.IPatientTransferTreatmentService;
import com.bdtd.card.registration.modular.treatment.service.IPatientInfoService;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.model.EnumOperator;
import com.stylefeng.guns.core.util.CommonUtils;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.core.wrapper.MybatisCustomWrapper;
import com.stylefeng.guns.modular.system.dao.PatientInfoMapper;
import com.stylefeng.guns.modular.system.model.ChartEntity;
import com.stylefeng.guns.modular.system.model.PatientInfo;
import com.stylefeng.guns.modular.system.model.PatientTransferTreatment;
import com.stylefeng.guns.scmmain.dao.IDtDepDao;

/**
 * <p>
 * 病患信息 服务实现类
 * </p>
 *
 * @author lilei123
 * @since 2018-06-26
 */
@Service
public class PatientInfoServiceImpl extends ServiceImpl<PatientInfoMapper, PatientInfo> implements IPatientInfoService {
    
    @Autowired
    IPatientTransferTreatmentService transferTreatmentService;
    @Autowired
    private IDtDepDao dtDepDao;
    
    private PatientInfo currPatient;

    @Override
    public void updateHandleType(Integer id, EnumHandleTypeMask handleType) {
        updateHandleType(id, handleType.getType());
    }

    @Override
    public void updateHandleType(Integer id, Long handleType) {
        PatientInfo info = selectById(id);
        info.setHandleType(info.getHandleType() | handleType);
        info.setUpdateDate(new Date());
        updateById(info);
    }

    @Override
    public int findByInTreatmentCount(String userNo) {
        return this.baseMapper.findByInTreatmentCount(userNo);
    }

    @Override
    public Tip cancelHandleType(Integer id, EnumHandleTypeMask handleType) {
        return cancelHandleType(id, handleType.getType());
    }

    @Override
    public Tip cancelHandleType(Integer id, Long handleType) {
        PatientInfo patientInfo = selectById(id);
        if (patientInfo == null) {
            return new Tip(500, "该记录不存在！");
        }
        patientInfo.setHandleType(patientInfo.getHandleType() ^ handleType);
        patientInfo.setUpdateDate(new Date());
        updateById(patientInfo);
        return null;
    }

    @Override
    public List<PatientInfo> findCanInHospital(String userName) {
        Wrapper<PatientInfo> wrapper = new MybatisCustomWrapper<>();
        CommonUtils.handleRequestParams(wrapper, "user_name", userName, EnumOperator.LIKE, "handle_type", EnumHandleTypeMask.TRANSFER_TREATMENT.getType(),
                EnumOperator.BIT, "handle_type", EnumHandleTypeMask.HAS_IN_HOSPITAL.getType(), EnumOperator.XOR,
                "status", 2, EnumOperator.GT);
        wrapper.orderDesc(Arrays.asList("update_date"));
        return this.selectList(wrapper);
    }

    @Override
    public List<Map<String, Object>> findCanInHospitalMap(String userName) {
        if (StringUtil.isNullEmpty(userName)) {
            return Collections.emptyList();
        }
        Wrapper<PatientInfo> wrapper = new MybatisCustomWrapper<>();
        CommonUtils.handleRequestParams(wrapper, "user_name", userName, EnumOperator.LIKE, "handle_type", EnumHandleTypeMask.TRANSFER_TREATMENT.getType(),
                EnumOperator.BIT, "handle_type", EnumHandleTypeMask.HAS_IN_HOSPITAL.getType(), EnumOperator.XOR,
                "status", 2, EnumOperator.GT);
        wrapper.orderDesc(Arrays.asList("clinic_date"));
        List<Map<String, Object>> result = new ArrayList<>();
        List<Map<String, Object>> list = this.selectMaps(wrapper);
        if (list == null || list.size() == 0) {
            return result;
        }
        
        Map<String, List<Map<String, Object>>> map = list.stream().collect(Collectors.groupingBy((item) -> {
            return (String)item.get("userNo");
        }));
        if (map == null || map.size() == 0) {
            return result;
        }
        
        map.values().stream().forEach((item) -> {
            if (item.size() > 1) {
                Collections.sort(item, (a, b) ->{
                    return Long.valueOf(((Date)a.get("clinicDate")).getTime() - ((Date)b.get("clinicDate")).getTime()).intValue();
                });
            }
            result.add(item.get(item.size() - 1));
            
        });
        
        result.sort((a, b) -> {
            return Long.valueOf(((Date)b.get("clinicDate")).getTime() - ((Date)a.get("clinicDate")).getTime()).intValue();
        });
        return result;
    }

    @Override
    public int cancelHandleType(List<Integer> idList, EnumHandleTypeMask handleType) {
        return this.baseMapper.cancelHandleTypeByIdList(MapUtil.createMap("idList", idList , "handleType", handleType.getType()));
    }

    @Override
    public Tip canRegistration(String userNo) {
        Wrapper<PatientInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("user_no", userNo);
        wrapper.orderBy("create_date", false);
        Page<PatientInfo> page = this.selectPage(new Page<>(0, 5), wrapper);
        if (page.getTotal() == 0) {
            return null;
        }
        
        for (PatientInfo info : page.getRecords()) {
            if (info.getStatus() == EnumPatientInfoStatus.REGISTRATION.getType() || info.getStatus() == EnumPatientInfoStatus.DIAGNOSISING.getType()) {
                if (info.getCreateDate().getTime() / DateUtil.ONE_DAY_TIME == new Date().getTime() / DateUtil.ONE_DAY_TIME) {
                    return new Tip(500, "您当前已挂过号，请直接就诊！");
                }
            }
            if ((info.getHandleType() & EnumHandleTypeMask.TRANSFER_TREATMENT.getType()) == EnumHandleTypeMask.TRANSFER_TREATMENT.getType()) {
                Wrapper<PatientTransferTreatment> wrapper1 = new EntityWrapper<>();
                wrapper1.eq("patient_info_id", info.getId());
                PatientTransferTreatment transfer = transferTreatmentService.selectOne(wrapper1);
                if (transfer != null && transfer.getStatus() == EnumPatientTransferStatus.COLLECTED.getType()) {
                    return new Tip(500, "当前存在转诊未回报记录，请先完成转诊回报！");
                }
            }
        }
        return null;
    }

    @Override
    public CylinderShapeEntity<Integer> trainWoundChart(Map<String, Object> param) {
        List<ChartEntity<Integer>> entities = this.baseMapper.patientInfoChart(param);
        String title = "训练伤统计图";
        String subTitle = "66194部队就诊系统";
        List<String> legend = Arrays.asList("人数");
        String seriesName = "训练伤";
        return ChartEntityUtil.getChartEntity(title, subTitle, legend, entities, dtDepDao, seriesName, null);
    }

    @Override
    public CylinderShapeEntity<Integer> feverChart(Map<String, Object> param) {
        List<ChartEntity<Integer>> entities = this.baseMapper.patientInfoChart(param);
        String title = "发热统计图";
        String subTitle = "66194部队就诊系统";
        List<String> legend = Arrays.asList("人数");
        String seriesName = "发热";
        return ChartEntityUtil.getChartEntity(title, subTitle, legend, entities, dtDepDao, seriesName, null);
    }

    @Override
    public long countByTimeBetween(java.sql.Date beginDate, java.sql.Date endDate) {
        return this.baseMapper.countByTimeBetween(MapUtil.createMap("beginDate", beginDate, "endDate", endDate));
    }

    @Override
    public PatientInfo getCurrPatient() {
        return this.currPatient;
    }

    @Override
    public void setCurrPatient(PatientInfo patientInfo) {
        this.currPatient = patientInfo;
    }

    @Override
    public int resetStatus(Integer id) {
        return this.baseMapper.resetStatus(MapUtil.createMap("id", id));
    }

    @Override
    public long total(Map<String, Object> params) {
        return this.baseMapper.total(params);
    }

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> params) {
        return this.baseMapper.findMaps(params);
    }

    @Override
    public CylinderShapeEntity<Integer> patientInfoChart(Map<String, Object> params) {
        List<ChartEntity<Integer>> entitys = this.baseMapper.patientInfoChart(params);
        String title = "门诊统计图";
        String subTitle = "66194部队就诊系统";
        List<String> legend = Arrays.asList("人数");
        String seriesName = "门诊";
        return ChartEntityUtil.getChartEntity(title, subTitle, legend, entitys, dtDepDao, seriesName, null);
    }

}
