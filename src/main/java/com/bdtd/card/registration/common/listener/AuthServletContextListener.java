package com.bdtd.card.registration.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdtd.card.registration.common.utils.AuthManager;

@WebListener
public class AuthServletContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        AuthManager authManager = ac.getBean(AuthManager.class);
        authManager.doAuth();
//        AuthManager.getInstance().doAuth();
        
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }

}
