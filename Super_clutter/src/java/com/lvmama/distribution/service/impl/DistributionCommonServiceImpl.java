package com.lvmama.distribution.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.distribution.DistributionMessage;
import com.lvmama.comm.bee.po.distribution.DistributionOrderRefund;
import com.lvmama.comm.bee.po.distribution.DistributionProduct;
import com.lvmama.comm.bee.po.distribution.DistributionProductCategory;
import com.lvmama.comm.bee.po.distribution.DistributorInfo;
import com.lvmama.comm.bee.po.distribution.OrdOrderDistribution;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassEvent;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.po.pass.PassPortLog;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.com.ISmsHistoryService;
import com.lvmama.comm.bee.service.distribution.DistributionOrderService;
import com.lvmama.comm.bee.service.distribution.DistributionProductService;
import com.lvmama.comm.bee.service.distribution.DistributionService;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.PerformDetail;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdProductChannel;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.vo.Constant;
import com.lvmama.distribution.client.WebServiceClient;
import com.lvmama.distribution.model.lv.Order;
import com.lvmama.distribution.model.lv.Product;
import com.lvmama.distribution.model.lv.Response;
import com.lvmama.distribution.model.lv.ResponseHead;
import com.lvmama.distribution.service.DistributionCommonService;
import com.lvmama.distribution.service.impl.DistributionPushService.EVENT_TYPE;
import com.lvmama.distribution.service.impl.DistributionPushService.OBJECT_TYPE;
import com.lvmama.distribution.service.impl.DistributionPushService.PUSH_STATUS;
import com.lvmama.distribution.util.DistributionUtil;

public class DistributionCommonServiceImpl implements DistributionCommonService {
	private static final Log log = LogFactory.getLog(DistributionCommonServiceImpl.class);
	private static final String ORDER = "ORD_ORDER";
	private static final String ORDER_ITEM = "ORD_ORDER_ITEM_META";
	/** 订单服务 */
	private OrderService orderServiceProxy;

	private ComPictureService comPictureService;
	private DistributionService distributionService;
	private DistributionProductService distributionProductService;
	/** 分销订单服务*/
	private DistributionOrderService distributionOrderService;
	private PassCodeService passCodeService;
	private ISmsHistoryService smsHistoryService;
	private ProdProductService prodProductService;
	private MetaProductBranchService metaProductBranchService;
	private ComLogService comLogService;

	private PerformTargetService performTargetService;
	
