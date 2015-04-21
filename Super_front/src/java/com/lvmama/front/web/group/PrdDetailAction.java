/**
 * 
 */
package com.lvmama.front.web.group;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.pet.po.prod.ProdContainer;
import com.lvmama.comm.pet.service.prod.ProductHeadQueryService;
import com.lvmama.comm.pet.vo.ViewPlaceCoordinate;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ProdCProduct;

/**
 * 
 * 团购明细Actin
 * @author songlianjun
 *
 */
@Results( { 
	@Result(name = "groupPrdDetail", location = "/WEB-INF/pages/group/groupPrdDetails.ftl", type = "freemarker"),
	@Result(name = "todayOtherGroupPrd", location = "/WEB-INF/pages/group/todayOtherGroupPrd.ftl", type = "freemarker")
})
public class PrdDetailAction extends OtherProductAction {
	private PageService pageService;
	private long diff;
	private Map<String, Object> productInfo;
	private ProdProduct prodProduct= null;
	private double discount;
	private long orderCount;
	private List<Map> tags;
	private boolean isPreview = true;
	private List<CalendarModel> cmList;
	private ProdProductBranch prodProductBranch;
	List<ViewPlaceCoordinate> placeCoordinateHotel;
	private ProductHeadQueryService productServiceProxy;
	private String tn;
	private String baiduid;
	protected String containerCode;
	protected String provincePlaceId;
	protected String cityPlaceId;
	protected String fromPlaceCode;
	protected Long fromPlaceId;
	protected String fromPlaceName;
	/**
	 * 查看团购产品详情
	 * @return
	 * @throws Exception
	 */
	@Action("/group/viewGroupPrdDetail")
	public String viewTuanPrdDetail() throws Exception {
		
		HttpSession session = getRequest().getSession(true);
		if (getFromPlaceId() != null) {
			session.setAttribute("fromPCode", getFromPlaceCode());
			session.setAttribute("fromPid", getFromPlaceId());
			session.setAttribute("fromPName", getFromPlaceName());
		} else if ((Long) session.getAttribute("fromPid") != null) {
			this.fromPlaceCode = (String) session.getAttribute("fromPCode");
			this.fromPlaceId = (Long) session.getAttribute("fromPid");
			this.fromPlaceName = (String) session.getAttribute("fromPName");
		}
		init(Constant.CHANNEL_ID.CH_TUANGOU.name());
		
		//productId = Long.valueOf(this.getRequest().getParameter("id"));
		productInfo = pageService.getProdCProductInfo(productId,isPreview);
		if(productInfo != null && productInfo.get("viewPage") != null) {
			ViewPage record = (ViewPage)productInfo.get("viewPage");
			record.setPictureList(getComPictureService().getPictureByPageId(record.getPageId()));
			productInfo.put("comPictureList", record.getPictureList());
		}
		if(productInfo.get("viewJourneyList") != null) {
			List<ViewJourney> viewJourneyList = (List<ViewJourney>)productInfo.get("viewJourneyList");
			for (ViewJourney vj : viewJourneyList) {
				vj.setJourneyPictureList(getComPictureService().getPictureByObjectIdAndType(vj.getJourneyId(), "VIEW_JOURNEY"));
			}
		}
		ProdCProduct prodCProduct =(ProdCProduct) productInfo.get("prodCProduct");
		if(prodCProduct==null ){//没有找到跳转404
			this.LOG.info("product is null,pageId:"+productId+" channel:"+Constant.CHANNEL.TUANGOU.name());
			 return ERROR; 
		}else if(!isPreview && !prodCProduct.getProdProduct().isOnLine()){
			this.LOG.info("产品"+productId+"未上线Sellable="+prodCProduct.getProdProduct().getOnLine());
			return ERROR;
		}
		prodProduct = prodCProduct.getProdProduct();
		if(prodProduct.getMarketPriceYuan()>0){
			discount = new BigDecimal(prodProduct.getSellPriceYuan()/prodProduct.getMarketPriceYuan()*10).setScale(1,BigDecimal.ROUND_FLOOR).doubleValue();
		}else{
			discount=0;
		}
		
		//默认类别
		prodProductBranch = (ProdProductBranch) productInfo.get("prodBranch");
		if (prodProductBranch==null) {
			this.LOG.info("产品"+productId+"没有默认类别");
		}
		
		// 时间价格表
		if(productId!=null && productId > 0){							
			cmList = productServiceProxy.getProductCalendarByProductId(productId);
		}
		
		// 酒店类的目的地周边酒店
		if (prodCProduct.getTo()!=null && Constant.PRODUCT_TYPE.HOTEL.name().equals(prodProduct.getProductType())
				&& !Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(prodProduct.getSubProductType())) {			
//			placeCoordinateHotel = placeRemoteService.getCoordinateByPlace(prodCProduct.getTo().getPlaceId() + "", "3");
		}
		
		long offlineTime = 0;
		long now = System.currentTimeMillis();
		if(prodProduct.getOfflineTime()!=null){
			offlineTime = prodProduct.getOfflineTime().getTime();
		}else{
			offlineTime=-1;
		}
		diff= offlineTime-now;
		//获取产团购产品已购买人数
		orderCount = super.groupDreamService.countOrderByProduct(productId);
		productInfo.put("cmList", productServiceProxy.getProductCalendarByProductId(productId));
		//查询产品标签
		tags = super.groupDreamService.getPrdTagByProductId(productId);
		loadOtherPrdList();
		return "groupPrdDetail";
	}
	
