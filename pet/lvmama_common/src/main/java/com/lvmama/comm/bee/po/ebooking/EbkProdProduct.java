package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.EBK_PRODUCT_AUDIT_STATUS;

/**
 * @since 2013-09-24
 */
public class EbkProdProduct implements Serializable {

	private static final long serialVersionUID = 138001285299817821L;

	/**
	 * column EBK_PROD_PRODUCT.EBK_PROD_PRODUCT_ID EBK产品主键
	 */
	private Long ebkProdProductId;

	/**
	 * column EBK_PROD_PRODUCT.META_NAME 供应商产品名称
	 */
	private String metaName;

	/**
	 * column EBK_PROD_PRODUCT.PROD_NAME 驴妈妈销售产品名称
	 */
	private String prodName;

	/**
	 * column EBK_PROD_PRODUCT.PRODUCT_TYPE 产品类型（周边，长线，出境）
	 */
	private String productType;

	/**
	 * column EBK_PROD_PRODUCT.SUB_PRODUCT_TYPE 产品子类型
	 */
	private String subProductType;

	/**
	 * column EBK_PROD_PRODUCT.RECOMMEND 一句话推荐
	 */
	private String recommend;

	/**
	 * column EBK_PROD_PRODUCT.FROM_PLACE_ID 出发地
	 */
	private Long fromPlaceId;
	
	/**
	 *  出发地名称
	 */
	private String fromPlaceName;

	/**
	 * column EBK_PROD_PRODUCT.TO_PLACE_ID 目的地
	 */
	private Long toPlaceId;
	
	/**
	 *  目的地名称
	 */
	private String toPlaceName;

	/**
	 * column EBK_PROD_PRODUCT.MANAGER_ID 驴妈妈联系人
	 */
	private Long managerId;
	
	/**
	 *  驴妈妈联系人名称
	 */
	private String managerName;

	/**
	 * column EBK_PROD_PRODUCT.ORG_ID 所属公司
	 * 
	 * @see com.lvmama.comm.vo.Constant.FILIALE_NAME
	 */
	private String orgId;
	
	/**
	 * column EBK_PROD_PRODUCT.SUPPLIER_ID 所属供应商ID
	 */
	private Long supplierId;

	/**
	 * column EBK_PROD_PRODUCT.STATUS 审核状态(未提交,待审核,审核不通过,审核通过)
	 */
	private String status;

	/**
	 * column EBK_PROD_PRODUCT.META_PRODUCT_ID 驴妈妈采购产品ID
	 */
	private Long metaProductId;

	/**
	 * column EBK_PROD_PRODUCT.PROD_PRODUCT_ID 驴妈妈销售产品ID
	 */
	private Long prodProductId;

	/**
	 * column EBK_PROD_PRODUCT.SUMIT_DATE 提交时间
	 */
	private Date sumitDate;

	/**
	 * column EBK_PROD_PRODUCT.EXAMINE_DATE 审核时间
	 */
	private Date examineDate;

	/**
	 * column EBK_PROD_PRODUCT.CREATE_USER_ID 创建人ID
	 */
	private Long createUserId;

	/**
	 * column EBK_PROD_PRODUCT.CREATE_DATE 创建时间
	 */
	private Date createDate;

	/**
	 * column EBK_PROD_PRODUCT.UPDATE_USER_ID 更新人ID
	 */
	private Long updateUserId;

	/**
	 * column EBK_PROD_PRODUCT.UPDATE_DATE 更新时间
	 */
	private Date updateDate;
	
	private String onLine;
	
	/**
	 * INITIAL_NUM 最少成团人数
	 */
	private Integer initialNum;
	/**
	 * ECONTRACT_TEMPLATE 电子合同模板
	 */
	private String econtractTemplate;
	/**
	 * REGION_NAME 境外地域名称
	 */
	private String regionName;
	/**
	 * COUNTRY 签证国家
	 */
	private String country;
	/**
	 * CITY 签证城市
	 */
	private String city;
	/**
	 * VISA_TYPE 签证类型
	 */
	private String visaType;
	/**
	 * isMultiJourney 是否为多行程
	 */
	private String isMultiJourney = "N";
	/**
	 * 产品特色，描述，发车等信息
	 */
	private List<EbkProdContent> ebkProdContents = new ArrayList<EbkProdContent>();

