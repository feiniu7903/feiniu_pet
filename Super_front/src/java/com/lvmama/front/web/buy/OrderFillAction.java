package com.lvmama.front.web.buy;
import com.lvmama.comm.bee.po.prod.*;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponPointChange;
import com.lvmama.comm.pet.po.mark.MarkCouponProduct;
import com.lvmama.comm.pet.po.prod.ProdAssemblyPoint;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.service.businessCoupon.ProdSeckillRuleService;
import com.lvmama.comm.pet.service.mark.MarkCouponPointChangeService;
import com.lvmama.comm.pet.service.mark.MarkCouponProductService;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.mark.MarkCouponUserService;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.place.PlaceFlightService;
import com.lvmama.comm.pet.service.prod.ProductHeadQueryService;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.comm.pet.service.user.IReceiverUserService;
import com.lvmama.comm.utils.*;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.vo.*;
import com.lvmama.front.web.BaseAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单填写页Action
 * @author Administrator
 *
 */
@Results( {		
	@Result(name = "input", location = "http://www.lvmama.com", type = "redirect"),
	@Result(name = "fill", location = "/WEB-INF/pages/order/fill.ftl", type = "freemarker")
})
public class OrderFillAction extends BaseAction {
	
	private static final long serialVersionUID = -5881603533560394693L;
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(OrderFillAction.class);
	// 页面传递属性区
	private ViewBuyInfo buyInfo;
	private ProdProductBranch mainProdBranch;
    private List<ProdProductBranch> relatedProductList;
    private String productId;
	// 远程调用服务区
	private PageService pageService;

	private ProductHeadQueryService productServiceProxy;
	private IReceiverUserService receiverUserService;
	//优惠相关服务
	private MarkCouponService markCouponService;
	private MarkCouponPointChangeService markCouponPointChangeService;
	private MarkCouponPointChange markCouponPointChange;
	private Long currentUserPoint;
	private String hasCoupon;
	private List<MarkCoupon> orderCouponList;
	private MarkCouponUserService markCouponUserService;
	private MarkCouponProductService markCouponProductService;
	private List<UserCouponDTO> userCouponList = new ArrayList<UserCouponDTO>();

	private List<ProdAssemblyPoint> prodAssemblyPointList;  //上车地点
	private Map<String,List<ProdProductRelation>> additionalProduct;
	private String mainProductNeedResourceConfirm;  //主产品是否需要资源确认
	private String mainProductEContract;  //主产品是否电子签约
	private Map<String,String> reserveNoticeMap = new HashMap<String, String>();
	private ViewPage viewPage;
	
	private List<UsrReceivers> receiversList;
	/**
	 * 自由行的已选中的产品信息.
	 */
	private Map<String,List<ViewProdProductBranch>> selfpackProduct;
	HashMap<String,ProdProductBranch> productsList = new HashMap<String,ProdProductBranch>();
	private PlaceFlightService placeFlightService;
	
	/** 取票人/联系人 */
	protected UsrReceivers contact = new UsrReceivers();
	private float marketPrice = 0f;
	private float sellPrice = 0f;
	/**
	* 填写游玩人数量
	*/
	private List<Date> visitDate = new ArrayList<Date>();	
	private long fillTravellerNum ;
	
	private List<String> firstTravellerInfoOptions; //第一游玩人的必填信息
	private List<String> travellerInfoOptions;  //游玩人的必填信息
	private List<String> contactInfoOptions;  //联系人的必填信息
	


	private Long baoxianSelect=0L;
	/**退出说明*/
	private String refundContent;
	private String provinceId;
	private List<ComCity> cityList;
	private List<UsrReceivers> usrReceiversList = new ArrayList<UsrReceivers>();
	private UsrReceivers usrReceivers = new UsrReceivers();
	private PlaceCityService placeCityService;
	private ProdProductBranchService prodProductBranchService;

	private ProdSeckillRuleService prodSeckillRuleService;


  protected CashAccountVO moneyAccount;
  transient CashAccountService cashAccountService;

    /**
     * 优惠是否有效
     */
    private String couponEnabled = "Y";

 	/**
	 * 订单填写页的统一入口，
	 * @return 不同的产品下单url
	 * 订单填写页的统一入口，根据所售卖的产品不同跳转到不同的页面上去
	 */
	@Action("/fill/ticket")
	public String execute() {
 		return initFillOrderData();
	}
	
