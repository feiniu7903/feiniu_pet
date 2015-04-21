package com.lvmama.comm.utils;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.vo.Constant.ORDER_VIEW_STATUS;
/**
 * 
 * @author shangzhengyuan
 * @category 1.0
 * @version 1.0
 * 在线预售权判断 
 */
public class OnLinePreSalePowerUtil{	
	public static boolean getOnLinePreSalePower(final OrdOrder order){
		//1.如果应付金额小于预售权上限并且系统使用在线预售权则进行预售权
		if(order.getOughtPayYuan()<= 10000){
			return true;
		}
		return false;
	}
	
	/**
	 * 取得下单流程进行状态
	 * @param order 订单
	 * @param preSalePowered 是否使用预授权
	 * @param signning 是否等待签约
	 * @return
	 */
	public static String orderProcessStatus(final OrdOrder order,final boolean preSalePowered,final boolean signning){
		//默认为支付等待页面
		String status = ORDER_VIEW_STATUS.UNVERIFIED.name();
		Boolean isSign = order.isEContractConfirmed();
		Boolean needSign = order.isNeedEContract();
		Boolean isAudit = order.isApprovePass();
		Boolean needAudit = order.isNeedResourceConfirm();
		Boolean isAmount = getOnLinePreSalePower(order);
		Boolean needPayment = order.isPayToLvmama();
		Boolean isPayment = order.isPaymentSucc();
		Boolean sign = needSign && !isSign;
		Boolean audit = needAudit && !isAudit;
		Boolean payment = needPayment && !isPayment;
		Boolean isPaymentChannelLimit=order.isPaymentChannelLimit();
		if(sign && !signning){
			return ORDER_VIEW_STATUS.UNSIGNED.name();
		}else if(sign && signning){
			return ORDER_VIEW_STATUS.SIGNED.name();
			//qjh(normalPay资源无需确认的产品在已过最晚取消时间后下单，使用正常支付渠道支付，无需使用预授权支付。)
		}else if(order.getOughtPay()!=0
				&&payment 
				&& ((audit && isAmount && preSalePowered)||StringUtils.equalsIgnoreCase(order.getNeedPrePay(), "true")) 
				&& !isPaymentChannelLimit){
			return ORDER_VIEW_STATUS.PREPAYED.name();
		}else if(audit){
			return ORDER_VIEW_STATUS.UNVERIFIED.name();
		}else if(payment){
			return ORDER_VIEW_STATUS.UNPAY.name();
		}else if(!sign && !audit && !payment){
			return ORDER_VIEW_STATUS.FINISHED.name();
		}
		return status;
	}
}
