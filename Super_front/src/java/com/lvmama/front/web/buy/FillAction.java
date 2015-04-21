package com.lvmama.front.web.buy;

import com.lvmama.comm.bee.po.prod.*;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.bee.vo.view.ViewProdProduct;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponPointChange;
import com.lvmama.comm.pet.po.prod.ProdAssemblyPoint;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.service.mark.MarkCouponPointChangeService;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.place.PlaceFlightService;
import com.lvmama.comm.pet.service.prod.ProductHeadQueryService;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.comm.pet.service.user.IReceiverUserService;
import com.lvmama.comm.pet.vo.mark.ProductWithCouponList;
import com.lvmama.comm.utils.*;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.vo.*;
import com.lvmama.front.web.BaseAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@ParentPackage("json-default")
//@ResultPath("/")
@Results( {		
		@Result(name = "overstock", location = "/WEB-INF/pages/buy/overstock.ftl", type = "freemarker"),
		@Result(name = "home", location = "http://www.lvmama.com", type = "redirect"),
		@Result(name = "invoice", location = "/WEB-INF/pages/buy/invoice.ftl"),
		@Result(name = "fill", location = "/WEB-INF/pages/buy/201107/fill.ftl", type = "freemarker"),
 		@Result(name = "201107fillRouteTravel", location = "/WEB-INF/pages/buy/201107/fillRouteTravel.ftl", type = "freemarker"),
 		@Result(name = "201107fillTicketTravel", location = "/WEB-INF/pages/buy/201107/fillTicketTravel.ftl", type = "freemarker"),
 		@Result(name="receivers_list",location="/WEB-INF/pages/buy/201107/receivers_list.ftl", type = "freemarker"),
		@Result(name="ticket",location = "/fill/ticket.do",type = "dispatcher")
})
public class FillAction extends BaseAction {
	private static final long serialVersionUID = -5881603533560394693L;
	private static final Log LOG = LogFactory.getLog(FillAction.class);
	private IReceiverUserService receiverUserService;
	private PageService pageService;
	private ViewBuyInfo buyInfo;
	private ProdProductBranch mainProdBranch;
	private ViewPage viewPage;
	private String mainProductNeedResourceConfirm;  //主产品是否需要资源确认
	private String mainProductEContract;  //主产品是否电子签约
	private List<ProdProductBranch> relatedProductList;
	private List<UsrReceivers> receiversList;
	private List<ProdAssemblyPoint> prodAssemblyPointList;  //上车地点
	private Map<String,List<ProdProductRelation>> additionalProduct;
	private MarkCouponService markCouponService;
	List<MarkCoupon> orderCouponList;
	private String hasCoupon;
	List<ProductWithCouponList> productCouponModelList;
	List<MarkCoupon> markCouponList;//活动数据
	HashMap<String,ProdProductBranch> productsList = new HashMap<String,ProdProductBranch>();
	private String branchIds;
	private String visitTime;
	/**
	 * 百度团购参数
	 */
	private String tn;
	private String baiduid;
	
	
	private List<String> firstTravellerInfoOptions; //第一游玩人的必填信息
	private List<String> travellerInfoOptions;  //游玩人的必填信息
	private List<String> contactInfoOptions;  //联系人的必填信息

	/**
	 * 自由行的已选中的产品信息.
	 */
	private Map<String,List<ViewProdProductBranch>> selfpackProduct;
	/** 取票人/联系人 */
	protected UsrReceivers contact = new UsrReceivers();
	private float marketPrice = 0f;
	private float sellPrice = 0f;
	/**
	 * 发票内容
	 */
	private List<CodeItem> invoiceDetails;
	private Long baoxianSelect=0L;
	/**填写游玩人数量*/
	private long fillTravellerNum ;
	private List<Date> visitDate = new ArrayList<Date>();	
	/**退出说明*/
	private String refundContent;
	private String provinceId;
	private List<ComCity> cityList;
	private List<UsrReceivers> usrReceiversList = new ArrayList<UsrReceivers>();
	private UsrReceivers usrReceivers = new UsrReceivers();
	private PlaceCityService placeCityService;
	
	private ProductHeadQueryService productServiceProxy;
	private PlaceFlightService placeFlightService;
	//积分兑换优惠券服务
	private MarkCouponPointChangeService markCouponPointChangeService;
	private MarkCouponPointChange markCouponPointChange;
	private Long currentUserPoint;
    protected CashAccountVO moneyAccount;
    transient CashAccountService cashAccountService;

    /**
     * 优惠是否有效
     */
    private String couponEnabled = "Y";

