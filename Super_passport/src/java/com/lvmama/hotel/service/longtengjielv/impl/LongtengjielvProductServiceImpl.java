package com.lvmama.hotel.service.longtengjielv.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.vo.Constant;
import com.lvmama.hotel.client.longtengjielv.LongtengjielvClient;
import com.lvmama.hotel.mock.LongtengjielvMock;
import com.lvmama.hotel.model.Append;
import com.lvmama.hotel.model.RoomType;
import com.lvmama.hotel.service.BaseHotelProductService;
import com.lvmama.hotel.service.longtengjielv.LongtengjielvProductService;
import com.lvmama.passport.utils.WebServiceConstant;

public class LongtengjielvProductServiceImpl extends BaseHotelProductService implements LongtengjielvProductService {
	private static final Log log = LogFactory.getLog(LongtengjielvProductServiceImpl.class);

	private static Long RATE = 6L;// 销售产品的加价比例

	private LongtengjielvClient longtengjielvClient;
	private LongtengjielvMock longtengjielvMock;

	/**
	 * 更新所有酒店下的所有房型及其下所有附加产品的时间价格及库存
	 */
	public void updateRoomTypesAndAdditionals(Date startDate, Date endDate) throws Exception {
		Long supplierId = Long.valueOf(WebServiceConstant.getProperties("longtengjielv.supplierId"));
		List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierId(supplierId);
		for (MetaProductBranch metaProductBranch : metaProductBranchs) {
			if (!metaProductBranch.isAdditional()) {
				String hotelCode = metaProductBranch.getProductTypeSupplier();
				String roomTypeID = metaProductBranch.getProductIdSupplier();
				log.info("metaProductId=" + metaProductBranch.getMetaProductId() + ", metaBranchId=" + metaProductBranch.getMetaBranchId() + ", hotelCode=" + hotelCode + ", roomTypeID=" + roomTypeID);
				if (StringUtils.isNotBlank(hotelCode) && StringUtils.isNotBlank(roomTypeID)) {
					updateRoomTypeTimePrice(hotelCode, roomTypeID, startDate, endDate);
					updateRoomTypeTimeStock(hotelCode, roomTypeID, startDate, endDate);
				}
			} else {
				String productTypeSupplier = metaProductBranch.getProductTypeSupplier();
				String productIdSupplier = metaProductBranch.getProductIdSupplier();
				log.info("metaProductId=" + metaProductBranch.getMetaProductId() + ", metaBranchId=" + metaProductBranch.getMetaBranchId() + ", productTypeSupplier=" + productTypeSupplier + ", productIdSupplier=" + productIdSupplier);
				if (StringUtils.isNotBlank(productTypeSupplier) && StringUtils.isNotBlank(productIdSupplier) && productTypeSupplier.split(",").length > 1) {
					String hotelCode = productTypeSupplier.split(",")[0];
					String roomTypeID = productTypeSupplier.split(",")[1];
					updateAdditionalTimePrice(hotelCode, roomTypeID, startDate, endDate);
				}
			}
		}
	}

	/**
	 * 更新指定酒店及其房型产品的时间价格
	 */
	public void updateRoomTypeTimePrice(String hotelCode, String roomTypeID, Date startDate, Date endDate) throws Exception {
		Long supplierId = Long.valueOf(WebServiceConstant.getProperties("longtengjielv.supplierId"));
		List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierType(supplierId, hotelCode, roomTypeID);
		if (!metaProductBranchs.isEmpty()) {
			MetaProductBranch metaProductBranch = metaProductBranchs.get(0);
			List<ProdProductBranch> prodProductBranchs = prodProductBranchService.getProductBranchByMetaProdBranchId(metaProductBranch.getMetaBranchId());
			List<RoomType> roomTypeList = longtengjielvClient.simplifyRoomTypePriceInfo(hotelCode, roomTypeID, startDate, endDate, "CNY");
			if (roomTypeList != null) {
				for (RoomType roomType : roomTypeList) {
					TimePrice metaTimePrice = saveRoomTypeMetaTimePrice(metaProductBranch, roomType);
					for (ProdProductBranch prodProductBranch : prodProductBranchs) {
						saveRoomTypeProdTimePrice(prodProductBranch, roomType, metaTimePrice);
					}
				}
				for (ProdProductBranch prodProductBranch : prodProductBranchs) {
					prodProductBranchService.updatePriceByBranchId(prodProductBranch.getProdBranchId());
					prodProductService.updatePriceByProductId(prodProductBranch.getProductId());
				}
			}
		}
	}

