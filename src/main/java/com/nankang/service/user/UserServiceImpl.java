package com.nankang.service.user;

import com.nankang.dao.BaseDao;
import com.nankang.dao.user.UserDao;
import com.nankang.dao.user.UserDaoImpl;
import com.nankang.pojo.User;
import org.junit.Test;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
//连接数据库
//业务层调dao层

public class UserServiceImpl implements UserService{
    private UserDao userDao;
    public UserServiceImpl(){
        userDao = new UserDaoImpl();
    }

    //调用login就能拿到该用户
    @Override
    public User login(String userCode, String password) throws SQLException {
        Connection connection = null;
        User user = null;
        connection = BaseDao.getConnection();
        try {
            user =userDao.getLoginUser(connection,userCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        finally {
            //关闭资源
           // BaseDao.closeResource(connection,null,null);
        }

        //前端拿到用户的密码和数据库里面的密码进行比对，如果相等则用户登录成功
        //password ：数据库里面的密码
        //user.getPassword() 从前端获取的
        if (null != user){
            if (!user.getPassword().equals(password)){//不相等才会进入大括号，说明用户名密码不正确
                //user = null;
                //userDao.insertUserLogLogin(connection,user);
            }else {
               userDao.insertUserLogLogin(connection,user);
            }
        }
        return user;
    }

    @Override
    public int getUserCount(String userName, int unitRole) {
        //创建链接
        Connection connection = null;
        int count = 0;
        try {
            connection = BaseDao.getConnection();
            count = userDao.getUserCount(connection, userName, unitRole);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return count;
    }

    @Override
    public List<User> getUserList(String queryUserName, int queryUnitRole, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<User>  userList = null;
        System.out.println("queryUserName------>" + queryUserName);
        System.out.println("queryUserRole------>" + queryUnitRole);
        System.out.println("currentPageNo------->" + currentPageNo);
        System.out.println("pageSize------>" + pageSize);

        try {
            connection =BaseDao.getConnection();
            userList = userDao.getUserList(connection, queryUserName, queryUnitRole, currentPageNo, pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return userList;
    }


    @Override
    public Boolean add(User user) throws Exception {
        boolean flag = false;
        Connection connection = null;

        try {
            //获取连接
            connection = BaseDao.getConnection();
            //开启jdbc事务管理
            connection.setAutoCommit(false);
            //调用dao层的接口
            int updateRows = userDao.add(connection, user);
            connection.commit();
            if (updateRows>0){
                flag = true;
                System.out.println("Add success!");
            }else {
                System.out.println("Add failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                //失败就回滚
                connection.rollback();
            }catch (SQLException e1){
                e1.printStackTrace();
            }
        }finally {
        //在service层对connection的关闭
            BaseDao.closeResource(connection,null,null);
        }
        return flag;
    }

    @Override
    public boolean deleteUserById(Integer delId) {
        Boolean flag=false;
        Connection connection=null;
        connection=BaseDao.getConnection();
        try {
            int deleteNum=userDao.deleteUserById(connection,delId);
            if(deleteNum>0)flag=true;
        } catch (Exception e) {
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return flag;
    }



    @Test
    public void test() throws SQLException {
        UserServiceImpl userService = new UserServiceImpl();
        //service层的login接口
      /*  User admin = userService.login("admin","1234567");
        System.out.println(admin.getPassword());*/

        int userCount = userService.getUserCount(null,0);
        System.out.println(userCount);

    }


}
