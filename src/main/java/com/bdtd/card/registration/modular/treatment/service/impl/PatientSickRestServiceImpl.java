package com.bdtd.card.registration.modular.treatment.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.bdtd.card.registration.common.model.chart.CylinderShapeEntity;
import com.bdtd.card.registration.common.utils.ChartEntityUtil;
import com.bdtd.card.registration.modular.treatment.service.IPatientSickRestService;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.modular.system.dao.PatientSickRestMapper;
import com.stylefeng.guns.modular.system.model.ChartEntity;
import com.stylefeng.guns.modular.system.model.PatientSickRest;
import com.stylefeng.guns.scmmain.dao.IDtDepDao;

/**
 * <p>
 * 病休管理 服务实现类
 * </p>
 *
 * @author lilei123
 * @since 2018-06-26
 */
@Service
public class PatientSickRestServiceImpl extends ServiceImpl<PatientSickRestMapper, PatientSickRest> implements IPatientSickRestService {

    @Autowired
    private IDtDepDao dtDepDao;

    @Override
    public long countByMap(Map<String, Object> params) {
        return this.baseMapper.countByMap(params);
    }

    @Override
    public List<Map<String, Object>> findByMap(Map<String, Object> params) {
        return this.baseMapper.findByMap(params);
    }

    @Override
    public CylinderShapeEntity<Integer> sickRestChart(Map<String, Object> param) {
        List<ChartEntity<Integer>> entitys = this.baseMapper.sickRestChart(param);
        String title = "病休统计图";
        String subTitle = "66194部队就诊系统";
        List<String> legend = Arrays.asList("人数");
        String seriesName = "病休";
        return ChartEntityUtil.getChartEntity(title, subTitle, legend, entitys, dtDepDao, seriesName, null);
    }

}
