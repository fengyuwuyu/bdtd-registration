package com.bdtd.card.registration.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdtd.card.registration.cache.AlertDepCache;
import com.bdtd.card.registration.schedule.TaskFactory;
import com.stylefeng.guns.modular.system.dao.AlertDepMapper;

@WebListener
public class MedicalContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		
		TaskFactory taskFactory = ac.getBean(TaskFactory.class);
		taskFactory.start();
		
		AlertDepMapper mapper = ac.getBean(AlertDepMapper.class);
        AlertDepCache.init(mapper);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
