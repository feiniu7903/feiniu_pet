package com.lvmama.distribution.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.distribution.DistributionOrderRefund;
import com.lvmama.comm.bee.po.distribution.DistributionProduct;
import com.lvmama.comm.bee.po.distribution.DistributorInfo;
import com.lvmama.comm.bee.po.distribution.OrdOrderDistribution;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassEvent;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
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
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.SynchronizedLock;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.distribution.model.jd.Body;
import com.lvmama.distribution.model.jd.Head;
import com.lvmama.distribution.model.jd.Order;
import com.lvmama.distribution.model.jd.Product;
import com.lvmama.distribution.model.jd.Request;
import com.lvmama.distribution.model.jd.Resource;
import com.lvmama.distribution.model.jd.Response;
import com.lvmama.distribution.model.jd.Result;
import com.lvmama.distribution.model.lv.common.ContactPerson;
import com.lvmama.distribution.model.lv.common.OrderInfo;
import com.lvmama.distribution.model.lv.common.PostPerson;
import com.lvmama.distribution.model.lv.common.ProductBranch;
import com.lvmama.distribution.model.lv.common.VisitCustomer;
import com.lvmama.distribution.service.BaseDistributionService;
import com.lvmama.distribution.service.DistributionForJingdongService;
import com.lvmama.distribution.util.DistributionUtil;
import com.lvmama.distribution.util.JdUtil;

/**
 * 京东调用Service
 * 
 * @author gaoxin
 * 
 */
public class DistributionForJingdongServiceImpl extends BaseDistributionService implements DistributionForJingdongService {
	private static final Log log = LogFactory.getLog(DistributionForJingdongServiceImpl.class);
	private Long pageSize = 20l;
	private DistributionProductService distributionProductService;
	
	/** 分销订单服务*/
	private DistributionOrderService distributionOrderService;
	private ProdProductService prodProductService;
	private static final String SUCCESS = "100";

	/**
	 * 京东提供服务 新增景区
	 */
	@Override
	public void addResources() {
		Long proSum = getDistributionProductPlaceCount();// 得到总数据数
		log.info("addResources count:" + proSum);
		Long pagesSum = getPagesSum(proSum);// 得到总页数
		for (int i = 0; i < pagesSum; i++) {
			List<Place> places = getDistributionProductPlaces(i * pageSize + 1, (i + 1) * pageSize);
			for (Place place : places) {
				String msg = buildAddResourceXml(place);
				//log.info("addResource requestXml: " + msg);
				pushXml("10001", msg);
			}
		}
	}

	/**
	 * 京东提供服务 更新景区
	 */
	@Override
	public void updateResources() {
		
		Long proSum = getDistributionProductPlaceCount();
		log.info("updateResources count:" + proSum);
		Long pagesSum = getPagesSum(proSum);// 得到总页数
		for (int i = 0; i < pagesSum; i++) {
			List<Place> places = getDistributionProductPlaces(i * pageSize + 1, (i + 1) * pageSize);
			for (Place place : places) {
				String msg = buildUpdateResourceXml(place);
				//log.info("updateResources requestXml: " + msg);
				pushXml("10003", msg);
			}
		}
	}

	/**
	 * 京东提供服务 新增产品
	 */
	@Override
	public void addProducts() {
		Long proSum = getDistributionProCount();
		log.info("addProducts count:" + proSum);
		Long pagesSum = getPagesSum(proSum);// 得到总页数
		for (int i = 0; i < pagesSum; i++) {
			List<DistributionProduct> products = getDistributionPro(i * pageSize + 1, (i + 1) * pageSize);
			for (DistributionProduct disproduct : products) {
				String msg = buildaddProductXml(disproduct);
//				log.info("addProducts requestXml: " + msg);
				pushXml("10002", msg);
			}
		}
	}

	/**
	 * 京东提供服务 更新产品
	 */
	@Override
	public void updateProducts() {
		Long proSum = getDistributionProCount();
		log.info("updateProducts count:" + proSum);
		Long pagesSum = getPagesSum(proSum);// 得到总页数
		for (int i = 0; i < pagesSum; i++) {
			List<DistributionProduct> products = getDistributionPro(i * pageSize + 1, (i + 1) * pageSize);
			for (DistributionProduct disproduct : products) {
				String msg = buildUpdateProductXml(disproduct);
				//log.info("updateProducts requestXml: " + msg);
				pushXml("10004", msg);
			}
		}
	}

	/**
	 * 驴妈妈发起退款
	 * @throws Exception 
	 */
	@Override
	public boolean applyOrderRefund(DistributionOrderRefund refundHistory){
		boolean applyResult=true;
		Head head = fillHeadBean();
		if(refundHistory==null){
			log.info("applyOrderRefund requestError:  DistributionOrderRefund is NULL");
			applyResult=false;
		}else{
			log.info("refund param="+refundHistory.toString());
			String msg = buildRefundXml(refundHistory, head);
			log.info("applyOrderRefund requestXml: " + msg);
			String responseXml=pushXml("10012", msg);
			String isSuccess="false";
			try {
				isSuccess = JdUtil.getIsSuccess(responseXml);
			} catch (Exception e) {
				applyResult=false;
				log.error("applyOrderRefund requestException: ",e);
			}
			if("true".equals(isSuccess)){
				distributionOrderService.updateRefundStatusByPartnerOrdid(refundHistory.getPartnerOrderId(),null);
			}else{
				log.info("applyOrderRefund requestError");
				applyResult=false;
			}
			
		}
		return applyResult;
	}


	/**
	 * 京东提供服务 新增每日价格
	 */
	@Override
	public void addDailyPrices() {
		Long proSum = getDistributionProCount();
		log.info("addDailyPrices count:" + proSum);
		Long pagesSum = getPagesSum(proSum);// 得到总页数
		for (int i = 0; i <= pagesSum; i++) {
			List<DistributionProduct> products = getDistributionPro(i * pageSize + 1, (i + 1) * pageSize);
			for (DistributionProduct disproduct : products) {
				String msg = buildAddDalyPricesXml(disproduct);
				pushXml("10009", msg);
			}
		}
	}


	/**
	 * 京东提供服务 修改每日价格
	 */
	@Override
	public void updateDailyPrice() {
		Long proSum = getDistributionProCount();
		log.info("updateDailyPrice count:" + proSum);
		Long pagesSum = getPagesSum(proSum);// 得到总页数
		for (int i = 0; i <= pagesSum; i++) {
			List<DistributionProduct> products = getDistributionPro(i * pageSize + 1, (i + 1) * pageSize);
			for (DistributionProduct disproduct : products) {
				String msg = buildUpdateDailyPriceXml(disproduct);
				//log.info("updateDailyPrice requestXml: " + msg);
				pushXml("10010", msg);
			}
		}
	}


