package com.bdtd.card.registration.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stylefeng.guns.core.util.MapUtil;
import com.stylefeng.guns.modular.system.dao.IrritabilityHistoryMapper;
import com.stylefeng.guns.modular.system.model.IrritabilityHistory;
import com.stylefeng.guns.scmmain.dao.IDtDepDao;

@Controller
public class TestController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	IDtDepDao depDao; 
	
	@Autowired
	private IrritabilityHistoryMapper historyMapper;
	
	@RequestMapping("test")
	@ResponseBody
	public Map<String, Object> userDaoTest() {
        //	    Long orgId = 200005L;
//        Set<Long> orgIdList = new HashSet<>();
//        depDao.findRecursiveById(orgId, orgIdList);
	    IrritabilityHistory entity = new IrritabilityHistory();
	    entity.setUserNo("test");
	    entity.setIrritabilityHistory("测试");
	    historyMapper.save(entity);
		return MapUtil.createSuccessMap();
	}
	
}
