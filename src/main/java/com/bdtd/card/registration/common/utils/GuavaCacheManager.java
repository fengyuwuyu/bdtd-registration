package com.bdtd.card.registration.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;

import com.stylefeng.guns.scmmain.model.DtUser;
import com.stylefeng.guns.scmmain.service.IDtUserService;

@Controller
public class GuavaCacheManager {
    
    @Autowired
    private IDtUserService dtUserService;
    
    @Cacheable(value="PERSONAL_EXPIRED", key="#userCard")
    public DtUser registration(String userCard) {
        return this.dtUserService.findByUserNo(userCard);
    }
}
