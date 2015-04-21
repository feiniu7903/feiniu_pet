package com.lvmama.order.dao.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.order.dao.OrderPersonDAO;

/**
 * 订单游客DAO实现类.
 *
 * <pre>
 * 封装订单游客CRUD
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.BaseIbatisDao
 * @see com.lvmama.ord.po.OrdPerson
 * @see com.lvmama.order.dao.OrderPersonDAO
 */
public final class OrderPersonDAOImpl extends BaseIbatisDAO implements
		OrderPersonDAO {

	public String getOrdPersonMobile(Long orderId, String personType) {
		OrdPerson pparam = new OrdPerson();
		pparam.setObjectId(orderId);
		pparam.setPersonType(personType);
		List<OrdPerson> opList = this.getOrdPersons(pparam);//查询订单联系人
		if(opList!=null&&opList.size()>0){
			OrdPerson contact = opList.size()!=0?opList.get(0):null;
			if(contact!=null){
				return contact.getMobile();
			}
		}
		return null;
	}
	
	public Long insert(final OrdPerson record) {
		Object newKey = super.insert("ORDER_PERSON.insert",
				record);
		return (Long) newKey;
	}

	public Long insertSelective(final OrdPerson record) {
		Object newKey = super.insert(
				"ORDER_PERSON.insertSelective", record);
		return (Long) newKey;
	}

	public List<OrdPerson> getOrdPersons(final OrdPerson pars) {
		return super.queryForList("ORDER_PERSON.select", pars);
	}
	
	public List<OrdPerson> queryOrdPersonByParams(final Map<String, String> params) {
		return super.queryForList("ORDER_PERSON.queryOrdPersonByParams", params);
	}

	public int deleteByPrimaryKey(final Long personId) {
		int rows = super.delete(
				"ORDER_PERSON.deleteByPrimaryKey", personId);
		return rows;
	}

	public int updateByPrimaryKey(final OrdPerson record) {
		int rows = super.update(
				"ORDER_PERSON.updateByPrimaryKey", record);
		return rows;
	}
	
	public OrdPerson selectByPrimaryKey(final Long personId) {
		Object obj = super.queryForObject(
				"ORDER_PERSON.selectByPrimaryKey", personId);
		if(obj != null)
			return (OrdPerson) obj;
		else
			return null;
	}

}