	/**
	 * 
	 * 产品详情页预定产品的实现
	 * 此处的原操作方法excecute()被注释
	 * 
	 * @since 201107
	 * @author Brian
	 */
	@Action("/buy/fill")
	public String execute() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("当前用户id:" + getUserId());
		}
		
		if (null == buyInfo||buyInfo.getProdBranchId()==null) {
			LOG.error("提交的buyInfo数据信息不全!跳转回主站.");
			return "home";
		}
		ProdProductBranch prodProductBranch = pageService.getProdBranchByProdBranchId(buyInfo.getProdBranchId());
		if(prodProductBranch == null) {
			LOG.error("类别不存在");
			return "home";
		}
		
		boolean isAperiodic = prodProductBranch.getProdProduct().IsAperiodic();
		//不定期产品用最后一天有效期做校验
		if(isAperiodic) {
			mainProdBranch=productServiceProxy.getProdBranchDetailByProdBranchId(buyInfo.getProdBranchId(),prodProductBranch.getValidEndTime());
		} else {
			//现在传入的为产品类别ID.为保证前台参数传的简单性,自己计算对应的产品ID.
			mainProdBranch=productServiceProxy.getProdBranchDetailByProdBranchId(buyInfo.getProdBranchId(),buyInfo.getVisitDate());
		}
		if(mainProdBranch==null){
			LOG.error("类别不存在");
			return "home";
		}
		if(mainProdBranch.getProdProduct().isTraffic()&&!Constant.SUB_PRODUCT_TYPE.TRAIN.name().equals(mainProdBranch.getProdProduct().getSubProductType())){
			LOG.error("只有火车票可售");
			return "home";
		}
		Map<String, Object> data = pageService.getProdCProductInfo(prodProductBranch.getProductId(), false);
		if (data.size() == 0) {
			return "home";
		}
		List<ProdProductBranch> branchList = (List<ProdProductBranch>) data.get("prodProductBranchList");
		if(branchList.isEmpty()) {
			return "home";
		}
		if(mainProdBranch.getProdProduct().getProductType().equals("TICKET")){
			return "ticket";
		}
		if(isAperiodic) {
			//目的地、搜索、频道跳转来的连接可能没有放置数量，取最小预定量填充
			if(buyInfo.getBuyNum().isEmpty()) { 
				Map<String, Integer> numMap = new HashMap<String, Integer>();
				for (ProdProductBranch b : branchList) {
					Integer num = b.getMinimum() < 1 ? 1 : Integer.parseInt(b.getMinimum().toString());
					numMap.put("product_" + b.getProdBranchId(), num);
				}
				buyInfo.setBuyNum(numMap);
			}
		}
		
		if(mainProdBranch.getProdProduct().isHotel()){
			if(Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT.name().equals(mainProdBranch.getProdProduct().getSubProductType())) {
				try{
					buyInfo.setDays(((ProdHotel)(mainProdBranch.getProdProduct())).getDays().intValue());
				}catch(NullPointerException ex){
					buyInfo.setDays(1);
				}
			} else if(mainProdBranch.getProdProduct().isSingleRoom() && isAperiodic) {
				buyInfo.setDays(1);
			}
		}
		buyInfo.setProductId(mainProdBranch.getProductId());
		
		viewPage = (ViewPage) data.get("viewPage");
		
		ProdCProduct prodCProduct = (ProdCProduct) data.get("prodCProduct");
		initOptionInfo(prodCProduct.getProdProduct());
		
		mainProductEContract = "false";
		if (null != prodCProduct.getProdRoute()) {
			mainProductEContract = prodCProduct.getProdRoute().isEContract() ? "true" : "false";
		}
		if(!prodCProduct.getProdProduct().isSellable()){
			LOG.error("当前产品"+buyInfo.getProductId()+" 已经过了上下线时间"); 
			return "home";
		}else if(!prodCProduct.getProdProduct().isOnLine()){
			LOG.error("当前产品"+buyInfo.getProductId()+"未上线online="+prodCProduct.getProdProduct().getOnLine());
			return "home";
		}else if(!productServiceProxy.isSellProductByChannel(buyInfo.getProductId(), buyInfo.getChannel())){
			LOG.error("当前产品"+buyInfo.getProductId()+"不能在"+buyInfo.getChannel()+"渠道销售");
			return "home";
		}else if(!pageService.checkDateCanSale(buyInfo.getProductId(),buyInfo.getVisitDate())){ 
			LOG.error("当前产品"+buyInfo.getProductId()+"游玩时间"+DateUtil.getDateTime("yyyy-MM-dd", buyInfo.getVisitDate())+"有时间限制"); 
			return "home"; 
		}
		if("您对订单的特殊要求".equals(buyInfo.getUserMemo())){
			buyInfo.setUserMemo(null);
		}
		if(prodCProduct.getProdProduct().hasSelfPack()){
			Map<Long,TimeInfo> map=buyInfo.getOrdItemProdMap();
			if(map.isEmpty()){
				LOG.error("没有选中自由行当中的产品列表");
				return "home";
			}
			selfpackProduct=new HashMap<String, List<ViewProdProductBranch>>();
			for(Long key:map.keySet()){
				TimeInfo ti=map.get(key);
				try{
					ProdProductBranch branch=productServiceProxy.getProdBranchDetailByProdBranchId(ti.getProdBranchId(), ti.getVisitDate());
					if(branch!=null){
						String productType=branch.getProdProduct().getProductType();
						List<ViewProdProductBranch> list=null;
						if(selfpackProduct.containsKey(productType)){
							list=selfpackProduct.get(productType);
						}else{
							list=new ArrayList<ViewProdProductBranch>();
						}
						if(branch.getProdProduct().isTraffic()){
							ProdTraffic pt=(ProdTraffic)branch.getProdProduct();
							if(pt.getGoFlightId()!=null){
								pt.setGoFlight(placeFlightService.queryPlaceFlightDetail(pt.getGoFlightId()));
							}
							if(pt.hasRound()&&pt.getBackFlightId()!=null){
								pt.setBackFlight(placeFlightService.queryPlaceFlightDetail(pt.getBackFlightId()));
							}
						}
						ViewProdProductBranch vppb=new ViewProdProductBranch();
						BeanUtils.copyProperties(branch,vppb);
						vppb.setTimeInfo(ti);
						list.add(vppb);
						selfpackProduct.put(productType, list);
						
						if(!BooleanUtils.toBoolean(mainProductNeedResourceConfirm)){//如果还没有设置为true时就对后续的产品都做判断.
							boolean resourceConfirm = pageService.isResourceConfirm(branch.getProdBranchId(), ti.getVisitDate());						
							mainProductNeedResourceConfirm = BooleanUtils.toStringTrueFalse(resourceConfirm);
						}
					}
				}catch(ParseException ex){					
				}
			}
		}else{
			if(ProductUtil.hasQueryBranchs(mainProdBranch.getProdProduct().getProductType())){
				relatedProductList=this.productServiceProxy.getProdBranchList(mainProdBranch.getProductId(), mainProdBranch.getProdBranchId(), buyInfo.getVisitDate());				
			}else{
				relatedProductList=Collections.emptyList();
			}
			//门票类，酒店类，其他类的产品属于一种订购方式
			if (mainProdBranch.getProdProduct().isTicket() || mainProdBranch.getProdProduct().isHotel() || mainProdBranch.getProdProduct().isOther()) {
				//酒店类的产品的特殊处理(非不定期)
				if (mainProdBranch.getProdProduct().isHotel() && !mainProdBranch.getProdProduct().IsAperiodic()) {
					if(!processingHotel()){
						return "overstock";
					}
				} else {
					if (LOG.isDebugEnabled()) {
						LOG.debug("门票类的销售产品，跳转至相应的订单页面");
					}
				}
			} else {
				//线路类的产品属于一种订购方式
				if (LOG.isDebugEnabled()) {
					LOG.debug("线路类的销售产品，跳转至相应的订单页面");
				}
			}
			//不定期默认为不需要资源审核
			if(isAperiodic) {
				mainProductNeedResourceConfirm = Constant.TRUE_FALSE.FALSE.name();
			} else {
				visitDate.add(buyInfo.getVisitDate());
				for (int i = 0; i < visitDate.size(); i++) {
					boolean resourceConfirm = pageService.isResourceConfirm(buyInfo.getProdBranchId(), visitDate.get(i));
					mainProductNeedResourceConfirm = BooleanUtils.toStringTrueFalse(resourceConfirm);
					if(resourceConfirm){
						break;
					}
				}
			}
		}
		
		//关联的附加商品.
		List<ProdProductRelation> relateList = null;
		//不定期暂时不做附加产品
		if(!mainProdBranch.getProdProduct().IsAperiodic()) {
			//关联的附加商品.
			relateList = this.productServiceProxy.getRelatProduct(
					mainProdBranch.getProductId(), buyInfo.getVisitDate());
			initAditionalProduct(relateList);
		}
		if(StringUtils.isNotEmpty(getUserId())){
			receiversList = receiverUserService.loadUserReceiversByUserId(getUserId());
		}
		
		if ("true".equals(mainProdBranch.getProdProduct().getPayToLvmama())) {
			//List<String> idsList = new ArrayList<String>();
			List<Long> idsList = new ArrayList<Long>();
			//mod by ljp 20120518
			List<String> subProductTypes = new ArrayList<String>();
			idsList.add(mainProdBranch.getProductId());
			subProductTypes.add(mainProdBranch.getProdProduct().getSubProductType());
			if(!mainProdBranch.getProdProduct().IsAperiodic()) {
				for (ProdProductRelation relateProduct: relateList) {
					if(idsList.contains(relateProduct.getRelatProductId().toString())){
						idsList.add(relateProduct.getRelatProductId());
						subProductTypes.add(relateProduct.getRelationProduct().getSubProductType());
					}
				}
			}
			if(!mainProdBranch.getProdProduct().hasSelfPack()){//超级自由行不使用优惠券
		    	Map<String,Object> map = new HashMap<String,Object>();
		    	map.put("productIds", idsList);
		    	map.put("subProductTypes", subProductTypes);
		    	map.put("withCode", "false");//只取优惠活动
				orderCouponList = markCouponService.selectAllCanUseAndProductCanUseMarkCoupon(map);
				filterCouponId(orderCouponList);//屏蔽指定的优惠活动批次号
				this.hasCoupon = "true";
			
				if(this.getUser() != null){//只有登录情况才显示积分兑换优惠
					//获取积分兑换优惠券规则
					markCouponPointChange = markCouponPointChangeService.selectBySubProductType(mainProdBranch.getProdProduct().getSubProductType());
					if (null != markCouponPointChange) {
						MarkCoupon markCoupon = markCouponService.selectMarkCouponByPk(markCouponPointChange.getCouponId());
						if(null == markCoupon || !"true".equals(markCoupon.getValid()) || ("FIXED".equals(markCoupon.getValidType()) && markCoupon.isOverDue())){
							markCouponPointChange = null;
						}
						currentUserPoint = this.getUser().getPoint();
					}
				}
			}
		}
		
		this.initReceiversList();
		//非不定期不计算最晚取消时间
		if(!mainProdBranch.getProdProduct().hasSelfPack()  && !mainProdBranch.getProdProduct().IsAperiodic()){
			initRefundContent(prodCProduct.getProdProduct().hasSelfPack());
		}
		//五周年活动不适用优惠券
		if(ActivityUtil.getInstance().checkActivityIsValid(Constant.ACTIVITY_FIVE_YEAR)){
			mainProdBranch.getProdProduct().setCouponAble("false");
			mainProdBranch.getProdProduct().setCouponActivity("false");
		}

        moneyAccount = cashAccountService.queryMoneyAccountByUserNo(this.getUserId());

        couponEnabled = Constant.initCouponEnabled(mainProdBranch.getProdProduct().getProductId())?"Y":"N";

 		return "fill";
	}

	/**
	 * 初始化游玩人和联系人的必填信息
	 * @param product 购买主产品
	 */
	private void initOptionInfo(ProdProduct product) {
		//联系人的手机和姓名必填
		contactInfoOptions = new ArrayList<String>();
		contactInfoOptions.add(Constant.TRAVELLER_INFO_OPTIONS.NAME.getCode());
		contactInfoOptions.add(Constant.TRAVELLER_INFO_OPTIONS.MOBILE.getCode());
		
		travellerInfoOptions = product.getTravellerInfoOptionsList();
		firstTravellerInfoOptions = product.getFirstTravellerInfoOptionsList();
		
		if (null == travellerInfoOptions) {
			travellerInfoOptions = new ArrayList<String>(0);
		}
		if (null == firstTravellerInfoOptions) {
			firstTravellerInfoOptions = new ArrayList<String>(0);
		}
		
		if (null != product.getContactInfoOptionsList()) {
			contactInfoOptions.addAll(product.getContactInfoOptionsList());
		}
	}
	
	/**
	 * 计算退款说明中的最晚取消时间
	 * @param isSelfPack
	 */
	private void initRefundContent(Boolean isSelfPack) {
		List<Long> branchIdList = new ArrayList<Long>();
		Date lastCancelTime = null;
		if (!isSelfPack) {
			Long branchId = null;
			if(mainProdBranch != null) {
				branchId = mainProdBranch.getProdBranchId();
			} else {
				branchId = Long.parseLong((Arrays.asList(branchIds.split(",")).get(0)));
			}
			ProdProductBranch prodProductBranch = pageService.getProdBranchByProdBranchId(branchId);
			//不定期不做此操作
			if(prodProductBranch != null && prodProductBranch.getProdProduct().IsAperiodic()) {} 
			else {
				// ACTIONI第一次执行时计算最晚取消时间
				if (mainProdBranch != null) {
					for (ProdProductBranch branch : relatedProductList) {
					// 防止数目为空的参与计算
					Integer buyNum = buyInfo.getBuyNum().get("product_" + branch.getProdBranchId());
					if (buyNum != null && buyNum > 0) {
						branchIdList.add(branch.getProdBranchId());
					}
				 }
					branchIdList.add(mainProdBranch.getProdBranchId());
					lastCancelTime = this.productServiceProxy.getProductsLastCancelTime(branchIdList, this.buyInfo.getVisitDate());
				} else {// 修改产品份数为零时动态获取的最晚取消时间
					for (String branchIdStr : Arrays.asList(branchIds.split(","))) {
						branchIdList.add(Long.valueOf(branchIdStr));
					}
					lastCancelTime = this.productServiceProxy.getProductsLastCancelTime(branchIdList, DateUtil.getDateByStr(visitTime, "yyyy-MM-dd"));
				}
			}
		}
		if (lastCancelTime != null) {
			this.refundContent = "您可在" + DateUtil.getFormatDate(lastCancelTime, "yyyy年MM月dd日 HH:mm") + "前致电驴妈妈客服中心（1010-6060）对该订单进行修改或取消";
		}

	}
	
	@Action("/buy/refundContent")
	public void findRefundContent() {
		JSONResult result = new JSONResult();
		initRefundContent(false);
		result.put("refundContent", refundContent);
		result.output(this.getResponse());
	}
	
	@Action("/buy/fillTraveler")
	public String travelerFill() {
		ProdProductBranch prodProductBranch = pageService.getProdBranchByProdBranchId(buyInfo.getProdBranchId());
		if(prodProductBranch == null) {
			LOG.error("类别不存在");
			return "home";
		}
		Map<String, Object> data = pageService.getProdCProductInfo(buyInfo.getProductId(), false);
		if (data.size() == 0) {
			return "home";
		}
		List<ProdProductBranch> branchList = (List<ProdProductBranch>) data.get("prodProductBranchList");
		if(branchList.isEmpty()) {
			return "home";
		}
		//非不定期产品才需校验主类别
		if(!prodProductBranch.getProdProduct().IsAperiodic()) {
			//现在传入的为产品类别ID.为保证前台参数传的简单性,自己计算对应的产品ID.
			mainProdBranch=productServiceProxy.getProdBranchDetailByProdBranchId(buyInfo.getProdBranchId(),buyInfo.getVisitDate());
			if(mainProdBranch==null){
				LOG.error("类别不存在");
				return "home";
			}
		} else {//不定期取第一个可售类别为主类别
			mainProdBranch = (ProdProductBranch) data.get("prodBranch");
		}
		receiversList = receiverUserService.loadUserReceiversByUserId(getUserId());
		
		ProdProduct prodProduct = ((ProdCProduct) data.get("prodCProduct")).getProdProduct();
		if (null == prodProduct) {
			return "home";
		}
		
		initOptionInfo(prodProduct);
		
		
		fillTravellerNum = 0L;
		if (baoxianSelect != null || baoxianSelect > 0) {//如果选择保险的时候就按产品信息读取需要填写的游玩人数
			if(mainProdBranch.getProdProduct().hasSelfPack()){
				fillTravellerNum=buyInfo.getTotalQuantity();
			}else{
				//含有必填的数据(且不是酒店)
				//酒店提交的数据结构不支持目前关于有玩人数的计算
				//主产品的人数
				if (buyInfo.getBuyNum().containsKey("product_" + mainProdBranch.getProdBranchId())) {
					fillTravellerNum = (mainProdBranch.getAdultQuantity() + mainProdBranch.getChildQuantity()) * buyInfo.getBuyNum().get("product_" + mainProdBranch.getProdBranchId());
				}  
				//关联产品
				relatedProductList = this.productServiceProxy.getProdBranchList(
						mainProdBranch.getProductId(),mainProdBranch.getProdBranchId(), buyInfo.getVisitDate());
				for (ProdProductBranch vpp : relatedProductList) {
					if (!vpp.hasAdditional() && buyInfo.getBuyNum().containsKey("product_" + vpp.getProdBranchId())) {
						fillTravellerNum += (vpp.getAdultQuantity() + vpp.getChildQuantity()) * buyInfo.getBuyNum().get("product_" + vpp.getProdBranchId());
					}
				}
				
			}
		}
		
		fillTravellerNum = fillTravellerNum == 0 ? 1 : fillTravellerNum;
		
		//保险产品强制填写投保人信息
		if (baoxianSelect > 0) {
			if (!firstTravellerInfoOptions.contains("NAME")) {
				firstTravellerInfoOptions.add("NAME");
			}
			if (!firstTravellerInfoOptions.contains("CARD_NUMBER")) {
				firstTravellerInfoOptions.add("CARD_NUMBER");
			}
			if (!firstTravellerInfoOptions.contains("MOBILE")) {
				firstTravellerInfoOptions.add("MOBILE");
			}
			if (!travellerInfoOptions.contains("NAME")) {
				travellerInfoOptions.add("NAME");
			}
			if (!travellerInfoOptions.contains("CARD_NUMBER")) {
				travellerInfoOptions.add("CARD_NUMBER");
			}
			if (!travellerInfoOptions.contains("MOBILE")) {
				travellerInfoOptions.add("MOBILE");
			}			
		}
		
		prodAssemblyPointList = this.productServiceProxy.getAssemblyPoints(buyInfo.getProductId());
		getUser();
		
		this.initReceiversList();
		
		//主产品是门票不需要必填信息
//		if ((prodProduct.isTicket() 
//				|| prodProduct.isHotel()
//				|| (prodProduct.isRoute() && Constant.SUB_PRODUCT_TYPE.FREENESS.name().equals(prodProduct.getSubProductType()))) 
//			&& this.travellerInfoOptions.isEmpty()) {
//			return "201107fillTicketTravel";
//		}
		return "201107fillRouteTravel";
	}
	

	public void buildCouponInfos(ViewProdProduct mainProduct,
			List<ViewProdProduct> relatedProductList) {
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("productId", Long.valueOf(mainProduct.getProductId()));
    	map.put("subProductType", mainProduct.getSubProductType());
    	List<MarkCoupon> productCouponList = markCouponService.selectProductCanUseMarkCoupon(map);
		
		if (productCouponList == null) {
			productCouponList = new ArrayList<MarkCoupon>();
		}

		this.productCouponModelList = new ArrayList<ProductWithCouponList>();
		ProductWithCouponList pwcl1 = new ProductWithCouponList();
		pwcl1.setProductId(mainProduct.getProductId());
		pwcl1.setProductName(mainProduct.getProductName());
		//pwcl1.setShortName(mainProduct.getShortName());
		pwcl1.setCouponList(productCouponList);
		this.productCouponModelList.add(pwcl1);

		for (ViewProdProduct viewProdProduct : relatedProductList) {
			if ("false".equals(viewProdProduct.getAdditional())) {
				//mob by ljp 20120518\
		    	map = new HashMap<String,Object>();
		    	map.put("productId", Long.valueOf(viewProdProduct.getProductId()));
		    	map.put("subProductType", viewProdProduct.getSubProductType());
		    	List<MarkCoupon> mcList = markCouponService.selectProductCanUseMarkCoupon(map);
				ProductWithCouponList pwcl2 = new ProductWithCouponList();
				pwcl2.setProductId(viewProdProduct.getProductId());
				pwcl2.setProductName(viewProdProduct.getProductName());
				//pwcl2.setShortName(viewProdProduct.getShortName());
				pwcl2.setCouponList(mcList);
				this.productCouponModelList.add(pwcl2);

			}
		}

		filterMarkCouponProd(this.productCouponModelList);// 过滤掉所有产品活动
		filterMarkCouponOrder(orderCouponList);// 过滤所有订单的活动

		for (ProductWithCouponList couponList : productCouponModelList) {
			if (couponList.isHasCoupon()) {
				this.hasCoupon = "true";
			}
		}
		

	}

	public void filterMarkCouponProd(List<ProductWithCouponList> pwclList) {
		for (int i = 0; i < pwclList.size(); i++) {
			ProductWithCouponList productWithCouponList = pwclList.get(i);
			for (int j = 0; j < productWithCouponList.getCouponList().size(); j++) {
				MarkCoupon mc = productWithCouponList.getCouponList().get(j);
				if (mc.getWithCode().equals("false")) {
					productWithCouponList.getCouponList().remove(i);
				}
			}
		}

	}

	
	/**
	 *屏蔽指定批次号活动
	 */
	public void filterCouponId(List<MarkCoupon> orderCouponList){
		String regex =Constant.getInstance().getProperty("FILTER_COUPONID");
		String[] couponIds = regex.split(",");
		if(couponIds.length>0){
			for (int j = 0; j < couponIds.length; j++) {
				for (int i = 0; i < orderCouponList.size(); i++) {
					MarkCoupon markCoupon = orderCouponList.get(i);
					if (markCoupon.getCouponId().toString().equals(couponIds[j])) {
						orderCouponList.remove(i);
					}
				}
			}
		}
		
	}
	
	public void filterMarkCouponOrder(List<MarkCoupon> orderCouponList) {
		for (int i = 0; i < orderCouponList.size(); i++) {
			MarkCoupon markCoupon = orderCouponList.get(i);
			if (markCoupon.getWithCode().equals("false")) {
				orderCouponList.remove(i);
			}
		}
	}
	
	/**
	 * 初始化数据并且过滤掉不能在前端销售的附加产品
	 * @param list
	 */
	private void initAditionalProduct(List<ProdProductRelation> list) {
		additionalProduct = new HashMap<String, List<ProdProductRelation>>();
		for (ProdProductRelation ppr:list) {
			//前台不可见的产品直接过滤掉
			if(!ppr.getBranch().hasVisible()){
				continue;
			}
			
			//如果是酒店相关的产品就需要判断能否加床
			//如果不能加床就过滤产品
			if (mainProdBranch.getProdProduct().isHotel()
					&& !mainProdBranch.hasExtraBedAble()
					&& ProductUtil.Hotel.EXTRABED.name().equals(
							ppr.getBranch().getBranchType())) {
				continue;
			}
			String subProductTypeStr=ppr.getSubProductTypeStr();
			
			List<ProdProductRelation> relates = additionalProduct.get(subProductTypeStr);
			if (relates != null) {
				relates.add(ppr);
			} else {
				relates = new ArrayList<ProdProductRelation>();
				relates.add(ppr);
				additionalProduct.put(subProductTypeStr,
						relates);
			}
		}
	}
	
	/**
	 * 对酒店类产品的深加工
	 * @return <code>false</code>无时间价格表时直接跳出
	 */
	private boolean processingHotel() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("酒店类的销售产品");
		}
		// 单房型的话，查询每天的价格
		if (mainProdBranch.getProdProduct().isSingleRoom()) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("单房型的酒店类产品");
			}
			try {
				productsList.put(buyInfo.getVisitTime(), mainProdBranch);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				sellPrice += mainProdBranch.getSellPriceYuan();
				marketPrice += mainProdBranch.getMarketPriceYuan();
				
				for (int i = 1; i < buyInfo.getDays(); i++) {
					String d = buyInfo.getSpecifyDate(i);
					Date date=sdf.parse(d);
					ProdProductBranch branch=productServiceProxy.getProdBranchDetailByProdBranchId(mainProdBranch.getProdBranchId(), date);
					if(branch==null){
						return false;
					}
					visitDate.add(date);
					marketPrice += branch.getMarketPriceYuan();
					sellPrice += branch.getSellPriceYuan();
					productsList.put(d, branch);
				}
//				this.invoiceDetails = CodeSet.getInstance().getCachedCodeList(
//						Constant.CODE_TYPE.INVOICE_CONTENT.name());
			} catch (ParseException pe) {
				StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), pe);
			}
		}
		return true;
	}	

	/**
	 * 检查是否超时
	 */
	@Action("/buy/doCheckSession")
	public void doCheckSession() {
		returnMessage(true);
	}

	/**
	 * 返回操作成功信息
	 */
	private void returnMessage(boolean flag) {
		try {
			if (flag) {
				this.getResponse().getWriter().write("{result:true}");
			} else {
				this.getResponse().getWriter().write("{result:false}");
			}
		} catch (Exception e) {
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
		}

	}

	public ViewBuyInfo getBuyinfo() {
		return buyInfo;
	}
	
	public ViewBuyInfo getBuyInfo() {
		return buyInfo;
	}

	public List<UsrReceivers> getReceiversList() {
		return receiversList;
	}
 
	/**
	 * @return the relatedProductList
	 */
	public List<ProdProductBranch> getRelatedProductList() {
		return relatedProductList;
	}

	/**
	 * @return the additionalProduct
	 */
	public Map<String, List<ProdProductRelation>> getAdditionalProduct() {
		return additionalProduct;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public List<ProductWithCouponList> getProductCouponModelList() {
		return productCouponModelList;
	}

	public void setProductCouponModelList(
			List<ProductWithCouponList> productCouponModelList) {
		this.productCouponModelList = productCouponModelList;
	}

	public List<MarkCoupon> getOrderCouponList() {
		return orderCouponList;
	}

	public void setOrderCouponList(List<MarkCoupon> orderCouponList) {
		this.orderCouponList = orderCouponList;
	}

	public String getHasCoupon() {
		return hasCoupon;
	}

	public void setHasCoupon(String hasCoupon) {
		this.hasCoupon = hasCoupon;
	}

	public void setBuyInfo(ViewBuyInfo buyInfo) {
		this.buyInfo = buyInfo;
	}
	public HashMap<String, ProdProductBranch> getProductsList() {
		return productsList;
	}
	
	public float getMarketPrice() {
		return marketPrice;
	}

	public float getSellPrice() {
		return sellPrice;
	}
 
	public List<CodeItem> getInvoiceDetails() {
		return invoiceDetails;
	}
	public UsrReceivers getContact() {
		return contact;
	}

	public void setContact(UsrReceivers contact) {
		this.contact = contact;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}
	
	public PageService getPageService() {
		return pageService;
	}

	public ViewPage getViewPage() {
		return viewPage;
	}

	public void setViewPage(ViewPage viewPage) {
		this.viewPage = viewPage;
	}

	public void setMainProductNeedResourceConfirm(
			String mainProductNeedResourceConfirm) {
		this.mainProductNeedResourceConfirm = mainProductNeedResourceConfirm;
	}

	public String getMainProductNeedResourceConfirm() {
		return mainProductNeedResourceConfirm;
	}

	public Long getBaoxianSelect() {
		return baoxianSelect;
	}

	public void setBaoxianSelect(Long baoxianSelect) {
		this.baoxianSelect = baoxianSelect;
	}

	public String getMainProductEContract() {
		return mainProductEContract;
	}

	public void setMainProductEContract(String mainProductEContract) {
		this.mainProductEContract = mainProductEContract;
	}

	public List<ProdAssemblyPoint> getProdAssemblyPointList() {
		return prodAssemblyPointList;
	}
 
	public List<String> getTravellerInfoOptions() {
		return travellerInfoOptions;
	}

	public List<String> getContactInfoOptions() {
		return contactInfoOptions;
	}

	public long getFillTravellerNum() {
		return fillTravellerNum;
	}

	public String getRefundContent() {
		return refundContent;
	}

	public void setRefundContent(String refundContent) {
		this.refundContent = refundContent;
	}

	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	
	/**
	 * 让页面联动，取城市信息
	 */
	@Action("/buy/citys")
	public void getCitys(){
		
		JSONResult result=new JSONResult();
		try{
			List<ComCity> list = placeCityService
					.getCityListByProvinceId(provinceId);
			
			
			result.put("list", converCity(list));
		}catch(Exception ex){
			
		}
		
		result.output(getResponse());
	}
	
	private JSONArray converCity(List<ComCity> list){
		JSONArray array=new JSONArray();
		if(CollectionUtils.isNotEmpty(list)){
			for(ComCity cc:list){
				JSONObject obj=new JSONObject();
				obj.put("cityId", cc.getCityId());
				obj.put("cityName", cc.getCityName());
				array.add(obj);
			}			
		}
		return array;
	}
	
	/**
	 * 加载订单邮寄地址信息.
	 * @return
	 */
	@Action("/buy/getReceivers")
	public void modifyAddress() {
		JSONResult result=new JSONResult();
		try{
			this.usrReceivers = this.receiverUserService.getRecieverByPk(this.usrReceivers.getReceiverId());
			if(!StringUtils.equals(usrReceivers.getUserId(),getUserId())){
				throw new Exception("没有权限操作");
			}
				
			if(StringUtils.isNotEmpty(usrReceivers.getProvince())){
				ComProvince cp=placeCityService.selectByProvinceName(usrReceivers.getProvince());
				if(cp!=null){				
					usrReceivers.setProvince(cp.getProvinceId());
					if(StringUtils.isNotEmpty(usrReceivers.getCity())){
						ComCity cc=placeCityService.selectCityByNameAndCity(cp.getProvinceId(),usrReceivers.getCity());
						if(cc!=null){
							usrReceivers.setCity(cc.getCityId());
						}
					}
					cityList=placeCityService.getCityListByProvinceId(cp.getProvinceId());
				}
			}
			result.put("citys", converCity(cityList));
			result.put("info", JSONObject.fromObject(usrReceivers));
			
		}catch(Exception ex){
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), ex);
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	/**
	 * 删除订单邮寄地址.
	 * @return
	 */
	@Action("/buy/removeAddress")
	public void removeAddress() {
		JSONResult result=new JSONResult();
		try{
			if(StringUtils.isEmpty(usrReceivers.getReceiverId())){
				throw new Exception("删除的地址不存在");
			}
			
			UsrReceivers usr=receiverUserService.getRecieverByPk(usrReceivers.getReceiverId());
			if(usr==null){
				throw new Exception("收件信息不存在");
			}
			
			if(StringUtils.isEmpty(getUserId())||!StringUtils.equals(usr.getUserId(), getUserId())){
				throw new Exception("您没有权限删除");
			}
			this.receiverUserService.delete(this.usrReceivers.getReceiverId());
		}catch(Exception ex){
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), ex);
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	/**
	 * 保存订单邮寄地址.
	 * @return
	 */
	@Action("/buy/confirmAddress")
	public void confirmAddress() {
		JSONResult result=new JSONResult();
		try{
			if(!isLogin()){
				throw new Exception("您没有登录不可以操作");
			}
			this.initUsrReceiversSaveAddress();
			UsrReceivers usr=null;
			if(StringUtils.isEmpty(usrReceivers.getReceiverId())){//为空时表示新加
				usr = new UsrReceivers();				
				UUIDGenerator gen = new UUIDGenerator();
				usr.setReceiverId(gen.generate().toString());
				usr.setUserId(this.getUserId());
				usr.setCreatedDate(new Date());
			}else{
				usr=receiverUserService.getRecieverByPk(usrReceivers.getReceiverId());
				if(usr==null){
					throw new Exception("收件信息不存在不可以编辑");
				}
				
				if(!StringUtils.equals(usr.getUserId(),getUserId())){
					throw new Exception("您修改的收件信息错误");
				}
			}
			usr.setAddress(this.usrReceivers.getAddress());
			usr.setMobileNumber(this.usrReceivers.getMobileNumber());
			usr.setReceiverName(this.usrReceivers.getReceiverName());
			usr.setPostCode(this.usrReceivers.getPostCode());			
			usr.setIsValid(UsrReceivers.VALID);			
			usr.setReceiversType(Constant.RECEIVERS_TYPE.ADDRESS.name());
			usr.setProvince(this.usrReceivers.getProvince());
			usr.setCity(this.usrReceivers.getCity());
			//新增地址操作.
			if (StringUtils.isEmpty(this.usrReceivers.getReceiverId())) {
				this.receiverUserService.insert(usr);
			} else {
			//修改地址操作.
				//logger.info(this.usrReceivers.getReceiverId());				
				this.receiverUserService.update(usr);
			}
		}catch(Exception ex){
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), ex);
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	@Action("/buy/loadReceiversList")	
	public String loadReceivers(){
		this.initReceiversList();
		return "receivers_list";
	}
	
	//第一次查询显示最近下单所使用的订单邮寄地址.
	@Action("/buy/loadReceiversList0")
	public String firstTimeReceivers() {
		UsrReceivers temp = new UsrReceivers();
		temp.setAddress("12345");
		temp.setProvince("provicesss");
		temp.setCity("cityaaa");
		temp.setMobileNumber("1234567654");
		temp.setReceiverId("1234556");
		this.usrReceiversList.add(temp);
		
		return "receivers_list";
	}
	
	/**
	 * 初始化usrReceivers对象的省份(province)与城市(city)属性值,将code码转换为name.
	 */
	private void initUsrReceiversSaveAddress() {
		String province=usrReceivers.getProvince();
		String city=usrReceivers.getCity();
		if(StringUtils.isNotEmpty(province)){//用编号转名字
			ComProvince cp=placeCityService.selectByPrimaryKey(province);
			if(cp!=null){
				usrReceivers.setProvince(cp.getProvinceName());
				
				ComCity cc=placeCityService.selectCityByPrimaryKey(city);
				if(cc!=null){
					usrReceivers.setCity(cc.getCityName());
				}
			}
		}
	}
	
	/**
	 * 初始化订单邮寄地址.
	 */
	private void initReceiversList(){
		//未登录状态地址列表初始化为空列表.
		if (this.getUserId() == null || this.getUserId().trim().equals("")) {
			this.usrReceiversList = new ArrayList<UsrReceivers>(0);
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", getUserId());
		params.put("receiversType", Constant.RECEIVERS_TYPE.ADDRESS.name());
		this.usrReceiversList = receiverUserService.loadRecieverByParams(params);
	}
	//是否有更多的订单邮寄地址.
	public boolean isMorePersonInfo() {
		return true;
	}
	
	public List<UsrReceivers> getUsrReceiversList() {
		return usrReceiversList;
	}

	public void setUsrReceiversList(List<UsrReceivers> usrReceiversList) {
		this.usrReceiversList = usrReceiversList;
	}

	public void setReceiverUserService(IReceiverUserService receiverUserService) {
		this.receiverUserService = receiverUserService;
	}

	public UsrReceivers getUsrReceivers() {
		return usrReceivers;
	}
	public void setUsrReceivers(UsrReceivers usrReceivers) {
		this.usrReceivers = usrReceivers;
	}
	public List<ComProvince> getProvinceList() {
		return placeCityService.getProvinceList();
	}
	
	/**
	 * @return the mainProdBranch
	 */
	public ProdProductBranch getMainProdBranch() {
		return mainProdBranch;
	}

	/**
	 * 是否存在酒店.
	 * 该值只在超级自由行来使用.
	 * @return
	 */
	public boolean hasSelfPackHotel(){
		return selfpackProduct!=null&&selfpackProduct.containsKey(Constant.PRODUCT_TYPE.HOTEL.name());
	}

	public boolean hasSelfPackOther(){
		return selfpackProduct!=null&&(selfpackProduct.containsKey(Constant.PRODUCT_TYPE.ROUTE.name())||selfpackProduct.containsKey(Constant.PRODUCT_TYPE.TICKET.name()));
	}
	public boolean hasSelfPackRoute(){
		return selfpackProduct!=null&&(selfpackProduct.containsKey(Constant.PRODUCT_TYPE.ROUTE.name()));
	}
	public boolean hasSelfPackTicket(){
		return selfpackProduct!=null&&(selfpackProduct.containsKey(Constant.PRODUCT_TYPE.TICKET.name()));
	}
	public boolean hasSelfPackTraffic(){
		return selfpackProduct!=null&&(selfpackProduct.containsKey(Constant.PRODUCT_TYPE.TRAFFIC.name()));
	}
	/**
	 * @return the selfpackProduct
	 */
	public Map<String, List<ViewProdProductBranch>> getSelfpackProduct() {
		return selfpackProduct;
	}

	public void setBranchIds(String branchIds) {
		this.branchIds = branchIds;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public void setProductServiceProxy(ProductHeadQueryService productServiceProxy) {
		this.productServiceProxy = productServiceProxy;
	}

	public void setPlaceCityService(PlaceCityService placeCityService) {
		this.placeCityService = placeCityService;
	}

	public void setMarkCouponPointChangeService(
			MarkCouponPointChangeService markCouponPointChangeService) {
		this.markCouponPointChangeService = markCouponPointChangeService;
	}

	public MarkCouponPointChange getMarkCouponPointChange() {
		return markCouponPointChange;
	}

	public Long getCurrentUserPoint() {
		return currentUserPoint;
	}

	public void setPlaceFlightService(PlaceFlightService placeFlightService) {
		this.placeFlightService = placeFlightService;
	}

	public List<String> getFirstTravellerInfoOptions() {
		return firstTravellerInfoOptions;
	}


	public String getBaiduid() {
		return baiduid;
	}

	public void setBaiduid(String baiduid) {
		this.baiduid = baiduid;
	}

	public String getTn() {
		return tn;
	}

	public void setTn(String tn) {
		this.tn = tn;
	}

    public CashAccountVO getMoneyAccount() {
        return moneyAccount;
    }

    public void setMoneyAccount(CashAccountVO moneyAccount) {
        this.moneyAccount = moneyAccount;
    }

    public CashAccountService getCashAccountService() {
        return cashAccountService;
    }

    public void setCashAccountService(CashAccountService cashAccountService) {
        this.cashAccountService = cashAccountService;
    }

    public String getCouponEnabled() {
        return couponEnabled;
    }

    public void setCouponEnabled(String couponEnabled) {
        this.couponEnabled = couponEnabled;
    }
}
