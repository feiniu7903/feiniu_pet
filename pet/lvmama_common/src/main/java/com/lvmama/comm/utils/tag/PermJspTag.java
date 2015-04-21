/**
 * 
 */
package com.lvmama.comm.utils.tag;

import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.lvmama.comm.pet.po.perm.PermPermission;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;

/**
 * check current user whether has the permission or not
 * 
 * @author huangl
 * 
 */
@SuppressWarnings("unchecked")
public class PermJspTag extends BodyTagSupport {
	private static Logger log = Logger.getLogger(PermJspTag.class);

	private String permCode;
	private Object permParentCode;
	private String scope; // default is snippet , the scope 'page' controls this
	private String redirect;// no permission redirect page
	private String nolog;// if you can't logon ,must logon first!
	private String noaccess;// logon but have no permissions
	
	private static final String REDIRECT_PAGE = "/index.jsp";

	// SKIP_BODY隐含0 ：跳过了开始和结束标签之间的代码。
	// EVAL_BODY_INCLUDE隐含1：将body的内容输出到存在的输出流中
	@Override
	public int doStartTag() throws JspException {
		PermUser permUser= (PermUser)ServletUtil.getSession((HttpServletRequest)pageContext.getRequest(), 
				(HttpServletResponse)pageContext.getResponse(), Constant.SESSION_BACK_USER);
		if(permUser!=null&&Constant.SYSTEM_USER.equals(permUser.getUserName())){
			return EVAL_BODY_INCLUDE;
		}
		if(permUser!=null){
			List<PermPermission> permList = permUser.getPermissionList();
			if(permCode.indexOf(",")>-1){
				for (PermPermission permPermission : permList) {
					if (isInclude(permCode,String.valueOf(permPermission.getPermissionId()),String.valueOf(permPermission.getParentId()),String.valueOf(permParentCode))) {
						return EVAL_BODY_INCLUDE;
					}
				}
			}else{
				for (PermPermission permPermission : permList) {
					if (permCode.equals(String.valueOf(permPermission.getPermissionId()))) {
						return EVAL_BODY_INCLUDE;
					}
				}
			}
		}
		return SKIP_BODY;
	}
	
	/**
	 * 权限代码','拆分
	 * @param permCode
	 * @param code
	 * @return
	 */
	@SuppressWarnings("unused")
	private static boolean isInclude(String permCode,String permissionId,String parentId,String prentCode){
		if (permissionId==null || "".equals(permissionId)) return false;
		StringTokenizer tokens = new StringTokenizer(permCode,",");
		while(tokens.hasMoreTokens()){
			if (permissionId.equals(tokens.nextToken())&&String.valueOf(parentId).equals(prentCode)) return true;
		}
		return false;
	}
	public String getNolog() {
		return nolog;
	}

	public void setNolog(String nolog) {
		this.nolog = nolog;
	}

	public String getNoaccess() {
		return noaccess;
	}

	public void setNoaccess(String noaccess) {
		this.noaccess = noaccess;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
	public String getPermCode() {
		return permCode;
	}

	public Object getPermParentCode() {
		return permParentCode;
	}

	public void setPermParentCode(Object permParentCode) throws JspException {
		this.permParentCode = permParentCode != null ? ExpressionEvaluatorManager.evaluate("permParentCode", permParentCode.toString(), Object.class, this, pageContext) : "";
	}

	public void setPermCode(String permCode) {
		this.permCode = permCode;
	}
}
