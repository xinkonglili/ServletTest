package com.nankang.pojo;

public class Unit { //单位表
    private Integer unitCode;
    private String unitName;
    private String address;
    private String fax;
    private Integer status;

    public Integer getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(Integer unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
