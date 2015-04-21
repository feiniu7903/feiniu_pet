package com.lvmama.clutter.service.client;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import com.lvmama.clutter.exception.ActivityLogicException;
import com.lvmama.clutter.model.MobileBranch;
import com.lvmama.clutter.model.MobileBranchItem;
import com.lvmama.clutter.service.IBaiduActivityService;
import com.lvmama.clutter.service.IClientProductService;
import com.lvmama.clutter.service.impl.ClientOrderServiceImpl;
import com.lvmama.clutter.utils.BaiduActivityUtils;
import com.lvmama.clutter.utils.BaiduXmlGenerator;
import com.lvmama.clutter.utils.BaiduActivityUtils.PRODUCT_STATUS;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Coupon;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.bee.vo.ord.PriceInfo;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mobile.MobileActBaidu;
import com.lvmama.comm.pet.po.mobile.MobileActBaiduOrder;
import com.lvmama.comm.pet.po.mobile.MobileActBaiduStock;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlacePhoto;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserCooperationUserService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.pet.vo.ProductList;
import com.lvmama.comm.pet.vo.favor.FavorResult;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.enums.PlacePhotoTypeEnum;

public class BaiduActivityServiceImpl extends ClientOrderServiceImpl implements IBaiduActivityService {
	private static final Log log = LogFactory.getLog(BaiduActivityServiceImpl.class);
	
	private static int rate = 8;// 预定 到成功提交订单 转换率 
	/**
	 *  半价票 ids
	 */
	public static String[] productIds = ClutterConstant.getBaiduActProductIdList(); // 半价票产品ids
	
	private UserCooperationUserService userCooperationUserService;
	
	IClientProductService mobileProductService;
	
	static final int max_buy_num=2;
	static final String BAIDUTUAN="BAIDUTUAN";
	@Override
	public List<ProdBranchSearchInfo> getActivityBranches(Long placeId) {
		ProductList productList = this.productSearchInfoService.getIndexProductByPlaceIdAnd4TypeAndTicketBranch(placeId, 1000, Constant.CHANNEL.TOUCH.name());
		List<ProdBranchSearchInfo> returnlist = new ArrayList<ProdBranchSearchInfo>();
		List<ProdBranchSearchInfo> list = productList.getProdBranchTicketList();
		for (ProdBranchSearchInfo prodBranchSearchInfo : list) {
			if(Constant.SUB_PRODUCT_TYPE.SINGLE.name().equals(prodBranchSearchInfo.getSubProductType())){
				returnlist.add(prodBranchSearchInfo);
				continue;
			}
		}
		
		return returnlist;
	}

	@Override
	public ProdProductBranch getActivityBranch(Long branchId,Date visitTime) {
		// TODO Auto-generated method stub
		ProdProductBranch	prodBrancheVisit = this.prodProductService.getPhoneProdBranchDetailByProdBranchId(branchId,DateUtil.getDayStart(visitTime),true);
		return prodBrancheVisit;
	}

	@Override
	public Long commitActivityOrder(MobileBranchItem branchItem, Date visitDate,
			String userId, String receiverName,String receiverMobile,String idCard,String couponCode) {
		BuyInfo createOrderBuyInfo = new BuyInfo();
		
		if(branchItem.getQuantity()>1){
			throw new ActivityLogicException("您最多只能购买1个");
		}
		
		if(StringUtil.isEmptyString(userId)){
			throw new ActivityLogicException("需要您通过百度账号登陆才能购买");
		}
		
		UserUser user = userUserProxy.getUserUserByUserNo(userId);
		if(user!=null){
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("userId", user.getId());
			List<UserCooperationUser> cooperationUseres = userCooperationUserService
					.getCooperationUsers(parameters);	
			if(cooperationUseres==null||cooperationUseres.isEmpty()){
				throw new ActivityLogicException("需要您通过百度账号登陆才能购买");
			}
			
			UserCooperationUser ucu = cooperationUseres.get(0);
			if(!BAIDUTUAN.equals(ucu.getCooperation())){
				throw new ActivityLogicException("需要您通过百度账号登陆才能购买");
			}
			
		}
		
		
		
		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item();
		//item.setProductId(branchItem.get);
		
		ProdProductBranch branch =  this.prodProductService.getProdBranchDetailByProdBranchId(branchItem.getBranchId(),  visitDate, true);
		item.setProductBranchId(branch.getProdBranchId());
		item.setProductId(branch.getProductId());
		item.setVisitTime(visitDate);
		item.setQuantity(branchItem.getQuantity().intValue());
		item.setIsDefault("true");
		
		itemList.add(item);
		
		Person receiverPerson = new Person();
		receiverPerson.setName(receiverName);
		receiverPerson.setMobile(receiverMobile);
		if(idCard!=null && !"".equals(idCard)){
			receiverPerson.setCertNo(idCard);	
			receiverPerson.setCertType(Constant.CERT_TYPE.ID_CARD.name());
		}
		receiverPerson.setPersonType(Constant.ORD_PERSON_TYPE.CONTACT.name());
		
		Person travellerPerson = new Person();
		
		BeanUtils.copyProperties(receiverPerson, travellerPerson);
		travellerPerson.setPersonType(Constant.ORD_PERSON_TYPE.TRAVELLER.name());
	
		Person bookerPerson = new Person();
		BeanUtils.copyProperties(receiverPerson, bookerPerson);
		bookerPerson.setPersonType(Constant.ORD_PERSON_TYPE.BOOKER.name());
		
		if(user.getRealName()!=null){
			bookerPerson.setName(user.getRealName());
		}
		
		if(user.getMobileNumber()!=null){
			bookerPerson.setMobile(user.getMobileNumber());
		}
		
		List<Person> personList = new ArrayList<Person>();
		personList.add(receiverPerson);
		personList.add(travellerPerson);
		personList.add(bookerPerson);
		
		createOrderBuyInfo.setItemList(itemList);
		createOrderBuyInfo.setPersonList(personList);

		/**
		 * 活动10分钟支付等待 不支付就取消掉
		 */
		createOrderBuyInfo.setWaitPayment(2L);
		/**
		 * 活动触屏版渠道
		 */
		createOrderBuyInfo.setChannel(Constant.CHANNEL.TOUCH.name());
		createOrderBuyInfo.setUserId(userId);
		
		createOrderBuyInfo.setMainProductType(Constant.PRODUCT_TYPE.TICKET.name());
		
		/**
		 * 处理优惠券
		 */
		List<Coupon> couponList = new ArrayList<Coupon>();
		if(!StringUtils.isEmpty(couponCode)){
			Coupon c = new Coupon();
			c.setChecked("true");
			c.setCode(couponCode);
			
			MarkCoupon mc =  markCouponService.selectMarkCouponByCouponCode(couponCode, false);
			if(mc!=null){ 
				c.setCouponId(mc.getCouponId());
			}
			if(mc!=null){ 
				createOrderBuyInfo.setPaymentChannel(mc.getPaymentChannel()); 
			}
			couponList.add(c);	
		}
		createOrderBuyInfo.setPaymentTarget(branch.getProdProduct().isPaymentToLvmama()?Constant.PAYMENT_TARGET.TOLVMAMA.name():Constant.PAYMENT_TARGET.TOSUPPLIER.name());
		createOrderBuyInfo.setCouponList(couponList);
		createOrderBuyInfo.setPaymentChannel(Constant.MARK_PAYMENT_CHANNEL.BAIDU_PAY.name());
		ResultHandle result=orderServiceProxy.checkOrderStock(createOrderBuyInfo); 
		
		if(result.isFail()){ 
			throw new ActivityLogicException(result.getMsg());
		}
		
		FavorResult fr =  favorService.calculateFavorResultByBuyInfo(createOrderBuyInfo);
		createOrderBuyInfo.setFavorResult(fr);
		
		if(!StringUtil.isEmptyString(couponCode)){
			try {
			PriceInfo priceInfo = orderServiceProxy.countPrice(createOrderBuyInfo);
			super.throwValidateCouponInfo(fr, priceInfo);
			} catch(Exception ex){
				throw new ActivityLogicException(ex.getMessage());
			}
		}
		
		
		OrdOrder order = orderServiceProxy.createOrder(createOrderBuyInfo);
		return order.getOrderId();
	}
	/**
	 * 百度查询CooperationUser
	 * @param token
	 * @return
	 */
	public UserCooperationUser  getCooperationUserByToken(String token,String lvsessionid){
			UserCooperationUser u=null;
		
			//获取百度用户信息参数
			Map<String,String> params=new HashMap<String, String>();
			params.put("access_token",token);
			//获取用户详细信息/简单信息接口getLoggedInUser
			String strLogin =HttpsUtil.requestPostForm("https://openapi.baidu.com/rest/2.0/passport/users/getInfo",params);
			
			//获取成功
			if(strLogin!=null && strLogin.length()>0 && strLogin.indexOf("userid")>=0){
				
				Map<String,Object> resultLogin =  JSONObject.fromObject(strLogin);
				
				//与驴妈妈绑定
				Map<String,String> paramLv=paramInit(resultLogin,token,lvsessionid);
				
				String jsonStrLoginThird =HttpsUtil.requestPostForm(ClutterConstant.getProperty("nssoUrl")+"/mobileAjax/cooperationUserRegisterLogin.do", paramLv);
				Map<String,Object> resultStrLoginThird =  JSONObject.fromObject(jsonStrLoginThird);
				
				Map<String, Object> parameters =new HashMap<String, Object>();
				if(resultStrLoginThird.get("success")!=null && "true".equals(resultStrLoginThird.get("success").toString())){
					parameters.put("cooperationUserAccount", paramLv.get("cooperationUid"));
				}else{
					//绑定用户失败
					throw new ActivityLogicException("绑定用户失败");
				}
				
				List<UserCooperationUser> uList=userCooperationUserService.getCooperationUsers(parameters);
				if(uList.size()>0){
					u=uList.get(0);
				}
			}else{
				//获取用户详细信息失败
				throw new ActivityLogicException("获取百度用户详细信息失败");
			}
		return u;
	}
	
