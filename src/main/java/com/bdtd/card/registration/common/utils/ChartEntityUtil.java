package com.bdtd.card.registration.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.bdtd.card.registration.common.model.chart.CylinderShapeEntity;
import com.bdtd.card.registration.common.model.chart.SeriesEntity;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.modular.system.model.ChartEntity;
import com.stylefeng.guns.scmmain.dao.IDtDepDao;

public class ChartEntityUtil {

    public static <T> CylinderShapeEntity<T> getChartEntity(String title, String subTitle, List<String> legend, List<ChartEntity<T>> entitys, IDtDepDao dtDepDao, String seriesName, String seriesType) {
        subTitle = StringUtil.isNullEmpty(subTitle) ? "66194部队就诊系统" : subTitle;
        List<String> xAxisData = new ArrayList<>();
        List<SeriesEntity<T>> series = new ArrayList<>();
        List<T> data = new ArrayList<>();
        SeriesEntity<T> innerEntity = StringUtil.isNullEmpty(seriesType) ? new SeriesEntity<>(seriesName, data) : new SeriesEntity<>(seriesName, seriesType, data);
        series.add(innerEntity);
        CylinderShapeEntity<T> cylinderShapeEntity = new CylinderShapeEntity<>(title, subTitle, legend, xAxisData, series);
        if (entitys != null && entitys.size() > 0) {
            List<String> orgNameList = null;
            if (entitys.get(0).getOrgName() == null && dtDepDao != null) {
                List<Long> orgIdList = entitys.stream().map((item) -> {
                    return item.getOrgId();
                }).collect(Collectors.toList());
                orgNameList = dtDepDao.findByIdIn(orgIdList);
            } else {
                orgNameList = entitys.stream().map((item) -> {
                   return item.getOrgName(); 
                }).collect(Collectors.toList());
            }
            
            xAxisData.addAll(orgNameList);
            data.addAll(entitys.stream().map((item) -> {
                return item.getCount();
            }).collect(Collectors.toList()));
        }
        return cylinderShapeEntity;
    }
}
