package com.lvmama.clutter.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.exception.NotFoundException;
import com.lvmama.clutter.model.MobileBranchItem;
import com.lvmama.clutter.model.MobilePersonItem;
import com.lvmama.clutter.service.IClientOrderService;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.EnumSeckillStatus;
import com.lvmama.clutter.utils.RefundUtils;
import com.lvmama.clutter.utils.SeckillUtils;
import com.lvmama.comm.bee.po.ord.OrdEContract;
import com.lvmama.comm.bee.po.ord.OrdEcontractSignLog;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderChannel;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.ord.TravelDescriptionService;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Coupon;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.BuyInfo.OrdTimeInfo;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.bee.vo.ord.PriceInfo;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.client.EContractClient;
import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.po.client.ClientOrderReport;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mobile.ClientSeckillService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.pet.vo.favor.FavorResult;
import com.lvmama.comm.pet.vo.mark.ValidateCodeInfo;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HotelUtils;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;

public  class ClientOrderServiceImpl extends AppServiceImpl implements IClientOrderService {
	
	
	
	private static final Log log = LogFactory.getLog(IClientOrderService.class);
	/**
	 * 订单消息服务. 
	 */
	private transient TopicMessageProducer orderMessageProducer;	
	/**
	 * 合同服务接口
	 */
	protected EContractClient contractClient;
	
	/**
	 * 订单行程固化
	 */
	protected TravelDescriptionService travelDescriptionService;
	
	/**
	 * 秒杀
	 */
	@Autowired
	protected ClientSeckillService clientSeckillService;
	
