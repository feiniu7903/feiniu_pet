package com.lvmama.clutter.web.place;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.xml.lv.po.RequestOrderItem;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderChannel;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Coupon;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.client.ClientCmtLatitude;
import com.lvmama.comm.pet.po.client.ClientCmtPlace;
import com.lvmama.comm.pet.po.client.ClientCommitCmtResult;
import com.lvmama.comm.pet.po.client.ClientOrderCmt;
import com.lvmama.comm.pet.po.client.ClientOrderReport;
import com.lvmama.comm.pet.po.client.ClientProduct;
import com.lvmama.comm.pet.po.client.ClientUser;
import com.lvmama.comm.pet.po.client.ClientUserCouponInfo;
import com.lvmama.comm.pet.po.client.ViewClientOrder;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.favor.FavorService;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.vo.favor.FavorResult;
import com.lvmama.comm.pet.vo.favor.strategy.OrderFavorStrategyForFifthAnniversary;
import com.lvmama.comm.pet.vo.mark.ValidateCodeInfo;
import com.lvmama.comm.utils.ActivityUtil;
import com.lvmama.comm.utils.ClientConstants;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HotelUtils;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
/**
 * 需要用户安全认证后方能访问的相关action
 * @author dengcheng
 *
 */
public class AppUserApiAction  extends AppBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1353405460304711146L;
	
	/**
	 * 手机号码
	 */
	private String mobile;
	
	/**
	 * 离店日期
	 */
	private String leaveTime;
	/**
	 * 参与活动标记
	 */
	private String promotionEnabled;
	/**
	 * 优惠券
	 */
	private String couponCode;
	
	
	
	private String cmtType;
	
	private String userId;

	/**
	 * 
	 */
	String latitudeInfo ;
	
	String objectId;
	
	String couponState;
	
	String orderItem;
	
	private ProdProductService prodProductService;
	
	
	
	private ProdProductPlaceService prodProductPlaceService;
	/**
	 * 订单服务
	 */
	private OrderService orderServiceProxy;
	
	private MarkCouponService markCouponService;
	
	private FavorService favorService;
	
	
	/**
	 * 短信发送服务器.
	 */
	private TopicMessageProducer orderMessageProducer;
	
	private CmtCommentService cmtCommentService;
	
	

	private boolean checkCanOrderToday(Long branchId){
		boolean flag = prodProductBranchService.checkPhoneOrderTime(branchId);
		return flag;
	}
	
	
	@Action("/client/api/v2/sso/commitOrder")
	public void commitOrder() {
		Map<String,Object> resultMap = super.resultMapCreator();

		requiredArgList.add(firstChannel);
		requiredArgList.add(userId);
		
		if(!isTodayOrder){
			requiredArgList.add(visitTime);
		}

		requiredArgList.add(userName);
		requiredArgList.add(mobile);
		requiredArgList.add(productId);

		this.checkArgs(requiredArgList, resultMap);
		/**
		 * 5周年活动逻辑处理
		 */
		if(ActivityUtil.getInstance().checkActivityIsValid(Constant.ACTIVITY_FIVE_YEAR)){ 
			if(ClutterConstant.getFifthSeckillProductIds().contains(productId.toString())){
				putErrorMessage(resultMap, "5周年活动产品客户端暂时不支持下单，请前往驴妈妈网站下单秒杀");
				super.sendResult(resultMap, "",false);
				return;
			}
		}

		/**
		 * 构建提交订单项目
		 */
		List<RequestOrderItem> orderItemList = this.createOrderItem();
		
		if(orderItemList.isEmpty()){
			putErrorMessage(resultMap, "构建订单失败");
		}
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",false);
			return;
		}
		
		if (isTodayOrder) {
			for (RequestOrderItem requestOrderItem : orderItemList) {
				boolean flag = this.checkCanOrderToday(Long.valueOf(requestOrderItem.getBranchId()));
				if (!flag) {
					this.putErrorMessage(resultMap, "提交失败,已超出最晚购买时间");
					super.sendResult(resultMap, null,false);
					return;
				}
			}
			visitTime = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		}
		
		OrdOrder o = null;
		Map<String,Object> errorMap = new HashMap<String, Object>();
		try {
		//验证今日当前时间是否可以定当日票

 
		Map<String,String> map = ClientConstants.getErrorInfo();

		ProdProduct mainProduct = prodProductService.getProdProductById(productId);
	
		
		/**
		 * 设置mainProduct couponAble
		 */
		ClientUtils.promotionSet(promotionEnabled, mainProduct);

		
	
		/**
		 * 验证是否重复下单
		 */
		if(mainProduct.isHotel()){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("productId", Long.valueOf(productId));
			params.put("userId", userId);
			params.put("visitTime", DateUtil.getDateByStr(visitTime, "yyyy-MM-dd"));
			params.put("subProductType", mainProduct.getSubProductType());
			String travellerInfoOptions = mainProduct.getTravellerInfoOptions();
			params.put("travellerInfoOptions", travellerInfoOptions);
			
			//酒店单房型需离店日期
			if(mainProduct.isSingleRoom()) {
				params.put("leaveTime", DateUtil.getDateByStr(leaveTime, "yyyy-MM-dd"));
			}
			params.put("visitorName", userName);
			ResultHandle isExisted = orderServiceProxy.checkCreateOrderLimitIsExisted(params);
			if(isExisted.isFail()){
				errorMap.put("errorInfo", map.get("checkVisitorIsExisted"));
			}
		}
		
		BuyInfo createOrderBuyInfo = new BuyInfo();
		List<Item> itemList = new ArrayList<Item>();
		for (RequestOrderItem requestOrderItem : orderItemList) {

			Item item = new Item();
		
			//查询产品当前日期类别。
			ProdProductBranch branch = null;
			if(isTodayOrder){
				branch =  this.prodProductService.getPhoneProdBranchDetailByProdBranchId(Long.valueOf(requestOrderItem.getBranchId()), DateUtil.toDate(visitTime, "yyyy-MM-dd"),true);
			} else {
				branch =  this.prodProductService.getProdBranchDetailByProdBranchId(Long.valueOf(requestOrderItem.getBranchId()), DateUtil.toDate(visitTime, "yyyy-MM-dd"),true);
			}
			if(null==branch) {
				errorMap.put("errorInfo", map.get("validVisitDate"));
				break;
			}
			
			item.setAdultQuantity(branch.getAdultQuantity());
			item.setChildQuantity(branch.getChildQuantity());
			
			//创建订单子项
			this.createOrderItem(item, Long.valueOf(productId), createOrderBuyInfo, requestOrderItem, DateUtil.toDate(visitTime, "yyyy-MM-dd"),branch,errorMap);
			//验证库存
			if(!prodProductService.isProductSellable(branch.getProdBranchId(), Long.valueOf(requestOrderItem.getQuantity()), DateUtil.toDate(visitTime, "yyyy-MM-dd"))){
				errorMap.put("errorInfo", map.get("overStock"));
				break;
			}
			//创建酒店订单项目。
			if(branch.getProdProduct().isSingleRoom()){
				HotelUtils.createOrderItemTimeInfo(DateUtil.toDate(visitTime, "yyyy-MM-dd"), DateUtil.toDate(leaveTime, "yyyy-MM-dd"), item);
				
			}
			itemList.add(item);	
			
			if(isTodayOrder){
				List<ClientOrderReport> reportList =  comClientService.getTodayOrderByUdid(udid);
				if(reportList!=null &&reportList.size()>0){
					for (ClientOrderReport clientOrderReport : reportList) {
						OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(clientOrderReport.getOrderId());
						if(order!=null && order.getMainProduct()!=null){
							if(!order.isCanceled()&&item.getProductBranchId()==order.getMainProduct().getProdBranchId().longValue()){
								errorMap.put("errorInfo", map.get("over_today_order_limit"));
								break;
							}
						}
					}
				}
			
			}
		}
		
		
		List<Person> personList = new ArrayList<Person>();
		Person person = new Person();
		/**
		 * 处理android 乱码
		 */
		if(isAndroid()){
			try {
				userName = new String(userName.getBytes("iso-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		person.setName(userName);
		person.setMobile(mobile);

		if(!StringUtil.isEmptyString(idCard)){
			if(StringUtil.isIdCard(idCard)){
				person.setCertNo(idCard);
				person.setCertType(Constant.CERT_TYPE.ID_CARD.name());
			} else {
				errorMap.put("errorInfo", map.get("idcard_error"));
			}
			
		}
		
		person.setPersonType(Constant.ORD_PERSON_TYPE.CONTACT.name());
		personList.add(person);
		createOrderBuyInfo.setMainSubProductType(mainProduct.getSubProductType());
		createOrderBuyInfo.setChannel(Constant.CHANNEL.CLIENT.name());
		
		createOrderBuyInfo.setItemList(itemList);
		createOrderBuyInfo.setPersonList(personList);
		if(isTodayOrder){
			createOrderBuyInfo.setTodayOrder(isTodayOrder);
			createOrderBuyInfo.setWaitPayment(Constant.WAIT_PAYMENT.PW_HALF_HOUR.getValue());
		} else {
			createOrderBuyInfo.setWaitPayment(Constant.WAIT_PAYMENT.PW_DEFAULT.getValue());
		}
		createOrderBuyInfo.setUserId(userId);
		/**
		 * 优惠券验证逻辑
		 */
	
		if(mainProduct.isPaymentToLvmama()){
			if(ClientUtils.canUserClientPromotion(mainProduct, promotionEnabled)) {
				couponCode = ClutterConstant.getPromotionCode();
				
			} else {
				if(!ClientUtils.canUserCoupon(mainProduct)&&!StringUtils.isEmpty(couponCode)){
					errorMap.put("errorInfo", map.get("can_not_use_coupon_code"));
				}
			}
			
			if(!StringUtils.isEmpty(couponCode)){
				this.userCoupon(createOrderBuyInfo, mainProduct,errorMap);
			}
		} else {
			if(!StringUtils.isEmpty(couponCode)){
				errorMap.put("errorInfo", map.get("coupon_can_used_for_topay_supplier"));
			}
		}
	
		if(ClientUtils.isCanCommitOrder(errorMap)){
			
			if(isTodayOrder){
				ClientOrderReport cor = new ClientOrderReport();
				cor.setChannel(this.firstChannel+"_"+this.secondChannel);
				cor.setUdid(udid);
				createOrderBuyInfo.setClientOrderReport(cor);
			}
			createOrderBuyInfo.setOrdOrderChannel(new OrdOrderChannel(null, this.firstChannel+"_"+this.secondChannel));
			FavorResult fr =  favorService.calculateFavorResultByBuyInfo(createOrderBuyInfo);
			ValidateCodeInfo info = fr.getValidateCodeInfo();
			if (Constant.COUPON_INFO.OK.name().equals(info.getKey())||info.getKey()==null) {
				
				if(ActivityUtil.getInstance().checkActivityIsValid(Constant.ACTIVITY_FIVE_YEAR)){ 
					createOrderBuyInfo.setCouponList(new ArrayList<Coupon>()); //5周年不使用优惠券 
					FavorResult fifthfr = new FavorResult(); 
					OrderFavorStrategyForFifthAnniversary ofsf = new OrderFavorStrategyForFifthAnniversary(new MarkCoupon(),new MarkCouponCode()); 
					fifthfr.addOrderFavorStrategy(ofsf); 
					createOrderBuyInfo.setFavorResult(fifthfr); 
				} else {
					createOrderBuyInfo.setFavorResult(fr);
				}
				o = orderServiceProxy.createOrder(createOrderBuyInfo);
			} else {
				String message = info.getValue();
				errorMap.put("errorInfo", message);
			}

			} 

		}  catch(Exception ex){
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
		} finally {
			if(ClientUtils.isCanCommitOrder(errorMap)){
				if(o!=null){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ViewClientOrder co = appService.getOrderByOrderId(o.getOrderId(), userId);
					super.sendResult(resultMap,co,false);
				}
				
			} else {
				String msg = ClientUtils.getErrorInfo(errorMap);
				this.putErrorMessage(resultMap, msg);
				super.sendResult(resultMap, null,false);
			}
		}

	}
	
	private void  userCoupon(BuyInfo createOrderBuyInfo,ProdProduct mainProduct,Map<String,Object> errorMap){

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("couponCode", couponCode);
			param.put("isValid", "true");
			MarkCoupon mc =  markCouponService.selectMarkCouponByCouponCode(couponCode, false);
			List<Coupon> couponList = new ArrayList<Coupon>();
			Coupon c = new Coupon();
			c.setChecked("true");
			if(!ActivityUtil.getInstance().checkActivityIsValid(Constant.ACTIVITY_FIVE_YEAR)){
				c.setCode(couponCode);
			} 
			if(mc!=null){ 
				c.setCouponId(mc.getCouponId());
			}
			couponList.add(c);
			if(mc!=null){ 
				createOrderBuyInfo.setPaymentChannel(mc.getPaymentChannel()); 
			}
			createOrderBuyInfo.setCouponList(couponList);
	
	}
	
	private boolean createOrderItem(Item item,Long defaultProductId,BuyInfo createOrderBuyInfo,RequestOrderItem requestOrderItem,Date visitTime,ProdProductBranch branch,Map<String,Object> errorMap){

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
		Map<String,String> map = ClientConstants.getErrorInfo();
		item.setQuantity(Integer.valueOf(requestOrderItem.getQuantity()));
		item.setProductId(branch.getProductId());
		item.setVisitTime(visitTime);

		
		
		if(prodrodProduct.getOfflineTime() != null){
			Date currentDate = new Date();
			if(currentDate.getTime()>prodrodProduct.getOfflineTime().getTime()) {
				errorMap.put("errorInfo", map.get("prod_isoffline"));
				return false;
			}
		}else if (Long.valueOf(requestOrderItem.getQuantity()) < branch.getMinimum()) {
			errorMap.put("errorInfo", map.get("less_mininum"));
			return false;
		} else if (Long.valueOf(requestOrderItem.getQuantity()) > branch.getMaximum()) {
			errorMap.put("errorInfo", map.get("out_mininum"));
			return false;
		}
		return true;
		
	}
	

	private List<RequestOrderItem> createOrderItem(){

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
			if(!StringUtils.isEmpty(itemArray[0])){
				orderItemList.add(roi);
			}
		
			
		}
		return orderItemList;
	}
	
	@Action("/client/api/v2/sso/queryUserCouponInfo")
	public void queryUserCouponInfo () {
		Map<String,Object> resultMap = super.resultMapCreator();

		requiredArgList.add(userId);
		requiredArgList.add(couponState);
		
		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",true);
			return;
		}
		 List<ClientUserCouponInfo> list = null;
		 
		try {
			 UserUser user = userUserProxy.getUserUserByUserNo(userId);
			 list = appService.queryUserCouponInfo(user, couponState);
		}catch(Exception ex){
				ex.printStackTrace();
				this.setExceptionResult(resultMap);
		} finally {
				super.sendResult(resultMap, list,true);
		}
	}
	
	@Action("/client/api/v2/sso/queryCommentListByUserId")
	public void queryCommentListByUserId () {
		Map<String,Object> resultMap = super.resultMapCreator();
		//UserUser user = this.getUser();
		requiredArgList.add(userId);
		requiredArgList.add(cmtType);
		
		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",true);
			return;
		}

		List<ClientCmtPlace> list = null;
		try {
			Map<String,Object> map = appService.queryCommentByUserId(userId, page, cmtType);
			list = (List<ClientCmtPlace>)map.get("list");
			resultMap.put("isLastPage", map.get("isLastPage"));
		}catch(Exception ex){
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
		} finally {
			super.sendResult(resultMap, list,true);
		}
	}
	
	@Action("/client/api/v2/sso/queryUserOrderList")
	public void queryUserOrderList () {
		Map<String,Object> resultMap = super.resultMapCreator();
		requiredArgList.add(userId);
		requiredArgList.add(page);

		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",true);
			return;
		}
		
		Map<String,Object> map = appService.getOrderListByOrderId(null, userId, page);
		List<?> list = null;
		try {
		 list = (List<?>)map.get("list");
		 resultMap.put("isLastPage", map.get("isLastPage"));
		}catch(Exception ex){
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
		} finally {
			super.sendResult(resultMap, list,true);
		}

	}
	
	
	@Action("/client/api/v2/sso/queryUserOrderDetail")
	public void queryUserOrderDetail () {
		Map<String,Object> resultMap = super.resultMapCreator();
		requiredArgList.add(orderId);
		requiredArgList.add(userId);

		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",false);
			return;
		}
		ViewClientOrder  vco =  null;
		try {
			vco = appService.getOrderByOrderId(Long.valueOf(orderId), userId);
		}catch(Exception ex){
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
		} finally {
			super.sendResult(resultMap, vco,false);
		}

	}
	
	@Action("/client/api/v2/sso/queryTimePriceByProductIdAndDate")
	public void queryTimePriceByProductIdAndDate () {
		Map<String,Object> resultMap = super.resultMapCreator();
		requiredArgList.add(productId);
		
		if(productId==null||isTodayOrder==false){
			requiredArgList.add(visitTime);
		} else if(isTodayOrder){
			visitTime = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
		}
		
		//requiredArgList.add(branchId);
		requiredArgList.add(udid);
		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",true);
			return;
		}
		
		List<ClientProduct> list = null;
		try {
				ProdProduct prod = prodProductService.getProdProduct(productId);
				Place p = prodProductPlaceService.getToDestByProductId(productId);
				
				if(isTodayOrder){
					list = 	appService.queryTimePriceByProductIdAndDateForTodayPhoneOrder(productId, branchId, DateUtil.toDate(visitTime,"yyyy-MM-dd"));
					
				} else {
					list = 	appService.queryTimePriceByProductIdAndDate(prod, branchId, DateUtil.toDate(visitTime,"yyyy-MM-dd"), udid);
				}
				
				if(list.isEmpty()){
					this.putErrorMessage(resultMap, "产品当前不可预订");
				} else {
					if(isTodayOrder){//如果是今日预订 干掉附加产品
						for (ClientProduct clientProduct : list) {
							
							if(isTodayOrder){
								clientProduct.setMaximum(4L);
							}

							if (clientProduct.isAdditional()) {
								list.remove(clientProduct);
							}
							
							boolean flag = prodProductBranchService.checkPhoneOrderTime(Long.valueOf(clientProduct.getBranchId()));
						
							
							if(!flag){
								this.putErrorMessage(resultMap, "提交失败,已超出最晚购买时间");
							}
						}
					}
				}
				
				
				
				//Map<Long,Long> filterMap = super.getFilterProducIds();
//				if(filterMap==null){
//					filterMap = new HashMap<Long, Long>();
//				}
//				resultMap.put("promotionName", ClutterConstant.getPromotionName());
//				
//				if(!prod.isPaymentToSupplier()&&filterMap.get(productId) == null){
//					if(StringUtil.isEmptyString(ClutterConstant.getPromotionName())){
//						resultMap.put("showPromotion", false);
//						resultMap.put("showCouponText", true);
//					} else {
//						resultMap.put("showPromotion", true);
//						resultMap.put("showCouponText", false);
//						
//					}
//				} else {
//					resultMap.put("promotionName", "");
//					resultMap.put("showPromotion", false);
//					resultMap.put("showCouponText", false);
//				}
				/**
				 * 5周年取消优惠
				 */
				if(ActivityUtil.getInstance().checkActivityIsValid(Constant.ACTIVITY_FIVE_YEAR)){
					resultMap.put("promotionName", "");
					resultMap.put("showPromotion", false);
					resultMap.put("showCouponText", false);
				}
				
				if(p!=null){
					resultMap.put("orderNotice", StringUtil.isEmptyString(p.getOrderNotice())?"":p.getOrderNotice());
					resultMap.put("importantTips", StringUtil.isEmptyString(p.getImportantTips())?"":p.getImportantTips());
				}

				
				if(!StringUtil.isEmptyString(prod.getTravellerInfoOptions())&&prod.isTicket()){
					resultMap.put("needIdCard", true);
				} else {
					resultMap.put("needIdCard", false);
				}
				//判断能否使用优惠券 和参加活动
				if (prod!=null) {
					if(prod.isPaymentToLvmama()){
						resultMap.put("couponAble", StringUtil.isEmptyString(prod.getCouponAble())?"":prod.getCouponAble());
					} else {
						resultMap.put("couponAble", "false");
					}
					
				}
				
				if (isWP7()) {
					resultMap.put("promotionName", "");
				}

		} catch(Exception ex){
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
		} finally{
			this.sendResult(resultMap, list,true);
		}
	}
	
	
	
	@Action("/client/api/v2/sso/commitComment")
	public void commitComment(){
		Map<String,Object> resultMap = super.resultMapCreator();
		if(isAndroid()){
			try {
				content = new String(content.getBytes("iso-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		requiredArgList.add(userId);
		requiredArgList.add(latitudeInfo);
		requiredArgList.add(objectId);
		requiredArgList.add(content);
		requiredArgList.add(cmtType);
		
		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",false);
			return;
		}
		ClientCommitCmtResult result = null;
		try {
		List<ClientCmtLatitude> clientCmtLatitudeList = new ArrayList<ClientCmtLatitude>();
		String[] latitudeInfoArray = latitudeInfo.split(",");
		for (String str : latitudeInfoArray) {
			String[] keys = str.split("_");
			 ClientCmtLatitude ccl = new ClientCmtLatitude();
		        ccl.setLatitudeId(keys[0]);
		        ccl.setScore(Integer.valueOf(keys[1]));
		        clientCmtLatitudeList.add(ccl);
		}
		 result = appService.commitComments(userId, clientCmtLatitudeList, objectId, content, cmtType,firstChannel);
		}catch(Exception ex){
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
		} finally {
			super.sendResult(resultMap, result,false);
		}
	}
	
	@Action("/client/api/v2/sso/test")
	public void test(){
	String cmtId = 	this.getRequest().getParameter("cmtId");
	String charecoding= 	this.getRequest().getParameter("charecoding");
	CommonCmtCommentVO vo = cmtCommentService.getCmtCommentByKey(Long.valueOf(cmtId));
	String content = "";
	try {
		content = new String(vo.getContent().getBytes("iso-8859-1"), charecoding);
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	this.sendAjaxMsg("{\"msg:\":\""+content+"\"}");
	}
	
	@Action("/client/api/v2/sso/queryWaitForCommentNumber")
	public void queryWaitForCommentNumber () {
		Map<String,Object> resultMap = super.resultMapCreator();
		
		requiredArgList.add(userId);

		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",false);
			return;
		}
		
		List<ClientOrderCmt> result = null;
		try {
			result = appService.getOrderWaitingComments(userId, 1L);
			if(result!=null&&!result.isEmpty()){
				resultMap.put("waitCommentNumber", result.size());
			} else {
				resultMap.put("waitCommentNumber", 0);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
		} finally {
			resultMap.put("isLastPage", true);
			super.sendResult(resultMap, "",false);
		}
	}

	@Action("/client/api/v2/sso/queryOrderWaitPayTimeSecond")
	public void queryOrderWaitPayTime(){
		Map<String,Object> resultMap = super.resultMapCreator();
		requiredArgList.add(orderId);
		requiredArgList.add(userId);

		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",false);
			return;
		}
		Map<String,Object> result = new HashMap<String,Object>();
		try {
	
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId));
		Date date1 = order.getApproveTime();
		Date date2 = new Date();
		long d=Math.abs(date2.getTime()-date1.getTime());
		//计算剩余预订秒数 减去上2秒的网络损耗
		long second = order.getWaitPayment()*60 - Math.abs(d/1000)-2;
		result.put("surplusSeconds", second<0?0:second);
		} catch(Exception ex){
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
		}finally {
			resultMap.put("isLastPage", "true");
			super.sendResult(resultMap, result,false);
		}
	}

	@Action("/client/api/v2/sso/queryCommentWaitForOrder")
	public void queryOrderWaitForComment () {
		Map<String,Object> resultMap = super.resultMapCreator();
		
		requiredArgList.add(userId);
		requiredArgList.add(page);

		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",false);
			return;
		}
		List<ClientOrderCmt> result = null;
		try {
			result = appService.getOrderWaitingComments(userId, page);
		}catch(Exception ex){
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
		} finally {
			resultMap.put("isLastPage", "true");
			super.sendResult(resultMap, result,false);
		}
	}

	
	

	@Action("/client/api/v2/sso/reSendSmsCert")
	public void reSendSmsCert () {
		Map<String,Object> resultMap = super.resultMapCreator();
		requiredArgList.add(orderId);
		requiredArgList.add(userId);

		
		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",false);
			return;
		}
		try {
	
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId));
		
		if(!order.getUserId().equals(userId)){
			this.putErrorMessage(resultMap, "拒绝访问");
			super.sendResult(resultMap, "",false);
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
			if(!(StringUtil.isEmptyString(orderHeadId)&&StringUtil.isEmptyString(mobileNumber))){
				orderMessageProducer.sendMsg(MessageFactory.newCertSmsSendMessage(Long.valueOf(orderHeadId),mobileNumber));
			}
			
		}
		} catch (Exception ex) {
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
		} finally {
			this.sendResult(resultMap, "",false);
		}
		
	}
	
	@Action("/client/api/v2/sso/passJson")
	public void getPassJson() {
		Map<String,Object> resultMap = super.resultMapCreator();
		String responseJson ="";
		requiredArgList.add(orderId);
		requiredArgList.add(userId);

		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",false);
			return;
		}
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId));
		
		if(!order.getUserId().equals(userId)){
			this.putErrorMessage(resultMap, "拒绝访问");
			super.sendResult(resultMap, "",false);
			return;
		}

		
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
		this.sendAjaxResult(responseJson);
}
	
	
	@Action("/client/api/v2/sso/getUserInfoByUserId")
	public void getUserInfoByUserId() {
		Map<String,Object> resultMap = super.resultMapCreator();
		requiredArgList.add(userId);

		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",false);
			return;
		}
		ClientUser cu = new ClientUser();
		try {
		UserUser user = 	userUserProxy.getUserUserByUserNo(userId);
			this.getUserByMap(user, cu);
		} catch (Exception ex){
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
		} finally {
			super.sendResult(resultMap, cu,false);
		}
		
	}
	
	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
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




	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}





	public String getLeaveTime() {
		return leaveTime;
	}


	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	public String getLatitudeInfo() {
		return latitudeInfo;
	}

	public void setLatitudeInfo(String latitudeInfo) {
		this.latitudeInfo = latitudeInfo;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getOrderItem() {
		return orderItem;
	}


	public void setOrderItem(String orderItem) {
		this.orderItem = orderItem;
	}


	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}


	public String getCouponState() {
		return couponState;
	}

	public void setCouponState(String couponState) {
		this.couponState = couponState;
	}

	public String getCmtType() {
		return cmtType;
	}

	public void setCmtType(String cmtType) {
		this.cmtType = cmtType;
	}

	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	public void setProdProductPlaceService(
			ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}

	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}


	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public void setFavorService(FavorService favorService) {
		this.favorService = favorService;
	}


	public void setCmtCommentService(CmtCommentService cmtCommentService) {
		this.cmtCommentService = cmtCommentService;
	}
}
