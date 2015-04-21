package com.lvmama.clutter.web.place;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.clutter.service.IClientForJsonService;
import com.lvmama.clutter.utils.ClutterConstant;
//import com.lvmama.clutter.utils.HttpClientUtil;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.clutter.xml.lv.po.RequestBody;
import com.lvmama.clutter.xml.lv.po.RequestHead;
import com.lvmama.clutter.xml.lv.po.RequestObject;
import com.lvmama.clutter.xml.lv.po.RequestOrderItem;
import com.lvmama.clutter.xml.lv.po.ResponseObject;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.client.ClientCmtLatitude;
import com.lvmama.comm.pet.po.client.ClientPlace;
import com.lvmama.comm.pet.po.client.ViewPlace;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StackOverFlowUtil;
import com.lvmama.comm.utils.UniformResourceLocator;
import com.lvmama.comm.vo.Constant;
/**
 * 客户端api action
 * @author dengcheng
 *
 */
public class ClientApiAction extends BaseAction {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		String userId;
		String visitTime;
		String leaveTime;
		String productId;
		String branchId;
		String udid;
		String method;
		String placeId;
		String stage;
		String keyword;
		String userName;
		String password;
		String firstChannel;
		String secondChannel;
		String mobile;
		String validateCode;
		String page;
		String pageSize;
		String orderId;
		String email;
		String content;
		String productType;
		String cityId;
		float latitude;
		float longitude;
		String subject;
		String couponState;
		String spot;

		String orderItem;
		
		String payTarget;
		String promotionEnabled;
		String couponCode;
		//线路搜索相关
		String keyword2;
		String toDest;
		
		String cityName;
		
		String loginChannel;
	
		//第三方登录唯一识别码
		String uid;
		//需要的点评纬度主题
		String placeMainTitle;

		String cmtType;
		
		String cmtLatitude;
		/**
		 * 公共ID
		 */
		String objectId;
		/**
		 * 
		 */
		String latitudeInfo ;

		
		Map<String,String> errorInfo = new HashMap<String,String> ();
		/**
		 * 分享人的昵称
		 */
		String screenName;
		/**
		 * 分享的渠道
		 */
		String shareChannel;
		
		/**
		 * 订单服务
		 */
		private OrderService orderServiceProxy;
		/**
		 * passbook zip 流
		 */
		/**
		 * 短信发送服务器.
		 */
		private TopicMessageProducer orderMessageProducer;
		
		public String getScreenName() {
			return screenName;
		}

		public void setScreenName(String screenName) {
			this.screenName = screenName;
		}

		public String getShareChannel() {
			return shareChannel;
		}

		public void setShareChannel(String shareChannel) {
			this.shareChannel = shareChannel;
		}

		public String getShareTarget() {
			return shareTarget;
		}

		public void setShareTarget(String shareTarget) {
			this.shareTarget = shareTarget;
		}

		/**
		 * 分享的对象
		 */
		String shareTarget;

		public String getCmtType() {
			return cmtType;
		}

		public void setCmtType(String cmtType) {
			this.cmtType = cmtType;
		}

		private final Log log = LogFactory.getLog(getClass());
		private IClientForJsonService clientForJsonServiceImpl;
		
