package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.PriceUtil;

public class ProdJourneyProduct implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1671234295127811545L;

	private Long journeyProductId;    

    private String require;

    private String defaultProduct;

    private Long discount;

    private Long prodJourenyId;
    private ProdProductJourney productJourney;

    private Long prodBranchId;
    
    private ProdProductBranch prodBranch;
    
    private ProdPackJourneyProduct prodPackJourneyProduct;
    
    private Long productId;//此值由ProdProductBranch当中提供

    public Long getJourneyProductId() {
        return journeyProductId;
    }

    public void setJourneyProductId(Long journeyProductId) {
        this.journeyProductId = journeyProductId;
    }

    public String getRequire() {
        return require;
    }

    public void setRequire(String require) {
        this.require = require == null ? null : require.trim();
    }
    
    public boolean hasRequire(){
    	return StringUtils.equals(require, "true");
    }
    public boolean hasDefaultProduct(){
    	return StringUtils.equals(defaultProduct, "true");
    }

    public String getDefaultProduct() {
        return defaultProduct;
    }

    public void setDefaultProduct(String defaultProduct) {
        this.defaultProduct = defaultProduct;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Long getProdJourenyId() {
        return prodJourenyId;
    }

    public void setProdJourenyId(Long prodJourenyId) {
        this.prodJourenyId = prodJourenyId;
    }

    public Long getProdBranchId() {
        return prodBranchId;
    }

    public void setProdBranchId(Long prodBranchId) {
        this.prodBranchId = prodBranchId;
    }

	

	/**
	 * @return the productId
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * @return the prodBranch
	 */
	public ProdProductBranch getProdBranch() {
		return prodBranch;
	}

	/**
	 * @param prodBranch the prodBranch to set
	 */
	public void setProdBranch(ProdProductBranch prodBranch) {
		this.prodBranch = prodBranch;
	}
    
 
	public void setDiscountYuan(float f){
		this.discount=PriceUtil.convertToFen(f);
	}
	public float getDiscountYuan(){
		return PriceUtil.convertToYuan(discount==null?0L:discount);
	}
	
	/**
	 * 如果是单房型酒店，产品的销售价已经计算为行程晚数的总价
	 * 所以在这里是取优惠价是直接用总价-晚数*优惠,
	 * 其他的产品取的是一天的价格
	 * @return
	 */
	public Long getSellPrice(){
		if(prodBranch==null){
			return 0L;
		}
		if(prodBranch.getProdProduct().isHotel()&&prodBranch.getProdProduct().isSingleRoom()){
			return prodBranch.getSellPrice()-(getDiscount()*productJourney.getMaxTime().getNights());
		}else{
			return prodBranch.getSellPrice()-getDiscount();
		}		
	}
	
	public float getSellPriceYuan(){
		return PriceUtil.convertToYuan(getSellPrice());
	}

	/**
	 * @return the productJourney
	 */
	public ProdProductJourney getProductJourney() {
		return productJourney;
	}

	/**
	 * @param productJourney the productJourney to set
	 */
	public void setProductJourney(ProdProductJourney productJourney) {
		this.productJourney = productJourney;
	}
	
	/**
	 * 给组合时能读取对应的所有的ID列表，大交通当中会出现
	 * @return
	 */
	public String getJourneyProductIds(){
		StringBuffer sb=new StringBuffer();
		if(prodBranch!=null){
			sb.append(this.prodBranch.getBranchType());
			sb.append("-");
			sb.append(prodBranchId);
			sb.append("-");
			sb.append(journeyProductId);
		}
		return sb.toString();
	}	
	
	/**
	 * 只有单个的时候计算的是成人数
	 * @param adult
	 * @param child
	 * @return
	 */
	public float calcTotalSellPrice(Integer adult,Integer child){
		return getSellPriceYuan()*adult;
	}

	public ProdPackJourneyProduct getProdPackJourneyProduct() {
		return prodPackJourneyProduct;
	}

	public void setProdPackJourneyProduct(
			ProdPackJourneyProduct prodPackJourneyProduct) {
		this.prodPackJourneyProduct = prodPackJourneyProduct;
	}
	
	public String getYesOrNoPackJournetProduct(){
		if(hasRequire()){
			return "true";
		}
		if(prodPackJourneyProduct!=null && prodPackJourneyProduct.getProdBranchId().equals(this.getProdBranchId())){
			return "true";
		}
		return "false";
	}
}