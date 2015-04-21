package com.lvmama.pet.payment.callback.data;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.vo.PaymentErrorData;

/**
 * 百付回调数据.
 * 
 * <pre>
 * * 详情请参考接口文档
 * </pre>
 * 
 * @author 李文战
 * @version Super 一期 
 * @since Super 一期
 * @see com.lvmama.common.vo.Constant
 */
public class ByPayCallbackData implements CallbackData {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(ByPayCallbackData.class);

	
	private String merchantId;	//商户代码	
	private String merchantOrderId;//商户订单号	
	private String merchantOrderAmt;	//商户订单金额	 
//	private String  settleDate;	//清算日期	
//	private String setlAmt;//	清算金额	
//	private String 	setlCurrency;//	清算币种
//	private String converRate;//	清算汇率	
	private String  cupsQid;//	Cups交易流水号
	private String cupsTraceNum;//	Cups系统跟踪号	
	private String cupsTraceTime;//	Cups系统跟踪时间	
	private String cupsRespCode;//	Cups响应码
	private String cupsRespDesc;//	Cups应答码描述	
	private String respCode;//	应答码
	private String respDesc;//	应答码描述	
	
	/**
	 * 百付支付返回来的数据.
	 * 
	 * @param map
	 *            参数map
	 * @return CMBCallbackData 百付数据对象
	 */
	public ByPayCallbackData(final Map<String, String> map) {
		this.setMerchantId(map.get("merchantId"));
		this.setMerchantOrderId(map.get("merchantOrderId"));
		this.setMerchantOrderAmt(map.get("merchantOrderAmt"));
		this.setCupsQid(map.get("cupsQid"));
		this.setCupsTraceNum(map.get("cupsTraceNum"));
		this.setCupsTraceTime(map.get("cupsTraceTime"));
		this.setCupsRespCode(map.get("cupsRespCode"));
		//如果百付的回调数据中cupsRespDesc为空  则通过cupsRespCode获取对应的中文解释
		if(StringUtils.isNotBlank(map.get("cupsRespDesc"))){
			this.setCupsRespDesc(map.get("cupsRespDesc"));	
		}
		else{
			String cupsRespDesc=PaymentErrorData.getInstance().getErrorMessage(Constant.PAYMENT_GATEWAY.TELBYPAY.name(), map.get("cupsRespCode"));
			this.setCupsRespDesc(cupsRespDesc);
		}
		
		if (LOG.isInfoEnabled()) {
			LOG.info("merchantId= " + this.getMerchantId());
			LOG.info("merchantOrderId= " + this.getMerchantOrderId());
			LOG.info("merchantOrderAmt=" + this.getMerchantOrderAmt());
			LOG.info("cupsQid=" + this.getCupsQid());
			LOG.info("cupsTraceNum=" + this.getCupsTraceNum());
			LOG.info("cupsTraceTime=" + this.getCupsTraceTime());
			LOG.info("cupsRespCode=" + this.getCupsRespCode());
			LOG.info("cupsRespDesc=" + this.getCupsRespDesc());
		}
	}
	/**
	 * 订单流水号
	 * 
	 * @return 订单流水号.
	 */
	public String getSerial() {
		return this.getMerchantOrderId();
	}

	/**
	 * 判断返回签名是否正确.
	 * 
	 * @return true;
	 */
	@Override
	public boolean checkSignature() {
		return true;
	}

	/**
	 * 是否支付成功.
	 * 
	 * @return 00代表支付成功，N代表支付不成功.
	 */
	@Override
	public boolean isSuccess() {
		return this.cupsRespCode.equals("00");
	}
	/**
	 * 返回信息.
	 * 
	 * @return 返回信息.
	 */
	@Override
	public String getMessage() {
		return null;
	}
	/**
	 * 网关支付号
	 * 
	 * @return 网关支付号.
	 */
	@Override
	public String getGatewayTradeNo() {
		return this.cupsQid;
	}
	/**
	 * 支付返回信息
	 * 
	 * @return 支付返回信息.
	 */
	@Override
	public String getCallbackInfo() {
		return this.cupsRespDesc;
	}
	/**
	 * 获取支付金额.
	 * 
	 * @return 支付金额，以分为单位.
	 */
	@Override
	public long getPaymentAmount() {
		return Long.valueOf(this.merchantOrderAmt);
	}
	/**
	 * 获取支付网关名称.
	 * 
	 * @return 支付网关名称.
	 */
	@Override
	public String getPaymentGateway() {
		return Constant.PAYMENT_GATEWAY.TELBYPAY.name();
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantOrderId() {
		return merchantOrderId;
	}

	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}

	public String getMerchantOrderAmt() {
		return merchantOrderAmt;
	}

	public void setMerchantOrderAmt(String merchantOrderAmt) {
		this.merchantOrderAmt = merchantOrderAmt;
	}

	public String getCupsQid() {
		return cupsQid;
	}

	public void setCupsQid(String cupsQid) {
		this.cupsQid = cupsQid;
	}

	public String getCupsTraceNum() {
		return cupsTraceNum;
	}

	public void setCupsTraceNum(String cupsTraceNum) {
		this.cupsTraceNum = cupsTraceNum;
	}

	public String getCupsTraceTime() {
		return cupsTraceTime;
	}

	public void setCupsTraceTime(String cupsTraceTime) {
		this.cupsTraceTime = cupsTraceTime;
	}

	public String getCupsRespCode() {
		return cupsRespCode;
	}

	public void setCupsRespCode(String cupsRespCode) {
		this.cupsRespCode = cupsRespCode;
	}

	public String getCupsRespDesc() {
		return cupsRespDesc;
	}

	public void setCupsRespDesc(String cupsRespDesc) {
		this.cupsRespDesc = cupsRespDesc;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespDesc() {
		return respDesc;
	}

	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	@Override
	public String getPaymentTradeNo() {
		return merchantOrderId;
	}
	@Override
	public String getRefundSerial() {
		return cupsQid;
	}
	@Override
	public Date getCallBackTime() {
		if(StringUtils.isNotBlank(cupsTraceTime) && cupsTraceTime.length()==14){
			return DateUtil.stringToDate(getCupsTraceTime(), "yyyyMMddHHmmss");
		}else{
			return new Date();
		}
	}
	
}
