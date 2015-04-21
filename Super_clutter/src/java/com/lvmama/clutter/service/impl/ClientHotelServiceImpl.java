package com.lvmama.clutter.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.exception.NoDataException;
import com.lvmama.clutter.exception.NotFoundException;
import com.lvmama.clutter.model.MobileHotelBookingRule;
import com.lvmama.clutter.model.MobileHotelGeo;
import com.lvmama.clutter.model.MobileHotelGuaranteeRule;
import com.lvmama.clutter.model.MobileHotelRule;
import com.lvmama.clutter.model.MobilePlaceHotel;
import com.lvmama.clutter.model.MobileProductHotel;
import com.lvmama.clutter.model.sumsang.Alert;
import com.lvmama.clutter.model.sumsang.Data;
import com.lvmama.clutter.model.sumsang.DateAlert;
import com.lvmama.clutter.model.sumsang.ElementDataBarcode;
import com.lvmama.clutter.model.sumsang.ElementDataImage;
import com.lvmama.clutter.model.sumsang.ElementDataText;
import com.lvmama.clutter.model.sumsang.Head;
import com.lvmama.clutter.model.sumsang.P;
import com.lvmama.clutter.model.sumsang.Partner;
import com.lvmama.clutter.model.sumsang.ValidUntil;
import com.lvmama.clutter.model.sumsang.View;
import com.lvmama.clutter.service.IClientHotelService;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.JSONUtil;
import com.lvmama.comm.pet.po.mobile.MobileHotel;
import com.lvmama.comm.pet.po.mobile.MobileHotelDest;
import com.lvmama.comm.pet.po.mobile.MobileHotelLandmark;
import com.lvmama.comm.pet.po.mobile.MobileHotelOrder;
import com.lvmama.comm.pet.po.mobile.MobileHotelOrderVisitor;
import com.lvmama.comm.pet.po.mobile.MobileHotelRoom;
import com.lvmama.comm.pet.po.mobile.MobileHotelRoomImage;
import com.lvmama.comm.pet.po.mobile.MobileOrderRelationSamsung;
import com.lvmama.comm.pet.po.mobile.UserCreditCard;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.ord.OrdOrderChannelService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.BookingRule;
import com.lvmama.elong.model.CancelOrderCondition;
import com.lvmama.elong.model.CancelOrderResult;
import com.lvmama.elong.model.Contact;
import com.lvmama.elong.model.CreateOrderCondition;
import com.lvmama.elong.model.CreateOrderResult;
import com.lvmama.elong.model.CreateOrderRoom;
import com.lvmama.elong.model.CreditCard;
import com.lvmama.elong.model.Customer;
import com.lvmama.elong.model.Detail;
import com.lvmama.elong.model.EnumBookingRule;
import com.lvmama.elong.model.EnumCurrencyCode;
import com.lvmama.elong.model.EnumGuaranteeMoneyType;
import com.lvmama.elong.model.EnumGuestTypeCode;
import com.lvmama.elong.model.EnumIdType;
import com.lvmama.elong.model.EnumOrderRelationType;
import com.lvmama.elong.model.EnumOrderStatusDescription;
import com.lvmama.elong.model.EnumPaymentType;
import com.lvmama.elong.model.EnumProductProperty;
import com.lvmama.elong.model.EnumSortType;
import com.lvmama.elong.model.GuaranteeRule;
import com.lvmama.elong.model.Hotel;
import com.lvmama.elong.model.HotelBrand;
import com.lvmama.elong.model.HotelBrandDTO;
import com.lvmama.elong.model.HotelCommentCondition;
import com.lvmama.elong.model.HotelDetailCondition;
import com.lvmama.elong.model.HotelList;
import com.lvmama.elong.model.HotelListCondition;
import com.lvmama.elong.model.ListRatePlan;
import com.lvmama.elong.model.NightlyRate;
import com.lvmama.elong.model.OrderDetailResult;
import com.lvmama.elong.model.OrderIdsCondition;
import com.lvmama.elong.model.OrderRelation;
import com.lvmama.elong.model.Position;
import com.lvmama.elong.model.RelatedOrderCondition;
import com.lvmama.elong.model.RelatedOrderResult;
import com.lvmama.elong.model.Review;
import com.lvmama.elong.model.Room;
import com.lvmama.elong.model.ValidateCondition;
import com.lvmama.elong.model.ValidateCreditCardCondition;
import com.lvmama.elong.model.ValidateCreditCardResult;
import com.lvmama.elong.model.ValidateResult;
import com.lvmama.elong.service.ICreditCardValidateService;
import com.lvmama.elong.service.IHotelBrandService;
import com.lvmama.elong.service.IHotelCommentService;
import com.lvmama.elong.service.IHotelDetailService;
import com.lvmama.elong.service.IHotelSearchService;
import com.lvmama.elong.service.IOrderCancelService;
import com.lvmama.elong.service.IOrderCreateService;
import com.lvmama.elong.service.IOrderDetailService;
import com.lvmama.elong.service.IOrderRelationService;
import com.lvmama.elong.service.IOrderValidateService;
import com.lvmama.elong.utils.DES;