	@Override
	public Map<String,Object> commitOrder(Map<String,Object> param) {
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
		boolean usedWexinCoupon = false;
		boolean isTodayOrder  = false;
		if(param.get("todayOrder")!=null){
			isTodayOrder = Boolean.valueOf(param.get("todayOrder").toString());
		}
		
		
		String[] branchArray = branchItem.split("_");
		String[] personArray = personItem.split(":");
		if(branchArray.length==0){
			throw new RuntimeException("类别项构建错误!");
		}
		
		//秒杀产品校验数据库的库存
		String branchId_num = branchArray[0];
		if(!StringUtils.isEmpty(branchId_num)){
			String branchIdStr = branchId_num.split("-")[0];
			long branchId = Long.parseLong(branchIdStr);
			//是否秒杀产品
			// 秒杀产品校验缓存里的库存
			ProdSeckillRule prodSeckillRule = clientSeckillService
					.getSeckillRuleByBranchId(branchId);
			if (prodSeckillRule != null) {// 是否秒杀产品
				String seckillStatus = SeckillUtils.getSeckillStatus(prodSeckillRule);
				resultMap.put("seckillStatus", seckillStatus);
				if (EnumSeckillStatus.SECKILL_BEFORE.name().equals(seckillStatus)) {
					throw new NotFoundException("距离秒杀还有段时间哦，请稍后再抢！ ");
				} else if (EnumSeckillStatus.SECKILL_AFTER.name().equals(seckillStatus)
						|| EnumSeckillStatus.SECKILL_FINISHED.name().equals(
								seckillStatus)) {
					throw new NotFoundException("秒杀已结束，下次早点来哦！");
				} else {
					long waitPeopleCount = clientSeckillService
							.getWaitPeopleByMemcached(branchId, true, 1L);
					// 缓存是否有库存
					if (waitPeopleCount < 1) {
						throw new NotFoundException("秒杀已结束，下次早点来哦！");
					}
				}
			}
		}
		
		if(personItem.length()==0){
			throw new RuntimeException("person项构建错误!");
		}
		
	
		BuyInfo createOrderBuyInfo = new BuyInfo();
		Map<String,Object> buildMap = this.setOrderItems(branchArray, isTodayOrder, visitTime, leaveTime, createOrderBuyInfo,couponCode);
		usedWexinCoupon =Boolean.valueOf(buildMap.get("usedWexinCoupon").toString());
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
				//long moreMobileRefund = RefundUtils.getMobileRefundFen(clientCommentRefundAmount, mainPoduct.getProductType());
				createOrderBuyInfo.setClientCommentRefundAmount(clientCommentRefundAmount);
				//createOrderBuyInfo.setClientCommentRefundAmount(moreMobileRefund);
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
		this.saveUsrReceivers(userId, orderPersons);
		
		/**
		 * 处理门票需要游玩人的情况。
		 * 4.0.0以上的app 当第一游玩人不为空的时候 自动保存联系人为游玩人
		 * 4.0.0版本一下的app 只要需要填写游玩人，必须自动将联系人保存为游玩人
		 * 4.0.0 以上 如果是目的地自由行并且不包含保险 并且只有第一游玩人，那么把联系人保存为游客
		 */
		

		if((mainPoduct.isTicket()&&mainPoduct.isOnlyFirstTravellerInfoOptionNotEmpty())||mainPoduct.getTravellerInfoOptionsList()==null||(mainPoduct.isFreeness()&&mainPoduct.isOnlyFirstTravellerInfoOptionNotEmpty()&&!hasInsurance)){
			List<Person> persons = createOrderBuyInfo.getPersonList();
			if(!this.containtTravelInfo(persons)&&(this.isAndroidOrIphonePlatform(firstChannel)||Constant.MOBILE_PLATFORM.IPAD.name().equals(firstChannel)||Constant.MOBILE_PLATFORM.WP8.name().equals(firstChannel))){

				
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

		} 
		
		
		
		/**
		 * 处理特殊产品
		 */
		if (5521 == mainPoduct.getProductId().longValue()) {
			Person p = this.getRecievePerson(orderPersons);
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
		this.checkGuGongRule(mainPoduct, createOrderBuyInfo, DateUtil.toDate(visitTime,"yyyy-MM-dd"));
		
		this.checkRoutePerson(param, mainPoduct, personList);
		createOrderBuyInfo.setPaymentTarget(mainPoduct.isPaymentToLvmama()?Constant.PAYMENT_TARGET.TOLVMAMA.name():Constant.PAYMENT_TARGET.TOSUPPLIER.name());
		if(Constant.CHANNEL.TOUCH.name().equals(firstChannel)){
			/**
			 * TOUCH 站通过losc它插入相关统计渠道。
			 */
			createOrderBuyInfo.setChannel(Constant.CHANNEL.TOUCH.name());
		} else {
			createOrderBuyInfo.setChannel(Constant.CHANNEL.CLIENT.name());
			
			createOrderBuyInfo.setOrdOrderChannel(new OrdOrderChannel(null, firstChannel+"_"+secondChannel));
			
		}
		UserUser user = userUserProxy.getUserUserByUserNo(param.get("userNo").toString());
		Person bookPerson = new Person();
		if(user!=null){
			bookPerson.setName(user.getRealName());
			bookPerson.setMobile(user.getMobileNumber());
			bookPerson.setPersonType(Constant.ORD_PERSON_TYPE.BOOKER.name());
			List<Person> persons = createOrderBuyInfo.getPersonList();
			persons.add(bookPerson);
			
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
		
		//自助巴士班
		String prodAssemblyPoint = (String) param.get("prodAssemblyPoint");
		if(!StringUtils.isEmpty(prodAssemblyPoint)) {		
			createOrderBuyInfo.setUserMemo("上车地点："+prodAssemblyPoint);
		}
		
		try {
			OrdOrder o = orderServiceProxy.createOrder(createOrderBuyInfo);
			//this.
			/**
			 * 写日志
			 */
			super.saveMobileLog(param, o.getOrderId(), Constant.MOBILE_PERSISTENCE_LOG_OBJECT_TYPE.ORDER.name());
			if(usedWexinCoupon) {
				for (OrdOrderItemProd  item: o.getOrdOrderItemProds()) {
					super.addWexinOrderLog(param, item.getProdBranchId());
				}
			}

			
			resultMap.put("needEContract", o.isNeedEContract());
			resultMap.put("orderId", o.getOrderId());
			resultMap.put("oughtPayYuan", o.getOughtPayYuan());

			/**
			 * 等待1秒
			 */
			Thread.sleep(1000);
			this.sendZeroYuan(o);
		} catch (Exception ex){
			ex.printStackTrace();
			throw new LogicException("创建订单失败!");
		}
		
		return resultMap;
	}
	
	
	private boolean containtTravelInfo(List<Person> persons){
		for (Person person : persons) {
			if(Constant.ORD_PERSON_TYPE.TRAVELLER.name().equals(person.getPersonType())){
				return true;
			}
		}
		return false;
	}
	
	protected void checkGuGongRule(ProdProduct mainPoduct,BuyInfo createOrderBuyInfo,Date visiDate){
		/**
		 * 验证故宫门票身份证
		 */
		Place gugong = null;
		if(mainPoduct.getToPlace()==null){
			gugong = prodProductPlaceService.getToDestByProductId(mainPoduct.getProductId());
		}
		if(mainPoduct.isTicket() &&gugong!=null&&100441L==gugong.getPlaceId()){
			Person contact = null;
				for (Person person : createOrderBuyInfo.getPersonList()) {
					if(Constant.ORD_PERSON_TYPE.CONTACT.name().equals(person.getPersonType())){
						contact = person;
						break;
					}
				}	
				if(contact!=null){
					if( contact.getCertNo()==null){
						throw new LogicException("请输入联系人身份证");
					} else
						if(!StringUtil.isGuGongIdCardNo(contact.getCertNo())){
							throw new LogicException("身份证格式不正确");
						} else {
							Person p = this.getRecievePerson(createOrderBuyInfo.getPersonList());
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("productId",mainPoduct.getProductId());
							params.put("userId",createOrderBuyInfo.getUserId());
							params.put("certNo",p.getCertNo());
							params.put("gugongproductLimit", "true");
							params.put("visitTime",visiDate);
							ResultHandle isExisted = orderServiceProxy.checkCreateOrderLimitIsExisted(params);
							if(isExisted.isFail()) {
								throw new LogicException(isExisted.getMsg());
							}
						}
				}
			
		}
	}
	
	
	protected boolean canCashRefund(Map<String, Object> param){
		String lvversion = (String)param.get("lvversion");
		Long appVersion = Long.valueOf(lvversion.replaceAll("[.]", ""));
		log.info("client order  isRefundabled");
		return Constant.MOBILE_PLATFORM.IPAD.name().equals(param.get("firstChannel").toString())||appVersion>=310||Constant.CHANNEL.TOUCH.name().equals(param.get("firstChannel").toString())||Constant.CHANNEL.WP8.name().equals(param.get("firstChannel").toString());
	}
	

	protected Person getRecievePerson(List<Person> persons){
		for (Person person : persons) {
			if(Constant.ORD_PERSON_TYPE.CONTACT.name().equals(person.getPersonType())){
				return person;
			}
		}
		return null;
	}
	

	
	
	protected void saveUsrReceivers(String userNo,List<Person> persons){
		List<UsrReceivers> saveReceivers = new ArrayList<UsrReceivers>();
		List<UsrReceivers> receiversList = this.receiverUserService.loadUserReceiversByUserId(userNo);
		Map<String,List<UsrReceivers>> mapReceiver =  new HashMap<String, List<UsrReceivers>>();
		for (UsrReceivers usrReceivers : receiversList) {
			List<UsrReceivers> list = mapReceiver.get(usrReceivers.getReceiverName());
			if(list==null){
				list = new ArrayList<UsrReceivers>();
			}
			list.add(usrReceivers);
			mapReceiver.put(usrReceivers.getReceiverName(), list);

		}
		//log.info("mapReceiver size:"+mapReceiver.size());
		for (Person person : persons) {
			if(mapReceiver.get(person.getName())!=null){
				this.setUpdateReceivers(person,saveReceivers,userNo,mapReceiver);
			} else {
				saveReceivers.add(this.buildNewReceiver(person, userNo));
			}
			
		}
		if(!saveReceivers.isEmpty()){
			log.info("saveReceivers  size:"+saveReceivers.size());
			this.receiverUserService.createContact(saveReceivers, userNo);
		}
		
	}
	
	

	private void setUpdateReceivers(Person person,List<UsrReceivers> saveReceivers,String userNo,Map<String,List<UsrReceivers>> mapReceivers){
		for (UsrReceivers usrReceivers : mapReceivers.get(person.getName())) {
					if(StringUtil.isNotEmptyString(person.getCertType())){
						if(StringUtil.isNotEmptyString(person.getCertNo())){
							usrReceivers.setCardType(person.getCertType());
						}
					}
					if(StringUtil.isNotEmptyString(person.getCertNo())){
						usrReceivers.setCardNum(person.getCertNo());
					}
					if(StringUtil.isNotEmptyString(person.getGender())){
						usrReceivers.setGender(person.getGender());
					}
					if(person.getBrithday()!=null){
						usrReceivers.setBrithday(person.getBrithday());
					}
					usrReceivers.setReceiverName(person.getName());
					if(StringUtil.isNotEmptyString(person.getMobile())&&!person.getMobile().equals(usrReceivers.getMobileNumber())){
						usrReceivers.setMobileNumber(person.getMobile());
					}
					saveReceivers.add(usrReceivers);
		}
		
	} 


	protected void sendZeroYuan(OrdOrder ordOrder) {
		OrdOrder newOrder = orderServiceProxy.queryOrdOrderByOrderId(ordOrder
				.getOrderId());
		if (newOrder.getOughtPay() <= newOrder.getActualPay()) {
			orderMessageProducer.sendMsg(MessageFactory
					.newOrderPay0YuanMessage(newOrder.getOrderId()));
		}
	}

	
	protected void throwValidateCouponInfo(FavorResult fr,PriceInfo priceInfo){
		ValidateCodeInfo info = fr.getValidateCodeInfo();
		if (!Constant.COUPON_INFO.OK.name().equals(info.getKey())&&info.getKey()!=null&&info.getValue()!=null) {
			throw new LogicException(info.getValue());
		} else {
			
			if(priceInfo!=null&&priceInfo.isSuccess()){
				if (!Constant.COUPON_INFO.OK.name().equals(priceInfo.getInfo().getKey())&&priceInfo.getInfo().getKey()!=null&&priceInfo.getInfo().getValue()!=null) {
					throw new LogicException(priceInfo.getInfo().getValue());
				}
			}
		}
	}
	
	protected void personLogic(String[] personArray,BuyInfo createOrderBuyInfo,List<Person> personList) {

		List<MobilePersonItem> personItemList = new ArrayList<MobilePersonItem>();
		

		/**
		 * 解析联系人相关数据
		 */
		for (String string : personArray) {
			String[] itemArray  = string.split("-");

			MobilePersonItem mpi = new MobilePersonItem();
			
			mpi.setPersonName(itemArray[0]);
			mpi.setPersonMobile(itemArray[1]);
			mpi.setPersonType(itemArray[2]);
			if(mpi.getPersonType().equals(Constant.RECEIVERS_TYPE.ADDRESS.name())){
				/**
				 * 处理有地址的其他情况
				 */
				if(itemArray.length>=4){
					mpi.setProvince(itemArray[3]);
					mpi.setCity(itemArray[4]);
					mpi.setAddress(itemArray[5]);
				}	
			} else {
			if(itemArray.length>=4){
				mpi.setCertNo(itemArray[3]);
				if(itemArray.length==4){
					mpi.setCertType(Constant.CERT_TYPE.ID_CARD.name());
				}
			}
			
			if(itemArray.length>=5){
				mpi.setCertType(itemArray[4]);
			}
			
			if(itemArray.length>=6){
			if(Constant.CERT_TYPE.HUZHAO.name().equals(itemArray[4])){
				if(itemArray.length!=7){
					throw new LogicException("护照必须包含生日和性别");
				} else {
					mpi.setBirthday(itemArray[5]);
					mpi.setGender(itemArray[6]);
				}
			}
			}
			}
			personItemList.add(mpi);
		}
		
		

		/**
		 * 判断是否有联系人信息
		 */
		boolean hasContact=false;
		

		for (MobilePersonItem mobilePersonItem : personItemList) {
			Person person = new Person();
			person.setName(mobilePersonItem.getPersonName());
			person.setMobile(mobilePersonItem.getPersonMobile());
			person.setPersonType(mobilePersonItem.getPersonType());
			person.setAddress(mobilePersonItem.getAddress());
			if(mobilePersonItem.getBirthday()!=null){
				person.setBrithday(DateUtil.toDate(mobilePersonItem.getBirthday(), "yyyy/MM/dd"));
				person.setGender(mobilePersonItem.getGender());
			} 
			
			/**
			 * 处理有身份证的情况
			 */
			if (!StringUtil.isEmptyString(mobilePersonItem.getCertNo())){
				person.setCertType(mobilePersonItem.getCertType());
				person.setCertNo(mobilePersonItem.getCertNo());
				if(Constant.CERT_TYPE.ID_CARD.name().equals(person.getCertType())){
					if(!StringUtil.isIdCard(mobilePersonItem.getCertNo())){
						String name = "";
						if(Constant.ORD_PERSON_TYPE.CONTACT.name().equals(mobilePersonItem.getPersonType())){
							name = Constant.ORD_PERSON_TYPE.CONTACT.getCnName();
						} else if(Constant.ORD_PERSON_TYPE.TRAVELLER.name().equals(mobilePersonItem.getPersonType())){
							name = Constant.ORD_PERSON_TYPE.TRAVELLER.getCnName();
						}
						throw new LogicException(name+"身份证格式不正确");
					}
				}
			}
			
			if(StringUtil.isNotEmptyString(mobilePersonItem.getAddress())){
				person.setAddress(mobilePersonItem.getProvince() + " " + mobilePersonItem.getCity() + " " + mobilePersonItem.getAddress());
				person.setProvince(mobilePersonItem.getProvince());
				person.setCity(mobilePersonItem.getCity());
			}
			
			/**
			 * 判断是否填写了联系人
			 */
			if(Constant.ORD_PERSON_TYPE.CONTACT.name().equals(mobilePersonItem.getPersonType())){
				hasContact = true;
				if(StringUtil.isEmptyString(person.getMobile())){
					throw new LogicException("必须填写联系人手机");
				}
			}
			
			personList.add(person);
		}
		
		if (!hasContact){
			throw new LogicException("您必须填写联系人");
		}
		
		createOrderBuyInfo.setPersonList(personList);
		
	}
	
	private boolean isAndroidOrIphonePlatform(String firstChannel){
		return Constant.MOBILE_PLATFORM.IPHONE.name().equals(firstChannel)||Constant.MOBILE_PLATFORM.ANDROID.name().equals(firstChannel);
	}

	
	protected Map<String,Object> setOrderItems(String[] branchArray,boolean isTodayOrder,String visitTime,String leaveTimeStr,BuyInfo createOrderBuyInfo,String couponCode){
		log.info("before invoke setOrderItems...");
		List<MobileBranchItem> branchItemList = new ArrayList<MobileBranchItem>();
		
		/**
		 * 解析类别对象
		 */
		for (String string : branchArray) {
			String[] itemArray  = string.split("-");
			MobileBranchItem mbi = new MobileBranchItem();
			mbi.setBranchId(Long.valueOf(itemArray[0]));
			mbi.setQuantity(Long.valueOf(itemArray[1]));
			branchItemList.add(mbi);
		}
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("usedWexinCoupon", false);
		resultMap.put("noEmergencyContact", false);
		resultMap.put("travellerNumber", 0);
		
		List<Item> itemList = new ArrayList<Item>();
		ProdProduct mainPoduct = null;
		
		boolean hasInsurance = false;
		int travellerNumber = 0;
		
		long clientCommentRefundAmount = 0;
		long quantity = 0;
		/**
		 * 构建订单项
		 */
		List<ProdProductBranch> allBranches = new  ArrayList<ProdProductBranch>();
		for (MobileBranchItem mobileBranchItem : branchItemList) {
			log.info("branchItemList size: "+branchItemList.size());
			Item item = new Item();
			if (isTodayOrder){
				ProdBranchSearchInfo pbsi = productSearchInfoService.getProdBranchSearchInfoByBranchId(mobileBranchItem.getBranchId());
				if(pbsi!=null&&!pbsi.canOrderTodayCurrentTime()){
					log.info("can order currentTime:"+DateUtil.formatDate(pbsi.getTodayOrderAbleTime(), "yyyy-MM-dd HH:mm:ss"));
					throw new LogicException("已超过最晚可预订时间");
				}
				visitTime = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
			}
			ProdProductBranch branch = null;
			if(isTodayOrder) {
				branch =  this.prodProductService.getPhoneProdBranchDetailByProdBranchId(mobileBranchItem.getBranchId(), DateUtil.toDate(visitTime, "yyyy-MM-dd"),true);
			} else {
				branch =  this.prodProductService.getProdBranchDetailByProdBranchId(mobileBranchItem.getBranchId(),  DateUtil.toDate(visitTime, "yyyy-MM-dd"), true);
			}
			
			if(branch==null){
				throw new LogicException("产品已下线!");
			}
			
			/**
			 * 获得保险数量
			 */
			if(branch.getProdProduct().isInsurance()){
				hasInsurance = true;
			}
			if("false".equals(branch.getAdditional())&&!branch.getProdProduct().isInsurance()&&!Constant.PRODUCT_TYPE.OTHER.name().equals(branch.getProdProduct().getProductType())){
				travellerNumber+=(branch.getAdultQuantity()+branch.getChildQuantity())*mobileBranchItem.getQuantity();
			}
	
			if(mobileBranchItem.getQuantity()>branch.getMaximum()){
					throw new LogicException("产品"+branch.getBranchName()+"的最大预定数量为"+branch.getMaximum());
			}
			
			if(mobileBranchItem.getQuantity()<branch.getMinimum()){
					throw new LogicException("产品"+branch.getBranchName()+"的最小预定数量为"+branch.getMinimum());
			}
			
			item.setQuantity(mobileBranchItem.getQuantity().intValue());
			item.setAdultQuantity(branch.getAdultQuantity());
			item.setProductBranchId(branch.getProdBranchId());
			item.setProductId(branch.getProductId());
			item.setProductType(branch.getProdProduct().getProductType());
			item.setSubProductType(branch.getProdProduct().getSubProductType());
		
			/**
			 * 处理之买一个产品非默认产品的情况
			 */
			if(branchItemList!=null && branchItemList.size()==1){
				mainPoduct =  branch.getProdProduct();
			}
			
			if("true".equals(branch.getDefaultBranch())&&!branch.hasAdditional()){
				/**
				 * 处理那种录入成其他产品，业务上是附加产品，但是录入的不是附加产品。坑爹的后台验证。。
				 */
				if(branch.getProdProduct()!=null && !Constant.PRODUCT_TYPE.OTHER.name().equals(branch.getProdProduct().getProductType())){
					mainPoduct = branch.getProdProduct();
					if(mainPoduct==null){
						throw new LogicException("产品已下线!");
					}
					item.setIsDefault("true");
				} 
			} 
			item.setVisitTime(DateUtil.getDateByStr(visitTime, "yyyy-MM-dd"));

			//处理单房型产品
			
			if(mainPoduct!=null && mainPoduct.isSingleRoom()){
				List<OrdTimeInfo> timeInfoList = new ArrayList<OrdTimeInfo>();
				
				if(leaveTimeStr==null){
					throw new LogicException("入住日期不能为空");
				}
				
				/**
				 * 处理离店时间，如果是单房产品
				 */
				Date leaveTime = DateUtil.getDateByStr(leaveTimeStr, "yyyy-MM-dd");
				
				List<Date> dateList = DateUtil.getDateList(DateUtil.getDateByStr(visitTime, "yyyy-MM-dd"), leaveTime);
				
				dateList.remove(dateList.size()-1);
					for (Date date : dateList) {
						OrdTimeInfo ti = new OrdTimeInfo();
						ti.setProductId(item.getProductId());
						ti.setProductBranchId(item.getProductBranchId());
						ti.setVisitTime(date);
						ti.setQuantity(Long.valueOf(item.getQuantity()));
						timeInfoList.add(ti);
					}
					
					item.setTimeInfoList(timeInfoList);
					item.setQuantity(HotelUtils.getHotelRoomQuantity(timeInfoList));
					
					
			}
			
			itemList.add(item);
			
			
			quantity = mobileBranchItem.getQuantity();
			
			
			
			if(branch==null || branch.getProdProduct()==null){
				throw new LogicException("产品已下线!");
			}
			
			//子产品返现
			if(null!=branch){
				ProdProduct pp = branch.getProdProduct();
				if(null!=pp){
					long maxCachRefund = pp.getMaxCashRefund();
					
					long totalMobileRefund = RefundUtils.getMobileRefundFen(maxCachRefund,branch.getProdProduct().getProductType());
					log.info("maxCachRefund: "+maxCachRefund+" totalMobileRefund: "+totalMobileRefund+" quantity: "+quantity);
					clientCommentRefundAmount += (totalMobileRefund*quantity);
				}
			}
			
			allBranches.add(branch);
		}
		
		
		if(mainPoduct == null && allBranches.size()>0){
			mainPoduct  = allBranches.get(0).getProdProduct();
		}
		
		//clientCommentRefundAmount+=mainPoduct.getMaxCashRefund();
		
		
		/**
		 * 如果主产品的附加产品选择类型为必选，那么需要验证是否含有附加产品
		 */
		
		createOrderBuyInfo.setItemList(itemList);
		
		
		/**
		 * 处理优惠券
		 */
		List<Coupon> couponList = new ArrayList<Coupon>();
		if(!StringUtils.isEmpty(couponCode)){
			Coupon c = new Coupon();
			c.setChecked("true");
			c.setCode(couponCode);
			
			MarkCoupon mc =  markCouponService.selectMarkCouponByCouponCode(couponCode, false);
			if(mc!=null){ 
				Long weixinActivityId = Long.valueOf(ClutterConstant.getProperty("weixinActiviyCouponId"));
				if(weixinActivityId!=null &&weixinActivityId.intValue()==mc.getCouponId().intValue()){
					resultMap.put("usedWexinCoupon", true);
				}
				c.setCouponId(mc.getCouponId());
			}
			if(mc!=null){ 
				createOrderBuyInfo.setPaymentChannel(mc.getPaymentChannel()); 
			}
			couponList.add(c);	
		}
		
		createOrderBuyInfo.setMainSubProductType(mainPoduct.getSubProductType());
		createOrderBuyInfo.setMainProductType(mainPoduct.getProductType());
		createOrderBuyInfo.setCouponList(couponList);
		createOrderBuyInfo.setTodayOrder(isTodayOrder);
		createOrderBuyInfo.setWaitPayment(isTodayOrder?Constant.WAIT_PAYMENT.PW_HALF_HOUR.getValue():Constant.WAIT_PAYMENT.PW_DEFAULT.getValue());
		
		resultMap.put("mainPoduct", mainPoduct);
		if(Constant.SUB_PRODUCT_TYPE.FREENESS.name().equals(mainPoduct.getSubProductType())){
			resultMap.put("noEmergencyContact", true);
		} else if(mainPoduct.isTicket()){
			resultMap.put("noEmergencyContact", true);
		}
		resultMap.put("hasInsurance", hasInsurance);
		if(hasInsurance){
			resultMap.put("travellerNumber", travellerNumber);
		} else if(mainPoduct.getFirstTravellerInfoOptionsList()==null&&mainPoduct.getTravellerInfoOptionsList()==null){
			resultMap.put("travellerNumber", 0);
		}  else {
			if(mainPoduct.getFirstTravellerInfoOptionsList()==null){
				travellerNumber = travellerNumber-1;
			}
			resultMap.put("travellerNumber", travellerNumber);
		}
		resultMap.put("clientCommentRefundAmount", clientCommentRefundAmount);
		return resultMap;
	}
	
	public Map<String,Object> validateCoupon(Map<String,Object> param){
		return null;
	};
	
	/**
	 * 临时方法 解决部分android 版本bug问题
	 * @param param
	 * @param pp
	 * @param personList
	 */
	protected void checkRoutePerson(Map<String,Object> param,ProdProduct pp,List<Person> personList){
		if("ANDROID".equals(param.get("firstChannel").toString())){
			String lvVersion = (String) param.get("lvversion");
			boolean isAndroid = (Boolean) param.get("isAndroid");
			if ("3.0.0".equals(lvVersion)&&pp.isRoute()&&isAndroid){
				boolean noTraveller = true;
				for (int i = 0; i < personList.size(); i++) {
						Person p = personList.get(i);
						if (Constant.ORD_PERSON_TYPE.TRAVELLER.name().equals(p.getPersonType())){
							noTraveller = false;
						}
				}
				if(noTraveller){
					throw new LogicException("团购暂不支持购买保险，如需请到d.lvmama.com下最新客户端。");
				}
			}
		}
	}
	
	public Map<String,Object> queryOrderWaitPayTimeSecond(Map<String,Object> param){
		ArgCheckUtils.validataRequiredArgs("orderId", param);
		Map<String,Object> result = new HashMap<String,Object>();
		Long orderId = Long.valueOf(param.get("orderId").toString());
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId));
		
		Date date1 = null;
		if(order.isApprovePass()){
			date1 = order.getApproveTime();
		} else {
			date1 = order.getCreateTime();
		}
		
		Date date2 = new Date();
		long d=Math.abs(date2.getTime()-date1.getTime());
		//计算剩余预订秒数 减去上2秒的网络损耗
		long second = order.getWaitPayment()*60 - Math.abs(d/1000)-2;
		// 如果是景区支付（5.0.1） ，second = 99999999
		if( order.isPayToSupplier() 
				&& null != param.get("lvversion") && null != param.get("firstChannel") 
				&& "IPHONE".equalsIgnoreCase(param.get("firstChannel").toString())
				&& "5.0.1".equals(param.get("lvversion").toString()) ) {
			second = 99999999l;
		}
		result.put("surplusSeconds", second<0?0:second);
		return result;
	}
	
	public Map<String,Object>  getEContractInfo(Map<String,Object> param){
		//ArgCheckUtils.validataRequiredArgs("orderId",Constant.LV_SESSION_ID, param);
		OrdEContract ordEcontract = ordEContractService.queryByOrderId(Long.valueOf(param.get("orderId").toString()));
		
		ComFile comFile = fsClient.downloadFile(ordEcontract.getFixedFileId());
		String contractContent = "";
		try {
			contractContent = new String(comFile.getFileData(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(null!=contractContent){
			contractContent = contractContent.replaceAll("<traveller>.*</traveller>", "");
		}else {
			contractContent = "<div>合同暂时没有生成，请稍后刷新</div>";
		}
		return null;
		
	}
	
	public Map<String,Object> cancelOrder(Map<String,Object> param){
		ArgCheckUtils.validataRequiredArgs("orderId","sign","time", param);
		String orderId = param.get("orderId").toString();
		String sign = param.get("sign").toString();
		String time = param.get("time").toString();
		String lvsessionId = param.get(Constant.LV_SESSION_ID).toString();
		String signKey = ClutterConstant.getMobileSignKey();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String serverSign = String.format("%s%s%s%s", orderId,time,lvsessionId,signKey);
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId));
		
		if(order.getLastCancelTime()!=null&&new Date().after(order.getLastCancelTime())){
			throw new LogicException("订单超过了最晚取消时间，不能取消");
		}
		
		if(MD5.md5(serverSign).equalsIgnoreCase(sign)){
			String userNo = param.get("userNo").toString();
			if(order.getIsShHolidayOrder()){
				orderMessageProducer.sendMsg(MessageFactory.newSupplierOrderCancelMessage(Long.valueOf(orderId)));
				throw new LogicException("订单取消申请中!");
			}
			boolean flag = orderServiceProxy.cancelOrder(Long.valueOf(orderId), "客户端授权取消", userNo);
			resultMap.put("operatorStatu", flag);
		} else {
			throw new LogicException("非法操作!");
		}
		
		return resultMap;
	}

	/**
	 * 取消订单 ，orderId lvsessionid ，wap专用. 
	 */
	@Override
	public Map<String, Object> cancellOrder4Wap(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("orderId", param);
		String orderId = param.get("orderId").toString();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String userNo = param.get("userNo").toString();
		
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId));
		if(order.getLastCancelTime()!=null&&new Date().after(order.getLastCancelTime())){
			throw new LogicException("订单超过了最晚取消时间，不能取消");
		}
		if(order.getIsShHolidayOrder()){
			orderMessageProducer.sendMsg(MessageFactory.newSupplierOrderCancelMessage(Long.valueOf(orderId)));
			throw new LogicException("订单取消申请中!");
		}
		boolean flag = orderServiceProxy.cancelOrder(Long.valueOf(orderId), "Wap端取消", userNo);
		resultMap.put("operatorStatu", flag);
		return resultMap;
	}
	
	
	/**
	 * 在线签约. 
	 * @throws Exception 
	 */
	@Override
	public String onlineSign(Map<String, Object> param) throws Exception {
		log.info("true".equals(param.get("optionsCheckBox1")));
		log.info("true".equals(param.get("optionsCheckBox2")));
		log.info("true".equals(param.get("optionsCheckBox3")));
		ArgCheckUtils.validataRequiredArgs("orderId","contactEmail", param);
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(param.get("orderId").toString()));
		if(null != order) {
			// 校验是否需要在线签约  且 没有被确认. 
			if(order.isNeedEContract() && !order.isEContractConfirmed()) {
				try{
					String userId = param.get("userNo").toString();
					// 保存联系人邮箱. 
					saveContactEmail(order,param.get("contactEmail").toString(),userId);
					//合同签约
					signContract(order.getOrderId(),userId,param,order);
				}catch(Exception e) {
					e.printStackTrace();
					throw new Exception("sign failed ! error msg :" +e.getMessage());
				}
			} else {
				throw new LogicException("订单已经签约成功了！");
			}
		} else {
			throw new Exception("can not find Order by orderId:" +param.get("orderId").toString() );
		}
		
