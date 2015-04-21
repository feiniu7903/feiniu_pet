
package com.lvmama.pet.payment.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PaymentQueryReturnInfo;
import com.lvmama.comm.pet.service.pay.PaymentQueryService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.utils.UnionUtil;
import com.lvmama.pet.vo.PaymentErrorData;
/**
 * 银联的支付查询.
 * @author zhangjie
 *
 */
public class UnionpayQueryServiceImpl implements PaymentQueryService {
	/**
	 * LOG.
	 */
	private static final Logger LOG = Logger.getLogger(UnionpayQueryServiceImpl.class);
	
	private String version = PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_VERSION");
	private String charset = PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_CHARSET");
	private String transType = PaymentConstant.getInstance().getProperty("UNIONPAY_TRANSTYPE_PAY");
	private String merId = PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_MERID");
	private String payQueryUrl = PaymentConstant.getInstance().getProperty("UNIONPAY_TRANSTYPE_QUERY_URL");
	private String key = PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_KEY");

	@Override
	public PaymentQueryReturnInfo paymentStateQuery(PayPayment info) {
		
		String[] valueVo = new String[]{
				this.version,
				this.charset,
				this.transType,
				this.merId,
				info.getPaymentTradeNo(),
				DateUtil.formatDate(info.getCreateTime(), "yyyyMMddHHmmss"),
				""
		};

		LOG.info("PaymentQueryReturnInfo postData : "+valueVo.toString());
		
		String res = UnionUtil.getUnionPayQueryRes(key, payQueryUrl, valueVo);

		LOG.info("PaymentQueryReturnInfo response:" + res );
		
		return responseInfo(res);
	}
	
	private PaymentQueryReturnInfo responseInfo(String res) {
		PaymentQueryReturnInfo paymentQueryReturnInfo = new PaymentQueryReturnInfo();
		if (res != null && !"".equals(res)) {
			String[] arr = UnionUtil.getResArr(res);
			if(UnionUtil.checkSecurity(key, arr)){//验证签名
				String respCode = UnionUtil.getRespCode(arr);//获取请求状态
				if("00".equals(respCode)){
					String queryResult = UnionUtil.getQueryResultCode(arr);//获取支付状态
					if("0".equals(queryResult)){
						Map<String, String> map = UnionUtil.getQuerySuccessAllCode(arr);
						paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.PAYED.getCode());
						paymentQueryReturnInfo.setCodeInfo("支付成功");
						paymentQueryReturnInfo.setCallbackInfo(map.get("respMsg"));
						paymentQueryReturnInfo.setCallbackTime(DateUtil.getDateByStr(map.get("respTime"), "yyyyMMddHHmmss"));
						paymentQueryReturnInfo.setGatewayTradeNo(map.get("qid"));
						paymentQueryReturnInfo.setRefundSerial(map.get("qid"));
					}else if("1".equals(queryResult)){
						paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL_PAY.getCode());
						paymentQueryReturnInfo.setCodeInfo("支付失败");
					}else if("2".equals(queryResult)){
						paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.WAIT_PAY.getCode());
						paymentQueryReturnInfo.setCodeInfo("支付等待中");
					}else if("3".equals(queryResult)){
						paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.NO_PAYMENT.getCode());
						paymentQueryReturnInfo.setCodeInfo("无此支付记录");
					}else{
						paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL.getCode());
						paymentQueryReturnInfo.setCodeInfo("查询失败");
					}
				}else{
					paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL.getCode());
					paymentQueryReturnInfo.setCodeInfo(PaymentErrorData.getInstance().getErrorMessage(Constant.PAYMENT_GATEWAY.CHINAPAY_PRE.name(), respCode));
				}
			}
		}else{
			paymentQueryReturnInfo.setCode(Constant.PAYMENT_QUERY_STATUS.FAIL.getCode());
			paymentQueryReturnInfo.setCodeInfo("查询失败！");
		}
		return paymentQueryReturnInfo;
	}

	public String getTransType() {
		return transType;
	}

}
