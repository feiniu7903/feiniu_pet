package com.lvmama.comm.pet.service.prod;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.pet.po.prod.ProdAssemblyPoint;
import com.lvmama.comm.pet.po.prod.ProdProductHead;

public interface ProductHeadQueryService {

    ProdProductHead getProdProductHeadByProductId(Long productId);
    
    public Date selectNearBranchTimePriceByBranchId(Long prodBranchId,Date beginDay);
    
  	ProdProductBranch getProdBranchDetailByProdBranchId(Long prodBranchId,Date visitTime);
  	/**
  	 * 获取产品的时间价格表,查询默认类别的时间价格表。
  	 * @param productId
  	 * @return
  	 */
  	 List<CalendarModel> getProductCalendarByProductId(Long productId);
  	 /**
  	 * 根据产品类别查询时间价格表
  	 * @param branchId
  	 * @return
  	 */
  	List<CalendarModel> getProductCalendarByBranchId(Long branchId);

  	/**
	  * 检查当前渠道是否可销售
	  * @param productId
	  * @param channel
	  * @return
	  */
	 boolean isSellProductByChannel(Long productId,String channel);
	 
	 /**
	  * 计算退款说明中的晚取消时间
	  */
	 Date getProductsLastCancelTime(List<Long> branchIdList,Date visitTime);
	 /**
	  * 查找产品的上车地点
	  * @param productId
	  * @return
	  */
	 List<ProdAssemblyPoint> getAssemblyPoints(Long productId);

		/**
		 * 取产品相关的类别列表
		 * 
		 * @param productId
		 * @param removeBranchId
		 *            该类别不读取
		 * @param visitTime    
		 * @return
		 */
		List<ProdProductBranch> getProdBranchList(Long productId, Long removeBranchId,Date visitTime);
		/**
		 * 查找主产品相关的附产品和付加产品
		 * 
		 * @param productId
		 * @param visitTime
		 * @return
		 */
		List<ProdProductRelation> getRelatProduct(Long productId, Date visitTime);
		/**
		 * 
		 * @param prodBranchId
		 * @param visitTime
		 * @param checkOnline
		 * @return
		 */
		ProdProductBranch getProdBranchDetailByProdBranchId(Long prodBranchId,Date visitTime,boolean checkOnline);
		List<ProdProductBranch> getProdBranchListAndOnline(Long productId, Long removeBranchId,Date visitTime,boolean checkOnline);

		/**
		 * 检查销售产品类别是否可卖，库存足够或可超卖
		 * 
		 * @param prodBranchId 类别ID
		 *            Map<销售产品id,订购数量>
		 * @param visitTime
		 *            游玩时
		 * @return
		 */
		boolean isSellable(Long prodBranchId, Long quantity, Date visitTime);


		List<CalendarModel> getSelfProductCalendarByBranchIdAndDay(Long branchId, Date today);
		
		/**
		 * 跟据指定时间区间，获取时间价格表
		 * @param branchId
		 * @param startTime
		 * @param endTime
		 * @return
		 */
		List<CalendarModel> getSelfProductCalendarByBranchIdAndTime(Long branchId, Date startTime, Date endTime);

}
