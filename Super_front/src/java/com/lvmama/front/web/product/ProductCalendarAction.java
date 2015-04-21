package com.lvmama.front.web.product;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.prod.ProdCouponIntervalService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.prod.ProdProductTagService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.pet.service.favor.FavorService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MemcachedCalendarUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 根据产品id显示出日历表，供其他页面调用
 * 
 * @author Brian
 * 
 */
@Results({
			@Result(name = "calendar", location = "/common/productCalendar.ftl", type = "freemarker"),
	       @Result(name = "timePriceTable", location = "/WEB-INF/pages/product/timePriceTable.ftl", type = "freemarker"),
	       @Result(name = "timePriceList", location = "/WEB-INF/pages/product/timePriceList.ftl", type = "freemarker"),
	       @Result(name = "selfTimePriceTable", location = "/WEB-INF/pages/product/selfTimePriceTable.ftl", type = "freemarker"),
	       @Result(name = "tuanTimePriceTable", location = "/WEB-INF/pages/product/tuanTimePriceTable.ftl",type = "freemarker")
})
public class ProductCalendarAction extends ProductBaseAction {
	private static final Log log = LogFactory.getLog(ProductCalendarAction.class);
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 6216912167292814804L;	
	private String targetDiv = "calendar";
	// @param 销售产品类别id.
	private Long branchId;
	private Long packId;
	private List<CalendarModel> cmList;
	private ProdProductBranch prodProductBranch;
	private ProdProduct product;
	private FavorService favorService;
	private ProdProductTagService prodProductTagService;
	private ProdCouponIntervalService prodCouponIntervalService;
	private ProdProductService prodProductService;
	private boolean isMultiJourney = false;
	private Integer month;
	
	//　指定最晚预约日期
	private Date validEndTime;
	
	private ProdProduct prod = null;
	
	private static String calendarMode = Constant.getInstance().getProperty("calendar.mode");

    /**
     * 优惠是否有效
     */
    private String couponEnabled = "Y";

	@Action("/product/calendarIgnorFront")
	public String calendarIgnorFront() {
		this.productId = id;
		getTimePrice();
		return "calendar";
	}
	/**
	 * 前台时间价格表
	 * @return
	 */
	@Action("/product/timePriceTable")
	public String timePriceTable() {
		getTimePrice();		
		return "timePriceTable";
	}
	/**
	 * 团购时间价格表
	 * @return
	 */
	@Action("/product/tuanTimePriceTable")
	public String tuanTimePriceTable(){
		getTimePriceByTime();
		return "tuanTimePriceTable";
	}
	/**
	 * 返回JSON格式时间价格表
	 * 
	 * @throws IOException 
	 * @author liudong
	 * @author nixianjun 2014.2.27
	 * */
	@Action("/product/timePriceJson")
	public void JSONTimePrice() throws IOException{
		String[] month ={"1","2","3","4","5","6","7","8","9","10","11","12"};
		getTimePrice();
		JSONObject objM=new JSONObject();
		if(CollectionUtils.isNotEmpty(cmList)){
			try {
				// 循环判断月份
				for (int m = 0; m < month.length; m++) {
					JSONArray array = new JSONArray();
					
					for (int k = 0; k < cmList.size(); k++) {
						CalendarModel cm = cmList.get(k);
						TimePrice[][] calendar = cm.getCalendar();
						for (int i = 0; i < calendar.length; i++) {
							TimePrice[] objH = calendar[i];
							for (int j = 0; j < objH.length; j++) {
								JSONObject obj = new JSONObject();
								TimePrice tp = objH[j];
								if ((DateUtil.getDateTime("M", tp.getSpecDate())).equals(month[m])) {
										obj.put("date", DateUtil.getDateTime("yyyy-MM-dd", tp.getSpecDate()));
										long dayStock = tp.getDayStock();
										if (dayStock == -1 || dayStock == 0|| dayStock > 0|| tp.isOnlyForLeave() == true|| tp.getOverSale().equals("true")) {
											 JSONObject j1=excecuteGetJsonTimeData(tp,dayStock);
											 obj.putAll(j1);
										} else {
											obj.put("number", "");
											obj.put("price", "");
											obj.put("active", "");
										 }
									   if(!array.contains(obj)){
										   array.add(obj);
									   }
								 }
							}
						}
					}
					if (array != null && !array.equals("")) {
						objM.put(month[m], array);
					}
 				}
				
			} catch (Exception e) {
			    e.printStackTrace();
			    log.info("新版景区返回时间价格表出错");
 			}
		}
 		outputJsonMsg(objM.toString());
	}
	
