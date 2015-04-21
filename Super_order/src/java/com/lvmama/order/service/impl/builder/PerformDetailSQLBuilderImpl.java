package com.lvmama.order.service.impl.builder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 履行明细SQL构建器.
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
public final class PerformDetailSQLBuilderImpl extends AbstractSQLBuilder {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory
			.getLog(PerformDetailSQLBuilderImpl.class);
	/**
	 * <pre>
	 * SELECT ORD_ORDER_ITEM_META.ORDER_ID,
	 *        META_PRODUCT.PRODUCT_NAME,
	 *        ORD_ORDER_ITEM_META.ADULT_QUANTITY * ORD_ORDER_ITEM_META.PRODUCT_QUANTITY * ORD_ORDER_ITEM_META.QUANTITY AS ADULT_QUANTITY,
	 *        ORD_ORDER_ITEM_META.CHILD_QUANTITY * ORD_ORDER_ITEM_META.PRODUCT_QUANTITY * ORD_ORDER_ITEM_META.QUANTITY AS CHILD_QUANTITY,
	 *        NVL((SELECT NVL(ORD_PERFORM.ADULT_QUANTITY, 0) +
	 *                   NVL(ORD_PERFORM.CHILD_QUANTITY, 0)
	 *              FROM ORD_PERFORM
	 *             WHERE ORD_PERFORM.OBJECT_TYPE = 'ORD_ORDER'
	 *               AND ORD_PERFORM.OBJECT_ID = ORD_ORDER_ITEM_META.ORDER_ID),
	 *            0) AS SINGLE_PERFORM_PASSED_QUANTITY,
	 *        NVL((SELECT NVL(ORD_PERFORM.ADULT_QUANTITY, 0) +
	 *                   NVL(ORD_PERFORM.CHILD_QUANTITY, 0)
	 *              FROM ORD_PERFORM
	 *             WHERE ORD_PERFORM.OBJECT_TYPE = 'ORD_ORDER_ITEM_META'
	 *               AND ORD_PERFORM.OBJECT_ID =
	 *                   ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID),
	 *            0) AS MULTIPLE_QUANTITY,
	 *        DECODE(ORD_ORDER.PAYMENT_TARGET,'TOLVMAMA',0,'TOSUPPLIER',ORD_ORDER.OUGHT_PAY) AS AMOUNT,
	 *        (SELECT ORD_PERSON.NAME
	 *           FROM ORD_PERSON
	 *          WHERE ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'
	 *            AND ORD_PERSON.OBJECT_ID = ORD_ORDER_ITEM_META.ORDER_ID
	 *            AND ORD_PERSON.PERSON_TYPE = 'CONTACT') AS CONTACT_NAME,
	 *        (SELECT ORD_PERSON.MOBILE
	 *           FROM ORD_PERSON
	 *          WHERE ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'
	 *            AND ORD_PERSON.OBJECT_ID = ORD_ORDER_ITEM_META.ORDER_ID
	 *            AND ORD_PERSON.PERSON_TYPE = 'CONTACT') AS CONTACT_MOBILE,
	 *        (ORD_ORDER_ITEM_META.VISIT_TIME +
	 *        NVL(ORD_ORDER_ITEM_META.VALID_DAYS, 0)) AS DEADLINE_TIME,
	 *        ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID,
	 *        ORD_ORDER_ITEM_META.FAX_MEMO,
	 *        SUP_PERFORM_TARGET.TARGET_ID,
	 *        ORD_ORDER.ORDER_STATUS,
	 *        ORD_ORDER.PAYMENT_TARGET
	 *   FROM ORD_ORDER_ITEM_PROD,
	 *        SUP_SUPPLIER,
	 *        ORD_ORDER,
	 *        META_PRODUCT,
	 *        META_PERFORM,
	 *        ORD_ORDER_ITEM_META,
	 *        SUP_PERFORM_TARGET
	 *  WHERE ((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND
	 *        ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND
	 *        ORD_ORDER.ORDER_STATUS = 'NORMAL') OR
	 *        (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND
	 *        ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND
	 *        ORD_ORDER.ORDER_STATUS = 'NORMAL'))
	 *    AND ORD_ORDER_ITEM_META.META_PRODUCT_ID = META_PRODUCT.META_PRODUCT_ID
	 *    AND META_PRODUCT.META_PRODUCT_ID = META_PERFORM.META_PRODUCT_ID
	 *    AND ORD_ORDER_ITEM_META.SUPPLIER_ID = SUP_SUPPLIER.SUPPLIER_ID
	 *    AND META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID
	 *    AND ORD_ORDER_ITEM_META.ORDER_ITEM_ID =
	 *        ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID
	 *    AND ORD_ORDER_ITEM_META.ORDER_ID = ORD_ORDER.ORDER_ID
	 * </pre>
	 *
	 * .
	 */
	private static String sb = new StringBuilder()

