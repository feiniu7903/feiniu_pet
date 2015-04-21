package com.lvmama.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.meta.MetaProductHotel;
import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.ord.OrderInfoDTO;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.BuyInfo.OrdTimeInfo;
import com.lvmama.comm.bee.vo.ord.Invoice;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.utils.PersonUtil;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.vo.Constant;
import com.lvmama.prd.dao.MetaProductDAO;
import com.lvmama.prd.dao.ProdProductDAO;
import com.lvmama.prd.dao.ProdTimePriceDAO;
import com.lvmama.prd.logic.ProductTimePriceLogic;

/**
 * 构建订单创建DTO.
 * 
 * <pre>
 * </pre>
 * 
 * @author liwenzhan
 * @author sunruyi
 * @version Super订单创建重构
 * @since Super订单创建重构
 */
public final class BuildOrderInfoDTO {
	/**
	 * 构造函数.
	 */
	private BuildOrderInfoDTO() {
	}

	/**
	 * 构建订单领单状态.
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 * @return 订单创建DTO{@link OrderInfoDTO}
	 */
	static OrderInfoDTO makeTaken(final OrderInfoDTO orderInfo) {
		// 操作人UtilityTool.isNotNull单)
		if (UtilityTool.isValid(orderInfo.getOperatorId())
				&& !orderInfo.getOperatorId().equalsIgnoreCase(
						orderInfo.getBuyInfo().getUserId())) {
			// 订单的支付方式是支付给驴妈妈并且需要资源审核
			if (orderInfo.isPayToLvmama() && orderInfo.isNeedResourceConfirm()) {
				final Date now = new Date();
				orderInfo.setTaken(Constant.AUDIT_TAKEN_STATUS.TAKEN.name());
				orderInfo.setTakenOperator(orderInfo.getOperatorId());
				orderInfo.setDealTime(now);
				// 如果订单未审核通过则设置为信息审核通过
				if (!orderInfo.isApprovePass()) {
					orderInfo
							.setApproveStatus(Constant.ORDER_APPROVE_STATUS.INFOPASS
									.name());
					orderInfo.setInfoApproveStatus(Constant.INFO_APPROVE_STATUS.INFOPASS.name());
					orderInfo.setApproveTime(now);
				}
			}
		} else {
			// 操作人是下单人 (前台下单) 
			orderInfo.setTaken(Constant.AUDIT_TAKEN_STATUS.UNTAKEN.name());
		}
		return orderInfo;
	}

	/**
	 * 构建订单游玩人列表.
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 * @return 订单创建DTO{@link OrderInfoDTO}
	 */
	static OrderInfoDTO makePersonList(final OrderInfoDTO orderInfo) {
		final List<OrdPerson> personList = new ArrayList<OrdPerson>();
		boolean hasTraveller=false;
		Person contactPerson=null;
		for (Person person : orderInfo.getBuyInfo().getPersonList()) {
			OrdPerson ordPerson = PersonUtil.converPerson(person,Constant.OBJECT_TYPE.ORD_ORDER,orderInfo.getOrderId());			
			personList.add(ordPerson);
			if(Constant.ORD_PERSON_TYPE.CONTACT.name().equals(ordPerson.getPersonType())){
				contactPerson = person;
			}
			if(!hasTraveller&&Constant.ORD_PERSON_TYPE.TRAVELLER.name().equals(ordPerson.getPersonType())){
				hasTraveller=true;
			}
		}
		if((Constant.PRODUCT_TYPE.TICKET.name().equals(orderInfo.getOrderType())
				||Constant.PRODUCT_TYPE.HOTEL.name().equals(orderInfo.getOrderType()))
				&&!hasTraveller&&contactPerson!=null){
			OrdPerson ordPerson = PersonUtil.converPerson(contactPerson, Constant.OBJECT_TYPE.ORD_ORDER, orderInfo.getOrderId());
			ordPerson.setPersonType(Constant.ORD_PERSON_TYPE.TRAVELLER.name());
			personList.add(ordPerson);
		}
		orderInfo.setPersonList(personList);
		return orderInfo;
	}
		
