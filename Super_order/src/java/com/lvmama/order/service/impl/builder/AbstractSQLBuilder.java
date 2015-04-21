package com.lvmama.order.service.impl.builder;

import static com.lvmama.comm.utils.UtilityTool.isValid;

import org.apache.commons.logging.Log;

import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.SQLBuilder;
import com.lvmama.order.service.builder.SqlKeyWord;

/**
 * 抽象SQL构建器.
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
 * @see com.lvmama.UtilityTool#isValid(Object)
 * @see com.lvmama.order.po.SQlBuilderMaterial
 * @see com.lvmama.order.service.builder.SQLBuilder
 * @see com.lvmama.order.service.builder.SqlKeyWord
 */
public abstract class AbstractSQLBuilder implements SQLBuilder, SqlKeyWord {
	/**
	 * SQL.
	 */
	private final transient StringBuilder stringBuilder = new StringBuilder();

	/**
	 * 获取SELECT.
	 *
	 * @return SELECT
	 */
	public abstract String getSelect();

	/**
	 * 获取Log.
	 *
	 * @return Log
	 */
	public abstract Log getLog();

	/**
	 * 构建SELECT语句.
	 *
	 * @param material
	 *            SQL构建器原材料
	 */
	@Override
	public void buildSQLSelectStatement(final SQlBuilderMaterial material) {
		if (!isValid(getSelect())) {
			throw new IllegalArgumentException("select statement is null!");
		}
		stringBuilder.append(getSelect());
	}

	/**
	 * 构建FROM语句.
	 *
	 * @param material
	 *            SQL构建器原材料
	 */
	@Override
	public void buildSQLFromStatement(final SQlBuilderMaterial material) {
		if (material.getTableSet().isEmpty()) {
			throw new IllegalArgumentException("from statement is null!");
		}
		int i = 0;
		for (String table : material.getTableSet()) {
			if (0 == i) {
				stringBuilder.append(FROM);
			}
			stringBuilder.append(table);
			if ((material.getTableSet().size() - 1) > i) {
				stringBuilder.append(COMMA);
			}
			i++;
		}
	}

	/**
	 * 构建WHERE语句.
	 *
	 * @param material
	 *            SQL构建器原材料
	 */
	@Override
	public void buildSQLWhereStatement(final SQlBuilderMaterial material) {
		int i = 0;
		for (String condition : material.getConditionSet()) {
			if (0 == i) {
				stringBuilder.append(WHERE);
			}
			stringBuilder.append(condition);
			if ((material.getConditionSet().size() - 1) > i) {
				stringBuilder.append(AND);
			}
			i++;
		}
	}

	/**
	 * 构建ORDER BY语句.
	 *
	 * @param material
	 *            SQL构建器原材料
	 */
	@Override
	public void buildSQLOrderByStatement(final SQlBuilderMaterial material) {
		int i = 0;
		for (String orderby : material.getOrderbySet()) {
			if (0 == i) {
				stringBuilder.append(ORDER_BY);
			}
			stringBuilder.append(orderby);
			if ((material.getOrderbySet().size() - 1) > i) {
				stringBuilder.append(COMMA);
			}
			i++;
		}
	}

	/**
	 * 构建GROUP BY语句.
	 *
	 * @param material
	 *            SQL构建器原材料
	 */
	@Override
	public void buildSQLGroupByStatement(final SQlBuilderMaterial material) {
		int i = 0;
		for (String orderby : material.getGroupBySet()) {
			if (0 == i) {
				stringBuilder.append(GROUP_BY);
			}
			stringBuilder.append(orderby);
			if ((material.getGroupBySet().size() - 1) > i) {
				stringBuilder.append(COMMA);
			}
			i++;
		}
	}

	/**
	 * 分页SQL1.
	 */
	private static final String PAGE1 = "SELECT * FROM (SELECT T.*, ROWNUM AS ROWNUM1 FROM (";
	/**
	 * 分页SQL2.
	 */
	private static final String PAGE2 = ") T WHERE ROWNUM <= ";

	/**
	 * 分页SQL3.
	 */
	private static final String PAGE3 = ") WHERE ROWNUM1 >=";

	/**
	 * 构建分页语句.
	 *
	 * @param material
	 *            SQL构建器原材料
	 */
	@Override
	public void buildSQLPageIndexStatement(final SQlBuilderMaterial material) {
		if (isValid(material.getBeginIndex())
				&& isValid(material.getEndIndex())) {
			stringBuilder.insert(0, PAGE1)
					.insert(stringBuilder.length(), PAGE2)
					.append(material.getEndIndex()).append(PAGE3)
					.append(material.getBeginIndex());
		}
	}

	/**
	 * 构建完整SQl.
	 *
	 * @return 完整SQl.
	 */
	@Override
	public String buildCompleteSQLStatement() {
		final String sql = stringBuilder.toString();
		if (getLog().isDebugEnabled()) {
			getLog().debug(sql);
		}
		return sql;
	}

	/**
	 * SQL1.
	 */
	private static final String SQL1 = "SELECT DISTINCT ORD_ORDER.ORDER_ID, ORD_ORDER.USER_ID FROM ORD_ORDER, ORD_ORDER_ITEM_META WHERE ORD_ORDER.ORDER_ID = ORD_ORDER_ITEM_META.ORDER_ID AND ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID IN (SELECT ORDER_ITEM_META_ID FROM (";
	/**
	 * SQL2.
	 */
	private static final String SQL2 = "))";

	/**
	 * buildOrderItemMetaIdInStatement.
	 *
	 * @return SQl
	 */
	@Override
	public String buildOrderItemMetaIdInStatement() {
		final String sql = stringBuilder.insert(0, SQL1).append(SQL2)
				.toString();
		if (getLog().isDebugEnabled()) {
			getLog().debug(sql);
		}
		return sql;
	}

	/**
	 * buildPassPortSummarySQLBuilderCount.
	 *
	 * @return SQl
	 */
	@Override
	public String buildPassPortSummarySQLBuilderCount() {
		return null;
	}
}
