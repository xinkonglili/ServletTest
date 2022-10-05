package com.nankang.servlet.user;

import com.nankang.pojo.User;
import com.nankang.service.user.UserService;
import com.nankang.service.user.UserServiceImpl;
import com.nankang.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LoginServlet start-----");
        //获取浏览器中的用户名和密码
        String userCode = req.getParameter("userCode");
        String userName = req.getParameter("userPassword");
        //用户输入的验证码
        String code = req.getParameter("userIdCode");//把验证码文本框里面的内容获取到
        HttpSession session = req.getSession();
        //强转，默认是object，拿到图片上的验证码
        String vcode = (String) session.getAttribute("userIdCode");
        //和数据库里面的密码进行比对，调用业务层
        UserService userService = new UserServiceImpl();
        User user = null;
        try {
            user = userService.login(userCode, userName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("LoginServlet----"+userCode);
        System.out.println("LoginServlet----"+userName);
        if (null!=user&& code.equalsIgnoreCase(vcode)){
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html;charset=utf-8");
            String lastAccessTime=null;
            Cookie[]cookies=req.getCookies();
            // 遍历Cookie数组，取出上次访问时间
            for(int i=0;cookies!=null&&i<cookies.length;i++){
                if("lastAccess".equals(cookies[i].getName())){
                    lastAccessTime= URLDecoder.decode(cookies[i].getValue());
                    break;
                }
            }
            // 用户第一次请求时，lastAccessTime为null
            // 非第一次请求时，lastAccessTime不为null
            if(lastAccessTime==null){
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy日MM年dd月 HH:mm:ss");
                resp.getWriter().print("你是首次访问本站！"+ sdf.format(date));
            }else {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy日MM年dd月 HH:mm:ss");
                resp.getWriter().print( "您现在的访问时间是："+sdf.format(date)+"   上次的访问时间是："+lastAccessTime);
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
            resp.addCookie(cookie);

            //将用户信息放到session中
            req.getSession().setAttribute(Constants.USER_SESSION,user);
            //跳转到主页重定向
            resp.sendRedirect("jsp/frame.jsp");

        }else{ //查无此人，无法登录
            //跳转回登录页面，然后提示用户密码或者用户名错误
            //前端页面里面的div中有error
             req.setAttribute("error","用户密码、用户名或验证码错误");
             req.getRequestDispatcher("login.jsp").forward(req,resp);

        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
