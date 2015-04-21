package com.lvmama.comm.bee.service.ord;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdUserOrder;

public interface IOrdUserOrderService {
	/**
	 * 根据主键删除OrdUserOrder。
	 * 
	 * @param userOrderId
	 * @return
	 */
	public int deleteOrdUserOrderByPrimaryKey(Long userOrderId);
	
	/**
	 * 插入OrdUserOrder对象到表中
	 * 
	 * @param record
	 * @return
	 */
    public int insertOrdUserOrder(OrdUserOrder record);

    /**
     * 根据主键查询OrdUserOrder。
     * 
     * @param userOrderId
     * @return
     */
    public OrdUserOrder queryOrdUserOrderByPrimaryKey(Long userOrderId);

    /**
     * 跟新OrdUserOrder到表中
     * 
     * @param record
     * @return
     */
    public int updateOrdUserOrderByPrimaryKey(OrdUserOrder record);
    
    /**
     * 根据参数组合条件查询OrdUserOrder对象列表
     * 
     * @param params
     * @return
     */
    public List<OrdUserOrder> queryOrdUserOrderListByParams(Map<String, Object> params);
    
    /**
     *  根据参数组合条件获取查询结果数量
     * 
     * @param params
     * @return
     */
    public Long getTotalCount(Map<String, Object> params);
    
    /**
     * 根据UserOrder的ID、支付类型，查询OrdUserOrder对象
     * 
     * @param userOrderId
     * @param paymentBizTyep
     * @return
     */
    public OrdUserOrder queryByUserOrderIdAndPaymentBizType(Long userOrderId, String paymentBizType);
}
