package com.lvmama.clutter.web.user;


import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.clutter.model.MobileOrderCmt;
import com.lvmama.clutter.model.MobileProductRoute;
import com.lvmama.clutter.model.MobileReceiver;
import com.lvmama.clutter.model.MobileUserCoupon;
import com.lvmama.clutter.service.IClientProductService;
import com.lvmama.clutter.service.IClientUserService;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.bee.vo.view.MarkCouponUserInfo;
import com.lvmama.comm.pet.po.comment.DicCommentLatitude;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.search.PlaceSearchInfoService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 用户处理Action
 * 
 * @author YangGan
 *
 */
@Results({ 
	@Result(name = "myconpon", location = "/WEB-INF/pages/user/myconpon.html", type="freemarker"),
	@Result(name = "myconpon_activity", location = "/WEB-INF/pages/user/myconpon.html", type="freemarker"),
	@Result(name = "mycollect", location = "/WEB-INF/pages/user/mycollect.html", type="freemarker"),
	@Result(name = "mycollection_ajax", location = "/WEB-INF/pages/user/mycollect_ajax.html", type="freemarker"),
	@Result(name = "envaluate", location = "/WEB-INF/pages/user/envaluate.html", type="freemarker"),
	@Result(name = "envaluate_ajax", location = "/WEB-INF/pages/user/envaluate_ajax.html", type="freemarker"),
	@Result(name = "add_evaluate", location = "/WEB-INF/pages/user/addevaluate.html", type="freemarker"),
	@Result(name = "add_contact_ajax", location = "/WEB-INF/pages/order/contact_list_ajax_V5.html", type="freemarker"),
	@Result(name = "add_contact", location = "/WEB-INF/pages/user/add_contact.html", type="freemarker"),
	@Result(name = "user_favoritor_place", location = "${request.contextPath}/place/${objectId}", type="redirect"),
	@Result(name = "user_favoritor_route", location = "${request.contextPath}/route/${objectId}", type="redirect"),
	@Result(name = "myBonus", location = "/WEB-INF/pages/user/myBonus.html", type="freemarker"),
	@Result(name = "bonusInfo", location = "/WEB-INF/pages/user/bonusInfo_${bonusType}.html", type="freemarker"),
	@Result(name = "bonusInfo_ajax", location = "/WEB-INF/pages/user/bonusInfo_ajax.html", type="freemarker"),
	@Result(name = "mypoint", location = "/WEB-INF/pages/user/point.html", type="freemarker")
})
@Namespace("/mobile/user")
public class UserAction extends BaseAction{

	private static final long serialVersionUID = 1L;

	/*登录之前的URL*/
	private String service;
	private String username;
	private String password;
	private String mobileOrEMail;// 手机号
	private String authenticationCode; // 校验码
	
	private int page = 1; // 当前页数.
	private boolean ajax; // 是否ajax查询
    private String collectionType; // 收藏类别 place or product 

    private String bonusType = "income";// income : 收入 | pay: 支出 | refund: 退款

	/**
	 * 点评维度列表
	 */
	private String[] latitudeIds;
	/**
	 * 点评维度评分列表
	 */
	private String[] scores;
	/**
	 * 点评内容
	 */
	private String content;
	/**
	 * 订单id
	 */
    private String orderId;  

    /**
     * 点评对象的id 
     */
    private String objectId;
    
    private Long currentPoint;//用户积分余额
    
    private Long aboutToExpiredPoint;//用户年末到期积分
    
    private Long usedPoint;//用户已使用积分
    
    private List<MobileProductRoute> pointProductList;//积分页面推荐商品--线路
	private List<PlaceSearchInfo> pointPlaceList;//积分页面推荐商品--景点

	/**
	 * User服务 
	 */
	protected IClientUserService mobileUserService;
	
	
	protected UserUserProxy userUserProxy;
	
	/**
	 * 产品服务 
	 */
	protected IClientProductService mobileProductService;
	
	/**
	 * 景点服务
	 */
	
	protected PlaceSearchInfoService placeSearchInfoService;

	String objectImageUrl; // 收藏id
	String objectName;
	String objectType;
	
	//活动 
	private String activityChannel = ""; // losc代码
	
	/**
	 * 优惠券
	 */
	private MarkCouponService markCouponService;
	
