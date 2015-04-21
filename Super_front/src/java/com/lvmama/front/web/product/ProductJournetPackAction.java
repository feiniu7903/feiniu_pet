package com.lvmama.front.web.product;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.prod.ProdJourneyProduct;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductJourney;
import com.lvmama.comm.bee.po.prod.ProdProductJourneyPack;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.service.prod.ProdProductJourneyService;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Coupon;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.BuyInfo.OrdTimeInfo;
import com.lvmama.comm.bee.vo.ord.PriceInfo;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceHotel;
import com.lvmama.comm.pet.po.place.PlacePhoto;
import com.lvmama.comm.pet.service.favor.FavorService;
import com.lvmama.comm.pet.service.place.HotelTrafficInfoService;
import com.lvmama.comm.pet.service.place.PlacePageService;
import com.lvmama.comm.pet.service.place.PlacePhotoService;
import com.lvmama.comm.pet.service.prod.ProductHeadQueryService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StackOverFlowUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.CouponInfo;
import com.lvmama.comm.vo.TimeInfo;
import com.lvmama.comm.vo.ViewBuyInfo;
import com.lvmama.front.web.BaseAction;

@ParentPackage("json-default")
@SuppressWarnings("unused")
@Results( {		
		@Result(name = "showJourneyPack", location = "/WEB-INF/pages/product/newdetail/buttom/selfPack/packIndex.ftl", type = "freemarker"),
		@Result(name="receivers_list",location="/WEB-INF/pages/buy/201107/receivers_list.ftl", type = "freemarker")
})
public class ProductJournetPackAction extends BaseAction{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProdProductJourneyService prodProductJourneyService;
	List<ProdProductJourneyPack> prodProdutJourneyPackList;
	protected ViewBuyInfo buyInfo = new ViewBuyInfo();
	protected OrderService orderServiceProxy;
	private PageService pageService;
	private FavorService favorService;
	protected ProdProductService prodProductService;
	private ProductHeadQueryService productServiceProxy;
	private PlacePhotoService placePhotoService;
	private ProdProductPlaceService prodProductPlaceService;
	private PlacePageService placePageService;
	private HotelTrafficInfoService hotelTrafficInfoService;
	
	private Map<String ,String> packContent = new HashMap<String,String>();
	private Map<String ,String> packPrice = new HashMap<String,String>();
	
	public void setProdProductJourneyService(
			ProdProductJourneyService prodProductJourneyService) {
		this.prodProductJourneyService = prodProductJourneyService;
	}
	
