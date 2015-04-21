package com.lvmama.comm.bee.service.prod;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.pet.po.prod.ProdCouponInterval;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
/**
 * 产品设置时间区间优惠
 * @author yuzhizeng
 *
 */
public interface ProdCouponIntervalService {

	ProdCouponInterval selectByPrimaryKey(Long prodCouponIntervalId);

	int deleteByPrimaryKey(ProdCouponInterval prodCouponInterval);

	int deleteByParams(Map<String, Object> params);
	
	void insert(ProdCouponInterval record);

	List<ProdCouponInterval> selectByParams(Map<String, Object> params);

	Integer selectRowCount(Map<String, Object> params);
	
	  /**
     * 查询促优惠的最大优惠有效期间(区间)
     * @param param
     * @return
     */
	ProdCouponInterval selectValidDate(Map<String, Object> param);
	
	/**
	 * 判断是否满足打tag的条件
	 */
	ProdProductTag checkProductTag(String couponType, Long productId);
	
	List<CalendarModel> fillCuCouponFlagForCalendar(Long productId, Long branchId, List<CalendarModel> calendarModelList, Map<String,Object> dateParam);
	
	int batchInsert(final List<ProdCouponInterval> list);
	
}