	/**
	 * 构建订单发票列表.
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 * @return 订单创建DTO{@link OrderInfoDTO}
	 */
	static OrderInfoDTO makeInvoiceList(final OrderInfoDTO orderInfo) {
		final List<OrdInvoice> invoiceList = new ArrayList<OrdInvoice>();
		if (UtilityTool.isNotNull(orderInfo.getBuyInfo().getInvoiceList())) {
			// 订单需要发票
			orderInfo.setNeedInvoice(Boolean.TRUE.toString());
			for (Invoice invoice : orderInfo.getBuyInfo().getInvoiceList()) {
				OrdInvoice ordInvoice = new OrdInvoice();
				ordInvoice.setTitle(invoice.getTitle());
				ordInvoice.setDetail(invoice.getDetail());
				ordInvoice.setMemo(invoice.getMemo());
				ordInvoice.setAmount(invoice.getAmount());
//				ordInvoice.setOrderId(orderInfo.getOrderId());
				ordInvoice.setStatus(Constant.INVOICE_STATUS.UNBILL.name());
				ordInvoice.setUserId(orderInfo.getUserId());
				invoiceList.add(ordInvoice);
			}
		}
		orderInfo.setInvoiceList(invoiceList);
		return orderInfo;
	}

	/**
	 * 构建销售产品订单子项列表. <br>
	 * 计算游玩日期，取最小
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 * @param prodProductDAO
	 * 			  产品DAO{@link ProdProductDAO}}
	 * @return 订单创建DTO{@link OrderInfoDTO}
	 */
	static OrderInfoDTO makeOrdOrderItemProds(final OrderInfoDTO orderInfo,final ProdProductDAO prodProductDAO, 
			ProdTimePriceDAO prodTimePriceDAO, ProductTimePriceLogic productTimePriceLogic) {
		List<OrdOrderItemProd> ordOrderItemProdList = new ArrayList<OrdOrderItemProd>();
		if (UtilityTool.isNotNull(orderInfo.getBuyInfo().getItemList())) {
			String cancelStrategy = "";
			for (Item item : orderInfo.getBuyInfo().getItemList()) {
				// 销售产品订单子项数量必须大于0
				if (item.getQuantity() > 0) {
					OrdOrderItemProd ordOrderItemProd = new OrdOrderItemProd();
					ordOrderItemProd.setProductId(item.getProductId());
					ordOrderItemProd.setProdBranchId(item.getProductBranchId());
					ordOrderItemProd.setQuantity(Long.valueOf(item
							.getQuantity()));
					ordOrderItemProd.setVisitTime(item.getVisitTime());
					ordOrderItemProd.setFaxMemo(item.getFaxMemo());
					ordOrderItemProd.setTimeInfoList(item.getTimeInfoList());
					ordOrderItemProd.setJourneyProductId(item.getJourneyProductId());
					ordOrderItemProd.setIsDefault(item.getIsDefault());		
					TimePrice timePrice = null;
					if(orderInfo.hasTodayOrder()){
						//当天预订的订单在订单子项当中记录最早入园时间与最晚入园时间，方便订单的最晚废单时间以及游玩的入园时间取值使用
						timePrice = productTimePriceLogic.calcCurrentProdTimePrice(item.getProductBranchId(), item.getVisitTime());
						if(timePrice!=null){
							ordOrderItemProd.setLatestUseTimeDate(timePrice.getLatestUseTimeDate());
							ordOrderItemProd.setEarliestUseTimeDate(timePrice.getEarliestUseTimeDate());
							ordOrderItemProd.setPrice(timePrice.getPrice());
						}			
					}else{
						//在此取销售产品时间价格表中的最晚取消小时数,以及提前预订小时数
						timePrice = productTimePriceLogic.calcProdTimePrice(item.getProductBranchId(), item.getVisitTime());
						if(timePrice!=null){
							ordOrderItemProd.setLastCancelHour(timePrice.getCancelHour());
							ordOrderItemProd.setAheadHour(timePrice.getAheadHour());
							ordOrderItemProd.setPrice(timePrice.getPrice());
						}		
					}
					//不定期构造订单子项上有效期
					if(orderInfo.IsAperiodic()) {
						ordOrderItemProd.setValidBeginTime(item.getValidBeginTime());
						ordOrderItemProd.setValidEndTime(item.getValidEndTime());
						ordOrderItemProd.setInvalidDate(item.getInvalidDate());
						ordOrderItemProd.setInvalidDateMemo(item.getInvalidDateMemo());
					}
					
					if(timePrice != null) {
						//保存多行程id
						if("true".equalsIgnoreCase(ordOrderItemProd.getIsDefault()) && timePrice.getMultiJourneyId() != null) {
							ordOrderItemProd.setMultiJourneyId(timePrice.getMultiJourneyId());
						}
						
						String stragtegy = timePrice.getCancelStrategy();
						if(StringUtils.isNotEmpty(stragtegy)) {
							if(StringUtils.isEmpty(cancelStrategy)) {
								cancelStrategy = stragtegy;
							} else {
								if(Constant.CANCEL_STRATEGY.FORBID.name().equalsIgnoreCase(cancelStrategy)) {   //不退不改,最高级
								} else if(Constant.CANCEL_STRATEGY.MANUAL.name().equalsIgnoreCase(cancelStrategy)) {  //人工确认退改,次于最高
									if(Constant.CANCEL_STRATEGY.FORBID.name().equalsIgnoreCase(stragtegy)) {
										cancelStrategy = stragtegy;
									}
								} else { //可退改,最低级
									cancelStrategy = stragtegy;
								}
							}
						}
					}
					
					ordOrderItemProdList.add(ordOrderItemProd);
					
					// 计算游玩日期，取最小
					if (!UtilityTool.isValid(orderInfo.getVisitTime())) {
						orderInfo.setVisitTime(item.getVisitTime());
					} else if (orderInfo.getVisitTime().getTime() > item
							.getVisitTime().getTime()) {
						orderInfo.setVisitTime(item.getVisitTime());
					}
				}
			}
			if(orderInfo.hasTodayOrder()){
				orderInfo.setCancelStrategy(Constant.CANCEL_STRATEGY.FORBID.name());
			}else{
				orderInfo.setCancelStrategy(cancelStrategy);
			}
		}
		orderInfo.setOrdOrderItemProds(ordOrderItemProdList);
		return orderInfo;
	}

