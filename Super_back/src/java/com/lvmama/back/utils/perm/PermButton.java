package com.lvmama.back.utils.perm;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zul.Button;

import com.opensymphony.oscache.util.StringUtil;

/**
 * 权限按钮-判断用户是否拥有权限.
 * @author huangli
 *
 */
@SuppressWarnings("unused")
public class PermButton extends Button{

	private static final long serialVersionUID = 1L;
	/**
	 * 权限code.多个权限时,逗号','区分.
	 */
	private String permCode;
	/**
	 * 父类菜单权限.当多个链接指向一个页面时,permCode以','区分.需要指定父级上级菜单权限.
	 */
	private String permParentCode;
	/**
	 * 原来的超级链接是否显示.
	 */
	private String isShow;

	public String getPermCode() {
		return permCode;
	}
	/**
	 * 判断有没有使用权限,如果有则原来是可见的则显示,没有就不显示
	 * @param permCode
	 */
	public void checkPermission(){
		if(StringUtils.isEmpty(permCode)) {
			return ;
		}
		boolean isPerm=PermUtils.hasPermission(getPermParentCode(),permCode.trim());
		if(!StringUtil.isEmpty(this.getIsShow())){
			if(isPerm&&"true".equals(this.getIsShow())){
				this.setVisible(true);
			}else{
				this.setVisible(false);
			}
		}else{
			if(isPerm){
				this.setVisible(true);
			}else{
				this.setVisible(false);
			}
		}
	}
	public void setPermCode(String permCode) {
		this.permCode = permCode;
		checkPermission();
	}

	public String getPermParentCode() {
		return permParentCode;
	}
	public void setPermParentCode(String permParentCode) {
		this.permParentCode = permParentCode;
		checkPermission();
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
		checkPermission();
	}

}
