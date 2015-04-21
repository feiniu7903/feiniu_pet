package com.lvmama.shholiday.request;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.shholiday.Response;
import com.lvmama.shholiday.response.ProductDetailResponse;

public class ProductDetailRequest extends AbstractRequest{
	
	
	public ProductDetailRequest(String productId,Date beginDate,Date endDate,String searchType){
		super();
		if(productId == null) productId = "";
		if(beginDate == null) beginDate=new Date();
		if(endDate == null) endDate=DateUtils.addMonths(beginDate, 2);
		if(searchType == null) searchType = "all";
		addParam("productId", productId);
		addParam("beginDate", DateUtil.formatDate(beginDate, "yyyyMMdd"));
		addParam("endDate", DateUtil.formatDate(endDate, "yyyyMMdd"));
		addParam("dateType", searchType);
	}

	@Override
	public String getTransactionName() {
		return REQUEST_TYPE.OTA_TourProductDetailRQ.name();
	}

	@Override
	public Class<? extends Response> getResponseClazz() {
		return ProductDetailResponse.class;
	}
}
