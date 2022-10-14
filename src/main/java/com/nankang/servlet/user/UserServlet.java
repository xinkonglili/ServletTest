package com.nankang.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.nankang.pojo.Department;
import com.nankang.pojo.Unit;
import com.nankang.pojo.User;
import com.nankang.service.role.RoleService;
import com.nankang.service.role.RoleServiceImpl;
import com.nankang.service.user.UserService;
import com.nankang.service.user.UserServiceImpl;
import com.nankang.util.Constants;
import com.nankang.util.PageSupport;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");//method前端页面传过来的
        System.out.println("method---------------> " + method);
        if (method!=null&&method.equals("add")){

            try {
                this.add(req, resp);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else if (method != null && method.equals("query")){
            this.query(req, resp);
        } else if (method != null && method.equals("deluser")) {
            this.delUser(req, resp);
        } else if (method != null && method.equals("view")) {
            //通过用户id得到用户
            this.getUserById(req,resp,"userview.jsp");
        }else if(method != null && method.equals("modify")){
            //通过用户id得到用户
            this.getUserById(req, resp,"usermodify.jsp");
        }else if(method != null && method.equals("modifyexe")){
            //验证用户
            this.modify(req, resp);
        } else if (method != null && method.equals("ucexist")) {
            //查询当前用户编码是否存在
            this.userCodeExist(req, resp);
        }
        else if (method != null && method.equals("getunitlist")) {
            //查询当前用户编码是否存在
            this.getUnitList(req, resp);
        } else if (method != null && method.equals("getdepartmentlist")) {
            //查询当前用户编码是否存在
            this.getDepartmentList(req, resp);
        }
    }

    private void modify(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        //需要拿到前端传进来的参数
        String id = req.getParameter("uid");
        String userName = req.getParameter("userName");
        String userCode = req.getParameter("userCode");
        String department = req.getParameter("department");
        String unitRole = req.getParameter("unitRole");

        //创建一个user来接收这些参数
        User user = new User();
        user.setId(Integer.valueOf(id));
        user.setUserName(userName);
        user.setUserCode(userCode);
        user.setUnitRole(unitRole);
        user.setDepartment(department);

        //调用service层
        UserServiceImpl userService = new UserServiceImpl();
        Boolean flag = userService.modify(user);

        //判断是否修改成功来决定跳转到哪个页面
        if (flag){
            resp.sendRedirect(req.getContextPath()+"/jsp/user.do?method=query");
        }else {
            req.getRequestDispatcher("usermodify.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    //重点难点
    public void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        //查询用户列表
        //从前端获取数据
        String queryUserName = req.getParameter("queryname");
        String temp = req.getParameter("queryUserRole");
        String temp1 = req.getParameter("queryUserDepartment");
        String pageIndex = req.getParameter("pageIndex");
        int queryUserRole=0;
        int queryUserDepartment=0;
        //获取用户列表
        UserServiceImpl userService = new UserServiceImpl();
        List<User> userList = null;
        List<User> userListDepartment = null;

        //第一次走这个请求一定是第一页，页面大小是固定的
        int pageSize = 5; //写到配置文件中
        int currentPageNo = 1;

        //输出控制台，显示参数的当前值
        System.out.println("queryUserName servlet--------"+queryUserName);
        System.out.println("queryUserRole servlet--------"+queryUserRole);
        System.out.println("queryUserDepartment servlet--------"+queryUserDepartment);
        System.out.println("query pageIndex--------- > " + pageIndex);

        if (queryUserName == null){
            queryUserName = "";
        }
        if (temp!=null && !temp.equals((""))){
            queryUserRole = Integer.parseInt(temp);//将字符串数字temp，转成正常数字
        }
        if (temp1!=null && !temp1.equals((""))){
            queryUserDepartment = Integer.parseInt(temp1);//将字符串数字temp1，转成正常数字
        }

        if (pageIndex != null){
            //解析页面
            currentPageNo = Integer.parseInt(pageIndex);
        }

        //获取用户的总数
        int totalCount = userService.getUserCount(queryUserName, queryUserRole);
        //总也页数的支持
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);
        int totalPageCount = pageSupport.getTotalCount();//totalPageCount:网页总的页数

        //控制首页和尾页
        if (currentPageNo<1){
            currentPageNo = 1;
        } else if (currentPageNo>totalPageCount) { //当前页面大于最后一页
            currentPageNo = totalPageCount;
        }

        //单位查询：获取用户列表展示
        userList = userService.getUserList(queryUserName,queryUserRole,currentPageNo,pageSize);
        //单位查询：获取用户列表展示
        userListDepartment = userService.getUserList(queryUserName,queryUserDepartment,currentPageNo,pageSize);

        req.setAttribute("userList", userList); //通过前端页面的foreach去遍历的
        req.setAttribute("userListDepartment", userListDepartment);
        //查找单位角色列表
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Unit> roleList = roleService.getRoleList();//拿到所有的角色

        //查找部门角色列表
        RoleServiceImpl roleDepartmentService = new RoleServiceImpl();
        List<Department> departmentList = roleDepartmentService.getDepartmentList();//拿到所有的角色


        /*Set set = new HashSet();
        set.addAll(roleList);*/
        req.setAttribute("userList",userList);
        req.setAttribute("roleList",roleList);
        req.setAttribute("userListDepartment",userListDepartment);
        req.setAttribute("roleDepartmentList",departmentList);

        req.setAttribute("totalCount",totalCount);
        req.setAttribute("currentPageNo",currentPageNo);
        req.setAttribute("totalPageCount",totalPageCount);

        //返回给前端
        try {
            req.getRequestDispatcher("userlist.jsp").forward(req,resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void add(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        System.out.println("正在执行用户增加操作！");
        //从前端得到页面的请求的参数即用户的值
        String userCode = req.getParameter("userCode");
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String unitRole = req.getParameter("unitRole");
        String department = req.getParameter("department");
        String createTime = req.getParameter("createTime");
        //把这些值塞进一个用户的属性中
        User user = new User();
        user.setUserCode(userCode);
        user.setUserName(userName);
        user.setPassword(password);
        user.setUnitRole(unitRole);
        user.setDepartment(department);
        user.setCreateTime(new Date());
        //查找当前正在登陆的用户的id
        user.setCreatedBy(((User)req.getSession().getAttribute(Constants.USER_SESSION)).getId());
        System.out.println("查找当前正在登陆的用户的id----->"+user.getCreatedBy());
        UserServiceImpl userService = new UserServiceImpl();
        Boolean flag = userService.add(user);
        if (flag){
            //response请求重定向:有2个请求，2个响应
            resp.sendRedirect(req.getContextPath()+"/jsp/user.do?method=query");
        }else {
            //request:请求转发:整个过程只有一个请求，一个响应
            req.getRequestDispatcher("useradd.jsp");
        }
    }


    private void delUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String id = req.getParameter("uid");
        Integer delId = 0;
        try{
            delId = Integer.parseInt(id);
        }catch (Exception e) {
            // TODO: handle exception
            delId = 0;
        }
        //需要判断是否能删除成功
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(delId <= 0){
            resultMap.put("delResult", "notexist");
        }else{
            UserService userService = new UserServiceImpl();
            if(userService.deleteUserById(delId)){
                resultMap.put("delResult", "true");
            }else{
                resultMap.put("delResult", "false");
            }
        }

        //把resultMap转换成json对象输出
        resp.setContentType("application/json");
        PrintWriter outPrintWriter = resp.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    //通过id得到用户信息
    private void getUserById(HttpServletRequest req, HttpServletResponse resp, String url) throws ServletException,IOException{
        String  id = req.getParameter("uid");
        if (!StringUtils.isNullOrEmpty(id)){
            //调用后台方法得到user对象
            UserServiceImpl userService = new UserServiceImpl();
            User user = userService.getUserById(id);
            req.setAttribute("user",user);
            req.getRequestDispatcher(url).forward(req,resp);
        }
    }

    //判断当前输入用户编码是否可用，即是否与已经存在的编码发生冲突
    private void userCodeExist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //先拿到用户的编码
        //http://localhost:8887/ServletTest_war_exploded/jsp/user.do?method=query
        //http://localhost:8887/ServletTest_war_exploded/jsp/user.do?method=query
        String userCode = req.getParameter("userCode");
        //用一个hashmap，暂存现在所有现存的用户编码
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(userCode)){
            //userCode == null || userCode.equals("")
            //如果输入的这个编码为空或者不存在，说明可用
            resultMap.put("userCode", "exist");
        }else{//如果输入的编码不为空，则需要去找一下是否存在这个用户
            UserService userService = new UserServiceImpl();
            User user = userService.selectUserCodeExist(userCode);
            if(null != user){
                resultMap.put("userCode","exist");
            }else{
                resultMap.put("userCode", "notexist");
            }
        }
        //把resultMap转为json字符串以json的形式输出
        //配置上下文的输出类型
        resp.setContentType("application/json");
        //从response对象中获取往外输出的writer对象
        PrintWriter outPrintWriter = resp.getWriter();
        //把resultMap转为json字符串 输出
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();//刷新
        outPrintWriter.close();//关闭流
    }


    //得到单位角色表
    private void getDepartmentList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        List<Unit> roleList = null;
        RoleService roleService = new RoleServiceImpl();
        roleList = roleService.getRoleList();
        //把roleList转换成json对象输出
        resp.setContentType("application/json");
        PrintWriter outPrintWriter = resp.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(roleList));
        outPrintWriter.flush();
        outPrintWriter.close();
    }


    //得到部门角色表
    private void getUnitList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        List<Unit> roleList = null;
        RoleService roleService = new RoleServiceImpl();
        roleList = roleService.getRoleList();
        //把roleList转换成json对象输出
        resp.setContentType("application/json");
        PrintWriter outPrintWriter = resp.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(roleList));
        outPrintWriter.flush();
        outPrintWriter.close();
    }



}
