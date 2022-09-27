package com.nankang.dao.user;

import com.nankang.dao.BaseDao;
import com.nankang.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    //重写了dao层接口中的方法
    @Override
    public User getLoginUser(Connection connection, String userCode) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;

        if(connection!= null){
            String sql ="select * from user where usercode=?";
            Object[] params ={userCode};

            rs = BaseDao.execute(connection,sql,params,rs,pstm);
            if (rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("usercode"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUnit(rs.getString("unit"));
                user.setCreateTime(rs.getTimestamp("create_time"));
                user.setDepartment(rs.getString("department"));
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return user;
    }
}
