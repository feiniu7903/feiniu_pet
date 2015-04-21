package com.lvmama.distribution.model.jd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.distribution.DistributionProduct;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant.VIEW_CONTENT_TYPE;
import com.lvmama.distribution.util.JdUtil;

/**
 * 产品
 * @author gaoxin
 * 
 */
public class Product {
	private String productId;// 供应商的产品
	private String resourceId;// 供应商的资源编号
	private String productName;// 产品名称
	private String productUnit;// 单位名称
	private String openTime;// 营业时间
	private String validDateType;// 入园有效日期类型
	private String validTimeBegin;// 票的有效开始时间
	private String validTimeEnd;// 票的有效结束时间
	private String buyDays;// 多少天有效
	private String payType;// 支付类型
	private String productDescription;// 产品介绍
	private String salesPrice;// 门市价
	private String retailPrice;// 建议零售价
	private String settlementPrice;// 结算价
	private String refundDescription;// 退款介绍
	private String idcardYn;// 是否需要身份证号码
	private String productType;// 产品类型
	private String playDays;// 游玩天数
	private String productHelp;// 使用说明
	private String productServices;// 费用包含
	private String numberLimit;// 购买限制
	private String validHours="72";// 提前购买小时数
	private String feature;// 扩展字段,备用
	private List<Branch> productBranch = new ArrayList<Branch>();
	private String branchId;
	private String state;
	private List<Price> priceList = new ArrayList<Price>();
	private DistributionProduct disproduct;
	private Map<String, String> contentMap;
	public Product() {}
	
	public Product(DistributionProduct disproduct) {
		this.disproduct=disproduct;
		ProdProduct prodProduct=disproduct.getProdProduct();
		List<ProdProductBranch> list=prodProduct.getProdBranchList();
		for (ProdProductBranch prdBranch : prodProduct.getProdBranchList()) {
			Branch branch = new Branch(prdBranch);
			if(prdBranch.getTimePriceList() != null){
				for (TimePrice timePrice : prdBranch.getTimePriceList()) {
					Price price = new Price(timePrice);
					priceList.add(price);
					branch.getPriceList().add(price);
				}
			}
			this.getProductBranch().add(branch);
		}
		this.productId=String.valueOf(prodProduct.getProductId());
		this.resourceId=String.valueOf(disproduct.getToDest().getPlaceId());
		String travellerInfoOptions = prodProduct.getTravellerInfoOptions();
		this.idcardYn=travellerInfoOptions!=null?(travellerInfoOptions.contains("CARD_NUMBER")?"1":"0"):"0";
		boolean jingdongState=false;
		if(list!=null&&!list.isEmpty()){
			ProdProductBranch prodBranch=list.get(0);
			if("Y".equals(prodProduct.getValid())&&"true".equals(prodProduct.getOnLine())&&"Y".equals(prodBranch.getValid())&&"true".equals(prodBranch.getOnline())){
				jingdongState=true;
			}
		}
		this.state = jingdongState ? "1" : "2";
		this.contentMap=disproduct.initContentMap();
		this.productUnit="张";
		this.validDateType="7";// 固定入园有效期类型为7
	}
	public Product(DistributionProduct disproduct,ProdProductBranch prodProductBranch) {
		this.disproduct=disproduct;
		ProdProduct prodProduct=disproduct.getProdProduct();
		Place place=disproduct.getToDest();
		this.getProductBranch().add(new Branch(prodProductBranch));
		this.productId=String.valueOf(prodProduct.getProductId());
		this.resourceId=String.valueOf(place.getPlaceId());
	}

