package com.lvmama.clutter.web.baidu;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.clutter.exception.ActivityLogicException;
import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.model.MobileBranch;
import com.lvmama.clutter.model.MobileBranchItem;
import com.lvmama.clutter.model.MobileOrder;
import com.lvmama.clutter.model.MobileOrderItem;
import com.lvmama.clutter.model.MobilePayment;
import com.lvmama.clutter.model.MobilePlace;
import com.lvmama.clutter.model.MobileProductRoute;
import com.lvmama.clutter.model.MobileTimePrice;
import com.lvmama.clutter.service.IBaiduActivityService;
import com.lvmama.clutter.service.IClientOrderService;
import com.lvmama.clutter.service.IClientPlaceService;
import com.lvmama.clutter.service.IClientProductService;
import com.lvmama.clutter.service.IClientUserService;
import com.lvmama.clutter.utils.BaiduActivityUtils;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.user.UserCooperationUserService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.ClientConstants;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;


@ParentPackage("clutterCreateOrderInterceptorPackage")
@ResultPath("/clutterCreateOrderInterceptor")
@Results({
		@Result(name = "baidu_order_fill", location = "/WEB-INF/pages/baidu/baidu_order_fill.html", type = "freemarker"),
		@Result(name = "baidu_order_success", location = "/WEB-INF/pages/baidu/baidu_order_success.html", type = "freemarker"),
		@Result(name = "order_fill", location = "/WEB-INF/pages/order/order_fill.html", type = "freemarker"),
		@Result(name = "error", location = "/WEB-INF/error.html", type = "freemarker") })
@Namespace("/mobile/bdorder")
public class BaiduOrderAction extends BaseAction {
	private static final long serialVersionUID = -6164959037367208915L;

	protected String productId;// 产品id
	
	protected String branchId; // 类别id
	
	protected String quantity;//票数
	
	protected boolean canOrderToday;// 是否今日定.
	
	protected String mobile;// 手机号
	
	protected String userName;// 取票人姓名
	
	private String idCard;//身份证
	
	protected String visitTime; // 游玩时间
	
	protected Long location = 1L;// 地址URL（订单返回用）
	
	protected String orderId;// 订单id
	
	private String baidutype;//1是框内0是框外
	
	private String countDownTime;//订单等时间
	
	private IBaiduActivityService baiduActivityService;
	
	/**
	 * User服务
	 */
	protected IClientUserService mobileUserService;
	
	/**
	 * 产品服务
	 */
	protected ProdProductService prodProductService;
	
	/**
	 * 产品服务
	 */
	IClientProductService mobileProductService;
	
	/**
	 * 订单服务
	 */
	protected IClientOrderService mobileOrderService;
	
	/**
	 * 景点服务 
	 */
	protected IClientPlaceService mobilePlaceService;
	
	//优惠券服务
	private MarkCouponService markCouponService;
	
	private String qingPlaceUrl;//景点详情Url
	
	/**
	 * sso服务
	 */
	protected UserUserProxy userUserProxy;
	
	private UserCooperationUserService userCooperationUserService;
	
