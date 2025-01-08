package cn.wenzhuo4657.middr.tigger.job;


import org.springframework.web.socket.WebSocketSession;

public class SessionBean {
    public SessionBean(WebSocketSession webSocketSession, Integer clientId) {
        this.webSocketSession = webSocketSession;
        this.clientId = clientId;
    }

    public SessionBean() {
    }

    private WebSocketSession webSocketSession;
    private  Integer clientId;

    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public void setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
}