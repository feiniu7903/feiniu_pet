package com.lvmama.order.service.impl.builder;

import com.lvmama.order.po.SQlBuilderMaterial;

/**
 * 默认SQL构建器原材料构建器.
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public interface IDefaultMaterialBuilder {
	/**
	 * buildMaterial.
	 * 
	 * @param settlementBusinessFlag
	 *            settlementBusinessFlag
	 * @param invoiceBusinessFlag
	 *            invoiceBusinessFlag
	 * @param settlementItemBusinessFlag
	 *            settlementItemBusinessFlag
	 * @param ordOrderItemMetaBusinessFlag
	 *            ordOrderItemMetaBusinessFlag
	 * @param ordSettlementBusinessFlag
	 *            ordSettlementBusinessFlag
	 * @param ordOrderBusinessFlag
	 *            ordOrderBusinessFlag
	 * @param passPortDetailBusinessFlag
	 *            passPortDetailBusinessFlag
	 * @param passPortSummaryBusinessFlag
	 *            passPortSummaryBusinessFlag
	 * @param performDetailBusinessFlag
	 *            performDetailBusinessFlag
	 * @param material
	 *            {@link SQlBuilderMaterial}
	 * @return {@link SQlBuilderMaterial}
	 */
	SQlBuilderMaterial buildMaterial(boolean settlementBusinessFlag,
			boolean invoiceBusinessFlag, boolean settlementItemBusinessFlag,
			boolean ordOrderItemMetaBusinessFlag,
			boolean ordSettlementBusinessFlag, boolean ordOrderBusinessFlag,
			boolean passPortDetailBusinessFlag,
			boolean passPortSummaryBusinessFlag,
			boolean performDetailBusinessFlag, SQlBuilderMaterial material);
}
