package com.bdtd.card.registration.modular.alert.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.bdtd.card.registration.cache.AlertDepCache;
import com.bdtd.card.registration.modular.alert.service.IAlertDepService;
import com.stylefeng.guns.modular.system.dao.AlertDepMapper;
import com.stylefeng.guns.modular.system.model.AlertDep;

/**
 * <p>
 * 报警部门 服务实现类
 * </p>
 *
 * @author lilei123
 * @since 2018-10-29
 */
@Service
public class AlertDepServiceImpl extends ServiceImpl<AlertDepMapper, AlertDep> implements IAlertDepService {

    @Override
    public List<Map<String, Object>> select() {
        return AlertDepCache.getCache().stream().map((item) -> {
            Map<String, Object> map = new HashMap<>(2);
            map.put("id", item.getDepSerial());
            map.put("name", item.getDepName());
            return map;
        }).collect(Collectors.toList());
    }

}