	static final String BAIDUTUAN="BAIDUTUAN";
	/**
	 * 判断用户是否百度登陆
	 * 
	 * @return
	 */
	@Action("baidu_login_status")
	public void baiduLoginStatus() {
		JSONObject resultLogin=new JSONObject();
		
		if(getUser()==null){//未登录
			resultLogin.put("status",1);
		}else{//是否是百度登陆
			UserUser user = userUserProxy.getUserUserByUserNo(getUser().getUserNo());
			if(user!=null){
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("userId", user.getId());
				List<UserCooperationUser> cooperationUseres = userCooperationUserService
						.getCooperationUsers(parameters);	
				
				if(cooperationUseres==null||cooperationUseres.isEmpty()){
					resultLogin.put("status",1);
				}else{
					UserCooperationUser ucu = cooperationUseres.get(0);
					if(!BAIDUTUAN.equals(ucu.getCooperation())){
						resultLogin.put("status",1);
					}else{
						ServletUtil.addCookie(getResponse(), "bd_uid_order", ucu.getCooperationUserAccount());
						resultLogin.put("status",0);//是百度登陆
					}
				}
			}
		}
		this.sendAjaxResult(resultLogin.toString());
	}
	/**
	 * 百度活动驴妈妈两个账号不限制
	 * 第三个个是79用户
	 * @return
	 */
	public boolean getLvmamaNoLimit(){
		if(getUser()!=null && (
				"40288a084560a3ce014563ffdb0a01f6".equals(getUser().getUserNo()) 
				|| "40288a084560a3ce0145698108d00601".equals(getUser().getUserNo()) 
				|| "ff8080814569a74a014569a74afb0000".equals(getUser().getUserNo()) 
				|| "40288a12457f370401458317598c024d".equals(getUser().getUserNo()) 
				|| "40288a12457f37040145831891fc0250".equals(getUser().getUserNo()) 
				|| "40288a12457f37040145831940090253".equals(getUser().getUserNo()) 
				|| "40288a12457f370401458319b5470254".equals(getUser().getUserNo()) 
				|| "5ad32f1a1cd28093011cd564e2b87cee".equals(getUser().getUserNo()))){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 填写订单.
	 * 
	 * @return
	 */
	@Action("baidu_order_fill")
	public String baiduFillOrder() {
		//判断用用户是否可定
		String userNo="";
		//百度两个账号不做限制
		if(!getLvmamaNoLimit()){
			if(getUser()!=null){
				userNo=getUser().getUserNo();
			}
			Map<String, Object> result=baiduActivityService.baiduBooking(Long.valueOf(productId),userNo);
			//不可预订直接返回
			if(result!=null && !"1000".equals(result.get("code"))){
				getRequest().setAttribute("msg",BaiduActivityUtils.BAIDU_MSG.getCnName(result.get("code").toString()));
				return "baidu_order_fill";
			}
		}
		// 返回地址次数SESSION读写
		this.urlBackCount();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			// 获取brancId
			param.put("branchId", branchId);
			String udid = ServletUtil.getCookieValue(getRequest(), "mb_udid");
			if (null == udid || udid.isEmpty()) {
				udid = UUID.randomUUID().toString();
				ServletUtil
						.addCookie(getResponse(), "mb_udid", udid, 30, false);// 默认保存30天
			}
			param.put("udid", udid);
			param.put("todayOrder", canOrderToday);
			//获取订单联系人
			if (getUser() != null) {
				//param.put("userNo", getUser().getUserNo());

				// 初始化默认联系人信息
				userName = getUser().getUserName();
				mobile = getUser().getMobileNumber();

			}
			if (!StringUtils.isEmpty(visitTime)) {
				param.put("visitTime", visitTime); // 预定 日期
			}

			// 获取Proudct
			ProdProduct product = prodProductService.getProdProductById(Long
					.valueOf(productId));

			// 时间价格表
			param.put("productId", productId);
			List<MobileTimePrice> timePrice = mobileProductService
					.timePrice(param);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("datas", timePrice);
			getRequest()
					.setAttribute("timePriceJson", JSONObject.fromObject(m));
			
			//获取类别数据
			Map<String, Object> map = mobileProductService
					.getProductItems(param);
			
			// 非今天明天后天 ，显示星期
			if (null != map && null != map.get("timeHolder")) {
				if (String.valueOf(map.get("timeHolder")).indexOf("20") != -1) {
					map.put("timeHolder", map.get("weekOfDay")); // 星期
				}
			}
			//获取百度价和原价
			Float marketPriceYuan=null;
			Float sellPriceYuan=null;
			if(map!=null){
				List<MobileBranch> branchList=(List<MobileBranch>) map.get("datas");
				if(branchList!=null && branchList.size()>0){
					for(MobileBranch o : branchList){
						marketPriceYuan=o.getSellPriceYuan();
						o.setSellPriceYuan(BaiduActivityUtils.getProductPrice(String.valueOf(o.getProductId()),o.getSellPriceYuan()));
						sellPriceYuan=o.getSellPriceYuan();
						break;
					}
				}
				map.put("datas", branchList);
			}
			getRequest().setAttribute("sellPriceYuan", sellPriceYuan);
			getRequest().setAttribute("marketPriceYuan", marketPriceYuan);
			getRequest().setAttribute("productItems", map);
			getRequest().setAttribute("product", product);
			getRequest().setAttribute("productName", product.getProductName());

		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
		}

		// 门票预定
		return "baidu_order_fill";
	}
	/**
	 *百度提交订单 
	 */
	@Action("baidu_order_submit")
	public void baiduOrderSubmit() {
		JSONObject jsonObj = new JSONObject();
		try {
			//表单验证
			validateParams();
			//中文转码
			userName = URLDecoder.decode(userName, "UTF-8");
			MobileBranchItem mobileBranch=new MobileBranchItem();
			mobileBranch.setBranchId(Long.valueOf(branchId));
			mobileBranch.setQuantity(Long.valueOf(quantity));
			
			Date visitDate=getStringToDate(visitTime);
			//根据活动类型获取对应的优惠券号
			String spottickType=BaiduActivityUtils.ticketType(productId);
			String couponId=null;
			if("1".equals(spottickType)){//半价
				couponId=Constant.getInstance().getValue("baidu.banjia.couponcode");
				
			}else if("2".equals(spottickType)){//立减
				couponId=Constant.getInstance().getValue("baidu.lijian.couponcode");
			}
			MarkCouponCode markCouponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(Long.valueOf(couponId));
			log.info("couponId:"+couponId);
			log.info("couponCode"+markCouponCode.getCouponCode());
			try {
				Long baiduOrderId=baiduActivityService.commitActivityOrder(mobileBranch, visitDate, getUser().getUserNo(), userName, mobile, idCard,markCouponCode.getCouponCode());
				orderId=baiduOrderId.toString();
				jsonObj.put("orderId", orderId);
				
			} catch (Exception e) {
				if(!getLvmamaNoLimit()){
					Map<String, Object> resMap=baiduActivityService.baiduAfterSubmit(Long.valueOf(productId),getUser().getUserNo(),baidutype);
				}
				log.error(e.getMessage());
				jsonObj.put("msg", e.getMessage());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			jsonObj.put("msg", e.getMessage());
		}
		super.sendAjaxResult(jsonObj.toString());
	}
	/**
	 * 表单校验
	 * 
	 * @throws Exception
	 */
	public void validateParams() throws Exception {
		// 取票人姓名
		if (StringUtil.isEmptyString(userName)) {
			throw new ActivityLogicException("订单联系人不能为空");
		}
		// 取票人手机
		if (!StringUtil.validMobileNumber(mobile)) {
			throw new ActivityLogicException("取票人手机不能为空");
		}
		if(getUser()==null){
			throw new ActivityLogicException("您需要百度登陆才能预订");
		}
		//百度两个账号不限制
		if(!getLvmamaNoLimit()){
			//百度活动购买记录接口-判断用户是否买过------百度接口过滤
			String udid = ServletUtil.getCookieValue(getRequest(), "bd_uid_order");
			log.info("bd_uid:"+udid);
			String jsonStr =HttpsUtil.proxyRequestGet("http://openapi.baidu.com/wallet/activity/info?activity_id=1&uid="+udid, InternetProtocol.getRemoteAddr(getRequest()));
			log.info("result:"+jsonStr);
			Map<String,Object> result =  JSONObject.fromObject(jsonStr);
			if(result!=null){
				if(result.get("result")!=null && (Integer)result.get("result")!=1){
					throw new ActivityLogicException("用户已经购买不能继续下单");
				}else if(result.get("error_code")!=null){
					throw new ActivityLogicException(result.get("error_msg").toString());
				}
			}
			//判断用户是否可提交------------------驴妈妈过滤
			Map<String, Object> resMap=baiduActivityService.baiduBeforSubmit(null,Long.valueOf(productId),getUser().getUserNo(),baidutype);
			//不可预订直接返回
			if(resMap!=null && !"1000".equals(resMap.get("code"))){
				throw new ActivityLogicException(BaiduActivityUtils.BAIDU_MSG.getCnName(resMap.get("code").toString()));
			}
		}
	}
	/**
	 * 时间转换
	 * @param dateString
	 * @return
	 */
	public Date getStringToDate(String dateString){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		try {
			date = dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
		}
		return date;
	}
	/**
	 * 订单提交成功.
	 * 
	 * @return
	 */
	@Action(value = "baidu_order_success", interceptorRefs = {
			@InterceptorRef(value = "clutterCreateOrderInterceptor"),
			@InterceptorRef(value = "defaultStack") })
	public String orderSuccess() {
		Map<String, Object> param = new HashMap<String, Object>();
		if(getUser()==null){
			getRequest().setAttribute("loginStatus", "no");
			getRequest().setAttribute("orderId", orderId);
			return "baidu_order_success";
		}
		try {
			param.put("orderId", orderId);
			param.put("userNo", getUser().getUserNo());
			param.put("userId", getUser().getId());
			
			MobileOrder order = mobileUserService.getOrder(param);
			if (null != order) {
				// 获取支付倒计时描述.
				Map<String, Object> m = mobileOrderService
						.queryOrderWaitPayTimeSecond(param);
				if (null != m) {
					getRequest().setAttribute("surplusSeconds",
							m.get("surplusSeconds"));
				}
				//获取百度价和原价
				Float marketPriceYuan=null;
				if(order.getPaymentChannel()!=null && Constant.MARK_PAYMENT_CHANNEL.BAIDU_PAY.name().equals(order.getPaymentChannel())){
					List<MobileOrderItem> orderItem=order.getOrderItem();
					for(MobileOrderItem o : orderItem){
						marketPriceYuan=o.getPrice();
						o.setPrice(BaiduActivityUtils.getProductPrice(String.valueOf(order.getProductId()),o.getPrice()));
					}
				}
				
				getRequest().setAttribute("order", order);
				getRequest().setAttribute("needResourceTicketCanToPay",
						ClientUtils.isNeedResourceTicketCanToPay(order));
				//================================================
				String objectName=getGoodsNeme(order);//产品名
				if(objectName!=null && objectName.length()>=64){
					objectName=URLEncoder.encode(objectName.substring(0,64),"UTF-8");
				}else{
					objectName=URLEncoder.encode(objectName,"UTF-8");
				}
				String objectDesc="";//活动类型
				//要判断哪张是立减/哪张是半价调用祖博接口
				String spottickType=BaiduActivityUtils.ticketType(String.valueOf(order.getProductId()));
				if(spottickType!=null && "1".equals(spottickType)){
					//objectDesc="半价";
					objectDesc=URLEncoder.encode("半价","UTF-8");
				}else if(spottickType!=null && "2".equals(spottickType)){
					//objectDesc="直减";
					objectDesc=URLEncoder.encode("直减","UTF-8");
				}else{
					//objectDesc="直减";
					objectDesc=URLEncoder.encode("其他","UTF-8");
				}
				String objectPageUrl="";//URL
				if("qing.lvmama.com".equals(getReauestHostName())){
					objectPageUrl="http://qing.lvmama.com/clutter/order/order_detail.htm?orderId="+order.getOrderId();
				}else{
					objectPageUrl="http://m.lvmama.com/clutter/order/order_detail.htm?orderId="+order.getOrderId();
				}
				
				String jsonStr =HttpsUtil.proxyRequestGet(ClutterConstant.getProperty("superFrontUrl")+"/baiduApp/toPay.do?orderId="+order.getOrderId()+"&objectName="+objectName+"&objectDesc="+objectDesc+"&objectPageUrl="+objectPageUrl, InternetProtocol.getRemoteAddr(getRequest()));
				//String jsonStr =HttpsUtil.proxyRequestGet(ClutterConstant.getProperty("superFrontUrl")+"/baiduApp/toPay.do?orderId="+order.getOrderId(), InternetProtocol.getRemoteAddr(getRequest()));
				getRequest().setAttribute("jsonStr", jsonStr);
				getRequest().setAttribute("marketPriceYuan", marketPriceYuan);
				getRequest().setAttribute("objectName", java.net.URLDecoder.decode(objectName));
				getRequest().setAttribute("objectDesc", java.net.URLDecoder.decode(objectDesc));
				getRequest().setAttribute("objectPageUrl", objectPageUrl);
				Date d=getStringToDateMl(order.getCreatedTime());
				getRequest().setAttribute("countDownTime", d.getTime()+15*60*1000-System.currentTimeMillis());
				//========================================================
				return "baidu_order_success";
			} else {
				getRequest().setAttribute("msg", "未找到订单，订单号：" + orderId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
		}
		return "error";
	}
	/**
	 * 获取产品名称
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public String getGoodsNeme(MobileOrder order) throws Exception{
		Map<String, Object> paramPlace = new HashMap<String, Object>();
		if(order.getPlaceId()!=null && order.getPlaceId()>0){// 获取景点信息
			String goodsNeme=BaiduActivityUtils.getBdNameByPlaceId(order.getPlaceId());
			log.info("placeId:"+order.getPlaceId());
			log.info("goodsNeme:"+goodsNeme);
			if(goodsNeme!=null && !"".equals(goodsNeme)){
				return goodsNeme;
			}else{
				paramPlace.put("placeId", order.getPlaceId());
				MobilePlace mobilePlace = mobilePlaceService.getPlace(paramPlace);
				return mobilePlace.getName();
			}
			
		}else{// 获取线路信息
			paramPlace.put("productId", order.getProductId());
			MobileProductRoute mpr = mobileProductService.getRouteDetail(paramPlace);
			return mpr.getProductName();
		}
	}
	/**
	 * 时间转换
	 * @param dateString
	 * @return
	 */
	public Date getStringToDateMl(String dateString){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=null;
		try {
			date = dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
		}
		return date;
	}
	/**
	 * 返回地址SESSION读写
	 */
	public void urlBackCount() {
		if (this.getRequest().getSession().getAttribute("location") != null
				&& !"".equals(this.getRequest().getSession()
						.getAttribute("location").toString())) {
			location = Long.valueOf(this.getRequest().getSession()
					.getAttribute("location").toString());
		}
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public boolean isCanOrderToday() {
		return canOrderToday;
	}
	public void setCanOrderToday(boolean canOrderToday) {
		this.canOrderToday = canOrderToday;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}
	public Long getLocation() {
		return location;
	}
	public void setLocation(Long location) {
		this.location = location;
	}
	public ProdProductService getProdProductService() {
		return prodProductService;
	}
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
	public IClientProductService getMobileProductService() {
		return mobileProductService;
	}
	public void setMobileProductService(IClientProductService mobileProductService) {
		this.mobileProductService = mobileProductService;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public IClientUserService getMobileUserService() {
		return mobileUserService;
	}
	public void setMobileUserService(IClientUserService mobileUserService) {
		this.mobileUserService = mobileUserService;
	}
	public IClientOrderService getMobileOrderService() {
		return mobileOrderService;
	}
	public void setMobileOrderService(IClientOrderService mobileOrderService) {
		this.mobileOrderService = mobileOrderService;
	}
	public IBaiduActivityService getBaiduActivityService() {
		return baiduActivityService;
	}
	public void setBaiduActivityService(IBaiduActivityService baiduActivityService) {
		this.baiduActivityService = baiduActivityService;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public UserUserProxy getUserUserProxy() {
		return userUserProxy;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public UserCooperationUserService getUserCooperationUserService() {
		return userCooperationUserService;
	}

	public void setUserCooperationUserService(
			UserCooperationUserService userCooperationUserService) {
		this.userCooperationUserService = userCooperationUserService;
	}

	public String getBaidutype() {
		return baidutype;
	}

	public void setBaidutype(String baidutype) {
		this.baidutype = baidutype;
	}

	public String getCountDownTime() {
		return countDownTime;
	}

	public void setCountDownTime(String countDownTime) {
		this.countDownTime = countDownTime;
	}

	public IClientPlaceService getMobilePlaceService() {
		return mobilePlaceService;
	}

	public void setMobilePlaceService(IClientPlaceService mobilePlaceService) {
		this.mobilePlaceService = mobilePlaceService;
	}

	public String getQingPlaceUrl() {
		return qingPlaceUrl;
	}

	public void setQingPlaceUrl(String qingPlaceUrl) {
		this.qingPlaceUrl = qingPlaceUrl;
	}

	public MarkCouponService getMarkCouponService() {
		return markCouponService;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
}
