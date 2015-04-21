package com.lvmama.clutter.web.html5;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceActivity;
import com.lvmama.comm.pet.po.visa.VisaApplicationDocument;
import com.lvmama.comm.pet.po.visa.VisaApplicationDocumentDetails;
import com.lvmama.comm.pet.service.place.PlaceActivityService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.pet.service.visa.VisaApplicationDocumentService;
import com.lvmama.comm.vo.ProdCProduct;
import com.lvmama.comm.vo.visa.VisaVO;

/**
 * 移动端 嵌入的html页面 .
 * @author qinzubo
 *
 */
@Results({ 
	@Result(name = "index", location = "/WEB-INF/pages/html5/html5_detail.html", type="freemarker"),
	@Result(name = "index_desc", location = "/WEB-INF/pages/html5/html5_desc.html", type="freemarker"),
	@Result(name = "index_desc_all", location = "/WEB-INF/pages/common/prod_comm_desc.html", type="freemarker"),
	@Result(name = "help", location = "/WEB-INF/pages/html5/help_${pageName}.html", type="freemarker"),
	@Result(name = "helpTags", location = "/WEB-INF/pages/html5/help_tags.html", type="freemarker"),
	@Result(name = "visa", location = "/WEB-INF/pages/html5/help_visa.html", type="freemarker"),
	@Result(name = "error", location = "/WEB-INF/pages/html5/html5_error.html", type="freemarker"),
	@Result(name = "placeActivity", location = "/WEB-INF/pages/html5/html5_activity.html", type="freemarker")
})
@Namespace("/mobile/html5")
public class Html5Action extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 目的地服务
	 */
	protected PlaceService placeService;
	
	/**
	 * 
	 */
	private PageService pageService;
	
	protected ComPictureService comPictureService;
	
	/**
	 * 产品服务 
	 */
	private ProdProductService prodProductService;
	
	/**
	 * 签证材料远程服务
	 */
	private VisaApplicationDocumentService visaApplicationDocumentService;
	
	private List<VisaVO> visaVOList=new ArrayList<VisaVO>();
	
	// 目的地. 
	private Place place;
	private ProdCProduct prodCProduct;
	private ViewPage viewPage; 
	private ProdProduct product;
	private List<ViewJourney> viewJourneyList ;
	private String placeId; // 景点id 
	private String productId;// 产品id 
	private String type = "FEATURES"; // 类型:  特色 FEATURES  费用说明 COSTCONTAIN 重要提示 IMPORTANTNOTES
	private String tag; // 用来标识跳转到那个页面. 

	private String pageName="";// 帮助页面文件的名字 
	private String helpTags="";// 标签 ：如 奖金抵用1 早订早惠2 多订多惠3积分抵扣4
	private String cashRefundYuan="0"; // 返现金额 
	private String mobileCashRefundYuan="0"; // 手機端多返金額
	private String firstChannel; // 渠道
	private PlaceActivityService placeActivityService;
	
	private String placeActivityId; // 景点活动id 

	/**
	 *  tags标签 ；1：优惠 ；2：抵扣 3：返现 
	 */
	private String tagsType; 
	
	/**
	 * 详情页 ，包括经典和产品详情 
	 * @return string 
	 * @throws Exception
	 */
	@Action("index")
	public String index() throws Exception {
		try{
			// Constant.VIEW_CONTENT_TYPE
			if(!StringUtils.isEmpty(placeId)) { // 景点 
				place = placeService.queryPlaceByPlaceId(Long.valueOf(placeId));
			} else if(!StringUtils.isEmpty(productId)) { // 产品 
				product = prodProductService.getProdProductById(Long.valueOf(productId));
				Map<String,Object> data = pageService.getProdCProductInfo(Long.valueOf(productId),false);
				if(null != data ) {
					if( data.get("viewPage") != null) {
						viewPage = (ViewPage)data.get("viewPage");
					}
					// 行程说明
					if(data.get("viewJourneyList") != null) {
						viewJourneyList = (List<ViewJourney>)data.get("viewJourneyList");
						for (ViewJourney vj : viewJourneyList) {
							vj.setJourneyPictureList(comPictureService.getPictureByObjectIdAndType(vj.getJourneyId(), "VIEW_JOURNEY"));
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
		if("1".equals(tag)) {
			return "index_desc";
		}else if("2".equals(tag)) {
			return "index_desc_all";
		}
		return "index";
	}

	/**
	 * 帮助页面
	 * @return
	 */
	@Action("help")
	public String help() {
		if(StringUtils.isEmpty(pageName)) {
			return "error";
		}
		if(!StringUtils.isEmpty(tagsType)) {
			return "helpTags";
		}
		return "help";
	}
	
	/**
	 * 帮助页面
	 * @return
	 */
	@Action("visa")
	public String visa() {
		if(StringUtils.isEmpty(productId)) {
			return "error";
		}
		prodCProduct = pageService.getProdCProduct(Long.valueOf(productId));
		//签证内容
		if(null!=prodCProduct.getProdRoute()&&StringUtils.isNotBlank(prodCProduct.getProdRoute().getCountry())
		    &&StringUtils.isNotBlank(prodCProduct.getProdRoute().getCity())
			&&StringUtils.isNotBlank(prodCProduct.getProdRoute().getVisaType())){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("country", prodCProduct.getProdRoute().getCountry());
			param.put("visaType", prodCProduct.getProdRoute().getVisaType());
			param.put("city", prodCProduct.getProdRoute().getCity());
			//文档
		    List<VisaApplicationDocument> visaApplicationDocumentList=visaApplicationDocumentService.query(param);
		    for(VisaApplicationDocument v:visaApplicationDocumentList){
		    	List<VisaApplicationDocumentDetails> vList=new ArrayList<VisaApplicationDocumentDetails>();
		    	VisaVO visaVo=new VisaVO();
		    	//文档详情
		    	 vList = visaApplicationDocumentService.queryDetailsByDocumentId(v.getDocumentId());
		    	 visaVo.setDocumentId(v.getDocumentId());
		    	 visaVo.setOccupation(v.getOccupation());
		    	 visaVo.setVisaApplicationDocumentDetailsList(vList);
		    	 this.visaVOList.add(visaVo);
		    }
			
		}
		
		return "visa";
	}
	
	@Action("placeActivity")
	public String placeActivity() {
		if(!StringUtils.isEmpty(placeActivityId)) {
			try{
				PlaceActivity p = placeActivityService.queryPlaceActivityByPlaceActivityId(Long.valueOf(placeActivityId));
				if(null != p) {
					this.getRequest().setAttribute("placeActivity", p);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return "placeActivity";
	}
	
	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}
	
	public Place getPlace() {
		return place;
	}


	public void setPlace(Place place) {
		this.place = place;
	}
	
	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	public ViewPage getViewPage() {
		return viewPage;
	}


	public void setViewPage(ViewPage viewPage) {
		this.viewPage = viewPage;
	}

	public String getPlaceId() {
		return placeId;
	}


	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}


	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
	
	public ProdProduct getProduct() {
		return product;
	}


	public void setProduct(ProdProduct product) {
		this.product = product;
	}
	
	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}

	public List<ViewJourney> getViewJourneyList() {
		return viewJourneyList;
	}


	public void setViewJourneyList(List<ViewJourney> viewJourneyList) {
		this.viewJourneyList = viewJourneyList;
	}
	
	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	
	public String getHelpTags() {
		return helpTags;
	}

	public void setHelpTags(String helpTags) {
		this.helpTags = helpTags;
	}
	public String getCashRefundYuan() {
		return ClientUtils.subZeroAndDot(cashRefundYuan);
	}

	public void setCashRefundYuan(String cashRefundYuan) {
		this.cashRefundYuan = cashRefundYuan;
	}

	public String getMobileCashRefundYuan() {
		return ClientUtils.subZeroAndDot(mobileCashRefundYuan);
	}

	public void setMobileCashRefundYuan(String mobileCashRefundYuan) {
		this.mobileCashRefundYuan = mobileCashRefundYuan;
	}
	

	public void setVisaApplicationDocumentService(
			VisaApplicationDocumentService visaApplicationDocumentService) {
		this.visaApplicationDocumentService = visaApplicationDocumentService;
	}

	public List<VisaVO> getVisaVOList() {
		return visaVOList;
	}

	public void setVisaVOList(List<VisaVO> visaVOList) {
		this.visaVOList = visaVOList;
	}
	public ProdCProduct getProdCProduct() {
		return prodCProduct;
	}

	public void setProdCProduct(ProdCProduct prodCProduct) {
		this.prodCProduct = prodCProduct;
	}

	public String getFirstChannel() {
		return firstChannel;
	}

	public void setFirstChannel(String firstChannel) {
		this.firstChannel = firstChannel;
	}
	
	public String getPlaceActivityId() {
		return placeActivityId;
	}

	public void setPlaceActivityId(String placeActivityId) {
		this.placeActivityId = placeActivityId;
	}
	
	public PlaceActivityService getPlaceActivityService() {
		return placeActivityService;
	}

	public void setPlaceActivityService(PlaceActivityService placeActivityService) {
		this.placeActivityService = placeActivityService;
	}
	
	public String getTagsType() {
		return tagsType;
	}

	public void setTagsType(String tagsType) {
		this.tagsType = tagsType;
	}
}
