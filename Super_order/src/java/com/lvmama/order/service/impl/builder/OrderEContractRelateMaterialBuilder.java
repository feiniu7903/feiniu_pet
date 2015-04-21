package com.lvmama.order.service.impl.builder;

import com.lvmama.comm.bee.vo.ord.CompositeQuery.EContractRelate;
import com.lvmama.comm.utils.SecurityTool;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

public class OrderEContractRelateMaterialBuilder  implements IMaterialBuilder, TableName {

	@Override
	public SQlBuilderMaterial buildMaterial(final Object obj,
			final SQlBuilderMaterial material) {
		final EContractRelate eContractRelate = (EContractRelate) obj;
		if (UtilityTool.isValid(eContractRelate.getNeedEContract())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.NEED_CONTRACT = '"
							+ SecurityTool.preventSqlInjection(eContractRelate.getNeedEContract().toString()) + "'");
		}
		if (UtilityTool.isValid(eContractRelate.getEContractStatus())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.ECONTRACT_STATUS = '"
							+ SecurityTool.preventSqlInjection(eContractRelate.getEContractStatus().toString()) + "'");
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
