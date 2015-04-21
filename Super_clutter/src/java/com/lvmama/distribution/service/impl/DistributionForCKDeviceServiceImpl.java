package com.lvmama.distribution.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ckdevice.CkDeviceInfo;
import com.lvmama.comm.bee.po.ckdevice.CkDeviceProduct;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdOrderMemo;
import com.lvmama.comm.bee.po.ord.OrderParent;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.ckdevice.CkDeviceProductService;
import com.lvmama.comm.bee.service.ckdevice.CkDeviceService;
import com.lvmama.comm.bee.service.ord.OrderParentService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.BuyInfo.OrdTimeInfo;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.client.UserClient;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ConstantMsg;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.distribution.model.ckdevice.Branch;
import com.lvmama.distribution.model.ckdevice.FirstCustomer;
import com.lvmama.distribution.model.ckdevice.Order;
import com.lvmama.distribution.model.ckdevice.OrderInfo;
import com.lvmama.distribution.model.ckdevice.Person;
import com.lvmama.distribution.model.ckdevice.Product;
import com.lvmama.distribution.model.ckdevice.Response;
import com.lvmama.distribution.model.ckdevice.vo.CKConfirmPaymentBody;
import com.lvmama.distribution.model.ckdevice.vo.CKConfirmPrintBody;
import com.lvmama.distribution.model.ckdevice.vo.CKCreateOrderBody;
import com.lvmama.distribution.model.ckdevice.vo.CKPayMentCheckSumBody;
import com.lvmama.distribution.model.ckdevice.vo.CKPrintInfoBody;
import com.lvmama.distribution.model.ckdevice.vo.CKProductInfoBody;
import com.lvmama.distribution.model.ckdevice.vo.CKQueryOrderBody;
import com.lvmama.distribution.model.ckdevice.vo.CKRequest;
import com.lvmama.distribution.model.ckdevice.vo.CheckReservationBody;
import com.lvmama.distribution.service.BaseDistributionService;
import com.lvmama.distribution.service.DistributionCommonService;
import com.lvmama.distribution.service.DistributionForCKDeviceService;
import com.lvmama.distribution.util.DistributionUtil;

import freemarker.template.TemplateException;

/**
 * CK设备Service
 * @author gaoxin
 */
public class DistributionForCKDeviceServiceImpl extends BaseDistributionService implements DistributionForCKDeviceService{
	private static final Log log = LogFactory.getLog(DistributionForCKDeviceServiceImpl.class);
	private static final String BASE_TEMPLATE_DIR = "/com/lvmama/distribution/template/ckdevice/";
	private static final String CHECK_RESERVATION_FILE = "checkReservation.xml";
	private static final String PRINTINFO_FILE = "getPrintInfo.xml";
	private static final String CONFIRM_PRINT_FILE = "confirmPrint.xml";
	private static final String QUERY_PRODUCT_FILE = "queryProductList.xml";
	private static final String CREATE_ORDER_FILE = "createOrder.xml";
	private static final String QUERY_ORDER_FILE = "queryOrder.xml";
	private static final String CONFIRM_PAYMENT_FILE = "confirmPayment.xml";
	private static final String REQUEST_PAYMENT_CHECKSUM_FILE = "requestPaymentChecksum.xml";
	private static final String  VISIT_CUSTOMER_NAME="立式设备";
	private static final String ORDER = "ORD_ORDER";
	private static final String ORDER_ITEM = "ORD_ORDER_ITEM_META";
	private ComLogService comLogService;
	private CkDeviceService deviceService;
	private CkDeviceProductService deviceProductService;
	private ComPictureService comPictureService;
	private ProdProductService prodProductService;
	private UserClient userClient;
	private OrderParentService orderParentService;
	private CashAccountService cashAccountService;
	
	@Override
	public String checkReservation(String requestXml) throws Exception {
		log.info("checkReservation requestXml: "+requestXml);
		Map<String, Object> model = new HashMap<String, Object>();
		CheckReservationBody  body = new CheckReservationBody();
		CKRequest request = new CKRequest(body);
		String resultCode = request.init(requestXml);
		if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			return buildResult(resultCode, model, CHECK_RESERVATION_FILE,"checkReservation responseXml: ");
		}
		
