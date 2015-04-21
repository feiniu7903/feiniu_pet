package com.lvmama.comm.utils.tag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.lvmama.comm.bee.po.eplace.EbkPermission;
import com.lvmama.comm.bee.po.eplace.EbkUser;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;


public class EbkPermJspTag extends BodyTagSupport{
	private String permissionId;
	private String bizType;
	@Override
	public int doStartTag() throws JspException {
		EbkUser loginUser= (EbkUser)ServletUtil.getSession((HttpServletRequest)pageContext.getRequest(), 
				(HttpServletResponse)pageContext.getResponse(), Constant.SESSION_EBOOKING_USER);
		//管理员账号
		if("true".equals(loginUser.getIsAdmin())){
			return EVAL_BODY_INCLUDE;
		}
		
		//check permission
		List<EbkPermission> loginUserPerms = (List<EbkPermission>)ServletUtil.getSession((HttpServletRequest)pageContext.getRequest(), 
				(HttpServletResponse)pageContext.getResponse(), Constant.Session_EBOOKING_USER_PERMISSION_LIST);
		if(loginUserPerms != null && loginUserPerms.size() > 0){
			for(EbkPermission p : loginUserPerms){
				String[] permissionIds = permissionId.split(",");
				for(String permId : permissionIds){
					if(p.getPermissionId().equals(Long.parseLong(permId))){
						return EVAL_BODY_INCLUDE;
					}
				}
			}
		}
		return SKIP_BODY;
	}
	public String getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
}
