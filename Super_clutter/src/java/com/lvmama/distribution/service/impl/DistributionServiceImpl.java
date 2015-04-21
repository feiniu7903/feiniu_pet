package com.lvmama.distribution.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.distribution.DistributionProduct;
import com.lvmama.comm.bee.po.distribution.DistributorInfo;
import com.lvmama.comm.bee.po.distribution.OrdOrderDistribution;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderMemo;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassEvent;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.distribution.DistributionOrderService;
import com.lvmama.comm.bee.service.distribution.DistributionProductService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.BuyInfo.OrdTimeInfo;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.SynchronizedLock;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.distribution.model.lv.Branch;
import com.lvmama.distribution.model.lv.FirstCustomer;
import com.lvmama.distribution.model.lv.Order;
import com.lvmama.distribution.model.lv.OrderInfo;
import com.lvmama.distribution.model.lv.OrderList;
import com.lvmama.distribution.model.lv.OtherCustomer;
import com.lvmama.distribution.model.lv.PriceList;
import com.lvmama.distribution.model.lv.Product;
import com.lvmama.distribution.model.lv.ProductList;
import com.lvmama.distribution.model.lv.ProductListParameter;
import com.lvmama.distribution.model.lv.ProductParameter;
import com.lvmama.distribution.model.lv.ProductPriceListParameter;
import com.lvmama.distribution.model.lv.ProductPriceParameter;
import com.lvmama.distribution.model.lv.Request;
import com.lvmama.distribution.model.lv.Response;
import com.lvmama.distribution.model.lv.ResponseBody;
import com.lvmama.distribution.model.lv.ResponseHead;
import com.lvmama.distribution.service.BaseDistributionService;
import com.lvmama.distribution.service.DistributionService;
import com.lvmama.distribution.util.DistributionUtil;

/**
 * 分销接口
 * 
 * @author clj
 * 
 */
public class DistributionServiceImpl extends BaseDistributionService implements DistributionService{
	private static final Log log = LogFactory.getLog(DistributionServiceImpl.class);
	private ComPictureService comPictureService;
	private ProdProductService prodProductService;
	private DistributionProductService distributionProductService;
	/** 分销订单服务*/
	private DistributionOrderService distributionOrderService;
	
	
	/**
	 * 创建多个联系人
	 */
	protected void createContacts(OrderInfo orderInfo, String userId) {
		// 创建多个联系人
		FirstCustomer firstTraveler = orderInfo.getFirstVisitCustomer();
		List<UsrReceivers> receiversList = new ArrayList< UsrReceivers>();
		UsrReceivers receiverContacts = new  UsrReceivers();
		receiverContacts.setCardNum(firstTraveler.getCredentials());
		receiverContacts.setCardType(firstTraveler.getCredentialsType());
		receiverContacts.setMobileNumber(firstTraveler.getMobile());
		receiverContacts.setReceiverName(firstTraveler.getName());
		receiverContacts.setPinyin(firstTraveler.getPinyin());
		receiversList.add(receiverContacts);

		// 游客信息
		List<com.lvmama.distribution.model.lv.Person> pList = orderInfo.getOtherVisitCustomer().getPersonList();
		for (int j = 0; j < pList.size(); j++) {
			UsrReceivers receiverVisitCustomer = new UsrReceivers();
			com.lvmama.distribution.model.lv.Person pp = pList.get(j);
			receiverVisitCustomer.setCardNum(pp.getCredentials());
			receiverVisitCustomer.setCardType(pp.getCredentialsType());
			receiverVisitCustomer.setMobileNumber(pp.getMobile());
			receiverVisitCustomer.setReceiverName(pp.getName());
			receiverVisitCustomer.setPinyin(pp.getPinyin());
			receiversList.add(receiverVisitCustomer);
		}
		receiverUserService.createContact(receiversList, userId);
	}

	/**
	 * 取得联系人id
	 */
	private String getReceiverId(List<UsrReceivers> receList, String name, String mobile, String cardNum) {
		if (receList != null && !receList.isEmpty()) {
			for (int j = 0; j < receList.size(); j++) {
				UsrReceivers re = receList.get(j);
				if (re.getReceiverName().equals(name) && re.getMobileNumber().equals(mobile) && re.getCardNum().equals(cardNum)) {
					return re.getReceiverId();
				}
			}
		}
		return null;
	}

	/**
	 * 注册用户
	 * 
	 * @param mobileNumber
	 * @param buyInfo
	 * @return
	 */
	private String rigestUser(String mobileNumber, BuyInfo buyInfo , boolean isRegisterUser) {
	String errorNo = Constant.DISTRIBUTION_MSG.SUCCESS.getCode();
	if (!isRegisterUser) {
			buyInfo.setUserId(Constant.getInstance().getDefaultRegisterUser());
		} else {
			if (mobileNumber != null) {
				if (StringUtil.validMobileNumber(mobileNumber)) {
					if (mobileNumber.charAt(0) == '0') {
						mobileNumber = mobileNumber.substring(1, mobileNumber.length());
					}
					try {
						// 该手机号是否已注册
						UserUser user = userUserProxy.getUsersByMobOrNameOrEmailOrCard(mobileNumber);
						if (user == null) {
							UserUser newUser = UserUserUtil.genDefaultUserByMobile(mobileNumber);
							newUser = userUserProxy.register(newUser);
							buyInfo.setUserId(newUser.getUserId());
						} else {
							buyInfo.setUserId(user.getUserId());
						}
					} catch (Exception e) {
						log.error("rigestUser", e);
						//"用户注册出错！";
						errorNo = "1001";
					}
				} else {
					//"手机号不合法！";
					errorNo = "1002";
				}
			} else {
				//"手机号为空！";
				errorNo = "1003";
			}
		}
		return errorNo;
	}

	/**
	 * 创建订单联系人列表
	 */
	private List<Person> createPersonList(OrderInfo orderInfo, String userId) {
		FirstCustomer firstCustomer = orderInfo.getFirstVisitCustomer();
		// 取票人信息
		List<UsrReceivers> receList = receiverUserService.loadUserReceiversByUserId(userId);
		String receiverId = getReceiverId(receList, firstCustomer.getName(), firstCustomer.getMobile(), firstCustomer.getCredentials());
		List<Person> personList = new ArrayList<Person>();
		Person contact = new Person();
		contact.setCertNo(firstCustomer.getCredentials());
		contact.setCertType(firstCustomer.getCredentialsType());
		contact.setMobile(firstCustomer.getMobile());
		contact.setName(firstCustomer.getName());
		contact.setPinyin(firstCustomer.getPinyin());
		contact.setPersonType(Constant.ORD_PERSON_TYPE.CONTACT.name());
		contact.setReceiverId(receiverId);
		personList.add(contact);
		// 预订人
		Person booker = new Person();
		booker.setCertNo(firstCustomer.getCredentials());
		booker.setCertType(firstCustomer.getCredentialsType());
		booker.setMobile(firstCustomer.getMobile());
		booker.setName(firstCustomer.getName());
		booker.setPinyin(firstCustomer.getPinyin());
		booker.setPersonType(Constant.ORD_PERSON_TYPE.BOOKER.name());
		booker.setReceiverId(receiverId);
		personList.add(booker);
		//第一游玩人
		Person firstPerson = new Person();
		firstPerson.setCertNo(firstCustomer.getCredentials());
		firstPerson.setCertType(firstCustomer.getCredentialsType());
		firstPerson.setMobile(firstCustomer.getMobile());
		firstPerson.setName(firstCustomer.getName());
		firstPerson.setPersonType(Constant.ORD_PERSON_TYPE.TRAVELLER.name());
		firstPerson.setReceiverId(getReceiverId(receList, firstCustomer.getName(), firstCustomer.getMobile(), firstCustomer.getCredentials()));
		personList.add(firstPerson);
		
		// 游客信息
		List<com.lvmama.distribution.model.lv.Person> pList = orderInfo.getOtherVisitCustomer().getPersonList();
		if(pList!=null){
			for (int j = 0; j < pList.size(); j++) {
				com.lvmama.distribution.model.lv.Person pp = pList.get(j);
				Person person = new Person();
				person.setCertNo(pp.getCredentials());
				person.setCertType(pp.getCredentialsType());
				person.setMobile(pp.getMobile());
				person.setName(pp.getName());
				person.setPersonType(Constant.ORD_PERSON_TYPE.TRAVELLER.name());
				person.setReceiverId(getReceiverId(receList, pp.getName(), pp.getMobile(), pp.getCredentials()));
				personList.add(person);
			}
		}
		return personList;
	}

