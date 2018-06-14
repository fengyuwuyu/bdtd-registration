package com.bdtd.card.registration.test;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bdtd.card.registration.scmmain.dao.IDtUserDao;
import com.bdtd.card.registration.scmmain.model.DtUser;
import com.stylefeng.guns.core.util.MapUtil;

@Controller
public class TestController {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier("dtUserDaoImpl")
	private IDtUserDao userDao;
	
	@RequestMapping("userDaoTest")
	@ResponseBody
	public Map<String, Object> userDaoTest() {
		DtUser user = userDao.findByUserCard("4D080D24");
		log.debug(user.toString());
		return MapUtil.createSuccessMap();
	}

}
