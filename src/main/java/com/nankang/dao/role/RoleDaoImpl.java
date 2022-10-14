package com.nankang.dao.role;

import com.nankang.dao.BaseDao;
import com.nankang.pojo.Department;
import com.nankang.pojo.Unit;
import com.nankang.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class RoleDaoImpl implements RoleDao{
    //获取部门列表
    @Override
    public List<Unit> getRoleList(Connection connection) throws SQLException {
        //查询列表
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<Unit> roleList = new ArrayList<Unit>();

        if (connection!=null){
            String sql = "select * from `unit`";
            Object[] params = {};
            rs = BaseDao.execute(connection,sql,params, rs, pstm);
            while (rs.next()){
                Unit unit = new Unit();
                unit.setUnitCode(rs.getInt("unitcode"));//从数据库里面读，然后set
                unit.setUnitName(rs.getString("unitname"));
               // unit.setUserName(rs.getString("username"));
                roleList.add(unit);
            }
        }
        BaseDao.closeResource(null,pstm,rs);

       // roleList = (ArrayList<Unit>) roleList.stream().distinct().collect(Collectors.toList());

        List list = roleList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(Unit::getUnitCode))),ArrayList::new));

        return list;
    }


    @Override
    public List<Unit> getUnitList(Connection connection) throws SQLException {
        //查询列表
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<Unit> roleList = new ArrayList<Unit>();

        if (connection!=null){
            String sql = "select * from `unit`";
            Object[] params = {};
            rs = BaseDao.execute(connection,sql,params, rs, pstm);
            while (rs.next()){
                Unit unit = new Unit();
                unit.setUnitCode(rs.getInt("unitcode"));//从数据库里面读，然后set
                unit.setUnitName(rs.getString("unitname"));
                // unit.setUserName(rs.getString("username"));
                roleList.add(unit);
            }
        }
        BaseDao.closeResource(null,pstm,rs);

        // roleList = (ArrayList<Unit>) roleList.stream().distinct().collect(Collectors.toList());

        List list = roleList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(Unit::getUnitCode))),ArrayList::new));

        return list;
    }

    @Override
    public List<Department> getDepartmentList(Connection connection) throws Exception {
        //查询用户列表
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<Department> departmentList = new ArrayList<Department>();
        if (connection!=null){
            String sql = "select * from `user`";
            Object[] params = {};
            rs = BaseDao.execute(connection,sql,params, rs, pstm);
            while (rs.next()){
                Department department = new Department();
                //从数据库里面读，然后set
                department.setDepartmentName(rs.getString("department_name"));
                department.setDepartmentNameId(rs.getInt("departmentid"));
                departmentList.add(department);
            }
        }
        return departmentList;
    }
}
