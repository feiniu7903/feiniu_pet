package com.lvmama.order.service.builder;

/**
 * 数据库表名.
 * 
 * <pre>
 * </pre>
 * 
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public interface TableName {
	String ORD_ORDER = "ORD_ORDER";
	String ORD_ORDER_ROUTE = "ORD_ORDER_ROUTE";
	String ORD_ORDER_TRACK = "ORD_ORDER_TRACK";
	String TRACK_LOG = "TRACK_LOG";
	String ORD_ECONTRACT = "ORD_ECONTRACT";
	String ORD_ORDER_ITEM_META = "ORD_ORDER_ITEM_META";
	String ORD_ORDER_ITEM_PROD = "ORD_ORDER_ITEM_PROD";
	/**下单用户各种信息,如订单联系人,取票人,游玩人,等.*/
	String ORD_PERSON = "ORD_PERSON";
	/**后台用户信息表.*/
	String PERM_USER = "lvmama_pet.PERM_USER";
	String ORD_SALE_SERVICE = "ORD_SALE_SERVICE";
	String PROD_PRODUCT_PLACE = "PROD_PRODUCT_PLACE";
	String COM_PLACE = "COM_PLACE";
	String PROD_PRODUCT = "PROD_PRODUCT";
	String META_PRODUCT = "META_PRODUCT";
	String META_PRODUCT_BRANCH = "META_PRODUCT_BRANCH";
	String SUP_SUPPLIER = "SUP_SUPPLIER";
	String COM_AUDIT = "COM_AUDIT";
	String ORD_INVOICE = "ORD_INVOICE";
	String ORD_INVOICE_RELATION = "ORD_INVOICE_RELATION";
	String ORD_SETTLEMENT_QUEUE = "ORD_SETTLEMENT_QUEUE";
	String SUP_SETTLEMENT_TARGET = "SUP_SETTLEMENT_TARGET";
	String ORD_SETTLEMENT_QUEUE_ITEM = "ORD_SETTLEMENT_QUEUE_ITEM";
	String ORD_REFUNDMENT = "ORD_REFUNDMENT";
	String ORD_SUB_SETTLEMENT_ITEM = "ORD_SUB_SETTLEMENT_ITEM";
	String ORD_SUB_SETTLEMENT = "ORD_SUB_SETTLEMENT";
	String ORD_SETTLEMENT = "ORD_SETTLEMENT";
	String META_PERFORM = "META_PERFORM";
	String SUP_PERFORM_TARGET = "SUP_PERFORM_TARGET";
	String PASS_PORT_USER = "PASS_PORT_USER";
	String USER_RELATE_SUPPLIER_PRODUCT = "USER_RELATE_SUPPLIER_PRODUCT";
	String PASS_PORT_CODE = "PASS_PORT_CODE";
	String PASS_CODE = "PASS_CODE";
	String ORD_PAYMENT = "ORD_PAYMENT";
	/**
	 * 固定的条件语句.<br>
	 * 查询待结算队列时必须存在相应的待结算子项，待结算子项在生成结算单时会被物理删除
	 */
	String ORD_SETTLEMENT_QUEUE_ITEM_EXISTS = new StringBuilder().append(
			"EXISTS ").append("(SELECT 1 FROM ").append(
			ORD_SETTLEMENT_QUEUE_ITEM).append(" WHERE ").append(
			ORD_SETTLEMENT_QUEUE_ITEM).append(".SETTLEMENT_QUEUE_ID = ")
			.append(ORD_SETTLEMENT_QUEUE).append(".SETTLEMENT_QUEUE_ID)")
			.toString();
}
