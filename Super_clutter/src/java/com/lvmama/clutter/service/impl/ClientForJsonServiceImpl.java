package com.lvmama.clutter.service.impl;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.lvmama.clutter.service.IClientForJsonService;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.CouponUtils;
import com.lvmama.clutter.xml.lv.po.RequestObject;
import com.lvmama.clutter.xml.lv.po.RequestOrderItem;
import com.lvmama.clutter.xml.lv.po.ResponseBody;
import com.lvmama.clutter.xml.lv.po.ResponseObject;
import com.lvmama.comm.bee.po.client.ComClientLog;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderChannel;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.GroupDreamService;
import com.lvmama.comm.bee.service.com.ISmsHistoryService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.view.ViewPageJourneyService;
import com.lvmama.comm.bee.service.view.ViewPageService;
import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Coupon;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.bee.vo.view.MarkCouponUserInfo;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.client.RecommendInfoClient;
import com.lvmama.comm.pet.client.UserClient;
import com.lvmama.comm.pet.po.client.ClientCmtLatitude;
import com.lvmama.comm.pet.po.client.ClientCmtPlace;
import com.lvmama.comm.pet.po.client.ClientGroupon2;
import com.lvmama.comm.pet.po.client.ClientOrderCmt;
import com.lvmama.comm.pet.po.client.ClientOrderReport;
import com.lvmama.comm.pet.po.client.ClientPicture;
import com.lvmama.comm.pet.po.client.ClientPlace;
import com.lvmama.comm.pet.po.client.ClientProduct;
import com.lvmama.comm.pet.po.client.ClientTimePrice;
import com.lvmama.comm.pet.po.client.ClientUser;
import com.lvmama.comm.pet.po.client.ClientViewJouney;
import com.lvmama.comm.pet.po.client.ViewClientOrder;
import com.lvmama.comm.pet.po.client.ViewOrdPerson;
import com.lvmama.comm.pet.po.comment.CmtLatitudeStatistics;
import com.lvmama.comm.pet.po.comment.DicCommentLatitude;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.money.CashAccount;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlacePhoto;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.po.pub.ComUserFeedback;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.client.ComClientService;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.comment.CmtLatitudeStatistisService;
import com.lvmama.comm.pet.service.comment.DicCommentLatitudeService;
import com.lvmama.comm.pet.service.favor.FavorService;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.place.PlaceCoordinateBaiduService;
import com.lvmama.comm.pet.service.place.PlacePhotoService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.pet.service.pub.ComUserFeedbackService;
import com.lvmama.comm.pet.service.search.PlaceSearchInfoService;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.pet.service.user.UserActionCollectionService;
import com.lvmama.comm.pet.service.user.UserCooperationUserService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.pet.vo.PlaceCoordinateVo;
import com.lvmama.comm.pet.vo.ProductList;
import com.lvmama.comm.pet.vo.mark.ValidateCodeInfo;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.ClientConstants;
import com.lvmama.comm.utils.CommentUtil;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HotelUtils;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UserLevelUtils;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtLatitudeVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
import com.lvmama.comm.vo.comment.DicCommentLatitudeVO;
import com.lvmama.comm.vo.comment.PlaceCmtCommentVO;
import com.lvmama.comm.vo.comment.PlaceCmtScoreVO;
import com.lvmama.comm.vo.comment.ProductCmtCommentVO;
import com.lvmama.comm.vo.enums.PlacePhotoTypeEnum;

public class ClientForJsonServiceImpl implements IClientForJsonService{

	private ComPictureService comPictureService;
	
	private MarkCouponService  markCouponService;
	private RecommendInfoClient recommendInfoClient;
	
	/**
	 * 日志输出
	 */
	private final Log log = LogFactory.getLog(getClass());
	/**
	 * 产品服务
	 */
	private ProductSearchInfoService productSearchInfoService;
	/**
	 * sso服务
	 */
	private UserUserProxy userUserProxy;
	/**
	 * UserClient
	 */
	private UserClient userClient;
	/**
	 * 现金账户服务
	 */
	private CashAccountService cashAccountService;

	/**
	 * 自由行行程
	 */
	private ViewPageJourneyService viewPageJourneyService;
	/**
	 * 页面展示
	 */
	private ViewPageService viewPageService;
	
	/**
	 * 订单服务
	 */
	private OrderService orderServiceProxy;
	/**
	 * 意见反馈
	 * @param list
	 * @return
	 */
	private ComUserFeedbackService comUserFeedbackService;
 
	private TopicMessageProducer orderMessageProducer;

	/**
	 * 第三方用户服务
	 */
	private UserCooperationUserService userCooperationUserService;
	
	private CmtCommentService cmtCommentService;
	
	private ProdProductPlaceService  prodProductPlaceService;
	
	private ProdProductService prodProductService;
	
	private ISmsHistoryService smsHistoryService;

	String COMMENT_SHARE_TEMPLATE="我刚在 @驴妈妈旅游网，点评了“%s”，点评具体内容 %s";

	private ComClientService comClientService;

	
	private CmtLatitudeStatistisService cmtLatitudeStatistisService;
	
	private DicCommentLatitudeService dicCommentLatitudeService;
	
	private PlaceService placeService;
	
	private PlacePhotoService placePhotoService;
	
	private GroupDreamService  groupDreamService;
	
	private PlaceSearchInfoService placeSearchInfoService;
	
	private PlaceCoordinateBaiduService placeCoordinateBaiduService;
	
