package com.nankang.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.nankang.pojo.ResultUnit;
import com.nankang.pojo.Unit;
import com.nankang.pojo.UnitPOVO;
import com.nankang.pojo.User;
import com.nankang.service.user.UserService;
import com.nankang.service.user.UserServiceImpl;
import com.nankang.util.Constants;
import com.nankang.util.PageSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class UnitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method != null && method.equals("queryUnit")) {
            this.queryUnit(req, resp);
        }else if (method!=null&&method.equals("addUnit")){

            try {
                this.addUnit(req, resp);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }  else if (method != null && method.equals("viewUnit")) {
            //通过用户id得到用户
            try {
                this.getUnitById(req,resp,"unitview.jsp");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else if (method != null && method.equals("delUnit")) {
            this.delUnitUser(req, resp);
        }else if(method != null && method.equals("unitmodifyexe")){
            //验证用户
            try {
                this.unitModify(req, resp);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    //通过id得到用户信息
    private void getUnitById(HttpServletRequest req, HttpServletResponse resp, String url) throws Exception {
        String  unitCode = req.getParameter("unitCode");
        if (!StringUtils.isNullOrEmpty(unitCode)){
            //调用后台方法得到unit对象
            UserServiceImpl userService = new UserServiceImpl();
            ResultUnit unit = userService.getUnitById(unitCode);
            req.setAttribute("unit",unit);
            req.getRequestDispatcher(url).forward(req,resp);
        }
    }


    private void unitModify(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //需要拿到前端传进来的参数
        String unitid = req.getParameter("unitid");
        String unitName = req.getParameter("unitName");
        String unitCode = req.getParameter("unitCode");
        String fax = req.getParameter("fax");
        ResultUnit unit = new ResultUnit();
        unit.setUnitName(unitName);
        unit.setFax(fax);
        unit.setUnitCode(Integer.valueOf(unitid));
        unit.setUnitCode(Integer.valueOf(unitCode));
        //调用service层
        UserServiceImpl userService = new UserServiceImpl();
        Boolean flag = userService.unitModify(unit);

        //判断是否修改成功来决定跳转到哪个页面
        if (flag){
            resp.sendRedirect(req.getContextPath()+"/jsp/unit.do?method=query");
        }else {
            req.getRequestDispatcher("unitmodify.jsp").forward(req,resp);
        }
    }


    //重点难点
    public void queryUnit(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        //查询部门列表
        //从前端获取数据
        String queryUnitName = req.getParameter("queryunitname");
        String temp = req.getParameter("queryUnitRole");
        String pageIndex = req.getParameter("pageIndex");
        int queryUnitRole = 0;
        //获取部门列表
        UserServiceImpl newUnitService = new UserServiceImpl();
        List<ResultUnit> newUnitList = null;

        //第一次走这个请求一定是第一页，页面大小是固定的
        int pageSize = 5; //写到配置文件中
        int currentPageNo = 1;
        if (queryUnitName == null){
            queryUnitName = "";
        }

        if (temp!=null && !temp.equals((""))){
            queryUnitRole = Integer.parseInt(temp);//将字符串数字temp，转成正常数字
        }

        if (pageIndex != null){
            //解析页面
            currentPageNo = Integer.parseInt(pageIndex);
        }

        //获取用户的总数
        int totalCount = newUnitService.getUserCount(queryUnitName, queryUnitRole);
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
        //单位查询：获取单位列表展示
        newUnitList = newUnitService.getUnitList(queryUnitName, queryUnitRole, currentPageNo, pageSize);
     /*   for (ResultUnit nu : newUnitList) {
            System.out.println("newUnitlist--getAddress-->"+ nu.getAddress());
            System.out.println("newUnitlist---getAddress->"+ nu.getUnitName());
            System.out.println("newUnitlist---getAddress->"+ nu.getDepartmentName());
            System.out.println("newUnitlist--getUnitCode-->"+ nu.getUnitCode());
        }*/
        req.setAttribute("newUnitList", newUnitList); //通过前端页面的foreach去遍历的
        req.setAttribute("currentPageNo",currentPageNo);
        req.setAttribute("totalCount",totalCount);
        req.setAttribute("currentPageNo",currentPageNo);
        req.setAttribute("totalPageCount",totalPageCount);

        //返回给前端
        try {
            req.getRequestDispatcher("unitlist.jsp").forward(req,resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void addUnit(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        System.out.println("正在执行单位增加操作！");
        //从前端得到页面的请求的参数即用户的值
        String unitAddress = req.getParameter("unitAddress");
        String unitCode = req.getParameter("unitCode");
        String unitName = req.getParameter("unitName");
        String unitPhone= req.getParameter("unitPhone");
        String unitFax = req.getParameter("unitFax");
        String departmentId = req.getParameter("DepartmentId");
        String[] split1 = departmentId.split("-");
        System.out.println("///////////////////////");
        for (int i =0;i<split1.length;i++){
            System.out.println(split1[i]);
        }

        //把这些值塞进一个部门的属性中
        UnitPOVO unitpovo = new UnitPOVO();
        unitpovo.setUnitCode(Integer.valueOf(unitCode));
        unitpovo.setDepartmentId(Integer.valueOf(split1[0]));
        unitpovo.setDepartmentName(split1[1]);
        unitpovo.setFax(unitFax);
        unitpovo.setUnitName(unitName);
        unitpovo.setAddress(unitAddress);
        unitpovo.setPhone(unitPhone);

        //查找当前正在登陆的用户的id
       // unit.setCreatedBy(((Unit)req.getSession().getAttribute(Constants.USER_SESSION)).getId());

        UserServiceImpl userService = new UserServiceImpl();
            Boolean flag = userService.addUnit(unitpovo);
        if (flag){
            //response请求重定向:有2个请求，2个响应
                resp.sendRedirect(req.getContextPath()+"/jsp/unit.do?method=queryUnit");
            }else {
                //request:请求转发:整个过程只有一个请求，一个响应
                req.getRequestDispatcher("unitadd.jsp");
        }
    }


    private void delUnitUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String unitCode = req.getParameter("unitCode");
        Integer delUnitId = 0;
        try{
            delUnitId = Integer.parseInt(unitCode);
        }catch (Exception e) {
            // TODO: handle exception
            delUnitId = 0;
        }
        //需要判断是否能删除成功
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(delUnitId <= 0){
            resultMap.put("delResult", "notexist");
        }else{
            UserService userService = new UserServiceImpl();
            if(userService.deleteUnitById(delUnitId)){
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




}
