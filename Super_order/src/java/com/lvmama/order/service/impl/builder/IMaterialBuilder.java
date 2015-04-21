package com.lvmama.order.service.impl.builder;

import com.lvmama.order.po.SQlBuilderMaterial;

/**
 * SQL构建器原材料构建器.<br>
 * 此类实现必须使用AOP代理
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public interface IMaterialBuilder {
	/**
	 * 构建{@link SQlBuilderMaterial}.
	 * 
	 * @param obj
	 *            Object
	 * @param material
	 *            material
	 * @return {@link SQlBuilderMaterial}
	 */
	SQlBuilderMaterial buildMaterial(Object obj, SQlBuilderMaterial material);

	/**
	 * 构建{@link SQlBuilderMaterial}.
	 * 
	 * @param obj
	 *            Object
	 * @param material
	 *            material
	 * @param businessflag
	 *            businessflag
	 * @return {@link SQlBuilderMaterial}
	 */
	SQlBuilderMaterial buildMaterial(Object obj, SQlBuilderMaterial material,
			boolean businessflag);
}