	/**
	 * 我的优惠券. 
	 * @return str
	 */
	@Action("myconpon")
	public String myconpon() {
		getRequest().setAttribute("prefixPic", Constant.getInstance().getPrefixPic());
		
		// 处理相关活动。
//		if(!StringUtils.isEmpty(activityChannel)) {
//			// 12580活动
//			if(activityChannel.equals(ClutterConstant.MOBILE_ACTIVITY_12580_LOGIN) 
//					|| activityChannel.equals(ClutterConstant.MOBILE_ACTIVITY_12580_REG)
//					|| activityChannel.equals(ClutterConstant.MOBILE_ACTIVITY_10000_LOGIN) 
//					|| activityChannel.equals(ClutterConstant.MOBILE_ACTIVITY_10000_REG)){
//				// 相关逻辑 - 赠送优惠券  3891,3892,3893,3894,3895,3896
//				boolean b = sendConpon4Wap();
//				if(b){ // 如果赠送成功
//					getRequest().setAttribute("hasSendConpon", "true");
//					//ServletUtil.addCookie(getResponse(), ClutterConstant.MOBILE_ACTIVITY_LOSC, "", 0, false);// 清楚相关cookies
//				}
//			}
//		}
		
		// 可用优惠券
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", getUser().getId());
		params.put("state", "USED");
		List<MobileUserCoupon> usedList = mobileUserService.getCoupon(params);
		// 已用优惠券
		params.put("state", "NOT_USED");
		List<MobileUserCoupon> unusedList = mobileUserService.getCoupon(params);
		getRequest().setAttribute("usedList", usedList);
		getRequest().setAttribute("unusedList", unusedList);
		
		//渠道类别 loscType 如：登陆 或者 注册等
		if(!StringUtils.isEmpty(activityChannel)) {
			return "myconpon_activity";
		}
		return "myconpon";
	}
	
	/**
	 * wap登录注册赠送优惠券 12580
	 */
	public boolean sendConpon4Wap() {
		try {
			// 12580活动活动有效期 2014-01-31 23:59:59
			if(activityChannel.indexOf("mobile_activity_12580") != -1 
					&& !ClientUtils.isValidateDate("", "2014-01-31 23:59:59", "yyyy-MM-dd HH:mm:ss")) {
				return false;
			}
			// 是否已经赠送过 - 如果没有赠送过
			UserUser user = getUser();
			if(null == user) {
				log.info("sendConpon4Wap user is null...");
				return false;
			}
			// 默认12580
			String strCouponIds = Constant.getInstance().getValue("activity.wap.coupon.id");
			// 如果是电信的 
			if(activityChannel.indexOf("mobile_activity_10000") != -1) {
				strCouponIds = Constant.getInstance().getValue("activity.wap.coupon.10000.id");
			}
			if(StringUtils.isEmpty(strCouponIds)) {
				log.info("strCouponIds is null...");
				return false;
			}
			// 如果没有赠送
			boolean b = false; // 判断是否送过优惠券
			String[] strcouponIdArr = strCouponIds.trim().split(",");
			for(int i = 0 ;i < strcouponIdArr.length;i++) {
				long couponId = Long.valueOf(strcouponIdArr[i]);
				if(!isGiveConpon4Activity(couponId,user)) {
					MarkCouponCode markCouponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(couponId);
					markCouponService.bindingUserAndCouponCode(user,markCouponCode.getCouponCode());
					b = true;
				}
			}
			return b;
		}catch(Exception e){
			log.info("sendConpon4Wap error....");
			e.printStackTrace();
		}
	    return false;
	}
	
