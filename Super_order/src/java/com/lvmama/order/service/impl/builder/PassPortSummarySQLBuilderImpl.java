package com.lvmama.order.service.impl.builder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 通关汇总SQL构建器.
 *
 * <pre>
 * 为复杂的订单查询构建SQL
 * <strong>注意，此实现不是同步的。</strong>
 * 如果多个线程同时访问一个 <code>SQLBuilderImpl</code>实例，
 * 而其中至少一个线程从结构上修改了 <code>SQLBuilderImpl</code>，
 * 那么它必须保持外部同步。
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public final class PassPortSummarySQLBuilderImpl extends AbstractSQLBuilder {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory
			.getLog(PassPortSummarySQLBuilderImpl.class);
	/**
	 * <pre>
	 * SELECT ORD_ORDER_ITEM_META.VISIT_TIME,
	 * 		  COUNT(DISTINCT ORD_ORDER.ORDER_ID) AS ORDER_COUNT,
	 *        (SUM(ORD_ORDER_ITEM_META.ADULT_QUANTITY * ORD_ORDER_ITEM_META.PRODUCT_QUANTITY * ORD_ORDER_ITEM_META.QUANTITY) +
	 *        SUM(ORD_ORDER_ITEM_META.CHILD_QUANTITY * ORD_ORDER_ITEM_META.PRODUCT_QUANTITY * ORD_ORDER_ITEM_META.QUANTITY)) AS VISITOR_QUANTITY,
	 *        SUM(NVL((SELECT NVL(ORD_PERFORM.ADULT_QUANTITY, 0) +
	 *                       NVL(ORD_PERFORM.CHILD_QUANTITY, 0)
	 *                  FROM ORD_PERFORM
	 *                 WHERE ORD_PERFORM.OBJECT_TYPE = 'ORD_ORDER'
	 *                   AND ORD_PERFORM.OBJECT_ID = ORD_ORDER_ITEM_META.ORDER_ID),
	 *                0)) AS SINGLE_PERFORM_PASSED_QUANTITY,
	 *        SUM(NVL((SELECT NVL(ORD_PERFORM.ADULT_QUANTITY, 0) +
	 *                       NVL(ORD_PERFORM.CHILD_QUANTITY, 0)
	 *                  FROM ORD_PERFORM
	 *                 WHERE ORD_PERFORM.OBJECT_TYPE = 'ORD_ORDER_ITEM_META'
	 *                   AND ORD_PERFORM.OBJECT_ID =
	 *                       ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID),
	 *                0)) AS MULTIPLE_QUANTITY,
	 *        SUM(NVL2((SELECT 1
	 *                   FROM ORD_PERFORM
	 *                  WHERE ((ORD_PERFORM.OBJECT_TYPE = 'ORD_ORDER_ITEM_META' AND
	 *                        ORD_PERFORM.OBJECT_ID =
	 *                        ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID) OR
	 *                        (ORD_PERFORM.OBJECT_TYPE = 'ORD_ORDER' AND
	 *                        ORD_PERFORM.OBJECT_ID = ORD_ORDER.ORDER_ID))),
	 *                 0,
	 *                 ((ORD_ORDER_ITEM_META.ADULT_QUANTITY *
	 *                 ORD_ORDER_ITEM_META.PRODUCT_QUANTITY *
	 *                 ORD_ORDER_ITEM_META.QUANTITY) +
	 *                 (ORD_ORDER_ITEM_META.CHILD_QUANTITY *
	 *                 ORD_ORDER_ITEM_META.PRODUCT_QUANTITY *
	 *                 ORD_ORDER_ITEM_META.QUANTITY)))) AS TO_BE_PASS_COUNT
	 * </pre>
	 *
	 * .
	 */
	private static String sb = new StringBuilder()
			.append("SELECT ORD_ORDER_ITEM_META.VISIT_TIME,")
			.append("COUNT(DISTINCT ORD_ORDER.ORDER_ID) AS ORDER_COUNT,")
			.append(" (SUM(ORD_ORDER_ITEM_META.ADULT_QUANTITY * ORD_ORDER_ITEM_META.PRODUCT_QUANTITY * ORD_ORDER_ITEM_META.QUANTITY) +")
			.append(" SUM(ORD_ORDER_ITEM_META.CHILD_QUANTITY * ORD_ORDER_ITEM_META.PRODUCT_QUANTITY * ORD_ORDER_ITEM_META.QUANTITY)) AS VISITOR_QUANTITY,")
			.append(" SUM(NVL(DECODE((SELECT NVL(ORD_PERFORM.ADULT_QUANTITY, 0) +")
			.append(" NVL(ORD_PERFORM.CHILD_QUANTITY, 0)")
			.append(" FROM ORD_PERFORM")
			.append(" WHERE ORD_PERFORM.OBJECT_TYPE = 'ORD_ORDER'")
			.append(" AND ORD_PERFORM.OBJECT_ID = ORD_ORDER_ITEM_META.ORDER_ID),0,((ORD_ORDER_ITEM_META.ADULT_QUANTITY * ORD_ORDER_ITEM_META.PRODUCT_QUANTITY * ORD_ORDER_ITEM_META.QUANTITY) + (ORD_ORDER_ITEM_META.CHILD_QUANTITY * ORD_ORDER_ITEM_META.PRODUCT_QUANTITY * ORD_ORDER_ITEM_META.QUANTITY)),(SELECT NVL(ORD_PERFORM.ADULT_QUANTITY, 0) + NVL(ORD_PERFORM.CHILD_QUANTITY, 0) FROM ORD_PERFORM WHERE ORD_PERFORM.OBJECT_TYPE = 'ORD_ORDER' AND ORD_PERFORM.OBJECT_ID = ORD_ORDER_ITEM_META.ORDER_ID)),")
			.append(" 0)) AS SINGLE_PERFORM_PASSED_QUANTITY,")
			.append(" SUM(NVL(DECODE((SELECT NVL(ORD_PERFORM.ADULT_QUANTITY, 0) +")
			.append(" NVL(ORD_PERFORM.CHILD_QUANTITY, 0)")
			.append(" FROM ORD_PERFORM")
			.append(" WHERE ORD_PERFORM.OBJECT_TYPE = 'ORD_ORDER_ITEM_META'")
			.append(" AND ORD_PERFORM.OBJECT_ID =")
			.append(" ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID),0,((ORD_ORDER_ITEM_META.ADULT_QUANTITY * ORD_ORDER_ITEM_META.PRODUCT_QUANTITY * ORD_ORDER_ITEM_META.QUANTITY) + (ORD_ORDER_ITEM_META.CHILD_QUANTITY * ORD_ORDER_ITEM_META.PRODUCT_QUANTITY * ORD_ORDER_ITEM_META.QUANTITY)),(SELECT NVL(ORD_PERFORM.ADULT_QUANTITY, 0) + NVL(ORD_PERFORM.CHILD_QUANTITY, 0) FROM ORD_PERFORM WHERE ORD_PERFORM.OBJECT_TYPE = 'ORD_ORDER_ITEM_META' AND ORD_PERFORM.OBJECT_ID = ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID)),")
			.append(" 0)) AS MULTIPLE_QUANTITY,")
			.append(" SUM(NVL2((SELECT 1")
			.append(" FROM ORD_PERFORM")
			.append(" WHERE ((ORD_PERFORM.OBJECT_TYPE = 'ORD_ORDER_ITEM_META' AND")
			.append(" ORD_PERFORM.OBJECT_ID =")
			.append(" ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID) OR")
			.append(" (ORD_PERFORM.OBJECT_TYPE = 'ORD_ORDER' AND")
			.append(" ORD_PERFORM.OBJECT_ID = ORD_ORDER.ORDER_ID))),")
			.append(" 0,").append(" ((ORD_ORDER_ITEM_META.ADULT_QUANTITY *")
			.append(" ORD_ORDER_ITEM_META.PRODUCT_QUANTITY *")
			.append(" ORD_ORDER_ITEM_META.QUANTITY) +")
			.append(" (ORD_ORDER_ITEM_META.CHILD_QUANTITY *")
			.append(" ORD_ORDER_ITEM_META.PRODUCT_QUANTITY *")
			.append(" ORD_ORDER_ITEM_META.QUANTITY)))) AS TO_BE_PASS_COUNT")
			.toString();
	/**
	 * 要查询的字段.
	 */
	private static final String SELECT = sb;

	/**
	 * 获取SELECT.
	 *
	 * @return SELECT
	 */
	@Override
	public String getSelect() {
		return SELECT;
	}

	/**
	 * 获取Log.
	 *
	 * @return Log
	 */
	@Override
	public Log getLog() {
		return LOG;
	}
}
