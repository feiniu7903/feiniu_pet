package com.lvmama.order.service.impl;

import static com.lvmama.comm.utils.UtilityTool.isValid;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.pet.po.prod.ProdEContract;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.service.IProductOrder;
import com.lvmama.prd.dao.ProdEContractDAO;
import com.lvmama.prd.dao.ProdProductDAO;

/**
 * 线路销售产品订单创建服务.
 * 
 * @author liwenzhan
 * @author sunruyi
 * @version Super订单创建重构
 * @since Super订单创建重构
 * @see com.lvmama.order.po.OrderInfoDTO
 * 
 */
public final class RouteBuildImpl implements IProductOrder {
	/**
	 * 产品电子合同DAO.
	 */
	private ProdEContractDAO prodEContractDAO;
	/**
	 * 销售产品DAO.
	 */
	private ProdProductDAO prodProductDAO;

	/**
	 * 修改销售产品订单子项.
	 * 
	 * @param ordOrderItemProd
	 *            销售产品订单子项
	 * @return 销售产品订单子项{@link OrdOrderItemProd}
	 */
	@Override
	public OrdOrderItemProd modifyOrderInfo(final OrdOrder order,
			final OrdOrderItemProd ordOrderItemProd) {
		final ProdProduct prodProduct = prodProductDAO
				.selectProductDetailByPrimaryKey(ordOrderItemProd
						.getProductId());
		// 电子合同
		if (prodProduct.isEContract()) {
			final ProdEContract prodEContract = prodEContractDAO
					.selectByProductId(ordOrderItemProd.getProductId());
			if (isValid(prodEContract)
					&& isValid(prodEContract.getEContractTemplate())) {
				ordOrderItemProd
						.setNeedEContract(Constant.ECONTRACT_TYPE.NEED_ECONTRACT
								.name());
			} else {
				ordOrderItemProd
						.setNeedEContract(Constant.ECONTRACT_TYPE.NEEDNOT_ECONTRACT
								.name());
			}
		}
		// 产品订单应付金额，为酒店单房型计算订单总金额使用
		ordOrderItemProd.setOughtPay(ordOrderItemProd.getPrice()
				* ordOrderItemProd.getQuantity());
		return ordOrderItemProd;
	}

	/**
	 * 保存额外数据.<br>
	 * 需要进行额外CRUD操作时所要实现的方法
	 * 
	 * @param ordOrderItemProd
	 *            销售产品订单子项
	 * @return boolean true代表保存额外数据成功，false代表保存额外数据失败
	 */
	@Override
	public boolean saveAdditionData(final OrdOrderItemProd ordOrderItemProd) {
		return true;
	}

	/**
	 * 修改采购产品订单子项.
	 * 
	 * @param ordOrderItemMeta
	 *            采购产品订单子项
	 * @return 采购产品订单子项{@link OrdOrderItemMeta}
	 */
	@Override
	public OrdOrderItemMeta modifyOrdOrderItemMeta(
			final OrdOrderItemMeta ordOrderItemMeta) {
		return ordOrderItemMeta;
	}

	/**
	 * setProdEContractDAO.
	 * 
	 * @param prodEContractDAO
	 *            产品电子合同DAO
	 */
	public void setProdEContractDAO(final ProdEContractDAO prodEContractDAO) {
		this.prodEContractDAO = prodEContractDAO;
	}

	/**
	 * setProdProductDAO.
	 * 
	 * @param prodProductDAO
	 *            销售产品DAO
	 */
	public void setProdProductDAO(final ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}
}
