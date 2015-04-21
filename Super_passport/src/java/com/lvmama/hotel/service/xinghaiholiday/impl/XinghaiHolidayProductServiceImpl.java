package com.lvmama.hotel.service.xinghaiholiday.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.hotel.client.xinghaiholiday.XinghaiHolidayClient;
import com.lvmama.hotel.mock.XinghaiHolidayMock;
import com.lvmama.hotel.model.Append;
import com.lvmama.hotel.model.RoomType;
import com.lvmama.hotel.service.BaseHotelProductService;
import com.lvmama.hotel.service.xinghaiholiday.XinghaiHolidayProductService;
import com.lvmama.passport.utils.WebServiceConstant;

public class XinghaiHolidayProductServiceImpl extends BaseHotelProductService implements XinghaiHolidayProductService {	
	private static Long RATE = 6L; // 销售产品的加价比例

	private XinghaiHolidayClient xinghaiHolidayClient;
	private XinghaiHolidayMock xinghaiHolidayMock;

	/**
	 * 更新所有酒店下的所有房型产品的时间价格及库存
	 */
	public void updateRoomTypes(Date startDate, Date endDate) throws Exception {
		Long supplierId = Long.valueOf(WebServiceConstant.getProperties("xinghaiholiday.supplierId"));
		List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierId(supplierId);
		for (MetaProductBranch metaProductBranch : metaProductBranchs) {
			if (!metaProductBranch.isAdditional()) {
				String hotelCode = metaProductBranch.getProductTypeSupplier();
				String roomTypeID = metaProductBranch.getProductIdSupplier();
				if (StringUtils.isNotBlank(hotelCode) && StringUtils.isNotBlank(roomTypeID)) {
					List<RoomType> roomPriceList = null;
					List<RoomType> roomStockList = null;
					if (Constant.getInstance().isHotelMockEnabled()) {
						roomPriceList = xinghaiHolidayMock.getRoomTypePriceJson(hotelCode, roomTypeID);
						roomStockList = xinghaiHolidayMock.getRoomStatusJson(hotelCode, roomTypeID);
					} else {
						roomPriceList = xinghaiHolidayClient.getHotelPrice(hotelCode, roomTypeID, startDate, endDate);
						roomStockList = xinghaiHolidayClient.getHotelRoomState(hotelCode, roomTypeID, startDate, endDate);
					}
					if (roomPriceList != null) {
						updateRoomTypeTimePrice(roomPriceList);
					}
					if (roomStockList != null) {
						updateRoomTypeTimeStock(roomStockList);
					}
				}
			}
		}
	}

	/**
	 * 更新某个酒店下的所有房型产品的时间价格
	 */
	public void updateRoomTypeTimePrice(List<RoomType> roomTypeList) throws Exception {
		Long supplierId = Long.valueOf(WebServiceConstant.getProperties("xinghaiholiday.supplierId"));
		for (RoomType roomType : roomTypeList) {
			List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierType(supplierId, roomType.getHotelID(), roomType.getRoomTypeID());
			if (!metaProductBranchs.isEmpty()) {
				MetaProductBranch metaProductBranch = metaProductBranchs.get(0);
				List<ProdProductBranch> prodProductBranchs = prodProductBranchService.getProductBranchByMetaProdBranchId(metaProductBranch.getMetaBranchId());
				TimePrice metaTimePrice = saveRoomTypeMetaTimePrice(roomType, metaProductBranch);
				for (ProdProductBranch prodProductBranch : prodProductBranchs) {
					saveRoomTypeProdTimePrice(roomType, prodProductBranch, metaTimePrice);
					prodProductBranchService.updatePriceByBranchId(prodProductBranch.getProdBranchId());
				}
			}
		}
	}

	/**
	 * 更新某个酒店下的所有房型产品的时间库存
	 */
	public void updateRoomTypeTimeStock(List<RoomType> roomTypeList) throws Exception {
		Long supplierId = Long.valueOf(WebServiceConstant.getProperties("xinghaiholiday.supplierId"));
		for (RoomType roomType : roomTypeList) {
			List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierType(supplierId, roomType.getHotelID(), roomType.getRoomTypeID());
			if (!metaProductBranchs.isEmpty()) {
				MetaProductBranch metaProductBranch = metaProductBranchs.get(0);
				saveRoomStockMetaTimePrice(roomType, metaProductBranch);
			}
		}
	}