		@Action("/client/api")
		public void apiAction() throws UnsupportedEncodingException, ServletException {
			String url ="";
			String responseJson = "";
			try{
				Map<String,Integer> nodeMap = this.getRequstLocation();
				RequestObject ro = new RequestObject();
				RequestHead head = new RequestHead();
				RequestBody body = new RequestBody();
				body.setUdid(udid);
				ro.setHead(head);
				ro.setBody(body);
				switch (nodeMap.get(method)) {
			
				case 1:
					break;
				case 2:
					break;
				case 3:
					body.setPlaceId(placeId);
					ResponseObject responseObj = clientForJsonServiceImpl.queryDestDetails(ro);
					ClientPlace cp = responseObj.getBody().getPlace();
					responseJson = this.ObjectToJson(responseObj);
					break;
				case 4:
					body.setKeyWord(keyword);
					responseJson = clientForJsonServiceImpl.queryAutoLocationInfos(ro);		
					break;
				case 5:
					body.setPlaceId(placeId);
					body.setStage(stage);
					
					responseJson = clientForJsonServiceImpl.queryTicketsList(ro);
					break;
				case 8:
					body.setUserName(userName);
					body.setPassword(password);
					responseJson = clientForJsonServiceImpl.clientLogin(ro,this.getRequest());
					break;
				case 9:
					head.setFirstChannel(firstChannel);
					body.setMobile(mobile);
					responseJson = clientForJsonServiceImpl.clientGetVlidateCode(ro);
					break;
				case 10:
					body.setMobile(mobile);
					body.setPassword(password);
					body.setValidateCode(validateCode);
					head.setFirstChannel(firstChannel);
					responseJson = clientForJsonServiceImpl.clientSubRegister(ro);
					break;
				case 11:
					//responseXml = clientServiceImpl.getRelateProduct(ro);
					break;
				case 12:
					body.setProductId(productId);
					body.setBranchId(branchId);
					responseJson = clientForJsonServiceImpl.getMainProdTimePrice(ro);
					break;
				case 13:
	
					body.setDate(visitTime);
					body.setProductId(productId);
					body.setBranchId(branchId);
					//设置需要过滤的产品ID 不能参与优惠活动
					ro.getBody().setFilterProductMap(this.getFilterProducIds());
					ResponseObject response= clientForJsonServiceImpl.queryTimePriceByProductIdAndDate(ro);
					if(isWP7()){
						response.getBody().setPromotionName("");
					}
					JSONObject json = JSONObject.fromObject(response);
					responseJson = json.toString();
					break;
				case 14:
					body.setProductId(productId);	
					responseJson = clientForJsonServiceImpl.queryProductDetails(ro);
					break;
				case 15:
					head.setUserId(userId);
					body.setOrderId((orderId==null||"".equals(orderId))?null:orderId);
					body.setPage(page==null||"".equals(page)?null:page);
					body.setPageSize(pageSize==null||"".equals(pageSize)?null:pageSize);
					responseJson = clientForJsonServiceImpl.queryUserOrderList(ro);
					break;
				case 16:
					//this.processCommitOrderArguments(ro);
					//responseJson = clientForJsonServiceImpl.commitOrder(ro);
					break;
				case 17:
					 responseJson = clientForJsonServiceImpl.queryGouponOnList(ro);
					break;
				case 18:
					/**
					 * 处理android 乱码
					 */
					if(isAndroid()){
						try {
							content = new String(content.getBytes("iso-8859-1"), "utf-8");
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					body.setContent(content);
					body.setEmail(email);
					if(isAndroid()){
						head.setFirstChannel(ClutterConstant.CLIENT_NAME.ANDROID.name());
					}
					if(isIos()){
						head.setFirstChannel(ClutterConstant.CLIENT_NAME.IPHONE.name());
					}
					if(isWP7()){
						head.setFirstChannel(ClutterConstant.CLIENT_NAME.WP7.name());
					}
					responseJson = clientForJsonServiceImpl.addfeedBack(ro);
					break;
				case 21:
					body.setOrderId(orderId);
					body.setMobile(mobile);
					responseJson = clientForJsonServiceImpl.reSendSms(ro);
					break;
				case 23:
					break;
				case 24:
					 url = ClutterConstant.getSearchHost()+"/search/clientSearch!getAutoCompletePlace.do?"+this.processSearchAction();
					//responseJson = HttpClientUtil.doGet(url);
					 responseJson = HttpsUtil.requestGet(url);
					break;
				case 25:
					url = ClutterConstant.getSearchHost()+"/search/clientSearch!getChinaTreeByHasProduct.do?"+this.processSearchAction();
					//responseJson = HttpClientUtil.doGet(url);
					responseJson = HttpsUtil.requestGet(url);
					break;
				case 26:
					//切换城市 景点改为关键字搜索
					url = ClutterConstant.getSearchHost()+"/search/clientSearch!placeSearch.do?"+this.processSearchAction();
					//responseJson = HttpClientUtil.doGet(url);
					responseJson = HttpsUtil.requestGet(url);
					break;
				case 27:
					body.setPlaceId(placeId);
					ClientPlace clientPlace = clientForJsonServiceImpl.getPlaceDetails(ro);
					
					responseJson = this.ObjectToJson(clientPlace);
					break;
				case 28:
					body.setKeyWord(this.keyword);
					url = ClutterConstant.getSearchHost()+"/search/clientSearch!getAutoCompletePlace.do?stage=1&keyword="+UniformResourceLocator.encode(this.keyword);
					//responseJson = HttpClientUtil.doGet(url);
					responseJson = HttpsUtil.requestGet(url);
					JSONArray array  = JSONArray.fromObject("["+responseJson+"]");
					if (array!=null&&array.size()>0) {
						JSONObject obj = array.getJSONObject(0);
						List<?> list =  (List<?>) obj.get("placeListJson");
						if(list!=null&&list.size()>0){
							@SuppressWarnings("unchecked")
							Map<String,String> map = (Map<String,String>)list.get(0);
							ViewPlace vp = new ViewPlace();
							vp.setId(Long.valueOf(map.get("id")));
							vp.setName(map.get("name"));
							responseJson = JSONObject.fromObject(vp).toString();
						}
						
					}
					break;
				case 29:
					body.setCouponState(couponState);
					head.setUserId(userId);
					responseJson = clientForJsonServiceImpl.queryUserCouponInfo(ro);
					break;
				case 30:
					body.setProductId(productId);
					responseJson = clientForJsonServiceImpl.querySimpleProductDetails(ro);
					break;
				case 31:
					body.setStage(stage);
					body.setPlaceId(placeId);
					responseJson = clientForJsonServiceImpl.queryHotelBranches(ro);
					break;
				case 32:
					break;
				case 33:
					//热门城市 用户景点城市切换 
					url = ClutterConstant.getSearchHost()+"/search/clientSearch!getChinaTreeByHasProduct.do?fromPage=isClient&method=changeDest&productType=hasTicket";
					//responseJson = HttpClientUtil.doGet(url);
					responseJson = HttpsUtil.requestGet(url);
					break;
				case 34:
					url = ClutterConstant.getSearchHost()+"/search/clientSearch!getAutoCompleteHotelSpot.do?"+this.processSearchAction();
					//responseJson = HttpClientUtil.doGet(url);
					responseJson = HttpsUtil.requestGet(url);
					break;
				case 35:
					url = ClutterConstant.getSearchHost()+"/search/clientSearch!getClientSubjectByCity.do?"+this.processSearchAction();
					//responseJson = HttpClientUtil.doGet(url);
					responseJson = HttpsUtil.requestGet(url);
					break;
				case 37:
					//酒店自动完成提示。
					url = ClutterConstant.getSearchHost()+"/search/clientSearch!getAutoCompleteHotelCity.do?"+this.processSearchAction();
					//responseJson = HttpClientUtil.doGet(url);
					responseJson = HttpsUtil.requestGet(url);
					break;
				case 38:
					//自由行产品全局搜索
					url = ClutterConstant.getSearchHost()+"/search/clientRouteSearch!routeSearch.do?"+this.processSearchAction();
					//responseJson = HttpClientUtil.doGet(url);
					responseJson = HttpsUtil.requestGet(url);
					break;
				case 39:
					//线路主题
					url = ClutterConstant.getSearchHost()+"/search/clientRouteSearch!getRouteSubject.do?"+this.processSearchAction();
					//responseJson = HttpClientUtil.doGet(url);
					responseJson = HttpsUtil.requestGet(url);
					break;
				case 40:
					//线路产品自动提示。 
					url = ClutterConstant.getSearchHost()+"/search/clientRouteSearch!getAutoComplete.do?"+this.processSearchAction();
					//responseJson = HttpClientUtil.doGet(url);
					responseJson = HttpsUtil.requestGet(url);
					break;
				case 41:
					//线路产品自动提示。
					url = ClutterConstant.getSearchHost()+"/search/clientSearch!nearSearch.do?"+this.processSearchAction();
					//responseJson = HttpClientUtil.doGet(url);
					responseJson = HttpsUtil.requestGet(url);
					break;
				default:
					break;
				}
			}catch(Exception e){
				StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
			}finally{
				this.sendJsonResult(responseJson);
			}
		}

		public void setClientForJsonServiceImpl(
				IClientForJsonService clientForJsonServiceImpl) {
			this.clientForJsonServiceImpl = clientForJsonServiceImpl;
		}

		private String processSearchAction(){
			String reqParameter = "";
			Map<String, String[]> e = this.getRequest().getParameterMap();
			if (e != null) {
				Set<String> set = e.keySet();

				for (String key : set) {
					if("keyword".equals(key)){
						reqParameter = reqParameter + "&" + key + "=" + UniformResourceLocator.encode(keyword);
					} else if("subject".equals(key)){
						reqParameter = reqParameter + "&" + key + "=" + UniformResourceLocator.encode(subject);
					} else if("spot".equals(key)){
						reqParameter = reqParameter + "&" + key + "=" + UniformResourceLocator.encode(spot);
					} else if("keyword2".equals(key)){
						reqParameter = reqParameter + "&" + key + "=" + UniformResourceLocator.encode(keyword2);
					}else if("toDest".equals(key)){
						reqParameter = reqParameter + "&" + key + "=" + UniformResourceLocator.encode(toDest);
					}else if("cityName".equals(key)){
						reqParameter = reqParameter + "&" + key + "=" + UniformResourceLocator.encode(cityName);
					} else {	
						reqParameter = reqParameter + "&" + key + "=" + e.get(key)[0];
					}

				}
			}
			return reqParameter;
			
		}
		
		@Action("/client/api/getNameByLocation")
		public void  getNameByLocation (){
			String responseJson = "";
			try{
			String url = ClutterConstant.getSearchHost()+"/search/clientSearch!getAutoCompletePlace.do?stage=1&keyword="+UniformResourceLocator.encode(this.keyword);
			//responseJson = HttpClientUtil.doGet(url);
			responseJson = HttpsUtil.requestGet(url);
			JSONArray array  = JSONArray.fromObject("["+responseJson+"]");
			if (array!=null&&array.size()>0) {
				JSONObject obj = array.getJSONObject(0);
				List<?> list =  (List<?>) obj.get("placeListJson");
				if(list!=null&&list.size()>0){
					Map<String,String> map = (Map<String,String>)list.get(0);
					ViewPlace vp = new ViewPlace();
					vp.setId(Long.valueOf(map.get("id")));
					vp.setName(map.get("name"));
					responseJson = JSONObject.fromObject(vp).toString();
				}
			}
			} catch (Exception e) {
				log.error(this.getClass(), e);
			} finally {
				this.sendJsonResult(responseJson);
			}	
		}
		
		@Action("/client/api/queryDestDetails")
		public void queryDestDetails () {
			String responseJson = "";
			try{
				RequestObject ro = new RequestObject();
				RequestHead head = new RequestHead();
				RequestBody body = new RequestBody();
				ro.setHead(head);
				ro.setBody(body);
				body.setPlaceId(placeId);
				ResponseObject responseObj = clientForJsonServiceImpl.queryDestDetails(ro);
				
				responseJson  = this.ObjectToJson(responseObj);
			} catch (Exception e) {
				log.error(this.getClass(), e);
			} finally {
				this.sendJsonResult(responseJson);
			}		
		}
		
		
		
		@Action("/client/api/queryTimePriceByProductIdAndDate")
		public void queryTimePriceByProductIdAndDate () {
			String responseJson = "";
			try{
				RequestObject ro = new RequestObject();
				RequestHead head = new RequestHead();
				RequestBody body = new RequestBody();
				ro.setHead(head);
				ro.setBody(body);
				body.setDate(visitTime);
				body.setProductId(productId);
				body.setBranchId(branchId);
				//设置需要过滤的产品ID 不能参与优惠活动
				ro.getBody().setFilterProductMap(this.getFilterProducIds());
				ResponseObject response= clientForJsonServiceImpl.queryTimePriceByProductIdAndDate(ro);
				if(isWP7()){
					response.getBody().setPromotionName("");
				}
				JSONObject json = JSONObject.fromObject(response);
				responseJson = json.toString();
			} catch (Exception e) {
				log.error(this.getClass(), e);
			} finally {
				this.sendJsonResult(responseJson);
			}		
		}
		
		
		@Action("/client/api/queryGouponOnList")
		public void queryGouponOnList () {
			String responseJson = "";
			try{
				RequestObject ro = new RequestObject();
				RequestHead head = new RequestHead();
				RequestBody body = new RequestBody();
				ro.setHead(head);
				ro.setBody(body);
				 responseJson = clientForJsonServiceImpl.queryGouponOnList(ro);
			} catch (Exception e) {
				log.error(this.getClass(), e);
			} finally {
				this.sendJsonResult(responseJson);
			}		
		}
		
		@Action("/client/api/queryProductDetails")
		public void queryProductDetails() {
			String responseJson = "";
			try{
				RequestObject ro = new RequestObject();
				RequestHead head = new RequestHead();
				RequestBody body = new RequestBody();
				ro.setHead(head);
				ro.setBody(body);
				body.setProductId(productId);	
				responseJson = clientForJsonServiceImpl.queryProductDetails(ro);
			} catch (Exception e) {
				log.error(this.getClass(), e);
			} finally {
				this.sendJsonResult(responseJson);
			}		
		}
		
		
		
		
		@Action("/client/api/queryTicketsList")
		public void queryTicketsList () {
			String responseJson = "";
			try{
				RequestObject ro = new RequestObject();
				RequestHead head = new RequestHead();
				RequestBody body = new RequestBody();
				ro.setHead(head);
				ro.setBody(body);
				body.setPlaceId(placeId);
				body.setStage(stage);
				responseJson = clientForJsonServiceImpl.queryTicketsList(ro);
			} catch (Exception e) {
				log.error(this.getClass(), e);
			} finally {
				this.sendJsonResult(responseJson);
			}		
		}
		
		@Action("/client/api/queryUserOrderList")
		public void queryUserOrderList () {
			String responseJson = "";
			try{
				RequestObject ro = new RequestObject();
				RequestHead head = new RequestHead();
				RequestBody body = new RequestBody();
				ro.setHead(head);
				ro.setBody(body);
				head.setUserId(userId);
				body.setOrderId((orderId==null||"".equals(orderId))?null:orderId);
				body.setPage(page==null||"".equals(page)?null:page);
				body.setPageSize(pageSize==null||"".equals(pageSize)?null:pageSize);
				responseJson = clientForJsonServiceImpl.queryUserOrderList(ro);
			} catch (Exception e) {
				log.error(this.getClass(), e);
			} finally {
				this.sendJsonResult(responseJson);
			}		
		}

		
		@Action("/client/api/getMainProdTimePrice")
		public void getMainProdTimePrice () {
			String responseJson = "";
			try{
				RequestObject ro = new RequestObject();
				RequestHead head = new RequestHead();
				RequestBody body = new RequestBody();
				ro.setHead(head);
				ro.setBody(body);
				body.setProductId(productId);
				body.setBranchId(branchId);
				responseJson = clientForJsonServiceImpl.getMainProdTimePrice(ro);
			} catch (Exception e) {
				log.error(this.getClass(), e);
			} finally {
				this.sendJsonResult(responseJson);
			}		
		}
		
		@Action("/client/api/querySimpleProductDetails")
		public void querySimpleProductDetails () {
			String responseJson = "";
			try{
				RequestObject ro = new RequestObject();
				RequestHead head = new RequestHead();
				RequestBody body = new RequestBody();
				ro.setHead(head);
				ro.setBody(body);
				body.setProductId(productId);
				responseJson = clientForJsonServiceImpl.querySimpleProductDetails(ro);
			} catch (Exception e) {
				log.error(this.getClass(), e);
			} finally {
				this.sendJsonResult(responseJson);
			}		
		}

		@Action("/client/api/queryHotelBranches")
		public void queryHotelBranches () {
			String responseJson = "";
			try{
				RequestObject ro = new RequestObject();
				RequestHead head = new RequestHead();
				RequestBody body = new RequestBody();
				ro.setHead(head);
				ro.setBody(body);
				body.setStage(stage);
				body.setPlaceId(placeId);
				responseJson = clientForJsonServiceImpl.queryHotelBranches(ro);
			} catch (Exception e) {
				log.error(this.getClass(), e);
			} finally {
				this.sendJsonResult(responseJson);
			}		
		}

		
		
		
		@Action("/client/api/queryUserCouponInfo")
		public void queryUserCouponInfo () {
			String responseJson = "";
			try{
				RequestObject ro = new RequestObject();
				RequestHead head = new RequestHead();
				RequestBody body = new RequestBody();
				ro.setHead(head);
				ro.setBody(body);
				body.setCouponState(couponState);
				head.setUserId(userId);
				responseJson = clientForJsonServiceImpl.queryUserCouponInfo(ro);
			} catch (Exception e) {
				log.error(this.getClass(), e);
			} finally {
				this.sendJsonResult(responseJson);
			}		
		}
	
		
		
			
		@Action("/client/api/getAutoCompleteHotelSpot")
		public void getAutoCompleteHotelSpot(){
			String url = ClutterConstant.getSearchHost()+"/search/clientSearch!getAutoCompleteHotelSpot.do?"+this.getRequest().getQueryString();
			try {
				this.getResponse().sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Action("/client/api/getAutoCompletePlace")
		public void  getAutoCompletePlace(){
			String url = ClutterConstant.getSearchHost()+"/search/clientSearch!getAutoCompletePlace.do?"+this.getRequest().getQueryString();
			 try {
					this.getResponse().sendRedirect(url);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		@Action("/client/api/getClientSubjectByCity")
		public void  getClientSubjectByCity(){
			String url = ClutterConstant.getSearchHost()+"/search/clientSearch!getClientSubjectByCity.do?"+this.getRequest().getQueryString();
			try {
				this.getResponse().sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Action("/client/api/getAutoCompleteHotelCity")
		public void getAutoCompleteHotelCity () {
			String url = ClutterConstant.getSearchHost()+"/search/clientSearch!getAutoCompleteHotelCity.do?"+this.getRequest().getQueryString();
			try {
				this.getResponse().sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		@Action("/client/api/routeSearch")
		public void routeSearch(){
			String url = ClutterConstant.getSearchHost()+"/search/clientRouteSearch!routeSearch.do?"+this.getRequest().getQueryString();
			try {
				this.getResponse().sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Action("/client/api/getRouteSubject")
		public void  getRouteSubject(){
			String url = ClutterConstant.getSearchHost()+"/search/clientRouteSearch!getRouteSubject.do?"+this.getRequest().getQueryString();
			try {
				this.getResponse().sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		@Action("/client/api/nearSearch")
		public void nearSearch(){
			String url = ClutterConstant.getSearchHost()+"/search/clientSearch!nearSearch.do?"+this.getRequest().getQueryString();
			try {
				this.getResponse().sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Action("/client/api/placeSearch")
		public void placeSearch(){
			String url = ClutterConstant.getSearchHost()+"/search/clientSearch!placeSearch.do?"+this.getRequest().getQueryString();
			try {
				this.getResponse().sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Action("/client/api/getAutoComplete")
		public void getAutoComplete(){
			String url = ClutterConstant.getSearchHost()+"/search/clientRouteSearch!getAutoComplete.do?"+this.getRequest().getQueryString();
			try {
				this.getResponse().sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Action("/client/api/login")
		public void login() {
			String responseJson = "";

			try {
				RequestObject ro = new RequestObject();
				RequestHead head = new RequestHead();
				RequestBody body = new RequestBody();
				ro.setHead(head);
				ro.setBody(body);
				
				body.setLoginChannel(loginChannel);
				head.setFirstChannel(firstChannel);
				body.setCooperationUid(uid);
				body.setUserName(userName);
				body.setPassword(password);
				body.setScreenName(screenName);
				if(isAndroid()){
					try {
						screenName = new String(screenName.getBytes("iso-8859-1"), "utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				responseJson = clientForJsonServiceImpl.clientLogin(ro,this.getRequest());
	
			} catch (Exception e) {
				log.error("userName: " + userName);
				log.error("password: " + password);
				log.error(this.getClass(), e);
			} finally {
				this.sendJsonResult(responseJson);
			}
		}
	
		
		@Action("/client/api/validateCode")
		public void mobileValidateCode() {
			String responseJson = "";
			try{
				RequestObject ro = new RequestObject();
				RequestHead head = new RequestHead();
				RequestBody body = new RequestBody();
				ro.setHead(head);
				ro.setBody(body);
				head.setFirstChannel(firstChannel);
				body.setMobile(mobile);
				responseJson = clientForJsonServiceImpl.clientGetVlidateCode(ro);
			} catch (Exception e) {
				log.error("firstChannel: " + firstChannel);
				log.error("mobile: " + mobile);
				log.error(this.getClass(), e);
			} finally {
				this.sendJsonResult(responseJson);
			}			
		}
		
		
		@Action("/client/api/reg")
		public void reg() {
			String responseJson = "";
			try{
				RequestObject ro = new RequestObject();
				RequestHead head = new RequestHead();
				RequestBody body = new RequestBody();
				ro.setHead(head);
				ro.setBody(body);
				body.setMobile(mobile);
				body.setPassword(password);
				body.setValidateCode(validateCode);
				head.setFirstChannel(firstChannel);
				responseJson = clientForJsonServiceImpl.clientSubRegister(ro);
				this.sendJsonResult(responseJson);
			} catch (Exception e) {
				log.error("firstChannel: " + firstChannel);
				log.error("mobile: " + mobile);
				log.error("validateCode: " + validateCode);
				log.error("password: " + password);
				log.error(this.getClass(), e);
			} finally {
				this.sendJsonResult(responseJson);
			}
			
		}
		
		

		
		@SuppressWarnings("unchecked")
		private  Map<String,String> getFilterProducIds() {
			Object object = MemcachedUtil.getInstance().get(ClutterConstant.CLUTTER_FILTER_PRODUCT_KEYS);
			if(object == null){
				Map<String,String> map = new HashMap<String,String>();
				//String json = HttpClientUtil.doGet("http://www.lvmama.com/client/datas/filter.json");
				String json = HttpsUtil.requestGet("http://www.lvmama.com/client/datas/filter.json");
				JSONArray array =JSONArray.fromObject(json);
				if(array!=null&&array.size()>0){
					for (Object object2 : array) {
						map.put(String.valueOf(object2), String.valueOf(object2));
					}
				}
				MemcachedUtil.getInstance().set(ClutterConstant.CLUTTER_FILTER_PRODUCT_KEYS, MemcachedUtil.getDateAfter(120),map);
				return map;
			} else {
				return (Map<String,String>)object;
			}
		}
		
		private void processCommitOrderArguments(RequestObject ro){
			ro.getHead().setFirstChannel(firstChannel);
			ro.getHead().setSecondChannel(secondChannel);
			ro.getHead().setUserId(userId);
			ro.getBody().setVisitTime(visitTime);
			ro.getBody().setLeaveTime(leaveTime);
			ro.getBody().setPayTarget(payTarget);

			this.userName=UniformResourceLocator.decode(userName);
			ro.getBody().setUserName(userName);
			ro.getBody().setMobile(mobile);
			ro.getBody().setProductId(productId);
			ro.getBody().setPromotionEnabled(promotionEnabled);
			ro.getBody().setCouponCode(couponCode);
			
			List<RequestOrderItem> orderItemList = new ArrayList<RequestOrderItem> ();
			if(orderItem.indexOf("-")!=-1){
				String[] orderItemArray = orderItem.split("-");
				for (String string : orderItemArray) {
					String[] itemArray = string.split("_");
					RequestOrderItem roi = new RequestOrderItem();
					if(StringUtils.isEmpty(itemArray[0])){
						continue;
					}
					roi.setBranchId(itemArray[0]);
					roi.setQuantity(itemArray[1]);
					orderItemList.add(roi);
				}
			} else {
	
				String[] itemArray = orderItem.split("_");
				RequestOrderItem roi = new RequestOrderItem();
				roi.setBranchId(itemArray[0]);
				roi.setQuantity(itemArray[1]);
				orderItemList.add(roi);
				
			}
			ro.getBody().setOrderItemList(orderItemList);
		}
		
		
		@Action("/client/api/commitOrder")
		public void commitOrder (){
			String responseJson = "";
			try{
			RequestObject ro = new RequestObject();
			RequestHead head = new RequestHead();
			RequestBody body = new RequestBody();
			ro.setHead(head);
			ro.setBody(body);
			this.processCommitOrderArguments(ro);
			if(ro.getBody().getOrderItemList().size()!=0){
				responseJson = clientForJsonServiceImpl.commitOrder(ro);
			}
			} catch (Exception e) {
				StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
			} finally {
				this.sendJsonResult(responseJson);
			}
			
		}
		
		//重发短信凭证信息
		@Action("/client/api/reSendSmsCert")
		public void reSendSmsCert () {
			String responseJson="{}";
			try {
			this.checkParamter(orderId, errorInfo);	
			this.checkParamter(userId, errorInfo);	
			if (errorInfo.size()>0) {
				responseJson = this.getErrorJson(errorInfo);
			} else{ 
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId));
			
			if(!order.getUserId().equals(userId)){
				this.sendAjaxResult("{\"message\",\"Access is denied\"}");
				return;
			}
			
			if (order.isShouldSendCert()) {
				String orderHeadId= order.getOrderId().toString();
				String mobileNumber = "";
				List<OrdPerson> personList  = order.getPersonList();
				for (OrdPerson ordPerson : personList) {
					if (Constant.ORD_PERSON_TYPE.CONTACT.name().equals(ordPerson.getPersonType())) {
						mobileNumber = ordPerson.getMobile();
					}
				}
				if(!(StringUtils.isEmpty(orderHeadId)&&StringUtils.isEmpty(mobileNumber))){
					orderMessageProducer.sendMsg(MessageFactory.newCertSmsSendMessage(Long.valueOf(orderHeadId),mobileNumber));
				}
				responseJson = "{\"message\":\"1\"}";
			}
			}
			} catch (Exception ex) {
				responseJson = "{\"message\":\"server error\"}";
				ex.printStackTrace();
			} finally {
				this.sendAjaxResult(responseJson);
			}
			
		}
		
		@Action("/client/api/passJson")
		public void getPassJson() {
			String responseJson="{}";
			this.checkParamter(orderId, errorInfo);	
			this.checkParamter(userId, errorInfo);	
			if (errorInfo.size()>0) {
				responseJson = this.getErrorJson(errorInfo);
			} else{ 
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId));
			
			if(!order.getUserId().equals(userId)){
				this.sendAjaxResult("{\"message\",\"Access is denied\"}");
				return;
			}
			
//			if (!(order.isCanToPay()&&order.isPaymentSucc())) {
//				this.sendAjaxResult("{\"message\",\"order statu error\"}");
//				return;
//			}
//			
//			if(!order.isCanToPay()&&order.isApprovePass()){
//				this.sendAjaxResult("{\"message\",\"order statu error\"}");
//				return;
//			}
			
			log.info("create pass.json file");
			Map<String,Object> passJson = new HashMap<String, Object>();
			passJson.put("formatVersion", 1);
			passJson.put("passTypeIdentifier", "pass.lvmama.order");
			passJson.put("serialNumber", order.getOrderId().toString());
			//passJson.put("authenticationToken", "vxwxd7J8AlNNFPS8k0a0FfUFtq0ewzFdc");
			
			passJson.put("relevantDate", DateUtil.formatDate(order.getVisitTime(), "yyyy-MM-dd")+"T09:00-03:00");
			//passJson.put("relevantDate", "2012-11-19T16:30-08:00");
			passJson.put("teamIdentifier", "KB7B8SQENU");
			passJson.put("description", "Lvmama Pass");
			passJson.put("logoText", order.getMainProduct().getProductName());
			passJson.put("foregroundColor","rgb(255, 255, 255)");
			passJson.put("backgroundColor","rgb(203, 71, 169)");
			passJson.put("organizationName","驴妈妈旅游网");
			
			Map<String,List<Map<String,Object>>> genericTempMap = new HashMap<String,List<Map<String,Object>>>();
			List<Map<String,Object>> primaryFieldsList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> secondFieldsList = new ArrayList<Map<String,Object>>();
			
			List<Map<String,Object>> auxiliaryFieldsList = new ArrayList<Map<String,Object>>();
			
			Map<String,Object> primaryFieldMap = new HashMap<String, Object>();
			primaryFieldMap.put("key", "orderId");
			primaryFieldMap.put("value", "订单号:"+order.getOrderId());
			primaryFieldsList.add(primaryFieldMap);

			genericTempMap.put("primaryFields", primaryFieldsList);
		
			
			Map<String,Object> secondFieldMap1 = new HashMap<String, Object>();
			secondFieldMap1.put("key", "price");
			secondFieldMap1.put("label", "价格");
			secondFieldMap1.put("value", String.valueOf(order.getOughtPayYuan()));
		
			Map<String,Object> secondFieldMap2 = new HashMap<String, Object>();
			secondFieldMap2.put("key", "backVisitDate");
			if (order.isHotel()){
				secondFieldMap2.put("label", "入住时间");
			} else {
				secondFieldMap2.put("label", "游玩时间");
			}
			
			secondFieldMap2.put("value", order.getZhVisitTime());
			secondFieldMap2.put("textAlignment", "PKTextAlignmentRight");

			secondFieldsList.add(secondFieldMap1);
			secondFieldsList.add(secondFieldMap2);

		
			genericTempMap.put("secondaryFields", secondFieldsList);
			
			
			List<OrdPerson> personList  = order.getPersonList();
			for (OrdPerson ordPerson : personList) {
				if (Constant.ORD_PERSON_TYPE.CONTACT.name().equals(ordPerson.getPersonType())) {
					
					Map<String,Object> personNameMap = new HashMap<String, Object>();
					personNameMap.put("key", "visitName");
					personNameMap.put("label", "取票人姓名");
					personNameMap.put("value", null==ordPerson.getName()?"":ordPerson.getName());
					auxiliaryFieldsList.add(personNameMap);
					
					Map<String,Object> personMobile = new HashMap<String, Object>();
					
					personMobile.put("key", "visitUserMobile");
					personMobile.put("label", "取票人手机");
					personMobile.put("value", ordPerson.getMobile());
					personMobile.put("textAlignment", "PKTextAlignmentRight");
					auxiliaryFieldsList.add(personMobile);
				}
				
			}
			
			genericTempMap.put("auxiliaryFields", auxiliaryFieldsList);
			passJson.put("generic", genericTempMap);
			
			List<Map<String,Object>> backFieldList = new ArrayList<Map<String,Object>>();
			
			
			Map<String,Object> memoMap = new HashMap<String, Object>();
			memoMap.put("label", "提示");
			memoMap.put("key", "memo");
			memoMap.put("value", "此卡仅作为预订记录，订单状态以“驴妈妈客户端”里面的为准。如需更改订单信息，请致电驴妈妈客服 10106060。 " +
					"入园/入住，仍以下单时给到的“二维码短信”“通关短信”“实体票”为准。如若遗失入园/入住短信，请致电驴妈妈客服 10106060。");
			backFieldList.add(memoMap);
			
			
			Map<String,Object> productNameMap = new HashMap<String, Object>();
			productNameMap.put("label", "产品名称");
			productNameMap.put("key", "productName");
			productNameMap.put("value", order.getMainProduct().getProductName());
			backFieldList.add(productNameMap);
			
		
			Map<String,Object> visitTimeMap = new HashMap<String, Object>();
			visitTimeMap.put("key", "visitDate");
			if (Constant.PRODUCT_TYPE.HOTEL.name().equals(order.getMainProduct().getProductType())){
				visitTimeMap.put("label", "入住时间");
			} else {
				visitTimeMap.put("label", "游玩时间");
			}
			
			visitTimeMap.put("value", order.getZhVisitTime());
			backFieldList.add(visitTimeMap);
			
			
			Map<String,Object> quantityMap = new HashMap<String, Object>();
			quantityMap.put("label", "数量");
			quantityMap.put("key", "quantity");
			quantityMap.put("value", order.calcTotalPersonQuantity());
			backFieldList.add(quantityMap);


			Map<String,Object> payMap = new HashMap<String, Object>();
			payMap.put("label", "支付方式");
			payMap.put("key", "payWay");
			payMap.put("value", order.isPayToLvmama()?"线上支付":"取票付款");
			backFieldList.add(payMap);
			
			Map<String,Object> priceMap = new HashMap<String, Object>();
			priceMap.put("label", "价格");
			priceMap.put("key", "backPrice");
			priceMap.put("value", String.valueOf(order.getOughtPayYuan()));
			backFieldList.add(priceMap);
			
			passJson.put("backFields", backFieldList);
			
			JSONObject json = JSONObject.fromObject(passJson);
			responseJson = json.toString();

			}
			this.sendAjaxResult(responseJson);
		}

		@Action("/client/api/queryCommentListByPlaceId")
		public void queryCommentListByPlaceId () {
			String responseJson = "{}";
			try {
			this.checkParamter(placeId, errorInfo);	
			this.checkParamter(page, errorInfo);	
			if (errorInfo.size()>0) {
				responseJson = this.getErrorJson(errorInfo);
			} else{
				responseJson = clientForJsonServiceImpl.queryPlaceComments(userId,placeId,Long.valueOf(page));
			} 
			} catch (Exception e) {
				log.error("placeId: " + placeId);
				log.error(this.getClass(), e);
				responseJson = "{\"message\":\"server error\"}";
			} finally {
				this.sendJsonResult(responseJson);
			}
		}
		
		@Action("/client/api/queryWaitForCommentNumber")
		public void queryWaitForCommentNumber() {
			String responseJson = "{}";
			try {
				this.checkParamter(userId, errorInfo);
				if (errorInfo.size()>0) {
					responseJson = this.getErrorJson(errorInfo);
				} else {
					String count = clientForJsonServiceImpl.queryWaitForCommentNumber(userId);
					responseJson = "{\"waitCommentNumber\":"+count+"}";
				} 
			} catch (Exception e) {
				log.error("userId: " + userId);
				log.error(this.getClass(), e);
				responseJson = "{\"message\":\"server error\"}";
			} finally {
				this.sendJsonResult(responseJson);
			}
		}
		
		@Action("/client/api/queryCommentListByUserId")
		public void queryCommentListByUserId () {
			String responseJson = "{}";
			try {
			this.checkParamter(userId, errorInfo);
			this.checkParamter(page, errorInfo);
			if (errorInfo.size()>0) {
				responseJson = this.getErrorJson(errorInfo);
			} else {
				responseJson = clientForJsonServiceImpl.queryCommentByUserId(userId, Long.valueOf(page), cmtType);
			} 
			} catch (Exception e) {
				log.error("userId: " + userId);
				log.error(this.getClass(), e);
				responseJson = "{\"message\":\"server error\"}";
			} finally {
				this.sendJsonResult(responseJson);
			}
		}
		
		@Action("/client/api/userShareLog")
		public void userShareLog () {
			String responseJson = "{}";
			try {
				this.checkParamter(uid, errorInfo);
				this.checkParamter(screenName, errorInfo);
				this.checkParamter(shareChannel, errorInfo);
				this.checkParamter(shareTarget, errorInfo);
				if (errorInfo.size()>0) {
					responseJson = this.getErrorJson(errorInfo);
				} else {
					clientForJsonServiceImpl.insertShareLog(uid, screenName, shareChannel, shareTarget);
				}
			} catch (Exception e) {
				log.error("userId: " + userId);
				log.error(this.getClass(), e);
				responseJson = "{\"message\":\"server error\"}";
			} finally {
				this.sendJsonResult(responseJson);
			}
		}
		
		@Action("/client/api/hotelCities")
		public void hotelCities () {
			String url = ClutterConstant.getSearchHost()+"/search/clientSearch!getCityListByHasProduct.do?"+this.getRequest().getQueryString();
			try {
				this.getResponse().sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private boolean paramtersIsError(String paramter) {
			if (StringUtils.isEmpty(paramter)) {
				return true;
			}
			return false;
		}
		
		
		
		
		private void checkParamter(String object,Map<String,String> errorInfo){
			if (StringUtils.isEmpty(object)) {
				errorInfo.put("message", "paramter error");
			}
		}
		
		private String getErrorJson(Map<String,String> errorInfo){
			JSONObject json = JSONObject.fromObject(errorInfo);
			return json.toString();
		}
		
		@Action("/client/api/commitComment")
		public void commitComment () {
			String responseJson = "{}";
			try {
				this.checkParamter(cmtType, errorInfo);
				this.checkParamter(userId, errorInfo);
				this.checkParamter(objectId, errorInfo);
				if(isAndroid()){
					try {
						content = new String(content.getBytes("iso-8859-1"), "utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				this.checkParamter(content, errorInfo);
				
				if (errorInfo.size()>0) {
					responseJson = this.getErrorJson(errorInfo);
				} else {
					String[] latitudeInfoArray = latitudeInfo.split(",");
					if (latitudeInfoArray == null ||latitudeInfoArray.length!=5) {
						responseJson = "{\"message\":\"latitudeInfo paramter error\"}";
					} else {
						List<ClientCmtLatitude> clientCmtLatitudeList = new ArrayList<ClientCmtLatitude>();
						for (String str : latitudeInfoArray) {
							String[] keys = str.split("_");
							 ClientCmtLatitude ccl = new ClientCmtLatitude();
						        ccl.setLatitudeId(keys[0]);
						        ccl.setScore(Integer.valueOf(keys[1]));
						        clientCmtLatitudeList.add(ccl);
						}

						responseJson = clientForJsonServiceImpl.commitComments(userId, clientCmtLatitudeList, objectId, content, cmtType);
					}
				}
			} catch (Exception e) {
				log.error(this.getClass(), e);
				responseJson = "{\"message\":\"server error\"}";
			} finally {
				this.sendJsonResult(responseJson);
			}
		}
		
		@Action("/client/api/queryOrderList")
		public void queryOrderList () {
			
		}
		
		@Action("/client/api/queryOrderDetails")
		public void queryOrderDetails () {
			
		}
		
		@Action("/client/api/queryCommentWaitForOrder")
		public void queryOrderWaitForComment () {
			String responseJson = "{}";
			try {
				this.checkParamter(userId, errorInfo);
				this.checkParamter(page, errorInfo);
				
				if (errorInfo.size()>0) {
					responseJson = this.getErrorJson(errorInfo);
				} else {
					responseJson = clientForJsonServiceImpl.getOrderWaitingComments(userId,Long.valueOf(page));	
				}
			} catch (Exception e) {
				log.error(this.getClass(), e);
				responseJson = "{\"message\":\"server error\"}";
			} finally {
				this.sendJsonResult(responseJson);
			}
		}
		
		@Action("/client/api/getPlaceDetails")
		public void getPlaceDetails () {
			String responseJson = "{}";
			try{
				this.checkParamter(placeId, errorInfo);
				if (errorInfo.size()>0) {
					responseJson = this.getErrorJson(errorInfo);
				} else {
					ClientPlace cp = clientForJsonServiceImpl.queryPlaceDetails(placeId);
					
					//最佳季节
					responseJson = this.ObjectToJson(cp);
				}
			} catch (Exception e) {
				log.error("placeId: " + placeId);
				log.error(this.getClass(), e);
				responseJson = "{\"message\":\"server error\"}";
			} finally {
				this.sendJsonResult(responseJson);
			}
		}
		
	
		

		@Action("/client/api/changeDest")
		public void changeDest() {
			String url  = ClutterConstant.getSearchHost()+"/search/clientSearch!getChinaTreeByHasProduct.do?fromPage=isClient&productType=hasTicket";
			try {
				this.getResponse().sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		@Action("/client/api/feedback")
		public void commitFeedBack () {
			String responseJson = "{}";
			try{
				if(!this.paramtersIsError(content)||!this.paramtersIsError(email)){
					RequestObject ro = new RequestObject();
					RequestHead head = new RequestHead();
					RequestBody body = new RequestBody();
					ro.setHead(head);
					ro.setBody(body);
					
					if(isAndroid()){
						head.setFirstChannel(ClutterConstant.CLIENT_NAME.ANDROID.name());
					}
					if(isIos()){
						head.setFirstChannel(ClutterConstant.CLIENT_NAME.IPHONE.name());
					}
					if(isWP7()){
						head.setFirstChannel(ClutterConstant.CLIENT_NAME.WP7.name());
					}
					body.setContent(content);
					body.setEmail(email);
					responseJson = clientForJsonServiceImpl.addfeedBack(ro);
				}else {
					responseJson = "{\"message\":\"paramter error\"}";
				}
			} catch (Exception e) {
				log.error("email: " + email);
				log.error("content: " + content);
				log.error(this.getClass(), e);
				responseJson = "{\"message\":\"server error\"}";
			} finally {
				this.sendJsonResult(responseJson);
			}
	
		}
		
		@Action("/client/api/getCommentLatitudeInfos")
		public void getCommentLatitudeInfos () {
			String responseJson = "{}";
			try{
				if(!this.paramtersIsError(placeId)||!this.paramtersIsError(orderId)){
					responseJson = clientForJsonServiceImpl.getCommentLatitudeInfos(placeId, orderId);
				}else {
					responseJson = "{\"message\":\"paramter error\"}";
				}
			} catch (Exception e) {
				log.error("email: " + email);
				log.error("content: " + content);
				log.error(this.getClass(), e);
				responseJson = "{\"message\":\"server error\"}";
			} finally {
				this.sendJsonResult(responseJson);
			}
		}
		
		@Action("/client/api/getUserInfoByUserId")
		public void getUserInfoByUserId() {
			String responseJson = "{}";
			try{
				if(!this.paramtersIsError(userId)){
					responseJson = clientForJsonServiceImpl.getUserByUserId(userId);
				}else {
					responseJson = "{\"message\":\"paramter error\"}";
				}
			} catch (Exception e) {
				log.error(this.getClass(), e);
				responseJson = "{\"message\":\"server error\"}";
			} finally {
				this.sendJsonResult(responseJson);
			}
			
		}
		
		//http://www.lvmama.com/guide/ajax/api.php?action=getOrgColumnInfoJson&placeid=1&columnid=42
	
		

		public Map<String,Integer> getRequstLocation(){
			Map<String,Integer> map = new HashMap<String,Integer>();
			map.put("queryDestList", 0);
			map.put("placeList", 1);
			map.put("queryGps", 2);
			map.put("queryDestDetails", 3);
			map.put("queryAutoLocationInfos", 4);
			map.put("queryTicketsList", 5);
			map.put("querHotelProductList", 6);
			map.put("queryDestRouteList", 7);
			map.put("clientLogin", 8);
			map.put("clientGetVlidateCode", 9);
			map.put("clientSubRegister", 10);
			map.put("getRelateProduct", 11);
			map.put("getMainProdTimePrice", 12);
			map.put("queryTimePriceByProductIdAndDate", 13);
			map.put("queryProductDetails", 14);
			map.put("queryUserOrderList", 15);
			map.put("commitOrder", 16);
			map.put("queryGouponOnList", 17);
			map.put("addfeedBack", 18);
			map.put("countPlaceInfos", 19);
			map.put("getPassCode", 20);
			map.put("reSendSms", 21);
			map.put("getPlaceArround", 22);
			map.put("openClient", 23);
			map.put("autoComplete", 24);
			map.put("changeDest", 25);
			map.put("search", 26);
			map.put("getPlaceDetails", 27);
			map.put("getNameByLocation", 28);
			map.put("queryUserCouponInfo", 29);
			map.put("querySimpleProductDetails", 30);
			map.put("queryHotelBranches", 31);
			map.put("getCities", 32);
			map.put("getHotCities", 33);
			map.put("getHotelArround", 34);
			map.put("getPlaceTheme", 35);
			map.put("getHotelCityAutoComplete", 37);
			map.put("routeSearch", 38);
			map.put("getRouteSubject", 39);
			map.put("routeAutoComplete", 40);
			map.put("nearSearch", 41);
			return map;
		} 
		
		
		public String ObjectToJson(Object obj){
			if(obj==null){
				return "{}";
			}
			JSONObject json = JSONObject.fromObject(obj);
			return json.toString();
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getVisitTime() {
			return visitTime;
		}

		public void setVisitTime(String visitTime) {
			this.visitTime = visitTime;
		}

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public String getPlaceId() {
			return placeId;
		}

		public void setPlaceId(String placeId) {
			this.placeId = placeId;
		}

		public String getStage() {
			return stage;
		}

		public void setStage(String stage) {
			this.stage = stage;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

	

		public String getFirstChannel() {
			return firstChannel;
		}

		public void setFirstChannel(String firstChannel) {
			this.firstChannel = firstChannel;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getKeyword() {
			return keyword;
		}

		public void setKeyword(String keyword) throws UnsupportedEncodingException {
			keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
			this.keyword = java.net.URLDecoder.decode(keyword, "utf-8");
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getValidateCode() {
			return validateCode;
		}

		public void setValidateCode(String validateCode) {
			this.validateCode = validateCode;
		}

		public String getPage() {
			return page;
		}

		public void setPage(String page) {
			this.page = page;
		}

		public String getPageSize() {
			return pageSize;
		}

		public void setPageSize(String pageSize) {
			this.pageSize = pageSize;
		}

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getProductType() {
			return productType;
		}

		public void setProductType(String productType) {
			this.productType = productType;
		}

		public float getLatitude() {
			return latitude;
		}

		public void setLatitude(float latitude) {
			this.latitude = latitude;
		}

		public float getLongitude() {
			return longitude;
		}

		public void setLongitude(float longitude) {
			this.longitude = longitude;
		}

		public String getCouponState() {
			return couponState;
		}

		public void setCouponState(String couponState) {
			this.couponState = couponState;
		}

		public String getLeaveTime() {
			return leaveTime;
		}

		public void setLeaveTime(String leaveTime) {
			this.leaveTime = leaveTime;
		}

		public String getOrderItem() {
			return orderItem;
		}

		public void setOrderItem(String orderItem) {
			this.orderItem = orderItem;
		}

		public String getPayTarget() {
			return payTarget;
		}

		public void setPayTarget(String payTarget) {
			this.payTarget = payTarget;
		}

		public String getPromotionEnabled() {
			return promotionEnabled;
		}

		public void setPromotionEnabled(String promotionEnabled) {
			this.promotionEnabled = promotionEnabled;
		}

		public String getCouponCode() {
			return couponCode;
		}

		public void setCouponCode(String couponCode) {
			this.couponCode = couponCode;
		}

		public String getBranchId() {
			return branchId;
		}

		public void setBranchId(String branchId) {
			this.branchId = branchId;
		}

		public String getUdid() {
			return udid;
		}

		public void setUdid(String udid) {
			this.udid = udid;
		}

		public String getSecondChannel() {
			return secondChannel;
		}

		public void setSecondChannel(String secondChannel) {
			this.secondChannel = secondChannel;
		}


		public String getCityId() {
			return cityId;
		}

		public void setCityId(String cityId) {
			this.cityId = cityId;
		}


		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) throws UnsupportedEncodingException {
			subject = new String(subject.getBytes("iso-8859-1"), "utf-8");
			this.subject = java.net.URLDecoder.decode(subject, "utf-8");
		}

		public String getSpot() {
			return spot;
		}

		public void setSpot(String spot) throws UnsupportedEncodingException {
			spot = new String(spot.getBytes("iso-8859-1"), "utf-8");
			this.spot = java.net.URLDecoder.decode(spot, "utf-8");
		}

		public String getKeyword2() {
			return keyword2;
		}

		public void setKeyword2(String keyword2) throws UnsupportedEncodingException {
			keyword2 = new String(keyword2.getBytes("iso-8859-1"), "utf-8");
			this.keyword2 = java.net.URLDecoder.decode(keyword2, "utf-8");
		}

		public String getToDest() {
			return toDest;
		}

		public void setToDest(String toDest) throws UnsupportedEncodingException {
			toDest = new String(toDest.getBytes("iso-8859-1"), "utf-8");
			this.toDest = java.net.URLDecoder.decode(toDest, "utf-8");
		}

		public String getCityName() {
			return cityName;
		}

		public void setCityName(String cityName) throws UnsupportedEncodingException {
			cityName = new String(cityName.getBytes("iso-8859-1"), "utf-8");
			this.cityName = java.net.URLDecoder.decode(cityName, "utf-8");
		}
		
		public String getUid() {
			return uid;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}
		
		public String getLoginChannel() {
			return loginChannel;
		}

		public void setLoginChannel(String loginChannel) {
			this.loginChannel = loginChannel;
		}

		public String getPlaceMainTitle() {
			return placeMainTitle;
		}

		public void setPlaceMainTitle(String placeMainTitle) {
			this.placeMainTitle = placeMainTitle;
		}

		public String getObjectId() {
			return objectId;
		}

		public void setObjectId(String objectId) {
			this.objectId = objectId;
		}
		
		public String  getLatitudeInfo() {
			return latitudeInfo;
		}

		public void setLatitudeInfo(String  latitudeInfo) {
			this.latitudeInfo = latitudeInfo;
		}

		public void setOrderServiceProxy(OrderService orderServiceProxy) {
			this.orderServiceProxy = orderServiceProxy;
		}

		public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
			this.orderMessageProducer = orderMessageProducer;
		}

}

