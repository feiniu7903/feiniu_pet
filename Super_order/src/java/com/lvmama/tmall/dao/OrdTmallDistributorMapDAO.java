package com.lvmama.tmall.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.tmall.OrdTmallDistributorMap;

public class OrdTmallDistributorMapDAO extends BaseIbatisDAO {

	public OrdTmallDistributorMapDAO() {
		super();
	}

	/***
	 * 拉取淘宝数据insert ord_tmall_distributor_map表中
	 * @param record
	 * @return
	 */
	public Long insert(OrdTmallDistributorMap record) {
		return (Long) super.insert("ORD_TMALL_DISTRIBUTOR_MAP.insert",record);

	}
	
	/***
	 * 根据分销id查询订单数量
	 * 如果数量不为零则返回true
	 * @param param
	 * @return
	 */
	public boolean getOrdTmallDistributorMapCount(Map<String,Object> param){
		Integer flag=(Integer)super.queryForObject("ORD_TMALL_DISTRIBUTOR_MAP.selectCountByParam", param);
		if(flag==0){
			return true;
		}
		return false;
	}
	
	/**
	 * 查询搬单结果集
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OrdTmallDistributorMap> getOrdTmallDistributorMapList(Map<String,Object> param){
		return (List<OrdTmallDistributorMap>)super.queryForList("ORD_TMALL_DISTRIBUTOR_MAP.selectByParam", param);
	}
	
	/***
	 * 根据订单id查询是否是实体票
	 */
	public boolean selectCertificateType(Long orderId){
		Long  certificateNum=(Long)super.queryForObject("ORD_TMALL_DISTRIBUTOR_MAP.selectCertificateType", orderId);
		if(certificateNum!=null&&certificateNum>=1){
			return true;
		}
		return false;
	}	
	/***
	 * 查询status为Create的记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String>  selectOrdOfCreate(){
		return (List<String>)super.queryForList("ORD_TMALL_DISTRIBUTOR_MAP.selectOrdOfCreate");
		 
	}
	
	/**
	 * 根据主键更新所有记录
	 * @param record
	 * @return
	 */
	public int updateAllByPrimaryKey(OrdTmallDistributorMap record) {
		return super.update("ORD_TMALL_DISTRIBUTOR_MAP.updateAllByPrimaryKey", record);

	}
	
	/***
	 * 根据条件查询订单数量
	 * @param param
	 * @return Integer
	 */
	public Integer selectCountByParam(Map<String,Object> param){
		return (Integer)super.queryForObject("ORD_TMALL_DISTRIBUTOR_MAP.selectCountByParam", param);
	}
	
	/**
	 * 根据主键查询对象
	 * @param ordTmallDistributorMapId
	 * @return
	 */
	public OrdTmallDistributorMap selectByPK(Long ordTmallDistributorMapId){
		OrdTmallDistributorMap resultObject=(OrdTmallDistributorMap) super.queryForObject("ORD_TMALL_DISTRIBUTOR_MAP.selectByPK", ordTmallDistributorMapId);
		return resultObject;
		
	}
}