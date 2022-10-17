package com.nankang.service.user;

import com.nankang.dao.BaseDao;
import com.nankang.dao.user.UserDao;
import com.nankang.dao.user.UserDaoImpl;
import com.nankang.pojo.Department;
import com.nankang.pojo.User;
import com.nankang.pojo.UserLogLogin;
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
    public UserLogLogin getUserLoginTime(String userName) throws SQLException {
        Connection connection = null;
        UserLogLogin userLoginTime = null;
        try {
            connection = BaseDao.getConnection();
            userLoginTime = userDao.getUserLoginTime(connection, userName);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        System.out.println("userLoginTime--------->"+ userLoginTime);
        return userLoginTime;

    }

    @Override
    public List<User> getUserList(String queryUserName, int queryUnitRole, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<User>  userList = null;
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
    public List<User> getUserListDepartment(String queryUserName, int queryUserDepartmentId, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<User>  userrListDepartment = null;
        System.out.println("queryUserName------>" + queryUserName);
        System.out.println("queryUserDepartmentId------>" + queryUserDepartmentId);
        System.out.println("currentPageNo------->" + currentPageNo);
        System.out.println("pageSize------>" + pageSize);

        try {
            connection =BaseDao.getConnection();
            userrListDepartment = userDao.getUserListDepartment(connection, queryUserName, queryUserDepartmentId, currentPageNo, pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }

        /*for (User user : userrListDepartment){
            System.out.println("userrListDepartment----------*8*888888"+user.getUserDepartmentId());
            System.out.println("userrListDepartment----------*8*888888"+user.getUserCode());
            System.out.println("userrListDepartment----------*8*888888"+user.getDepartment());
        }*/
        return userrListDepartment;
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
           // user.setCreateTime();
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

    @Override
    public User getUserById(String id)  {
        User user = new User();
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            user = userDao.getUserById(connection,id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return user;
    }

    @Override
    public Boolean modify(User user) {
        Boolean flag = false;
        Connection connection = null;

        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);
            int updateNum = userDao.modify(connection,user);
            connection.commit(); //提交事务
            if (updateNum>0){
                flag = true;
                System.out.println("修改用户成功");
            }else {
                System.out.println("修改用户失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //若抛出异常，则说明修改失败需要回滚
            System.out.println("修改失败，回滚事务");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        } finally {
            BaseDao.closeResource(connection,null,null);
        }
        return flag;
    }



    @Override
    public User selectUserCodeExist(String userCode) {

        Connection connection = null;
        User user = null;
        try {
            connection = BaseDao.getConnection();
            user = userDao.getLoginUser(connection, userCode);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return user;
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