	/**
	 * 更新指定酒店及其房型产品的时间库存
	 */
	public void updateRoomTypeTimeStock(String hotelCode, String roomTypeID, Date startDate, Date endDate) throws Exception {
		Long supplierId = Long.valueOf(WebServiceConstant.getProperties("longtengjielv.supplierId"));
		List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierType(supplierId, hotelCode, roomTypeID);
		if (!metaProductBranchs.isEmpty()) {
			MetaProductBranch metaProductBranch = metaProductBranchs.get(0);
			List<RoomType> roomTypeList = longtengjielvClient.simplifyRoomStatusInfo(hotelCode, roomTypeID, startDate, endDate);
			if (roomTypeList != null) {
				for (RoomType roomType : roomTypeList) {
					saveRoomStockMetaTimePrice(metaProductBranch, roomType);
				}
			}
		}
	}

	/**
	 * 更新指定酒店及其房型下的所有附加产品的时间价格
	 */
	public void updateAdditionalTimePrice(String hotelCode, String roomTypeID, Date startDate, Date endDate) throws Exception {
		Long supplierId = Long.valueOf(WebServiceConstant.getProperties("longtengjielv.supplierId"));
		List<Append> appendList = longtengjielvClient.simplifyRoomPriceAppendInfo(hotelCode, roomTypeID, startDate, endDate, "CNY");
		if (appendList != null) {
			for (Append append : appendList) {
				List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierType(supplierId, append.getProductTypeSupplier(), append.getProductIdSupplier());
				if (!metaProductBranchs.isEmpty()) {
					MetaProductBranch metaProductBranch = metaProductBranchs.get(0);
					List<ProdProductBranch> prodProductBranchs = prodProductBranchService.getProductBranchByMetaProdBranchId(metaProductBranch.getMetaBranchId());
					TimePrice metaTimePrice = saveAdditionalMetaTimePrice(metaProductBranch, append);
					for (ProdProductBranch prodProductBranch : prodProductBranchs) {
						saveAdditionalProdTimePrice(prodProductBranch, append, metaTimePrice);
						prodProductBranchService.updatePriceByBranchId(prodProductBranch.getProdBranchId());
						prodProductService.updatePriceByProductId(prodProductBranch.getProductId());
					}
				}
			}
		}
	}

	/**
	 * 上下线指定的酒店产品
	 */
	public void onOfflineHotel(String hotelCode) throws Exception {
		Long supplierId = Long.valueOf(WebServiceConstant.getProperties("longtengjielv.supplierId"));
		boolean isHotelOnline = isHotelOnline(hotelCode);
		List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierType(supplierId, hotelCode, null);
		for (MetaProductBranch metaProductBranch : metaProductBranchs) {
			onOffline(metaProductBranch, isHotelOnline);
		}
	}

	/**
	 * 上下线指定酒店下的指定房型产品
	 */
	public void onOfflineRoomType(String hotelCode, String roomTypeID) throws Exception {
		Long supplierId = Long.valueOf(WebServiceConstant.getProperties("longtengjielv.supplierId"));
		boolean isRoomTypeOnline = isRoomTypeOnline(hotelCode, roomTypeID);
		List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierType(supplierId,null, roomTypeID);
		if (!metaProductBranchs.isEmpty()) {
			onOffline(metaProductBranchs.get(0), isRoomTypeOnline);
		}
	}

	private boolean isHotelOnline(String hotelCode) throws Exception {
		if (Constant.getInstance().isHotelMockEnabled()) {
			return longtengjielvMock.isHotelOnline(hotelCode);
		}
		return longtengjielvClient.isHotelOnline(hotelCode);
	}

	private boolean isRoomTypeOnline(String hotelCode, String roomTypeID) throws Exception {
		if (Constant.getInstance().isHotelMockEnabled()) {
			return longtengjielvMock.isRoomTypeOnline(hotelCode, roomTypeID);
		}
		return longtengjielvClient.isRoomTypeOnline(hotelCode, roomTypeID);
	}

