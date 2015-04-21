package com.lvmama.order.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdInvoiceRelation;

/**
 * 发票DAO接口.
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.ord.po.OrdInvoice
 */
public interface OrderInvoiceDAO {

	int deleteByPrimaryKey(Long invoiceId);

    Long insert(OrdInvoice record);
    
    long getInvoiceAmountSum(Map<String,Object> param);

    OrdInvoice selectByPrimaryKey(Long invoiceId);

    int updateByPrimaryKey(OrdInvoice record);
    
    int updateByParamMap(Map params);
    
    List<OrdInvoice> queryInvoiceByOrderId(Long orderId);
    
    List<OrdInvoice> queryInvoiceByStatus(String status);
    
    List<OrdInvoiceRelation> queryRelationList(Long invoiceId);
    
    boolean updateRedFlag(OrdInvoice record);
    
    /**
     * 查询没有取消的订单数量
     * @param orderId
     * @param status
     * @return
     */
    long selectNotCancelInvoiceCountByOrderId(Long orderId,String status);
}
