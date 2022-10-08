package com.nankang.dao.role;

import com.nankang.pojo.Unit;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RoleDao {
    //获取部门unit列表
    public List<Unit> getRoleList(Connection connection) throws Exception;

}
