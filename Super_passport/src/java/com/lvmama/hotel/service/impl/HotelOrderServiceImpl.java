package com.lvmama.hotel.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderHotel;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaTime;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProdTime;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.ord.OrderHotelService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.hotel.model.Append;
import com.lvmama.hotel.model.OrdOrderItem;
import com.lvmama.hotel.model.OrderRequest;
import com.lvmama.hotel.model.OrderResponse;
import com.lvmama.hotel.model.OrderStatus;
import com.lvmama.hotel.model.RoomType;
import com.lvmama.hotel.model.TimePriceRequest;
import com.lvmama.hotel.service.HotelOrderService;
import com.lvmama.hotel.support.HotelOrderServiceSupport;

public class HotelOrderServiceImpl implements HotelOrderService {
	private static final Log log = LogFactory.getLog(HotelOrderServiceImpl.class);

	private Map<String, HotelOrderServiceSupport> hotelOrderServiceSupportMap;
	private OrderHotelService orderHotelService;
	private MetaProductBranchService metaProductBranchService;
	private ComLogService comLogService;
	private ComMessageService comMessageService;
	private OrderService orderServiceProxy;

	public String submitOrder(Long orderId) {
		String content = "订单" + orderId;
		try {
			OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			List<OrdOrderItem> masterItems = new ArrayList<OrdOrderItem>();
			List<OrdOrderItem> slaveItems = new ArrayList<OrdOrderItem>();
			fillItems(ordOrder, masterItems, slaveItems);
			for (OrdOrderItem masterItem : masterItems) {
				HotelOrderServiceSupport hotelOrderServiceSupport = hotelOrderServiceSupportMap.get(masterItem.getMeta().getSupplierId().toString());
				if (isPartnerHotelOrder(masterItem, hotelOrderServiceSupport)) {
					List<OrdOrderItem> masterSlaveItems = getMasterSlaveItems(masterItem, slaveItems);
					if (isCheckPassed(masterItem, masterSlaveItems, hotelOrderServiceSupport)) {
						List<OrdOrderHotel> ordOrderHotelList = new ArrayList<OrdOrderHotel>();
						OrderRequest orderRequest = buildOrderRequest(ordOrder, masterItem, masterSlaveItems, ordOrderHotelList);
						OrderResponse orderResponse = hotelOrderServiceSupport.submitOrder(orderRequest);
						if (orderResponse != null) {
							log.info("HotelOrderService orderResponse: " + orderResponse.toString());
							saveOrdOrderHotels(ordOrderHotelList, orderResponse);
							addComLog(orderResponse.getOrderStatus().getStatusCode() + " " + orderResponse.getOrderStatus().getStatusName(), orderId);
							content += "  下单成功";
						} else {
							content += "  下单失败";
							addComLog(content, orderId);
							comMessageService.addSystemComMessage(Constant.EVENT_TYPE.PAYMENT_FAILED_ORDER.name(), content, Constant.SYSTEM_USER);
						}
					} else {
						content += "  支付前后采购类别时间价格不一致";
						addComLog(content, orderId);
						comMessageService.addSystemComMessage(Constant.EVENT_TYPE.PAYMENT_FAILED_ORDER.name(), content, Constant.SYSTEM_USER);
					}
				}
			}
		} catch (Exception e) {
			log.error("HotelOrder Exception: ",e);
			content += "  供应商返回异常：" + e.getMessage();
			addComLog(content, orderId);
			comMessageService.addSystemComMessage(Constant.EVENT_TYPE.PAYMENT_FAILED_ORDER.name(), content, Constant.SYSTEM_USER);
		}
		return content;
	}

