package com.bdtd.card.registration.modular.inhospital.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.bdtd.card.registration.common.model.chart.CylinderShapeEntity;
import com.bdtd.card.registration.common.model.chart.MarkPoint;
import com.bdtd.card.registration.common.model.chart.MarkPointData;
import com.bdtd.card.registration.common.utils.ChartEntityUtil;
import com.bdtd.card.registration.modular.inhospital.service.IInHospitalTemperatureRecordService;
import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.modular.system.dao.InHospitalTemperatureRecordMapper;
import com.stylefeng.guns.modular.system.model.ChartEntity;
import com.stylefeng.guns.modular.system.model.InHospitalTemperatureRecord;

/**
 * <p>
 * 体温记录 服务实现类
 * </p>
 *
 * @author lilei123
 * @since 2018-07-08
 */
@Service
public class InHospitalTemperatureRecordServiceImpl extends ServiceImpl<InHospitalTemperatureRecordMapper, InHospitalTemperatureRecord> implements IInHospitalTemperatureRecordService {
    
    @Override
    public CylinderShapeEntity<Float> chartList(Integer inHospitalId) {
        Map<String, Object> params = MapUtil.createMap("inHospitalId", inHospitalId);
        List<ChartEntity<Float>> entitys = this.baseMapper.chartList(params);
//        entitys.forEach((item) -> {
//            String orgName = item.getOrgName();
//            String[] arr = orgName.split(" ");
//            if (!"2点".equals(arr[1])) {
//                item.setOrgName(arr[1]);
//            }
//        });
        
        String title = "体温记录表";
        String subTitle = "66194部队就诊系统";
        List<String> legend = Arrays.asList("时间");
        String seriesName = "体温";
        String seriesType = "line";
        CylinderShapeEntity<Float> shapeEntity = ChartEntityUtil.getChartEntity(title, subTitle, legend, entitys, null, seriesName, seriesType);
        MarkPoint markPoint = new MarkPoint(Arrays.asList(new MarkPointData("max", "最高体温"), new MarkPointData("min", "最低体温")));
        shapeEntity.getSeries().get(0).setMarkPoint(markPoint);
        return shapeEntity;
    }

}