	private TimePrice saveRoomTypeMetaTimePrice(MetaProductBranch metaProductBranch, RoomType roomType) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		TimePrice timePrice = metaProductService.getMetaTimePriceByIdAndDate(metaProductBranch.getMetaBranchId(), roomType.getTimePriceDate());
		TimePrice bean = new TimePrice();
		if (timePrice == null) {
			bean.setProductId(metaProductBranch.getMetaProductId());
			bean.setMetaBranchId(metaProductBranch.getMetaBranchId());
			bean.setCancelHour(roomType.getCancelHour());
			bean.setAheadHour(roomType.getAheadHour());
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
			bean.setCancelHour(roomType.getCancelHour());
			bean.setAheadHour(roomType.getAheadHour());
			metaProductService.updateTimePrice(bean, timePrice);
		}
		return bean;
	}

	private void saveRoomStockMetaTimePrice(MetaProductBranch metaProductBranch, RoomType roomType) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		TimePrice timePrice = metaProductService.getMetaTimePriceByIdAndDate(metaProductBranch.getMetaBranchId(), roomType.getTimePriceDate());
		if (timePrice != null) {
			TimePrice bean = new TimePrice();
			PropertyUtils.copyProperties(bean, timePrice);
			bean.setResourceConfirm(String.valueOf(roomType.isResourceConfirm()));
			bean.setDayStock(roomType.getDayStock());
			metaProductService.updateTimePrice(bean, timePrice);
		}
	}

	private void saveRoomTypeProdTimePrice(ProdProductBranch prodProductBranch, RoomType roomType, TimePrice metaTimePrice) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
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
			bean.setBeginDate(roomType.getTimePriceDate());
			bean.setEndDate(roomType.getTimePriceDate());
			prodProductService.saveTimePrice(bean, productId, "longtengjielv System");	
		} else {
			PropertyUtils.copyProperties(bean, timePrice);
			bean.setCancelHour(roomType.getCancelHour());
			bean.setAheadHour(roomType.getAheadHour());
			bean.setBeginDate(roomType.getTimePriceDate());
			bean.setEndDate(roomType.getTimePriceDate());
			prodProductService.saveTimePrice(bean, productId, "longtengjielv System");	
		}
	}

	private TimePrice saveAdditionalMetaTimePrice(MetaProductBranch metaProductBranch, Append append) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		TimePrice timePrice = metaProductService.getMetaTimePriceByIdAndDate(metaProductBranch.getMetaBranchId(), append.getTimePriceDate());
		TimePrice bean = new TimePrice();
		if (timePrice == null) {
			bean.setProductId(metaProductBranch.getMetaProductId());
			bean.setMetaBranchId(metaProductBranch.getMetaBranchId());
			bean.setSpecDate(append.getTimePriceDate());
			bean.setSettlementPrice(append.getSettlementPrice());
			bean.setMarketPrice(append.getSettlementPrice() * 130 / 100);
			bean.setCancelHour(0L);
			bean.setAheadHour(1440L);
			bean.setOverSale("true");
			bean.setResourceConfirm("false");
			bean.setDayStock(-1);
			metaProductService.insertTimePrice(bean);
		} else {
			PropertyUtils.copyProperties(bean, timePrice);
			bean.setSettlementPrice(append.getSettlementPrice());
			bean.setMarketPrice(append.getSettlementPrice() * 130 / 100);
			bean.setCancelHour(0L);
			bean.setAheadHour(1440L);
			metaProductService.updateTimePrice(bean, timePrice);
		}
		return bean;
	}

	private void saveAdditionalProdTimePrice(ProdProductBranch prodProductBranch, Append append, TimePrice metaTimePrice) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Long productId = prodProductBranch.getProductId();
		Long prodBranchId = prodProductBranch.getProdBranchId();
		TimePrice timePrice = prodProductService.getTimePriceByProdId(productId, prodBranchId, append.getTimePriceDate());
		TimePrice bean = new TimePrice();
		if (timePrice == null) {
			bean.setProductId(productId);
			bean.setProdBranchId(prodBranchId);
			bean.setSpecDate(append.getTimePriceDate());
			bean.setCancelHour(0L);
			bean.setAheadHour(1440L);
			bean.setPriceType(Constant.PRICE_TYPE.RATE_PRICE.name());
			bean.setRatePrice(RATE);
			bean.setBeginDate(append.getTimePriceDate());
			bean.setEndDate(append.getTimePriceDate());
			prodProductService.saveTimePrice(bean, productId, "longtengjielv System");	
		} else {
			PropertyUtils.copyProperties(bean, timePrice);
			bean.setBeginDate(append.getTimePriceDate());
			bean.setEndDate(append.getTimePriceDate());
			prodProductService.saveTimePrice(bean, productId, "longtengjielv System");	
		}
	}

	public void setLongtengjielvClient(LongtengjielvClient longtengjielvClient) {
		this.longtengjielvClient = longtengjielvClient;
	}

	public void setLongtengjielvMock(LongtengjielvMock longtengjielvMock) {
		this.longtengjielvMock = longtengjielvMock;
	}
}