	/**
	 * 京东提供服务 上、下架产品
	 */
	@Override
	public void onOffLineProduct() {
		Long proSum = getDistributionProCount();
		log.info("onOffLineProduct count:" + proSum);
		Long pagesSum = getPagesSum(proSum);// 得到总页数
		for (int i = 0; i <= pagesSum; i++) {
			List<DistributionProduct> products = getDistributionPro(i * pageSize + 1, (i + 1) * pageSize);
			for (DistributionProduct disproduct : products) {
				ProdProduct prodProduct=disproduct.getProdProduct();
				if ("Y".equals(prodProduct.getValid()) && "true".equals(disproduct.getProdProduct().getOnLine())) {
					List<ProdProductBranch> prodList=prodProduct.getProdBranchList();
					for (ProdProductBranch prodBranch : prodList) {
						String msg = buildOnOffLineBranchXml(disproduct, prodBranch);
						if ("Y".equals(prodBranch.getValid()) && "true".equals(prodBranch.getOnline())) {
							pushXml("10011", msg);
						} else {
							pushXml("10006", msg);
						}
					}
				} else {
					String msg = buildOnOffLineProductXml(disproduct);
					pushXml("10006", msg);
				}
			}
		}
	}

	/**
	 * 获取重发短信xml
	 * 
	 * @param request
	 * @return
	 */
	@Override
	public String getReSendSMSXml(Request request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String errorNo = null;
		String describe = null;
		String dealTime = null;
		Head reqhead=request.getHead();
		try {
			resultMap = reSendSMS(request);
		} catch (Exception e) {
			errorNo = "200";
			log.error("getReSendSMSXml Error:" , e);
		}
		if (errorNo == null) {
			errorNo = (String) resultMap.get("errorNo");
		}
		describe = (String) resultMap.get("describe");
		dealTime = DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmssSSS");
		Map<String, Object> msgMap = errorCheck(errorNo, describe);
		boolean isSuccess = (Boolean) msgMap.get("isSuccess");
		String errorCode = (String) msgMap.get("errorCode");
		String errorMsg = (String) msgMap.get("errorMsg");
		Result result = new Result(isSuccess + "", errorCode, errorMsg, dealTime);
		String version = DistributionUtil.getPropertiesByKey("jingdong.version");
		Head head = new Head(version , reqhead.getMessageId());
		Body body = new Body(result);
		Response res = new Response(head, body);
		String resXML = res.buildReSendSMSToXml();
		log.info("Response getReSendSMSXml:" + resXML);
		return resXML;
	}

	/**
	 * 获取下单响应xml
	 * 
	 * @param request
	 * @return
	 */
	@Override
	public String getSumbitOrderResXml(Request request) {
		Result result = null;
		String errorNo = null;
		String describe = null;
		OrdOrder ordOrder = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Head reqhead=request.getHead();
		Body reqbody=request.getBody();
		Order reqOrder=reqbody.getOrder();
		try {
			resultMap = createOrder(request);
		} catch (Exception e) {
			errorNo = "217";
			log.error("getSumbitOrderResXml Error:" , e);
		}
		if (errorNo == null) {
			errorNo = (String) resultMap.get("errorNo");
		}
		describe = (String) resultMap.get("describe");
		ordOrder = (OrdOrder) resultMap.get("ordOrder");
		String dealTime = DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmssSSS");
		Map<String, Object> msgMap = errorCheck(errorNo, describe);
		boolean isSuccess = (Boolean) msgMap.get("isSuccess");
		String errorCode = (String) msgMap.get("errorCode");
		String errorMsg = (String) msgMap.get("errorMsg");
		if (ordOrder != null && isSuccess) {
			Order order = new Order(ordOrder);
			order.setOrderId(reqOrder.getOrderId());
			order.setStatus("2");
			result = new Result(isSuccess + "", errorCode, errorMsg, dealTime, order);
		} else {
			result = new Result(isSuccess + "", errorCode, errorMsg, dealTime);
		}
		String version = DistributionUtil.getPropertiesByKey("jingdong.version");
		Head head = new Head(version, reqhead.getMessageId());
		Body body = new Body(result);
		Response res = new Response(head, body);
		String resXML = res.buildOrderToXml();
		log.info("Response getSumbitOrderResXml:" + resXML);
		return resXML;
	}

	/**
	 * 获取查询出订单xml
	 * 
	 * @param request
	 * @return
	 */
	@Override
	public String getQueryOrderResXml(Request request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String errorNo = null;
		OrdOrder ordOrder = null;
		Head reqhead=request.getHead();
		Body reqbody=request.getBody();
		Order reqOrder=reqbody.getOrder();
		try {
			resultMap = queryOrder(request);
		} catch (Exception e) {
			errorNo = "201";
			log.error("QueryOrderResXml Error:" , e);
		}
		if (errorNo == null) {
			errorNo = (String) resultMap.get("errorNo");
		}
		ordOrder = (OrdOrder) resultMap.get("ordOrder");
		String dealTime = DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmssSSS");
		Map<String, Object> msgMap = errorCheck(errorNo, "");
		boolean isSuccess = (Boolean) msgMap.get("isSuccess");
		String errorCode = (String) msgMap.get("errorCode");
		String errorMsg = (String) msgMap.get("errorMsg");
		Result result = null;
		if (ordOrder != null && isSuccess) {
			Order order = new Order(ordOrder);
			order.setOrderId(reqOrder.getOrderId());
			result = new Result(isSuccess + "", errorCode, errorMsg, dealTime, order);
		} else {
			result = new Result(isSuccess + "", errorCode, errorMsg, dealTime);
		}
		String version = DistributionUtil.getPropertiesByKey("jingdong.version");
		Head head = new Head(version , reqhead.getMessageId());
		Body body = new Body(result);
		Response res = new Response(head, body);
		String resXML = res.buildQueryOrderToXml();
		log.info("Response getQueryOrderResXml:" + resXML);
		return resXML;
	}