	public String cancelOrder(Long orderId) {
		String content = "订单" + orderId;
		try {
			List<OrdOrderHotel> ordHotelList = orderHotelService.searchOrderHotelByOrderId(orderId);
			OrderStatus orderStatus = null;
			if (ordHotelList != null && ordHotelList.size() > 0) {
				OrdOrderHotel hotel = ordHotelList.get(0);
				HotelOrderServiceSupport hotelOrderServiceSupport = hotelOrderServiceSupportMap.get(hotel.getHotelSupplierId().toString());
				orderStatus = hotelOrderServiceSupport.cancelOrder(hotel.getPartnerOrderId());
				if (orderStatus != null) {
					for (OrdOrderHotel ordHotel : ordHotelList) {
						ordHotel.setStatusCode(orderStatus.getStatusCode());
						ordHotel.setStatusName(orderStatus.getStatusName());
						orderHotelService.updateOrderStatus(ordHotel);
					}
					addComLog(orderStatus.getStatusCode() + " " + orderStatus.getStatusName(), orderId);
					content = "废单成功";
				} else {
					content = "废单失败";
					addComLog(content, orderId);
					comMessageService.addSystemComMessage(Constant.EVENT_TYPE.PAYMENT_FAILED_ORDER.name(), content, Constant.SYSTEM_USER);
				}
			}
		} catch (Exception e) {
			log.error("HotelOrder Exception: ",e);
			content += "  供应商返回异常：" + e.getMessage();
			addComLog(content, orderId);
			comMessageService.addSystemComMessage(Constant.EVENT_TYPE.PAYMENT_FAILED_ORDER.name(), content, Constant.SYSTEM_USER);
		}
		return content;
	}
	
	/**
	 * 酒店订单日志
	 */
	public void addComLog(String content, Long orderId) {
		ComLog log = new ComLog();
		log.setObjectId(orderId);
		log.setObjectType("ORD_ORDER");
		log.setOperatorName("SYSTEM");
		log.setLogName("酒店订单状态");
		log.setLogType("hotelOrderStatus");
		log.setContent(content);
		log.setParentType("ORD_ORDER");
		comLogService.addComLog(log);
	}

	private void fillItems(OrdOrder ordOrder, List<OrdOrderItem> masterItems, List<OrdOrderItem> slaveItems) {
		for (OrdOrderItemProd prod : ordOrder.getOrdOrderItemProds()) {
			if (!prod.isAdditionalProduct() && isHotelOrder(prod)) {
				for (OrdOrderItemMeta meta : prod.getOrdOrderItemMetas()) {
					MetaProductBranch branch = metaProductBranchService.getMetaBranch(meta.getMetaBranchId());
					if (!branch.isAdditional()) {
						masterItems.add(new OrdOrderItem(meta, prod));
					} else {
						slaveItems.add(new OrdOrderItem(meta, prod));
					}
				}
			} else {
				for (OrdOrderItemMeta meta : prod.getOrdOrderItemMetas()) {
					slaveItems.add(new OrdOrderItem(meta, prod));
				}
			}
		}
	}

	private Long getRoomCount(OrdOrderItem masterItem) {
		List<OrdOrderItemProdTime> prodTimeList = masterItem.getProd().getAllOrdOrderItemProdTime();
		if (!prodTimeList.isEmpty()) {
			return prodTimeList.get(0).getQuantity();
		}
		return masterItem.getProd().getQuantity();
	}
	
	private OrderRequest buildOrderRequest(OrdOrder ordOrder, OrdOrderItem masterItem, List<OrdOrderItem> masterSlaveItems, List<OrdOrderHotel> ordOrderHotelList) {
		OrderRequest orderRequest = new OrderRequest();

		// 房型
		orderRequest.setHotelID(masterItem.getMeta().getProductTypeSupplier());// 酒店ID
		orderRequest.setRoomTypeID(masterItem.getMeta().getProductIdSupplier());// 房型ID
		orderRequest.setCheckInDate(masterItem.getMeta().getBeginDate());// 入住时间
		orderRequest.setCheckOutDate(masterItem.getMeta().getEndDate());// 离店时间
		orderRequest.setQuantity(getRoomCount(masterItem));// 房间数量
		orderRequest.setCurrency("CNY");// 币种人民币
		orderRequest.setOrderId(String.valueOf(ordOrder.getOrderId()));
		orderRequest.setBedType(masterItem.getMeta().getBranchType());// 床型

		// 游客信息
		String travellerName="";
		String travellerPinyin="";
		String travellerMobile="";
		 for(OrdPerson p:ordOrder.getPersonList()){
				if(StringUtils.equals(p.getPersonType(),Constant.ORD_PERSON_TYPE.TRAVELLER.name())){
					travellerName=p.getName();
					travellerPinyin=p.getPinyin();
					travellerMobile=p.getMobile();
					break;
				}
		}
		orderRequest.setContactName(travellerName);
		orderRequest.setContactPinyin(travellerPinyin);
		orderRequest.setContactMobile(travellerMobile);

		addOrdOrderHotel(ordOrderHotelList, ordOrder.getOrderId(), masterItem.getMeta().getOrderItemMetaId(), masterItem.getMeta().getSupplierId(), masterItem.getMeta().getSupplier().getSupplierName(), masterItem.getMeta().getBeginDate());

		// 附加费用
		List<Append> appendList = new ArrayList<Append>();
		for (OrdOrderItem slaveItem : masterSlaveItems) {
			Append append = new Append();
			append.setProductIdSupplier(slaveItem.getMeta().getProductIdSupplier());
			append.setTimePriceDate(slaveItem.getMeta().getBeginDate());
			append.setTimePriceDateEnd(slaveItem.getMeta().getEndDate());
			append.setQuantity(slaveItem.getProd().getQuantity());// 份数
			appendList.add(append);

			addOrdOrderHotel(ordOrderHotelList, ordOrder.getOrderId(), slaveItem.getMeta().getOrderItemMetaId(), slaveItem.getMeta().getSupplierId(), slaveItem.getMeta().getSupplier().getSupplierName(), masterItem.getMeta().getBeginDate());
		}
		orderRequest.setAppendList(appendList);
		log.info("HotelOrderService orderRequest: " + orderRequest.toString());
		return orderRequest;
	}

