package com.lvmama.comm.bee.service.ord;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrderAmountApply;


/**
 * 订单修改SERVICE.
 * 原名ModifyOrderAmountService
 * @author liwenzhan
 * @version  20111008
 * @see com.lvmama.comm.ord.po.OrdOrder;
 * @see com.lvmama.comm.ord.po.OrdOrderAmountApply;
 * @see com.lvmama.comm.ord.po.OrdOrderAmountItem;
 * @see com.lvmama.comm.vo.Constant;
 * @see com.lvmama.order.dao.OrdOrderAmountApplyDAO;
 * @see com.lvmama.order.dao.OrderDAO;
 * 
 */
public interface OrderAmountService {
    /**
     * 保存订单修改数据.
     * @param amountApply
     */
	void saveModifyOrderAmountApply(OrdOrderAmountApply amountApply);
    /**
     * 根据修改申请的ID取相应的数据.
     * @param applyId
     * @return
     */
	OrdOrderAmountApply selectByPrimaryKey(final Long applyId);
    /**
     * 更新修改订单申请.
     * @param ordOrderAmountApply
     * @return
     */
	int updateOrderModifyAmountApply(OrdOrderAmountApply ordOrderAmountApply);
	/**
	 * 更新修改订单申请,同时修改订单金额 ,生成金额纪录OrdOrderAmountItem.
	 * @param ordOrderAmountApply
	 * @return
	 */
	int updateOrderModifyAmountApplyOrder(OrdOrderAmountApply ordOrderAmountApply);
	/**
	 * 根据条件进行查询.
	 * @param ordOrderAmountApply
	 * @return
	 */
	List<OrdOrderAmountApply> queryOrderAmountApply(Map<String, Object> parameter);
	
    /**
     * 根据条件进行数据记录数查询.
     */
    Long queryOrderAmountApplyCount(Map<String, Object> parameter);
}