package com.mychat.mychatserver.websocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import com.mychat.mychatserver.decoder.MessageDecoder;
import com.mychat.mychatserver.entity.Message;
import com.mychat.mychatserver.entity.MessageStatus;
import com.mychat.mychatserver.service.GroupConnectService;
import com.mychat.mychatserver.service.MessageService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat/{uid}",decoders = {MessageDecoder.class})
@Component
public class WebSocketServer {

    public static GroupConnectService groupConnectService;
    public static MessageService messageService;

    public static final Map<Integer,Session> sessions = new ConcurrentHashMap<Integer,Session>();
    @OnOpen
    public void onOpen(Session session, @PathParam("uid")Integer uid) {
        System.out.printf("新连接:uid(%d),session(%s)%n", uid, session.getId());
        sessions.put(uid, session);
        System.out.println("服务器人数：" + Integer.toString(sessions.size()));

        //发送未读消息
        List<Integer> messageIds = messageService.getMessageIds(uid,"unread");
        List<Message> messages = messageService.getMessage(messageIds);
        for(int i = 0; i < messages.size(); i++){
            try {
                session.getBasicRemote().sendText(messages.get(i).toJSON());
                MessageStatus tmp = new MessageStatus(uid,messageIds.get(i),"read");
                messageService.updateMessageStstus(tmp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClose
    public void onClose(Session session,@PathParam("uid")Integer uid) {
        System.out.printf("连接关闭:uid(%d),session(%s)%n", uid, session.getId());
        sessions.remove(uid);
        System.out.println("服务器人数：" + Integer.toString(sessions.size()));
    }

    @OnMessage
    public void onMessage(Message message, Session session,@PathParam("uid")Integer uid){
        System.out.println("Message received: " + message.toJSON());
        try {
            sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message) {
        //获取待发送用户的ID列表
        List<Integer> UsersID = new ArrayList<>();
        if(Objects.equals(message.getReceiverType(), "user")){
            UsersID.add(message.getToReceiver());
        }else {
            UsersID = groupConnectService.getAllUidByGroupId(message.getToReceiver());
        }

        System.out.println(UsersID);
        //存数据库
        message.setSentTime(LocalDateTime.now());
        Integer num = messageService.saveMessage(message);
        if(num!=1)//插入失败
            return;
        Integer megID = message.getMessageId();

        //发送消息
        for(Integer uid : UsersID) {
            if(Objects.equals(uid, message.getFromUserUid())){//不能自己发给自己
                continue;
            }
            //存用户消息状态表
            MessageStatus messageStatus = new MessageStatus();
            messageStatus.setUid(uid);
            messageStatus.setMessageId(megID);
            messageStatus.setStatus("unread");

            Session session = WebSocketServer.sessions.get(uid);
            if (session != null && session.isOpen()) {// 在线
                try {
                    session.getBasicRemote().sendText(message.toJSON());
                    messageStatus.setStatus("read");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {//不在线
                System.out.println("User with uid " + uid + " not found or session is closed.");
            }

            messageService.saveMessageStatus(messageStatus);//存状态
        }
    }

    public void hello(){
        System.out.println("hello");
    }
}