	/**
	 * 验证订单信息
	 * 
	 * @param branch
	 * @param quan
	 * @return
	 */
	private String validateOrderInfo(Branch branch) {
		String errorNo = Constant.DISTRIBUTION_MSG.SUCCESS.getCode();
		Long quantity = Long.valueOf(branch.getQuantity());
		String visitDate = branch.getVisitDate();
		Long branchId = Long.parseLong(branch.getBranchId());
		ProdProductBranch prodProductBranch = prodProductBranchService.selectProdProductBranchByPK(branchId);
		if (prodProductBranch == null) {
			//"没有此产品类别！";
			errorNo = "1019";
		}else if (quantity == null) {
			//"选购产品数量为空！";
			errorNo = "1004";
		}else if (quantity <= 0) {
			//"选购产品数量<=0！";
			errorNo = "1005";
		}else if (quantity > prodProductBranch.getMaximum()) {
			//"订购数量超过最大可售数";
			errorNo = "1006";
		}else if (quantity < prodProductBranch.getMinimum()) {
			//"订购数量小于最小订购数";
			errorNo = "1007";
		}else if (visitDate == null && "".equals(visitDate)) {
			//"游玩日期为空！";
			errorNo = "1008";
		}
		return errorNo;
	}

	/**
	 * 酒店单房型预订
	 * 
	 * @param branch
	 * @param product
	 * @param item
	 * @param addDay
	 * @param quan
	 * @return
	 */
	private String createSingleRoom(Branch branch, ProdProduct product, Item item, int addDay, Long quan) {
			String errorNo = Constant.DISTRIBUTION_MSG.SUCCESS.getCode();
			String visitDate = branch.getVisitDate();
			Long branchId = Long.parseLong(branch.getBranchId());
			Date visitD = DateUtil.toDate(visitDate, "yyyy-MM-dd");
			String endDate = branch.getLeaveDate();
			if (endDate == null && "".equals(endDate)) {
				//"离店日期为空！"
				errorNo = "1009";
				return errorNo;
			}
			try {
				Date endD = DateUtil.toDate(endDate, "yyyy-MM-dd");
				if (visitD.compareTo(endD) == 0 || visitD.after(endD)) {
					//"离店日期必须大于入住日期";
					errorNo = "1010";
					return errorNo;
				}
				List<OrdTimeInfo> timeInfoList = new ArrayList<OrdTimeInfo>();
				// 入住天数
				int days = DateUtil.getDaysBetween(visitD, endD);
				// 计算出每天的价格、库存信息
				for (int j = 0; j < days; j++) {
					Date d = DateUtil.dsDay_Date(visitD, addDay);
					// 检验库存
				boolean productSellable = prodProductService.isProductSellable(branchId, quan, d);
				if (!productSellable) {
					//"当前库存不足，请选择其他日期";
					errorNo = "1011";
					break;
				}

				ProdProductBranch b = prodProductService.getProdBranchDetailByProdBranchId(branchId, d,true);
				// 保存每天的时间价格信息
				OrdTimeInfo timeInfo = new OrdTimeInfo();
				timeInfo.setProductId(product.getProductId());
				timeInfo.setVisitTime(d);
				timeInfo.setMarketPrice(b.getMarketPrice());
				timeInfo.setSellPrice(b.getSellPrice());
				timeInfo.setProductBranchId(b.getProdBranchId());
				timeInfo.setQuantity(quan);
				timeInfoList.add(timeInfo);
			}
			item.setTimeInfoList(timeInfoList);
			// 单房型数量为天数*用户选择的数量
			item.setQuantity(quan.intValue() * timeInfoList.size());
		} catch (Exception e) {
			log.error("createSingleRoom", e);
			// "离店日期格式错误！"
			errorNo = "1012";
			return errorNo;
		}
		return errorNo;
	}
	
	/**
	 * 生成返回信息
	 * 
	 * @param errorNo
	 * @param describe
	 * @param obj
	 * @return
	 */
	private String createResult(String errorNo , String xmlStr) {
		String describe="";
		if (errorNo == null) {
			errorNo="2000";
		}
		describe = Constant.DISTRIBUTION_MSG.getCnName(errorNo);
		log.info("errorNo:"+errorNo+"describe"+describe);
		ResponseHead responseHead = new ResponseHead(errorNo, describe);
		ResponseBody responseBody = new ResponseBody(xmlStr);
		Response response = new Response(responseHead, responseBody);
		String xml = response.buildXmlStr();
		return xml;
	}