	public JSONObject excecuteGetJsonTimeData(TimePrice tp,long dayStock){
		JSONObject obj = new JSONObject();
		if ((!tp.isNeedResourceConfirm() && dayStock == -1) || dayStock > 9) {
					if (isMultiJourney && tp.getMultiJourneyId() == null) { } else {
						obj.put("number", "充足");
						obj.put("price",
								tp.getPriceInt());
						if ((tp.getFavorJsonParams() != null && !tp
								.getFavorJsonParams()
								.equals(""))
								|| (tp.getCuCouponFlag() > 0)) {
							obj.put("active", "促");
						} else {
							obj.put("active", "");
						}
					 }
			} else if (dayStock > -1&& dayStock != 0) {
						if (isMultiJourney
								&& tp.getMultiJourneyId() == null) {
						} else {
							obj.put("number", dayStock);
							obj.put("price",
									tp.getPriceInt());
							if ((tp.getFavorJsonParams() != null && !tp
									.getFavorJsonParams()
									.equals(""))
									|| (tp.getCuCouponFlag() > 0)) {
								obj.put("active", "促");
							} else {
								obj.put("active", "");
							}
						}
			} else if (((tp.isOverSaleAble() || tp.isNeedResourceConfirm()) && dayStock == -1)|| (tp.isOverSaleAble() && dayStock == 0)) {
							if (isMultiJourney
									&& tp.getMultiJourneyId() == null) {

								} else {
									obj.put("number", "");
									obj.put("price",
											tp.getPriceInt());
									if ((tp.getFavorJsonParams() != null && !tp
											.getFavorJsonParams()
											.equals(""))
											|| (tp.getCuCouponFlag() > 0)) {
										obj.put("active", "促");
									} else {
										obj.put("active", "");
									}
								}
				} else if (!tp.isSellable(1)) {
				obj.put("number", "售完");
				obj.put("price", tp.getPriceInt());
				obj.put("active", "");
			   }
	   return obj;
	}
	
	public boolean getCalendarOnOff(){
		Object ob = MemcachedCalendarUtil.getInstance().get(Constant.Calendar_MECH.PROD_PRODUCT_CALENDAR_ON_OFF.getCode().toString());
		//memcached
		if(ob == null){
			//属性文件
			if(StringUtils.isNotEmpty(calendarMode) && calendarMode.trim().equals("true")){
				return true;
			}
			return false;
		}else{
			return true;
		}
	}
	
	public void getCalendarMode(){
		if (branchId!=null&&branchId > 0) {
			this.prodProductBranch = super.pageService.getProdBranchByProdBranchId(branchId);			
			this.cmList = productServiceProxy.getProductCalendarByBranchId(branchId);
			this.cmList = favorService.fillFavorParamsInfoForCalendar(null,branchId, cmList);//填充优惠信息
			//填充促优惠标签
			this.cmList = fillCuCouponFlagForCalendar(prodProductBranch.getProductId(), branchId, cmList);
			this.prod = prodProductService.getProdProduct(prodProductBranch.getProductId());
		}else if(productId!=null&&productId > 0){			
			this.prodProductBranch = super.pageService.selectDefaultBranchByProductId(productId);
			this.cmList = productServiceProxy.getProductCalendarByProductId(productId);
			this.cmList = favorService.fillFavorParamsInfoForCalendar(productId, null,cmList);//填充优惠信息
			//填充促优惠标签
			this.cmList = fillCuCouponFlagForCalendar(productId, prodProductBranch.getProdBranchId(), cmList);
			this.prod = prodProductService.getProdProduct(productId);
		}
	} 
	
