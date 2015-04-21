package com.lvmama.back.sweb.prod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.prod.ProductModelProperty;
import com.lvmama.comm.bee.po.prod.ProductModelType;
import com.lvmama.comm.bee.service.prod.ProdProductModelPropertyService;
import com.lvmama.comm.bee.service.prod.ProdProductTagService;
import com.lvmama.comm.bee.service.prod.ProdTagService;
import com.lvmama.comm.bee.service.prod.ProductModelPropertyService;
import com.lvmama.comm.bee.service.prod.ProductModelTypeService;
import com.lvmama.comm.pet.po.prod.ProdProductModelProperty;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.pet.po.prod.ProdTagGroup;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 产品属性管理
 * @author ganyingwen
 *
 */
@Results({
	@Result(name="toPropertyList", location="/WEB-INF/pages/back/prod/product_modelProperty_list.jsp"),
	@Result(name="editModelProperty", location="/WEB-INF/pages/back/prod/product_editModelProperty.jsp")
})

public class ProductPropertyManagerAction extends BaseAction {
	/**
	 * 模块服务
	 */
	private ProductModelTypeService productModelTypeService;
	/**
	 * 模块属性服务
	 */
	private ProductModelPropertyService productModelPropertyService;
	/**
	 * 模块属性对象
	 */
	private ProductModelProperty productModelProperty;
	/**
	 * 模块列表
	 */
	private List<ProductModelType> modelTypeList;
	/**
	 * 模块属性ID
	 */
    private Long id;
    /**
     * 产品类型数组
     */
    private String[] productTypes;
    /**
     * 一级模块ID
     */
    private Long firstModelId;
    /**
     * 二级模块ID
     */
    private Long secondModelId;
    /**
     * 产品子类型
     */
    private String subProductType;
    /**
     * 模块属性管理所属产品类型
     */
    private static final String PRODUCT_TYPE = Constant.PRODUCT_TYPE.ROUTE.name();
    private boolean blank = false; 
    private String isValid;
    ProdProductModelPropertyService prodProductModelPropertyService;
    private ProdTagService prodTagService;
    private ProdProductTagService prodProductTagService;
    private static final Long BRAND_TAG_GROUP_ID = new Long(81);
    private static final int BRAND_SECOND_MODEL_ID = 7;
    
    public ProdProductModelPropertyService getProdProductModelPropertyService() {
		return prodProductModelPropertyService;
	}

	public void setProdProductModelPropertyService(ProdProductModelPropertyService prodProductModelPropertyService) {
		this.prodProductModelPropertyService = prodProductModelPropertyService;
	}

	/**
     * 属性列表
     * @return
     */
    @Action("/prod/modelPropertyList")
    public String modelPropertyList() {
    	Map<String, Object> params = initParams();
    	params.put("isMaintain", "Y");
    	Long totalRowCount = productModelPropertyService.count(params);
    	pagination = super.initPagination();
		pagination.setTotalRecords(totalRowCount);
		params.put("_startRow", pagination.getFirstRow());
		params.put("_endRow", pagination.getLastRow());
		List<ProductModelProperty> modelPropertyList = productModelPropertyService.select(params);
		pagination.setRecords(modelPropertyList);
		pagination.setActionUrl(WebUtils.getUrl(getRequest()));	
		modelTypeList = productModelTypeService.select(new HashMap());
		
    	return "toPropertyList";
    }
    
    /**
     * 编辑模块属性
     * @return
     */
    @Action("/prod/editModelProperty")
    public String editModelProperty() {
    	blank = true;
    	productModelProperty = initModelProperty();  
    	modelTypeList = productModelTypeService.select(new HashMap());
    	return "editModelProperty";
    }
    
    /**
     * 增加或更新模块属性
     * @return
     * @throws IOException 
     */
    @Action("/prod/saveOrUpdateProperty")
    public void saveOrUpdateProperty() throws IOException {
    	 
    	String msg = "";
    	productModelProperty = initModelProperty();    	
    	if (productModelProperty.getId()==null) {
    		productModelProperty = productModelPropertyService.insert(productModelProperty);
    	 	if (productModelProperty!=null && productModelProperty.getId() != null) {
        		msg = "{flag:'Y',msg:'增加成功'}";
        		
        		//2级模块如是品牌，就新增品牌标签
        		Long SecondModelId = productModelProperty.getSecondModelId();
        		if(productModelProperty.getProperty() != null && SecondModelId != null && SecondModelId.intValue() == BRAND_SECOND_MODEL_ID){
        			//不存在已有标签才能新增
        			ProdTag oldProdTag = prodTagService.getTagByTagName(productModelProperty.getProperty());
        			if(oldProdTag == null){
        				addBrandTag(productModelProperty);
        			}
        		}
        	}else {
        		msg = "{flag:'N',msg:'增加失败'}";
        	}
    	}else{
    		if(isChangeProductType()){
    			msg = "{flag:'N',msg:'修改失败，有产品与你取消适用的产品类型关联，请先找DBA导数据，并联系线下产品经理去除关联'}";
    		}else{
    			int count = productModelPropertyService.update(productModelProperty);
        	 	if (count != 0) {
            		msg = "{flag:'Y',msg:'修改成功'}";
            	}else {
            		msg = "{flag:'N',msg:'修改失败'}";
            	}
        	 	//设置(新增或删除)当前品牌标签和产品关联
        	 	Long SecondModelId = productModelProperty.getSecondModelId();
        	 	if(productModelProperty.getProperty() != null && SecondModelId != null && SecondModelId.intValue() == BRAND_SECOND_MODEL_ID){
        			addOrDelBrandTag(productModelProperty);
        	 	}
    		}
    	}
    	
    	this.getResponse().setCharacterEncoding("utf-8");
    	this.getResponse().getWriter().write(msg);    	
    }
    