	/**
	 * 产品景点，地区
	 */
	private List<EbkProdPlace> ebkProdPlaces = new ArrayList<EbkProdPlace>();

	/**
	 * 产品对象
	 */
	private List<EbkProdTarget> ebkProdTargets = new ArrayList<EbkProdTarget>();

	/**
	 * 产品行程
	 */
	private List<EbkProdJourney> ebkProdJourneys = new ArrayList<EbkProdJourney>();
	
	/**
	 * 多行程
	 */
	private List<EbkMultiJourney> ebkMultiJourneys = new ArrayList<EbkMultiJourney>();

	/**
	 * 产品属性信息
	 */
	private List<EbkProdModelProperty> ebkProdModelPropertys = new ArrayList<EbkProdModelProperty>();
	
	/**
	 * 产品图片
	 */
	private List<ComPicture> comPictures = new ArrayList<ComPicture>();

	/**
	 * 产品类别
	 */
	private List<EbkProdBranch> ebkProdBranchs = new ArrayList<EbkProdBranch>();
	
	/**
	 * 时间价格表
	 */
	private List<EbkProdTimePrice> ebkProdTimePrices=new ArrayList<EbkProdTimePrice>();
	/**
	 * 关联销售产品表
	 * @return
	 */
	private List<EbkProdRelation> ebkProdRelations = new ArrayList<EbkProdRelation>();
	
	/**
	 * 行程天数
	 */
	private String tourDays;
	
	public String getVisaTypeCh(){
		return Constant.VISA_TYPE.getCnNameV2(visaType);
	}
	public String getCityCh(){
		return Constant.VISA_CITY.getCnNameV2(city);
	}
	public String getRegionNameCh(){
		return Constant.REGION_NAMES.getCnName(regionName);
	}
	public String getEcontractTemplateCh(){
		return Constant.ECONTRACT_TEMPLATE.getCnName(econtractTemplate);
	}
	
	public String getSubProductTypeCh(){
		return Constant.SUB_PRODUCT_TYPE.getCnName(this.subProductType);
	}
	public EbkProdProduct() {
		super();
	}

	public EbkProdProduct(Long ebkProdProductId, String metaName,
			String prodName, String productType, String subProductType,
			String recommend, Long fromPlaceId, Long toPlaceId, Long managerId,
			String orgId, Long supplierId, String status, Long metaProductId,
			Long prodProductId, Date sumitDate, Date examineDate,
			Long createUserId, Date createDate, Long updateUserId,
			Date updateDate) {
		this.ebkProdProductId = ebkProdProductId;
		this.metaName = metaName;
		this.prodName = prodName;
		this.productType = productType;
		this.subProductType = subProductType;
		this.recommend = recommend;
		this.fromPlaceId = fromPlaceId;
		this.toPlaceId = toPlaceId;
		this.managerId = managerId;
		this.orgId = orgId;
		this.supplierId = supplierId;
		this.status = status;
		this.metaProductId = metaProductId;
		this.prodProductId = prodProductId;
		this.sumitDate = sumitDate;
		this.examineDate = examineDate;
		this.createUserId = createUserId;
		this.createDate = createDate;
		this.updateUserId = updateUserId;
		this.updateDate = updateDate;
	}

