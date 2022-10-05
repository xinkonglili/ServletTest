package com.nankang.servlet.user;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginTime extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置字符编码方式
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String lastAccessTime=null;
        Cookie[]cookies=request.getCookies();
        // 遍历Cookie数组，取出上次访问时间
        for(int i=0;cookies!=null&&i<cookies.length;i++){
            if("lastAccess".equals(cookies[i].getName())){
                lastAccessTime=URLDecoder.decode(cookies[i].getValue());
                break;
            }
        }
        // 用户第一次请求时，lastAccessTime为null
        // 非第一次请求时，lastAccessTime不为null
        if(lastAccessTime==null){
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy日MM年dd月 HH:mm:ss");
            response.getWriter().print("你是首次访问本站！"+ sdf.format(date));
        }else {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy日MM年dd月 HH:mm:ss");
            response.getWriter().print( "您现在的访问时间是："+sdf.format(date)+"   上次的访问时间是："+lastAccessTime);
        }
        // 每次进入都需要将当前时间更新进Cookie，覆盖原来记录的时间
        // 设置过期时间 10天
        /**
         * 关于设置Cookie的value时报错：Cookie值中存在无效字符 的问题
         * 此处应在设置时统一编码和解码方式：
         * Cookie cookie=new Cookie("lastAccess", URLEncoder.encode(currentTime));
         * lastAccessTime=URLDecoder.decode(cookies[i].getValue());
         */
        String currentTime=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        Cookie cookie=new Cookie("lastAccess", URLEncoder.encode(currentTime));
        cookie.setMaxAge(10*24*60*60);
        response.addCookie(cookie);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