    //添加品牌标签
    private void addBrandTag(ProductModelProperty productModelProperty){
    	//品牌标签组的ID=81
		ProdTag prodTag = new ProdTag();
		ProdTagGroup prodTagGroup = prodTagService.selectByPrimaryKey(BRAND_TAG_GROUP_ID);
		
		if( prodTagGroup != null){
			prodTag.setCssId(prodTagGroup.getCssId());
			prodTag.setTagGroupId(prodTagGroup.getTagGroupId());
			prodTag.setTagName(productModelProperty.getProperty());
			prodTag.setTagPinYin(productModelProperty.getPingying());
			prodTag.setDescription(productModelProperty.getProperty());
			prodTag.setCssId(prodTagGroup.getCssId());
			
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("tagGroupId", BRAND_TAG_GROUP_ID);
			List<ProdTag> prodTagList = prodTagService.getGroupsAndTags(searchParams);
			if(prodTagList != null && prodTagList.size() > 0){
				prodTag.setTagSEQ(new Long(prodTagList.size() + 1));
			}else{
				prodTag.setTagSEQ(new Long(1));
			}
			
			ProdTag oldProdTag = prodTagService.getTagByTagName(productModelProperty.getProperty());
			if(oldProdTag == null){
				prodTagService.addTag(prodTag);
			}
		}
    }
    
    //设置(新增或删除)当前品牌标签和产品关联
    private void addOrDelBrandTag(ProductModelProperty productModelProperty){
    	String tagName = productModelProperty.getProperty();
    	
		if(tagName != null && productModelProperty.getIsValid().equalsIgnoreCase("Y")){
			//获取品牌关联的所有产品
			List<Long> productIds = new ArrayList<Long>();
			List<ProdProductTag> prodProductTags = new ArrayList<ProdProductTag>();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("modelPropertyId", productModelProperty.getId());
			List<ProdProductModelProperty> list = prodProductModelPropertyService.selectByParam(map);
			
			//建立所有产品与该品牌标签关联
			if(list != null && list.size() > 0){
				ProdProductTag prodProductTag = new ProdProductTag();
				prodProductTag.setProductId(prodProductTag.getProductId());
				prodProductTag.setCreator(Constant.PROD_PRODUCT_TAG_CREATOR.SYSTEM.getCode());
				productIds.add(prodProductTag.getProductId());
				prodProductTags.add(prodProductTag);
			}
			//标签保存(1,有记录---先删后插      2,没记录---新插)
			prodProductTagService.addSystemProgProductTags(prodProductTags, productIds, tagName);
			
		}else{
			//无效删关联
			ProdTag prodTag = prodTagService.getTagByTagName(tagName);
			if(prodTag != null){
				prodProductTagService.deleteByTagId(prodTag.getTagId());
			}
		}
    }
    
    
    /**
     * 获取所有自由行类型列表
     * @return
     */
	public List<CodeItem> getSubProductTypeList(){
		List<CodeItem> codeItemList = ProductUtil.getSubProductTypeList(PRODUCT_TYPE, false);
		codeItemList.add(new CodeItem(Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT.name(),"酒店套餐"));
		
		String[] types = {};
		
		if (productTypes != null) {
			types = productTypes;			
		}else if(productModelProperty != null){
			types = productModelProperty.getProductTypes();
		}
		
		if (types != null && types.length>0) {
			for (CodeItem ci : codeItemList) {			
				for (String type : types) {
					if (ci.getCode().equals(type)) {
						ci.setChecked("true");
					}
				}
			}
		}
		return codeItemList;
	}
	
