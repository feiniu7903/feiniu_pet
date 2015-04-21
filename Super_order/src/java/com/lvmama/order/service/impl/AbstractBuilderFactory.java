package com.lvmama.order.service.impl;

import com.lvmama.order.service.builder.Director;
import com.lvmama.order.service.builder.SQLBuilder;

/**
 * 抽象构造工厂.
 *
 * <pre></pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.order.service.builder.Director
 * @see com.lvmama.order.service.builder.SQLBuilder
 */
public abstract class AbstractBuilderFactory {
	/**
	 * 使用方法注入.
	 *
	 * <pre>
	 * 确保每次使用时都是一个新实例
	 * </pre>
	 *
	 * @return DirectorImpl
	 */
	public abstract Director getDirector();

	/**
	 * 使用方法注入.
	 *
	 * <pre>
	 * 确保每次使用时都是一个新实例
	 * </pre>
	 *
	 * @return SQLBuilderImpl
	 */
	public abstract SQLBuilder getSQLBuilder();
	
	
	/**
	 * 
	 * @return
	 */
	public abstract SQLBuilder getSQLBuilderSum();

	/**
	 * 使用方法注入.
	 *
	 * <pre>
	 * 确保每次使用时都是一个新实例
	 * </pre>
	 *
	 * @return SQLBuilderCountImpl
	 */
	public abstract SQLBuilder getSQLBuilderCount();

	/**
	 * 使用方法注入.
	 *
	 * <pre>
	 * 确保每次使用时都是一个新实例
	 * </pre>
	 *
	 * @return SaleServiceSQLBuilderImpl
	 */
	public abstract SQLBuilder getSaleServiceSQLBuilder();

	/**
	 * 使用方法注入.
	 *
	 * <pre>
	 * 确保每次使用时都是一个新实例
	 * </pre>
	 *
	 * @return SaleServiceSQLBuilderCountImpl
	 */
	public abstract SQLBuilder getSaleServiceSQLBuilderCount();

	/**
	 * 使用方法注入.
	 *
	 * <pre>
	 * 确保每次使用时都是一个新实例
	 * </pre>
	 *
	 * @return SettlementQueueSQLBuilderImpl
	 */
	public abstract SQLBuilder getSettlementQueueSQLBuilder();

	/**
	 * 使用方法注入.
	 *
	 * <pre>
	 * 确保每次使用时都是一个新实例
	 * </pre>
	 *
	 * @return SettlementQueueSQLBuilderCountImpl
	 */
	public abstract SQLBuilder getSettlementQueueSQLBuilderCount();

	/**
	 * 使用方法注入.
	 *
	 * <pre>
	 * 确保每次使用时都是一个新实例
	 * </pre>
	 *
	 * @return InvoiceSQLBuilderImpl
	 */
	public abstract SQLBuilder getInvoiceSQLBuilder();

	/**
	 * 使用方法注入.
	 *
	 * <pre>
	 * 确保每次使用时都是一个新实例
	 * </pre>
	 *
	 * @return InvoiceSQLBuilderCountImpl
	 */
	public abstract SQLBuilder getInvoiceSQLBuilderCount();

	/**
	 * 使用方法注入.
	 *
	 * <pre>
	 * 确保每次使用时都是一个新实例
	 * </pre>
	 *
	 * @return SettlementItemSQLBuilderImpl
	 */
	public abstract SQLBuilder getSettlementItemSQLBuilder();

	/**
	 * 使用方法注入.
	 *
	 * <pre>
	 * 确保每次使用时都是一个新实例
	 * </pre>
	 *
	 * @return SettlementItemSQLBuilderCountImpl
	 */
	public abstract SQLBuilder getSettlementItemSQLBuilderCount();

	/**
	 * 使用方法注入.
	 *
	 * <pre>
	 * 确保每次使用时都是一个新实例
	 * </pre>
	 *
	 * @return OrdOrderItemMetaSQLBuilderImpl
	 */
	public abstract SQLBuilder getOrdOrderItemMetaSQLBuilder();

	/**
	 * 使用方法注入.
	 *
	 * <pre>
	 * 确保每次使用时都是一个新实例
	 * </pre>
	 *
	 * @return OrdOrderItemMetaSQLBuilderCountImpl
	 */
	public abstract SQLBuilder getOrdOrderItemMetaSQLBuilderCount();

	/**
	 * 使用方法注入.
	 *
	 * <pre>
	 * 确保每次使用时都是一个新实例
	 * </pre>
	 *
	 * @return OrdSettlementSQLBuilderImpl
	 */
	public abstract SQLBuilder getOrdSettlementSQLBuilder();

	/**
	 * 使用方法注入.
	 *
	 * <pre>
	 * 确保每次使用时都是一个新实例
	 * </pre>
	 *
	 * @return OrdSettlementSQLBuilderCountImpl
	 */
	public abstract SQLBuilder getOrdSettlementSQLBuilderCount();

	/**
	 * 使用方法注入.
	 *
	 * <pre>
	 * 确保每次使用时都是一个新实例
	 * </pre>
	 *
	 * @return PerformDetailSQLBuilderImpl
	 */
	public abstract SQLBuilder getPerformDetailSQLBuilder();

	/**
	 * 使用方法注入.
	 *
	 * <pre>
	 * 确保每次使用时都是一个新实例
	 * </pre>
	 *
	 * @return PerformDetailSQLBuilderCountImpl
	 */
	public abstract SQLBuilder getPerformDetailSQLBuilderCount();

	/**
	 * 使用方法注入.
	 *
	 * <pre>
	 * 确保每次使用时都是一个新实例
	 * </pre>
	 *
	 * @return PassPortDetailSQLBuilderImpl
	 */
	public abstract SQLBuilder getPassPortDetailSQLBuilder();

	/**
	 * 使用方法注入.
	 *
	 * <pre>
	 * 确保每次使用时都是一个新实例
	 * </pre>
	 *
	 * @return PassPortDetailSQLBuilderCountImpl
	 */
	public abstract SQLBuilder getPassPortDetailSQLBuilderCount();

	/**
	 * 使用方法注入.
	 *
	 * <pre>
	 * 确保每次使用时都是一个新实例
	 * </pre>
	 *
	 * @return PassPortSummarySQLBuilderImpl
	 */
	public abstract SQLBuilder getPassPortSummarySQLBuilder();

	/**
	 * 使用方法注入.
	 *
	 * <pre>
	 * 确保每次使用时都是一个新实例
	 * </pre>
	 *
	 * @return PassPortSummarySQLBuilderCountImpl
	 */
	public abstract SQLBuilder getPassPortSummarySQLBuilderCount();
	
	public abstract SQLBuilder getFinishedSettlementItemSQLBuilder() ; 
	public abstract SQLBuilder getFinishedSettlementItemSQLBuilderCount();
}
