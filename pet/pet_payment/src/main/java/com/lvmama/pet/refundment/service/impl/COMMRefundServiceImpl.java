package com.lvmama.pet.refundment.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;

import com.bocom.netpay.b2cAPI.BOCOMB2CClient;
import com.bocom.netpay.b2cAPI.BOCOMB2COPReply;
import com.bocom.netpay.b2cAPI.OpResult;
import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.comm.vo.RefundmentToBankInfo;

public class COMMRefundServiceImpl implements BankRefundmentService {
	/**
	 * LOG.
	 */
	private static final Logger LOG = Logger.getLogger(AlipayRefundServiceImpl.class);

	@Override
	public BankReturnInfo refund(RefundmentToBankInfo info) {
		BankReturnInfo refumentinfo =new BankReturnInfo();
		//商户操作员号
		String operator = PaymentConstant.getInstance().getProperty("COMM_OPERATOR");
		
		String order = info.getPaymentTradeNo();
		String orderdate = DateUtil.formatDate(info.getCallbackTime(),"yyyyMMdd");
		String amount = String.valueOf(PriceUtil.convertToYuan(info.getRefundAmount()));
		//退款备注用于每次退款交易的流水号（每次退款唯一）
		String comment = SerialUtil.generate24ByteSerialAttaObjectId(info.getOrderId());
		String code ;
		String err ;
		String msg ;
		BOCOMB2CClient client = new BOCOMB2CClient();
		String b2cMerchantXML = PaymentConstant.getInstance().getProperty("COMM_CONFIG_PATH");
		int ret = client.initialize(b2cMerchantXML);
		if(ret != 0){
			LOG.error("init error: "+client.getLastErr());
			refumentinfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
			refumentinfo.setCode(ret+"");
			refumentinfo.setCodeInfo(client.getLastErr());
			return refumentinfo;
		} else {
			//退款录入
			LOG.info("operator: " + operator);
			LOG.info("order: " + order);
			LOG.info("orderdate: " +orderdate);
			LOG.info("amount: " + amount);
			LOG.info("comment: " + comment);
			BOCOMB2COPReply rep = client.DirectRefund(operator,order,orderdate,amount,comment);
			if (rep == null){  
				err = client.getLastErr();
				LOG.info("error message: "+err);
			}else{
				//得到交易返回码
				code = rep.getRetCode();
				err = rep.getLastErr();
				msg = rep.getErrorMessage();
				LOG.info("transaction code: "+code);
				LOG.info("transaction message: "+msg);
				boolean isSuccess = "0".equals(code);
				if(isSuccess){//表示交易成功
					OpResult opr = rep.getOpResult();
					//退款流水号
					String serial = opr.getValueByName ("serial");
					//退款账号
					String account = opr.getValueByName ("account");
			
					LOG.info("refundment serial: "+serial);
					LOG.info("refundment account: "+account);
				}
				refumentinfo.setCode(code);
				refumentinfo.setCodeInfo(msg);
				if(isSuccess){
					refumentinfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.SUCCESS.name());
				}
				else{
					refumentinfo.setSuccessFlag(Constant.PAY_REFUNDMENT_SERIAL_STATUS.FAIL.name());
				}
			}
		}
		return refumentinfo;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		COMMRefundServiceImpl cs = new COMMRefundServiceImpl();
		RefundmentToBankInfo info = new RefundmentToBankInfo();
		info.setPaymentId(101L);
		info.setCreateTime(new Date());
		info.setRefundAmount(100L);
		cs.refund(info);
	}

}
