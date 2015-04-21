/**
 * 
 */
package com.lvmama.comm.utils.json;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;


/**
 * json操作类，用于在action当中对需要返加json的操作，
 * 并且有多次变量传入的情况
 * 
 * @author yangbin
 *
 */
public class JSONResult {
	
	private static Log logger=LogFactory.getLog(JSONResult.class);

	private net.sf.json.JSONObject obj;
	private HttpServletResponse res;
	public JSONResult()
	{
		this.obj=new net.sf.json.JSONObject();
	}
	
	public JSONResult(HttpServletResponse res){
		this();
		this.res=res;	
	}
	
	
	public <T>void put(String key,T o)
	{
		try
		{
			obj.put(key, o);
		}catch(Exception ex)
		{		
			logger.info(ex);
		}
	}
	
	/**
	 * 捕捉代码当中的异常存入json当中
	 * @param ex
	 */
	public void raise(JSONResultException ex)
	{
		try{
			obj.put("msg", ex.getMessage());
			obj.put("code", ex.getErrorCode());
		}catch(Exception exx)
		{
			logger.info(ex);
		}
	}
	
	public JSONResult raise(String msg){
		obj.put("code", -1);
		obj.put("msg", msg);
		return this;
	}
	public JSONResult raise(ResultHandle result){
		return raise(result.getMsg());
	}
	
	public void raise(Exception ex){
		if(logger.isDebugEnabled()){
			ex.printStackTrace();
		}else if(ex instanceof SQLException || ex instanceof DataAccessException||ex instanceof NullPointerException){
			ex.printStackTrace();
		}
		obj.put("code", -1);
		obj.put("msg", ex.getMessage());
	}
	

	/**
	 * 输出json当中的对象内容到response
	 * 并且对code值不存在时赋为0
	 * @param res
	 */
	public void output(HttpServletResponse res)
	{
		res.setContentType("text/html;charset=UTF-8");
		res.setCharacterEncoding("UTF-8");
		try
		{
			if(!obj.has("code"))
			{
				obj.put("code", 0);
			}
			obj.put("success", obj.getInt("code")==0);
			if(!obj.getBoolean("success")){
				if(!obj.containsKey("msg")||StringUtils.isEmpty(obj.getString("msg"))){
					obj.put("msg", "错误未定义");
				}
			}
			res.getOutputStream().write(obj.toString().getBytes("UTF-8"));
		}catch(Exception ex)
		{			
			logger.info(ex);
		}
	}
	
	/**
	 * 输出json当中的对象内容到response
	 * 并且对code值不存在时赋为0
	 * @param res
	 */
	public void outputJSON(HttpServletResponse res)
	{
		res.setContentType("text/json;charset=UTF-8");
		res.setCharacterEncoding("UTF-8");
		try
		{
			if(!obj.has("code"))
			{
				obj.put("code", 0);
			}
			obj.put("success", obj.getInt("code")==0);
			if(!obj.getBoolean("success")){
				if(!obj.containsKey("msg")||StringUtils.isEmpty(obj.getString("msg"))){
					obj.put("msg", "错误未定义");
				}
			}
			res.getOutputStream().write(obj.toString().getBytes("UTF-8"));
		}catch(Exception ex)
		{			
			logger.info(ex);
		}
	}
	
	/**
	 * 输出json当中的对象内容到response
	 * 并且对code值不存在时赋为0
	 * @param res
	 */
	public void outPutNoMessage(HttpServletResponse res) {
		res.setContentType("text/html;charset=UTF-8");
		res.setCharacterEncoding("UTF-8");
		try {
			res.getOutputStream().write(obj.toString().getBytes("UTF-8"));
		} catch(Exception ex) {			
			logger.info(ex);
		}
	}
	
	public void output(){
		output(res);
	}
}
