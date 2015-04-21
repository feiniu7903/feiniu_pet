package com.lvmama.front.web.buy;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.pet.po.money.CashAccount;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderChannel;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.ord.OversoldException;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.service.prod.ProdProductRoyaltyService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Coupon;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.BuyInfo.OrdTimeInfo;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.favor.FavorService;
import com.lvmama.comm.pet.service.ord.OrdOrderChannelService;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.comm.pet.service.user.IReceiverUserService;
import com.lvmama.comm.pet.vo.favor.FavorResult;
import com.lvmama.comm.pet.vo.favor.strategy.OrderFavorStrategyForFifthAnniversary;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.utils.ord.ProductBlackValidateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.CouponInfo;
import com.lvmama.comm.vo.TimeInfo;
import com.lvmama.comm.vo.ViewBuyInfo;
import com.lvmama.front.web.BaseAction;

@Results( {
		@Result(name = "invalid.token", location = "/WEB-INF/pages/buy/wrong.ftl", type = "freemarker"),
		@Result(name=com.opensymphony.xwork2.Action.ERROR,params={"status", "404"},type="dispatcher"),
		@Result(name = "bookFail", location = "/WEB-INF/pages/buy/bookFail.ftl", type = "freemarker"),
		@Result(name = "overstock", location = "/WEB-INF/pages/buy/overstock.ftl", type = "freemarker") })
@InterceptorRefs( {
		@InterceptorRef(value = "token", params = { "excludeMethods",
				"ajaxPriceInfo,ajaxCheckSock,ajaxCheckVisitorIsExisted" }), @InterceptorRef("defaultStack") })
public class CreateOrderAction extends BaseAction {

	private static final long serialVersionUID = -4271372779875255489L;
	protected static final Log LOG = LogFactory.getLog(FrontCreateOrderAction.class);
	/**
	 * 是否需要邮寄:需要.
	 */
	private static final String NEED_PHYSICAL = "true";
	protected ProdProductService prodProductService;
	protected OrderService orderServiceProxy;
	protected FavorService favorService;
	protected ProdProductRoyaltyService prodProductRoyaltyService;
	protected OrdOrderChannelService ordOrderChannelService; 
	
	protected ViewBuyInfo buyInfo = new ViewBuyInfo();
	private PageService pageService;
	protected Long orderId;
	protected String orderIds = "";
	/** 取票人/联系人 */
	protected UsrReceivers contact = new UsrReceivers();
	/** 游客 */
	protected List<UsrReceivers> travellerList = new ArrayList<UsrReceivers>();
	/** 紧急联系人 **/
	protected UsrReceivers emergencyContact;
	
	/**
	 * 百度参数uid
	 */
	private String baiduid;
	private String tn;
	
	private String brithdayDate;
	
	public String getBrithdayDate() {
		return brithdayDate;
	}

	public void setBrithdayDate(String brithdayDate) {
		this.brithdayDate = brithdayDate;
	}
	private IReceiverUserService receiverUserService;
	/** 订票人 */
	protected UsrReceivers booker;
	protected OrdOrder order;
	private PlaceCityService placeCityService;
	//未登录用户时需要填写的用户地址信息.
	private UsrReceivers receiverAddress = new UsrReceivers();
	private String physical;
	public String getPhysical() {
		return physical;
	}

	public void setPhysical(String physical) {
		this.physical = physical;
	}
	protected String CHANNEL = Constant.CHANNEL.FRONTEND.name();
	protected OrdInvoice ordInvoice = new OrdInvoice();
	
	/*上车地点*/
	protected String prodAssemblyPoint;

	/**
	 * 发送生成合同消息接口
	 */
	private transient TopicMessageProducer orderMessageProducer;

    private CashAccountService cashAccountService;
    protected TopicMessageProducer resourceMessageProducer;
    private UserUserProxy userUserProxy;

	public BuyInfo getOrderInfo() throws Exception {
		BuyInfo createOrderBuyInfo = new BuyInfo();
		createOrderBuyInfo.setMainProductType(buyInfo.getProductType());
		createOrderBuyInfo.setMainSubProductType(buyInfo.getSubProductType());
		createOrderBuyInfo.setItemList(this.getItem());
		createOrderBuyInfo.setWaitPayment(Constant.WAIT_PAYMENT.PW_DEFAULT.getValue());
		createOrderBuyInfo.setCouponList(this.getCoupon());
		createOrderBuyInfo.setSelfPack(buyInfo.getSelfPack());
		createOrderBuyInfo.setIsAperiodic(buyInfo.getIsAperiodic());
		createOrderBuyInfo.setCashValue(buyInfo.getCashValue());
		createOrderBuyInfo.setPersonList(this.getPerson());
		if(buyInfo.IsAperiodic()) {
			createOrderBuyInfo.setValidBeginTime(buyInfo.getValidBeginTime());
			createOrderBuyInfo.setValidEndTime(buyInfo.getValidEndTime());
		}
		LOG.debug("new order id:" + this.orderId);
		return createOrderBuyInfo;
	}