			.append("SELECT PASS_PORT_CODE.USED_TIME, PASS_PORT_CODE.STATUS, ORD_ORDER_ITEM_META.ORDER_ID,")
			.append(" META_PRODUCT_BRANCH.BRANCH_NAME,")
			.append(" ORD_ORDER_ITEM_META.ADULT_QUANTITY * ORD_ORDER_ITEM_META.PRODUCT_QUANTITY * ORD_ORDER_ITEM_META.QUANTITY AS ADULT_QUANTITY,")
			.append(" ORD_ORDER_ITEM_META.CHILD_QUANTITY * ORD_ORDER_ITEM_META.PRODUCT_QUANTITY * ORD_ORDER_ITEM_META.QUANTITY AS CHILD_QUANTITY,")
			.append(" NVL((SELECT sum(NVL(ORD_PERFORM.ADULT_QUANTITY, 0)) + ")
			.append(" sum(NVL(ORD_PERFORM.CHILD_QUANTITY, 0))")
			.append(" FROM ORD_PERFORM")
			.append(" WHERE ORD_PERFORM.OBJECT_TYPE = 'ORD_ORDER'")
			.append(" AND ORD_PERFORM.OBJECT_ID = ORD_ORDER_ITEM_META.ORDER_ID),")
			.append(" 0) AS SINGLE_PERFORM_PASSED_QUANTITY,")
			.append(" NVL((SELECT sum(NVL(ORD_PERFORM.ADULT_QUANTITY, 0)) + ")
			.append(" sum(NVL(ORD_PERFORM.CHILD_QUANTITY, 0))")
			.append(" FROM ORD_PERFORM")
			.append(" WHERE ORD_PERFORM.OBJECT_TYPE = 'ORD_ORDER'")
			.append(" AND ORD_PERFORM.OBJECT_ID = ORD_ORDER_ITEM_META.ORDER_ID),")
			.append(" -1) AS SINGLE_FLAG,")
			.append(" NVL((SELECT NVL(ORD_PERFORM.ADULT_QUANTITY, 0) + ")
			.append(" NVL(ORD_PERFORM.CHILD_QUANTITY, 0)")
			.append(" FROM ORD_PERFORM")
			.append(" WHERE ORD_PERFORM.OBJECT_TYPE = 'ORD_ORDER_ITEM_META'")
			.append(" AND ORD_PERFORM.OBJECT_ID = ")
			.append(" ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID),")
			.append(" 0) AS MULTIPLE_QUANTITY,")
			.append(" NVL((SELECT NVL(ORD_PERFORM.ADULT_QUANTITY, 0) + ")
			.append(" NVL(ORD_PERFORM.CHILD_QUANTITY, 0)")
			.append(" FROM ORD_PERFORM")
			.append(" WHERE ORD_PERFORM.OBJECT_TYPE = 'ORD_ORDER_ITEM_META'")
			.append(" AND ORD_PERFORM.OBJECT_ID = ")
			.append(" ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID),")
			.append(" -1) AS MULTIPLE_FLAG,")
			.append(" DECODE(ORD_ORDER.PAYMENT_TARGET,'TOLVMAMA',0,'TOSUPPLIER',ORD_ORDER.OUGHT_PAY) AS AMOUNT,")
			
//			.append(" (SELECT ORD_PERSON.NAME")
//			.append(" FROM ORD_PERSON")
//			.append(" WHERE ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'")
//			.append(" AND ORD_PERSON.OBJECT_ID = ORD_ORDER_ITEM_META.ORDER_ID")
//			.append(" AND ORD_PERSON.PERSON_TYPE = 'CONTACT') AS CONTACT_NAME,")
//			.append(" (SELECT ORD_PERSON.MOBILE")
//			.append(" FROM ORD_PERSON")
//			.append(" WHERE ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'")
//			.append(" AND ORD_PERSON.OBJECT_ID = ORD_ORDER_ITEM_META.ORDER_ID")
//			.append(" AND ORD_PERSON.PERSON_TYPE = 'CONTACT') AS CONTACT_MOBILE,")
//			 //添加身份证号 --start  
//			.append(" (SELECT ORD_PERSON.Cert_No")
//			.append(" FROM ORD_PERSON ")
//			.append(" WHERE ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'")
//			.append(" AND ORD_PERSON.OBJECT_ID =ORD_ORDER_ITEM_META.ORDER_ID")
//			.append(" AND ORD_PERSON.PERSON_TYPE = 'CONTACT' and ORD_PERSON.Cert_Type='ID_CARD') AS CONTACT_Cert_No,")
//             //---end    
			.append(" ORD_PERSON.NAME AS CONTACT_NAME ,")
			.append(" ORD_PERSON.MOBILE AS CONTACT_MOBILE,")
			.append(" ORD_PERSON.CERT_NO AS CONTACT_Cert_No,")
			.append(" ORD_PERSON.OBJECT_TYPE AS OBJECT_TYPE ,")
			
			.append(" ORD_ORDER_ITEM_META.VISIT_TIME  AS DEADLINE_TIME,")
			.append(" ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID,")
			.append(" ORD_ORDER_ITEM_META.FAX_MEMO,")
			.append(" SUP_PERFORM_TARGET.TARGET_ID,")
			.append(" ORD_ORDER.ORDER_STATUS,")
			.append(" ORD_ORDER.PAYMENT_TARGET,")
			
			.append(" (SELECT ORD_PERFORM.CREATE_TIME ")
            .append(" FROM ORD_PERFORM")
            .append(" WHERE (ORD_ORDER_ITEM_META.ORDER_ID =ORD_PERFORM.OBJECT_ID  and ORD_PERFORM.Object_Type='ORD_ORDER' and META_PERFORM.TARGET_ID = ORD_PERFORM.TARGET_ID ) OR (ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID = ORD_PERFORM.OBJECT_ID and ORD_PERFORM.Object_Type='ORD_ORDER_ITEM_META' and META_PERFORM.TARGET_ID = ORD_PERFORM.TARGET_ID ))")
			.append("  AS PERFORM_TIME , ")
  
			 .append(" SUP_PERFORM_TARGET.CERTIFICATE_TYPE ,")
			.append(" ORD_ORDER.CREATE_TIME as ORDER_CREATE_TIME ,")
			.append(" ORD_ORDER_ITEM_PROD.PRICE as PROD_PRICE ")
			
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
