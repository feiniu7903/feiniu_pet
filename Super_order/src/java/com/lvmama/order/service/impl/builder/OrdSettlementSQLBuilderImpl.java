package com.lvmama.order.service.impl.builder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * SQL构建器.
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
public final class OrdSettlementSQLBuilderImpl extends AbstractSQLBuilder {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory
			.getLog(OrdSettlementSQLBuilderImpl.class);
	/**
	 * 要查询的字段.
	 */
	private static final String SELECT = "SELECT DISTINCT ORD_SETTLEMENT.* ";

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
