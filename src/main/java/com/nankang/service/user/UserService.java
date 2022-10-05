package com.nankang.service.user;

import com.nankang.pojo.User;

import java.sql.SQLException;

public interface UserService {
    //用户登录
    public User login(String userCode, String password) throws SQLException;


}