	public String getEditLogMessage(){
		StringBuffer sb = new StringBuffer();
		if(StringUtils.isNotBlank(metaName)){
			sb.append("供应商产品名称修改为："+metaName+";");
		}
		if(StringUtils.isNotBlank(prodName)){
			sb.append("驴妈妈销售产品名称修改为："+prodName+";");
		}
		if(StringUtils.isNotBlank(subProductType)){
			sb.append("供应商产品类型修改为："+Constant.SUB_PRODUCT_TYPE.getCnName(subProductType)+";");
		}
		if(StringUtils.isNotBlank(recommend)){
			sb.append("一句话推荐修改为："+recommend+";");
		}
		if(fromPlaceId!=null){
			sb.append("出发地修改为："+fromPlaceName+";");
		}
		if(toPlaceId!=null){
			sb.append("目的地修改为："+toPlaceName+";");
		}
		if(managerId!=null){
			sb.append("驴妈妈联系人修改为："+managerName+";");
		}
		if(orgId!=null){
			sb.append("所属公司修改为："+Constant.FILIALE_NAME.getCnName(orgId.toString()));
		}
		if(ebkProdPlaces!=null&&ebkProdPlaces.size()>0){
			sb.append("行程包含景点修改为：");
			for(EbkProdPlace ebkProdPlace : ebkProdPlaces){
				sb.append(ebkProdPlace.getPlaceName()+",");
			}
			sb.deleteCharAt(sb.lastIndexOf(","));
			sb.append(";");
		}
		if(ebkProdTargets!=null&&ebkProdTargets.size()>0){
			for(EbkProdTarget ebkProdTarget : ebkProdTargets){
				if(Constant.COM_LOG_OBJECT_TYPE.SUP_PERFORM_TARGET.name().equalsIgnoreCase(ebkProdTarget.getTargetType())){
					sb.append("履行对象修改为："+ebkProdTarget.getTargetName()+";");
				}
				if(Constant.COM_LOG_OBJECT_TYPE.SUP_B_CERTIFICATE_TARGET.name().equalsIgnoreCase(ebkProdTarget.getTargetType())){
					sb.append("凭证对象修改为："+ebkProdTarget.getTargetName()+";");
				}
				if(Constant.COM_LOG_OBJECT_TYPE.SUP_SETTLEMENT_TARGET.name().equalsIgnoreCase(ebkProdTarget.getTargetType())){
					sb.append("结算对象修改为："+ebkProdTarget.getTargetName()+";");
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * getter for Column EBK_PROD_PRODUCT.EBK_PROD_PRODUCT_ID
	 */
	public Long getEbkProdProductId() {
		return ebkProdProductId;
	}

	/**
	 * setter for Column EBK_PROD_PRODUCT.EBK_PROD_PRODUCT_ID
	 * 
	 * @param ebkProdProductId
	 */
	public void setEbkProdProductId(Long ebkProdProductId) {
		this.ebkProdProductId = ebkProdProductId;
	}

	/**
	 * getter for Column EBK_PROD_PRODUCT.META_NAME
	 */
	public String getMetaName() {
		return metaName;
	}

	/**
	 * setter for Column EBK_PROD_PRODUCT.META_NAME
	 * 
	 * @param metaName
	 */
	public void setMetaName(String metaName) {
		this.metaName = metaName;
	}

	/**
	 * getter for Column EBK_PROD_PRODUCT.PROD_NAME
	 */
	public String getProdName() {
		return prodName;
	}

	/**
	 * setter for Column EBK_PROD_PRODUCT.PROD_NAME
	 * 
	 * @param prodName
	 */
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	/**
	 * getter for Column EBK_PROD_PRODUCT.PRODUCT_TYPE
	 */
	public String getProductType() {
		return productType;
	}

	public String getSubProductTypeZh() {
		return Constant.SUB_PRODUCT_TYPE.getCnName(this.getSubProductType());
	}

	/**
	 * setter for Column EBK_PROD_PRODUCT.PRODUCT_TYPE
	 * 
	 * @param productType
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * getter for Column EBK_PROD_PRODUCT.SUB_PRODUCT_TYPE
	 */
	public String getSubProductType() {
		return subProductType;
	}

	/**
	 * setter for Column EBK_PROD_PRODUCT.SUB_PRODUCT_TYPE
	 * 
	 * @param subProductType
	 */
	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	/**
	 * getter for Column EBK_PROD_PRODUCT.RECOMMEND
	 */
	public String getRecommend() {
		return recommend;
	}

	/**
	 * setter for Column EBK_PROD_PRODUCT.RECOMMEND
	 * 
	 * @param recommend
	 */
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	/**
	 * getter for Column EBK_PROD_PRODUCT.FROM_PLACE_ID
	 */
	public Long getFromPlaceId() {
		return fromPlaceId;
	}

	/**
	 * setter for Column EBK_PROD_PRODUCT.FROM_PLACE_ID
	 * 
	 * @param fromPlaceId
	 */
	public void setFromPlaceId(Long fromPlaceId) {
		this.fromPlaceId = fromPlaceId;
	}

	/**
	 * getter for Column EBK_PROD_PRODUCT.TO_PLACE_ID
	 */
	public Long getToPlaceId() {
		return toPlaceId;
	}

	/**
	 * setter for Column EBK_PROD_PRODUCT.TO_PLACE_ID
	 * 
	 * @param toPlaceId
	 */
	public void setToPlaceId(Long toPlaceId) {
		this.toPlaceId = toPlaceId;
	}

	/**
	 * getter for Column EBK_PROD_PRODUCT.MANAGER_ID
	 */
	public Long getManagerId() {
		return managerId;
	}

	/**
	 * setter for Column EBK_PROD_PRODUCT.MANAGER_ID
	 * 
	 * @param managerId
	 */
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	/**
	 * getter for Column EBK_PROD_PRODUCT.ORG_ID
	 */
	public String getOrgId() {
		return orgId;
	}
	public String getOrgIdZh() {
		return Constant.FILIALE_NAME.getCnName(this.orgId);
	}
	public String getOrgIdZh(String orgId) {
		return Constant.FILIALE_NAME.getCnName(orgId);
	}

	/**
	 * setter for Column EBK_PROD_PRODUCT.ORG_ID
	 * 
	 * @param orgId
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/**
	 * getter for Column EBK_PROD_PRODUCT.SUPPLIER_ID
	 */
	public Long getSupplierId() {
		return supplierId;
	}

	/**
	 * setter for Column EBK_PROD_PRODUCT.SUPPLIER_ID
	 * 
	 * @param supplierId
	 */
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	/**
	 * getter for Column EBK_PROD_PRODUCT.STATUS
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * setter for Column EBK_PROD_PRODUCT.STATUS
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatusCN(){
		return EBK_PRODUCT_AUDIT_STATUS.getCnNameByCode(this.status);
	}

	/**
	 * getter for Column EBK_PROD_PRODUCT.META_PRODUCT_ID
	 */
	public Long getMetaProductId() {
		return metaProductId;
	}

	/**
	 * setter for Column EBK_PROD_PRODUCT.META_PRODUCT_ID
	 * 
	 * @param metaProductId
	 */
	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}

	/**
	 * getter for Column EBK_PROD_PRODUCT.PROD_PRODUCT_ID
	 */
	public Long getProdProductId() {
		return prodProductId;
	}

	/**
	 * setter for Column EBK_PROD_PRODUCT.PROD_PRODUCT_ID
	 * 
	 * @param prodProductId
	 */
	public void setProdProductId(Long prodProductId) {
		this.prodProductId = prodProductId;
	}

	/**
	 * getter for Column EBK_PROD_PRODUCT.SUMIT_DATE
	 */
	public Date getSumitDate() {
		return sumitDate;
	}

	/**
	 * setter for Column EBK_PROD_PRODUCT.SUMIT_DATE
	 * 
	 * @param sumitDate
	 */
	public void setSumitDate(Date sumitDate) {
		this.sumitDate = sumitDate;
	}

	/**
	 * getter for Column EBK_PROD_PRODUCT.EXAMINE_DATE
	 */
	public Date getExamineDate() {
		return examineDate;
	}

	/**
	 * setter for Column EBK_PROD_PRODUCT.EXAMINE_DATE
	 * 
	 * @param examineDate
	 */
	public void setExamineDate(Date examineDate) {
		this.examineDate = examineDate;
	}

	/**
	 * getter for Column EBK_PROD_PRODUCT.CREATE_USER_ID
	 */
	public Long getCreateUserId() {
		return createUserId;
	}

	/**
	 * setter for Column EBK_PROD_PRODUCT.CREATE_USER_ID
	 * 
	 * @param createUserId
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * getter for Column EBK_PROD_PRODUCT.CREATE_DATE
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * setter for Column EBK_PROD_PRODUCT.CREATE_DATE
	 * 
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * getter for Column EBK_PROD_PRODUCT.UPDATE_USER_ID
	 */
	public Long getUpdateUserId() {
		return updateUserId;
	}

	/**
	 * setter for Column EBK_PROD_PRODUCT.UPDATE_USER_ID
	 * 
	 * @param updateUserId
	 */
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	/**
	 * getter for Column EBK_PROD_PRODUCT.UPDATE_DATE
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * setter for Column EBK_PROD_PRODUCT.UPDATE_DATE
	 * 
	 * @param updateDate
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public List<EbkProdContent> getEbkProdContents() {
		return ebkProdContents;
	}

	public void setEbkProdContents(List<EbkProdContent> ebkProdContents) {
		this.ebkProdContents = ebkProdContents;
	}

	public List<EbkProdPlace> getEbkProdPlaces() {
		return ebkProdPlaces;
	}

	public void setEbkProdPlaces(List<EbkProdPlace> ebkProdPlaces) {
		this.ebkProdPlaces = ebkProdPlaces;
	}

	public List<EbkProdTarget> getEbkProdTargets() {
		return ebkProdTargets;
	}

	public void setEbkProdTargets(List<EbkProdTarget> ebkProdTargets) {
		this.ebkProdTargets = ebkProdTargets;
	}

	public List<EbkProdJourney> getEbkProdJourneys() {
		return ebkProdJourneys;
	}

	public void setEbkProdJourneys(List<EbkProdJourney> ebkProdJourneys) {
		this.ebkProdJourneys = ebkProdJourneys;
	}

	public List<EbkProdModelProperty> getEbkProdModelPropertys() {
		return ebkProdModelPropertys;
	}

	public void setEbkProdModelPropertys(
			List<EbkProdModelProperty> ebkProdModelPropertys) {
		this.ebkProdModelPropertys = ebkProdModelPropertys;
	}

	public List<EbkProdBranch> getEbkProdBranchs() {
		return ebkProdBranchs;
	}

	public void setEbkProdBranchs(List<EbkProdBranch> ebkProdBranchs) {
		this.ebkProdBranchs = ebkProdBranchs;
	}
	public List<EbkProdTimePrice> getEbkProdTimePrices() {
		return ebkProdTimePrices;
	}
	public void setEbkProdTimePrices(List<EbkProdTimePrice> ebkProdTimePrices) {
		this.ebkProdTimePrices = ebkProdTimePrices;
	}
	public List<ComPicture> getComPictures() {
		return comPictures;
	}
	public void setComPictures(List<ComPicture> comPictures) {
		this.comPictures = comPictures;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getFromPlaceName() {
		return fromPlaceName;
	}
	public void setFromPlaceName(String fromPlaceName) {
		this.fromPlaceName = fromPlaceName;
	}
	public String getToPlaceName() {
		return toPlaceName;
	}
	public void setToPlaceName(String toPlaceName) {
		this.toPlaceName = toPlaceName;
	}
	public String getOnLine() {
		return onLine;
	}
	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}
	public Integer getInitialNum() {
		return initialNum;
	}
	public void setInitialNum(Integer initialNum) {
		this.initialNum = initialNum;
	}
	public String getEcontractTemplate() {
		return econtractTemplate;
	}
	public void setEcontractTemplate(String econtractTemplate) {
		this.econtractTemplate = econtractTemplate;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getVisaType() {
		return visaType;
	}
	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}
	public List<EbkProdRelation> getEbkProdRelations() {
		return ebkProdRelations;
	}
	public void setEbkProdRelations(List<EbkProdRelation> ebkProdRelations) {
		this.ebkProdRelations = ebkProdRelations;
	}
	
	public String getZhMultiJourney() {
		if(StringUtils.isEmpty(isMultiJourney)) {
			return "单行程";
		}
		return "Y".equalsIgnoreCase(isMultiJourney)?"多行程":"单行程";
	}
	
	public boolean hasMultiJourney() {
		if(StringUtils.isEmpty(isMultiJourney)) {
			return false;
		}
		return "Y".equalsIgnoreCase(isMultiJourney)?true:false;
	}

	public String getIsMultiJourney() {
		return isMultiJourney;
	}

	public void setIsMultiJourney(String isMultiJourney) {
		this.isMultiJourney = isMultiJourney;
	}
	public List<EbkMultiJourney> getEbkMultiJourneys() {
		return ebkMultiJourneys;
	}
	public void setEbkMultiJourneys(List<EbkMultiJourney> ebkMultiJourneys) {
		this.ebkMultiJourneys = ebkMultiJourneys;
	}
	public String getTourDays() {
		return tourDays;
	}
	public void setTourDays(String tourDays) {
		this.tourDays = tourDays;
	}
	
	
}