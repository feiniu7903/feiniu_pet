package com.lvmama.tnt.order.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.prod.LimitSaleTime;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.service.prod.ProdProductRoyaltyService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.prod.ProductHeadQueryService;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.comm.pet.service.user.IReceiverUserService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.SynchronizedLock;
import com.lvmama.comm.utils.UUIDGenerator;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.utils.ord.TimePriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.tnt.comm.util.PriceUtil;
import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.comm.vo.ResultGod;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.order.po.TntOrder;
import com.lvmama.tnt.order.vo.Person;
import com.lvmama.tnt.order.vo.TntBuyInfo;
import com.lvmama.tnt.order.vo.TntOrderItemProd;
import com.lvmama.tnt.order.vo.TntPayPayment;
import com.lvmama.tnt.order.vo.TntPriceInfo;
import com.lvmama.tnt.prod.service.TntProdPolicyService;

/**
 * 分销平台创建订单
 * 
 * @author gaoxin
 * 
 */
@Repository("orderCreateService")
public class OrderCreateServiceImpl implements OrderCreateService {
	protected static final Log LOG = LogFactory
			.getLog(OrderCreateServiceImpl.class);

	private static final String ERROR = "error";
	/**
	 * 是否需要邮寄:需要.
	 */
	private static final String NEED_PHYSICAL = "true";
	@Autowired
	protected OrderService orderServiceProxy;
	@Autowired
	protected ProdProductService prodProductService;
	@Autowired
	private PageService pageService;
	@Autowired
	private PlaceCityService placeCityService;
	@Autowired
	private IReceiverUserService receiverUserService;
	@Autowired
	private TntProdPolicyService tntProdPolicyService;

	@Autowired
	protected PayPaymentService payPaymentService;
	@Autowired
	protected TopicMessageProducer resourceMessageProducer;
	/**
	 * 查询附加产品类别
	 **/
	@Autowired
	private ProductHeadQueryService productServiceProxy;

	@Autowired
	private ProdProductRoyaltyService prodProductRoyaltyService;

	private BuyInfo toOrderBuyInfo(TntBuyInfo tntBuyInfo) {
		BuyInfo createOrderBuyInfo = new BuyInfo();
		createOrderBuyInfo.setMainProductType(tntBuyInfo.getProductType());
		createOrderBuyInfo.setUserId(this.getUserId());
		createOrderBuyInfo.setChannel(TntConstant.CHANNEL_CODE.DISTRIBUTOR_B2B
				.name());
		// 不定期
		List<Item> list = this.getItem(tntBuyInfo);
		createOrderBuyInfo.setIsAperiodic(tntBuyInfo.isAperiodic());
		if (Boolean.parseBoolean(tntBuyInfo.isAperiodic())) {
			createOrderBuyInfo
					.setValidBeginTime(tntBuyInfo.getValidBeginTime());
			createOrderBuyInfo.setValidEndTime(tntBuyInfo.getValidEndTime());
		}
		createOrderBuyInfo.setItemList(list);
		createOrderBuyInfo.setPersonList(this.getPerson(tntBuyInfo));
		if (StringUtils.equals(tntBuyInfo.getUserMemo(), "您对订单的特殊要求")) {
			createOrderBuyInfo.setUserMemo("");
		} else {
			createOrderBuyInfo.setUserMemo(tntBuyInfo.getUserMemo());
		}
		// 支付对象
		String paymentTarget = TntConstant.PRODUCT_PAY_TYPE.TOLVMAMA.name();
		if (!tntBuyInfo.getIsPayToLvmama()) {
			paymentTarget = TntConstant.PRODUCT_PAY_TYPE.TOSUPPLIER.name();
		}
		createOrderBuyInfo.setPaymentTarget(paymentTarget);
		createOrderBuyInfo.setWaitPayment(Constant.WAIT_PAYMENT.PW_DEFAULT
				.getValue());

		// 支付渠道
		createOrderBuyInfo
				.setPaymentChannel(Constant.PAYMENT_GATEWAY_DIST_MANUAL.DISTRIBUTOR_B2B
						.name());
		return createOrderBuyInfo;
	}

	public BuyInfo getOrderInfo(TntBuyInfo tntBuyInfo) throws Exception {
		BuyInfo createOrderBuyInfo = new BuyInfo();
		createOrderBuyInfo.setMainProductType(tntBuyInfo.getProductType());
		createOrderBuyInfo.setItemList(this.getItem(tntBuyInfo));
		createOrderBuyInfo.setWaitPayment(Constant.WAIT_PAYMENT.PW_DEFAULT
				.getValue());
		createOrderBuyInfo.setIsAperiodic(tntBuyInfo.isAperiodic());
		if (Boolean.parseBoolean(tntBuyInfo.isAperiodic())) {
			createOrderBuyInfo
					.setValidBeginTime(tntBuyInfo.getValidBeginTime());
			createOrderBuyInfo.setValidEndTime(tntBuyInfo.getValidEndTime());
		}
		return createOrderBuyInfo;
	}

