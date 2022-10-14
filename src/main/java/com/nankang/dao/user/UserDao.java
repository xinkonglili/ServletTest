package com.nankang.dao.user;

import com.nankang.pojo.Unit;
import com.nankang.pojo.User;
import com.nankang.pojo.UserLogLogin;

import javax.swing.plaf.PanelUI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
   // userCode:admin  userName:系统管理员
    public User getLoginUser(Connection connection, String userCode) throws SQLException;

    public UserLogLogin getUserLoginTime(Connection connection, String userCode) throws SQLException;
    public User insertUserLogLogin(Connection connection, User user) throws SQLException;
    public User insertUserLogOperation(Connection connection, User user) throws SQLException;

    //查询用户总数,unitRole: 1代表admin，2代表用户
    public int getUserCount(Connection connection, String userName, int unitRole) throws  SQLException;

    //获取用户列表
    public List<User> getUserList(Connection connection, String userName, int unitRole,int currentPageNo, int pageSize) throws SQLException;
    public List<User> getUserListDepartment(Connection connection, String userName, int userDepartmentId,int currentPageNo, int pageSize) throws SQLException;
    public int add(Connection connection,User user) throws Exception;

    public int deleteUserById(Connection connection, Integer delId) throws Exception;

    //通过用户id来获取用户信息
    public User getUserById(Connection connection,String id) throws Exception;
    //修改用户信息
    public int modify(Connection connection, User user) throws SQLException;

    public User addOperationLog(Connection connection, User user) throws  SQLException;



}
