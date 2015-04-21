package com.lvmama.shholiday.request;

import com.lvmama.shholiday.Response;
import com.lvmama.shholiday.response.ProductInfoListResponse;

public class ProductInfoListReqeust extends AbstractRequest {
	
	public ProductInfoListReqeust(String destinationCode,String productName,String productType,int reqNumOfEachPage,int reqPageNo){
		super();
		if (productName == null) productName = "";
		if(destinationCode == null ) destinationCode = "";
		if(productType == null ) productType = "";
		if(reqNumOfEachPage <= 0) reqNumOfEachPage = 10;
		if(reqPageNo <= 0) reqPageNo = 1;
		addParam("productName", productName);
		addParam("destinationCode", destinationCode);
		addParam("productType", productType);
		addParam("reqNumOfEachPage", reqNumOfEachPage);
		addParam("reqPageNo", reqPageNo);
	}

	@Override
	public String getTransactionName() {
		return REQUEST_TYPE.OTA_TourTeamProductSearchRQ.name();
	}

	@Override
	public Class<? extends Response> getResponseClazz() {
		return ProductInfoListResponse.class;
	}

	

}
