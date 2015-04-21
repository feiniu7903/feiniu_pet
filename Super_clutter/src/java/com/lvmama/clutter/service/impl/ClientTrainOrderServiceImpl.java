package com.lvmama.clutter.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.model.MobileBranchItem;
import com.lvmama.clutter.model.MobilePersonItem;
import com.lvmama.clutter.service.IClientOrderService;
import com.lvmama.clutter.service.client.v4_0.ClientOrderServiceV40;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderChannel;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;

public class ClientTrainOrderServiceImpl extends ClientOrderServiceV40 implements IClientOrderService {
	private static final Log log = LogFactory.getLog(ClientTrainOrderServiceImpl.class);

	@Override
	public Map<String,Object> validateCoupon(Map<String,Object> param){
		
		return null;

	}
	@Override
	public Map<String, Object> validateTravellerInfo(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("firstChannel", "secondChannel",
				"branchItem", "visitTime", "udid", param);
		String branchItem = param.get("branchItem").toString();
		String visitTime = param.get("visitTime").toString();

		String[] branchArray = branchItem.split("_");
		if (branchArray.length == 0) {
			throw new RuntimeException("类别项构建错误!");
		}

		BuyInfo createOrderBuyInfo = new BuyInfo();
		Map<String, Object> result = this.setOrderItems(branchArray,false, visitTime, null, createOrderBuyInfo, null);
		int travellerCount = (Integer) result.get("travellerNumber");
		List<Map<String, Object>> travellerOptions = new ArrayList<Map<String, Object>>();

		/**
		 * 处理游玩人选项。
		 */
		for (int i = 0; i < travellerCount; i++) {
				Map<String, Object> travellerOption = new HashMap<String, Object>();
				travellerOption.put("NAME", "NAME");
				travellerOption.put("CARD_NUMBER", "CARD_NUMBER");
				travellerOptions.add(travellerOption);
		}

		result.remove("mainPoduct");
		result.remove("travellerNumber");
		result.put("noEmergencyContact", true);
		result.put("travellerOptions", travellerOptions);
		return result;
	}

	
	@Override
	public Map<String, Object> commitOrder(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("userNo","firstChannel","secondChannel","branchItem","visitTime","personItem","udid",param);
		Map<String,Object> resultMap = new HashMap<String,Object>();
	
		String branchItem = param.get("branchItem").toString();
		String visitTime = param.get("visitTime").toString();
		String leaveTime = param.get("leaveTime")==null?null:param.get("leaveTime").toString();
		String personItem = param.get("personItem").toString();
		String firstChannel = param.get("firstChannel").toString();
		String secondChannel = param.get("secondChannel").toString();
		String couponCode = param.get("couponCode")==null?null:param.get("couponCode").toString().toUpperCase();
		String userId = param.get("userNo").toString();
		
		String[] branchArray = branchItem.split("_");
		String[] personArray = personItem.split(":");
		if(branchArray.length==0){
			throw new RuntimeException("类别项构建错误!");
		}
		
		if(personItem.length()==0){
			throw new RuntimeException("person项构建错误!");
		}
	
		BuyInfo createOrderBuyInfo = new BuyInfo();
		Map<String,Object> buildMap = this.setOrderItems(branchArray, false, visitTime, leaveTime, createOrderBuyInfo,couponCode);
		createOrderBuyInfo.setUserId(param.get("userNo").toString());
		ProdProduct mainPoduct = (ProdProduct)buildMap.get("mainPoduct");
		log.info("client train order "+param.get("lvversion")+" "+param.get("firstChannel")+"mainproduct id is "+mainPoduct.getProductId());

		/**
		 * 构建联系人相关数据,先验证优惠券 后处理联系人游玩人相关数据。
		 */
		List<Person> personList = new ArrayList<Person>();
		this.personLogic(personArray, createOrderBuyInfo,personList);
		
		List<Person> orderPersons = createOrderBuyInfo.getPersonList();
		this.saveUsrReceivers(userId, orderPersons);
		
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
		
		ResultHandle result=orderServiceProxy.checkOrderStock(createOrderBuyInfo); 
		if(result.isFail()){ 
			throw new LogicException(result.getMsg());
		}
		
		try {
			OrdOrder o = orderServiceProxy.createOrder(createOrderBuyInfo);
			/**
			 * 写日志
			 */
			super.saveMobileLog(param, o.getOrderId(), Constant.MOBILE_PERSISTENCE_LOG_OBJECT_TYPE.ORDER.name());
			resultMap.put("orderId", o.getOrderId());
			resultMap.put("oughtPayYuan", o.getOughtPayYuan());

		} catch (Exception ex){
			ex.printStackTrace();
			throw new LogicException("创建订单失败!");
		}
		
		return resultMap;
	}
	

	protected Map<String,Object> setOrderItems(String[] branchArray,boolean isTodayOrder,String visitTime,String leaveTimeStr,BuyInfo createOrderBuyInfo,String couponCode){
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
		List<Item> itemList = new ArrayList<Item>();
		ProdProduct mainPoduct = null;
		int travellerNumber = 0;
		/**
		 * 构建订单项
		 */
		for (MobileBranchItem mobileBranchItem : branchItemList) {
			Item item = new Item();

			ProdProductBranch branch =  this.prodProductService.getProdBranchDetailByProdBranchId(mobileBranchItem.getBranchId(),  DateUtil.toDate(visitTime, "yyyy-MM-dd"), true);
			
			if(branch==null){
				throw new LogicException("产品已下线!");
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
			//if("true".equals(branch.getDefaultBranch())&&!branch.hasAdditional()){
			if(null == mainPoduct && !branch.hasAdditional()){
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
			itemList.add(item);
		}
		
		if(mainPoduct==null){
			throw new LogicException("产品已下线!");
		}
		
		/**
		 * 如果主产品的附加产品选择类型为必选，那么需要验证是否含有附加产品
		 */
		createOrderBuyInfo.setItemList(itemList);
		createOrderBuyInfo.setMainSubProductType(mainPoduct.getSubProductType());
		createOrderBuyInfo.setMainProductType(mainPoduct.getProductType());
		createOrderBuyInfo.setWaitPayment(Constant.WAIT_PAYMENT.PW_DEFAULT.getValue());
		resultMap.put("mainPoduct", mainPoduct);
		resultMap.put("travellerNumber", travellerNumber);
		return resultMap;
	}
	
	
    /**
     * 解析校验人员相关数据. 
     */
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
	
	@Override
	public Map<String, Object> queryOrderWaitPayTimeSecond(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> cancelOrder(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> cancellOrder4Wap(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getEContractInfo(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String onlineSign(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected void saveUsrReceivers(String userNo,List<Person> persons){
		try {
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
		}catch(Exception e) {
			e.printStackTrace();
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
	

}
