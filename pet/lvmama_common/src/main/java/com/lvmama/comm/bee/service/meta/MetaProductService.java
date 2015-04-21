package com.lvmama.comm.bee.service.meta;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductTraffic;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.utils.json.ResultHandle;

public interface MetaProductService {
	/**
	 * 添加采购产品
	 * 
	 * @param metaProduct
	 */
	Long addMetaProduct(MetaProduct metaProduct,String operatorName);
	
	/**
	 * 使所有的采购产品的任务生效
	 * 
	 * @param metaProduct
	 */
	public void updateEffectedTask(List<Map<String,Object>> changeList);
	
	/**
	 * 更新采购产品
	 * 
	 * @param metaProduct
	 */
	public void updateMetaProduct(MetaProduct metaProduct,String operatorName);

	/**
	 * 根据ID与类型查询采购产品
	 * @param metaProductId ID
	 * @param type TICKET HOTEL, ROUTE, OTHER
	 * @return
	 */
	MetaProduct getMetaProduct(Long metaProductId, String type);
	
	/**
	 * 根据ID与类型查询采购产品
	 * @param metaProductId
	 * @return
	 */
	MetaProduct getMetaProductByMetaProductId(Long metaProductId);
	/**
	 * 根据ID查询采购产品
	 * @param metaProductId ID
	 * @param type TICKET HOTEL, ROUTE, OTHER
	 * @return
	 */
	MetaProduct getMetaProduct(Long metaProductId);
	

	/**
	 * 
	 * @param param
	 * @return
	 */
	public List<MetaProduct> findMetaProduct(Map param);
	
	
	public List<MetaProduct> getMetaProductByProductId(long productId);
	/**
	 * 查询采购类别时间价格
	 * @param metaBranch
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public CalendarModel getMetaTimePriceByMetaProductId(Long metaBranchId, Date beginDate, Date endDate);

	/**
	 * 只修改价格
	 * @param bean
	 * @param metaProductId
	 * @return 
	 */
	public void updateTimePrice(TimePrice bean, Long metaProductId,String operatorName);
	/**
	 * 只修改价格
	 * @param bean
	 * @param metaProductId
	 * @return 
	 */
	public void updateStock(TimePrice bean, Long metaProductId,String operatorName);
	
	/**
	 * 只修改自动清库存小时数
	 * @param bean
	 * @param metaProductId
	 * @return 
	 */
	public void updateZeroStock(TimePrice bean, Long metaProductId,String operatorName);
	/**
	 * 只修改最晚取消小时数、提前预定小时数(门票：最早/晚换票时间)
	 * @param bean
	 * @param metaProductId
	 * @return 
	 */
	public void updateHour(TimePrice bean, Long metaBranchId,String operatorName);
	/**
	 * 保存时间价格表
	 * @param bean
	 * @param metaProductId
	 * @return 
	 */
	public void saveTimePrice(TimePrice bean, Long metaBranchId,String operatorName);
	
	/**
	 * 判断是否存在旧的时间价格表.
	 * @param productId
	 * @param date
	 * @return
	 */
	public boolean hasOldTimePrice(Long productId,Date date);
	
	/**
	 * 根据产品ID删除记录(修改VALID状态值)
	 * */
	public void changeMetaProductValid(Map params,String operatorName);
	 
	/**
	 * 查询是否存在销售产品
	 * @param metaProductId
	 * @param bean
	 */
	public Long selectIsExistProdProduct(TimePrice timePriceBean);
	
 	public Integer selectRowCount(Map searchConds);
 	/**
 	 * 批量更新产品经理
 	 * */
 	public void updateManager(HashMap params);
 	/**
	 * 批量修改销售产品id
	 * */
	public void updateOrgIds(Map<String, Object> params);
	

	/**
	 * 检查从今天开始往后是否存在资源不需要审核的时间价格表
	 * @param metaProductId
	 * @return
	 */
	public boolean checkNotNeedResourceConfirm(Long metaProductId);
	
	//查询结算对象对应的采购产品列表
	public List<MetaProduct> getMetaProductListByTargetId(Map<String, Object> params);
	public Integer getMetaProductListByTargetIdCount(Map<String, Object> params);
	
	//查询履行对象对应的采购产品列表
	public List<MetaProduct> getMetaProductListByPerformTargetId(Map<String, Object> params);
	public Integer getMetaProductListByPerformTargetIdCount(Map<String, Object> params);
	/**
	 * 根据采购产品类别id查询采购产品信息
	 */
	public List<MetaProduct> getEbkMetaProductByBranchIds(List<Long> metaBranchIds);
	/**
	 * 根据采购产品id查询采购产品信息
	 */
	public List<MetaProduct> getEbkMetaProductByProductId(Long metaProductId);
	
	/**
	 * 根据入住日期和产品类别设置库存和超卖状态
	 * @param specdates
	 * @param metaBranchId
	 * @param operatorName
	 */
	public void updateStockByTimeAndBrachId(String[] specDates,Long metaBranchId, String operatorName);

	/**
	 * 查询EBK用户绑定的所有履行对象的所有采购产品
	 * @param userMetaMap
	 * @return
	 */
	List<MetaProduct> getEbkUserMetaProductsByParam(Map<String, Object> params);
	

	public TimePrice getMetaTimePriceByIdAndDate(Long metaBranchId, Date specDate);
	
	public void insertTimePrice(TimePrice bean);
	
	public void updateTimePrice(TimePrice bean, TimePrice timePrice);
	
	/**
	 * 动态更新价格表字段
	 * @param timePrice
	 */
	public void updateDynamicTimePrice(TimePrice timePrice);

	public void deleteByBeginDateAndEndDate(Map<String,Object> param);


	/**
	 * 根据对象id查询关联的不定期产品
	 * */
	Long selectMetaProductCountByTargetId(Long targetId, String targetType);
	/**
	 * 不定期产品时间价格表校验
	 * */
	public ResultHandle aperiodicTimePriceValidation(TimePrice bean, Long metaBranchId);
	
	public MetaProduct getMetaProductByBranchId(Long branchId);
	
	/**
	 * 根据订单id查询订单子子项id
	 * @param orderId 订单id
	 * @return
	 */
	public List<OrdOrderItemMeta> queryOrderItemMetaIdByOrderId(Long orderId);

	/**
	 * 火车票当售空时不删除数据，仅更新库存为0
	 * @param bean
	 * @param metaBranchId
	 */
	public void updateTrainTimePrice(TimePrice bean, Long metaBranchId);
	/**
	 * 根据车次名称获取车次产品信息
	 * @param fullName
	 * @return
	 */
	MetaProductTraffic getTrainMetaProduct(String fullName);
	
	/**
	 * 标记库存为0，是否已经发送过邮件
	 * @param parameters
	 * @return
	 */
	public void signSendEmail(Map<String,Object> parameters);
	
}
