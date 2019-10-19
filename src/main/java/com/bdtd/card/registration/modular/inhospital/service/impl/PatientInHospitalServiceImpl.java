package com.bdtd.card.registration.modular.inhospital.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.bdtd.card.registration.common.model.EnumInHospitalStatus;
import com.bdtd.card.registration.common.model.EnumSickRestType;
import com.bdtd.card.registration.common.model.chart.CylinderShapeEntity;
import com.bdtd.card.registration.common.model.chart.SeriesEntity;
import com.bdtd.card.registration.modular.inhospital.service.IPatientInHospitalService;
import com.stylefeng.guns.config.properties.BdtdProperties;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.modular.system.dao.PatientInHospitalMapper;
import com.stylefeng.guns.modular.system.dao.PatientInfoMapper;
import com.stylefeng.guns.modular.system.dao.PatientSickRestMapper;
import com.stylefeng.guns.modular.system.dao.PatientTransferTreatmentMapper;
import com.stylefeng.guns.modular.system.model.ChartEntity;
import com.stylefeng.guns.modular.system.model.PatientInHospital;
import com.stylefeng.guns.scmmain.dao.IDtUserDao;

/**
 * <p>
 * 住院 服务实现类
 * </p>
 *
 * @author lilei123
 * @since 2018-06-26
 */
@Service
public class PatientInHospitalServiceImpl extends ServiceImpl<PatientInHospitalMapper, PatientInHospital> implements IPatientInHospitalService {
    
    @Autowired
    private IDtUserDao dtUserDao;
    @Autowired
    private PatientInfoMapper patientInfoMapper;
    @Autowired
    private PatientTransferTreatmentMapper transferTreatmentMapper;
    @Autowired
    private PatientSickRestMapper sickRestMapper;
    @Autowired
    private BdtdProperties bdtdProperties;

    @Override
    public List<Map<String, Object>> findByMap(Map<String, Object> params) {
        return this.baseMapper.findByMap(params);
    }

    @Override
    public long countByMap(Map<String, Object> params) {
        return this.baseMapper.countByMap(params);
    }

    @Override
    public Map<String, Object> findById(Integer id) {
        return this.baseMapper.findById(id);
    }

    @Override
    public long countInhospitalByMap(Map<String, Object> params) {
    	params.put("inhospitalDictIds", bdtdProperties.getInhospitalDictIds());
        return this.baseMapper.countInhospitalByMap(params);
    }

    @Override
    public List<Map<String, Object>> findInhospitalByMap(Map<String, Object> params) {
    	params.put("inhospitalDictIds", bdtdProperties.getInhospitalDictIds());
        return this.baseMapper.findInhospitalByMap(params);
    }

    @Override
    public CylinderShapeEntity<Long> inhospitalChart(Map<String, Object> param) {
    	param.put("inhospitalDictIds", bdtdProperties.getInhospitalDictIds());
        List<ChartEntity<Long>> entitys = this.baseMapper.inhospitalChart(param);
        String title = "住院统计图";
        String subTitle = "66194部队就诊系统";
        List<String> legend = Arrays.asList("本院", "外院");
        List<String> xAxisData = new ArrayList<>();
        List<SeriesEntity<Long>> series = new ArrayList<>();
        List<Long> innerData = new ArrayList<>();
        SeriesEntity<Long> innerEntity = new SeriesEntity<>("本院", innerData);
        series.add(innerEntity);
        List<Long> outerData = new ArrayList<>();
        SeriesEntity<Long> outerEntity = new SeriesEntity<>("外院", outerData);
        series.add(outerEntity);
        CylinderShapeEntity<Long> cylinderShapeEntity = new CylinderShapeEntity<>(title, subTitle, legend, xAxisData, series);
        if (entitys != null && entitys.size() > 0) {
            Map<Long, List<ChartEntity<Long>>> map = entitys.stream().collect(Collectors.groupingBy(ChartEntity::getOrgId));
            map.values().stream().sorted((a, b) -> {
                int count1 = 0;
                int count2 = 0;
                if (a.get(0).getStatus().equals("本院")) {
                    count1 = a.get(0).getCount().intValue();
                } else {
                    count1 = 0;
                }
                if (b.get(0).getStatus().equals("本院")) {
                    count2 = a.get(0).getCount().intValue();
                } else {
                    count2 = 0;
                }
                return count1 - count2;
            }).forEach((chartEntities) -> {
                xAxisData.add(chartEntities.get(0).getOrgName());
                if (chartEntities.size() == 2) {
                    innerData.add(chartEntities.get(0).getCount());
                    outerData.add(chartEntities.get(1).getCount());
                } else {
                    if (chartEntities.get(0).getStatus().equals("本院")) {
                        innerData.add(chartEntities.get(0).getCount());
                        outerData.add(0L);
                    } else {
                        innerData.add(0L);
                        outerData.add(chartEntities.get(0).getCount());
                    }
                }
            });
        }
        return cylinderShapeEntity;
    }

    @Override
    public Map<String, Object> monthlyWoundChart(String beginDate, String endDate) {
        List<Long> inhospitalList = this.baseMapper.monthlyWoundChart(MapUtil.createMap("beginDate", beginDate, "endDate", endDate, "inhospitalDictIds", bdtdProperties.getInhospitalDictIds()));
        List<Long> patientInfoList = this.patientInfoMapper.monthlyWoundChart(MapUtil.createMap("beginDate", beginDate, "endDate", endDate));
        
        int begin = Integer.valueOf(beginDate.split("-")[1]);
        int end = Integer.valueOf(endDate.split("-")[1]);
        
        List<String> xAxisData = new ArrayList<>(12);
        for (int i = begin; i < 13; i++) {
            xAxisData.add(i + "月");
        }
        for (int i = 1; i <= end; i++) {
            xAxisData.add(i + "月");
        }
        
        int len = inhospitalList.size();
        for (int i = 0; i < 13 - begin + end - len; i++) {
            inhospitalList.add(0, 0L);
        }
        len = patientInfoList.size();
        for (int i = 0; i < 13 - begin + end - len; i++) {
            patientInfoList.add(0, 0L);
        }
        return MapUtil.createMap("inHospitalData", inhospitalList, "xAxisData", xAxisData, "patientInfoData", patientInfoList);
    }

    @Override
    public Map<String, Object> woundAbsentChart() {
        long total = dtUserDao.total();
        long inHospitalCount = this.baseMapper.countByMap(MapUtil.createMap("status", EnumInHospitalStatus.IN_HOSPITAL.getType()));
        long inHospitalOtherCount = transferTreatmentMapper.woundAbsentChart(MapUtil.createMap("inhospitalDictIds", bdtdProperties.getInhospitalDictIds()));
        long fullRestCount = this.sickRestMapper.woundAbsentChart(MapUtil.createMap("sickRestType", EnumSickRestType.WHOLE_REST.getType()));
        long falfRestCount = this.sickRestMapper.woundAbsentChart(MapUtil.createMap("sickRestType", EnumSickRestType.HALF_REST.getType()));
        return MapUtil.createMap("inHospitalCount", inHospitalCount, "inHospitalOtherCount", inHospitalOtherCount, "fullRestCount", fullRestCount, "falfRestCount", falfRestCount, "total", total);
    }

}
