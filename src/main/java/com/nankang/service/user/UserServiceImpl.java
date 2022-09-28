package com.nankang.service.user;

import com.nankang.dao.BaseDao;
import com.nankang.dao.user.UserDao;
import com.nankang.dao.user.UserDaoImpl;
import com.nankang.pojo.User;
import org.junit.Test;


import java.sql.Connection;
import java.sql.SQLException;

public class UserServiceImpl implements UserService{
    private UserDao userDao;
    public UserServiceImpl(){
        userDao = new UserDaoImpl();
    }

    //调用login就能拿到该用户
    @Override
    public User login(String userCode, String password)  {
        Connection connection = null;
        User user = null;

        connection = BaseDao.getConnection();
        try {
            user =userDao.getLoginUser(connection,userCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //关闭资源
            BaseDao.closeResource(connection,null,null);
        }

        //前端拿到用户的密码和数据库里面的密码进行比对，如果相等则用户登录成功
        if (null != user){
            if (!user.getPassword().equals(password)){
                user = null;
            }
        }
        return user;
    }
    @Test
    public void test(){
        UserServiceImpl userService = new UserServiceImpl();
        //service层的login接口
        User admin = userService.login("admin","1234567");
        System.out.println(admin.getPassword());

    }


}
