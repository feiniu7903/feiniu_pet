package com.lvmama.front.web.buy;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.utils.StackOverFlowUtil;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.front.web.BaseAction;

/**
 * 网上银行分期支付Ajax请求处理Action.
 * @author sunruyi
 * @see com.lvmama.common.UtilityTool
 * @see com.lvmama.common.ord.po.OrdOrder
 * @see com.lvmama.common.ord.service.OrderService
 * @see com.lvmama.common.utils.json.JSONResult
 * @see com.lvmama.common.utils.json.JSONResultException
 * @see com.lvmama.common.vo.Constant
 * @see com.lvmama.front.web.BaseAction
 */
public class AjaxInstalmentBankPayAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4420605169846023766L;
	/**
	 * 远程订单服务.
	 */
	private OrderService orderServiceProxy;
	/**
	 * 订单号.
	 */
	private Long orderId;
	/**
	 * 分期数.
	 */
	private int instalmentNumber;
	/**
	 * 手续费率.
	 */
	private float feeRate;
	/**
	 * 检查订单支付状态.
	 * <pre>
	 * 仅支付状态为UNPAY时允许分期支付.
	 * </pre>
	 */
	@Action(value="/ajax/checkIsCanInstalment")
	public void checkIsCanInstalment(){
		JSONResult result = new JSONResult();
		try {
			if(UtilityTool.isValid(orderId)){
				OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
				result.put("isCanInstalment", order.isCanInstalment());
			}
		} catch (Exception ex) {
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), ex);
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	/**
	 * 初始化分期计划.
	 * <pre>
	 * 计算首期付款金额、及以后各期付款金额.
	 * 计算公式：1、以后各期付款金额 = 订单应付金额 / 期数
	 *           2、首期付款金额 = 订单应付金额 - 以后各期付款金额  * 期数 + 手续费
	 *           3、手续费 = 订单应付金额 * 费率
	 * </pre>
	 */
	@Action(value="/ajax/initInstalmentPlan")
	public void initInstalmentPlan(){
		JSONResult result = new JSONResult();
		try {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			long oughtPay = (long)order.getOughtPayYuan();
			long instalmentFee = (long)(oughtPay * feeRate);
			long averagePayment = (long)(oughtPay / instalmentNumber);
			long downPayment = oughtPay - averagePayment * (instalmentNumber -1) + instalmentFee;
			long totalPayment = oughtPay + instalmentFee;
			result.put("totalPayment", totalPayment);//总计
			result.put("instalmentFee", instalmentFee);//手续费
			result.put("downPayment", downPayment);//第一期
			result.put("averagePayment", averagePayment);//以后每期
		} catch (Exception ex) {
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), ex);
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public int getInstalmentNumber() {
		return instalmentNumber;
	}
	public void setInstalmentNumber(int instalmentNumber) {
		this.instalmentNumber = instalmentNumber;
	}
	public float getFeeRate() {
		return feeRate;
	}
	public void setFeeRate(float feeRate) {
		this.feeRate = feeRate;
	}
}
