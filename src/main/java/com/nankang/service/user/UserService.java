package com.nankang.service.user;

import com.nankang.pojo.*;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    //用户登录
    public User login(String userCode, String password) throws SQLException;

    //查询记录数
    public int getUserCount(String userName, int unitRole) throws SQLException;
    public UserLogLogin getUserLoginTime(String userName) throws SQLException;

    //根据条件查询用户列表
    public List<User> getUserList(String queryUserName, int queryUnitRole, int currentPageNo, int pageSize);

    public List<ResultUnit> getUnitList(String queryUserName, int queryUnitName, int currentPageNo, int pageSize);
    public Boolean addUser(User user) throws Exception;
    public Boolean addUnit(UnitPOVO unitpovo) throws Exception;
    public boolean deleteUserById(Integer delId);
    public boolean deleteUnitById(Integer delUnitId);

    public User getUserById(String id) throws Exception;
    public ResultUnit getUnitById(String unitCode) throws Exception;

    //修改用户的信息
    public Boolean modify(User user) throws Exception;
    public Boolean  unitModify(ResultUnit resultUnit) throws Exception;

    //根据用户编码，判断用户是否存在
    public User selectUserCodeExist(String userCode);
}
