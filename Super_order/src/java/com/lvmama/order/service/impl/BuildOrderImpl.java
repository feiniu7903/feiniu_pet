package com.lvmama.order.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.meta.MetaProductTicket;
import com.lvmama.comm.bee.po.meta.MetaTravelCode;
import com.lvmama.comm.bee.po.ord.OrdExpress;
import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdInvoiceRelation;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.bee.po.ord.OrdOrderChannel;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaAperiodic;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdOrderRoute;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.ord.OrdUserOrder;
import com.lvmama.comm.bee.po.ord.OrderInfoDTO;
import com.lvmama.comm.bee.po.prod.ProdJourneyProduct;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductBranchItem;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.pub.ComAudit;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.OrderChannelInfo;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.mark.MarkCouponUsage;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.vo.favor.FavorProductResult;
import com.lvmama.comm.pet.vo.favor.FavorResult;
import com.lvmama.comm.pet.vo.favor.OrderFavorStrategy;
import com.lvmama.comm.pet.vo.favor.ProductFavorStrategy;
import com.lvmama.comm.utils.ActivityUtil;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.InvoiceUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.PersonUtil;
import com.lvmama.comm.utils.RandomFactory;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.utils.ord.RouteUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrdUserOrderDAO;
import com.lvmama.order.service.BuildOrderParent;
import com.lvmama.order.service.IBuildOrder;
import com.lvmama.order.service.IProductOrder;
import com.lvmama.order.util.GeneralSequenceNo;
import com.lvmama.prd.dao.MetaTravelCodeDAO;

/**
 * 订单创建服务实现类.
 * 
 * <pre>
 * 如何设置订单资源状态：
 * ORD_ORDER_ITEM_META表RESOURCE_STATUS字段与ORD_ORDER_ITEM_META表RESOURCE_CONFIRM字段有关
 * RESOURCE_CONFIRM为true时，RESOURCE_STATUS为UNCONFIRMED
 * RESOURCE_CONFIRM为false时，RESOURCE_STATUS为AMPLE
 * ORD_ORDER_ITEM_META表RESOURCE_CONFIRM字段与采购产品时间价格有关
 * ORD_ORDER表RESOURCE_CONFIRM_STATUS字段与ORD_ORDER表RESOURCE_CONFIRM字段有关
 * RESOURCE_CONFIRM为true时，RESOURCE_STATUS为UNCONFIRMED
 * RESOURCE_CONFIRM为false时，RESOURCE_STATUS为AMPLE
 * ORD_ORDER表RESOURCE_CONFIRM字段与ORD_ORDER_ITEM_META表RESOURCE_CONFIRM字段有关
 * 如果ORD_ORDER相关ORD_ORDER_ITEM_META中任意一条RESOURCE_CONFIRM字段为true，则ORD_ORDER表RESOURCE_CONFIRM字段为true
 * 特殊情况当调用者明确声明待创建订单资源已确认时
 * 即BuyInfo的resourceConfirmStatus字段声明为字符串true
 * ORD_ORDER表RESOURCE_CONFIRM_STATUS字段会被强行设置为AMPLE，此时其他三个字段计算逻辑保持不变
 * </pre>
 * 
 * @author liwenzhan
 * @author sunruyi
 * @version Super订单创建重构
 * @since Super订单创建重构
 */
