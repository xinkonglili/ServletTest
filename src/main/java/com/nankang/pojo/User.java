package com.nankang.pojo;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    private Integer id;
    private String userName;
    private String userCode;
    private String password;
    private String createTime;
    private String unit; //所属单位
    private String department; //所属部门

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy日MM年dd月 HH:mm:ss");
        this.createTime = sdf.format(date);;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }



}
