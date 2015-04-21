package com.lvmama.back.sweb.meta;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.vo.Constant.FIN_CURRENCY;


/**
 * 该类为采购产品的基类.
 * 不用区分采购产品类型的，并且不需要通过set的表单方式来给metaProduct传值.
 * @author yangbin
 *
 */
public abstract class MetaProductFormAction extends AbstractMetaProductAction{

	protected MetaProduct metaProduct;
	// 币种
	protected FIN_CURRENCY currency;
	
	public MetaProductFormAction(String menuType) {
		super(menuType);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -336172598553859086L;

	@Override
	public void doBefore() {
		// TODO Auto-generated method stub
		metaProduct=metaProductService.getMetaProduct(metaProductId);
		currency = FIN_CURRENCY.valueOf(metaProduct.getCurrencyType());
		metaProductType=metaProduct.getProductType();
	}

	public MetaProduct getMetaProduct() {
		return metaProduct;
	}
	
	
}
