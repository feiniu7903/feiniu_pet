package com.lvmama.comm.bee.service.tmall;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.tmall.OrdTmallDistributorMap;

public interface OrdTmallDistributorMapService {
	/***
	 * 根据淘宝分销id查询订单数量
	 * @param fenxiaoId
	 * @return
	 */
	 boolean getOrdTmallDistributorMapCount(Long fenXiaoId);
	 
    /***
	 * 拉取淘宝数据insert ord_tmall_map表中
	 * @param record
	 * @return
	 */
	 Long insert(OrdTmallDistributorMap record);
	 
	 /**
	  * 根据主键更新对象所有的值 
	  * @param record
	  * @return
	  */
	 Integer updateAllByPrimaryKey(OrdTmallDistributorMap record);
	 
	/***
	 * 查询status为creat的记录
	 * @return
	 */
	 List<String>  selectOrdOfCreate();
	 
	 /***
	 * 根据订单id查询是否是实体票
	 */
	 boolean selectCertificateType(Long orderId);
	 
	 /**
	  * 根据条件查询记录数
	  * 
	  * @param param
	  * @return Integer
	  */
	 Integer selectCountByParam(Map<String,Object> param);
	 /**
	  * 根据条件查询--通用查询
	  * @param param
	  * @return List
	  */
	 List<OrdTmallDistributorMap> selectByParam(Map<String,Object> param);
	 
	 /**
	  * 根据主键查询对象
	  * @param ordTmallDistributorMapId
	  * @return
	  */
	 OrdTmallDistributorMap selectByPK(Long ordTmallDistributorMapId);

}
