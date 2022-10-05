package com.nankang.dao.user;

import com.nankang.dao.BaseDao;
import com.nankang.pojo.User;

import java.sql.*;

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
           // String sql1 = " update user set create_time = NOW() where usercode = params;";
            rs = BaseDao.execute(connection, sql,params, rs, pstm);
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("usercode"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUnit(rs.getString("unit"));
                user.setCreateTime(rs.getTimestamp("create_time"));
                user.setDepartment(rs.getString("department"));
            }
            BaseDao.closeResource(null, pstm, rs);
            System.out.println("--------tiem:"+user.getCreateTime());
        }
        return user;
    }


    @Override
    public User insertUserLogLogin(Connection connection, String userCode) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;
        if (connection != null) {
            String sql1 = " insert into user_log_login (usercode,create_time) values (userCode,NOW();";
            Object[] params = {userCode};
            System.out.println(BaseDao.execute(connection, sql1,params, rs, pstm));
            BaseDao.execute(connection, sql1,params, rs, pstm);
            BaseDao.closeResource(null, pstm, rs);
        }
        return user;
    }
}