	private void saveOrdOrderHotels(List<OrdOrderHotel> ordOrderHotelList, OrderResponse orderResponse) {
		for (OrdOrderHotel ordOrderHotel : ordOrderHotelList) {
			ordOrderHotel.setPartnerOrderId(orderResponse.getPartnerOrderID());
			ordOrderHotel.setStatusCode(orderResponse.getOrderStatus().getStatusCode());
			ordOrderHotel.setStatusName(orderResponse.getOrderStatus().getStatusName());
			orderHotelService.saveOrdOrderHotel(ordOrderHotel);
		}
	}

	private List<OrdOrderItem> getMasterSlaveItems(OrdOrderItem masterItem, List<OrdOrderItem> slaveItems) {
		List<OrdOrderItem> masterSlaveItems = new ArrayList<OrdOrderItem>();
		for (OrdOrderItem slaveItem : slaveItems) {
			if (isMasterSlave(slaveItem.getMeta(), masterItem.getMeta())) {
				masterSlaveItems.add(slaveItem);
			}
		}
		return masterSlaveItems;
	}

	private boolean isCheckPassed(OrdOrderItem masterItem, List<OrdOrderItem> masterSlaveItems, HotelOrderServiceSupport hotelOrderServiceSupport) throws Exception {
		OrdOrderItemMeta masterMeta = masterItem.getMeta();
		for (OrdOrderItemMetaTime metaTime : masterMeta.getAllOrdOrderItemMetaTime()) {
			if (isResourceConfirm(masterMeta, metaTime)) {
				return false;
			} else {
				TimePriceRequest timePriceRequest = new TimePriceRequest();
				timePriceRequest.setHotelCode(masterMeta.getProductTypeSupplier());
				timePriceRequest.setRoomTypeID(masterMeta.getProductIdSupplier());
				timePriceRequest.setStartDate(metaTime.getVisitTime());
				timePriceRequest.setEndDate(DateUtils.addDays(metaTime.getVisitTime(), 1));
				timePriceRequest.setCurrency("CNY");

				List<RoomType> roomTypeList = hotelOrderServiceSupport.queryRoomTypeTimePrice(timePriceRequest);
				if (roomTypeList == null) {
					return false;
				} else {
					if (!metaTime.getSettlementPrice().equals(roomTypeList.get(0).getSettlementPrice())) {
						log.info("HotelOrderService meteProductId=" + masterMeta.getMetaProductId() + ", metaBranchId=" + masterMeta.getMetaBranchId() + ", oldPrice=" + masterMeta.getSettlementPrice() + ", newPrice="
								+ roomTypeList.get(0).getSettlementPrice());
						return false;
					}
				}
			}
		}

		for (OrdOrderItem slaveItem : masterSlaveItems) {
			OrdOrderItemMeta slaveMeta = slaveItem.getMeta();
			for (OrdOrderItemMetaTime metaTime : slaveMeta.getAllOrdOrderItemMetaTime()) {
				if (isResourceConfirm(slaveMeta, metaTime)) {
					return false;
				} else {
					TimePriceRequest timePriceRequest = new TimePriceRequest();
					timePriceRequest.setHotelCode(slaveMeta.getProductTypeSupplier());
					timePriceRequest.setRoomTypeID(slaveMeta.getProductIdSupplier());
					timePriceRequest.setStartDate(metaTime.getVisitTime());
					timePriceRequest.setEndDate(DateUtils.addDays(metaTime.getVisitTime(), 1));
					timePriceRequest.setCurrency("CNY");

					List<Append> appendList = hotelOrderServiceSupport.queryAppendTimePrice(timePriceRequest);
					if (appendList != null) {
						if (!metaTime.getSettlementPrice().equals(appendList.get(0).getSettlementPrice())) {
							log.info("HotelOrderService meteProductId=" + slaveMeta.getMetaProductId() + ", metaBranchId=" + masterMeta.getMetaBranchId() + ", oldPrice=" + slaveMeta.getSettlementPrice() + ", newPrice="
									+ appendList.get(0).getSettlementPrice());
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private boolean isHotelOrder(OrdOrderItemProd prod) {
		String productType = prod.getProductType();
		return Constant.PRODUCT_TYPE.HOTEL.name().equals(productType) || Constant.PRODUCT_TYPE.HOTEL_FOREIGN.name().equals(productType);
	}

	private boolean isMasterSlave(OrdOrderItemMeta slaveMeta, OrdOrderItemMeta masterMeta) {
		if (!slaveMeta.getSupplierId().equals(masterMeta.getSupplierId())) {
			return false;
		} else if (StringUtils.isBlank(slaveMeta.getProductTypeSupplier())) {
			return false;
		} else if (StringUtils.isBlank(slaveMeta.getProductIdSupplier())) {
			return false;
		} else {
			if (!StringUtils.equals(slaveMeta.getProductTypeSupplier().split(",")[0], masterMeta.getProductTypeSupplier())) {
				return false;
			}
			if (slaveMeta.getProductTypeSupplier().split(",").length > 1) {
				if (!StringUtils.equals(slaveMeta.getProductTypeSupplier().split(",")[1], masterMeta.getProductIdSupplier())) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isPartnerHotelOrder(OrdOrderItem masterItem, HotelOrderServiceSupport hotelOrderServiceSupport) {
		return hotelOrderServiceSupport != null && masterItem.getMeta() != null && StringUtils.isNotBlank(masterItem.getMeta().getProductTypeSupplier()) && StringUtils.isNotBlank(masterItem.getMeta().getProductIdSupplier());
	}

	private boolean isResourceConfirm(OrdOrderItemMeta meta, OrdOrderItemMetaTime metaTime) {
		TimePrice timePrice = metaProductBranchService.getTimePrice(meta.getMetaBranchId(), metaTime.getVisitTime());
		return StringUtils.equals(timePrice.getResourceConfirm(), "true");
	}

	private void addOrdOrderHotel(List<OrdOrderHotel> ordOrderHotelList, Long orderId, Long orderItemMetaId, Long supplierId, String supplierName,Date visitDate) {
		OrdOrderHotel ordOrderHotel = new OrdOrderHotel();
		ordOrderHotel.setLvmamaOrderId(orderId);
		ordOrderHotel.setOrderItemId(orderItemMetaId);// 订单子子项Id
		ordOrderHotel.setHotelSupplierId(supplierId);// 供应商Id
		ordOrderHotel.setHotelSupplierName(supplierName);// 供应商名称
		ordOrderHotel.setCreateTime(new Date());
		ordOrderHotel.setVisitDate(visitDate);//游玩时间
		ordOrderHotelList.add(ordOrderHotel);
	}

	public Map<String, HotelOrderServiceSupport> getHotelOrderServiceSupportMap() {
		return hotelOrderServiceSupportMap;
	}

	public void setHotelOrderServiceSupportMap(Map<String, HotelOrderServiceSupport> hotelOrderServiceSupportMap) {
		this.hotelOrderServiceSupportMap = hotelOrderServiceSupportMap;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public void setMetaProductBranchService(MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public void setOrderHotelService(OrderHotelService orderHotelService) {
		this.orderHotelService = orderHotelService;
	}

	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}
	
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
}