public class ClientHotelServiceImpl extends AppServiceImpl implements
		IClientHotelService {
	private final Logger logger = Logger.getLogger(this.getClass());
	protected final static String MOBILE_HOTEL_CACHE = "MOBILE_HOTEL_CACHE";
	protected static int MOBILE_HOTEL_MEMCACHE_SECOND = 60 * 60 * 2; // 缓存时间2小时
	protected final static String HOT_CITY = "上海,杭州 ,南京,北京,成都,广州 ,深圳,三亚";
	private final String CASH_BACK_DATE = Constant.getInstance().getValue("cash.back.date");
	private final double CASH_BACK_RATE = Double.parseDouble(Constant.getInstance().getValue("cash.back.rate"));

	private final String CASH_BACK_DESC = "下单后并在{0}零点前入住，每间夜将返{1}元到你的驴妈妈奖金账户，利用奖金可以购买其他驴妈妈的产品例如门票度假线路等！\n备注：奖金返现可能会有0-7个工作日左右延时，是由于和酒店确认需要时间，请谅解";
	private final static Map<Integer, String> facilitieMap = new HashMap<Integer, String>();
	private IHotelSearchService hotelSearchService;
	private IHotelDetailService hotelDetailService;
	private IOrderValidateService orderValidateService;
	private IOrderCreateService orderCreateService;
	// private IOrderListService orderListService;
	private IOrderDetailService orderDetailService;
	private IOrderCancelService orderCancelService;
	private ICreditCardValidateService creditCardValidateService;
	private IHotelBrandService hotelBrandService;
	private IHotelCommentService hotelCommentService;
	private IOrderRelationService orderRelationService;
	
	private OrdOrderChannelService ordOrderChannelService;

	@Override
	public Map<String, Object> getComments(Map<String, Object> param)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HotelCommentCondition condition = new HotelCommentCondition();
		// condition.setCityId(this.getT(param, "cityId", String.class, true));
		condition.setHotelIds(this.getT(param, "hotelId", String.class, true));
		String sortBy = this.getT(param, "sortBy", String.class, false);
		if (!StringUtils.isEmpty(sortBy)) {
			condition.setSortBy(sortBy);
		}
		String sortType = this.getT(param, "sortType", String.class, false);
		if (!StringUtils.isEmpty(sortType)) {
			condition.setSortType(sortType);
		}
		String pageSize = this.getT(param, "pageSize", String.class, false);
		if (!StringUtils.isEmpty(pageSize)) {
			condition.setPageSize(Long.parseLong(pageSize));
		}
		String pageIndex = this.getT(param, "pageIndex", String.class, false);
		if (!StringUtils.isEmpty(pageIndex)) {
			condition.setPageIndex(Long.parseLong(pageIndex));
		}
		JSONObject jsonObjHotelComments = hotelCommentService
				.getComments(condition);
		if (null == jsonObjHotelComments || jsonObjHotelComments.isNullObject()) {
			throw new NotFoundException("对不起，该酒店还没有评论");
		}
		// Object objTemp = jsonObjHotelComments.get("CommentSummaryList");
		// if(null!=objTemp){
		// JSONArray jsonCommentSummaryList = JSONArray.fromObject(objTemp);
		// if(null!=jsonCommentSummaryList&&jsonCommentSummaryList.size()>0){
		// JSONObject jsonCommentSummary =
		// (JSONObject)jsonCommentSummaryList.get(0);
		JSONObject jsonCommentSummary = jsonObjHotelComments;
		JSONArray jsonAarryhotelCommonts = JSONArray
				.fromObject(jsonCommentSummary.get("LatestCommentItemList"));
		jsonAarryhotelCommonts = JSONUtil.jsonArrayPropertyIgnore(
				jsonAarryhotelCommonts, new String[] { "Id", "AppId",
						"ProductId", "ParentId", "CommentSummaryId", "HotelId",
						"CityId", "Title", "ReserNo", "RoomTypeId",
						"RoomTypeName", "UserRating", "RatingImage",
						"CommentTitularId", "CommentSourceId",
						"MemberSourceId", "MemberId", "UserfulTotal",
						"CreatedBy", "ModifiedTime", "ModifiedBy",
						"CreatedTime", "CreatedByIp", "Status", "IsDeleted",
						"HasInvalidWord", "CommentReplyList" });
		resultMap.put("hotelComments", jsonAarryhotelCommonts);
		Object objTotalComment = jsonCommentSummary.get("TotalComment");
		// Object objGoodComment = jsonCommentSummary.get("GoodComment");
		Object objBadComment = jsonCommentSummary.get("BadComment");

		int totalComment = (Integer) objTotalComment;
		if (totalComment == 0) {
			resultMap.put("goodCommentRate", "100%");
			resultMap.put("badCommentRate", "0%");
		} else {
			// int goodComment = (Integer)objGoodComment;
			int badComment = (Integer) objBadComment;
			double badCommentRate = (double) badComment / totalComment;
			resultMap.put("goodCommentRate",
					(int) Math.ceil((1 - badCommentRate) * 100) + "%");
			resultMap.put("badCommentRate",
					(int) Math.floor(badCommentRate * 100) + "%");
		}
		resultMap.put("totalComment", totalComment);
		// }
		// }
		// int pageNo = (Integer) jsonObjHotelComments.get("PageNo");
		// int pageCount = (Integer) jsonObjHotelComments.get("PageCount");
		// resultMap.put("hasNext", pageNo>=pageCount?false:true);
		resultMap.put("hasNext", false);
		return resultMap;
	}

	@Override
	public Map<String, Object> orderCancel(Map<String, Object> param)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ArgCheckUtils.validataRequiredArgs("orderId", "sign", "time", param);
		String orderId = param.get("orderId").toString();
		String sign = param.get("sign").toString();
		String time = param.get("time").toString();
		String lvsessionId = param.get(Constant.LV_SESSION_ID).toString();
		String signKey = ClutterConstant.getMobileSignKey();
		String serverSign = String.format("%s%s%s%s", orderId, time,
				lvsessionId, signKey);
		if (MD5.md5(serverSign).equalsIgnoreCase(sign)) {
			CancelOrderCondition condition = new CancelOrderCondition();
			condition.setOrderId(Long.valueOf(orderId));
			CancelOrderResult cancelOrderResult = null;
			try {
				cancelOrderResult = orderCancelService.cancelOrder(condition);
			} catch (ElongServiceException e) {
				logger.error(
						MessageFormat.format("【艺龙】取消订单异常--[{0}]",
								e.getMessage()), e);
				String message = this.getMessage(e.getMessage());
				throw new LogicException(message);
			}
			if (null != cancelOrderResult && cancelOrderResult.isSuccesss()) {
				resultMap.put("resultCode", 0);
			} else {
				logger.error("取消订单失败");
				throw new LogicException("取消订单失败");
			}
		}
		return resultMap;
	}

	/**
	 * 
	 * @param message
	 *            eg. H002001|入住日期范围无效 request.Request
	 * @return eg. 入住日期范围无效
	 */
	private String getMessage(String message) {
		if (message.contains("A101010010")) {// A101010010|错误原因：登陆失败,检查账号是否通过审核
			return "网络超时，请稍后再试";
		} else {
			Pattern pattern = Pattern.compile("\\|([^\\s]*)");
			Matcher matcher = pattern.matcher(message);
			if (matcher.find()) {
				return matcher.group(1);
			} else {
				return message;
			}
		}
	}

	/**
	 * 初始化分页信息 --暂未用到.
	 * 
	 * @param params
	 * @param count
	 *            默认显示条数
	 * @param page
	 *            默认页数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page initPage(Map params, long count, long page) {
		try {
			Object oCount = params.get("count");
			Object oPage = params.get("page");
			if (null != oCount) {
				count = Long.valueOf(oCount + "");
			}
			if (null != oPage) {
				page = Long.valueOf(oPage + "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 初始化分页信息
		return new Page(count < 1 ? 10 : count, page < 1 ? 1 : page);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> orderList(Map<String, Object> param)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Integer pageSize = this.getT(param, "pageSize", Integer.class, false);
		if (null == pageSize) {
			pageSize = 5;
		}
		int pageIndex = this.getT(param, "pageIndex", Integer.class, true);
		Page p = this.initPage(param, pageSize, pageIndex);
		p.setTotalResultSize(mobileHotelService
				.countMobileHotelOrderList(param));
		param.put("isPaging", "true"); // 是否使用分页
		param.put("startRows", p.getStartRows());
		param.put("endRows", p.getEndRows());

		List<MobileHotelOrder> mobileHotelOrders = mobileHotelService
				.queryMobileHotelOrderList(param);
		List<JSONObject> jsonOrderDetailResults = new ArrayList<JSONObject>();
		if (null != mobileHotelOrders && mobileHotelOrders.size() > 0) {
			for (MobileHotelOrder mobileHotelOrder : mobileHotelOrders) {
				OrderIdsCondition condition = new OrderIdsCondition();
				condition.setOrderId(mobileHotelOrder.getOrderId());
				OrderDetailResult orderDetailResult = null;
				try {
					orderDetailResult = orderDetailService
							.detailOrder(condition);
				} catch (Exception e) {
					logger.error(
							MessageFormat.format("【艺龙】获取订单详情异常--[{0}]",
									e.getMessage()), e);
					String message = this.getMessage(e.getMessage());
					throw new LogicException(message);
				}

				if (null != orderDetailResult) {
					// 根据hotelId查询hotel相关信息
					String hotelId = orderDetailResult.getHotelId();
					MobileHotel stMobileHotel = mobileHotelService
							.selectMobileHotelByHotelId(hotelId);

					orderDetailResult.setCreationDate(mobileHotelOrder
							.getCreateTime());

					JSONObject jsonOrderDetailResult = JSONUtil.jsonDateFormat(
							orderDetailResult, "yyyy-MM-dd");

					if (null != stMobileHotel) {
						jsonOrderDetailResult.put("name",
								stMobileHotel.getName());
						jsonOrderDetailResult.put("address",
								stMobileHotel.getAddress());
						jsonOrderDetailResult.put("phone",
								stMobileHotel.getPhone());
						int starRate = Integer.parseInt(stMobileHotel
								.getStarrate());
						String placeType = this
								.getPlaceTypeByStarRate(starRate);
						jsonOrderDetailResult.put("placeType", placeType);
						jsonOrderDetailResult.put("latitude",
								stMobileHotel.getGooglelat());
						jsonOrderDetailResult.put("longitude",
								stMobileHotel.getGooglelon());

					}

					String status = orderDetailResult.getStatus();
					jsonOrderDetailResult.put("statusCode", status);
					jsonOrderDetailResult.put("status",
							EnumOrderStatusDescription.valueOf(status).value());

					param.put("imgType", 5);
					param.put("imgSize", 3);
					List<MobileHotelRoomImage> mobileHotelRoomImages = mobileHotelService
							.queryMobileHotelRoomImageList(param);
					MobileHotelRoomImage mobileHotelRoomImage = mobileHotelRoomImages
							.get(0);
					if (null != mobileHotelRoomImage) {
						jsonOrderDetailResult.put("icon",
								mobileHotelRoomImage.getImgUrl());
					} else {
						jsonOrderDetailResult.put("icon", "");
					}

					EnumPaymentType paymentType = orderDetailResult
							.getPaymentType();
					if (paymentType.equals(EnumPaymentType.SelfPay)) {
						jsonOrderDetailResult.put("paymentType", "前台支付");
					} else if (paymentType.equals(EnumPaymentType.Prepay)) {
						jsonOrderDetailResult.put("paymentType", "预付");
					} else {
						jsonOrderDetailResult.put("paymentType", "");
					}

					Date cancalTime = orderDetailResult.getCancelTime();
					Date currentTime = Calendar.getInstance().getTime();

					if ("E".equals(status)) {// 订单已取消
						jsonOrderDetailResult.put("canCancel", false);
					} else if ("D".equals(status)) {// 订单已删除
						jsonOrderDetailResult.put("canCancel", false);
					} else if (currentTime.after(cancalTime)) {// 已经超过取消时间
						jsonOrderDetailResult.put("canCancel", false);
					} else {
						jsonOrderDetailResult.put("canCancel", true);
					}

					jsonOrderDetailResults.add(jsonOrderDetailResult);
				}
			}
		}
		resultMap.put("hasNext", p.hasNext());
		resultMap.put("orderDetailResults", jsonOrderDetailResults);

		return resultMap;
	}
	
	@Override
	public Map<String, Object> orderDetail(Map<String, Object> param)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OrderIdsCondition condition = new OrderIdsCondition();
		condition.setOrderId(this.getT(param, "orderId", Integer.class, true));
		// condition.setAffiliateConfirmationId(this.getT(param,
		// "affiliateConfirmationId", String.class, true));

		OrderDetailResult orderDetailResult = null;
		try {
			orderDetailResult = orderDetailService.detailOrder(condition);
		} catch (Exception e) {
			logger.error(
					MessageFormat.format("【艺龙】获取订单详情异常--[{0}]", e.getMessage()),
					e);
			String message = this.getMessage(e.getMessage());
			throw new LogicException(message);
		}

		if (null != orderDetailResult) {
			// MobileOrder order = new MobileOrder();
			// order.setOrderId(orderDetailResult.getOrderId());

			// 根据hotelId查询hotel相关信息
			String hotelId = orderDetailResult.getHotelId();
			MobileHotel stMobileHotel = mobileHotelService
					.selectMobileHotelByHotelId(hotelId);

			List<MobileHotelOrder> mobileHotelOrders = mobileHotelService
					.queryMobileHotelOrderList(param);

			if (null != mobileHotelOrders && mobileHotelOrders.size() > 0) {
				Date createTime = mobileHotelOrders.get(0).getCreateTime();
				if (null != createTime) {
					orderDetailResult.setCreationDate(createTime);
				}
			}
			// mobileHotelService

			JSONObject jsonOrderDetailResult = JSONUtil.jsonDateFormat(
					orderDetailResult, "yyyy-MM-dd");
			if (null != stMobileHotel) {
				jsonOrderDetailResult.put("name", stMobileHotel.getName());
				jsonOrderDetailResult
						.put("address", stMobileHotel.getAddress());
				jsonOrderDetailResult.put("phone", stMobileHotel.getPhone());
				int starRate = Integer.parseInt(stMobileHotel.getStarrate());
				String placeType = this.getPlaceTypeByStarRate(starRate);
				jsonOrderDetailResult.put("placeType", placeType);
				jsonOrderDetailResult.put("latitude",
						stMobileHotel.getGooglelat());
				jsonOrderDetailResult.put("longitude",
						stMobileHotel.getGooglelon());

			}

			HotelDetailCondition hotelDetailCondition = new HotelDetailCondition();
			Date arrivalDate = orderDetailResult.getArrivalDate();
			hotelDetailCondition.setArrivalDate(arrivalDate);
			Date departureDate = orderDetailResult.getDepartureDate();
			hotelDetailCondition.setDepartureDate(departureDate);
			hotelDetailCondition.setHotelIds(orderDetailResult.getHotelId());

			param.put("roomId", orderDetailResult.getRoomTypeId());
			List<MobileHotelRoom> mobileHotelRooms = mobileHotelService
					.queryMobileHotelRoomList(param);
			for (MobileHotelRoom mobileHotelRoom : mobileHotelRooms) {
				if (orderDetailResult.getRoomTypeId().equals(
						mobileHotelRoom.getRoomId())) {
					int numberOfRooms = orderDetailResult.getNumberOfRooms();
					Double totalPrice = orderDetailResult.getTotalPrice()
							.doubleValue();
					jsonOrderDetailResult.put("roomType",
							mobileHotelRoom.getName());
					String shareContent = String.format(
							"我在@驴妈妈旅游网 上订了“%s”这家酒店，%s才%s",
							stMobileHotel.getName(), mobileHotelRoom.getName(),
							totalPrice / numberOfRooms);
					jsonOrderDetailResult.put("shareContent", shareContent);
					jsonOrderDetailResult.put("wapUrl", "");
				}
			}

			param.put("imgType", 5);
			param.put("imgSize", 3);
			List<MobileHotelRoomImage> mobileHotelRoomImages = mobileHotelService
					.queryMobileHotelRoomImageList(param);
			if (null != mobileHotelRoomImages
					&& mobileHotelRoomImages.size() > 0) {
				MobileHotelRoomImage mobileHotelRoomImage = mobileHotelRoomImages
						.get(0);
				if (null != mobileHotelRoomImage) {
					jsonOrderDetailResult.put("icon",
							mobileHotelRoomImage.getImgUrl());
				} else {
					jsonOrderDetailResult.put("icon", "");
				}
			}else{
				jsonOrderDetailResult.put("icon", "");
			}

			String status = orderDetailResult.getStatus();
			jsonOrderDetailResult.put("statusCode", status);
			jsonOrderDetailResult.put("status", EnumOrderStatusDescription
					.valueOf(status).value());

			EnumPaymentType paymentType = orderDetailResult.getPaymentType();
			if (paymentType.equals(EnumPaymentType.SelfPay)) {
				jsonOrderDetailResult.put("paymentType", "前台支付");
			} else if (paymentType.equals(EnumPaymentType.Prepay)) {
				jsonOrderDetailResult.put("paymentType", "预付");
			} else {
				jsonOrderDetailResult.put("paymentType", "");
			}
			Date cancalTime = orderDetailResult.getCancelTime();
			Date currentTime = Calendar.getInstance().getTime();

			if ("E".equals(status)) {// 订单已取消
				jsonOrderDetailResult.put("canCancel", false);
			} else if ("D".equals(status)) {// 订单已删除
				jsonOrderDetailResult.put("canCancel", false);
			} else if (currentTime.after(cancalTime)) {// 已经超过取消时间
				jsonOrderDetailResult.put("canCancel", false);
			} else {
				jsonOrderDetailResult.put("canCancel", true);
			}

			// resultMap.put("orderDetailResult", orderDetailResult);
			resultMap.put("orderDetailResult", jsonOrderDetailResult);
		} else {
			logger.error("获取订单详情失败");
			throw new LogicException("获取订单详情失败");
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> orderCreate(Map<String, Object> param)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CreateOrderCondition condition = new CreateOrderCondition();

		Date arrivalDate = this.getT(param, "arrivalDate", Date.class, true);
		condition.setArrivalDate(arrivalDate);
		Date departureDate = this
				.getT(param, "departureDate", Date.class, true);
		condition.setDepartureDate(departureDate);

		// 联系人信息
		Contact contact = new Contact();
		String contactName = this
				.getT(param, "contactName", String.class, true);
		contact.setName(contactName);
		String contactMobile = this.getT(param, "contactMobile", String.class,
				true).replaceAll("\\s|-", "");// 去掉空格和-
		contact.setMobile(contactMobile);
		condition.setContact(contact);

		// 客人信息
		List<CreateOrderRoom> orderRooms = new ArrayList<CreateOrderRoom>();// start
																			// orderRooms

		String customerNames = this.getT(param, "customerNames", String.class,
				true);

		List<String> customerNameList = Arrays.asList(customerNames.split(","));
		for (String customerName : customerNameList) {

			CreateOrderRoom createOrderRoom = new CreateOrderRoom();
			Customer customer = new Customer();
			customer.setName(customerName);
			customer.setNationality("中国");// 国籍

			List<Customer> customers = new ArrayList<Customer>();// start
																	// customers
			customers.add(customer);
			createOrderRoom.setCustomers(customers);

			orderRooms.add(createOrderRoom);// end orderRooms
		}
		condition.setOrderRooms(orderRooms);

		MobileHotelRule mobileHotelRule = this.getMobileHotelRule(param);
		// 根据预定规则验证相关参数
		List<MobileHotelBookingRule> bookingRules = mobileHotelRule
				.getBookingRules();
		for (MobileHotelBookingRule bookingRule : bookingRules) {
			if (bookingRule.getTypeCode()
					.equals(EnumBookingRule.PerRoomPerName)) {
				if (bookingRule.getNumberOfRooms() < customerNameList.size()) {
					throw new LogicException(bookingRule.getDescription());
				}
			}
			if (bookingRule.getTypeCode().equals(
					EnumBookingRule.RejectCheckinTime)) {
				Date currentDate = Calendar.getInstance().getTime();
				Date dayStart = DateUtil.getDayStart(currentDate);
				int startHour = bookingRule.getStartHour();
				Date startHourDate = DateUtils.addHours(dayStart, startHour);
				int endHour = bookingRule.getEndHour();
				Date endHourDate = DateUtils.addHours(dayStart, endHour);
				if (startHourDate.before(currentDate)
						&& endHourDate.after(currentDate)) {// 在不接受预定的时间之内
					throw new LogicException(bookingRule.getDescription());
				}
			}
			if (bookingRule.getTypeCode().equals(EnumBookingRule.NeedPhoneNo)) {
				if (StringUtils.isEmpty(contactMobile)) {
					throw new LogicException(bookingRule.getDescription());
				}

			}
		}

		// 根据担保规则设置最早到店和最晚到店时间
		String option = (String) param.get("option");
		if (!StringUtils.isEmpty(option)) {
			List<MobileHotelGuaranteeRule> mobileGuaranteeList = mobileHotelRule
					.getGuaranteeRules();

			for (MobileHotelGuaranteeRule mobileGuarantee : mobileGuaranteeList) {
				if (option.equals(mobileGuarantee.getOption())) {
					condition.setEarliestArrivalTime(mobileGuarantee
							.getEarliestArrivalTime());
					condition.setLatestArrivalTime(mobileGuarantee
							.getLatestArrivalTime());
				}
			}
		} else {// 处理 不显示保留时间的选项
			if (DateUtils.isSameDay(Calendar.getInstance().getTime(),
					arrivalDate)) {// 当天
				condition.setEarliestArrivalTime(this.getNextHours());
				Date lastArrivalTime = DateUtils.addHours(this.getNextHours(),
						3);
				Date currentDate = Calendar.getInstance().getTime();
				if (DateUtils.isSameDay(currentDate, lastArrivalTime)) {
					condition.setLatestArrivalTime(DateUtils.addHours(
							this.getNextHours(), 3));
				} else {
					condition
							.setLatestArrivalTime(DateUtils.addMinutes(
									DateUtils.addHours(
											DateUtil.getDayStart(currentDate),
											24), -1));
				}

			} else {// 非当天
				Date dayStart = DateUtil.getDayStart(arrivalDate);
				condition.setEarliestArrivalTime(DateUtils.addHours(dayStart,
						14));
				condition
						.setLatestArrivalTime(DateUtils.addHours(dayStart, 20));
			}
		}

		String hotelId = this.getT(param, "hotelId", String.class, true);
		String firstChannel = this.getT(param, "firstChannel", String.class, false);
		String secondChannel = this.getT(param, "secondChannel", String.class, false);
		condition.setHotelId(hotelId);
		String roomTypeId = this.getT(param, "roomTypeId", String.class, true);
		condition.setRoomTypeId(roomTypeId);
		condition.setRatePlanId(this.getT(param, "ratePlanId", Integer.class,
				true));
		Integer numberOfRooms = this.getT(param, "numberOfRooms",
				Integer.class, true);
		condition.setNumberOfRooms(numberOfRooms);
		condition.setTotalPrice(this.getT(param, "totalPrice",
				BigDecimal.class, true));

		condition.setNumberOfCustomers(this.getT(param, "numberOfCustomers",
				Integer.class, true));
		condition.setCustomerIPAddress(this.getT(param, "customerIPAddress",
				String.class, true));

		String customerType = this.getT(param, "customerType", String.class,
				true);
		condition.setCustomerType(EnumGuestTypeCode.valueOf(customerType));
		Map<String, Object> roomProperties = this.getRoomProperties(param);
		condition.setPaymentType(EnumPaymentType.SelfPay);
		condition.setCurrencyCode((EnumCurrencyCode) roomProperties
				.get("currencyCode"));

		String ccNo = (String) param.get("ccNo");
		if (!StringUtils.isEmpty(ccNo)) {
			ValidateCreditCardCondition vCondition = new ValidateCreditCardCondition();
			String appKey = Constant.getInstance().getValue(
					"elong.hotel.app.key");
			String keyStore = appKey.substring(appKey.length() - 8,
					appKey.length());
			long epoch = System.currentTimeMillis() / 1000;
			String encodeCCNo = DES.encrypt(epoch + "#" + ccNo, keyStore);
			vCondition.setCreditCardNo(encodeCCNo);
			ValidateCreditCardResult vResult = creditCardValidateService
					.validateCreditCard(vCondition);
			if (null != vResult) {
				if (!vResult.isIsValid()) {
					logger.info("对不起，该信用卡号不可用");
					throw new LogicException("对不起，该信用卡号不可用");
				}
				String ccCvv = (String) param.get("ccCvv");
				if (StringUtils.isEmpty(ccCvv) && !vResult.isIsNeedVerifyCode()) {
					logger.info("对不起，请填写信用卡验证码");
					throw new LogicException("对不起，请填写信用卡验证码");
				}
			} else {
				logger.info("对不起，改信用卡号验证失败");
				throw new LogicException("对不起，改信用卡号验证失败");
			}
			this.saveCreditCard(param, ccNo);// 保存信用卡信息
			condition.setCreditCard(this.getCreditCard(param));// 信用卡信息
		}

		MobileHotelOrder mobileHotelOrder = new MobileHotelOrder();
		long affiliateConfirmationId = mobileHotelService
				.saveMobileHotelOrder(mobileHotelOrder);

		String userId = (String) param.get("userNo");
		// 保存入住人信息
		this.saveMobileHotelOrderVisitor(customerNameList,
				affiliateConfirmationId, contactName, contactMobile, userId);

		CreateOrderResult createOrderResult = null;
		try {
			condition.setAffiliateConfirmationId(String
					.valueOf(affiliateConfirmationId));
			// 验证订单
			param.put("earliestArrivalTime", condition.getEarliestArrivalTime());
			param.put("latestArrivalTime", condition.getLatestArrivalTime());
			Map<String, Object> isValidateMap = this.orderValidate(param);

			// 下单
			if (null != isValidateMap
					&& (Integer) isValidateMap.get("resultCode") == 0) {
				createOrderResult = orderCreateService.createOrder(condition);
				mobileHotelOrder.setOrderId(createOrderResult.getOrderId());
				mobileHotelOrder.setCancelTime(createOrderResult
						.getCancelTime());
				mobileHotelOrder
						.setCreateTime(Calendar.getInstance().getTime());
				mobileHotelOrder.setCurrencyCode(createOrderResult
						.getCurrencyCode().value());
				mobileHotelOrder.setUserId(userId);
				mobileHotelOrder.setLvHotelOrderId(affiliateConfirmationId);
				mobileHotelOrder.setGuaranteeAmount(createOrderResult
						.getGuaranteeAmount().longValue());
				mobileHotelOrder.setArrivaldate(arrivalDate);
				mobileHotelOrder.setDeparturedate(departureDate);
				Double dTotalPrice = this.getT(param, "totalPrice",
						Double.class, true) * 100;
				mobileHotelOrder.setTotalPrice(dTotalPrice.longValue());
				mobileHotelOrder.setQuantity(numberOfRooms.longValue());
				mobileHotelOrder.setHotelId(hotelId);
				mobileHotelOrder.setRoomId(roomTypeId);
				mobileHotelOrder.setQueryRelated("N");
				String channel="";
				if(firstChannel!=null && firstChannel!="" && secondChannel!=null && secondChannel!=""){
					channel=firstChannel+"_"+secondChannel;
				}
				mobileHotelOrder.setChannel(channel);
				mobileHotelService.updateMobileHotelOrder(mobileHotelOrder);
				
			} else {
				mobileHotelService
						.deleteMobileHotelOrderByPrimaryKey(affiliateConfirmationId);
				logger.info("验证订单失败");
				throw new LogicException("验证订单失败");
			}
		} catch (ElongServiceException e) {
			// 记录下单失败原因
			mobileHotelOrder.setUserId(userId);
			mobileHotelOrder.setCreateTime(Calendar.getInstance().getTime());
			mobileHotelOrder.setMessage(MessageFormat.format(
					"错误信息：{0}，酒店ID：{1}", e.getMessage(), hotelId));
			mobileHotelOrder.setIsValid("N");
			mobileHotelOrder.setLvHotelOrderId(affiliateConfirmationId);
			mobileHotelService.updateMobileHotelOrder(mobileHotelOrder);
			logger.error(
					MessageFormat.format("【艺龙】提交订单异常--[{0}]", e.getMessage()),
					e);
			String message = this.getMessage(e.getMessage());
			throw new LogicException(message);
		}
		resultMap.put("orderId", createOrderResult.getOrderId());
		resultMap.put("resultCode", 0);
		return resultMap;
	}

	/**
	 * 获取酒店房间的支付币种，支付方式等
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> getRoomProperties(Map<String, Object> param)
			throws Exception {
		Map<String, Object> roomProperties = new HashMap<String, Object>();
		HotelDetailCondition condition = new HotelDetailCondition();
		Date arrivalDate = this.getT(param, "arrivalDate", Date.class, true);
		condition.setArrivalDate(arrivalDate);
		Date departureDate = this
				.getT(param, "departureDate", Date.class, true);
		condition.setDepartureDate(departureDate);
		String hotelId = this.getT(param, "hotelId", String.class, true);
		condition.setHotelIds(hotelId);
		String roomTypeId = this.getT(param, "roomTypeId", String.class, true);
		Integer ratePlanId = this
				.getT(param, "ratePlanId", Integer.class, true);

		Hotel hotel = null;
		try {
			hotel = hotelDetailService.getHotel(condition);
		} catch (ElongServiceException e) {
			logger.error(
					MessageFormat.format("【艺龙】获取酒店详情异常--[{0}]", e.getMessage()),
					e);
			String message = this.getMessage(e.getMessage());
			throw new LogicException(message);
		}
		List<Room> rooms = hotel.getRooms();
		for (Room room : rooms) {
			if (roomTypeId.equals(room.getRoomTypeId())) {
				List<ListRatePlan> listRatePlans = room.getRatePlans();
				for (ListRatePlan listRatePlan : listRatePlans) {
					if (ratePlanId.equals(listRatePlan.getRatePlanId())) {
						roomProperties.put("paymentType",
								listRatePlan.getPaymentType());
						roomProperties.put("currencyCode",
								listRatePlan.getCurrencyCode());
					}
				}
			}
		}
		return roomProperties;
	}

	private void saveMobileHotelOrderVisitor(List<String> customerNameList,
			long affiliateConfirmationId, String contactName,
			String contactMobile, String userId) {
		Date currentDate = Calendar.getInstance().getTime();
		for (String customerName : customerNameList) {
			MobileHotelOrderVisitor mobileHotelOrderVisitor = new MobileHotelOrderVisitor();
			mobileHotelOrderVisitor.setCreatedTime(currentDate);
			mobileHotelOrderVisitor.setLvHotelOrderId(affiliateConfirmationId);
			mobileHotelOrderVisitor.setName(customerName);
			mobileHotelOrderVisitor.setUserId(userId);
			if (customerName.equals(contactName)) {
				mobileHotelOrderVisitor.setMobile(contactMobile);
			}
			mobileHotelService
					.saveMobileHotelOrderVisitor(mobileHotelOrderVisitor);
			this.saveHotelVisitorToReceiver(customerName, contactMobile, userId);
		}

	}

	@Override
	public Map<String, Object> orderFill(Map<String, Object> param)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		MobileHotelRule mobileHotelRule = this.getMobileHotelRule(param);

		JSONObject jsonMobileHotelRule = JSONUtil.jsonDateFormat(
				mobileHotelRule, "yyyy-MM-dd");
		resultMap.put("mobileHotelRule", jsonMobileHotelRule);
		return resultMap;
	}

	/**
	 * 保存信用卡信息
	 * 
	 * @param param
	 * @param ccNo
	 * @throws Exception
	 */
	private void saveCreditCard(Map<String, Object> param, String ccNo)
			throws Exception {
		Boolean saveCardFlag = this.getT(param, "saveCardFlag", Boolean.class,
				false);
		if (null != saveCardFlag && saveCardFlag) {
			// 保存用户是否保存信用卡状态
			String userNo = (String) param.get("userNo");
			UserUser userUser = userUserProxy.getUserUserByUserNo(userNo);
			userUser.setSaveCreditCard("Y");
			userUserProxy.update(userUser);

			// 保存信用卡信息
			UserCreditCard userCreditCard = new UserCreditCard();
			String localCCNo = DES.encrypt(
					ccNo.substring(0, ccNo.length() - 4), Constant.DES_KEY);
			userCreditCard.setCreditCardNo(localCCNo);
			userCreditCard.setUserId(userNo);
			String ccCvv = this.getT(param, "ccCvv", String.class, false);
			userCreditCard.setCreditCardCvv(ccCvv);

			int ccExpirationYear = this.getT(param, "ccExpirationYear",
					Integer.class, true);
			int ccExpirationMonth = this.getT(param, "ccExpirationMonth",
					Integer.class, true);

			ccExpirationMonth = ccExpirationMonth - 1;// 月份是从0开始的 你设置是12
														// 其实系统以为是1月，所以用0来表示1月

			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, ccExpirationYear);
			calendar.set(Calendar.MONTH, ccExpirationMonth);
			userCreditCard.setCreditCardEffectiveDate(calendar.getTime());

			String ccHolderName = this.getT(param, "ccHolderName",
					String.class, true);
			String ccIdType = this.getT(param, "ccIdType", String.class, true);
			String ccIdNo = this.getT(param, "ccIdNo", String.class, true);

			userCreditCard.setCreditCardHolder(ccHolderName);
			userCreditCard.setIdentifyCardType(ccIdType);
			userCreditCard.setIdentifyCardNo(ccIdNo);

			UserCreditCard persistCard = mobileHotelService
					.selectUserCreditCard(userCreditCard);
			if (null != persistCard) {
				// 卡号已经存在
			} else {
				mobileHotelService.saveUserCreditCard(userCreditCard);
			}
		} else {
			// 保存用户是否保存信用卡状态
			String userNo = (String) param.get("userNo");
			UserUser userUser = userUserProxy.getUserUserByUserNo(userNo);
			userUser.setSaveCreditCard("N");
			userUserProxy.update(userUser);
		}
	}

	private MobileHotelRule getMobileHotelRule(Map<String, Object> param)
			throws Exception {
		MobileHotelRule mobileHotelRule = new MobileHotelRule();
		List<MobileHotelGuaranteeRule> mobileGuaranteeRules = new ArrayList<MobileHotelGuaranteeRule>();
		List<MobileHotelBookingRule> mobileBookingRules = new ArrayList<MobileHotelBookingRule>();
		HotelDetailCondition condition = new HotelDetailCondition();
		Date arrivalDate = this.getT(param, "arrivalDate", Date.class, true);
		condition.setArrivalDate(arrivalDate);
		Date departureDate = this
				.getT(param, "departureDate", Date.class, true);
		condition.setDepartureDate(departureDate);
		condition.setHotelIds(this.getT(param, "hotelId", String.class, true));
		Hotel hotel = null;
		try {
			hotel = hotelDetailService.getHotel(condition);
		} catch (ElongServiceException e) {
			logger.error(
					MessageFormat.format("【艺龙】获取酒店详情异常--[{0}]", e.getMessage()),
					e);
			String message = this.getMessage(e.getMessage());
			throw new LogicException(message);
		}

		int numberOfRooms = this.getT(param, "numberOfRooms", Integer.class,
				true);
		String roomTypeId = this.getT(param, "roomTypeId", String.class, true);
		Integer ratePlanId = this
				.getT(param, "ratePlanId", Integer.class, true);

		mobileHotelRule.setArrivalDate(arrivalDate);
		mobileHotelRule.setDepartureDate(departureDate);
		mobileHotelRule.setCountDays(DateUtil.getDaysBetween(arrivalDate,
				departureDate));

		if (null != hotel) {
			List<Room> rooms = hotel.getRooms();
			for (Room room : rooms) {
				if (roomTypeId.equals(room.getRoomTypeId())) {// 预定房型
					List<ListRatePlan> listRatePlans = room.getRatePlans();
					for (ListRatePlan listRatePlan : listRatePlans) {
						if (ratePlanId == listRatePlan.getRatePlanId()) {// 预定产品
							// tips温馨提示
							StringBuffer tipsBuffer = new StringBuffer();
							tipsBuffer.append("早餐信息："
									+ listRatePlan.getRatePlanName());
							List<NightlyRate> nightlyRates = listRatePlan
									.getNightlyRates();
							Long addBed = nightlyRates.get(0).getAddBed()
									.longValue();
							if (addBed != -1) {
								tipsBuffer.append("\n");
								tipsBuffer.append("\n加床信息：可加床，加床价格" + addBed
										+ "元，请至酒店办理！");
							}
							mobileHotelRule.setTips(tipsBuffer.toString());
							EnumGuestTypeCode customerType = listRatePlan
									.getCustomerType();
							mobileHotelRule.setCustomerType(customerType
									.value());
							Double totalPrice = 0.00;
							for (NightlyRate nightlyRate : nightlyRates) {
								totalPrice += nightlyRate.getMember()
										.doubleValue();
							}
							mobileHotelRule.setTotalPrice(totalPrice
									* numberOfRooms);
							// 预定规则
							String bookingRuleIds = listRatePlan
									.getBookingRuleIds();
							if (!StringUtils.isEmpty(bookingRuleIds)) {
								List<BookingRule> bookingRules = hotel
										.getBookingRules();
								if (bookingRules.size() > 0) {
									for (BookingRule bookingRule : bookingRules) {
										Long bookingRuleId = bookingRule
												.getBookingRuleId();
										Pattern pattern = Pattern
												.compile(String
														.valueOf(bookingRuleId));
										Matcher matcher = pattern
												.matcher(bookingRuleIds);
										if (matcher.find()) {// 过滤bookingRuleIds中不包含的担保规则
											if (!(bookingRule
													.getTypeCode()
													.equals(EnumBookingRule.NeedNationality) || bookingRule
													.getTypeCode()
													.equals(EnumBookingRule.ForeignerNeedEnName))) {
												EnumBookingRule enumBookingRule = bookingRule
														.getTypeCode();
												String description = bookingRule
														.getDescription();
												MobileHotelBookingRule mobileHotelBookingRule = new MobileHotelBookingRule();
												mobileHotelBookingRule
														.setTypeCode(enumBookingRule);
												mobileHotelBookingRule
														.setNumberOfRooms(numberOfRooms);
												mobileHotelBookingRule
														.setDescription(description);
												mobileBookingRules
														.add(mobileHotelBookingRule);
											}
										}
									}
								}
							}
							// 担保规则
							String guaranteeRuleIds = listRatePlan
									.getGuaranteeRuleIds();
							List<GuaranteeRule> guaranteeRules = hotel
									.getGuaranteeRules();
							if (guaranteeRules.size() > 0) {
								StringBuffer buffer = new StringBuffer();// 担保说明
								if (!StringUtils.isEmpty(guaranteeRuleIds)) {// 担保ID不为空
									for (GuaranteeRule guaranteeRule : guaranteeRules) {
										int guranteeRuleId = guaranteeRule
												.getGuranteeRuleId();
										Pattern pattern = Pattern
												.compile(String
														.valueOf(guranteeRuleId));
										Matcher matcher = pattern
												.matcher(guaranteeRuleIds);
										if (matcher.find()) {// 过滤guaranteeRuleIds中不包含的担保规则

											buffer.append(guaranteeRule
													.getDescription() + "\n");
											EnumGuaranteeMoneyType guaranteeMoneyType = guaranteeRule.getGuaranteeType();
											String guaranteeType =  guaranteeMoneyType.value();
											double guaranteePrice = 0;
											if(EnumGuaranteeMoneyType.FirstNightCost.equals(guaranteeMoneyType)){
												//首晚价格
												guaranteePrice = nightlyRates.get(0).getMember().doubleValue()*numberOfRooms;
											}else{
												guaranteePrice = totalPrice;
											}
											
											mobileHotelRule.setGuaranteePrice(guaranteePrice);
											mobileHotelRule.setGuaranteeType(guaranteeType);
											

											List<MobileHotelGuaranteeRule> subOptions = this
													.getSubOptions(
															guaranteeRule,
															arrivalDate);

											subOptions = this.isGuarantee(
													guaranteeRule, arrivalDate,
													numberOfRooms, subOptions,guaranteePrice);

											mobileGuaranteeRules
													.addAll(subOptions);
										} else {

										}
										String guaranteeDescription = null;
										if (buffer.length() > 0) {
											guaranteeDescription = buffer
													.deleteCharAt(
															buffer.length() - 1)
													.toString();
										}
										// 担保说明
										mobileHotelRule
												.setGuaranteeDescription(guaranteeDescription);

									}
								} else {// 不需要担保
									List<MobileHotelGuaranteeRule> options = this
											.getOptionsByNoGuaranteeRule(arrivalDate);
									mobileGuaranteeRules.addAll(options);
								}
							} else {// 不需要担保
								List<MobileHotelGuaranteeRule> options = this
										.getOptionsByNoGuaranteeRule(arrivalDate);
								mobileGuaranteeRules.addAll(options);
							}

						}
					}
				}
			}
		} else {

		}

		String userId = (String) param.get("userNo");
		List<MobileHotelOrderVisitor> visitoies = this.getLastVisitoies(userId);
		String[] ignores = new String[] { "mobileHotelOrderVisitorId",
				"lvHotelOrderId", "createdTime", "userId" };
		JSONArray visitorJsonArray = JSONUtil.jsonArrayPropertyIgnore(
				visitoies, ignores);

		mobileHotelRule.setVisitorJsonArray(visitorJsonArray);
		mobileHotelRule.setBookingRules(mobileBookingRules);
		mobileHotelRule.setGuaranteeRules(mobileGuaranteeRules);
		return mobileHotelRule;
	}

	/**
	 * 获取用户上一个订单的入住人信息
	 * 
	 * @param userId
	 * @return
	 */
	private List<MobileHotelOrderVisitor> getLastVisitoies(String userId) {
		logger.debug(MessageFormat.format("userId=[{0}]", userId));
		Long lastLVHotelOrderId = mobileHotelService
				.getLastOrderIdByUserId(userId);
		MobileHotelOrderVisitor mobileHotelOrderVisitor = new MobileHotelOrderVisitor();
		mobileHotelOrderVisitor.setUserId(userId);
		mobileHotelOrderVisitor.setLvHotelOrderId(lastLVHotelOrderId);
		List<MobileHotelOrderVisitor> visitoies = mobileHotelService
				.selectByParams(mobileHotelOrderVisitor);
		return visitoies;
	}

	/**
	 * | IsAmountGuarantee | IsTimeGuarantee |担保规则校验 | false | false |
	 * 无条件强制担保订单，即必须担保 | true | false | 房量担保，检查Amount | false | true |
	 * 到店时间担保，检查StartTime和EndTime | true | true | 房量担保和到店时间担保
	 * 同时担保。需要检查Amount、StartTime和EndTime
	 */
	private List<MobileHotelGuaranteeRule> isGuarantee(
			GuaranteeRule guaranteeRule, Date arrivalDate, int numberOfRooms,
			List<MobileHotelGuaranteeRule> subMobileGuaranteeRules, double guaranteePrice) {
		if (null != subMobileGuaranteeRules) {
			if (!guaranteeRule.isIsTimeGuarantee()
					&& !guaranteeRule.isIsAmountGuarantee()) {// false&&false
				subMobileGuaranteeRules = new ArrayList<MobileHotelGuaranteeRule>();// 不显示保留时间的选项
			} else if (guaranteeRule.isIsAmountGuarantee()
					&& !guaranteeRule.isIsTimeGuarantee()) {// true&&false
				if (numberOfRooms > guaranteeRule.getAmount()) {// 将任何时间改成都需要担保
					subMobileGuaranteeRules = this
							.changeAllTimeNeedGuaranteeByAmount(subMobileGuaranteeRules, guaranteePrice);
				} else {
					subMobileGuaranteeRules = new ArrayList<MobileHotelGuaranteeRule>();// 不显示保留时间的选项
				}
			} else if (!guaranteeRule.isIsAmountGuarantee()
					&& guaranteeRule.isIsTimeGuarantee()) {// false&&true
				subMobileGuaranteeRules = this.changTimeNeedGuaranteeByTime(
						guaranteeRule, arrivalDate, subMobileGuaranteeRules, guaranteePrice);
			} else {// true&&true
				if (numberOfRooms > guaranteeRule.getAmount()) {// 将任何时间改成都需要担保
					subMobileGuaranteeRules = this
							.changeAllTimeNeedGuaranteeByAmount(subMobileGuaranteeRules, guaranteePrice);
				} else {
					subMobileGuaranteeRules = this
							.changTimeNeedGuaranteeByTime(guaranteeRule,
									arrivalDate, subMobileGuaranteeRules, guaranteePrice);
				}
			}
		}
		return subMobileGuaranteeRules;
	}

	private List<MobileHotelGuaranteeRule> changTimeNeedGuaranteeByTime(
			GuaranteeRule guaranteeRule, Date arrivalDate,
			List<MobileHotelGuaranteeRule> subMobileGuaranteeRules, double guaranteePrice) {
		String startTime = guaranteeRule.getStartTime();
		String endTime = guaranteeRule.getEndTime();
		Date currentDate = Calendar.getInstance().getTime();

		if (DateUtils.isSameDay(arrivalDate, Calendar.getInstance().getTime())) {// 当天
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			int currentHour = calendar.get(Calendar.HOUR_OF_DAY);// 当前时间下一个整点
			if ("00:00".equals(startTime) && "23.59".equals(endTime)) {// 酒店全天需要担保
				subMobileGuaranteeRules = new ArrayList<MobileHotelGuaranteeRule>();// 不显示保留时间的选项
			} else {
				if ((currentHour + 1) >= Integer.parseInt(startTime.substring(
						0, 2))) {// 预定时间下一个整点需要担保
					subMobileGuaranteeRules = new ArrayList<MobileHotelGuaranteeRule>();// 不显示保留时间的选项
				} else {
					subMobileGuaranteeRules = this
							.resetLastTimeNeedGuarantee(subMobileGuaranteeRules, guaranteePrice);
				}
			}
		} else {
			if ("00:00".equals(startTime) && "23.59".equals(endTime)) {// 酒店全天需要担保
				subMobileGuaranteeRules = new ArrayList<MobileHotelGuaranteeRule>();// 不显示保留时间的选项
			} else {
				startTime = StringUtils.replace(startTime, "次日", "");
				Integer intStartTime = Integer.parseInt(startTime.substring(0,
						2));
				if (intStartTime <= 14) {// 14点之前(含)需要担保
					subMobileGuaranteeRules = new ArrayList<MobileHotelGuaranteeRule>();// 不显示保留时间的选项
				} else {// T点之后需要担保
					subMobileGuaranteeRules = this
							.resetLastTimeNeedGuarantee(subMobileGuaranteeRules, guaranteePrice);
				}
			}

		}
		return subMobileGuaranteeRules;
	}

	private String getFormatPriceStr(double guaranteePrice){
		if(guaranteePrice % 1.0 == 0){
			NumberFormat nf = new DecimalFormat("#");
		    return nf.format(guaranteePrice);
		}
		return String.valueOf(guaranteePrice);
	}
	
	private List<MobileHotelGuaranteeRule> resetLastTimeNeedGuarantee(
			List<MobileHotelGuaranteeRule> subMobileGuaranteeRules, double guaranteePrice) {
		List<MobileHotelGuaranteeRule> tempSubMobileGuaranteeRules = new ArrayList<MobileHotelGuaranteeRule>();// 不显示保留时间的选项
		for (MobileHotelGuaranteeRule mobileGuaranteeRule : subMobileGuaranteeRules) {
			if ("次日6:00".equals(mobileGuaranteeRule.getOption())) {
				mobileGuaranteeRule.setNeedGuarantee(true);
				mobileGuaranteeRule.setOption(mobileGuaranteeRule.getOption()
						+ "(担保 ¥"+getFormatPriceStr(guaranteePrice)+")");
				tempSubMobileGuaranteeRules.add(mobileGuaranteeRule);
			} else {
				tempSubMobileGuaranteeRules.add(mobileGuaranteeRule);
			}
		}
		subMobileGuaranteeRules = tempSubMobileGuaranteeRules;
		return subMobileGuaranteeRules;
	}

	/**
	 * 将所有时间都改成需要担保
	 * 
	 * @param subMobileGuaranteeRules
	 */
	private List<MobileHotelGuaranteeRule> changeAllTimeNeedGuaranteeByAmount(
			List<MobileHotelGuaranteeRule> subMobileGuaranteeRules, double guaranteePrice) {
		List<MobileHotelGuaranteeRule> tempSubMobileGuaranteeRules = new ArrayList<MobileHotelGuaranteeRule>();// 不显示保留时间的选项
		for (MobileHotelGuaranteeRule mobileGuaranteeRule : subMobileGuaranteeRules) {
			if (!mobileGuaranteeRule.isNeedGuarantee()) {
				mobileGuaranteeRule.setNeedGuarantee(true);
				mobileGuaranteeRule.setOption(mobileGuaranteeRule.getOption()
						+ "(担保 ¥"+getFormatPriceStr(guaranteePrice)+")");
				tempSubMobileGuaranteeRules.add(mobileGuaranteeRule);
			} else {
				tempSubMobileGuaranteeRules.add(mobileGuaranteeRule);
			}
		}
		subMobileGuaranteeRules = tempSubMobileGuaranteeRules;
		return subMobileGuaranteeRules;
	}

	/**
	 * 获得信用卡信息
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private CreditCard getCreditCard(Map<String, Object> param)
			throws Exception {
		CreditCard creditCard = new CreditCard();
		String ccNo = this.getT(param, "ccNo", String.class, true);
		String appKey = Constant.getInstance().getValue("elong.hotel.app.key");
		String keyStore = appKey
				.substring(appKey.length() - 8, appKey.length());
		long epoch = System.currentTimeMillis() / 1000;
		ccNo = DES.encrypt(epoch + "#" + ccNo, keyStore);
		creditCard.setNumber(ccNo);
		String ccCvv = this.getT(param, "ccCvv", String.class, false);
		creditCard.setCVV(ccCvv);
		int ccExpirationYear = this.getT(param, "ccExpirationYear",
				Integer.class, true);
		creditCard.setExpirationYear(ccExpirationYear);
		int ccExpirationMonth = this.getT(param, "ccExpirationMonth",
				Integer.class, true);
		creditCard.setExpirationMonth(ccExpirationMonth);
		String ccHolderName = this.getT(param, "ccHolderName", String.class,
				true);
		creditCard.setHolderName(ccHolderName);
		String ccIdType = this.getT(param, "ccIdType", String.class, true);
		creditCard.setIdType(EnumIdType.fromValue(ccIdType));
		String ccIdNo = this.getT(param, "ccIdNo", String.class, true);
		creditCard.setIdNo(ccIdNo);
		return creditCard;
	}

	/**
	 * 获取下一个整点
	 * 
	 * @param arrivalDate
	 * @return
	 */
	private Date getNextHours() {
		Calendar calendar = Calendar.getInstance();
		int nextHours = calendar.get(Calendar.HOUR_OF_DAY) + 1;// 下一整点
		Date arrivalDateStart = DateUtil.getDayStart(calendar.getTime());
		return DateUtils.addHours(arrivalDateStart, nextHours);
	}

	@Override
	public Map<String, Object> orderValidate(Map<String, Object> param)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ValidateCondition condition = new ValidateCondition();
		Date arrivalDate = this.getT(param, "arrivalDate", Date.class, true);
		condition.setArrivalDate(arrivalDate);
		Object earliestObj = param.get("earliestArrivalTime");

		Date earliestArrivalTime = null;
		if (earliestObj instanceof Date) {
			earliestArrivalTime = (Date) earliestObj;
		} else {
			earliestArrivalTime = this.getT(param, "earliestArrivalTime",
					Date.class, true);
		}
		condition.setEarliestArrivalTime(earliestArrivalTime);

		Object latestObj = param.get("latestArrivalTime");
		Date latestArrivalTime = null;
		if (latestObj instanceof Date) {
			latestArrivalTime = (Date) latestObj;
		} else {
			latestArrivalTime = this.getT(param, "latestArrivalTime",
					Date.class, true);
		}
		condition.setLatestArrivalTime(latestArrivalTime);

		Date departureDate = this
				.getT(param, "departureDate", Date.class, true);
		condition.setDepartureDate(departureDate);

		condition.setHotelId(this.getT(param, "hotelId", String.class, true));
		condition.setRoomTypeId(this.getT(param, "roomTypeId", String.class,
				true));
		condition.setRatePlanId(this.getT(param, "ratePlanId", Integer.class,
				true));
		condition.setNumberOfRooms(this.getT(param, "numberOfRooms",
				Integer.class, true));
		condition.setTotalPrice(this.getT(param, "totalPrice",
				BigDecimal.class, true));

		ValidateResult validateResult = null;
		try {
			validateResult = orderValidateService.validateOrder(condition);
		} catch (ElongServiceException e) {
			logger.error(
					MessageFormat.format("【艺龙】订单验证异常--[{0}]", e.getMessage()),
					e);
			String message = this.getMessage(e.getMessage());
			throw new LogicException(message);
		}
		if (null != validateResult
				&& "OK".equals(validateResult.getResultCode().value())) {
			resultMap.put("resultCode", 0);
		} else {
			resultMap.put("resultCode", -1);
			throw new LogicException(validateResult.getErrorMessage());
		}

		return resultMap;
	}

	@Override
	public Map<String, Object> getHotel(Map<String, Object> param)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String key = MOBILE_HOTEL_CACHE + getMemcacheKeyByParams(param);
		Object obj = MemcachedUtil.getInstance().get(key);

		if (null != obj) {
			JSONObject jsonHotel = (JSONObject) obj;
			resultMap.put("hotel", jsonHotel);
		} else {
			if (facilitieMap.size() == 0) {
				this.initFacilitieMap();
			}
			HotelDetailCondition condition = new HotelDetailCondition();
			Date arrivalDate = this
					.getT(param, "arrivalDate", Date.class, true);
			condition.setArrivalDate(arrivalDate);
			Date departureDate = this.getT(param, "departureDate", Date.class,
					true);
			condition.setDepartureDate(departureDate);
			String hotelId = this.getT(param, "hotelId", String.class, true);
			condition.setHotelIds(hotelId);
			Hotel hotel = null;
			try {
				hotel = hotelDetailService.getHotel(condition);
			} catch (ElongServiceException e) {
				logger.error(
						MessageFormat.format("【艺龙】获取酒店详情异常--[{0}]",
								e.getMessage()), e);
				String message = this.getMessage(e.getMessage());
				throw new LogicException(message);
			}

			// 酒店信息
			MobilePlaceHotel mobilePlaceHotel = new MobilePlaceHotel();
			if (null != hotel) {
				MobileHotel stHotel = mobileHotelService
						.selectMobileHotelByHotelId(hotelId);// hotel静态数据

				mobilePlaceHotel.setPlaceId(hotelId);
				if (null != stHotel) {
					mobilePlaceHotel.setSellPrice(hotel.getLowRate()
							.doubleValue());
					mobilePlaceHotel.setDistance(hotel.getDistance()
							.doubleValue());

					int startRate = Integer.parseInt(stHotel.getStarrate());
					if (startRate == 5) {
						mobilePlaceHotel.setPlaceType("豪华酒店");
					} else if (startRate == 4) {
						mobilePlaceHotel.setPlaceType("高档酒店");
					} else if (startRate == 3) {
						mobilePlaceHotel.setPlaceType("舒适酒店");
					} else {
						mobilePlaceHotel.setPlaceType("经济酒店");
					}
					mobilePlaceHotel.setName(stHotel.getName());
					mobilePlaceHotel.setAddress(stHotel.getAddress());
					mobilePlaceHotel.setLatitude(stHotel.getGooglelat());
					mobilePlaceHotel.setLongitude(stHotel.getGooglelon());
					mobilePlaceHotel.setCommentCount(stHotel.getCount());
					mobilePlaceHotel.setCommentGoodRate(stHotel.getScore());
					mobilePlaceHotel.setIntroEditor(stHotel.getIntroeditor());
					mobilePlaceHotel.setDescription(stHotel.getDescription());

					mobilePlaceHotel.setGeneralAmenities(stHotel
							.getGeneralamenities());
					mobilePlaceHotel.setRoomAmenities(stHotel
							.getRoomamenities());// 客房设施暂无
					mobilePlaceHotel.setRecreationAmenities(stHotel
							.getRecreationamenities());
					mobilePlaceHotel.setTraffic(stHotel.getTraffic());

					String facilities = stHotel.getFacilities();
					StringBuffer netBuffer = new StringBuffer();
					if (!StringUtils.isEmpty(facilities)) {
						List<String> facilitieList = Arrays.asList(facilities
								.split(","));
						for (Entry<Integer, String> entry : facilitieMap
								.entrySet()) {
							if (facilitieList.contains(String.valueOf(entry
									.getKey()))) {
								netBuffer.append(entry.getValue());
							}
						}
					}
					mobilePlaceHotel.setNetDesc(netBuffer.toString());

				}
				List<MobileHotelRoomImage> mobileHotelRoomImages = mobileHotelService
						.queryMobileHotelRoomImageList(param);

				List<String> smallImages = new ArrayList<String>();
				List<String> middleImages = new ArrayList<String>();
				List<String> largeImages = new ArrayList<String>();
				for (MobileHotelRoomImage mobileHotelRoomImage : mobileHotelRoomImages) {
					if (mobileHotelRoomImage.getWatermark() != 1) {// 去掉水印图片
						if (mobileHotelRoomImage.getImgSize() == 2) {// 70x70
							smallImages.add(mobileHotelRoomImage.getImgUrl());
						} else if (mobileHotelRoomImage.getImgSize() == 3) {// 120x120
							middleImages.add(mobileHotelRoomImage.getImgUrl());
						} else if (mobileHotelRoomImage.getImgSize() == 1) {// 350
							largeImages.add(mobileHotelRoomImage.getImgUrl());
						}
					}
					// 地图酒店图片
					if (mobileHotelRoomImage.getImgType() == 8) {
						if (mobileHotelRoomImage.getImgSize() == 3) {
							mobilePlaceHotel.setImages(mobileHotelRoomImage
									.getImgUrl());
						}
					}

				}
				mobilePlaceHotel.setSmallImages(smallImages);
				mobilePlaceHotel.setMiddleImages(middleImages);
				mobilePlaceHotel.setLargeImages(largeImages);

				// 只要设置这个数组，指定过滤哪些字段。
				String[] excludeProperties = new String[] { "announcement",
						"branchId", "cashRefund", "cashRefundY", "cmtNum",
						"converManagerRecommend", "couponAble", "days",
						"hasIn", "managerRecommend", "marketPrice",
						"marketPriceYuan", "maxCashRefund", "mobileCashRefund",
						"moreDetailUrl", "nonSmokingRoom", "placeCmtScoreList",
						"preferentUrl", "preferentialInfo", "preferentialTags",
						"productId", "productName", "productType",
						"roomGround", "services", "subProductType",
						"zhSubProductType",
						// "imageList","floor","roomArea","icon", "broadband",
						"hasBusinessCoupon", "smallImage", "sellPriceYuan" };

				JSONObject jsonHotel = JSONUtil.jsonPropertyIgnore(
						mobilePlaceHotel, excludeProperties);
				MemcachedUtil.getInstance().set(key, MemcachedUtil.TWO_HOUR,
						jsonHotel);
				resultMap.put("hotel", jsonHotel);
			} else {
				throw new LogicException("酒店详情为空");
			}

		}
		return resultMap;
	}

	/**
	 * 获取酒店房型
	 */
	@Override
	public Map<String, Object> getHotelRooms(Map<String, Object> param)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HotelDetailCondition condition = new HotelDetailCondition();
		Date arrivalDate = this.getT(param, "arrivalDate", Date.class, true);
		condition.setArrivalDate(arrivalDate);
		Date departureDate = this
				.getT(param, "departureDate", Date.class, true);
		condition.setDepartureDate(departureDate);
		String hotelId = this.getT(param, "hotelId", String.class, true);
		condition.setHotelIds(hotelId);
		Hotel hotel = null;
		try {
			hotel = hotelDetailService.getHotel(condition);
		} catch (ElongServiceException e) {
			logger.error(
					MessageFormat.format("【艺龙】获取酒店详情异常--[{0}]", e.getMessage()),
					e);
			String message = this.getMessage(e.getMessage());
			throw new LogicException(message);
		}

		List<MobileProductHotel> mobileProductHotels = new ArrayList<MobileProductHotel>();
		List<MobileHotelRoom> mobileHotelRooms = mobileHotelService
				.queryMobileHotelRoomList(param);
		List<MobileHotelRoomImage> mobileHotelRoomImages = mobileHotelService
				.queryMobileHotelRoomImageList(param);

		// 来自动态数据的房间信息
		List<Room> rooms = hotel.getRooms();
		for (Room room : rooms) {
			// 房间信息
			List<ListRatePlan> ListRatePlans = room.getRatePlans();
			for (ListRatePlan listRatePlan : ListRatePlans) {
				if (listRatePlan.isStatus()) {// 过滤掉不可订客房
					MobileProductHotel mobileProductHotel = new MobileProductHotel();
					// 房间静态数据信息
					for (MobileHotelRoom mobileHotelRoom : mobileHotelRooms) {
						if (room.getRoomTypeId().equals(
								mobileHotelRoom.getRoomId())) {

							mobileProductHotel.setHotelId(hotelId);
							mobileProductHotel.setHotelRoom(mobileHotelRoom
									.getName());
							mobileProductHotel.setBedType(mobileHotelRoom
									.getBedtype());

							mobileProductHotel.setDescription(mobileHotelRoom
									.getDescription());
							mobileProductHotel.setRoomTypeId(mobileHotelRoom
									.getRoomId());

							int broadnetAccess = mobileHotelRoom
									.getBroadnetaccess();
							int broadnetFee = mobileHotelRoom.getBroadnetfee();
							mobileProductHotel
									.setBroadnetAccess(broadnetAccess);
							mobileProductHotel.setBroadnetFee(mobileHotelRoom
									.getBroadnetfee());

							if (1 == broadnetAccess) {
								if (1 == broadnetFee) {
									mobileProductHotel.setBroadband("免费宽带");
								} else {
									mobileProductHotel.setBroadband("宽带");
								}
							} else {
								mobileProductHotel.setBroadband("无宽带");
							}

							String floor = mobileHotelRoom.getFloor();
							if (!StringUtils.isEmpty(floor)) {
								mobileProductHotel.setFloor(floor + "楼");
							} else {
								mobileProductHotel.setFloor("楼层未知");
							}
							String area = mobileHotelRoom.getArea();
							if (!StringUtils.isEmpty(area)) {
								mobileProductHotel.setRoomArea(area + "平米");
							} else {
								mobileProductHotel.setRoomArea("面积未知");
							}

							List<String> imageList = new ArrayList<String>();
							// 8-房型 说明是房间的图片
							for (MobileHotelRoomImage mobileHotelRoomImage : mobileHotelRoomImages) {
								if (mobileHotelRoomImage.getImgType() == 8
										&& mobileHotelRoom.getRoomId().equals(
												mobileHotelRoomImage
														.getRoomId())) {
									String icon = mobileProductHotel.getIcon();
									if (mobileHotelRoomImage.getImgSize() == 2
											&& StringUtils.isEmpty(icon)) {
										mobileProductHotel
												.setIcon(mobileHotelRoomImage
														.getImgUrl());
									}
									if (mobileHotelRoomImage.getImgSize() == 1) {
										imageList.add(mobileHotelRoomImage
												.getImgUrl());
									}
								}
							}

							mobileProductHotel.setImageList(imageList);
						}
					}
					// 房间动态信息
					List<NightlyRate> nightlyRates = listRatePlan
							.getNightlyRates();
					double totalSellPrice = 0;
					int nightRateSize = 0;
					if(nightlyRates!=null){
						nightRateSize = nightlyRates.size();
					}
					for (NightlyRate nightlyRate : nightlyRates) {
						mobileProductHotel.setSmallImage(room.getImageUrl());
						Double member = nightlyRate.getMember().doubleValue();
						totalSellPrice+=member;
						
						mobileProductHotel.setRatePlanId(listRatePlan
								.getRatePlanId());
						mobileProductHotel.setStatus(listRatePlan.isStatus());
						mobileProductHotel.setHasBreakfast(listRatePlan
								.getRatePlanName());
						
					}
					if(totalSellPrice!=0&&nightRateSize!=0){
						
						double sellPrice = new BigDecimal(totalSellPrice/nightRateSize).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						mobileProductHotel.setSellPrice(sellPrice);
						if (arrivalDate.before(DateUtils.parseDate(
								CASH_BACK_DATE, "yyyy-MM-dd"))) {
//							mobileProductHotel.setCashBack(Math.ceil(sellPrice
//									* CASH_BACK_RATE));
							//android 适配显示返现0元
							if(sellPrice==0){
								if((Boolean) param.get("isAndroid")){
									mobileProductHotel.setCashBack("");
								}else{
									mobileProductHotel.setCashBack("0");
								}
							}else{
								String sellPriceStr = String.valueOf(Math.ceil(sellPrice
										* CASH_BACK_RATE));
								String cashBack = sellPriceStr.substring(0, sellPriceStr.lastIndexOf("."));
								mobileProductHotel.setCashBack(cashBack);
							}
							String cashBackDateStr = DateFormatUtils.format(DateUtils.parseDate(
								CASH_BACK_DATE, "yyyy-MM-dd"), "yyyy年MM月dd号");
							mobileProductHotel
									.setCashBackDesc(MessageFormat.format(
											CASH_BACK_DESC, cashBackDateStr,
											Math.ceil(sellPrice * CASH_BACK_RATE)));
						}
					}
					mobileProductHotels.add(mobileProductHotel);
				}
			}
		}

		String[] excludeProperties = new String[] { "announcement", "branchId",
				"cashRefund", "cashRefundY", "cmtNum",
				"converManagerRecommend", "couponAble", "days", "hasIn",
				"managerRecommend", "marketPrice", "marketPriceYuan",
				"maxCashRefund", "mobileCashRefund", "moreDetailUrl",
				"nonSmokingRoom", "placeCmtScoreList", "preferentUrl",
				"preferentialInfo", "preferentialTags", "productId",
				"productName", "productType", "roomGround", "services",
				"subProductType", "zhSubProductType",
				// "imageList","floor","roomArea","icon", "broadband",
				"hasBusinessCoupon", "smallImage", "sellPriceYuan" };

		if (null == mobileProductHotels || mobileProductHotels.size() == 0) {
			throw new NoDataException("刚选择的入住/离店日期无房间可预订，请调整日期");
		}

		JSONArray jsonHotelRooms = JSONUtil.jsonArrayPropertyIgnore(
				mobileProductHotels, excludeProperties);
		resultMap.put("hotelRooms", jsonHotelRooms);
		return resultMap;
	}

	private void initFacilitieMap() {
		facilitieMap.put(1, "免费wifi");
		facilitieMap.put(2, "收费wifi");
		facilitieMap.put(3, "免费宽带");
		facilitieMap.put(4, "收费宽带");
		// facilitieMap.put(5, "免费停车场");
		// facilitieMap.put(6, "收费停车场");
		// facilitieMap.put(7, "免费接机服务");
		// facilitieMap.put(8, "收费接机服务");
		// facilitieMap.put(9, "室内游泳池");
		// facilitieMap.put(10, "室外游泳池");
		// facilitieMap.put(11, "健身房");
		// facilitieMap.put(12, "商务中心");
		// facilitieMap.put(13, "会议室");
		// facilitieMap.put(14, "酒店餐厅");
	}

	@Override
	public Map<String, Object> search(Map<String, Object> param)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		HotelListCondition condition = new HotelListCondition();

		Date currentDate = Calendar.getInstance().getTime();
		Date arrivalDate = this.getT(param, "arrivalDate", Date.class, false);
		Date departureDate = this.getT(param, "departureDate", Date.class,
				false);
		// 到店时间，离店时间为空则默认为当前时间到明天的这个时间
		if (null == arrivalDate) {
			arrivalDate = currentDate;
		}
		if (null == departureDate) {
			departureDate = DateUtils.addDays(currentDate, 1);
		}
		condition.setArrivalDate(arrivalDate);
		condition.setDepartureDate(departureDate);

		condition.setCityId(this.getT(param, "cityId", String.class, true));
		condition.setPaymentType(EnumPaymentType.SelfPay);
		condition.setProductProperties(EnumProductProperty.All);
		condition.setResultType("1,2,3");
		condition.setBrandId(this.getT(param, "brandId", String.class, false));
		condition
				.setStarRate(this.getT(param, "starRate", String.class, false));
		condition.setDistrictId(this.getT(param, "districtId", String.class,
				false));
		condition.setLocationId(this.getT(param, "locationId", String.class,
				false));

		// 关键字查询
		condition.setQueryText(this.getT(param, "queryText", String.class,
				false));

		// 位置查询
		Position position = new Position();
		String pLatitude = (String) param.get("pLatitude");
		if(!StringUtils.isEmpty(pLatitude)){
			position.setLatitude(this.getT(param, "pLatitude", BigDecimal.class,
					false));
		}
		
		String pLongitude = (String) param.get("pLongitude");
		if(!StringUtils.isEmpty(pLongitude)){
			position.setLongitude(this.getT(param, "pLongitude", BigDecimal.class,
					false));
		}
		
		String pRadiusStr = (String) param.get("pRadius");
		if(!StringUtils.isEmpty(pRadiusStr)){
			Integer pRadius = this.getT(param, "pRadius", Integer.class, false);
			if (null != pRadius) {
				if (pRadius > 20) {// 单位：米,最大20km
					pRadius = 20 * 1000;// 转换成千米
				}
				pRadius = pRadius * 1000;// 转换成千米
			} else {
				pRadius = 10 * 1000;
			}
			position.setRadius(pRadius);

		}else{
			position.setRadius(10 * 1000);
		}
		condition.setPosition(position);

		// 价格查询
		String strLowRate = (String) param.get("lowRate");
		if (!StringUtils.isEmpty(strLowRate)) {
			Integer lowRate = this.getT(param, "lowRate", Integer.class, false);
			condition.setLowRate(lowRate);
		}
		
		String strHighRate = (String) param.get("highRate");
		if (!StringUtils.isEmpty(strHighRate)) {
			Integer highRate = this.getT(param, "highRate", Integer.class, false);
			condition.setHighRate(highRate);
		}

		// 分页参数，排序方式
		int pageSize = this.getT(param, "pageSize", Integer.class, true);
		if (pageSize > 20) {
			pageSize = 20;
		}

		Integer pageIndex = this.getT(param, "pageIndex", Integer.class, true);
		condition.setPageSize(pageSize);
		condition.setPageIndex(pageIndex);
		String strSort = this.getT(param, "sort", String.class, false);

		EnumSortType sort = null;
		if (!StringUtils.isEmpty(strSort)) {
			sort = EnumSortType.fromValue(strSort);
		} else {
			sort = EnumSortType.Default;
		}
		condition.setSort(sort);

		List<Hotel> hotels = new ArrayList<Hotel>();
		HotelList hotelList = null;
		try {
			hotelList = hotelSearchService.list(condition);
			hotels = hotelList.getHotels();
		} catch (ElongServiceException e) {
			logger.error(
					MessageFormat.format("【艺龙】获取酒店列表异常--[{0}]", e.getMessage()),
					e);
			String message = this.getMessage(e.getMessage());
			throw new LogicException(message);
		}

		List<MobilePlaceHotel> mobilePlaceHotels = new ArrayList<MobilePlaceHotel>();

		if (null != hotels) {
			for (Hotel hotel : hotels) {
				MobilePlaceHotel mobilePlaceHotel = new MobilePlaceHotel();
				mobilePlaceHotel.setPlaceId(hotel.getHotelId());
				Detail detail = hotel.getDetail();
				Review review = null;
				if (null != detail) {
					mobilePlaceHotel.setName(detail.getHotelName());
					mobilePlaceHotel.setAddress(detail.getAddress());
					int starRate = detail.getStarRate();
					String placeType = this.getPlaceTypeByStarRate(starRate);

					mobilePlaceHotel.setPlaceType(placeType);
					mobilePlaceHotel.setImages(detail.getThumbNailUrl());
					double sellPrice = hotel.getLowRate().doubleValue();
					mobilePlaceHotel.setSellPrice(sellPrice);
					if (arrivalDate.before(DateUtils.parseDate(CASH_BACK_DATE,
							"yyyy-MM-dd"))) {
//						mobilePlaceHotel.setCashBack(Math.ceil(sellPrice
//								* CASH_BACK_RATE));
						//android 适配显示返现0元
						if(sellPrice==0){
							if((Boolean) param.get("isAndroid")){
								mobilePlaceHotel.setCashBack("");
							}else{
								mobilePlaceHotel.setCashBack("0");
							}
						}else{
							String sellPriceStr = String.valueOf(Math.ceil(sellPrice
									* CASH_BACK_RATE));
							String cashBack = sellPriceStr.substring(0, sellPriceStr.lastIndexOf("."));
							mobilePlaceHotel.setCashBack(cashBack);
						}
					}
					mobilePlaceHotel.setDistance(hotel.getDistance()
							.doubleValue());
					mobilePlaceHotel.setLatitude(detail.getLatitude());
					mobilePlaceHotel.setLongitude(detail.getLongitude());
					// mobilePlaceHotel.setWifi(wifi);
					// mobilePlaceHotel.setPark(park);
					review = detail.getReview();
				}
				if (null != review) {
					mobilePlaceHotel.setCommentCount(Long.valueOf(review
							.getCount()));
					String score = review.getScore();
					if (StringUtils.isEmpty(score) || "%".equals(score)) {
						mobilePlaceHotel.setCommentGoodRate("");
					} else {
						mobilePlaceHotel.setCommentGoodRate(review.getScore());
					}
				}

				mobilePlaceHotels.add(mobilePlaceHotel);
			}
		}
		resultMap.put("hotels", mobilePlaceHotels);
		int count = hotelList.getCount();
		if ((pageIndex + 1) * pageSize < count) {
			resultMap.put("hasNext", true);
		} else {
			resultMap.put("hasNext", false);
		}

		return resultMap;

	}

	private String getPlaceTypeByStarRate(int starRate) {
		String placeType = null;
		if (starRate == 5) {
			placeType = "豪华酒店";
		} else if (starRate == 4) {
			placeType = "高档酒店";
		} else if (starRate == 3) {
			placeType = "舒适酒店";
		} else {
			placeType = "经济酒店";
		}
		return placeType;
	}

	/*
	 * (non-Javadoc) 酒店筛选条件接口
	 * 
	 * @see
	 * com.lvmama.clutter.service.IClientHotelService#searchLocations(java.util
	 * .Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> hotelSearchFilterData(Map<String, Object> param)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String key = "CLUTTER_HOTEL_SEARCHFILTERDATA";
		Object o = MemcachedUtil.getInstance().get(key);
		if (o == null) {
			// cityId查城市列表

			// 查询品牌列表
			List<HotelBrandDTO> brandList = ClutterConstant.getHotelBrands();

			// 价格区间
			List<Map<String, Object>> priceList = new ArrayList<Map<String, Object>>();
			Map<String, Object> price1Map = new HashMap<String, Object>();
			price1Map.put("name", "价格不限");
			price1Map.put("value", "0,1000000");

			Map<String, Object> price2Map = new HashMap<String, Object>();
			price2Map.put("name", "0-100");
			price2Map.put("value", "0,100");

			Map<String, Object> price3Map = new HashMap<String, Object>();
			price3Map.put("name", "100-300");
			price3Map.put("value", "100,300");

			Map<String, Object> price4Map = new HashMap<String, Object>();
			price4Map.put("name", "300-500");
			price4Map.put("value", "300,500");

			Map<String, Object> price5Map = new HashMap<String, Object>();
			price5Map.put("name", "500以上");
			price5Map.put("value", "500,1000000");

			priceList.add(price1Map);
			priceList.add(price2Map);
			priceList.add(price3Map);
			priceList.add(price4Map);
			priceList.add(price5Map);

			// 星级区间
			List<Map<String, Object>> starList = new ArrayList<Map<String, Object>>();

			Map<String, Object> star2Map = new HashMap<String, Object>();
			star2Map.put("name", "经济酒店");
			star2Map.put("value", "0,1,2");

			Map<String, Object> star3Map = new HashMap<String, Object>();
			star3Map.put("name", "舒适酒店");
			star3Map.put("value", "3");

			Map<String, Object> star4Map = new HashMap<String, Object>();
			star4Map.put("name", "高档酒店");
			star4Map.put("value", "4");

			Map<String, Object> star5Map = new HashMap<String, Object>();
			star5Map.put("name", "豪华酒店");
			star5Map.put("value", "5");

			starList.add(star2Map);
			starList.add(star3Map);
			starList.add(star4Map);
			starList.add(star5Map);

			// 排序条件
			List<Map<String, Object>> sortList = new ArrayList<Map<String, Object>>();
			Map<String, Object> sortPriceUpMap = new HashMap<String, Object>();

			Map<String, Object> defaultMap = new HashMap<String, Object>();
			defaultMap.put("value", "Default");
			defaultMap.put("name", "默认排序");
			sortList.add(defaultMap);

			sortPriceUpMap.put("value", "RateDesc");
			sortPriceUpMap.put("name", "价格从高到低");

			sortList.add(sortPriceUpMap);

			Map<String, Object> sortPriceDownMap = new HashMap<String, Object>();
			sortPriceDownMap.put("value", "RateAsc");
			sortPriceDownMap.put("name", "价格从低到高");
			sortList.add(sortPriceDownMap);

			Map<String, Object> starDescMap = new HashMap<String, Object>();
			starDescMap.put("value", "StarRankDesc");
			starDescMap.put("name", "星级从高到低");
			sortList.add(starDescMap);

			// 数据添加到返回列表中
			// resultMap.put("cities", LanMark);
			resultMap.put("brandList", brandList);
			resultMap.put("priceList", priceList);
			resultMap.put("starList", starList);
			resultMap.put("seqs", sortList);
			MemcachedUtil.getInstance().set(key, MemcachedUtil.TWO_HOUR,
					resultMap);
		} else {
			resultMap = (Map<String, Object>) o;
		}
		return initVersion(resultMap, param);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getLandMarks(Map<String, Object> param)
			throws Exception {
		ArgCheckUtils.validataRequiredArgs("cityCode", param);
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		String cityCode = "";
		if (param.get("cityCode") != null) {
			cityCode = param.get("cityCode").toString();
			params.put("cityCode", cityCode);
		}

		String key = "CLUTTER_HOTEL_LANDMARKS" + cityCode;
		Object o = MemcachedUtil.getInstance().get(key);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		if (o == null) {
			List<MobileHotelLandmark> disList = mobileHotelService
					.queryMobileHotelLandmarkList(params);

			Map<String, Object> groupMap1 = new HashMap<String, Object>();
			Map<String, Object> groupMap2 = new HashMap<String, Object>();
			Map<String, Object> groupMap3 = new HashMap<String, Object>();

			List<MobileHotelGeo> disList1 = new ArrayList<MobileHotelGeo>();
			List<MobileHotelGeo> disList2 = new ArrayList<MobileHotelGeo>();
			List<MobileHotelGeo> disList3 = new ArrayList<MobileHotelGeo>();

			if (disList != null && disList.size() > 0) {
				for (MobileHotelLandmark mobileHotelLandmark : disList) {
					MobileHotelGeo mobileHotelGeo = modelHelp(mobileHotelLandmark);

					if ("LandmarkLocations".equals(mobileHotelLandmark
							.getLocationType())) {
						disList1.add(mobileHotelGeo);
					} else if ("Districts".equals(mobileHotelLandmark
							.getLocationType())) {
						disList2.add(mobileHotelGeo);
					} else if ("CommericalLocations".equals(mobileHotelLandmark
							.getLocationType())) {
						disList3.add(mobileHotelGeo);
					}
				}
			}
			if (disList1 != null && disList1.size() > 0) {
				groupMap1.put("datas", disList1);
				groupMap1.put("name", "地标");
				resultList.add(groupMap1);
			}
			if (disList2 != null && disList2.size() > 0) {
				groupMap2.put("datas", disList2);
				groupMap2.put("name", "行政区");
				resultList.add(groupMap2);
			}
			if (disList3 != null && disList3.size() > 0) {
				groupMap3.put("datas", disList3);
				groupMap3.put("name", "商圈");
				resultList.add(groupMap3);
			}
			MemcachedUtil.getInstance().set(key, MemcachedUtil.TWO_HOUR,
					resultList);
		} else {
			resultList = (List<Map<String, Object>>) o;
		}
		result.put("datas", resultList);
		result.put("cityCode", cityCode);
		return initVersion(result, param);
	}

	public MobileHotelGeo modelHelp(MobileHotelLandmark mobileHotelLandmark) {
		MobileHotelGeo mobileHotelGeo = new MobileHotelGeo();
		if (mobileHotelLandmark != null) {
			mobileHotelGeo.setLocationId(mobileHotelLandmark.getLocationId());
			mobileHotelGeo.setLocationName(mobileHotelLandmark
					.getLocationName());
			mobileHotelGeo.setLocationType(mobileHotelLandmark
					.getLocationType());
			mobileHotelGeo.setPinyin(mobileHotelLandmark.getPinyin());
		}
		return mobileHotelGeo;
	}

	/**
	 * 酒店无担保规则
	 * 
	 * @return
	 */
	private List<MobileHotelGuaranteeRule> getOptionsByNoGuaranteeRule(
			Date arrivalDate) {
		List<MobileHotelGuaranteeRule> mobileGuaranteeList = new ArrayList<MobileHotelGuaranteeRule>();
		Date currentDate = Calendar.getInstance().getTime();
		if (DateUtils.isSameDay(arrivalDate, Calendar.getInstance().getTime())) {// 当天
			Date todayStart = DateUtil.getDayStart(currentDate);
			if (currentDate.before(DateUtils.addHours(todayStart, 6))) {// 00:00-06:00

				/**
				 * 为了解决选择23:59无对应的最找对应最早最晚到店时间
				 * 00:00-06:00属于当天预订23:59之后的，写入时间23:59-次日6:00
				 */
				Date tempTodayStart = DateUtils.addDays(todayStart, -1);
				MobileHotelGuaranteeRule mobileGuarantee1 = new MobileHotelGuaranteeRule(
						"23:59", false, DateUtils.addMinutes(
								DateUtils.addHours(tempTodayStart, 24), -1),
						DateUtils.addHours(tempTodayStart, 30));// 下一整点-23:59
				mobileGuaranteeList.add(mobileGuarantee1);

				MobileHotelGuaranteeRule mobileGuarantee2 = new MobileHotelGuaranteeRule(
						"次日6:00", false, DateUtils.addMinutes(
								DateUtils.addHours(tempTodayStart, 24), -1),
						DateUtils.addHours(tempTodayStart, 30));
				mobileGuaranteeList.add(mobileGuarantee2);
			} else if (currentDate.before(DateUtils.addHours(todayStart, 14))) {// 06:00-14:00
				MobileHotelGuaranteeRule mobileGuarantee = new MobileHotelGuaranteeRule(
						"20:00", false, DateUtils.addHours(todayStart, 14),
						DateUtils.addHours(todayStart, 20));
				mobileGuaranteeList.add(mobileGuarantee);

				MobileHotelGuaranteeRule mobileGuarantee1 = new MobileHotelGuaranteeRule(
						"23:59", false, DateUtils.addHours(todayStart, 20),
						DateUtils.addMinutes(
								DateUtils.addHours(todayStart, 24), -1));
				mobileGuaranteeList.add(mobileGuarantee1);

				MobileHotelGuaranteeRule mobileGuarantee2 = new MobileHotelGuaranteeRule(
						"次日6:00", false, DateUtils.addMinutes(
								DateUtils.addHours(todayStart, 24), -1),
						DateUtils.addHours(todayStart, 30));
				mobileGuaranteeList.add(mobileGuarantee2);

			} else if (currentDate.before(DateUtils.addHours(todayStart, 19))) {// 14:00-19:00
				MobileHotelGuaranteeRule mobileGuarantee = new MobileHotelGuaranteeRule(
						"20:00", false, this.getNextHours(),
						DateUtils.addHours(todayStart, 20));// 下一整点-20:00
				mobileGuaranteeList.add(mobileGuarantee);

				MobileHotelGuaranteeRule mobileGuarantee1 = new MobileHotelGuaranteeRule(
						"23:59", false, DateUtils.addHours(todayStart, 20),
						DateUtils.addMinutes(
								DateUtils.addHours(todayStart, 24), -1));
				mobileGuaranteeList.add(mobileGuarantee1);

				MobileHotelGuaranteeRule mobileGuarantee2 = new MobileHotelGuaranteeRule(
						"次日6:00", false, DateUtils.addMinutes(
								DateUtils.addHours(todayStart, 24), -1),
						DateUtils.addHours(todayStart, 30));
				mobileGuaranteeList.add(mobileGuarantee2);

			} else if (currentDate.before(DateUtils.addMinutes(
					DateUtils.addHours(todayStart, 24), -1))) {// 19:00-23:59
				MobileHotelGuaranteeRule mobileGuarantee1 = new MobileHotelGuaranteeRule(
						"23:59", false, this.getNextHours(),
						DateUtils.addMinutes(
								DateUtils.addHours(todayStart, 24), -1));// 下一整点-23:59
				mobileGuaranteeList.add(mobileGuarantee1);

				MobileHotelGuaranteeRule mobileGuarantee2 = new MobileHotelGuaranteeRule(
						"次日6:00", false, DateUtils.addMinutes(
								DateUtils.addHours(todayStart, 24), -1),
						DateUtils.addHours(todayStart, 30));
				mobileGuaranteeList.add(mobileGuarantee2);
			} else if (currentDate.after(DateUtils.addMinutes(
					DateUtils.addHours(todayStart, 24), -1))) {// 23:59-00:00
				MobileHotelGuaranteeRule mobileGuarantee2 = new MobileHotelGuaranteeRule(
						"次日6:00", false, DateUtils.addMinutes(
								DateUtils.addHours(todayStart, 24), -1),
						DateUtils.addHours(todayStart, 30));
				mobileGuaranteeList.add(mobileGuarantee2);
			} else {

			}
		} else {// 非当天
			Date dayStart = DateUtil.getDayStart(arrivalDate);
			MobileHotelGuaranteeRule mobileGuarantee = new MobileHotelGuaranteeRule(
					"20:00", false, DateUtils.addHours(dayStart, 14),
					DateUtils.addHours(dayStart, 20));
			mobileGuaranteeList.add(mobileGuarantee);

			MobileHotelGuaranteeRule mobileGuarantee1 = new MobileHotelGuaranteeRule(
					"23:59", false, DateUtils.addHours(dayStart, 20),
					DateUtils.addMinutes(DateUtils.addHours(dayStart, 24), -1));
			mobileGuaranteeList.add(mobileGuarantee1);

			MobileHotelGuaranteeRule mobileGuarantee2 = new MobileHotelGuaranteeRule(
					"次日6:00", false, DateUtils.addMinutes(
							DateUtils.addHours(dayStart, 24), -1),
					DateUtils.addHours(dayStart, 30));
			mobileGuaranteeList.add(mobileGuarantee2);
		}

		return mobileGuaranteeList;
	}

	/**
	 * 获取酒店保留至下拉框的值
	 * 
	 * @param guaranteeRule
	 * @param arrivalDate
	 * @return
	 */
	private List<MobileHotelGuaranteeRule> getSubOptions(
			GuaranteeRule guaranteeRule, Date arrivalDate) {
		List<MobileHotelGuaranteeRule> subMobileGuaranteeList = new ArrayList<MobileHotelGuaranteeRule>();
		String startTime = guaranteeRule.getStartTime();
		String endTime = guaranteeRule.getEndTime();

		if (DateUtils.isSameDay(arrivalDate, Calendar.getInstance().getTime())) {// 当天
			Date currentDate = Calendar.getInstance().getTime();
			Date dayStart = DateUtil.getDayStart(currentDate);
			if ("00:00".equals(startTime) && "23.59".equals(endTime)) {// 酒店全天需要担保
				// 不显示保留时间的选项
			} else if (StringUtils.isEmpty(startTime)
					&& StringUtils.isEmpty(endTime)) {// 全天不需要担保
				subMobileGuaranteeList = this
						.getOptionsByNoGuaranteeRule(arrivalDate);
			} else {
				int startHours = Integer.parseInt(startTime.substring(0, 2));
				Calendar calendar = Calendar.getInstance();
				int nextHours = calendar.get(Calendar.HOUR_OF_DAY) + 1;
				if (startHours <= nextHours) {// 当天预订，预订时间下一个整点需要担保
					// 不保留时间选项，同全天需要担保
				} else {// 当天预订，预订时间下一个整点不需要担保
						// T点
					MobileHotelGuaranteeRule mobileGuarantee1 = new MobileHotelGuaranteeRule(
							startTime, false, this.getNextHours(),
							DateUtils.addHours(dayStart, startHours));
					subMobileGuaranteeList.add(mobileGuarantee1);
					// 次日6:00
					MobileHotelGuaranteeRule mobileGuarantee2 = new MobileHotelGuaranteeRule(
							"次日6:00", false, DateUtils.addHours(dayStart,
									startHours), DateUtils.addHours(dayStart,
									30));
					subMobileGuaranteeList.add(mobileGuarantee2);
				}
			}
		} else {// 非当天
			Date dayStart = DateUtil.getDayStart(arrivalDate);
			if ("00:00".equals(startTime) && "23.59".equals(endTime)) {// 酒店全天需要担保
				// 不显示保留时间的选项
			} else if (StringUtils.isEmpty(startTime)
					&& StringUtils.isEmpty(endTime)) {// 酒店全天不需要担保

				MobileHotelGuaranteeRule mobileGuarantee = new MobileHotelGuaranteeRule(
						"20:00", false, DateUtils.addHours(dayStart, 14),
						DateUtils.addHours(dayStart, 20));
				subMobileGuaranteeList.add(mobileGuarantee);

				MobileHotelGuaranteeRule mobileGuarantee1 = new MobileHotelGuaranteeRule(
						"23:59", false, DateUtils.addHours(dayStart, 20),
						DateUtils.addMinutes(DateUtils.addHours(dayStart, 24),
								-1));
				subMobileGuaranteeList.add(mobileGuarantee1);

				MobileHotelGuaranteeRule mobileGuarantee2 = new MobileHotelGuaranteeRule(
						"次日6:00", false, DateUtils.addMinutes(
								DateUtils.addHours(dayStart, 24), -1),
						DateUtils.addHours(dayStart, 30));
				subMobileGuaranteeList.add(mobileGuarantee2);

			} else if (!StringUtils.isEmpty(startTime)) {
				int startHours = Integer.parseInt(startTime.substring(0, 2));
				MobileHotelGuaranteeRule mobileGuarantee1 = new MobileHotelGuaranteeRule(
						startTime, false, DateUtils.addHours(dayStart, 14),
						DateUtils.addHours(dayStart, startHours));
				subMobileGuaranteeList.add(mobileGuarantee1);
				// 次日6:00
				MobileHotelGuaranteeRule mobileGuarantee2 = new MobileHotelGuaranteeRule(
						"次日6:00", false, DateUtils.addHours(dayStart,
								startHours), DateUtils.addHours(dayStart, 30));
				subMobileGuaranteeList.add(mobileGuarantee2);
			}
		}
		return subMobileGuaranteeList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getCities(Map<String, Object> param)
			throws Exception {
		String key = "CLUTTER_HOTEL_CITIES";
		Object o = MemcachedUtil.getInstance().get(key);
		Map<String, Object> resultMap = null;
		if (o == null) {
			resultMap = new HashMap<String, Object>();
			List<MobileHotelDest> mobileHotelDests = mobileHotelService
					.queryMobileHotelDestList(param);

			List<Map<String, Object>> cities = new ArrayList<Map<String, Object>>(
					500);
			for (MobileHotelDest mobileHotelDest : mobileHotelDests) {
				Map<String, Object> citiesMap = new HashMap<String, Object>();
				citiesMap.put("name", mobileHotelDest.getCityName());
				citiesMap.put("pinyin", mobileHotelDest.getPinyin());
				citiesMap.put("isHot",
						HOT_CITY.contains(mobileHotelDest.getCityName()));
				citiesMap.put("id", mobileHotelDest.getCityCode());
				cities.add(citiesMap);
			}
			resultMap.put("cities", cities);
			MemcachedUtil.getInstance().set(key, MemcachedUtil.TWO_HOUR,
					resultMap);
		} else {
			resultMap = (Map<String, Object>) o;
		}
		return initVersion(resultMap, param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getHotelBrandList(Map<String, Object> params)
			throws Exception {
		String key = MOBILE_HOTEL_CACHE + getMemcacheKeyByParams(params);
		Object obj = MemcachedUtil.getInstance().get(key);
		Map<String, Object> map = new HashMap<String, Object>();
		List<HotelBrand> hotelBrandList;
		if (null != obj) {
			hotelBrandList = (List<HotelBrand>) obj;
		} else {
			hotelBrandList = hotelBrandService.getHotelBrandList(params);
			// 存入memcache

			if (null != hotelBrandList && hotelBrandList.size() > 0) {
				MemcachedUtil.getInstance().set(key,
						MOBILE_HOTEL_MEMCACHE_SECOND, hotelBrandList);
			}
		}
		map.put("datas", hotelBrandList);
		return initVersion(map, params);
	}

	@Override
	public Map<String, Object> getUserCreditCards(Map<String, Object> param)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userId = (String) param.get("userNo");
		List<UserCreditCard> userCreditCards = mobileHotelService
				.selectUserCreditCardByUserId(userId);
		List<UserCreditCard> userCreditCardList = new ArrayList<UserCreditCard>();
		for (UserCreditCard userCreditCard : userCreditCards) {
			String creditCardNo = userCreditCard.getCreditCardNo();
			creditCardNo = DES.decrypt(creditCardNo, Constant.DES_KEY);
			userCreditCard.setCreditCardNo(creditCardNo);
			userCreditCardList.add(userCreditCard);
		}
		JSONArray jsonUserCreditCards = JSONUtil.jsonArrayDateFormat(
				userCreditCardList, "yyyy-MM-dd");
		resultMap.put("userCreditCards", jsonUserCreditCards);
		return resultMap;
	}

	@Override
	public Map<String, Object> getOrderRelation(Map<String, Object> param)
			throws Exception {
		ArgCheckUtils.validataRequiredArgs("orderIds", "relationType", param);
		RelatedOrderCondition condition = new RelatedOrderCondition();
		condition.setOrderIds(this.getT(param, "orderIds", String.class, true));
		condition.setRelationType(EnumOrderRelationType.Child);
		RelatedOrderResult orderResult = orderRelationService
				.getRelatedOrder(condition);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (null != orderResult) {
			List<OrderRelation> listRelation = orderResult.getRelations();
			resultMap.put("datas", listRelation);
		}
		return resultMap;
	}

	/**
	 * 根据参数获取相应的key .
	 * 
	 * @param params
	 * @return key
	 */
	public String getMemcacheKeyByParams(Map<String, Object> params) {
		String memcacheKey = "";
		// 先从缓存中区
		try {
			// memcacheKey = params.get("method").toString();
			memcacheKey = MD5.encode(params.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memcacheKey;
	}

	/**
	 * 先从缓存中回去信息.
	 * 
	 * @param params
	 * @return obj
	 */
	public Object getMemecachedInfo(String memcacheKey) {
		Object obj = null;
		// 先从缓存中区
		if (!StringUtils.isEmpty(memcacheKey)) {
			obj = MemcachedUtil.getInstance().get(
					MOBILE_HOTEL_CACHE + memcacheKey);
		}

		return obj;
	}

	public void setHotelSearchService(IHotelSearchService hotelSearchService) {
		this.hotelSearchService = hotelSearchService;
	}

	public void setHotelDetailService(IHotelDetailService hotelDetailService) {
		this.hotelDetailService = hotelDetailService;
	}

	public void setOrderValidateService(
			IOrderValidateService orderValidateService) {
		this.orderValidateService = orderValidateService;
	}

	public void setOrderCreateService(IOrderCreateService orderCreateService) {
		this.orderCreateService = orderCreateService;
	}

	// public void setOrderListService(IOrderListService orderListService) {
	// this.orderListService = orderListService;
	// }

	public void setOrderDetailService(IOrderDetailService orderDetailService) {
		this.orderDetailService = orderDetailService;
	}

	public void setOrderCancelService(IOrderCancelService orderCancelService) {
		this.orderCancelService = orderCancelService;
	}

	public void setCreditCardValidateService(
			ICreditCardValidateService creditCardValidateService) {
		this.creditCardValidateService = creditCardValidateService;
	}

	public void setHotelBrandService(IHotelBrandService hotelBrandService) {
		this.hotelBrandService = hotelBrandService;
	}

	public void setHotelCommentService(IHotelCommentService hotelCommentService) {
		this.hotelCommentService = hotelCommentService;
	}

	public void setOrderRelationService(
			IOrderRelationService orderRelationService) {
		this.orderRelationService = orderRelationService;
	}
	
	/**
	 * 发布ticket
	 * 
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> issueHotel(Map<String, Object> param) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String,Object> orderResultMap = this.orderDetail(param);
		String serial = (String) param.get("serial");
		JSONObject jsonOrderDetailResult = (JSONObject) orderResultMap.get("orderDetailResult");
		long orderId = Long.parseLong((String)param.get("orderId"));
		if(null!=jsonOrderDetailResult&&!jsonOrderDetailResult.equals(JSONNull.getInstance())){
			String ticketId = this.sendData(jsonOrderDetailResult,serial);
			resultMap.put("ticketId", ticketId);
			
			//TODO 保存到数据库ticketId serial orderId
			MobileOrderRelationSamsung result = mobileClientService.selectByOrderId(orderId);
			if(null!=result){
				
			}else{
				MobileOrderRelationSamsung record = new MobileOrderRelationSamsung();
				record.setTicketid(ticketId);
				record.setSerial(serial);
				record.setOrderid(orderId);
				record.setCreateDate(Calendar.getInstance().getTime());
				record.setUpdateDate(Calendar.getInstance().getTime());
				mobileClientService.insert(record);
			}
		}
		return resultMap;
	}
	public void setOrdOrderChannelService(
			OrdOrderChannelService ordOrderChannelService) {
		this.ordOrderChannelService = ordOrderChannelService;
	}
	private String sendData(JSONObject jsonOrderDetailResult,String serial) {
		Data data = new Data();

		Head head = new Head();
		head.setVersion("1.1");
		if(!StringUtils.isEmpty(serial)){
			head.setSerial(serial);
		}else{
			head.setSerial(String.valueOf(System.currentTimeMillis()));
		}
		
		head.setSkinId("56716172-5a6e-392a-80e4-1ac5be97aca1");
		head.setKeywords(new String[] { "lvmama", "route" });
		head.setStorable(true);

		String arrivalDateStr = jsonOrderDetailResult.getString("arrivalDate");
		String format = "yyyy/MM/dd HH:mm";
		if(!StringUtils.isEmpty(arrivalDateStr)){
			try {
				ValidUntil validUntil = new ValidUntil();
				Date arrivalDateAddOne = DateUtils.addDays(DateUtils.parseDate(arrivalDateStr, "yyyy-MM-dd"), 1);
				validUntil.setValue(DateFormatUtils.format(arrivalDateAddOne,
						format));
				validUntil.setFormat(format);
				head.setValidUntil(validUntil);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		data.setHead(head);

		// views
		List<View> views = new ArrayList<View>();

		// barcode
		View main_barcode_a = new View();
		main_barcode_a.setId("MAIN_BARCODE_a");

		ElementDataBarcode barcode = new ElementDataBarcode();
		barcode.setCaption(String.valueOf(jsonOrderDetailResult.get("orderId")));
		barcode.setType("QRCODE");
		barcode.setValue(String.valueOf(jsonOrderDetailResult.get("orderId")));

		main_barcode_a.setBarcode(barcode);
		views.add(main_barcode_a);
		
		String icon = jsonOrderDetailResult.getString("icon");
		if(!StringUtils.isEmpty(icon)){
			// image
			View main_middle_image_a = new View();
			main_middle_image_a.setId("MAIN_MIDDLE_IMAGE_a");
			
			ElementDataImage image = new ElementDataImage();
			image.setValue(this.getBase64Image(icon));
			image.setType("jpg;base64");
			image.setAlign("middle");

			main_middle_image_a.setImage(image);
			views.add(main_middle_image_a);
		}
		
		//list text1
		View abstract_value1 = new View();
		abstract_value1.setId("ABSTRACT_VALUE1");

		ElementDataText abstract_value1Text = new ElementDataText();
		abstract_value1Text.setValue(jsonOrderDetailResult.getString("name"));
		abstract_value1Text.setAlign("middle");

		abstract_value1.setText(abstract_value1Text);
		views.add(abstract_value1);
		
		//list text2
		View abstract_value2 = new View();
		abstract_value2.setId("ABSTRACT_VALUE2");

		ElementDataText abstract_value2Text = new ElementDataText();
		abstract_value2Text.setValue(jsonOrderDetailResult.getString("arrivalDate"));
		abstract_value2Text.setAlign("middle");

		abstract_value2.setText(abstract_value2Text);
		views.add(abstract_value2);

		// top image text
		View main_top_value1_a = new View();
		main_top_value1_a.setId("MAIN_TOP_VALUE1_a");

		ElementDataText middleText = new ElementDataText();
		middleText.setValue(jsonOrderDetailResult.getString("name"));
		middleText.setAlign("middle");

		main_top_value1_a.setText(middleText);
		views.add(main_top_value1_a);

		// 入住人
		View main_middle_value1_a = new View();
		main_middle_value1_a.setId("MAIN_BOTTOM_VALUE1_a");

		ElementDataText leftText = new ElementDataText();
		leftText.setValue(this.getCustomer(jsonOrderDetailResult));
		leftText.setAlign("left");

		main_middle_value1_a.setText(leftText);
		views.add(main_middle_value1_a);

		// 入住时间
		View main_middle_value1_b = new View();
		main_middle_value1_b.setId("MAIN_AUX1_VALUE1_a");

		ElementDataText rightText = new ElementDataText();
		rightText.setValue(jsonOrderDetailResult.getString("arrivalDate"));
		rightText.setAlign("left");

		main_middle_value1_b.setText(rightText);
		views.add(main_middle_value1_b);

		// 离店时间
		View main_aux1_value1_c = new View();
		main_aux1_value1_c.setId("MAIN_AUX1_VALUE1_c");

		ElementDataText oLeftText = new ElementDataText();
		oLeftText.setValue(jsonOrderDetailResult.getString("departureDate"));
		oLeftText.setAlign("left");

		main_aux1_value1_c.setText(oLeftText);
		views.add(main_aux1_value1_c);
		
		// 酒店地址
		View main_aux2_value1_c = new View();
		main_aux2_value1_c.setId("MAIN_AUX2_VALUE1_a");

		ElementDataText oBottomText = new ElementDataText();
		oBottomText.setValue(jsonOrderDetailResult.getString("address"));
		oBottomText.setAlign("left");

		main_aux2_value1_c.setText(oBottomText);
		views.add(main_aux2_value1_c);

		data.setView(views);
		
		//String jsonOrderDetailResult.getString("arrivalDate");
		//jsonOrderDetailResult.getString("departureDate");
		//this.getHotel(param);

		List<Alert> alerts = new ArrayList<Alert>();
		//Alert geofenceAlert = new Alert();
		//GeofenceAlert geofence = new GeofenceAlert();
		//geofence.setAltitude(null);
		//geofence.setLatitude(mobileOrder.getBaiduLatitude());
		//geofence.setLongitude(mobileOrder.getBaiduLongitude());
		//geofence.setRangeinmeter(10);
		
		//geofenceAlert.setId("ALERT1");
		//geofenceAlert.setGeofence(geofence);
		
		//alerts.add(geofenceAlert);

		Alert timeAlert = new Alert();
		DateAlert date = new DateAlert();
		date.setBeforeinmin(5);
		date.setFormat(format);
		String visitTime = jsonOrderDetailResult.getString("arrivalDate");
		Date visitDate = null;
		try {
			visitDate = DateUtils.parseDate(visitTime, "yyyy-MM-dd");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (null != visitDate) {
			date.setValue(DateFormatUtils.format(
					DateUtils.addDays(visitDate, -2), format));
		}
		timeAlert.setDate(date);
		alerts.add(timeAlert);

		data.setAlerts(alerts);

		List<Partner> partners = new ArrayList<Partner>();
		Partner partner = new Partner();
		P p = new P();
		partner.setPartner(p);
		partners.add(partner);
		data.setPartners(partners);

		System.out.println(data);
		
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(
				"https://api.wallet.samsung.com/tkt/tickets");
		String authorization = new String(
				Base64.encodeBase64("c89bf26b2c3342dcb760ce075e1c0cea:334dc213ece748ea840e0f7145bc1cca"
						.getBytes()));
		System.out.println(authorization);

		postMethod.setRequestHeader("X-TKT-Protocol-Version", "1.1");
		postMethod.setRequestHeader("Authorization", "Basic " + authorization);
		postMethod.setRequestHeader("Content-Type", "application/json");
		postMethod.setRequestHeader("charset", "UTF-8");

		String jsonDataStr = JSONUtil.jsonPropertyFilter(data).toString();
		System.out.println(jsonDataStr);
		RequestEntity requestEntity = new ByteArrayRequestEntity(
				jsonDataStr.getBytes());
		postMethod.setRequestEntity(requestEntity);
		try {
			int statusCode = httpClient.executeMethod(postMethod);

			System.out.println(statusCode);
			String strResponseBody = new String(postMethod.getResponseBody());
			try {
				JSONObject responseJsonObject = JSONObject.fromObject(strResponseBody);
				String ticketId = (String) responseJsonObject.get("ticketId");
				return ticketId;
			} catch (Exception e) {
				throw new LogicException("酒店订单同步失败");
			}
		} catch (Exception e) {
			throw new LogicException("酒店订单同步失败");
		} finally {
			postMethod.releaseConnection();
		}
	}
	private String getCustomer(JSONObject jsonOrderDetailResult){
		JSONObject jsonContact = (JSONObject) jsonOrderDetailResult.get("contact");
		return jsonContact.getString("name");
	}
	
	private String getBase64Image(String imgUrl) {
		try {
			URL url = new URL(imgUrl);
			InputStream is = url.openStream();
			BufferedImage bi = ImageIO.read(is);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, "jpg", baos);
			byte[] bytes = baos.toByteArray();

			return new String(Base64.encodeBase64(bytes)).trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
