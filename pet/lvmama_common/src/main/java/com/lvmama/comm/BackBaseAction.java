package com.lvmama.comm;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.ServletActionContext;

import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;
import com.opensymphony.xwork2.ActionContext;

/**
 * BackBaseAction
 * @author yuzhibing
 *
 */
public class BackBaseAction extends BaseAction {

	private static final long serialVersionUID = -7597794371786898017L;
	
	protected PermUser user;
	protected Long page = 1L;
	protected Page pagination;
	/**
	 * 获取当前登录用户
	 * @return
	 */
	public boolean isLogined() {
		PermUser user = getSessionUser();
		if (user!=null && user.getPermUserPass()){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 获取当前登录用户
	 * @return
	 */
	protected PermUser getSessionUser(){
		return (PermUser)ServletUtil.getSession(getRequest(), getResponse(), Constant.SESSION_BACK_USER);
	}
	
	public String getBasePath() {
		String path = this.getRequest().getContextPath();
		String basePath = getRequest().getScheme() + "://"
				+ getRequest().getServerName() + ":" + getRequest().getServerPort()
				+ path + "/";
		return basePath;
	}

	/**
	 * 获得session中的UserName
	 * 
	 * @return
	 */
	public String getSessionUserName() {
		PermUser permUser=getSessionUser();
		if(permUser!=null){
			return permUser.getUserName();
		}
		return "";
	}
	
	protected String getSessionUserNameAndCheck(){
		PermUser pu=getSessionUser();
		if(pu==null){
			throw new IllegalArgumentException("您没有登录或离开太久");
		}
		return pu.getUserName();
	}

	public HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}
	/**
	 * 获得session中的DepartmentId
	 * 
	 * @return
	 */
	public Long getSessionUserDepartmentId() {
		PermUser permUser=getSessionUser();
		if(permUser!=null){
			return permUser.getDepartmentId();
		}
		return 0L;
	}

	/**
	 * 获得session中的User RealName
	 * 
	 * @return
	 */
	public String getSessionUserRealName() {
		PermUser permUser=getSessionUser();
		if(permUser!=null){
			return permUser.getRealName();
		}
		return "";
	}

	protected <T> Page<?> initPage(){
		long pageSize=NumberUtils.toLong(getRequest().getParameter("perPageRecord"));
		long pageNo=NumberUtils.toLong(getRequest().getParameter("page"));
		pagination=new Page<T>(pageSize<1?10:pageSize, pageNo<1?1:pageNo);
		return pagination;
	}
	protected HttpServletResponse getResponseValue(){
	    HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
	    return response;
	}
	protected void outputToClient(String msg) throws Exception{
		HttpServletResponse response=this.getResponseValue();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(msg);
		out.flush();
		out.close();
	}
	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public Page<?> getPagination() {
		return pagination;
	}
	public void exportXLS(Map<String, Object> map, String path,String fileName){
		try {
			File templateResource = ResourceUtil.getResourceFile(path);
			String templateFileName = templateResource.getAbsolutePath();
			String destFileName =System.getProperty("java.io.tmpdir") + "/" + fileName + ".xls";
			XLSTransformer transformer = new XLSTransformer();
			transformer.transformXLS(templateFileName, map, destFileName);
			HttpServletResponse response = getResponse();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);   
			File file = new File(destFileName);
			InputStream inputStream=new FileInputStream(destFileName);  
			if (file != null && file.exists()) {
				OutputStream os=response.getOutputStream();  
	            byte[] b=new byte[1024];  
	            int length;  
	            while((length=inputStream.read(b))>0){  
	                os.write(b,0,length);  
	            }  
	            inputStream.close();  
			} else {
				throw new RuntimeException("Download failed!  path:"+path+" fileName:"+fileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 获取request中参数的集合,如果有多个值的对于只取其中一个
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getParameterMap() {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		Enumeration<String> parameternames = this.getRequest().getParameterNames();
		while (parameternames.hasMoreElements()) {
			String name = (String) parameternames.nextElement();
			parameterMap.put(name, this.getRequest().getParameter(name));
		}
		return parameterMap;
	}
}