	public String createOrder() throws Exception {
		buyInfo.setUserId(this.getBookerUserId());
		
		BuyInfo createOrderBuyInfo = new BuyInfo();
		createOrderBuyInfo.setMainProductType(buyInfo.getProductType());
		createOrderBuyInfo.setMainSubProductType(buyInfo.getSubProductType());
		createOrderBuyInfo.setUserId(this.getBookerUserId());
		createOrderBuyInfo.setChannel(buyInfo.getChannel());
		//不定期
		List<Item> list = this.getItem();	
		createOrderBuyInfo.setIsAperiodic(buyInfo.getIsAperiodic());
		if(buyInfo.IsAperiodic()) {
			createOrderBuyInfo.setValidBeginTime(buyInfo.getValidBeginTime());
			createOrderBuyInfo.setValidEndTime(buyInfo.getValidEndTime());
		}
		createOrderBuyInfo.setSelfPack(buyInfo.getSelfPack());
		createOrderBuyInfo.setItemList(list);
		createOrderBuyInfo.setPersonList(this.getPerson());
		if(StringUtils.equals(buyInfo.getUserMemo(),"您对订单的特殊要求")){
			createOrderBuyInfo.setUserMemo("");
		}else{
			createOrderBuyInfo.setUserMemo(buyInfo.getUserMemo());
		}
		createOrderBuyInfo.setPaymentTarget(buyInfo.getPaymentTarget());
		
		/**
		 *  秒杀新增
		 *  2014-04-16
		 *  @author zenglei
		 */
		if(buyInfo.getSeckillId() != null){
			createOrderBuyInfo.setSeckillId(buyInfo.getSeckillId());
		}
		
		if(buyInfo.getWaitPayment() != null){
			createOrderBuyInfo.setWaitPayment(buyInfo.getWaitPayment());
		}else{
			createOrderBuyInfo.setWaitPayment(Constant.WAIT_PAYMENT.PW_DEFAULT.getValue());
		}
		createOrderBuyInfo.setNeedInvoice(buyInfo.getNeedInvoice());
		
		//只有后台允许这个产品使用优惠券才能使用，防止客户端提交错误数据
		ProdProduct prodProduct = prodProductService.getProdProduct(buyInfo.getProductId());
		createOrderBuyInfo.setCouponList(favorService.filterCouponListByProductCouponUseFlag(this.getCoupon(), prodProduct));
//		//故宫产品限制支付网关为支付宝 add by taiqichao 2013/06/07
//		if(prodProductRoyaltyService.getRoyaltyProductIds().contains(buyInfo.getProductId())){
//			buyInfo.setPaymentChannel(Constant.PAYMENT_GATEWAY.ALIPAY.name());
//		}
		createOrderBuyInfo.setPaymentChannel(buyInfo.getPaymentChannel());		
		if(prodAssemblyPoint != null) {		
			createOrderBuyInfo.setUserMemo(buyInfo.getUserMemo()+"。上车地点："+prodAssemblyPoint);
		}
		
		createOrderBuyInfo.setFavorResult(favorService.calculateFavorResultByBuyInfo(createOrderBuyInfo));
		if(ActivityUtil.getInstance().checkActivityIsValid(Constant.ACTIVITY_FIVE_YEAR)){
			createOrderBuyInfo.setCouponList(new ArrayList<Coupon>()); //5周年不使用优惠券
			FavorResult fr = new FavorResult();
			OrderFavorStrategyForFifthAnniversary ofsf = new OrderFavorStrategyForFifthAnniversary(new MarkCoupon(),new MarkCouponCode());
			fr.addOrderFavorStrategy(ofsf);
			createOrderBuyInfo.setFavorResult(fr);
		}
		ResultHandle result=orderServiceProxy.checkOrderStock(createOrderBuyInfo);
		if(result.isFail()){
			return "overstock";
		}
		if(buyInfo.hasSelfPack()){			
			//检查行程是否选中了所有的必选产品.
			if(!prodProductService.checkJourneyRequird(buyInfo)){
				//如果没有选中定义的产品就跳出
				LOG.info("行程存在没有选择正确的产品");
				return "error";
			}
		}
		
		//超卖异常捕获 modify by taiqichao 20140423
		try {
			order = orderServiceProxy.createOrder(createOrderBuyInfo);
		} catch (OversoldException  e) {
			LOG.info("成功防止了超卖");
			return "overstock";
		}
		
		order=orderServiceProxy.queryOrdOrderByOrderId(order.getOrderId());
		
		this.orderId = order.getOrderId();
		LOG.info("新订单orderId："+orderId+" 下单userId:"+this.getBookerUserId());
		LOG.debug("new order id:"+this.orderId);
		
		// 保存取票人信息，订票人信息
		this.saveReceiver();
		this.saveOrderPersonInfo();
		/**
		 * 订单行程固化
		 */

		String userName = getUser().getUserName();
//		String travelXml = travelDescriptionService.getTravelDesc(buyInfo.getProductId());
//		if(Constant.PRODUCT_TYPE.ROUTE.name().equals(order.getMainProduct().getProductType()) && !StringUtil.isEmptyString(travelXml)){
//			ComFile comFile =new ComFile();
//			comFile.setFileData(travelXml.getBytes());
//			comFile.setFileName(order.getOrderId()+"_travel.xml");
//			Long fileId = fsClient.uploadFile(comFile.getFile(), "eContract");
//			travelDescriptionService.initOrderTravel(fileId,orderId,userName);
//		}
//		/**
//		 * 生成电子合同
//		 * 20120727 szy
//		 * 在线预售权修改 在生成订单后就生成合同
//		 */
//		if(order.isNeedEContract()){
//			boolean isCreated = contractClient.createEContract(order, userName);
//			if(!isCreated){
//				LOG.info("订单"+order.getOrderId()+"生成合同失败");
//			}
//		}
		
		this.saveOrderChannel(orderId);
		
		//校验是否可以使用奖金支付
		UserUser uu = userUserProxy.getUserUserByUserNo(this.getBookerUserId());
		boolean cashUseBonusPay=cashAccountService.canUseBonusPay(uu.getId());
		if(cashUseBonusPay){
			this.useCash(orderId);
		}else{
			LOG.info("不可以使用奖金账户支付订单,order id:"+orderId+",user id:"+uu.getId()+",nick name:"+uu.getNickName());
		}
		
        //黑名单	
		try {
			ProductBlackValidateUtil.getProductBlackValidateUtil().insertBlackOrder(buyInfo.getProductId(), this.getContact().getMobileNumber(), 
					orderId, this.getUser().getId(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return "view";
		}
	}
    private void useCash(Long orderId) {
        if (buyInfo.getCashValue() != null && buyInfo.getCashValue() > 0) {
            Long bonus = PriceUtil.convertToFen(buyInfo.getCashValue());

            UserUser uu = userUserProxy.getUserUserByUserNo(this.getBookerUserId());
            CashAccount cashAccount = cashAccountService.queryCashAccountByUserId(uu.getId());
            if (cashAccount != null && (cashAccount.getBonusBalance() + cashAccount.getNewBonusBalance()) >= bonus) {

                Long oldPayAmount = 0L;
                Long newPayAmount = 0L;

                if (bonus <= cashAccount.getNewBonusBalance()) {
                    newPayAmount = bonus;
                } else {
                    newPayAmount = cashAccount.getNewBonusBalance();
                    oldPayAmount = bonus - newPayAmount;
                }

                // 从现金账户支付
                List<Long> paymentIds = cashAccountService.payFromBonus(uu.getId(), orderId, Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.getCode(), oldPayAmount,newPayAmount);
                // 发送支付成功消息
                for(Long paymentId:paymentIds){
                    resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(paymentId));
                }
            }
        }
    }
	private void saveOrderChannel(Long orderId) {
		if (StringUtils.isNotBlank(tn) && StringUtils.isNotBlank(baiduid)){
			OrdOrderChannel orderChannel = new OrdOrderChannel(orderId, Constant.BAIDU_TUANGOU_CHANNEL);
			orderChannel.setArg1(tn);
			orderChannel.setArg2(baiduid);
			ordOrderChannelService.insert(orderChannel);
		}
	}

	/**
	 * 保存订单邮寄地址信息.
	 */
	private void saveOrderPersonInfo() {
		//此产品需要邮寄地址.
		if (NEED_PHYSICAL.equals(this.physical)) {
			HttpServletRequest req = super.getRequest();
			String receiverId = req.getParameter("receiverId");
			//已登录用户可以选择的邮寄地址信息.
			if ((receiverId != null) && !receiverId.trim().equals("")) {
				receiverAddress = this.receiverUserService.getRecieverByPk(receiverId);
			} else {
			//未登录用户所填写的邮寄地址信息.
				UUIDGenerator gen = new UUIDGenerator();
				receiverId = gen.generate().toString();
//				String address = req.getParameter("usrReceivers.address");
//				String city = req.getParameter("usrReceivers.city");
//				String mobileNumber = req.getParameter("usrReceivers.mobileNumber");
//				String postCode = req.getParameter("usrReceivers.postCode");
//				String province = req.getParameter("usrReceivers.province");
//				String receiverName = req.getParameter("usrReceivers.receiverName");
//				usrReceivers.setAddress(address);
//				usrReceivers.setCity(city);
//				usrReceivers.setMobileNumber(mobileNumber);
//				usrReceivers.setPostCode(postCode);
//				usrReceivers.setProvince(province);
//				usrReceivers.setReceiverName(receiverName);
				this.receiverAddress.setReceiverId(receiverId);
				this.receiverAddress.setUserId(this.getUserId());
				this.receiverAddress.setIsValid("Y");
				this.receiverAddress.setCreatedDate(new Date());
				this.receiverAddress.setReceiversType(Constant.RECEIVERS_TYPE.ADDRESS.name());
				this.initUsrReceiversSaveAddress();
				this.receiverUserService.insert(this.receiverAddress);
				this.receiverAddress = this.receiverUserService.getRecieverByPk(receiverId);
				
			}
			Person person = usrReceiver2OrdPerson(receiverAddress);
			this.orderServiceProxy.addPerson2OrdOrder(person, orderId, super.getUserId());
		}
	}

	/**
	 * 初始化usrReceivers对象的省份(province)与城市(city)属性值,将code码转换为name.
	 */
	private void initUsrReceiversSaveAddress() {
		String province=receiverAddress.getProvince();
		String city=receiverAddress.getCity();
		if(StringUtils.isNotEmpty(province)){//用编号转名字
			ComProvince cp=placeCityService.selectByPrimaryKey(province);
			if(cp!=null){
				receiverAddress.setProvince(cp.getProvinceName());
				ComCity cc=placeCityService.selectCityByPrimaryKey(city);
				if(cc!=null){
					receiverAddress.setCity(cc.getCityName());
				}
			}
		}
	}
	
	/**
	 * 将UsrReceivers对象中的信息转换到Person对象中.
	 * @param usrReceiver
	 * @return
	 */
	private Person usrReceiver2OrdPerson(UsrReceivers usrReceiver) {
		Person result = new Person(); 
		result.setReceiverId(usrReceiver.getReceiverId());
		result.setPersonType(Constant.ORD_PERSON_TYPE.ADDRESS.name());
		result.setAddress(usrReceiver.getProvince() + " " + usrReceiver.getCity() + " " + usrReceiver.getAddress());
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
	 * 关联下单人
	 */
	public void relatedOrderUser() {
		if (this.isLogin()) {

		}
	}

	private List<Coupon> getCoupon() {
		// 构造优惠方式数据
		List<CouponInfo> infoList = this.buyInfo.getCouponList();
		List<Coupon> couponList = new ArrayList<Coupon>();
		if (infoList != null) {
			for (CouponInfo couponInfo : infoList) {
				Coupon coupon = new Coupon();
					
				if (couponInfo!=null && "true".equals(couponInfo.getChecked())){
					coupon.setChecked(couponInfo.getChecked());
					coupon.setCode(couponInfo.getCode());
					coupon.setCouponId(couponInfo.getCouponId());
					couponList.add(coupon);
					break;
				}
				

			}
		}

		return couponList;
	}

	/**
	 * 将取票人注册为用户
	 */
	public void contactRegister() {
		String mobile = this.getContact().getMobileNumber();
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(mobile)) {
			// 构造HttpClient的实例
			HttpClient httpClient = new HttpClient();
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
			// 创建GET方法的实例
			GetMethod getMethod = new GetMethod(
					"http://login.lvmama.com/nsso/ajax/silentRegisterByMobile.do?mobile="
							+ mobile);
			try {
				// 执行getMethod
				int statusCode = httpClient.executeMethod(getMethod);
				if (statusCode != HttpStatus.SC_OK) {

				}
			} catch (Exception e) {
				StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
			} finally {
				// 释放连接
				getMethod.releaseConnection();
				httpClient = null;
			}
		}
	}

	/**
	 * 保存receivers
	 * 
	 * @param orderId
	 */
	public void saveReceiver() {
		if (org.apache.commons.lang3.StringUtils.isEmpty(this.getUserId())) {
			return;
		}
		List<UsrReceivers> receivers = new ArrayList<UsrReceivers>();
		List<UsrReceivers> addRe = this.getUsrReceivers();			
		addRe.add(this.getContact());
		if (addRe.size() > 0) {
			//用户常用联系人列表	
			List<UsrReceivers> receiversList = this.receiverUserService.loadUserReceiversByUserId(getUserId()); 
			for (int i = 0; i < addRe.size(); i++) {
				boolean isExist = false;
				UsrReceivers u = addRe.get(i);
				if (u != null && org.apache.commons.lang3.StringUtils.isNotEmpty(u.getReceiverName()) && "true".equals(u.getUseOffen())) {	
					//判断游玩人名字是否已存在用户常用联系人列表中
					for(UsrReceivers receiver:receiversList){
						if(receiver.getReceiverName()==null){
							break;
						}
						if(receiver.getReceiverName().trim().equals(addRe.get(i).getReceiverName().trim())){
							isExist=true;
							break;
						}
					}
					if(!isExist) receivers.add(addRe.get(i));
				}
			}
			this.receiverUserService.createContact(receivers, this.getUserId());
		}
	}

	/**
	 * 获取游客填写的联系信息
	 * 
	 * @return
	 */
	public List<Person> getPerson() {
		List<Person> personList = new ArrayList<Person>();

		// 联系人
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(this.getContact()
				.getMobileNumber())) {
			personList.add(this.setPerson(this.getContact(),
					Constant.ORD_PERSON_TYPE.CONTACT.name()));
			//联系人不再作为游玩人保存
			//this.travellerList.add(this.contact);
		}
		// 游客信息
		List<UsrReceivers> travellerList=getUsrReceivers();
		for (int i = 0; i < travellerList.size(); i++) {
			UsrReceivers usrReceivers = travellerList.get(i);
			if (usrReceivers != null) {
				Person person = this.setPerson(usrReceivers,Constant.ORD_PERSON_TYPE.TRAVELLER.name());
				
				//如果证件类型为非客服联系我时提供、非身份证类型时,需要设置页面传过来的日期;如果是身份证类型的日期则直接从身份证号码中解析得到.
				//当需要增加新的证件类型时,如果新增的证件类型不需要输入出生年月,则需要在此条件中增加新增类型的判断项.
//				if (!Constant.CERTIFICATE_TYPE.CUSTOMER_SERVICE_ADVICE.name().equals(person.getCertType()) && !Constant.CERTIFICATE_TYPE.ID_CARD.name().equals(person.getCertType()) && it.hasNext()) {
//					person.setBrithday(it.next());
//					//前台页面中的证件类型如果为"其它",则说明是儿童,需要将证件号码设置为出生年月.
//					if (Constant.CERTIFICATE_TYPE.OTHER.name().equals(person.getCertType())) {
//						person.setCertNo(DateUtil.getFormatDate(person.getBrithday(), "yyyyMMdd"));
//					}
//				}
				
			
				personList.add(person);	
			}
		}
		
		//紧急联系人
		if (!StringUtils.isEmpty(getRequest().getParameter("emergencyContact.mobileNumber")) && !StringUtils.isEmpty(getRequest().getParameter("emergencyContact.receiverName"))) {
			emergencyContact = new UsrReceivers();
			emergencyContact.setReceiverName(getRequest().getParameter("emergencyContact.receiverName"));
			emergencyContact.setMobileNumber(getRequest().getParameter("emergencyContact.mobileNumber"));
			personList.add(setPerson(emergencyContact, Constant.ORD_PERSON_TYPE.EMERGENCY_CONTACT.name()));
		} 
		
		
		// 预订人信息
		if (isLogin()) {
			UserUser users = this.getUser();
			this.booker = new UsrReceivers();
			this.booker.setUserId(users.getUserId());
			this.booker.setReceiverName(users.getRealName());
			this.booker.setEmail(users.getEmail());
			if (org.apache.commons.lang3.StringUtils.isNotEmpty(users
					.getMobileNumber())) {
				this.booker.setMobileNumber(users.getMobileNumber());
			} else {
				this.booker.setMobileNumber(this.getContact().getMobileNumber());
			}
			personList.add(this.setPerson(this.booker,
					Constant.ORD_PERSON_TYPE.BOOKER.name()));
		} else {
			if (org.apache.commons.lang3.StringUtils.isNotEmpty(this.getContact()
					.getMobileNumber())) {
				personList.add(this.setPerson(this.getContact(),
						Constant.ORD_PERSON_TYPE.BOOKER.name()));
			}
		}
		return personList;
	}
	/**
	 * 将页面中的表单项"brithdayDate"获取,并将转换为日期类型的结果放置到一个List中.
	 * @return 返回日期列表.
	 */
	private List<Date> getBrithdays() {
		Object arg = this.getParameters().get("brithdayDate");
		if (arg == null || "".equals(arg)) {
			return new ArrayList<Date>(0);
		}
		String[] brithdayDateArray = new String[0];
		if (arg instanceof String[]) {
			brithdayDateArray = (String[])arg;
		}
		List<Date> result = new ArrayList<Date>(brithdayDateArray.length);
		for (String bd : brithdayDateArray) {
			if (!"".equals(bd)){
				Date brithday = DateUtil.toDate(bd,"yyyy-MM-dd");
				result.add(brithday);
			}
		}
		return result;
	}
	