	private FavorService favorService;

	
	@Override
	public String  queryUserCouponInfo(RequestObject ro) {
		try {
		List<MarkCouponUserInfo> list = new ArrayList<MarkCouponUserInfo>();
		String userId = ro.getHead().getUserId();
		String state = ro.getBody().getCouponState();
		if (!ro.getBody().paramterIsError()){
			UserUser user = userUserProxy.getUserUserByUserNo(userId);
			list = markCouponService.queryMobileUserCouponInfoByUserId(user.getId());
			ResponseObject responseObj = new ResponseObject(null, "1", true);
			ResponseBody responseBody = new ResponseBody();
			responseBody.setUserCouponInfoList(CouponUtils.filterCouponInf(list, state));
			responseObj.setBody(responseBody);
			return this.toJson(responseObj);
		} else {
			return this.errorResponse(ro);
		}

		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	
	private String toJson(ResponseObject ro){
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setJsonPropertyFilter(new PropertyFilter(){  

			public boolean apply(Object source/* 属性的拥有者 */ , String name /*属性名字*/ , Object value/* 属性值 */ ){  
				if(value==null){
					return true;
				}
				return false;
			}  
			});  
		JSONObject json = JSONObject.fromObject(ro,jsonConfig); 
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,new JsonValueProcessor() {
			private final String format="yyyy-MM-dd";
			@Override
			public Object processObjectValue(String arg0, Object value, JsonConfig arg2) {
				if(value==null)
				{
					return "";
				}
				if (value instanceof Date) {
					String str = new SimpleDateFormat(format).format((Date) value);
					return str;
				} else {
					return value.toString();
				}
				
			}
			
			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {
				// TODO Auto-generated method stub
				return null;
			}
		}
			);
		//JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"})); 
		return json.toString();
		
	}
	
	
	
	@Override
	public String queryPlaceList(RequestObject ro) {
		
//		String pageSize = "5";
//		String page = "1";
//		
//		String placeId = ro.getBody().getPlaceId();
//		String stage = ro.getBody().getStage();
//		pageSize = ro.getBody().getPageSize();
//		page  = ro.getBody().getPage();
//		if (!ro.getBody().paramterIsError()){
//			Page<ViewPlace> vpconfig = lvmamaClientPlaceRemoteService.getPlaceByPageConfig(stage,placeId,Integer.parseInt(pageSize),Integer.parseInt(page));
//			ResponseObject responseObj = new ResponseObject(null, vpconfig.getCurrentPage()+"", vpconfig.getCurrentPage()==vpconfig.getTotalPages());
//			ResponseBody responseBody = new ResponseBody();
//			responseBody.setPlaceList(vpconfig.getItems());
//			responseObj.setBody(responseBody);
//			return this.toJson(responseObj);
//		} else {
//			return this.errorResponse(ro);
//		}
		return "";
	
	}




 
	@Override
	public String queryGps(RequestObject ro) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public String queryAutoLocationInfos(RequestObject ro) {
//		String name = ro.getBody().getKeyWord();
//		if(!ro.getBody().paramterIsError()){
//			ViewPlace vp = this.lvmamaClientPlaceRemoteService.getDestByName(StringUtils.trimAllWhitespace(name));
//			ResponseObject responseObj = new ResponseObject(null, "1", true);
//			ResponseBody responseBody = new ResponseBody();
//			if(vp!=null){
//				responseBody.setPlace(vp);
//			}
//			responseObj.setBody(responseBody);
//			return this.toJson(responseObj);
//		} else {
//			return this.errorResponse(ro);
//		}
		return "";
	
	}



	@Override
	public ResponseObject queryDestDetails(RequestObject ro) {
		String placeId = ro.getBody().getPlaceId();
		ResponseObject responseObj = new ResponseObject(null, "1", true);
		if(!ro.getBody().paramterIsError()){
			try {
			Place place = this.placeService.queryPlaceByPlaceId(Long.valueOf(placeId));
			ClientPlace vp = new ClientPlace();
			BeanUtils.copyProperties(place, vp);
			vp.setRecommendReason(place.getRemarkes());
		    vp.setId(place.getPlaceId());
			
			ResponseBody responseBody = new ResponseBody();
			responseBody.setPlace(vp);
			responseObj.setBody(responseBody);

			}catch (Exception ex) {
				ex.printStackTrace();
			}
			return responseObj;
		} else {
			return null;
		}
		
	}



	


	@Override
	public String queryTicketsList(RequestObject ro) {
		String placeId = ro.getBody().getPlaceId();

		if(!ro.getBody().paramterIsError()){
			Long pid = Long.valueOf(placeId);
	
		ProductList productList = productSearchInfoService.getIndexProductByPlaceIdAnd4TypeAndTicketBranch(pid, 100L, "FRONTEND");
		//getIndexProductByPlaceIdAnd4TypeAndTicketBranch
		ResponseObject responseObj = new ResponseObject(null, "1", true);
		ResponseBody responseBody = new ResponseBody();
		List<ClientProduct> cpList= new ArrayList<ClientProduct>();
		ClientUtils.copyProductList(productList.getProdBranchTicketList(),cpList,Constant.PRODUCT_TYPE.TICKET.name());
		
		for (ClientProduct clientProduct : cpList) { 
			ProdProduct pp = prodProductService.getProdProductById(clientProduct.getProductId());
			if (pp != null) {
				clientProduct.setProductType(pp.getProductType()); 
			}
			
		}
		
		responseBody.setProductListInfo(cpList);
		responseObj.setBody(responseBody);
		return this.toJson(responseObj);
	} else {
		return this.errorResponse(ro);
	}
	}

	@Override
	public String clientGetVlidateCode(RequestObject ro) {
		String channel = ro.getHead().getFirstChannel();
		String mobile = ro.getBody().getMobile();
		if(!ro.getBody().paramterIsError()){
			boolean b = StringUtil.validMobileNumber(mobile);
			ResponseObject responseObj = new ResponseObject(null, "1", true);
			String message="";
			if (!b) {
				message = "mobileValidateError";
			} else {
				try {
					boolean flag = userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.MOBILE,mobile);
					if (flag) {
						  UserUser userUser = new UserUser();
					      userUser.setMobileNumber(mobile);
					
					      String code = userClient.sendAuthenticationCode(UserUserProxy.USER_IDENTITY_TYPE.MOBILE, userUser, com.lvmama.comm.vo.Constant.SMS_SSO_TEMPLATE.SMS_REGIST_AUTHENTICATION_CODE.name());
					      if (code != null) {
							message = "commitsuccess";
						} else {
							message = "error";
						}

					} else {
						message = "mobile_in_users";
					}
				} catch (Exception e) {
					message = "error";
					e.printStackTrace();
				}
			}
			responseObj.getHead().setErrorMessage(ClientConstants.getErrorInfo().get(message));
			return this.toJson(responseObj);
		} else {
			return this.errorResponse(ro);
		}
		
	}

	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}



	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}



	private boolean validateAuthenticationCode(String mobile, String code, String channel){
		 boolean flag = userUserProxy.validateAuthenticationCode(UserUserProxy.USER_IDENTITY_TYPE.MOBILE, code, mobile);
		  if(flag){
		    return true;
		  }
		  return false;
	}
	
	private void checkUserLoginDate (UserUser user,ClientUser cu,HttpServletRequest request) {
		Date lastLoginDate = user.getLastLoginDate();
		Date currentDate = new Date();
		if (lastLoginDate!=null&&com.lvmama.comm.utils.DateUtil.compareDateLessOneDayMore(currentDate, lastLoginDate)) {
			user.setLastLoginDate(new Date());
			userUserProxy.update(user);//设置上次登录时间
			UserActionCollectionService userActionCollectionService = (UserActionCollectionService) SpringBeanProxy.getBean("userActionCollectionService");
			if (null != userActionCollectionService) {
				userActionCollectionService.save(user.getId(), InternetProtocol.getRemoteAddr(request), InternetProtocol.getRemotePort(request), "LOGIN", null);
			}
			userUserProxy.addUserPoint(user.getId(), "POINT_FOR_LOGIN", null, "客户端登陆");
			cu.setPoint(5L);
		} else {
			cu.setPoint(0L);
		}
	}

	@Override
	public String clientLogin(RequestObject ro,HttpServletRequest request) {
		try {
		if (!StringUtil.isEmptyString(ro.getBody().getLoginChannel())) {
			return this.loginByCooperationChanel(ro, request);
		}
		
		String userName = ro.getBody().getUserName();
		String password = ro.getBody().getPassword();
		
		if (!ro.getBody().paramterIsError()) {
			ClientUser cu = new ClientUser();
			UserUser user = userUserProxy.login(StringUtils.trimAllWhitespace(userName), StringUtils.trimAllWhitespace(password));
			ResponseObject responseObj = new ResponseObject(null, "1", true);
			if (user != null) {
				this.getUserByMap(user, cu);
				this.checkUserLoginDate(user, cu,request);
			} else {
				responseObj.getHead().setErrorMessage("用户名或密码错误！");
			}
			
			ResponseBody responseBody = new ResponseBody();
			responseBody.setClientUser(cu);
			responseObj.setBody(responseBody);
			
			return this.toJson(responseObj);
		} else {
			return this.errorResponse(ro);
		}
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return "{}";
		
	}
	
	
	private List<UserCooperationUser> getCooperationUserByChannelUID (String channel,String uid) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cooperationUserAccount", uid);
		parameters.put("cooperation", channel);
		List<UserCooperationUser> cooperationUseres = userCooperationUserService.getCooperationUsers(parameters);
		return cooperationUseres;
	}
	
	private UserUser registByCooperationInfo(RequestObject ro,HttpServletRequest request,UserUser users) {
		UserUser user = UserUserUtil.genDefaultUser();
		user.setChannel(ro.getHead().getFirstChannel());
		user.setUserName("From "+ro.getBody().getLoginChannel().toLowerCase()+" weibo " + (null != ro.getBody().getScreenName() ? ro.getBody().getScreenName() : "") + "("
				+ ro.getBody().getCooperationUid() + ")");
		UserCooperationUser cu = new UserCooperationUser();
		cu.setCooperation(ro.getBody().getLoginChannel());
		cu.setCooperationUserAccount(ro.getBody().getCooperationUid());
		userUserProxy.registerUserCooperationUser(user, cu);
		users = userUserProxy.getUserUserByUserNo(user.getUserNo());
		return users;
	}
	
	private UserUser registByUser(RequestObject ro,HttpServletRequest request,UserUser users) {
		UserUser user = UserUserUtil.genDefaultUser();
		user.setChannel(ro.getHead().getFirstChannel());
		user.setUserName("From "+ro.getBody().getLoginChannel().toLowerCase()+" weibo " + (null != ro.getBody().getScreenName() ? ro.getBody().getScreenName() : "") + "("
				+ ro.getBody().getCooperationUid() + ")");		
		userUserProxy.register(user);
		users = userUserProxy.getUserUserByUserNo(user.getUserNo());
		return users;
	}
	
	private String loginByCooperationChanel(RequestObject ro,HttpServletRequest request){
		try {
		if ("SINA".equals(ro.getBody().getLoginChannel())||"TENCENT".equals(ro.getBody().getLoginChannel())) {
			UserUser users = null;
			List<UserCooperationUser> cooperationUseres = this.getCooperationUserByChannelUID(ro.getBody().getLoginChannel(),ro.getBody().getCooperationUid());
			if (null == cooperationUseres || cooperationUseres.isEmpty()) {
				users = this.registByCooperationInfo(ro, request, users);
			} else {
				UserCooperationUser ucu = cooperationUseres.get(0);
				users = userUserProxy.getUserUserByPk(ucu.getUserId());
				if("N".equals(users.getIsValid())) {
		
					users = this.registByUser(ro, request, users);
					ucu.setUserId(users.getId());
					userCooperationUserService.update(ucu);
				}
			}
			
			ClientUser cu = new ClientUser();
			
			cu.setUserId(users.getUserId());
			cu.setEmail(users.getEmail());
			cu.setImageUrl(Constant.PIC_HOST + users.getImageUrl());
			cu.setMobileNumber(StringUtil.trimNullValue(users.getMobileNumber()));
			cu.setNickName(StringUtil.trimNullValue(users.getNickName()));
			cu.setRealName(StringUtil.trimNullValue(users.getRealName()));
			cu.setUserName(StringUtil.trimNullValue(users.getUserName()));
			cu.setPoint(users.getPoint());
			cu.setLastLoginTime(DateUtil.getFormatDate(users.getLastLoginDate(), "yyyy-MM-dd HH:mm:ss"));
			Long point = 0L;
			if (users.getPoint() != null) {
				point = users.getPoint();
			}
			
			this.checkUserLoginDate(users, cu, request);
			cu.setLevel(UserLevelUtils.getLevel(point));
			
			cu.setWithdraw(users.getWithdraw() == null ? "0元" : PriceUtil
					.convertToYuan(users.getWithdraw())
					+ "元");
			
	
			CashAccount ba =  cashAccountService.queryCashAccountByUserId(users.getId());
			if(ba!=null) {
				cu.setAwardBalance(ba.getBonusBalanceFloat()+"元");
			} else {
				cu.setAwardBalance("0元");
			}
			
			cu.setCashBalance(ba.getTotalCashBalanceYuan() + "元");
			ResponseObject responseObj = new ResponseObject(null, "1", true);
			ResponseBody responseBody = new ResponseBody();
			responseBody.setClientUser(cu);
			responseObj.setBody(responseBody);
			return this.toJson(responseObj);
			
		}
		} catch(Exception ex){
			ex.printStackTrace();
			return "{\"message\":\"error\"}";
		}
		return "{}";
	}

	private ClientUser getUserByMap(UserUser user, ClientUser cu) {
		cu.setUserId(user.getUserId());
		cu.setEmail(user.getEmail());
		cu.setImageUrl(Constant.PIC_HOST + user.getImageUrl());
		cu.setMobileNumber(StringUtil.trimNullValue(user.getMobileNumber()));
		cu.setNickName(StringUtil.trimNullValue(user.getNickName()));
		cu.setRealName(StringUtil.trimNullValue(user.getRealName()));
		cu.setUserName(StringUtil.trimNullValue(user.getUserName()));
		cu.setPoint(user.getPoint());
		cu.setLastLoginTime(DateUtil.getFormatDate(user.getLastLoginDate(), "yyyy-MM-dd HH:mm:ss"));
		Long point = 0L;
		if (user.getPoint() != null) {
			point = user.getPoint();
		}
		cu.setLevel(UserLevelUtils.getLevel(point));
		
		cu.setWithdraw(user.getWithdraw() == null ? "0元" : PriceUtil
				.convertToYuan(user.getWithdraw())
				+ "元");
	
		CashAccount ba =  cashAccountService.queryCashAccountByUserId(user.getId());
		
		if(ba!=null) {
			cu.setAwardBalance(ba.getBonusBalanceFloat()+"元");
		} else {
			cu.setAwardBalance("0元");
		}
		cu.setCashBalance(ba.getTotalCashBalanceYuan() + "元");
		return cu;
	}


	@Override
	public String clientSubRegister(RequestObject ro)  {
		String mobile = ro.getBody().getMobile();
		String pwd = ro.getBody().getPassword();
		String code = ro.getBody().getValidateCode();
		String channel = ro.getHead().getFirstChannel();
		if (!ro.getBody().paramterIsError()) {
			String message = "";
			ClientUser cu = new ClientUser();
			ResponseObject responseObj = new ResponseObject(null, "1", true);
			boolean b = validateAuthenticationCode(mobile, StringUtils.trimAllWhitespace(code), channel);
			boolean isUserRegistrable=false;
			isUserRegistrable = userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.MOBILE, mobile);
			log.error("b: " + b + ", isUserRegistrable: " + isUserRegistrable);
			if (b&&isUserRegistrable) {
				UserUser user = UserUserUtil.genDefaultUserByMobile(mobile);
			    user.setRealPass(pwd);
			    user.setGroupId(channel);
			    user.setChannel(channel);
			    try {
					user.setUserPassword(UserUserUtil.encodePassword(user.getRealPass()));
				} catch (NoSuchAlgorithmException e1) {
					log.error(this.getClass(),e1);
				}
			    try {
			     user = this.userUserProxy.register(user);
			    } catch (Exception e) {
			    	e.printStackTrace();
			    }
				if (user != null) {
					this.getUserByMap(user, cu);
					cu.setPoint(100L);
					String couponCode = this.subCouponCodeByRegister(cu.getUserId());
					cu.setCouponInfo("注册成功获得8元优惠券:"+couponCode);
					userUserProxy.addUserPoint(user.getId(), "POINT_FOR_NORMAL_REGISTER", null, "客户端注册");
					message = "commitsuccess";
				} else {
					message = "mobileValidateError";
				}

			} else {
				if(!isUserRegistrable){
					message = "mobileIsUsed";
				} else {
					message = "validateCodeError";
				}
			}
			responseObj.getHead().setErrorMessage(ClientConstants.getErrorInfo().get(message));
			ResponseBody responseBody = new ResponseBody();
			responseBody.setClientUser(cu);
			responseObj.setBody(responseBody);
			return this.toJson(responseObj);
		} else {
			return this.errorResponse(ro);
		}
		
	}

	private String subCouponCodeByRegister(String userId){
		UserUser user = userUserProxy.getUserUserByUserNo(userId);
		String couponId = ClutterConstant.getClientRegisterCouponId();
		MarkCoupon mc = markCouponService.selectMarkCouponByPk(Long.valueOf(couponId));
		if(!mc.isOverDue()) {
		MarkCouponCode markCouponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(Long.valueOf(couponId));
		
		markCouponService.bindingUserAndCouponCode(user, markCouponCode.getCouponCode());
		return markCouponCode.getCouponCode();
		}
		return null;
		
	}




	@Override
	public String getMainProdTimePrice(RequestObject ro) {
		Long productId = Long.valueOf(ro.getBody().getProductId());
		if (!ro.getBody().paramterIsError()) {
			Long branchId = null;
			if(!StringUtil.isEmptyString(ro.getBody().getBranchId())){
				branchId = Long.valueOf(ro.getBody().getBranchId());
			}
			List<TimePrice>  timePriceList = prodProductService.getMainProdTimePrice(productId, branchId);
			List<ClientTimePrice> ctpList = new ArrayList<ClientTimePrice>();
			for (TimePrice timePrice : timePriceList) {
				ClientTimePrice ctp = new ClientTimePrice();
				ctp.setDate(DateUtil.getFormatDate(timePrice.getSpecDate(),
				"yyyy-MM-dd"));
				ctp.setPrice(timePrice.getPriceF());
				ctp.setMarketPrice(timePrice.getMarketPriceF()+"");
				ctpList.add(ctp);			
			}
			ResponseObject responseObj = new ResponseObject(null, "1", true);
			ResponseBody responseBody = new ResponseBody();
			if (!ctpList.isEmpty()) {
				responseBody.setTimePriceList(ctpList);
			}
			responseObj.setBody(responseBody);
	
			return this.toJson(responseObj);
		} else {
			return this.errorResponse(ro);
		}
	}
	
	public String querySimpleProductDetails(RequestObject ro){
		String productId = ro.getBody().getProductId();
		if(!ro.getBody().paramterIsError()){
			Long pid = Long.valueOf(productId);
			ProdProductBranch prodBranches = prodProductService.getProductDefaultBranchByProductId(pid);
			ProdProduct pp = prodBranches.getProdProduct();
			List<ClientProduct> vpsiList = new ArrayList<ClientProduct>();
	
			ClientProduct vps = new ClientProduct();
			//团购产品统一不能使用优惠
			vps.setCouponAble("false");
			//vps.setCouponAble(pp.getCouponAble());
			vps.setProductId(prodBranches.getProductId());
			vps.setProdBranchId(prodBranches.getProdBranchId());
			vps.setShortName(prodBranches.getBranchName());
			vps.setProductName(prodBranches.getProdProduct().getProductName());
			vps.setMinimum(prodBranches.getMinimum());
			vps.setMaximum(prodBranches.getMaximum());
			vps.setPayToSupplier(pp.getPayToSupplier());
			vps.setPayToLvmama(pp.getPayToLvmama());
			vps.setSellPrice(prodBranches.getSellPrice());
			vps.setProductType(pp.getProductType());
			vps.setSubProductType(pp.getSubProductType());
			vps.setMarketPrice(prodBranches.getMarketPrice());
			vpsiList.add(vps);
			ResponseObject responseObj = new ResponseObject(null, "1", true);
			ResponseBody responseBody = new ResponseBody();
			if (!vpsiList.isEmpty()) {
				responseBody.setProductListInfo(vpsiList);
			}
			responseObj.setBody(responseBody);
			return this.toJson(responseObj);
		} else {
			return this.errorResponse(ro);
		}
	}
	
	public String queryProductDetails(RequestObject ro){
		String productId = ro.getBody().getProductId();
		if(!ro.getBody().paramterIsError()){
			Long pid = Long.valueOf(productId);
			ViewPage viewPage = viewPageService.selectByProductId(pid); 
			if(viewPage==null){ 
				return "{}"; 
			}
			ViewPage vp = viewPageService.getViewPage(viewPage.getPageId());
			if(vp == null){
				return "{}";
			}
			ProdProduct pp = prodProductService.getProdProductById(pid);
			String shareContent = String.format("我刚在 @驴妈妈旅游网 发现一个不错的产品，“%s”", com.lvmama.comm.utils.StringUtil.subStringStr(pp.getProductName(), 60));
			String shareUrl =  Constant.WWW_HOST+"/product/"+pid;
			List<ViewJourney> vj = viewPageJourneyService.getViewJourneysByProductId(pid);
			List<ClientViewJouney> cvjList = new ArrayList<ClientViewJouney>();
			if(!vj.isEmpty()){
				for (ViewJourney viewJourney : vj) {
					viewJourney.setPlaceList(null);
					viewJourney.setProdTargetId(null);
					ClientViewJouney cvj = new ClientViewJouney();
					cvj.setContent(viewJourney.getContent());
					cvj.setTitle(viewJourney.getTitle());
					cvj.setSeq(viewJourney.getSeq());
					cvj.setJourneyId(viewJourney.getJourneyId());
					cvj.setDinner(viewJourney.getHotel());
					cvj.setHotel(viewJourney.getHotel());
					cvjList.add(cvj);
				}
			}
			ResponseObject responseObj = new ResponseObject(null, "1", true);
			ResponseBody responseBody = new ResponseBody();
			for (ViewContent viewContent : vp.getContentList()) {
				viewContent.setContent(StringUtil.filterOutHTMLTags(viewContent.getContent()));
			}
			responseBody.setContentList(vp.getContentList());
			responseBody.setShareContent(shareContent);
			responseBody.setShareUrl(shareUrl);
			responseBody.setPictureList(this.comPictureService.getPictureByPageId(vp.getPageId()));
			responseBody.setClientJourneyList(cvjList);
			responseObj.setBody(responseBody);
			return this.toJson(responseObj);
		} else {
			return this.errorResponse(ro);
		}

	}
	
	
	public String getUserByUserId(String userId) {
		try {
		ClientUser cu = new ClientUser();
		UserUser user = userUserProxy.getUserUserByUserNo(userId);
		if (user != null) {
			this.getUserByMap(user, cu);
		}
		ResponseObject responseObj = new ResponseObject(null, "1", true);
		ResponseBody responseBody = new ResponseBody();
		responseBody.setClientUser(cu);
		responseObj.setBody(responseBody);
		
		return this.toJson(responseObj);
	}  catch(Exception ex){
		ex.printStackTrace();
	}
		return "{}";
	}
	
	
	public ResponseObject queryTimePriceByProductIdAndDate(RequestObject ro){
		String productId = ro.getBody().getProductId();
		String value = null;
		if( ro.getBody().getFilterProductMap()!=null){
			value = ro.getBody().getFilterProductMap().get(productId);
		}
	
		
		String date = ro.getBody().getDate();
		if(!ro.getBody().paramterIsError()){
			Long pid = Long.valueOf(productId); 
			List<ProdProductBranch> listBranches = prodProductService.getProductBranchByProductId(pid, "false");
			List<ClientProduct> vpsiList = new ArrayList<ClientProduct>();
			ProdProduct prodProduct = prodProductService.getProdProductById(pid);
			if(Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(prodProduct.getSubProductType())){
				String branchIdStr = ro.getBody().getBranchId();
				
				if( ro.getBody().getFilterProductMap()!=null){
					value = ro.getBody().getFilterProductMap().get(branchIdStr);
				}
				ProdProductBranch prodBranches = null;
		
				pid = Long.valueOf(branchIdStr);
				prodBranches = this.prodProductService.getProdBranchDetailByProdBranchId(pid, DateUtil.toDate(date,"yyyy-MM-dd"),true);
				if(null!=prodBranches){
				
					ClientProduct vps = new ClientProduct();
					vps.setProductId(prodBranches.getProductId());
					vps.setShortName(prodBranches.getBranchName());
					//vps.setCouponAble(prodBranches.getProdProduct().getCouponAble());
					vps.setCouponAble("true");
					vps.setProductName(prodBranches.getProdProduct().getProductName());
					vps.setMinimum(prodBranches.getMinimum());
					vps.setMaximum(prodBranches.getMaximum());
					vps.setPayToSupplier(prodProduct.getPayToSupplier());
					vps.setPayToLvmama(prodProduct.getPayToLvmama());
					vps.setSellPrice(prodBranches.getSellPrice());
					vps.setMarketPrice(prodBranches.getMarketPrice());
					vps.setAdultQuantity(prodBranches.getAdultQuantity());
					vps.setChildQuantity(prodBranches.getChildQuantity());
					vps.setProdBranchId(prodBranches.getProdBranchId());
					vps.setBranchId(prodBranches.getProdBranchId()+"");
					vpsiList.add(vps);
				
				}
			} else {
				for (ProdProductBranch branches : listBranches) {
					ProdProductBranch prodBranches = this.prodProductService.getProdBranchDetailByProdBranchId(branches.getProdBranchId(), DateUtil.toDate(date,"yyyy-MM-dd"),true);
					if(null==prodBranches){
						continue;
					}
					ClientProduct vps = new ClientProduct();
					vps.setProductId(prodBranches.getProductId());
					vps.setProdBranchId(branches.getProdBranchId());
					vps.setBranchId(prodBranches.getProdBranchId()+"");
					vps.setShortName(prodBranches.getBranchName());
					//vps.setCouponAble(prodBranches.getProdProduct().getCouponAble());
					vps.setCouponAble("true");
					vps.setProductName(prodBranches.getProdProduct().getProductName());
					vps.setMinimum(prodBranches.getMinimum());
					vps.setMaximum(prodBranches.getMaximum());
					vps.setPayToSupplier(prodProduct.getPayToSupplier());
					vps.setPayToLvmama(prodProduct.getPayToLvmama());
					vps.setSellPrice(prodBranches.getSellPrice());
					vps.setMarketPrice(prodBranches.getMarketPrice());
					vps.setAdultQuantity(prodBranches.getAdultQuantity());
					vps.setChildQuantity(prodBranches.getChildQuantity());
					vpsiList.add(vps);
				}
			}
			//获得附加产品
			List<ProdProductRelation> relateList = this.prodProductService.getRelatProduct(
					Long.valueOf(productId),  DateUtil.toDate(date,"yyyy-MM-dd"));
			List<ClientProduct> addList = new ArrayList<ClientProduct>();
			
			for (ProdProductRelation prodProductRelation : relateList) {
				ClientProduct vps = new ClientProduct();
				vps.setProductId(prodProductRelation.getProductId());
				
				vps.setBranchId(prodProductRelation.getProdBranchId()+"");
				vps.setProdBranchId(prodProductRelation.getProdBranchId());
				vps.setShortName(prodProductRelation.getBranch().getBranchName());
				vps.setCouponAble("true");
				vps.setMinimum(prodProductRelation.getBranch().getMinimum());
				vps.setMaximum(prodProductRelation.getBranch().getMaximum());
				vps.setPayToSupplier(prodProduct.getPayToSupplier());
				vps.setPayToLvmama(prodProduct.getPayToLvmama());
				vps.setSellPrice(prodProductRelation.getBranch().getSellPrice());
				vps.setMarketPrice(prodProductRelation.getBranch().getMarketPrice());
				vps.setAdultQuantity(prodProductRelation.getBranch().getAdultQuantity());
				vps.setChildQuantity(prodProductRelation.getBranch().getChildQuantity());
				vps.setSaleNumType(prodProductRelation.getSaleNumType());
				vps.setIsAddtional("true");
				addList.add(vps);
			
			}
			
			
			ResponseObject responseObj = new ResponseObject(null, "1", true);
			ResponseBody responseBody = new ResponseBody();
			if (!vpsiList.isEmpty()) {
				responseBody.setProductListInfo(vpsiList);
			}
			if(!addList.isEmpty()){
				responseBody.setAddtionalListInfo(addList);
			}
			
			if(value!=null){
				responseBody.setPromotionName("");
			}
			//超过10单禁止使用优惠活动
			if (comClientService!=null) {
				Long count = comClientService.countUdidOrder(ro.getBody().getUdid());
				if(count>5){
					responseBody.setPromotionName("");
				}
			}

			responseObj.setBody(responseBody);
			return responseObj;
		} else {
			return null;
		}
	}
	
	
	private boolean createOrderItem(Item item,Long defaultProductId,String msgkey,BuyInfo createOrderBuyInfo,RequestOrderItem requestOrderItem,Date visitTime,ProdProductBranch branch){

		ProdProduct prodrodProduct  = null;
		if(branch.getProductId().equals(defaultProductId)){
			item.setProductBranchId(branch.getProdBranchId());
			item.setIsDefault("true");
			prodrodProduct = prodProductService.getProdProductById(branch.getProductId());
			Boolean payToLvmama = Boolean.valueOf(prodrodProduct.getPayToLvmama());
			createOrderBuyInfo.setPaymentTarget(payToLvmama?Constant.PAYMENT_TARGET.TOLVMAMA.name():Constant.PAYMENT_TARGET.TOSUPPLIER.name());
		} else {
			 prodrodProduct = prodProductService.getProdProductById(branch.getProductId());
			item.setProductBranchId(branch.getProdBranchId());
		}
		
		item.setQuantity(Integer.valueOf(requestOrderItem.getQuantity()));
		item.setProductId(branch.getProductId());
		item.setVisitTime(visitTime);

		if(prodrodProduct.getOfflineTime() != null){
			Date currentDate = new Date();
			if(currentDate.getTime()>prodrodProduct.getOfflineTime().getTime()) {
				msgkey ="prod_isoffline";
				return false;
			}
		}else if (Long.valueOf(requestOrderItem.getQuantity()) < branch.getMinimum()) {
			msgkey = "less_mininum";
			return false;
		} else if (Long.valueOf(requestOrderItem.getQuantity()) > branch.getMaximum()) {
			msgkey = "out_mininum";
			return false;
		}
		return true;
		
	}

	@Override
	public String commitOrder(RequestObject ro) {
		List<RequestOrderItem> listOrderItem = ro.getBody().getOrderItemList();
		String vistTimeStr = ro.getBody().getVisitTime();
		String msgkey="commitsuccess";
		String paytarget = ro.getBody().getPayTarget();
		String userId =ro.getHead().getUserId();
		String travellerName = ro.getBody().getUserName();
		String travellerMobile = ro.getBody().getMobile();
		String productId = ro.getBody().getProductId();
		try {
		if (!ro.getBody().paramterIsError())  {
			ProdProduct mainProduct = prodProductService.getProdProductById(Long.valueOf(productId));
			String promotionEnabled = ro.getBody().getPromotionEnabled();
			if ("true".equals(promotionEnabled)) {
				mainProduct.setCouponAble("true");
				
			} else {
				if(mainProduct.getCouponAble()==null||"".equals(mainProduct.getCouponAble())){
					mainProduct.setCouponAble("true");
				} 
			}
			//酒店离开时间。
			String leaveTimeStr = ro.getBody().getLeaveTime();
			//取优惠券的代码必须放在if下面 不能移动到上面 优惠券不是必填项
			String couponCode = ro.getBody().getCouponCode();
			couponCode = StringUtils.trimAllWhitespace(couponCode);
			Date visitTime = DateUtil.toDate(vistTimeStr,"yyyy-MM-dd");
			Date leaveTime = null;
			
			if(null!=leaveTimeStr&&!"".equals(leaveTimeStr)){
				leaveTime =  DateUtil.toDate(leaveTimeStr,"yyyy-MM-dd");
			}
			
			if (!listOrderItem.isEmpty()) {
				if(Constant.PRODUCT_TYPE.HOTEL.name().equals(mainProduct.getProductType())){
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("productId", Long.valueOf(productId));
					params.put("userId", userId);
					params.put("visitTime", visitTime);
					params.put("subProductType", mainProduct.getSubProductType());
					String travellerInfoOptions = mainProduct.getTravellerInfoOptions();
					params.put("travellerInfoOptions", travellerInfoOptions);
					
					//酒店单房型需离店日期
					if(Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(mainProduct.getSubProductType()) && leaveTime != null) {
						params.put("leaveTime", leaveTime);
					}
					params.put("visitorName", travellerName);
					ResultHandle isExisted = orderServiceProxy.checkCreateOrderLimitIsExisted(params);
					if(isExisted.isFail()){
						msgkey = "checkVisitorIsExisted";
					}
				}

				BuyInfo createOrderBuyInfo = new BuyInfo();
				List<Item> itemList = new ArrayList<Item>();
				for (int i = 0; i < listOrderItem.size(); i++) {
					RequestOrderItem requestOrderItem = listOrderItem.get(i);
					
					Item item = new Item();
				
					//查询产品当前日期类别。
					ProdProductBranch branch  =  this.prodProductService.getProdBranchDetailByProdBranchId(Long.valueOf(requestOrderItem.getBranchId()), visitTime,true);
					
					if(null==branch) {
						msgkey = "validVisitDate";
						break;
					}
					item.setAdultQuantity(branch.getAdultQuantity());
					item.setChildQuantity(branch.getChildQuantity());
					
					//创建订单子项
					this.createOrderItem(item, Long.valueOf(productId), msgkey, createOrderBuyInfo, requestOrderItem, visitTime,branch);
					//验证库存
					if(!prodProductService.isProductSellable(branch.getProductId(), Long.valueOf(requestOrderItem.getQuantity()), visitTime)){
						msgkey = "overStock";
						break;
					}
					
					//创建酒店订单项目。
					if(Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(branch.getProdProduct().getSubProductType())){
						HotelUtils.createOrderItemTimeInfo(visitTime, leaveTime, item);
					}
					
					itemList.add(item);	
					
				}
			
				
				
				List<Person> personList = new ArrayList<Person>();
				Person person = new Person();
				person.setName(travellerName);
				person.setMobile(travellerMobile);
				person.setPersonType(Constant.ORD_PERSON_TYPE.CONTACT.name());
				personList.add(person);
				createOrderBuyInfo.setChannel(Constant.CHANNEL.CLIENT.name());
				
				createOrderBuyInfo.setItemList(itemList);
				createOrderBuyInfo.setPersonList(personList);
				
				
				createOrderBuyInfo.setWaitPayment(Constant.WAIT_PAYMENT.PW_DEFAULT.getValue());
				createOrderBuyInfo.setUserId(userId);
				boolean isPaytoSupplier = false;
				if (Constant.PAYMENT_TARGET.TOSUPPLIER.name().equals(paytarget)) {
					isPaytoSupplier = true;
				}
				
				
				String message="";
				// 优惠券不为空且不是景区支付.
				if((couponCode==null||"".equals(couponCode))&&"true".equals(promotionEnabled)&&"true".equals(mainProduct.getCouponAble())){
					couponCode = ClutterConstant.getClientSubOrderCouponCode();
				} else {
					//当优惠券不为空  并且产品设置了 不能使用优惠券 那么提示不能使用优惠券
					if(!StringUtil.isEmptyString(couponCode) && "false".equals(mainProduct.getCouponAble())){
						msgkey = "can_not_use_coupon_code";
					}
					
				}
				if (!"".equals(couponCode)&&couponCode != null&&!isPaytoSupplier==true&&"true".equals(mainProduct.getCouponAble())) {
					Map codeparam = new HashMap<String, Object>();
					codeparam.put("couponCode", couponCode);
					codeparam.put("isValid", "true");
					List<MarkCouponCode> MccList = markCouponService.selectMarkCouponCodeByParam(codeparam);
					if (MccList != null && MccList.size() != 0) {
						ValidateCodeInfo info =  this.favorService.calculateFavorResultByBuyInfo(createOrderBuyInfo).getValidateCodeInfo();
						if (!Constant.COUPON_INFO.OK.name().equals(info.getKey())) {
							message = info.getValue();
							msgkey = "coupon_validate_error";
						}
					} else {
						msgkey = "coupon_error";
					}
					if (couponCode != null
							&& !"".equals(couponCode)&&"true".equals(mainProduct.getCouponAble())) {
						Map param = new HashMap<String, Object>();
						param.put("couponCode", couponCode);
						param.put("isValid", "true");
						List<MarkCouponCode> MarkCouponCodeList = this.markCouponService.selectMarkCouponCodeByParam(param);
						if (MarkCouponCodeList != null
								&& MarkCouponCodeList.size() != 0) {
							List<Coupon> couponList = new ArrayList<Coupon>();
							Coupon c = new Coupon();
							c.setChecked("true");
							c.setCode(MarkCouponCodeList.get(0).getCouponCode());
							c.setCouponId(MarkCouponCodeList.get(0).getCouponId());
							couponList.add(c);
							MarkCoupon mc = this.markCouponService.selectMarkCouponByPk(MarkCouponCodeList.get(0).getCouponId()); 
							if(mc!=null){ 
							createOrderBuyInfo.setPaymentChannel(mc.getPaymentChannel()); 
							}
							createOrderBuyInfo.setCouponList(couponList);
						}
					} else {
						msgkey = "coupon_error";
					}
					// 优惠券不为空且是景区支付.
				} else if(!"".equals(couponCode)&&couponCode != null&isPaytoSupplier==true) {
					msgkey = "coupon_can_used_for_topay_supplier";
				}
				
				
				List<ViewClientOrder> vcoList = new ArrayList<ViewClientOrder>();
				if ("commitsuccess".equals(msgkey)){
					OrdOrder o = null;	
					
					try {
						
						ClientOrderReport cor = new ClientOrderReport();
						cor.setChannel(ro.getHead().getFirstChannel()+"_"+ro.getHead().getSecondChannel());
						cor.setUdid(ro.getBody().getUdid());
						createOrderBuyInfo.setClientOrderReport(cor);
						createOrderBuyInfo.setOrdOrderChannel(new OrdOrderChannel(null, ro.getHead().getFirstChannel()+"_"+ro.getHead().getSecondChannel()));
						o = orderServiceProxy.createOrder(createOrderBuyInfo);
					
						getClientOrder(o,vcoList);
					} catch (Exception ex){
						msgkey="createOrderError";
						ex.printStackTrace();
					}
					
					if (o==null) {
						msgkey="createOrderError";
					}
					ResponseObject responseObj = new ResponseObject(null, "1", true);
					ResponseBody responseBody = new ResponseBody();
					responseObj.getHead().setErrorMessage(ClientConstants.getErrorInfo().get(msgkey));
					responseBody.setOrdersList(vcoList);
					responseObj.setBody(responseBody);
					return this.toJson(responseObj);
				
				} else {
					ResponseObject responseObj = new ResponseObject(null, "1", true);
					Map<String,String> map = ClientConstants.getErrorInfo();
					if(!"".equals(message)){
						map.put("coupon_validate_error", message);
					}
					responseObj.getHead().setErrorMessage(map.get(msgkey));
					return this.toJson(responseObj);
				}
	
			}
			return null;
			
			
		}else {
			return this.errorResponse(ro);
		}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return  this.errorResponse(ro);
	}


	@Override
	public String commitSuggest(RequestObject ro) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String queryUserOrderList(RequestObject ro) {
		String userId = ro.getHead().getUserId();
		String pageSizeStr = ro.getBody().getPageSize();
		String pageStr = ro.getBody().getPage();
		if (!ro.getBody().paramterIsError()) {
			Long pageSize = Long.valueOf(pageSizeStr);
			Long currentPage = Long.valueOf(pageStr);
			Long orderId = null;
			if(ro.getBody().getOrderId()!=null&&!"".equals(ro.getBody().getOrderId())){
				orderId = Long.valueOf(ro.getBody().getOrderId());
			}
		
			CompositeQuery compositeQuery = new CompositeQuery();
			OrderIdentity orderIdentity = new OrderIdentity();
			orderIdentity.setUserId(userId);
			if (orderId!=null){
				orderIdentity.setOrderId(orderId);
			}
			compositeQuery.setOrderIdentity(orderIdentity);
			
			Long totalRecords = orderServiceProxy
			.compositeQueryOrdOrderCount(compositeQuery);
	
	
			Page pageConfig = Page.page(totalRecords, pageSize,
					currentPage);
			PageIndex pageIndex = new PageIndex();
			pageIndex.setBeginIndex(new Integer(""+pageConfig.getStartRows()));
			pageIndex.setEndIndex(new Integer(""+pageConfig.getEndRows()));
			compositeQuery.setPageIndex(pageIndex);
			List<OrdOrder> ordersList = orderServiceProxy
			.compositeQueryOrdOrder(compositeQuery);
			
			List<ViewClientOrder> vcoList = new ArrayList<ViewClientOrder>();
			for (OrdOrder ordOrder : ordersList) {
				if(ordOrder==null){
					continue;
				}
				if(ordOrder.getMainProduct() == null){
					continue;
				}
				getClientOrder(ordOrder,vcoList);
			}
			ResponseObject responseObj = null;
			
			if(pageConfig.getTotalPages()!=0L){
				responseObj = new ResponseObject(null, pageConfig.getCurrentPage()+"", pageConfig.getCurrentPage()==pageConfig.getTotalPages());
			} else {
				responseObj = new ResponseObject(null, pageConfig.getCurrentPage()+"", true);
			}
			
			ResponseBody responseBody = new ResponseBody();
			responseBody.setOrdersList(vcoList);
			responseObj.setBody(responseBody);
			return this.toJson(responseObj);
		} else {
			return this.errorResponse(ro);
		}
	}
	
	private void getClientOrder(OrdOrder ordOrder,List<ViewClientOrder> vcoList){
		Long marketPrice = new Long(0);
		for (Iterator<OrdOrderItemProd> it = ordOrder.getOrdOrderItemProds().iterator(); it.hasNext();) {
			OrdOrderItemProd prod = it.next();
			marketPrice += prod.getMarketPrice() * prod.getQuantity();
		}
	

		ViewClientOrder vco = new ViewClientOrder();
		if(ordOrder.getOughtPay()!=null) {
			vco.setJieshen(Long.valueOf((marketPrice-ordOrder.getOughtPay())/100)+"");
		}

		vco.setCreateTime(DateUtil.getFormatDate(ordOrder.getCreateTime(), "yyyy-MM-dd"));
	
		vco.setOrderId(ordOrder.getOrderId());
		vco.setOrderViewStatus(ordOrder.getOrderViewStatus());
		vco.setPaymentTarget(ordOrder.getPaymentTarget());
		vco.setProductName(ordOrder.getMainProduct().getProductName());
		vco.setTitleName(ordOrder.getMainProduct().getShortName());
		vco.setAmount(Long.valueOf(ordOrder.getOughtPay()/100)+"");
		vco.setOrderType(ordOrder.getOrderType());
		vco.setNeedResourceConfirm(ordOrder.isNeedResourceConfirm());
		vco.setResourceConfirmStatus(ordOrder.getResourceConfirmStatus());
		vco.setPayUrl(Constant.getInstance().getIphonePayUrl());
		vco.setAlipayAppUrl(Constant.getInstance().getAliPayAppUrl()); 
		vco.setAlipayWapUrl(Constant.getInstance().getAliPayWapUrl());
		vco.setUpompPayUrl(Constant.getInstance().getUpompPayUrl());
		vco.setMianProductId(ordOrder.getMainProduct().getProductId());
		vco.setCanSendCert(ordOrder.isShouldSendCert());
		vco.setCanToPay(ordOrder.isCanToPay());
		if (Constant.PRODUCT_TYPE.ROUTE.name().equals(ordOrder.calcOrderType())) {
			vco.setVisitTime(ordOrder.getZhVisitTime());
			vco.setQuantity(ordOrder.getMainProduct().getQuantity()+"");
		} else if(Constant.PRODUCT_TYPE.TICKET.name().equals(ordOrder.calcOrderType())){
			vco.setVisitTime(ordOrder.getZhVisitTime());
			vco.setQuantity(ordOrder.getMainProduct().getQuantity()+"");
		}else if(Constant.PRODUCT_TYPE.HOTEL.name().equals(ordOrder.calcOrderType())){
			if(!Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT.equals(ordOrder.getMainProduct().getSubProductType())){
				vco.setVisitTime(ordOrder.getMainProduct().getDateRange());
				vco.setQuantity(ordOrder.getMainProduct().getQuantity()+"");
			} else {
				vco.setVisitTime(ordOrder.getZhVisitTime());
				vco.setQuantity(ordOrder.getMainProduct().getHotelQuantity());
			}
			
		}else if(Constant.PRODUCT_TYPE.OTHER.name().equals(ordOrder.calcOrderType())){
			vco.setVisitTime(ordOrder.getZhVisitTime());
			vco.setQuantity(ordOrder.getMainProduct().getQuantity()+"");
		}
		vco.setMainProductType(ordOrder.getMainProduct().getProductType());
		vco.setApproveStatus(ordOrder.getApproveStatus());
		
		if(ordOrder.isHotel()) {
			String[] range = ordOrder.getMainProduct().getDateRange().split("至");
			if(range.length>1){
				vco.setVisitTime(range[0]);
				vco.setLeaveTime(range[1]);
			}
			vco.setQuantity(ordOrder.getMainProduct().getHotelQuantity());
		}
		List<String> additionalProdList = new ArrayList<String> ();
		List<OrdOrderItemProd> ordOrderItemProdList = ordOrder.getOrdOrderItemProds();
		for (OrdOrderItemProd ordOrderItemProd : ordOrderItemProdList) {
			if(ordOrderItemProd.isAdditionalProduct()){
				additionalProdList.add(ordOrderItemProd.getShortName()+"x"+ordOrderItemProd.getQuantity());
				
			}
		}
		vco.setAdditionList(additionalProdList);
		List<ViewOrdPerson> vopList = new ArrayList<ViewOrdPerson>();
		for (OrdPerson op : ordOrder.getPersonList()) {
			ViewOrdPerson vop = new ViewOrdPerson();
			BeanUtils.copyProperties(op, vop);
			vopList.add(vop);
		}
		vco.setListPerson(vopList);

		vcoList.add(vco);
	}
	
	@Override
	public String queryGouponOnList(RequestObject ro){
			List<ClientGroupon2> cglist = new ArrayList<ClientGroupon2>();
		 Map<String,List<RecommendInfo>> groupMap = recommendInfoClient.getRecommendProductByBlockIdAndStation(6519L,"main");
		 List<RecommendInfo> topList  = groupMap.get("main"+"_"+Constant.GROUP_TOP_PREFIX);
		//处理团购推荐产品
			if(topList!=null && topList.size()>0){
				for(RecommendInfo  rec :topList){
					//查询产品信息 
					Map<String,Object> returnMap = groupDreamService.getTodayGroupProduct(Long.valueOf(rec.getRecommObjectId()));
					if (returnMap==null) {
				 		continue;
				 	}
					ProdProduct pp = (ProdProduct) returnMap.get("prodProduct");
					ClientGroupon2 cg = new ClientGroupon2();
					cg.setProductId(pp.getProductId()+"");
					cg.setProductName(pp.getProductName());
					cg.setMarketPrice(pp.getMarketPrice()+"");
					cg.setSellPrice(pp.getSellPrice()+"");
					cg.setSmallImage(pp.getSmallImage());
					cg.setOfflineTime(DateUtil.getFormatDate(pp.getOfflineTime(), "yyyy-MM-dd HH:mm:ss"));
					cg.setOrderCount(returnMap.get("orderCount").toString());
					cg.setMinGroupSize(returnMap.get("MIN_GROUP_SIZE").toString());
					cg.setProductType(pp.getProductType());
					cg.setManagerRecommend(returnMap.get("MANAGERRECOMMEND").toString());
					List<ComPicture> list = comPictureService.getPictureByPageId((Long) returnMap.get("pageId"));
					List<ClientPicture> cpList = new ArrayList<ClientPicture>();
					for (ComPicture comPicture : list) {
						ClientPicture clientp  = new ClientPicture();
						BeanUtils.copyProperties(comPicture, clientp);
						cpList.add(clientp);
					}
					cg.setPictureList(cpList);
					cglist.add(cg);
					
				}
			}
		
			//处理团购其他产品
			List<RecommendInfo> otherList  = groupMap.get("main"+"_"+Constant.GROUP_OTHER_PREFIX);
			if(otherList!=null&& otherList.size()>0){
				for(RecommendInfo  rec :otherList){
					 Map<String,Object>  returnMap  = groupDreamService.getTodayGroupProduct(Long.valueOf(rec.getRecommObjectId()));
					 	if (returnMap==null) {
					 		continue;
					 	}
					 	ProdProduct pp = (ProdProduct) returnMap.get("prodProduct");
					 
						ClientGroupon2 cg = new ClientGroupon2();
						cg.setProductId(pp.getProductId()+"");
						cg.setProductName(pp.getProductName());
						cg.setMarketPrice(pp.getMarketPrice()+"");
						cg.setSellPrice(pp.getSellPrice()+"");
						cg.setOfflineTime(DateUtil.getFormatDate(pp.getOfflineTime(), "yyyy-MM-dd HH:mm:ss"));
						cg.setOrderCount(returnMap.get("orderCount").toString());
						cg.setMinGroupSize(returnMap.get("MIN_GROUP_SIZE").toString());
						cg.setManagerRecommend(returnMap.get("MANAGERRECOMMEND").toString());
						cg.setSmallImage(pp.getSmallImage());
						List<ComPicture> list = comPictureService.getPictureByPageId((Long) returnMap.get("pageId"));
						List<ClientPicture> cpList = new ArrayList<ClientPicture>();
						for (ComPicture comPicture : list) {
							ClientPicture clientp  = new ClientPicture();
							BeanUtils.copyProperties(comPicture, clientp);
							cpList.add(clientp);
						}
						cg.setPictureList(cpList);
						cglist.add(cg);
				}
			}
			ResponseObject responseObj = new ResponseObject(null, "1", true);
			ResponseBody responseBody = new ResponseBody();
			responseBody.setGouponList(cglist);
			//responseBody.setGroupPrdList(groupPrdList);
			responseObj.setBody(responseBody);
			return this.toJson(responseObj);
	}
	
	
	@Override
	public String addfeedBack(RequestObject ro) {
		String content = ro.getBody().getContent();
		if (!ro.getBody().paramterIsError()) {
			String msgkey = "";
			try {
				ComUserFeedback fd = new ComUserFeedback();
				fd.setContent(content);
				fd.setType(ro.getHead().getFirstChannel());
				fd.setStateId(Constant.FEED_BACK_STATE_ID.PENDING.name());
				fd.setEmail(ro.getBody().getEmail());
				fd.setCreateDate(new Date());
				UserUser user=userUserProxy.getUserUserByUserNo(ro.getHead().getUserId());
			    if(user!=null){
			     fd.setUserId(user.getId());
			    }
			    this.comUserFeedbackService.saveUserFeedBack(fd);
				msgkey = "commitsuccess";
			} catch (Exception ex) {
				msgkey = "error";
				ex.printStackTrace();
			}
			ResponseObject responseObj = new ResponseObject(null, "1", true);
			responseObj.getHead().setErrorMessage(ClientConstants.getErrorInfo().get(msgkey));
			ResponseBody responseBody = new ResponseBody();
			responseObj.setBody(responseBody);
			return this.toJson(responseObj);
		} else {
			return this.errorResponse(ro);
		}
	}


	
	
	private String errorResponse(RequestObject ro){
		JSONObject json = JSONObject.fromObject(ro);	
		return json.toString();
	}

	

	@Override
	public String reSendSms(RequestObject ro) {
		String orderId = ro.getBody().getOrderId();
		String mobileNumber = ro.getBody().getMobile();
		if(!ro.getBody().paramterIsError()){
			orderMessageProducer.sendMsg(MessageFactory.newCertSmsSendMessage(Long
					.valueOf(orderId), mobileNumber));
			ResponseObject responseObj = new ResponseObject(null, "1", true);
			ResponseBody responseBody = new ResponseBody();
			responseObj.setBody(responseBody);
			JSONObject json = JSONObject.fromObject(responseObj);	
			return json.toString();
		} else {
			return this.errorResponse(ro);
		}
	}

	public String countPlaceInfos(RequestObject ro){
		if (!ro.getBody().paramterIsError()) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("productType", ro.getBody().getProductType());
			map.put("placeId", ro.getBody().getPlaceId());
			map.put("channel", Constant.CHANNEL.FRONTEND.name());
			Long count = productSearchInfoService.countProductSearchInfoByParam(map);
			ResponseObject responseObj = new ResponseObject(null, "1", true);
			ResponseBody responseBody = new ResponseBody();
			responseBody.setHasProduct(count==0L?false:true);
			responseObj.setBody(responseBody);
			return this.toJson(responseObj);
		} else {
			return this.errorResponse(ro);
		}
	}
	
	public String queryHotelBranches(RequestObject ro) {
		String placeId = ro.getBody().getPlaceId();
		String stage = ro.getBody().getStage();
		if(!ro.getBody().paramterIsError()){
		Long pid = Long.valueOf(placeId);
		
		List<ProductSearchInfo> searchInfoList =  productSearchInfoService.getProductHotelByPlaceIdAndType(pid, 1000, "FRONTEND",false);
		List<ClientProduct> cpList= new ArrayList<ClientProduct>();
		for (ProductSearchInfo viewProductSearchInfo : searchInfoList) {
			ClientUtils.copyProductList(viewProductSearchInfo.getProdBranchSearchInfoList(),cpList,viewProductSearchInfo.getProductType());
			for (ClientProduct clientProduct : cpList) {
				clientProduct.setPayToLvmama(viewProductSearchInfo.getPayToLvmama());
				clientProduct.setPayToSupplier(viewProductSearchInfo.getPayToSupplier());
			}

		}
		
		ResponseObject responseObj = new ResponseObject(null, "1", true);
		ResponseBody responseBody = new ResponseBody();
		responseBody.setProductListInfo(cpList);
		responseObj.setBody(responseBody);

		return this.toJson(responseObj);
		} else {
			return this.errorResponse(ro);
		}
	}
	
	
	public ClientPlace getPlaceDetails(RequestObject ro) {
		String placeId = ro.getBody().getPlaceId();
		if(!ro.getBody().paramterIsError()) {
			return this.queryPlaceDetails(placeId);
		
		}
		return null;
	}

	
	public Map<String,Object> defaultParamters (Long page) {
		HashMap<String,Object> paramters = new HashMap<String,Object>();
		paramters.put("pageSize", 10L);
		paramters.put("currentPage", page);
		return paramters;
	}
	
	public String queryPlaceComments(String userId,String placeId,Long page) {
	
		Map<String,Object> paramters = this.defaultParamters(page);
		String[] isAudits={com.lvmama.comm.vo.Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name()};
		paramters.put("isAudits", isAudits);
		paramters.put("placeId", placeId);
		UserUser user = null;
		if(!StringUtil.isEmptyString(userId)){
			user =  userUserProxy.getUserUserByUserNo(userId);
			paramters.put("notEqualUserId", user.getId());
		}
	
		paramters.put("isHid", "N");
		paramters.put("createTime321", "true");
		Page<CommonCmtCommentVO> pageConfig = 	this.cmtCommentService.queryCmtCommentListForApp(paramters);
		List<ClientCmtPlace> cmtList = new ArrayList<ClientCmtPlace> ();
		List<ClientCmtPlace> userAuditCmtList = this.queryMyComment(user, placeId);
		
		
		if (userAuditCmtList!=null&&userAuditCmtList.size()!=0) {
			cmtList.addAll(userAuditCmtList);
		}
	
		for (CommonCmtCommentVO cmtCommentVO : pageConfig.getItems()) {
			ClientCmtPlace ccp   = new ClientCmtPlace();
			this.covertComment(ccp, cmtCommentVO);
			ccp.setAvgScore(cmtCommentVO.getSumaryLatitude().getScore()+"");
			cmtList.add(ccp);
		}
		
	 JSONArray json = JSONArray.fromObject(cmtList);
	 String returnJson = "{\"isLastPage\":\""+this.isLastPage(pageConfig)+"\",\"list\":"+json+"}";
	 return returnJson;
	}
	
	/**
	 * 根据用户ID 查询我点评的未审核的点评数据
	 * @return
	 */
	private List<ClientCmtPlace> queryMyComment(UserUser user,String placeId){
		if (user==null) {
			return null;
		}
		Map<String,Object> paramters = this.defaultParamters(1L);
		paramters.put("pageSize", 100L);
		paramters.put("currentPage", "1");
		String[] isAudits={com.lvmama.comm.vo.Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name(),com.lvmama.comm.vo.Constant.CMT_AUDIT_STATUS.AUDIT_GOING.name(),com.lvmama.comm.vo.Constant.CMT_AUDIT_STATUS.AUDIT_FAILED.name()};
		paramters.put("isAudits", isAudits);
		paramters.put("userId", user.getId());
		paramters.put("placeId", placeId);
		paramters.put("isHid", "N");
		paramters.put("createTime321", "true");
		Page<CommonCmtCommentVO> pageConfig = 	this.cmtCommentService.queryCmtCommentListForApp(paramters);
		List<ClientCmtPlace> cmtList = new ArrayList<ClientCmtPlace> ();
		for (CommonCmtCommentVO cmtCommentVO : pageConfig.getItems()) {
			System.out.println(cmtCommentVO.getContent()+"####");
			 ClientCmtPlace ccp   = new ClientCmtPlace();
			 this.covertComment(ccp, cmtCommentVO);
			 if(cmtCommentVO.getSumaryLatitude()==null){
				 continue;
			 }
			 ccp.setAvgScore(cmtCommentVO.getSumaryLatitude().getScore()+"");
			 cmtList.add(ccp);
		}
		return cmtList;
	}
	
	private void covertComment( ClientCmtPlace ccp,CommonCmtCommentVO commonCmtCommentVO){
		 ccp.setAvgScore(commonCmtCommentVO.getAvgScore()==null?"":commonCmtCommentVO.getAvgScore()+"");
		 ccp.setContent(commonCmtCommentVO.getContent());
		 ccp.setCreatedTimeStr(com.lvmama.comm.utils.DateUtil.formatDate(commonCmtCommentVO.getCreatedTime(), "yyyy-MM-dd HH:mm:ss"));
		 ccp.setBest(Boolean.valueOf("Y".equals(commonCmtCommentVO.getIsBest())));
		 ccp.setExperience(com.lvmama.comm.vo.Constant.EXPERIENCE_COMMENT_TYPE.equals(commonCmtCommentVO.getCmtType())?true:false);
		 ccp.setCashRefund(commonCmtCommentVO.getCashRefund()==null?"":commonCmtCommentVO.getCashRefund()+"");
		 ccp.setPoint(commonCmtCommentVO.getPoint()+"");
		 ccp.setUserName(commonCmtCommentVO.getUserName());
		 ccp.setAuditStatu(commonCmtCommentVO.getIsAudit());
		 ccp.setChAudit(commonCmtCommentVO.getChAudit());
		
		 if(commonCmtCommentVO.getCmtType().equals(Constant.EXPERIENCE_COMMENT_TYPE))
		    {
		     ProdProduct product = prodProductService.getProdProductById(commonCmtCommentVO.getProductId());
		     if(product != null)
		     {
		      ProductCmtCommentVO productCmtCommentVO = CommentUtil.composeProductComment(commonCmtCommentVO, product, null);
		      ccp.setProductName(productCmtCommentVO.getProductName());
		     }
		     //兼容客户端老版本 使用之前的判断逻辑
		     commonCmtCommentVO.setCmtType("2");
		    }
		    else
		    {
		     Place place = placeService.queryPlaceByPlaceId(commonCmtCommentVO.getPlaceId());
		     PlaceCmtCommentVO placeCmtCommentVO = CommentUtil.composePlaceComment(commonCmtCommentVO, place);
		     if(placeCmtCommentVO != null)
		     {
		    	 ccp.setPlaceName(placeCmtCommentVO.getPlaceName());
		     }
		     //兼容客户端老版本 使用之前的判断逻辑
		     commonCmtCommentVO.setCmtType("1");
		     }

		 ccp.setPlaceId(commonCmtCommentVO.getPlaceId()+"");
		 ccp.setCmtLatitudes(commonCmtCommentVO.getCmtLatitudes());
		 ccp.setCmtType(commonCmtCommentVO.getCmtType());
		 ccp.setOrderId(commonCmtCommentVO.getOrderId()+"");
	}
	
	public String queryCommentByUserId(String userId,Long page,String cmtType) {
		Map<String,Object> paramters = this.defaultParamters(page);
		UserUser user = userUserProxy.getUserUserByUserNo(userId);
		String[] isAudits={com.lvmama.comm.vo.Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name(),com.lvmama.comm.vo.Constant.CMT_AUDIT_STATUS.AUDIT_GOING.name(),com.lvmama.comm.vo.Constant.CMT_AUDIT_STATUS.AUDIT_FAILED.name()};
		paramters.put("isAudits", isAudits);
		if("1".equals(cmtType)){
			cmtType = com.lvmama.comm.vo.Constant.COMMON_COMMENT_TYPE;
		} else {
			cmtType = com.lvmama.comm.vo.Constant.EXPERIENCE_COMMENT_TYPE;
		}
		paramters.put("cmtType", cmtType);
		paramters.put("userId", user.getId());
		paramters.put("createTime321", "true");

		Page<CommonCmtCommentVO> pageConfig = 	this.cmtCommentService.queryCmtCommentListForApp(paramters);
		List<ClientCmtPlace> cmtList = new ArrayList<ClientCmtPlace> ();

		for (CommonCmtCommentVO cmtCommentVO : pageConfig.getItems()) {
			ClientCmtPlace ccp   = new ClientCmtPlace();
			this.covertComment(ccp, cmtCommentVO);
			//如果是取用户的点评直接去点评他自己评的总分
			
			ccp.setAvgScore(cmtCommentVO.getSumaryLatitude().getScore()+"");
			cmtList.add(ccp);
		}
	 JSONArray json = JSONArray.fromObject(cmtList);
	 String returnJson = "{\"isLastPage\":\""+this.isLastPage(pageConfig)+"\",\"list\":"+json.toString()+"}";
	 return returnJson;	
	}

	
	public String commitComments(String userId,List<ClientCmtLatitude> ccList,String objectId,String content,String cmtType) {
		try {
		String shareContent = "";
		UserUser user = userUserProxy.getUserUserByUserNo(userId);
		if(user != null){
			CommonCmtCommentVO comment = new CommonCmtCommentVO();
			comment.setCmtType(Constant.EXPERIENCE_COMMENT_TYPE); 
			comment.setContent(content); 
			if (cmtType.equals("2")) {
				OrdOrder orderOrder = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(objectId));
				Long productId = orderOrder.getMainProduct().getProductId();
				Long destId = prodProductPlaceService.selectDestByProductId(productId);
				comment.setProductId(productId); 
				comment.setPlaceId(destId);
				comment.setOrderId(Long.valueOf(objectId)); 
				comment.setCmtType(com.lvmama.comm.vo.Constant.EXPERIENCE_COMMENT_TYPE);
				shareContent = String.format(COMMENT_SHARE_TEMPLATE, orderOrder.getMainProduct().getProductName(),content);
			} else {
				Place place = this.placeService.queryPlaceByPlaceId(Long.valueOf(objectId));
				shareContent = String.format(COMMENT_SHARE_TEMPLATE, place.getName(),content);
				comment.setPlaceId(Long.valueOf(objectId));
				comment.setCmtType(com.lvmama.comm.vo.Constant.COMMON_COMMENT_TYPE);
			}
			
			String commentId = this.commitComment(user, comment, ccList);
			shareContent = com.lvmama.comm.utils.StringUtil.subStringStr(shareContent, 140);
			String shareUrl = Constant.WWW_HOST+"/comment/"+commentId;
			return "{\"message\":\"1\",\"shareContent\":\""+shareContent+"\",\"shareUrl\":\""+shareUrl+"\"}";
		}
		
		} catch (Exception ex) {
			ex.printStackTrace();
			return "{\"message\":\"-1\"}";
		}
		return "{\"message\":\"1\"}";
	}
	
	public String commitComment(UserUser users,CommonCmtCommentVO comment, List<ClientCmtLatitude> cmtLatitudeList){
		List<CmtLatitudeVO> cmtLatitudeVoList = new ArrayList<CmtLatitudeVO>();
			if (null != cmtLatitudeList && !cmtLatitudeList.isEmpty()) {
				for (ClientCmtLatitude clientlatitude : cmtLatitudeList) {
					CmtLatitudeVO latitude = new CmtLatitudeVO();
					BeanUtils.copyProperties(clientlatitude, latitude);
					latitude.setCommentId(comment.getCommentId());
					cmtLatitudeVoList.add(latitude);
				}
			}
		comment.setCmtLatitudes(cmtLatitudeVoList);
		return cmtCommentService.insert(users, comment).toString();	
	}
	
	 private  List<DicCommentLatitudeVO> getPlaceInfoLatitude(Place place) {
			List<DicCommentLatitudeVO> list = new ArrayList<DicCommentLatitudeVO>();
			Map<String,Object> paramters = new HashMap<String,Object> ();
			paramters.put("placeId", place.getPlaceId());
			
			String subject = place.getCmtTitle();
			//景点门票依主题取维度，目的地统一维度，酒店及产品也统一维度
			if("3".equalsIgnoreCase(place.getStage())){
				subject = "酒店和酒店产品";
			}else if("1".equalsIgnoreCase(place.getStage())){
				subject = "目的地";
			}
			if (StringUtil.isEmptyString(subject)) {
			if ("3".equalsIgnoreCase(place.getStage())) {
				subject = "其他主题";
			} else {
				// place主题无对应的纬度和place无主题,默认主题为: 其它
				subject = "其它";
			}
			}
			
			paramters.put("subject", subject);
			List<DicCommentLatitude> dicCommentLatitudeList = dicCommentLatitudeService.getDicCommentLatitudeList(paramters);
			for (DicCommentLatitude dicCommentLatitude : dicCommentLatitudeList) {
				DicCommentLatitudeVO dicCommentLatitudeVO = new DicCommentLatitudeVO();
				BeanUtils.copyProperties(dicCommentLatitude, dicCommentLatitudeVO);
				list.add(dicCommentLatitudeVO);
				
			}
			
			if(list.isEmpty()){
				if ("3".equalsIgnoreCase(place.getStage())) {
					subject = "其他主题";
				} else {
					// place主题无对应的纬度和place无主题,默认主题为: 其它
					subject = "其它";
				}
				
				paramters.put("subject", subject);
				dicCommentLatitudeList = dicCommentLatitudeService.getDicCommentLatitudeList(paramters);
				for (DicCommentLatitude dicCommentLatitude : dicCommentLatitudeList) {
					DicCommentLatitudeVO dicCommentLatitudeVO = new DicCommentLatitudeVO();
					BeanUtils.copyProperties(dicCommentLatitude, dicCommentLatitudeVO);
					list.add(dicCommentLatitudeVO);	
				}
			}
			
			
			return list;
		}
	
	public String getCommentLatitudeInfos(String placeId,String orderId) {
		List<DicCommentLatitudeVO> list  = null;
		if (!org.apache.commons.lang.StringUtils.isEmpty(placeId)) {
			Place place = this.placeService.queryPlaceByPlaceId(Long.valueOf(placeId));
			list = this.getPlaceInfoLatitude(place);
		}
		
		if (!org.apache.commons.lang.StringUtils.isEmpty(orderId)) {
			
			OrdOrder orderOrder = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId));
			Long destId = prodProductPlaceService.selectDestByProductId(orderOrder.getMainProduct().getProductId());
			Place place = this.placeService.queryPlaceByPlaceId(Long.valueOf(destId));
			
			if (place!=null){
				
			String productType = orderOrder.getMainProduct().getProductType();
			
			 if(!StringUtil.isEmptyString(place.getCmtTitle())&&!StringUtil.isEmptyString(place.getStage())) {
					//酒店产品和酒店景点维度一致(取酒店统一的维度)，门票和目的景点维度一致(依placeId主题取维度)
					if((Constant.PRODUCT_TYPE.HOTEL.name().equalsIgnoreCase(productType)||Constant.PRODUCT_TYPE.TICKET.name().equalsIgnoreCase(productType))) 
						list = this.getPlaceInfoLatitude(place);
				}else{
					 //线路产品
					 list = new ArrayList<DicCommentLatitudeVO>();
					Constant.PRODUCT_LATITUDE productLatitude = Constant.PRODUCT_LATITUDE.getProductLatitude(productType);
					String subject = productLatitude.getChSubject();
					
					Map<String,Object> parames =  new HashMap<String,Object>();
					parames.put("subject", subject);
					List<DicCommentLatitude> dicCommentLatitudeList = dicCommentLatitudeService.getDicCommentLatitudeList(parames);
					for (DicCommentLatitude dicCommentLatitude : dicCommentLatitudeList) {
						DicCommentLatitudeVO dicCommentLatitudeVO = new DicCommentLatitudeVO();
						BeanUtils.copyProperties(dicCommentLatitude, dicCommentLatitudeVO);
						list.add(dicCommentLatitudeVO);
					}
			}
			
			}
				
		}
		if (list == null) {
			 list = new ArrayList<DicCommentLatitudeVO>();
		}
		DicCommentLatitudeVO main = new DicCommentLatitudeVO();
		main.setLatitudeId(Constant.DEFAULT_LATITUDE_ID);
		main.setName("总体");
		
		if(list==null){
			list=new ArrayList<DicCommentLatitudeVO>();
		}
		
		list.add(main);
		JSONArray json = JSONArray.fromObject(list);
		return json.toString();
	}
	
	
	public ClientPlace queryPlaceDetails(String placeId) {

			Place place = this.placeService.queryPlaceByPlaceId(Long.valueOf(placeId));
			PlacePhoto pp = new PlacePhoto();
			pp.setType(PlacePhotoTypeEnum.LARGE.name());
			pp.setPlaceId(place.getPlaceId());
			List<PlacePhoto> ppList =  this.placePhotoService.queryByPlacePhoto(pp);
			ClientPlace vp = new ClientPlace();
			BeanUtils.copyProperties(place, vp);
			vp.setId(place.getPlaceId());
//			vp.setHotelFacility(place.getHotelFacilities());新度假酒店place中不包含酒店信息
			vp.setHotelStar(place.getHotelStar());
			vp.setPlaceMainTitel(place.getFirstTopic());
			vp.setPlaceTitel(place.getScenicSecondTopic());

			vp.setRecommendReason(StringUtil.filterOutHTMLTags(place.getRemarkes()));
			vp.setDescription(StringUtil.filterOutHTMLTags(place.getDescription()));
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("placeId", placeId);
			List<PlaceCoordinateVo> list  = placeCoordinateBaiduService.getBaiduMapListByParams(param);
			
			if (list!=null&&!list.isEmpty()) {
				PlaceCoordinateVo pcb = list.get(0);
				vp.setLatitude(pcb.getLatitude().floatValue());
				vp.setLongitude(pcb.getLongitude().floatValue());
			}

			if("3".equals(place.getStage())){
				vp.setStartTime(place.getHotelOpenTime());
				vp.setRoomNum(place.getHotelRoomNum().intValue());
//				vp.setAirport(place.getHotelTrafficInfo()); 新度假酒店place中不包含酒店信息
				vp.setPhone(place.getHotelPhone());
			} else if("2".equals(place.getStage())){
				vp.setRecommendTime(place.getScenicRecommendTime());
				vp.setStartTime(place.getScenicOpenTime());
			}
			
			PlaceSearchInfo psi = placeSearchInfoService.getPlaceSearchInfoByPlaceId(Long.valueOf(placeId));
			Map<String,Object> parameters  = new HashMap<String, Object>();
	    	parameters.put("placeId", placeId);
	    	List<CmtLatitudeStatistics> cmtLatitudeStatisticsList =	cmtLatitudeStatistisService.getLatitudeStatisticsList(parameters);
			List<PlaceCmtScoreVO> pcsVoList = new ArrayList<PlaceCmtScoreVO>();
			for (CmtLatitudeStatistics cmtLatitudeStatistics : cmtLatitudeStatisticsList) {
			    	PlaceCmtScoreVO pcv = new PlaceCmtScoreVO();
			    	pcv.setName(cmtLatitudeStatistics.getLatitudeName());
			    	pcv.setScore(cmtLatitudeStatistics.getAvgScore()+"");
			    	if(cmtLatitudeStatistics.getLatitudeId().equals("FFFFFFFFFFFFFFFFFFFFFFFFFFFF")){
			    		pcv.setMain(true);
			    	}
			    	pcsVoList.add(pcv);
			}	    

			vp.setPlaceCmtScoreList(pcsVoList);
			
			List<String> imageList = new ArrayList<String>();
			if (ppList!=null && ppList.size()!=0) {
				for (PlacePhoto placePhoto : ppList) {
						imageList.add(placePhoto.getImagesUrl());
					
				}
			}
			vp.setImageList(imageList);
			
			Map<String,Object> map = new HashMap<String, Object>();
			if("2".equals(vp.getStage())){
				map.put("productType", "TICKET");
			} else if("3".equals(vp.getStage())){
				map.put("productType", "HOTEL");
			}
			map.put("placeId", placeId);
			map.put("channel", Constant.CHANNEL.FRONTEND.name());
			Long count = productSearchInfoService.countProductSearchInfoByParam(map);
			if(count>0){
				vp.setHasProduct(true);
			}
			
			map.put("productType", Constant.PRODUCT_TYPE.ROUTE.name());
			map.put("subProductType", Constant.SUB_PRODUCT_TYPE.FREENESS.name());
			
			Long freenessCount = productSearchInfoService.countProductSearchInfoByParam(map);
			
			if(freenessCount>0){
				vp.setHasFreeness(true);
			}

			if (psi!=null&&psi.getProductsPrice()!=null) {
				vp.setSellPrice(Long.valueOf(psi.getProductsPrice()));
			}

			vp.setCmtNum(String.valueOf(psi.getCmtNum()));
			vp.setMarketPrice(psi.getMarketPrice());
			//JSONObject jo = JSONObject.fromObject(cp);
			return vp;
	}
	
	public String getOrderWaitingComments(String userId,Long page) {
		List<OrderAndComment> canCommentOrderProductList = orderServiceProxy.selectCanCommentOrderProductByUserNo(userId);
		List<ClientOrderCmt> cocList = new ArrayList<ClientOrderCmt>();
		for (OrderAndComment orderAndComment : canCommentOrderProductList) {
			Map<String,Object> parameters = new HashMap<String, Object>();
			parameters.put("orderId", orderAndComment.getOrderId());
			parameters.put("productId", orderAndComment.getProductId());
			parameters.put("isHide", "displayall");
			List<CommonCmtCommentVO> cmtCommentList = cmtCommentService.getCmtCommentList(parameters);
			if(cmtCommentList==null||cmtCommentList.size()==0){
				 ClientOrderCmt coc = new ClientOrderCmt();
				coc.setCashRefund(orderAndComment.getCashRefund()+"");
				coc.setOrderId(orderAndComment.getOrderId()+"");
				coc.setProductName(orderAndComment.getProductName());
				cocList.add(coc);
			}
		}
		JSONArray json = JSONArray.fromObject(cocList);
		String returnJson = "{\"isLastPage\":\"true\",\"list\":"+json+"}";
		return returnJson;
	}
	
	
	public String queryWaitForCommentNumber (String userId) {
		Map<String,Object> paramters = this.defaultParamters(1L);
		paramters.put("userId", userId);
	
		List<OrderAndComment> canCommentOrderProductList = orderServiceProxy.selectCanCommentOrderProductByUserNo(userId);
		List<ClientOrderCmt> cocList = new ArrayList<ClientOrderCmt>();
		if (canCommentOrderProductList!=null) {
			for (OrderAndComment orderAndComment : canCommentOrderProductList) {
				Map<String,Object> parameters = new HashMap<String, Object>();
				parameters.put("orderId", orderAndComment.getOrderId());
				parameters.put("productId", orderAndComment.getProductId());
				parameters.put("isHide", "displayall");
				List<CommonCmtCommentVO> cmtCommentList = cmtCommentService.getCmtCommentList(parameters);
				if(cmtCommentList==null||cmtCommentList.size()==0){
					 ClientOrderCmt coc = new ClientOrderCmt();
					coc.setCashRefund(orderAndComment.getCashRefund()+"");
					coc.setOrderId(orderAndComment.getOrderId()+"");
					coc.setProductName(orderAndComment.getProductName());
					cocList.add(coc);
				}
			}
		}
		
		if (cocList==null||cocList.size()==0) {
			return "0";
		} else {
			return String.valueOf(cocList.size());
		}
	}
	
	public void insertShareLog(String uid,String screenName,String shareChannel,String shareTarget) {
		ComClientLog ccl = new ComClientLog();
		ccl.setVisitTime(new Date());
		ccl.setDeviceName(shareTarget);
		ccl.setUserId(uid);
		ccl.setOsname(screenName);
		ccl.setChannel(shareChannel);
		ccl.setCreateTime(new Date());
		comClientService.insert(ccl);
	}

	private boolean isLastPage(Page<?> pageConfig) {
		if (pageConfig==null) {
			return true;
		}
		
		if (pageConfig.getItems()==null||pageConfig.getItems().size()==0)  {
			return true;
		}
		
		if (pageConfig.getCurrentPage()==pageConfig.getTotalPages()) {
			return true;
		}
		return false;
	}
	
	public void setCashAccountService(
			CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}

	public void setproductSearchInfoService(
			ProductSearchInfoService productSearchInfoService) {
		this.productSearchInfoService = productSearchInfoService;
	}
 
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

 
	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}


	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}
	
	public void setUserCooperationUserService(
			UserCooperationUserService userCooperationUserService) {
		this.userCooperationUserService = userCooperationUserService;
	}


	public void setComUserFeedbackService(
			ComUserFeedbackService comUserFeedbackService) {
		this.comUserFeedbackService = comUserFeedbackService;
	}

	
	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}


	public void setViewPageJourneyService(ViewPageJourneyService viewPageJourneyService) {
		this.viewPageJourneyService = viewPageJourneyService;
	}


	public void setViewPageService(ViewPageService viewPageService) {
		this.viewPageService = viewPageService;
	}


	public void setProdProductPlaceService(ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}


	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}


	public void setSmsHistoryService(ISmsHistoryService smsHistoryService) {
		this.smsHistoryService = smsHistoryService;
	}


	public void setComClientService(ComClientService comClientService) {
		this.comClientService = comClientService;
	}

	public void setCmtLatitudeStatistisService(
			CmtLatitudeStatistisService cmtLatitudeStatistisService) {
		this.cmtLatitudeStatistisService = cmtLatitudeStatistisService;
	}


	public void setCmtCommentService(CmtCommentService cmtCommentService) {
		this.cmtCommentService = cmtCommentService;
	}


	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}


	public void setDicCommentLatitudeService(
			DicCommentLatitudeService dicCommentLatitudeService) {
		this.dicCommentLatitudeService = dicCommentLatitudeService;
	}


	public void setGroupDreamService(GroupDreamService groupDreamService) {
		this.groupDreamService = groupDreamService;
	}


	public void setPlaceSearchInfoService(
			PlaceSearchInfoService placeSearchInfoService) {
		this.placeSearchInfoService = placeSearchInfoService;
	}


	public void setPlacePhotoService(PlacePhotoService placePhotoService) {
		this.placePhotoService = placePhotoService;
	}


	public void setRecommendInfoClient(RecommendInfoClient recommendInfoClient) {
		this.recommendInfoClient = recommendInfoClient;
	}


	public void setPlaceCoordinateBaiduService(
			PlaceCoordinateBaiduService placeCoordinateBaiduService) {
		this.placeCoordinateBaiduService = placeCoordinateBaiduService;
	}


	public void setFavorService(FavorService favorService) {
		this.favorService = favorService;
	}
 
}