	public void setReceiverUserService(IReceiverUserService receiverUserService) {
		this.receiverUserService = receiverUserService;
	}

	public String ticketOrderFill() {
		return initFillOrderData();
	}
	
	public String aroundOrderFill() {
		return initFillOrderData();
	}
	
	public String domesticOrderFill() {
		return initFillOrderData();
	}
	
	public String abroadOrderFill() {
		return initFillOrderData();
	}
	
	public String hotelOrderFill() {
		return initFillOrderData();
	}
	
	@SuppressWarnings("unchecked")
	private String initFillOrderData() {
		if(!isLogin()){
			LOG.error("用户未登录");
			return "input";
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("当前用户id:" + getUserId());
		}
		if (null == buyInfo||buyInfo.getProdBranchId()==null) {
			LOG.error("提交的buyInfo数据信息不全!跳转回主站.");
			return "input";
		}
		if(null != this.productId && !this.productId.equals("")){
			if(NumberUtils.isDigits(this.productId)){
				buyInfo.setProductId(Long.parseLong(this.productId));
			}
		}
		ProdProductBranch prodProductBranch = pageService.getProdBranchByProdBranchId(buyInfo.getProdBranchId());
		if(prodProductBranch == null) {
			LOG.error("类别不存在");
			return "input";
		}
		
		
		boolean isAperiodic = prodProductBranch.getProdProduct().IsAperiodic();
		//不定期产品用最后一天有效期做校验
		if(isAperiodic) {
			mainProdBranch=productServiceProxy.getProdBranchDetailByProdBranchId(buyInfo.getProdBranchId(),prodProductBranch.getValidEndTime());
		} else {
			/**
			 * 没有游玩日期，给个默认的最近日期
			 * @author nixianjun 2014.2.25
			 */
	        if(null==buyInfo.getVisitDate()){
	        	Date date=prodProductBranchService.selectNearBranchTimePriceByBranchId(buyInfo.getProdBranchId());
	        	if(null!=date)buyInfo.setVisitTime(DateUtil.formatDate(date, "yyyy-MM-dd"));
	        }
	      //现在传入的为产品类别ID.为保证前台参数传的简单性,自己计算对应的产品ID.
	      mainProdBranch=productServiceProxy.getProdBranchDetailByProdBranchId(buyInfo.getProdBranchId(),buyInfo.getVisitDate());
	        /**
	         * 2014-4-30 17:57:54
	         */
	        getProduCtBranchToTemp();
		}
		if(mainProdBranch==null){
			LOG.error(buyInfo.getVisitDate()+"类别不存在");
			return "input";
		}
		if(mainProdBranch.getProdProduct().isTraffic()&&!Constant.SUB_PRODUCT_TYPE.TRAIN.name().equals(mainProdBranch.getProdProduct().getSubProductType())){
			LOG.error("只有火车票可售");
			return "input";
		}
		Map<String, Object> data = pageService.getProdCProductInfo(prodProductBranch.getProductId(), false);
		if (data.size() == 0) {
			return "input";
		}
		List<ProdProductBranch> branchList = (List<ProdProductBranch>) data.get("prodProductBranchList");
		if(branchList.isEmpty()) {
			return "input";
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
		//游玩人数
	//	initOptionInfo(prodCProduct.getProdProduct());
		
		mainProductEContract = "false";
		if (null != prodCProduct.getProdRoute()) {
			mainProductEContract = prodCProduct.getProdRoute().isEContract() ? "true" : "false";
		}
			
		if(!prodCProduct.getProdProduct().isSellable()){
			LOG.error("当前产品"+buyInfo.getProductId()+" 已经过了上下线时间"); 
			return "input";
		} else if(!prodCProduct.getProdProduct().isOnLine()) {
			LOG.error("当前产品"+buyInfo.getProductId()+"未上线online="+prodCProduct.getProdProduct().getOnLine());
			return "input";
		} else if(!productServiceProxy.isSellProductByChannel(buyInfo.getProductId(), buyInfo.getChannel())) {
			LOG.error("当前产品"+buyInfo.getProductId()+"不能在"+buyInfo.getChannel()+"渠道销售");
			return "input";
		} else if(!pageService.checkDateCanSale(buyInfo.getProductId(),buyInfo.getVisitDate())) { 
			LOG.error("当前产品"+buyInfo.getProductId()+"游玩时间"+DateUtil.getDateTime("yyyy-MM-dd", buyInfo.getVisitDate())+"有时间限制"); 
			return "input";
		}
		if("您对订单的特殊要求".equals(buyInfo.getUserMemo())){
			buyInfo.setUserMemo(null);
		}
		if(prodCProduct.getProdProduct().hasSelfPack()){
			Map<Long,TimeInfo> map=buyInfo.getOrdItemProdMap();
			if(map.isEmpty()){
				LOG.error("没有选中自由行当中的产品列表");
				return "input";
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
			/**
			 * 门票,购买数量逻辑
			 */
			if(mainProdBranch.getProdProduct().isTicket()){
				List<ProdProductBranch> relatedProductList2=new ArrayList<ProdProductBranch>();
				relatedProductList2.addAll(relatedProductList);
				relatedProductList2.add(mainProdBranch);
				if(null!=relatedProductList2&&relatedProductList2.size()>0){
					for(ProdProductBranch product:relatedProductList2){
						String skey="product_"+product.getProdBranchId();
						if(null==buyInfo.getBuyNum().get(skey)){
							buyInfo.getBuyNum().put(skey, product.getMinimum().intValue());
						}
					}
				}
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
			if(prodProductBranch.getProdProduct().IsAperiodic()) {
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
					//显示当前用户未使用的优惠券
					Map<String,Object> params = new HashMap<String, Object>();
					params.put("userId", this.getUser().getId());
					params.put("used", "false");
					List<UserCouponDTO> userCouponDto = markCouponUserService.getMySpaceUserCouponData(params);
					if(userCouponDto != null && userCouponDto.size() > 0){
						for (int i = 0; i < userCouponDto.size(); i++) {
							MarkCoupon coupon = userCouponDto.get(i).getMarkCoupon();

							MarkCouponProduct mcp = null;
							//全场通用
							if(coupon.getCouponTarget() != null && coupon.getCouponTarget().equals(Constant.COUPON_TARGET.ORDER.toString())){
								mcp = new MarkCouponProduct();
							}else{
								// 判断优惠券是否有绑定过该产品或着该
								if(this.mainProdBranch.getProductId() != null){
									mcp = markCouponProductService.getSuitableMarkCouponProduct(coupon.getCouponId(),this.mainProdBranch.getProductId(),null);
								}
								if(mcp == null && !StringUtils.isEmpty(this.mainProdBranch.getProdProduct().getSubProductType())){
									mcp = markCouponProductService.getSuitableMarkCouponProduct(coupon.getCouponId(), null, this.mainProdBranch.getProdProduct().getSubProductType());
								}
							}
							if(mcp != null){
								this.userCouponList.add(userCouponDto.get(i));
							}
						}
					}
					
				}
			}
		}
		initOptionInfo(prodCProduct.getProdProduct());
		this.travelerFill(prodCProduct.getProdProduct());
		initUsrReceiversSaveAddress();
		this.reserveNotice();

		//初始化秒杀信息
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("productId", buyInfo.getProductId());
		param.put("nowDate", new Date());
		List<ProdSeckillRule> prodSeckillRuleList =prodSeckillRuleService.queryValidSeckillRule(param);
		if(prodSeckillRuleList != null && prodSeckillRuleList.size() > 0){
			buyInfo.setSeckillBranchId(prodSeckillRuleList.get(0).getBranchId());
			buyInfo.setSeckillId(prodSeckillRuleList.get(0).getId());
			buyInfo.setWaitPayment(prodSeckillRuleList.get(0).getPayValidTime());
		}
    moneyAccount = cashAccountService.queryMoneyAccountByUserNo(this.getUserId());

     couponEnabled = Constant.initCouponEnabled(mainProdBranch.getProdProduct().getProductId())?"Y":"N";


    return "fill";

	}


	//游玩人信息
	public String travelerFill(ProdProduct prodProduct) {
		prodAssemblyPointList = this.productServiceProxy.getAssemblyPoints(buyInfo.getProductId());
		getUser();
		this.initReceiversList();
		return null;
	}
	public void reserveNotice(){
		//费用说明
		StringBuffer feiyong = new StringBuffer();
		if(mainProdBranch.getProdProduct().getProductType().equals("HOTEL")){
			if(mainProdBranch.getDescription() != null){
				 String[] description = mainProdBranch.getDescription().split("\n");
				 for (int i = 0; i < description.length; i++) {
					 feiyong.append(description[i]);
				}
			}
		}else if(mainProdBranch.getProdProduct().getProductType().equals("TRAFFIC") && mainProdBranch.getProdProduct().getSubProductType().equals("TRAIN")){
			feiyong.append("卧铺上中下铺位为随机出票，实际出票铺位以铁路局为准，驴妈妈暂收下铺位的价格。出票后根据实际票价退还差价，至实际支付给驴妈妈的银行卡中。<br/>");
			feiyong.append("请凭下单填写的身份证或护照到火车站或全国各代售点窗口取票，凭票进站，部分火车站可以刷身份证直接进站，以具体车站为准。");
		}else{
			if(null != this.viewPage && null != this.viewPage.getContents() && null != this.viewPage.getContents().get("COSTCONTAIN")){
				ViewContent view = (ViewContent)this.viewPage.getContents().get("COSTCONTAIN");
				feiyong.append(view.getContentRn());
			}
		}
		//预订须知
		StringBuffer yuding = new StringBuffer();
		if(mainProdBranch.getProdProduct().getProductType().equals("TICKET")){
			if(null != this.viewPage && null != this.viewPage.getContents() && null != this.viewPage.getContents().get("ORDERTOKNOWN")){
				ViewContent view = (ViewContent)this.viewPage.getContents().get("ORDERTOKNOWN");
				yuding.append(view.getContentRn());
			}
		}
		//退款说明
		StringBuffer tuikuan = new StringBuffer();
		if(mainProdBranch.getProdProduct().isTrain()){
			tuikuan.append("1)产品预订失败退款：如果快铁驴行预订失败，驴妈妈将在确认失败后退款至原支付渠道，由银行将所有支付款项全额退回。 <br/>");
			tuikuan.append("2)产品差价退款：如果快铁驴行订单内含有的车票的实际票价低于您所支付的票价，驴妈妈将在确认实际票价后将差额退款至原支付渠道，由银行将差额款项退回，因出票原因产生的差额或全额退款，将在2小时内返还。<br/>");
			tuikuan.append("3)产品退票退款：若客户自行将快铁驴行订单内含有的车票进行退票，驴妈妈将在确认退票后，将差额部分退回原支付渠道，由银行将差额款项退回，退回时间为20个工作日。 <br/>");
			tuikuan.append("4)用户在火车站或代售窗口改签后，请在原列车班次的前一天17:00之前，致电我司客服（10106060）进行保险改签。游客可在改签提交后的5个工作日致电保险客服热线(太平保险：95589)查询到最终投保记录。如没有及时通知，保险还是原车次生效，一旦出险，后果自行承担。<br/>");
		}else if(null != this.viewPage && null != this.viewPage.getContents() && null != this.viewPage.getContents().get("REFUNDSEXPLANATION")){
			ViewContent view = (ViewContent)this.viewPage.getContents().get("REFUNDSEXPLANATION");
			tuikuan.append(view.getContentRn());
		}
		
		if(!feiyong.toString().equals("")){
			reserveNoticeMap.put("feiyong",feiyong.toString());
		}
		if(!yuding.toString().equals("")){
			reserveNoticeMap.put("yuding",yuding.toString());
		}
		if(!tuikuan.toString().equals("")){
			reserveNoticeMap.put("tuikuan",tuikuan.toString());
		}
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
		//System.out.println(travellerInfoOptions);
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
	private void getMarkCouponInfo(){
		
		if("true".equals(mainProdBranch.getProdProduct().getPayToLvmama()) && !mainProdBranch.getProdProduct().hasSelfPack()){//超级自由行不使用优惠券
			List<Long> idsList = new ArrayList<Long>();
			List<String> subProductTypes = new ArrayList<String>();
			idsList.add(mainProdBranch.getProductId());
			subProductTypes.add(mainProdBranch.getProdProduct().getSubProductType());
			
//			if(!mainProdBranch.getProdProduct().IsAperiodic()) {
//				for (ProdProductRelation relateProduct: relateList) {
//					if(idsList.contains(relateProduct.getRelatProductId().toString())){
//						idsList.add(relateProduct.getRelatProductId());
//						subProductTypes.add(relateProduct.getRelationProduct().getSubProductType());
//					}
//				}
//			}
			
			Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("productIds", idsList);
	    	map.put("subProductTypes", subProductTypes);
	    	map.put("withCode", "false");//只取优惠活动
			orderCouponList = markCouponService.selectAllCanUseAndProductCanUseMarkCoupon(map);
			this.hasCoupon = "true";
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
			String subProductTypeStr=ppr.getSubProductTypeStrTwo();
			
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
		if(null!=usrReceiversList && usrReceiversList.size()>0){
			usrReceivers=usrReceiversList.get(0);	
		}
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
	 * 加载地址列表
	 * */
	public void loadAddresses() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", getUserId());
		params.put("receiversType", Constant.RECEIVERS_TYPE.ADDRESS.name());
		this.usrReceiversList = receiverUserService.loadRecieverByParams(params);
		if(usrReceivers.getReceiverId() != null){
			this.usrReceivers = this.receiverUserService.getRecieverByPk(usrReceivers.getReceiverId());
		}
	}
	
	
	/**
	 * 加载订单邮寄地址信息.
	 * @return
	 */
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
	 * 保存订单邮寄地址.
	 * @return
	 */
	@Action("/usrReceivers/confirmAddress")
	public void confirmAddress() {
		JSONObject result=new JSONObject();
		try{
			if(!isLogin()){
				result.put("success", false);
				result.put("msg", "请登录");
				outputJsonMsg(result.toString());
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
					result.put("success", false);
					result.put("msg", "收件信息不存在不可以编辑");
					outputJsonMsg(result.toString());
				}
				
			/*	if(!StringUtils.equals(usr.getUserId(),getUserId())){
					throw new Exception("您修改的收件信息错误");
				}*/
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
				result.put("success", true);
				result.put("receiverId", usr.getReceiverId());
				result.put("msg", "增加成功");
			} else {
			//修改地址操作.
				//logger.info(this.usrReceivers.getReceiverId());				
				this.receiverUserService.updatepostCode(usr);
				result.put("success", true);
				result.put("receiverId", usr.getReceiverId());
				result.put("msg", "修改成功");
			}
			outputJsonMsg(result.toString());
		}catch(Exception ex){
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), ex);
		}
		
	}
	
	public void getProduCtBranchToTemp(){
		//现在传入的为产品类别ID.为保证前台参数传的简单性,自己计算对应的产品ID.
		for (int i = 0; i < 30; i++) {
			if(mainProdBranch == null){
				Calendar ca = Calendar.getInstance();
				ca.setTime(buyInfo.getVisitDate());
				ca.add(Calendar.DAY_OF_MONTH, 1);
				mainProdBranch = productServiceProxy.getProdBranchDetailByProdBranchId(buyInfo.getProdBranchId(),ca.getTime());
				buyInfo.setVisitTime(DateUtil.formatDate(ca.getTime(), "yyyy-MM-dd"));
				log.info("===========find branch ======buyInfoProduct:"+buyInfo.getProductId()+"===========time:"+DateUtil.formatDate(ca.getTime(), "yyyy-MM-dd"));
			}else{
				break;
			}
		}
	}
	public ViewBuyInfo getBuyInfo() {
		return buyInfo;
	}
	public void setBuyInfo(ViewBuyInfo buyInfo) {
		this.buyInfo = buyInfo;
	}
	public ProdProductBranch getMainProdBranch() {
		return mainProdBranch;
	}
	public List<ProdProductBranch> getRelatedProductList() {
		return relatedProductList;
	}
	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}
	
