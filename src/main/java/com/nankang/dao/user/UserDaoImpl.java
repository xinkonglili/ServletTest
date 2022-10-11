package com.nankang.dao.user;

import com.mysql.cj.util.StringUtils;
import com.nankang.dao.BaseDao;
import com.nankang.pojo.Unit;
import com.nankang.pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {

    //重写了dao层接口中的方法
    @Override
    public User getLoginUser(Connection connection, String userCode) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;

        if (connection != null) {
            //执行这条sql语句去数据库查
            String sql = "select * from user where usercode=?";
            Object[] params = {userCode};
            rs = BaseDao.execute(connection, sql, params, rs, pstm);
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("usercode"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUnitRole(rs.getString("unitRole"));
                user.setCreateTime(rs.getTimestamp("create_time"));
                user.setDepartment(rs.getString("department"));
            }
            BaseDao.closeResource(null, pstm, rs);
            System.out.println("--------time:" + user.getCreateTime());
        }
        return user;
    }

    @Override
    public User insertUserLogLogin(Connection connection, User user) throws SQLException {
        PreparedStatement pstm = null;
        if (connection != null) {
            String sql1 = " insert into user_log_login (usercode,create_time) values (?,NOW());";
            Object[] params = {user.getUserCode()};
            BaseDao.execute(connection, sql1, params, pstm);
            BaseDao.closeResource(null, pstm, null);
        }
        return user;
    }

    @Override
    public int getUserCount(Connection connection, String userName, int unitRole) throws SQLException {
        int count = 0;
        PreparedStatement pstm = null; //执行sql的对象
        ResultSet rs = null;
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select COUNT(1) as count from `user` us, `unit` un where un.`unitcode` = us.`unitrole`");
            ArrayList<Object> list = new ArrayList<Object>();//存放参数
            if (!StringUtils.isNullOrEmpty(userName)) { //如果用户名不为空
                sql.append(" and us.userName like ?");//sql语句的拼接
                list.add("%"+userName+"%"); //模糊查询,list默认下标为1
            }

            if (unitRole > 0) {
                sql.append(" and us.unitRole = ?");
                list.add(unitRole); //index = 1
            }

            //把list转换为数组
            Object[] params = list.toArray();
            System.out.println("UserDaoImpl--->getUserCount打印出：" + sql);
            rs = BaseDao.execute(connection, sql.toString(), params, rs, pstm);
            if(rs.next()){
                count=rs.getInt("count");
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return count;
    }

    @Override
    public List<User> getUserList(Connection connection, String userName, int unitRole, int currentPageNo, int pageSize) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<User>();
        if (connection != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select DISTINCT us.*, un.unitname as userRoleName from `user` us, `unit` un where un.`unitcode` = us.`unitrole`");
            List<Object> list = new ArrayList<Object>();
            if (!StringUtils.isNullOrEmpty(userName)) { //如果用户名不为空
                sql.append(" and us.userName like ?");//sql语句的拼接
                list.add("%"+userName+"%"); //模糊查询,list默认下标为1
            }

            if (unitRole > 0) {
                sql.append(" and us.unitRole = ?");
                list.add(unitRole); //index = 1
            }

            sql.append(" order by create_time desc limit ?,?");
            currentPageNo = (currentPageNo-1) * pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("sql------>"+ sql.toString());
            rs = BaseDao.execute(connection, sql.toString(),params,rs, pstm);
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("usercode"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUnitRole(rs.getString("unitRole"));
                user.setUnitName(rs.getString("unitName"));
                user.setCreateTime(rs.getTimestamp("create_time"));
                user.setDepartment(rs.getString("department"));
                userList.add(user);
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        System.out.println("userList------------"+userList);
        for (User user : userList){
           /* ����
            admin
            1*/
            System.out.println(user.getUserName());
            System.out.println(user.getUserCode());
            System.out.println(user.getUnitRole());
        }
        return userList ;
    }

    @Override
    public int add(Connection connection, User user) throws Exception {
        PreparedStatement pstm = null;
        int updateNum =0;
        if (connection!=null){
            String sql = "insert into user (usercode,username,password,create_time,unitrole,department)" +
                    "VALUES (?,?,?,?,?,?)";
            Object[] params ={user.getUserCode(),user.getUserName(),user.getPassword(),user.getCreateTime(),
                    user.getUnitRole(),user.getDepartment()};
            updateNum = BaseDao.execute(connection, sql, params, pstm);
            BaseDao.closeResource(null,pstm,null);
        }
        return updateNum;
    }


    //根据用户id删除该用户
    public int deleteUserById(Connection connection, Integer delId) throws Exception {
        PreparedStatement pstm=null;
        int deleteNum=0;
        if(connection!=null){
            String sql="DELETE FROM `user` WHERE id=?";
            Object[] params={delId};
            deleteNum=BaseDao.execute(connection,sql,params,pstm);
            BaseDao.closeResource(null,pstm,null);
        }
        return deleteNum;
    }

}

