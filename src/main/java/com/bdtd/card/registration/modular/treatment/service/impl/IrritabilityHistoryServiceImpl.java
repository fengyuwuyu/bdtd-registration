package com.bdtd.card.registration.modular.treatment.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.bdtd.card.registration.modular.treatment.service.IIrritabilityHistoryService;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.modular.system.dao.IrritabilityHistoryMapper;
import com.stylefeng.guns.modular.system.model.IrritabilityHistory;

/**
 * <p>
 * 过敏史 服务实现类
 * </p>
 *
 * @author lilei123
 * @since 2018-08-03
 */
@Service
public class IrritabilityHistoryServiceImpl extends ServiceImpl<IrritabilityHistoryMapper, IrritabilityHistory> implements IIrritabilityHistoryService {

    @Override
    public void saveOrUpdate(String userNo, String irritabilityHistory) {
        if (!StringUtil.isNullEmpty(irritabilityHistory) && !StringUtil.isNullEmpty(userNo)) {
            IrritabilityHistory entity = new IrritabilityHistory();
            entity.setUserNo(userNo);
            entity.setIrritabilityHistory(irritabilityHistory);
            Wrapper<IrritabilityHistory> wrapper = new EntityWrapper<>();
            wrapper.eq("user_no", userNo);
            if (selectCount(wrapper) == 0) {
                this.baseMapper.save(entity);
            } else {
                this.update(entity, wrapper);
            }
        }
    }

}
