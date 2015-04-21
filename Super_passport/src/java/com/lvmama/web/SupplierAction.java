package com.lvmama.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.duijie.SupplierProd;
import com.lvmama.comm.bee.po.duijie.SupplierViewContent;
import com.lvmama.comm.bee.po.duijie.SupplierViewJourney;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.duijie.SupplierProdService;
import com.lvmama.comm.bee.service.duijie.SupplierViewContentService;
import com.lvmama.comm.bee.service.duijie.SupplierViewJourneyService;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SUPPLIER_PRODUCT_TYPE;
import com.lvmama.comm.vo.duijie.SupplierVO;
import com.lvmama.jinjiang.service.JinjiangProductService;
import com.lvmama.passport.utils.WebServiceConstant;
import com.lvmama.shholiday.response.ProductDetailResponse;
import com.lvmama.shholiday.service.ShHolidayProductService;
import com.lvmama.shholiday.vo.product.ProductInfo;
import com.lvmama.shholiday.vo.product.ProductPrice;

/**
 * 
 * 供应商action
 * @author yanzhirong
 *
 */
@Results({
	@Result(name = "selectByCondition",location = "/WEB-INF/duijie/duijieProductInStock.jsp"),
	@Result(name = "supplierProductInfoDetail",location = "/WEB-INF/duijie/supplierProductInfoDetail.jsp"),
	@Result(name = "selectShHolidayProduct",location = "/WEB-INF/duijie/duijieShHolidayProductInStock.jsp"),
	@Result(name = "shHolidayProductInfoDetail",location = "/WEB-INF/duijie/shHolidayProductInfoDetail.jsp")
})
@ParentPackage("json-default")
public class SupplierAction extends BackBaseAction{

	private static final long serialVersionUID = 6118485108491356477L;
	
	/** 供应商Id*/
	private Long supplierId;
	
	/** 目的地*/
	private String destination;
	
	/** 线路类型*/
	private String routeType;
	
	/** 关键字*/
	private String keyword;	
	
	/** 上航产品id*/
	private Long productId;
	
	/** 供应商产品id*/
	private String supplierProdId;
	
	/** 驴妈妈产品id*/
	private Long lvmamaProdId;
	
	/** 供应商产品*/
	private ProductInfo productInfo;
	
	/** 供应商产品价格*/
	private List<String> priceList;
	
	/** 产品名称*/
	private String productName;
	
	/** 产品展示页面信息*/
	private ViewPage viewPage;
	
	/** 驴妈妈产品页面Id*/
	private Long pageId;
	
	/** 产品详细页面信息map*/
	private Map<String,String> contentmap = new HashMap<String,String>();
	
	/** 供应商id列表*/
	private List<SupplierVO> supplierList = new ArrayList<SupplierVO>();
	
	/** 线路类型枚举*/
	private SUPPLIER_PRODUCT_TYPE[] routeTypeList;
	
	/** 供应商产品行程列表*/
	private List<SupplierViewJourney> journeyList;
	
	private ShHolidayProductService shholidayProductService;
	
	private JinjiangProductService jinjiangProductService;
	
	private SupplierProdService supplierProdService;
	
	private SupplierViewContentService supplierViewContentService;
	
	private SupplierViewJourneyService supplierViewJourneyService;
	
	private Page<ProductInfo> productInfoPage = new Page<ProductInfo>();
	
	private Page<SupplierProd> supplierProdPage = new Page<SupplierProd>();
	
	
	/**
	 * 根据条件查询供应商产品列表
	 * @return
	 */
	@Action("/route/duijieProductInStock")
	public String selectByCondition() {
		supplierList.add(new SupplierVO(Long.valueOf(WebServiceConstant.getProperties("shholiday.supplierId")),"上航假期"));
		supplierList.add(new SupplierVO(Long.valueOf(WebServiceConstant.getProperties("jinjiang.supplierId")),"锦江国旅"));
		routeTypeList = Constant.SUPPLIER_PRODUCT_TYPE.values();
		if(supplierId !=null && supplierId.equals(Long.valueOf(WebServiceConstant.getProperties("shholiday.supplierId")))){
			return selectShHolidayProduct();
		}else if(supplierId !=null &&supplierId.equals(Long.valueOf(WebServiceConstant.getProperties("jinjiang.supplierId")))){
			return selectJinjiangProduct();
		}else{
			return "selectByCondition";
		}
	}
	
