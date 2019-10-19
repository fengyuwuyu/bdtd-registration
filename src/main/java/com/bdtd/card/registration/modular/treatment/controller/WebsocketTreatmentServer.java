package com.bdtd.card.registration.modular.treatment.controller;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.stylefeng.guns.core.log.LogManager;
import com.stylefeng.guns.core.log.factory.LogTaskFactory;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.JsonUtil;
import com.stylefeng.guns.core.util.MapUtil;

@Controller
@ServerEndpoint("/treatment")
public class WebsocketTreatmentServer {
    
    private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>(16);
    private Logger log = LoggerFactory.getLogger(getClass());
    
    @OnOpen  
    public void onOpen(Session session) {  
        sessionMap.put(session.getId(), session);
    }
    
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info(message);
        log.info("当前打开连接数： " + sessionMap.size());
    }
    
    @OnClose
    public void onClose(Session session) {
        sessionMap.remove(session.getId());
        log.info("session关闭了");
    }
    
    @OnError  
    public void onError(Session session, Throwable error) {  
        sessionMap.remove(session.getId());
        log.error("发生错误， error = " + error.getMessage() + ", 移除session");
        error.printStackTrace();  
    } 
    
    public void sendMessage(Integer outpatient, Integer status) {  
        for (Map.Entry<String, Session> entry : sessionMap.entrySet()) {
            try {
                entry.getValue().getBasicRemote().sendText(JsonUtil.objToJson(MapUtil.createMap("outpatient", outpatient, "status", status)));
            } catch (IOException e) {
                log.error("WebSocket发送数据失败， user = " + entry.getValue().getUserPrincipal().toString());
                LogManager.me().executeLog(LogTaskFactory.exceptionLog(ShiroKit.getUser().getId(), e));
            }
        }
    }  
}