	/**
	 * 更新所有酒店下的所有附加产品的时间价格
	 */
	public void updateAdditionalTimePrice(Date startDate, Date endDate) throws Exception {
		Long supplierId = Long.valueOf(WebServiceConstant.getProperties("xinghaiholiday.supplierId"));
		List<Append> appendList = new ArrayList<Append>();
		List<String> supplierTypeList = metaProductBranchService.selectSupplierTypeBySupplierId(supplierId);
		for (String hotelCode : supplierTypeList) {
			if (StringUtils.isNotBlank(hotelCode)) {
				List<Append> additionalList = xinghaiHolidayClient.getHotelInfo(hotelCode);
				if (additionalList != null && !additionalList.isEmpty()) {
					appendList.addAll(additionalList);
				}
			}
		}
		for (Append append : appendList) {
			String hotelCode = append.getHotelID();
			String productIdSupplier = append.getProductIdSupplier();
			List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierType(supplierId, hotelCode, productIdSupplier);
			if (!metaProductBranchs.isEmpty()) {
				MetaProductBranch metaProductBranch = metaProductBranchs.get(0);
				List<ProdProductBranch> prodProductBranchs = prodProductBranchService.getProductBranchByMetaProdBranchId(metaProductBranch.getMetaBranchId());
				Date newStartDate = trimStartDate(startDate, append);
				Date newEndDate = trimEndDate(endDate, append);
				Date dateIndex = newStartDate;
				append.setTimePriceDate(dateIndex);
				while (dateIndex.compareTo(newEndDate) <= 0) {
					TimePrice metaTimePrice = saveAdditionMetaTimePrice(append, metaProductBranch);
					for (ProdProductBranch prodProductBranch : prodProductBranchs) {
						saveAdditionalProdTimePrice(append, prodProductBranch, metaTimePrice);
						prodProductBranchService.updatePriceByBranchId(prodProductBranch.getProdBranchId());
					}
					dateIndex = DateUtils.addDays(dateIndex, 1);
					append.setTimePriceDate(dateIndex);
				}
			}
		}
	}

	/**
	 * 上下线所有酒店产品
	 */
	public void onOffLineHotels() throws Exception {
		Long supplierId = Long.valueOf(WebServiceConstant.getProperties("xinghaiholiday.supplierId"));
		List<String> hotelIdList = xinghaiHolidayClient.getHotelList();
		List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierId(supplierId);
		for (MetaProductBranch metaProductBranch : metaProductBranchs) {
			String hotelId = metaProductBranch.getProductTypeSupplier();
			if (Constant.getInstance().isHotelMockEnabled()) {
				onOffline(metaProductBranch, xinghaiHolidayMock.isHotelOnline(hotelId));
			} else {
				if (hotelIdList.contains(hotelId)) {
					onOffline(metaProductBranch, true);
				} else {
					onOffline(metaProductBranch, false);
				}
			}
		}
	}

	/**
	 * 上下线所有酒店下的所有房型产品
	 */
	public void onOffLineRoomTypes() throws Exception {
		Long supplierId = Long.valueOf(WebServiceConstant.getProperties("xinghaiholiday.supplierId"));
		List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierId(supplierId);
		for (MetaProductBranch metaProductBranch : metaProductBranchs) {
			if (!metaProductBranch.isAdditional()) {
				String hotelCode = metaProductBranch.getProductTypeSupplier();
				String roomTypeId = metaProductBranch.getProductIdSupplier();
				boolean isOnline = true;
				if (Constant.getInstance().isHotelMockEnabled()) {
					isOnline = xinghaiHolidayMock.isRoomTypeOnline(hotelCode, roomTypeId);
				} else {
					isOnline = xinghaiHolidayClient.isRoomTypeOnline(hotelCode, roomTypeId);
				}
				onOffline(metaProductBranch, isOnline);
			}
		}
	}