	@Override
	public String createOrder(String requestXml) {
		log.info("request createOrder:" + requestXml);
		Order order = new Order();
		String errorNo = Constant.DISTRIBUTION_MSG.SUCCESS.getCode();
		Request request = null;
		String responseXml = "";
		String body = null;
		DistributorInfo distributorInfo = null;
		
		try {
			request = DistributionUtil.getRequestCreateOrder(requestXml);
			log.info("localSign: " + request.getLocalSigned());
		} catch (Exception e) {
			errorNo = "1014";
			log.error("RequestCreateOrder :", e);
			responseXml = this.createResult(errorNo , null);
			log.info("response createOrder:" + responseXml);
			return responseXml;
		}
		distributorInfo = distributionCommonService.getDistributorByCode(request.getRequestHead().getPartnerCode());
		errorNo = checkRequest(distributorInfo, request);
		if (Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)) {
			OrderInfo orderInfo = request.getRequestBody().getOrderInfo();
			Long productId = Long.parseLong(orderInfo.getProductId());
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("distributorInfoId", distributorInfo.getDistributorInfoId());
			params.put("productId", productId);
			ProdProduct product = null;
			List<Branch> branchList = orderInfo.getProductBranch().getBranchList();
			boolean flag = true;
			Boolean isTaocan = false;
			if (branchList != null && !branchList.isEmpty()) {
				for(Branch branch : branchList){
					String branchId = branch.getBranchId();
					params.put("productBranchId", branchId);
					params.put("volid", Constant.TRUE_FALSE.TRUE.getCode());
					flag  = distributionProductService.selectDistributionProductBranchList(params);
					if(!isTaocan){
						ProdProductBranch prodBranch = prodProductBranchService.selectProdProductBranchByPK(Long.valueOf(branchId));
						isTaocan = prodBranch.getAdultQuantity()+prodBranch.getChildQuantity()>1L;
					}
					if(!flag){
						break;
					}
				}
			}else{
				errorNo = "1013";// 未选购产品!;
			}
			
			if(flag){
				product = prodProductService.getProdProductById(productId);
				Date onlineTime = product.getOnlineTime();
				Date offLineTime = product.getOfflineTime();
				Date nowTime = new Date();
				if (nowTime.after(onlineTime) && nowTime.before(offLineTime)) {
					boolean validateCardNo = validateCardNo(isTaocan,product , orderInfo);
					if(validateCardNo){
						BuyInfo buyInfo = new BuyInfo();
						OrdOrder ordOrder = null;
						FirstCustomer firstCustomer = orderInfo.getFirstVisitCustomer();
						String mobileNumber = firstCustomer.getMobile();
						// 注册用户
						errorNo = this.rigestUser(mobileNumber, buyInfo, distributorInfo.isRegisterUser());
						if (Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)) {
							// 创建联系人列表
							createContacts(orderInfo, buyInfo.getUserId());
							List<Item> itemList = new ArrayList<Item>();
								for (int i = 0; i < branchList.size(); i++) {
									Branch branch = branchList.get(i);
									String visitDate = branch.getVisitDate();
									Long branchId = Long.parseLong(branch.getBranchId());
									Long quan = Long.valueOf(branch.getQuantity());
									Date visitD = null;
									try {
										visitD = DateUtil.toDate(visitDate, "yyyy-MM-dd");
									} catch (Exception e) {
										//"游玩日期格式错误！";
										errorNo = "1018";
										break;
									}
									// 验证订单信息
									errorNo = this.validateOrderInfo(branch);
									log.info("errorNo:" + errorNo);
									if (!Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)) {
										break;
									}
									Item item = new Item();
									item.setProductId(productId);
									item.setVisitTime(visitD);
									item.setQuantity(quan.intValue());
									item.setProductBranchId(branchId);
									// 单房型，需设置每天时间价格信息
									if (Constant.PRODUCT_TYPE.HOTEL.name().equals(product.getProductType()) && Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(product.getSubProductType())) {
										errorNo= this.createSingleRoom(branch, product, item, i, quan);
										log.info("errorNo:" + errorNo);
										if (!Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)) {
											break;
										}
									} else {
										log.info("item.getProductBranchId():"+item.getProductBranchId()+"item.getVisitTime():"+item.getVisitTime());
										TimePrice timePrice = prodProductService.calcProdTimePrice(item.getProductBranchId(), item.getVisitTime(),false);
										if (timePrice == null) {
											//"当前日期不能预订，请选择其它时间";
											errorNo = "1028";
											break;
										} else {
											boolean productSellable = prodProductService.isProductSellable(branchId, quan, visitD);
											Map<String,Object> paramsMap = new HashMap<String,Object>();
											String productType = product.getProductType();
											paramsMap.put("productType", productType);
											paramsMap.put("distributorInfoId", distributorInfo.getDistributorInfoId());
											if (Constant.PRODUCT_TYPE.ROUTE.name().equals(productType)) {
												paramsMap.put("subProductType", product.getSubProductType());
											}else if (Constant.PRODUCT_TYPE.TICKET.name().equals(productType)) {
												boolean payOnline = product.isPaymentToLvmama();
												String onlineVal = payOnline ? Constant.TRUE_FALSE.TRUE.getValue() : Constant.TRUE_FALSE.FALSE.getValue();
												paramsMap.put("payOnline", onlineVal);
											}
											boolean distributeSellable = distributionProductService.isSellableDistributionProductTimePrice(paramsMap, timePrice);

											if (!productSellable || !distributeSellable) {
												//"当前库存不足，请选择其他日期";
												errorNo = "1011";
												break;
											}
										}
									}
									if (i == 0) {
										item.setIsDefault("true");
									}
									itemList.add(item);
		
								}
								if (Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)) {
									buyInfo.setChannel(distributorInfo.getChannelCode()); // 分销渠道
									buyInfo.setItemList(itemList);
									buyInfo.setPersonList(createPersonList(orderInfo, buyInfo.getUserId()));
									String payTarget = Constant.PAYMENT_TARGET.TOLVMAMA.name();
									// 支付对象
									if (product.isPaymentToSupplier()) {
										payTarget = Constant.PAYMENT_TARGET.TOSUPPLIER.name();
									}else {
										String isResourceConfirm = Constant.PRODUCT_TYPE.ROUTE.name().equals(product.getProductType()) ?  Boolean.FALSE.toString():Boolean.TRUE.toString() ;
										buyInfo.setResourceConfirmStatus(isResourceConfirm);
									}
									buyInfo.setPaymentTarget(payTarget);
									buyInfo.setWaitPayment(Constant.WAIT_PAYMENT.PW_DEFAULT.getValue());
									log.info("SubProductType:"+product.getSubProductType());
									ResultHandle handle = orderServiceProxy
											.checkOrderStock(buyInfo);
									if (handle.isFail()) {
										errorNo = "1028";
									}else{
										ordOrder = orderServiceProxy.createOrder(buyInfo);
										// add by zhangwengang 2013/11/1 保存备注信息
										if (null != orderInfo.getOrderMemo()) {
											OrdOrderMemo memo = new OrdOrderMemo();
											memo.setOrderId(ordOrder.getOrderId());
											memo.setContent(orderInfo.getOrderMemo());
											memo.setType("M1");
											memo.setOperatorName(firstCustomer.getName());
											memo.setCreateTime(new Date());
											memo.setUserMemo("false");
											orderServiceProxy.saveMemo(memo, firstCustomer.getName());
										}
									}
								}
						}
						log.info("errorNo:" + errorNo);
						String serialNo = DateUtil.getFormatDate(new Date(), "yyyyMMdd") + distributionOrderService.getSerialNo();
						if (ordOrder != null) {
							OrdOrderDistribution ordOrderDitribution = new OrdOrderDistribution();
							ordOrderDitribution.setOrderId(ordOrder.getOrderId());
							ordOrderDitribution.setDistributionInfoId(distributorInfo.getDistributorInfoId());
							ordOrderDitribution.setSerialNo(serialNo);
							ordOrderDitribution.setPartnerOrderId(request.getRequestBody().getOrderInfo().getPartnerOrderId());
							//去那儿门票计算点评返现值
							if(Constant.DISTRIBUTOR.QUNA_TICKET.getCode().equalsIgnoreCase(distributorInfo.getDistributorCode())){
								ordOrderDitribution.setCommentsCashback(getCommentsCashback(branchList,distributorInfo.getDistributorInfoId()));
							}
							distributionOrderService.insertOrdOrderDistribution(ordOrderDitribution);
						}
						if (Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)) {
							order = new Order(ordOrder.getOrderId().toString(), ordOrder.getOrderStatus(), ordOrder.getPaymentStatus());
							body=order.buildOrderXmlStr();
						}
					}else{
						errorNo ="1033";
					}
				}else{
					errorNo = "1028";
				}
			}else{
				errorNo = "1027";
			}
		}
		responseXml = this.createResult(errorNo , body);
		log.info("response createOrder:" + responseXml);
		return responseXml;
	}
	private Long getCommentsCashback(List<Branch> branchList,
			Long distributorInfoId) {
		Long commentsCashback =0L;
		for(Branch branch:branchList){
			DistributionProduct distributionProduct = distributionProductService.getDistributionProductByBranchId(Long.valueOf(branch.getBranchId()), distributorInfoId);
			if(distributionProduct!=null){
				commentsCashback = commentsCashback + distributionProduct.getCommentsCashback()*Long.valueOf(branch.getQuantity());
			}
		}

		return commentsCashback;
	}

	/**
	 * 获取订单
	 */
	@Override
	public String getOrder(String requestXml) {
		log.info("request getOrder:" + requestXml);
		String errorNo = Constant.DISTRIBUTION_MSG.SUCCESS.getCode();
		Order order = null;
		Request request=null;
		OrderList orderList = new OrderList();
		try {
			request = DistributionUtil.getRequestOrder(requestXml);
			log.info("localSign: " + request.getLocalSigned());
		} catch (Exception e) {
			errorNo = "1014";
			log.error("RequestGetOrder :", e);
			String responseXml = this.createResult(errorNo , null);
			log.info("response GetOrder:" + responseXml);
			return responseXml;
		}
		String partnerCode = request.getRequestHead().getPartnerCode();
		DistributorInfo distributorInfo = this.distributionCommonService.getDistributorByCode(partnerCode);
		errorNo = checkRequest(distributorInfo, request);
		if(Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)) {
			String serialNoList = request.getRequestBody().getOrder().getOrderId();
			String[] orderIds = serialNoList.split(",");
			List<Order> orders = new ArrayList<Order>();
			for (String orderId : orderIds) {
				OrdOrderDistribution ordOrderDistribution = distributionOrderService.selectByOrderIdAndDistributionInfoId(Long.valueOf(orderId), distributorInfo.getDistributorInfoId());
				if (ordOrderDistribution != null) {
					OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId));
					order = new Order(ordOrderDistribution.getOrderId().toString(), ordOrder.getOrderStatus(), ordOrder.getPaymentStatus(), distributionCommonService.getCredenctStatus(ordOrder),ordOrder.getPerformStatus());
				} else {
					order = new Order();
				}
				orders.add(order);
			}
			orderList.setOrders(orders);
		}
		String body=orderList.buildForGetOrder();
		String responseXml = this.createResult(errorNo, body);
		log.info("response getOrder_body:"+body);
		return responseXml;
	}

	/**
	 * 查询单个产品信息
	 */
	@Override
	public String getProductInfo(String requestXml) {
		log.info("request productInfo:" + requestXml);
		String errorNo = Constant.DISTRIBUTION_MSG.SUCCESS.getCode();
		Request request=null;
		Product product = new Product();
		try {
			request = DistributionUtil.getRequestProduct(requestXml);
			log.info("localSign: " + request.getLocalSigned());
		} catch (Exception e) {
			errorNo = "1014";
			log.error("RequestgetProductInfo Exception:", e);
			String responseXml = this.createResult(errorNo , null);
			log.info("response getProductInfo:" + responseXml);
			return responseXml;
		}
		String partnerCode = request.getRequestHead().getPartnerCode();
		DistributorInfo distributorInfo = this.distributionCommonService.getDistributorByCode(partnerCode);
		errorNo = checkRequest(distributorInfo, request);
		if(Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)){
			ProductParameter parameter = request.getRequestBody().getProductParameter();
			String productId = parameter.getProductId();
			Date beginDate = DateUtil.toDate(parameter.getBeginDate(), "yyyy-MM-dd");
			Date endDate = DateUtil.toDate(parameter.getEndDate(), "yyyy-MM-dd");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("productId", Long.valueOf(productId));
			params.put("beginDate", beginDate);
			params.put("endDate", endDate);
			params.put("distributorInfoId", distributorInfo.getDistributorInfoId());
			List<DistributionProduct> distributionProductList = this.distributionCommonService.getDistributionProduct(params);
			if (distributionProductList != null && !distributionProductList.isEmpty()) {
				// 取得图片信息
				DistributionProduct dp = distributionProductList.get(0);
				if(dp != null && dp.getViewPage() != null) {
					dp.getViewPage().setPictureList(comPictureService.getPictureByPageId(dp.getViewPage().getPageId()));
				}
				log.info("productID :" + dp.getProductId());
				product = new Product(dp);
				if(distributorInfo!=null && Constant.DISTRIBUTOR.QUNA_TICKET.getCode().equalsIgnoreCase(distributorInfo.getDistributorCode())){
					product.setBranchIsQuar(true);
					product.setCashZero(dp.getProdProduct().isPaymentToSupplier());
				}
			} else {
				errorNo = "1027";// 不存在此分销产品
			}
		}
		
		String body = product.buildForGetProductInfo();
