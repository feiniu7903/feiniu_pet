package com.lvmama.prd.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.prod.ProdCouponIntervalService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.pet.po.prod.ProdCouponInterval;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.vo.Constant;
import com.lvmama.prd.dao.ProdCouponIntervalDAO;

public class ProdCouponIntervalServiceImpl implements ProdCouponIntervalService {

	private ProdCouponIntervalDAO prodCouponIntervalDAO;
	
	@Override
	public ProdCouponInterval selectByPrimaryKey(Long prodCouponIntervalId) {
		if(prodCouponIntervalId != null){
			return prodCouponIntervalDAO.selectByPrimaryKey(prodCouponIntervalId);
		}else{
			return null;
		}
	}

	@Override
	public int deleteByPrimaryKey(ProdCouponInterval prodCouponInterval) {
		return prodCouponIntervalDAO.deleteByPrimaryKey(prodCouponInterval);
	}
	
	public int deleteByParams(Map<String, Object> params) {
		if(params != null && params.size() > 0){
			return prodCouponIntervalDAO.deleteByParams(params);
		}else{
			return 0;
		}
	}
	 
	@Override
	public void insert(ProdCouponInterval record) {
		prodCouponIntervalDAO.insert(record);
	}

	public int batchInsert(final List<ProdCouponInterval> list) {
		return prodCouponIntervalDAO.batchInsert(list);
	}
	
	@Override
	public List<ProdCouponInterval> selectByParams(Map<String, Object> params) {
		if(params != null && params.size() > 0){
			return prodCouponIntervalDAO.selectByParams(params);
		}else{
			return null;
		}
	}

	@Override
	public Integer selectRowCount(Map<String, Object> params) {
		if(params != null && params.size() > 0){
			return prodCouponIntervalDAO.selectRowCount(params);
		}else{
			return null;
		}
	}

	public ProdCouponInterval selectValidDate(Map<String, Object> params) {
		if(params != null && params.size() > 0){
			return prodCouponIntervalDAO.selectValidDate(params);
		}else{
			return null;
		}
	}
	
	/**
	 * 判断是否满足打tag的条件
	 */
	public ProdProductTag checkProductTag(String couponType, Long productId) {
		ProdProductTag prodProductTag = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("couponType", couponType);
		param.put("productId", productId);  //标签是打在产品ID上
		//注意：prodCouponInterval没有ID值,获取产品促时间记录的最大(小)时间
		ProdCouponInterval prodCouponInterval = prodCouponIntervalDAO.selectValidDate(param);
		
		if (prodCouponInterval != null && prodCouponInterval.getBeginTime() != null
				&& prodCouponInterval.getEndTime() != null) {
			prodProductTag = new ProdProductTag();
			prodProductTag.setBeginTime(prodCouponInterval.getBeginTime());
			prodProductTag.setEndTime(prodCouponInterval.getEndTime());
			prodProductTag.setProductId(productId);
			prodProductTag.setCreator(Constant.PROD_PRODUCT_TAG_CREATOR.SYSTEM.getCode());
		}
		return prodProductTag;
	}
	
	public List<CalendarModel> fillCuCouponFlagForCalendar(Long productId, Long branchId, List<CalendarModel> calendarModelList, Map<String,Object> dateParam){
		
		//先获取该产品的满足条件的促优惠时间记录
		dateParam.put("productId", productId);
		dateParam.put("branchId", branchId);
		List<ProdCouponInterval> list = prodCouponIntervalDAO.selectByParams(dateParam);
		
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DATE, -1);//系统前一天
		
		if(list != null && list.size() > 0){
			//遍历时间价格表每一天 (calendarModelList 6个月)
			for(CalendarModel calendarModel : calendarModelList){
				TimePrice[][]  timePriceArray = calendarModel.getCalendar();
				//日历窗口有6个排
				for(int i = 0; i < timePriceArray.length; i++){
					for(int j = 0; j < timePriceArray[i].length; j++){	//一排7天,timePrice时间有重复
						
						TimePrice timePrice = timePriceArray[i][j];
						//获取适合当前时间价格表的'促'优惠
						if(timePrice.getSpecDate() != null && timePrice.getSpecDate().after(cal.getTime())){
							for(ProdCouponInterval prodCI : list){
								if (timePrice.getSpecDate().getTime() == prodCI.getSpecDate().getTime()) {
									timePrice.setCuCouponFlag(1);
								}
							}
							
							//设置日历的'促'标签提示信息
							if(timePrice.getCuCouponFlag() > 0){
								if(timePrice.getFavorJsonParams() != null){
									String oldFavorJsonParams = timePrice.getFavorJsonParams();
									String cuTitle = "[{\"index\":\""+Constant.FAVOR_TIME_PRICE_TEMPLATE_INDEX.SALES_PROMOTION_TITLE.getIndex()+"\"}";
									if(oldFavorJsonParams.equalsIgnoreCase("")){
										//没获取到任何早多提示
										timePrice.setFavorJsonParams(cuTitle + "]");
									}else{
										//已有早多优惠提示
										if(timePrice.getFavorJsonParams().length() > 2){
											//重复遍历日期，没有{"index":"7"}就加
											if(oldFavorJsonParams.indexOf(cuTitle) == -1){
												timePrice.setFavorJsonParams(oldFavorJsonParams.replaceAll("\\[", cuTitle +  ","));
											}
										}
									}
								}else{
									//favorJsonParams提示为NULL
									String cuTitle = "[{\"index\":\""+Constant.FAVOR_TIME_PRICE_TEMPLATE_INDEX.SALES_PROMOTION_TITLE.getIndex()+"\"}";
									timePrice.setFavorJsonParams(cuTitle + "]");
								}
							}
						}
					}
				}
			}
		}
		return calendarModelList;
	}
 
	
	public void setProdCouponIntervalDAO(ProdCouponIntervalDAO prodCouponIntervalDAO) {
		this.prodCouponIntervalDAO = prodCouponIntervalDAO;
	}
	
	
}
