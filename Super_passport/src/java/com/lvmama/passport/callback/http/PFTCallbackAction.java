package com.lvmama.passport.callback.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassDevice;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.passport.processor.UsedCodeProcessor;
import com.lvmama.passport.utils.WebServiceConstant;

public class PFTCallbackAction extends BackBaseAction{
	
	private static final long serialVersionUID = -7852247291751967604L;
	private static final String CHARSET_GBK = "GBK";
	private static final String SCENCE_MRSSJ = "mrssj";    //摩锐水世界
	private static final String SCENCE_JXLHS = "jxlhs";     //江西龙虎山
	private PassCodeService passCodeService;
	private UsedCodeProcessor usedCodeProcessor;
    
	@Action("/PFTCallback")
	public void pftCallback() {
		String result = "error";
	 try {
            Enumeration<String> enumeration = ServletActionContext.getRequest().getParameterNames();
            String jsonstr = (String) enumeration.nextElement();
			log.info("the request data is:  "+jsonstr);
			
		    JSONObject jsObj = JSONObject.fromObject(jsonstr);
		    String verifyCode = jsObj.getString("VerifyCode");
		    String order_no = jsObj.getString("OrderCall");
            String status = jsObj.getString("OrderState");
            log.info("status is:  "+status);
            log.info("order_no is:  "+order_no);
            log.info("VerifyCode is:  "+verifyCode);
			
			 //PrintWriter out = ServletActionContext.getResponse().getWriter();
			 //out.write("success");
	        if (verifyCode != null && status != null) {
	            if (checkSign(SCENCE_JXLHS,verifyCode)) {
	              if (StringUtils.equals(status, "1")) {
	                if (order_no != null) {
					  Map<String, Object> data = new HashMap<String, Object>();
					  data.put("serialNo", order_no.trim());
					  PassCode passCode = passCodeService.getPassCodeByParams(data);
	                  if (passCode != null) {
						List<PassPortCode> passPortCodeList = this.passCodeService.queryProviderByCode(passCode.getCodeId());
						PassPortCode passPortCode = passPortCodeList.get(0);
	                    if (passPortCode != null)
	                    {
	                    	Long targetId = passPortCode.getTargetId();
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("targetId", targetId);
							List<PassDevice> passDeviceList = this.passCodeService.searchPassDevice(params);
							Passport passport = new Passport();
							passport.setSerialno(passCode.getSerialNo());
							passport.setPortId(targetId);
							passport.setOutPortId(targetId.toString());
							if (passDeviceList != null && passDeviceList.size() > 0) {
								passport.setDeviceId(passDeviceList.get(0).getDeviceNo().toString());
							}
							passport.setChild("0");
							passport.setAdult("0");
							passport.setUsedDate(new Date());
							// 更新履行状态
							String code = usedCodeProcessor.update(passport);
							if ("SUCCESS".equals(code)) {
								result = "success";
							}
	                    }
	                  }
	                }
	              }
	              else if (StringUtils.equals(status, "2")) {
	                result = "success";
	              }
	              log.info("result: "+result);
	            }
	            result = "sign error";
	          }
	        result = "param can not be blank";
	        log.info("result: "+result);
	      }
	      catch (Exception e) {
	        result = "exception error";
	        this.log.info(e.getMessage());
	      }
	      sendAjaxMsg("success");
	    }
	
