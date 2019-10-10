package com.cjh.websocket.controller;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * websocket服务端
 * @author chen jia hao
 */
@Component
@ServerEndpoint(value = "/myWebSocket") //可以路径传参
public class MyWebSocket {


    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    // 若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    //用来存放每个客户端对应的MyWebSocket对象
    static CopyOnWriteArraySet<MyWebSocket> user = new CopyOnWriteArraySet<MyWebSocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    @OnMessage
    public void onMessage(String message,Session session) throws IOException {

        //群发消息
        for (MyWebSocket myWebSocket : user) {
            myWebSocket.session.getBasicRemote().sendText(session.getId()+"说："+message);
            //myWebSocket.session.getBasicRemote().sendText("<img src=''/>");
        }
    }

    @OnOpen
    public void onOpen(@PathParam(value = "userno") String param, Session session, EndpointConfig config){
        System.out.println(session.getId()+" open...");
        this.session = session;
        user.add(this);
    }
    @OnClose
    public void onClose(){
        System.out.println(this.session.getId()+" close...");
        user.remove(this);
    }

    @OnError
    public void onError(Session session,Throwable error){
        System.out.println(this.session.getId()+" error...");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public static void sendMessage(String message) throws IOException{
//        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
        //群发消息
        for (MyWebSocket myWebSocket : user) {
            myWebSocket.session.getBasicRemote().sendText("xt说："+message);
            //myWebSocket.session.getBasicRemote().sendText("<img src=''/>");
        }
    }
    public static void sendOne(String message) throws IOException{
    }
}