	@Override
	public ResultGod<TntOrder> createOrder(TntBuyInfo tntBuyInfo)
			throws Exception {
		ResultGod<TntOrder> god = new ResultGod<TntOrder>();
		String error = validateSubmitDate(tntBuyInfo);
		if (error == null) {
			BuyInfo createOrderBuyInfo = toOrderBuyInfo(tntBuyInfo);
			ResultHandle result = orderServiceProxy
					.checkOrderStock(createOrderBuyInfo);
			if (result.isFail()) {
				error = "该产品库存不足，无法下单！";
			} else {
				try {
					OrdOrder order = orderServiceProxy
							.createOrder(createOrderBuyInfo);
					if (order != null && order.getIsShHolidayOrder()) {
						if (Constant.ORDER_STATUS.CANCEL.getCode()
								.equalsIgnoreCase(order.getOrderStatus())) {
							error = "bookFail";
						}
					}
					if (error == null) {
						order = orderServiceProxy.queryOrdOrderByOrderId(order
								.getOrderId());
						if (order != null) {
							Long orderId = order.getOrderId();
							LOG.debug("new order id:" + orderId);

							tntBuyInfo.setOrderId(orderId);
							saveOrderPersonInfo(orderId, tntBuyInfo);
							Long userId = TntUtil.parserLong(tntBuyInfo
									.getDistributor());
							// 计算分销订单价格
							Long distOrderAmount = calculateDistOrderAmount(
									order, userId);
							TntOrder tntOrder = reserve(order);
							if (distOrderAmount != null) {
								tntOrder.setOrderAmount(distOrderAmount);
							}
							tntOrder.setDistributorId(TntUtil
									.parserLong(tntBuyInfo.getDistributor()));
							god.setResult(tntOrder);
						} else {
							error = "下单失败";
						}
					}
				} catch (Exception e) {
					error = e.getMessage();
					e.printStackTrace();
				}
			}
		}
		god.setSuccess(error == null);
		god.setErrorText(error);
		return god;
	}

