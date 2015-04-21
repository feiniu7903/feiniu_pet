/**
 * 
 */
package com.lvmama.comm.utils.tag;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.lvmama.comm.pet.po.perm.PermPermission;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 根据权限控制页面表单元素
 * 
 * @author shihui
 * 
 */
public class PermJspElement extends BodyTagSupport {
	private static final long serialVersionUID = 3077676019495939284L;

	private String permCode;

	private String id = "";

	private Object value = "";

	private String name = "";

	private String type = "text";

	private String size = "15";

	private String list = "";

	private String listKey = "";

	private String listValue = "";

	private String style = "";

	private String headerKey = "";

	private String headerValue = "";
	
	private Object permParentCode="";
	
	@Override
	public int doStartTag() throws JspException {
		PermUser permUser= (PermUser)ServletUtil.getSession((HttpServletRequest)pageContext.getRequest(), 
				(HttpServletResponse)pageContext.getResponse(), Constant.SESSION_BACK_USER);
		if(permUser!=null&&Constant.SYSTEM_USER.equals(permUser.getUserName())){
			return outElement(false);
		}
		
		List<PermPermission> permList = permUser.getPermissionList();
		if(permCode.indexOf(",")>-1){
			for (PermPermission permPermission : permList) {
				if (isInclude(permCode,String.valueOf(permPermission.getPermissionId()),String.valueOf(permPermission.getParentId()),String.valueOf(permParentCode))) {
					return outElement(false);
				}
			}
		}else{
			for (PermPermission permPermission : permList) {
				if (permCode.equals(String.valueOf(permPermission.getPermissionId()))) {
					return outElement(false);
				}
			}
		}
		return outElement(true);
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
	public int outElement(boolean isDisabled) {
		JspWriter out = this.pageContext.getOut();
		try {
			String content = "";
			if (type.equalsIgnoreCase("select")) {
				if (!list.equals("")) {
					List<CodeItem> itemList = (List<CodeItem>) this.pageContext.getRequest().getAttribute(list);
					if(isDisabled) {
						content = "<select name='" + name + "' id='" + id + "' style='" + style + "' disabled><option value=''>请选择</option>";
					}else{
						content = "<select name='" + name + "' id='" + id + "' style='" + style + "'><option value=''>请选择</option>";
					}
					out.println(content);
					for (int i = 0; i < itemList.size(); i++) {
						CodeItem item = itemList.get(i);
						out.println("<option value='" + item.getCode() + "'>" + item.getName() + "</option>");
					}
					out.println("</select>");
				} else {
					if(isDisabled) {
						content = "<select id='" + id + "' name='" + name + "' style='" + style + "' disabled>";
					}else{
						content = "<select id='" + id + "' name='" + name + "' style='" + style + "'>";
					}
					out.println(content);
					return EVAL_BODY_INCLUDE;
				}
			} else {
				if(isDisabled) {
					content = "<input id='" + id + "' name='" + name + "'  type='" + type + "'  size='" + size + "' value='" + value + "' disabled></input>";
				}else{
					content = "<input id='" + id + "' name='" + name + "'  type='" + type + "'  size='" + size + "' value='" + value + "'></input>";
				}
				out.println(content);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		if(type.equalsIgnoreCase("select") && list.equals("")) {
			try {
				out.println("</select>");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return super.doEndTag();
	}

	/**
	 * 权限代码','拆分
	 * 
	 * @param permCode
	 * @param code
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean isInclude(String permCode, String code) {
		if (code == null || "".equals(code))
			return false;
		StringTokenizer tokens = new StringTokenizer(permCode, ",");
		while (tokens.hasMoreTokens()) {
			if (code.equals(tokens.nextToken()))
				return true;
		}
		return false;
	}

	public String getPermCode() {
		return permCode;
	}

	public void setPermCode(String permCode) {
		this.permCode = permCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) throws JspException {
		// 转换el表达式
		if(value != null){
			value=ExpressionEvaluatorManager.evaluate("value", value.toString(), Object.class, this, pageContext);
			if("null".equals(value)){
				value=String.valueOf(value).replace("null", "");
			}
		}else{
			value="";
		}
		this.value = value;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public String getListKey() {
		return listKey;
	}

	public void setListKey(String listKey) {
		this.listKey = listKey;
	}

	public String getListValue() {
		return listValue;
	}

	public void setListValue(String listValue) {
		this.listValue = listValue;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getHeaderKey() {
		return headerKey;
	}

	public void setHeaderKey(String headerKey) {
		this.headerKey = headerKey;
	}

	public String getHeaderValue() {
		return headerValue;
	}

	public void setHeaderValue(String headerValue) {
		this.headerValue = headerValue;
	}
	public Object getPermParentCode() {
		return permParentCode;
	}
	public void setPermParentCode(Object permParentCode) {
		this.permParentCode = permParentCode;
	}
}
