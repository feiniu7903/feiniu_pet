package com.lvmama.passport.processor.impl;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.UpdateContentProcessor;
import com.lvmama.passport.processor.impl.client.gmedia.GmediaServiceClient;
import com.lvmama.passport.processor.impl.client.gmedia.GmediaUtil;
import com.lvmama.passport.processor.impl.client.gmedia.model.Body;
import com.lvmama.passport.processor.impl.client.gmedia.model.Head;
import com.lvmama.passport.processor.impl.client.gmedia.model.Request;
import com.lvmama.passport.processor.impl.client.gmedia.model.Response;
import com.lvmama.passport.processor.impl.util.OrderUtil;

/**
 * 银河第三方接口实现
 * 
 * @author chenlinjun
 * 
 */
public class GmediaProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor, UpdateContentProcessor {
	private static final Log log = LogFactory.getLog(GmediaProcessorImpl.class);
	private static final String STATU="100";
	private static Map<String, String> OrderErrorMap = new HashMap<String, String>();
	
	@Override
	public Passport apply(PassCode passCode) {
		log.info("Gmedia Apply Code Request :"+passCode.getSerialNo());
		GmediaServiceClient client = GmediaServiceClient.getClientInstance();
		String uuid=passCode.getSerialNo();
		String date=GmediaUtil.dateToString(passCode.getCreateTime());
		String timeStamp=date;
		String dealTime=date;
		//请求头信息
		Head head=new Head(passCode);
		head.setSequenceId(uuid);
		head.setTimeStamp(timeStamp);
		head.setDealTime(dealTime);
		head.setSigned(head.makeSignedForApplyCode());
		//消息体信息
		Body body=new Body(passCode);
		body.setTimeStamp(timeStamp);
		body.setDealTime(dealTime);
		//整个请求信息
		Request request=new Request();
		request.setHead(head);
		request.setBody(body);
		Passport passport=new Passport();
		Long startTime = 0L;
		try{
			String result=client.execute(request.toApplayCodeRequestXml(), "applyCodeReq");
			log.info("Gmedia ApplyCode Reqeust Result:"+result);
			//String result=StringUtil.getTestData();
			//返回值处理
			startTime = System.currentTimeMillis();
			Response response=GmediaUtil.getResponse(result);
			log.info("Gmedia Apply serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			head=response.getHead();
			body=GmediaUtil.getResponseBody(response.getBody());
			passport.setSerialno(passCode.getSerialNo());
			passport.setCode(body.getTCode());
			passport.setAddCode(body.getAssistCode());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.LVMAMA.name());
			passport.setSendOrderid(true);
			passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
			
			if (STATU.equals(head.getStatusCode().trim())) {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			}else{
				String statusCode=head.getStatusCode().trim();
				log.info("Gmedia Applay Result status: " + statusCode);
				passport.setComLogContent("GmediaProcessorImpl_apply_Received an abnormal status code " + statusCode + getOrderErrorMsg(statusCode));
				this.reapplySet(passport, passCode.getReapplyCount());
			}
		}catch(Exception e){
			log.info("Gmedia Apply serialNo Error:" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.error("Gmedia ApplyCode Exception:",e);
			String error=e.getMessage();
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
			passport.setComLogContent(error);
			this.reapplySet(passport, passCode.getReapplyCount());
		}

		return passport;
	}

	/**
	 * 重新申请码处理
	 * @param passport
	 * @param error
	 */
	public void reapplySet(Passport passport,long times){
		OrderUtil.init().reapplySet(passport, times);
	}
	
	@Override
	public Passport destroy(PassCode passCode) {
		log.info("Gmedia Destroy Code Request :"+passCode.getSerialNo());
		GmediaServiceClient client = GmediaServiceClient.getClientInstance();
		Head head=new Head(passCode);
		String uuid=passCode.getSerialNo();
		String date=GmediaUtil.dateToString(passCode.getCreateTime());
		String timeStamp=date;
		String dealTime=date;
		head.setSequenceId(uuid);
		head.setTimeStamp(timeStamp);
		head.setDealTime(dealTime);

		head.setSigned(head.makeSignedForDestoyCode());
		Body body=new Body(passCode);
		body.setTimeStamp(timeStamp);
		body.setDealTime(dealTime);
		Request request=new Request();
		request.setHead(head);
		request.setBody(body);
		
		Passport passport=new Passport();
		passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
		passport.setSerialno(uuid);
		Long startTime = 0L;
		try{
			startTime = System.currentTimeMillis();
			String result=client.execute(request.toDestoyCodeRequestXml(),"sendDiscardReq");
			log.info("Gmedia destroy serialNo :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);
			log.info("Gmedia destroyCode Request Result ："+result);
			//返回值处理
			Response response=GmediaUtil.getResponse(result);
			head=response.getHead();
			body=GmediaUtil.getResponseBody(response.getBody());
			if(STATU.equals(head.getStatusCode().trim())) {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
			} else {
				passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
				passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
				String statusCode=head.getStatusCode().trim();
				passport.setComLogContent("GmediaProcessorImpl_destroy_Received an abnormal status code "+statusCode + getOrderErrorMsg(statusCode));
			}
		}catch(Exception e){
			log.error("Gmedia destroy serialNo Error :" +passCode.getSerialNo() +" UseTime:"+ (System.currentTimeMillis() - startTime)/1000);			log.error("Gmedia Destroy Exception:",e);
			passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
			passport.setComLogContent(e.getMessage());
			passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
		}
		return passport;
	}

	@Override
	public Passport update(PassCode passCode) {
		Passport passport=new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setUpdatePrintContent(passCode.getUpdateTerminalContent());
		passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
		return passport;
	}
	
	/**
	 * 取得失败原因信息
	 * @param code
	 * @return
	 */
	private static String getOrderErrorMsg(String code) {
		if(OrderErrorMap.isEmpty()) {
			OrderErrorMap.put("100", "成功");
			OrderErrorMap.put("711", "合作伙伴编号不存在或非法");
			OrderErrorMap.put("712", "凭证ID不存在");
			OrderErrorMap.put("713", "凭证已经作废");
			OrderErrorMap.put("714", "凭证已使用");
			OrderErrorMap.put("900", "签名验证出错");
			OrderErrorMap.put("901", "数据解密失败");
			OrderErrorMap.put("999", "其他错误");
		}
		String errorMsg=OrderErrorMap.get(code);
		if(errorMsg!=null){
			return OrderErrorMap.get(code);
		}else{
			return "未知错误";
		}
	}
}
