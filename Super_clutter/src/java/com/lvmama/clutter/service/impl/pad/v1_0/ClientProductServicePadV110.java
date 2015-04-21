package com.lvmama.clutter.service.impl.pad.v1_0;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.clutter.model.MobileCouponPoint;
import com.lvmama.clutter.model.MobileGroupOn;
import com.lvmama.clutter.model.MobilePersonItem;
import com.lvmama.clutter.model.MobileProduct;
import com.lvmama.clutter.model.MobileProductRoute;
import com.lvmama.clutter.model.MobileProductTitle;
import com.lvmama.clutter.model.MobileTimePrice;
import com.lvmama.clutter.service.client.v4_0.ClientProductServiceV40;
import com.lvmama.clutter.service.impl.ClientProductServiceImpl;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.vo.visa.VisaVO;

public class ClientProductServicePadV110 extends ClientProductServiceV40{

	@Override
	public MobileProduct getProduct(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.getProduct(params);
	}

	@Override
	public List<MobileTimePrice> timePrice(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return super.timePrice(params);
	}

	@Override
	public Map<String, Object> getGroupOnList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getGroupOnList(param);
	}

	@Override
	public MobileGroupOn getGroupOnDetail(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getGroupOnDetail(param);
	}

	@Override
	public Map<String, Object> getBranches(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getBranches(param);
	}

	@Override
	public List<MobileProductTitle> getPlaceRoutes(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getPlaceRoutes(param);
	}

	@Override
	public Map<String, Object> getProductItems(Map<String, Object> param) {
		// TODO Auto-generated method stub
		Map<String, Object> result = super.getProductItems(param);
		return result;
	}


	@Override
	public void getCouponByPoint(String subProductType, String userNo,
			MobileCouponPoint mpoint) {
		// TODO Auto-generated method stub
		super.getCouponByPoint(subProductType, userNo, mpoint);
	}

	@Override
	public MobileProductRoute getRouteDetail(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.getRouteDetail(params);
	}

	@Override
	public Map<String, Object> getRouteDetail4Wap(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return super.getRouteDetail4Wap(params);
	}

	@Override
	public List<MobilePersonItem> getLatestPersonList(String userNo,
			String productType) {
		// TODO Auto-generated method stub
		return super.getLatestPersonList(userNo, productType);
	}

	@Override
	public List<ViewJourney> getViewJourneyList(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		return super.getViewJourneyList(param);
	}
	
	@Override
	public List<VisaVO> getVisaList(Map<String, Object> params) {
		return super.getVisaList(params);
	}
}
