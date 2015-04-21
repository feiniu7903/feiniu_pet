
package com.lvmama.clutter.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.cookie.DateUtils;
import org.springframework.beans.BeanUtils;

import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.model.MobileMyFavorite;
import com.lvmama.clutter.model.MobileOrder;
import com.lvmama.clutter.model.MobileOrderCmt;
import com.lvmama.clutter.model.MobileOrderItem;
import com.lvmama.clutter.model.MobilePersonItem;
import com.lvmama.clutter.model.MobileReceiver;
import com.lvmama.clutter.model.MobileUser;
import com.lvmama.clutter.model.MobileUserCoupon;
import com.lvmama.clutter.service.IClientUserService;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.CouponUtils;
import com.lvmama.clutter.utils.JSONUtil;
import com.lvmama.clutter.utils.MobileCopyPropertyUtils;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountItem;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdOrderTraffic;
import com.lvmama.comm.bee.po.ord.OrdOrderTrafficTicketInfo;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.prod.LineStationStation;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.view.MarkCouponUserInfo;
import com.lvmama.comm.pet.po.businessCoupon.BusinessCoupon;
import com.lvmama.comm.pet.po.comment.DicCommentLatitude;
import com.lvmama.comm.pet.po.mark.MarkCouponUsage;
import com.lvmama.comm.pet.po.mobile.MobileFavorite;
import com.lvmama.comm.pet.po.mobile.MobilePersistanceLog;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.businessCoupon.BusinessCouponService;
import com.lvmama.comm.pet.service.mark.MarkCouponUserService;
import com.lvmama.comm.pet.service.mobile.MobileFavoriteService;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.ord.OrdOrderChannelService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.pet.vo.PlaceCoordinateVo;
import com.lvmama.comm.utils.ClientConstants;
import com.lvmama.comm.utils.CommentUtil;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.CLIENT_FAVORITE_TYPE;
import com.lvmama.comm.vo.Constant.PAYMENT_TARGET;
import com.lvmama.comm.vo.ProdCProduct;
import com.lvmama.comm.vo.UserCouponDTO;
import com.lvmama.comm.vo.comment.CmtLatitudeVO;
import com.lvmama.comm.vo.comment.CmtPictureVO;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
import com.lvmama.comm.vo.comment.PlaceCmtCommentVO;
import com.lvmama.comm.vo.comment.ProductCmtCommentVO;

public class ClientUserServiceImpl extends AppServiceImpl implements IClientUserService {
	private static final Log log = LogFactory.getLog(ClientUserServiceImpl.class);
	/**
	 * 现金账户服务
	 */
	protected CashAccountService cashAccountService;
	
	/**
	 * 我的收藏接口. 
	 */
	MobileFavoriteService mobileFavoriteService;
	
	/**
	 * 优惠策略
	 */
	private BusinessCouponService businessCouponService;
	
	/**
	 * 优惠券
	 */
	private MarkCouponUserService markCouponUserService;
	
	/**
	 * 订单渠道服务. 
	 */
	private OrdOrderChannelService ordOrderChannelService;
	