		return "success";
	}
	
	/**
	 * 保存联系人邮箱信息.
	 * 若是签约产品，并且已购选前台签约框，则保存信息。
	 * @param order  订单  
	 * @param contactEmail  联系人邮箱 
	 * @param isSigning    用户id . 
	 */
	private void saveContactEmail(OrdOrder order,String contactEmail,String userId){
		// 如果需要签约 ，且签约状态是等待签约. 
			// 联系人邮箱
			// 更新订单联系人用户
			Person contactPersion = new Person();
			BeanUtils.copyProperties(order.getContact(), contactPersion);
			contactPersion.setEmail(contactEmail);
			orderServiceProxy.updatePerson(contactPersion, order.getOrderId(),userId);
	}
	
	/**
	 * 合同签约. 
	 * @param orderId
	 * @param userId
	 */
	private void signContract(final Long orderId,String userId,Map<String, Object> param,OrdOrder order){
		
	
		OrdEcontractSignLog signLog = new OrdEcontractSignLog();
		signLog.setSignMode(Constant.ECONTRACT_SIGN_TYPE.ONLINE_SIGN.getCode());
		signLog.setSignDate(new java.util.Date());
		signLog.setSignUser(userId);
		
		boolean isSign = orderServiceProxy.updateOrdEContractStatusToConfirmed(orderId);
		

		//boolean isUpdated = contractClient.updateEContract(order, "true".equals(param.get("optionsCheckBox1")), "true".equals(param.get("optionsCheckBox2")), "true".equals(param.get("optionsCheckBox3")), "true".equals(param.get("optionsCheckBox4")), "system"); 

		
		//if(!isUpdated){ 
		//	log.info("电子合同修改同意项失败, 订单号"+orderId); 
		//} 

		if(order.isCanceled()){
			if(Constant.ORDER_RESOURCE_STATUS.LACK.name().equals(order.getResourceConfirmStatus())){
				throw new LogicException("您的订单由于资源不满足已经取消了!");
			} else {
				throw new LogicException("您的订单已经取消了!");
			}
		}
		if(!isSign){
			log.info("前台用户签约，订单"+orderId+" 在订单表中没能修改签约状态-驴途");
		}
		isSign = ordEContractService.signContract(orderId, signLog);
		if(!isSign){
			log.info("电子合同签约失败 订单号"+orderId + "-驴途");
		} else {
			orderMessageProducer.sendMsg(MessageFactory.newOrderContactUpdateAgreeItem(order.getOrderId(),"true".equals(param.get("optionsCheckBox1")), "true".equals(param.get("optionsCheckBox2")), "true".equals(param.get("optionsCheckBox3")), true)); 
		}
	}
	
	
	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	public void setContractClient(EContractClient contractClient) {
		this.contractClient = contractClient;
	}

	public void setTravelDescriptionService(
			TravelDescriptionService travelDescriptionService) {
		this.travelDescriptionService = travelDescriptionService;
	}

	@Override
	public Map<String, Object> cashAccountValidateAndPay(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

}
