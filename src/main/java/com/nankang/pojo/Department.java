package com.nankang.pojo;

public class Department { //部门字典表
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getDepartmentNameId() {
        return departmentNameId;
    }

    public void setDepartmentNameId(Integer departmentNameId) {
        this.departmentNameId = departmentNameId;
    }

    private String departmentName;
    private Integer departmentNameId;

}
