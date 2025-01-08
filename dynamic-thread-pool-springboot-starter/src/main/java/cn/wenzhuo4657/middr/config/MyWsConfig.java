package cn.wenzhuo4657.middr.config;

import cn.wenzhuo4657.middr.tigger.job.DynamicStackStateHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * @author: wenzhuo4657
 * @date: 2025/1/4
 * @description: webSocket路由注册
 */
@Configuration
@EnableWebSocket
public class MyWsConfig implements WebSocketConfigurer {
    @Resource
    DynamicStackStateHandler myWsHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWsHandler,"/myWs1").setAllowedOrigins("*");
    }
}