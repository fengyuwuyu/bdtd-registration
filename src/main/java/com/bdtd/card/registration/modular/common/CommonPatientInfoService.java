package com.bdtd.card.registration.modular.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdtd.card.registration.modular.treatment.service.IIrritabilityHistoryService;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.modular.system.model.IrritabilityHistory;

@Service
public class CommonPatientInfoService {
    
    @Autowired
    private IIrritabilityHistoryService irritabilityHistoryService;

    public void replaceIrritabilityHistory(List<Map<String, Object>> rows) {
        if (rows == null || rows.size() == 0) {
            return;
        }
        
        List<String> userNoList = rows.stream().map((item) -> item.get("userNo").toString()).collect(Collectors.toList());
        List<IrritabilityHistory> irritabilityHistories = this.irritabilityHistoryService.selectBatchIds(userNoList);
        if (irritabilityHistories != null && irritabilityHistories.size() > 0) {
            Map<String, String> irritabilityHistoryMap = new HashMap<>(irritabilityHistories.size());
            irritabilityHistories.stream().forEach((item) -> {
                irritabilityHistoryMap.put(item.getUserNo(), item.getIrritabilityHistory());
            });
            
            rows.stream().forEach((item) -> {
                String irritability = irritabilityHistoryMap.get(item.get("userNo"));
                if (!StringUtil.isNullEmpty(irritability)) {
                    item.put("irritabilityHistory", irritability);
                }
            });
        }
    }
    
}