	public boolean loginWithBaiduUid(String uid,String lvsessionid){
		Map<String,String> param = new HashMap<String,String>();
		param.put("cooperationUid", uid);
		param.put("cooperationChannel", Constant.BAIDU_ACTIVITY_CHANNEL);
		param.put("lvsessionid", lvsessionid);
		param.put("loginType", "HTML5");
		String jsonStrLoginThird =HttpsUtil.requestPostForm(ClutterConstant.getProperty("nssoUrl")+"/mobileAjax/cooperationUserRegisterLogin.do", param);
		Map<String,Object> resultStrLoginThird =  JSONObject.fromObject(jsonStrLoginThird);
		if(resultStrLoginThird.get("success")!=null && "true".equals(resultStrLoginThird.get("success").toString())){
			return true;
		}
		return false;
	}

	/**参数处理
	 * @param resultLogin
	 * @return
	 */
	public Map<String,String> paramInit(Map<String,Object> resultLogin,String token,String lvsessionid){
		Map<String,String> paramLv=new HashMap<String, String>();
		
		paramLv.put("cooperationUid",resultLogin.get("userid").toString());
		
		String cooperationUserName="";
		if(resultLogin.get("realname")!=null && !"".endsWith(resultLogin.get("realname").toString())){
			cooperationUserName=resultLogin.get("realname").toString();
		}else{
			if(resultLogin.get("username")!=null && !"".endsWith(resultLogin.get("username").toString())){
				cooperationUserName=resultLogin.get("username").toString();
			}else{
				cooperationUserName=resultLogin.get("userid").toString();
			}
		}
		paramLv.put("cooperationUserName", cooperationUserName);
		
		paramLv.put("cooperationChannel", BAIDUTUAN);
		paramLv.put("profileImageUrl", "http://tb.himg.baidu.com/sys/portraitn/item/"+resultLogin.get("portrait").toString());
		//获取sign参数
		Map<String,Object> signParam=new HashMap<String, Object>();
		signParam.put("bd_uid", resultLogin.get("userid"));
		signParam.put("bd_displayname", resultLogin.get("username"));
		signParam.put("bd_token", token);
		signParam.put("bd_timestamp", new Date().getTime());
		//signMD5加密
		String sign=MD5.md5(signParam.toString()+"CdRBKcdVQDdZn8AFoBwgZlfyAxd5Es08");
		paramLv.put("sign", sign);
		
		paramLv.put("loginType", "HTML5");
		paramLv.put("lvsessionid", lvsessionid);
		
		return paramLv;
	}
	
	
	/**
	 * 判断当前用户是否可以进入预订详情页面,目的链接掉多余的请求 。 
	 * 1，判断活动日期是否开始 ，如2014-04-24到2014-05-02
	 * 2，判断是否在可预订的时间段
	 * 3，判断该用户是否预定过，用户预订信息从缓存中读取；
	 * 4，用户没5秒钟之内只能过来一次请求
	 * 5，每类产品过来的总请求数据为可预订数量*2； 有效期为5分钟
	 * 
	 * code 1000: 可以预定
	 *      1001：活动未开始
	 *      1002，预定时间段还不到
	 *      1003， 用户已经预订过
	 *      1004, 5秒钟内访问多次
	 *      1005，该类产品请求最大数已经达到。
	 *      2000,未知错误 
	 */
	@Override
	public Map<String, Object> baiduBooking(Long productid,String userNo) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("code", BaiduActivityUtils.BAIDU_MSG.UNKNOW_ERROR.getCode());
		//  1：半价票 ；2 立减票 ;0:其它票
		String p_type = BaiduActivityUtils.ticketType(productid+"");
		// 如果不是半价产品   和 立减产品 
		if("0".equals(p_type)) {
			resultMap.put("code", BaiduActivityUtils.BAIDU_MSG.SUCCESS.getCode());
			return resultMap;
		}
		
		// 坑爹的if else 。。。
		// 如果在活动有效期，有效期在配置文件中配置 
		if(BaiduActivityUtils.isValidateDate(productid+"")) {
				// 判断当前用户是否预订过
				if(this.canBooked(userNo)) {
					// 如果是立减产品 
					if("2".equals(p_type)) {
						/* 
						 * 如果是框外的话 ，需要数量限制 
						 * 最大请求数 = 当前时间段可预订数*2；
						 */
						/*if() {
							String sandby = BaiduActivityUtils.getDay4Sandby(BaiduActivityUtils.getDayFromStartDate());
							long canSellQty = this.canBookAtCurrentTime4Sandby(productid);
							if(this.productMaxRequestAmout(productid,sandby,canSellQty*rate)) {
								// 到达这里总数今日订单预订详情页面了。
								resultMap.put("code", BaiduActivityUtils.BAIDU_MSG.SUCCESS.getCode());
							} else {
								resultMap.put("code", BaiduActivityUtils.BAIDU_MSG.ERROR_5.getCode());
							}
						} else {
							resultMap.put("code", BaiduActivityUtils.BAIDU_MSG.SUCCESS.getCode());
						}*/
						resultMap.put("code", BaiduActivityUtils.BAIDU_MSG.SUCCESS.getCode());
						return resultMap;
					//半价产品 
					} else { 
						// 判断当前时间段是否可定 
						long canSellQty = this.canBookAtCurrentTime(productid);
						if(canSellQty > 0) {
							// 最大请求数 = 当前时间段可预订数*2；
							if(this.productMaxRequestAmout(productid,BaiduActivityUtils.getHourFromStartDate(),canSellQty*rate)) {
								// 到达这里总数今日订单预订详情页面了。
								resultMap.put("code", BaiduActivityUtils.BAIDU_MSG.SUCCESS.getCode());
							} else {
								resultMap.put("code", BaiduActivityUtils.BAIDU_MSG.ERROR_5.getCode());
							}
						} else {
							resultMap.put("code", BaiduActivityUtils.getBookedMsg());
						}
					}
				} else {
					resultMap.put("code", BaiduActivityUtils.BAIDU_MSG.ERROR_3.getCode());
				}
		} else {
			resultMap.put("code", BaiduActivityUtils.BAIDU_MSG.ERROR_1.getCode());
		}
		
