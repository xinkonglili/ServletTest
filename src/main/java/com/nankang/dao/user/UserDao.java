package com.nankang.dao.user;

import com.nankang.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserDao {
   // userCode:admin  userName:系统管理员
    public User getLoginUser(Connection connection, String userCode) throws SQLException;

}
