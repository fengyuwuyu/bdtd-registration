package com.bdtd.card.registration.modular.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stylefeng.guns.core.util.DictCacheFactory;

@RequestMapping("/dictSelect")
@Controller
public class DictSelectController {
    
    @Autowired
    private DictCacheFactory dictCacheFactory;

    @RequestMapping("/select") 
    @ResponseBody
    public Object select(@RequestParam(required=true) Integer id) {
        return dictCacheFactory.getDictListByParentId(id);
    }
}