	@Action("/PFTMRSSJCallback")
	public void pftYJXSSJCallback() {
		String result = "error";
	 try {
            Enumeration<String> enumeration = ServletActionContext.getRequest().getParameterNames();
            String jsonstr = (String) enumeration.nextElement();
			log.info("the request data is:  "+jsonstr);
			
		    JSONObject jsObj = JSONObject.fromObject(jsonstr);
		    String verifyCode = jsObj.getString("VerifyCode");
		    String order_no = jsObj.getString("OrderCall");
            String status = jsObj.getString("OrderState");
            log.info("status is:  "+status);
            log.info("order_no is:  "+order_no);
            log.info("VerifyCode is:  "+verifyCode);
			
			 //PrintWriter out = ServletActionContext.getResponse().getWriter();
			 //out.write("success");
	        if (verifyCode != null && status != null) {
	            if (checkSign(SCENCE_MRSSJ,verifyCode)) {
	              if (StringUtils.equals(status, "1")) {
	                if (order_no != null) {
					  Map<String, Object> data = new HashMap<String, Object>();
					  data.put("serialNo", order_no.trim());
					  PassCode passCode = passCodeService.getPassCodeByParams(data);
	                  if (passCode != null) {
						List<PassPortCode> passPortCodeList = this.passCodeService.queryProviderByCode(passCode.getCodeId());
						PassPortCode passPortCode = passPortCodeList.get(0);
	                    if (passPortCode != null)
	                    {
	                    	Long targetId = passPortCode.getTargetId();
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("targetId", targetId);
							List<PassDevice> passDeviceList = this.passCodeService.searchPassDevice(params);
							Passport passport = new Passport();
							passport.setSerialno(passCode.getSerialNo());
							passport.setPortId(targetId);
							passport.setOutPortId(targetId.toString());
							if (passDeviceList != null && passDeviceList.size() > 0) {
								passport.setDeviceId(passDeviceList.get(0).getDeviceNo().toString());
							}
							passport.setChild("0");
							passport.setAdult("0");
							passport.setUsedDate(new Date());
							// 更新履行状态
							String code = usedCodeProcessor.update(passport);
							if ("SUCCESS".equals(code)) {
								result = "success";
							}
	                    }
	                  }
	                }
	              }
	              else if (StringUtils.equals(status, "2")) {
	                result = "success";
	              }
	              log.info("result: "+result);
	            }
	            result = "sign error";
	          }
	        result = "param can not be blank";
	        log.info("result: "+result);
	      }
	      catch (Exception e) {
	        result = "exception error";
	        this.log.info(e.getMessage());
	      }
	      sendAjaxMsg("success");
	    }	

	    public boolean checkSign(String scenceFlag,String verifyCode) throws Exception
	    {
	      //String signValue = sign(WebServiceConstant.getProperties("pft.agentId")+WebServiceConstant.getProperties("pft.agentPassword"));
	      String signValue = sign(this.getPFTAgentAcc(scenceFlag)+this.getPFTAgentPwd(scenceFlag));
	      this.log.info("md5 code is:  "+signValue);
	      if (StringUtils.equals(signValue, verifyCode)) {
	        return true;
	      }
	      return false;
	    }

	    public String sign(String secret)
	      throws IOException
	    {
	      byte[] bytes = encryptMD5(secret);

	      return byte2hex(bytes);
	    }

		private  byte[] encryptMD5(String data) throws IOException {
			byte[] bytes = null;
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				bytes = md.digest(data.getBytes(CHARSET_GBK));
			} catch (GeneralSecurityException gse) {
				throw new IOException(gse.getMessage());
			}
			return bytes;
		}

		private  String byte2hex(byte[] bytes) {
			StringBuilder sign = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				String hex = Integer.toHexString(bytes[i] & 0xFF);
				if (hex.length() == 1) {
					sign.append("0");
				}
				sign.append(hex);
			}
			return sign.toString();
		}
		
	    public String getPFTAgentAcc(String scenceFlag){
	    	//拼接景区类型名
	        String sceneTypeName = "pft"+scenceFlag+".agentId"; 
	    	return WebServiceConstant.getProperties(sceneTypeName);
	    }
	    
	    public String getPFTAgentPwd(String scenceFlag){
	    	//拼接景区类型名
	        String sceneTypeName = "pft"+scenceFlag+".agentPassword"; 
	    	return WebServiceConstant.getProperties(sceneTypeName);
	    }

	    public void setPassCodeService(PassCodeService passCodeService) {
	      this.passCodeService = passCodeService;
	    }

	    public void setUsedCodeProcessor(UsedCodeProcessor usedCodeProcessor) {
	      this.usedCodeProcessor = usedCodeProcessor;
	    }
}