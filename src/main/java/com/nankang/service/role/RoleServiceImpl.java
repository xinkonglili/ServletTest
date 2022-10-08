package com.nankang.service.role;

import com.nankang.dao.BaseDao;
import com.nankang.dao.role.RoleDao;
import com.nankang.dao.role.RoleDaoImpl;
import com.nankang.pojo.Unit;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class RoleServiceImpl implements RoleService{
    private RoleDao roleDao;

    public RoleServiceImpl(){
        roleDao = new RoleDaoImpl(); //new RoleDaoImpl()
    }

    //getRoleList:获取unit表中的信息
    @Override
    public List<Unit> getRoleList() {
        //获取连接
        Connection connection = null;
        List<Unit> roleList = null;
        try {
            connection = BaseDao.getConnection();
            roleList = roleDao.getRoleList(connection);
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection,null,null);
        }
        return roleList;
    }

    @Test
    public void test(){
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Unit> roleList = roleService.getRoleList();
        for (Unit unit : roleList ){
            System.out.println(unit.getUnitName());
        }
    }
}
