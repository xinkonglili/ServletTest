package com.nankang.service.role;

import com.nankang.dao.BaseDao;
import com.nankang.dao.role.RoleDao;
import com.nankang.dao.role.RoleDaoImpl;
import com.nankang.pojo.Department;
import com.nankang.pojo.Unit;
import com.nankang.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        roleList =  roleList.stream().distinct().collect(Collectors.toList());
        return roleList;
    }


    @Override
    public List<Unit> getUnitList() {
        //获取连接
        Connection connection = null;
        List<Unit> unitList = null;
        try {
            connection = BaseDao.getConnection();
            unitList = roleDao.getUnitList(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection,null,null);
        }
        unitList =  unitList.stream().distinct().collect(Collectors.toList());
        return unitList;
    }

    @Override
    public List<Department> getDepartmentList() {
        //获取连接
        Connection connection = null;
        List<Department> departmentList = null;
        try {
            connection = BaseDao.getConnection();
            departmentList = roleDao.getDepartmentList(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection,null,null);
        }
     //   departmentList =  departmentList.stream().distinct().collect(Collectors.toList());
        return departmentList;
    }

    @Test
    public void test(){
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Unit> roleList = roleService.getRoleList();
        System.out.println("---------"+roleList.toArray());//.lang.Object;@4e41089d
        //com.nankang.pojo.Unit@32a068d1, com.nankang.pojo.Unit@33cb5951, com.nankang.pojo.Unit@365c30cc]
        System.out.println("================="+Arrays.toString(roleList.toArray()));
        /*  系统管理员
            普通用户
            普通用户*/
        for (Unit unit : roleList ){
            System.out.println(unit.getUnitName());
        }
    }
}