	/**
	 * 锦江对接产品列表
	 * @return
	 */
	private String selectJinjiangProduct(){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		/*供应商Id*/
		if(null != supplierId){
			paramMap.put("supplierId", supplierId);
		}
		
		/*目的地*/
		if(!StringUtil.isEmptyString(destination)){
			paramMap.put("destination", destination);
		}
		
		/* 线路类型*/
		if(!StringUtil.isEmptyString(routeType)){
			paramMap.put("routeType", routeType);
		}
		
		/* 关键字*/
		if(!StringUtil.isEmptyString(keyword)){
			paramMap.put("keyword", keyword);
		}
		supplierProdPage.buildUrl(getRequest());
		supplierProdPage.setCurrentPage(super.page);
		long pageNo = supplierProdPage.getCurrentPage();
		paramMap.put("pageNo", pageNo);
		paramMap.put("start", supplierProdPage.getStartRows());
		paramMap.put("end", supplierProdPage.getEndRows());
		Page<SupplierProd> page = jinjiangProductService.selectProductInfoByCondition(pageNo,10L,paramMap);
		supplierProdPage.setTotalResultSize(page.getTotalResultSize());
		supplierProdPage.setItems(page.getItems());
		return "selectByCondition";
	}
	
	/**
	 * 上航对接产品列表
	 * @return
	 */
	private String selectShHolidayProduct(){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		/*供应商Id*/
		if(null != supplierId){
			paramMap.put("supplierId", supplierId);
		}
		
		/*目的地*/
		if(!StringUtil.isEmptyString(destination)){
			paramMap.put("destination", destination);
		}
		
		/* 线路类型*/
		if(!StringUtil.isEmptyString(routeType)){
			paramMap.put("routeType", routeType);
		}
		
		/* 关键字*/
		if(!StringUtil.isEmptyString(keyword)){
			paramMap.put("keyword", keyword);
		}
		productInfoPage.buildUrl(getRequest());
		productInfoPage.setCurrentPage(super.page);
		long pageNo = productInfoPage.getCurrentPage();
		paramMap.put("pageNo", pageNo);
		productInfoPage.setTotalResultSize(100);
		productInfoPage.setItems(shholidayProductService.selectProductInfoByCondition(pageNo,10L,paramMap).getItems());
		return "selectShHolidayProduct";
	}
	
	/** 根据上航产品Id查询产品详情*/
	@Action("/route/shholidayProductInfoDetail")
	public String shholidayProductInfoDetail(){
		ProductDetailResponse  res = shholidayProductService.selectProductInfoById(productId);
		productInfo = res.getProductInfo();
		productName = productInfo.getSupplierProdName();
		
		productInfo.setLvmamaProdId(pageId);
		
		/*List<ProductPrice> productPriceList = res.getProductPrices();
		for(ProductPrice price:productPriceList){
			priceList.add(Constant.SH_HOLIDAY_BRANCH_TYPE.getCnName(price.getSupplierBranchId())+"价:"+price.getIndividualPrice());
		}*/
		
		//产品描述
		List<ViewContent> contentList=productInfo.getContents();
		
		viewPage=new ViewPage();
		if(viewPage!=null && contentList.size()>0){
			//viewPage.initContents(contentList);
			for(int i=0;i<contentList.size();i++) {
				ViewContent content = contentList.get(i);
				contentmap.put(content.getContentType(), content.getContent());
			}
		}
		
		return "shHolidayProductInfoDetail";
	}
	
	
	/**根据供应商产品Id查询产品详情*/
	@Action("/route/supplierProductInfoDetail")
	public String supplierProductInfoDetail(){
		Map<String,Object> productMap = new HashMap<String,Object>();
		productMap.put("supplierProdId", supplierProdId);
		productMap.put("supplierId", Long.parseLong(WebServiceConstant.getProperties("jinjiang.supplierId")));
		List<SupplierProd> prodList = supplierProdService.selectSupplierProd(productMap);
		
		SupplierProd supplierProd = prodList != null ? prodList.get(0): null;
		
		if(supplierProd != null){
			productName = supplierProd.getSupplierProdName();
			lvmamaProdId = supplierProd.getLvmamaProdId();
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("productId", supplierProd.getId());
			
			//产品描述
			List<SupplierViewContent> supplierViewContentList = supplierViewContentService.selectSupplierViewContentByCondition(params);
			
			if(supplierViewContentList !=null && !supplierViewContentList.isEmpty()){
				for(int i=0;i<supplierViewContentList.size();i++) {
					SupplierViewContent content = supplierViewContentList.get(i);
					contentmap.put(content.getContentType(), content.getContent());
				}
			}
			
			//行程
			Map<String,Object> journeyMap = new HashMap<String,Object>();
			journeyMap.put("productId", supplierProd.getId());
			journeyList = supplierViewJourneyService.selectSupplierViewJourneyByCondition(journeyMap);
		}
		
		return "supplierProductInfoDetail";
	}
	
