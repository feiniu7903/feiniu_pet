package com.lvmama.prd.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.prod.ProdProductItem;
import com.lvmama.comm.bee.service.prod.ProdProductItemService;
import com.lvmama.comm.utils.LogViewUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.prd.dao.MetaProductDAO;
import com.lvmama.prd.dao.ProdProductDAO;
import com.lvmama.prd.dao.ProdProductItemDAO;
import com.lvmama.prd.logic.ProductTimePriceLogic;

public class ProdProductItemServiceImpl implements ProdProductItemService{
	
	private ProdProductItemDAO prodProductItemDAO;
	private ProdProductDAO prodProductDAO;
	private MetaProductDAO metaProductDAO;
	private ComLogDAO comLogDAO;
	private ProductTimePriceLogic productTimePriceLogic;
	
	
	public List<Date> checkTimePrice(ProdProductItem productItem){
		List<Date> list = new ArrayList<Date>();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 00);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		list = productTimePriceLogic.checkTimePriceContain(new Date(cal.getTimeInMillis()), productItem.getProductId(), productItem.getMetaProductId()); 
		
		return list;
	}

	public void updateProductItem(ProdProductItem productItem) {
		prodProductItemDAO.updateByPrimaryKey(productItem);
	}
	
	public void deleteProductItem(Long productId, Long productItemId,String productName,String operatorName) {
		prodProductItemDAO.deleteByPrimaryKey(productItemId);
		updatePaymentTarget(productId);
		comLogDAO.insert("PROD_PRODUCT_ITEM",productId,productItemId,operatorName,
				Constant.COM_LOG_ORDER_EVENT.deleteProdProductItem.name(),
				"删除采购产品绑定",LogViewUtil.logDeleteStr(operatorName)+"删除与"+productName+"绑定;", "PROD_PRODUCT");
	}
	
	public List<ProdProductItem> getProductItems(Long productId) {
		return prodProductItemDAO.selectProductItems(productId);
	}
	
	public void addProductItem(ProdProductItem productItem,String operatorName) {
		MetaProduct newProdProduct=this.metaProductDAO.getMetaProductByPk(productItem.getMetaProductId());
		prodProductItemDAO.insert(productItem);
		updatePaymentTarget(productItem.getProductId());
		comLogDAO.insert("PROD_PRODUCT_ITEM",productItem.getProductId(),null,operatorName,
				Constant.COM_LOG_ORDER_EVENT.insertProdProductItem.name(),
				"添加采购产品绑定", LogViewUtil.logNewStr(operatorName)+"添加与"+newProdProduct.getProductName()+"绑定", "PROD_PRODUCT");
	}
	
	public void updatePaymentTarget(Long productId) {
		boolean payToLvmama = false;
		boolean payToSupplier = false;
		List<MetaProduct> metas = metaProductDAO.getMetaProductByProductId(productId);
		for (MetaProduct metaProduct : metas) {
			if (metaProduct.isPaymentToLvmama()) {
				payToLvmama = true;
			}
			if (metaProduct.isPaymentToSupplier()) {
				payToSupplier = true;
			}
		}
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("productId", productId);
		param.put("payToLvmama", Boolean.toString(payToLvmama));
		param.put("payToSupplier", Boolean.toString(payToSupplier));
	    this.prodProductDAO.updatePaymentTarget(param);		
	}

	@Override
	public List<ProdProductItem> selectProdProductByMetaId(Long metaProductId) {
		return this.prodProductItemDAO.selectProdProductByMetaId(metaProductId);
	}
	public boolean existsProductItem(Long productId, Long productItemId) {
		return prodProductItemDAO.existsProductItem(productId, productItemId);
	}

	public void setProdProductItemDAO(ProdProductItemDAO productItemDAO) {
		this.prodProductItemDAO = productItemDAO;
	}

	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}

	public void setMetaProductDAO(MetaProductDAO metaProductDAO) {
		this.metaProductDAO = metaProductDAO;
	}

	public ProductTimePriceLogic getProductTimePriceLogic() {
		return productTimePriceLogic;
	}

	public void setProductTimePriceLogic(ProductTimePriceLogic productTimePriceLogic) {
		this.productTimePriceLogic = productTimePriceLogic;
	}

	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}

}