		return resultMap;
	}


	/**
	 * 产品状态 
	 * 	SUCCESS("0","可以预定"),
		MSG_1("1","不是半价票"), 
		MSG_2("2","活动没开始 或者 已经结束"),
		MSG_3("3","当前时段已经售完"),
		MSG_4("4","3秒钟内访问多次"),
		UNKNOW_ERROR("-1","未知异常");
	 */
	@Override
	public String getProductStatus(Long productid) {

		// 产品id  105030  105022  不需要开始日期限制 
		if("105030".equals(productid+"") || "105022".equals(productid+"")) {
			return PRODUCT_STATUS.SUCCESS.getCode(); // 可订
		}
				
				
		//  是否半价票 
		boolean b = BaiduActivityUtils.isHalfPriceProduct(productid);
		// 如果不是半价产品 
		if(!b) {
			return PRODUCT_STATUS.MSG_1.getCode();
		}
				
		// 时间是否有效 
		if(BaiduActivityUtils.isValidateDate(productid+"")) {
			// 是否可定
			if(this.canBookAtCurrentTime(productid)>0) {
				return PRODUCT_STATUS.SUCCESS.getCode(); // 可订
			} else {
				return PRODUCT_STATUS.MSG_3.getCode();// 改产品不可定 
			}
	     } else {
	    	 return PRODUCT_STATUS.MSG_2.getCode(); // 活动没开始 或者 已经结束 
	     }
	}
	
	
	/**
	 * 点击预订按钮 校验
	 * 0, 时间是否有效 
	 * 1，用户是否订过，
	 * 2，是否有库存 （从缓存中读取，memcached计数器来计数 ，活动开始前初始化计数器）
	 * 3，插入用户订单表 （用户唯一性校验）
	 * 4，插入每天预订数量表 (总数校验)
	 * 5, 插入总表(总的订单量唯一性校验 )
	 *    和提交订单必须在一个事物内完成，
	 *    否则需要回滚。
	 *    
	 *    inOrOut 框内 1； 框外 0 ；
	 */
	@Override
	public Map<String, Object> baiduBeforSubmit(Long placeid,Long productid,String userNo,String inOrOut) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("code", BaiduActivityUtils.BAIDU_MSG.UNKNOW_ERROR.getCode());
		
		//  1：半价票 ；2 立减票 ;0:其它票
		String p_type = BaiduActivityUtils.ticketType(productid+"");
		// 如果不是半价产品 
		if("0".equals(p_type)) {
			resultMap.put("code", BaiduActivityUtils.BAIDU_MSG.ERROR_9.getCode());
			return resultMap;
		}
				
		// 时间是否有效 
		if(BaiduActivityUtils.isValidateDate(productid+"")) {
			// 是否已经预订过 
			if(this.canBooked(userNo)) {
				// 立减票
				if("2".equals(p_type)) {
					// 立减票是否有效 
                    if(this.isValidateTime4Sandby(productid, inOrOut)) {
                    	// 如果是框外
    					if(BaiduActivityUtils.BD_FRAMEWORKER.OUT.getCode().equals(inOrOut) ) {
    						String sandby = BaiduActivityUtils.getDay4Sandby(BaiduActivityUtils.getDayFromStartDate());
    						this.insertInfo(resultMap, userNo, productid, placeid, sandby);
    					} else {
    						// 插入用户订单表 （用户唯一性校验）
    						try{
    							this.inserMobileActOrder(userNo, productid, placeid);
    							resultMap.put("code", BaiduActivityUtils.BAIDU_MSG.SUCCESS.getCode());
    						}catch(Exception e) {
    							e.printStackTrace();
    							log.error("..baidu act baiduBeforSubmit userNo unique error.....userNo="+userNo + "..productid="+productid);
    							resultMap.put("code", BaiduActivityUtils.getBookedMsg());
    							//throw new ActivityLogicException("你已经购买过此产品");
    							return resultMap;
    						}
    						return resultMap;
    					}
                    } else {
                    	resultMap.put("code", BaiduActivityUtils.getBookedMsg());
                    }
					return resultMap;
				// 半价票 
				} else {
					//当前时间段是否可定 
					if(this.canBookAtCurrentTime(productid)>0) {
						Long sandby = BaiduActivityUtils.getHourFromStartDate();
						this.insertInfo(resultMap, userNo, productid, placeid, sandby);
						return resultMap;
					} else {
						resultMap.put("code", BaiduActivityUtils.getBookedMsg());
					}
				}
			} else {
				resultMap.put("code", BaiduActivityUtils.BAIDU_MSG.ERROR_3.getCode());
			}
		} else {
			resultMap.put("code", BaiduActivityUtils.BAIDU_MSG.ERROR_1.getCode());
		}
		return resultMap;
	}

	/**
	 * 插入相关逻辑 
	 * @param resultMap 
	 * @param userNo       
	 * @param productid
	 * @param placeid      景点id 
	 * @param sandby       时间段 
	 * @return map 
	 */
	private Map<String,Object> insertInfo(Map<String,Object> resultMap,String userNo,Long productid,Long placeid,Object sandby) {
		// 插入用户订单表 （用户唯一性校验）
		try{
			if(null == MemcachedUtil.getInstance().get(BaiduActivityUtils.BAIDU_USER_ORDER+userNo)) {
				this.inserMobileActOrder(userNo, productid, placeid);
				resultMap.put("code", BaiduActivityUtils.BAIDU_MSG.SUCCESS.getCode());
			} else {
				log.error("..baidu act baiduBeforSubmit userNo validate unique error.....userNo="+userNo);
				resultMap.put("code", BaiduActivityUtils.BAIDU_MSG.ERROR_3.getCode());
				return resultMap;
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("..baidu act baiduBeforSubmit userNo unique error.....userNo="+userNo);
			resultMap.put("code", BaiduActivityUtils.BAIDU_MSG.ERROR_3.getCode());
			//throw new ActivityLogicException("你已经购买过此产品");
			return resultMap;
		}
		//插入每天预订数量表 (每天可定数量计算 )
		try{
			if(this.canBookedByMemcached(productid, sandby)) {
				this.inserMobileBaiduAct(userNo, productid,sandby);	
			} else {
				this.rollback(productid, userNo, 1,sandby);
				resultMap.put("code", BaiduActivityUtils.getBookedMsg());
				log.error(".....baidu act baiduBeforSubmit amOrPm qty validate error.....productid="+productid+"...amOrPm="+sandby);
				return resultMap;
			}
		}catch(Exception e) {
			this.rollback(productid, userNo, 1,sandby);
			e.printStackTrace();
			log.error("..baidu act baiduBeforSubmit amOrPm qty check error.....productid="+productid+"...amOrPm="+sandby);
			resultMap.put("code", BaiduActivityUtils.getBookedMsg());
			return resultMap;
		}
		
		// 插入总表(总的订单量唯一性校验 ，只针对半价票)
		try{
			// 如果不是立减票 
			if((sandby+"").indexOf("s") == -1) {
				//log.error(".....this..inserMobileBaiduActStock total qty check error.....productid="+productid);
				this.inserMobileBaiduActStock(userNo, productid);
			}
			// 缓存计数加1
			//this.stockMemcachedIncr(productid);
			resultMap.put("code", BaiduActivityUtils.BAIDU_MSG.SUCCESS.getCode());
		}catch(Exception e) {
			this.rollback(productid, userNo, 2,sandby);
			e.printStackTrace();
			log.error("..baidu act baiduBeforSubmit total qty check error.....productid="+productid);
			resultMap.put("code", BaiduActivityUtils.BAIDU_MSG.ERROR_10.getCode());
			return resultMap;
			// throw new ActivityLogicException("该产品已经售完");
		}
		
		return resultMap;
	}
	/**
	 * 当天表 
	 * @param userNo
	 * @param productid
	 */
	private void inserMobileBaiduAct(String userNo, Long productid,Object sandby) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("productid", productid);
		param.put("amOrPm", sandby+"");// 当前时间段 
		mobileClientService.updateAddQtyMobileActBaidu(param);
		if (log.isDebugEnabled()){
			log.info(".....updateAddQtyMobileActBaidu....success.....");
		}
		// 当天可预订缓存+1
		this.stockMemcachedIncrOfDay(productid,sandby);
	
	}
	
	/**
	 * 总表 
	 * @param userNo
	 * @param productid
	 */
	private void inserMobileBaiduActStock(String userNo, Long productid) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("productid", productid);
		mobileClientService.updateAddQtyMobileActBaiduStock(param); 
		if (log.isDebugEnabled()){
			log.info(".....updat eAddQtyMobileActBaiduStock....success.....");
		}
	}
	

	/**
	 * 如果提交订单失败 
	 */
	@Override
	public Map<String, Object> baiduAfterSubmit(Long productid,String userNo,String inOrOut) {
		try{
			// 默认半价票 
			Object sandBy = BaiduActivityUtils.getHourFromStartDate();
			int type = 3;
			// 如果是立减票
			if(BaiduActivityUtils.isSandByTicket(productid+"")) {
				// 如果是框外 （没有总数校验 ）
				if(BaiduActivityUtils.BD_FRAMEWORKER.OUT.getCode().equals(inOrOut)) {
					sandBy = BaiduActivityUtils.getDay4Sandby(BaiduActivityUtils.getDayFromStartDate()) ;
					type = 2;
				} else {
					// 如果是框内，只计算是否订过 。 
					type = 1;
				}
			}
			this.rollback(productid, userNo, type,sandBy);
		}catch(Exception e) {
			e.printStackTrace();
			log.error("....baidu act baiduAfterSubmit .....userNo="+userNo + "..productid="+productid);
		}
		return null;
	}

	/**
	 * 回滚已经插入数据库表的数据 
	 */
	private void rollback(Long productid,String userNo,int type,Object sandBy) {
		// 用户订单表 
		if(type > 0) {
			int i = mobileClientService.deleteByUserId(userNo,productid);
			if(i > 0) {
				MemcachedUtil.getInstance().remove(BaiduActivityUtils.BAIDU_USER_ORDER+userNo);
			}
			if (log.isDebugEnabled()){
				log.info("....baidu act rollback ...1..userNo="+userNo + "..productid="+productid +"..sandBy="+sandBy);
			}
		}
		// 每天可售记录表
		if(type > 1) {
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("productid", productid);
			param.put("amOrPm", sandBy+"");
			boolean b = mobileClientService.updateMinusQtyMobileActBaidu(param);  // 可售数量减1
			// 缓存减1
			if(b) {
				this.stockMemcachedDecrOfDay(productid,sandBy);
			}
			if (log.isDebugEnabled()){
				log.info("....baidu act rollback ..2...userNo="+userNo + "..productid="+productid +"..sandBy="+sandBy);
			}
		}
		// 总表
		if(type > 2) {
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("productid", productid);
			boolean b = mobileClientService.updateMinusQtyMobileActBaiduStock(param); 
			if (log.isDebugEnabled()){
				log.info("....baidu act rollback ..3...userNo="+userNo + "..productid="+productid +"..sandBy="+sandBy);
			}
			// 缓存减1 
			/*if(b) {
				this.stockMemcachedDecr(productid);
			}*/
		}
	}
	

	/**
	 * 判断当前时间段是否可预订该产品 
	 * 1, 从缓存中获取本时间段已经预订数量 ，如果没有则读取数据库
	 * 2, 从缓存中获取本时间段可以预定的数量 ， 如果没有从数据库读取。
	 * 
	 * @param productid 产品id
	 * @return 是否可定 
	 **/
	private long canBookAtCurrentTime(Long productid) {
		Long sandby = BaiduActivityUtils.getHourFromStartDate();
		// 当前时间段-可以预定数量 
		long canBookCount = this.getCanBookCount4Day(productid,sandby);
		// 当前时间段-实际已经预订数量
		long actrueBook = this.actureCanBookQty(productid,sandby);
		if (log.isDebugEnabled()){
			log.info("...canBookAtCurrentTime....canBookCount=" +canBookCount + "..actrueBook="+actrueBook + "..productid="+productid );
		}
		return canBookCount - actrueBook;
	}
	
	/**
	 * 只在memcached中判断是否可定， 如果memcached不存在 则 可定；
	 *  插入数据库校验 钱判断 ；
	 * @return
	 */
	private boolean canBookedByMemcached(Long productid,Object sandby) {
		try{
			String key = sandby+BaiduActivityUtils.BAIDU_CAN_ORDER_DAY+productid;
			// 当前时间段可预订数量 
			Object obj = MemcachedUtil.getInstance().get(key);
			
			String hasBookedKey = sandby+BaiduActivityUtils.BAIDU_HAS_BOOKED_DAY+productid;
			// 当前时间段已经预订数量 
			long qty = MemcachedUtil.getInstance().getCounter(hasBookedKey);
			
			if(null != obj &&  qty > 0 && Long.valueOf(obj+"") <= qty) {
				return false;
			} else {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	/**
	 * 当前时间段可以预定数量  , 从天数表读取 
	 * @param productid 
	 * @return long
	 */
	private long getCanBookCount4Day(Long productid,Object sandby) {
		String key = sandby+BaiduActivityUtils.BAIDU_CAN_ORDER_DAY+productid;
		Object obj = MemcachedUtil.getInstance().get(key);
		if(null != obj){
			return Long.valueOf(obj+"");
		}  else {
			return this.canBookCountByDay(productid,sandby);
		}
	}
	
	/**
	 * 当前时间段可以预定数量 
	 * @param productid
	 * @return
	 */
	private long canBookCountByDay(Long productid,Object sandby) {
		try{
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("productid", productid);
			param.put("amOrPm", sandby+""); // 表示当前第几个时间段 
			List<MobileActBaidu> list = mobileClientService.queryMobileActBaiduList(param);
			if(null != list && list.size() > 0) {
				MobileActBaidu mbs = list.get(0);
				Long canSel= mbs.getCanSelQty();
				String key = sandby+BaiduActivityUtils.BAIDU_CAN_ORDER_DAY+productid;
				MemcachedUtil.getInstance().set(key,BaiduActivityUtils.BAIDU_CAN_ORDER_DAY_TIME,canSel);
				return canSel;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0l;
	}


	/**
	 * 当前时间段 - 预订总数  加1
	 */
	private void stockMemcachedIncrOfDay(Long productid,Object sandBy) {
		String key = sandBy+BaiduActivityUtils.BAIDU_HAS_BOOKED_DAY+productid;
		long qty = MemcachedUtil.getInstance().getCounter(key);
		if(qty == -1) {
			// 从数据库获取已经预订的数量 
			long count = this.getQtyOfDay(productid,sandBy);
			MemcachedUtil.getInstance().addOrIncrAndInit(key, count);
		} else {
			MemcachedUtil.getInstance().incr(key); // 计数加1
		}
	}

	/**
	 * 当前时间段 - 已预订总数  减1
	 */
	private void stockMemcachedDecrOfDay(Long productid,Object sandby) {
		String key = sandby+BaiduActivityUtils.BAIDU_HAS_BOOKED_DAY+productid;
		long qty = MemcachedUtil.getInstance().getCounter(key);
		if(qty == -1) {
			// 从数据库获取
			long count = this.getQtyOfDay(productid,sandby);
			MemcachedUtil.getInstance().addOrIncrAndInit(key, count);
		} else {
			log.info("....stockMemcachedDecrOfDay...");
			MemcachedUtil.getInstance().decr(key); //
		}
	}
	
	
	
	/**
	 * 半价票
	 * 如果没有达到最大请求数  = 可预订数量；
	 * @param productid
	 * @param sandby   时间段 
	 * @param maxReq   最多可购买数量  
	 * @return true 没有达到最大请求数
	 */
	private boolean productMaxRequestAmout(Long productid,Object sandby,long maxReq) {
		String key = sandby+BaiduActivityUtils.BAIDU_MAX_REQUEST+productid;
		Long o = MemcachedUtil.getInstance().getCounter(key);
		log.info("....productMaxRequestAmout.....o="+o+"....maxReq="+maxReq+"...productid="+productid+"..sandby="+sandby);
		if(o < 1) {
			MemcachedUtil.getInstance().addOrIncr(key, 1l);
			return true;
		} else {
			if(MemcachedUtil.getInstance().getCounter(key) < maxReq) { // 最多maxQty个请求 
				MemcachedUtil.getInstance().incr(key);
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * 半价票
	 * 每隔一定时间段 - 初始化最大请求数 
	 * @param productid
	 * @param sandby   时间段 
	 * @param maxReq   最多可购买数量  
	 * @return true 没有达到最大请求数
	 */
	private long initMemMaxRequestAmout(Object sandby) {
		long r = 0;
		if(null != productIds && productIds.length > 0) {
			for(int i = 0;i < productIds.length;i++) {
				Long productid = Long.valueOf(productIds[i].trim());
				// 当前时间段-可以预定数量 
				long canBookCount = this.getCanBookCount4Day(productid,sandby);
				// 当前时间段-实际已经预订数量
				long actrueBook = this.actureCanBookQty(productid,sandby);
				log.info("...initMemMaxRequestAmout....canBookCount=" +canBookCount + "..actrueBook="+actrueBook + "..productid="+productid );
				long canQty = canBookCount - actrueBook;
				if(canQty > 0) {
					String key = sandby+BaiduActivityUtils.BAIDU_MAX_REQUEST+productid;
					Long o = MemcachedUtil.getInstance().getCounter(key);
					// 如果计数器不存在 。
					if(o < 1) {
						MemcachedUtil.getInstance().addOrIncr(key, 1l);
						 r = 1l;
					} else {
						long amount = actrueBook*rate;
						MemcachedUtil.getInstance().addOrIncrAndInit(key,0l);
						r = amount;
					}
				} 
			}
		}
		return r;
	}

	/**
	 * 最大可预订数量 ，按时间段计算；
	 * 活动开始之前初始化到缓存中；
	 * 如果缓存没有初始化 （-1）则从数据库查找。
	 * @param productid
	 * @return long
	 */
	private int getCanOrderNumOfDay(Long productid,Long sandby) {
		String key = sandby+BaiduActivityUtils.BAIDU_CAN_ORDER_DAY+productid;
		Object count = MemcachedUtil.getInstance().get(key);
		if(null != count) {
			return Integer.valueOf(count+"");
		}  else {
			// 从数据库获取
			int qty = 0;
			MobileActBaidu mab = this.getMobileActBaiduByProductId(productid,sandby);
			if(null != mab) {
				qty =mab.getCanSelQty().intValue();
			}
			MemcachedUtil.getInstance().set(key,BaiduActivityUtils.BAIDU_CAN_ORDER_DAY_TIME,qty);
			return qty;
		}
	}
	
	/**
	 * 当前时间段已经预订数量 
	 * @param productid
	 * @return
	 */
	private Long getQtyOfDay(Long productid,Object sandBy) {
		MobileActBaidu mab = this.getMobileActBaiduByProductId(productid,sandBy);
		if(null != mab) {
			return mab.getQuantity();
		}
		return 0l;
	}
	
	/**
	 * 获取当前时间段 - 可卖记录表
	 * @param productid
	 * @param sandBy 
	 * @return
	 */
	private MobileActBaidu getMobileActBaiduByProductId(Long productid, Object sandBy) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("productid", productid); // 产品 
		param.put("amOrPm",sandBy+""); // 当前时间段 
		param.put("valid", "1");
		List<MobileActBaidu> list = mobileClientService.queryMobileActBaiduList(param);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}
	
	/**
	 * 插入订单表 
	 * @param userNo
	 * @param productid
	 * @param placeid
	 */
	public void inserMobileActOrder(String userNo,Long productid,Long placeid) {
		// 插入用户订单表 （用户唯一性校验）
		String key = BaiduActivityUtils.BAIDU_USER_ORDER+userNo;
		MobileActBaiduOrder mab = new MobileActBaiduOrder();
		mab.setLvUserid(userNo); // 数据表 有唯一性校验 
		mab.setCreateDate(new Date());
		mab.setProductid(productid);
		mab.setPlaceid(placeid);
		mobileClientService.insertMobileActBaiduOrder(mab);
		// 加入缓存
	    MemcachedUtil.getInstance().set(key,BaiduActivityUtils.BAIDU_USER_ORDER_SUCCESS,"booked");
	    if (log.isDebugEnabled()){
			log.info("....inserMobileActOrder.....");
		}
	}
	
	/**
	 * 当前时间段实实际预订数量 quantity
	 * @param productid
	 * @return
	 */
	private Long actureCanBookQty(Long productid,Object sandBy) {
		String key = sandBy+BaiduActivityUtils.BAIDU_HAS_BOOKED_DAY+productid;
		long qty = MemcachedUtil.getInstance().getCounter(key);
		if(qty == -1) {
			// 从数据库获取已经预订的数量 
			long count = this.getQtyOfDay(productid,sandBy);
			MemcachedUtil.getInstance().addOrIncrAndInit(key, count);
			
			return count;
		} else {
			return qty;
		}
	}
	

	/**
	 * 用户成功预订缓存
	 * @param userNo
	 * @return
	 */
	private boolean canBooked(String userNo) {
		if(StringUtils.isEmpty(userNo)) {
			return true;
		} else {
			// 从缓存中读取 - 缓存在服务启动的时候初始化
			Object o = MemcachedUtil.getInstance().get(BaiduActivityUtils.BAIDU_USER_ORDER+userNo);
			//log.info(".....canbooked...........key="+BaiduActivityUtils.BAIDU_USER_ORDER+userNo+"..value===" + o);
			if(null == o) {
				return true;
			}
			return false;
		}
	}

	public void setUserCooperationUserService(
			UserCooperationUserService userCooperationUserService) {
		this.userCooperationUserService = userCooperationUserService;
	}

	/**
	 * 初始化百度活动数据数据 
	 * 
	 */
	@Override
	public Map<String, Object> only4JunitTest(Map<String, Object> params) {
		// 判断内网 
		log.info(".... baidu act.....only4JunitTest....params="+params);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(null != params && null != params.get("opt") && !StringUtils.isEmpty(params.get("opt").toString())) {
			String opt = params.get("opt").toString();
			// 查看当前时间段信息
			if("0".equals(opt)) {
				resultMap.put("banjia", BaiduActivityUtils.getHourFromStartDate());
				resultMap.put("lijian", BaiduActivityUtils.getDay4Sandby(BaiduActivityUtils.getDayFromStartDate()));
			 // 修改每天可预订数量 半价票 和 立减票 
			}else if("1".equals(opt)) {
				String sandby = params.get("sandby").toString(); // 时间段  
				String qty = params.get("canSelQty").toString(); // 可售数量
				Long productId = null;
				if(null != params.get("productId")) {
					productId = Long.valueOf(params.get("productId").toString());
				}
				int i = this.initBaiduCanBookOfDay(sandby,Long.valueOf(qty),productId);
				resultMap.put("modifyQty", i);
			// 修改总表数量
			} else if("2".equals(opt)) {
				String qty = params.get("canSelQty").toString(); // 可售数量 
				Long productId = null;
				if(null != params.get("productId")) {
					productId = Long.valueOf(params.get("productId").toString());
				}
				int i = this.initBaiduStockInfo2(Long.valueOf(qty),productId);
				resultMap.put("modifyQty", i);
			//清楚缓存 
			}else if("3".equals(opt)) {
				String sinleOrbatch = params.get("sinleOrbatch").toString();// （1:单个修改（一次可以清楚多个key,用逗号隔开','） ；2：批量修改 ； ）
				String keys = "";
				if(null != params.get("keys")) {
					keys = params.get("keys").toString();
				}
				String sandby = ""; 
				if(null != params.get("sandby")) {
					sandby = params.get("sandby").toString();
				}
				String booktype = "";  // ；booktype 1:已预订；2:当前可预订 
				if(null != params.get("booktype")) {
					booktype = params.get("booktype").toString();
				}
				
				int i = this.cleanMemecached4Baidu(sinleOrbatch,keys,sandby,booktype);// 删除相关memcached 
				resultMap.put("modifyQty", i);
			// 修改memcached 计数器，当天已经预订的数量 
			}else if("4".equals(opt)) {
				String sinleOrbatch = params.get("sinleOrbatch").toString();// （1:单个修改（一次可以清楚多个key,用逗号隔开','） ；2：批量修改 ； ）
				String keys = "";
				if(null != params.get("keys")) {
					keys = params.get("keys").toString();
				}
				String sandby = ""; 
				if(null != params.get("sandby")) {
					sandby = params.get("sandby").toString();
				}
				String isCounter = "";  // 是否修改计数器 ，1：是  ；其它否 
				if(null != params.get("isCounter")) {
					isCounter = params.get("isCounter").toString();
				}
				
				int second = 0; // 描述 ，只对非计数器有效 
				if(null != params.get("second")) {
					second = Integer.valueOf(params.get("second").toString());
				}
				
				String count = params.get("count").toString();
				int i = this.modifyMemecached4Baidu(second,isCounter,sinleOrbatch,keys,sandby,Long.valueOf(count));//修改相关memcached 
				resultMap.put("modifyQty", i);
			// 初始化缓存  
			}else if("5".equals(opt)) {
				String sandby = params.get("sandby").toString();
				int i = this.initMemcached(sandby);
				resultMap.put("modifyQty", i);
			// 查看memcached信息 
			}else if("6".equals(opt)) {
				String sinleOrbatch = params.get("sinleOrbatch").toString();// （1:单个查看（一次可以清楚多个key,用逗号隔开','） ；2：批量查看 ）
				String sandby = "";
				if(null != params.get("sandby")) {
					sandby = params.get("sandby").toString();
				}
				String keys = "";
				if(null != params.get("keys")) {
					keys = params.get("keys").toString();
				}
				String booktype = ""; // booktype 1:已预订；2:当前可预订  (对应单个查询如果booktype == 1 不是从计数器中查询 )
				if(null != params.get("booktype")) {
					booktype = params.get("booktype").toString();
				}
				int i = this.searchMemcached(booktype,sinleOrbatch,sandby,keys,resultMap);
				resultMap.put("modifyQty", i);
			// 查看销售信息 
			}else if("7".equals(opt)) {
				String userNo = "";
				if(null != params.get("userNo")) {
					userNo = params.get("userNo").toString();
				}
				String type = params.get("table").toString(); // 1：每天可售表 ； 2：总表 ;3:用户信息 
				String sandby = "";
				if(null != params.get("sandby")) {
					sandby = params.get("sandby").toString();
				}
				int i = this.searchBaiduAct(type,sandby,userNo,resultMap);
				resultMap.put("modifyQty", i);
			// 清除用户信息 ；慎用。。。
			} else if("8".equals(opt)) {
				String userNo = params.get("userNo").toString(); // 1：用户信息
				String d_table = params.get("d_table").toString();  // true 会删除表中内容 
				String productId = params.get("productId").toString(); 
				int i = this.initBaiduActUserInfo(userNo,Long.valueOf(productId),d_table);
				resultMap.put("modifyQty", i);
			// 报表 
			}else if("9".equals(opt)){
				this.baiduReport();
			} // 初始化 天数表 可预订数据 
			else if("10".equals(opt)) {
				String type = "";
				if(null != params.get("type")) {
					type = params.get("type").toString();
				}
				this.initBaiduCanBookOfDay(type);
			// 初始化总表可预订数量
			} else if("11".equals(opt)) {
				String type = "";
				if(null != params.get("type")) {
					type = params.get("type").toString();
				}
				this.initBaiduStockInfo(type);
			}else if("12".equals(opt)) {
				String sandby = params.get("sandby").toString();  // 时间段
				this.initOrderInfo(sandby,resultMap);
			} else if("13".equals(opt)) { // 初始化请求计数器
				String sandby = "";
				if(null != params.get("sandby")) {
					sandby = params.get("sandby").toString();
				} else {
					sandby = BaiduActivityUtils.getHourFromStartDate()+"";
				}
				long i = this.initMemMaxRequestAmout(sandby);
				resultMap.put("datas", i);
			}
		}
		return resultMap;
	}

	/**
	 * 每个时间段销售
	 * @param sandby
	 */
	private void initOrderInfo(String sandby,Map<String,Object> resultMap) {
		if(StringUtils.isEmpty(sandby)) {
			return ;
		}
		List<String> ids = new ArrayList<String>();
		Map<String,Object> params = new HashMap<String,Object>();
		try {
			// 立减票
			if(sandby.indexOf("s")!= -1) {
				int day = Integer.valueOf(sandby.replace("s", ""));
				int start = day - 1;
				int end = day;
				params.put("createTimeStart", com.lvmama.clutter.utils.DateUtil.getDateAddDay(com.lvmama.clutter.utils.DateUtil.parseDate(ClutterConstant.getBaiduActStartDate4Sandby(),"yyyy-MM-dd HH:mm:ss"),start));
			    params.put("createTimeEnd",com.lvmama.clutter.utils.DateUtil.getDateAddDay(com.lvmama.clutter.utils.DateUtil.parseDate(ClutterConstant.getBaiduActStartDate4Sandby(),"yyyy-MM-dd HH:mm:ss"),end));
				CollectionUtils.addAll(ids, ClutterConstant.getBaiduActSandByProductIdList());  
				params.put("ids", ids);
				/*Object obj = mobileClientService.queryMobileActSellList(params);
				resultMap.put("datas", obj);*/
			} else { // 半价票 
				int day = Integer.valueOf(sandby);
				int start = (day - 1)*12;
				int end = day*12;
				params.put("createTimeStart", com.lvmama.clutter.utils.DateUtil.getDateAddHour(com.lvmama.clutter.utils.DateUtil.parseDate(ClutterConstant.getBaiduActStartDate(),"yyyy-MM-dd HH:mm:ss"),start));
				params.put("createTimeEnd",com.lvmama.clutter.utils.DateUtil.getDateAddHour(com.lvmama.clutter.utils.DateUtil.parseDate(ClutterConstant.getBaiduActStartDate(),"yyyy-MM-dd HH:mm:ss"),end));
				CollectionUtils.addAll(ids, ClutterConstant.getBaiduActProductIdList());  
				params.put("ids", ids);
				/*Object obj = mobileClientService.queryMobileActSellList(params);
				resultMap.put("datas", obj);*/
			}
		}catch(Exception e) {
		    e.printStackTrace();	
		}
	}
	/**
	 * 报表 
	 */
	private void baiduReport() {
		// TODO Auto-generated method stub
		
	}

    /**
	 * 初始化每天可以预定张数 
	 * type 1:单倍半价票 ；2：双倍半价票 ；3：立减票 
	 */
	private void initBaiduCanBookOfDay(String type) {
		int totalDays = 18; // 共18个时间段
		
		if("1".equals(type)) {
			// 单倍半价票 
			for(int d = 1 ; d <= totalDays;d++) {
				// 共100个半价产品
				String[] ids = ClutterConstant.getBaiduActProdId4Single();
				if(null != ids && ids.length > 0) {
					for(int i = 0 ;i < ids.length; i++) {
						MobileActBaidu mab = new MobileActBaidu();
						mab.setAmOrPm(d+""); // 时间段
						mab.setValid("1");
						mab.setProductid(Long.valueOf(ids[i]));
						mab.setCanSelQty(Long.valueOf(ClutterConstant.getBaiduActSellQty()));
						mab.setQuantity(0l);
						mab.setUpdateDate(new Date());
						mobileClientService.insertMobileActBaidu(mab);
					}
				}
				
			}
		} else if("2".equals(type)) {
			// 双倍半价票 
			for(int d = 1 ; d <= totalDays;d++) {
				// 共100个半价产品
				String[] ids = ClutterConstant.getBaiduActProdId4Double();
				if(null != ids && ids.length > 0) {
					for(int i = 0 ;i < ids.length; i++) {
						MobileActBaidu mab = new MobileActBaidu();
						mab.setAmOrPm(d+""); // 时间段
						mab.setValid("1");
						mab.setProductid(Long.valueOf(ids[i]));
						mab.setCanSelQty(Long.valueOf(ClutterConstant.getBaiduActSellQty4Double()));
						mab.setQuantity(0l);
						mab.setUpdateDate(new Date());
						mobileClientService.insertMobileActBaidu(mab);
					}
				}
				
			}
		} else if("3".equals(type)) {
			// 立减票 
			long totalDays2 = 9l; // 共9个时间段
			for(long d = 1 ; d <= totalDays2;d++) {
				// 共100个半价产品
				String[] ids = ClutterConstant.getBaiduActSandByProductIdList();
				if(null != ids && ids.length > 0) {
					for(int i = 0 ;i < ids.length; i++) {
						MobileActBaidu mab = new MobileActBaidu();
						mab.setAmOrPm(BaiduActivityUtils.getDay4Sandby(d)); // 时间段
						mab.setValid("1");
						mab.setProductid(Long.valueOf(ids[i]));
						mab.setCanSelQty(Long.valueOf(ClutterConstant.getBaiduActSellQty4Sandby()));
						mab.setQuantity(0l);
						mab.setUpdateDate(new Date());
						mobileClientService.insertMobileActBaidu(mab);
					}
				}
			}
		}
	}
	
	/**
	 * 库存 type:1  单倍半价票总库存  ;  2:双倍半价票总库存 
	 */
	private void initBaiduStockInfo(String type) {
		if("1".equals(type)) {
			// 单倍半价票库存 
			String[] ids = ClutterConstant.getBaiduActProdId4Single();
			if(null != ids && ids.length > 0) {
				for(int i = 0 ;i < ids.length; i++) {
					MobileActBaiduStock mab = new MobileActBaiduStock();
					mab.setValid("1");
					mab.setProductid(Long.valueOf(ids[i]));
					mab.setCanSelQty(ClutterConstant.getBaiduActSellQty()*18l);
					mab.setQuantity(0l);
					mab.setUpdateDate(new Date());
					mobileClientService.insertMobileActBaiduStock(mab);
				}
			}
		} else if("2".equals(type)) {
			// 双倍半价票库存 
			String[] douids = ClutterConstant.getBaiduActProdId4Double();
			if(null != douids && douids.length > 0) {
				for(int i = 0 ;i < douids.length; i++) {
					MobileActBaiduStock mab = new MobileActBaiduStock();
					mab.setValid("1");
					mab.setProductid(Long.valueOf(douids[i]));
					mab.setCanSelQty(ClutterConstant.getBaiduActSellQty4Double()*18l);
					mab.setQuantity(0l);
					mab.setUpdateDate(new Date());
					mobileClientService.insertMobileActBaiduStock(mab);
				}
			}
		}
	}
	
	
	/**
	 *  清除用户信息 ；慎用。。。
	 * @param userNo
	 * @param d_table
	 * @return
	 */
	private int initBaiduActUserInfo(String userNo, Long productId,String d_table) {
		int i = 0 ;
		if(StringUtils.isEmpty(userNo)) {
			return i;
		}
		String key = BaiduActivityUtils.BAIDU_USER_ORDER+userNo;
		MemcachedUtil.getInstance().remove(key);
		log.info(".... baidu act.....delete userInfo initBaiduActUserInfo....userNo="+userNo);
		if("true".equals(d_table)) {
			log.info(".............userNo="+userNo+"..productId="+productId);
			mobileClientService.deleteByUserId(userNo,productId);
		}
		
		return 0;
	}

	/**
	 * 查看表数据 
	 * @param type  1：每天可售表 ； 2：总表  ;3:用户表
	 * @param sandby 时间段 
	 * @return
	 */
	private int searchBaiduAct(String type, String sandby,String userNo,Map<String,Object> resultMap) {
		int i = 0 ; 
		if(StringUtils.isEmpty(type)) {
			return i;
		}
		if("3".equals(type)) {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("lvUserid", userNo);
			List<MobileActBaiduOrder> list = mobileClientService.queryMobileActBaiduOrderList(params);
			for(MobileActBaiduOrder mbs:list) {
				mbs.setCreateDate(null);
			}
			resultMap.put("datas", list);
		} else if("1".equals(type)) {
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("amOrPm", sandby+""); // 表示当前第几个时间段 
			List<MobileActBaidu> list = mobileClientService.queryMobileActBaiduList(param);
			for(MobileActBaidu mbs:list) {
				mbs.setUpdateDate(null);
			}
			resultMap.put("datas", list);
			
		}else if("2".equals(type)){
			Map<String, Object> param = new HashMap<String,Object>();
			List<MobileActBaiduStock> list = mobileClientService.queryMobileActBaiduStockList(param);
			for(MobileActBaiduStock mbs:list) {
				mbs.setUpdateDate(null);
			}
			resultMap.put("datas", list);
		}
		return i;
	}

	/**
	 * 看memcached信息
	 *  @param booktype 1:已预订；2:当前可预订 
	 * @param sinleOrbatch
	 * @param sandby
	 * @param keys
	 * @return
	 */
	private int searchMemcached(String booktype,String sinleOrbatch, String sandby, String keys,Map<String,Object> resultMap) {
		int i = 0;
		 // 如果是单个查询 ，需要完整的keys 
		if("1".equals(sinleOrbatch)) {
			if(StringUtils.isEmpty(keys)) {
				return i;
			}
			String[] keyArrays = keys.split(",");
			for(int a = 0 ; a < keyArrays.length;a++) {
				// 
				try {
					Object l = "";
					if("1".equals(booktype)) {
						l = MemcachedUtil.getInstance().get(keyArrays[a]);
					} else {
						l = MemcachedUtil.getInstance().getCounter(keyArrays[a]);
					}
					
					resultMap.put(keyArrays[a], l);
					i++;
					
				}catch(Exception e){
					e.printStackTrace();
					log.info("...baidu act   delete memcatched key error...key="+keyArrays[a]);
				}
			}
		// 批量查询  
		} else if("2".equals(sinleOrbatch)) {
			if(StringUtils.isEmpty(sandby)) {
				return i;
			}
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("amOrPm", sandby+""); // 表示当前第几个时间段 
			List<MobileActBaidu> list = mobileClientService.queryMobileActBaiduList(param);
			if(null != list && list.size() > 0) {
				log.info("....baidu act searchMemcached...sandBy="+sandby + "..size=" + list.size());
				for(MobileActBaidu mbo:list) {
					// 放入缓存 
					String key = sandby+BaiduActivityUtils.BAIDU_HAS_BOOKED_DAY+mbo.getProductid();
					if("2".equals(booktype)) {
						key = sandby+BaiduActivityUtils.BAIDU_CAN_ORDER_DAY+mbo.getProductid();
					}
					try {
						Object l = "";
						if("2".equals(booktype)) {
							l = MemcachedUtil.getInstance().get(key);
						} else {
							l = MemcachedUtil.getInstance().getCounter(key);
						}
						
						resultMap.put(key, l);
						i++;
					}catch(Exception e){
						e.printStackTrace();
						log.info("...baidu act  searchMemcached delete memcatched key error...222..key="+MemcachedUtil.getInstance().remove(key));
					}
				}
			}
		}
		return i;
	}

	/**
	 * 初始化缓存  ,每天可售金额 
	 * @param sandby
	 * @return
	 */
	private int initMemcached(String sandby) {
		int i = 0 ; 
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("amOrPm", sandby+""); // 表示当前第几个时间段 
		List<MobileActBaidu> list = mobileClientService.queryMobileActBaiduList(param);
		if(null != list && list.size() > 0) {
			log.info("....baidu act initMemcached...sandby="+sandby + "..size=" + list.size());
			for(MobileActBaidu mbo:list) {
				// 放入缓存 
				String key = sandby + BaiduActivityUtils.BAIDU_CAN_ORDER_DAY+mbo.getProductid();
				try {
					MemcachedUtil.getInstance().set(key,BaiduActivityUtils.BAIDU_CAN_ORDER_DAY_TIME,mbo.getCanSelQty());
					i++;
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return i;
	}

	/**
	 * 修改memcached计数器
	 * @param sec  设置秒
	 * @param isCounter  是否修改计数器  1： 修改计数器 ； 否则没有修改 
	 * @param sinleOrbatch
	 * @param keys
	 * @param sandby
	 * @return
	 */
	private int modifyMemecached4Baidu(int sec,String isCounter ,String sinleOrbatch, String keys,
			String sandBy,Long count) {
		int i = 0 ; 
		if(StringUtils.isEmpty(sinleOrbatch) ) {
			return i;
		}
        // 如果是单个修改，需要完整的keys 
		if("1".equals(sinleOrbatch)) {
			if(StringUtils.isEmpty(keys)) {
				return i;
			}
			String[] keyArrays = keys.split(",");
			for(int a = 0 ; a < keyArrays.length;a++) {
				// 
				try {
					if("1".equals(isCounter)) {
						// 如果计数器存在 ，则删除计数器，然后初始化 
						MemcachedUtil.getInstance().addOrIncrAndInit(keyArrays[a], count);
					} else {
						MemcachedUtil.getInstance().set(keyArrays[a], sec,count);
					}
					i++;
					
				}catch(Exception e){
					e.printStackTrace();
					log.info("...baidu act   delete memcatched key error...key="+keyArrays[a]);
				}
			}
		// 批量修改  计数器 
		} else if("2".equals(sinleOrbatch)) {
			if(StringUtils.isEmpty(sandBy)) {
				return i;
			}
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("amOrPm", sandBy+""); // 表示当前第几个时间段 
			List<MobileActBaidu> list = mobileClientService.queryMobileActBaiduList(param);
			if(null != list && list.size() > 0) {
				log.info("....baidu act modifyMemecached4Baidu...sandBy="+sandBy + "..size=" + list.size());
				for(MobileActBaidu mbo:list) {
					String key = sandBy+BaiduActivityUtils.BAIDU_HAS_BOOKED_DAY+mbo.getProductid();
					if("1".equals(isCounter)) {
						
					} else {
						key = sandBy+BaiduActivityUtils.BAIDU_CAN_ORDER_DAY+mbo.getProductid();
					}
					
					try {
						if("1".equals(isCounter)) {
							// 放入缓存  - 计数器 
							MemcachedUtil.getInstance().addOrIncrAndInit(key, count);
						} else {
							// 放入缓存  - 非计数器 
							MemcachedUtil.getInstance().set(key,sec, count);
						}
						i++;
					}catch(Exception e){
						e.printStackTrace();
						log.info("...baidu act   delete memcatched key error...222..key="+MemcachedUtil.getInstance().remove(key));
					}
				}
			}
		}
		return i;
	}

	/**
	 * 删除相关memcached 
	 * @param sinleOrbatch 1:单个清楚（一次可以清楚多个key,用逗号隔开','） ；2：批量清 ；
	 * @param keys  （一次可以清楚多个key,用逗号隔开','）
	 * @param sandBy  时间段 
	 * @param   booktype 1:已预订；2:当前可预订 
	 */
	private int cleanMemecached4Baidu(String sinleOrbatch,
			String keys,String sandBy,String bookeType) {
		int i = 0 ; 
		if(StringUtils.isEmpty(sinleOrbatch) ) {
			return i;
		}
        // 如果是单个清楚 ，需要完整的keys 
		if("1".equals(sinleOrbatch)) {
			if(StringUtils.isEmpty(keys)) {
				return i;
			}
			String[] keyArrays = keys.split(",");
			for(int a = 0 ; a < keyArrays.length;a++) {
				// 
				try {
					MemcachedUtil.getInstance().remove(keyArrays[a]);
				}catch(Exception e){
					e.printStackTrace();
					log.info("...baidu act   delete memcatched key error...key="+keyArrays[a]);
				}
			}
		// 批量删除 
		} else if("2".equals(sinleOrbatch)) {
			if(StringUtils.isEmpty(sandBy)) {
				return i;
			}
			
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("amOrPm", sandBy+""); // 表示当前第几个时间段 
			List<MobileActBaidu> list = mobileClientService.queryMobileActBaiduList(param);
			if(null != list && list.size() > 0) {
				log.info("....baidu act initCanBookOfDayInfo...sandBy="+sandBy + "..size=" + list.size());
				for(MobileActBaidu mbo:list) {
					// 放入缓存 
					String key = sandBy + BaiduActivityUtils.BAIDU_CAN_ORDER_DAY+mbo.getProductid();
					if("2".equals(bookeType)) {
						key = sandBy + BaiduActivityUtils.BAIDU_HAS_BOOKED_DAY+mbo.getProductid();
					} else {
						 MemcachedUtil.getInstance().addOrIncrAndInit(key, 0l);
					}
					try {
						MemcachedUtil.getInstance().remove(key);
					}catch(Exception e){
						e.printStackTrace();
						log.info("...baidu act   delete memcatched key error...222..key="+MemcachedUtil.getInstance().remove(key));
					}
					i++;
				}
			}
		}
		return i;
	}

	/**
	 * 修改每天可预订数量 
	 * ticketType  1:半价票 ； 0：立减票 
	 */
	private int initBaiduCanBookOfDay(String sandBy,Long canSelQty,Long productId) {
		int i = 0;
		if(StringUtils.isEmpty(sandBy) ) {
			return i;
		}
	
		Map<String, Object> param = new HashMap<String,Object>();
		if(null != productId) {
			param.put("productid", productId);
		}
		param.put("amOrPm", sandBy+""); // 对应立减票表示当前第几个时间段 
		List<MobileActBaidu> list = mobileClientService.queryMobileActBaiduList(param);
		if(null != list && list.size() > 0) {
			log.info("....baidu act initBaiduCanBookOfDay.....sandBy="+sandBy + "..size=" + list.size());
			for(MobileActBaidu mbo:list) {
				try{
					if(null != mbo) {
						if(mbo.getQuantity() > canSelQty ) {
							mbo.setQuantity(canSelQty); // 如果已经销售数量大于要设置的数量 ，则该产品不可售了。
						}
						
						if(null != canSelQty) {
							mbo.setCanSelQty(canSelQty);
						}
						mobileClientService.updateMobileActBaidu(mbo);
						i ++;
					}
				}catch(Exception e) {
					e.printStackTrace();
					log.error("....baidu act initBaiduCanBookOfDay..error...sandBy="+sandBy + "..size=" + list.size());
				}
				
			}
		}
		return i;
	}
	
	/**
	 * 修改库存 
	 */
	private int  initBaiduStockInfo2(Long canselQty,Long productId) {
		int i = 0;
		if(null == canselQty ) {
			return i;
		}
		
		Map<String, Object> param = new HashMap<String,Object>();
		if(null != productId) {
			param.put("productid", productId);
		}
		List<MobileActBaiduStock> list = mobileClientService.queryMobileActBaiduStockList(param);
		if(null != list && list.size() > 0) {
			log.info("....baidu act initBaiduStockInfo.....canselQty="+canselQty + "..size=" + list.size());
			for(MobileActBaiduStock mbo:list) {
				try{
					if(null != mbo) {
						if(mbo.getQuantity() > canselQty ) {
							mbo.setQuantity(canselQty); // 如果已经销售数量大于要设置的数量 ，则该产品不可售了。
						}
						
						if(null != canselQty) {
							mbo.setCanSelQty(canselQty);
						}
						mobileClientService.updateMobileActBaiduStock(mbo);
						i++;
					}
				}catch(Exception e) {
					e.printStackTrace();
					log.error("....baidu act initBaiduCanBookOfDay..error...canselQty="+canselQty + "..size=" + list.size());
				}
				
			}
		}
		return i;
	}
	
	/**
	 * 生成百度所需的xml格式文件 . 
	 */
	@Override
	public Map<String, Object> generatorBaiduXml(Map<String, Object> params) {
		//
		this.baiduSiteMap();
		/*String t_name = "grab4baidu.xml";
		if(null != params.get("fileName")) {
			t_name = params.get("fileName").toString().trim()+".xml";
		}
		String fileNamePrex = BaiduActivityUtils.getRootPath()+File.separator+"baidu"+File.separator;
		String fileName = fileNamePrex+t_name;
		//log.info("...generatorBaiduXml fileName="+fileName);
		//String fileNamePrex="E://";
		//String fileName="E://test.xml";
		String[] placeIds = ClutterConstant.getBaiduActPlaceids();
		BaiduXmlGenerator bxg = BaiduXmlGenerator.getInstance();
		Document doc = bxg.getDocument();
		Element element = bxg.getUrlSetElement(doc);
		Map<String,String> placeIdsMap = ClutterConstant.getBaiduActPlaceids4Map();
		for(String strPlaceId:placeIds) {
			try {
				Long placeId = Long.valueOf(strPlaceId);
				Place place = this.placeService.queryPlaceByPlaceId(placeId);
				// 获取景点对应的产品信息
				if(null == place) {
					log.error("....generatorBaiduXml...error ... place can not find...placeId="+placeId);
					continue;
				}
				place.setImgUrl(this.imgUrl(placeId)); // 设置图片 
				
				// 总点评数 和 分数 
				PlaceSearchInfo psi = placeSearchInfoService.getPlaceSearchInfoByPlaceId(placeId);
				place.setCommentCount(null == psi.getCmtNum()?0l:psi.getCmtNum()); // 点评总数
				place.setAvgScore(null == psi.getCmtNiceRate()?0f:(psi.getCmtNiceRate().intValue()*20)); // 点评评价分数 . 
				
				// 可售数量 
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("placeId", placeId);
				Map<String, Object> productMap = mobileProductService.getBranches(param);
				
				// 活动票 
				if(placeIdsMap.containsKey(placeId+"")) {
					this.initActivityBranch(productMap, placeId);
				}
							
				// 过滤掉不存在priceList的数据 
				if(this.hasPriceList(productMap)) {
					// 生成 xml 
					bxg.getUrlInfo(element, place, productMap);
				} 
				
			}catch(Exception e) {
				e.printStackTrace();
				log.error("...generatorBaiduXml...error......33.....");
			}
		}
		
		bxg.getneratorBaiduXml(doc, fileName);
		
		// 生成sitemap
		Document smDoc = bxg.getDocument();
		try{
			Element elementSm = bxg.getSiteMap(smDoc);
			List<String> names = new ArrayList<String>();
			names.add("http://qing.lvmama.com/clutter/baidu/"+t_name);
			bxg.genaratorSiteMap(elementSm,names);
			bxg.getneratorBaiduXml(smDoc, fileNamePrex+"sitemap.xml");
		}catch(Exception e) {
			e.printStackTrace();
			log.error("...genaratorSiteMap...error......44.....");
		}
		
		*/
		
		return null;
	}
	
	/**
	 * 所有景点 。
	 */
	public void baiduSiteMap() {
		String t_name = "grab4baidu";
		long pageSize = 1000;
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("isValid", "Y");
		param.put("stage", "2");
		Long count = placeService.countPlaceListByParam(param); // 总数量
		if(count > 0) {
			Map<String,String> placeIds = ClutterConstant.getBaiduActPlaceids4Map();
			long size = count/pageSize ; // 页数 
			if(count%pageSize != 0) {
				size++;
			}
			
			BaiduXmlGenerator bxg = BaiduXmlGenerator.getInstance();
			
			String fileNamePrex = BaiduActivityUtils.getRootPath()+File.separator+"baiduClientStatic"+File.separator;
			String fileName = fileNamePrex+t_name;
			//String fileNamePrex="E://";
			//String fileName="E://test";
			
			//List<String> names = new ArrayList<String>();
			Map<String,String> maps = new HashMap<String,String>();
			
			Page p = new Page(pageSize, 1);
			// 每页生成一个xml文件 
			for(long i = 1; i <= size ;i++) {
				p.setPage(i);
				param.put("startRows", p.getStartRows());
				param.put("endRows", p.getEndRows());
				List<Place> list = placeService.queryPlaceListByParam(param );
				
				
				Document doc = bxg.getDocument();
				Element element = bxg.getUrlSetElement(doc);
				
				
				// 生成一个xml文件 
				if(null != list && list.size() > 0) {
					for(Place place:list) {
						Long placeId = place.getPlaceId();
						try {
							// 获取景点对应的产品信息
							if(null == place || null == placeId || placeId < 1) {
								log.error("....generatorBaiduXml...error ... place can not find...placeId="+placeId);
								continue;
							}
							
							// 总点评数 和 分数 
							PlaceSearchInfo psi = placeSearchInfoService.getPlaceSearchInfoByPlaceId(placeId);
							place.setCommentCount(null == psi.getCmtNum()?0l:psi.getCmtNum()); // 点评总数
							place.setAvgScore(null == psi.getCmtNiceRate()?0f:(psi.getCmtNiceRate().intValue()*20)); // 点评评价分数 . 
							
							place.setImgUrl(this.imgUrl(psi)); // 设置图片 
							
							// 可售数量 
							Map<String,Object> params = new HashMap<String,Object>();
							params.put("placeId", placeId);
							Map<String, Object> productMap = mobileProductService.getBranches(params);
							// 活动票 
							if(placeIds.containsKey(placeId+"")) {
								this.initActivityBranch(productMap, placeId);
							}
							// 过滤掉不存在priceList的数据 
							if(this.hasPriceList(productMap)) {
								// 生成 xml 
								bxg.getUrlInfo(element, place, productMap);
								maps.put(i+"", "http://qing.lvmama.com/clutter/baidu/"+t_name+i+".xml");
							} 
							
						}catch(Exception e) {
							e.printStackTrace();
							log.error("...generatorBaiduXml...error......33.....");
						}
					}
					bxg.getneratorBaiduXml(doc, fileName+i+".xml");
				}
			}
			
			// 生成sitemap
			Document smDoc = bxg.getDocument();
			try{
				Element elementSm = bxg.getSiteMap(smDoc);
				//List<String> names = this.getNamse(size,fileName);
				List<String> names = new ArrayList<String>();
				names.addAll(maps.values());
				bxg.genaratorSiteMap(elementSm,names);
				bxg.getneratorBaiduXml(smDoc, fileNamePrex+"sitemap.xml");
			}catch(Exception e) {
				e.printStackTrace();
				log.error("...genaratorSiteMap...error......44.....");
			}
			
		}
	}
	
	private String imgUrl(PlaceSearchInfo place) {
		if(null != place) {
			if(!StringUtils.isEmpty(place.getImgUrl())) {
				return place.getImgUrl();
			} else if(!StringUtils.isEmpty(place.getMiddleImage())) {
				return place.getMiddleImage();
			}else if(!StringUtils.isEmpty(place.getSmallImage())) {
				return place.getSmallImage();
			}else if(!StringUtils.isEmpty(place.getLargeImage())) {
				return place.getLargeImage();
			} 
		}
		
		return this.imgUrl(place.getPlaceId());
	}

	/**
	 * 初始化活动票 
	 * @param productMap
	 * @param placeId
	 */
	public void initActivityBranch(Map<String,Object>productMap,Long placeId ) {
		try {
			Map<String,Object> m = new HashMap<String,Object>();
			List<MobileBranch> mlist = this.getActivityBranch(placeId);
			if(null != mlist && mlist.size() > 0) {
				m.put("datas", mlist);
				if(null != productMap  ) {
					if(null != productMap.get("single")) {
						List<Map<String,Object>> listObj = (List<Map<String, Object>>) productMap.get("single");
						if(null != listObj && listObj.size() > 0) {
							listObj.add(m);
						} else {
							List<Map<String,Object>> objs = new ArrayList<Map<String,Object>>();
							objs.add(m);
							productMap.put("single", objs);
						}
					} else {
						List<Map<String,Object>> obj = new ArrayList<Map<String,Object>>();
						obj.add(m);
						productMap.put("single", obj);
					}
				} else {
					productMap = new HashMap<String,Object>();
					List<Map<String,Object>> obj = new ArrayList<Map<String,Object>>();
					obj.add(m);
					productMap.put("single", obj);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
	}

	/**
	 * 获取活动票 
	 * @param placeId
	 * @return
	 */
	public List<MobileBranch> getActivityBranch(Long placeId) {
		//百度活动产品--立减/半价
		List<ProdBranchSearchInfo> baiduGoodsList=this.getActivityBranches(placeId);
		List<MobileBranch> listB = new ArrayList<MobileBranch>();
		if(baiduGoodsList!=null && baiduGoodsList.size()>0){ 
			for (ProdBranchSearchInfo o : baiduGoodsList) {
				MobileBranch mb = ClientUtils.copyTicketBranch(o,new ProductSearchInfo());//对象转换
				mb.setFullName(o.getProductName());
				mb.setMarketPriceYuan(mb.getSellPriceYuan());
				mb.setSellPriceYuan(BaiduActivityUtils.getProductPrice(String.valueOf(mb.getProductId()),mb.getSellPriceYuan()));
				listB.add(mb);
			}
		}
		return listB;
	}


	/**
	 * sitemap名字 
	 * @param size
	 * @return
	 */
	private List<String> getNamse(long size,String t_name) {
		List<String> names =new ArrayList<String>();
		for(int i = 0; i < size ;i++) {
			names.add("http://qing.lvmama.com/clutter/baidu/"+t_name+(i+1)+".xml");
		}
		return names;
	}

	/**
	 * 是否有可售门票 
	 * @param productMap
	 * @return
	 */
	public boolean hasPriceList(Map<String,Object> productMap) {
		boolean b = false;
		if(null != productMap  ) {
			if(null != productMap.get("single")) {
				List<Map<String,Object>> obj = (List<Map<String, Object>>) productMap.get("single");
				if(null != obj && obj.size() > 0) {
					List<MobileBranch> mbList = (List<MobileBranch>)obj.get(0).get("datas");
					if(null != mbList && mbList.size() > 0) {
						b = true;
					}
				}
			} 
			if(null != productMap.get("union")) {
				List<Map<String,Object>> obj = (List<Map<String, Object>>) productMap.get("union");
				if(null != obj && obj.size() > 0) {
					List<MobileBranch> mbList = (List<MobileBranch>)obj.get(0).get("datas");
					if(null != mbList && mbList.size() > 0) {
						b = true;
					}
				}
			}
			if(null != productMap.get("suit")) {
				List<Map<String,Object>> obj = (List<Map<String, Object>>) productMap.get("suit");
				if(null != obj && obj.size() > 0) {
					List<MobileBranch> mbList = (List<MobileBranch>)obj.get(0).get("datas");
					if(null != mbList && mbList.size() > 0) {
						b = true;
					}
				}
			}
		}
		
		return b;
	}
	
	/**
	 * 获取url 
	 * @param placeId
	 * @return
	 */
	private String imgUrl(Long placeId ){
		PlacePhoto pp = new PlacePhoto();
		pp.setType(PlacePhotoTypeEnum.MIDDLE.name());
		pp.setPlaceId(placeId);
		List<PlacePhoto> ppList =  this.placePhotoService.queryByPlacePhoto(pp);
		if(null != ppList && ppList.size() > 0) {
			PlacePhoto p = ppList.get(0);
			return p.getImagesUrl();
		}
		return "";
	}
	
	public void setMobileProductService(IClientProductService mobileProductService) {
		this.mobileProductService = mobileProductService;
	}

	 /**
	  * 框内：判断框内立减票是否可定，是一个总开关，在php中设置，默认是可以预定；否则提示去下载app
	  * 
	  * 框外：判断是否达到今天预定数量，如果达到提示去下载app ；否则可以下载； 
	  *  
	  * @param productId 产品id
	  * @param type 0 ：框内； 1：框外
	  * @return
	  */
	@Override
	public String booking4HalfAndSandbyTicket(Long productId, String type) {
		
		// 产品id  105030  105022  不需要开始日期限制 
		if("105030".equals(productId+"") || "105022".equals(productId+"")) {
			return BaiduActivityUtils.BAIDU_MSG.SUCCESS.getCode(); // 可订
		}
						
				
		String result=BaiduActivityUtils.BAIDU_MSG.ERROR_12.getCode();
		
		try {
			// 时间是否有效 
			if(BaiduActivityUtils.isValidateDate(productId+"")) { 
				// 立减是否可销售 
				if(this.isValidateTime4Sandby(productId, type)) { 
					result= BaiduActivityUtils.BAIDU_MSG.SUCCESS.getCode(); 
				} else { 
					result=BaiduActivityUtils.getBookedMsg(); 
				} 
			} else { 
				result= BaiduActivityUtils.BAIDU_MSG.ERROR_1.getCode(); 
			} 

			
		}catch(Exception e) {
			e.printStackTrace();
			log.error("....booking4HalfAndSandbyTicket...error.....");
		}
		
		return result;
	}
	
	/**
	 * 立减票是否有效 ；
	 * @param productId
	 * @param type
	 * @return
	 */
	private boolean isValidateTime4Sandby(Long productId, String type){
		boolean b  = false;
		// 如果是框内 
		if(BaiduActivityUtils.BD_FRAMEWORKER.IN.getCode().equals(type)) {
			// 从缓存中获取总开关 
			b = this.canBook4KuangNei();
		// 如果是框外
		} else if(BaiduActivityUtils.BD_FRAMEWORKER.OUT.getCode().equals(type) ) {
			// 判断当前时间段是否可定 
			b = this.canBookAtCurrentTime4Sandby(productId)>0;
		}
		
		return b;
	}
	
	
	/**
	 * 框内开关是否开启  默认true ；
	 * 
	 * @return 
	 */
	private boolean canBook4KuangNei() {
		boolean b = true;
		try {
			String key = "baidu_act_mainSwitch4Sandby"; // 立减票框内总开关   缓存 2小时 
			// 从缓存中访问 ；
			Object obj = MemcachedUtil.getInstance().get(key);
			if(null == obj) {
				//访问php后台开关 
				String jsonStr =HttpsUtil.requestGet("http://m.lvmama.com/client/bd_act_swit.json");
				@SuppressWarnings("unchecked")
				Map<String,Object> jsonStrMap =  JSONObject.fromObject(jsonStr);
				if(null != jsonStrMap && null != jsonStrMap.get("switch")) {
					String r_str = jsonStrMap.get("switch").toString();
					b = r_str.equals("true");
					MemcachedUtil.getInstance().set(key, 60*60*2,r_str); // 缓存两个小时 
				}
			} else {
				return "true".equals(obj.toString());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * 立减票
	 * 判断当前时间段是否可预订该产品 
	 * 1, 从缓存中获取本时间段已经预订数量 ，如果没有则读取数据库
	 * 2, 从缓存中获取本时间段可以预定的数量 ， 如果没有从数据库读取。
	 * 
	 * @param productid 产品id
	 * @return 是否可定 
	 **/
	private long canBookAtCurrentTime4Sandby(Long productid) {
		Object sandby = BaiduActivityUtils.getDay4Sandby(BaiduActivityUtils.getDayFromStartDate());
		
		// 当前时间段-可以预定数量 
		long canBookCount = this.getCanBookCount4Day(productid,sandby);
		// 当前时间段-实际已经预订数量
		long actrueBook = this.actureCanBookQty(productid,sandby);
		log.info("...canBookAtCurrentTime4Sandby..sandby="+sandby+"..canBookCount=" +canBookCount + "..actrueBook="+actrueBook + "..productid="+productid );
		return canBookCount - actrueBook;
	}
	

}