	/** 根据供应商产品Id入库从未入库的产品
	 * @throws Exception */
	@Action("/route/saveMetaProductUnStocked")
	public void saveMetaProductUnStocked() throws Exception{
		ResultHandle result = new ResultHandle();
		boolean flag = true;
		Long lvmamaProductId = null;
		Map<String,Object> params = new HashMap<String,Object>();
		
		try{
			if(supplierId != null && supplierId.equals(Long.valueOf(WebServiceConstant.getProperties("shholiday.supplierId")))){
				shholidayProductService.saveMetaProductForUnStocked(productId.toString());
				params.put("supplierProdId", productId.toString());
				params.put("supplierChannel", "SHHOLIDAY");
			}else if(supplierId != null && supplierId.equals(Long.valueOf(WebServiceConstant.getProperties("jinjiang.supplierId")))){
				flag = jinjiangProductService.saveProductUnStocked(supplierProdId);
				params.put("supplierProdId",supplierProdId );
				params.put("supplierChannel", "JINJIANG");
			}
		}catch(Exception e){
            log.error("saveMetaProductUnStocked error! supplierId=" + supplierId + ", supplierProdId=" + supplierProdId, e);
			result.isFail();
			result.setMsg("出现入库异常！");
		}
		if(!flag){
			result.isFail();
			result.setMsg("入库失败！");
		}
		lvmamaProductId = supplierProdService.getProductIdByCondition(params);
		String resultJson = "{"+(result.isSuccess()?"\"success\":true":"\"fail\":true,\"success\":false")+",\"msg\":\""+(result.getMsg()!=null?result.getMsg():"")+"\""+
				(lvmamaProductId != null?",\"productId\":"+lvmamaProductId:"")+"}";
		//JSONObject.fromObject(result).toString();
		this.sendAjaxResultByJson(resultJson);
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getRouteType() {
		return routeType;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Page<ProductInfo> getProductInfoPage() {
		return productInfoPage;
	}

	public void setProductInfoPage(Page<ProductInfo> productInfoPage) {
		this.productInfoPage = productInfoPage;
	}

	

	public void setShholidayProductService(
			ShHolidayProductService shholidayProductService) {
		this.shholidayProductService = shholidayProductService;
	}

	public SUPPLIER_PRODUCT_TYPE[] getRouteTypeList() {
		return routeTypeList;
	}

	public void setRouteTypeList(SUPPLIER_PRODUCT_TYPE[] routeTypeList) {
		this.routeTypeList = routeTypeList;
	}

	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public List<String> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<String> priceList) {
		this.priceList = priceList;
	}

	public ViewPage getViewPage() {
		return viewPage;
	}

	public void setViewPage(ViewPage viewPage) {
		this.viewPage = viewPage;
	}

	public Map<String, String> getContentmap() {
		return contentmap;
	}

	public void setContentmap(Map<String, String> contentmap) {
		this.contentmap = contentmap;
	}

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	public void setJinjiangProductService(
			JinjiangProductService jinjiangProductService) {
		this.jinjiangProductService = jinjiangProductService;
	}

	public List<SupplierVO> getSupplierList() {
		return supplierList;
	}

	public void setSupplierList(List<SupplierVO> supplierList) {
		this.supplierList = supplierList;
	}

	public Page<SupplierProd> getSupplierProdPage() {
		return supplierProdPage;
	}

	public void setSupplierProdPage(Page<SupplierProd> supplierProdPage) {
		this.supplierProdPage = supplierProdPage;
	}

	public String getSupplierProdId() {
		return supplierProdId;
	}

	public void setSupplierProdId(String supplierProdId) {
		this.supplierProdId = supplierProdId;
	}

	public void setSupplierViewContentService(
			SupplierViewContentService supplierViewContentService) {
		this.supplierViewContentService = supplierViewContentService;
	}

	public void setSupplierViewJourneyService(
			SupplierViewJourneyService supplierViewJourneyService) {
		this.supplierViewJourneyService = supplierViewJourneyService;
	}

	public void setSupplierProdService(SupplierProdService supplierProdService) {
		this.supplierProdService = supplierProdService;
	}

	public void setLvmamaProdId(Long lvmamaProdId) {
		this.lvmamaProdId = lvmamaProdId;
	}

	public void setJourneyList(List<SupplierViewJourney> journeyList) {
		this.journeyList = journeyList;
	}

	public Long getLvmamaProdId() {
		return lvmamaProdId;
	}

	public List<SupplierViewJourney> getJourneyList() {
		return journeyList;
	}
	
}
