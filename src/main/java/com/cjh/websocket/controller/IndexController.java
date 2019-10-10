package com.cjh.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author chen jia hao
 */
@Controller
public class IndexController {

    @RequestMapping(value = "toIndex")
    public ModelAndView toIndex(){
        return new ModelAndView("index");
    }

    /**
     * 后台向前端发送消息
     * 延伸定时任务
     * ...
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "test")
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MyWebSocket.sendMessage("10s后关闭服务器");
        System.out.println("kkk.....");
        try {
            response.getWriter().print("ok...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "test1")
    public void test1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MyWebSocket.sendMessage("单独通知");
    }
}
