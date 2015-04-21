package com.lvmama.comm.pet.po.search;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.vo.Constant;

public class Shantou implements Serializable{
	public final static String DEFAULT_PIC = "http://pic.lvmama.com/cmt/images/img_90_90.jpg";

	//必选字段
	private int outerId;		//产品id
	private String name;		//产品名称
	private String loc;			//产品url
	private String sellerName;  //商家站点 (驴妈妈旅游网)
	private Long price;		//产品现价 驴妈妈价
	private String title;		//产品描述
	
	private String productType;	//产品类型
	private String subProductType;	//产品子类型
	private String tagetUrl;	//跳转至产品的url (http://www.lvmama.com+产品url)
	private Long value;		//产品原价 市场价
	private String image;		//产品小图片url
	private String sellerSiteUrl;//销售站点 (http://www.lvmama.com)
	
	private String fromCity;	//出发城市
	private String toCity;		//目的地城市
	private String fromDate;	//出发日期
	private Float rank=0F;		//好评度
	private String Phone;		//预订电话 (1010 6060)
	private String visitDay;	//游玩天数
	private String level;		//地点一级分类
	private String subLevel;	//地点二级分类
	private String thirdLevel;	//地点三级分类
	private String productAlltoPlaceContent;
	
	
	// -------------------------------get/set------------------------------
	
	public int getOuterId() {
		return outerId;
	}
	public void setOuterId(int outerId) {
		this.outerId = outerId;
	}
	public String getName() {
		if(StringUtils.isNotBlank(this.name) && (name.contains("&")||name.contains("<")||name.contains(">"))) {
			return  name.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		 }else {
			 return name;
		 }
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoc() {
		return "http://www.lvmama.com"+loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	 
	public String getTitle() {
		if(StringUtils.isNotBlank(this.title) && (title.contains("&")||title.contains("<")||title.contains(">"))) {
			return  title.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		 }else {
			 return title;
		 }
 	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getSubProductType() {
		return subProductType;
	}
	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}
	public String getTagetUrl() {
		return "http://www.lvmama.com"+loc;
	}
	public void setTagetUrl(String tagetUrl) {
		this.tagetUrl = tagetUrl;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getSellerSiteUrl() {
		return sellerSiteUrl;
	}
	public void setSellerSiteUrl(String sellerSiteUrl) {
		this.sellerSiteUrl = sellerSiteUrl;
	}
	public String getFromCity() {
		return fromCity;
	}
	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}
	public String getToCity() {
		return toCity;
	}
	public void setToCity(String toCity) {
		this.toCity = toCity;
	}
	public String getFromDate() {
		return fromDate;
	}
	public String getProductAlltoPlaceContent() {
		return productAlltoPlaceContent;
	}
	public void setProductAlltoPlaceContent(String productAlltoPlaceContent) {
		this.productAlltoPlaceContent = productAlltoPlaceContent;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public Float  getRank() {
		return rank;
	}
	public void setRank(Float rank) {
		this.rank = rank;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getVisitDay() {
		return visitDay;
	}
	public void setVisitDay(String visitDay) {
		this.visitDay = visitDay;
	}
	public String getLevel() {
		String[] strList=this.getAllLevelList();
		if(null!=strList&&strList.length>=1){
  			return strList[strList.length-1];
		}else {
			return "";
		}
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getSubLevel() {
		String[] strList=this.getAllLevelList();
		if(null!=strList&&strList.length>=2){
  			return strList[strList.length-2];
		}else {
			return "";
		}
	}
	public void setSubLevel(String subLevel) {
		this.subLevel = subLevel;
	}
	public String getThirdLevel() {
		String[] strList=this.getAllLevelList();
		if(null!=strList&&strList.length>=3){
  			return strList[strList.length-3];
		}else {
			return "";
		}
	}
	public void setThirdLevel(String thirdLevel) {
		this.thirdLevel = thirdLevel;
	}
	private String[] getAllLevelList() {
		if(StringUtils.isBlank(this.productAlltoPlaceContent)){
			return null;
		}else {
		String AllLevel= this.productAlltoPlaceContent;
		String[] levelInfo = AllLevel.split(",");
		String temp ;//存储层级名称的临时字符串
		StringBuffer sb = new StringBuffer();
		for(String level :levelInfo){
			if(level.indexOf("~")>0){
				temp = level.substring(0,level.indexOf("~"));
				sb.append(temp+",");
			}
		}
		
		temp = sb.toString(); 
		levelInfo = temp.split(",");
		StringBuffer sbs = new StringBuffer();

		for(String level : levelInfo){
			if("北京".equals(level) || "中国".equals(level)){
				continue;
			}
			sbs.append(level+",");
		}
		
		temp= sbs.toString();
		return temp.split(",");
		}
	}
	public Float getAvgScorePercent() {
		return  this.rank * 20;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	public Long getPrice() {
		return price;
	}
	public Long getValue() {
		return value;
	}
	public Integer getSellPriceInteger() {
		Integer p = 0;
		if (this.price != null) {
			p = Integer.valueOf(this.price.toString());
		}
		return p / 100;
	}
	public Integer getMarketPriceInteger() {
		Integer price = 0;
		if (this.value != null) {
			price = Integer.valueOf(this.value.toString());
		}
		return price / 100;
	}
	public String getSmallImageUrl() {
		if (this.image == null) {
			return DEFAULT_PIC;
		}
		if (image.startsWith("http://")) {
			return image;
		}
		return Constant.getInstance().getPrefixPic() + getImage();
	}
	
	public String getType(){
		return   this.getProductType().equals(Constant.PRODUCT_TYPE.TICKET.getCode())?Constant.PRODUCT_TYPE.TICKET.getCnName():Constant.SUB_PRODUCT_TYPE.getCnName(this.getSubProductType());
	}
	public String getTypeUrl(){
		if(this.getProductType().equals(Constant.PRODUCT_TYPE.TICKET.getCode())){
			return "http://www.lvmama.com/ticket";
		}else {
			if(this.getSubProductType().equals(Constant.ROUTE_SUB_PRODUCT_TYPE.FREENESS.getCode())){
				return "http://www.lvmama.com/freetour";
			}else if(this.getSubProductType().equals(Constant.ROUTE_SUB_PRODUCT_TYPE.FREENESS_FOREIGN.getCode())){
				return "http://www.lvmama.com/abroad";
			}else if(this.getSubProductType().equals(Constant.ROUTE_SUB_PRODUCT_TYPE.FREENESS_LONG.getCode())){
				return "http://www.lvmama.com/destroute";
			}else if(this.getSubProductType().equals(Constant.ROUTE_SUB_PRODUCT_TYPE.GROUP.getCode())){
				return "http://www.lvmama.com/around";
			}else if(this.getSubProductType().equals(Constant.ROUTE_SUB_PRODUCT_TYPE.GROUP_FOREIGN.getCode())){
				return "http://www.lvmama.com/abroad";
			}else if(this.getSubProductType().equals(Constant.ROUTE_SUB_PRODUCT_TYPE.GROUP_LONG.getCode())){
				return "http://www.lvmama.com/destroute";
			}else if(this.getSubProductType().equals(Constant.ROUTE_SUB_PRODUCT_TYPE.SELFHELP_BUS.getCode())){
				return "http://www.lvmama.com/around";
			}
		}
		return "http://www.lvmama.com/";
	}
}
