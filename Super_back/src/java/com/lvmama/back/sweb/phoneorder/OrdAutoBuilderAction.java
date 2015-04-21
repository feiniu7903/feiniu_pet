/**
 * 
 */
package com.lvmama.back.sweb.phoneorder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.SynchronizedLock;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;

/**
 * 专门用来给分销生成订单
 * @author lancey
 *
 */
@Results({
	@Result(name="input",location="/WEB-INF/pages/back/phoneorder/auto_build_order.jsp")
})
public class OrdAutoBuilderAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1149296891801199696L;
	
	private OrderService orderServiceProxy;
	private UserUserProxy userUserProxy;
	private ProdProductBranchService prodProductBranchService;
	private PayPaymentService payPaymentService;
	private TopicMessageProducer resourceMessageProducer; 
	
	
	private Long prodBranchId;//产品类别
	private Long count;//生成订单数量
	private String contactName; //联系人
	private String contactMobile;//联系人手机号
	private String booker="银联旅游卡分销专用";//银联旅游卡分销专用
	//private String booker="yuzhibing1";
	private final String channel=Constant.CHANNEL.EXPORT_DIEM.name();
	

	@Action("/phoneOrder/autoBuilder")
	public String goForm(){
		return INPUT;
	}
	
	@Action("/phoneOrder/saveAutoBuilder")
	public void submit(){
		JSONResult result = new JSONResult();
		try{
			BuyInfo buyInfo = new BuyInfo();
			buyInfo.setChannel(channel);
			Date date = prodProductBranchService.selectNearBranchTimePriceByBranchId(prodBranchId);
			if(date==null){
				throw new IllegalArgumentException("当前类别不存在可售的时间价格表");
			}
			ProdProductBranch prodBranch = prodProductBranchService.getProductBranchDetailByBranchId(prodBranchId, date, true);
			if(prodBranch==null){
				throw new IllegalArgumentException("类别不存在或不存在可售的时间价格表");
			}
			
			UserUser user = userUserProxy.getUsersByIdentity(booker,UserUserProxy.USER_IDENTITY_TYPE.USER_NAME);
			if(user==null){
				throw new IllegalArgumentException("下单人不存在");
			}
			String operatorName=getOperatorNameAndCheck();
			buyInfo.setIsAperiodic(prodBranch.getProdProduct().getIsAperiodic());
			
			BuyInfo.Item item = new BuyInfo.Item();
			item.setProductId(prodBranch.getProductId());
			item.setProductBranchId(prodBranch.getProdBranchId());
			item.setIsDefault("true");
			item.setQuantity(1);
			item.setVisitTime(date);
			List<BuyInfo.Item> list = new ArrayList<BuyInfo.Item>();
			list.add(item);
			buyInfo.setItemList(list);
			
			buyInfo.setMainProductType(prodBranch.getProdProduct().getProductType());
			buyInfo.setMainSubProductType(prodBranch.getProdProduct().getSubProductType());
			if(prodBranch.getProdProduct().isPaymentToLvmama()){
				buyInfo.setPaymentTarget(Constant.PAYMENT_TARGET.TOLVMAMA.name());
			}else if(prodBranch.getProdProduct().isPaymentToSupplier()){
				buyInfo.setPaymentTarget(Constant.PAYMENT_TARGET.TOSUPPLIER.name());
			}
			
			buyInfo.setTodayOrder(false);
			buyInfo.setSelfPack("false");
			
			List<Person> personList = new ArrayList<Person>();
			Person person = new Person();
			person.setName(contactName);
			person.setMobile(contactMobile);
			person.setPersonType(Constant.ORD_PERSON_TYPE.CONTACT.name());
			personList.add(person);
			
			buyInfo.setPersonList(personList);
			
			buyInfo.setUserId(user.getUserId());
			
			int resultCount=create(buyInfo,operatorName);
			result.put("resultCount", resultCount);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	
	private int create(BuyInfo buyInfo,String operatorName){
		int resultCount=0;
		for(int i=0;i<count;i++){
			try{
				OrdOrder order = orderServiceProxy.createOrderWithOperatorId(
						buyInfo, operatorName);
				
				if(order.isNormal()&&order.isUnpay()&&order.isPayToLvmama()){
					try{
						Thread.sleep(2*1000);//等待2秒，避免消息同时发出
					}catch(InterruptedException exx){
						
					}
					paymentOrder(order,operatorName);
					resultCount++;
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return resultCount;
	}
	
	
	private boolean paymentOrder(OrdOrder order,String operatorName) {
		PayPayment payPayment = new PayPayment();
		payPayment.setObjectId(order.getOrderId());
		payPayment.setSerial(payPayment.geneSerialNo());
		String key = "PAYMENT_DISTRIBUTION_ACTION" + payPayment.getSerial();
		if (SynchronizedLock.isOnDoingMemCached(key)) {
			return false;
		}
		try {
			Date clllbackTime = new Date();
			
			payPayment.setCallbackInfo("分销支付");
			payPayment.setGatewayTradeNo(DateUtil.formatDate(clllbackTime, "yyyyMMddHHmmssSSS")+order.getOrderId());
			payPayment.setPaymentTradeNo(payPayment.getGatewayTradeNo());
			payPayment.setCallbackTime(clllbackTime);
			payPayment.setCreateTime(clllbackTime);
			payPayment.setPaymentGateway("FENXIAO_"+channel);
			payPayment.setAmount(order.getOughtPay());
			payPayment.setOperator(operatorName);
			payPayment.setBizType(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.getCode());
			payPayment.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
			payPayment.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
			payPayment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
			

			Long paymentId = payPaymentService.savePayment(payPayment);
			resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(paymentId));
		} finally {
			SynchronizedLock.releaseMemCached(key);
		}
		return true;

	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}

	
}
