package com.lvmama.order.service.builder;

import com.lvmama.order.po.SQlBuilderMaterial;

/**
 * SQL构建器.
 *
 * <pre>
 * 构建SQL
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.order.po.SQlBuilderMaterial
 */
public interface SQLBuilder {
	/**
	 * 构建SELECT语句.
	 *
	 * @param material
	 *            SQL构建器原材料
	 */
	void buildSQLSelectStatement(SQlBuilderMaterial material);

	/**
	 * 构建FROM语句.
	 *
	 * @param material
	 *            SQL构建器原材料
	 */
	void buildSQLFromStatement(SQlBuilderMaterial material);

	/**
	 * 构建WHERE语句.
	 *
	 * @param material
	 *            SQL构建器原材料
	 */
	void buildSQLWhereStatement(SQlBuilderMaterial material);

	/**
	 * 构建ORDER BY语句.
	 *
	 * @param material
	 *            SQL构建器原材料
	 */
	void buildSQLOrderByStatement(SQlBuilderMaterial material);

	/**
	 * 构建GROUP BY语句.
	 *
	 * @param material
	 *            SQL构建器原材料
	 */
	void buildSQLGroupByStatement(SQlBuilderMaterial material);

	/**
	 * 构建分页语句.
	 *
	 * @param material
	 *            SQL构建器原材料
	 */
	void buildSQLPageIndexStatement(SQlBuilderMaterial material);

	/**
	 * 构建完整SQl.
	 *
	 * @return 完整SQl.
	 */
	String buildCompleteSQLStatement();

	/**
	 * buildOrderItemMetaIdInStatement.
	 *
	 * @return SQl
	 */
	String buildOrderItemMetaIdInStatement();

	/**
	 * buildPassPortSummarySQLBuilderCount.
	 *
	 * @return SQl
	 */
	String buildPassPortSummarySQLBuilderCount();
}