//		log.info("response productInfo_body:" + body);
		String responseXml = this.createResult(errorNo, body);
		log.info("response productInfo: errorNo" + Constant.DISTRIBUTION_MSG.getCnName(errorNo));
		return responseXml;
	}

	@Override
	public String getProductPrice(String requestXml) {
		log.info("request productPrice:" + requestXml);
		String errorNo = Constant.DISTRIBUTION_MSG.SUCCESS.getCode();
		Request request=null;
		Product product = new Product();
		try {
			request = DistributionUtil.getRequestProductPrice(requestXml);
			log.info("localSign: " + request.getLocalSigned());
		} catch (Exception e) {
			errorNo = "1014";
			log.error("GetProductPrice Exception :", e);
			String responseXml = this.createResult(errorNo , null);
			log.info("response getProductPrice:" + responseXml);
			return responseXml;
		}
		String partnerCode = request.getRequestHead().getPartnerCode();
		DistributorInfo distributorInfo = this.distributionCommonService.getDistributorByCode(partnerCode);
		errorNo = checkRequest(distributorInfo,request);
		if(Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)){
			ProductPriceParameter parameter = request.getRequestBody().getProductPriceParameter();
			Long productId = Long.valueOf(parameter.getProductId());
			Date beginDate = DateUtil.toDate(parameter.getBeginDate(), "yyyy-MM-dd");
			Date endDate = DateUtil.toDate(parameter.getEndDate(), "yyyy-MM-dd");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("beginDate", beginDate);
			params.put("endDate", endDate);
			params.put("productId", productId);
			params.put("distributorInfoId", distributorInfo.getDistributorInfoId());
			List<DistributionProduct> distributionProductList = this.distributionProductService.getDistributionProductTimePriceList(params);
			if (distributionProductList != null && !distributionProductList.isEmpty()) {
				product = new Product(distributionProductList.get(0));
			} else {
				errorNo = "1027"; // 没有此分销产品
			}
		}
		String body=product.buildForGetProductPrice();
		//log.info("response productPrice_body:" + body);
		String responseXml = this.createResult(errorNo , body);
		//log.info("response productPrice:" + responseXml);
		return responseXml;
	}

	private String checkRequest(DistributorInfo distributorInfo,Request request) {
		String errorNo = Constant.DISTRIBUTION_MSG.SUCCESS.getCode();
		if(distributorInfo != null){
			String lvmamaSigned = "";
			try {
				String paramInfo = distributorInfo.getDistributorKey() + request.getLocalSigned();
				lvmamaSigned = MD5.encode(paramInfo);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			String reqeustSigned = request.getRequestHead().getSigned();
			boolean flag = lvmamaSigned.equals(reqeustSigned);
			log.info("lvmamaSigned: " + lvmamaSigned);
			if (!flag) {
				errorNo = "1015";// 签证验证未通过
			}else if (!this.validateIp(distributorInfo.getDistributorInfoId())) {
				errorNo = "1026";// 无效访问IP
			}
		}else{
			errorNo = "1021"; // 商户不存在
		}
		return errorNo;
	}
	
	/**
	 * 分销产品基本信息列表接口 
	 */
	@Override
	public String productInfoList(String requestXml) {
		log.info("request productInfoList:" + requestXml);
		String errorNo = Constant.DISTRIBUTION_MSG.SUCCESS.getCode();
		Request request=null;
		ProductList productList = new ProductList();
		try {
			request = DistributionUtil.getRequestProductList(requestXml);
			log.info("localSign: " + request.getLocalSigned());
		} catch (Exception e) {
			errorNo = "1014";
			log.error("RequestproductInfoList Exception:", e);
			String responseXml = this.createResult(errorNo , null);
			log.info("response productInfoList:" + responseXml);
			return responseXml;
		}
		String partnerCode = request.getRequestHead().getPartnerCode();
		DistributorInfo distributorInfo = this.distributionCommonService.getDistributorByCode(partnerCode);
		errorNo = checkRequest(distributorInfo, request);
		if(Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)){
			Map<String, Object> params = new HashMap<String, Object>();
			ProductListParameter parameter = request.getRequestBody().getProductListParameter();
			String productType = parameter.getProductType();
			Integer currentPage = Integer.valueOf(parameter.getCurrentPage());
			Integer pageSize = Integer.valueOf(parameter.getPageSize());
			int startRow = (Integer.valueOf(currentPage));
			if (startRow != 1) {
				startRow = ((Integer.valueOf(currentPage) - 1) * Integer.valueOf(pageSize)) + 1;
			}
			int endRow = Integer.valueOf(currentPage) * Integer.valueOf(pageSize);
			params.put("start", startRow);
			params.put("end", endRow);
			params.put("productType", productType);
			params.put("distributorInfoId", distributorInfo.getDistributorInfoId());
			List<DistributionProduct> distributionProductList = this.distributionCommonService.getDistributionProduct(params);
			for(DistributionProduct dp : distributionProductList) {
				ViewPage vp = dp.getViewPage();
				if(vp != null) {
					vp.setPictureList(comPictureService.getPictureByPageId(vp.getPageId()));
				}
			}
			productList = new ProductList(distributionProductList);
			if(distributorInfo!=null && Constant.DISTRIBUTOR.QUNA_TICKET.getCode().equalsIgnoreCase(distributorInfo.getDistributorCode())){
				productList.setQunar(true);
			}
		}
		
		String body=productList.buildForProductInfoList();
//		log.info("response productInfoList_body:" + body);
		String responseXml = this.createResult(errorNo , body);
		log.info("response productInfoList: errorNo" + Constant.DISTRIBUTION_MSG.getCnName(errorNo));
		return responseXml;
	}

	/**
	 * 产品时间价格表接口 
	 */
	@Override
	public String productPriceList(String requestXml) {
		log.info("request productPriceList:" + requestXml);
		String errorNo = Constant.DISTRIBUTION_MSG.SUCCESS.getCode();
		Request request=null;
		PriceList productList = new PriceList();
		try {
			request = DistributionUtil.getRequestProductPriceList(requestXml);
			log.info("localSign: " + request.getLocalSigned());
		} catch (Exception e) {
			errorNo = "1014";
			log.error("RequestproductPriceList Exception:", e);
			String responseXml = this.createResult(errorNo , null);
			log.info("response productPriceList:" + responseXml);
			return responseXml;
		}
		String partnerCode = request.getRequestHead().getPartnerCode();
		DistributorInfo distributorInfo = this.distributionCommonService.getDistributorByCode(partnerCode);
		errorNo = checkRequest(distributorInfo, request);
		if(Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)){
			ProductPriceListParameter parameter = request.getRequestBody().getProductPriceListParameter();
			Date beginDate = DateUtil.toDate(parameter.getBeginDate(), "yyyy-MM-dd");
			Date endDate = DateUtil.toDate(parameter.getEndDate(), "yyyy-MM-dd");
			Integer currentPage = Integer.valueOf(parameter.getCurrentPage());
			Integer pageSize = Integer.valueOf(parameter.getPageSize());
			String productType = parameter.getProductType();
			int startRow = (Integer.valueOf(currentPage));
			if (startRow != 1) {
				startRow = ((Integer.valueOf(currentPage) - 1) * Integer.valueOf(pageSize)) + 1;
			}
			int endRow = Integer.valueOf(currentPage) * Integer.valueOf(pageSize);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("start", startRow);
			params.put("end", endRow);
			params.put("productType", productType);
			params.put("beginDate", beginDate);
			params.put("endDate", endDate);
			params.put("distributorInfoId", distributorInfo.getDistributorInfoId());
			List<DistributionProduct> distributionProductList = this.distributionProductService.getDistributionProductTimePriceList(params);
			productList = new PriceList(distributionProductList);
		}
		String body=productList.buildForProductPriceList();
		//log.info("response productPriceList_body:"+body);
		String responseXml = this.createResult(errorNo ,body );
		//log.info("response productPriceList:"+responseXml);
		return responseXml;
	}

	@Override
	public String resendCode(String requestXml) {
		log.info("request resendCode:" + requestXml);
		String errorNo = Constant.DISTRIBUTION_MSG.SUCCESS.getCode();
		Request request=null;
		Order orderInfo = new Order();
		try {
			request = DistributionUtil.getRequestResendCode(requestXml);
			log.info("localSign: " + request.getLocalSigned());
		} catch (Exception e) {
			errorNo = "1014";
			log.error("RequestresendCode Exception:", e);
			String responseXml = this.createResult(errorNo , null);
			log.info("response resendCode:" + responseXml);
			return responseXml;
		}
		Order order = request.getRequestBody().getOrder();
		String partnerCode = request.getRequestHead().getPartnerCode();
		DistributorInfo distributorInfo = this.distributionCommonService.getDistributorByCode(partnerCode);
		errorNo = checkRequest(distributorInfo, request);
		if(Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)){
			OrdOrderDistribution ordOrderDitribution = distributionOrderService.selectByOrderIdAndDistributionInfoId(Long.valueOf(order.getOrderId()), distributorInfo
					.getDistributorInfoId());
			if (ordOrderDitribution != null) {
				Long orderId = ordOrderDitribution.getOrderId();
				OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
				boolean isNeedPay = false;
				for (OrdOrderItemMeta ordOrderItemMeta : ordOrder.getAllOrdOrderItemMetas()) {
					if(Constant.PAYMENT_TARGET.TOLVMAMA.name().equals(ordOrderItemMeta.getPaymentTarget())){
							isNeedPay = true;
					}
				}
				if (ordOrder.isNormal()) {
					if(isNeedPay){
						if(!ordOrder.isPaymentSucc()){
							errorNo = "1029";
						}else{
							if (ordOrder.isPassportOrder()) {
								Map<String, Object> param = new HashMap<String, Object>();
								param.put("orderId", ordOrder.getOrderId());
								List<PassCode> codeList = passCodeService.queryPassCodes(param);
								if (codeList != null && !codeList.isEmpty()) {
									PassCode passCode = codeList.get(0);
									if (!passCode.isNeedSendSms()) {
										PassEvent event = distributionCommonService.resendByNotLvmama(codeList.get(0).getCodeId());
										passportMessageProducer.sendMsg(MessageFactory.newPasscodeEventMessage(event.getEventId()));
									} else {
										orderMessageProducer.sendMsg(MessageFactory.newCertSmsSendMessage(orderId, passCode.getMobile()));
									}
								}
							} else {
								OrdPerson contact = null;
								if (!ordOrder.getTravellerList().isEmpty() && null != ordOrder.getTravellerList().get(0).getMobile()) {
									contact = ordOrder.getTravellerList().get(0);
								} else {
									contact = ordOrder.getContact();
								}
								orderMessageProducer.sendMsg(MessageFactory.newCertSmsSendMessage(orderId, contact.getMobile()));
							}
						}
					}else{
						if (ordOrder.isPassportOrder()) {
							Map<String, Object> param = new HashMap<String, Object>();
							param.put("orderId", ordOrder.getOrderId());
							List<PassCode> codeList = passCodeService.queryPassCodes(param);
							if (codeList != null && !codeList.isEmpty()) {
								PassCode passCode = codeList.get(0);
								if (!passCode.isNeedSendSms()) {
									PassEvent event = distributionCommonService.resendByNotLvmama(codeList.get(0).getCodeId());
									passportMessageProducer.sendMsg(MessageFactory.newPasscodeEventMessage(event.getEventId()));
								} else {
									orderMessageProducer.sendMsg(MessageFactory.newCertSmsSendMessage(orderId, passCode.getMobile()));
								}
							}
						} else {
							OrdPerson contact = null;
							if (!ordOrder.getTravellerList().isEmpty() && null != ordOrder.getTravellerList().get(0).getMobile()) {
								contact = ordOrder.getTravellerList().get(0);
							} else {
								contact = ordOrder.getContact();
							}
							orderMessageProducer.sendMsg(MessageFactory.newCertSmsSendMessage(orderId, contact.getMobile()));
						}
					}
				} else {
					errorNo = "1029";// 重发凭证失败,原因为订单未支付或订单状态不正常
				}
			} else {
				// 没有此订单，请检查订单号是否正确
				errorNo = "1016";
			}
		}
		String responseXml = null;
		if (Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)){
			String body=orderInfo.buildForResendCode();
			log.info("response resendCode_body:" + body);
			responseXml = this.createResult(Constant.DISTRIBUTION_MSG.SUCCESS.getCode(), body);
		}else {
			responseXml = this.createResult(errorNo , null);
		}
		
		//log.info("response resendCode:" + responseXml);
		return responseXml;
	}

	@Override
	public String updateOrderStatus(String requestXml) {
		log.info("request updateOrderStatus:" + requestXml);
		String errorNo = Constant.DISTRIBUTION_MSG.SUCCESS.getCode();
		Request request=null;
		Order order = new Order();
		try {
			request = DistributionUtil.getRequestUpdateOrderStatus(requestXml);
			log.info("localSign: " + request.getLocalSigned());
		} catch (Exception e) {
			errorNo = "1014";
			log.error("RequestUpdateOrderStatus Exception:", e);
			String responseXml = this.createResult(errorNo , null);
			log.info("response updateOrderStatus:" + responseXml);
			return responseXml;
		}
		Order requestOrder = request.getRequestBody().getOrder();
		String partnerCode = request.getRequestHead().getPartnerCode();
		String orderId = request.getRequestBody().getOrder().getOrderId();
		DistributorInfo distributorInfo = this.distributionCommonService.getDistributorByCode(partnerCode);
		errorNo = checkRequest(distributorInfo, request);
		if(Constant.ORDER_STATUS.CANCEL.name().equals(requestOrder.getStatus())){
			errorNo = "1034";//不提供取消接口
		}
		if(Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)){
			OrdOrderDistribution ordOrderDitribution = distributionOrderService.selectByOrderIdAndDistributionInfoId(Long.valueOf(orderId), distributorInfo.getDistributorInfoId());
			if (ordOrderDitribution != null) {
				if (Constant.PAYMENT_STATUS.PAYED.name().equals(requestOrder.getPaymentStatus()) && Constant.ORDER_STATUS.NORMAL.name().equals(requestOrder.getStatus())
						) {
					Map<String, Object> map = this.doUpdate(requestOrder, ordOrderDitribution, distributorInfo);
					errorNo = (String) map.get("errorNo");
					if (Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)) {
						order = (Order) map.get("order");
					}
				} else {
					// 无效订单状态参数,无法更新状态
					errorNo = "1025";
				}
			} else {
				// 没有此订单，请检查订单号是否正确
				errorNo = "1016";
			}
		}
		String body=order.buildOrderXmlStr();
		String responseXml = this.createResult(errorNo , body);
		log.info("response updateOrderStatus_body:"+body);
		//log.info("response updateOrderStatus" + responseXml);
		return responseXml;
	}
	
	/**
	 * 查询产品上下架信息化接口
	 */
	public String getProductOnLine(String requestXml) {
		log.info("request getProductOnLine:" + requestXml);
		String errorNo = Constant.DISTRIBUTION_MSG.SUCCESS.getCode();
		Request request=null;
		ProductList productList = new ProductList();
		try {
			request = DistributionUtil.getRequestProductOnLine(requestXml);
			log.info("localSign: " + request.getLocalSigned());
		} catch (Exception e) {
			errorNo = "1014";
			log.error("RequestGetProductOnLine Exception:", e);
			String responseXml = this.createResult(errorNo , null);
			log.info("response getProductOnLine:" + responseXml);
			return responseXml;
		}
		String partnerCode = request.getRequestHead().getPartnerCode();
		DistributorInfo distributorInfo = this.distributionCommonService.getDistributorByCode(partnerCode);
		errorNo = checkRequest(distributorInfo, request);
		if(Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)){
			ProductParameter parameter = request.getRequestBody().getProductParameter();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("productIds", parameter.getProductIdList());
			params.put("distributorInfoId", distributorInfo.getDistributorInfoId());
			params.put("start", 0);
			params.put("end", 10000);
			List<DistributionProduct> distributionProductList = this.distributionProductService.getDistributionOnline(params);
			if (distributionProductList != null && !distributionProductList.isEmpty()) {
				productList = new ProductList(distributionProductList);
			} else {
				errorNo = "1027";// 无此分销产品
			}
		}
		String body=productList.buildForGetProductOnLine();
		String responseXml = this.createResult(errorNo , body);
		log.info("response getProductOnLine_body:"+body);
		//log.info("response getProductOnLine:"+responseXml);
		return responseXml;
	}

	/**
	 * 支付，取消操作
	 * 
	 * @param requestOrder
	 * @param ordOrderDitribution
	 * @param distributorInfo
	 * @param order
	 * @return
	 */
	private Map<String, Object> doUpdate(Order requestOrder, OrdOrderDistribution ordOrderDitribution, DistributorInfo distributorInfo) {
		String errorNo = Constant.DISTRIBUTION_MSG.SUCCESS.getCode();
		Order order = null;
		Map<String, Object> map = new HashMap<String, Object>();
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(ordOrderDitribution.getOrderId());
		if (Constant.ORDER_STATUS.NORMAL.name().equals(requestOrder.getStatus())) {
			if (Constant.PAYMENT_STATUS.PAYED.name().equals(ordOrder.getPaymentStatus())) {
				// 订单已经支付,不能重复支付
				errorNo = "1022";
			} else if (Constant.ORDER_STATUS.CANCEL.name().equals(ordOrder.getOrderStatus())) {
				errorNo = "1030";// 订单已经取消，不能进行支付
			} else if (Constant.PAYMENT_STATUS.PAYED.name().equals(requestOrder.getPaymentStatus())
					&& Constant.PAYMENT_STATUS.UNPAY.name().equals(ordOrder.getPaymentStatus())) {
				if (this.orderPayment(ordOrder.getOrderId(), distributorInfo , requestOrder)) {
					order = new Order(ordOrderDitribution.getOrderId().toString(), ordOrder.getOrderStatus(), Constant.PAYMENT_STATUS.PAYED.name());
				} else {
					// 订单支付失败，请重新支付
					errorNo = "1017";
				}
			}
		} else if (Constant.ORDER_STATUS.CANCEL.name().equals(requestOrder.getStatus()) && Constant.ORDER_STATUS.NORMAL.name().equals(ordOrder.getOrderStatus())) {
			Date date = ordOrder.getCancelTime() == null ? ordOrder.getVisitTime() : ordOrder.getCancelTime();
			if (!new Date().after(date)) {
				if (this.cancelOrder(ordOrder.getOrderId(), distributorInfo)) {
					ordOrder = orderServiceProxy.queryOrdOrderByOrderId(ordOrderDitribution.getOrderId());
					order = new Order(ordOrderDitribution.getOrderId().toString(), ordOrder.getOrderStatus(), ordOrder.getPaymentStatus());
				} else {
					// 取消订单失败
					errorNo = "1020";
				}
			} else {
				// 订单已经过了最晚取消时间不能取消
				errorNo = "1023";
			}
		} else if (Constant.ORDER_STATUS.CANCEL.name().equals(ordOrder.getOrderStatus())) {
			// 订单已经取消不能再取消
			errorNo = "1024";
		}else{
			errorNo = "1025";
		}
		map.put("errorNo", errorNo);
		map.put("order", order);
		return map;
	}


	/**
	 * 订单支付
	 * 
	 * @param orderId
	 * @return
	 */
	private boolean orderPayment(Long orderId, DistributorInfo distributorInfo ,Order requestOrder) {
		PayPayment payPayment = new PayPayment();
		payPayment.setObjectId(orderId);
		payPayment.setSerial(payPayment.geneSerialNo());
		String key = "PAYMENT_DISTRIBUTION_ACTION" + payPayment.getSerial();
		if (SynchronizedLock.isOnDoingMemCached(key)) { 
			return false;
		}
		try {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			Date clllbackTime = new Date();
			payPayment.setCallbackInfo("分销支付");
			payPayment.setGatewayTradeNo(requestOrder.getPaymentSerialno());
			payPayment.setPaymentTradeNo(requestOrder.getBankOrderId());
			payPayment.setCallbackTime(clllbackTime);
			payPayment.setCreateTime(clllbackTime);
			payPayment.setPaymentGateway("FENXIAO_"+distributorInfo.getDistributorCode());
			payPayment.setAmount(order.getOughtPay());
			payPayment.setOperator(distributorInfo.getDistributorCode());
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

	/**
	 * 获取订单的资源审核状态 
	 */
	public String getOrderApprove(String requestXml) {
		log.info("request getOrderApprove:" + requestXml);
		String errorNo = Constant.DISTRIBUTION_MSG.SUCCESS.getCode();
		Order order = null;
		Request request=null;
		OrderList orderList = new OrderList();
		try {
			request = DistributionUtil.getRequestOrderApprove(requestXml);
			log.info("localSign: " + request.getLocalSigned());
		} catch (Exception e) {
			errorNo = "1014";
			log.error("RequestGetOrderApprove Exception:", e);
			String responseXml = this.createResult(errorNo , null);
			log.info("response getOrderApprove:" + responseXml);
			return responseXml;
		}
		String partnerCode = request.getRequestHead().getPartnerCode();
		DistributorInfo distributorInfo = this.distributionCommonService.getDistributorByCode(partnerCode);
		errorNo = checkRequest(distributorInfo, request);
		if(Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)){
			String orderIds = request.getRequestBody().getOrder().getOrderId();
			List<OrdOrderDistribution> distributionorderList = distributionOrderService.selectDistributionOrderApproveStatus(distributorInfo.getDistributorInfoId(), orderIds);
			List<Order> orders = new ArrayList<Order>();
			for (OrdOrderDistribution ordOrderDistribution : distributionorderList) {
				OrdOrder ordOrder = ordOrderDistribution.getOrdOrder();
				order = new Order();
				order.setApproveStatusOfOrder(ordOrder);
				orders.add(order);
			}
			orderList.setOrders(orders);
		}
		String body=orderList.buildForGetOrderApprove();
		String responseXml = this.createResult(errorNo , body);
		log.info("response getOrderApprove_body:" + body);
		//log.info("response getOrderApprove:" + responseXml);
		return responseXml;
	}

	/**
	 * 告知驴妈妈订单的审核状态已经正常更新
	 */
	public String orderApproveCallBack(String requestXml) {
		log.info("request orderApproveCallBack:" + requestXml);
		String errorNo = Constant.DISTRIBUTION_MSG.SUCCESS.getCode();
		Request request=null;
		try {
			request = DistributionUtil.getRequestOrderApproveCallBack(requestXml);
			log.info("localSign: " + request.getLocalSigned());
		} catch (Exception e) {
			errorNo = "1014";
			log.error("RequestOrderApproveCallBack Exception:", e);
			String responseXml = this.createResult(errorNo , null);
			log.info("response orderApproveCallback:" + responseXml);
			return responseXml;
		}
		String partnerCode = request.getRequestHead().getPartnerCode();
		DistributorInfo distributorInfo = this.distributionCommonService.getDistributorByCode(partnerCode);
		errorNo = checkRequest(distributorInfo, request);
		if(Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)){
			String orderIds = request.getRequestBody().getOrder().getOrderId();
			distributionOrderService.updateOrdOrderDistributionResourceStatus(orderIds, distributorInfo.getDistributorInfoId());
		}
		String responseXml = this.createResult(errorNo, "");
		log.info("response orderApproveCallBack:" + responseXml);
		return responseXml;
	}
	
	/**
	 * 验证订单信息
	 */
	public String validateOrder(String requestXml) {
		log.info("request validateOrder:" + requestXml);
		String errorNo = Constant.DISTRIBUTION_MSG.SUCCESS.getCode();
		Request request = null;
		String responseXml = "";
		DistributorInfo distributorInfo = null;
		try {
			request = DistributionUtil.getRequestCreateOrder(requestXml);
			log.info("localSign: " + request.getLocalSigned());
		} catch (Exception e) {
			errorNo = "1014";
			log.error("RequestValidateOrder Exception :", e);
			responseXml = this.createResult(errorNo , null);
			log.info("response validateOrder xml:" + responseXml);
			return responseXml;
		}
		distributorInfo = this.distributionCommonService.getDistributorByCode(request.getRequestHead().getPartnerCode());
		errorNo = checkRequest(distributorInfo, request);
		if (!Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)) {
			responseXml = this.createResult(errorNo , null);
			log.info("response validateOrder:" + responseXml);
			return responseXml;
		}
		OrderInfo orderInfo = request.getRequestBody().getOrderInfo();
		Long productId = Long.parseLong(orderInfo.getProductId());
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("distributorInfoId", distributorInfo.getDistributorInfoId());
		params.put("productId", productId);
		Boolean flag = true;
		Boolean isTaocan = false;
		List<Branch> branchList = orderInfo.getProductBranch().getBranchList();
		if (branchList != null && !branchList.isEmpty()) {
			for(Branch branch : branchList){
				String branchId = branch.getBranchId();
				
				params.put("productBranchId", branchId);
				params.put("volid", Constant.TRUE_FALSE.TRUE.getCode());
				flag = distributionProductService.selectDistributionProductBranchList(params);
				if(!isTaocan){
					ProdProductBranch prodBranch = prodProductBranchService.selectProdProductBranchByPK(Long.valueOf(branchId));
					isTaocan = prodBranch.getAdultQuantity()+prodBranch.getChildQuantity()>1L;
				}
				if(!flag){
					break;
				}

			}
		}else{
			errorNo = "1013";// 未选购产品!;
		}
		if(flag){
			ProdProduct product = prodProductService.getProdProductById(productId);
			Date onlineTime = product.getOnlineTime();
			Date offLineTime = product.getOfflineTime();
			Date nowTime = new Date();
			if (nowTime.after(onlineTime) && nowTime.before(offLineTime)) {
				boolean validateCardNo = validateCardNo(isTaocan,product, orderInfo);
				if (validateCardNo) {
					if ("true".equalsIgnoreCase(product.getOnLine())) {
						BuyInfo buyInfo = new BuyInfo();
						FirstCustomer firstCustomer = orderInfo.getFirstVisitCustomer();
						String mobileNumber = firstCustomer.getMobile();
						// 注册用户
						errorNo = this.rigestUser(mobileNumber, buyInfo, distributorInfo.isRegisterUser());
						log.info("errorNo:" + errorNo);
						if (Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)) {
							// 创建联系人列表
							createContacts(orderInfo, buyInfo.getUserId());
							List<Item> itemList = new ArrayList<Item>();
							for (int i = 0; i < branchList.size(); i++) {
								Branch branch = branchList.get(i);
								String visitDate = branch.getVisitDate();
								Long branchId = Long.parseLong(branch.getBranchId());
								Long quan = Long.valueOf(branch.getQuantity());
								Float price = Float.valueOf(branch.getSellPrice());
								Date visitD = null;
								try {
									visitD = DateUtil.toDate(visitDate, "yyyy-MM-dd");
								} catch (Exception e) {
									errorNo = "1018";
									break;
								}
								// 验证订单信息
								errorNo = this.validateOrderInfo(branch);
								log.info("errorNo:" + errorNo);
								if (!Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)) {
									break;
								}
								Item item = new Item();
								item.setProductId(productId);
								item.setVisitTime(visitD);
								item.setQuantity(quan.intValue());
								item.setProductBranchId(branchId);
								// 单房型，需设置每天时间价格信息
								if (Constant.PRODUCT_TYPE.HOTEL.name().equals(product.getProductType())
										&& Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(product.getSubProductType())) {
									errorNo = this.createSingleRoom(branch, product, item, i, quan);
									log.info("errorNo:" + errorNo);
									if (!Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)) {
										break;
									}
								} else {
									TimePrice timePrice = prodProductService.calcProdTimePrice(item.getProductBranchId(), item.getVisitTime(),
											false);
									if (timePrice == null) {
										errorNo = "1028";// "当前日期不能预订，请选择其它时间";
										break;
									} else {
										boolean productSellable = prodProductService.isProductSellable(branchId, quan, visitD);
										Map<String, Object> paramsMap = new HashMap<String, Object>();
										String productType = product.getProductType();
										paramsMap.put("productType", productType);
										paramsMap.put("distributorInfoId", distributorInfo.getDistributorInfoId());
										if (Constant.PRODUCT_TYPE.ROUTE.name().equals(productType)) {
											paramsMap.put("subProductType", product.getSubProductType());
										}else if (Constant.PRODUCT_TYPE.TICKET.name().equals(productType)) {
											boolean payOnline = product.isPaymentToLvmama();
											String onlineVal = payOnline ? Constant.TRUE_FALSE.TRUE.getValue() : Constant.TRUE_FALSE.FALSE.getValue();
											paramsMap.put("payOnline", onlineVal);
										}
										boolean distributeSellable = distributionProductService.isSellableDistributionProductTimePrice(paramsMap, timePrice);
										if (timePrice.getSellPriceFloat() != price) {
											errorNo = "1031";// "当前日期的价格不符，请检查价格";
											break;
										} else if (!productSellable || !distributeSellable) {
											errorNo = "1011";// "当前库存不足，请选择其他日期";
											log.info("productSellable= " + productSellable + " distributeSellable= "+distributeSellable );
											break;
										}
									}
								}
								if (i == 0) {
									item.setIsDefault("true");
								}
								itemList.add(item);
							}
							if (Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)) {
								buyInfo.setChannel(distributorInfo.getChannelCode()); // 分销渠道
								buyInfo.setItemList(itemList);
								buyInfo.setPersonList(createPersonList(orderInfo, buyInfo.getUserId()));
								ResultHandle handle = orderServiceProxy.checkOrderStock(buyInfo);
								if (handle.isFail()) {
									errorNo = "1028";
								}
							}
						}

					} else {
						errorNo = "1032";// 产品已下线
					}
				} else {
					errorNo = "1033";// 游客信息部分为空
				}
			}else{
				errorNo = "1028";
			}
		}else{
			errorNo="1027";//没有此分销产品
		}
		log.info("errorNo:" + errorNo);
		if (Constant.DISTRIBUTION_MSG.SUCCESS.getCode().equals(errorNo)) {
			errorNo = Constant.DISTRIBUTION_MSG.SUCCESS.getCode();
		}
		responseXml = this.createResult(errorNo, "");
		log.info("response validateOrder:" + responseXml);
		return responseXml;
	}

	private boolean validateCardNo(Boolean isTaocan,ProdProduct product, OrderInfo orderInfo) {
		List<String> firstTraveller = product.getFirstTravellerInfoOptionsList();
		List<String> otherTraveller = product.getTravellerInfoOptionsList();
		FirstCustomer firstCustomer = orderInfo.getFirstVisitCustomer();
		if(StringUtils.isBlank(firstCustomer.getName())){
			return false;
		}
		if(StringUtils.isBlank(firstCustomer.getMobile())){
			return false;
		}
		if(firstTraveller != null){
			if(firstTraveller.contains("PINYIN") && StringUtils.isBlank(firstCustomer.getPinyin())){
				return false;
			}else if (firstTraveller.contains("CARD_NUMBER") && StringUtils.isBlank(firstCustomer.getCredentials())){
				return false;
			}
		}
		
		if(otherTraveller != null && firstTraveller!=null){
			OtherCustomer otherCustomer = orderInfo.getOtherVisitCustomer();
			List< com.lvmama.distribution.model.lv.Person> visitorList = otherCustomer.getPersonList();
			Branch branch = (Branch)orderInfo.getProductBranch().getBranchList().get(0);
			if(otherTraveller.contains("NAME")){
				if(visitorList != null){
					for(com.lvmama.distribution.model.lv.Person visitor : visitorList){
						if(Integer.parseInt(branch.getQuantity())>1 &&StringUtils.isBlank(visitor.getName())  ){
							return false;
						}
						if(isTaocan && StringUtils.isBlank(visitor.getName())){
							return false;
						}
					}
				}
			}
			if(otherTraveller.contains("PINYIN")){
				if(visitorList != null){
					for(com.lvmama.distribution.model.lv.Person visitor : visitorList){
						if(StringUtils.isBlank(firstCustomer.getPinyin())){
							return false;
						}
						if(Integer.parseInt(branch.getQuantity())>1 && StringUtils.isBlank(visitor.getPinyin())){
							return false;
						}
						if(isTaocan && StringUtils.isBlank(visitor.getPinyin())){
							return false;
						}
					}
				}
			}
			if (otherTraveller.contains("CARD_NUMBER")){
				if(visitorList != null){
					for(com.lvmama.distribution.model.lv.Person visitor : visitorList){
						if(StringUtils.isBlank(firstCustomer.getCredentials())){
							return false;
						}
						if(Integer.parseInt(branch.getQuantity())>1 && StringUtils.isBlank(visitor.getCredentials())){
							return false;
						}
						if(isTaocan && StringUtils.isBlank(visitor.getCredentials())){
							return false;
						}
					}
				}
			}
			if(otherTraveller.contains("MOBILE")){
				if(visitorList != null){
					for(com.lvmama.distribution.model.lv.Person visitor : visitorList){
						if(Integer.parseInt(branch.getQuantity())>1 && StringUtils.isBlank(visitor.getMobile())){
							return false;
						}
						if(isTaocan && StringUtils.isBlank(visitor.getMobile())){
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setDistributionProductService(DistributionProductService distributionProductService) {
		this.distributionProductService = distributionProductService;
	}

	public DistributionOrderService getDistributionOrderService() {
		return distributionOrderService;
	}

	public void setDistributionOrderService(DistributionOrderService distributionOrderService) {
		this.distributionOrderService = distributionOrderService;
	}
}