	/**
	 * 构建订单是否发送通关码.
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 * @param passportFlag
	 *            订单是否发送通关码判断标识
	 * @return 订单创建DTO{@link OrderInfoDTO}
	 */
	static OrderInfoDTO markPassport(final OrderInfoDTO orderInfo,
			final boolean passportFlag) {
		if (passportFlag) {
			orderInfo.setPassport(Boolean.TRUE.toString());
		}
		return orderInfo;
	}

	/**
	 * 构建订单审核状态.
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 * @param approveStatusFlag
	 *            订单审核状态判断标识
	 * @return 订单创建DTO{@link OrderInfoDTO}
	 */
	static OrderInfoDTO markApproveStatus(final OrderInfoDTO orderInfo,
			final boolean approveStatusFlag) {
		if (approveStatusFlag) {
			orderInfo.setApproveStatus(Constant.ORDER_APPROVE_STATUS.UNVERIFIED
					.name());
			orderInfo.setInfoApproveStatus(Constant.INFO_APPROVE_STATUS.UNVERIFIED
					.name());
		} else {
			orderInfo.setApproveStatus(Constant.ORDER_APPROVE_STATUS.VERIFIED
					.name());
			orderInfo.setInfoApproveStatus(Constant.INFO_APPROVE_STATUS.INFOPASS
					.name());
			orderInfo.setApproveTime(new Date());
		}
		return orderInfo;
	}

	/**
	 * 构造销售价格.<br>
	 * 用于利润计算
	 * 
	 * @param sumSettlementPrice
	 *            总结算金额
	 * @param orderItemProdPrice
	 *            销售产品单价
	 * @param ordOrderItemMeta
	 *            采购产品订单子项
	 * @return 销售价格
	 */
	 static long markSellPrice(final long sumSettlementPrice,
			final long orderItemProdPrice,
			final OrdOrderItemMeta ordOrderItemMeta) {
		long sellPrice = 0L;
		// 总结算金额大于0
		if (sumSettlementPrice > 0) {
			// 之所以这么计算的原因可能是正常情况下与销售产品单价相等，特殊情况下不等
			sellPrice = Math
					.round((ordOrderItemMeta.getSettlementPrice()*ordOrderItemMeta.getProductQuantity() * orderItemProdPrice)
							/ sumSettlementPrice);
		}
		return sellPrice;
	}