	public void setProductServiceProxy(ProductHeadQueryService productServiceProxy) {
		this.productServiceProxy = productServiceProxy;
	}

	public MarkCouponPointChange getMarkCouponPointChange() {
		return markCouponPointChange;
	}

	public void setMarkCouponPointChange(MarkCouponPointChange markCouponPointChange) {
		this.markCouponPointChange = markCouponPointChange;
	}

	public Long getCurrentUserPoint() {
		return currentUserPoint;
	}

	public void setCurrentUserPoint(Long currentUserPoint) {
		this.currentUserPoint = currentUserPoint;
	}

	public String getHasCoupon() {
		return hasCoupon;
	}

	public void setHasCoupon(String hasCoupon) {
		this.hasCoupon = hasCoupon;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public void setMarkCouponPointChangeService(
			MarkCouponPointChangeService markCouponPointChangeService) {
		this.markCouponPointChangeService = markCouponPointChangeService;
	}

	public List<MarkCoupon> getOrderCouponList() {
		return orderCouponList;
	}

	public void setOrderCouponList(List<MarkCoupon> orderCouponList) {
		this.orderCouponList = orderCouponList;
	}

	public List<ProdAssemblyPoint> getProdAssemblyPointList() {
		return prodAssemblyPointList;
	}

	public void setProdAssemblyPointList(
			List<ProdAssemblyPoint> prodAssemblyPointList) {
		this.prodAssemblyPointList = prodAssemblyPointList;
	}

	public Map<String, List<ProdProductRelation>> getAdditionalProduct() {
		return additionalProduct;
	}

	public void setAdditionalProduct(
			Map<String, List<ProdProductRelation>> additionalProduct) {
		this.additionalProduct = additionalProduct;
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

	public Map<String, List<ViewProdProductBranch>> getSelfpackProduct() {
		return selfpackProduct;
	}

	public void setSelfpackProduct(
			Map<String, List<ViewProdProductBranch>> selfpackProduct) {
		this.selfpackProduct = selfpackProduct;
	}

	public String getMainProductEContract() {
		return mainProductEContract;
	}

	public void setMainProductEContract(String mainProductEContract) {
		this.mainProductEContract = mainProductEContract;
	}

	public List<UsrReceivers> getReceiversList() {
		return receiversList;
	}

	public void setReceiversList(List<UsrReceivers> receiversList) {
		this.receiversList = receiversList;
	}
	public void setRelatedProductList(List<ProdProductBranch> relatedProductList) {
		this.relatedProductList = relatedProductList;
	}

	public UsrReceivers getContact() {
		return contact;
	}

	public void setContact(UsrReceivers contact) {
		this.contact = contact;
	}

	public float getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(float marketPrice) {
		this.marketPrice = marketPrice;
	}

	public float getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(float sellPrice) {
		this.sellPrice = sellPrice;
	}
	public long getFillTravellerNum() {
		return fillTravellerNum;
	}

	public void setFillTravellerNum(long fillTravellerNum) {
		this.fillTravellerNum = fillTravellerNum;
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

	public List<ComCity> getCityList() {
		return cityList;
	}

	public void setCityList(List<ComCity> cityList) {
		this.cityList = cityList;
	}

	public List<UsrReceivers> getUsrReceiversList() {
		return usrReceiversList;
	}

	public void setUsrReceiversList(List<UsrReceivers> usrReceiversList) {
		this.usrReceiversList = usrReceiversList;
	}

	public UsrReceivers getUsrReceivers() {
		return usrReceivers;
	}

	public void setUsrReceivers(UsrReceivers usrReceivers) {
		this.usrReceivers = usrReceivers;
	}

	public PlaceCityService getPlaceCityService() {
		return placeCityService;
	}

	public void setPlaceCityService(PlaceCityService placeCityService) {
		this.placeCityService = placeCityService;
	}

	public void setMarkCouponUserService(MarkCouponUserService markCouponUserService) {
		this.markCouponUserService = markCouponUserService;
	}

	public List<UserCouponDTO> getUserCouponList() {
		return userCouponList;
	}

	public void setUserCouponList(List<UserCouponDTO> userCouponList) {
		this.userCouponList = userCouponList;
	}

	public void setMarkCouponProductService(
			MarkCouponProductService markCouponProductService) {
		this.markCouponProductService = markCouponProductService;
	}
	
	public List<ComProvince> getProvinceList() {
		return placeCityService.getProvinceList();
	}
	
	public List<String> getFirstTravellerInfoOptions() {
		return firstTravellerInfoOptions;
	}

	public void setFirstTravellerInfoOptions(List<String> firstTravellerInfoOptions) {
		this.firstTravellerInfoOptions = firstTravellerInfoOptions;
	}

	public List<String> getTravellerInfoOptions() {
		return travellerInfoOptions;
	}

	public void setTravellerInfoOptions(List<String> travellerInfoOptions) {
		this.travellerInfoOptions = travellerInfoOptions;
	}

	public List<String> getContactInfoOptions() {
		return contactInfoOptions;
	}

	public void setContactInfoOptions(List<String> contactInfoOptions) {
		this.contactInfoOptions = contactInfoOptions;
	}

	public Map<String, String> getReserveNoticeMap() {
		return reserveNoticeMap;
	}

	public void setReserveNoticeMap(Map<String, String> reserveNoticeMap) {
		this.reserveNoticeMap = reserveNoticeMap;
	}

	public ViewPage getViewPage() {
		return viewPage;
	}

	public void setViewPage(ViewPage viewPage) {
		this.viewPage = viewPage;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public void setProdSeckillRuleService(
			ProdSeckillRuleService prodSeckillRuleService) {
		this.prodSeckillRuleService = prodSeckillRuleService;
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
