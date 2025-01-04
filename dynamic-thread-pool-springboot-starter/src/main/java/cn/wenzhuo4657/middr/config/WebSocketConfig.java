package cn.wenzhuo4657.middr.config;

import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author: wenzhuo4657
 * @date: 2025/1/4
 * @description: webSocket路由注册
 */
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

    }
}
