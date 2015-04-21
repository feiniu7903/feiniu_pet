package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;

/**
 * 角色部门信息
 *
 * @author zhushuying
 */
public class NcComplaintRole implements Serializable {


    private static final long serialVersionUID = -2411968184111603905L;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 人员信息
     */
    private String persons;
    /**
     * 部门ID
     */
    private String orgId;
    /**
     * 部门名称
     */
    private String departmentName;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getPersons() {
        return persons;
    }

    public void setPersons(String persons) {
        this.persons = persons;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
