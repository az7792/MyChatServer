package com.mychat.mychatserver.config;

import com.mychat.mychatserver.websocket.WebSocketServer;
import com.mychat.mychatserver.service.GroupConnectService;
import com.mychat.mychatserver.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Component
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Autowired
    public void setGroupConnectService(GroupConnectService groupConnectService){
        WebSocketServer.groupConnectService = groupConnectService;
    }

    @Autowired
    public void setMessageService(MessageService messageService){
        WebSocketServer.messageService = messageService;
    }
}