	/**
	 * 是否已经赠送过优惠券  - 活动专用
	 * @return
	 * @throws Exception 
	 */
	public boolean isGiveConpon4Activity(Long couponId,UserUser u){
		try{
			List<MarkCouponUserInfo> list = markCouponService.queryMobileUserCouponInfoByUserId(u.getId());
			if(null != list && list.size() > 0){
				String couponid = couponId+"";
				for(int i =0;i < list.size();i++) {
					MarkCouponUserInfo mui = list.get(i);
					if(null != mui.getMarkCoupon() && couponid.equals(String.valueOf(mui.getMarkCoupon().getCouponId()))) {
						// 如果已经赠送过优惠券
						return true;
					}
				}
			}
		}catch(Exception e){
			log.info("isGiveConpon4Activity exceptiou .....");
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 我的收藏. 
	 * @return str
	 */
	@Action("mycollect")
	public String mycollection() {
		getRequest().setAttribute("prefixPic", Constant.getInstance().getPrefixPic());

		// 景点收藏
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("page", page);
		params.put("userId", getUser().getId());
		params.put("userNo", getUser().getUserNo());
		params.put("objectTypes", "PLACE");
		Map<String,Object> placeMap = mobileUserService.getFavoriteList(params);
		if(null != placeMap && null != placeMap.get("datas")) {
			getRequest().setAttribute("placeList", placeMap.get("datas"));
			getRequest().setAttribute("isLastPage", placeMap.get("isLastPage"));
		}
		// 产品收藏
		 params.put("objectTypes", "PRODUCT");
		 Map<String,Object> productMap = mobileUserService.getFavoriteList(params);
		 if(null != productMap && null != productMap.get("datas")) {
				getRequest().setAttribute("productList", productMap.get("datas"));
				getRequest().setAttribute("isUsedLastPage", productMap.get("isLastPage"));
		 }
		 
		 if(ajax) {
			 return "mycollect_ajax";
		 }
		
		return "mycollect";
	}
	
	/**
	 * 我的收藏. 
	 * @return str
	 */
	@Action("mycollection_ajax")
	public String mycollectionAjax() {
		getRequest().setAttribute("prefixPic", Constant.getInstance().getPrefixPic());

		// 收藏
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("page", page);
		params.put("userNo", getUser().getUserNo());
		params.put("userId", getUser().getId());
		params.put("objectTypes", collectionType);
		Map<String,Object> placeMap = mobileUserService.getFavoriteList(params);
		if(null != placeMap && null != placeMap.get("datas")) {
			getRequest().setAttribute("productList", placeMap.get("datas"));
			if("PLACE".equals(collectionType)) {
				getRequest().setAttribute("isLastPage", placeMap.get("isLastPage"));
			} else {
				getRequest().setAttribute("isUsedLastPage", placeMap.get("isLastPage"));
			}
		}
		return "mycollection_ajax";
	}
	
	/**
	 * 我的点评.  
	 * @return str
	 */
	@Action("envaluate")
	public String envaluate() {
		getRequest().setAttribute("prefixPic", Constant.getInstance().getPrefixPic());

		// 待点评
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", getUser().getId());
		params.put("userNo",  getUser().getUserNo());
		List<MobileOrderCmt> orderList = mobileUserService.queryCommentWaitForOrder(params);
		getRequest().setAttribute("unCommentList", orderList);
		// 已点评
		Map<String,Object> orderMap = mobileUserService.queryCommentForOrder(params);
		if(null != orderMap && null != orderMap.get("datas")) {
			getRequest().setAttribute("isLastPage", orderMap.get("isLastPage"));
			getRequest().setAttribute("commentList", orderMap.get("datas"));
		}
		
		return "envaluate";
	}
	
	/**
	 * 我的点评 - ajax查询.  
	 * @return str
	 */
	@Action("envaluate_ajax")
	public String envaluateAjax() {
		getRequest().setAttribute("prefixPic", Constant.getInstance().getPrefixPic());

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", getUser().getId());
		params.put("page", page);
		params.put("userNo",  getUser().getUserNo());
		// 已点评
		Map<String,Object> orderMap = mobileUserService.queryCommentForOrder(params);
		if(null != orderMap && null != orderMap.get("datas")) {
			getRequest().setAttribute("isLastPage", orderMap.get("isLastPage"));
			getRequest().setAttribute("commentList", orderMap.get("datas"));
		}
		
		return "envaluate_ajax";
	}
	
	/**
	 * 添加点评 页面. 
	 * @return 
	 */
	@Action("add_evaluate")
	public String addEvaluate() {
		// 待点评维度
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderId", orderId);
		try{
			 List<DicCommentLatitude>  latitudeList = mobileUserService.getCommentLatitudeInfos(params);
			 getRequest().setAttribute("latitudeList", latitudeList);
		}catch(Exception e){
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
		}
		
		return "add_evaluate";
	}
	
	/**
	 * 添加点评 
	 * @return
	 */
	@Action("submit_comment")
	public void submitComment() {
		String str  = "{\"code\":1,\"msg\":\"\"}";;
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("latitudeInfo", getCmtLatitudeStr());
			params.put("objectId", objectId);
			params.put("content", content);
			params.put("firstChannel", Constant.CHANNEL.TOUCH.name());
			params.put("userNo", getUser().getUserNo());
			str = mobileUserService.commitComment(params);
			if("success".equals(str)) {
				str  = "{\"code\":1,\"msg\":\"\"}";;
			} else {
				str  = "{\"code\":-1,\"msg\":\"服务器异常!\"}";;
			}
		}catch(Exception e) {
			e.printStackTrace();
			str  = "{\"code\":-1,\"msg\":\""+e.getMessage()+"\"}";
		}
		
		super.sendAjaxResult(str);
		
	}
	
	/**
	 * 添加收藏
	 * @return
	 */
	@Action("submit_favoritor")
	public void submitFavoritor() {
		String str  = "{\"code\":1,\"msg\":\"\"}";;
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("isValid", "1");
			params.put("objectId", objectId);
			params.put("objectType", objectType);
			params.put("objectImageUrl", objectImageUrl);
			if(!StringUtils.isEmpty(objectName)) {
				objectName = URLDecoder.decode(objectName,"UTF-8");
			} else {
				objectName = "";
			}
			params.put("objectName", objectName);
			params.put("userId", getUser().getId());
			str = mobileUserService.addFavorite(params);
			if("success".equals(str)) {
				str  = "{\"code\":1,\"msg\":\"\"}";;
			} else {
				str  = "{\"code\":-1,\"msg\":\"服务器异常!\"}";;
			}
		}catch(Exception e) {
			e.printStackTrace();
			str  = "{\"code\":-1,\"msg\":\""+e.getMessage()+"\"}";
		}
		
		super.sendAjaxResult(str);
		
	}
	