	/**
	 * 构造新增产品报文
	 * 
	 * @return
	 */
	public String buildProductToXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<product>")
				.append(JdUtil.buildXmlElement("productId", disproduct.getProdProduct().getProductId()))
				.append(JdUtil.buildXmlElement("resourceId", disproduct.getToDest().getPlaceId()))
				.append(JdUtil.buildXmlElement("productName", disproduct.getProdProduct().getProductName()))
				.append(JdUtil.buildXmlElement("productUnit", productUnit))
				.append(JdUtil.buildXmlElement("validDateType", validDateType))
				.append(JdUtil.buildXmlElement("validTimeBegin", DateUtil.getDateTime("yyyyMMddHHmm", this.disproduct.getProdProduct().getOnlineTime())))
				.append(JdUtil.buildXmlElement("validTimeEnd", DateUtil.getDateTime("yyyyMMddHHmm",this.disproduct.getProdProduct().getOfflineTime())))
				.append(JdUtil.buildXmlElement("payType", disproduct.getPaymentTypeForJingdong()))
				.append(JdUtil.buildXmlElementInCDATA("refundDescription", contentMap.get(VIEW_CONTENT_TYPE.REFUNDSEXPLANATION.name())))
				.append(JdUtil.buildXmlElement("idcardYn", idcardYn))
				.append(JdUtil.buildXmlElement("productType", "1"))
				.append(JdUtil.buildXmlElementInCheck("playDays", playDays))
				.append(JdUtil.buildXmlElementInCDATA("productHelp", contentMap.get(VIEW_CONTENT_TYPE.PLAYPOINTOUT.name())))
				.append(JdUtil.buildXmlElementInCDATA("productServices",contentMap.get(VIEW_CONTENT_TYPE.COSTCONTAIN.name()) ))
				.append(JdUtil.buildXmlElementInCheck("numberLimit", numberLimit))
				.append(JdUtil.buildXmlElement("validHours", validHours))
				.append(JdUtil.buildXmlElementInCheck("feature", feature));
		if (productBranch.size() > 0) {
			sb.append("<productBranch>");
			for (Branch branch : productBranch) {
				sb.append(branch.buildBranchToXml());
			}
			sb.append("</productBranch>");
		}
		sb.append("</product>");
		return sb.toString();
	}

	/**
	 * 上、下架产品 报文
	 * 
	 * @return
	 */
	public String buildOnOffLineToXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<product>")
		.append(JdUtil.buildXmlElement("productId", disproduct.getProdProduct().getProductId()))
		.append(JdUtil.buildXmlElement("resourceId", disproduct.getToDest().getPlaceId()));
		if (productBranch.size() > 0) {
			sb.append("<productBranch>");
			for (Branch branch : productBranch) {
				branch.getPriceList().clear();
				sb.append(branch.buildOnOffLineBranchToXml());
			}
			sb.append("</productBranch>");
		}
		sb.append("</product>");
		return sb.toString();
	}

	/**
	 * 构造更新产品报文
	 * 
	 * @return
	 */

	public String buildUpdateProductToXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<product>").append(JdUtil.buildXmlElement("productId", disproduct.getProdProduct().getProductId()))
				.append(JdUtil.buildXmlElement("resourceId", disproduct.getToDest().getPlaceId()))
				.append(JdUtil.buildXmlElement("productName", disproduct.getProdProduct().getProductName()))
				.append(JdUtil.buildXmlElementInCheck("validDateType", validDateType))
				.append(JdUtil.buildXmlElementInCheck("validTimeBegin",DateUtil.getDateTime("yyyyMMddHHmm", this.disproduct.getProdProduct().getOnlineTime())))
				.append(JdUtil.buildXmlElementInCheck("validTimeEnd", DateUtil.getDateTime("yyyyMMddHHmm",this.disproduct.getProdProduct().getOfflineTime())))
				.append(JdUtil.buildXmlElementInCheck("buyDays", buyDays))
				.append(JdUtil.buildXmlElementInCDATA("productDescription",this.productDescription))
				.append(JdUtil.buildXmlElementInCDATA("refundDescription", contentMap.get(VIEW_CONTENT_TYPE.REFUNDSEXPLANATION.name())))
				.append(JdUtil.buildXmlElementInCheck("idcardYn", idcardYn))
				.append(JdUtil.buildXmlElementInCDATA("productHelp", contentMap.get(VIEW_CONTENT_TYPE.PLAYPOINTOUT.name())))
				.append(JdUtil.buildXmlElementInCDATA("productServices", contentMap.get(VIEW_CONTENT_TYPE.COSTCONTAIN.name())))
				.append(JdUtil.buildXmlElement("validHours", validHours))
				.append(JdUtil.buildXmlElementInCheck("feature", feature));
		if (productBranch.size() > 0) {
			sb.append("<productBranch>");
			for (Branch branch : productBranch) {
				branch.getPriceList().clear();
				sb.append(branch.buildBranchToXml());
			}
			sb.append("</productBranch>");
		}
		sb.append("</product>");
		return sb.toString();
	}

	/**
	 * 新增、修改 每日价格 报文
	 * 
	 * @return
	 */
	public String buildAddDailyPriceToXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<product>")
		.append(JdUtil.buildXmlElement("productId", disproduct.getProdProduct().getProductId()))
		.append(JdUtil.buildXmlElement("resourceId", disproduct.getToDest().getPlaceId()))
		.append("<productBranch>");
		if (productBranch.size() > 0) {
			for (Branch branch : productBranch) {
				sb.append(branch.buildBranchToXmlInDailyPrice());
			}
		}
		sb.append("</productBranch>")
		.append("</product>");
		return sb.toString();
	}

	/**
	 * 查询每日价格
	 * 
	 * @return
	 */
	public String buildQueryDailyPricesToXml() {
		StringBuilder sb = new StringBuilder();
		ProdProduct prod=disproduct.getProdProduct();
		ProdProductBranch prodBranch=prod.getProdBranchList().get(0);
		sb.append("<product>")
		.append(JdUtil.buildXmlElement("productId", prod.getProductId()))
		.append(JdUtil.buildXmlElement("branchId", prodBranch.getProdBranchId()))
		.append(JdUtil.buildXmlElement("state", state));
		if (priceList != null && priceList.size() > 0) {
			sb.append("<prices>");
				for (Price price : priceList) {
					sb.append(price.buildPriceToXml());
				}
			sb.append("</prices>");
		}
		sb.append("</product>");
		return sb.toString();
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getValidDateType() {
		return validDateType;
	}

	public void setValidDateType(String validDateType) {
		this.validDateType = validDateType;
	}

	public String getValidTimeBegin() {
		return validTimeBegin;
	}

	public void setValidTimeBegin(String validTimeBegin) {
		this.validTimeBegin = validTimeBegin;
	}

	public String getValidTimeEnd() {
		return validTimeEnd;
	}

	public void setValidTimeEnd(String validTimeEnd) {
		this.validTimeEnd = validTimeEnd;
	}

	public String getBuyDays() {
		return buyDays;
	}

	public void setBuyDays(String buyDays) {
		this.buyDays = buyDays;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(String salesPrice) {
		this.salesPrice = salesPrice;
	}

	public String getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}

	public String getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(String settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public String getRefundDescription() {
		return refundDescription;
	}

	public void setRefundDescription(String refundDescription) {
		this.refundDescription = refundDescription;
	}

	public String getIdcardYn() {
		return idcardYn;
	}

	public void setIdcardYn(String idcardYn) {
		this.idcardYn = idcardYn;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getPlayDays() {
		return playDays;
	}

	public void setPlayDays(String playDays) {
		this.playDays = playDays;
	}

	public String getProductHelp() {
		return productHelp;
	}

	public void setProductHelp(String productHelp) {
		this.productHelp = productHelp;
	}

	public String getProductServices() {
		return productServices;
	}

	public void setProductServices(String productServices) {
		this.productServices = productServices;
	}

	public String getNumberLimit() {
		return numberLimit;
	}

	public void setNumberLimit(String numberLimit) {
		this.numberLimit = numberLimit;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public List<Branch> getProductBranch() {
		return productBranch;
	}

	public void setProductBranch(List<Branch> productBranch) {
		this.productBranch = productBranch;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Price> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<Price> priceList) {
		this.priceList = priceList;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public DistributionProduct getDisproduct() {
		return disproduct;
	}

	public void setDisproduct(DistributionProduct disproduct) {
		this.disproduct = disproduct;
	}

	public Map<String, String> getContentMap() {
		return contentMap;
	}

	public void setContentMap(Map<String, String> contentMap) {
		this.contentMap = contentMap;
	}

	public String getValidHours() {
		return validHours;
	}

	public void setValidHours(String validHours) {
		this.validHours = validHours;
	}

}
