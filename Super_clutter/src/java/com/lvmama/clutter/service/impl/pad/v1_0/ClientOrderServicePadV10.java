package com.lvmama.clutter.service.impl.pad.v1_0;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.service.client.v3_1.ClientOrderServiceV31;
import com.lvmama.clutter.service.client.v4_0.ClientOrderServiceV40;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.RefundUtils;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderChannel;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.ord.TravelDescriptionService;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.bee.vo.ord.PriceInfo;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.client.EContractClient;
import com.lvmama.comm.pet.po.client.ClientCmtLatitude;
import com.lvmama.comm.pet.po.client.ClientOrderReport;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.vo.favor.FavorResult;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;

public class ClientOrderServicePadV10 extends ClientOrderServiceV40{
	private static final Log log = LogFactory.getLog(ClientOrderServicePadV10.class);
	@Override
	public Map<String, Object> commitOrder(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("userNo","firstChannel","secondChannel","branchItem","visitTime","personItem","udid",param);
		String lvversion = (String)param.get("lvversion");
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String branchItem = param.get("branchItem").toString();
		String visitTime = param.get("visitTime").toString();
		String leaveTime = param.get("leaveTime")==null?null:param.get("leaveTime").toString();
		String personItem = param.get("personItem").toString();
		String udid = param.get("udid").toString();
		String userId = param.get("userNo").toString();
		String firstChannel = param.get("firstChannel").toString();
		String secondChannel = param.get("secondChannel").toString();
		String couponCode = param.get("couponCode")==null?null:param.get("couponCode").toString().toUpperCase();
		
		boolean isTodayOrder  = false;
		if(param.get("todayOrder")!=null){
			isTodayOrder = Boolean.valueOf(param.get("todayOrder").toString());
		}
		
		
		String[] branchArray = branchItem.split("_");
		String[] personArray = personItem.split(":");
		if(branchArray.length==0){
			throw new RuntimeException("类别项构建错误!");
		}
		
		if(personItem.length()==0){
			throw new RuntimeException("person项构建错误!");
		}
		
	
		BuyInfo createOrderBuyInfo = new BuyInfo();
		Map<String,Object> buildMap = this.setOrderItems(branchArray, isTodayOrder, visitTime, leaveTime, createOrderBuyInfo,couponCode);
		/**
		 * 是否包含保险
		 */
		boolean hasInsurance = (Boolean) buildMap.get("hasInsurance");
		ProdProduct mainPoduct = (ProdProduct)buildMap.get("mainPoduct");
		
		
		/**
		 * 设置设备预订次数
		 */
		if(isTodayOrder){
			ClientOrderReport cor = new ClientOrderReport();
			cor.setChannel(firstChannel+"_"+secondChannel);
			cor.setUdid(udid);
			createOrderBuyInfo.setClientOrderReport(cor);
		}
		
		log.info("client order "+param.get("lvversion")+" "+param.get("firstChannel")+"mainproduct id is "+mainPoduct.getProductId());
		
		/**
		 * 添加客户端3.1对于下单多返现的支持
		 */
		Long appVersion = 0l;
		if(lvversion!=null){
			appVersion = Long.valueOf(lvversion.replaceAll("[.]", ""));
		}
		if(mainPoduct.isPaymentToLvmama()&&lvversion!=null && "Y".equals(mainPoduct.getIsRefundable())){
			log.info("client order  isRefundabled");
			if(this.canCashRefund(param)){
				log.info("client order isRefundabled "+appVersion +" "+param.get("firstChannel"));
				
				//总共返现
				long clientCommentRefundAmount = (Long) buildMap.get("clientCommentRefundAmount");
				long moreMobileRefund = RefundUtils.getMobileRefundFen(clientCommentRefundAmount, mainPoduct.getProductType());
				createOrderBuyInfo.setClientCommentRefundAmount(moreMobileRefund);
				
//				if(mainPoduct.isTicket()){
//					createOrderBuyInfo.setClientCommentRefundAmount(ClutterConstant.getMobileTiketRefund());
//				} else if(mainPoduct.isRoute()){
//					createOrderBuyInfo.setClientCommentRefundAmount(ClutterConstant.getMobileRouteRefund());
//				}
			}
		}
		
		
		log.info("client order ClientCommentRefundAmount "+createOrderBuyInfo.getClientCommentRefundAmount());
		
		FavorResult fr =  favorService.calculateFavorResultByBuyInfo(createOrderBuyInfo);
		createOrderBuyInfo.setFavorResult(fr);
		if(!StringUtil.isEmptyString(couponCode)){
			PriceInfo priceInfo = orderServiceProxy.countPrice(createOrderBuyInfo);
			this.throwValidateCouponInfo(fr, priceInfo);
		}
		
		/**
		 * 构建联系人相关数据,先验证优惠券 后处理联系人游玩人相关数据。
		 */
		List<Person> personList = new ArrayList<Person>();
		this.personLogic(personArray, createOrderBuyInfo,personList);
		
		
		
		List<Person> orderPersons = createOrderBuyInfo.getPersonList();
		super.saveUsrReceivers(userId, orderPersons);
		
		/**
		 * 
		 *  如果是目的地自由行并且不包含保险 并且只有第一游玩人，那么把联系人保存为游客
		 *  只对ipad 的110 版本生效
		 */

		if((appVersion>=110||Constant.MOBILE_PLATFORM.WP8.name().equals(firstChannel))&&mainPoduct.isOnlyFirstTravellerInfoOptionNotEmpty()&&!hasInsurance){
			List<Person> persons = createOrderBuyInfo.getPersonList();
			Person ctPerson = null;
			for (Person person : persons) {
				if(Constant.ORD_PERSON_TYPE.CONTACT.name().equals(person.getPersonType())){
					ctPerson = person;
				}
			}
			
			if(ctPerson!=null){
				Person travelPerson = new Person();
				BeanUtils.copyProperties(ctPerson, travelPerson);
				travelPerson.setPersonType(Constant.ORD_PERSON_TYPE.TRAVELLER.name());
				persons.add(travelPerson);
			}
		} 
		
		/**
		 * 处理特殊产品
		 */
		if (5521 == mainPoduct.getProductId().longValue()) {
			Person p = super.getRecievePerson(orderPersons);
			if(p!=null){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("productId", mainPoduct.getProductId());
				params.put("userId", userId);
				params.put("contactMobile", p.getMobile());
				params.put("productLimit", "true");
				params.put("beginTime", DateUtil.formatDate(
						DateUtil.getFirstdayOfMonth(new Date()),
						"yyyy-MM-dd"));
				params.put("endTime", DateUtil.formatDate(
						DateUtil.getLastdayOfMonth(new Date()),
						"yyyy-MM-dd"));
				ResultHandle isExisted = orderServiceProxy
						.checkCreateOrderLimitIsExisted(params);
				if (isExisted.isFail()) {
					throw new LogicException(isExisted.getMsg());
				}
			}
		}
		
		/**
		 * 检测是否是故宫产品并验证
		 * 
		 */
		super.checkGuGongRule(mainPoduct, createOrderBuyInfo, DateUtil.toDate(visitTime,"yyyy-MM-dd"));
		
		this.checkRoutePerson(param, mainPoduct, personList);
		createOrderBuyInfo.setPaymentTarget(mainPoduct.isPaymentToLvmama()?Constant.PAYMENT_TARGET.TOLVMAMA.name():Constant.PAYMENT_TARGET.TOSUPPLIER.name());
		if(Constant.CHANNEL.TOUCH.name().equals(firstChannel)){
			/**
			 * TOUCH 站通过losc它插入相关统计渠道。
			 */
			createOrderBuyInfo.setChannel(Constant.CHANNEL.TOUCH.name());
		} else {
			createOrderBuyInfo.setChannel(Constant.CHANNEL.CLIENT.name());
			/**
			 * 客户端必须是310的版本
			 */
			if(appVersion>=310){
				createOrderBuyInfo.setOrdOrderChannel(new OrdOrderChannel(null, firstChannel+"_"+secondChannel,String.valueOf(ClutterConstant.getMobileTiketRefund()!=0&&ClutterConstant.getMobileRouteRefund()!=0),null));
			} else {
				createOrderBuyInfo.setOrdOrderChannel(new OrdOrderChannel(null, firstChannel+"_"+secondChannel));
			}
		}
		
		createOrderBuyInfo.setUserId(param.get("userNo").toString());
		
		ResultHandle result=orderServiceProxy.checkOrderStock(createOrderBuyInfo); 
		
		if(result.isFail()){ 
			throw new LogicException(result.getMsg());
		}
		
		if(createOrderBuyInfo.getItemList()!=null && createOrderBuyInfo.getItemList().size()>0){
			//故宫门票每笔订单只能限定5张
			if(prodProductRoyaltyService.getRoyaltyProductIds().contains(createOrderBuyInfo.getItemList().get(0).getProductId())){
				int count=0;
				for(BuyInfo.Item item:createOrderBuyInfo.getItemList()){
					count=item.getQuantity()+count;
				}
				if(count>5){
					throw new LogicException("故宫门票一笔订单最多限购5张");
				}
			}
		}
		
		try {
			OrdOrder o = orderServiceProxy.createOrder(createOrderBuyInfo);
			/**
			 * 写日志
			 */
			super.saveMobileLog(param, o.getOrderId(), Constant.MOBILE_PERSISTENCE_LOG_OBJECT_TYPE.ORDER.name());
			resultMap.put("needEContract", o.isNeedEContract());
			resultMap.put("orderId", o.getOrderId());
			resultMap.put("oughtPayYuan", o.getOughtPayYuan());
			
			
			/**
			 * 等待1秒
			 */
			Thread.sleep(1000);
			super.sendZeroYuan(o);
		} catch (Exception ex){
			ex.printStackTrace();
			throw new LogicException("创建订单失败!");
		}
		
		return resultMap;
	}

	@Override
	public Map<String, Object> validateCoupon(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.validateCoupon(param);
	}

	@Override
	public void addfeedBack(String content, String email, String userId,
			String firstChannel) {
		// TODO Auto-generated method stub
		super.addfeedBack(content, email, userId, firstChannel);
	}

	@Override
	public String commitComment(UserUser users, CommonCmtCommentVO comment,
			List<ClientCmtLatitude> cmtLatitudeList) {
		// TODO Auto-generated method stub
		return super.commitComment(users, comment, cmtLatitudeList);
	}

	@Override
	public Map<String, Object> queryOrderWaitPayTimeSecond(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.queryOrderWaitPayTimeSecond(param);
	}

	@Override
	public Map<String, Object> getEContractInfo(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getEContractInfo(param);
	}

	@Override
	public Map<String, Object> cancelOrder(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.cancelOrder(param);
	}

	@Override
	public Map<String, Object> cancellOrder4Wap(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.cancellOrder4Wap(param);
	}

	@Override
	public String onlineSign(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return super.onlineSign(param);
	}


}
