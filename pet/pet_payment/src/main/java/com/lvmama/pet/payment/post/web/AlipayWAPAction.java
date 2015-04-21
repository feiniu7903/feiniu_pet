package com.lvmama.pet.payment.post.web;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.nuxeo.common.xmap.XMap;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.payment.post.data.AlipayWAPPostData;
import com.lvmama.pet.payment.post.data.PostData;
import com.lvmama.pet.utils.AlipayUtil;
import com.lvmama.pet.vo.AlipayWAPDirectTradeCreateRes;
import com.lvmama.pet.vo.AlipayWAPErrorCode;
import com.lvmama.pet.vo.AlipayWAPResponseResult;

public class AlipayWAPAction  extends PayAction {

	private static final long serialVersionUID = 1180581231180895031L;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	private AlipayWAPPostData alipayWAPPostData;
	
	@Action("/pay/alipayWAP")
	public void alipayWAP() {
		if (payment()) {
			try {
				LOG.info("alipayWAP request init data:"+alipayWAPPostData.getRequestParams());
				//获取授权令牌
				AlipayWAPResponseResult resResult = getRequestToken();
				log.info(StringUtil.printParam(resResult));
				String businessResult = "";
				if (resResult.isSuccess()) {
					businessResult = resResult.getBusinessResult();
				} else {
					//授权令牌错误
					String errorMessage="errorCode:" + resResult.getErrorMessage().getCode() + ",subErrorCode:" + resResult.getErrorMessage().getSubCode() + ",errorMsg:"
					+ resResult.getErrorMessage().getMsg() + ",errorDetail:" + resResult.getErrorMessage().getDetail();
					log.error(errorMessage);
					PayPayment payment = new PayPayment();
					payment.setPaymentTradeNo(alipayWAPPostData.getOutTradeNo());
					payment.setCallbackInfo(errorMessage);
					payment.setCallbackTime(new Date());
					getPayPaymentService().callBackPayPayment(payment, false);
					return ;
				}
				XMap xmap=new XMap();
				xmap.register(AlipayWAPDirectTradeCreateRes.class);
				AlipayWAPDirectTradeCreateRes directTradeCreateRes = (AlipayWAPDirectTradeCreateRes) xmap.load(new ByteArrayInputStream(businessResult
						.getBytes(alipayWAPPostData.getCharset())));
				String requestToken = directTradeCreateRes.getRequestToken();
				//封装交易参数
				Map<String, String> authParams = alipayWAPPostData.initExecuteParams(requestToken);
				String redirectURL = alipayWAPPostData.getReqUrl() + "?" + AlipayUtil.mapToUrl(authParams);
				log.info("redirectURL:"+redirectURL);
				//sendAjaxMsg(redirectURL);
				//getRequest().getRequestDispatcher(redirectURL).forward(getRequest(), getResponse());
				getResponse().sendRedirect(redirectURL);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
	}
	/**
	 * 发起获取授权令牌请求
	 * @author ZHANG Nan
	 * @return
	 * @throws Exception
	 */
	private AlipayWAPResponseResult getRequestToken() throws Exception {
		String invokeUrl = alipayWAPPostData.getReqUrl()  + "?";
		URL serverUrl = new URL(invokeUrl);
		HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.connect();
		String params = AlipayUtil.mapToUrl(alipayWAPPostData.getRequestParams());
		conn.getOutputStream().write(params.getBytes());
		InputStream is = conn.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		String responseStr = URLDecoder.decode(buffer.toString(),alipayWAPPostData.getCharset());
		conn.disconnect();
		return praseResult(responseStr);
	}
	/**
	 * 解析获取的授权令牌
	 * @author ZHANG Nan
	 * @param responseStr
	 * @return
	 * @throws Exception
	 */
	private AlipayWAPResponseResult praseResult(String responseStr) throws Exception {
		AlipayWAPResponseResult result = new AlipayWAPResponseResult();
		if (responseStr.contains("<err>")) {
			result.setSuccess(false);
			String businessResult = AlipayUtil.getParameter(responseStr, "res_error");
			// 转换错误信息
			XMap xmap=new XMap();
			xmap.register(AlipayWAPErrorCode.class);
			AlipayWAPErrorCode errorCode = (AlipayWAPErrorCode) xmap.load(new ByteArrayInputStream(businessResult.getBytes(alipayWAPPostData.getCharset())));
			result.setErrorMessage(errorCode);
		} else {
			result.setSuccess(true);
            result.setBusinessResult(AlipayUtil.getParameter(responseStr, "res_data"));
		}
		return result;
	}

	
	
	@Override
	PostData getPostData(PayPayment payPayment) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("objectName", super.getObjectName());
		alipayWAPPostData=new AlipayWAPPostData(payPayment,paramMap,getRequestParameter("cashierCode"));
		return alipayWAPPostData;
	}

	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.ALIPAY_WAP.name();
	}


	
	public AlipayWAPPostData getAlipayWAPPostData() {
		return alipayWAPPostData;
	}
	public void setAlipayWAPPostData(AlipayWAPPostData alipayWAPPostData) {
		this.alipayWAPPostData = alipayWAPPostData;
	}
	@Override
	String getPaymentTradeNo(Long randomId) {

		//充值标识
		String cashRecharge="";
		if(Constant.PAYMENT_BIZ_TYPE.CASH_ACCOUNT.name().equals(super.getBizType())){
			cashRecharge=Constant.ALIPAY_TRANSACTION_TYPE_PREFIX.R.name();
		}
		return cashRecharge+SerialUtil.generate24ByteSerialAttaObjectId(randomId);
	}
}