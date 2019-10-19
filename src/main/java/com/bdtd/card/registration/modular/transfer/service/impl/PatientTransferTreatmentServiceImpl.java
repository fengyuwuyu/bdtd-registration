package com.bdtd.card.registration.modular.transfer.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.bdtd.card.registration.common.model.chart.CylinderShapeEntity;
import com.bdtd.card.registration.common.utils.ChartEntityUtil;
import com.bdtd.card.registration.modular.transfer.service.IPatientTransferTreatmentService;
import com.stylefeng.guns.modular.system.dao.PatientTransferTreatmentMapper;
import com.stylefeng.guns.modular.system.model.ChartEntity;
import com.stylefeng.guns.modular.system.model.PatientTransferTreatment;
import com.stylefeng.guns.scmmain.dao.IDtDepDao;

/**
 * <p>
 * 转诊 服务实现类
 * </p>
 *
 * @author lilei123
 * @since 2018-06-26
 */
@Service
public class PatientTransferTreatmentServiceImpl extends ServiceImpl<PatientTransferTreatmentMapper, PatientTransferTreatment> implements IPatientTransferTreatmentService {

    @Autowired
    private IDtDepDao dtDepDao;

    @Override
    public List<Map<String, Object>> findByMap(Map<String, Object> params) {
        return this.baseMapper.findByMap(params);
    }

    @Override
    public long countByMap(Map<String, Object> params) {
        return this.baseMapper.countByMap(params);
    }

    @Override
    public int updateByMap(Map<String, Object> params) {
        params.put("updateDate", new Date());
        return this.baseMapper.updateByMap(params);
    }

    @Override
    public int toPreTransferStatus(Map<String, Object> params) {
        params.put("updateDate", new Date());
        return this.baseMapper.toPreTransferStatus(params);
    }

    @Override
    public CylinderShapeEntity<Integer> transferChart(Map<String, Object> param) {
        List<ChartEntity<Integer>> entitys = this.baseMapper.transferChart(param);
        String title = "转诊统计图";
        String subTitle = "66194部队就诊系统";
        List<String> legend = Arrays.asList("人数");
        String seriesName = "转诊";
        return ChartEntityUtil.getChartEntity(title, subTitle, legend, entitys, dtDepDao, seriesName, null);
    }

    @Override
    public int countByPatientInfoId(Map<String, Object> param) {
        return this.baseMapper.countByPatientInfoId(param);
    }

}
