package com.nankang.dao.user;

import com.mysql.cj.util.StringUtils;
import com.nankang.dao.BaseDao;
import com.nankang.pojo.*;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class UserDaoImpl implements UserDao {

    //重写了dao层接口中的方法
    @Override
    public User getLoginUser(Connection connection, String userCode) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;

        if (connection != null) {
            //执行这条sql语句去数据库查
            String sql = "select * from user where usercode=?";
            Object[] params = {userCode};
            rs = BaseDao.execute(connection, sql, params, rs, pstm);
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("usercode"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUnitRole(rs.getString("unitRole"));
                user.setCreateTime(rs.getTimestamp("create_time"));
                user.setDepartment(rs.getString("department"));
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return user;
    }

    @Override
    public UserLogLogin getUserLoginTime(Connection connection, String userCode) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        UserLogLogin userLogLogin = null;

        if (connection != null) {
            //执行这条sql语句去数据库查
            String sql = "SELECT  create_time  FROM `user_log_login`  where usercode = ? ORDER BY create_time desc LIMIT 1;";
            Object[] params = {userCode};
            rs = BaseDao.execute(connection, sql, params, rs, pstm);
            if (rs.next()) {
                userLogLogin.setCreateTime(rs.getString("createTime"));
            }
            BaseDao.closeResource(null, pstm, rs);
        }
       //System.out.println("userDao获取用户上次登录的时间--->" + userLogLogin.getCreateTime());
        return userLogLogin;
    }

    @Override
    public User insertUserLogLogin(Connection connection, User user) throws SQLException {
        PreparedStatement pstm = null;
        if (connection != null) {
            String sql1 = " insert into user_log_login (usercode,create_time) values (?,NOW());";
            Object[] params = {user.getUserCode()};
            BaseDao.execute(connection, sql1, params, pstm);
            BaseDao.closeResource(null, pstm, null);
        }
        return user;
    }

    @Override
    public User insertUserLogOperation(Connection connection, User user) throws SQLException {
        PreparedStatement pstm = null;
        if (connection != null) {
            String sql1 = " insert into user_log_login (usercode,create_time) values (?,NOW());";
            Object[] params = {user.getUserCode()};
            BaseDao.execute(connection, sql1, params, pstm);
            BaseDao.closeResource(null, pstm, null);
        }
        return user;
    }

    @Override
    public int getUserCount(Connection connection, String userName, int unitRole) throws SQLException {
        int count = 0;
        PreparedStatement pstm = null; //执行sql的对象
        ResultSet rs = null;
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("\n" +
                    "select COUNT(1) as count from user us, unit un where us.`unitrole` = un.`unitcode`");
            ArrayList<Object> list = new ArrayList<Object>();//存放参数
            if (!StringUtils.isNullOrEmpty(userName)) { //如果用户名不为空
                sql.append(" and us.userName like ?");//sql语句的拼接
                list.add("%"+userName+"%"); //模糊查询,list默认下标为1
            }

            if (unitRole > 0) {
                sql.append(" and us.unitRole = ?");
                list.add(unitRole); //index = 1
            }

            //把list转换为数组
            Object[] params = list.toArray();
            System.out.println("UserDaoImpl--->getUserCount打印出：" + sql);
            rs = BaseDao.execute(connection, sql.toString(), params, rs, pstm);
            if(rs.next()){
                count=rs.getInt("count");
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return count;
    }

    @Override
    public List<User> getUserList(Connection connection, String userName, int unitRole, int currentPageNo, int pageSize) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<User>();
        if (connection != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select us.* ,un.unitname as unitname, dep.department_name as department_name from user us, " +
                    "unit un, department dep WHERE us.unitrole = un.unitcode " +
                    "and us.userdepartmentid = dep.departmentid");
            List<Object> list = new ArrayList<Object>();

            if (!StringUtils.isNullOrEmpty(userName)) { //如果用户名不为空
                sql.append(" and us.userName like ?");//sql语句的拼接
                list.add("%"+userName+"%"); //模糊查询,list默认下标为1
            }

            if (unitRole > 0) {
                sql.append(" and us.unitRole = ?");
                list.add(unitRole); //index = 1
            }

            sql.append(" order by unitrole desc limit ?,?");
            currentPageNo = (currentPageNo-1) * pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("sql------>"+ sql.toString());
            rs = BaseDao.execute(connection, sql.toString(),params,rs, pstm);
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("usercode"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUnitRole(rs.getString("unitRole"));
                user.setUnitName(rs.getString("unitName"));
                user.setCreateTime(rs.getTimestamp("create_time"));
                user.setDepartment(rs.getString("department"));
                userList.add(user);
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return userList ;
    }

    @Override
    public List<ResultUnit> getUnitList(Connection connection, String unitName, int unitid, int currentPageNo, int pageSize) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<ResultUnit> newUnitList = new ArrayList();
        if (connection != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select un.unitcode,un.unitname,un.address,un.phone,un.fax,dep.department_name from unit un," +
                    "department dep where un.unitcode = dep.unitid");
            List<Object> list = new ArrayList<Object>();
            if (!StringUtils.isNullOrEmpty(unitName)) { //如果单位名不为空
                sql.append(" and un.unitName like ?");//sql语句的拼接
                list.add(""+unitName+""); //模糊查询,list默认下标为1
            }
            System.out.println("unitid-------------" + unitid);
            if ( unitid > 0) {
                sql.append(" and dep.unitid = ?");
                list.add(unitid); //index = 1
            }
            sql.append(" order by unitid desc limit ?,?");
            System.out.println("sql------>"+ sql.toString());
            currentPageNo = (currentPageNo) * pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            rs = BaseDao.execute(connection, sql.toString(),params,rs, pstm);
            while (rs.next()){
                ResultUnit resultUnit = new ResultUnit();
                resultUnit.setUnitName(rs.getString("unitname"));
                resultUnit.setDepartmentName(rs.getString("department_name"));
                resultUnit.setAddress(rs.getString("address"));
                //resultUnit.setUnitCodeName(rs.getString("unitcodename"));
                resultUnit.setFax(rs.getString("fax"));
                resultUnit.setUnitCode(rs.getInt("unitCode"));
                resultUnit.setPhone(rs.getString("phone"));
                newUnitList.add(resultUnit);
            }
            BaseDao.closeResource(null,pstm,rs);
        }

        return newUnitList;
    }

    @Override
    public int add(Connection connection, User user) throws Exception {
        PreparedStatement pstm = null;
        int updateNum =0;
        if (connection!=null){

            Object[] params ={user.getUserCode(),user.getUserName(),user.getPassword(),user.getUnitRole(),
                    user.getDepartment(),user.getUserDepartmentId(),user.getUnitName(),user.getCreateTime()};
            String sql = "insert into user (usercode,username,password,unitrole,department,userdepartmentid,unitname,create_time)" +
                    "VALUES (?,?,?,?,?,?,?,?)";
            updateNum = BaseDao.execute(connection, sql, params, pstm);
            BaseDao.closeResource(null,pstm,null);
        }

        return updateNum;
    }

    @Override
    public int addUnit(Connection connection, Unit unit) throws Exception {
        PreparedStatement pstm = null;
        int updateNum =0;
        if (connection!=null){

            Object[] params ={unit.getUnitCode(),unit.getUnitName(),
                    unit.getAddress(),unit.getPhone(),unit.getFax(),unit.getUnitCodeName()};
            String sql = " \n" +
                    " INSERT into unit (unitcode,unitname,address,phone,fax,unitcodename) VALUES (?,?,?,?,?,?)";
            updateNum = BaseDao.execute(connection, sql, params, pstm);
            BaseDao.closeResource(null,pstm,null);
        }
        return updateNum;
    }


    //根据用户id删除该用户
    public int deleteUserById(Connection connection, Integer delId) throws Exception {
        PreparedStatement pstm=null;
        int deleteNum=0;
        if(connection!=null){
            String sql="DELETE FROM `user` WHERE id=?";
            Object[] params={delId};
            deleteNum=BaseDao.execute(connection,sql,params,pstm);
            BaseDao.closeResource(null,pstm,null);
        }
        return deleteNum;
    }

    @Override
    public int deleteUnitById(Connection connection, Integer delUnitId) throws Exception {
        PreparedStatement pstm=null;
        int deleteNum=0;
        if(connection!=null){
            String sql="DELETE FROM `unit` WHERE id=?";
            Object[] params={delUnitId};
            deleteNum=BaseDao.execute(connection,sql,params,pstm);
            BaseDao.closeResource(null,pstm,null);
        }
        return deleteNum;
    }

    //查看是要返回结果集的
    @Override
    public User getUserById(Connection connection, String id) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = new User();
        if (connection!=null){
            String sql = "SELECT us.*,un.unitname as userUnitName from user us,unit un where us.id = ? and us.unitrole = un.unitcode";
            Object[] param = {id};
            rs = BaseDao.execute(connection, sql, param, rs, pstm);
            while (rs.next()){
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("usercode"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setCreateTime(rs.getDate("create_time"));
                user.setUnitRole(rs.getString("unitrole"));
                user.setDepartment(rs.getString("department"));
                user.setUnitName(rs.getString("unitname"));
                user.setUserDepartmentId(rs.getString("userdepartmentid"));

            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return user;
    }

    @Override
    public ResultUnit getUnitById(Connection connection, String unitCode) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ResultUnit unit = new ResultUnit();
        if (connection!=null){
            String sql = "select un.*,dep.department_name as departmentname from unit un, department dep WHERE un.unitcode = ? and un.unitcode = dep.unitid;";
            Object[] param = {unitCode};
            rs = BaseDao.execute(connection, sql, param, rs, pstm);
            while (rs.next()){
                unit.setUnitCode(rs.getInt("unitcode"));
                unit.setUnitName(rs.getString("unitname"));
                unit.setAddress(rs.getString("address"));
                unit.setPhone(rs.getString("phone"));
                unit.setFax(rs.getString("fax"));
                unit.setDepartmentName(rs.getString("departmentname"));
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return unit;
    }

    @Override
    public int modify(Connection connection, User user) throws SQLException {
        int updateNum = 0; //更新数据的条数
        PreparedStatement pstm = null;
        if (null!=connection){
            String sql = "UPDATE user set username = ?,usercode =?,unitrole =?, unitname = ? where id = ? ";
            Object[] params ={user.getUserName(),user.getUserCode(),
                    user.getUnitRole(),user.getUnitName(), user.getId()};
            updateNum =BaseDao.execute(connection,sql,params,pstm);
            BaseDao.closeResource(null,pstm,null);
        }

        return updateNum;
    }

    @Override
    public int unitModify(Connection connection, ResultUnit resultUnit) throws SQLException {
        int updateNum = 0; //更新数据的条数
        PreparedStatement pstm = null;
        if (null!=connection){
            String sql = "UPDATE unit set unitname = ?,unitcode =?,address =?, phone = ?, fax = ? where unitcode = ? ";
            Object[] params ={resultUnit.getUnitCode(),resultUnit.getUnitName(),
                    resultUnit.getAddress(),resultUnit.getPhone(), resultUnit.getFax()};
            updateNum =BaseDao.execute(connection,sql,params,pstm);
            BaseDao.closeResource(null,pstm,null);
        }

        return updateNum;
    }

    @Override
    public User addOperationLog(Connection connection, User user) throws SQLException {
        PreparedStatement pstm = null;
        if (connection!=null){
            String sql = "insert into user_log_operation (operation_id,username,type,company_name,department_name,data_before,data_after,time) " +
                    "VALUES (?, ?, ?,?,?,?,?,?)";
            Object[] params = {user.getUserCode()};
            BaseDao.execute(connection,sql,params,pstm);
            BaseDao.closeResource(null,pstm,null);
        }
        return null;
    }

}

