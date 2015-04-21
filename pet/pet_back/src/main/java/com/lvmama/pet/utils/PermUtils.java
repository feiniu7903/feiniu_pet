/**
 * 
 */
package com.lvmama.pet.utils;

import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zkoss.zk.ui.Executions;

import com.lvmama.comm.pet.po.perm.PermPermission;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;

/**
 * check current user whether has the permission or not
 * @author huangl
 *
 *
 */
@SuppressWarnings("unchecked")
public class PermUtils {
	/**
	 * 该用户是否有指定权限,'admin'管理员用户直接跳过权限检查.
	 * @param parentCode
	 * @param permissionCode
	 * @return
	 */
	public static boolean hasPermission(String prentCode,String permCode) {
		if (permCode==null || "".equals(permCode)) return false;
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		HttpServletResponse response = (HttpServletResponse) Executions.getCurrent().getNativeResponse();
		PermUser permUser = (PermUser)ServletUtil.getSession(request, response, com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
		if(permUser == null || permUser.getPermissionList() == null || permUser.getPermissionList().size() == 0){
			return false;
		} else if(permUser != null && Constant.SYSTEM_USER.equals(permUser.getUserName())) {
			return true;
		}
		
		List<PermPermission> permList= permUser.getPermissionList();
		if(permCode.indexOf(",")>-1){
			for (PermPermission permPermission : permList) {
				if (isInclude(permCode,String.valueOf(permPermission.getPermissionId()),String.valueOf(permPermission.getParentId()),prentCode)) {
					return true;
				}
			}
		}else{
			for (PermPermission permPermission : permList) {
				if (permCode.equals(String.valueOf(permPermission.getPermissionId()))) {
					return true;
				}
			}
		}
		return false;
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
}
