package com.lvmama.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.service.ord.OrderPersonService;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.utils.LogViewUtil;
import com.lvmama.comm.utils.PersonUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.dao.OrderPersonDAO;
import com.lvmama.order.dao.QueryDAO;

/**
 * 订单联系人服务实现类.
 *
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.ord.po.OrdOrder
 * @see com.lvmama.ord.po.OrdPerson
 * @see com.lvmama.ord.service.po.Person
 * @see com.lvmama.vo.Constant
 * @see com.lvmama.order.dao.OrderDAO
 * @see com.lvmama.order.dao.OrderPersonDAO
 * @see com.lvmama.order.dao.QueryDAO
 * @see com.lvmama.order.service.OrderPersonService
 */
public class OrderPersonServiceImpl extends OrderServiceImpl implements
		OrderPersonService {
	private static Logger logger = Logger
			.getLogger(OrderPersonServiceImpl.class);

	private OrderPersonDAO orderPersonDAO;
	private OrderDAO orderDAO;
	private QueryDAO queryDAO;
	private ComLogDAO comLogDAO; 

	public void setOrderPersonDAO(OrderPersonDAO orderPersonDAO) {
		this.orderPersonDAO = orderPersonDAO;
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public void setQueryDAO(QueryDAO queryDAO) {
		this.queryDAO = queryDAO;
	}
	 
	/**
	 * 向指定ID订单添加{@link Person}.
	 *
	 * @param person
	 *            {@link Person}
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表添加成功，<code>false</code>代表添加失败
	 * </pre>
	 */
	@Override
	public boolean addPerson2OrdOrder(Person person, Long orderId,
			String operatorId) {
		if (person.getPersonId() > 0) {
			String str=this.generPersonLogStr(person, operatorId);
			boolean isFlag=updatePerson(person, orderId, operatorId);
			if(!"".equals(str)){
				this.insertLog("ORDER_PERSON",orderId, person.getPersonId(),operatorId,
						Constant.COM_LOG_ORDER_EVENT.updateOrdPerson.name(),
						"修改订单联系人",str);
				}
			return isFlag;
		} else {
			return insertPerson(person, orderId, operatorId);
		}
	}

	private String generPersonLogStr(Person person,String operatorName){
		StringBuffer strBuf=new StringBuffer();
		OrdPerson oldPerson=this.orderPersonDAO.selectByPrimaryKey(person.getPersonId());
		if(!LogViewUtil.logIsEmptyStr(person.getName()).equals(LogViewUtil.logIsEmptyStr(oldPerson.getName()))){
			strBuf.append(LogViewUtil.logEditStr("取票(联系)人", oldPerson.getName(), person.getName()));
		}
		if(!LogViewUtil.logIsEmptyStr(person.getEmail()).equals(LogViewUtil.logIsEmptyStr(oldPerson.getEmail()))){
			strBuf.append(LogViewUtil.logEditStr("Email", oldPerson.getEmail(), person.getEmail()));
		}
		if(!LogViewUtil.logIsEmptyStr(person.getMobile()).equals(LogViewUtil.logIsEmptyStr(oldPerson.getMobile()))){
			strBuf.append(LogViewUtil.logEditStr("联系电话", oldPerson.getMobile(), person.getMobile()));
		}
		if(!LogViewUtil.logIsEmptyStr(person.getTel()).equals(LogViewUtil.logIsEmptyStr(oldPerson.getTel()))){
			strBuf.append(LogViewUtil.logEditStr("座机号", oldPerson.getTel(), person.getTel()));
		}
		if(!LogViewUtil.logIsEmptyStr(person.getCertType()).equals(LogViewUtil.logIsEmptyStr(oldPerson.getCertType()))){
			strBuf.append(LogViewUtil.logEditStr("证件类型", oldPerson.getZhCertType(), person.getZhCertType()));
		}
		if(!LogViewUtil.logIsEmptyStr(person.getCertNo()).equals(LogViewUtil.logIsEmptyStr(oldPerson.getCertNo()))){
			strBuf.append(LogViewUtil.logEditStr("证件号码", oldPerson.getCertNo(), person.getCertNo()));
		}
		if(!LogViewUtil.logIsEmptyStr(person.getFax()).equals(LogViewUtil.logIsEmptyStr(oldPerson.getFax()))){
			strBuf.append(LogViewUtil.logEditStr("传真", oldPerson.getFax(), person.getFax()));
		}
		if(!LogViewUtil.logIsEmptyStr(person.getFaxTo()).equals(LogViewUtil.logIsEmptyStr(oldPerson.getFaxTo()))){
			strBuf.append(LogViewUtil.logEditStr("传真接收人", oldPerson.getFaxTo(), person.getFaxTo()));
		}
		if(person.getBrithday() != null && oldPerson.getBrithday() != null && !person.getBrithday().equals(oldPerson.getBrithday())){
			strBuf.append(LogViewUtil.logEditStr("出生日期", oldPerson.getBrithday().toString(), person.getBrithday().toString()));
		}
		if(!LogViewUtil.logIsEmptyStr(person.getGender()).equals(LogViewUtil.logIsEmptyStr(oldPerson.getGender()))){
			strBuf.append(LogViewUtil.logEditStr("性别", oldPerson.getGender(), person.getGender()));
		}
		return strBuf.toString();
	}
	
	@Override
	public boolean insertPerson(Person person, Long orderId, String operatorId){	
		Long personId=insertPerson(person, orderId, operatorId, true);
		if(personId!=null){
			this.insertLog("ORDER_PERSON",orderId, personId,operatorId,
					Constant.COM_LOG_ORDER_EVENT.insertOrdPerson.name(),
					"添加订单联系人", LogViewUtil.logNewStr(operatorId) + Constant.ORD_PERSON_TYPE.getCnName(person.getPersonType()) +"：" + person.getName());
		}
		return true;
	}
	
	@Override
	public boolean insertInvoicePerson(Person person, Long invoiceId,
			String operatorId) {
		OrdPerson ordPerson=selectInvoicePersonByInvoiceId(invoiceId);
		if(ordPerson==null){//添加新数据
			ordPerson=PersonUtil.converPerson(person, Constant.OBJECT_TYPE.ORD_INVOICE, invoiceId);		
			Long personId=orderPersonDAO.insertSelective(ordPerson);
			if(personId!=null){
				this.insertLog("ORDER_PERSON","ORD_INVOICE",invoiceId, personId,operatorId,
						Constant.COM_LOG_ORDER_EVENT.insertOrdPerson.name(),
						"添加发票收件信息", LogViewUtil.logNewStr(operatorId));
			}
		}else{//更新数据
			ordPerson=PersonUtil.converPerson(person,ordPerson);
			orderPersonDAO.updateByPrimaryKey(ordPerson);
			this.insertLog("ORDER_PERSON","ORD_INVOICE",invoiceId, ordPerson.getPersonId(),operatorId,
					Constant.COM_LOG_ORDER_EVENT.insertOrdPerson.name(),
					"修改发票收件信息", LogViewUtil.logNewStr(operatorId));
		}
		return true;
	}

	public Long insertPerson(Person person, Long orderId, String operatorId, boolean isNeedCheckOrderId) {
		if(isNeedCheckOrderId)
		{
			OrdOrder order = orderDAO.selectByPrimaryKey(orderId);
			if (order == null) {
				throwException("addPerson2OrdOrder Error: Can not find OrdOrder by orderId = " + orderId, logger);
			}
		}
		
		OrdPerson ordPerson = new OrdPerson();
		ordPerson.setProvince(person.getProvince()); //
		ordPerson.setCity(person.getCity());         //
		ordPerson.setAddress(person.getAddress());
		ordPerson.setCertNo(person.getCertNo());
		ordPerson.setCertType(person.getCertType());
		ordPerson.setEmail(person.getEmail());
		ordPerson.setFax(person.getFax());
		ordPerson.setFaxTo(person.getFaxTo());
		ordPerson.setMemo(person.getMemo());
		ordPerson.setMobile(person.getMobile());
		ordPerson.setName(person.getName());
		ordPerson.setObjectId(orderId);
		ordPerson.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
		ordPerson.setPersonType(person.getPersonType());
		ordPerson.setPostcode(person.getPostcode());
		ordPerson.setQq(person.getQq());
		ordPerson.setTel(person.getTel());
		ordPerson.setBrithday(person.getBrithday());
		ordPerson.setGender(person.getGender());
		ordPerson.setReceiverId(person.getReceiverId());
		ordPerson.setPinyin(person.getPinyin());
		orderPersonDAO.insertSelective(ordPerson);
		return ordPerson.getPersonId();
	}
	@Override
	public void createOrdPerson(List<Person> personList, Long orderId, String operatorId) {
		for (Person person : personList) {
			insertPerson(person, orderId, operatorId, false);
		}
	}

	/**
	 * 移除指定ID订单的{@link Person}.
	 *
	 * @param personId
	 *            {@link Person}的ID
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表移除成功，<code>false</code>代表移除失败
	 * </pre>
	 */
	@Override
	public boolean removePersonFromOrdOrder(Long personId, Long orderId,
			String operatorId) {
		int row = orderPersonDAO.deleteByPrimaryKey(personId);
		this.insertLog("ORDER_PERSON",orderId, personId,operatorId,
				Constant.COM_LOG_ORDER_EVENT.deleteOrdPerson.name(),
				"删除订单联系人", LogViewUtil.logDeleteStr(operatorId));
		return (row == 1 ? true : false);
	}

	/**
	 * 更改指定ID订单的{@link Person}.
	 *
	 * @param person
	 *            {@link Person}
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人ID
	 * @return <pre>
	 * <code>true</code>代表更改成功，<code>false</code>代表更改失败
	 * </pre>
	 */
	@Override
	public boolean updatePerson(Person person, Long orderId, String operatorId) {
		OrdOrder order = orderDAO.selectByPrimaryKey(orderId);
		if (order == null) {
			logger.info("addPerson2OrdOrder Error: Can not find OrdOrder by orderId = "
					+ orderId);
		}

		OrdPerson ordPerson = new OrdPerson();
		ordPerson.setPersonId(person.getPersonId());
		ordPerson.setAddress(person.getAddress());
		ordPerson.setCertNo(person.getCertNo());
		ordPerson.setCertType(person.getCertType());
		ordPerson.setEmail(person.getEmail());
		ordPerson.setFax(person.getFax());
		ordPerson.setFaxTo(person.getFaxTo());
		ordPerson.setMemo(person.getMemo());
		ordPerson.setMobile(person.getMobile());
		ordPerson.setName(person.getName());
		ordPerson.setObjectId(orderId);
		ordPerson.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
		ordPerson.setPersonType(person.getPersonType());
		ordPerson.setPostcode(person.getPostcode());
		ordPerson.setQq(person.getQq());
		ordPerson.setTel(person.getTel());
		ordPerson.setBrithday(person.getBrithday());
		ordPerson.setGender(person.getGender());
		ordPerson.setReceiverId(person.getReceiverId());
		ordPerson.setPinyin(person.getPinyin());
		int row = orderPersonDAO.updateByPrimaryKey(ordPerson);
		if(row == 1)
			return true;
		else
			return false;
	}

	/**
	 * 根据订单ID查询{@link Person}.
	 *
	 * @param orderId
	 *            订单ID
	 * @return <pre>
	 * 指定订单ID的{@link Person}，如果指定订单ID的{@link Person}不存在，则返回元素数为0的{@link Person}列表，
	 *         使用者可以安全的使用以下方式来检查方法返回值：
	 *         <code>0==List.size()</code>
	 *         <code>true==List.isEmpty()</code>
	 *         此方法保证永远不会返回<code>null</code>
	 * </pre>
	 */
	@Override
	public List<Person> queryPersonByOrderId(Long orderId) {
		List<OrdPerson> ordPersonList = queryDAO
				.queryOrdPersonByOrdOrderId(orderId);
		List<Person> personList = new ArrayList<Person>();

		if (ordPersonList == null || ordPersonList.size() == 0)
			return personList;

		for (OrdPerson ordPerson : ordPersonList) {
			Person person = new Person();
			person.setPersonId(ordPerson.getPersonId());
			person.setAddress(ordPerson.getAddress());
			person.setCertNo(ordPerson.getCertNo());
			person.setCertType(ordPerson.getCertType());
			person.setEmail(ordPerson.getEmail());
			person.setFax(ordPerson.getFax());
			person.setFaxTo(ordPerson.getFaxTo());
			person.setMemo(ordPerson.getMemo());
			person.setMobile(ordPerson.getMobile());
			person.setName(ordPerson.getName());
			person.setPersonType(ordPerson.getPersonType());
			person.setPostcode(ordPerson.getPostcode());
			person.setQq(ordPerson.getQq());
			person.setTel(ordPerson.getTel());
			person.setReceiverId(ordPerson.getReceiverId());
			person.setBrithday(ordPerson.getBrithday());
			person.setGender(ordPerson.getGender());
			person.setPinyin(ordPerson.getPinyin());
			personList.add(person);
		}

		return personList;
	}
	public void insertLog(String objectType, Long parentId, Long objectId, String operatorName,
			String logType, String logName, String content) {
		ComLog log = new ComLog();
		log.setParentId(parentId);
		log.setObjectType(objectType);
		log.setObjectId(objectId);
		log.setOperatorName(operatorName);
		log.setLogType(logType);
		log.setLogName(logName);
		
		if (content != null)
			log.setContent(content);
		comLogDAO.insert(log);
	}
	
	public void insertLog(String objectType,String parentType, Long parentId, Long objectId, String operatorName,
			String logType, String logName, String content){
		ComLog log = new ComLog();
		log.setParentId(parentId);
		log.setParentType(parentType);
		log.setObjectType(objectType);
		log.setObjectId(objectId);
		log.setOperatorName(operatorName);
		log.setLogType(logType);
		log.setLogName(logName);
		
		if (content != null)
			log.setContent(content);
		comLogDAO.insert(log);
	}
	
	/**
	 * 根据Person ID查询{OrdPerson}.
	 *
	 * @param personId
	 *            主键
	 * @return OrdPerson
	 * 
	 */
	@Override
	public OrdPerson selectByPrimaryKey(final Long personId)
	{
		return orderPersonDAO.selectByPrimaryKey(personId);
	}

	public ComLogDAO getComLogDAO() {
		return comLogDAO;
	}

	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}

	@Override
	public OrdPerson selectInvoicePersonByInvoiceId(Long invoiceId) {
		Map<String,String> params=new HashMap<String, String>();
		params.put("objectType", "ORD_INVOICE");
		params.put("objectId", String.valueOf(invoiceId));
		params.put("personType", Constant.ORD_PERSON_TYPE.ADDRESS.name());
		List<OrdPerson> list=orderPersonDAO.queryOrdPersonByParams(params);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	
}