	public List<ProdProductJourneyPack> getProdProdutJourneyPackList() {
		return prodProdutJourneyPackList;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	
	
	public void setProductServiceProxy(ProductHeadQueryService productServiceProxy) {
		this.productServiceProxy = productServiceProxy;
	}

	public void setPlacePhotoService(PlacePhotoService placePhotoService) {
		this.placePhotoService = placePhotoService;
	}

	public void setProdProductPlaceService(
			ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setFavorService(FavorService favorService) {
		this.favorService = favorService;
	}

	public ViewBuyInfo getBuyInfo() {
		return buyInfo;
	}

	public void setBuyInfo(ViewBuyInfo buyInfo) {
		this.buyInfo = buyInfo;
	}

	
	public Map<String, String> getPackContent() {
		return packContent;
	}
	
	

	public Map<String, String> getPackPrice() {
		return packPrice;
	}

	@Action("/product/showJourneyPack")
	public String show() {
		if(buyInfo==null){
			return "showJourneyPack";
		}
		try{
			ProdProduct product = pageService.getProdProductByProductId(buyInfo.getProductId());
			if(product!=null){
				buyInfo.setProductType(product.getProductType());
				buyInfo.setSubProductType(product.getSubProductType());
				buyInfo.setSelfPack("true");
			}
			List<ProdProductJourneyPack> packList = prodProductJourneyService.queryProductJourneyPackByProductId(buyInfo.getProductId());
			prodProdutJourneyPackList = new ArrayList<ProdProductJourneyPack>();
			if(CollectionUtils.isNotEmpty(packList)){
				for(ProdProductJourneyPack pk:packList){
					try{
						if("true".equalsIgnoreCase(pk.getOnLine())){
							pk.setValid("false");
							buyInfo.setPackId(pk.getProdJourneyPackId());
							initContent(pk);
							if(prodProductService.checkJourneyRequird(buyInfo)){
								BuyInfo buyInfo=getOrderInfo();
								if(buyInfo.getItemList() != null && buyInfo.getItemList().size()>0){
									buyInfo.setFavorResult(favorService.calculateFavorResultByBuyInfo(buyInfo));
									PriceInfo priceInfo=orderServiceProxy.countPrice(buyInfo);
									if(priceInfo!=null&&priceInfo.getPrice()>0){
										packPrice.put(""+pk.getProdJourneyPackId(),""+(PriceUtil.convertToFen(priceInfo.getPrice())/100));
										pk.setValid("true");
									}
								}
							}
							prodProdutJourneyPackList.add(pk);
						}
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "showJourneyPack";
	}
	private void initContent(ProdProductJourneyPack pk) {
		try{
			StringBuffer str = new StringBuffer("");
			Date beginTime = buyInfo.getVisitDate();
			for(ProdProductJourney prodProductJourney:pk.getProdProductJourneys()){
				List<ProdJourneyProduct> hotels = prodProductJourney.getHotelList();
				str.append(initViewProdProductBranchList(prodProductJourney,hotels,beginTime));
				
				List<ProdJourneyProduct> routes = prodProductJourney.getRouteList();
				str.append(initViewProdProductBranchList(prodProductJourney,routes,beginTime));
				
				List<ProdJourneyProduct> tickets = prodProductJourney.getTicketList();
				str.append(initViewProdProductBranchList(prodProductJourney, tickets,beginTime));
				
				List<ProdJourneyProduct> traffics = prodProductJourney.getTrafficList();
				str.append(initViewProdProductBranchList(prodProductJourney, traffics,beginTime));
				int addDay = prodProductJourney.getMinTime().getNights().intValue();
				
				beginTime = DateUtil.dsDay_Date(beginTime, addDay);
			}
			if(str!=null && str.length()>1){
				int end = str.length()-1;
				buyInfo.setContent(str.substring(0,end));
				packContent.put(""+pk.getProdJourneyPackId(), str.substring(0,end));
			}
		}catch(Exception e){
			//log.error("初始化产品列表出错，错误原因" + e);
		}
	}

	private String initViewProdProductBranchList(ProdProductJourney prodProductJourney,
			List<ProdJourneyProduct> prodJourneyProducts,Date visitDate) throws ParseException {
		StringBuffer str = new StringBuffer();
		if(prodJourneyProducts==null) return "";
		String visitTimeStr = DateUtil.formatDate(visitDate, "yyyy-MM-dd");
		
		List<ProdJourneyProduct> doubleProductlist =  new ArrayList<ProdJourneyProduct>();
		Map<String,String> productName = new HashMap<String,String>();
		
		for(ProdJourneyProduct prodjp:prodJourneyProducts){			
			ProdProductBranch branch=prodjp.getProdBranch();
			if(productName.get(branch.getProdProduct().getProductName())==null){
				productName.put(branch.getProdProduct().getProductName(), branch.getProdProduct().getProductName());
			}else{
				doubleProductlist.add(prodjp);
			}
			int addDay = prodProductJourney.getMinTime().getNights().intValue();
			if(branch.getProdProduct().isHotel()){
				String eaveTime =DateUtil.formatDate(DateUtil.dsDay_Date(visitDate, addDay), "yyyy-MM-dd");
				str.append("hotel_").append(branch.getProdBranchId()).append("_")
				.append(prodjp.getJourneyProductId()).append("_")
				.append(buyInfo.getAdult()).append("_")
				.append(visitTimeStr).append("_")
				.append(eaveTime).append(";");
				
			}else if(branch.getProdProduct().isTraffic()){
				str.append("traffic_").append(branch.getBranchType()).append("-")
				.append(branch.getProdBranchId()).append("-")
				.append(prodjp.getJourneyProductId()).append("_")
				.append(visitTimeStr).append(";");
			}else{
				str.append("product_").append(branch.getProdBranchId()).append("_")
				.append(prodjp.getJourneyProductId()).append("_");
				if(branch.getBranchType().contains("CHILD")){
					str.append(buyInfo.getChild()).append("_");
				}else{
					str.append(buyInfo.getAdult()).append("_");
				}
				
				str.append(visitTimeStr).append(";");
			}
			branch=productServiceProxy.getProdBranchDetailByProdBranchId(prodjp.getProdBranchId(), buyInfo.getVisitDate());			
			if(branch!=null){
				initProductInfo(branch.getProdProduct());
				prodjp.setProdBranch(branch);
			}
		}
		if(doubleProductlist.size()>0){
			prodJourneyProducts.removeAll(doubleProductlist);
		}
		return str.toString();
	}
	
	private void initProductInfo(ProdProduct prodProduct){

		Place comPlace = null;
		comPlace=prodProductPlaceService.getToDestByProductId(prodProduct.getProductId());
		if(comPlace==null){
			comPlace =new Place();
		}else{
			PlacePhoto placePhoto = new PlacePhoto();
			placePhoto.setPlaceId(comPlace.getPlaceId());
			comPlace.setPlacePhoto(placePhotoService.queryByPlacePhoto(placePhoto));	
		}				

		//酒店信息
		PlaceHotel placeHotel =  placePageService.searchPlaceHotel(comPlace.getPlaceId());
		if(placeHotel==null){
			placeHotel = new PlaceHotel();
		}
		//酒店交通信息
		placeHotel.setHotelTrafficInfos(hotelTrafficInfoService.queryByPlaceId(comPlace.getPlaceId()));
		comPlace.setPlaceHotel(placeHotel);
		prodProduct.setToPlace(comPlace);
	}
	
	public BuyInfo getOrderInfo() throws Exception {
		BuyInfo createOrderBuyInfo = new BuyInfo();
		createOrderBuyInfo.setMainProductType(buyInfo.getProductType());
		createOrderBuyInfo.setMainSubProductType(buyInfo.getSubProductType());
		createOrderBuyInfo.setItemList(this.getItem());
		createOrderBuyInfo.setWaitPayment(Constant.WAIT_PAYMENT.PW_DEFAULT.getValue());
		createOrderBuyInfo.setCouponList(this.getCoupon());
		createOrderBuyInfo.setSelfPack(buyInfo.getSelfPack());
		return createOrderBuyInfo;
	}
	
	private List<Coupon> getCoupon() {
		// 构造优惠方式数据
		List<CouponInfo> infoList = this.buyInfo.getCouponList();
		List<Coupon> couponList = new ArrayList<Coupon>();
		if (infoList != null) {
			for (CouponInfo couponInfo : infoList) {
				Coupon coupon = new Coupon();
					
				if (couponInfo!=null && "true".equals(couponInfo.getChecked())){
					coupon.setChecked(couponInfo.getChecked());
					coupon.setCode(couponInfo.getCode());
					coupon.setCouponId(couponInfo.getCouponId());
					couponList.add(coupon);
					break;
				}
			}
		}
		return couponList;
	}
	protected List<Item> getItem() {
		List<Item> itemList = new ArrayList<Item>();
		if(!buyInfo.hasSelfPack()){
			Map<Long, Long> ordOrderItemProds = buyInfo.getOrdItemProdList();
			for (Iterator<Long> iterator = ordOrderItemProds.keySet().iterator(); iterator
					.hasNext();) {
				Long prodBranchId = (Long) iterator.next();
				Long quantity = ordOrderItemProds.get(prodBranchId);
				if(quantity!=null&&quantity>0){
					Item item = new Item();
					ProdProductBranch branch=pageService.getProdBranchByProdBranchId(prodBranchId);
					item.setProductId(branch.getProductId());
					item.setProductBranchId(branch.getProdBranchId());
					item.setQuantity(quantity.intValue());
					item.setVisitTime(buyInfo.getVisitDate());
					item.setFaxMemo(null);
					item.setProductType(branch.getProdProduct().getProductType());
					item.setSubProductType(branch.getProdProduct().getSubProductType());
					itemList.add(item);
				}
			}
			/**
			 * 设置房型日期信息
			 */
			if(Constant.PRODUCT_TYPE.HOTEL.name().equals(buyInfo.getProductType()) && Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(buyInfo.getSubProductType()))	{
				itemList.add(this.getHotelSigelRoomItem());
			}
			
			/**
			 * 设置订单的主产品.
			 */
			Item item=(Item)CollectionUtils.find(itemList, new Predicate() {
				
				public boolean evaluate(Object arg0) {
					Item item = (Item)arg0;
					return buyInfo.getProdBranchId().equals(item.getProductBranchId());
				}
			});
			if(item==null){		
				if(itemList.size()==1){
					item=itemList.get(0);
				}else{
					for(BuyInfo.Item it:itemList){
						if(!Constant.PRODUCT_TYPE.OTHER.name().equals(it.getProductType())){
							item=it;
							break;
						}
					}
					if(item==null){
						item=itemList.get(0);
					}
				}
			}
			item.setIsDefault("true");
		}else{
			Map<Long,TimeInfo> map=buyInfo.getOrdItemProdMap();
			for(Long key:map.keySet()){
				TimeInfo ti=map.get(key);
				Item item=new Item();
				item.setProductBranchId(ti.getProdBranchId());
				item.setJourneyProductId(ti.getJourneyProductId());
				ProdProductBranch branch=pageService.getProdBranchByProdBranchId(item.getProductBranchId());
				item.setProductId(branch.getProductId());
				try{
					item.setVisitTime(ti.getVisitDate());
				}catch(ParseException ex){
					
				}
				if(ti.hasHotel()){
					int quantity=0;
					List<OrdTimeInfo> ordTimeInfoList = new ArrayList<OrdTimeInfo>();
					try{
						int days=(int)ti.getDays();
						for(int i=0;i<days;i++){						
							Date vt=DateUtils.addDays(ti.getVisitDate(), i);
							OrdTimeInfo oti=new OrdTimeInfo();
							oti.setQuantity(ti.getQuantity());
							oti.setVisitTime(vt);
							oti.setProductBranchId(item.getProductBranchId());
							oti.setProductId(item.getProductId());
							ordTimeInfoList.add(oti);
							quantity+=oti.getQuantity();							
						}
					}catch(ParseException ex){
						
					}
					item.setTimeInfoList(ordTimeInfoList);
					item.setQuantity(quantity);
					
				}else{
					item.setQuantity(ti.getQuantity().intValue());
				}
				item.setFaxMemo(null);
				itemList.add(item);
			}	
			if(buyInfo.hasSelfPack()){
				//添加当前下单的主对象
				Item item=new Item();
				item.setQuantity(buyInfo.getTotalQuantity());
				item.setFaxMemo(null);
				item.setIsDefault("true");
				item.setProductBranchId(buyInfo.getProdBranchId());
				ProdProductBranch branch=pageService.getProdBranchByProdBranchId(item.getProductBranchId());
				item.setProductId(branch.getProductId());
				item.setVisitTime(buyInfo.getVisitDate());	
				itemList.add(item);
			}
		}
		
		return itemList;
	}
	
	private Item getHotelSigelRoomItem(){
		Item item = new Item();
		List<OrdTimeInfo> ordTimeInfoList = new ArrayList<OrdTimeInfo>();
		for (TimeInfo timeInfo : buyInfo.getTimeInfo()) {			
			OrdTimeInfo ordTimeInfo = new OrdTimeInfo();
			ProdProductBranch branch=pageService.getProdBranchByProdBranchId(timeInfo.getProductId());
			
			ordTimeInfo.setProductBranchId(branch.getProdBranchId());
			ordTimeInfo.setProductId(branch.getProductId());
			item.setProductBranchId(branch.getProdBranchId());
			item.setProductId(branch.getProductId());
			ordTimeInfo.setQuantity(timeInfo.getQuantity());
			try{
			ordTimeInfo.setVisitTime(timeInfo.getVisitDate());
			}catch(Exception ex){
				StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), ex);
			}
			ordTimeInfoList.add(ordTimeInfo);
		}
		item.setTimeInfoList(ordTimeInfoList);
		item.setQuantity(getHotelRoomQuantity(buyInfo.getTimeInfo()));
		item.setVisitTime(getSigelRoomFistVisitDate(buyInfo.getTimeInfo()));
		return item;
	}
	
	private int getHotelRoomQuantity(List<TimeInfo> timeInfoList){
		int quantity =0;
		for (TimeInfo timeInfo : timeInfoList) {
			quantity+=timeInfo.getQuantity();
		}
		return quantity;
	}
	
	private Date getSigelRoomFistVisitDate(List<TimeInfo> timeInfoList){
		List<Date> dateList = new ArrayList<Date>();
		for (TimeInfo timeInfo : timeInfoList) {
			try {
				dateList.add(timeInfo.getVisitDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		java.util.Collections.sort(dateList);
		return dateList.get(0);
		
	}
	/**
	 * @param pageService the pageService to set
	 */
	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}
	public void setPlacePageService(PlacePageService placePageService) {
		this.placePageService = placePageService;
	}

	public void setHotelTrafficInfoService(
			HotelTrafficInfoService hotelTrafficInfoService) {
		this.hotelTrafficInfoService = hotelTrafficInfoService;
	}
	
}