		CkDeviceInfo deviceInfo = deviceService.selectByDeviceCode(request.getHeader().getDeviceCode());
		resultCode = request.checkRequest(deviceInfo, body.getRevervationNo());
		if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			return buildResult(resultCode, model, CHECK_RESERVATION_FILE,"checkReservation responseXml: ");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("addCode", body.getRevervationNo());
		PassCode pc = passCodeService.getPassCodeByParams(params);
		if(pc == null){
			return buildResult(ConstantMsg.CK_MSG.CODE_EXISTS.getCode(), model, CHECK_RESERVATION_FILE,"checkReservation responseXml: ");
		}
		Long orderId = pc.getOrderId();
		resultCode = checkAddCode(orderId);
		if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			return buildResult(resultCode, model, CHECK_RESERVATION_FILE,"码已使用--checkReservation responseXml: ");
		}
		OrdOrder ordOrder= orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if(Constant.PAYMENT_TARGET.TOSUPPLIER.name().equalsIgnoreCase(ordOrder.getPaymentTarget())){
			return buildResult(ConstantMsg.CK_MSG.PAY_TOSUPPLIER.getCode(), model, CHECK_RESERVATION_FILE,"checkReservation responseXml: ");
		}
		
		if("1".equals(body.getNoType())){
			if(!StringUtils.equals(body.getPhoneNo(),ordOrder.getFristTravellerOrdPerson().getMobile())){
				return buildResult(ConstantMsg.CK_MSG.CODEUNMACHPHONE.getCode(), model, CHECK_RESERVATION_FILE,"checkReservation responseXml: ");
			}
		}
		List<OrdOrder> orderList = new ArrayList<OrdOrder>();
		orderList.add(ordOrder);
		Order order= new Order(orderId,body.getPhoneNo());
		model.put("order",buildOrder(order,orderList));
		return buildResult(resultCode, model, CHECK_RESERVATION_FILE,"checkReservation responseXml: ");
	}

	@Override
	public String getPrintInfo(String requestXml) throws Exception{
		log.info("getPrintInfo requestXml: "+requestXml);
		Map<String, Object> model = new HashMap<String, Object>();
		Order order = new Order();
		CKPrintInfoBody body = new CKPrintInfoBody();
		CKRequest request = new CKRequest(body);
		String resultCode = request.init(requestXml);
		if(ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			CkDeviceInfo deviceInfo = deviceService.selectByDeviceCode(request.getHeader().getDeviceCode());
			resultCode = request.checkRequest(deviceInfo, body.getOrderId()+"");
		}
		if(ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			List<OrdOrder> orderList = queryParentOrder(order,body.getOrderId());
			resultCode = buildOrderForPrintInfo(order,orderList,request.getHeader().getDeviceCode());
		}
		if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			return buildResult(resultCode, model, PRINTINFO_FILE,"getPrintInfo responseXml: ");
		}
		model.put("order", order);
		return buildResult(resultCode, model, PRINTINFO_FILE,"getPrintInfo responseXml: ");
	}
	@Override
	public String confirmPrint(String requestXml) throws Exception {
		log.info("confirmPrint requestXml: "+requestXml);
		Map<String, Object> model = new HashMap<String, Object>();
		CKConfirmPrintBody body = new CKConfirmPrintBody();
		CKRequest request = new CKRequest(body);
		String resultCode = request.init(requestXml);
		if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			return buildResult(resultCode, model, CONFIRM_PRINT_FILE,"confirmPrint responseXml: ");
		}
		CkDeviceInfo deviceInfo = deviceService.selectByDeviceCode(request.getHeader().getDeviceCode());
		resultCode = request.checkRequest(deviceInfo, body.getOrderId()+"");
		if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			return buildResult(resultCode, model, CONFIRM_PRINT_FILE,"confirmPrint responseXml: ");
		}
		Order order = new Order();
		List<OrdOrder> orderList = queryParentOrder(order,body.getOrderId());
		resultCode = updatePassPortPerform(orderList,deviceInfo);
		if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			return buildResult(resultCode, model, PRINTINFO_FILE,"getPrintInfo responseXml: ");
		}
		return buildResult(resultCode, model, CONFIRM_PRINT_FILE,"confirmPrint responseXml: ");
	}
	
	@Override
	public String queryProductList(String requestXml) throws Exception {
		log.info("queryProductList requestXml: "+requestXml);
		Map<String, Object> model = new HashMap<String, Object>();
		CKProductInfoBody body = new CKProductInfoBody();
		CKRequest request = new CKRequest(body);
		String resultCode = request.init(requestXml);
		if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			return buildResult(resultCode, model, QUERY_PRODUCT_FILE,"queryProductList responseXml: ");
		}
		Response response = new Response();
		CkDeviceInfo deviceInfo = deviceService.selectByDeviceCode(request.getHeader().getDeviceCode());
		resultCode = request.checkRequest(deviceInfo, body.getKeyWord());
		if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			return buildResult(resultCode, model, QUERY_PRODUCT_FILE,"queryProductList responseXml: ");
		}
		resultCode = body.checkParams();
		if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			return buildResult(resultCode, model, QUERY_PRODUCT_FILE,"queryProductList responseXml: ");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productType", body.getProductType());
		params.put("deviceInfoId", deviceInfo.getCkDeviceInfoId());
		params.put("beginDate", body.getBeginTime());
		params.put("endDate", body.getEndTime());
		params.put("start", body.getStartRow());
		params.put("end", body.getEndRow());
		List<CkDeviceProduct> distributionProductList = this.deviceProductService.getDeviceProductList(params);
		if(CollectionUtils.isEmpty(distributionProductList)){
			log.info("查询产品为空   deviceInfo.getCkDeviceInfoId()= " +deviceInfo.getCkDeviceInfoId() + " body.getBeginTime() ="+ body.getBeginTime() + " body.getEndTime()=" + body.getEndTime() );
			return buildResult(ConstantMsg.CK_MSG.NOPROUCT_NOW.getCode(), model, QUERY_PRODUCT_FILE,"queryProductList responseXml: ");
		}
		List<Product> productList = new ArrayList<Product>();
		Long  totalNum=0L;
		for(CkDeviceProduct dp : distributionProductList) {
			ViewPage vp = dp.getViewPage();
			if(vp != null) {
				vp.setPictureList(comPictureService.getPictureByPageId(vp.getPageId()));
			}
			Product product = new Product(dp);
			if(product.getBranchCount()>0){
				productList.add(product);
				totalNum +=product.getBranchCount();
			}
		}
		response.setTotalNum(totalNum);
		response.setProducts(productList);
		model.put("responseBody", response);
		return buildResult(resultCode, model, QUERY_PRODUCT_FILE,"queryProductList responseXml: ");
	}

	@Override
	public String createOrder(String requestXml) throws Exception {
		log.info("createOrder requestXml:" + requestXml);
		Map<String, Object> model = new HashMap<String, Object>();
		CKCreateOrderBody body = new CKCreateOrderBody();
		CKRequest request = new CKRequest(body);
		String resultCode = request.init(requestXml);
		if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			return buildResult(resultCode, model, CREATE_ORDER_FILE,"createOrder responseXml: ");
		}
		CkDeviceInfo deviceInfo = deviceService.selectByDeviceCode(request.getHeader().getDeviceCode());
		resultCode = request.checkRequest(deviceInfo, body.getRequest().getBody().getOrderList().get(0).getProductId());
		if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			return buildResult(resultCode, model, CREATE_ORDER_FILE,"createOrder responseXml: ");
		}
		String mobileNumber=body.getRequest().getBody().getOrderList().get(0).getFirstVisitCustomer().getMobile();
		List<OrderInfo> orderInfos = body.getRequest().getBody().getOrderList();
		List<Order> subOrder = new ArrayList<Order>();
		UserUser user = new UserUser();
		user.setMobileNumber(mobileNumber);
		// 注册用户
		resultCode = this.rigestUser(user);
		if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			return buildResult(resultCode, model, CREATE_ORDER_FILE,"createOrder responseXml: ");
		}
		for (OrderInfo orderInfo : orderInfos) {
			resultCode = validateCreateOrderInfo(deviceInfo,orderInfo,user.getUserId());
			if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
				return buildResult(resultCode, model, CREATE_ORDER_FILE,"createOrder responseXml: ");
			}
		}
		for (OrderInfo orderInfo : orderInfos) {
			resultCode = createOrder(subOrder,orderInfo,user.getUserId());
			if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
				cancelOrder(subOrder);
				return buildResult(resultCode, model, CREATE_ORDER_FILE,"createOrder responseXml: ");
			}
		}
		createParentOrder(mobileNumber,subOrder,model);
		return buildResult(resultCode, model, CREATE_ORDER_FILE,"createOrder responseXml: ");
	}

	@Override
	public String queryOrder(String requestXml) throws Exception {
		log.info("queryOrder requestXml:" + requestXml);
		Map<String, Object> model = new HashMap<String, Object>();
		CKQueryOrderBody body = new CKQueryOrderBody();
		CKRequest request = new CKRequest(body);
		String resultCode = request.init(requestXml);
		if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			return buildResult(resultCode, model, QUERY_ORDER_FILE,"queryOrder responseXml: ");
		}
		CkDeviceInfo deviceInfo = deviceService.selectByDeviceCode(request.getHeader().getDeviceCode());
		resultCode = request.checkRequest(deviceInfo, body.getPhoneNo());
		if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			return buildResult(resultCode, model, QUERY_ORDER_FILE,"queryOrder responseXml: ");
		}
		Order order=new Order();
		List<OrdOrder> ordOrderList = getOrder(body.getPhoneNo(),order);
		order = buildOrder(order,ordOrderList);
		model.put("order", order);
		return buildResult(resultCode, model, QUERY_ORDER_FILE,"queryOrder responseXml: ");
	}
	
	@Override
	public String confirmPayment(String requestXml) throws Exception {
		log.info("confirmPayment requestXml:" + requestXml);
		Map<String, Object> model = new HashMap<String, Object>();
		CKConfirmPaymentBody  body = new CKConfirmPaymentBody();
		CKRequest request = new CKRequest(body);
		String resultCode = request.init(requestXml);
		if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			return buildResult(resultCode, model, CONFIRM_PAYMENT_FILE,"confirmPayment responseXml: ");
		}
		
		CkDeviceInfo deviceInfo = deviceService.selectByDeviceCode(request.getHeader().getDeviceCode());
		resultCode = request.checkRequest(deviceInfo, body.getOrderId());
		if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			return buildResult(resultCode, model, CONFIRM_PAYMENT_FILE,"confirmPayment responseXml: ");
		}
		boolean validateVerifyCodeSuccess = userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, body.getChecksum(), body.getPhoneNo());
		if(!validateVerifyCodeSuccess){
			log.info("验证码失败：" + body.getChecksum() + " body.getPhoneNo()=" + body.getPhoneNo());
			return buildResult(ConstantMsg.CK_MSG.CHECKSUM_FAULT.getCode(), model, CONFIRM_PAYMENT_FILE,"confirmPayment responseXml: ");
		}
		Order order = new Order();
		order.setPhoneNo(body.getPhoneNo());
		List<OrdOrder> ordOrderList = queryParentOrder(order,body.getLongOrderId());
		resultCode = paymentByCashAccount(ordOrderList,order);
		if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			return buildResult(resultCode, model, CONFIRM_PAYMENT_FILE,"confirmPayment responseXml: ");
		}
		return buildResult(resultCode, model, CONFIRM_PAYMENT_FILE,"confirmPayment responseXml: ");
	}
	
	@Override
	public String requestPaymentChecksum(String requestXml) throws Exception {
		log.info("requestPaymentChecksum requestXml:" + requestXml);
		Map<String, Object> model = new HashMap<String, Object>();
		CKPayMentCheckSumBody body = new CKPayMentCheckSumBody();
		CKRequest request = new CKRequest(body);
		String resultCode = request.init(requestXml);
		if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			return buildResult(resultCode, model, REQUEST_PAYMENT_CHECKSUM_FILE,"requestPaymentChecksum responseXml: ");
		}
		CkDeviceInfo deviceInfo = deviceService.selectByDeviceCode(request.getHeader().getDeviceCode());
		resultCode = request.checkRequest(deviceInfo, body.getPhoneNo());
		if(!ConstantMsg.CK_MSG.SUCCESS.getCode().equalsIgnoreCase(resultCode)){
			return buildResult(resultCode, model, REQUEST_PAYMENT_CHECKSUM_FILE,"requestPaymentChecksum responseXml: ");
		}
		List<OrdOrder> ordOrderList = getOrder(body.getPhoneNo(),new Order());
		if(ordOrderList == null || ordOrderList.isEmpty()){
			return buildResult(ConstantMsg.CK_MSG.ORDER_EXISTS.getCode(), model, REQUEST_PAYMENT_CHECKSUM_FILE,"requestPaymentChecksum responseXml: ");
		}
		UserUser user = new UserUser();
		user.setMobileNumber(body.getPhoneNo());
		String code = userClient.sendAuthenticationCode (USER_IDENTITY_TYPE.MOBILE, user, Constant.SMS_SSO_TEMPLATE.SMS_MOBILE_AUTHENTICATION_CODE.name());
		log.info("CK设备请求手机验证码  code:"+ code + " body.getPhoneNo()=" + body.getPhoneNo());
		return buildResult(resultCode, model, REQUEST_PAYMENT_CHECKSUM_FILE,"requestPaymentChecksum responseXml: ");
	}
	
	
	
	private List<OrdOrder> queryParentOrder(Order order,Long ordId) {
		List<OrdOrder> ordersList = new ArrayList<OrdOrder>();
		OrderParent orderParent = orderParentService.find(ordId); 
		if(orderParent!=null && orderParent.getOrderParentId()!=null){
			order.setOrderId(orderParent.getOrderParentId()+"");
			order.setPhoneNo(orderParent.getPhoneNo());
			String[] oId  = orderParent.getSubOrderNum().split(";");
			for(String id : oId){
				OrdOrder ord = orderServiceProxy.queryOrdOrderByOrderId(new Long(id));
				ordersList.add(ord);
			}
			if(oId.length==1){
				order.setOrderId(oId[0]);
			}
		}
		if(ordersList.size()==0){
			OrdOrder ord = orderServiceProxy.queryOrdOrderByOrderId(ordId);
			if(ord!=null){
				ordersList.add(ord);
				order.setOrderId(ord.getOrderId()+"");
			}
		}
		return ordersList;
	}

	public  String checkAddCode(Long orderId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		params.put("status", Constant.PASSCODE_STATUS.SUCCESS.name());
		params.put("passportStatus", Constant.PASSCODE_USE_STATUS.UNUSED);
		List<PassCode> pcs = passCodeService.queryPassCodes(params);
		if(pcs==null || pcs.isEmpty()){
			return ConstantMsg.CK_MSG.CODE_EXISTS.getCode();
		}
		return ConstantMsg.CK_MSG.SUCCESS.getCode();
	}
	
	private void createParentOrder(String mobileNumber,List<Order> subOrder,
			Map<String, Object> model) {
		UserUser user = userUserProxy.getUsersByMobOrNameOrEmailOrCard(mobileNumber);
		OrderParent orderParent = new OrderParent();
		orderParent.setPhoneNo(mobileNumber);
		if(user!=null){
			orderParent.setUserId(user.getId());
		}
		String str="";
		for(Order order :subOrder){
			str = str + order.getOrderId() + ";";
			//todo 订单状态判断逻辑
		}
		if(str.length()>1&&str.lastIndexOf(";")==str.length()-1){
			str = str.substring(0,str.length()-1);
		}
		orderParent.setSubOrderNum(str);
		long id = orderParentService.insert(orderParent);
		Order pOrder = new Order(id+"", subOrder.get(0).getStatus(), subOrder.get(0).getPaymentStatus());
		model.put("order", pOrder);
	}

	private String paymentByCashAccount(List<OrdOrder> ordOrderList,Order order){
		
		if(ordOrderList.size()<1){
			return ConstantMsg.CK_MSG.ORDER_EXITS.getCode();
		}
		Long totalPay=0L;
		for(OrdOrder ord:ordOrderList){
			if (Constant.PAYMENT_STATUS.PAYED.name().equals(ord.getPaymentStatus())) {
				log.info("订单不可以重复支付 订单ID= " + ord.getOrderId());
				return ConstantMsg.CK_MSG.DUPLICATE_PAYED.getCode();
			} else if (Constant.ORDER_STATUS.CANCEL.name().equals(ord.getOrderStatus())) {
				log.info("订单已经取消 订单ID= " + ord.getOrderId());
				return ConstantMsg.CK_MSG.PAY_CANCEL_ORDER.getCode();// 订单已经取消，不能进行支付
			} 
			totalPay += ord.getOughtPay(); 
		}
		UserUser user = userUserProxy.getUsersByMobOrNameOrEmailOrCard(order.getPhoneNo());
		if(user==null){
			log.info("查询用户失败  order.getPhoneNo()= " + order.getPhoneNo());
			return ConstantMsg.CK_MSG.PARTINER_EXISTS.getCode();
		}
		CashAccountVO cashAccount = cashAccountService.queryMoneyAccountByUserNo(user.getUserNo());
		if(cashAccount==null || !"Y".equalsIgnoreCase(cashAccount.getValid())){
			log.info("查询现金账号失败  user.getUserNo()= " + user.getUserNo());
			if(cashAccount!=null){
				log.info("现金账号无效  cashAccount.getValid()=" + cashAccount.getValid() + " cashAccount.getCashAccountId()= " + cashAccount.getCashAccountId());
			}
			return ConstantMsg.CK_MSG.PARTINER_EXISTS.getCode();
		}
		if(totalPay>cashAccount.getMaxPayMoney()){
			log.info("订单金额大于现有账号金额  totalPay= " + totalPay + " cashAccount.getMaxPayMoney()=" + cashAccount.getMaxPayMoney() );
			return ConstantMsg.CK_MSG.NOT_ENOUGH_MONEY.getCode();
		}
		
		for(OrdOrder ord:ordOrderList){
			try {
				// 从现金账户支付 
				List<Long> paymentIds = cashAccountService.payFromCashAccount(user.getId(), ord.getOrderId(),Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name(), ord.getOughtPay(),0L);
				if(paymentIds==null || paymentIds.isEmpty()){
					return ConstantMsg.CK_MSG.FAIL_PAY.getCode();
				}
				// 发送支付成功消息
				for(Long paymentId:paymentIds) {
					log.info("paymentId: " + paymentId);
					resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(paymentId));
				}
			} catch (Exception ex) {
				log.error("pay from cash account failed, order id: " + order.getOrderId() + ", user id: " + user.getUserNo() + ", amount: " + ord.getOughtPay());
				log.error(this.getClass(), ex);
				return ConstantMsg.CK_MSG.FAIL_PAY.getCode();

			}
		}
		order.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.name());
		order.setStatus(Constant.ORDER_STATUS.NORMAL.getCode());
		return ConstantMsg.CK_MSG.SUCCESS.getCode();
	}
	
	private List<OrdOrder> getOrder(String contactMobile,Order order){
		List<OrdOrder> ordersList = new ArrayList<OrdOrder>();
		OrderParent orderParent = orderParentService.queryLastOrderByPhoneOrUserId(null, contactMobile); 
		if(orderParent!=null && orderParent.getOrderParentId()!=null){
			order.setOrderId(orderParent.getOrderParentId()+"");
			String[] oId  = orderParent.getSubOrderNum().split(";");
			for(String id : oId){
				OrdOrder ord = orderServiceProxy.queryOrdOrderByOrderId(new Long(id));
				ordersList.add(ord);
			}
		}
		return ordersList;
	}
	
	private Order buildOrder(Order order,List<OrdOrder> ordOrderList) {
		Long totalQuantity = 0L;
		Long metaTotalQuantity = 0l;
		float totalAmount = 0f;
		List<Product> productList = new ArrayList<Product>();
		if(ordOrderList==null || ordOrderList.size()<1){
			return order;
		}
		for(OrdOrder ordOrder:ordOrderList){
			order.setPaymentStatus(ordOrder.getPaymentStatus());
			order.setStatus(ordOrder.getOrderStatus());
			order.setCredenctStatus(distributionCommonService.getCredenctStatus(ordOrder));
			order.setPerformStatus(queryPerformStatus(ordOrder.getOrderId()));
			List<OrdOrderItemProd> ordOrderItemProds = ordOrder.getOrdOrderItemProds();
			String firstProductName = null;
			
			for (OrdOrderItemProd ordOrderItemProd : ordOrderItemProds) {
				Long productId = ordOrderItemProd.getProductId();
				Place toDest = prodProductService.getProdProductPlaceById(productId).getToPlace();
				String placeId = toDest != null ? String.valueOf(toDest.getPlaceId()) : "";
				Long quantity = ordOrderItemProd.getQuantity();
				float amount = ordOrderItemProd.getAmountYuan();
				if(firstProductName == null){
					firstProductName = ordOrderItemProd.getProductName();
				}
				Product product = new Product(String.valueOf(productId), placeId, getProductName(ordOrderItemProd.getProductName()), ordOrderItemProd.getZhBranchName(), String.valueOf(ordOrderItemProd.getPriceYuan()), ordOrderItemProd.getZhVisitTime(),String.valueOf(quantity));
				product.setIsLocal("Y");
				product.setPlaceTo(toDest != null ? toDest.getName() : "");
				productList.add(product);
				totalQuantity += quantity;
				totalAmount += amount;
				order.setOrderDesc(firstProductName);
				metaTotalQuantity +=1;
			}
		}
		order.setMetaTotalQuantity(metaTotalQuantity+"");
		order.setTotalQuantity(String.valueOf(totalQuantity));
		order.setTotalAmount(String.valueOf(totalAmount));
		order.setProductList(productList);
		return order;
	}
	
	private String getProductName(String name){
		if(StringUtils.isNotEmpty(name)) {
			int start=name.lastIndexOf("(");
			if(start != -1){
				return name.substring(0,start);
			}
		}
		return "";
	}
	private String buildOrderForPrintInfo(Order order,List<OrdOrder> ordOrderList, String deviceCode) {
		List<Product> productList = new ArrayList<Product>();
		if(ordOrderList==null || ordOrderList.size()<1){
			return ConstantMsg.CK_MSG.ORDER_EXITS.getCode();
		}
		int totalQuantity =0;
		CkDeviceInfo deviceInfo = deviceService.selectByDeviceCode(deviceCode);
		String placeName = deviceInfo.getCkDeviceName();
		for(OrdOrder ordOrder:ordOrderList){
			if(Constant.PAYMENT_TARGET.TOSUPPLIER.name().equalsIgnoreCase(ordOrder.getPaymentTarget())){
				return ConstantMsg.CK_MSG.PAY_TOSUPPLIER.getCode();
			}
			if(!Constant.PAYMENT_STATUS.PAYED.getCode().equalsIgnoreCase(ordOrder.getPaymentStatus())){
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ordOrder = orderServiceProxy.queryOrdOrderByOrderId(ordOrder.getOrderId());
				log.info("订单未支付重复查询" + ordOrder.getPaymentStatus());
				if(!Constant.PAYMENT_STATUS.PAYED.getCode().equalsIgnoreCase(ordOrder.getPaymentStatus())){
					return ConstantMsg.CK_MSG.ORDER_UNPAY.getCode();
				}
			}
			if(isCanPrint(ordOrder)){
				order.setPaymentStatus(ordOrder.getPaymentStatus());
				order.setStatus(ordOrder.getOrderStatus());
				order.setCredenctStatus(distributionCommonService.getCredenctStatus(ordOrder));
				order.setPerformStatus(queryPerformStatus(ordOrder.getOrderId()));
				List<OrdOrderItemProd> ordOrderItemProds = ordOrder.getOrdOrderItemProds();
				for (OrdOrderItemProd ordOrderItemProd : ordOrderItemProds) {
					Long productId = ordOrderItemProd.getProductId();
					Place toDest = prodProductService.getProdProductPlaceById(productId).getToPlace();
					String placeId = toDest != null ? String.valueOf(toDest.getPlaceId()) : "";
					for(OrdOrderItemMeta ordMeta:ordOrderItemProd.getOrdOrderItemMetas()){
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("metaProductId", ordMeta.getMetaProductId());
						params.put("deviceInfoId", deviceInfo.getCkDeviceInfoId());
						List<CkDeviceProduct> ckDPList = deviceProductService.queryCanPrintDeviceProductInfo(params);
						if(ckDPList==null || ckDPList.isEmpty()){
							continue;
						}
						Long quantity = ordMeta.getQuantity()*ordMeta.getProductQuantity();
						Product product = new Product(String.valueOf(productId), placeId, ordMeta.getShortProductName(), ordMeta.getZhBranchName(), String.valueOf(ordOrderItemProd.getPriceYuan()), ordOrderItemProd.getZhVisitTime(),String.valueOf(quantity));
						product.setIsLocal("Y");
						product.setPlaceTo(toDest != null ? toDest.getName() : "");
						product.setQrCode("");
						product.setPlaceName(placeName);
						List<PassCode> pcs = getPassCodeList(ordOrder.getOrderId());
						if(pcs !=null && pcs.size()>0){
							if(!isHaveCode(pcs,ordOrder.getOrderId(),ordMeta,product)){
								continue;
							}
						}
						productList.add(product);
						totalQuantity++;
					}
				}
			}
		}
		order.setTotalQuantity(totalQuantity+"");
		order.setProductList(productList);
		if(order.getProductList()==null || order.getProductList().isEmpty()){
			return ConstantMsg.CK_MSG.UNSUPPORT_CODE.getCode();
		}
		return ConstantMsg.CK_MSG.SUCCESS.getCode();
	}
	
	private boolean isCanPrint(OrdOrder ord) {
		if(ord.getAllOrdOrderItemMetas()!=null && ord.getAllOrdOrderItemMetas().size()>0){
			for(OrdOrderItemMeta meta : ord.getAllOrdOrderItemMetas()){
				if(ord.getVisitTime()!=null && meta.getValidDays()!=null){
					int validDay = Integer.valueOf(new String().valueOf(meta.getValidDays()));
					Date visitTime = DateUtil.dsDay_Date(ord.getVisitTime(), validDay+1);
					if(new Date().after(visitTime)){
						return false;
					}
				}
			}
		}
		return true;
	}



	private String queryPerformStatus(Long orderId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		params.put("status", Constant.PASSCODE_STATUS.SUCCESS.name());
		params.put("passportStatus", Constant.PASSCODE_USE_STATUS.UNUSED.name());
		List<PassCode> unPcs = passCodeService.queryPassCodes(params);
		 
		params.put("orderId", orderId);
		params.put("status", Constant.PASSCODE_STATUS.SUCCESS.name());
		params.put("passportStatus", Constant.PASSCODE_USE_STATUS.USED.name());
		List<PassCode> uPcs = passCodeService.queryPassCodes(params);
		if(uPcs.size()>0 && unPcs.size()==0 ){
			return Constant.PASSCODE_USE_STATUS.USED.name();
		}
		return Constant.PASSCODE_USE_STATUS.UNUSED.name();
	}



	private boolean isHaveCode(List<PassCode> pcs, Long orderId, OrdOrderItemMeta ordMeta, Product product) {
		for(PassCode pc:pcs){
			if(Constant.OBJECT_TYPE.ORD_ORDER_ITEM_META.getCode().equalsIgnoreCase(pc.getObjectType())){
				if(ordMeta.getOrderItemMetaId()==pc.getObjectId()){
					if(pc.getCodeImage()!=null){
						product.setQrCode(DistributionUtil.encode(pc.getCodeImage()));
						return true;
					}
				}
			}else{
				List<PassPortCode> ppc = passCodeService.queryPassPortCodes(pc.getCodeId());
				for(PassPortCode pas: ppc){
					if(Constant.PASSCODE_USE_STATUS.UNUSED.getCode().equals(pas.getStatus())){
						CompositeQuery compositeQuery = new CompositeQuery();
						compositeQuery.getMetaPerformRelate().setTargetId(String.valueOf(pas.getTargetId()));
						compositeQuery.getMetaPerformRelate().setOrderId(orderId);
						compositeQuery.getMetaPerformRelate().setMetaBranchId(ordMeta.getMetaBranchId());
						compositeQuery.getPageIndex().setBeginIndex(0);
						compositeQuery.getPageIndex().setEndIndex(10000);
						List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
						if(orderItemMetas!=null&&orderItemMetas.size()>0){
							if(pc.getCodeImage()!=null){
								product.setQrCode(DistributionUtil.encode(pc.getCodeImage()));
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	private List<PassCode> getPassCodeList(Long orderId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		params.put("status", Constant.PASSCODE_STATUS.SUCCESS.name());
		params.put("passportStatus", Constant.PASSCODE_USE_STATUS.UNUSED);
		List<PassCode> pcs = passCodeService.queryPassCodes(params);
		if(pcs==null || pcs.size()<1){
			log.info("查询码为空  orderId=" + orderId);
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pcs = passCodeService.queryPassCodes(params);
			if(pcs==null || pcs.size()<1){
				log.info("第二次查询码为空  orderId=" + orderId);
			}
		}
		return pcs;
	}

	private String buildResult(String errorNo, Map<String, Object> model, String template,String msg) throws IOException, TemplateException {
		String responseXml;
		model.put("code", errorNo);
		model.put("describe", ConstantMsg.CK_MSG.getCnName(errorNo));
		responseXml = TemplateUtils.fillFileTemplate(BASE_TEMPLATE_DIR,template , model);
//		if(msg.indexOf("queryProductList")==-1){
			log.info(msg+responseXml);
//		}
		return responseXml;
	}
	
	
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
		List<Person> pList = orderInfo.getOtherVisitCustomer().getPersonList();
		for (int j = 0; j < pList.size(); j++) {
			UsrReceivers receiverVisitCustomer = new UsrReceivers();
			Person pp = pList.get(j);
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
	private String rigestUser(UserUser user) {
		String mobileNumber = user.getMobileNumber();
		String errorNo = ConstantMsg.CK_MSG.SUCCESS.getCode();
		if (mobileNumber.charAt(0) == '0') {
			mobileNumber = mobileNumber.substring(1, mobileNumber.length());
		}
		try {
			// 该手机号是否已注册
			UserUser u = userUserProxy.getUsersByMobOrNameOrEmailOrCard(mobileNumber);
			if (u == null) {
				UserUser newUser = UserUserUtil.genDefaultUserByMobile(mobileNumber);
				newUser = userUserProxy.register(newUser);
				user.setUserId(newUser.getUserId());
			} else {
				user.setUserId(u.getUserId());
			}
		} catch (Exception e) {
			log.error("rigestUser", e);
			//"用户注册出错！";
			errorNo = "1001";
		}
		return errorNo;
	}

	/**
	 * 创建订单联系人列表
	 */
	private List<com.lvmama.comm.bee.vo.ord.Person> createPersonList(OrderInfo orderInfo, String userId) {
		FirstCustomer firstCustomer = orderInfo.getFirstVisitCustomer();
		// 取票人信息
		List<UsrReceivers> receList = receiverUserService.loadUserReceiversByUserId(userId);
		String receiverId = getReceiverId(receList, firstCustomer.getName(), firstCustomer.getMobile(), firstCustomer.getCredentials());
		List<com.lvmama.comm.bee.vo.ord.Person> personList = new ArrayList<com.lvmama.comm.bee.vo.ord.Person>();
		com.lvmama.comm.bee.vo.ord.Person contact = new com.lvmama.comm.bee.vo.ord.Person();
		contact.setCertNo(firstCustomer.getCredentials());
		contact.setCertType(firstCustomer.getCredentialsType());
		contact.setMobile(firstCustomer.getMobile());
		contact.setName(firstCustomer.getName());
		contact.setPinyin(firstCustomer.getPinyin());
		contact.setPersonType(Constant.ORD_PERSON_TYPE.CONTACT.name());
		contact.setReceiverId(receiverId);
		personList.add(contact);
		// 预订人
		com.lvmama.comm.bee.vo.ord.Person booker = new com.lvmama.comm.bee.vo.ord.Person();
		booker.setCertNo(firstCustomer.getCredentials());
		booker.setCertType(firstCustomer.getCredentialsType());
		booker.setMobile(firstCustomer.getMobile());
		booker.setName(firstCustomer.getName());
		booker.setPinyin(firstCustomer.getPinyin());
		booker.setPersonType(Constant.ORD_PERSON_TYPE.BOOKER.name());
		booker.setReceiverId(receiverId);
		personList.add(booker);
		//第一游玩人
		com.lvmama.comm.bee.vo.ord.Person firstPerson = new com.lvmama.comm.bee.vo.ord.Person();
		firstPerson.setCertNo(firstCustomer.getCredentials());
		firstPerson.setCertType(firstCustomer.getCredentialsType());
		firstPerson.setMobile(firstCustomer.getMobile());
		firstPerson.setName(firstCustomer.getName());
		firstPerson.setPersonType(Constant.ORD_PERSON_TYPE.TRAVELLER.name());
		firstPerson.setReceiverId(getReceiverId(receList, firstCustomer.getName(), firstCustomer.getMobile(), firstCustomer.getCredentials()));
		personList.add(firstPerson);
		
		// 游客信息
		List<Person> pList = orderInfo.getOtherVisitCustomer().getPersonList();
		if(pList!=null){
			for (int j = 0; j < pList.size(); j++) {
				Person pp = pList.get(j);
				com.lvmama.comm.bee.vo.ord.Person person = new com.lvmama.comm.bee.vo.ord.Person();
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
		String errorNo = ConstantMsg.CK_MSG.SUCCESS.getCode();
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
			String errorNo = ConstantMsg.CK_MSG.SUCCESS.getCode();
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
	private String updatePassPortPerform(List<OrdOrder> orderList,CkDeviceInfo deviceInfo) {
		if(orderList == null || orderList.isEmpty()){
			return ConstantMsg.CK_MSG.ORDER_EXITS.getCode();
		}
		for(OrdOrder ord:orderList){
			List<PassPortCode> ppc = passCodeService.searchPassPortByOrderId(ord.getOrderId());
			if(ppc!=null){
				for(PassPortCode pas: ppc){
					if(Constant.PASSCODE_USE_STATUS.UNUSED.getCode().equals(pas.getStatus())){
						CompositeQuery compositeQuery = new CompositeQuery();
						compositeQuery.getMetaPerformRelate().setTargetId(String.valueOf(pas.getTargetId()));
						compositeQuery.getMetaPerformRelate().setOrderId(ord.getOrderId());
						compositeQuery.getPageIndex().setBeginIndex(0);
						compositeQuery.getPageIndex().setEndIndex(10000);
						List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("metaProductId", orderItemMetas.get(0).getMetaProductId());
						params.put("deviceInfoId", deviceInfo.getCkDeviceInfoId());
						List<CkDeviceProduct> ckDPList = deviceProductService.queryCanPrintDeviceProductInfo(params);
						if(ckDPList!=null&&ckDPList.size()>0){
							update(pas) ;
						}
					}
				}
			}
		}
		
		return ConstantMsg.CK_MSG.SUCCESS.getCode();
	}
	
	 private void update(PassPortCode pas) {
		Passport passport = new Passport();
		passport.setPortId(pas.getTargetId());
		passport.setOutPortId(pas.getTargetId().toString());
		passport.setChild("0");
		passport.setAdult("0");
		passport.setUsedDate(new Date());
		PassCode passCode = passCodeService.getPassCodeByCodeId(pas.getCodeId());
		this.passCodeService.updatePassPortCode(passCode,passport);
		insertlog(pas.getOrderId(),pas.getCodeId());
		addOrderPerform(pas);
	}
	 private String addOrderPerform(PassPortCode pas) {
		 List<PassPortCode> ppc = passCodeService.queryPassPortCodes(pas.getCodeId());
		 for(PassPortCode pp:ppc){
			 if(Constant.PASSCODE_USE_STATUS.UNUSED.getCode().equals(pp.getStatus())){
				 return "";
			 }
		 }
		 PassCode passCode = passCodeService.getPassCodeByCodeId(pas.getCodeId());
			String flag = PassportConstant.PASSCODE_APPLY_STATUS.SUCCESS.name();
			// 成人数
			Long adultQuantity = 0L;
			// 儿童数
			Long childQuantity = 0L;
			log.info("adultQuantity Total:" + adultQuantity);
			log.info("childQuantity Total:" + childQuantity);
			StringBuilder buf=new StringBuilder();
			buf.append(",adultQuantity:"+adultQuantity);
			buf.append(",childQuantity:"+childQuantity);
			buf.append(",OrderId:"+passCode.getOrderId());
			
			// 需要更新履行对象属性
			boolean suc = false;
			if (ORDER.equals(passCode.getObjectType())) {
				CompositeQuery compositeQuery = new CompositeQuery();
				compositeQuery.getMetaPerformRelate().setOrderId(passCode.getOrderId());
				compositeQuery.getMetaPerformRelate().setTargetId(pas.getTargetId()+"");
				compositeQuery.getPageIndex().setBeginIndex(0);
				compositeQuery.getPageIndex().setEndIndex(1000000000);
				List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
				if(!orderItemMetas.isEmpty()){
					//对订单子子项做履行
					if(orderItemMetas.size() > 1) {
						for(OrdOrderItemMeta meta : orderItemMetas){
							buf.append(",Perform Method:"+ORDER_ITEM);
							suc = orderServiceProxy.insertOrdPerform(pas.getTargetId(), meta.getOrderItemMetaId(), ORDER_ITEM, meta.getTotalAdultQuantity(), meta.getTotalChildQuantity());
							if(!suc){
								break;
							}
						}
					} else if(orderItemMetas.size() == 1){
						if (adultQuantity == 0 && childQuantity == 0) {
							adultQuantity = orderItemMetas.get(0).getTotalAdultQuantity();
							childQuantity = orderItemMetas.get(0).getTotalChildQuantity();
						}
						suc = orderServiceProxy.insertOrdPerform(pas.getTargetId(), orderItemMetas.get(0).getOrderItemMetaId(), ORDER_ITEM, adultQuantity, childQuantity);
					}
				}else{
					flag = PassportConstant.PASSCODE_APPLY_STATUS.FAILED.name();
				}
			} else {
				if (adultQuantity == 0 && childQuantity == 0) {
					OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
					OrdOrderItemMeta itemMeta = this.getItemMeta(ordOrder, passCode);
					if (itemMeta != null) {
						adultQuantity = itemMeta.getTotalAdultQuantity();
						childQuantity = itemMeta.getTotalChildQuantity();
					}
				}
				buf.append(",Perform Method:"+passCode.getObjectType());
				suc = orderServiceProxy.insertOrdPerform(pas.getTargetId(), passCode.getObjectId(), passCode.getObjectType(), adultQuantity, childQuantity);
			}
			return flag;
		}
	 private void insertlog(Long orderId,Long codeId){
		 ComLog log = new ComLog();
			log.setObjectType("PASS_CODE");
			log.setParentId(orderId);
			log.setObjectId(codeId);
			log.setOperatorName("SYSTEM");
			log.setLogType(Constant.COM_LOG_ORDER_EVENT.systemApprovePass.name());
			log.setLogName("设备刷码通关");
			log.setContent("CK设备更新订单履行状态");
			comLogService.addComLog(log);
	 }
	 private OrdOrderItemMeta getItemMeta(OrdOrder ordOrder, PassCode passCode) {
			for (OrdOrderItemMeta item : ordOrder.getAllOrdOrderItemMetas()) {
				if (item.getOrderItemMetaId().longValue() == (passCode.getObjectId().longValue())) {
					return item;
				}
			}
			return null;
		}
	 
	 private void cancelOrder(List<Order> subOrder) {
			if(subOrder!=null && subOrder.size()>0){
				for(Order order:subOrder){
					orderServiceProxy.cancelOrder(order.getLongOrderId(), "设备其他产品下单失败 系统自测取消订单", "SYSTEM");
				}
			}
		}

		private String validateCreateOrderInfo(CkDeviceInfo deviceInfo, OrderInfo orderInfo, String userId) {
			Long productId = Long.parseLong(orderInfo.getProductId());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("deviceInfoId", deviceInfo.getCkDeviceInfoId());
			params.put("productId", productId);
			for (Branch branch : orderInfo.getProductBranch().getBranchList()) {
				params.put("productBranchId", branch.getBranchId());
				params.put("volid", Constant.TRUE_FALSE.TRUE.getCode());
				if (!deviceProductService.hasProduct(params)) {
					log.info("resultNo=1027" + "deviceInfoId=" + deviceInfo.getCkDeviceInfoId() + "branch.getBranchId()=" + branch.getBranchId());
					return "1027";
				}
			}

			ProdProduct product = prodProductService.getProdProductById(productId);
			Date onlineTime = product.getOnlineTime();
			Date offLineTime = product.getOfflineTime();
			Date nowTime = new Date();
			if (!(nowTime.after(onlineTime) && nowTime.before(offLineTime))) {
				log.info("resultNo=1028" + " product.getProductId()= " + product.getProductId() + "onlineTime=" + onlineTime + "offLineTime=" + offLineTime);
				return "1028";
			}
			if(product.isPaymentToSupplier()){
				log.info("resultNo=1041" + " product.getProductId()= " + product.getProductId() + "product.getPayToSupplier()=" + product.getPayToSupplier());
				return ConstantMsg.CK_MSG.PAY_TOSUPPLIER_BOOK_FAIL.getCode();
			}

			orderInfo.getFirstVisitCustomer().setName(VISIT_CUSTOMER_NAME);
			// 创建联系人列表
			createContacts(orderInfo, userId);
			for (Branch branch:orderInfo.getProductBranch().getBranchList()) {
				String visitDate = branch.getVisitDate();
				Long branchId = Long.parseLong(branch.getBranchId());
				Long quantity = Long.valueOf(branch.getQuantity());
				Date visitD =DateUtil.toDate(visitDate, "yyyy-MM-dd");
				// 验证订单信息
				String errorNo = this.validateOrderInfo(branch);
				if (!ConstantMsg.CK_MSG.SUCCESS.getCode().equals(errorNo)) {
					log.info("resultNo="+ errorNo + "ProductId= " +orderInfo.getProductId() + " branch.getBranchId()= " + branch.getBranchId());
					return errorNo;
				}
				TimePrice timePrice = prodProductService.calcProdTimePrice(branchId, visitD);
				if (timePrice == null) {
					log.info("resultNo="+ 1028 + "ProductId= " +orderInfo.getProductId() +" timePrice branchId = " + branch.getBranchId() );
					return  "1028";
				} 
				boolean productSellable = prodProductService.isProductSellable(branchId, quantity, visitD);
				if (!productSellable) {
					log.info("resultNo="+ 1028 + "branchId=" + branchId + " quantity=" + quantity +" visitD=" + visitD);
					return "1011";
				}
			}
			return  ConstantMsg.CK_MSG.SUCCESS.getCode();
		}

		private String createOrder( List<Order> subOrder,OrderInfo orderInfo, String userId) {
			List<Item> itemList = new ArrayList<Item>();
			int i=0;
			for (Branch branch:orderInfo.getProductBranch().getBranchList()) {
				String visitDate = branch.getVisitDate();
				Long branchId = Long.parseLong(branch.getBranchId());
				Long quantity = Long.valueOf(branch.getQuantity());
				Date visitD =DateUtil.toDate(visitDate, "yyyy-MM-dd");
				ProdProduct product = prodProductService.getProdProductById(Long.valueOf(orderInfo.getProductId()));
				Item item = new Item();
				item.setProductId(product.getProductId());
				item.setVisitTime(visitD);
				item.setQuantity(quantity.intValue());
				item.setProductBranchId(branchId);
				// 单房型，需设置每天时间价格信息
				if (Constant.PRODUCT_TYPE.HOTEL.name().equals(product.getProductType()) && Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(product.getSubProductType())) {
					String errorNo = this.createSingleRoom(branch, product, item, i, quantity);
					if (!ConstantMsg.CK_MSG.SUCCESS.getCode().equals(errorNo)) {
						log.info("errorNo:" + errorNo + "branchId" + branchId + "quantity =" + quantity + " i=" + i);
						return errorNo;
					}
				} 
				if (i == 0) {
					item.setIsDefault("true");
				}
				itemList.add(item);
				i++;
			}
			BuyInfo buyInfo = new BuyInfo();
			buyInfo.setUserId(userId);
			buyInfo.setChannel(Constant.CHANNEL.DISTRIBUTION_CKDEVICE.name()); // 分销渠道
			buyInfo.setItemList(itemList);
			buyInfo.setPersonList(createPersonList(orderInfo, buyInfo.getUserId()));
			buyInfo.setResourceConfirmStatus(Boolean.TRUE.toString());
			buyInfo.setPaymentTarget(Constant.PAYMENT_TARGET.TOLVMAMA.name());
			buyInfo.setWaitPayment(Constant.WAIT_PAYMENT.PW_DEFAULT.getValue());
			ResultHandle handle = orderServiceProxy.checkOrderStock(buyInfo);
			if (handle.isFail()) {
				return "1028";
			} else {
				OrdOrder ordOrder = orderServiceProxy.createOrder(buyInfo);
				if(ordOrder == null || !Constant.ORDER_STATUS.NORMAL.getCode().equals(ordOrder.getOrderStatus())){
					log.info("create Order error productId" + itemList.get(0).getProductId());
					return ConstantMsg.CK_MSG.UNKNOW_ERROR.getCode();
				}
				if (null != orderInfo.getOrderMemo()) {
					OrdOrderMemo memo = new OrdOrderMemo();
					memo.setOrderId(ordOrder.getOrderId());
					memo.setContent(orderInfo.getOrderMemo());
					memo.setType("M1");
					memo.setOperatorName(VISIT_CUSTOMER_NAME);
					memo.setCreateTime(new Date());
					memo.setUserMemo("false");
					orderServiceProxy.saveMemo(memo, VISIT_CUSTOMER_NAME);
				}
				Order order = new Order(ordOrder.getOrderId().toString(), ordOrder.getOrderStatus(), ordOrder.getPaymentStatus());
				subOrder.add(order);
			}
			return ConstantMsg.CK_MSG.SUCCESS.getCode();
		} 
	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public void setDistributionCommonService(
			DistributionCommonService distributionCommonService) {
		this.distributionCommonService = distributionCommonService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}

	public void setOrderParentService(OrderParentService orderParentService) {
		this.orderParentService = orderParentService;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}

	public void setDeviceProductService(CkDeviceProductService deviceProductService) {
		this.deviceProductService = deviceProductService;
	}

	public void setDeviceService(CkDeviceService deviceService) {
		this.deviceService = deviceService;
	}
	
}
