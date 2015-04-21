package com.lvmama.finance.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jxls.transformer.XLSTransformer;

import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;


/**
 * action基类
 * 
 * @author yanggan
 *
 */
public class BaseAction {

	public final String REDIRECT = "redirect:";
	
	/**
	 * 获取当前登录用户
	 * @return
	 */
	protected PermUser getSessionUser(){
		return (PermUser)this.getSession(com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
	}
	protected Object getSession(String key) {
		return ServletUtil.getSession(getRequest(), getResponse(), key);
	}
	protected HttpServletResponse getResponse() {
		return FinanceContext.getResponse();
	}
	
	public void exportXLS(Map<String, Object> map, String path,String fileName){
		try {
			File templateResource = ResourceUtil.getResourceFile(path);
			String templateFileName = templateResource.getAbsolutePath();
			String destFileName = Constant.getTempDir() + "/" + fileName ;
			XLSTransformer transformer = new XLSTransformer();
			transformer.transformXLS(templateFileName, map, destFileName);
			HttpServletResponse response = getResponse();
			if(fileName.indexOf(".xlsx") != -1	){
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			}else{
				response.setContentType("application/vnd.ms-excel");
			}
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
				throw new RuntimeException("下载失败");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public void sendAjaxResult(String json) {

		this.getResponse().setContentType("text/html;charset=UTF-8");
		this.getResponse().setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = this.getResponse().getWriter();
			out.write(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected HttpServletRequest getRequest(){
		return FinanceContext.getRequest();
	}
	protected HttpSession getSession(){
		return getRequest().getSession();
	}
}