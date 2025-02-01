package cn.wenzhuo4657.middr.tigger.job;

import cn.wenzhuo4657.middr.registry.utils.ProtostuffUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import javax.websocket.ClientEndpoint;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


@ServerEndpoint(value = "/rs")
@Component
public class DynamicStackStateServer  extends AbstractWebSocketHandler {
    private static final Logger log= LoggerFactory.getLogger(DynamicStackStateServer.class);

//    private static final Map<String , SessionBean> sessionBeanMap;//web层的连接
    private static  SessionBean sessionBean;
    private static AtomicInteger clientIdMaker;

    static {
//        sessionBeanMap=new HashMap<>();
        clientIdMaker=new AtomicInteger(1);
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        Task.submit(new uploadStackInfo());

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        /**
         *  @author:wenzhuo4657
            des: 作为使用者只会发送二进制消息，一旦检测到有人发送了文本消息则认为其要接收线程帧信息。
        */
        sessionBean=new SessionBean(session,clientIdMaker.getAndIncrement());
//        sessionBeanMap.put(session.getId(),sessionBean);
        log.info(sessionBean.getClientId()+"请求接收线程帧信息.");
        uploadStackInfo uploadStackInfo = new uploadStackInfo();
        uploadStackInfo.run();
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }

    public void sendMessage( StackTraceElementEntity entity){



            try {
                sessionBean.getWebSocketSession().sendMessage(new BinaryMessage(entity.toString().getBytes()));
            } catch (IOException e) {
//                e.printStackTrace();
                log.error(e.getMessage());
            }

    }

    class StackTraceElementEntity{

        private String threadName;
        private List<String> stackTraceElements;
        private int stackTraceLength;
        public StackTraceElementEntity(String threadName, List<String> stackTraceElements) {
            this.threadName = threadName;
//            this.stackTraceElements = stackTraceElements;
            this.stackTraceLength=stackTraceElements.size();
        }

        public String getThreadName() {
            return threadName;
        }

        public List<String> getStackTraceElements() {
            return stackTraceElements;
        }

        public int getStackTraceLength() {
            return stackTraceLength;
        }

        @Override
        public String toString() {
            return
                    "threadName='" + threadName + '\'' +
                    ", stackTraceLength=" + stackTraceLength ;
        }
    }


    class  uploadStackInfo implements  Runnable{
        @Override
        public void run() {
            Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
            for (Thread thread: allStackTraces.keySet()){
                StackTraceElement[] stackTrace = allStackTraces.get(thread);//内部是快照数据，不会发生线程并发问题
                List<String> stackTraceElements = new ArrayList<>();

                // 提取栈帧的关键信息
                for (StackTraceElement element : stackTrace) {
                    String stackTraceElement = element.getClassName() + "." + element.getMethodName() +
                            " (" + element.getFileName() +  ")";
                    stackTraceElements.add(stackTraceElement);
                }

                // 创建栈信息实体
                StackTraceElementEntity entity = new StackTraceElementEntity(thread.getName(), stackTraceElements);

                sendMessage(entity);
            }
        }
    }


    static class  Task {
        private final static ScheduledThreadPoolExecutor executor;
        static{
            executor=new ScheduledThreadPoolExecutor(2);
        }
        protected static void submit(Runnable run){
            if (run!=null)
                executor.schedule(run,1, TimeUnit.SECONDS);
            else
                log.error("提交任务失败。。。。。");
        }


    }

}