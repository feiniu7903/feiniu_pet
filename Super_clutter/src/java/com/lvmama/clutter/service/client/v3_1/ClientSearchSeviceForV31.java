package com.lvmama.clutter.service.client.v3_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.clutter.model.MobilePlace;
import com.lvmama.clutter.service.impl.ClientSearchServiceImpl;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;

public class ClientSearchSeviceForV31 extends ClientSearchServiceImpl{
	
	private static final Log log = LogFactory.getLog(ClientSearchSeviceForV31.class);
	
	@Override
	public Map<String, Object> placeSearch(Map<String, Object> params) {
		Page<PlaceBean> pageConfig = this.getPlaceSearchList(params);
		List<MobilePlace> mplaceList = new ArrayList<MobilePlace>();

		for (PlaceBean placeBean : pageConfig.getItems()){
			mplaceList.add(convertToMobilePlace(placeBean));
		}
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("datas", mplaceList);
		resultMap.put("isLastPage", isLastPage(pageConfig));
		return resultMap;
	}
	
	
	@Override
	protected MobilePlace convertToMobilePlace(PlaceBean placeBean){
		/**
		 * 3.1 之前的版本
		 */
		MobilePlace mp = super.convertToMobilePlace(placeBean);
		/*********** V3.1 添加的属性 **********/
		//距离
		mp.setJuli(String.valueOf(placeBean.getBoost()));
		// 主题类型 
		mp.setSubject(placeBean.getPlaceMainTitel());
		// 返现金额 (是分 还是元)
		mp.setMaxCashRefund(StringUtil.isEmptyString(placeBean.getCashRefund())?0l:PriceUtil.convertToFen(placeBean.getCashRefund()));
		// 优惠 - 景点不显示惠 
		//mp.setHasBusinessCoupon(ClientUtils.hasBusinessCoupon(placeBean.getTagList())); 
		return mp;
	}
}
