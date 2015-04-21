package com.lvmama.order.service.builder;

/**
 * SQL关键字.
 *
 * <pre>
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public interface SqlKeyWord {
	/**
	 * 字符串FROM.
	 */
	String FROM = " FROM ";
	/**
	 * 字符串COMMA.
	 */
	String COMMA = ", ";

	/**
	 * 字符串WHERE.
	 */
	String WHERE = " WHERE ";
	/**
	 * 字符串AND.
	 */
	String AND = " AND ";
	/**
	 * 字符串ORDER_BY.
	 */
	String ORDER_BY = " ORDER BY ";
	/**
	 * 字符串GROUP_BY.
	 */
	String GROUP_BY = " GROUP BY ";
	/**
	 * 字符串).
	 */
	String LEFT_PARENTHESIS = ")";
}
