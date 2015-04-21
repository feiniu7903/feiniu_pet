package com.lvmama.comm.pet.po.shop;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.vo.Constant;
/**
 * 积分商城的产品
 * @author Brian
 *
 */
public class ShopProduct implements Serializable{
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 3928515206967608081L;
	/**
	 * 产品标识
	 */
	private Long productId;
	/**
	 * 产品编号
	 */
	private String productCode;
	/**
	 * 产品名称
	 */	
	private String productName;
	/**
	 * 积分
	 */	
	private Long pointChange;
	/**
	 * 市场价
	 */	
	private Long marketPrice;
	/**
	 * 库存
	 */	
	private Long stocks = 0l;
	/**
	 * 是否上线
	 */
	private String isValid = "N";
	/**
	 * 兑换类型，默认为积分兑换
	 */
	private String changeType = Constant.SHOP_CHANGE_TYPE.POINT_CHANGE.name();
	/**
	 * 产品类型，默认为实物
	 */	
	private String productType = Constant.SHOP_PRODUCT_TYPE.PRODUCT.name();
	/**
	 * 是否热推产品
	 */	
	private String isHotProduct = "N";
	/**
	 * 是否推荐产品
	 */	
	private String isRecommend = "N";
	/**
	 * 图片地址
	 */	
	private String pictures;
	/**
	 * 详细内容
	 */
	private String content;
	/**
	 * 创建时间
	 */	
	private Date createTime;
	/**
	 * 修改时间
	 */	
	private Date modifyTime;
	/**
	 * 限制条件
	 */
	private List<ShopProductCondition> shopProductConditions =new ArrayList<ShopProductCondition>();
	/**
	 * 中奖率
	 */
	private Double winningRate;
	/**
	 * 有效活动开始时间
	 */
	private Date beginTime;
	/**
	 * 活动有效结束时间
	 */
	private Date endTime;
	
	/**
	 * 是否需要手机验证　
	 */
	private String isValidate;
	/**
	 * 获得中文的产品类型
	 * @return 中文的产品类型
	 */
	public String getChProductType() {
		return Constant.SHOP_PRODUCT_TYPE.getCnName(productType);
	}
	
	/**
	 * 获取中文的兑换类型
	 * @return 中文的兑换类型
	 */
	public String getChChangeType() {
		return Constant.SHOP_CHANGE_TYPE.getCnName(changeType);
	}	

	/**
	 * 获得中文的上下线描述
	 * @return 中文上下线描述
	 */
	public String getChIsValid() {
		if ("Y".equals(this.isValid)) {
			return "在线";
		} else {
			return "已下线";
		}
	}
	
	/**
	 * 是否显示上线
	 * @return TRUE/FALSE
	 */
	public String getShowOnline() {
		return new Boolean(!"Y".equals(this.isValid)).toString();
	}
	
	/**
	 * 是否显示下线
	 * @return TRUE/FALSE
	 */
	public String getShowOffline() {
		return new Boolean("Y".equals(this.isValid)).toString();
	}
	
	/**
	 * 是否显示编辑库存按钮
	 * @param productType
	 * @return
	 */
	public String getEditStock(){
		if(!"Y".equals(this.isValid) && Constant.SHOP_PRODUCT_TYPE.COOPERATION_COUPON.name().equals(productType)){
			return Boolean.TRUE.toString();
		}
		return Boolean.FALSE.toString();
	}
	
	/**
	 * 获取中文的推荐描述
	 * @return 中文的推荐描述
	 */
	public String getChCommend() {
		StringBuilder sb = new StringBuilder();
		if ("Y".equals(this.isHotProduct)) {
			sb.append("热卖推荐 ");
		}
		if ("Y".equals(this.isRecommend)) {
			sb.append("首页推荐");
		}
		return sb.toString();
	}
	
	/**
	 * 获取图片地址的绝对地址
	 * @return 产品图片的绝对地址
	 */
	public List<String> getAbsolutePictureUrl() {
		if (null != pictures && !"".equals(pictures)) {
			List<String> absoPicUrl = new ArrayList<String>();
			String[] picturess = pictures.split(",");
			for (String pic : picturess) {
				absoPicUrl.add(Constant.getInstance().getPrefixPic() + pic);
			}
			return absoPicUrl;
		} else {
			return null;
		}
	}
	
	/**
	 * 获取小于4张的图片地址的绝对地址
	 * @return 产品图片的绝对地址
	 */
	public List<String> getFourAbsolutePictureUrl() {
		
		List<String> picturess = getAbsolutePictureUrl();
		if (null != picturess && picturess.size() > 4) {
			return picturess.subList(0, 4);
		} else {
			return picturess;
		}
	}
	
	/** 
	* 获取第一张图片地址的绝对地址 
	* @return 产品图片的绝对地址 
	*/ 
	public String getFirstAbsolutePictureUrl() {
		List<String> picturess = getAbsolutePictureUrl();
		if (null != picturess && picturess.size() > 0) {
			return picturess.get(0);
		} else {
			return "";
		}
	}

	
	public String getWinningRateForString(){
		DecimalFormat df = new DecimalFormat("0.00000");
		String winningRateString = "";
		if (this.winningRate != null) {
			winningRateString = df.format(this.winningRate);
		}
		return winningRateString;
	}
	
	/**
	 *产品是否可售
	 * @return Y/N
	 */
	public String getIsCanSell() {
		String isCanSell = "N";
		if(isValid.equalsIgnoreCase("Y") && stocks > 0){
			Date date = new Date();
			if(beginTime != null && endTime != null){
				if (date.getTime() > beginTime.getTime() && date.getTime() < endTime.getTime()) {
					isCanSell = "Y";
				}
			}else{
				isCanSell = "Y";
			}
		}
		return isCanSell;
	}
	
	/**
	 *产品剩余存活时间(毫秒)
	 * @return long
	 */
	public long getLifeTime() {
		long lifeTime = 0;
		Date date = new Date();
		if(beginTime != null && endTime != null){
			if (date.getTime() > beginTime.getTime()
					&& date.getTime() < endTime.getTime()) {
				lifeTime = endTime.getTime() - date.getTime();
			}
		}
		return lifeTime;
	}
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getPointChange() {
		return pointChange;
	}
	public void setPointChange(Long pointChange) {
		this.pointChange = pointChange;
	}
	public Long getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Long getStocks() {
		return stocks;
	}
	public void setStocks(Long stocks) {
		this.stocks = stocks;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getIsHotProduct() {
		return isHotProduct;
	}
	public void setIsHotProduct(String isHotProduct) {
		this.isHotProduct = isHotProduct;
	}
	public String getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
	public String getPictures() {
		return pictures;
	}
	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public List<ShopProductCondition> getShopProductConditions() {
		return shopProductConditions;
	}

	public void setShopProductConditions(
			List<ShopProductCondition> shopProductConditions) {
		this.shopProductConditions = shopProductConditions;
	}

	public Double getWinningRate() {
		return winningRate;
	}

	public void setWinningRate(Double winningRate) {
		this.winningRate = winningRate;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getIsValidate() {
		return isValidate;
	}

	public void setIsValidate(String isValidate) {
		this.isValidate = isValidate;
	}
	
}
