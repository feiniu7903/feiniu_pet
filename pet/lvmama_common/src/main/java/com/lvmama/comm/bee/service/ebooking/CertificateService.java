package com.lvmama.comm.bee.service.ebooking;

import com.lvmama.comm.bee.po.ord.OrdOrder;


/**
 * 
 * @author ranlongfei 2013-3-27
 * @version
 */
public interface CertificateService {
	/** 创建订单 */
	public static String ORDER_CREATE="ORDER_CREATE";
	/** 订单审核通过 */
	public static String  ORDER_APPROVE="ORDER_APPROVE";
	/**订单审核通过*/
	public static String  ORDER_MODIFY_PERSON="ORDER_MODIFY_PERSON";
	/**订单审核通过*/
	public static String  ORDER_PAYMENT="ORDER_PAYMENT";
	/**结算价修改*/
	public static String  ORDER_MODIFY_SETTLEMENT_PRICE="ORDER_MODIFY_SETTLEMENT_PRICE";
	/** 订单取消 */
	public static String  ORDER_CANCEL="ORDER_CANCEL";
	/** 重发 */
	public static String  RETRANSMISSION="RETRANSMISSION";
	/** 期票订单修改 */
	public static String  UPDATE_APERIODIC_ORDER="UPDATE_APERIODIC_ORDER";
	/**
	 * 备注变更
	 */
	public static String ORDER_MEMO_CHANGE="ORDER_MEMO_CHANGE";
	/**
	 * ebk转换传真
	 */
	public static String EBK_CHANGE_TO_FAX="EBK_CHANGE_TO_FAX";
	/**
	 * 手工生成传真 用凭证
	 * 
	 * @author: ranlongfei 2013-4-22 上午11:15:24
	 * @param ebkCertificateId
	 * @param operator
	 * @return
	 */
	boolean createCertificateEbkFaxTaskWithCertId(Long ebkCertificateId, String operator);
	/**
	 * 手工生成传真 用子子项
	 * 
	 * @author: ranlongfei 2013-4-22 上午11:15:24
	 * @param orderItemMetaId
	 * @param operator
	 * @return
	 */
	boolean createCertificateEbkFaxTaskWithMetaId(Long orderItemMetaId, String operator);
	/**
	 * 生成凭证
	 * @param orderItemMetaIdList 
	 * @param order
	 * @return
	 */
	boolean createSupplierCertificate(OrdOrder ordOrder,String ebkCertificateEvent,String userMemoStatus, String orderItemMetaIdList);
	/**
	 * 生成凭证
	 * @param orderItemMetaId
	 * @param operator
	 * @return
	 */
	boolean reCreateSupplierCertificate(Long orderItemMetaId, String operator,String ebkCertificateEvent);
	/**
	 * 生成凭证
	 * @param orderId
	 * @param orderItemMetaIdList 
	 * @return
	 */
	boolean createSupplierCertificate(Long orderId,String ebkCertificateEvent,String userMemoStatus, String orderItemMetaIdList);
}
