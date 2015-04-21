package com.lvmama.comm.bee.service.ord;

import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.vo.ord.Person;

/**
 * 订单联系人服务.
 *
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.comm.TmallPerson.service.po.Person
 */
public interface OrderPersonService {
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
	boolean addPerson2OrdOrder(Person person, Long orderId, String operatorId);

	boolean insertPerson(Person person, Long orderId, String operatorId);
	/**
	 * 写入一个发票的收件地址信息,如果该发票已经存在地址，即为修改发票地址
	 * @param person
	 * @param invoiceId
	 * @param operatorId
	 * @return
	 */
	boolean insertInvoicePerson(Person person,Long invoiceId,String operatorId);
	
	void createOrdPerson(List<Person> personList, Long orderId, String operatorId);

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
	boolean removePersonFromOrdOrder(Long personId, Long orderId,
			String operatorId);

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
	boolean updatePerson(Person person, Long orderId, String operatorId);

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
	List<Person> queryPersonByOrderId(Long orderId);
	
	/**
	 * 根据Person ID查询{OrdPerson}.
	 *
	 * @param personId
	 *            主键
	 * @return OrdPerson
	 * 
	 */
	OrdPerson selectByPrimaryKey(final Long personId);
	
	
	/**
	 * 一个发票只有一个地址
	 * 根据发票ID查询
	 * @param invoiceId
	 * @return
	 */
	OrdPerson selectInvoicePersonByInvoiceId(final Long invoiceId);
}
