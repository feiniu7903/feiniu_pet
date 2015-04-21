package com.lvmama.tmall.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.tmall.OrdTmallMap;

public class OrdTmallMapDAO extends BaseIbatisDAO {

	public OrdTmallMapDAO() {
		super();
	}

	/***
	 * 拉取淘宝数据insert ord_tmall_map表中
	 * @param record
	 * @return
	 */
	public Long insert(OrdTmallMap record) {
		return (Long) super.insert("ORD_TMALL_MAP.insert",
				record);

	}
	
	/***
	 * 根据淘宝id查询订单数量
	 * @param tmall_order_no
	 * @return
	 */
	public boolean selectTmallNo(String tmall_order_no){
		Integer flag=(Integer)super.queryForObject("ORD_TMALL_MAP.selectTmallNo", tmall_order_no);
		if(flag==0){
			return true;
		}
		return false;
	}
	
	/***
	 * 根据订单id查询是否是实体票
	 */
	public boolean selectCertificateType(Long orderId){
		Long  certificateNum=(Long)super.queryForObject("ORD_TMALL_MAP.selectCertificateType", orderId);
		if(certificateNum!=null&&certificateNum>=1){
			return true;
		}
		return false;
	}
	
	/***
	 * 查询status为creat的记录
	 * @return
	 */
	public List<String>  selectOrdOfCreate(){
		//return (OrdTmallMap) super.queryForObject("ORD_TMALL_MAP.selectOrdOfCreate");
				//.queryForList("ORD_TMALL_MAP.selectOrdOfCreate");
		return (List<String>)super.queryForList("ORD_TMALL_MAP.selectOrdOfCreate");
		 
	}
	
	/**
	 * 查询搬单失败结果集
	 * @param param
	 * @return
	 */
	public List<OrdTmallMap> getFailedOrderList(Map<String, String> param){
		return (List<OrdTmallMap>)super.queryForList("ORD_TMALL_MAP.selectFailedOrderList", param);
	}
	
	/**
	 * 查询搬单失败结果集大小
	 * @param param
	 * @return
	 */
	public Long getFailedOrderListCount(Map<String, String> param){
		return (Long)super.queryForObject("ORD_TMALL_MAP.getFailedOrderListCount", param);
	}
	
	/**
	 * 查询搬单结果集
	 * @param param
	 * @return
	 */
	public List<OrdTmallMap> getOrderList(Map<String, String> param){
		return (List<OrdTmallMap>)super.queryForList("ORD_TMALL_MAP.selectOrderList", param);
	}
	
	/**
	 * 查询搬单结果集大小
	 * @param param
	 * @return
	 */
	public Long getOrderListCount(Map<String, String> param){
		return (Long)super.queryForObject("ORD_TMALL_MAP.getOrderListCount", param);
	}
	
	public int updateByPrimaryKey(OrdTmallMap record) {
		return super.update("ORD_TMALL_MAP.updateByPrimaryKey", record);

	}
	//根据天猫id,产品id,类别id进行动态更新
	public int updateByOrdSelective(OrdTmallMap record){
		return super.update("ORD_TMALL_MAP.updateByOrdSelective", record);
	}
	//根据天猫id进行动态更新
	public int updateByTmallOrderNoSelective(OrdTmallMap record) {
		return super.update("ORD_TMALL_MAP.updateByTmallOrderNoSelective", record);
	}
	/***
	 * 主键id动态更新
	 * @param record
	 * @return
	 */

	public int updateByPrimaryKeySelective(OrdTmallMap record) {
		return super.update("ORD_TMALL_MAP.updateByPrimaryKeySelective", record);
	}

	/****
	 * 主键id查询
	 * @param tmallMapId
	 * @return
	 */
	public OrdTmallMap selectByPrimaryKey(Long tmallMapId) {
		OrdTmallMap map=new OrdTmallMap();
		map.setTmallMapId(tmallMapId);
		return (OrdTmallMap) super.queryForObject("ORD_TMALL_MAP.selectByPrimaryKey", map);
	}
	
	/****
	 * 根据淘宝id查询
	 * @param tmallId
	 * @return
	 */
	public List<OrdTmallMap> selectByTmallNo(String  tmallId) {
		OrdTmallMap map=new OrdTmallMap();
		map.setTmallOrderNo(tmallId);
		return (List<OrdTmallMap>)super.queryForList("ORD_TMALL_MAP.selectByTmallNo", map);
	}
	
	/****
	 * 根据驴妈妈订单id查询
	 * @param oid
	 * @return
	 */
	public OrdTmallMap selectByLvOrderId(Long  oid) {
		OrdTmallMap map=new OrdTmallMap();
		map.setLvOrderId(oid);
		return (OrdTmallMap)super.queryForObject("ORD_TMALL_MAP.selectByLvOrderId", map);
	}
	
	/****
	 * 根据tid,产品id,类别id查询
	 * @param tmallMapId
	 * @return
	 */
	public OrdTmallMap getOrderByUK(String tid,Long productId,Long branchId){
		OrdTmallMap map=new OrdTmallMap();
		map.setTmallOrderNo(tid);
		map.setProductId(productId);
		map.setCategoryId(branchId);
		return (OrdTmallMap) super.queryForObject("ORD_TMALL_MAP.selectOrderByUK", map);
	}

	public int deleteByPrimaryKey(Long tmallMapId) {
		return 0;
	}
}