	/**
	 * 构建订单是否需要电子签约.
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 * @param needEContractFlag
	 *            订单是否需要电子签约判断标识
	 * @return 订单创建DTO{@link OrderInfoDTO}
	 */
	static OrderInfoDTO markNeedEContract(final OrderInfoDTO orderInfo,
			final boolean needEContractFlag) {
		if (needEContractFlag&&!orderInfo.hasSelfPack()) {//现在的针对自由行的产品添加该判断，对自由行的产品不签合同.
			orderInfo.setNeedContract(Constant.ECONTRACT_TYPE.NEED_ECONTRACT
					.name());
		}
		return orderInfo;
	}

	/**
	 * @deprecated 需要修改
	 * 构建订单优惠金额明细.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param markCoupon
	 *            优惠券
	 * @return 订单优惠金额明细{@link OrdOrderAmountItem}
	 */
	static OrdOrderAmountItem markCouponAmountItem(final Long orderId,
			final MarkCoupon markCoupon) {
		final OrdOrderAmountItem couponAmountItem = new OrdOrderAmountItem();
//		couponAmountItem.setItemAmount(markCoupon.getYouhuiAmont());
//		couponAmountItem.setOrderId(orderId);
//		couponAmountItem.setItemName(markCoupon.getCouponName());
//		couponAmountItem
//				.setOderAmountType(Constant.ORDER_AMOUNT_TYPE.ORDER_COUPON_AMOUNT
//						.name());
		return couponAmountItem;
	}

	/**
	 * 构建订单金额明细.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param oughtPay
	 *            应付金额
	 * @return 订单金额明细{@link OrdOrderAmountItem}
	 */
	static OrdOrderAmountItem markOrderAmountItem(final Long orderId,
			final Long oughtPay) {
		OrdOrderAmountItem ordOrderAmountItem = new OrdOrderAmountItem();
		ordOrderAmountItem.setOrderId(orderId);
		ordOrderAmountItem.setItemAmount(oughtPay);
		ordOrderAmountItem.setItemName("订单金额");
		ordOrderAmountItem
				.setOderAmountType(Constant.ORDER_AMOUNT_TYPE.ORDER_AMOUNT
						.name());
		return ordOrderAmountItem;
	}

	/**
	 * 构造资源审核状态.
	 * 
	 * @param orderResourceConfirm
	 *            资源确认状态
	 * @return 资源审核状态
	 */
	static String makeResourceConfirmStatus(final boolean orderResourceConfirm) {
		return orderResourceConfirm ? Constant.ORDER_RESOURCE_STATUS.UNCONFIRMED
				.name() : Constant.ORDER_RESOURCE_STATUS.AMPLE.name();
	}
	
	/**
	 * 晚数设置操作 
	 * 酒店套餐设置为采购上的对应晚数
	 * 单房型设置为ord_order_item_prod_time上的行数
	 * 如果非酒店类型的销售产品设置为1晚
	 * @param metaProductDAO
	 * @param orderItemProd
	 * @param orderItemMeta
	 * @return
	 */
	static OrdOrderItemMeta makeOrderItemMetaNights(MetaProductDAO metaProductDAO,final OrdOrderItemProd orderItemProd,final OrdOrderItemMeta orderItemMeta){
		if(Constant.PRODUCT_TYPE.HOTEL.name().equals(orderItemMeta.getProductType())){
			MetaProductHotel metaProductHotel=(MetaProductHotel)metaProductDAO.getMetaProduct(orderItemMeta.getMetaProductId(), Constant.PRODUCT_TYPE.HOTEL.name());
			if(Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT.name().equals(orderItemMeta.getSubProductType())){				
				orderItemMeta.setNights(metaProductHotel.getNights());			
			}else{				
				List<OrdTimeInfo> tis=orderItemProd.getTimeInfoList();
				if(CollectionUtils.isNotEmpty(tis)){//非酒店单房型产品该值为空
					orderItemMeta.setNights((long)tis.size());					
				}else{
					orderItemMeta.setNights(metaProductHotel.getNights());
				}
			}
		}
		return orderItemMeta;
	}
}