	/**
	 * 根据自由行类型码获取自由行中文名称
	 * @param productTypes 自由行类型码(以顿号分隔)
	 * @return
	 */
	public String getChTypes(String productTypes) {
		StringBuffer buff = new StringBuffer();
		List<CodeItem> codeItemList = getSubProductTypeList();
		String[] types = productTypes.split(";");
		for (CodeItem codeItem : codeItemList) {			
			for(String type : types) {
				if (codeItem.getCode().equals(type)) {
					buff.append(codeItem.getName() + "、");
				}
			}			
		}
		return buff.substring(0, buff.length()-1);
	}	
	
	/**
	 * 根据模块类型Id获取模块类型对象
	 * @param id
	 * @return
	 */
	public ProductModelType getModeTypeById(Long id) {
		if (modelTypeList != null) {
			for (ProductModelType modelType : modelTypeList) {
				if (id.longValue() == modelType.getId().longValue()) {
					return modelType;
				}
			}
		}
		return null;
	}
    
	/**
	 * 把参数封装成Map
	 * @return Map对象
	 */
	private Map<String, Object> initParams() {
    	Map<String, Object> params = new HashMap<String, Object>();
    	if (id!=null) {
    		params.put("id", id);
    	}
    	String firstModelId = this.getRequest().getParameter("firstModelId");
    	if (StringUtils.isNotEmpty(firstModelId) && NumberUtils.isNumber(firstModelId)) {
    		params.put("firstModelId", NumberUtils.toLong(firstModelId));
    	}
    	String secondModelId = this.getRequest().getParameter("secondModelId");
    	if (StringUtils.isNotEmpty(secondModelId) && NumberUtils.isNumber(secondModelId)) {
    		params.put("secondModelId", NumberUtils.toLong(secondModelId));
    	}
    	String property = this.getRequest().getParameter("property");
    	if (StringUtils.isNotEmpty(property)) {
    		params.put("property", property);
    	}
    	if (StringUtils.isNotEmpty(isValid)) {
    		params.put("isValid", isValid);
    	}
    	if (productTypes != null && productTypes.length!=0) {
    		if(productTypes.length>1){
    			StringBuffer sb=new StringBuffer("");
    			boolean b=false;
    			List<String> list=new ArrayList<String>();
    			for(String type:productTypes){
    				if(type.equals("GROUP")){
    					b=true;
    				}else{
    					list.add(type);
    				}
    			}
    			if(b){
    				sb.append("(PRODUCT_TYPE='GROUP' or ");
    				int n=list.size();
    				int curCount=1;
    				for(String type:list){
    					sb.append(" PRODUCT_TYPE like '%"+type+"%'");
    					if(curCount!=n){
    						sb.append(" or ");
    					}else{
    						sb.append(" ) ");
    					}
    					curCount++;
    				}
    				params.put("isGroupSql", sb.toString());
    			}else{
    				params.put("productTypes", productTypes);
    			}
    			
    		}else{
    			if(productTypes[0].equals("GROUP")){
    				params.put("isGroupSql", " (PRODUCT_TYPE='GROUP' or (PRODUCT_TYPE like '%GROUP;%') or (PRODUCT_TYPE like '%GROUP')) ");
    			}else{
    				params.put("productTypes", productTypes);
    			}
    			
    		}
    		
    	}
    	
    	return params;
    }
    
	
   /**
    * 把参数封装成ProductModelProperty对象
    * @return ProductModelProperty对象
    */
    private ProductModelProperty initModelProperty() {
    	ProductModelProperty productModelProperty = new ProductModelProperty();
    	if (id!=null) {
    		Map<String, Object> params = new HashMap<String, Object>();
    		params.put("id", id);
    		productModelProperty = productModelPropertyService.select(params).get(0);
    	}
    	if (productTypes != null && productTypes.length!=0) {
    		StringBuffer buf = new StringBuffer();
    		for(int i=0; i<productTypes.length; i++) {
    			buf.append(productTypes[i] + ",");    			
    		}
    		productModelProperty.setProductType(buf.substring(0, buf.length()-1));
    	}
       	String productType = this.getRequest().getParameter("productType");
    	if (StringUtils.isNotEmpty(productType)) {
    		productModelProperty.setProductType(productType);
    	}    	
    	String firstModelId = this.getRequest().getParameter("firstModelId");
    	if (StringUtils.isNotEmpty(firstModelId) && NumberUtils.isNumber(firstModelId)) {
    		productModelProperty.setFirstModelId(NumberUtils.toLong(firstModelId));
    	}
    	String secondModelId = this.getRequest().getParameter("secondModelId");
    	if (StringUtils.isNotEmpty(secondModelId) && NumberUtils.isNumber(secondModelId)) {
    		productModelProperty.setSecondModelId(NumberUtils.toLong(secondModelId));
    	}
    	String property = this.getRequest().getParameter("property");
    	if (StringUtils.isNotEmpty(property)) {
    		productModelProperty.setProperty(property);
    	}
    	String orderNum = this.getRequest().getParameter("orderNum");
    	if (StringUtils.isNotEmpty(orderNum) && NumberUtils.isNumber(orderNum)) {
    		productModelProperty.setOrderNum(NumberUtils.toInt(orderNum));
    	}
    	String thesaurus = this.getRequest().getParameter("thesaurus");
    	if (StringUtils.isNotEmpty(thesaurus)) {
    		productModelProperty.setThesaurus(thesaurus);
    	}
    	String pingying = this.getRequest().getParameter("pingying");
    	if (StringUtils.isNotEmpty(pingying)) {
    		productModelProperty.setPingying(pingying);
    	}
    	if (StringUtils.isNotEmpty(isValid)) {
    		productModelProperty.setIsValid(isValid);
    	}
    	return productModelProperty;
    }
    /**
     * 判断是否productType有变化
     */
    private boolean isChangeProductType(){
    	boolean b=false;
    	ProductModelProperty oldProductModelProperty = new ProductModelProperty();
    	if (id!=null) {
    		Map<String, Object> params = new HashMap<String, Object>();
    		params.put("id", id);
    		oldProductModelProperty = productModelPropertyService.select(params).get(0);
    	}
    	productTypes=productModelProperty.getProductTypes();
    	if (productTypes != null && productTypes.length>=0) {
    		int n=productTypes.length;
    		if(n>1){
    			int rightCount=0;
    			int m=oldProductModelProperty.getProductTypes().length;
    			for(String oldProperty:oldProductModelProperty.getProductTypes()){
    				for(String newProperty:productTypes){
    					if(oldProperty.equals(newProperty)){
    						rightCount++;
    					}
    				}
    			}
    			if(rightCount!=m){
    				//检测是否有关联
    				b= prodProductModelPropertyService.isCheckExistByProperty(id.toString());
    			}
    		}else{
    			if(!productTypes[0].equals(oldProductModelProperty.getProductType())){
        			//检测是否有关联
    				b=prodProductModelPropertyService.isCheckExistByProperty(id.toString());
        		}
    		}
    	}
    	return b;
    }
    