	/**
	 * 根据用户32位 唯一标示查找.
	 */
	public MobileUser getUser(Map<String, String> param) {
		MobileUser mu = new MobileUser();
		UserUser user = userUserProxy.getUserUserByUserNo(String.valueOf(param
				.get("userNo")));
		if (null == user) {
			return null;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userId", user.getId());
		List<UserCooperationUser> cooperationUseres = userCooperationUserService.getCooperationUsers(parameters);
		if(cooperationUseres!=null && !cooperationUseres.isEmpty()){
			UserCooperationUser ucu = cooperationUseres.get(0);
			mu.setLoginChannel(ucu.getCooperation());
		}
		// 账号信息 
		CashAccountVO moneyAccount = cashAccountService.queryMoneyAccountByUserNo(user.getUserNo());
		MobileCopyPropertyUtils.copyUserUser2MobileUser(user, mu,moneyAccount);
		return mu;
	}

	/**
	 * modify V3.1.0 
	 * 获取优惠劵信息.
	 */
	@Override
	public List<MobileUserCoupon> getCoupon(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("state", param);
		//List<MarkCouponUserInfo> list = markCouponService.queryMobileUserCouponInfoByUserId(Long.valueOf(param.get("userId").toString()));
		
		List<MarkCouponUserInfo> list =  new ArrayList<MarkCouponUserInfo>();
		String state = String.valueOf(param.get("state"));
		String userId = param.get("userId").toString();
		List<UserCouponDTO> userCouponList = new ArrayList<UserCouponDTO>();
		// 未使用
		HashMap<String, Object> params = new HashMap<String, Object>();
		if(state.equals(ClientConstants.COUPON_STATE.NOT_USED.name())) {
			params.put("userId", userId);
			params.put("used", "false");
			params.put("applyField", "userCouponList1");
			params.put("_startRow", "1");
			params.put("_endRow", "1000");
			userCouponList = markCouponUserService.getMySpaceUserCouponData(params);
			
		// 已使用 
		} else if(state.equals(ClientConstants.COUPON_STATE.USED.name())) {
			UserUser user = userUserProxy.getUserUserByPk(Long.valueOf(param.get("userId").toString()));
			params.put("userId", null==user ?"0":user.getUserNo());
			params.put("used", "true");
			params.put("objectType", Constant.OBJECT_TYPE.ORD_ORDER.name());//获取用户的优惠券信息
			params.put("_startRow", "1");
			params.put("_endRow","1000");
			List<MarkCouponUsage> markCouponUsageList = favorOrderService.selectByParam(params);
			userCouponList = markCouponUserService.getMySpaceUserCouponData(markCouponUsageList);
		} else {
			userCouponList = new ArrayList<UserCouponDTO>();
		}
		
		// 数据复制 
		if(null != userCouponList && userCouponList.size() > 0) {
			for(int i = 0 ;i < userCouponList.size() ;i++) {
				UserCouponDTO uct = userCouponList.get(i);
				MarkCouponUserInfo mcu = new MarkCouponUserInfo();
				if(null != uct) {
					mcu.setMarkCoupon(uct.getMarkCoupon());
					mcu.setMarkCouponCode(uct.getMarkCouponCode());
					list.add(mcu);
				}
			}
		}
		
		List<MobileUserCoupon> couponList = CouponUtils.filterMobileUserCouponInf(list,String.valueOf(param.get("state")));
		return couponList;
	}

	@Override
	public String subOrder(Map<String, String> param) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * 查询订单.
	 */
	@Override
	public MobileOrder getOrder(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("orderId", param);
		String subProductType = String.valueOf(param.get("subProductType"));
		
		Map<String,Object> map = this.getOrderViewList(Long.valueOf(param.get("orderId").toString()), String.valueOf(param.get("userNo")), 1L,subProductType,0L);
		List<MobileOrder> orderList = (List<MobileOrder>)map.get("datas");
		if ( !orderList.isEmpty() ) {
			// 初始化订单奖金支付信息 . 
			return initBonus(orderList.get(0));
		}
		return null;
	}

	/**
	 * 查询订单列表.
	 */
	@Override
	public Map<String,Object> getOrderList(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("page",param);
		String subProductType  = null;
		if(param.get("subProductType")!=null) {
			subProductType = String.valueOf(param.get("subProductType"));
		}
		Long lvversion = 0L;
		if(param.get("lvversion")!=null){
			lvversion = Long.valueOf(param.get("lvversion").toString());
		}
		Map<String,Object> map = this.getOrderViewList(null,String.valueOf(param.get("userNo")), Long.valueOf(param.get("page").toString()),subProductType,lvversion);
		map.remove("ordOrderList");
		return map;
	}

	/**
	 * 添加收藏. 
	 */
	@Override
	public String addFavorite(Map<String, Object> params) {
		ArgCheckUtils.validataRequiredArgs("objectId","objectType",params);
		// 判断是否收藏. 
		Map<String,Object> p = new HashMap<String,Object> ();
		p.put("objectId", params.get("objectId"));
		p.put("objectType", params.get("objectType"));
		p.put("userId", params.get("userId"));
		List<MobileFavorite> mfList = mobileFavoriteService.queryMobileFavoriteList(p);
		if(null != mfList && mfList.size() > 0) {
			return "success";
		}
		MobileFavorite mf = new MobileFavorite();
		mf.setCreatedTime(new Date());
		mf.setIsValid(String.valueOf(params.get("isValid")));
		mf.setObjectId(Long.valueOf(params.get("objectId")+""));
		mf.setObjectImageUrl(String.valueOf(params.get("objectImageUrl")));
		mf.setObjectName(String.valueOf(params.get("objectName")));
		mf.setObjectType(String.valueOf(params.get("objectType")));
		mf.setUserId(Long.valueOf(params.get("userId")+""));
		mobileFavoriteService.insertMobileFavorite(mf);
		return "success";
		/*return MobileCopyPropertyUtils.copyFavorite2MyFavorite(m,new MobileMyFavorite());*/
	}
	
	/**
	 * 删除收藏. 
	 */
	@Override
	public boolean cancelFavorite(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("id",param);
		int r = mobileFavoriteService.deleteByObjectIdAndUserId(Long.valueOf(param.get("id")+""),Long.valueOf(param.get("userId")+""));
		if(r > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 返回我的收藏列表. 
	 */
	@Override
	public Map<String,Object> getFavoriteList(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("objectTypes" ,param);
		String objectTypes = String.valueOf(null == param.get("objectTypes")?"":param.get("objectTypes"));
		Object page = param.get("page"); // 分页信息. 
		if(StringUtils.isEmpty(objectTypes)) {
			objectTypes = "PLACE"; // 默认景点. 
		}
		param.put("objectType", objectTypes);
		Page p =  initPage(param,10,1);
		// 如果有分页 
		if(null != page ) {
			// 初始化分页信息 . 
			param.put("isPaging", "true"); // 是否使用分页 
			param.put("startRows", p.getStartRows());
			param.put("endRows", p.getEndRows());
			p.setTotalResultSize(mobileFavoriteService.countMobileFavoriteList(param));
		}
		
		Map<String,Object> t_map = new HashMap<String,Object>();
		List<MobileMyFavorite>resultList = new ArrayList<MobileMyFavorite>();
		try {
			List<MobileFavorite> mfList = mobileFavoriteService.queryMobileFavoriteList(param);
			if(null != mfList && mfList.size() > 0) {
				resultList = MobileCopyPropertyUtils.copyFavoriteList2MyFavoriteList(mfList, new ArrayList<MobileMyFavorite>());
				for(MobileMyFavorite mf:resultList) {
					// 景点
					if(CLIENT_FAVORITE_TYPE.PLACE.getCode().equals(mf.getObjectType())) {
						// 标的(城市,景点,酒店)相关搜索
						PlaceSearchInfo psi = placeSearchInfoService.getPlaceSearchInfoByPlaceId(mf.getObjectId());
						if(null != psi) {
							mf.setAddress(psi.getAddress());
							mf.setMarketPriceYuan(PriceUtil.convertToYuan(psi.getMarketPrice()));
							mf.setSellPriceYuan(psi.getProductsPriceInteger().floatValue());
							mf.setAvgScore(psi.getCmtAvgScore());
							// v3.1 
							mf.setSubject(psi.getPlaceFirstSubject());
							// 返现金额 (是分 还是元)
							mf.setMaxCashRefund(StringUtils.isEmpty(psi.getCashRefund()) ?0l:PriceUtil.convertToFen(psi.getCashRefund()));
							// 是否今日可定
							mf.setTodayOrderAble(psi.canOrderTodayCurrentTimeForPlace());
							// 优惠 
							// mf.setHasBusinessCoupon(ClientUtils.hasBusinessCoupon(psi)); 
						}
						// 产品 
					} else if(CLIENT_FAVORITE_TYPE.PRODUCT.getCode().equals(mf.getObjectType())) {
						// 点评信息
						CmtTitleStatisticsVO productCommentStatistics = cmtTitleStatistisService.getCmtTitleStatisticsByProductId(mf.getObjectId());
						if(null != productCommentStatistics) {
							mf.setAvgScore( productCommentStatistics.getAvgScore()); // 点评分数
							mf.setCmtNum(productCommentStatistics.getCommentCount()); // 点评数
						}
						/************* V3.1 ***************/
						ProductSearchInfo psi = productSearchInfoService.queryProductSearchInfoByProductId(mf.getObjectId());
						
						if(null != psi ) {
							mf.setVisitDay(psi.getVisitDay()); // 天数
							mf.setHasBusinessCoupon(ClientUtils.hasBusinessCoupon(psi)); // 优惠
							mf.setSubject(null == psi.getSubProductType()?"":Constant.SUB_PRODUCT_TYPE.getCnName(psi.getSubProductType()));
							mf.setSellPriceYuan(PriceUtil.convertToYuan(psi.getSellPrice()));
							mf.setMarketPriceYuan(PriceUtil.convertToYuan(psi.getMarketPrice()));
							// 返现金额 
							mf.setMaxCashRefund(StringUtils.isEmpty(psi.getCashRefund())?0l:PriceUtil.convertToFen(psi.getCashRefund()));
						}
						
						// 攻略 	
					} else if(CLIENT_FAVORITE_TYPE.GUIDE.getCode().equals(mf.getObjectType())) {
						
					}
				}
			}
			
		}catch(Exception e){
			System.out.print("super_clutter Error==" + param.toString());
			e.printStackTrace();
		}
		
		t_map.put("datas", resultList);
		
		if(null != page) {
			t_map.put("isLastPage", !p.hasNext()); // 是否最后一页 
		}
		return t_map;
	}

	@Override
	public String addContact(Map<String, String> param) {
		ArgCheckUtils.validataRequiredArgs("receiverName", param);

		
		UsrReceivers ur = null;
		String cardType = null;
		if(null != param.get("certType")) {
			cardType = param.get("certType").toString();
		}

		String receiversId = null;
		if(param.get("receiversId")!=null){
			receiversId = param.get("receiversId").toString();
		}
		List<UsrReceivers> saveReceivers = new ArrayList<UsrReceivers>();
		List<UsrReceivers> receiversList = this.receiverUserService.loadUserReceiversByUserId(param.get("userNo").toString());
		Map<String,List<UsrReceivers>> mapReceiver =  new HashMap<String, List<UsrReceivers>>();
		for (UsrReceivers usrReceivers : receiversList) {
			List<UsrReceivers> list = mapReceiver.get(usrReceivers.getReceiverName());
			if(list==null){
				list = new ArrayList<UsrReceivers>();
			}
			list.add(usrReceivers);
			mapReceiver.put(usrReceivers.getReceiverName(), list);

		}
		ur  = new UsrReceivers();
		ur.setReceiverName(param.get("receiverName").toString());
		ur.setUserId(param.get("userNo").toString());
		ur.setMobileNumber(param.get("mobileNumber"));
		ur.setReceiversType(Constant.ORD_PERSON_TYPE.CONTACT.name());
		
		ur.setGender(null==param.get("gender")?"":param.get("gender").toString());
		if(null != param.get("birthday")) {
			ur.setBrithday(DateUtil.toDate(param.get("birthday"), "yyyy-MM-dd"));
		}
		if (param.get("certNo")!=null){
			ur.setCardNum(param.get("certNo").toString());	
			if(cardType!=null){
				ur.setCardType(cardType);
			}
			
		} else if(param.get("certType")!=null&&Constant.CERT_TYPE.ERTONG.name().equals(cardType)){
			ur.setBrithday(DateUtil.toDate(param.get("birthday"), "yyyy-MM-dd"));
		}
		if(receiversId==null){
			if(mapReceiver.get(param.get("receiverName").toString())!=null){
				List<UsrReceivers> list = mapReceiver.get(param.get("receiverName"));
				for (UsrReceivers usrReceivers : list) {
					UsrReceivers tempReceivers = new UsrReceivers();
					BeanUtils.copyProperties(ur, tempReceivers);
					tempReceivers.setReceiverId(usrReceivers.getReceiverId());
					saveReceivers.add(tempReceivers);
				}
				
			} else  {
				ur.setUseOffen("true");
				saveReceivers.add(ur);
			}
		} else {
			ur.setUseOffen("true");
			ur.setReceiverId(receiversId);
			saveReceivers.add(ur);
		}
		
		this.receiverUserService.createContact(saveReceivers, param.get("userNo").toString());
		return null;
	}

	/**
	 * 获取订单列表.
	 * @param orderId  订单id
	 * @param userId   用户id
	 * @param page
	 * @return
	 */
	protected Map<String, Object> getOrderViewList(Long orderId, String userId,Long page,final String subProductType,Long lvversion) {
		// 综合查询 
		CompositeQuery compositeQuery = new CompositeQuery();
		OrderIdentity orderIdentity = new OrderIdentity();
		if(userId!=null && !"null".equals(userId)){
			orderIdentity.setUserId(userId);
		}
		if (orderId != null) {
			orderIdentity.setOrderId(orderId);
		}
		OrderContent content = compositeQuery.getContent();
		if(StringUtil.isNotEmptyString(subProductType) && !"null".equals(subProductType)){
			content.setSubProductType(subProductType);
		}
		
		compositeQuery.setOrderIdentity(orderIdentity);
		
		// 查询记录总数
		Long totalRecords = orderServiceProxy.compositeQueryOrdOrderCount(compositeQuery);

		// 分页相关 
		Page<MobileOrder> pageConfig = Page.page(totalRecords, 5L, page);
		PageIndex pageIndex = new PageIndex();
		pageIndex.setBeginIndex(new Integer("" + pageConfig.getStartRows()));
		pageIndex.setEndIndex(new Integer("" + pageConfig.getEndRows()));
		compositeQuery.setPageIndex(pageIndex);
		
		// 订单列表
		List<OrdOrder> ordersList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
		List<MobileOrder> vcoList = new ArrayList<MobileOrder>();
		if (ordersList != null && !ordersList.isEmpty()) {
			for (OrdOrder ordOrder : ordersList) {
				if (ordOrder == null || null == ordOrder.getMainProduct()) {
					continue;
					
				} 
				// 初始化订单
				MobileOrder mo  = getMobileOrder(ordOrder);
				vcoList.add(mo);
			}
		}
		pageConfig.setItems(vcoList);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("datas", vcoList);
		resultMap.put("ordOrderList", ordersList);
		resultMap.put("isLastPage", this.isLastPage(pageConfig));
		return resultMap;
	}

	/**
	 * 获取订单列表.
	 * @param orderId  订单id
	 * @param userId   用户id
	 * @param page
	 * @return
	 */
	protected MobileOrder getMobileOrderByOrderId(Long orderId,String userId) {
		// 查询记录总数
		OrdOrder orderOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if (orderOrder == null || !userId.equalsIgnoreCase(orderOrder.getUserId())) {
			throw new LogicException("订单不存在");
		}
		MobileOrder mo = getMobileOrder(orderOrder);
		
		mo.setCanToPay(orderOrder.isCanToPay());
		
		try {
			//复制游玩人座位号
			initPersonsSeat(orderOrder,mo);
			
			// 判断现金支付是否临时关闭  true是，false：否 
			this.tempCloseCashAccountPay(orderOrder,mo);
			
			// 初始化订单操作标签
			this.initOptButton(orderOrder,mo);
			// 初始化奖金账号相关 
			this.initMoneyAccount(orderOrder,mo);
			
			/*// 初始化出发时间 
			if(null != orderOrder.getMainProduct()) {
				initDepartureTime(mo,orderOrder.getMainProduct().getProdBranchId());
			}*/
		}catch(Exception e) {
			e.printStackTrace();
		}
		 return mo;
	}
	
	// 初始化奖金相关部分 
	
	private void initMoneyAccount(OrdOrder orderOrder, MobileOrder mo) {
		if(null == orderOrder || null == mo) {
			return ;
		}
		//actualPay
		
		mo.setActualPay(orderOrder.getActualPayYuan()); // 实付金额 。单位元
		UserUser user = userUserProxy.getUserUserByUserNo(orderOrder.getUserId());
		if(null == user) {
			log.info("...client....订单idorderOrder.orderId=="+orderOrder);
			return;
		}
		mo.setMobileCanChecked(!this.mobileChecked(user));// 是否绑定； true：未绑定； false：已绑定
		CashAccountVO moneyAccount = cashAccountService.queryMoneyAccountByUserId(user.getId());
		if(null == moneyAccount) {
			log.info("....client..userid="+user.getId()+"=现金账号不存在.=");
			return;
		}
		mo.setCashAccountValid(!"Y".equals(moneyAccount.getValid()));// 是否冻结 ； true:是；false：否 
		mo.setMaxPayMoney(moneyAccount.getMaxPayMoneyYuan());// 可用金额 ，可以支付金额 
	}
	
	/**
	 * 手机号是否绑定 . true 已绑定；false:为绑定 。 
	 * @return
	 */
	private boolean mobileChecked(UserUser user) {
		if(null != user && StringUtils.isNotBlank(user.getMobileNumber())) {
			if("Y".equals(user.getIsMobileChecked())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 初始化操作按钮  V5.0.0
	 * @param orderOrder
	 * @param mo
	 */
	private void initOptButton(OrdOrder orderOrder, MobileOrder mo) {
		if(null == orderOrder || null == mo) {
			return ;
		}
		// 行程单 <#if !obj.isCanceled() && "ROUTE"==obj.mainProduct.productType && obj.isNeedEContract() && obj.isEContractConfirmed() >
		if(!orderOrder.isCanceled() && "ROUTE".equals(orderOrder.getMainProduct().getProductType()) && orderOrder.isNeedEContract() && orderOrder.isEContractConfirmed()) {
			mo.setHasDownloadTravel(true);
		}
		
		// 电子合同  <#if obj.isNeedEContract() && obj.isEContractConfirmed() && !obj.isCanceled()>
		/*if(orderOrder.isNeedEContract() && orderOrder.isEContractConfirmed() && !orderOrder.isCanceled()) {
			mo.setHasPdfContract(true);
		}*/
		//<#if obj.mainProduct.productType?if_exists=="ROUTE"&& obj.paymentStatus?default("")=="PAYED">
	     /*<#if obj.orderRoute.groupWordStatus?if_exists=="SENT_NO_NOTICE"||obj.orderRoute.groupWordStatus?if_exists=="MODIFY_NO_NOTICE"||obj.orderRoute.groupWordStatus?default("")=="SENT_NOTICE" || obj.orderRoute.groupWordStatus?default("")=="MODIFY_NOTICE">
      	<a href="${base}/groupAdviceNoteDownload/order.do?objectId=${obj.orderId?if_exists}&objectType=ORD_ORDER">下载出团通知书</a>
       </#if>
 </#if>*/
		// 出团通知书
		if( "ROUTE".equals(orderOrder.getMainProduct().getProductType()) && "PAYED".equals(orderOrder.getPaymentStatus())) {
			if(null != orderOrder.getOrderRoute() && ("SENT_NO_NOTICE".equals(orderOrder.getOrderRoute().getGroupWordStatus())
					|| "MODIFY_NO_NOTICE".equals(orderOrder.getOrderRoute().getGroupWordStatus()) 
					|| "SENT_NOTICE".equals(orderOrder.getOrderRoute().getGroupWordStatus()) 
					|| "MODIFY_NOTICE".equals(orderOrder.getOrderRoute().getGroupWordStatus())  )) {
				
				mo.setHasGroupAdviceNote(true);
			}
		}
		
	}

	/**
	 * 奖金支付是否临时关闭 
	 * @param order
	 * @return
	 */
	public void tempCloseCashAccountPay(OrdOrder order,MobileOrder mo) {
		if(null == order) {
			return ;
		}
		//由于驴妈妈账户被盗严重  对于广州长隆供应商的产品临时关闭存款账户支付功能(485=广州长隆供应商ID)
		List<OrdOrderItemMeta> ordOrderItemMetaList=order.getAllOrdOrderItemMetas();
		if(ordOrderItemMetaList!=null && ordOrderItemMetaList.size()>0){
			for (OrdOrderItemMeta ordOrderItemMeta : ordOrderItemMetaList) {
				if(ordOrderItemMeta.getSupplierId()!=null && 
						(485==ordOrderItemMeta.getSupplierId().longValue()
						||2261==ordOrderItemMeta.getSupplierId().longValue()
						||4462==ordOrderItemMeta.getSupplierId().longValue()
						||4367==ordOrderItemMeta.getSupplierId().longValue()
						||1340==ordOrderItemMeta.getSupplierId().longValue()
						||6134==ordOrderItemMeta.getSupplierId().longValue()
						)){
					mo.setTempCloseCashAccountPay(true); // 关闭
				}
			}
		}
	}
	
	/**
	 * 初始火车票订单出发时间 
	 * @param mo
	 * @param branchId
	 */
	public void initDepartureTime(MobileOrder mo,Long branchId) {
		LineStationStation lss = getLineStationStationByBranchId(branchId);
		if(null != lss) {
			mo.setDepartureTime(lss.getDepartureTime());
			mo.setProductName(mo.getDepartureStationName()+"-" +mo.getArrivalStationName() + lss.getLineName());
		}
	}
	
	/**
	 * 根据branchId获取LineStationStation 
	 * @param branchId
	 * @return
	 */
	public LineStationStation  getLineStationStationByBranchId(Long branchId) {
		ProdProductBranch ppb = prodProductBranchService.selectProdProductBranchByPK(branchId);
		if(null != ppb && null != ppb.getStationStationId()) {
			return  prodTrainService.getStationStationById(ppb.getStationStationId());
		}
		return null;
		
	}
	
	
	/**
	 * 格式化order信息
	 * @param ordOrder 元数据项
	 * @param vcoList  目的列表  
	 */
	protected MobileOrder getMobileOrder(OrdOrder ordOrder) {
		// 市场价格
		Long marketPrice = new Long(0);
		for (Iterator<OrdOrderItemProd> it = ordOrder.getOrdOrderItemProds().iterator(); it.hasNext();) {
			OrdOrderItemProd prod = it.next();
			if(prod == null){
				continue;
			}
			if(null !=prod.getMarketPrice() && null != prod.getQuantity() ) {
				marketPrice += prod.getMarketPrice() * prod.getQuantity();
			}
		}

		MobileOrder vco = new MobileOrder();
		// 节省额
		if (ordOrder.getOughtPay() != null) {
			vco.setJieshen(Long.valueOf((marketPrice - ordOrder.getOughtPay()) / 100)+ "");
		}

		vco.setOrderId(ordOrder.getOrderId());
		if(ordOrder.getOrderViewStatus()!=null){
			/**
			 * 如果需要签约未签约 
			 */
			if(ordOrder.isNormal()&&ordOrder.isNeedEContract()&&!ordOrder.isEContractConfirmed()){
				ordOrder.setOrderViewStatus(Constant.ORDER_VIEW_STATUS.UNSIGNED.name());
			} else if(ordOrder.isCanceled()){
				ordOrder.setOrderViewStatus(ordOrder.getOrderStatus());
			}
		} else {
			if(ordOrder.isPayToLvmama()){
				if(ordOrder.isNeedEContract()&&!ordOrder.isEContractConfirmed()){
					ordOrder.setOrderViewStatus(Constant.ORDER_VIEW_STATUS.UNSIGNED.name());
				} else if(ordOrder.isCanceled()){
					ordOrder.setOrderViewStatus(ordOrder.getOrderStatus());
				} else {
					ordOrder.setOrderViewStatus(ordOrder.getPaymentStatus());
				}
			} else {
				ordOrder.setOrderViewStatus(ordOrder.getApproveStatus());
			}
			
		}
		
		vco.setDescUrl("/html5/index.htm?tag=1&productId="+ordOrder.getMainProduct().getProductId());
		vco.setProductName(ordOrder.getMainProduct().getProductName());
		vco.setAmount(String.valueOf(ordOrder.getOughtPayYuan()));
		vco.setNeedResourceConfirm(ordOrder.isNeedResourceConfirm());
		vco.setAlipayAppUrl(Constant.getInstance().getAliPayAppUrl());
		vco.setAlipayWapUrl(Constant.getInstance().getAliPayWapUrl());
		vco.setUpompPayUrl(Constant.getInstance().getUpompPayUrl());
		vco.setResourceConfirmStatus(ordOrder.getResourceConfirmStatus());
		vco.setCanCancel(ordOrder.isCancelAble()&&!ordOrder.isCanceled()&&!ordOrder.isPaymentSucc());
		if(ordOrder.hasTodayOrder()||ordOrder.isForbid()||ordOrder.isManual()||ordOrder.isPartPay()){
			vco.setCanCancel(false);
		} 
		
		vco.setMainProductType(ordOrder.getMainProduct().getProductType());
		vco.setMainSubProductType(ordOrder.getMainProduct().getSubProductType());
		vco.setPhysical(Boolean.valueOf(ordOrder.getPhysical()));
		vco.setNeedEContract(ordOrder.isNeedEContract());// 是否需要电子签约 
		vco.setEContractConfirmed(ordOrder.isEContractConfirmed()); // 电子签约是否已被确认
		vco.setManual(ordOrder.isManual());//人工退改
		vco.setForbid(ordOrder.isForbid());//不退不改
		vco.setPaymentChannel(ordOrder.getPaymentChannel());//支付渠道
		
	    // 优惠总金额: 积分抵扣 + 多顶多惠 + 早定早恵 + 优惠活动的优惠券 + 普通金额优惠券
		vco.setCouponUsageAmount(sumYouhuiAmount(ordOrder.getOrderId()));  
		
		if(!ordOrder.isEContractConfirmed()&&ordOrder.isNeedEContract()){
			vco.setGotoSign(true);
		}
		
		/**
		 * 对于客户端支付给驴妈妈的就直接支付，即使是资源需要确定的
		 */
	
		 vco.setCanToPay(ordOrder.mobileCanToPay());
		 /**
			 * 如果实付为0 那么不能支付
			 */
		if(ordOrder.getOughtPay()==0L){
				vco.setCanToPay(false);
		}
		
		// 设置图片
		if(null != ordOrder.getMainProduct() && null != ordOrder.getMainProduct().getProductId()) {
			ProdProduct pp = prodProductService.getProdProductById(ordOrder.getMainProduct().getProductId());
			if(null != pp) {
				vco.setImgUrl(pp.getSmallImage());
			}
		}
		 if (Constant.PRODUCT_TYPE.HOTEL.name().equals( // 酒店
				ordOrder.calcOrderType())) {
			if (!Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT.equals(ordOrder.getMainProduct().getSubProductType())) {
				// 如果visitime为空 
				try {
					vco.setVisitTime(ordOrder.getMainProduct().getDateRange());
				}catch(Exception e){
					e.printStackTrace();
					vco.setVisitTime("");
				}
				vco.setQuantity(ordOrder.getMainProduct().getQuantity().toString());
			} else {
				vco.setVisitTime(DateUtil.formatDate(ordOrder.getVisitTime(), "yyyy-MM-dd"));
				vco.setQuantity(ordOrder.getMainProduct().getHotelQuantity());
			}
		} else  {
			vco.setVisitTime(DateUtil.formatDate(ordOrder.getVisitTime(), "yyyy-MM-dd"));
			vco.setQuantity(ordOrder.getMainProduct().getQuantity().toString());
		}

		// 酒店 入住时间 和 离开时间 
		if (ordOrder.isHotel() && null != ordOrder.getMainProduct()) {
			try {
				if(StringUtils.isEmpty(ordOrder.getMainProduct().getDateRange())) {
					String[] range = ordOrder.getMainProduct().getDateRange().split("至");
					if (range.length > 1) {
						vco.setVisitTime(range[0]);
						vco.setLeaveTime(range[1]);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			vco.setQuantity(ordOrder.getMainProduct().getHotelQuantity());
		}
		
		// 附加产品 
		List<MobileOrderItem> orderItemProdList = new ArrayList<MobileOrderItem>();
		List<OrdOrderItemProd> ordOrderItemProdList = ordOrder.getOrdOrderItemProds();
		for (OrdOrderItemProd ordOrderItemProd : ordOrderItemProdList) {
			if(ordOrderItemProd==null){
				continue;
			}
			MobileOrderItem moi = new MobileOrderItem();
			moi.setAmount((long)ordOrderItemProd.getAmountYuan());
			moi.setFamount(ordOrderItemProd.getAmountYuan());
			moi.setProductName(ordOrderItemProd.getProductName());
			moi.setQuantity(ordOrderItemProd.getQuantity());
			moi.setAdditional(ordOrderItemProd.isAdditionalProduct());
			moi.setShortName(ordOrderItemProd.getShortName());
			moi.setPrice(ordOrderItemProd.getPriceYuan());
			orderItemProdList.add(moi);

			
		}
		vco.setOrderItem(orderItemProdList);
		vco.setCreatedTime(DateUtil.formatDate(ordOrder.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
		
		// 人员信息 
		List<MobilePersonItem> vopList = new ArrayList<MobilePersonItem>();
		for (OrdPerson op : ordOrder.getPersonList()) {
			MobilePersonItem vop = new MobilePersonItem();
		    // 如果是门票 ，过滤掉游玩人 
			/*if(null != ordOrder.getMainProduct() && 
					Constant.PRODUCT_TYPE.TICKET.name().equals(ordOrder.getMainProduct().getProductType())
					&& Constant.ORD_PERSON_TYPE.TRAVELLER.name().equals(op.getPersonType())) {
				continue;
			}*/
			if (!StringUtil.isEmptyString(op.getCertNo()) && Constant.CERT_TYPE.ID_CARD.name().equals(op.getCertType())) {
				if(op.getCertNo()!=null){
					if (op.getCertNo().length() == 18) {
						vop.setCertNo("**************" + op.getCertNo().substring(op.getCertNo().length() - 4,op.getCertNo().length()));
					} else if (op.getCertNo().length() == 15) {
						vop.setCertNo("***********" + op.getCertNo().substring(op.getCertNo().length() - 4,op.getCertNo().length()));
					}
				}
				
			} else {
				vop.setCertNo(op.getCertNo());
			}
			// 护照
			if (!StringUtil.isEmptyString(op.getCertNo()) && Constant.CERT_TYPE.HUZHAO.name().equals(op.getCertType())) {
				if(op.getCertNo()!=null){
					vop.setCertNo(op.getCertNo());
					vop.setGender(null == op.getGender()?"":op.getGender());
				}
			}
			vop.setPersonMobile(op.getMobile());
			vop.setPersonName(op.getName());
			vop.setPersonType(op.getPersonType());
			vop.setCertType(null == op.getCertType()?"":op.getCertType());
			
			//v3.1 联系人， 重发短信凭证用到
			if(Constant.ORD_PERSON_TYPE.CONTACT.getCode().equals(op.getPersonType())) {
				vco.setContactPersonMobile(op.getMobile());
			}
			
			vopList.add(vop);
		}

		// 当日定相关 
		if ("true".equals(ordOrder.getTodayOrder())) {
			vco.setLatestPassTime(DateUtil.formatDate( ordOrder.getLatestUseTime(), "yyyy-MM-dd HH:mm"));
			vco.setEarliestPassTime(DateUtil.formatDate(ordOrder.getVisitTime(), "yyyy-MM-dd HH:mm"));
			// 今日预订 精确到时分秒
			vco.setVisitTime(DateUtil.formatDate(ordOrder.getVisitTime(),"yyyy-MM-dd"));
		}
		vco.setZhOrderViewState(ordOrder.getZhOrderViewStatus());
		vco.setOrderViewStatus(ordOrder.getOrderViewStatus());
		// 支付中文名称 
		if(PAYMENT_TARGET.TOLVMAMA.getCode().equals(ordOrder.getPaymentTarget())) {
			vco.setZhPaymentTarget(ClutterConstant.MOBILE_PAY_TARGET_LVMM);
		} else if(PAYMENT_TARGET.TOSUPPLIER.getCode().equals(ordOrder.getPaymentTarget())) {
			vco.setZhPaymentTarget(ClutterConstant.MOBILE_PAY_TARGET_SUPPLIER);
		} else {
			vco.setZhPaymentTarget(ordOrder.getPaymentTarget());
		}
		vco.setTodayOrder(Boolean.valueOf(ordOrder.getTodayOrder()));
		vco.setUserMemo(ordOrder.getUserMemo());
		vco.setListPerson(vopList);
		
		// v3.1 是否可重发验证码，不包括非定期订单 
		vco.setCanSendCert(ordOrder.isShouldSendCert() && com.lvmama.clutter.utils.DateUtil.afterCurrentDate(ordOrder.getVisitTime(),1));
		vco.setProductId(ordOrder.getMainProduct().getProductId());
		// 如果是门票
		ProdCProduct prodc = pageService.getProdCProduct(ordOrder.getMainProduct().getProductId());
		
		
		if(Constant.PRODUCT_TYPE.TICKET.getCode().equals(ordOrder.getMainProduct().getProductType())) {
			// 根据产品id找到placeId 
			
			if(null != prodc && null != prodc.getTo()) {
				Long placeId = prodc.getTo().getPlaceId();
				vco.setPlaceId(placeId);
				this.setLocation2MobileOrder(vco, placeId);
			}
		}
		
		
		ProductSearchInfo psi = productSearchInfoService.queryProductSearchInfoByProductId(ordOrder.getMainProduct().getProductId());
		ProdProductBranch ppb = prodProductBranchService.selectProdProductBranchByPK(ordOrder.getMainProduct().getProdBranchId());
		if(null != prodc) {
			vco.setShareContent(prodc.getProdProduct(), prodc.getTo(), ppb);
		}
		
		if(psi!=null && !StringUtil.isEmptyString(psi.getChannelGroup()) ){
			vco.setGroupProduct(true);
		}
		// 是否支付给驴妈妈
		vco.setPayTarget(ordOrder.getPaymentTarget());
		//vcoList.add(vco);
		// 订单类别
	    vco.setOrderType(ordOrder.getOrderType());
	    
	    // v4.1.0火车票 增加出发站和终点站 
	    getTrainInfos(ordOrder,vco);
	    
	    this.setSumsangProperty(vco, prodc);
	    
		return vco;
	}
	
	/**
	 * 火车票信息 - 出发站和到达站
	 * @param ordOrder
	 * @param vco
	 */
	private void getTrainInfos(OrdOrder ordOrder,MobileOrder vco) {
		if(null != ordOrder && null != vco ) {
			if (Constant.SUB_PRODUCT_TYPE.TRAIN.name().equals(ordOrder.getOrderType()) 
		    		&& Constant.SUB_PRODUCT_TYPE.TRAIN.name().equals(ordOrder.getMainProduct().getSubProductType())) {
				try {
					// 出发站和到达站
					getStation(vco.getProductName(),vco);
					// 初始化出发时间 
					if(null != ordOrder.getMainProduct()) {
						initDepartureTime(vco,ordOrder.getMainProduct().getProdBranchId());
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
		    }
		}
	}
	
	
	/**
	 * 设置用户座位号 
	 * @param vco
	 */
	private void initPersonsSeat(OrdOrder ordOrder,MobileOrder vco) {
		if(null != ordOrder && Constant.SUB_PRODUCT_TYPE.TRAIN.name().equals(ordOrder.getOrderType())) {
			List<OrdOrderTraffic> list = ordOrder.getOrderTrafficList();
			if(null != list && list.size() > 0){
				Map<String,Object> personMap = new HashMap<String,Object>();
				for(OrdOrderTraffic otf:list){
					List<OrdOrderTrafficTicketInfo> ordInfoList = otf.getOrderTrafficTicketInfoList();
					if(null != ordInfoList && ordInfoList.size() >0) {
						for(OrdOrderTrafficTicketInfo oot :ordInfoList) {
							personMap.put(oot.getPerson().getName(), oot.getSeatNo());
						}
					}
				}
				
				List<MobilePersonItem> personItemList = vco.getListPerson();
				if(null != personItemList && personItemList.size() > 0) {
					for(MobilePersonItem mpi:personItemList) {
						mpi.setSeatName(null==personMap.get(mpi.getPersonName())?"":personMap.get(mpi.getPersonName()).toString());
					}
				}
			}
			
		}
	}
	
	// 截取车站名称： 广州南-西安北G832(衡阳东-洛阳龙门 一等座 成人票)
	public void getStation(String productName,MobileOrder vco) {
		if(!StringUtils.isEmpty(productName)) {
			String  t_pname = productName;
			String regStr = "\\((.*?)\\)";  
	        Pattern pattern = Pattern.compile(regStr);  
	        Matcher matcher = pattern.matcher(productName);  
	        while(matcher.find()){  
	        	productName = matcher.group(1);
	        	break;
	        }  
	        String[] name = productName.split(" ");
	        if(null != name && name.length > 0) {
	        	if(name.length > 1) {
	        		 vco.setSeatType(name[1]); // 设置座位名称， 一等座等 
	        	}
	        	String[] stat = name[0].split("-");
	        	  if(null != stat && stat.length ==2) {
	        		  vco.setDepartureStationName(stat[0]);
					  vco.setArrivalStationName(stat[1]);
	        	  }
	        }
	        
	        
	        // 去掉括号内容 
	        int i = t_pname.indexOf("(");
			if(i != -1) {
				String t_prodName = t_pname.substring(0,i);
				vco.setProductName(t_prodName);
			}
		}
		
		
		
	}
	/**
	 * 初始化订单奖金信息 . 
	 */
	protected MobileOrder initBonus(MobileOrder mobileOrder) {
		if(null == mobileOrder) {
			return mobileOrder;
		}
		try {
			//失败 {"error_response":{"code":"ORDER_CANT_NOT_BE_FOUND","msg":"无法找到订单"}}
			//成功{"success_response":{"bonusBalance":"1000","bonusPaidAmount":"0","bonus":"960","actBonus":"0"}}
			String jsons = HttpsUtil.requestGet(ClutterConstant.getSuperFrontUrl()+"/bonusAppPay/bonusPayInfo.do?orderId="+mobileOrder.getOrderId()); 
			JSONObject jo = JSONUtil.getObject(jsons);
			Object obj = jo.get("success_response"); // 如果成功。 
			if(null != obj) {
				String bonusJson = obj.toString();
				JSONObject jo2 = JSONUtil.getObject(bonusJson);
				mobileOrder.setBonusBalance(Long.valueOf(jo2.get("bonusBalance").toString()));
				mobileOrder.setBonusPaidAmount(Long.valueOf(jo2.get("bonusPaidAmount").toString()));
				mobileOrder.setActBonus(Long.valueOf(jo2.get("actBonus").toString()));
				mobileOrder.setBonus(Long.valueOf(jo2.get("bonus").toString()));
			}
		}catch(Exception e){
			
		}
		return mobileOrder;
		
	}
	
	

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}

	@Override
	public String removeContact(Map<String, String> param) {
		ArgCheckUtils.validataRequiredArgs("receiverId", param);
		receiverUserService.delete(param.get("receiverId"));
		return null;
	}
	
	@Override
	public List<MobileReceiver> getContact(Map<String,Object> param) {
		List<UsrReceivers>   list = receiverUserService.loadReceiversByPageConfig(0L, 100, param.get("userNo").toString(),Constant.RECEIVERS_TYPE.CONTACT.name());
		List<MobileReceiver> receiverList = new ArrayList<MobileReceiver>();
		for (UsrReceivers usrReceivers : list) {
			MobileReceiver mr = new MobileReceiver();
			// 过滤掉儿童
			if(Constant.CERT_TYPE.ERTONG.name().equals(usrReceivers.getCardType())) {
				continue;
			}
			//
			mr.setReceiverName(usrReceivers.getReceiverName());
			mr.setGender(usrReceivers.getGender());
			mr.setMobileNumber(usrReceivers.getMobileNumber());
			mr.setReceiverId(usrReceivers.getReceiverId());
			mr.setCertNo(usrReceivers.getCardNum());
			mr.setCertType(usrReceivers.getCardType());
			mr.setBirthday(usrReceivers.getZhBrithday());
			/*if(Constant.CERT_TYPE.ERTONG.name().equals(usrReceivers.getCardType())){
				mr.setCertNo(DateUtil.formatDate(usrReceivers.getBrithday(), "yyyy-MM-dd"));
			}*/
			receiverList.add(mr);
		}
		return receiverList;
	}

	
	public void setMobileFavoriteService(MobileFavoriteService mobileFavoriteService) {
		this.mobileFavoriteService = mobileFavoriteService;
	}

	/**
	 * 获取待点评的订单 . 
	 */
	@Override
	public List<MobileOrderCmt> queryCommentWaitForOrder(Map<String, Object> param) {
		// 获取可点评的订单产品信息
		List<OrderAndComment> canCommentOrderProductList = orderServiceProxy.selectCanCommentOrderProductByUserNo(param.get("userNo").toString());
		
		List<MobileOrderCmt> mocList = new ArrayList<MobileOrderCmt>();
		for (OrderAndComment orderAndComment : canCommentOrderProductList) {
			MobileOrderCmt coc = new MobileOrderCmt();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("orderId", orderAndComment.getOrderId());
			parameters.put("productId", orderAndComment.getProductId());
			parameters.put("isHide", "displayall");
			// 查询点评列表.
			List<CommonCmtCommentVO> cmtCommentList = cmtCommentService.getCmtCommentList(parameters);
			if (cmtCommentList == null || cmtCommentList.size() == 0) {
				coc.setCashRefund(orderAndComment.getCashRefund() + "");
				coc.setOrderId(Long.valueOf(orderAndComment.getOrderId()));
				coc.setProductName(orderAndComment.getProductName());
				coc.setCreateTime(DateUtils.formatDate(orderAndComment.getOrderCreateTime(), "yyyy-MM-dd HH:mm:ss"));
				 // 总金额
				if(null != orderAndComment.getOrderId()) {
					 OrdOrder order =  orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderAndComment.getOrderId()));
					 if(null != order) {
						 coc.setChannel(order.getChannel());
						 coc.setAuditStatu(Constant.CMT_AUDIT_STATUS.AUDIT_GOING.name());// 待审核
						 coc.setTotalPrice(order.getOrderPayFloat());
						 coc.setVisitTime(com.lvmama.clutter.utils.DateUtil.dateFormat(order.getVisitTime(),"yyyy-MM-dd"));
						 // 图片 . 
						 if(null != order.getMainProduct() ) {
							 ProdProduct pp = prodProductService.getProdProductById(order.getMainProduct().getProductId());
							 if(null != pp) {
								 coc.setImgUrl(pp.getSmallImage());
								 coc.setProductType(pp.getProductType());
								 coc.setProductId(pp.getProductId());
								 // 如果是景点
							      if(Constant.PRODUCT_TYPE.TICKET.name().equals(pp.getProductType())) {
							    	 Place p =  prodProductPlaceService.getToDestByProductId(pp.getProductId());
							    	 if(null != p ) {
							    		 coc.setPlaceId(p.getPlaceId());
							    	 }
							      }
							 }
						 }
						 
					 }
					 
					 // 是否3.1.0以前版本所下的订单，如果是则不在前台显示返现信息 . 
//					 List<OrderChannelInfo> ociList = ordOrderChannelService.queryOrderByOrderId(order.getOrderId());
//					 if(null != ociList && ociList.size() > 0) {
//						 OrderChannelInfo oci = ociList.get(0);
//						 coc.setNewOrder("true".equalsIgnoreCase(oci.getArg1()));
//					 }
					 /**
					  * 是否3.1.0以前版本所下的订单，如果是则不在前台显示返现信息 . 
					  */
					 List<MobilePersistanceLog> list = mobileClientService.selectListbyPersistanceobjectId(order.getOrderId(), Constant.MOBILE_PERSISTENCE_LOG_OBJECT_TYPE.ORDER.name());
					 if(list!=null&&!list.isEmpty()){
						 MobilePersistanceLog mpl = list.get(0);
						 if(mpl.getLvVersion()>=310||mpl.isTouch()){
							 coc.setNewOrder(true);
						 }
					 } else {
						 coc.setNewOrder(false);
					 }
				 }
				
				// 判读是否团购产品 
				ProductSearchInfo psi = productSearchInfoService.queryProductSearchInfoByProductId(orderAndComment.getProductId());
				if(psi!=null && !StringUtil.isEmptyString(psi.getChannelGroup()) ){
					coc.setGroupProduct(true);
				}
				
				
				mocList.add(coc);
			}
		}
		return mocList;
	}

	/**
	 * 已点评订单  
	 */
	@Override
	public Map<String,Object> queryCommentForOrder(Map<String, Object> param) {
		if(null == param.get("page")) {
			param.put("page", "1");
		}
		Map<String,Object> paramters = this.defaultParamters(Long.valueOf(param.get("page").toString()));
		UserUser user = userUserProxy.getUserUserByUserNo(param.get("userNo").toString());
		String[] isAudits={com.lvmama.comm.vo.Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name(),com.lvmama.comm.vo.Constant.CMT_AUDIT_STATUS.AUDIT_GOING.name(),com.lvmama.comm.vo.Constant.CMT_AUDIT_STATUS.AUDIT_FAILED.name()};
		paramters.put("isAudits", isAudits);

		paramters.put("cmtType", Constant.EXPERIENCE_COMMENT_TYPE); // 体验点评 
		paramters.put("userId", user.getId());
		paramters.put("createTime321", "true");
		Page<CommonCmtCommentVO> pageConfig = 	this.cmtCommentService.queryCmtCommentListForApp(paramters);
		List<MobileOrderCmt> cmtList = new ArrayList<MobileOrderCmt> ();

		for (CommonCmtCommentVO cmtCommentVO : pageConfig.getItems()) {
			MobileOrderCmt moc   = new MobileOrderCmt();
			CmtLatitudeVO cmtLatitudeVO = cmtCommentVO.getSumaryLatitude();
			if(null!=cmtLatitudeVO){
				moc.setScore(cmtLatitudeVO.getScore());
			}
			moc.setAvgScore(cmtCommentVO.getAvgScore());
			moc.setCmtContent(cmtCommentVO.getContent());//点评内容
			moc.setCmtPictureList(cmtCommentVO.getCmtPictureList());//点评图片
			moc.setCreateTime(DateFormatUtils.format(cmtCommentVO.getCreatedTime(), "yyyy-MM-dd"));//点评时间
			moc.setCashRefund((cmtCommentVO.getCashRefund()==null||0 == cmtCommentVO.getCashRefund())?"":cmtCommentVO.getCashRefund()+"");
			moc.setPoint(cmtCommentVO.getPoint());
			moc.setAuditStatu(cmtCommentVO.getIsAudit());
			// 体验点评 
			if(cmtCommentVO.getCmtType().equals(Constant.EXPERIENCE_COMMENT_TYPE)){
			     ProdProduct product = prodProductService.getProdProductById(cmtCommentVO.getProductId());
			     if(product != null){
			    	  moc.setProductId(product.getProductId());
				      moc.setProductType(product.getProductType());
				      // 如果是景点
				      if(Constant.PRODUCT_TYPE.TICKET.name().equals(product.getProductType())) {
				    	  Place place = placeService.queryPlaceByPlaceId(cmtCommentVO.getPlaceId());
				    	  if(null != place) {
				    		  moc.setPlaceId(place.getPlaceId());
				    	  }
				      } 
				      ProductCmtCommentVO productCmtCommentVO = CommentUtil.composeProductComment(cmtCommentVO, product, null);
				      moc.setImgUrl(productCmtCommentVO.getSmallImage());
				      moc.setProductName(productCmtCommentVO.getProductName());
			     } else {
			    	 if(null != cmtCommentVO.getPlaceId()) {
			    		 moc.setPlaceId(cmtCommentVO.getPlaceId());
			    		 moc.setProductType(Constant.PRODUCT_TYPE.TICKET.name());
			    		 Place place = placeService.queryPlaceByPlaceId(cmtCommentVO.getPlaceId());
				    	 if(null != place) {
				    		 moc.setProductName(place.getName());
				    	 }
			    	 } 
                     if(null != cmtCommentVO.getProductId()) {
			    		 moc.setProductId(cmtCommentVO.getProductId());
			    	 }

			     }
			 } else { // 普通点评 .
			     Place place = placeService.queryPlaceByPlaceId(cmtCommentVO.getPlaceId());
			     PlaceCmtCommentVO placeCmtCommentVO = CommentUtil.composePlaceComment(cmtCommentVO, place);
			     if(placeCmtCommentVO != null)  {
			    	 moc.setProductName(placeCmtCommentVO.getPlaceName());
			    	 moc.setImgUrl(place.getSmallImage());
			     }
			    
			 }
			 moc.setOrderId(cmtCommentVO.getOrderId());
			 
			 // 游玩时间
			 if(null != cmtCommentVO.getOrderId()) {
				 OrdOrder order =  orderServiceProxy.queryOrdOrderByOrderId(cmtCommentVO.getOrderId());
				 if(null != order) {
					 moc.setVisitTime(com.lvmama.clutter.utils.DateUtil.dateFormat(order.getVisitTime(),"yyyy-MM-dd"));
					 moc.setTotalPrice(order.getOrderPayFloat());
					 moc.setChannel(order.getChannel());
				 }
			 }
			 
			// 判读是否团头产品 
			ProductSearchInfo psi = productSearchInfoService.queryProductSearchInfoByProductId(cmtCommentVO.getProductId());
			if(psi!=null && !StringUtil.isEmptyString(psi.getChannelGroup()) ){
				moc.setGroupProduct(true);
			}
			
			// 是否3.1.0以前版本所下的订单，如果是则不在前台显示返现信息 . 
//			List<OrderChannelInfo> ociList = ordOrderChannelService.queryOrderByOrderId(cmtCommentVO.getOrderId());
//			if(null != ociList && ociList.size() > 0) {
//				OrderChannelInfo oci = ociList.get(0);
//				moc.setNewOrder("true".equalsIgnoreCase(oci.getArg1()));
//			}
			 List<MobilePersistanceLog> list = mobileClientService.selectListbyPersistanceobjectId(cmtCommentVO.getOrderId(), Constant.MOBILE_PERSISTENCE_LOG_OBJECT_TYPE.ORDER.name());
			 if(list!=null&&!list.isEmpty()){
				 MobilePersistanceLog mpl = list.get(0);
				 if(mpl.getLvVersion()>=310||mpl.isTouch()){
					 moc.setNewOrder(true);
				 }
			 } else {
				 moc.setNewOrder(false);
			 }			
			cmtList.add(moc);
			
		}
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("datas", cmtList);
		resultMap.put("isLastPage", this.isLastPage(pageConfig));
		return resultMap;
	}
	
	/**
	 * 待点评订单的点评纬度 . 
	 * @throws Exception 
	 */
	@Override
	public List<DicCommentLatitude> getCommentLatitudeInfos(Map<String, Object> param) throws Exception {
		 ArgCheckUtils.validataRequiredArgs("orderId", param);
		 // 点评维度列表. 
		 List<DicCommentLatitude> commentLatitudeList = null;
		 Object objOrderId = param.get("orderId");
		 String orderId = (null == objOrderId?null:objOrderId.toString());
		 //产品点评获取维度
		 if(null != orderId){
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId));
			if(null == order || null == order.getMainProduct()) {
				throw new Exception(" can not find productId by orderId "+orderId+" !");
			}
			ProdProduct product = queryProductInfoByProductId(order.getMainProduct().getProductId());
			if (null == product) {
				throw new Exception(" can not find product by productId "+order.getMainProduct().getProductId()+"");
			}
			
			 //对于酒店和门票的产品点评，要插入对应目的地信息，并使用对应目的地的维度
			if(product.getProductType().equals(Constant.PRODUCT_TYPE.TICKET.name()) 
					|| product.getProductType().equals(Constant.PRODUCT_TYPE.HOTEL.name())){
				//产品目的地参数
				Long destPlaceId = getProductCommentDestId(product.getProductId());
				if(destPlaceId == null) {
					throw new Exception(" can not find destPlace by productId "+product.getProductId()+"!");
				} 
				Place toPlace = placeService.queryPlaceByPlaceId(destPlaceId);
				if(toPlace == null) {
					throw new Exception(" can not find toPlace by destPlaceId "+destPlaceId+"!");
				} 
				commentLatitudeList = dicCommentLatitudeService.getLatitudesOfProduct(toPlace, product.getProductType());
			} else {
				//线路
				commentLatitudeList = dicCommentLatitudeService.getLatitudesOfProduct(null, product.getProductType());
			}
		}
		return commentLatitudeList;
	}

	/**
	 * 提交点评 . 
	 * @throws Exception 
	 */
	@Override
	public String commitComment(Map<String, Object> param) throws Exception{
 		Map<String,Object> m = submitCommitComment(param);
		if(null == m || null == m.get("commentId") || Long.valueOf(m.get("commentId").toString()) < 1) {
			throw new Exception(" 点评提交失败，请重试！ !");
		}
		return "success";
	}
	
	/**
	 * 提交点评 . 
	 * @param param
	 * @param returnType 1:commentId 2:productId 
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> submitCommitComment(Map<String, Object> param) throws Exception{
		ArgCheckUtils.validataRequiredArgs("latitudeInfo","objectId","content", param);
		// 获取用户对象. 
		UserUser users = userUserProxy.getUserUserByUserNo(param.get("userNo").toString());
 		Long commentId = null;
		String saveContent = changeContent(param.get("content").toString()); // 要保存的内容 . 
		String objectId = param.get("objectId").toString(); // 类型
		String[] latitudeInfoArray = String.valueOf(param.get("latitudeInfo")).split(",");
		String[] latitudeIds =  new String[latitudeInfoArray.length];
		int[] scores =  new int[latitudeInfoArray.length];
		initLatitudeInfo(latitudeInfoArray,latitudeIds,scores); // 初始化维度 和 评分  
		
		/**
		 * 1， 根据订单号 得到订单
		 * 2，根据订单得到产品
		 * 3, 根据产品获取其对应的目的地. 
		 * 4, 保存 
		 */
		OrdOrder orderOrder = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(objectId));
		ProdProduct product = queryProductInfoByProductId(orderOrder.getMainProduct().getProductId());
		if (null == product) {
			throw new Exception(" can not find ProdProduct by productId "+orderOrder.getMainProduct().getProductId()+"!");
		}
		boolean result = checkConditionForProdCmting(product, Long.valueOf(objectId),users.getUserId());
		if(!result){
			throw new Exception(" order doesn't exist or has already commented !");
		}
		CommonCmtCommentVO comment = new CommonCmtCommentVO();
		
		//对于门票产品点评和酒店产品点评需要保存目的地
		if(product.getProductType().equals(Constant.PRODUCT_TYPE.TICKET.name()) 
				|| product.getProductType().equals(Constant.PRODUCT_TYPE.HOTEL.name())) {
			Long destPlaceId = getProductCommentDestId(product.getProductId());
			if(destPlaceId == null) {
				throw new Exception(" can not find destPlaceId by productId "+product.getProductId()+" !");
			}
			comment.setPlaceId(destPlaceId);
		} 
		comment.setCmtType(Constant.EXPERIENCE_COMMENT_TYPE);
		comment.setProductId(product.getProductId());
		comment.setOrderId(Long.valueOf(objectId));
		comment.setContent(saveContent);
		String firstChannel = "";
		if(param.get("firstChannel")!=null){
			firstChannel = param.get("firstChannel").toString();
			comment.setChannel(firstChannel);
		}
		comment.setCmtLatitudes(getCmtLatitude(latitudeIds,scores));
		//comment.setCmtPictureList(getCmtPicture());
		comment.setCmtPictureList(this.wrapCmtPicture(param));
		// 优先使用nickname V3.1.0 
		if(!StringUtils.isEmpty(users.getNickName())) {
			users.setUserName(users.getNickName());
		}
		
		commentId = cmtCommentService.insert(users, comment);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("commentId", commentId);
		resultMap.put("cashRefund", product.getIsRefundable());
		return resultMap;
	}
	
	private List<CmtPictureVO> wrapCmtPicture(Map<String,Object> params){
		Object imageUrlObj = params.get("imageUrls");
		log.info("imageUrlObj="+imageUrlObj);
		if(null!=imageUrlObj){
			List<CmtPictureVO> pictures = new ArrayList<CmtPictureVO>();
			if(imageUrlObj instanceof String){
				try {
					JSONArray jsonArray = JSONArray.fromObject(imageUrlObj);
					for(Object obj:jsonArray){
						String imageUrl = (String) obj;
						log.info("imageUrl="+imageUrl);
						CmtPictureVO cmtPictureVO = new CmtPictureVO();
						cmtPictureVO.setPicUrl(imageUrl);
						pictures.add(cmtPictureVO);
					}
					
				} catch (Exception e) {//当String不能转成json的时候，当String处理
					String imageUrl = (String) imageUrlObj;
					log.info("imageUrl="+imageUrl);
					CmtPictureVO cmtPictureVO = new CmtPictureVO();
					cmtPictureVO.setPicUrl(imageUrl);
					pictures.add(cmtPictureVO);
				}
			}else if(imageUrlObj instanceof String[]){
				String[] imageUrls = (String[]) imageUrlObj;
				for(String imageUrl:imageUrls){
					log.info("imageUrl="+imageUrl);
					CmtPictureVO cmtPictureVO = new CmtPictureVO();
					cmtPictureVO.setPicUrl(imageUrl);
					pictures.add(cmtPictureVO);
				}
			}else{
				
			}
			return pictures;
		}
		
		return null;
	}
	
	/**
	 * 过滤内容
	 * @param content
	 * @return
	 */
	protected String changeContent(final String content) {
		if (StringUtils.isEmpty(content)) {
			return null;
		}  else {
			String contentStr = StringUtil.filterOutHTMLTags(content);
			return contentStr;
		}
	}	

	/**
	 * 查询点评所对应的产品
	 * @param productId
	 * @return
	 */
	protected ProdProduct queryProductInfoByProductId(Long productId){
		if(productId == null)
			return null;
		ProdProduct product = prodProductService.getProdProductById(productId);
		return product;
	}
	
	/**
	 * 获取点评产品所对应的目的地
	 * @return
	 */
	protected Long getProductCommentDestId(Long productId) {
		Long destPlaceId = prodProductPlaceService.selectDestByProductId(productId);
		if(destPlaceId == null){
			List<ProdProductPlace> prodProductPlaceList =  prodProductPlaceService.getProdProductPlaceListByProductId(productId);
			if (prodProductPlaceList != null && prodProductPlaceList.size() > 0) {
				destPlaceId = prodProductPlaceList.get(0).getPlaceId();
			}
		}
		return destPlaceId;
	}
	
	/*
	 * 获取维度前校验业务(针对产品)
	 * */
	protected boolean checkConditionForProdCmting(ProdProduct product, Long orderId,String userNo){
		//检查该订单是否是本人的
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if(ordOrder != null){
			if(!ordOrder.getUserId().equals(userNo)){
				return false;
			}
		} else {
			return false;
		}
		
		//该订单已写产品点评,转点评首页
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("orderId", orderId);
		parameters.put("productId", product.getProductId());
		parameters.put("getProductCmts", "Y");
		parameters.put("isHide", "displayall");//查所有点评
		Long count = cmtCommentService.getCommentTotalCount(parameters);
		if(count > 0){
			return false;
		}
		return true;
	}
	
	
	/**
	 * 获取点评纬度的对象
	 * @return 纬度的集合
	 */
	protected List<CmtLatitudeVO> getCmtLatitude(String[] latitudeIds,int[] scores) {
		// 获取指标
		List<CmtLatitudeVO> latitudes = new ArrayList<CmtLatitudeVO>();
		for (int i = 0; i < latitudeIds.length; i++) {
			CmtLatitudeVO cmtLatitudeVO = new CmtLatitudeVO();
			cmtLatitudeVO.setLatitudeId(latitudeIds[i]);
			cmtLatitudeVO.setScore(scores[i]);
			latitudes.add(cmtLatitudeVO);
		}

		return latitudes;
	}
	
	/**
	 * 格式化维度和评分.  
	 * @param latitudeInfoArray
	 * @param ids
	 * @param scores
	 */
	public void initLatitudeInfo(String[] latitudeInfoArray,String[]ids,int[] scores) {
		for (int i = 0; i < latitudeInfoArray.length;i++) {
			String[] keys = latitudeInfoArray[i].split("_");
		    ids[i] = keys[0];
		    scores[i] = (Integer.valueOf(keys[1]));
		}
	}

	/**
	 * 判断攻略是否被收藏.
	 */
	@Override
	public boolean isStrategyCollected(Map<String, Object> params) {
		ArgCheckUtils.validataRequiredArgs("objectId", params);
		params.put("objectType",Constant.CLIENT_FAVORITE_TYPE.GUIDE.getCode());
        List<MobileFavorite> lmf = mobileFavoriteService.queryMobileFavoriteList(params);
        if(null != lmf && lmf.size() > 0) {
        	return true;
        }
		return false;
	}
	
	
	/**
	 * 初始化分页信息  --暂未用到.
	 * @param params
	 * @param count  默认显示条数
	 * @param page   默认页数 
	 * @return
	 */
	public Page initPage(Map params,long count,long page) {
		try{
			Object oCount = params.get("count");
			Object oPage = params.get("page");
			if(null != oCount ) {
				count =Long.valueOf(oCount+"");
			}
			if(null != oPage) {
				page = Long.valueOf(oPage+"");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		// 初始化分页信息 
		return new Page(count<1?10:count, page<1?1:page);
	}

	public Map<String,Object> defaultParamters (Long page) {
		HashMap<String,Object> paramters = new HashMap<String,Object>();
		paramters.put("pageSize", 10L);
		paramters.put("currentPage", page);
		return paramters;
	}

	/**
	 *  V3.1  add
	 *  根据订单号，获取某个订单的优惠总金额 
	 * @param orderId  订单id 
	 * @return amount 
	 */
	public float sumYouhuiAmount(Long orderId) {
		// 早定早惠优惠金额
		float earlyCouponAmount = 0f;
		// 多订多惠优惠金额
		float moreCouponAmount= 0f;
		
		//优惠券列表
		List<OrdOrderAmountItem> listAmountItem=orderServiceProxy.queryOrdOrderAmountItem( orderId, "ALL");
		//优惠策略(业务端)
		Map<String,Object> businessCouponparam = new HashMap<String,Object>();
		businessCouponparam.put("objectId", orderId);
		businessCouponparam.put("objectType", "ORD_ORDER_ITEM_PROD");
		List<MarkCouponUsage> markCouponUsageList=favorOrderService.selectByParam(businessCouponparam);
		
		businessCouponparam.clear();
		
		if(markCouponUsageList!=null && markCouponUsageList.size()>0){
			List<Long> businessCouponIds =new ArrayList<Long>();
			for(MarkCouponUsage markCouponUsage:markCouponUsageList){
				businessCouponIds.add(markCouponUsage.getCouponCodeId());
			}
			businessCouponparam.put("businessCouponIds", businessCouponIds);
			List<BusinessCoupon> businessCouponList=businessCouponService.selectByIDs(businessCouponparam);
			Long tempEarlyAmount=0l;
			Long tempMoreAmount=0l;
			
			// 
			if(businessCouponList != null && businessCouponList.size() > 0){
				for(BusinessCoupon businessCoupon :businessCouponList){
					for(MarkCouponUsage markCouponUsage:markCouponUsageList){
						if(businessCoupon.getBusinessCouponId().compareTo(markCouponUsage.getCouponCodeId())==0){
							if("EARLY".equals(businessCoupon.getCouponType())){
								//1.早订早惠
								tempEarlyAmount+=markCouponUsage.getAmount();
							}else if ("MORE".equals(businessCoupon.getCouponType())){
								//2.多买多惠
								tempMoreAmount+=markCouponUsage.getAmount();
							}
						}
					}
				}
			}
			earlyCouponAmount+=PriceUtil.convertToYuan(tempEarlyAmount);
			moreCouponAmount+=PriceUtil.convertToYuan(tempMoreAmount);
		}
		
		return getSumYouHuiAmount( listAmountItem,  earlyCouponAmount,  moreCouponAmount);
		
	}
	
	/**
	 *  V3.1  add
	 *  获取优惠总额
	 * @param listAmountItem    优惠列表 
	 * @param earlyCouponAmount 早定早恵
	 * @param moreCouponAmount  多订多惠
	 * @return
	 */
	public Float getSumYouHuiAmount(List<OrdOrderAmountItem> listAmountItem,float earlyCouponAmount,float moreCouponAmount) {
		float _sum = 0.0f;
		if (null != listAmountItem && !listAmountItem.isEmpty()) {
			for (OrdOrderAmountItem item : listAmountItem) {
				if (item.isCouponItem()) {
					BigDecimal b1 = new BigDecimal(Float.toString(_sum));
					BigDecimal b2 = new BigDecimal(Float.toString(PriceUtil.convertToYuan(item.getItemAmount())));
					_sum = b1.add(b2).floatValue();
					
				}
			}
		}
		
		/*if(earlyCouponAmount != 0){
			_sum += earlyCouponAmount;
		}
		
		if(moreCouponAmount != 0){
			_sum += moreCouponAmount;
		}*/
		
		return _sum;
	}
	
	@Override
	public Map<String, Object> getBonusInfo(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getBonusIncome(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getBonusPayment(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getBonusRefund(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> reSendSmsCert(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> bonusPay(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> bindingCouponToUser(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> queryCmtWaitForOrder(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setBusinessCouponService(BusinessCouponService businessCouponService) {
		this.businessCouponService = businessCouponService;
	}

	@Override
	public Map<String, Object> commitOrderComment(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public void setMarkCouponUserService(MarkCouponUserService markCouponUserService) {
		this.markCouponUserService = markCouponUserService;
	}

	public void setOrdOrderChannelService(
			OrdOrderChannelService ordOrderChannelService) {
		this.ordOrderChannelService = ordOrderChannelService;
	}
	
	@Override
	public UserUser getUsersByMobOrNameOrEmailOrCard(String value) {
		return userUserProxy.getUsersByMobOrNameOrEmailOrCard(value);
	}

	@Override
	public String addContacts(Map<String, String> param) throws Exception {
		ArgCheckUtils.validataRequiredArgs("receivers", param);
		String jsons = param.get("receivers");
		if(StringUtils.isEmpty(jsons)) {
			throw new Exception("游玩人列表为空");
		}
		JSONArray data = JSONArray.fromObject(jsons);
		if(!data.isEmpty()) {
			for(int i=0;i<data.size();i++){
				JSONObject jobj =  (JSONObject) data.get(i);
				Map<String,String> m = new HashMap<String,String>();
				m.put("receiverName", null ==jobj.get("receiverName")?"":jobj.get("receiverName").toString());
				m.put("mobileNumber", null ==jobj.get("mobileNumber")?"":jobj.get("mobileNumber").toString());
				m.put("certType", null ==jobj.get("certType")?"":jobj.get("certType").toString());
				m.put("gender",null ==jobj.get("gender")?"":jobj.get("gender").toString());
				m.put("receiverId", null ==jobj.get("receiverId")?"":jobj.get("receiverId").toString());
				m.put("birthday", null ==jobj.get("birthday")?"":jobj.get("birthday").toString());
				m.put("certNo", null ==jobj.get("certNo")?"":jobj.get("certNo").toString());
				
				this.addContact(m);
			}
		}
		return null;
	}
	
	/**
	 * 设置sumsang wallet需要的属性
	 * @param vco
	 * @param prodc
	 */
	private void setSumsangProperty(MobileOrder vco, ProdCProduct prodc){
		if(null != prodc) {
			//为mobile order设置location
			Place from = prodc.getFrom();
			Place dest = prodc.getTo();
			
			if(null!=dest){
				String address = dest.getAddress();
				String scenicOpenTime = dest.getScenicOpenTime();
				String destPlaceName = dest.getName();
				String cityName = dest.getCity();
				
				vco.setAddress(address);
				vco.setScenicOpenTime(scenicOpenTime);
				vco.setDestPlaceName(destPlaceName);
				vco.setCityName(cityName);
			}
			
			if(null!=from){
				String fromPlaceName = from.getName();
				vco.setFromPlaceName(fromPlaceName);
			}else{
				vco.setFromPlaceName("各地");
			}
		}
	}
	
	private void setLocation2MobileOrder(MobileOrder vco, Long placeId) {
		Map<String,Object> coordinateParam = new HashMap<String,Object>();
		coordinateParam.put("placeId", placeId);
		List<PlaceCoordinateVo> listGoogle = placeCoordinateGoogleService.getGoogleMapListByParams(coordinateParam);
		if (!listGoogle.isEmpty()){
			PlaceCoordinateVo pcv = listGoogle.get(0);
			vco.setGoogleLatitude(pcv.getLatitude());
			vco.setGoogleLongitude(pcv.getLongitude());
			pcv.getPlaceAddress();
			
		}
		
		List<PlaceCoordinateVo> listBaidu = placeCoordinateBaiduService.getBaiduMapListByParams(coordinateParam);
		if (!listBaidu.isEmpty()){
			PlaceCoordinateVo pcv = listBaidu.get(0);
			vco.setBaiduLatitude(pcv.getLatitude());
			vco.setBaiduLongitude(pcv.getLongitude());
		}
	}

	@Override
	public Map<String, Object> getMoneyInfo(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getAdvanceOrder(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getBindingInfo(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getPaymentTarget(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> commitPayment(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

}

