package com.lvmama.front.web.buy;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.ProdAvailableBonus;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdAvailableBonusService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.Constant;
import com.lvmama.front.web.BaseAction;

/**
 * 客户端奖金支付
 * 
 * @author taiqichao
 *
 */
public class AppBonusPaymentAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	
	private OrderService orderServiceProxy;
	
	private CashAccountService cashAccountService;
	
	private ProdAvailableBonusService prodAvailableBonusService;
	
	private TopicMessageProducer resourceMessageProducer;
	
	private Long orderId;
	
	private OrdOrder order;
	
	private CashAccountVO moneyAccount;
	
	
	/**
	 * 获得奖金支付信息
	 */
	@Action("/bonusAppPay/bonusPayInfo")
	public void bonusPayInfo(){
		
		if (null == orderId) {
			responseErrorMsg(APIErrorCode.PARAMETER_ERROR);
			return;
		}

		this.order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if (null == order) {
			responseErrorMsg(APIErrorCode.ORDER_CANT_NOT_BE_FOUND);
			return;
		}
		
		moneyAccount = cashAccountService.queryMoneyAccountByUserNo(order.getUserId());
		
		
		//可用奖金金额
		Long availableCashFen=0L;

		//判断用户是否可以使用奖金
		boolean cashUseBonusPay=cashAccountService.canUseBonusPay(moneyAccount.getUserId());
		if(cashUseBonusPay){
			//应付金额
			Long oughtPayFen=order.getOughtPay()-order.getActualPay()<=0?0:order.getOughtPay()-order.getActualPay();
			//可用奖金金额
			availableCashFen = getActBonus(oughtPayFen);
		}else{
			LOG.info("不可以使用奖金账户支付订单,order id:"+orderId+",user id:"+moneyAccount.getUserId());
		}
		
		//返回数据
		Map<String,Object> data=new HashMap<String,Object>();
		Map<String, String> info=new HashMap<String, String>();
		info.put("bonusPaidAmount", String.valueOf(order.getBonusPaidAmount()));//订单已使用奖金支付金额
		info.put("bonus", String.valueOf(availableCashFen));//最大可用
		info.put("actBonus", String.valueOf(availableCashFen));//实际可用
		info.put("bonusBalance", String.valueOf(moneyAccount.getTotalBonusBalance()));//奖金余额
		data.put("success_response",info);
		this.sendAjaxMsg(JSONObject.fromObject(data).toString());
	}

	/**
	 * 计算可用奖金金额
	 * @param oughtPayFen 应付金额(分)
	 * @return 可用奖金金额(分)
	 */
	private Long getActBonus(Long oughtPayFen) {
		Long availableCashFen = 0L;
		//只允许使用一次奖金
		if(order.getBonusPaidAmount()==null || order.getBonusPaidAmount()==0L) {
			//先用新奖金
	        availableCashFen = moneyAccount.getNewBonusBalance() > oughtPayFen ? oughtPayFen : moneyAccount.getNewBonusBalance();
	        //如果新奖金不够用，再用按规则用老奖金
	        if (availableCashFen < oughtPayFen) {
	            //先找产品子类
	        	OrdOrderItemProd prod=order.getMainProduct();
	            ProdAvailableBonus prodAvailableBonus = prodAvailableBonusService.getProdAvailableBonusBySubProductType(prod.getSubProductType());
	            //子类找不到，再找产品大类
	            if (prodAvailableBonus == null) {
	                prodAvailableBonus = prodAvailableBonusService.getProdAvailableBonusByMainProductType(prod.getProductType());
	            }
	            if (prodAvailableBonus != null) {
	            	Long prodAvailableBonusFen=PriceUtil.convertToFen(prodAvailableBonus.getAmount());
	            	availableCashFen += (prodAvailableBonusFen > moneyAccount.getBonusBalance() ? moneyAccount.getBonusBalance() : prodAvailableBonusFen);
	            }
	            availableCashFen = availableCashFen > oughtPayFen ? oughtPayFen : availableCashFen;
	        }
		}
		return availableCashFen;
	}
	
	/**
	 * 移动客户端奖金支付接口
	 * 
	 * @throws Exception
	 */
	@Action("/bonusAppPay/doPay")
	public void doPay(){
		
		if (null == orderId) {
			responseErrorMsg(APIErrorCode.PARAMETER_ERROR);
			return;
		}

		this.order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if (null == order) {
			responseErrorMsg(APIErrorCode.ORDER_CANT_NOT_BE_FOUND);
			return;
		}
		
		moneyAccount = cashAccountService.queryMoneyAccountByUserNo(order.getUserId());
		
		//判断用户是否可以使用奖金
		boolean cashUseBonusPay=cashAccountService.canUseBonusPay(moneyAccount.getUserId());
		
		if(cashUseBonusPay){
			//应付金额
			Long oughtPayFen=order.getOughtPay()-order.getActualPay()<=0?0:order.getOughtPay()-order.getActualPay();
			
			//可用奖金金额
			Long availableCashFen = getActBonus(oughtPayFen);
			if(availableCashFen<=0L){
				responseErrorMsg(APIErrorCode.BONUS_AVAILABLE_IS_ZERO);
				return;
			}
			
			//付款
	        if(moneyAccount.getTotalBonusBalance()>=availableCashFen) {
	             Long oldPayAmount = 0L;
	             Long newPayAmount = 0L;
	             if (availableCashFen <= moneyAccount.getNewBonusBalance()) {
	                 newPayAmount = availableCashFen;
	             } else {
	                 newPayAmount = moneyAccount.getNewBonusBalance();
	                 oldPayAmount = availableCashFen - newPayAmount;
	             }
	             // 从现金账户支付
	             List<Long> paymentIds = cashAccountService.payFromBonus(moneyAccount.getUserId(),orderId,Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.getCode(), oldPayAmount,newPayAmount);
	             // 发送支付成功消息
	             for(Long paymentId:paymentIds){
	                 resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(paymentId));
	             }
	        }
		}else{
			LOG.info("不可以使用奖金账户支付订单,order id:"+orderId+",user id:"+moneyAccount.getUserId());
		}
        
        //获取当前订单还需支付金额
		this.order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		Map<String,Object> data=new HashMap<String,Object>();
		Map<String, String> info=new HashMap<String, String>();
		info.put("order_ought_pay_amount", String.valueOf(order.getOughtPayFenLong()));
		data.put("success_response",info);
		this.sendAjaxMsg(JSONObject.fromObject(data).toString());
	}
	
	/**
	 * 错误代码
	 * 
	 * @author taiqichao
	 *
	 */
	public enum APIErrorCode{
		PARAMETER_ERROR("参数错误"),
		NETWORK_ERROR("网络请求错误"),
		BONUS_AVAILABLE_IS_ZERO("可用奖金金额为0"),
		ORDER_CANT_NOT_BE_FOUND("无法找到订单");
		
		private String msg;
		APIErrorCode(String msg){
			this.msg=msg;
		}
		public static String getMsg(String code){
			for(APIErrorCode item:APIErrorCode.values()){
				if(item.name().equals(code)) {
					return item.msg;
				}
			}
			return code;
		}
	}
	
	/**
	 * 响应错误消息
	 * @param code 错误代码 @see com.lvmama.front.web.buy.AppBonusPaymentAction.APIErrorCode
	 */
	private void responseErrorMsg(APIErrorCode code){
		this.responseErrorMsg(code.name(), APIErrorCode.getMsg(code.name()));
	}
	/**
	 * 响应错误消息
	 * @param code 错误代码
	 * @param msg 消息
	 */
	private void responseErrorMsg(String code,String msg){
		Map<String,Object> data=new HashMap<String,Object>();
		Map<String, String> info=new HashMap<String, String>();
		info.put("code", code);
		info.put("msg", msg);
		data.put("error_response",info);
		this.sendAjaxMsg(JSONObject.fromObject(data).toString());
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	
	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}

	public void setProdAvailableBonusService(
			ProdAvailableBonusService prodAvailableBonusService) {
		this.prodAvailableBonusService = prodAvailableBonusService;
	}

	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}	
	
	

}
