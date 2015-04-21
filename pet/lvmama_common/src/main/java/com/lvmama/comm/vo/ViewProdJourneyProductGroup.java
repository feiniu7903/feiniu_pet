/**
 * 
 */
package com.lvmama.comm.vo;

import java.util.List;

import com.lvmama.comm.bee.po.prod.ProdJourneyProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.ProductUtil;

/**
 * 行程产品组,机票使用
 * @author yangbin
 *
 */
public class ViewProdJourneyProductGroup extends ProdJourneyProduct{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1730438387917095477L;

	private List<ProdJourneyProduct> prodJourneyProductList;
	
	private ProdJourneyProduct journeyProduct;
	
	private String journeyProductIds;

	public void setProdJourneyProductList(
			List<ProdJourneyProduct> prodJourneyProductList) {
		this.prodJourneyProductList = prodJourneyProductList;
		this.journeyProduct = prodJourneyProductList.get(0);
		
		StringBuffer sb=new StringBuffer();
		for(ProdJourneyProduct pjp:prodJourneyProductList){
			sb.append(pjp.getProdBranch().getBranchType());
			sb.append("-");
			sb.append(pjp.getProdBranchId());
			sb.append("-");
			sb.append(pjp.getJourneyProductId());
			sb.append(",");
		}
		sb.setLength(sb.length()-1);
		journeyProductIds = sb.toString();
	}

	@Override
	public String getRequire() {
		return journeyProduct.getRequire();
	}

	@Override
	public boolean hasRequire() {
		return journeyProduct.hasRequire();
	}

	@Override
	public boolean hasDefaultProduct() {
		return journeyProduct.hasDefaultProduct();
	}

	@Override
	public String getDefaultProduct() {
		return journeyProduct.getDefaultProduct();
	}

	@Override
	public float getDiscountYuan() {
		return journeyProduct.getDiscountYuan();
	}

	@Override
	public Long getSellPrice() {
		return journeyProduct.getSellPrice();
	}

	public List<ProdJourneyProduct> getProdJourneyProductList() {
		return prodJourneyProductList;
	}

	@Override
	public ProdProductBranch getProdBranch() {
		return journeyProduct.getProdBranch();
	}

	@Override
	public String getJourneyProductIds() {		
		return journeyProductIds;
	}
		
	@Override
	public float calcTotalSellPrice(Integer adult, Integer child) {
		long total=0;
		for(ProdJourneyProduct pjp:prodJourneyProductList){
			long price=pjp.getSellPrice()-pjp.getDiscount();
			if(ProductUtil.isAdultTicket(pjp.getProdBranch())){
				price*=adult;
			}else{
				price*=child;
			}
			total+=price;
		}
		return PriceUtil.convertToYuan(total);
	}

	@Override
	public Long getJourneyProductId() {
		return journeyProduct.getJourneyProductId();
	}
	
	
}