	private void getTimePrice() {
		ProdProduct prod = null;
		if(getCalendarOnOff()){
			Object obCalendar = MemcachedCalendarUtil.getInstance().get(Constant.Calendar_MECH.PROD_PRODUCT_CALENDAR_MECH.getCode().toString()+productId);
			Object obProduct = MemcachedCalendarUtil.getInstance().get(Constant.Calendar_MECH.PROD_PRODUCT_MECH.getCode().toString()+productId);
			if(obCalendar == null || obProduct == null){
				getCalendarMode();
				log.info("============ProductCalendar=== set Memcached=========productId : "+productId);
				MemcachedCalendarUtil.getInstance().set(Constant.Calendar_MECH.PROD_PRODUCT_CALENDAR_MECH.getCode().toString()+productId, this.cmList);
				MemcachedCalendarUtil.getInstance().set(Constant.Calendar_MECH.PROD_PRODUCT_MECH.getCode().toString()+productId, this.prod);
			}else{
				log.info("============ProductCalendar=== get Memcached=========productId : "+productId);
				this.cmList = (List<CalendarModel>) obCalendar;
				this.prod = (ProdProduct) prod;
			}
		}else{
			getCalendarMode();
		}
		if(this.prod != null) {
			if(this.prod.isRoute()) {
				ProdRoute pr = (ProdRoute) this.prod;
				isMultiJourney = pr.hasMultiJourney();
			}
		}

        couponEnabled = Constant.initCouponEnabled(productId)?"Y":"N";

	}
	
	public void getTimePriceByTime(){
		ProdProduct prod = null;
		if (branchId!=null && branchId > 0) {
			prodProductBranch = super.pageService.getProdBranchByProdBranchId(branchId);			
			cmList = productServiceProxy.getSelfProductCalendarByBranchIdAndTime(branchId,null,validEndTime);
			cmList = favorService.fillFavorParamsInfoForCalendar(null,branchId, cmList);//填充优惠信息
			//填充促优惠标签
			cmList = fillCuCouponFlagForCalendar(prodProductBranch.getProductId(), branchId, cmList);
			prod = prodProductService.getProdProduct(prodProductBranch.getProductId());
		}
		if(prod != null) {
			if(prod.isRoute()) {
				ProdRoute pr = (ProdRoute) prod;
				isMultiJourney = pr.hasMultiJourney();
			}
		}
	}
	/**
	 * 前台时间价格表
	 * @return
	 */
	@Action("/product/selfTimePriceTable")
	public String timePriceTableForSelf() {
		Date beginDay = new Date();
		int maxDay = 43;
		String url = "selfTimePriceTable";
		if(month!=null && month>0){
			url = "timePriceList";
			beginDay = getDay(month);
		}
		//最多查询180天的数据
		int betweenTime = DateUtil.getDaysBetween(new Date(), beginDay);
		if(betweenTime+43>180){
			maxDay = 180-betweenTime;
		}
		if(maxDay<=0){
			return "selfTimePriceTable";
		}
		if (branchId!=null&&branchId > 0) {
			prodProductBranch = super.pageService.getProdBranchByProdBranchId(branchId);			
			cmList = productServiceProxy.getSelfProductCalendarByBranchIdAndDay(branchId,beginDay);
			cmList = favorService.fillFavorParamsInfoForCalendar(null,branchId, cmList);//填充优惠信息
			//填充促优惠标签
			cmList = fillCuCouponFlagForCalendar(prodProductBranch.getProductId(), branchId, cmList);
			//只取请求月以后的数据
			if(month!=null&&cmList!=null){
				if(month>cmList.get(0).getMonth()){
					cmList.remove(0);
				}
			}
		}
		return url;
	}
	
	
	private  Date getDay(int month) {
		if(month>12){
			month = month-12;
		}
		Date beginDay = new Date();
		int m = DateUtil.getMonth(beginDay);  
		int y = Integer.parseInt(DateUtil.getFormatYear(beginDay)); 
		//最多查询180天的数据 只需加一年就可以
		if(m>month){
			y++;
		}
		Calendar c = Calendar.getInstance();
		c.set(y, month-1, 1);
		c.getTime();
		c.set(Calendar.DAY_OF_WEEK, 1);
		Date newDay = c.getTime();
		newDay = DateUtil.accurateToDay(newDay);
		return newDay;
	}
	