public final class BuildOrderImpl extends BuildOrderParent implements
		IBuildOrder {
	
	private MetaTravelCodeDAO metaTravelCodeDAO;
	
	private OrdUserOrderDAO ordUserOrderDAO;
	
	/**
	 * 初始化订单创建DTO.
	 * 
	 * @param buyInfo
	 *            购买信息
	 * @param originalOrderId
	 *            原订单ID
	 * @param operatorId
	 *            操作人
	 * @return 订单创建DTO{@link OrderInfoDTO}
	 */
	@Override
	public OrderInfoDTO initOrderInfo(final BuyInfo buyInfo,
			final Long originalOrderId, final Map<Long,SupBCertificateTarget> bcertTargetMap, final Map<Long,List<SupPerformTarget>> performTargetMap,final Map<Long, OrdOrderItemMeta> ordOrderItemMateMap, final String operatorId) {
		OrderInfoDTO orderInfo = new OrderInfoDTO();
		orderInfo.setBuyInfo(buyInfo);
		orderInfo.setOriginalOrderId(originalOrderId);
		orderInfo.setOperatorId(operatorId);
		orderInfo.setOrderNo(GeneralSequenceNo.generateSerialNo());
		orderInfo.setChannel(buyInfo.getChannel());
		orderInfo.setPaymentTarget(buyInfo.getPaymentTarget());
		orderInfo.setPaymentChannel(buyInfo.getPaymentChannel());
		orderInfo.setWaitPayment(buyInfo.getWaitPayment());
		orderInfo.setUserId(buyInfo.getUserId());
		orderInfo.setUserMemo(buyInfo.getUserMemo());
		orderInfo.setActualPay(0L);
		orderInfo.setRedail(buyInfo.getRedail());
		orderInfo.setSelfPack(buyInfo.getSelfPack());
		orderInfo.setTodayOrder(Boolean.toString(buyInfo.isTodayOrder()));
		orderInfo.setCreateTime(new Date());//该值用来在后面计算使用，不存入数据库
		orderInfo.setIsAperiodic(buyInfo.getIsAperiodic());
		/**
		 * 秒杀新增
		 * 2014-04-14
		 * @author zenglei
		 */
		if(buyInfo.getSeckillId() != null){
			orderInfo.setSeckillId(buyInfo.getSeckillId());
		}
		
		if(buyInfo.IsAperiodic()) {
			orderInfo.setValidBeginTime(buyInfo.getValidBeginTime());
			orderInfo.setValidEndTime(buyInfo.getValidEndTime());
		}
		markOrderInfo(orderInfo,bcertTargetMap,performTargetMap,ordOrderItemMateMap);
		return orderInfo;
	}

	/**
	 * 
	 * 构建订单创建DTO.
	 * 
	 * <pre>
	 *       1.判断是否有资源markResourceAmple().
	 *          a.根据购买信息(buyInfo)中的有没有资源
	 *       2.组装订单的子产品明细makeOrdItemProdList().
	 *          a.把购买信息(buyInfo)中的子产品明细中放到orderInfo中 .
	 *       3.组装订单里的子产品明细 makeOrderInfoType()
	 *          a.判断订单信息(buyInfo)里面有没有子产品明细，然后进行组装.
	 *       4.判断前台或后台下单makeOrderStage().
	 *           a.当操作者是下单人时,为后台下单.
	 *               判断支付对象是lvmama,需要确认时。
	 *                  设置领单状态taken为TAKEN,领单者takenOperator为当前操作者OperatorId,领单时间dealTime.
	 *               判断是否需要审核时。
	 *                  设置审核状态ApproveStatus，审核时间ApproveTime.
	 *           b.当操作者不是下单人时,为前台下单.
	 *               设置领单状态taken为UNTAKEN.
	 *    	5.判定是否需要资源审核
	 *    		a.需要的话在此不加最晚取消小时数的逻辑
	 *    		b.需要的话就在此加入最晚取消小时数的逻辑
	 * </pre>
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 * @return 订单创建DTO{@link OrderInfoDTO}
	 */
	private OrderInfoDTO markOrderInfo(OrderInfoDTO orderInfo, Map<Long,SupBCertificateTarget> bcertTargetMap, Map<Long,List<SupPerformTarget>> performTargetMap, Map<Long, OrdOrderItemMeta> ordOrderItemMateMap) {
		// 构建销售产品订单子项列表
		orderInfo = BuildOrderInfoDTO.makeOrdOrderItemProds(orderInfo,prodProductDAO,prodTimePriceDAO, productTimePriceLogic);
		// 构造订单应付余额
		orderInfo = makeOughtPay(orderInfo);
		// 订单子子项
		orderInfo = makeResourceConfirm(orderInfo,bcertTargetMap,performTargetMap,ordOrderItemMateMap);
		
		orderInfo = makeSupplierChannelInfo(orderInfo);
		
		//构造订单最晚取消时间
		orderInfo.makeLastCancelTime();
		
		if(!orderInfo.hasTodayOrder()){//不是当日预订的时候
			//非不定期才构造最晚取消时间
			if(!orderInfo.IsAperiodic()) {
				//构造订单最晚取消时间
				orderInfo.makeLastCancelTime();
			}
			//构造订单是否必须使用预授权支付;
			orderInfo.makeOrderNeedPrePay();
			
			// 构造订单支付等待时间
			if(orderInfo.getSeckillId() == null){
				orderInfo.reCalcWaitPayment();
			}
		}
		if(ActivityUtil.getInstance().checkActivityIsValid(Constant.ACTIVITY_FIVE_YEAR)){
			// 构造订单支付等待时间
			orderInfo.setWaitPayment(Constant.WAIT_PAYMENT.PW_2HOUR.getValue());
		}
		
		//上航订单支付等待时间12小时
		if(orderInfo.getIsShHolidayOrder()){
			orderInfo.setWaitPayment(Constant.WAIT_PAYMENT.PW_12HOUR.getValue());
		}
		
		//不需要资源审核的订单在此加最晚取消小时数与支付等待时间的逻辑
		//供应商为港捷旅订房集团中心，不需要此操作
		if(orderInfo.getApproveTime()!=null && !orderInfo.hasGangjie() && orderInfo.getNeedPrePay()!= "normalPay"){
			orderInfo.compareWaitPayment();
		}
		// 构建订单领单状态
		orderInfo = BuildOrderInfoDTO.makeTaken(orderInfo);
		return orderInfo;
	}
	
	
	private OrderInfoDTO makeSupplierChannelInfo(final OrderInfoDTO orderInfo){
		if(orderInfo.getIsShHolidayOrder()){
			OrdOrderItemProd itemProd = orderInfo.getMainProduct();
			for(OrdOrderItemMeta itemMeta:itemProd.getOrdOrderItemMetas()){
				if(itemMeta.getProductIdSupplier()!=null){
					MetaTravelCode metaTravelCode = metaTravelCodeDAO.selectBySuppAndDate(itemMeta.getProductIdSupplier(), DateUtil.accurateToDay(itemMeta.getVisitTime()));
					if(metaTravelCode!=null){
						orderInfo.setTravelGroupCode(metaTravelCode.getTravelCode());
						break;
					}
				}
			}
		}
		
		if(orderInfo.getIsJinjiangOrder()){
			OrdOrderItemProd itemProd = orderInfo.getMainProduct();
			for(OrdOrderItemMeta itemMeta:itemProd.getOrdOrderItemMetas()){
				if(itemMeta.getProductIdSupplier()!=null){
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("supplierProductId", itemMeta.getProductIdSupplier());
					params.put("specDate", DateUtil.accurateToDay(itemMeta.getVisitTime()));
					params.put("supplierChannel", Constant.SUPPLIER_CHANNEL.JINJIANG.getCode());
					List<MetaTravelCode> metaTravelCodes = metaTravelCodeDAO.selectByCondition(params);
					if(metaTravelCodes!=null && metaTravelCodes.size()>0){
						orderInfo.setTravelGroupCode(metaTravelCodes.get(0).getTravelCodeId());
						break;
					}
				}
			}
		}
		return orderInfo;
	}
	
	
	
	/**
	 * 构造订单应付余额.
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 * @return 订单创建DTO{@link OrderInfoDTO}
	 */
	private OrderInfoDTO makeOughtPay(final OrderInfoDTO orderInfo) {
		if (UtilityTool.isNotNull(orderInfo.getOrdOrderItemProds())) {
			Long oughtPay = 0L;
//			Long settlementPrice=0L;
			Long payDeposit=0L;
			Set<Long> sendSmsProductId = new HashSet<Long>();
			Long mainProductId = orderInfo.getMainProduct().getProductId();
 			for (OrdOrderItemProd ordOrderItemProd : orderInfo
					.getOrdOrderItemProds()) {
				// 销售产品
				final ProdProduct prodProduct = prodProductDAO
						.selectByPrimaryKey(ordOrderItemProd.getProductId());
				final ProdProductBranch prodProductBranch = prodProductBranchDAO
						.selectByPrimaryKey(ordOrderItemProd.getProdBranchId());
				// 销售产品子类型
				ordOrderItemProd.setSubProductType(prodProduct.getSubProductType());
				// 产品名称
				ordOrderItemProd.setProductName(prodProduct.getProductName()+"("+prodProductBranch.getBranchName()+")");
				// 产品类型
				ordOrderItemProd.setProductType(prodProduct.getProductType());
				//分类产品类型
				ordOrderItemProd.setBranchType(prodProductBranch.getBranchType());
				// 市场价
				ordOrderItemProd.setMarketPrice(prodProductBranch.getMarketPrice());
				// 附加产品
				ordOrderItemProd.setAdditional(prodProductBranch.getAdditional());
				
				ordOrderItemProd.setWrapPage(prodProduct.getWrapPage());
				// 是否发送短信
				if(!ordOrderItemProd.getProductId().equals(mainProductId)){
					if(orderInfo.hasSelfPack()&& !sendSmsProductId.contains(prodProduct.getProductId())){
						ordOrderItemProd.setSendSms(prodProduct.getSendSms());
						sendSmsProductId.add(prodProduct.getProductId());
					} else if(!orderInfo.hasSelfPack() && !sendSmsProductId.contains(prodProduct.getProductId())){
						ordOrderItemProd.setSendSms(prodProduct.getSendSms());
						sendSmsProductId.add(prodProduct.getProductId());
					}else{
						ordOrderItemProd.setSendSms("false");
					}
				}else{
					ordOrderItemProd.setSendSms("false");
				}
				
				// 单价
//				Long price = 0L;
//				if (ActivityUtil.getInstance().checkActivityIsValid(Constant.ACTIVITY_FIVE_YEAR) && 
//						!(Constant.CHANNEL.DISTRIBUTION_YIHAODIAN.name()).equals(orderInfo.getChannel()) &&
//						!(Constant.CHANNEL.DISTRIBUTION_JINGDONG.name()).equals(orderInfo.getChannel()) &&
//						!(Constant.CHANNEL.DISTRIBUTION_YINLIAN.name()).equals(orderInfo.getChannel()) &&
//						!(Constant.CHANNEL.DISTRIBUTION_QUNA.name()).equals(orderInfo.getChannel()) &&
//						!(Constant.CHANNEL.DISTRIBUTION_QUNA_TUAN.name()).equals(orderInfo.getChannel()) &&
//						!(Constant.CHANNEL.DISTRIBUTION_QUNA_TICKET.name()).equals(orderInfo.getChannel()) &&
//						!(Constant.CHANNEL.DISTRIBUTION_QUNA_TICKET_TUAN.name()).equals(orderInfo.getChannel()) &&
//						!(Constant.CHANNEL.DISTRIBUTION_JINZONGLV.name()).equals(orderInfo.getChannel()) &&
//						!(Constant.CHANNEL.DISTRIBUTION_UNICOM114.name()).equals(orderInfo.getChannel()) &&
//						!(Constant.CHANNEL.DISTRIBUTION_SHMOBILE.name()).equals(orderInfo.getChannel()) &&
//						!(Constant.CHANNEL.TAOBAL.name()).equals(orderInfo.getChannel())) {
//					price = ordOrderItemProd.getPrice();
//				} else {
//					price = markPrice(prodProduct.getProductId(),ordOrderItemProd.getProdBranchId(), ordOrderItemProd.getVisitTime());
//				}
				Long price = markPrice(prodProduct.getProductId(),ordOrderItemProd.getProdBranchId(), ordOrderItemProd.getVisitTime());
				if(orderInfo.hasSelfPack()){//是超级自由行需要在价格上去计算减去优惠的金额
					ProdJourneyProduct pjp=prodJourneyProductDAO.selectByPrimaryKey(ordOrderItemProd.getJourneyProductId());
					if(pjp!=null){
						price=price-pjp.getDiscount();
					}
				}
				ordOrderItemProd.setPrice(price);
//				//判断是不是主产品.
				if(ordOrderItemProd.hasDefault()){
					//首次应该支付金额
					 payDeposit +=prodProduct.getPayDeposit() * ordOrderItemProd.getQuantity();					 
				}
				// 不是自由行的主产品就需要计算总价和成本价
				if(!(orderInfo.hasSelfPack()&&ordOrderItemProd.hasDefault())){
					oughtPay += price * ordOrderItemProd.getQuantity();
				}
			}
			orderInfo.setOughtPay(oughtPay);
			orderInfo.setOrderPay(oughtPay);
			// 构造订单类型
			this.markOrderType(orderInfo);
			// 构造订单是否可以分笔和支付金额
			this.markOrderIsOpenPartPay(orderInfo,payDeposit);
			//构造团号(非不定期)
			if(!orderInfo.IsAperiodic()) {
				markTravelCode(orderInfo);
			}
			
			//购造订单的组织ID
			OrdOrderItemProd mainProd=orderInfo.getMainProduct();
			if(mainProd!=null)
			{
				final ProdProduct prodProduct = prodProductDAO
						.selectByPrimaryKey(mainProd.getProductId());
				orderInfo.setOrgId(prodProduct.getOrgId());
				mainProd.setSendSms(prodProduct.getSendSms());//设置主产品发短信
				orderInfo.setPrePaymentAble(prodProduct.getPrePaymentAble());//设置订单的预授权支付Flag
			}
		}
		
		return orderInfo;
	}

	/**
	 * 构造订单的类型判断订单是不是长线和出境,是否可以分笔和支付金额.
	 * 
	 * @param orderInfo
	 * @return
	 */
	private OrderInfoDTO markOrderIsOpenPartPay(OrderInfoDTO orderInfo,final Long payDeposit) {
			orderInfo.setIsOpenPartPay(String.valueOf(RouteUtil.hasMakePayDeposit(orderInfo.getOrderType())));
			orderInfo.setPayDeposit(payDeposit);
		return orderInfo;
	}
	
	/**
	 * 构造订单的团号,只有出境,长途,自助巴士班有团号概念.
	 * @param orderInfo
	 * @return
	 */
	private OrderInfoDTO markTravelCode(OrderInfoDTO orderInfo){
		//判断类型能否生存团号
		if (RouteUtil.hasTravelGroupProduct(orderInfo.getOrderType())) {
			//判断主产品
			final ProdProduct prodProduct = prodProductDAO.selectProductDetailByPrimaryKey(orderInfo.getMainProduct().getProductId());
			//增加是否能生成团号判断
			if(RouteUtil.hasTravelGroupProduct(prodProduct)){
				//生成团号
				orderInfo.setTravelGroupCode(RouteUtil.makeTravelGroupCode(prodProduct, orderInfo.getVisitTime()));
			}
		}
		return orderInfo;
	}
	
	
	/**
	 * 构造价格.
	 * 
	 * @param prodProductId
	 *            销售产品ID
	 * @param visitTime
	 *            游玩时间
	 * @return 价格
	 */
	private Long markPrice(final Long prodProductId,final Long prodProductBranchId, final Date visitTime) {
		final TimePrice timePrice = prodTimePriceDAO.getProdTimePrice( prodProductId,prodProductBranchId, visitTime);
		return timePrice.getPrice();
	}

	/**
	 * 构造订单类型.
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 * @return 订单创建DTO{@link OrderInfoDTO}
	 */
	private OrderInfoDTO markOrderType(OrderInfoDTO orderInfo) {
		boolean isRouteProductType = false;
		boolean isHotelProductType = false;
		boolean isTicketProductType = false;
		for (OrdOrderItemProd ordOrderItemProd : orderInfo
				.getOrdOrderItemProds()) {
			// 销售产品
			final ProdProduct prodProduct = prodProductDAO
					.selectByPrimaryKey(ordOrderItemProd.getProductId());
			// 销售产品类型
			String productType = prodProduct.getProductType();
			// 订单类型优先级别由高到低依次为线路、酒店、门票和其他
			if("true".equals(ordOrderItemProd.getIsDefault())&&"true".equals(orderInfo.getSelfPack())){
				orderInfo.setOrderType(prodProduct.getSubProductType());
				break;
			} else if (Constant.PRODUCT_TYPE.ROUTE.name().equalsIgnoreCase(productType)) {
				orderInfo.setOrderType(prodProduct.getSubProductType());
				isRouteProductType = true;
			} else if (Constant.PRODUCT_TYPE.HOTEL.name().equalsIgnoreCase(
					productType)) {
				if (!isRouteProductType) {
					orderInfo.setOrderType(productType);
					isHotelProductType = true;
				}
			} else if (Constant.PRODUCT_TYPE.TICKET.name().equalsIgnoreCase(
					productType)) {
				if (!isRouteProductType && !isHotelProductType) {
					orderInfo.setOrderType(productType);
					isTicketProductType = true;
				}
			} else if (Constant.PRODUCT_TYPE.TRAFFIC.name().equalsIgnoreCase(
					productType)
					&& Constant.SUB_PRODUCT_TYPE.TRAIN.name().equalsIgnoreCase(
							prodProduct.getSubProductType())) {
				if(!isRouteProductType && !isHotelProductType &&!isTicketProductType){
					orderInfo.setOrderType(prodProduct.getSubProductType());
				}
				
			}else if (Constant.PRODUCT_TYPE.OTHER.name().equalsIgnoreCase(
					productType)) {
				if (!UtilityTool.isValid(orderInfo.getOrderType())) {
					orderInfo.setOrderType(productType);
				}
			}
		}
		return orderInfo;
	}

	/**
	 * 构建订单资源确认状态.
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 * @return 订单创建DTO{@link OrderInfoDTO}
	 */
	private OrderInfoDTO makeResourceConfirm(OrderInfoDTO orderInfo,
			final Map<Long,SupBCertificateTarget> bcertTargetMap, 
			final Map<Long,List<SupPerformTarget>> performTargetMap,
			final Map<Long, OrdOrderItemMeta> ordOrderItemMateMap
			) {
		Boolean orderPhysical = false;
//		Long orderCashRefund = 0L;
		// 订单资源状态
		String orderResourceConfirm = Boolean.FALSE.toString();
		for (OrdOrderItemProd ordOrderItemProd : orderInfo
				.getOrdOrderItemProds()) {
			if (ordOrderItemProd.getQuantity() > 0) {
				// 销售产品
				ProdProduct prodProduct = prodProductDAO
						.selectByPrimaryKey(ordOrderItemProd.getProductId());
				orderPhysical = Boolean.TRUE.toString().equalsIgnoreCase(
						prodProduct.getPhysical());
				List<ProdProductBranchItem> prodProductBranchItemList = prodProductBranchItemDAO
						.selectBranchItemByProdBranchId(ordOrderItemProd.getProdBranchId());

				// 构造采购产品订单子项
				//如果不是自由行产品的主产品，则计算采购产品
				if(!"true".equals(ordOrderItemProd.getIsDefault())||!"true".equals(orderInfo.getSelfPack())){
					orderInfo = markOrdOrderItemMeta(orderInfo, 
													ordOrderItemProd,
													prodProductBranchItemList,
													bcertTargetMap,
													performTargetMap,ordOrderItemMateMap);
				}
				if (Boolean.TRUE.toString().equalsIgnoreCase(
						orderInfo.getResourceConfirm())) {
					orderResourceConfirm = orderInfo.getResourceConfirm();
				}
			}
		}
		// 资源确认状态
		orderInfo.setResourceConfirm(orderResourceConfirm);
		// 资源审核状态
		if (Boolean.TRUE.toString().equalsIgnoreCase(
				orderInfo.getBuyInfo().getResourceConfirmStatus())) {
			orderInfo
					.setResourceConfirmStatus(Constant.ORDER_RESOURCE_STATUS.AMPLE
							.name());
		} else {
			orderInfo.setResourceConfirmStatus(BuildOrderInfoDTO
					.makeResourceConfirmStatus(Boolean
							.valueOf(orderResourceConfirm)));
		}

		orderInfo.setPhysical(orderPhysical.toString());

		// 构建订单审核状态
		orderInfo = BuildOrderInfoDTO.markApproveStatus(
				orderInfo,
				!Constant.ORDER_RESOURCE_STATUS.AMPLE.name().equalsIgnoreCase(
						orderInfo.getResourceConfirmStatus())
						&& Boolean.valueOf(orderInfo.getResourceConfirm()));
		return orderInfo;
	}


	/**
	 * 构造采购产品订单子项.
	 * 
	 * @param orderInfo 订单创建DTO{@link OrderInfoDTO}
	 * @param ordOrderItemProd 销售产品订单子项
	 * @return 订单创建DTO{@link OrderInfoDTO}
	 */
	private OrderInfoDTO markOrdOrderItemMeta(OrderInfoDTO orderInfo,
			final OrdOrderItemProd ordOrderItemProd,
			final List<ProdProductBranchItem> prodProductBranchItemList,
			final Map<Long,SupBCertificateTarget> bcertTargetMap,
			final Map<Long,List<SupPerformTarget>> performTargetMap,
			final Map<Long, OrdOrderItemMeta> ordOrderItemMateMap) {
		boolean resourceConfirm = false;
		boolean orderResourceConfirm = false;
		long sumSettlementPrice = 0L;
		final List<OrdOrderItemMeta> ordOrderItemMetaList = new ArrayList<OrdOrderItemMeta>();
		for (int i = 0; i < prodProductBranchItemList.size(); i++) {
			final ProdProductBranchItem prodProductBranchItem = prodProductBranchItemList.get(i);
			// 采购产品
			final MetaProduct metaProduct = metaProductDAO
					.getMetaProductByPk(prodProductBranchItem.getMetaProductId());
			//采购产品分类
			final MetaProductBranch metaProductBranch = metaProductBranchDAO
			.selectBrachByPrimaryKey(prodProductBranchItem.getMetaBranchId());
			// 订单是否发送通关码判断标识
			final boolean passportFlag = (!orderInfo.isPassportOrder())
					&& isContainPassport(performTargetMap.get(prodProductBranchItem.getMetaProductId()));
			// 构建订单是否发送通关码
			orderInfo = BuildOrderInfoDTO.markPassport(orderInfo, passportFlag);
			final OrdOrderItemMeta ordOrderItemMeta = new OrdOrderItemMeta();
			//履行对象ID
			List<SupPerformTarget> performTargetList = performTargetMap.get(prodProductBranchItem.getMetaProductId());
			if(performTargetList!=null && performTargetList.size()>0){
				ordOrderItemMeta.setPerformTargetId(performTargetList.get(0).getTargetId());
			}
			ordOrderItemMeta.setFilialeName(metaProduct.getFilialeName());
			ordOrderItemMeta.setOrderItemMetaId(this.makeOrderItemMetaId());
			ordOrderItemMeta.setMetaProductId(metaProduct.getMetaProductId());
			ordOrderItemMeta.setMetaBranchId(prodProductBranchItem.getMetaBranchId());
			ordOrderItemMeta.setOrderId(orderInfo.getOrderId());
			ordOrderItemMeta
					.setPerformStatus(Constant.ORDER_PERFORM_STATUS.UNPERFORMED
							.name());
			//ordOrderItemMeta.setSettlementStatus(Boolean.FALSE.toString());
			ordOrderItemMeta.setSettlementStatus(Constant.SETTLEMENT_STATUS.UNSETTLEMENTED.name());
			ordOrderItemMeta.setProductName(metaProduct.getProductName()+"("+metaProductBranch.getBranchName()+")");
			ordOrderItemMeta.setProductType(metaProduct.getProductType());
			ordOrderItemMeta.setBranchType(metaProductBranch.getBranchType());
			ordOrderItemMeta.setOrderItemId(ordOrderItemProd
					.getOrderItemProdId());
			ordOrderItemMeta.setSupplierId(metaProduct.getSupplierId());
			ordOrderItemMeta.setAdultQuantity(metaProductBranch.getAdultQuantity());
			ordOrderItemMeta.setChildQuantity(metaProductBranch.getChildQuantity());
			ordOrderItemMeta.setIsResourceSendFax(metaProduct.getIsResourceSendFax());//是否需要资源审核后发送传真
			ordOrderItemMeta.setProductQuantity(prodProductBranchItem.getQuantity());
			ordOrderItemMeta.setVisitTime(ordOrderItemProd.getVisitTime());
			ordOrderItemMeta.setFaxMemo(ordOrderItemProd.getFaxMemo());
			ordOrderItemMeta.setTaken(Constant.AUDIT_TAKEN_STATUS.UNTAKEN
					.name());
			ordOrderItemMeta.setValidDays(metaProduct.getValidDays());
			//目前只支持销售类别打包一个采购类别
			if(orderInfo.hasTodayOrder()&&metaProduct.isTicket()){
				MetaProductTicket mpt = (MetaProductTicket)metaProductDAO.getMetaProduct(metaProduct.getMetaProductId(), Constant.PRODUCT_TYPE.TICKET.name());
				ordOrderItemProd.setLastTicketTime(mpt.getLastTicketTime());
				ordOrderItemProd.setLastPassTime(mpt.getLastPassTime());
				ordOrderItemProd.makeVisitTime(orderInfo.getCreateTime());
				ordOrderItemMeta.setVisitTime(ordOrderItemProd.getVisitTime());//重新设置产品上的该值
			}
			
			// 采购产品时间价格
			final TimePrice timePrice = metaTimePriceDAO
					.getMetaTimePriceByIdAndDate(
							ordOrderItemMeta.getMetaBranchId(),
							ordOrderItemProd.getVisitTime());
			resourceConfirm = productResourceConfirmLogic.isResourceConfirm(
					metaProductBranch, timePrice.getSpecDate());
			if (resourceConfirm) {
				orderResourceConfirm = resourceConfirm;
			}
			// 资源确认状态
			ordOrderItemMeta
					.setResourceConfirm(String.valueOf(resourceConfirm));
			// 资源审核状态
			ordOrderItemMeta.setResourceStatus(BuildOrderInfoDTO
					.makeResourceConfirmStatus(resourceConfirm));
			//早餐数
			ordOrderItemMeta.setBreakfastCount(timePrice.getBreakfastCount() == null ? 0 : timePrice.getBreakfastCount());
			ordOrderItemMeta.setMarketPrice(timePrice.getMarketPrice());
			ordOrderItemMeta.setSettlementPrice(timePrice.getSettlementPrice());
			ordOrderItemMeta.setActualSettlementPrice(timePrice
					.getSettlementPrice());
			

			ordOrderItemMeta.setSubProductType(metaProduct.getSubProductType());
			if(StringUtils.isEmpty(orderInfo.getSupplierChannel())&&StringUtils.isNotEmpty(metaProduct.getSupplierChannel())){
				orderInfo.setSupplierChannel(metaProduct.getSupplierChannel());
			}
			ordOrderItemMeta.setRefund(Boolean.FALSE.toString());

			ordOrderItemMeta.setQuantity(ordOrderItemProd.getQuantity());
			ordOrderItemMeta.setPaymentTarget(orderInfo.getPaymentTarget());
			ordOrderItemMeta.setProductIdSupplier(metaProductBranch
					.getProductIdSupplier());
			ordOrderItemMeta.setProductTypeSupplier(metaProductBranch
					.getProductTypeSupplier());
			//是否虚拟
			ordOrderItemMeta.setVirtual(metaProductBranch.getVirtual());
			
			SupBCertificateTarget bCertificateTarget = bcertTargetMap.get(prodProductBranchItem.getMetaProductId());
			//由凭证对象为传真，并且采购类别设为需要单独创建传真来决定sendFax是否为true
			if(bCertificateTarget != null){
				if (BooleanUtils.toBoolean(bCertificateTarget.isSendFax())
						&& metaProductBranch.hasSendFax()) {
					ordOrderItemMeta.setSendFax("true");
				} else {
					ordOrderItemMeta.setSendFax("false");
				}
				//由凭证对象为供应商资源确认
				if (bCertificateTarget.hasSupplier() && !"true".equals(metaProductBranch.getVirtual())) {
					ordOrderItemMeta.setSupplierFlag("true");
				} else {
					ordOrderItemMeta.setSupplierFlag("false");
				}	
			}
					
			// 累加结算价
			sumSettlementPrice += timePrice.getSettlementPrice()
					* ordOrderItemMeta.getProductQuantity();
			ordOrderItemMeta.setTotalSettlementPrice(ordOrderItemMeta.getSettlementPrice()*ordOrderItemMeta.getQuantity()*
					ordOrderItemMeta.getProductQuantity());
			
			
			//设置机票相关信息
			OrdOrderItemMeta ordOrderItemMetaDTO=ordOrderItemMateMap.get(prodProductBranchItem.getMetaProductId());
			if(null!=ordOrderItemMetaDTO){
				ordOrderItemMeta.setDirection(ordOrderItemMetaDTO.getDirection());
				ordOrderItemMeta.setGoFlightCode(ordOrderItemMetaDTO.getGoFlightCode());
				ordOrderItemMeta.setGoFlightTime(ordOrderItemMetaDTO.getGoFlightTime());
				ordOrderItemMeta.setBackFlightCode(ordOrderItemMetaDTO.getBackFlightCode());
				ordOrderItemMeta.setBackFlightTime(ordOrderItemMetaDTO.getBackFlightTime());
			}
			ordOrderItemMetaList.add(ordOrderItemMeta);
		}
		ordOrderItemProd.setSumSettlementPrice(sumSettlementPrice);
		ordOrderItemProd.setResourceStatus(String.valueOf(orderResourceConfirm));
		// 资源确认
		orderInfo.setResourceConfirm(String.valueOf(orderResourceConfirm));
		// 资源审核
		orderInfo.setResourceConfirmStatus(BuildOrderInfoDTO
				.makeResourceConfirmStatus(orderResourceConfirm));
		ordOrderItemProd.setOrdOrderItemMetas(ordOrderItemMetaList);
		orderInfo.setAllOrdOrderItemMetas(ordOrderItemMetaList);
		return orderInfo;
	}

	/**
	 * 保存订单创建DTO.
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 * @return 订单创建DTO{@link OrderInfoDTO}
	 */
	@Override
	public OrderInfoDTO saveOrderInfo(final OrderInfoDTO orderInfo) {
		this.saveOrder(orderInfo);
		return orderInfo;
	}

	/**
	 * 保存OrderInfo.
	 * 
	 * <pre>
	 *     1.保存订单信息insert().
	 *     2.包装订单中的游玩人信息列表makeOrdPersonList().
	 *        a.把购买信息(buyInfo)中的游玩人信息保存到(orderInfo)中.
	 *     3.包装订单中的发票信息列表makeOrdInvoiceList().
	 *        a.判断购买信息(buyInfo)中有没有发票信息.
	 *          有,把购买信息中的发票信息保存到订单中.
	 *          没有,不保存.
	 *     4.保存OrderInfo中游玩人OrdPerson对象列表.
	 *     5.保存OrderInfo中发票ordInvoice对象列表.
	 *     6.保存OrderInfo中快递ordExpress对象.
	 *     7.保存子产品明细列表saveOrdOrderItemProd().
	 * 
	 * </pre>
	 * 
	 * @param orderInfo
	 *            订单信息
	 */
	private void saveOrder(OrderInfoDTO orderInfo) {
		boolean isAperiodic = orderInfo.getBuyInfo().IsAperiodic();
		orderDAO.insert(orderInfo);
		 // 保存团附加表的信息
		saveOrderRoute(orderInfo);
		// 构建订单游玩人列表
		orderInfo = BuildOrderInfoDTO.makePersonList(orderInfo);
		// 构建订单发票列表
		orderInfo = BuildOrderInfoDTO.makeInvoiceList(orderInfo);
		// 保存游玩人列表
		saveOrdPerson(orderInfo);
        //保存废单重下的前台渠道
        saveOrdOrderChannel(orderInfo);
		// 保存快递
		saveOrdExpress(orderInfo.getOrderId());
		// 保存销售产品订单子项
		saveOrdOrderItemProd(orderInfo);
		//生成密码券
		if(isAperiodic) {
			saveOrderItemMetaAperiodic(orderInfo);
		}
		if(!orderInfo.hasSelfPack() && Constant.initCouponEnabled(orderInfo.getMainProduct().getProductId())){//非超级自由行产品才可以使用优惠券
			// 处理优惠券和优惠活动
			orderInfo = markFavor(orderInfo);
		}else{//超级自由行以订单金额做为产品金额
			OrdOrderAmountItem amountItem = BuildOrderInfoDTO.markOrderAmountItem(
					orderInfo.getOrderId(), orderInfo.getOughtPay());
			orderInfo.setAmountItem(amountItem);
		}
		// 保存发票,此方法放订单项后面，发票的金额需要在此处写入
		orderInfo = saveOrdInvice(orderInfo);

		//保存客户端访问信息
		saveClientInfo(orderInfo);
		
		// 保存ComAudit
		saveComAudit(orderInfo);
		productStockLogic.minusStock(orderInfo);
		// 保存订单价格明细
		saveAmountItem(orderInfo);
		if(orderInfo.hasTodayOrder()){
			changeTodayOrder(orderInfo);
		}

		if(orderInfo.isPayToLvmama()) {
			Long cashRefund=0L;
			//客户端点评多返的奖金额度 
			Long clientCmtRefundAmount=orderInfo.getBuyInfo().getClientCommentRefundAmount();
			if(null!=clientCmtRefundAmount&&clientCmtRefundAmount>0){
				//客户端 直接自己计算返现金额
				//cashRefund=orderDAO.queryOrderCashRefundByOrderIdForClient(orderInfo.getOrderId(), clientCmtRefundAmount);
				cashRefund = clientCmtRefundAmount;
			}else{//常规，不需要多返现的
				cashRefund=bonusReturnLogic.getOrderBonusReturnAmount(orderInfo);
			}
			orderInfo.setCashRefund(cashRefund);
		} else {
			orderInfo.setCashRefund(0L);
		}

        if(!isCouponEnabled(orderInfo.getMainProduct().getProductId())) {
            orderInfo.setCashRefund(0L);
        }

		//不定期订单,清除订单上的游玩时间
		if(isAperiodic) {
			orderInfo.setVisitTime(null);
		}
		
		//订单整合（BEE和VST)
		saveOrdUserOrder(orderInfo);
		
		//设置是否为测试单
		orderInfo.setTestOrderFlag(buildTestOrderFlag(orderInfo));
		orderDAO.updateByPrimaryKey(orderInfo);
	}
    /**
     * 优惠是否有效
     * @param productId
     * @return
     */
    private boolean isCouponEnabled(Long productId) {
        String couponEnabled = "Y";
        Object status = MemcachedUtil.getInstance().get("PRODUCT_COUPON_ENABLED_" + productId);
        if (status != null) {
            couponEnabled = (String) status;
        } else {
            status = MemcachedUtil.getInstance().get("ALL_COUPON_ENABLED");
            if (status != null) {
                couponEnabled = (String) status;
            }
        }

        return "Y".equals(couponEnabled);
    }


    private void saveOrdOrderChannel(OrderInfoDTO orderInfo) {
        if (orderInfo.getOriginalOrderId() != null) {
            List<OrderChannelInfo> orderChannelInfoList = ordOrderChannelDAO.queryByOrderId(orderInfo.getOriginalOrderId());
            if (orderChannelInfoList != null && orderChannelInfoList.size() > 0) {
                for (OrderChannelInfo orderChannelInfo : orderChannelInfoList) {
                    orderChannelInfo.setOrderId(orderInfo.getOrderId());
                    orderChannelInfo.setCreateDate(new Date());
                    ordOrderChannelDAO.insert(orderChannelInfo);
                }
            }
        }
    }
	private String buildTestOrderFlag(OrderInfoDTO  orderInfo ){
		List<OrdPerson> personList = orderInfo.getPersonList();
		List<OrdOrderItemProd> ordOrderItemProdList= orderInfo.getOrdOrderItemProds(); 
		String[] testOrderString = Constant.getInstance().getTestOrderFlagString();
		if(testOrderString == null || testOrderString.length < 1) {
			return "false";
		}
		if( personList!= null) {
			for(OrdPerson person : personList) {
				if(person == null || person.getName() == null) {
					continue;
				}
				for(String test : testOrderString) {
					if(!StringUtil.isEmptyString(test) && person.getName().contains(test)) {
						return "true";
					}
				}
			}
		}
		if(ordOrderItemProdList != null) {
			for(OrdOrderItemProd ooip : ordOrderItemProdList) {
				if(ooip == null || ooip.getProductName() == null) {
					continue;
				}
				for(String test : testOrderString) {
					if(!"".equals(test) && ooip.getProductName().contains(test)) {
						return "true";
					}
				}
				if(ooip.getOrdOrderItemMetas()!=null){
					for(OrdOrderItemMeta ooim:ooip.getOrdOrderItemMetas()){
						if(ooim == null || ooim.getProductName() == null) {
							continue;
						}
						for(String test : testOrderString) {
							if(!"".equals(test) && ooim.getProductName().contains(test)) {
								return "true";
							}
						}
					}
				}
			}
		}
		return "false";
	}
	
	/**
	 * 生成订单密码券，对应订单子子项
	 * add by shihui
	 * */
	private void saveOrderItemMetaAperiodic(OrderInfoDTO orderInfo) {
		String code = getPasswordCertificate();
		if (UtilityTool.isNotNull(orderInfo.getOrdOrderItemProds())) {
			for (OrdOrderItemProd ordOrderItemProd : orderInfo
					.getOrdOrderItemProds()) {
				for (OrdOrderItemMeta ordOrderItemMeta : ordOrderItemProd.getOrdOrderItemMetas()) {
					if(!ordOrderItemMeta.isOtherProductType()) {
						OrdOrderItemMetaAperiodic aperiodic = new OrdOrderItemMetaAperiodic();
						aperiodic.setPasswordCertificate(code);
						aperiodic.setActivationStatus(Constant.APERIODIC_ACTIVATION_STATUS.INVALID.name());
						aperiodic.setOrderItemMetaId(ordOrderItemMeta.getOrderItemMetaId());
						aperiodic.setOrderId(orderInfo.getOrderId());
						aperiodic.setValidBeginTime(ordOrderItemProd.getValidBeginTime());
						aperiodic.setValidEndTime(ordOrderItemProd.getValidEndTime());
						aperiodic.setInvalidDate(ordOrderItemProd.getInvalidDate());
						aperiodic.setInvalidDateMemo(ordOrderItemProd.getInvalidDateMemo());
						orderItemMetaAperiodicDAO.insert(aperiodic);
					}
				}
			}
		}
	}
	
	private String getPasswordCertificate(){
		Long code = RandomFactory.generateLong(6);
		String codeStr = code.toString();
		if(!orderItemMetaAperiodicDAO.isPasswordCertificateExisted(codeStr))
			return codeStr;
		else
			return getPasswordCertificate();
	}
	
	/**
	 * 更改游玩时间的时分秒,该值在短信当中使用
	 * @param orderInfo
	 */
	private void changeTodayOrder(OrderInfoDTO orderInfo){
		List<Date> dates = new ArrayList<Date>();
		dates.add(orderInfo.getCreateTime());
		Date latestUseTimeDate = null;
		List<Date> lastCancelTime = new ArrayList<Date>();
		for(OrdOrderItemProd itemProd:orderInfo.getOrdOrderItemProds()){
			Date date = itemProd.getEarliestUseTimeDate();
			long min=0;
			
			if(itemProd.getLastPassTime()!=null){//最晚入园时间加上换票时间
				min+=itemProd.getLastPassTime().intValue();				
			}
			
			if(itemProd.getLastTicketTime()!=null){
				dates.add(DateUtils.addMinutes(orderInfo.getCreateTime(), itemProd.getLastTicketTime().intValue()));
				min+=itemProd.getLastTicketTime().intValue();
			}
			dates.add(date);
			latestUseTimeDate=itemProd.getLatestUseTimeDate();
			
			lastCancelTime.add(DateUtils.addMinutes(latestUseTimeDate, -(int)min));
		}
		Date today = Collections.max(dates);		
		orderInfo.setVisitTime(today);
		orderInfo.setLatestUseTime(latestUseTimeDate);
		orderInfo.setLastCancelTime(Collections.min(lastCancelTime));
	}
	
	private void saveClientInfo(OrderInfoDTO orderInfo){
		
		if(orderInfo.getBuyInfo().getClientOrderReport()!=null && 
				!StringUtil.isEmptyString(orderInfo.getBuyInfo().getClientOrderReport().getUdid())){
			orderInfo.getBuyInfo().getClientOrderReport().setOrderId(orderInfo.getOrderId());
			clientOrderReportDAO.insert(orderInfo.getBuyInfo().getClientOrderReport());
		}
		
		if(orderInfo.getBuyInfo().getOrdOrderChannel() !=null){
			ordOrderChannelDAO.insert(new OrdOrderChannel(orderInfo.getOrderId(), orderInfo.getBuyInfo().getOrdOrderChannel().getChannel(),orderInfo.getBuyInfo().getOrdOrderChannel().getArg1(),orderInfo.getBuyInfo().getOrdOrderChannel().getArg2()));
		}
	}

	/**
	 * 保存团附加表的信息
	 * @param orderInfo
	 */
	private void saveOrderRoute(OrderInfoDTO orderInfo) {
		//只有出境和长途 才需要团的附加表
			if(RouteUtil.hasMakePayDeposit(orderInfo.getOrderType())){
				OrdOrderRoute orderRoute = new OrdOrderRoute();
				orderRoute.setOrderId(orderInfo.getOrderId());
				orderRoute.setTrafficTicketStatus("false");
				orderRoute.setGroupWordStatus(Constant.GROUP_ADVICE_STATE.NEEDSEND.name());
				//只有出境才有签证状态，其他都为空.
				if(RouteUtil.hasVisa(orderInfo.getOrderType())){
					orderRoute.setVisaStatus(Constant.VISA_STATUS.WAIT_NOTICE.name());
				}
				orderInfo.setOrderRoute(orderRoute);
				orderRouteService.insertOrderRoute(orderRoute);
			}
	}
	
	/**
	 * 取消订单.
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 */
	private void cancelOrder(final OrderInfoDTO orderInfo) {
		if (UtilityTool.isValid(orderInfo.getOriginalOrderId())) {
			orderUpdateService.cancelOrder(orderInfo.getOriginalOrderId(),
					Constant.ORD_CANCEL_REASON.CANCEL_TO_CREATE_NEW.name(),
					orderInfo.getTakenOperator(), null);
		}
	}

	/**
	 * 保存操作日志.
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 */
	public void saveLog(final OrderInfoDTO orderInfo) {
		final String operatorId = orderInfo.getOperatorId();
		final Long orderId = orderInfo.getOrderId();
		final String userId = orderInfo.getUserId();
		// 后台下单
		if (UtilityTool.isValid(operatorId) && !operatorId.equalsIgnoreCase(userId)) {
			saveComLog("ORD_ORDER", orderId, operatorId,
					Constant.COM_LOG_ORDER_EVENT.placeOrder.name(), "后台下单");
			// 支付给驴妈妈，订单资源需要
			if (orderInfo.isPayToLvmama() && orderInfo.isNeedResourceConfirm()) {
				saveComLog("ORD_ORDER", orderId, operatorId,
						Constant.COM_LOG_ORDER_EVENT.orderAuditGoing.name(),
						"领单");
				// 资源审核不通过
				if (!orderInfo.isApprovePass()) {
					saveComLog("ORD_ORDER", orderId, operatorId,
							Constant.COM_LOG_ORDER_EVENT.approveInfo.name(),
							"信息审核通过");
				} else {
					// 资源审核通过
					saveComLog("ORD_ORDER", orderId, operatorId,
							Constant.COM_LOG_ORDER_EVENT.approvePass.name(),
							"下单时资源已确认，直接审核通过");
				}
			}
		} else {
			// 前台下单
			saveComLog("ORD_ORDER", orderId, userId,
					Constant.COM_LOG_ORDER_EVENT.create.name(), "用户前台下单");
		}
	}

	/**
	 * 创建订单.
	 * 
	 * @param buyInfo
	 *            购买信息
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人
	 * @return 订单创建DTO{@link OrderInfoDTO}
	 */
	@Override
	public OrderInfoDTO buildOrderInfo(final BuyInfo buyInfo,
			final Long orderId, 
			final Map<Long,SupBCertificateTarget> bcertTargetMap,
			final Map<Long,List<SupPerformTarget>> performTargetMap,
			final Map<Long, OrdOrderItemMeta> ordOrderItemMateMap,
			final String operatorId) {
		// 初始化订单创建DTO
		OrderInfoDTO orderInfo = this.initOrderInfo(buyInfo, orderId,bcertTargetMap,performTargetMap,ordOrderItemMateMap,operatorId);
		// 保存订单创建DTO
		orderInfo = this.saveOrderInfo(orderInfo);
//		if(orderInfo.getIsShHolidayOrder()){
//			String result = supplierOrderService.createOrder(orderInfo);
//			if(!"SUCCESS".equalsIgnoreCase(result)){
//				orderDAO.cancelOrder(orderInfo.getOrderId(), "SYSTEM", "第三方下单失败  ");
//			}
//		}
		// 保存操作日志
		this.saveLog(orderInfo);
		return orderInfo;
	}

	/**
	 * 构造采购产品订单子项数据库主键.
	 * 
	 * @return 采购产品订单子项数据库主键
	 */
	private Long makeOrderItemMetaId() {
		return orderItemMetaDAO.makeOrderItemMetaId();
	}

	/**
	 * 是否包含通关码.
	 * 
	 * @return true代表包含，false代表不包含
	 */
	private boolean isContainPassport(final List<SupPerformTarget> supPerformTargetList) {
		if(supPerformTargetList != null){
			for (SupPerformTarget supPerformTarget : supPerformTargetList) {
				if (Constant.CCERT_TYPE.DIMENSION.name().equalsIgnoreCase(
						supPerformTarget.getCertificateType())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 处理优惠券
	 * @param orderInfo
	 * @return
	 */
	private OrderInfoDTO markFavor (final OrderInfoDTO orderInfo) {	
		FavorResult favorResult = orderInfo.getBuyInfo().getFavorResult();
		if(favorResult == null){
			return orderInfo;
		}
		
		try{
			Long discountAmount = favorResult.sumAllFavorDiscountAmount(orderInfo);
			OrdOrderAmountItem amountItem = BuildOrderInfoDTO.markOrderAmountItem(
					orderInfo.getOrderId(), orderInfo.getOughtPay());
			orderInfo.setAmountItem(amountItem);
					
			if (null != discountAmount && discountAmount > 0L ) {
				
				//记录优惠系统日志，累加已优惠总额，供后面优惠券计算
				Long totalProductDiscountAmount = 0l;
				for(int i = 0; i < orderInfo.getOrdOrderItemProds().size(); i++){
					OrdOrderItemProd ordOrderItemProd = orderInfo.getOrdOrderItemProds().get(i);
					FavorProductResult favorProductResult = favorResult.getMatchingFavorProductResultByOrderItem(ordOrderItemProd);
				    if(favorProductResult != null){
				    	long total_discountAmount = 0;
				    	List<ProductFavorStrategy> productFavorStrategyList = favorProductResult.getFavorStrategyList();
						for(int j = 0; j < productFavorStrategyList.size();j++){
							long currentFavorDiscount = productFavorStrategyList.get(j).getDiscountAmount(ordOrderItemProd, total_discountAmount);
							total_discountAmount += currentFavorDiscount;
							totalProductDiscountAmount += currentFavorDiscount;
							MarkCouponUsage markCouponUsage = new MarkCouponUsage();
							markCouponUsage.setCouponCodeId(productFavorStrategyList.get(j).getBusinessCoupon().getBusinessCouponId());
							markCouponUsage.setObjectId(orderInfo.getOrderId());
							markCouponUsage.setSubObjectIdA(ordOrderItemProd.getProductId());//设置优惠对应的产品ID
							markCouponUsage.setSubObjectIdB(ordOrderItemProd.getProdBranchId());//设置优惠对应的产品类别ID
							markCouponUsage.setCreateTime(new Date());
							markCouponUsage.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER_ITEM_PROD.name());
							markCouponUsage.setStrategy(productFavorStrategyList.get(j).getFavorType());
							markCouponUsage.setAmount(currentFavorDiscount);
							favorOrderService.saveCouponUsage(markCouponUsage);
						}
				    }
				}
				
				//记录优惠券日志
				List<OrderFavorStrategy> favorOrderStrategyList = favorResult.getFavorOrderResult().getFavorStrategyList();
				if(favorOrderStrategyList != null && favorOrderStrategyList.size() > 0){
					long total_discountAmount = 0;
					for(int i = 0; i < favorOrderStrategyList.size(); i++){
						long currentFavorDiscount = favorOrderStrategyList.get(i).getDiscountAmount(orderInfo, total_discountAmount + totalProductDiscountAmount);
						total_discountAmount += currentFavorDiscount;
						
						//记录优惠券明细
						OrdOrderAmountItem couponAmountItem = markCouponAmountItem(orderInfo.getOrderId(), 
								favorOrderStrategyList.get(i).getMarkCoupon(), currentFavorDiscount);
						orderInfo.setCouponAmountItem(couponAmountItem);
						
						//记录日志
						MarkCouponUsage markCouponUsage = new MarkCouponUsage();
						markCouponUsage.setObjectId(orderInfo.getOrderId());
						markCouponUsage.setCreateTime(new Date());
						markCouponUsage.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
						markCouponUsage.setCouponCodeId(favorOrderStrategyList.get(i).getMarkCouponCode().getCouponCodeId());
						markCouponUsage.setStrategy(favorOrderStrategyList.get(i).getFavorType());
						markCouponUsage.setAmount(currentFavorDiscount);
						favorOrderService.saveCouponUsage(markCouponUsage);
					}
				}
				
				
				//更新销售产品价格明细
				for(int i = 0; i < orderInfo.getOrdOrderItemProds().size(); i++){
					OrdOrderItemProd ordOrderItemProd = orderInfo.getOrdOrderItemProds().get(i);
					FavorProductResult favorProductResult = favorResult.getMatchingFavorProductResultByOrderItem(ordOrderItemProd);
				    if(favorProductResult != null){
				    	long total_discountAmount = 0;
				    	List<ProductFavorStrategy> productFavorStrategyList = favorProductResult.getFavorStrategyList();
						for(int j = 0; j < productFavorStrategyList.size();j++){
							long currentFavorDiscount = productFavorStrategyList.get(j).getDiscountAmount(ordOrderItemProd, total_discountAmount);
							total_discountAmount += currentFavorDiscount;
						}
						
						//更新销售产品价格明细
						ordOrderItemProd.setPrice(ordOrderItemProd.getPrice()-total_discountAmount / ordOrderItemProd.getQuantity());
						orderItemProdDAO.updateByPrimaryKey(ordOrderItemProd);
				    }
				}
				
			}
			Long oughtPay = orderInfo.getOughtPay() - discountAmount; 
			orderInfo.setOughtPay((oughtPay < 0?0:oughtPay));
			orderInfo.setOrderPay((oughtPay < 0?0:oughtPay));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return orderInfo; 
	}
	
	
	private OrdOrderAmountItem markCouponAmountItem(final Long orderId,
			final MarkCoupon markCoupon, final Long actualDiscount) {
		final OrdOrderAmountItem couponAmountItem = new OrdOrderAmountItem();
		couponAmountItem.setItemAmount(actualDiscount);
		couponAmountItem.setOrderId(orderId);
		couponAmountItem.setItemName(markCoupon.getCouponName());
		couponAmountItem
				.setOderAmountType(Constant.ORDER_AMOUNT_TYPE.ORDER_COUPON_AMOUNT
						.name());
		return couponAmountItem;
	}
	
	

//	/**
//	 * 处理优惠券.
//	 * 
//	 * @param orderInfo
//	 *            订单创建DTO{@link OrderInfoDTO}
//	 * @return 订单创建DTO{@link OrderInfoDTO}
//	 */
//	private OrderInfoDTO markCoupon(final OrderInfoDTO orderInfo) {
//		// 构建订单金额明细，必须放在优惠券处理之前，优惠券处理会修改订单应付金额
//		OrdOrderAmountItem amountItem = BuildOrderInfoDTO.markOrderAmountItem(
//				orderInfo.getOrderId(), orderInfo.getOughtPay());
//		orderInfo.setAmountItem(amountItem);
//		// 处理优惠券
//		if (UtilityTool.isValid(orderInfo.getBuyInfo().getCheckedCoupon())
//				&& UtilityTool.isValid(orderInfo.getBuyInfo().getCheckedCoupon()
//						.getCouponId())
//				&& Boolean.TRUE.toString().equals(
//						orderInfo.getBuyInfo().getCheckedCoupon().getChecked())) {
//			final Coupon coupon = orderInfo.getBuyInfo().getCheckedCoupon();
//			final MarkCoupon markCoupon = markCouponDAO
//					.selectByPrimaryKey(coupon.getCouponId());
//			final Long productAmount = couponLogic.getMarkCouponProductPrice(
//					orderInfo, markCoupon);
//			if (productAmount != 0) {
//				markCoupon.setAmount(productAmount);
//			}
//			final Pair<Long,MarkCoupon> pair =
//			 couponLogic.countOrderCouponAmount(
//					//orderInfo.getOughtPay(),OrderUitl.getEnjoyCouponamount(orderInfo.getOrdOrderItemProds()),//caokun modify for 附加产品优惠券
//					orderInfo.getOughtPay(),
//					orderInfo.calcTotalPersonQuantity(), markCoupon);
//			final long oughtPay = pair.getFirst();
//			final MarkCouponCode markCouponCode = couponLogic
//					.getMarkCouponCodeByCouponIdAndCode(coupon.getCouponId(),
//							coupon.getCode());
//			// 构建订单优惠金额明细
//			OrdOrderAmountItem couponAmountItem = BuildOrderInfoDTO
//					.markCouponAmountItem(orderInfo.getOrderId(), markCoupon);
//			orderInfo.setMarkCoupon(pair.getSecond());
//			orderInfo.setMarkCouponCode(markCouponCode);
//			orderInfo.setCouponAmountItem(couponAmountItem);
//			orderInfo.setOughtPay((oughtPay < 0?0:oughtPay));
//			orderInfo.setOrderPay((oughtPay < 0?0:oughtPay));
//			// 保存优惠券
//			saveCoupon(orderInfo);
//		}
//		return orderInfo;
//	}

	/**
	 * 保存游玩人列表.
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 */
	private void saveOrdPerson(final OrderInfoDTO orderInfo) {
		for (OrdPerson ordPerson : orderInfo.getPersonList()) {
			orderPersonDAO.insertSelective(ordPerson);
		}
	}

	/**
	 * 保存发票同时保存发票地址及关联信息.
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 */
	private OrderInfoDTO saveOrdInvice(OrderInfoDTO orderInfo) {
		if (UtilityTool.isNotNull(orderInfo.getInvoiceList())) {
			for (OrdInvoice ordInvoice : orderInfo.getInvoiceList()) {
				
				ordInvoice.setAmount(orderInfo.getOughtPay());//对发票金额放值
				ordInvoice.setCompany(InvoiceUtil.getInvoiceCompany(orderInfo.getOrderType()).name());//按订单类型给发票主体
				Long invoiceId=orderInvoiceDAO.insert(ordInvoice);
				OrdInvoiceRelation relation=new OrdInvoiceRelation();
				relation.setOrderId(orderInfo.getOrderId());
				relation.setInvoiceId(invoiceId);
				
				orderInvoiceRelationDAO.insert(relation);
				
				
				//促成发票送货地址
				if(UtilityTool.isNotNull(orderInfo.getBuyInfo().getInvoiceAddress())){
					OrdPerson person=PersonUtil.converPerson(orderInfo.getBuyInfo().getInvoiceAddress(), Constant.OBJECT_TYPE.ORD_INVOICE, invoiceId);
					orderPersonDAO.insertSelective(person);
				}
				
				//更新订单发票flag
				orderInfo.setNeedInvoice("true");
			}
		}		
		return orderInfo;
	}

	/**
	 * 保存销售产品订单子项.
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 * @return 订单创建DTO{@link OrderInfoDTO}
	 */
	private OrderInfoDTO saveOrdOrderItemProd(OrderInfoDTO orderInfo) {
		if (UtilityTool.isNotNull(orderInfo.getOrdOrderItemProds())) {
			boolean isAperiodic = orderInfo.getBuyInfo().IsAperiodic();
			// 酒店单房型订单应付金额，为酒店单房型计算订单总金额使用
			Long hotelOughtPay = 0L;
			// 产品订单应付金额，为酒店单房型计算订单总金额使用
			Long oughtPay = 0L;
			
			
			// 资源状态，为酒店单房型计算订单总金额使用
			String resourceConfirm = Boolean.FALSE.toString();
			for (OrdOrderItemProd ordOrderItemProd : orderInfo
					.getOrdOrderItemProds()) {
				ordOrderItemProd.setOrderId(orderInfo.getOrderId());
				// 销售产品订单子项层级接口
				final IProductOrder productOrder = (IProductOrder) buildOrderFactory
						.chooseOrderServiceImpl(ordOrderItemProd);
				ordOrderItemProd = productOrder
						.modifyOrderInfo(orderInfo,ordOrderItemProd);
							
				
				for (OrdOrderItemMeta orderItemMeta : ordOrderItemProd
						.getOrdOrderItemMetas()) {
					// 构造销售价格
					final long sellPrice = BuildOrderInfoDTO.markSellPrice(
							ordOrderItemProd.getSumSettlementPrice(),
							ordOrderItemProd.getPrice(), orderItemMeta);
					orderItemMeta.setSellPrice(sellPrice);
				}
				
				// 订单是否需要电子签约判断标识
				final boolean needEContractFlag = Constant.ECONTRACT_TYPE.NEED_ECONTRACT
						.name().equals(ordOrderItemProd.getNeedEContract());
				// 构建订单是否需要电子签约
				orderInfo = BuildOrderInfoDTO.markNeedEContract(orderInfo,
						needEContractFlag);

				// 主产品订单应付金额，为酒店单房型计算订单总金额使用
				// 酒店单房型订单如果跨天会执行多次，每次结果都相同，只需要取第一次结果即可
				if (UtilityTool.isValid(ordOrderItemProd.getHotelOughtPay())
						&& (ordOrderItemProd.getHotelOughtPay() > 0)) {
					hotelOughtPay += ordOrderItemProd.getHotelOughtPay();//多个单房型时相总价相加
				}
				// 产品订单应付金额，为酒店单房型计算订单总金额使用
				if (UtilityTool.isValid(ordOrderItemProd.getOughtPay())
						&& (ordOrderItemProd.getOughtPay() > 0)) {
					if(!(ordOrderItemProd.hasDefault()&&orderInfo.hasSelfPack())){//超级自由行的主产品不计算金额
						oughtPay += ordOrderItemProd.getOughtPay();
					}
				}
				// 资源状态，为酒店单房型计算订单总金额使用
				if (UtilityTool.isValid(ordOrderItemProd.getResourceStatus())) {
					if (Boolean.TRUE.toString().equals(
							ordOrderItemProd.getResourceStatus())) {
						resourceConfirm = ordOrderItemProd.getResourceStatus();
					}
				}
				//不定期订单清除游玩时间
				if(isAperiodic) {
					ordOrderItemProd.setVisitTime(null);
				}
				// 保存销售产品订单子项
				orderItemProdDAO.insert(ordOrderItemProd);
				saveOrdOrderItemMeta(orderInfo, ordOrderItemProd);
			}

			//查找分公司并加入到订单当中
			OrdOrderItemProd mainProduct=orderInfo.getMainProduct();
			if(mainProduct!=null){
				ProdProduct prod=prodProductDAO.selectProductDetailByPrimaryKey(mainProduct.getProductId());
				if(prod!=null){
					orderInfo.setFilialeName(prod.getFilialeName());
				}				
			}
			// 酒店单房型订单 重新计算以下值
			if (hotelOughtPay > 0) {
				// 重新计算订单应付金额
				orderInfo.setOughtPay(hotelOughtPay + oughtPay);
				orderInfo.setOrderPay(hotelOughtPay + oughtPay);
				// 重新计算订单资源确认状态
				orderInfo.setResourceConfirm(resourceConfirm);
				// 重新计算订单资源审核状态
				if (Boolean.TRUE.toString().equalsIgnoreCase(
						orderInfo.getBuyInfo().getResourceConfirmStatus())) {
					orderInfo
							.setResourceConfirmStatus(Constant.ORDER_RESOURCE_STATUS.AMPLE
									.name());
				} else {
					orderInfo.setResourceConfirmStatus(BuildOrderInfoDTO
							.makeResourceConfirmStatus(Boolean
									.valueOf(resourceConfirm)));
				}
				// 重新构建订单审核状态
				orderInfo = BuildOrderInfoDTO.markApproveStatus(
						orderInfo,
						!Constant.ORDER_RESOURCE_STATUS.AMPLE.name()
								.equalsIgnoreCase(
										orderInfo.getResourceConfirmStatus())
								&& Boolean.valueOf(orderInfo
										.getResourceConfirm()));
				// 重新构建订单领单状态
				BuildOrderInfoDTO.makeTaken(orderInfo);
			}
		}
		return orderInfo;
	}

	/**
	 * 保存采购产品订单子项.
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 * @param ordOrderItemProd
	 *            销售产品订单子项
	 */
	private void saveOrdOrderItemMeta(final OrderInfoDTO orderInfo,
			final OrdOrderItemProd ordOrderItemProd) {
		boolean isAperiodic = orderInfo.getBuyInfo().IsAperiodic();
		if (UtilityTool.isNotNull(ordOrderItemProd.getOrdOrderItemMetas())) {
			for (OrdOrderItemMeta ordOrderItemMeta : ordOrderItemProd
					.getOrdOrderItemMetas()) {
				ordOrderItemMeta.setOrderId(orderInfo.getOrderId());
				ordOrderItemMeta.setOrderItemId(ordOrderItemProd
						.getOrderItemProdId());
				// 保存采购产品订单子项
				BuildOrderInfoDTO.makeOrderItemMetaNights(metaProductDAO,ordOrderItemProd, ordOrderItemMeta);
				if(isAperiodic) {
					ordOrderItemMeta.setVisitTime(null);
				}
				orderItemMetaDAO.insert(ordOrderItemMeta);
			}
			// 销售产品订单子项层级接口
			final IProductOrder productOrder = (IProductOrder) buildOrderFactory
			.chooseOrderServiceImpl(ordOrderItemProd);
			// 保存附加数据
			productOrder.saveAdditionData(ordOrderItemProd);
			
		}
	}

	/**
	 * 保存ComAudit.
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 */
	private void saveComAudit(final OrderInfoDTO orderInfo) {
		//
		if (UtilityTool.isValid(orderInfo.getOperatorId())
				&& !orderInfo.getOperatorId().equalsIgnoreCase(
						orderInfo.getBuyInfo().getUserId())
				&& orderInfo.isPayToLvmama()
				&& orderInfo.isNeedResourceConfirm()) {
			ComAudit comAudit = new ComAudit();
			comAudit.setObjectId(orderInfo.getOrderId());
			comAudit.setObjectType(Constant.AUDIT_OBJECT_TYPE.ORD_ORDER.name());
			comAudit.setOperatorName(orderInfo.getTakenOperator());
			comAudit.setAuditStatus(Constant.AUDIT_STATUS.AUDIT_GOING.name());
			orderAuditDAO.insert(comAudit);
		}
	}

	/**
	 * 保存快递.
	 * 
	 * @param orderId
	 *            订单ID
	 */
	private void saveOrdExpress(final Long orderId) {
		OrdExpress ordExpress = new OrdExpress();
		ordExpress.setObjectId(orderId);
		ordExpress.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
		orderExpressDAO.insert(ordExpress);
	}

	/**
	 * @deprecated
	 * 保存优惠券.
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 */
	private void saveCoupon(final OrderInfoDTO orderInfo) {
//		// 更新B类优惠券
//		updateBcouponUsed(orderInfo.getMarkCouponCode(),
//				couponLogic.isBCouponCode(orderInfo.getMarkCoupon()));
//		// 保存A类优惠券
//		this.couponLogic.saveCouponUsage(orderInfo.getMarkCoupon(), orderInfo,
//				orderInfo.getMarkCouponCode());
	}

	/**
	 * @deprecated
	 * 更新B类优惠券.
	 * 
	 * @param markCouponCode
	 *            markCouponCode
	 * @param flag
	 *            判断标识
	 */
	private void updateBcouponUsed(final MarkCouponCode markCouponCode,
			final boolean flag) {
		if (flag) {
			//this.couponLogic.updateBcouponUsed(markCouponCode);
		}
	}

	/**
	 * 保存订单价格明细.
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 */
	private void saveAmountItem(final OrderInfoDTO orderInfo) {
		// 保存订单优惠券价格明细
		saveAmountItem(orderInfo.getOrderId(), orderInfo.getCouponAmountItem(),
				UtilityTool.isValid(orderInfo.getCouponAmountItem().getOrderId()));
		// 保存订单价格明细
		saveAmountItem(orderInfo.getOrderId(), orderInfo.getAmountItem(),
				UtilityTool.isValid(orderInfo.getAmountItem()));
	}

	/**
	 * 保存订单价格明细.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param ordOrderAmountItem
	 *            订单价格明细
	 * @param flag
	 *            判断标识
	 */
	private void saveAmountItem(final Long orderId,
			final OrdOrderAmountItem ordOrderAmountItem, final boolean flag) {
		if (flag) {
			ordOrderAmountItem.setOrderId(orderId);
			orderAmountItemDAO.insert(ordOrderAmountItem);
		}
	}

	/**
	 * 保存操作日志.
	 * 
	 * @param objectType
	 *            objectType
	 * @param objectId
	 *            objectId
	 * @param operatorName
	 *            operatorName
	 * @param logType
	 *            logType
	 * @param logName
	 *            logName
	 */
	private void saveComLog(final String objectType, final Long objectId,
			final String operatorName, final String logType,
			final String logName) {
		final ComLog log = new ComLog();
		log.setObjectType(objectType);
		log.setObjectId(objectId);
		log.setOperatorName(operatorName);
		log.setLogType(logType);
		log.setLogName(logName);
		comLogDAO.insert(log);
	}

	@Override
	public Map<Item,List<ProdProductBranchItem>> buildProductBranchItem(BuyInfo buyInfo) {
		Map<Item,List<ProdProductBranchItem>> branchItemList = new HashMap<Item,List<ProdProductBranchItem>>();
		List<ProdProductBranchItem> prodProductBranchItemList = null;
		for (Item item : buyInfo.getItemList()) {
			// 销售产品订单子项数量必须大于0
			if (item.getQuantity() > 0) {
				prodProductBranchItemList = prodProductBranchItemDAO
						.selectBranchItemByProdBranchId(item.getProductBranchId());
				branchItemList.put(item, prodProductBranchItemList);
			}
		}
		return branchItemList;
	}

	public void setMetaTravelCodeDAO(MetaTravelCodeDAO metaTravelCodeDAO) {
		this.metaTravelCodeDAO = metaTravelCodeDAO;
	}
	
	public void setOrdUserOrderDAO(OrdUserOrderDAO ordUserOrderDAO) {
		this.ordUserOrderDAO = ordUserOrderDAO;
	}

	/**
	 * 订单整合（BEE和VST）
	 * 
	 * @param orderInfo
	 */
	private void saveOrdUserOrder(OrderInfoDTO orderInfo) {
		if (orderInfo != null) {
			OrdUserOrder ordUserOrder = new OrdUserOrder();
			ordUserOrder.setBizType(OrdUserOrder.BIZ_TYPE.BIZ_BEE.name());
			ordUserOrder.setCreateTime(orderInfo.getCreateTime());
			ordUserOrder.setOrderId(orderInfo.getOrderId());
			ordUserOrder.setOrderType(orderInfo.getOrderType());
			ordUserOrder.setUserId(orderInfo.getBuyInfo().getUserNo());
			
			ordUserOrderDAO.insertSelective(ordUserOrder);
		}
	}
}
