package com.nankang.dao.role;

import com.nankang.dao.BaseDao;
import com.nankang.pojo.Unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao{
    //获取部门列表
    @Override
    public List<Unit> getRoleList(Connection connection) throws SQLException {
        //查询列表
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<Unit> roleList = new ArrayList<Unit>();
        if (connection!=null){
            String sql = "select * from unit";
            Object[] params = {};
            rs = BaseDao.execute(connection,sql,params,rs,pstm);
            while (rs.next()){
                Unit unit = new Unit();
                unit.setUnitCode(rs.getInt("unitcode"));//从数据库里面读，然后set
                unit.setUnitName(rs.getString("unitname"));
                unit.setUserName(rs.getString("username"));
                roleList.add(unit);
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return roleList;
    }
}