	private TimePrice saveRoomTypeMetaTimePrice(RoomType roomType, MetaProductBranch metaProductBranch) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		TimePrice timePrice = metaProductService.getMetaTimePriceByIdAndDate(metaProductBranch.getMetaBranchId(), roomType.getTimePriceDate());
		TimePrice bean = new TimePrice();
		if (timePrice == null) {
			bean.setProductId(metaProductBranch.getMetaProductId());
			bean.setMetaBranchId(metaProductBranch.getMetaBranchId());
			bean.setSpecDate(roomType.getTimePriceDate());
			bean.setSettlementPrice(roomType.getSettlementPrice());
			bean.setMarketPrice(roomType.getSettlementPrice() * 130 / 100);
			bean.setOverSale("true");
			bean.setResourceConfirm("true");
			bean.setDayStock(0);
			bean.setTotalDayStock(0L);
			bean.setBreakfastCount(0L);
			metaProductService.insertTimePrice(bean);
		} else {
			PropertyUtils.copyProperties(bean, timePrice);
			bean.setSettlementPrice(roomType.getSettlementPrice());
			bean.setMarketPrice(roomType.getSettlementPrice() * 130 / 100);
			metaProductService.updateTimePrice(bean, timePrice);
		}
		return bean;
	}

	private void saveRoomStockMetaTimePrice(RoomType roomType, MetaProductBranch metaProductBranch) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		TimePrice timePrice = metaProductService.getMetaTimePriceByIdAndDate(metaProductBranch.getMetaBranchId(), roomType.getTimePriceDate());
		if (timePrice != null) {
			TimePrice bean = new TimePrice();
			PropertyUtils.copyProperties(bean, timePrice);
			bean.setResourceConfirm(String.valueOf(roomType.isResourceConfirm()));
			bean.setDayStock(roomType.getDayStock());
			metaProductService.updateTimePrice(bean, timePrice);
		}
	}

	private void saveRoomTypeProdTimePrice(RoomType roomType, ProdProductBranch prodProductBranch, TimePrice metaTimePrice) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Long productId = prodProductBranch.getProductId();
		Long prodBranchId = prodProductBranch.getProdBranchId();
		TimePrice timePrice = prodProductService.getTimePriceByProdId(productId, prodBranchId, roomType.getTimePriceDate());
		TimePrice bean = new TimePrice();
		if (timePrice == null) {
			bean.setProductId(productId);
			bean.setProdBranchId(prodBranchId);
			bean.setSpecDate(roomType.getTimePriceDate());
			bean.setCancelHour(roomType.getCancelHour());
			bean.setAheadHour(roomType.getAheadHour());
			bean.setPriceType(Constant.PRICE_TYPE.RATE_PRICE.name());
			bean.setRatePrice(RATE);
			prodProductService.insertTimePrice(bean, metaTimePrice);
		} else {
			PropertyUtils.copyProperties(bean, timePrice);
			bean.setCancelHour(roomType.getCancelHour());
			bean.setAheadHour(roomType.getAheadHour());
			prodProductService.updateTimePrice(bean, metaTimePrice);
		}
	}

	private TimePrice saveAdditionMetaTimePrice(Append append, MetaProductBranch metaProductBranch) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		TimePrice timePrice = metaProductService.getMetaTimePriceByIdAndDate(metaProductBranch.getMetaBranchId(), append.getTimePriceDate());
		TimePrice bean = new TimePrice();
		if (timePrice == null) {
			bean.setProductId(metaProductBranch.getMetaProductId());
			bean.setMetaBranchId(metaProductBranch.getMetaBranchId());
			bean.setSpecDate(append.getTimePriceDate());
			bean.setSettlementPrice(append.getSettlementPrice());
			bean.setMarketPrice(append.getSettlementPrice() * 130 / 100);
			bean.setOverSale("true");
			bean.setResourceConfirm("false");
			bean.setDayStock(-1);
			metaProductService.insertTimePrice(bean);
		} else {
			PropertyUtils.copyProperties(bean, timePrice);
			bean.setSettlementPrice(append.getSettlementPrice());
			bean.setMarketPrice(append.getSettlementPrice() * 130 / 100);
			metaProductService.updateTimePrice(bean, timePrice);
		}
		return bean;
	}

	private void saveAdditionalProdTimePrice(Append append, ProdProductBranch prodProductBranch, TimePrice metaTimePrice) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Long productId = prodProductBranch.getProductId();
		Long prodBranchId = prodProductBranch.getProdBranchId();
		TimePrice timePrice = prodProductService.getTimePriceByProdId(productId, prodBranchId, append.getTimePriceDate());
		TimePrice bean = new TimePrice();
		if (timePrice == null) {
			bean.setProductId(productId);
			bean.setProdBranchId(prodBranchId);
			bean.setSpecDate(append.getTimePriceDate());
			bean.setCancelHour(0L);
			bean.setAheadHour(24L);
			bean.setPriceType(Constant.PRICE_TYPE.RATE_PRICE.name());
			bean.setRatePrice(RATE);
			prodProductService.insertTimePrice(bean, metaTimePrice);
		} else {
			PropertyUtils.copyProperties(bean, timePrice);
			prodProductService.updateTimePrice(bean, metaTimePrice);
		}
	}
	
	private Date trimStartDate(Date startDate, Append append) {
		if (startDate.before(append.getTimePriceDate())) {
			return append.getTimePriceDate();
		}
		return startDate;
	}

	private Date trimEndDate(Date endDate, Append append) {
		if (endDate.after(append.getTimePriceDateEnd())) {
			return append.getTimePriceDateEnd();
		}
		return endDate;
	}

	public void setXinghaiHolidayClient(XinghaiHolidayClient xinghaiHolidayClient) {
		this.xinghaiHolidayClient = xinghaiHolidayClient;
	}

	public void setMetaProductBranchService(MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public void setProdProductBranchService(ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setXinghaiHolidayMock(XinghaiHolidayMock xinghaiHolidayMock) {
		this.xinghaiHolidayMock = xinghaiHolidayMock;
	}
}