	/**
	 * 添加收藏
	 * @return
	 */
	@Action("cancel_favoritor")
	public void cancelFavoritor() {
		String str  = "{\"code\":1,\"msg\":\"\"}";;
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("id", objectId);
			params.put("userId", getUser().getId());
			boolean b = mobileUserService.cancelFavorite(params);
			if(b) {
				str  = "{\"code\":1,\"msg\":\"\"}";;
			} else {
				str  = "{\"code\":-1,\"msg\":\"服务器异常!\"}";;
			}
		}catch(Exception e) {
			e.printStackTrace();
			str  = "{\"code\":-1,\"msg\":\""+e.getMessage()+"\"}";
		}
		super.sendAjaxResult(str);
	}
	
	/**
	 * 我的奖金账号基本信息 . 
	 * @return
	 */
	@Action("myBonus")
	public String myBonus() {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("userId", getUser().getId());
			params.put("userNo", getUser().getUserNo());
			resultMap = mobileUserService.getBonusInfo(params);
		}catch(Exception e) {
			e.printStackTrace();
		}
		getRequest().setAttribute("bonus", resultMap);
		
		return "myBonus";
	}
	
	/**
	 * 我的奖金账号基本信息 . 
	 * @return
	 */
	@Action("bonusInfo")
	public String bonusInfo() {
		List<Map<String,Object>> resultMapList = new ArrayList<Map<String,Object>>();
		getRequest().setAttribute("isLastPage", true);
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("userId", getUser().getId());
			params.put("userNo", getUser().getUserNo());
			params.put("page", page);
			// income : 收入 | pay: 支出 | refund: 退款  
			bonusType = getRequest().getParameter("bonusType");
			Map<String,Object> resultMap = new HashMap<String,Object>();
			if("income".equals(bonusType)) {
				resultMap = mobileUserService.getBonusIncome(params);
			} else if("pay".equals(bonusType)) {
				resultMap = mobileUserService.getBonusPayment(params);
			} else if("refund".equals(bonusType)) {
				resultMap = mobileUserService.getBonusRefund(params);
			}
			
			if(null != resultMap && null != resultMap.get("datas")) {
				resultMapList = (List<Map<String, Object>>) resultMap.get("datas");
				getRequest().setAttribute("isLastPage", resultMap.get("isLastPage"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		getRequest().setAttribute("bonusList", resultMapList);
		
		if(ajax) {
			return "bonusInfo_ajax";
		}
		
		return "bonusInfo";
	}
	
	/**
	 * 获取游玩人列表 
	 * @return
	 */
	@Action("get_contacts")
	public String getContacts() {
		getUserContacts();
		return "get_contacts";
	}
	
	/**
	 * 添加游玩人  
	 * @return
	 */
	@Action("add_contact")
	public String addContact() {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			Map<String, String> param = new HashMap<String,String>();
			// receiverName mobileNumber  certType  gender  receiverId  birthday  certNo  userNo 
			param.put("receiverName", ClientUtils.filterOutHTMLTags(getRequest().getParameter("receiverName")) );
			param.put("mobileNumber", getRequest().getParameter("mobileNumber"));
			param.put("certType", getRequest().getParameter("certType"));
			param.put("receiverId", getRequest().getParameter("receiverId"));
			// 护照才允许填写生日和性别
			if(Constant.CERT_TYPE.HUZHAO.getCode().equals(getRequest().getParameter("certType"))) {
				if(!StringUtils.isEmpty(getRequest().getParameter("birthday"))) {
					param.put("birthday", getRequest().getParameter("birthday"));
				}
				if(!StringUtils.isEmpty(getRequest().getParameter("gender"))) {
					param.put("gender", getRequest().getParameter("gender"));
				}
			}
			param.put("certNo", getRequest().getParameter("certNo"));
			param.put("userNo", getUser().getUserNo());
			mobileUserService.addContact(param );
			putResultMessageForWap(resultMap,null);
		}catch(Exception e) {
			e.printStackTrace();
			putResultMessageForWap(resultMap,"系统忙,请稍后再试");
		}
		if(ajax) {
			refreshContactUsedByFillOrder();
			return "add_contact_ajax";
		}
		/*JSONObject jsonObj = JSONObject.fromObject(resultMap);
		super.sendAjaxResult(jsonObj.toString());*/
		return "add_contact";
	}
	
	/**
	 * 填写订单时刷新游玩人列表 . 
	 */
	public void refreshContactUsedByFillOrder() {
		String ids = getRequest().getParameter("choosedIds");
		if(!StringUtils.isEmpty(ids)){
			String[] idsArr = ids.split(",");
			getRequest().setAttribute("choosedIdList", Arrays.asList(idsArr));
		}
		// 查询用户列表 
		getUserContacts();
	}
	
	/**
	 * 获取游玩人列表 
	 */
	public void getUserContacts() {
		try {
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("userNo", getUser().getUserNo());
			List<MobileReceiver> contactList = mobileUserService.getContact(param );
			getRequest().setAttribute("contactList", contactList);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除游玩人  
	 * @return
	 */
	@Action("remove_contact")
	public String removeContact() {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		String receiverId = getRequest().getParameter("receiverId");
		try {
			if(StringUtils.isEmpty(receiverId)) {
				putResultMessageForWap(resultMap,"被删除用户id不存在");
			} else {
				Map<String, String> param = new HashMap<String,String>();
				param.put("receiverId", receiverId);
				mobileUserService.removeContact(param );
				putResultMessageForWap(resultMap,null);
			}
		}catch(Exception e) {
			e.printStackTrace();
			putResultMessageForWap(resultMap,"系统忙,请稍后再试");
		}
		
		if(ajax) {
			refreshContactUsedByFillOrder();
			return "add_contact_ajax";
		}
		
		/*JSONObject jsonObj = JSONObject.fromObject(resultMap);
		super.sendAjaxResult(jsonObj.toString());*/
		return "remove_contact";
	}
	
	 /** 
     * 判断用户是否登录. 
	 * @return
	 */
	@Action("user_favoritor")
	public String userFavoritor() {
		// 门票 
		if(Constant.CLIENT_FAVORITE_TYPE.PLACE.getCode().equals(objectType)) {
			return "user_favoritor_place";
	    // 线路 
		} else {
			return "user_favoritor_route";
		}
		
	}
	/**
	 * 用户积分
	 * @return
	 */
	@Action("mypoint")
	public String userPoint(){
		
		//===========================积分
		//当前积分 
		currentPoint = userUserProxy.getUserUserByPk(getUser().getId()).getPoint(); 
		currentPoint = null == currentPoint ? 0L : currentPoint; 
		//已用积分 
		usedPoint = userUserProxy.getUsedUsersPoint(getUser().getId()); 
		usedPoint = null == usedPoint ? 0L : usedPoint; 
		//年底到期积分 
		aboutToExpiredPoint = userUserProxy.getAboutToExpiredUsersPoint(getUser().getId()); 
		aboutToExpiredPoint = null == aboutToExpiredPoint ? 0L : aboutToExpiredPoint; 
		//===========================推荐商品
		List<String> productIds=ClutterConstant.getProductIds();//线路列表route
		List<String> priaceIds=ClutterConstant.getPlacedIds();//景点列表place
		
		//生成随机不重复的景点，路线商品列表
		pointProductList=this.products(productIds,0);//0是product
		pointPlaceList=this.places(priaceIds,1);//1是place
		
		return "mypoint";
	}
	/**
	 * 查询自由行产品详情
	 * @return
	 */
	public MobileProductRoute getProduct(String productId){
		Map<String,Object> param = new HashMap<String,Object>();
		MobileProductRoute mpr=null;
		try {
			param.put("productId", Long.valueOf(productId));
			UserUser u = getUser();
			if(null != u) {
				param.put("userNo", u.getUserNo());
				param.put("userId", u.getId());
			}
			mpr = mobileProductService.getRouteDetail(param);
			if(null != mpr && !StringUtils.isEmpty(mpr.getProductName())) {
				mpr.setProductName(ClientUtils.filterQuotationMarks(mpr.getProductName()));
			}
			getRequest().setAttribute("mpr", mpr);
			// 设置图片前缀 
			this.setImagePrefix();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mpr;
	}
	/**
	 * 查询景点产品详情
	 * @param placeId
	 * @return
	 */
	public PlaceSearchInfo getPlace(String placeId){
		PlaceSearchInfo placeSearchInfo=placeSearchInfoService.getPlaceSearchInfoByPlaceId(Long.valueOf(placeId));
		if(placeSearchInfo!=null && placeSearchInfo.getProductsPrice()!=null){
			Long productsPrice=PriceUtil.getLongPriceYuan(Long.valueOf(placeSearchInfo.getProductsPrice()));
			placeSearchInfo.setProductsPrice(String.valueOf(productsPrice));
		}
		
		return placeSearchInfo;
	}
	//路线列表
	public List<MobileProductRoute> products(List<String> listAll,int flag){
		List<MobileProductRoute> withoutProducts=new ArrayList<MobileProductRoute>();
		List<String> withoutProductList=new ArrayList<String>();
		for(int i=0;i<2;i++){
			String productId=this.withoutProductList(withoutProductList,listAll,flag);
			withoutProductList.add(productId);
			withoutProducts.add(this.getProduct(productId));
		}
		return withoutProducts;
	}
	//景点列表
	public List<PlaceSearchInfo> places(List<String> listAll,int flag){
		List<PlaceSearchInfo> withoutPlaces=new ArrayList<PlaceSearchInfo>();
		List<String> withoutProductList=new ArrayList<String>();
		for(int i=0;i<2;i++){
			String placeId=this.withoutProductList(withoutProductList,listAll,flag);
			withoutProductList.add(placeId);
			withoutPlaces.add(this.getPlace(placeId));
		}
		return withoutPlaces;
	}
	//生成随机数
	public int roandmIndex(int size){
		Random r = new Random();
		int roandm=0;
		roandm=Math.abs(r.nextInt(size));
		
		return roandm;
	}
	//根据产品列表生成1个随机的不重复的产品
	public String  withoutProductList(List<String> withoutProductList,List<String> listAll,int flag){
		while(true){
			int index = roandmIndex(listAll.size());
			if(!getProductId(withoutProductList,index,listAll,flag)){
				 return listAll.get(index);
			}
		}
	}
	//去重复判断
	public boolean getProductId(List<String> withoutProductList,int index,List<String> listAll,int flag){
		String pOld=listAll.get(index);
		if(withoutProductList!=null && withoutProductList.size()>0){
			for(String p : withoutProductList){
				//线路
				if(flag==0){
					MobileProductRoute p1=this.getProduct(pOld);
					if(p.equals(pOld) || p1==null){
						return true	;
					}
				}//景点
				else{
					PlaceSearchInfo o1=this.getPlace(pOld);
					if(p.equals(pOld) || o1==null){
						return true	;
					}
				}
			}
		}else {
			//线路
			if(flag==0){
				MobileProductRoute p1=this.getProduct(pOld);
				if(p1==null){
					return true	;
				}
			}//景点
			else{
				PlaceSearchInfo o1=this.getPlace(pOld);
				if(o1==null){
					return true	;
				}
			}
		}
		return false;
	}
	
	/**
	 * 格式化维度信息
	 */
	private String getCmtLatitudeStr() {
		StringBuffer sb = new StringBuffer("");
		// 获取指标
		for (int i = 0; i < latitudeIds.length; i++) {
			sb.append(latitudeIds[i]+"_").append(scores[i]);
			if( i != latitudeIds.length -1 ) {
				sb.append(",");
			}
		}

		return sb.toString();
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobileOrEMail() {
		return mobileOrEMail;
	}

	public void setMobileOrEMail(String mobileNo) {
		this.mobileOrEMail = mobileNo;
	}
	
	public String getAuthenticationCode() {
		return authenticationCode;
	}

	public void setAuthenticationCode(String authenticationCode) {
		this.authenticationCode = authenticationCode;
	}

	public boolean isAjax() {
		return ajax;
	}

	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}
	
	public String getCollectionType() {
		return collectionType;
	}


	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}

	public int getPage() {
		return page;
	}


	public void setPage(int page) {
		this.page = page;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String[] getLatitudeIds() {
		return latitudeIds;
	}

	public void setLatitudeIds(String[] latitudeIds) {
		this.latitudeIds = latitudeIds;
	}

	public String[] getScores() {
		return scores;
	}

	public void setScores(String[] scores) {
		this.scores = scores;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	
	public String getObjectImageUrl() {
		return objectImageUrl;
	}

	public void setObjectImageUrl(String objectImageUrl) {
		this.objectImageUrl = objectImageUrl;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
    public String getBonusType() {
		return bonusType;
    }

	public void setBonusType(String bonusType) {
		this.bonusType = bonusType;
	}
	
	public void setMobileUserService(IClientUserService mobileUserService) {
		this.mobileUserService = mobileUserService;
	}
	
	public String getActivityChannel() {
		return activityChannel;
	}

	public void setActivityChannel(String activityChannel) {
		this.activityChannel = activityChannel;
	}
	
	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public UserUserProxy getUserUserProxy() {
		return userUserProxy;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public Long getCurrentPoint() {
		return currentPoint;
	}

	public void setCurrentPoint(Long currentPoint) {
		this.currentPoint = currentPoint;
	}

	public Long getAboutToExpiredPoint() {
		return aboutToExpiredPoint;
	}

	public void setAboutToExpiredPoint(Long aboutToExpiredPoint) {
		this.aboutToExpiredPoint = aboutToExpiredPoint;
	}

	public Long getUsedPoint() {
		return usedPoint;
	}

	public void setUsedPoint(Long usedPoint) {
		this.usedPoint = usedPoint;
	}

	public IClientProductService getMobileProductService() {
		return mobileProductService;
	}

	public void setMobileProductService(IClientProductService mobileProductService) {
		this.mobileProductService = mobileProductService;
	}
	public List<MobileProductRoute> getPointProductList() {
		return pointProductList;
	}

	public void setPointProductList(List<MobileProductRoute> pointProductList) {
		this.pointProductList = pointProductList;
	}

	public List<PlaceSearchInfo> getPointPlaceList() {
		return pointPlaceList;
	}

	public void setPointPlaceList(List<PlaceSearchInfo> pointPlaceList) {
		this.pointPlaceList = pointPlaceList;
	}
	public PlaceSearchInfoService getPlaceSearchInfoService() {
		return placeSearchInfoService;
	}

	public void setPlaceSearchInfoService(
			PlaceSearchInfoService placeSearchInfoService) {
		this.placeSearchInfoService = placeSearchInfoService;
	}
	
}
