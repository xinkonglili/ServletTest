package com.nankang.service.role;

import com.nankang.pojo.Department;
import com.nankang.pojo.Unit;
import com.nankang.pojo.User;

import java.util.List;

public interface RoleService {
    public List<Unit>  getRoleList();
    public List<Unit> getUnitList();
    public List<Department> getDepartmentList();
}
