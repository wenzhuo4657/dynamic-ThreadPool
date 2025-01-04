package cn.wenzhuo4657.middr.tigger.job;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import java.net.URI;


/**
 * @author: wenzhuo4657
 * @date: 2025/1/4
 * @description:
 */

import java.io.IOException;
import java.util.concurrent.*;
import java.util.logging.Logger;
import java.util.logging.Level;

@ClientEndpoint
@Component
public class dynamicStackStateHandler {
    private static final Logger LOGGER = Logger.getLogger(dynamicStackStateHandler.class.getName());
    private static final String SERVER_URI = "ws://localhost:8080/threadframes";
    private Session session;
    private ScheduledExecutorService reconnectExecutor;
    private ThreadFrameMonitor frameMonitor;

    public dynamicStackStateHandler() {
        frameMonitor = new ThreadFrameMonitor();
        reconnectExecutor = Executors.newSingleThreadScheduledExecutor();
        dynamicStackStateHandler client = new dynamicStackStateHandler();
        client.connect();
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        LOGGER.info("WebSocket connection established");
        startThreadFrameMonitoring();
    }



    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        LOGGER.warning("WebSocket connection closed: " + closeReason);

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        LOGGER.log(Level.SEVERE, "WebSocket error", throwable);

    }

    private void startThreadFrameMonitoring() {
        // 启动本地线程帧监控
        frameMonitor.startMonitoring(this::sendThreadFrameUpdate);
    }

    private void sendThreadFrameUpdate(String frameData) {
        try {
            if (session != null && session.isOpen()) {
                session.getBasicRemote().sendText(frameData);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error sending thread frame update", e);
        }
    }




    public void connect() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, URI.create(SERVER_URI));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Initial connection failed", e);
        }
    }

    // 内部类：线程帧监控器
    private static class ThreadFrameMonitor {
        private ScheduledExecutorService executor;

        public void startMonitoring(FrameUpdateCallback callback) {
            executor = Executors.newSingleThreadScheduledExecutor();
            executor.scheduleAtFixedRate(() -> {
                // 模拟获取线程帧信息
                String frameData = captureThreadFrameData();
                callback.onFrameUpdate(frameData);
            }, 0, 1, TimeUnit.SECONDS);
        }

        private String captureThreadFrameData() {
            // 实际应用中，这里应该是获取真实的线程帧信息
            return "ThreadFrame: " + System.currentTimeMillis();
        }

        interface FrameUpdateCallback {
            void onFrameUpdate(String frameData);
        }
    }


}