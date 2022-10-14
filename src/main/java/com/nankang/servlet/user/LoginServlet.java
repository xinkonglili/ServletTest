package com.nankang.servlet.user;

import com.nankang.pojo.User;
import com.nankang.pojo.UserLogLogin;
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
        try {
            UserLogLogin userLoginTime = userService.getUserLoginTime(userName);
            System.out.println("获取用户的上次登录时间userLoginTime--->"+ userLoginTime);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (null!=user&& code.equalsIgnoreCase(vcode)){ ///查有此人，可以登录
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html;charset=utf-8");
            //将用户信息放到session中
            req.getSession().setAttribute(Constants.USER_SESSION,user);
//            HttpSession session1 = req.getSession();
            /*session1.setAttribute(Constants.USER_SESSION,user);
            User userrr = (User) session1.getAttribute(Constants.USER_SESSION);
           *//* System.out.println("==========="+userrr.getUserCode() );
            System.out.println("77577777777845====="+user.getUserCode());*/

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
