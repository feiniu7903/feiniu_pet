package com.lvmama.order.service.builder;

import com.lvmama.comm.bee.vo.ord.CompositeQuery;


/**
 * SQL控制器.
 *
 * <pre>
 * 控制SQL的生成
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.ord.service.po.CompositeQuery
 */
public interface Director extends BusinessFlag {
	/**
	 * setCompositeQuery.
	 *
	 * @param query
	 *            综合查询
	 */
	void setCompositeQuery(CompositeQuery query);

	/**
	 * 命令SQL构建器构建SQL.
	 * <br/>注:调用此方法等同于调用order(builder,true);
	 * @param builder
	 *            SQL构建器
	 */
	void order(SQLBuilder builder);
	/**
	 * 命令SQL构建器构建SQL.
	 * @param builder SQL构建器.
	 * @param pageable 是否进行分页, 值为true进行分页处理,值为false不进行分页处理.
	 */
	void order(SQLBuilder builder,boolean pageable);
}