	/**
	 * 构造person
	 * 
	 * @param usrReceivers
	 * @param personType
	 * @return
	 */
	public Person setPerson(UsrReceivers usrReceivers, String personType) {
		Person person = new Person();

		person.setName(usrReceivers.getReceiverName());
		if(ActivityUtil.getInstance().checkActivityIsValid(Constant.ACTIVITY_FIVE_YEAR)){
			String requestIp = InternetProtocol.getRemoteAddr(getRequest());
            if(ActivityUtil.isCompanyInnerIp(requestIp)) {
            	if (null != person.getName() && person.getName().startsWith("amp;")) {
            		person.setName(person.getName().substring(4));
            	} else {
            		person.setName("测试");
            	}
            }
        }
		person.setMobile(usrReceivers.getMobileNumber());
		person.setPersonType(personType);
		person.setReceiverId(usrReceivers.getReceiverId());
		person.setCertNo(usrReceivers.getCardNum());
		person.setCertType(usrReceivers.getCardType());
		person.setEmail(usrReceivers.getEmail());
		person.setGender(usrReceivers.getGender());
		person.setPinyin(usrReceivers.getPinyin());
		person.setBrithday(usrReceivers.getBrithday());
		return person;
	}
	
	private Item getHotelSigelRoomItem(){
		Item item = new Item();
		List<OrdTimeInfo> ordTimeInfoList = new ArrayList<OrdTimeInfo>();
		for (TimeInfo timeInfo : buyInfo.getTimeInfo()) {			
			OrdTimeInfo ordTimeInfo = new OrdTimeInfo();
			ProdProductBranch branch=pageService.getProdBranchByProdBranchId(timeInfo.getProductId());
			
			ordTimeInfo.setProductBranchId(branch.getProdBranchId());
			ordTimeInfo.setProductId(branch.getProductId());
			item.setProductBranchId(branch.getProdBranchId());
			item.setProductId(branch.getProductId());
			ordTimeInfo.setQuantity(timeInfo.getQuantity());
			try{
			ordTimeInfo.setVisitTime(timeInfo.getVisitDate());
			}catch(Exception ex){
				StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), ex);
			}
			ordTimeInfoList.add(ordTimeInfo);
		}
		item.setTimeInfoList(ordTimeInfoList);
		item.setQuantity(getHotelRoomQuantity(buyInfo.getTimeInfo()));
		item.setVisitTime(getSigelRoomFistVisitDate(buyInfo.getTimeInfo()));
		return item;
	}
	
	/**
	 * 排序获得酒店最近入住日期
	 * @param timeInfoList
	 * @return
	 */
	private Date getSigelRoomFistVisitDate(List<TimeInfo> timeInfoList){
		List<Date> dateList = new ArrayList<Date>();
		for (TimeInfo timeInfo : timeInfoList) {
			try {
				dateList.add(timeInfo.getVisitDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		java.util.Collections.sort(dateList);
		return dateList.get(0);
		
	}
	
	/**
	 * 获得酒店房型所有房间数量
	 * @param timeInfoList
	 * @return
	 */
	private int getHotelRoomQuantity(List<TimeInfo> timeInfoList){
		int quantity =0;
		for (TimeInfo timeInfo : timeInfoList) {
			quantity+=timeInfo.getQuantity();
		}
		return quantity;
	}

	protected List<Item> getItem() {
		List<Item> itemList = new ArrayList<Item>();
		if(!buyInfo.hasSelfPack()){
			if(buyInfo.getProductId() != null) {
				ProdProduct product = pageService.getProdProductByProductId(buyInfo.getProductId());
				if(product != null) {
					buyInfo.setIsAperiodic(product.getIsAperiodic());
				}
			} else if(buyInfo.getProdBranchId() != null) {
				ProdProductBranch branch = pageService.getProdBranchByProdBranchId(buyInfo.getProdBranchId());
				if(branch != null) {
					buyInfo.setIsAperiodic(branch.getProdProduct().getIsAperiodic());
				}
			}
			Date beginTime = null, endTime = null;
			Map<Long, Long> ordOrderItemProds = buyInfo.getOrdItemProdList();
			for (Iterator<Long> iterator = ordOrderItemProds.keySet().iterator(); iterator
					.hasNext();) {
				Long prodBranchId = (Long) iterator.next();
				Long quantity = ordOrderItemProds.get(prodBranchId);
				if(quantity != null && quantity > 0){
					Item item = new Item();
					ProdProductBranch branch=pageService.getProdBranchByProdBranchId(prodBranchId);
					item.setProductId(branch.getProductId());
					item.setProductBranchId(branch.getProdBranchId());
					item.setQuantity(Integer.parseInt(ordOrderItemProds.get(prodBranchId)
							+ ""));
					item.setFaxMemo(null);
					item.setProductType(branch.getProdProduct().getProductType());
					item.setSubProductType(branch.getProdProduct().getSubProductType());
					//不定期取有效期最后日期为游玩日期
					if(branch.getProdProduct().IsAperiodic()) {
						Date validBeginTime = branch.getValidBeginTime();
						Date validEndTime = branch.getValidEndTime();
						if(validBeginTime != null && validEndTime != null && !DateUtil.getDayStart(new Date()).after(validEndTime)) {
							item.setVisitTime(validEndTime);
							item.setValidBeginTime(validBeginTime);
							item.setValidEndTime(validEndTime);
							item.setInvalidDate(branch.getInvalidDate());
							item.setInvalidDateMemo(branch.getInvalidDateMemo());
							itemList.add(item);
							
							//取多类别最大区间有效期
							if(beginTime == null) {
								beginTime = validBeginTime;
							} else {
								beginTime = beginTime.before(validBeginTime) ? beginTime : validBeginTime;
							}
							if(endTime == null) {
								endTime = validEndTime;
							} else {
								endTime = endTime.after(validEndTime) ? endTime : validEndTime;
							}
						}
					} else {
						item.setVisitTime(buyInfo.getVisitDate());
						itemList.add(item);
					}
				}		
			}
			/**
			 * 设置房型日期信息(非不定期)
			 */
			if(Constant.PRODUCT_TYPE.HOTEL.name().equals(buyInfo.getProductType()) && Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(buyInfo.getSubProductType()) && !buyInfo.IsAperiodic())	{
				itemList.add(this.getHotelSigelRoomItem());
			}
			
			/**
			 * 设置订单的主产品.
			 */
			Item item=(Item)CollectionUtils.find(itemList, new Predicate() {
				
				public boolean evaluate(Object arg0) {
					Item item = (Item)arg0;
					return buyInfo.getProdBranchId().equals(item.getProductBranchId());
				}
			});
			if(!itemList.isEmpty()) {
				if(item==null){		
					if(itemList.size()==1){
						item=itemList.get(0);
					}else{
						for(BuyInfo.Item it:itemList){
							if(!Constant.PRODUCT_TYPE.OTHER.name().equals(it.getProductType())){
								item=it;
								break;
							}
						}
						if(item==null){
							item=itemList.get(0);
						}
					}
				}
				item.setIsDefault("true");
			}
			if(beginTime != null && endTime != null) {
				buyInfo.setValidBeginTime(beginTime);
				buyInfo.setValidEndTime(endTime);
			}
		}else{
			Map<Long,TimeInfo> map=buyInfo.getOrdItemProdMap();
			for(Long key:map.keySet()){
				TimeInfo ti=map.get(key);
				Item item=new Item();
				item.setProductBranchId(ti.getProdBranchId());
				item.setJourneyProductId(ti.getJourneyProductId());
				ProdProductBranch branch=pageService.getProdBranchByProdBranchId(item.getProductBranchId());
				item.setProductId(branch.getProductId());
				try{
					item.setVisitTime(ti.getVisitDate());
				}catch(ParseException ex){
					
				}
				if(ti.hasHotel()){
					int quantity=0;
					List<OrdTimeInfo> ordTimeInfoList = new ArrayList<OrdTimeInfo>();
					try{
						int days=(int)ti.getDays();
						for(int i=0;i<days;i++){						
							Date vt=DateUtils.addDays(ti.getVisitDate(), i);
							OrdTimeInfo oti=new OrdTimeInfo();
							oti.setQuantity(ti.getQuantity());
							oti.setVisitTime(vt);
							oti.setProductBranchId(item.getProductBranchId());
							oti.setProductId(item.getProductId());
							ordTimeInfoList.add(oti);
							quantity+=oti.getQuantity();							
						}
					}catch(ParseException ex){
						
					}
					item.setTimeInfoList(ordTimeInfoList);
					item.setQuantity(quantity);
					
				}else{
					item.setQuantity(ti.getQuantity().intValue());
				}
				item.setFaxMemo(null);
				itemList.add(item);
			}	
			if(buyInfo.hasSelfPack()){
				//添加当前下单的主对象
				Item item=new Item();
				item.setQuantity(buyInfo.getTotalQuantity());
				item.setFaxMemo(null);
				item.setIsDefault("true");
				item.setProductBranchId(buyInfo.getProdBranchId());
				ProdProductBranch branch=pageService.getProdBranchByProdBranchId(item.getProductBranchId());
				item.setProductId(branch.getProductId());
				item.setVisitTime(buyInfo.getVisitDate());	
				itemList.add(item);
			}
		}
		
		return itemList;
	}

	/**
	 * @param pageService the pageService to set
	 */
	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public ViewBuyInfo getBuyInfo() {
		return buyInfo;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
 
	public List<UsrReceivers> getTravellerList() {
		return travellerList;
	}

	public OrdOrder getOrder() {
		return order;
	}

	public OrdInvoice getOrdInvoice() {
		return ordInvoice;
	}

	public String getProdAssemblyPoint() {
		return prodAssemblyPoint;
	}

	public void setProdAssemblyPoint(String prodAssemblyPoint) {
		this.prodAssemblyPoint = prodAssemblyPoint;
	}

	public void setEmergencyContact(UsrReceivers emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	public TopicMessageProducer getOrderMessageProducer() {
		return orderMessageProducer;
	}

	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	public UsrReceivers getBooker() {
		return booker;
	}

	public void setBooker(UsrReceivers booker) {
		this.booker = booker;
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public PageService getPageService() {
		return pageService;
	}

	public UsrReceivers getEmergencyContact() {
		return emergencyContact;
	}
	public void setBuyInfo(ViewBuyInfo buyInfo) {
		this.buyInfo = buyInfo;
	}

	public void setContact(UsrReceivers contact) {
		this.contact = contact;
	}
	
	public UsrReceivers getContact(){
		List<UsrReceivers> receiversList = this.receiverUserService.loadUserReceiversByUserId(getUserId());
		return replaceHiddenMobile(this.contact,receiversList);
	}
	
	/**
	 * 替换***
	 * @param usrReceivers 目标
	 * @param receiversList 数据源
	 * @return
	 */
	private UsrReceivers replaceHiddenMobile(UsrReceivers usrReceivers,List<UsrReceivers> receiversList){
		if(null==usrReceivers){
			return null;
		}
		if(StringUtils.isNotEmpty(usrReceivers.getMobileNumber())){
			Pattern pattern = Pattern.compile("(1[0-9]{2,2}[\\*]{4,4}[0-9]{4,4})");
			if(StringUtils.isNotBlank(usrReceivers.getReceiverId())){//选择的常用联系人
				Matcher matcher = pattern.matcher(usrReceivers.getMobileNumber());
				if (matcher.matches()) {//134****8362,替换掉****
					for(UsrReceivers receiver:receiversList){
						if(receiver.getReceiverId().equals(usrReceivers.getReceiverId())){
							usrReceivers.setMobileNumber(receiver.getMobileNumber());
							break;
						}
					}
				}
			}
		}
		return usrReceivers;
	}
	
	public List<UsrReceivers> getUsrReceivers(){
		List<UsrReceivers> receiversList = this.receiverUserService.loadUserReceiversByUserId(getUserId());
		for (UsrReceivers usrReceivers : this.travellerList) {
			replaceHiddenMobile(usrReceivers,receiversList);
		}
		return travellerList;
	}
	

	public void setTravellerList(List<UsrReceivers> travellerList) {
		this.travellerList = travellerList;
	}

	public void setOrder(OrdOrder order) {
		this.order = order;
	}
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setReceiverUserService(IReceiverUserService receiverUserService) {
		this.receiverUserService = receiverUserService;
	}
	public void setPlaceCityService(PlaceCityService placeCityService) {
		this.placeCityService = placeCityService;
	}

	public void setFavorService(FavorService favorService) {
		this.favorService = favorService;
	}
 
	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	public void setProdProductRoyaltyService(
			ProdProductRoyaltyService prodProductRoyaltyService) {
		this.prodProductRoyaltyService = prodProductRoyaltyService;
	}
	

	public void setOrdOrderChannelService(
			OrdOrderChannelService ordOrderChannelService) {
		this.ordOrderChannelService = ordOrderChannelService;
	}

	public String getBaiduid() {
		return baiduid;
	}

	public void setBaiduid(String baiduid) {
		this.baiduid = baiduid;
	}

	public String getTn() {
		return tn;
	}

	public void setTn(String tn) {
		this.tn = tn;
	}

	public UsrReceivers getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(UsrReceivers receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

    public CashAccountService getCashAccountService() {
        return cashAccountService;
    }

    public void setCashAccountService(CashAccountService cashAccountService) {
        this.cashAccountService = cashAccountService;
    }

    public TopicMessageProducer getResourceMessageProducer() {
        return resourceMessageProducer;
    }

    public void setResourceMessageProducer(TopicMessageProducer resourceMessageProducer) {
        this.resourceMessageProducer = resourceMessageProducer;
    }

    public UserUserProxy getUserUserProxy() {
        return userUserProxy;
    }

    public void setUserUserProxy(UserUserProxy userUserProxy) {
        this.userUserProxy = userUserProxy;
    }
}
