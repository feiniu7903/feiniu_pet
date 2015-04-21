package com.lvmama.back.sweb.complaint;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.ord.NcComplaintRole;
import com.lvmama.comm.bee.service.complaint.NcComplaintRoleService;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.vo.Page;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import java.util.*;

/**
 * 角色管理
 * @author zhushuying
 *
 */
@SuppressWarnings("serial")
@Results({
	@Result(name = "allRole", location = "/WEB-INF/pages/back/complaint/role_list.jsp"),
	@Result(name = "update", location = "/WEB-INF/pages/back/complaint/rolePerson_edit.jsp")})
public class ComplaintRoleAction extends BackBaseAction{
	private NcComplaintRoleService ncComplaintRoleService;
	private Long roleId;
    private String persons;
    private Long orgId;
	private String departmentName;

	private Page<NcComplaintRole> rolePage = new Page<NcComplaintRole>();
	private PermUserService permUserService;
	
	@Action("/roleProd/queryRoleList")
	public String AllRoleList(){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("isShow", "YES");
		rolePage.setTotalResultSize(ncComplaintRoleService.getRoleCount(params));
		rolePage.buildUrl(getRequest());
		rolePage.setCurrentPage(super.page);
		params.put("start", rolePage.getStartRows());
		params.put("end", rolePage.getEndRows());
		if(rolePage.getTotalResultSize()>0){
			rolePage.setItems(ncComplaintRoleService.getAllRoleByPage(params));
		}
		return "allRole";
	}
	@Action("/roleProd/updateUsers")
	public String updateUsers(){
		NcComplaintRole role=ncComplaintRoleService.selectRoleByOrgId(orgId);
		setRequestAttribute("role", role);
		return "update";
	}
	@Action("/roleProd/update_user")
	public void update(){
		long res=0;
		NcComplaintRole role=ncComplaintRoleService.selectRoleByOrgId(orgId);
		List<String> list=new ArrayList<String>();
		//将获取的字符串转换成list集合
        StringTokenizer st=new StringTokenizer(persons,",");
        while(st.hasMoreTokens()){
        	list.add(st.nextToken());
        }
        if(!isEqualPerson(list)){
			sendAjaxMsg("不能添加相同的人员");
			return;
        };
        for(String userName:list){
        	//判断客服是否存在
        	PermUser pUser=permUserService.getPermUserByUserName(userName);
        	if(pUser==null){
        		sendAjaxMsg(userName+"客服不存在");
        		return;
    		}
        }

        if (role != null) {
            role.setPersons(persons);

            if (role.getRoleId() == null) {
                res = ncComplaintRoleService.insert(role);
            } else {
                res = ncComplaintRoleService.updateRole(role);
            }
        }
        if(res>0){
			sendAjaxMsg("SUCCESS");
		}else{
			sendAjaxMsg("FAILED");
		}
	}
	//判断添加的人员是否存在相同的
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean isEqualPerson(List<String> list) {
		HashSet set=new HashSet();
		for(String i:list){
		    set.add(i);
		}
		if(!(set.size()==list.size())){
    		return false;
		}
		return true;
	}
	public NcComplaintRoleService getNcComplaintRoleService() {
		return ncComplaintRoleService;
	}

	public void setNcComplaintRoleService(
			NcComplaintRoleService ncComplaintRoleService) {
		this.ncComplaintRoleService = ncComplaintRoleService;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPersons() {
		return persons;
	}
	public void setPersons(String persons) {
		this.persons = persons;
	}
	public Page<NcComplaintRole> getRolePage() {
		return rolePage;
	}
	public void setRolePage(Page<NcComplaintRole> rolePage) {
		this.rolePage = rolePage;
	}
	public PermUserService getPermUserService() {
		return permUserService;
	}
	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
