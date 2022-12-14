package com.nankang.pojo;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    private Integer id;
    private String userName;
    private String userCode;
    private String password;
    private String createTime;

    public User(String userCode, String userName, Integer departmentId, Integer unitRole, String image, String password) {
    }

    public User() {

    }


    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    private String images;

    public int getUserDepartmentId() {
        return userDepartmentId;
    }

    public void setUserDepartmentId(int userDepartmentId) {
        this.userDepartmentId = userDepartmentId;
    }

    private Integer userDepartmentId;
    private String unitName;

    public void setUserDepartmentId(Integer userDepartmentId) {
        this.userDepartmentId = userDepartmentId;
    }

    public Integer getUnitRole() {
        return unitRole;
    }

    public void setUnitRole(Integer unitRole) {
        this.unitRole = unitRole;
    }

    private Integer unitRole; //所属单位
    private String department; //所属部门
    private Integer createdBy;   //创建者
    private Date creationDate; //创建时间
    private Integer modifyBy;     //更新者
    private Date modifyDate;   //更新时间

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Integer modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        this.createTime = sdf.format(date);;
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }



}