    /**
     * 获取一级模块CodeItem列表
     * @param grade
     * @return
     */
    public List<CodeItem> getFirstGradeModelList() {    	
    	List<CodeItem> ciList = new ArrayList<CodeItem>();
    	if (!blank) {
    		ciList.add(new CodeItem("", "全部"));
    	}
    	for (ProductModelType type : modelTypeList) { 
			Long id = type.getId();			
			if (type.getParentId()==null&&(type.getIsMaintain()==null||type.getIsMaintain().equals(""))) { 
				for (ProductModelType type2 : modelTypeList) { 					
					if (type2.getParentId()!=null && type2.getParentId().longValue()==id.longValue() && "Y".equals(type2.getIsMaintain())) {						
						CodeItem ci = new CodeItem(id + "", type.getModelName());
						if (firstModelId != null && firstModelId.longValue()==id.longValue()) {
							ci.setChecked("true");
						}
						ciList.add(ci);
						break;
					}
				}
			} 
    	}
    	return ciList;
    }
	public ProductModelPropertyService getProductModelPropertyService() {
		return productModelPropertyService;
	}
	public void setProductModelPropertyService(
			ProductModelPropertyService productModelPropertyService) {
		this.productModelPropertyService = productModelPropertyService;
	}
	public ProductModelProperty getProductModelProperty() {
		return productModelProperty;
	}
	public void setProductModelProperty(ProductModelProperty productModelProperty) {
		this.productModelProperty = productModelProperty;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String[] getProductTypes() {
		return productTypes;
	}

	public void setProductTypes(String[] productTypes) {
		this.productTypes = productTypes;
	}

	public static void main(String[] args) {
		String[] abc = {};
		System.out.println(abc.length);
		
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public List<ProductModelType> getModelTypeList() {
		return modelTypeList;
	}

	public void setModelTypeList(List<ProductModelType> modelTypeList) {
		this.modelTypeList = modelTypeList;
	}

	public void setProductModelTypeService(
			ProductModelTypeService productModelTypeService) {
		this.productModelTypeService = productModelTypeService;
	}

	public Long getFirstModelId() {
		return firstModelId;
	}

	public void setFirstModelId(Long firstModelId) {
		this.firstModelId = firstModelId;
	}

	public Long getSecondModelId() {
		return secondModelId;
	}

	public void setSecondModelId(Long secondModelId) {
		this.secondModelId = secondModelId;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public void setProdTagService(ProdTagService prodTagService) {
		this.prodTagService = prodTagService;
	}

	public void setProdProductTagService(ProdProductTagService prodProductTagService) {
		this.prodProductTagService = prodProductTagService;
	}
	
	
}