	/**
	 * 获取该产品当前7个月范围促优惠时间标示
	 * @param productId
	 * @param branchId
	 * @param List<CalendarModel> list
	 * @return
	 */
	private List<CalendarModel> fillCuCouponFlagForCalendar(Long productId, Long branchId, List<CalendarModel> cmList){
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DATE, -1);
		Date startDate = cal.getTime();
		cal.add(Calendar.MONTH, 7);
		Date endDate = cal.getTime();
		Map<String,Object> dateParam = new HashMap<String,Object>();
		dateParam.put("startDate", startDate);
		dateParam.put("endDate", endDate);
		cmList = prodCouponIntervalService.fillCuCouponFlagForCalendar(productId, branchId, cmList, dateParam);
		return cmList;
	}
	
	public String getTargetDiv() {
		return targetDiv;
	}

	public void setTargetDiv(String targetDiv) {
		this.targetDiv = targetDiv;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
 
	public List<CalendarModel> getCmList() {
		return cmList;
	}

	public ProdProductBranch getProdProductBranch() {
		return prodProductBranch;
	}

	public ProdProduct getProduct() {
		return product;
	}
	
	
	public Long getPackId() {
		return packId;
	}
	public void setPackId(Long packId) {
		this.packId = packId;
	}
	/**
	 * 判断产品是不是自主打包
	 * @return
	 */
	public boolean hasProdSelfPack(){
		return prodProductBranch != null
				&& prodProductBranch.getProdProduct() != null
				&& prodProductBranch.getProdProduct().hasSelfPack();
	}
	
	/**
	 * 是否需要显示起标记
	 * @return
	 */
	public boolean hasPriceQiFlag(){
		return prodProductBranch!=null
				&& prodProductBranch.getProdProduct()!=null
				&& prodProductBranch.getProdProduct().isRoute()
				&& ArrayUtils.contains(qiFlagProductType, prodProductBranch.getProdProduct().getSubProductType())
				&& ((ProdRoute)prodProductBranch.getProdProduct()).hasQiFlag();
	}
	private String qiFlagProductType[]={Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name(),Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name()};
	private static final Log logger=LogFactory.getLog(ProductCalendarAction.class);

	public void setFavorService(FavorService favorService) {
		this.favorService = favorService;
	}
	 
	public void setProdProductTagService(ProdProductTagService prodProductTagService) {
		this.prodProductTagService = prodProductTagService;
	}
	public void setProdCouponIntervalService(
			ProdCouponIntervalService prodCouponIntervalService) {
		this.prodCouponIntervalService = prodCouponIntervalService;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
	public Date getValidEndTime() {
		return validEndTime;
	}
	public void setValidEndTime(Date validEndTime) {
		this.validEndTime = validEndTime;
	}
	
	public boolean hasMultiJourney() {
		return isMultiJourney;
	}

    public String getCouponEnabled() {
        return couponEnabled;
    }

    public void setCouponEnabled(String couponEnabled) {
        this.couponEnabled = couponEnabled;
    }
}
