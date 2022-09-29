package com.nankang.dao.user;

import com.nankang.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
   // userCode:admin  userName:系统管理员
    public User getLoginUser(Connection connection, String userCode) throws SQLException;
    //通过用户输入的条件来查询用户列表
    //public List<User> getUserList(Connection connection, String userName, int )



}
