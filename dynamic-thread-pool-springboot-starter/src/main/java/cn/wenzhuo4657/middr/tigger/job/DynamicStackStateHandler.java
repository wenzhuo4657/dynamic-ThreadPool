package cn.wenzhuo4657.middr.tigger.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import javax.websocket.*;
import java.net.URI;


/**
 * @author: wenzhuo4657
 * @date: 2025/1/4
 * @description:
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

@ClientEndpoint
@Component

public class DynamicStackStateHandler  extends AbstractWebSocketHandler {
    private static final Logger log= LoggerFactory.getLogger(DynamicStackStateHandler.class);

    private static final Map<String , SessionBean> sessionBeanMap;//web层的连接
    private static AtomicInteger clientIdMaker;

    static {
        sessionBeanMap=new HashMap<>();
        clientIdMaker=new AtomicInteger(1);
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        SessionBean sessionBean=new SessionBean(session,clientIdMaker.getAndIncrement());
        sessionBeanMap.put(session.getId(),sessionBean);
        log.info(sessionBeanMap.get(session.getId()).getClientId()+"建立了连接");

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
  //  wenzhuo TODO 2025/1/8 : 升级连接为展示层，非展示层连接都会将线程帧信息发送给展示层连接
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }

    public void sendMessage(){
        for(String key:sessionBeanMap.keySet()){
            try {
                sessionBeanMap.get(key).getWebSocketSession().sendMessage(new TextMessage(""));
            } catch (IOException e) {
//                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
    }
}