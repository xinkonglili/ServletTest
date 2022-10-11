package com.nankang.service.user;

import com.nankang.dao.BaseDao;
import com.nankang.pojo.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    //用户登录
    public User login(String userCode, String password) throws SQLException;

    //查询记录数
    public int getUserCount(String userName, int unitRole) throws SQLException;

    //根据条件查询用户列表
    public List<User> getUserList(String queryUserName, int queryUnitRole, int currentPageNo, int pageSize);

    public Boolean add(User user) throws Exception;
    public boolean deleteUserById(Integer delId);
}