	/**
	 * 查询每日价格
	 * 
	 * @param request
	 * @return
	 */
	@Override
	public String getDailyPrices(Request request) {
		String errorNo = null;
		DistributionProduct disproduct = null;
		Head reqhead=request.getHead();
		String messageId = reqhead.getMessageId();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			DistributorInfo distributorInfo = new DistributorInfo();
			distributorInfo = distributionCommonService.getDistributorByCode(Constant.DISTRIBUTOR.JINGDONG.name());
			Head head=request.getHead();
			Body body=request.getBody();
			Product product=body.getProduct();
			paramMap.put("distributorInfoId", distributorInfo.getDistributorInfoId());
			paramMap.put("productId", Long.parseLong(product.getProductId()));
			paramMap.put("branchId", Long.parseLong(product.getBranchId()));
			paramMap.put("beginDate", DateUtil.getDateByStr(product.getValidTimeBegin(), "yyyyMMdd"));
			paramMap.put("endDate", DateUtil.getDateByStr(product.getValidTimeEnd(), "yyyyMMdd"));
			String SignStr = distributorInfo.getDistributorKey() + head.getMessageId() + head.getPartnerCode()+ head.getTimeStamp() + product.getProductId();
			List<DistributionProduct> products = distributionCommonService.getDistributionProduct(paramMap);
			
			if(!Constant.DISTRIBUTOR.JINGDONG.name().equals(head.getPartnerCode())){
				errorNo = "202";
				log.info("合作伙伴id不正确");
			}else if (!checkSign(request, SignStr)) {
				errorNo = "203";
				log.info("消息签名不一致");
			} else {
				if (products != null && !products.isEmpty()) {
					disproduct = products.get(0);
				} else {
					disproduct = buildNullProduct(request);
				}
				if(disproduct.getProdProduct().getProdBranchList() != null&& !disproduct.getProdProduct().getProdBranchList().isEmpty()){
				}else{
					disproduct = buildNullProduct(request);
				}
			}
		} catch (Exception e) {
			errorNo = "201";
			log.error("getDailyPricesResXML Error:" , e);
		}
		String resXML = buildQueryDailyPriceXml(errorNo, disproduct, messageId);
		//log.info("Response getDailyPrices:" + resXML);
		return resXML;
	}

	/**
	 * 无此分销产品返回产品下线
	 * @param request
	 * @return
	 */
	private DistributionProduct buildNullProduct(Request request) {
		List<ProdProductBranch> prodBranchList = new ArrayList<ProdProductBranch>();
		ProdProductBranch prodBranch = new ProdProductBranch();
		Product reqProduct = request.getBody().getProduct();
		Long branchId = Long.valueOf(reqProduct .getBranchId());
		Long productId = Long.valueOf(reqProduct.getProductId());
		prodBranch.setProdBranchId(branchId);
		prodBranchList.add(prodBranch);
		ProdProduct prod = new ProdProduct();
		prod.setProductId(productId);
		prod.setProdBranchList(prodBranchList);
		DistributionProduct disProd = new DistributionProduct();
		disProd.setProdProduct(prod);
		return disProd;
	}

	/**
	 * 重发短信 返回结果
	 */
	private Map<String, Object> reSendSMS(Request request) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DistributorInfo distributorInfo = new DistributorInfo();
		distributorInfo = distributionCommonService.getDistributorByCode(Constant.DISTRIBUTOR.JINGDONG.name());
		Head head=request.getHead();
		Body body=request.getBody();
		Order order=body.getOrder();
		paramMap.put("distributorInfoId", distributorInfo.getDistributorInfoId());
		paramMap.put("partnerOrderId", order.getOrderId());
		List<OrdOrderDistribution> ordOrderDitriDistributions = distributionOrderService.selectDistributionOrderByParams(paramMap);
		String SignStr = distributorInfo.getDistributorKey() + head.getMessageId() + head.getPartnerCode()+ head.getTimeStamp() + order.getOrderId();
		if(!Constant.DISTRIBUTOR.JINGDONG.name().equals(head.getPartnerCode())){
			resultMap.put("errorNo", "202");
			resultMap.put("describe", "合作伙伴id不正确");
		}else if (!checkSign(request, SignStr)) {
			resultMap.put("errorNo", "203");
			resultMap.put("describe", "消息签名不一致");
		} else if (ordOrderDitriDistributions != null && !ordOrderDitriDistributions.isEmpty()) {
			paramMap.put("orderId", ordOrderDitriDistributions.get(0).getOrderId());
			resultMap = this.resendCode(paramMap);
		} else {
			resultMap.put("errorNo", "114");
			resultMap.put("describe", "没有此订单");
		}
		return resultMap;
	}
	/**
	 * 重发凭证接口
	 * @param paramMap
	 * @return
	 */
	private Map<String,Object> resendCode(Map<String,Object> paramMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long distributorInfoId = (Long) paramMap.get("distributorInfoId");
		Long orderId = (Long) paramMap.get("orderId");
		OrdOrderDistribution ordOrderDitribution = distributionOrderService.selectByOrderIdAndDistributionInfoId(orderId, distributorInfoId);
		if (ordOrderDitribution != null) {
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			if (ordOrder.isPaymentSucc() && ordOrder.isNormal()) {
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
				resultMap.put("errorNo", "100");
			}else{
				resultMap.put("errorNo", "115");
				log.info("describe:订单状态不正常，不能进行重发凭证");
			}
		} else {
			resultMap.put("errorNo", "114");
			log.info("describe:没有此订单");
		}
		return resultMap;
	}
	/**
	 * 查询订单 返回结果
	 */
	private Map<String, Object> queryOrder(Request request) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DistributorInfo distributorInfo = new DistributorInfo();
		distributorInfo = distributionCommonService.getDistributorByCode(Constant.DISTRIBUTOR.JINGDONG.name());
		Map<String, Object> params = new HashMap<String, Object>();
		Head head=request.getHead();
		Body body=request.getBody();
		Order order=body.getOrder();
		params.put("distributorInfoId", distributorInfo.getDistributorInfoId());
		params.put("partnerOrderId", order.getOrderId());
		List<OrdOrderDistribution> ordOrderDitriDistributions = distributionOrderService.selectDistributionOrderByParams(params);
		String SignStr = distributorInfo.getDistributorKey() + head.getMessageId() + head.getPartnerCode()+ head.getTimeStamp() + order.getOrderId();
		if(!Constant.DISTRIBUTOR.JINGDONG.name().equals(head.getPartnerCode())){
			resultMap.put("errorNo", "202");
			resultMap.put("describe", "合作伙伴id不正确");
		}else if (!checkSign(request, SignStr)) {
			resultMap.put("errorNo", "203");
			resultMap.put("describe", "消息签名不一致");
		} else if (ordOrderDitriDistributions != null && !ordOrderDitriDistributions.isEmpty()) {
			paramMap.put("distributorInfoId", distributorInfo.getDistributorInfoId());
			paramMap.put("orderId", ordOrderDitriDistributions.get(0).getOrderId());
			resultMap = this.getOrder(paramMap);
		} else {
			resultMap.put("errorNo", "114");
			resultMap.put("describe", "没有此订单");
		}
		return resultMap;
	}
	/**
	 * 查询订单接口
	 * @param paramMap
	 * @return
	 */
	private Map<String,Object> getOrder(Map<String,Object> paramMap){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Long distributorInfoId = (Long) paramMap.get("distributorInfoId");
		Long orderId = (Long) paramMap.get("orderId");
		OrdOrderDistribution ordOrderDistribution = distributionOrderService.selectByOrderIdAndDistributionInfoId(orderId, distributorInfoId);
		if (ordOrderDistribution != null) {
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(ordOrderDistribution.getOrderId());
			resultMap.put("ordOrder", ordOrder);
			resultMap.put("errorNo", "100");
		} else{
			resultMap.put("errorNo", "114");
			log.info("describe:没有此订单");
		}
		return resultMap;
	}
	/**
	 * 京东下单 返回下单结果
	 */
	private Map<String, Object> createOrder(Request request) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OrderInfo orderInfo = new OrderInfo();
		DistributorInfo distributorInfo = new DistributorInfo();
		Map<String, Object> params = new HashMap<String, Object>();
		ContactPerson contactPerson = new ContactPerson();
		ProductBranch branch = new ProductBranch();
		List<ProductBranch> productBranchList = new ArrayList<ProductBranch>();
		List<VisitCustomer> visitCustomerList = new ArrayList<VisitCustomer>();
		VisitCustomer visitor = new VisitCustomer();
		Head head=request.getHead();
		Body body=request.getBody();
		Order order=body.getOrder();
		distributorInfo = distributionCommonService.getDistributorByCode(Constant.DISTRIBUTOR.JINGDONG.name());
		params.put("distributorInfoId", distributorInfo.getDistributorInfoId());
		params.put("partnerOrderId", order.getOrderId());
		List<OrdOrderDistribution> ordOrderDitriDistributions = distributionOrderService.selectDistributionOrderByParams(params);
		String SignStr = distributorInfo.getDistributorKey() + head.getMessageId() + head.getPartnerCode()+ head.getTimeStamp() + order.getOrderId();
		if(!Constant.DISTRIBUTOR.JINGDONG.name().equals(head.getPartnerCode())){
			resultMap.put("errorNo", "202");
			resultMap.put("describe", "合作伙伴id不正确");
		}else if (!checkSign(request, SignStr)) {
			resultMap.put("errorNo", "203");
			resultMap.put("describe", "消息签名不一致");
		} else if (ordOrderDitriDistributions.isEmpty()) {
			order = body.getOrder();
			orderInfo.setPartnerOrderId(order.getOrderId());
			orderInfo.setProductId(order.getProductId());

			params.put("productId", order.getProductId());
			params.put("prodBranchId", order.getBranchId());
			params.put("distributorInfoId", distributorInfo.getDistributorInfoId());
			DistributionProduct dbp = distributionProductService.getDistributionProductByBranchId(Long.parseLong(order.getBranchId()), distributorInfo.getDistributorInfoId());
			Long prodId=Long.parseLong(order.getProductId());
			ProdProduct prodProduct = prodProductService.getProdProduct(prodId);
			System.out.println(prodProduct == null || dbp == null || dbp.getProductId() != prodId);
			if (prodProduct == null || dbp == null) {
				resultMap.put("errorNo", "209");
				resultMap.put("describe", "productId错误，branchId错误");
				// 退出返回
			} else {
				contactPerson.setMobile(order.getUser().getMobile());
				contactPerson.setName(order.getUser().getName());
				contactPerson.setCredentials(order.getUser().getIdCard());
				contactPerson.setCredentialsType(Constant.CERTIFICATE_TYPE.ID_CARD.name());

				branch.setBranchId(Long.parseLong(order.getBranchId()));
				branch.setQuantity(Integer.parseInt(order.getCount()));
				branch.setVisitDate(DateUtil.getDateByStr(order.getInDate(), "yyyyMMdd"));
				branch.setSellPrice(order.getSettlementPrice());
				productBranchList.add(branch);

				visitor.setCredentials(order.getUser().getIdCard());
				visitor.setCredentialsType(Constant.CERTIFICATE_TYPE.ID_CARD.name());
				visitor.setMobile(order.getUser().getMobile());
				visitor.setName(order.getUser().getName());
				visitCustomerList.add(visitor);

				orderInfo.setContactPerson(contactPerson);
				orderInfo.setProductBranchList(productBranchList);
				orderInfo.setVisitCustomerList(visitCustomerList);

				paramMap.put("orderInfo", orderInfo);
				paramMap.put("prodProduct", prodProduct);
				paramMap.put("distributorInfo", distributorInfo);
				resultMap = this.createOrder(paramMap);
			}
			if ("100".equals(resultMap.get("errorNo"))) {
				OrdOrder ordOrder = (OrdOrder) resultMap.get("ordOrder");
				orderInfo.setStatus(ordOrder.getOrderStatus());
				orderInfo.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.name());
				orderInfo.setOrderId(ordOrder.getOrderId());

				paramMap.put("orderInfo", orderInfo);
				resultMap = this.updateOrderStatus(paramMap);
				resultMap.put("ordOrder", ordOrder);
			}
		} else {
			resultMap.put("errorNo", "216");
		}
		return resultMap;
	}

	/**
	 * 获取分销景区
	 * 
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	private List<Place> getDistributionProductPlaces(Long startRow, Long endRow) {
		DistributorInfo distributorInfo = new DistributorInfo();
		Map<String, Object> pageMap = new HashMap<String, Object>();
		distributorInfo = distributionCommonService.getDistributorByCode(Constant.DISTRIBUTOR.JINGDONG.name());
		pageMap.put("distributorId", distributorInfo.getDistributorInfoId());
		pageMap.put("_startRow", startRow);
		pageMap.put("_endRow", endRow);
		List<Place> places = distributionCommonService.getDistributionProductPlace(pageMap);
		return places;
	}

	/**
	 * 获取京东分销产品
	 * 
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	private List<DistributionProduct> getDistributionPro(Long startRow, Long endRow) {
		DistributorInfo distributorInfo = new DistributorInfo();
		Map<String, Object> pageMap = new HashMap<String, Object>();
		distributorInfo = distributionCommonService.getDistributorByCode(Constant.DISTRIBUTOR.JINGDONG.name());
		pageMap.put("distributorInfoId", distributorInfo.getDistributorInfoId());
		pageMap.put("start", startRow);
		pageMap.put("end", endRow);
		List<DistributionProduct> products = distributionCommonService.getDistributionProduct(pageMap);
		return products;
	}

	/**
	 * 获取景区数量
	 * 
	 * @return
	 */
	private Long getDistributionProductPlaceCount() {
		DistributorInfo distributorInfo = new DistributorInfo();
		Map<String, Object> pageMap = new HashMap<String, Object>();
		distributorInfo = distributionCommonService.getDistributorByCode(Constant.DISTRIBUTOR.JINGDONG.name());
		pageMap.put("distributorId", distributorInfo.getDistributorInfoId());
		Long count = distributionCommonService.getDistributionProductPlaceCount(pageMap);
		return count;
	}

	/**
	 * 获取京东分销的产品数量
	 * 
	 * @return
	 */
	private Long getDistributionProCount() {
		DistributorInfo distributorInfo = new DistributorInfo();
		Map<String, Object> pageMap = new HashMap<String, Object>();
		distributorInfo = distributionCommonService.getDistributorByCode(Constant.DISTRIBUTOR.JINGDONG.name());
		pageMap.put("distributorInfoId", distributorInfo.getDistributorInfoId());
		Long count = distributionCommonService.getDistributionProductCount(pageMap);
		return count;
	}
	/**
	 * 更新订单状态
	 * @param paramMap
	 * @return
	 */
	private Map<String, Object> updateOrderStatus(Map<String, Object> paramMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OrderInfo orderInfo = (OrderInfo) paramMap.get("orderInfo");
		DistributorInfo distributorInfo = (DistributorInfo) paramMap.get("distributorInfo");
		String errorNo = null;
		if (Constant.PAYMENT_STATUS.PAYED.name().equals(orderInfo.getPaymentStatus()) && Constant.ORDER_STATUS.NORMAL.name().equals(orderInfo.getStatus())
				|| Constant.ORDER_STATUS.CANCEL.name().equals(orderInfo.getStatus())) {
			Map<String, Object> map = this.doUpdate(orderInfo, distributorInfo);
			errorNo = (String) map.get("errorNo");
		} else {
			errorNo = "122";// 无效订单状态参数,无法更新状态
		}
		resultMap.put("errorNo", errorNo);
		return resultMap;
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
	private Map<String, Object> doUpdate(OrderInfo orderInfo, DistributorInfo distributorInfo) {
		String errorNo = "100";
		Map<String, Object> map = new HashMap<String, Object>();
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderInfo.getOrderId());
		if (Constant.ORDER_STATUS.NORMAL.name().equals(orderInfo.getStatus())) {
			if (Constant.PAYMENT_STATUS.PAYED.name().equals(ordOrder.getPaymentStatus())) {
				errorNo = "116";// 订单已经支付,不能重复支付
			} else if (Constant.ORDER_STATUS.CANCEL.name().equals(ordOrder.getOrderStatus())) {
				errorNo = "117";// 订单已经取消，不能进行支付
			} else if (Constant.PAYMENT_STATUS.PAYED.name().equals(orderInfo.getPaymentStatus())
					&& Constant.PAYMENT_STATUS.UNPAY.name().equals(ordOrder.getPaymentStatus())) {
				if (!this.orderPayment(ordOrder.getOrderId(), distributorInfo, orderInfo)) {
					errorNo = "118";//订单支付失败，请重新支付
				}
			}
		} else if (Constant.ORDER_STATUS.CANCEL.name().equals(orderInfo.getStatus()) && Constant.ORDER_STATUS.NORMAL.name().equals(ordOrder.getOrderStatus())) {
			Date date = ordOrder.getCancelTime() == null ? ordOrder.getVisitTime() : ordOrder.getCancelTime();
			if (!new Date().after(date)) {
				if (!this.cancelOrder(ordOrder.getOrderId(), distributorInfo)) {
					errorNo = "119";// 取消订单失败
				}
			} else {
				// 订单已经过了最晚取消时间不能取消
				errorNo = "120";
			}
		} else if (Constant.ORDER_STATUS.CANCEL.name().equals(ordOrder.getOrderStatus())) {
			errorNo = "121";// 订单已经取消不能再取消
		}
		map.put("errorNo", errorNo);
		return map;
	}
	
	/**
	 * 订单支付
	 * 
	 * @param orderId
	 * @return
	 */
	private boolean orderPayment(Long orderId, DistributorInfo distributorInfo ,OrderInfo orderInfo) {
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
			payPayment.setGatewayTradeNo(orderInfo.getPaymentSerialno());
			payPayment.setPaymentTradeNo(orderInfo.getBankOrderId());
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
	 * <h1>创建订单</h1>
	 * @param paramMap
	 * 参数集:<br/>
	 * 1.分销商订单信息(产品类别列表,联系人,游玩人,邮寄人):orderInfo;<br/>
	 * 2.驴妈妈销售产品信息:prodProduct;
	 * <hr/>
	 * @return resultMap
	 * 结果集:<br/>
	 * 1.错误号:errorNo;<br/>
	 * 2.错误描述:describe;<br/>
	 * 3.驴妈妈订单信息:ordOrder
	 */
	private Map<String, Object> createOrder(Map<String, Object> paramMap) {
		String errorNo = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OrderInfo orderInfo = (OrderInfo) paramMap.get("orderInfo");
		ProdProduct product = (ProdProduct) paramMap.get("prodProduct");
		DistributorInfo distributorInfo = (DistributorInfo) paramMap.get("distributorInfo");
		List<ProductBranch> branchList = orderInfo.getProductBranchList();
		ContactPerson contactPerson = orderInfo.getContactPerson();
		Long productId = product.getProductId();
		BuyInfo buyInfo = new BuyInfo();
		OrdOrder ordOrder = null;
		String mobileNumber = contactPerson.getMobile();
		// 注册用户
		resultMap = rigestUser(mobileNumber, buyInfo, resultMap);
		errorNo = (String) resultMap.get("errorNo");
		if (errorNo == null) {
			// 创建联系人列表
			createContacts(orderInfo ,buyInfo.getUserId());
			List<Item> itemList = new ArrayList<Item>();
				for (int i = 0; i < branchList.size(); i++) {
					ProductBranch branch = branchList.get(i);
					Long branchId = branch.getBranchId();
					Long quan = branch.getQuantity().longValue();
					Date visitDate = branch.getVisitDate();
					float price=Float.valueOf(branch.getSellPrice());
					// 验证订单信息
					resultMap= this.validateOrderInfo(branch,resultMap);
					errorNo = (String)resultMap.get("errorNo");
					if (errorNo != null) {
						break;
					}
					Item item = new Item();
					item.setProductId(productId);
					item.setVisitTime(visitDate);
					item.setQuantity(quan.intValue());
					item.setProductBranchId(branchId);
					// 单房型，需设置每天时间价格信息
					if (Constant.PRODUCT_TYPE.HOTEL.name().equals(product.getProductType()) && Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(product.getSubProductType())) {
						resultMap = this.createSingleRoom(branch, product, item, i, quan, resultMap);
						errorNo = (String) resultMap.get("errorNo");
						if (errorNo != null) {
							break;
						}
					} else {
						TimePrice timePrice = prodProductService.calcProdTimePrice(item.getProductBranchId(), item.getVisitTime(), false);
						if (timePrice == null) {
							String msg = DateUtil.getFormatDate(visitDate, "yyyy-MM-dd") + "当前日期不能预订，请选择其它时间";
							errorNo="101";
							resultMap.put("errorNo", errorNo);
							log.info("describe:"+msg);
							break;
						} else {
							boolean productSellable = prodProductService.isProductSellable(branchId, quan, visitDate);
							float ratePrice = timePrice.getPrice()*0.97f;
							if(ratePrice != price){
								String msg = DateUtil.getFormatDate(visitDate, "yyyy-MM-dd") + "当前日期的价格不符，请检查价格";
								errorNo="101";
								resultMap.put("errorNo", errorNo);
								log.info("describe:"+msg);
							}else if(!productSellable){
								String msg = DateUtil.getFormatDate(visitDate, "yyyy-MM-dd") + "当前库存不足，请选择其他日期";
								errorNo="102";
								resultMap.put("errorNo", errorNo);
								log.info("describe:"+msg);
								break;
							}
						}
					}
					if (i == 0) {
						item.setIsDefault("true");
					}
					itemList.add(item);
				}
				if (errorNo == null) {
					buyInfo.setChannel(distributorInfo.getChannelCode()); // 分销渠道
					buyInfo.setItemList(itemList);
					buyInfo.setPersonList(createPersonList(orderInfo, buyInfo.getUserId()));
					String payTarget = Constant.PAYMENT_TARGET.TOLVMAMA.name();
					// 支付对象
					if (product.isPaymentToLvmama()) {
						payTarget = Constant.PAYMENT_TARGET.TOLVMAMA.name();
						buyInfo.setResourceConfirmStatus(Boolean.TRUE.toString());
					} else if (product.isPaymentToSupplier()) {
						payTarget = Constant.PAYMENT_TARGET.TOSUPPLIER.name();
					}
					buyInfo.setPaymentTarget(payTarget);
					buyInfo.setWaitPayment(Constant.WAIT_PAYMENT.PW_DEFAULT.getValue());
					ResultHandle handle = orderServiceProxy
							.checkOrderStock(buyInfo);
					if (handle.isFail()) {
						errorNo = "1006";
					}else{
						ordOrder = orderServiceProxy.createOrder(buyInfo);
					}
				}
		}
		if (ordOrder != null) {
			String serialNo = DateUtil.getFormatDate(new Date(), "yyyyMMdd") + distributionOrderService.getSerialNo();
			OrdOrderDistribution ordOrderDitribution = new OrdOrderDistribution();
			ordOrderDitribution.setOrderId(ordOrder.getOrderId());
			ordOrderDitribution.setDistributionInfoId(distributorInfo.getDistributorInfoId());
			ordOrderDitribution.setSerialNo(serialNo);
			ordOrderDitribution.setPartnerOrderId(orderInfo.getPartnerOrderId());
			distributionOrderService.insertOrdOrderDistribution(ordOrderDitribution);
			resultMap.put("ordOrder",ordOrder );
			errorNo="100";
			resultMap.put("errorNo", errorNo);
		}
		return resultMap;
	}
	
	/**
	 * 创建多个联系人
	 */
	
	private void createContacts(OrderInfo orderInfo, String userId) {
		// 创建多个联系人
		List<UsrReceivers> receiversList = new ArrayList<UsrReceivers>();
		UsrReceivers receiverContacts = new UsrReceivers();
		ContactPerson contactPerson = orderInfo.getContactPerson();
		receiverContacts.setCardNum(contactPerson.getCredentials());
		receiverContacts.setCardType(contactPerson.getCredentialsType());
		receiverContacts.setMobileNumber(contactPerson.getMobile());
		receiverContacts.setReceiverName(contactPerson.getName());
		receiversList.add(receiverContacts);
		// 游客信息
		List<VisitCustomer> customerList = orderInfo.getVisitCustomerList();
		for (VisitCustomer customer : customerList) {
			UsrReceivers receiverVisitCustomer = new UsrReceivers();
			receiverVisitCustomer.setCardNum(customer.getCredentials());
			receiverVisitCustomer.setCardType(customer.getCredentialsType());
			receiverVisitCustomer.setMobileNumber(customer.getMobile());
			receiverVisitCustomer.setReceiverName(customer.getName());
			receiversList.add(receiverVisitCustomer);
		}
		//邮寄人
		PostPerson postPerson = orderInfo.getPostPerson();;
		if (postPerson != null) {
			UsrReceivers receiverPoster = new UsrReceivers();
			receiverPoster.setCardNum(postPerson.getCredentials());
			receiverPoster.setCardType(postPerson.getCredentialsType());
			receiverPoster.setMobileNumber(postPerson.getMobile());
			receiverPoster.setReceiverName(postPerson.getName());
			receiversList.add(receiverPoster);
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
	private Map<String,Object> rigestUser(String mobileNumber, BuyInfo buyInfo,Map<String,Object> resultMap) {
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
					resultMap.put("errorNo", "111");
					log.info("describe:用户注册出错！");
				}
			} else {
				resultMap.put("errorNo", "112");
				log.info("describe:手机号不合法！");
			}
		} else {
			resultMap.put("errorNo", "113");
			log.info("describe:手机号为空！");
		}
		return resultMap;
	}

	/**
	 * 创建订单联系人列表
	 */
	private List<Person> createPersonList(OrderInfo orderInfo, String userId) {
		ContactPerson contactPerson = orderInfo.getContactPerson();
		// 取票人信息
		List<UsrReceivers> receList = receiverUserService.loadUserReceiversByUserId(userId);
		String receiverId = getReceiverId(receList, contactPerson.getName(), contactPerson.getMobile(), contactPerson.getCredentials());
		List<Person> personList = new ArrayList<Person>();
		Person contact = new Person();
		contact.setCertNo(contactPerson.getCredentials());
		contact.setCertType(contactPerson.getCredentialsType());
		contact.setMobile(contactPerson.getMobile());
		contact.setName(contactPerson.getName());
		contact.setPersonType(Constant.ORD_PERSON_TYPE.CONTACT.name());
		contact.setReceiverId(receiverId);
		personList.add(contact);
		// 预订人
		Person booker = new Person();
		booker.setCertNo(contactPerson.getCredentials());
		booker.setCertType(contactPerson.getCredentialsType());
		booker.setMobile(contactPerson.getMobile());
		booker.setName(contactPerson.getName());
		booker.setPersonType(Constant.ORD_PERSON_TYPE.BOOKER.name());
		booker.setReceiverId(receiverId);
		personList.add(booker);
		
		//邮寄人
		PostPerson postperson = orderInfo.getPostPerson();//orderInfo.getPostPerson();
		if (postperson != null) {
			Person poster = new Person();
			poster.setCertNo(postperson.getCredentials());
			poster.setCertType(postperson.getCredentialsType());
			poster.setMobile(postperson.getMobile());
			poster.setName(postperson.getName());
			poster.setPersonType(Constant.ORD_PERSON_TYPE.ADDRESS.name());
			poster.setReceiverId(getReceiverId(receList, postperson.getName(), postperson.getMobile(), postperson.getCredentials()));
			personList.add(poster);
		}
		
		// 游客信息
		List<VisitCustomer> pList = orderInfo.getVisitCustomerList();
		if(pList!=null){
			for (int j = 0; j < pList.size(); j++) {
				VisitCustomer pp = pList.get(j);
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
	private Map<String, Object> validateOrderInfo(ProductBranch branch, Map<String, Object> resultMap) {
		Integer quantity = branch.getQuantity();
		Date visitDate = branch.getVisitDate();
		Long branchId = branch.getBranchId();
		ProdProductBranch prodProductBranch = prodProductBranchService.selectProdProductBranchByPK(branchId);
		if (prodProductBranch == null) {
			resultMap.put("errorNo", "106");
			log.info("describe:没有此产品类别！");
			return resultMap;
		}
		if (quantity <= 0) {
			resultMap.put("errorNo", "107");
			log.info("describe:选购产品数量<=0！");
			return resultMap;
		}
		if (quantity > prodProductBranch.getMaximum()) {
			resultMap.put("errorNo", "108");
			log.info("describe:订购数量超过最大可售数");
			return resultMap;
		}
		if (quantity < prodProductBranch.getMinimum()) {
			resultMap.put("errorNo", "109");
			log.info("describe:订购数量小于最小订购数");
			return resultMap;
		}
		if (visitDate == null && "".equals(visitDate)) {
			resultMap.put("errorNo", "110");
			log.info("describe:游玩日期为空!");
			return resultMap;
		}
		return resultMap;
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
	private Map<String,Object> createSingleRoom(ProductBranch branch, ProdProduct product, Item item, int addDay, Long quan,Map<String,Object> resultMap) {
		String msg = null;
		Date visitDate = branch.getVisitDate();
		Long branchId = branch.getBranchId();
		Date endDate = branch.getLeaveDate();
		if (endDate == null && "".equals(endDate)) {
			resultMap.put("errorNo", "103");
			log.info("describe:离店日期为空！");
			return resultMap;
		}
		try {
			if (visitDate.compareTo(endDate) == 0 || visitDate.after(endDate)) {
				resultMap.put("errorNo", "104");
				log.info("describe:离店日期必须大于入住日期");
			}
			List<OrdTimeInfo> timeInfoList = new ArrayList<OrdTimeInfo>();
			// 入住天数
			int days = DateUtil.getDaysBetween(visitDate, endDate);
			// 计算出每天的价格、库存信息
			for (int j = 0; j < days; j++) {
				Date d = DateUtil.dsDay_Date(visitDate, addDay);
				// 检验库存
				boolean productSellable = prodProductService.isProductSellable(branchId, quan, d);
				if (!productSellable) {
					msg = DateUtil.getFormatDate(d, "yyyy-MM-dd") + "当前库存不足，请选择其他日期";
					resultMap.put("errorNo", 102);
					log.info("describe:"+msg);
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
			if (msg != null) {
				return resultMap;
			}
			item.setTimeInfoList(timeInfoList);
			// 单房型数量为天数*用户选择的数量
			item.setQuantity(quan.intValue() * timeInfoList.size());
		} catch (Exception e) {
			log.error("createSingleRoom", e);
			resultMap.put("errorNo", "105");
			log.info("describe:离店日期格式错误！");
			return resultMap;
		}
		return resultMap;
	}
	
	
	private String buildQueryDailyPriceXml(String errorNo, DistributionProduct disproduct, String messageId) {
		Map<String, Object> msgMap = errorCheck(errorNo, "");
		String dealTime = DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmssSSS");
		boolean isSuccess = (Boolean) msgMap.get("isSuccess");
		String errorCode = (String) msgMap.get("errorCode");
		String errorMsg = (String) msgMap.get("errorMsg");
		Result result = null;
		if (disproduct != null && isSuccess) {
			Product product = new Product(disproduct);
			result = new Result(isSuccess + "", errorCode, errorMsg, dealTime, product);
		} else {
			Product product = new Product(disproduct);
			product.setState("2");
			result = new Result(isSuccess + "", errorCode, errorMsg, dealTime, product);
		}
		String version = DistributionUtil.getPropertiesByKey("jingdong.version");
		Head head = new Head(version , messageId);
		Body body = new Body(result);
		Response res = new Response(head, body);
		String resXML = res.buildQueryDailyPricesToXml();
		return resXML;
	}

	
	private Head fillHeadBean() {
		String dealTime = DateUtil.getDateTime("yyyyMMddHHmmssSSS", new Date());
		String version = DistributionUtil.getPropertiesByKey("jingdong.version");
		String partnerCode = DistributionUtil.getPropertiesByKey("jingdong.partnerCode");
		String proxyId = DistributionUtil.getPropertiesByKey("jingdong.proxyId");
		Head head = new Head(version, dealTime, partnerCode, proxyId , dealTime);
		return head;
	}
	
	private String buildUpdateDailyPriceXml(DistributionProduct disproduct) {
		Product product = new Product(disproduct);
		Head head = fillHeadBean();
		Body body = new Body(product);
		Request request = new Request(head, body);
		String msg;
		msg = request.buildDailyPriceToXml();
		return msg;
	}

	private String buildOnOffLineProductXml(DistributionProduct disproduct) {
		Head head = fillHeadBean();
		Product product = new Product(disproduct);
		Body body = new Body(product);
		Request request = new Request(head, body);
		String msg = request.buildOnOffLineToXml();
		return msg;
	}

	private String buildOnOffLineBranchXml(DistributionProduct disproduct,
			ProdProductBranch prodBranch) {
		Head head = fillHeadBean();
		Product product = new Product(disproduct, prodBranch);
		Body body = new Body(product);
		Request request = new Request(head, body);
		String msg = request.buildOnOffLineToXml();
		return msg;
		//log.info("onOffLineProduct requestXml" + msg);
	}
	
	
	private String buildUpdateProductXml(DistributionProduct disproduct) {
		Product product = new Product(disproduct);
		Head head = fillHeadBean();
		Body body = new Body(product);//
		Request request = new Request(head, body);
		String msg = request.buildUpdateProductToXml();
		return msg;
	}
	
	
	private String buildRefundXml(DistributionOrderRefund refundHistory,
			Head head) {
		Order order=new Order(refundHistory);
		Body body = new Body(order);
		Request request = new Request(head, body);
		String msg = request.buildApplyRefundToXml();
		return msg;
	}
	
	private String buildAddDalyPricesXml(DistributionProduct disproduct) {
		Product product = new Product(disproduct);
		Head head = fillHeadBean();
		Body body = new Body(product);//
		Request request = new Request(head, body);
		String msg = request.buildDailyPriceToXml();
		return msg;
	}

	
	private String buildUpdateResourceXml(Place place) {
		Head head = fillHeadBean();
		Resource resource = new Resource(place);
		Body body = new Body(resource);
		Request request = new Request(head, body);
		String msg = request.buildUpdateResourceToXml();
		return msg;
	}
	
	private String buildAddResourceXml(Place place) {
		Head head = fillHeadBean();
		Resource resource = new Resource(place);
		Body body = new Body(resource);
		Request request = new Request(head, body);
		String msg = request.buildResourceToXml();
		return msg;
	}

	
	private String buildaddProductXml(DistributionProduct disproduct) {
		Product product = new Product(disproduct);
		Head head = fillHeadBean();
		Body body = new Body(product);
		Request request = new Request(head, body);
		String msg = request.buildProductToXml();
		return msg;
	}
	
	private String pushXml(String cmd, String msg) {
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("cmd", cmd);
		reqMap.put("msg", msg);
		String url=DistributionUtil.getPropertiesByKey("jingdong.url");
		return HttpsUtil.requestPostForm(url, reqMap);
	}
	
	
	private Map<String, Object> initErrorMap() {
		Map<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("200", "必要元素为空");
		errorMap.put("201", "参数不正确");
		errorMap.put("202", "合作伙伴id不正确");
		errorMap.put("203", "消息签名不一致");
		errorMap.put("208", "失败id");
		errorMap.put("209", "id不存在");
		errorMap.put("216", "下单失败,重复下单");
		errorMap.put("217", "下单失败，其他原因");
		errorMap.put("218", "不同意退票");
		errorMap.put("219", "库存不足");
		errorMap.put("220", "没有此订单 ");
		errorMap.put("221", "订单状态不正常，不能进行重发凭证");
		return errorMap;
	}

	private Map<String, String> initErrorNoMap() {
		Map<String, String> errorNoMap = new HashMap<String, String>();
		errorNoMap.put("101", "201");
		errorNoMap.put("102", "219");
		errorNoMap.put("103", "200");
		errorNoMap.put("104", "201");
		errorNoMap.put("105", "201");
		errorNoMap.put("106", "201");
		errorNoMap.put("107", "201");
		errorNoMap.put("108", "201");
		errorNoMap.put("109", "201");
		errorNoMap.put("110", "200");
		errorNoMap.put("111", "217");
		errorNoMap.put("112", "217");
		errorNoMap.put("113", "200");
		errorNoMap.put("114", "220");
		errorNoMap.put("115", "221");
		errorNoMap.put("116", "217");
		errorNoMap.put("117", "217");
		errorNoMap.put("118", "217");
		errorNoMap.put("119", "217");
		errorNoMap.put("121", "217");
		errorNoMap.put("200", "221");
		errorNoMap.put("216", "216");
		errorNoMap.put("217", "217");
		errorNoMap.put("201", "201");
		errorNoMap.put("209", "209");
		errorNoMap.put("202", "202");
		errorNoMap.put("203", "203");
		return errorNoMap;
	}

	/**
	 * 错误转化
	 * 
	 * @param errorNo
	 * @param describe
	 * @return
	 */
	private Map<String, Object> errorCheck(String errorNo, String describe) {
		boolean isSuccess = false;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (SUCCESS.equals(errorNo) || errorNo == null) {
			isSuccess = true;
		} else {
			Map<String, Object> errorMap = initErrorMap();
			Map<String, String> errorNoMap = initErrorNoMap();
			String errorNoForJingdong = errorNoMap.get(errorNo);
			String describeForJingdong = (String) errorMap.get(errorNoForJingdong);
			resultMap.put("errorCode", errorNoForJingdong);
			resultMap.put("errorMsg", describeForJingdong);
		}
		log.info("DistributionForJingdongMessage No:" + errorNo + "describe:" + describe);
		resultMap.put("isSuccess", isSuccess);
		return resultMap;
	}

	/**
	 * 检查签名 Base64(MD5(key+messageId+PartnerCode+timeStamp+orderid))
	 * 
	 * @param request
	 * @return
	 */
	private boolean checkSign(Request request, String signStr) {
		boolean isSuccess = true;
		Head head=request.getHead();
		String sign = head.getSigned();
		String jingdongSign = JdUtil.getSigned(signStr);
		log.info("SignStr:"+signStr+"\n sign:"+jingdongSign);
		if (!jingdongSign.equals(sign)) {
			isSuccess = false;
		}
		return isSuccess;
	}
	
	/**
	 * 计算总页数
	 * @param proSum
	 * @return
	 */
	private Long getPagesSum(Long proSum) {
		return proSum % pageSize > 0 ? (proSum / pageSize) + 1 : proSum / pageSize;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public DistributionProductService getDistributionProductService() {
		return distributionProductService;
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