	/**
	 * 订单支付
	 * 
	 * @param orderId
	 *            lvmama订单号
	 * @param paymentGateway
	 *            支付渠道
	 * @param operator
	 *            支付人
	 * @return
	 */
	@Override
	public boolean orderPayment(TntPayPayment tntPayPayment) {
		Long orderId = tntPayPayment.getOrderId();
		PayPayment payPayment = new PayPayment();
		payPayment.setObjectId(orderId);
		payPayment.setSerial(payPayment.geneSerialNo());
		String key = "PAYMENT_DISTRIBUTION_ACTION" + payPayment.getSerial();
		if (SynchronizedLock.isOnDoingMemCached(key)) {
			return false;
		}
		try {
			String paymentGateway = tntPayPayment.getPaymentGateway();
			String operator = tntPayPayment.getOperator();
			String gatewayTradeNo = tntPayPayment.getGatewayTradeNo();
			String paymentTradeNo = tntPayPayment.getPaymentTradeNo();
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			Date clllbackTime = new Date();
			payPayment.setCallbackInfo("分销支付");
			payPayment.setGatewayTradeNo(gatewayTradeNo);
			payPayment.setPaymentTradeNo(paymentTradeNo);
			payPayment.setCallbackTime(clllbackTime);
			payPayment.setCreateTime(clllbackTime);
			payPayment.setPaymentGateway(paymentGateway);
			payPayment.setAmount(order.getOughtPay());
			payPayment.setOperator(operator);
			payPayment.setBizType(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER
					.getCode());
			payPayment.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
			payPayment.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
			payPayment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());

			Long paymentId = payPaymentService.savePayment(payPayment);
			resourceMessageProducer.sendMsg(MessageFactory
					.newPaymentSuccessCallMessage(paymentId));
		} finally {
			SynchronizedLock.releaseMemCached(key);
		}
		return true;
	}

	protected List<Item> getItem(TntBuyInfo tntBuyInfo) {
		List<Item> itemList = new ArrayList<Item>();
		if (tntBuyInfo.getProductId() != null) {
			ProdProduct product = pageService
					.getProdProductByProductId(tntBuyInfo.getProductId());
			if (product != null) {
				tntBuyInfo.setAperiodic(product.getIsAperiodic());
			}
		} else if (tntBuyInfo.getBranchId() != null) {
			ProdProductBranch branch = pageService
					.getProdBranchByProdBranchId(tntBuyInfo.getBranchId());
			if (branch != null) {
				tntBuyInfo.setAperiodic(branch.getProdProduct()
						.getIsAperiodic());
			}
		}
		Date beginTime = null, endTime = null;
		Map<Long, Long> ordOrderItemProds = tntBuyInfo.getOrdItemProdList();
		for (Iterator<Long> iterator = ordOrderItemProds.keySet().iterator(); iterator
				.hasNext();) {
			Long prodBranchId = (Long) iterator.next();
			Long quantity = ordOrderItemProds.get(prodBranchId);
			if (quantity != null && quantity > 0) {
				Item item = new Item();
				ProdProductBranch branch = pageService
						.getProdBranchByProdBranchId(prodBranchId);
				item.setProductId(branch.getProductId());
				item.setProductBranchId(branch.getProdBranchId());
				item.setQuantity(Integer.parseInt(ordOrderItemProds
						.get(prodBranchId) + ""));
				item.setFaxMemo(null);
				item.setProductType(branch.getProdProduct().getProductType());
				item.setSubProductType(branch.getProdProduct()
						.getSubProductType());
				// 不定期取有效期最后日期为游玩日期
				if (branch.getProdProduct().IsAperiodic()) {
					Date validBeginTime = branch.getValidBeginTime();
					Date validEndTime = branch.getValidEndTime();
					if (validBeginTime != null
							&& validEndTime != null
							&& !DateUtil.getDayStart(new Date()).after(
									validEndTime)) {
						item.setVisitTime(validEndTime);
						item.setValidBeginTime(validBeginTime);
						item.setValidEndTime(validEndTime);
						item.setInvalidDate(branch.getInvalidDate());
						item.setInvalidDateMemo(branch.getInvalidDateMemo());
						itemList.add(item);

						// 取多类别最大区间有效期
						if (beginTime == null) {
							beginTime = validBeginTime;
						} else {
							beginTime = beginTime.before(validBeginTime) ? beginTime
									: validBeginTime;
						}
						if (endTime == null) {
							endTime = validEndTime;
						} else {
							endTime = endTime.after(validEndTime) ? endTime
									: validEndTime;
						}
					}
				} else {
					item.setVisitTime(tntBuyInfo.getVisitDate());
					itemList.add(item);
				}
			}
		}

		/**
		 * 设置订单的主产品.
		 */
		final Long branchId = tntBuyInfo.getBranchId();
		Item item = (Item) CollectionUtils.find(itemList, new Predicate() {
			public boolean evaluate(Object arg0) {
				Item item = (Item) arg0;
				return branchId.equals(item.getProductBranchId());
			}
		});
		if (!itemList.isEmpty()) {
			if (item == null) {
				if (itemList.size() == 1) {
					item = itemList.get(0);
				} else {
					for (BuyInfo.Item it : itemList) {
						if (!Constant.PRODUCT_TYPE.OTHER.name().equals(
								it.getProductType())) {
							item = it;
							break;
						}
					}
					if (item == null) {
						item = itemList.get(0);
					}
				}
			}
			item.setIsDefault("true");
		}
		if (beginTime != null && endTime != null) {
			tntBuyInfo.setValidBeginTime(beginTime);
			tntBuyInfo.setValidEndTime(endTime);
		}

		return itemList;
	}

	/**
	 * 验证购买数量是否小于最低购买数
	 * 
	 * @param 验证是否存在非法数据
	 */
	@SuppressWarnings("deprecation")
	@Override
	public String validateSubmitDate(TntBuyInfo tntBuyInfo) {
		String error = null;
		try {
			Person contact = tntBuyInfo.getContact();
			// 判断取票人数据是否合法
			if (contact == null || contact.getFullName() == null
					|| contact.getMobile() == null
					|| StringUtils.isEmpty(contact.getFullName().trim())
					|| StringUtils.isEmpty(contact.getMobile().trim())) {
				error = "=====用户:" + tntBuyInfo.getDistributor()
						+ "下单时=======取票人Contact为NULL";
				return error;
			}
			BuyInfo buyInfo = this.getOrderInfo(tntBuyInfo);
			List<Item> itemList = buyInfo.getItemList();
			ProdProduct prodProduct = pageService
					.getProdProductByProductId(tntBuyInfo.getProductId());
			List<ProdProductRelation> relateList = new ArrayList<ProdProductRelation>();
			Map<Long, ProdProductRelation> relateListMap = new HashMap<Long, ProdProductRelation>();
			if (!prodProduct.IsAperiodic()) {
				// 关联的附加商品.
				relateList = productServiceProxy.getRelatProduct(
						tntBuyInfo.getProductId(), tntBuyInfo.getVisitDate());
				for (int i = 0; i < relateList.size(); i++) {
					relateListMap.put(relateList.get(i).getProdBranchId(),
							relateList.get(i));
				}
			}
			int buyNum = 0; // 购买总数
			for (Item item : itemList) {
				// 不是附加产品才纳入 购买数
				if (relateListMap.get(item.getProductBranchId()) == null) {
					ProdProductBranch branch = pageService
							.getProdBranchByProdBranchId(item
									.getProductBranchId());
					Long adultQuantity = branch.getAdultQuantity() == null ? 0L
							: branch.getAdultQuantity();
					Long childQuantity = branch.getChildQuantity() == null ? 0L
							: branch.getChildQuantity();
					buyNum += (item.getQuantity() * (adultQuantity + childQuantity));
				}
			}
			for (Item item : itemList) {
				if (relateListMap.get(item.getProductBranchId()) != null) {
					ProdProduct product = pageService
							.getProdProductByProductId(item.getProductId());
					// 保险、税金 人数应与购买人数一致
					if (StringUtils.equals(product.getSubProductType(),
							Constant.SUB_PRODUCT_TYPE.INSURANCE.name())
							|| StringUtils.equals(product.getSubProductType(),
									Constant.SUB_PRODUCT_TYPE.TAX.name())) {
						if (buyNum != item.getQuantity()) {
							error = "=====分销商:" + tntBuyInfo.getDistributor()
									+ "下单时=======存在错误数据";
							return error;
						}
					} else if (StringUtils.equals(product.getSubProductType(),
							Constant.SUB_PRODUCT_TYPE.EXPRESS.name())) { // 快递
						if (item.getQuantity() != 1) {
							error = "=====分销商:" + tntBuyInfo.getDistributor()
									+ "下单时=======存在错误数据";
							return error;
						}
					} else {
						ProdProductRelation prodRelation = relateListMap
								.get(item.getProductBranchId());
						if (null != prodRelation) {
							ProdProductBranch branch = prodRelation.getBranch();
							int miniNum = branch.getMinimum() == null ? 0
									: branch.getMinimum().intValue();
							int maxNum = branch.getMaximum() == null ? 0
									: branch.getMaximum().intValue();
							if (prodRelation.getSaleNumType().equals(
									Constant.SALE_NUMBER_TYPE.OPT.name())) {
								if (item.getQuantity() < miniNum
										|| (maxNum != 0 && item.getQuantity() > maxNum)) {
									error = "=====分销商:"
											+ tntBuyInfo.getDistributor()
											+ "下单时=======存在错误数据";
									return error;
								}
							} else if (prodRelation.getSaleNumType().equals(
									Constant.SALE_NUMBER_TYPE.ANY.name())) {
								if (item.getQuantity() < miniNum
										|| (maxNum != 0 && item.getQuantity() > maxNum)) {
									error = "=====分销商:"
											+ tntBuyInfo.getDistributor()
											+ "下单时=======存在错误数据";
									return error;
								}
							} else if (prodRelation.getSaleNumType().equals(
									Constant.SALE_NUMBER_TYPE.ALL.name())) {
								if (item.getQuantity() != buyNum) {
									error = "=====分销商:"
											+ tntBuyInfo.getDistributor()
											+ "下单时=======存在错误数据";
									return error;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {

		}
		return error;
	}

	/**
	 * 保存订单邮寄地址信息.
	 */
	private void saveOrderPersonInfo(Long orderId, TntBuyInfo tntBuyInfo) {
		// 此产品需要邮寄地址.
		if (NEED_PHYSICAL.equals(tntBuyInfo.getPhysical())) {
			UUIDGenerator gen = new UUIDGenerator();
			String receiverId = gen.generate().toString();
			UsrReceivers receiverAddress = new UsrReceivers();
			receiverAddress.setReceiverId(receiverId);
			receiverAddress.setUserId(getUserId());
			receiverAddress.setIsValid("Y");
			receiverAddress.setCreatedDate(new Date());
			receiverAddress.setReceiversType(Constant.RECEIVERS_TYPE.ADDRESS
					.name());
			this.initUsrReceiversSaveAddress(receiverAddress, tntBuyInfo);
			this.receiverUserService.insert(receiverAddress);
			receiverAddress = this.receiverUserService
					.getRecieverByPk(receiverId);
			com.lvmama.comm.bee.vo.ord.Person person = usrReceiver2OrdPerson(receiverAddress);
			this.orderServiceProxy.addPerson2OrdOrder(person, orderId,
					getUserId());
		}
	}

	/**
	 * 初始化usrReceivers对象的省份(province)与城市(city)属性值,将code码转换为name.
	 */
	private void initUsrReceiversSaveAddress(UsrReceivers receiverAddress,
			TntBuyInfo tntBuyInfo) {
		Person receiver = tntBuyInfo.getUsrReceivers();
		String province = receiver.getProvince();
		String city = receiver.getCity();
		receiverAddress.setAddress(receiver.getAddress());
		receiverAddress.setBrithday(receiver.getBirthday());
		receiverAddress.setCardNum(receiver.getCertNo());
		receiverAddress.setCardType(receiver.getCertType());
		receiverAddress.setEmail(receiver.getEmail());
		receiverAddress.setFax(receiver.getFax());
		receiverAddress.setGender(receiver.getGender());
		receiverAddress.setMobileNumber(receiver.getMobile());
		receiverAddress.setPhone(receiver.getPhone());
		receiverAddress.setPinyin(receiver.getPinyin());
		receiverAddress.setPostCode(receiver.getPostCode());
		receiverAddress.setReceiverName(receiver.getFullName());
		if (StringUtils.isNotEmpty(province)) {// 用编号转名字
			ComProvince cp = placeCityService.selectByPrimaryKey(province);
			if (cp != null) {
				receiverAddress.setProvince(cp.getProvinceName());
				ComCity cc = placeCityService.selectCityByPrimaryKey(city);
				if (cc != null) {
					receiverAddress.setCity(cc.getCityName());
				}
			}
		}
	}

	/**
	 * 将UsrReceivers对象中的信息转换到Person对象中.
	 * 
	 * @param usrReceiver
	 * @return
	 */
	private com.lvmama.comm.bee.vo.ord.Person usrReceiver2OrdPerson(
			UsrReceivers usrReceiver) {
		com.lvmama.comm.bee.vo.ord.Person result = new com.lvmama.comm.bee.vo.ord.Person();
		result.setReceiverId(usrReceiver.getReceiverId());
		result.setPersonType(Constant.ORD_PERSON_TYPE.ADDRESS.name());
		result.setAddress(usrReceiver.getProvince() + " "
				+ usrReceiver.getCity() + " " + usrReceiver.getAddress());
		result.setProvince(usrReceiver.getProvince());
		result.setCity(usrReceiver.getCity());
		result.setMobile(usrReceiver.getMobileNumber());
		result.setName(usrReceiver.getReceiverName());
		result.setEmail(usrReceiver.getEmail());
		result.setPostcode(usrReceiver.getPostCode());
		result.setCertNo(usrReceiver.getCardNum());
		return result;
	}

	/**
	 * 获取游客填写的联系信息
	 * 
	 * @return
	 */
	public List<com.lvmama.comm.bee.vo.ord.Person> getPerson(
			TntBuyInfo tntBuyInfo) {
		List<com.lvmama.comm.bee.vo.ord.Person> personList = new ArrayList<com.lvmama.comm.bee.vo.ord.Person>();

		// 联系人
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(tntBuyInfo
				.getContact().getMobile())) {
			personList.add(this.setPerson(tntBuyInfo.getContact(),
					Constant.ORD_PERSON_TYPE.CONTACT.name()));
		}
		// 游客信息
		List<Person> travellerList = tntBuyInfo.getTravellers();
		for (int i = 0; i < travellerList.size(); i++) {
			Person traveller = travellerList.get(i);
			if (traveller != null) {
				com.lvmama.comm.bee.vo.ord.Person person = this.setPerson(
						traveller, Constant.ORD_PERSON_TYPE.TRAVELLER.name());
				personList.add(person);
			}
		}
		Person emergencyContact = tntBuyInfo.getEmergencyContact();
		// 紧急联系人
		if (emergencyContact != null) {
			personList.add(setPerson(emergencyContact,
					Constant.ORD_PERSON_TYPE.EMERGENCY_CONTACT.name()));
		}

		// 预订人信息
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(tntBuyInfo
				.getContact().getMobile())) {
			personList.add(this.setPerson(tntBuyInfo.getContact(),
					Constant.ORD_PERSON_TYPE.BOOKER.name()));
		}
		return personList;
	}

	public com.lvmama.comm.bee.vo.ord.Person setPerson(Person person,
			String personType) {
		com.lvmama.comm.bee.vo.ord.Person lvPerson = new com.lvmama.comm.bee.vo.ord.Person();
		lvPerson.setName(person.getFullName());
		lvPerson.setMobile(person.getMobile());
		lvPerson.setPersonType(personType);
		lvPerson.setReceiverId(null);
		lvPerson.setCertNo(person.getIdNo());
		lvPerson.setCertType(person.getIdType());
		lvPerson.setEmail(person.getEmail());
		lvPerson.setGender(person.getGender());
		lvPerson.setPinyin(person.getPinyin());
		String birthday = person.getBirthday();
		if (birthday != null && !birthday.isEmpty())
			lvPerson.setBrithday(TntUtil.stringToDate(birthday));
		return lvPerson;
	}

	private String getUserId() {
		return Constant.getInstance().getDefaultRegisterUser();
	}

	@Override
	public TntPriceInfo countPrice(TntBuyInfo info) {
		BuyInfo buyInfo;
		int price = 0;
		Long marketPrice = 0l;
		try {
			boolean payToLvmama = info.getIsPayToLvmama();
			buyInfo = getOrderInfo(info);
			List<Item> items = buyInfo.getItemList();
			if (items != null) {
				Long userId = TntUtil.parserLong(info.getDistributor());
				for (Item item : items) {
					Long branchId = item.getProductBranchId();
					Date visitDate = item.getVisitTime();
					TimePrice timePrice = this.prodProductService
							.calcProdTimePrice(branchId, visitDate);
					if (timePrice != null) {
						price += PriceUtil
								.convertToYuanInt(tntProdPolicyService
										.calculatePrice(branchId, userId,
												timePrice.getPrice(),
												timePrice.getSettlementPrice(),
												payToLvmama))
								* item.getQuantity();
						marketPrice += timePrice.getMarketPrice();
					}

				}
			}

			TntPriceInfo t = new TntPriceInfo();
			t.setOughtPay((float) price);
			t.setMarketPrice(marketPrice);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> checkDistPrice(TntBuyInfo info) {
		Map<String, Object> map = checkPrice(info);
		List<Map<String, Object>> prdTimePriceList = (List<Map<String, Object>>) map
				.get("price");
		String distributor = info.getDistributor();
		if (prdTimePriceList != null && distributor != null) {
			Long userId = TntUtil.parserLong(distributor);
			for (Map<String, Object> priceMap : prdTimePriceList) {
				Long branchId = (Long) priceMap.get("branchId");
				Long sellPrice = (Long) priceMap.get("sellPrice");
				Boolean isPayToLvmama = Boolean.parseBoolean(priceMap.get(
						"isPayToLvmama").toString());
				TimePrice timePrice = prodProductService.calcProdTimePrice(
						branchId, info.getVisitDate());
				if (timePrice != null) {
					Long price = tntProdPolicyService.calculatePrice(branchId,
							userId, sellPrice, timePrice.getSettlementPrice(),
							isPayToLvmama);
					priceMap.put("price", PriceUtil.convertToYuanInt(price));
				}
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> checkPrice(TntBuyInfo info) {
		Long productId = info.getProductId();
		Long branchId = info.getBranchId();
		String choseDate = info.getVisitTime();
		boolean isProductPreview = info.isProductPreview();
		String error = null;
		String ERROR = "error";
		boolean submitOrder = info.isSubmitOrder();
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("flag", "N");
		if (productId != null || branchId != null) {
			ProdProduct prodProduct = pageService
					.getProdProductByProductId(productId);
			if (prodProduct == null) {
				error = "当前商品不可售";
				jsonMap.put(ERROR, error);
				return jsonMap;
			}
			List<ProdProductBranch> list = new ArrayList<ProdProductBranch>();
			Date visitDate = null;
			// 非不定期才校验主类别
			if (!prodProduct.IsAperiodic()) {
				visitDate = DateUtil.toDate(choseDate,
						DateUtil.PATTERN_yyyy_MM_dd);
				if (branchId != null) {
					ProdProductBranch branch = this.productServiceProxy
							.getProdBranchDetailByProdBranchId(branchId,
									visitDate, !isProductPreview);
					if (branch == null) {
						error = "该商品"
								+ DateUtil.getFormatDate(visitDate,
										"yyyy-MM-dd") + "不可售";
					} else {
						// 为非酒店单房型时只能展示该类别
						ProdProduct product = pageService
								.getProdProductByProductId(branch
										.getProductId());
						if (product.getOnlineTime().getTime() > System
								.currentTimeMillis()) {
							error = "此产品未到销售时间";
						} else {
							if (product.isSingleRoom()) {
								productId = branch.getProductId();
								list.add(branch);
							}
						}
					}
				}
			}
			// 非不定期,visitDate为null无影响,会取branch的最晚有效期来做校验
			if (list.isEmpty()) {// 为非酒店单房型时需要展示所有的可售类别
				list = this.productServiceProxy.getProdBranchListAndOnline(
						productId, null, visitDate, !isProductPreview);
			}
			if (CollectionUtils.isNotEmpty(list)) {
				ProdProductBranch productBranch = list.get(0);
				ProdProduct product = productBranch.getProdProduct();
				// 产品类型
				jsonMap.put("productType", product.getProductType());
				// 自主打包
				jsonMap.put("selfPack", productBranch.getProdProduct()
						.hasSelfPack());
				LimitSaleTime canSaleDateTime = pageService.getDateCanSale(
						productBranch.getProductId(), visitDate);
				if (!pageService.checkDateCanSale(productBranch.getProductId(),
						visitDate)) {
					if (prodProductRoyaltyService.getRoyaltyProductIds()
							.contains(productBranch.getProductId())) {
						error = "该产品" + productBranch.getProductId()
								+ "可预订范围为每天的"
								+ canSaleDateTime.getLimitHourStart() + "至"
								+ canSaleDateTime.getLimitHourEnd();
					} else {
						if (StringUtils.equals(canSaleDateTime.getLimitType(),
								Constant.LIMIT_SALE_TYPE.HOURRANGE.getCode())) {
							error = "此产品当前游玩日期可预订范围为每天的"
									+ canSaleDateTime.getLimitHourStart() + "至"
									+ canSaleDateTime.getLimitHourEnd();
						} else {
							error = "此产品当前游玩日期需要"
									+ DateUtil.getDateTime("yyyy-MM-dd HH:mm",
											canSaleDateTime.getLimitSaleTime())
									+ "后下单";
						}

					}
				} else {
					// 酒店单房型检查
					if (branchId != null && product.isHotel()
							&& product.isSingleRoom() && !product.IsAperiodic()) {
						// String endDate = info.getEndDate();
						// Date date = null;
						// if (StringUtils.isNotEmpty(endDate)) {
						// date = DateUtil.toDate(endDate,
						// DateUtil.PATTERN_yyyy_MM_dd);
						// } else {
						// date = DateUtil.dsDay_Date(visitDate, 1);
						// }
						// this.checkHotle(productBranch, visitDate, date);
					} else {
						// 非酒店单房型检查(不定期产品暂时不做附加产品)
						Map<String, Integer> buyNum = info.getBuyNum();
						if (submitOrder && !product.IsAperiodic()) {
							List<ProdProductRelation> relatList = this.productServiceProxy
									.getRelatProduct(
											productBranch.getProductId(),
											visitDate);
							if (CollectionUtils.isNotEmpty(relatList)) {
								for (ProdProductRelation relation : relatList) {
									list.add(relation.getBranch());
								}
							}
						}
						checkProduct(jsonMap, buyNum, visitDate, list);
					}
				}
			} else {
				error = "当前商品不可售";
			}
			if (error != null)
				jsonMap.put(ERROR, error);
			return jsonMap;
		}

		// 订单提交，如果jsonMap flag==N并且没有error的情况直接把error定义为无库存;
		if ("N".equals(jsonMap.get("flag")) && !jsonMap.containsKey(error)) {
			jsonMap.put(error, "预订的产品库存为空");
		}
		return null;
	}

	private void checkProduct(Map<String, Object> jsonMap,
			Map<String, Integer> ordNum, Date visitDate,
			List<ProdProductBranch> prodBranchList) {
		String error = null;
		if (CollectionUtils.isNotEmpty(prodBranchList)) {
			for (ProdProductBranch branch : prodBranchList) {
				Integer ordNumber = ordNum.get("param"
						+ branch.getProdBranchId());
				if (branch.getProdProduct().isAdditional()) {
					ordNumber = ordNum.get("addition"
							+ branch.getProdBranchId());
				}
				if (ordNumber != null) {
					// 不定期取类别的最后有效期做校验
					if (branch.getProdProduct().IsAperiodic()) {
						visitDate = branch.getValidEndTime();
					}
					error = check(branch, visitDate, ordNumber.longValue());
					if (error != null) {
						jsonMap.put(ERROR, error);
						return;
					}
				}
			}
		}
		List<Map<String, Object>> prdTimePriceList = new ArrayList<Map<String, Object>>();
		for (ProdProductBranch branch : prodBranchList) {
			Map<String, Object> priceMap = new HashMap<String, Object>();
			priceMap.put("branchName", branch.getBranchName());
			priceMap.put("branchId", branch.getProdBranchId());
			priceMap.put("price", branch.getSellPriceYuan() + "");
			priceMap.put("sellPrice", branch.getSellPrice());
			priceMap.put("branchDefault", branch.hasDefault());
			priceMap.put("minimum", branch.getMinimum());
			priceMap.put("maximum", branch.getMaximum());
			priceMap.put("priceUtil", branch.getPriceUnit());
			priceMap.put("isPayToLvmama", branch.getProdProduct()
					.isPaymentToLvmama());
			prdTimePriceList.add(priceMap);
		}
		jsonMap.put("price", prdTimePriceList);
		jsonMap.put("flag", "Y");
	}

	private String check(ProdProductBranch prodBranch, Date visitDate,
			Long ordNumber) {
		String error = null;
		String submitOrder = "true";
		if (visitDate.getTime() < DateUtil.getClearCalendar().getTime()
				.getTime()) {
			error = "该商品" + DateUtil.getFormatDate(visitDate, "yyyy-MM-dd")
					+ "不可售";
			return error;
		}

		if (!pageService.isVisitDateProduct(prodBranch.getProductId(),
				prodBranch.getProdBranchId(), visitDate)) {
			error = "该商品" + DateUtil.getFormatDate(visitDate, "yyyy-MM-dd")
					+ "不可售";
			return error;
		}

		// 开始时间限制检查

		LimitSaleTime limitSaleTime = pageService.getDateCanSale(
				prodBranch.getProductId(), visitDate);
		if (limitSaleTime != null) {
			if (!StringUtils.equals(limitSaleTime.getLimitType(),
					Constant.LIMIT_SALE_TYPE.HOURRANGE.getCode())) {
				String resTime = DateUtil.getFormatDate(
						limitSaleTime.getLimitSaleTime(), "yyyy-MM-dd HH:mm");
				error = "该商品此游玩日期的销售从" + resTime + "开始";
				return error;
			} else {
				error = "该商品此游玩日期的销售时间为每天的" + limitSaleTime.getLimitHourStart()
						+ "至" + limitSaleTime.getLimitHourEnd();
				return error;
			}
		}

		if (ordNumber == null || ordNumber < 1) {
			error = "请选择产品";
			return error;
		}
		if (StringUtils.equals("true", submitOrder)) {
			if (!prodBranch.hasOnline()
					|| !prodBranch.getProdProduct().isOnLine()) {
				error = "该商品" + DateUtil.getFormatDate(visitDate, "yyyy-MM-dd")
						+ "不可售";
				return error;
			}
		}

		// 最小订购数
		if (ordNumber < prodBranch.getMinimum()) {
			error = "产品【" + prodBranch.getFullName() + "】最小预订量为："
					+ prodBranch.getMinimum();
			return error;
		}

		// 最大订购数
		if (ordNumber > prodBranch.getMaximum()) {
			error = "产品【" + prodBranch.getFullName() + "】最多可售数量为"
					+ prodBranch.getMaximum();
			return error;
		}
		// ViewProdProduct vpp =
		// productRemoteService.getProductByProductId(productId, visitDate);
		// 检查库存数，是否可超卖
		// 当ordNumber为0时表示没有订购此产品，不需要检查库存
		if (ordNumber > 0
				&& !this.productServiceProxy.isSellable(
						prodBranch.getProdBranchId(), ordNumber, visitDate)) {
			String value = null;
			if (prodBranch.getStock() > 0) {
				value = "产品【" + prodBranch.getFullName() + "】库存不足";
			} else {
				value = "产品【"
						+ prodBranch.getFullName()
						+ "】,"
						+ DateUtil.getDateTime(DateUtil.PATTERN_yyyy_MM_dd,
								visitDate) + "库存不足,请选择其他日期";
			}
			error = value;
		}
		return error;
	}

	@Override
	public String checkOrderStock(TntBuyInfo info) throws Exception {
		String error = null;
		BuyInfo buyInfo = toOrderBuyInfo(info);
		try {
			if (buyInfo.getItemList().isEmpty()) {
				throw new Exception("未选购产品");
			}
			if (Constant.PRODUCT_TYPE.TRAFFIC.name().equals(
					buyInfo.getMainProductType())
					&& Constant.SUB_PRODUCT_TYPE.TRAIN.name().equals(
							buyInfo.getMainSubProductType())) {
				buyInfo.setLocalCheck(buyInfo.getLocalCheck());
				if (TimePriceUtil.hasTrainSoldout()) {
					throw new IllegalArgumentException("不在可售时间范围");
				}
			}
			// 不定期校验有效期
			if (buyInfo.IsAperiodic()) {
				if (buyInfo.getValidBeginTime() != null
						&& buyInfo.getValidEndTime() != null) {
					if (DateUtil.getDayStart(new Date()).after(
							buyInfo.getValidEndTime())) {
						throw new Exception("当前商品不可售");
					}
				} else {
					throw new Exception("当前商品不可售");
				}
			}
			ResultHandle handle = orderServiceProxy.checkOrderStock(buyInfo);
			if (handle.isFail()) {
				error = handle.getMsg();
			}
			if (buyInfo.getItemList() != null
					&& buyInfo.getItemList().size() > 0) {
				// 故宫门票每笔订单只能限定5张
				if (prodProductRoyaltyService.getRoyaltyProductIds().contains(
						buyInfo.getItemList().get(0).getProductId())) {
					int count = 0;
					for (BuyInfo.Item item : buyInfo.getItemList()) {
						count = item.getQuantity() + count;
					}
					if (count > 5) {
						error = "故宫门票一笔订单最多限购5张";
					}
				}
			}
		} catch (IndexOutOfBoundsException e) {
			error = "未选购产品";
			e.printStackTrace();
		}
		return error;
	}

	@Override
	public String buildOrder(TntOrder t) {
		Long orderId = t.getOrderId();
		String error = null;
		if (null == orderId) {
			error = "订单号为null,无法展示订单内容";
		} else {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			if (null == order) {
				error = "无法找到订单号为" + orderId + "的订单，展示订单内容失败!";
			} else {
				List<OrdOrderItemProd> mainOrdProd = order
						.getOrdOrderItemProds();
				List<OrdOrderItemProd> mainOrderList = new ArrayList<OrdOrderItemProd>();
				List<OrdOrderItemProd> relativeOrderList = new ArrayList<OrdOrderItemProd>();
				List<OrdOrderItemProd> additionalOrderList = new ArrayList<OrdOrderItemProd>();
				if (order.hasSelfPack()) {
					mainOrderList.add(order.getMainProduct());
				}
				if (order.IsAperiodic()) {
					t.setVisitTimeBegin(DateUtil.formatDate(
							order.getValidBeginTime(), "yyyy-MM-dd"));
					t.setVisitTimeEnd(DateUtil.formatDate(
							order.getValidEndTime(), "yyyy-MM-dd"));
				}
				for (int i = 0; i < mainOrdProd.size(); i++) {
					OrdOrderItemProd itemProd = mainOrdProd.get(i);
					if ("true".equals(itemProd.getIsDefault())) {
						// 主产品
						mainOrderList.add(itemProd);
					} else if (!itemProd.isAdditionalProduct()) {
						// 相关产品
						relativeOrderList.add(itemProd);
					} else {
						// 附加产品
						additionalOrderList.add(itemProd);
					}
				}
				t.setMainOrderList(reserve(mainOrderList));
				t.setRelativeOrderList(reserve(relativeOrderList));
				t.setAdditionalOrderList(reserve(additionalOrderList));
				t.setLastWaitPaymentTime(order.getLastWaitPaymentTimeOfString());

				// 取票人手机号
				List<OrdPerson> persons = order.getPersonList();
				if (persons != null && !persons.isEmpty()) {
					if (t.getTickerMobile() == null)
						t.setTickerMobile(persons.get(0).getMobile());
				}
			}
		}
		if (error != null) {
			LOG.error(error);
		}
		return error;
	}

	public void buildPriceOrder(TntOrder t) {
		if (t == null)
			return;
		boolean payToLvmama = t.isPayToLvmama();
		Date visitTime = t.getVisitTime();
		if (visitTime == null && t.getVisitTimeEnd() != null) {
			visitTime = DateUtil
					.getDateByStr(t.getVisitTimeEnd(), "yyyy-MM-dd");
		}
		if (t.getMainOrderList() != null) {
			bulidTProd(t.getMainOrderList(), t.getDistributorId(), payToLvmama,
					visitTime);
		}
		if (t.getAdditionalOrderList() != null) {
			bulidTProd(t.getAdditionalOrderList(), t.getDistributorId(),
					payToLvmama, visitTime);
		}
		if (t.getRelativeOrderList() != null) {
			bulidTProd(t.getRelativeOrderList(), t.getDistributorId(),
					payToLvmama, visitTime);
		}
	}

	private void bulidTProd(List<TntOrderItemProd> list, Long userId,
			boolean payToLvmama, Date visitTime) {
		for (TntOrderItemProd tProd : list) {
			Long branchId = tProd.getProdBranchId();
			TimePrice timePrice = prodProductService.calcProdTimePrice(
					branchId, visitTime);
			Long price = null;
			if (timePrice != null) {
				price = tntProdPolicyService.calculatePrice(branchId, userId,
						timePrice.getPrice(), timePrice.getSettlementPrice(),
						payToLvmama);
			}
			price = price == null ? tProd.getPrice() : price;
			tProd.setDistPrice(price);
		}
	}

	private TntOrderItemProd reserve(OrdOrderItemProd t) {
		TntOrderItemProd top = new TntOrderItemProd();
		top.setMarketPrice(t.getMarketPrice());
		top.setOrderId(t.getOrderId());
		top.setOrderItemProdId(t.getOrderItemProdId());
		top.setPrice(t.getPrice());
		top.setProdBranchId(t.getProdBranchId());
		top.setProductId(t.getProductId());
		top.setProductName(t.getProductName());
		top.setProductType(t.getProductType());
		top.setQuantity(t.getQuantity());
		top.setVisitTime(t.getVisitTime());
		return top;
	}

	protected List<TntOrderItemProd> reserve(List<OrdOrderItemProd> list) {
		List<TntOrderItemProd> items = null;
		if (list != null) {
			items = new ArrayList<TntOrderItemProd>();
			for (OrdOrderItemProd t : list) {
				items.add(reserve(t));
			}
		}
		return items;
	}

	protected TntOrder reserve(OrdOrder order) {
		TntOrder to = new TntOrder();
		to.setApproveStatus(order.getApproveStatus());
		OrdPerson contact = order.getContact();
		if (contact != null) {
			to.setContactMoblie(contact.getMobile());
			to.setContactName(contact.getName());
		}
		to.setOrderAmount(order.getOrderPay());
		to.setOrderId(order.getOrderId());
		to.setOrderStatus(order.getOrderStatus());
		to.setPaymentStatus(order.getPaymentStatus());
		to.setPaymentTime(order.getPaymentTime());
		to.setPerformStatus(order.getPerformStatus());
		OrdOrderItemProd prod = order.getMainProduct();
		if (prod != null) {
			to.setProductId(prod.getProductId());
			to.setProductName(prod.getProductName());
			to.setProductType(prod.getProductType());
			to.setQuantity(prod.getQuantity().toString());
		}
		to.setSettleStatus(order.getSettlementStatus());
		to.setResourceConfirmStatus(order.getResourceConfirmStatus());
		to.setResourceLackReason(order.getResourceLackReason());
		to.setVisitTime(order.getVisitTime());
		to.setPaymentTarget(order.getPaymentTarget());
		return to;
	}

	public Long calculateDistOrderAmount(OrdOrder order, Long userId) {
		Date visitDate = order.getVisitTime();
		if (visitDate == null)
			visitDate = order.getValidEndTime();
		if (visitDate == null)
			visitDate = order.getValidBeginTime();
		List<OrdOrderItemProd> items = order.getOrdOrderItemProds();
		Long oughtPay = order.getOughtPay();
		if (items != null && !items.isEmpty()) {
			boolean payToLvmama = TntConstant.PRODUCT_PAY_TYPE
					.isPayToLvmama(order.getPaymentTarget());
			Long amount = 0l;
			for (OrdOrderItemProd t : items) {
				Long branchId = t.getProdBranchId();
				TimePrice timePrice = prodProductService.calcProdTimePrice(
						branchId, visitDate);
				Long price = t.getPrice() != null ? t.getPrice() : 0;
				if (timePrice != null) {
					price = tntProdPolicyService.calculatePrice(branchId,
							userId, price, timePrice.getSettlementPrice(),
							payToLvmama);
				}
				Long quantity = t.getQuantity() != null ? t.getQuantity() : 0;
				amount += PriceUtil.convertToYuanInt(price) * quantity;
			}
			return PriceUtil.convertToFen(amount);
		}
		return oughtPay;
	}

	@Override
	public Long calculateDistOrderAmount(Long orderId, Long userId) {
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if (order != null) {
			return calculateDistOrderAmount(order, userId);
		}
		return null;
	}

}