package com.lvmama.comm.bee.service.tmall;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.tmall.OrdTmallMap;

public interface OrdTmallMapService {
	/***
	 * 根据淘宝id查询订单数量
	 * @param tmall_order_no
	 * @return
	 */
	 boolean selectTmallNo(String tmall_order_no);
	 
    /***
	 * 拉取淘宝数据insert ord_tmall_map表中
	 * @param record
	 * @return
	 */
	 Long insert(OrdTmallMap record);
	 
	/***
	 * 查询status为creat的记录
	 * @return
	 */
	 List<String>  selectOrdOfCreate();
	 
	/***
	 * 根据淘宝id进行动态更新 (status,processstatus,FailedReason....)
	 * @param record
	 * @return
	 */
	 int updateByTmallOrderNoSelective(OrdTmallMap record) ;
	 
	/***
	 * 根据天猫id,产品id,类别id进行动态更新 下单成功的相关订单记录 (驴妈妈订单号，状态,备注信息，淘宝客服工号等)
	 * @param record
	 * @return
	 */
	 int updateByOrdSelective(OrdTmallMap record);
	 
    /***
	 * 查询搬单失败结果集
	 * @param param
	 * @return
	 */
	 List<OrdTmallMap> getFailedOrderList(Map<String, String> param);
	 
    /***
	 * 查询搬单失败结果集大小
	 * @param param
	 * @return
	 */
	 Long getFailedOrderListCount(Map<String, String> param);
	 
    /***
	 * 查询搬单结果集
	 * @param param
	 * @return
	 */
	 List<OrdTmallMap> getOrderList(Map<String, String> param);
	 
    /***
	 * 查询搬单结果集大小
	 * @param param
	 * @return
	 */
	 Long getOrderListCount(Map<String, String> param);
	 
    /***
	 * 主键id动态更新
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(OrdTmallMap record);
	
	/***
	 * 主键id查询
	 * @param tmallMapId
	 * @return
	 */
	OrdTmallMap selectByPrimaryKey(Long tmallMapId);
	
	/****
	 * 根据tid,产品id,类别id查询
	 * @param tmallMapId
	 * @return
	 */
	 OrdTmallMap getOrderByUK(String tid,Long productId,Long branchId);
	 
	 /***
		 * 根据订单id查询是否是实体票
		 */
	 boolean selectCertificateType(Long orderId);
	 
     List<OrdTmallMap> selectByTmallNo(String tid);
     
     OrdTmallMap selectByLvOrderId(Long oid);
}
