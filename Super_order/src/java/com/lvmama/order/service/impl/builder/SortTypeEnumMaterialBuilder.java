package com.lvmama.order.service.impl.builder;

import java.util.List;

import com.lvmama.comm.bee.vo.ord.CompositeQuery.SortTypeEnum;
import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

/**
 * SQL构建器原材料构建器.
 * 
 * <pre>
 * 与订单相关排序类型有关的枚举，此类必须使用AOP代理
 * </pre>
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class SortTypeEnumMaterialBuilder implements IMaterialBuilder, TableName {
	/**
	 * 构建{@link SQlBuilderMaterial}.
	 * 
	 * @param obj
	 *            Object
	 * @param material
	 *            material
	 * @return {@link SQlBuilderMaterial}
	 */
	@Override
	public SQlBuilderMaterial buildMaterial(final Object obj,
			final SQlBuilderMaterial material) {
		final List<SortTypeEnum> sortList = (List<SortTypeEnum>) obj;
		for (SortTypeEnum st : sortList) {
			if (st.equals(SortTypeEnum.CREATE_TIME_ASC)) {
				material.getTableSet().add(ORD_ORDER);
				material.getOrderbySet().add("ORD_ORDER.CREATE_TIME ASC");
			}
			if (st.equals(SortTypeEnum.CREATE_TIME_DESC)) {
				material.getTableSet().add(ORD_ORDER);
				material.getOrderbySet().add("ORD_ORDER.CREATE_TIME DESC");
			}
			if (st.equals(SortTypeEnum.ORDER_ID_ASC)) {
				material.getTableSet().add(ORD_ORDER);
				material.getOrderbySet().add("ORD_ORDER.ORDER_ID ASC");
			}
			if (st.equals(SortTypeEnum.REDAIL_ASC)) {
				material.getTableSet().add(ORD_ORDER);
				material.getOrderbySet().add("ORD_ORDER.REDAIL ASC");
			}
			if (st.equals(SortTypeEnum.ORDER_ID_DESC)) {
				material.getTableSet().add(ORD_ORDER);
				material.getOrderbySet().add("ORD_ORDER.ORDER_ID DESC");
			}
			if (st.equals(SortTypeEnum.SALE_SERVICE_CREATE_TIME_ASC)) {
				material.getTableSet().add(ORD_SALE_SERVICE);
				material.getOrderbySet()
						.add("ORD_SALE_SERVICE.CREATE_TIME ASC");
			}
			if (st.equals(SortTypeEnum.SALE_SERVICE_CREATE_TIME_DESC)) {
				material.getTableSet().add(ORD_SALE_SERVICE);
				material.getOrderbySet().add(
						"ORD_SALE_SERVICE.CREATE_TIME DESC");
			}
	
			if (st.equals(SortTypeEnum.ORDER_ITEM_META_ID_ASC)) {
				material.getTableSet().add(ORD_ORDER_ITEM_META);
				material.getOrderbySet().add(
						"ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID ASC");
			}
			if (st.equals(SortTypeEnum.ORDER_ITEM_META_ID_DESC)) {
				material.getTableSet().add(ORD_ORDER_ITEM_META);
				material.getOrderbySet().add(
						"ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID DESC");
			}
		}
		return material;
	}

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
	@Override
	public SQlBuilderMaterial buildMaterial(final Object obj,
			final SQlBuilderMaterial material, final boolean businessflag) {
		return material;
	}
}
