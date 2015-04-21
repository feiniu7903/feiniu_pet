package com.lvmama.order.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdPerson;

/**
 * 订单游客DAO接口.
 *
 * <pre>
 * 封装订单游客CRUD
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.ord.po.OrdPerson
 */
public interface OrderPersonDAO {
	Long insert(OrdPerson record);

	Long insertSelective(OrdPerson record);

	List<OrdPerson> getOrdPersons(OrdPerson pars);
	
	List<OrdPerson> queryOrdPersonByParams(Map<String, String> params);

	int deleteByPrimaryKey(Long personId);

	int updateByPrimaryKey(OrdPerson record);
	
	OrdPerson selectByPrimaryKey(final Long personId);
	
	String getOrdPersonMobile(Long orderId,String personType);
}