	protected void init(String channel) {
	    provincePlaceId = (String) getRequest().getAttribute(Constant.DEFAULT_PROVINCE_PLACE_ID);
        cityPlaceId = (String) getRequest().getAttribute(Constant.DEFAULT_CITY_PLACE_ID);
        if (provincePlaceId == null) {
    		String ipProvincePlaceId = (String) getRequest().getAttribute(Constant.IP_PROVINCE_PLACE_ID);
    		String ipCityPlaceId = (String) getRequest().getAttribute(Constant.IP_CITY_PLACE_ID);
    		if (!"".equals(ipProvincePlaceId) && ipProvincePlaceId != null) {
    		    provincePlaceId = this.getDefaultPlaceId(containerCode, ipProvincePlaceId, "3548");
            }
            if (provincePlaceId == null || "".equals(provincePlaceId)) {
                provincePlaceId = "79";
                cityPlaceId = "79";
            } else if (!"".equals(ipCityPlaceId)) {
                cityPlaceId = this.getDefaultPlaceId(containerCode, ipCityPlaceId, null);
            }
            ServletUtil.addCookie(super.getResponse(), Constant.DEFAULT_PROVINCE_PLACE_ID, provincePlaceId, 30);
            ServletUtil.addCookie(super.getResponse(), Constant.DEFAULT_CITY_PLACE_ID, cityPlaceId, 30);
        }
		if (fromPlaceCode == null) {
			fromPlaceCode = (String) getRequest().getAttribute(Constant.IP_AREA_LOCATION);
		}
		if (fromPlaceId == null) {
            fromPlaceId = (Long) getRequest().getAttribute(Constant.IP_FROM_PLACE_ID);
        }
		if (fromPlaceName == null) {
		    fromPlaceName = (String) getRequest().getAttribute(Constant.IP_FROM_PLACE_NAME);
		}
	}
	private String getDefaultPlaceId(String containerCode, String ipLocationId, String destId) {
		String key = "ToPlaceOnlyTemplateHomeAction_getDefaultPlaceId" + "_" + containerCode + "_" + ipLocationId + "_" + destId;
		ProdContainer prodContainer = (ProdContainer) MemcachedUtil.getInstance().get(key);
		if (prodContainer == null) {
			prodContainer = prodContainerProductService.getToPlace(containerCode, ipLocationId, destId);
			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR, prodContainer);
		}
		if (prodContainer!=null) {
			return prodContainer.getToPlaceId();
		}else{
			return "";
		}
	}
	public String groupProductPreview() throws Exception{
		this.isPreview = false;
		return this.viewTuanPrdDetail();
	}
	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}
	public double getDiscount() {
		return discount;
	}
	public Map<String, Object> getProductInfo() {
		return productInfo;
	}
	public long getDiff() {
		return diff;
	}
	public ProdProduct getProdProduct() {
		return prodProduct;
	}
	public long getOrderCount() {
		return orderCount;
	}
	public List<Map> getTags() {
		return tags;
	}
	/**
	 * 当前系统时间
	 * @return
	 */
	public Long getSysDate() {
		
		Date  sysdate = Calendar.getInstance().getTime();
		sysdate.setHours(0);
		sysdate.setMinutes(0);
		sysdate.setSeconds(0);
		return sysdate.getTime();
	}
	
	public List<CalendarModel> getCmList() {
		return cmList;
	}
	
	public ProdProductBranch getProdProductBranch() {
		return prodProductBranch;
	}



	/**
	 * @return the placeCoordinateHotel
	 */
	public List<ViewPlaceCoordinate> getPlaceCoordinateHotel() {
		return placeCoordinateHotel;
	}

	public void setProductServiceProxy(ProductHeadQueryService productServiceProxy) {
		this.productServiceProxy = productServiceProxy;
	}

	public String getContainerCode() {
		return containerCode;
	}

	public void setContainerCode(String containerCode) {
		this.containerCode = containerCode;
	}

	public String getProvincePlaceId() {
		return provincePlaceId;
	}

	public void setProvincePlaceId(String provincePlaceId) {
		this.provincePlaceId = provincePlaceId;
	}

	public String getCityPlaceId() {
		return cityPlaceId;
	}

	public void setCityPlaceId(String cityPlaceId) {
		this.cityPlaceId = cityPlaceId;
	}

	public String getFromPlaceCode() {
		return fromPlaceCode;
	}

	public void setFromPlaceCode(String fromPlaceCode) {
		this.fromPlaceCode = fromPlaceCode;
	}

	public Long getFromPlaceId() {
		return fromPlaceId;
	}

	public void setFromPlaceId(Long fromPlaceId) {
		this.fromPlaceId = fromPlaceId;
	}

	public String getFromPlaceName() {
		return fromPlaceName;
	}

	public void setFromPlaceName(String fromPlaceName) {
		this.fromPlaceName = fromPlaceName;
	}

	public String getTn() {
		return tn;
	}

	public void setTn(String tn) {
		this.tn = tn;
	}

	public String getBaiduid() {
		return baiduid;
	}

	public void setBaiduid(String baiduid) {
		this.baiduid = baiduid;
	}
}
