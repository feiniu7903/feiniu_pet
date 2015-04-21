/**
 * 
 */
package com.lvmama.train.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.BaseVo;
import com.lvmama.comm.vo.train.Rsp;
import com.lvmama.passport.utils.WebServiceConstant;
import com.lvmama.train.TrainTicketSign;

/**
 * @author yangbin
 *
 */
@Results({
	@Result(name="errorPage",location="/WEB-INF/train/error.jsp")
})
public class TrainNotifyAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8577343610017523111L;

	protected String output(Rsp rsp){
		try {
			if(rsp.getStatus().getRet_code()==Constant.HTTP_SUCCESS){
				this.getResponse().getWriter().write(rsp.getRspJson());
				return null;
			}else{
				//getRequest().setAttribute("json", rsp.getRspJson());
				jsonContent = rsp.getRspJson();
				return "errorPage";
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String jsonContent;

	public String getJsonContent() {
		return jsonContent;
	}
	
	protected Rsp checkSign(){
		java.util.Enumeration<String> params = getRequest().getParameterNames();
		Map<String,String> map = new HashMap<String, String>();
		String sign=null;
		while(params.hasMoreElements()){
			String key= params.nextElement();
			if(!"sign".equals(key)){
				map.put(key, getRequestParameter(key));
			}else{
				sign = getRequestParameter(key);
			}
		}
		String url = getRequest().getRequestURI();
		url = url.replace(getRequest().getContextPath(), "");
		TrainTicketSign tts = new TrainTicketSign();
		try {
			String signResult = tts.sign(WebServiceConstant.getProperties("train.account.userKey"),"POST",url,map);
			if(!signResult.equals(sign)){
				return new Rsp(Constant.HTTP_SERVER_ERROR, 
						Constant.HTTP_SERVER_ERROR_MSG,
						new BaseVo(Constant.REPLY_CODE.SIGN_ERROR.getRetCode(), 
								  Constant.REPLY_CODE.SIGN_ERROR.getRetMsg()));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