	private void addProduct(Long distributorId, Map<String, Object> params) {
		List<ProdProductBranch> productBranchs=distributionProductService.selectProdCanDistributeByProdType(params);
		if(!productBranchs.isEmpty() && productBranchs.get(0) != null){
			//不是附加的且是默认产品类别只有一个
			ProdProductBranch productBranch = (ProdProductBranch)productBranchs.get(0);
			Long branchId=productBranch.getProdBranchId();
			Long productId=productBranch.getProductId();
			Map<String,Object> paramsMap = new HashMap<String,Object>();
			paramsMap.put("distributorInfoId", distributorId);
			paramsMap.put("productBranchId", branchId);
			Boolean flag = distributionProductService.selectDistributionProductBranchList(paramsMap); 
			if(!flag){
				distributionProductService.saveDistributionProduct(distributorId,productId,branchId);
				StringBuilder content = new StringBuilder("");
				content.append(" 分销产品添加 分销商id:");
				content.append(distributorId);
				content.append(",产品id:");
				content.append(productId);
				content.append(",产品类别id:");
				content.append(branchId);
				comLogService.insert("DISTRIBUTOR_PRODUCT", null, branchId, "SYSTEM", "DISTRIBUTOR_PRODUCT_ADD", "自动添加分销产品", content.toString(), null);
			}
		}
	}
	
	
	/**
	 * 更新为自动更新返现金额
	 * @param distributorId
	 * @param productId
	 * @param branchId
	 */
	private void addCash(Long productId){
		
		DistributorInfo distrorbor = distributionService.selectByDistributorCode(Constant.DISTRIBUTOR.QUNA_TICKET.name());
		if(distrorbor==null){
			return ;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		params.put("distributorInfoId", distrorbor.getDistributorInfoId());
		params.put("volid", "true");
		List<DistributionProduct> list = distributionProductService.selectAllByParams(params);
		if(list==null || list.size()<1){
			return ;
		}
		for(DistributionProduct distributionProduct:list){
			distributionProductService.autoUpdateCommentsCashback(distributionProduct);
			comLogService.insert("DISTRIBUTOR_PRODUCT", null, distributionProduct.getProductBranchId(), "system", "DISTRIBUTOR_PRODUCT_ADD", "更新分销产品", "产品更新时更新评论返现值", null);
		}
	}
	
	/**
	 * 给分销商添加符合条件的产品
	 */
	@Override
	public void addProductToDistributor(Long productId){
		if(this.checkMultiJourneyProduct(productId)){
			return;
		}
		
		List<DistributionProductCategory> distributeCategoryList=distributionService.selectAllDistributionProdCategory();
		for(DistributionProductCategory distributeCategory : distributeCategoryList){
			Map<String,Object> params=new HashMap<String, Object>();
			Long distributorId = distributeCategory.getDistributorInfoId();
			DistributorInfo di = distributionService.selectByDistributorId(distributorId);
			if(!di.isAddNewprod()) {
				continue;
			}
			params.put("productType", distributeCategory.getProductType());
			params.put("productId", productId);
			if(distributeCategory.isTicket()){
				if(distributeCategory.getPayOnline().equals("true")){
					params.put("paytolvmama", "true");
				}
			}else{
				params.put("subProductType", distributeCategory.getSubProductType());
			}
			addProduct(distributorId, params);
		}
		addCash(productId);
	}
	
	/**
	 * job定时推送下线
	 * @param distributionMessage
	 */
	public void pushOffLine(DistributionMessage distributionMessage) {
		String partnerCode = distributionMessage.getDistributorChannel().split(",")[1];
		String key = distributionMessage.getDistributorChannel().split(",")[2];
		Long branchId = distributionMessage.getObjectId();
		String signed=key+branchId;
		try {
			signed=MD5.encode(signed);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String body = "<branchId>"+branchId+"</branchId>";
		log.info("Disribution offLine push branchId: " +branchId +"partnerCode:"+partnerCode);
		this.push(partnerCode ,body ,signed ,distributionMessage);
	}
	
	
	/**
	 * job定时推送产品
	 * @param distributionMessage
	 */
	public void pushProduct(DistributionMessage distributionMessage) {
		String distributorId = distributionMessage.getDistributorChannel().split(",")[0];
		String partnerCode = distributionMessage.getDistributorChannel().split(",")[1];
		String key = distributionMessage.getDistributorChannel().split(",")[2];
		Long productId = distributionMessage.getObjectId();
		String signed=key+productId;
		
		try {
			signed=MD5.encode(signed);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		Map<String , Object> params=new HashMap<String , Object>();
		params.put("productId", productId);
		params.put("distributorInfoId", Long.valueOf(distributorId));
		List<DistributionProduct> distributionProductList = this.distributionProductService.getDistributionProductTimePriceList(params);
		Product product = new Product();
		if (distributionProductList != null && !distributionProductList.isEmpty()) {
			product = new Product(distributionProductList.get(0));
			String body=null;
			DistributorInfo distributor = distributionService.selectByDistributorId(Long.valueOf(distributorId));
			if(distributor!=null && Constant.DISTRIBUTOR.QUNA_TICKET.getCode().equalsIgnoreCase(distributor.getDistributorCode())){
				body=product.buildForPushProductByQunar();
			}else{
				body=product.buildForPushProduct();
			}
			log.info("body"+body);
			
			log.info("Disribution product push productId: " +productId +"partnerCode:"+partnerCode );
			this.push(partnerCode, body, signed,distributionMessage);
		}
	}
	
	@Override
	public void pushOrder(Long orderId){
		OrdOrder ordOrder=orderServiceProxy.queryOrdOrderByOrderId(orderId);
		String distributorChannel=ordOrder.getChannel();
		DistributorInfo distributor=this.distributionService.selectByDistributorChannel(distributorChannel);
		if(distributor != null && distributor.isPushUpdate()){
			Long distributorId=distributor.getDistributorInfoId();
			OrdOrderDistribution ordOrderDistribution = distributionOrderService.selectByOrderIdAndDistributionInfoId(orderId, distributorId);
			Order order = new Order(ordOrderDistribution.getOrderId().toString(), ordOrder.getOrderStatus() , ordOrder.getApproveStatus() , ordOrder.getPaymentStatus(), getCredenctStatus(ordOrder),ordOrder.getZhWaitPayment(),ordOrder.getPerformStatus());
			String body = order.buildForPushOrder();
			String partnerCode=distributor.getDistributorCode();
			String signed=distributor.getDistributorKey()+orderId;
			try {
				signed=MD5.encode(signed);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			log.info("Disribution orderCancel push orderId: " +orderId +"partnerCode:"+partnerCode);
			DistributionMessage distributionMessage = new DistributionMessage();
			distributionMessage.setEventType(EVENT_TYPE.cancelorder.name());
			distributionMessage.setObjectType(OBJECT_TYPE.order.name());
			this.push(partnerCode, body, signed,distributionMessage);
		}
	}

	
	/**
	 * 推送产品、订单 给分销商
	 * @param partnerCode
	 * @param xmlStr
	 * @param signed
	 * @param eventType
	 */
	private void push(String partnerCode, String xmlStr, String signed,DistributionMessage distributionMessage){
		String eventType = distributionMessage.getEventType();
//		String objectType = distributionMessage.getObjectType() ;
		String xmlRequest=DistributionUtil.createRequest(partnerCode, xmlStr, signed);
		String urlStr="";
		try {
			if(!EVENT_TYPE.cancelorder.name().equals(eventType)){
				distributionMessage.setStatus(PUSH_STATUS.pushed.name());
				distributionService.saveOrUpdateDistributionMessage(distributionMessage);
			}
			if(Constant.DISTRIBUTOR.QUNA.name().equals(partnerCode)||Constant.DISTRIBUTOR.QUNA_TUAN.name().equals(partnerCode)){
					QunarPushServiceImpl qunarPush = new QunarPushServiceImpl();
				if(EVENT_TYPE.cancelorder.name().equals(eventType)){
					urlStr=DistributionUtil.getPropertiesByKey("qunaer.pushorder");
				}else{
					qunarPush.push(partnerCode, xmlStr, signed, eventType);
					return;
				}
			}else if (Constant.DISTRIBUTOR.QUNA_TICKET.name().equals(partnerCode)||Constant.DISTRIBUTOR.QUNA_TICKET_TUAN.name().equals(partnerCode)){
				if(EVENT_TYPE.cancelorder.name().equals(eventType)){
					urlStr=DistributionUtil.getPropertiesByKey("qunaerticket.pushorder");
				}else if(EVENT_TYPE.offLine.name().equals(eventType)){
					urlStr=DistributionUtil.getPropertiesByKey("qunaerticket.pushoffLine");
				}else{
					urlStr=DistributionUtil.getPropertiesByKey("qunaerticket.pushproduct");
				}
			}else {
				String urlKey ="";
				if(EVENT_TYPE.cancelorder.name().equals(eventType)){
					urlKey = partnerCode.toLowerCase()+".pushorder";
					urlStr=DistributionUtil.getPropertiesByKey(urlKey);
				}else if(EVENT_TYPE.offLine.name().equals(eventType)){
					urlKey = partnerCode.toLowerCase()+".pushoffLine";
					urlStr=DistributionUtil.getPropertiesByKey(urlKey);
				}else{
					urlKey = partnerCode.toLowerCase()+".pushproduct";
					urlStr=DistributionUtil.getPropertiesByKey(urlKey);
				}
			}
			log.info("xmlRequest url: "+urlStr );
			xmlRequest=DistributionUtil.encode(xmlRequest.getBytes("utf-8"));
			String result=HttpsUtil.requestPostXml(urlStr, xmlRequest);
			log.info("DistributionPush result:"+result);
		} catch (Exception e) {
			log.error("DistributionPush Exception distributor:"+partnerCode +"method:" +eventType ,e);
			if(!EVENT_TYPE.cancelorder.name().equals(eventType)){
				distributionMessage.setStatus(PUSH_STATUS.fail.name());
				distributionService.saveOrUpdateDistributionMessage(distributionMessage);
			}
			/*if(OBJECT_TYPE.product.name().equals(objectType)){
				reapply(distributionMessage);
			}*/
		}
	}
	
	/**
	 * 重试三次
	 * @param distributionMessage
	 * @param xmlRequest
	 * @param urlStr
	
	private void reapply(DistributionMessage distributionMessage) {
		Long times = distributionMessage.getReapplyTime();
		if (times < 3) {
			distributionMessage.setReapplyTime(times + 1);
			distributionMessage.setStatus(PUSH_STATUS.pushing.name());
		} else {
			distributionMessage.setStatus(PUSH_STATUS.fail.name());
		}
	}
	 */
	/**
	 * 根据条件取分销产品
	 */
	public List<DistributionProduct> getDistributionProduct(Map<String, Object> pageMap) {
		List<DistributionProduct> list = this.distributionProductService.getDistributionProductList(pageMap);
		for(DistributionProduct dp : list) {
			ViewPage vp = dp.getViewPage();
			if(vp != null) {
				vp.setPictureList(comPictureService.getPictureByPageId(vp.getPageId()));
			}
			if(dp.getProdProduct().isRoute()){
				for (ViewJourney vj : dp.getViewJourneyList()) {
					List<ComPicture> comPictureList = comPictureService.getPictureByObjectIdAndType(vj.getJourneyId(), "VIEW_JOURNEY");
					if (comPictureList != null && comPictureList.size() != 0) {
						vj.setJourneyPictureList(comPictureList);
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 取分销产品总数
	 * @param objectMap
	 * @return
	 */
	public Long getDistributionProductCount(Map<String, Object> pageMap) {
		return this.distributionProductService.getDistributionProductCount(pageMap);
	}
	
	/**
	 * 返回产品对应需要推送的分销商
	 * @param productId
	 * @return
	 */
	public List<DistributorInfo> getDistributorsByProductId(Long productId){
		return distributionService.selectWhiteListByProductIdAndProductBranchId(productId, null);
	}
	
	
	public DistributorInfo getDistributorById(Long distributorId){
		return distributionService.selectByDistributorId(distributorId);
	}
	
	public DistributorInfo getDistributorById(String distributorChannel){
		return distributionService.selectByDistributorChannel(distributorChannel);
	}

	
	/**
	 * http方式分销商退款
	 */
	@Override
	public boolean refundForHttp(DistributionOrderRefund refund, String url,Map<String,String> map) {
		boolean applyResult=false;
		try {
			String resStr=HttpsUtil.requestPostForm(url, map);
			log.info("refund url:"+url);
			if(resStr==null||(resStr!=null&&"".equals(resStr.trim()))){
				return false;
			}
			Response response=DistributionUtil.getRefundRes(resStr);
			ResponseHead head=response.getHead();
			applyResult=("1000".equals(head.getCode()))?true:false;
			log.info("applyOrderRefund message:"+head.getDescribe());
		} catch (Exception e) {
			applyResult=false;
			log.error("applyOrderRefund requestException: ",e);
		}
		if(applyResult){
			refundStatu(refund.getPartnerOrderId(),refund.getDistributionOrderRefundId());
		}
		return applyResult;
	}
	
	@Override
	public boolean refundForWebService(DistributionOrderRefund refund,String wsdl) {
		boolean applyResult=false;
		Order order=new Order(refund);
		String msg=order.buildForRefund();
		String signed=order.getRefundSigned();
		try {
			signed=MD5.encode(signed);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		msg=DistributionUtil.createRequest(refund.getDistributorCode(), msg, signed);
		log.info("applyOrderRefund requestXml: " + msg);
		WebServiceClient client= WebServiceClient.getClientInstance();
		log.info("refund wsdl:"+wsdl);
		try {
			Object [] responseXml=client.execute(wsdl, new Object[]{msg}, "refund");
			String resStr="";
			if(responseXml!=null&&responseXml.length>0){
				resStr=String.valueOf(responseXml[0]);
				log.info("applyOrderRefund message:"+resStr);
				Response response=DistributionUtil.getRefundRes(resStr);
				ResponseHead head=response.getHead();
				applyResult="1000".equals(head.getCode());
			}else{
				applyResult=false;
				log.info("applyOrderRefund message: result is null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			applyResult=false;
		}
		if(applyResult){
			refundStatu(refund.getPartnerOrderId(),refund.getDistributionOrderRefundId());
		}
		return applyResult;
	}
	/**
	 * 更新退款状态
	 */
	@Override
	public void refundStatu(String orderId,Long distributionOrderRefundId){
		distributionOrderService.updateRefundStatusByPartnerOrdid(orderId,distributionOrderRefundId);
	}

	/**
	 * 由服务重发短信
	 * @param codeId
	 * @return
	 */
	@Override
	public PassEvent resendByNotLvmama(Long codeId) {
		PassEvent event =  passCodeService.resend(codeId);
		return event;
	}

	/**
	 * 取得订单的凭证状态
	 * @param ordOrder
	 * @return
	 */
	@Override
	public String getCredenctStatus(OrdOrder ordOrder) {
		String result = null;
		if (ordOrder.isPassportOrder()) {// 二维码凭证
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("orderId", ordOrder.getOrderId());
			param.put("_endRow", 10);
			param.put("_startRow", 1);
			List<PassCode> codeList = passCodeService.queryPassCodes((param));
			if (codeList != null && !codeList.isEmpty()) {
				PassCode code = codeList.get(0);
				if ("SUCCESS".equals(code.getStatus())) {
					result = "CREDENCE_SEND";
				} else {
					result = "CREDENCE_NO_SEND";
				}
			}
		} else if (ordOrder.isNeedSendFax()) {// 传真凭证
			boolean isSentFax = this.distributionOrderService.isSentFax(ordOrder.getOrderId());
			if (isSentFax) {
				result = "CREDENCE_SEND";
			} else {
				result = "CREDENCE_NO_SEND";
			}
		} else {// 普通短信凭证
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("objectId", ordOrder.getOrderId());
			Integer smsCount = smsHistoryService.selectRowCount(params);
			if (smsCount != null && smsCount > 0) {
				result = "CREDENCE_SEND";
			} else {
				result = "CREDENCE_NO_SEND";
			}
		}
		return result;
	}
	
	
	
	/**
	 * 根据分销商帐号查询分销商
	 * @param partnerCode
	 * @return
	 */
	@Override
	public DistributorInfo getDistributorByCode(String partnerCode){
		return distributionService.selectByDistributorCode(partnerCode);
	}
	
	/**
	 * 根据分销商Id查询分销商Ip白名单
	 * @param distributorInfoId
	 * @return
	 */
	@Override
	public List<String> getDistributorIps(Long distributorInfoId){
		return distributionService.selectByDistributorInfoId(distributorInfoId);	
	}
	
	
	@Override
	public List<Place> getDistributionProductPlace(Map<String, Object> paramMap) {
		return this.distributionService.getDistributionProductPlace(paramMap);
	}

	@Override
	public Long getDistributionProductPlaceCount(Map<String, Object> paramMap) {
		return this.distributionService.getDistributionProductPlaceCount(paramMap);
	}
	
	@Override
	public void cancelDistributorByProdChannel(Long productId) {
		boolean cancelFlag = true;
		List<ProdProductChannel>  chanelsList = prodProductService.getProductChannelByProductId(productId);
		for (ProdProductChannel channel : chanelsList) {
			if ("FRONTEND".equals(channel.getProductChannel())) {
				cancelFlag = false;
				break;
			}
		}
		// 销售渠道没有驴妈妈前台的产品做取消分销操作
		if (cancelFlag) {
			distributionProductService.updateDistributionProductVolid(productId, "false");
		}
	}
	
	@Override
	public void cancelDistributorByMetaProdTarget(Long metaProductId,
			String payTarget) {
		Set<Long> rstSet = new HashSet<Long>();
		// 根据采购id获取销售产品
		List<MetaProductBranch> metaProductBranchList = metaProductBranchService
				.selectBranchListByProductId(metaProductId);
		for (MetaProductBranch metaProductBranch : metaProductBranchList) {
			List<ProdProduct> productList = prodProductService
					.selectProductByMetaBranchId(metaProductBranch
							.getMetaBranchId());
			for (ProdProduct product : productList) {
				rstSet.add(product.getProductId());
			}
		}
		Iterator<Long> it = rstSet.iterator();
		while (it.hasNext()) {
			distributionProductService.checkCancel(it.next(), payTarget);
		}
	}
	
	/**
	 * 判断分销的产品是否为多行程产品
	 * @param productId
	 * @return
	 */
	private boolean checkMultiJourneyProduct(Long productId){
		ProdRoute prodRoute =prodProductService.selectProdRouteByPrimaryKey(productId);
		if(prodRoute == null){
			return false;
		}
		return "Y".equalsIgnoreCase(prodRoute.getIsMultiJourney());
	}
	/**
	 * 保存分销推送消息
	 * @param distributionMessage
	 */
	@Override
	public void saveOrUpdateMessage(DistributionMessage distributionMessage){
		distributionService.saveOrUpdateDistributionMessage(distributionMessage);
	}
	
	/**
	 * 查询可推送消息列表
	 * @param distributionMessage
	 */
	@Override
	public List<DistributionMessage> queryMessageByMsgParams(DistributionMessage distributionMessage){
		return distributionService.getMessagesByMsgParams(distributionMessage);
	}
	
	
	/**
	 * 通过传递Map参数通关.
	 * @param map
	 */
	public void passByParam(Map<String, Long> map){
		PerformDetail performDetail =new PerformDetail();
		Long orderId = map.get("orderId");
		Long targetId = map.get("targetId");
		Long adultQuantity = map.get("adultQuantity");
		Long childQuantity = map.get("childQuantity");
		Long orderItemMetaId = map.get("orderItemMetaId");
		performDetail.setOrderId(orderId);
		performDetail.setTargetId(targetId);
		performDetail.setAdultQuantity(adultQuantity);
		performDetail.setChildQuantity(childQuantity);
		performDetail.setOrderItemMetaId(orderItemMetaId);
		
	}
	/**
	 * 执行通关动作.
	 * 
	 * @param performDetail
	 */
	public void pass(PerformDetail performDetail) {
		String msg="";
		SupPerformTarget supPerformTarget = this.performTargetService.getSupPerformTarget(performDetail.getTargetId());
		boolean flag = this.addPerform(performDetail.getOrderId(), performDetail.getTargetId(), performDetail.getAdultQuantity(), performDetail.getChildQuantity());
		if (Constant.CCERT_TYPE.DIMENSION.name().equals(supPerformTarget.getCertificateType())) {
			// 二维码信息更新
			List<Long> list = new ArrayList<Long>();
			list.add(performDetail.getOrderId());
			list.add(performDetail.getOrderItemMetaId());
			PassPortCode passPortCode = passCodeService.getPassPortCodeByObjectIdAndTargetId(list, performDetail.getTargetId());
			if (passPortCode != null) {
				passPortCode.setStatus(Constant.PASSCODE_USE_STATUS.USED.name());
				passPortCode.setUsedTime(new Date());
				// 更新通关点信息
				passCodeService.updatePassPortCode(passPortCode);
			}
		}
	}

	/**
	 * 添加履行信息
	 * 
	 * @param orderId
	 * @param targetId
	 * @param adultQuantity
	 * @param childQuantity
	 */
	private boolean addPerform(Long orderId, Long targetId, Long adultQuantity, Long childQuantity) {
		boolean flag = false;
		boolean suc = false;
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getMetaPerformRelate().setOrderId(orderId);
		compositeQuery.getMetaPerformRelate().setTargetId(String.valueOf(targetId));
		compositeQuery.getPageIndex().setBeginIndex(1);
		compositeQuery.getPageIndex().setEndIndex(1000000000);
		List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy
				.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
		int size = orderItemMetas.size();
		if (size > 1) {
			for(OrdOrderItemMeta meta : orderItemMetas){
				suc = orderServiceProxy.insertOrdPerform(Long.valueOf("targetId"), meta.getOrderItemMetaId(), ORDER_ITEM, meta.getTotalAdultQuantity(), meta.getTotalChildQuantity());
				if(!suc){
					break;
				}
			}
		} else {
			Long orderItemMetaId = orderItemMetas.get(0).getOrderItemMetaId();
			flag = orderServiceProxy.insertOrdPerform(targetId, orderItemMetaId, ORDER_ITEM, adultQuantity,
					childQuantity);
		}
		this.passLogs(orderId, "立式设备凭证打印", null);
		return flag;
	}
	/**
	 * 通关日志
	 * @param orderId
	 * @param content
	 * @param codeId
	 */
	private void passLogs(Long orderId,String content,Long codeId){
		ComLog log = new ComLog();
		log.setObjectType("PASS_CODE");
		log.setParentId(orderId);
		log.setOperatorName("SYSTEM");
		log.setLogType(Constant.COM_LOG_ORDER_EVENT.systemApprovePass.name());
		log.setLogName("设备刷码通关");
		log.setContent(content);
		comLogService.addComLog(log);
	}
	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}


	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}


	public void setSmsHistoryService(ISmsHistoryService smsHistoryService) {
		this.smsHistoryService = smsHistoryService;
	}


	public void setDistributionProductService(DistributionProductService distributionProductService) {
		this.distributionProductService = distributionProductService;
	}

	public void setDistributionOrderService(DistributionOrderService distributionOrderService) {
		this.distributionOrderService = distributionOrderService;
	}

	public void setDistributionService(DistributionService distributionService) {
		this.distributionService = distributionService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
	
	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
	
}