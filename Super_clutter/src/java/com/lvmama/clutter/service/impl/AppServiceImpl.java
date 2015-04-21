package com.lvmama.clutter.service.impl;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.lvmama.clutter.model.MobileBranch;
import com.lvmama.clutter.model.MobileDest;
import com.lvmama.clutter.service.AppService;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.CouponUtils;
import com.lvmama.comm.bee.po.client.ComClientLog;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.meta.MetaProductTicket;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.prod.ProdHotel;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.GroupDreamService;
import com.lvmama.comm.bee.service.favor.FavorOrderService;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.service.prod.ProdProductRoyaltyService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.prod.ProdTrainService;
import com.lvmama.comm.bee.service.view.ViewPageJourneyService;
import com.lvmama.comm.bee.service.view.ViewPageService;
import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.bee.vo.view.MarkCouponUserInfo;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.client.RecommendInfoClient;
import com.lvmama.comm.pet.po.client.ClientCmtLatitude;
import com.lvmama.comm.pet.po.client.ClientCmtPlace;
import com.lvmama.comm.pet.po.client.ClientCommitCmtResult;
import com.lvmama.comm.pet.po.client.ClientGroupon2;
import com.lvmama.comm.pet.po.client.ClientOrderCmt;
import com.lvmama.comm.pet.po.client.ClientPicture;
import com.lvmama.comm.pet.po.client.ClientPlace;
import com.lvmama.comm.pet.po.client.ClientProduct;
import com.lvmama.comm.pet.po.client.ClientTimePrice;
import com.lvmama.comm.pet.po.client.ClientUserCouponInfo;
import com.lvmama.comm.pet.po.client.ClientViewJouney;
import com.lvmama.comm.pet.po.client.ViewClientOrder;
import com.lvmama.comm.pet.po.client.ViewOrdPerson;
import com.lvmama.comm.pet.po.comment.CmtLatitudeStatistics;
import com.lvmama.comm.pet.po.comment.DicCommentLatitude;
import com.lvmama.comm.pet.po.mobile.MobilePersistanceLog;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlacePhoto;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.po.pub.ComUserFeedback;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.client.ComClientService;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.comment.CmtLatitudeStatistisService;
import com.lvmama.comm.pet.service.comment.CmtTitleStatistisService;
import com.lvmama.comm.pet.service.comment.DicCommentLatitudeService;
import com.lvmama.comm.pet.service.econtract.OrdEContractService;
import com.lvmama.comm.pet.service.favor.FavorService;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.mobile.MobileClientService;
import com.lvmama.comm.pet.service.mobile.MobileHotelService;
import com.lvmama.comm.pet.service.mobile.MobileTrainService;
import com.lvmama.comm.pet.service.place.PlaceCoordinateBaiduService;
import com.lvmama.comm.pet.service.place.PlaceCoordinateGoogleService;
import com.lvmama.comm.pet.service.place.PlacePhotoService;
import com.lvmama.comm.pet.service.place.PlacePlaceDestService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.prod.ProductServiceProxy;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.pet.service.pub.ComUserFeedbackService;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.comm.pet.service.search.PlaceSearchInfoService;
import com.lvmama.comm.pet.service.search.ProdTrainCacheService;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.pet.service.seo.RecommendInfoService;
import com.lvmama.comm.pet.service.user.IReceiverUserService;
import com.lvmama.comm.pet.service.user.UserCooperationUserService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.pet.vo.PlaceCoordinateVo;
import com.lvmama.comm.pet.vo.ProductList;
import com.lvmama.comm.search.vo.ClientPlaceSearchVO;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.TreeBean;
import com.lvmama.comm.utils.ActivityUtil;
import com.lvmama.comm.utils.CommentUtil;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtLatitudeVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
import com.lvmama.comm.vo.comment.DicCommentLatitudeVO;
import com.lvmama.comm.vo.comment.PlaceCmtCommentVO;
import com.lvmama.comm.vo.comment.PlaceCmtScoreVO;
import com.lvmama.comm.vo.comment.ProductCmtCommentVO;
import com.lvmama.comm.vo.enums.PlacePhotoTypeEnum;
import com.lvmama.comm.vst.service.search.VstClientPlaceService;
import com.lvmama.comm.vst.service.search.VstClientProductService;

public class AppServiceImpl implements AppService {
	private static final Log log = LogFactory.getLog(AppServiceImpl.class);
	private String CLUTTER_MOBILE_OFFLINE_CITY_TREE_CACHE = "CLUTTER_MOBILE_OFFLINE_CITY_TREE_CACHE";
	protected final static String HOT_CITY = "北京,上海,南京,杭州,成都,广州,三亚";
	
	
	/**
	 * 目的地服务
	 */
	protected PlaceService placeService;
	/**
	 * 图片服务
	 */
	protected PlacePhotoService placePhotoService;

	/**
	 * 产品相关查询
	 */
	protected RecommendInfoClient recommendInfoClient;

	/**
	 * 产品服务
	 */
	protected ProductSearchInfoService productSearchInfoService;

	/**
	 * 页面展示
	 */
	protected ViewPageService viewPageService;

	protected ProdProductService prodProductService;

	/**
	 * 自由行行程
	 */
	protected ViewPageJourneyService viewPageJourneyService;

	protected DicCommentLatitudeService dicCommentLatitudeService;

	protected MarkCouponService markCouponService;

	protected CmtCommentService cmtCommentService;

	protected PlaceCityService placeCityService;

	/**
	 * 订单服务
	 */
	protected OrderService orderServiceProxy;

	/**
	 * sso服务
	 */
	protected UserUserProxy userUserProxy;

	protected ProductServiceProxy productServiceProxy;

	protected ProdProductPlaceService prodProductPlaceService;

	protected ProdProductBranchService prodProductBranchService;

	protected CmtLatitudeStatistisService cmtLatitudeStatistisService;

	protected PlaceSearchInfoService placeSearchInfoService;

	protected ComPictureService comPictureService;

	protected MetaProductService metaProductService;

	protected MetaProductBranchService metaProductBranchService;

	protected PageService pageService;

	protected CmtTitleStatistisService cmtTitleStatistisService;

	/**
	 * 意见反馈
	 * 
	 * @param list
	 * @return
	 */
	protected ComUserFeedbackService comUserFeedbackService;

	protected GroupDreamService groupDreamService;

	String COMMENT_SHARE_TEMPLATE = "我刚在 @驴妈妈旅游网，点评了“%s”，点评具体内容 %s";

	protected ComClientService comClientService;

	protected PlaceCoordinateBaiduService placeCoordinateBaiduService;

	protected PlaceCoordinateGoogleService placeCoordinateGoogleService;

	/**
	 * 推荐信息查询
	 */
	protected RecommendInfoService recommendInfoService;

	protected FavorService favorService;

	protected IReceiverUserService receiverUserService;

	//vst接口
	protected VstClientProductService vstClientProductService;

	protected VstClientPlaceService vstClientPlaceService;

	protected PlacePlaceDestService placePlaceDestService;


	protected OrdEContractService ordEContractService;

	protected FavorOrderService favorOrderService;

	protected MobileClientService mobileClientService;

	//火车票
	protected MobileHotelService mobileHotelService;
	protected ProdTrainCacheService prodTrainCacheService;
	protected ProdTrainService prodTrainService;
	
	protected MobileTrainService mobileTrainService;

	protected FSClient fsClient;

	protected UserCooperationUserService userCooperationUserService;

	protected ProdProductRoyaltyService prodProductRoyaltyService;
	
	
	public boolean isCheckVersion(Map<String, Object> param) {
		if (param.get("checkVersion") != null) {
			return true;
		}
		return false;
	}

	public Map<String, Object> resultVersionCheck(
			Map<String, Object> resultMap, Map<String, Object> param) {
		JSONObject json = JSONObject.fromObject(resultMap);
		try {
			String version = MD5.encode(json.toString());
			resultMap.put("version", version);
			if (this.isCheckVersion(param)) {
				return this.getVersion(resultMap);
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultMap;
	}

	public Map<String, Object> getVersion(Map<String, Object> param) {
		Map<String, Object> versionMap = new HashMap<String, Object>();
		versionMap.put("version", param.get("version"));
		return versionMap;
	}

	@Override
	public ClientPlace getPlaceDetails(Long placeId) {
		Place place = this.placeService.queryPlaceByPlaceId(Long
				.valueOf(placeId));
		if (place == null) {
			return null;
		}
		PlacePhoto pp = new PlacePhoto();
		pp.setType(PlacePhotoTypeEnum.LARGE.name());
		pp.setPlaceId(place.getPlaceId());
		List<PlacePhoto> ppList = this.placePhotoService.queryByPlacePhoto(pp);
		ClientPlace cp = new ClientPlace();
		BeanUtils.copyProperties(place, cp);
		cp.setId(place.getPlaceId());
		// cp.setHotelFacility(place.getHotelFacilities());新度假酒店place中不包含酒店信息

		cp.setHotelStar(place.getHotelStar());
		cp.setPlaceMainTitel(place.getFirstTopic());
		cp.setPlaceTitel(place.getScenicSecondTopic());
		cp.setRecommendReason(StringUtil.filterOutHTMLTags(place.getRemarkes()));
		cp.setDescription(StringUtil.filterOutHTMLTags(place.getDescription()));

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("placeId", placeId);
		List<PlaceCoordinateVo> list = placeCoordinateBaiduService
				.getBaiduMapListByParams(param);

		if (list != null && !list.isEmpty()) {
			PlaceCoordinateVo pcb = list.get(0);
			if (pcb.getLatitude() != null) {
				cp.setLatitude(pcb.getLatitude().floatValue());
			}
			if (pcb.getLongitude() != null) {
				cp.setLongitude(pcb.getLongitude().floatValue());
			}

		}

		if ("3".equals(place.getStage())) {
			cp.setStartTime(place.getHotelOpenTime());
			if (place.getHotelRoomNum() != null) {
				cp.setRoomNum(place.getHotelRoomNum().intValue());
			}
			// cp.setAirport(place.getHotelTrafficInfo());新度假酒店place中不包含酒店信息
			cp.setPhone(place.getHotelPhone());
		} else if ("2".equals(place.getStage())) {
			cp.setRecommendTime(place.getScenicRecommendTime());
			cp.setStartTime(place.getScenicOpenTime());
		}

		PlaceSearchInfo psi = placeSearchInfoService
				.getPlaceSearchInfoByPlaceId(Long.valueOf(placeId));
		if (!cp.getStage().equals("1")) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("placeId", placeId);
			List<CmtLatitudeStatistics> cmtLatitudeStatisticsList = cmtLatitudeStatistisService
					.getLatitudeStatisticsList(parameters);
			List<PlaceCmtScoreVO> pcsVoList = new ArrayList<PlaceCmtScoreVO>();
			for (CmtLatitudeStatistics cmtLatitudeStatistics : cmtLatitudeStatisticsList) {
				PlaceCmtScoreVO pcv = new PlaceCmtScoreVO();
				pcv.setName(cmtLatitudeStatistics.getLatitudeName());
				pcv.setScore(cmtLatitudeStatistics.getAvgScore() + "");
				if (cmtLatitudeStatistics.getLatitudeId().equals(
						"FFFFFFFFFFFFFFFFFFFFFFFFFFFF")) {
					pcv.setMain(true);
				}
				pcsVoList.add(pcv);
			}

			cp.setPlaceCmtScoreList(pcsVoList);
		}

		if (!"1".equals(cp.getStage())) {
			List<String> imageList = new ArrayList<String>();
			if (ppList != null && ppList.size() != 0) {
				for (PlacePhoto placePhoto : ppList) {
					if (!StringUtil.isEmptyString(placePhoto.getImagesUrl())) {
						imageList.add(placePhoto.getImagesUrl());
					}
				}
			}
			cp.setImageList(imageList);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		if ("2".equals(cp.getStage())) {
			map.put("productType", "TICKET");
		} else if ("3".equals(cp.getStage())) {
			map.put("productType", "HOTEL");
		}
		map.put("productIds", "true");
		map.put("placeId", placeId);
		map.put("channel", Constant.CHANNEL.FRONTEND.name());
		Long count = productSearchInfoService
				.countProductSearchInfoByParam(map);
		if (count > 0) {
			cp.setHasProduct(true);
		}

		map.put("productType", Constant.PRODUCT_TYPE.ROUTE.name());
		map.put("subProductType", Constant.SUB_PRODUCT_TYPE.FREENESS.name());

		Long freenessCount = productSearchInfoService
				.countProductSearchInfoByParam(map);

		if (freenessCount > 0) {
			cp.setHasFreeness(true);
		}

		if (psi != null && psi.getProductsPrice() != null) {
			cp.setSellPrice(Long.valueOf(psi.getProductsPrice()));
		}

		cp.setCmtNum(String.valueOf(psi.getCmtNum()));
		cp.setMarketPrice(psi.getMarketPrice());
		return cp;
	}

	@Override
	public List<?> getProductList(Long placeId, String stage) {
		List<ClientProduct> list = new ArrayList<ClientProduct>();
		if ("3".equals(stage)) {
			List<ProductSearchInfo> searchInfoList = productSearchInfoService
					.getProductHotelByPlaceIdAndType(placeId, 1000, "FRONTEND",
							false);

			for (ProductSearchInfo viewProductSearchInfo : searchInfoList) {
				if (Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT.name().equals(
						viewProductSearchInfo.getSubProductType())) {
					ProdHotel ph = prodProductService
							.getProdHotelById(viewProductSearchInfo
									.getProductId());
					ClientUtils.copyHotelSuitProductList(viewProductSearchInfo,
							ph, list, Constant.PRODUCT_TYPE.HOTEL.name());
				} else {
					ClientUtils
							.copyProductList(viewProductSearchInfo
									.getProdBranchSearchInfoList(), list,
									Constant.PRODUCT_TYPE.HOTEL.name());
				}
			}
		} else {
			ProductList prodList = productSearchInfoService
					.getIndexProductByPlaceIdAnd4TypeAndTicketBranch(placeId,
							100L, "FRONTEND");
			ClientUtils.copyProductList(prodList.getProdBranchTicketList(),
					list, Constant.PRODUCT_TYPE.TICKET.name());
		}
		return list;
	}

	public List<ClientProduct> queryGroupOnListForSh() {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("toDest", "上海");
		map.put("channel", "TUANGOU");
		List<ProductSearchInfo> list = productSearchInfoService
				.queryProductSearchInfoByParam(map);
		List<ClientProduct> cpList = new ArrayList<ClientProduct>();
		for (ProductSearchInfo productSearchInfo : list) {
			ClientUtils.copyProductSearchInfo(productSearchInfo, cpList);
		}
		return cpList;

	}

	@Override
	public Map<String, Object> queryGroupProductList(Long placeId, String stage) {
		ProductList prodList = productSearchInfoService
				.getProductByPlaceIdAnd4Type(placeId, 100L, "FRONTEND");
		Map<String, Object> allElementMap = new HashMap<String, Object>();
		List<Map<String, Object>> singleList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> suitList = new ArrayList<Map<String, Object>>();
		if ("2".equals(stage)) {
			List<Map<String, Object>> unionList = new ArrayList<Map<String, Object>>();
			if (prodList.isNotNull()) {
				List<ProductSearchInfo> ticktProdList = prodList
						.getProductTicketList();
				for (ProductSearchInfo productSearchInfo : ticktProdList) {
					if (productSearchInfo.hasAperiodic()) {
						/**
						 * 过滤期票
						 */
						continue;
					}
					if (ActivityUtil.getInstance().checkActivityIsValid(
							Constant.ACTIVITY_FIVE_YEAR)) {
						if (ClutterConstant.getFifthSeckillProductIds()
								.contains(
										productSearchInfo.getProductId()
												.toString())) {
							continue;
						}
					}
					if (productSearchInfo.getSubProductType().equals(
							Constant.SUB_PRODUCT_TYPE.SINGLE.name())
							|| productSearchInfo.getSubProductType().equals(
									Constant.SUB_PRODUCT_TYPE.WHOLE.name())) {
						List<ClientProduct> clientProductSingleList = new ArrayList<ClientProduct>();
						this.addBranchElement(clientProductSingleList,
								productSearchInfo, singleList);
					}

					if (productSearchInfo.getSubProductType().equals(
							Constant.SUB_PRODUCT_TYPE.UNION.name())) {
						List<ClientProduct> clientProductUnionList = new ArrayList<ClientProduct>();
						this.addBranchElement(clientProductUnionList,
								productSearchInfo, unionList);
					}

					if (productSearchInfo.getSubProductType().equals(
							Constant.SUB_PRODUCT_TYPE.SUIT.name())) {
						List<ClientProduct> clientProductSuitList = new ArrayList<ClientProduct>();
						this.addBranchElement(clientProductSuitList,
								productSearchInfo, suitList);
					}

				}
			}
			allElementMap.put("single", singleList);
			allElementMap.put("union", unionList);
			allElementMap.put("suit", suitList);
			return allElementMap;
		} else {
			List<ClientProduct> clientProductsuitList = new ArrayList<ClientProduct>();
			List<ProductSearchInfo> ticktProdList = prodList
					.getProductHotelList();
			List<ClientProduct> clientProductSingleList = new ArrayList<ClientProduct>();

			for (ProductSearchInfo productSearchInfo : ticktProdList) {
				if (productSearchInfo.getSubProductType().equals(
						Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name())) {
					List<ProdProductBranch> ppbList = prodProductService
							.getProductBranchByProductId(
									productSearchInfo.getProductId(), "false");
					ClientUtils.copyProductBranchList(ppbList,
							clientProductSingleList,
							productSearchInfo.getProductType(),
							productSearchInfo.getSubProductType());

				}

				if (productSearchInfo.getSubProductType().equals(
						Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT.name())) {

					ClientUtils.copyProductSearchInfo(productSearchInfo,
							clientProductsuitList);

				}
			}

			for (ClientProduct clientProduct : clientProductsuitList) {
				ProdHotel ph = prodProductService
						.getProdHotelById(clientProduct.getProductId());
				clientProduct.setDays(ph.getDays() + "");
			}

			// 酒店直接取所有产品的所有类别
			allElementMap.put("single", clientProductSingleList);
			allElementMap.put("suit", clientProductsuitList);
			return allElementMap;
		}
	}

	private void addBranchElement(List<ClientProduct> clientProductList,
			ProductSearchInfo productSearchInfo, List<Map<String, Object>> list) {

		List<ProdProductBranch> ppbList = prodProductService
				.getProductBranchByProductId(productSearchInfo.getProductId(),
						"false");

		ClientUtils.copyProductBranchList(ppbList, clientProductList,
				productSearchInfo.getProductType(),
				productSearchInfo.getSubProductType());

		// 查询当前分类是否支持当天预订 只对单门票有效
		if (productSearchInfo.getProductType().equals(
				Constant.PRODUCT_TYPE.TICKET.name())) {

			for (ClientProduct clientProduct : clientProductList) {
				if (clientProduct.isCanOrderToday()) {
					boolean flag2 = prodProductBranchService
							.checkPhoneOrderTime(Long.valueOf(clientProduct
									.getBranchId()));
					TimePrice tp = prodProductBranchService
							.calcCurrentProdTimePric(
									Long.valueOf(clientProduct.getBranchId()),
									DateUtil.getDayStart(new Date()));
					if (tp == null) {
						continue;
					}
					clientProduct.setCanOrderTodayCurrentTime(flag2);

					List<MetaProductBranch> mpbList = metaProductBranchService
							.getMetaProductBranchByProdBranchId(Long
									.valueOf(clientProduct.getBranchId()));

					Long lastTicketTime = null;
					Long LastPassTime = null;
					boolean isPayToLvmm = false;
					boolean isPayToSupplier = false;
					for (MetaProductBranch metaProductBranch : mpbList) {
						MetaProductBranch mpb = metaProductBranch;
						MetaProductTicket mp = (MetaProductTicket) metaProductService
								.getMetaProduct(mpb.getMetaProductId(),
										Constant.PRODUCT_TYPE.TICKET.name());
						if (mp == null) {
							continue;
						}
						isPayToLvmm = mp.isPaymentToLvmama();
						isPayToSupplier = mp.isPaymentToSupplier();

						if (lastTicketTime != null) {
							if (mp.getLastTicketTime() >= lastTicketTime) {
								lastTicketTime = mp.getLastTicketTime();
							}
						} else {
							lastTicketTime = mp.getLastTicketTime();
						}

						if (LastPassTime != null) {
							if (mp.getLastPassTime() >= LastPassTime) {
								LastPassTime = mp.getLastPassTime();
							}
						} else {
							LastPassTime = mp.getLastPassTime();
						}
					}
					if (lastTicketTime != null && lastTicketTime != null) {
						String leatestOrderTime = "";

						Float lastTicketTimeHour = lastTicketTime / 60f;
						String todayOrderTips = "";
						if (isPayToLvmm == false && isPayToSupplier == true
								&& lastTicketTimeHour == 0.0f) {
							leatestOrderTime = ClientUtils.getLeatestOrder(
									tp.getLatestUseTimeDate(), lastTicketTime,
									LastPassTime, 0L);
							todayOrderTips = String.format("最晚%s可订",
									leatestOrderTime);
						} else if (isPayToLvmm == false
								&& isPayToSupplier == true) {
							leatestOrderTime = ClientUtils.getLeatestOrder(
									tp.getLatestUseTimeDate(), lastTicketTime,
									LastPassTime, 0L);
							todayOrderTips = String.format(
									"①至少提前%s小时  ② 最晚%s可订", lastTicketTimeHour,
									leatestOrderTime);
						} else if (isPayToLvmm == true) {
							leatestOrderTime = ClientUtils.getLeatestOrder(
									tp.getLatestUseTimeDate(), lastTicketTime,
									LastPassTime, 60 * 30);
							/**
							 * 如果是支付给驴妈妈必须加上0.5h的支付等待时间
							 */
							lastTicketTimeHour = lastTicketTimeHour + 0.5f;
							todayOrderTips = String.format(
									"①至少提前%s小时  ② 最晚%s可订", lastTicketTimeHour,
									leatestOrderTime);
						}
						clientProduct.setTodayOrderTips(todayOrderTips);
					}

				}

			}
		}
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("productName", productSearchInfo.getProductName());
		obj.put("datas", clientProductList);
		list.add(obj);
	}

	public ClientProduct getProductDetails(Long productId) {
		Long pid = Long.valueOf(productId);
		ViewPage viewPage = viewPageService.selectByProductId(pid);
		if (viewPage == null) {
			return null;
		}
		ViewPage vp = viewPageService.getViewPage(viewPage.getPageId());
		if (vp == null) {
			return null;
		}
		ProdProduct pp = prodProductService.getProdProductById(productId);

		List<ViewJourney> vj = viewPageJourneyService
				.getViewJourneysByProductId(productId);
		ClientProduct cp = new ClientProduct();
		BeanUtils.copyProperties(pp, cp);
		if (pp.isHotel()) {
			ProdHotel ph = prodProductService.getProdHotelById(productId);
			cp.setDays(ph.getDays() + "");
		}
		ProdProductBranch defaultBranch = prodProductService
				.getProductDefaultBranchByProductId(productId);
		if (defaultBranch != null) {
			cp.setBranchId(defaultBranch.getProdBranchId() + "");
		}
		for (int i = 0; i < vp.getContentList().size(); i++) {

			ViewContent viewContent = vp.getContentList().get(i);

			if (viewContent.getContentType().equals(
					Constant.VIEW_CONTENT_TYPE.ACITONTOKNOW.name())) {

			}
			if (viewContent.getContentType().equals(
					Constant.VIEW_CONTENT_TYPE.ANNOUNCEMENT.name())) {
				cp.setAnnouncement(StringUtil.filterOutHTMLTags(viewContent
						.getContent()));
			}

			if (viewContent.getContentType().equals(
					Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name())) {
				cp.setCostcontain(StringUtil.filterOutHTMLTags(viewContent
						.getContent()));
			}

			if (viewContent.getContentType().equals(
					Constant.VIEW_CONTENT_TYPE.FEATURES.name())) {
				cp.setFauture(StringUtil.filterOutHTMLTags(viewContent
						.getContent()));
			}

			if (viewContent.getContentType().equals(
					Constant.VIEW_CONTENT_TYPE.IMPORTMENTCLEW.name())) {
				cp.setImportmentclew(StringUtil.filterOutHTMLTags(viewContent
						.getContent()));
			}

			if (viewContent.getContentType().equals(
					Constant.VIEW_CONTENT_TYPE.MANAGERRECOMMEND.name())) {
				cp.setManagerRecommend(StringUtil.filterOutHTMLTags(viewContent
						.getContent()));
			}

			if (viewContent.getContentType().equals(
					Constant.VIEW_CONTENT_TYPE.ORDERTOKNOWN.name())) {
				cp.setOrderToKnow(StringUtil.filterOutHTMLTags(viewContent
						.getContent()));
			}

			if (viewContent.getContentType().equals(
					Constant.VIEW_CONTENT_TYPE.REFUNDSEXPLANATION.name())) {
				cp.setRefundSexPlanation(StringUtil
						.filterOutHTMLTags(viewContent.getContent()));
			}

			if (viewContent.getContentType().equals(
					Constant.VIEW_CONTENT_TYPE.SERVICEGUARANTEE.name())) {
				cp.setServiceGuarantee(StringUtil.filterOutHTMLTags(viewContent
						.getContent()));
			}

			if (viewContent.getContentType().equals(
					Constant.VIEW_CONTENT_TYPE.SERVICEGUARANTEE.name())) {
				cp.setShoppingExplain(StringUtil.filterOutHTMLTags(viewContent
						.getContent()));
			}
			if (viewContent.getContentType().equals(
					Constant.VIEW_CONTENT_TYPE.ACITONTOKNOW.name())) {
				cp.setActionToKnow(StringUtil.filterOutHTMLTags(viewContent
						.getContent()));
			}
			if (viewContent.getContentType().equals(
					Constant.VIEW_CONTENT_TYPE.RECOMMENDPROJECT.name())) {
				cp.setRecommendProject(StringUtil.filterOutHTMLTags(viewContent
						.getContent()));
			}
		}

		List<ComPicture> list = this.comPictureService.getPictureByPageId(vp
				.getPageId());
		List<String> imageList = new ArrayList<String>();
		if (list != null) {
			for (ComPicture comPicture : list) {
				imageList.add(comPicture.getPictureUrl());
			}
		}

		cp.setImageList(imageList);

		List<ClientViewJouney> cvjList = new ArrayList<ClientViewJouney>();
		if (!vj.isEmpty()) {
			for (ViewJourney viewJourney : vj) {
				viewJourney.setPlaceList(null);
				viewJourney.setProdTargetId(null);
				ClientViewJouney cvj = new ClientViewJouney();
				cvj.setContent(viewJourney.getContent());
				cvj.setTitle(viewJourney.getTitle());
				cvj.setSeq(viewJourney.getSeq());
				cvj.setJourneyId(viewJourney.getJourneyId());
				cvj.setDinner(viewJourney.getDinner());
				cvj.setHotel(viewJourney.getHotel());
				cvjList.add(cvj);
			}
		}
		cp.setViewJouneyList(cvjList);
		return cp;

	}

	public List<ClientTimePrice> getMainProdTimePrice(Long productId,
			Long branchId) {
		if (branchId == null) {
			List<ProdProductBranch> list = prodProductService
					.getProductBranchByProductId(productId, "false");
			ProdProductBranch mainProductBranch = list.get(0);
			branchId = mainProductBranch.getProdBranchId();
		}
		List<ClientTimePrice> ctpList = new ArrayList<ClientTimePrice>();
		if (branchId != null) {
			List<TimePrice> timePriceList = prodProductService
					.getMainProdTimePrice(productId, branchId);

			for (TimePrice timePrice : timePriceList) {
				if (timePrice.isSellable(1L)) {
					ClientTimePrice ctp = new ClientTimePrice();
					ctp.setDate(DateUtil.getFormatDate(timePrice.getSpecDate(),
							"yyyy-MM-dd"));
					ctp.setPrice(timePrice.getPriceF());
					ctp.setMarketPrice(timePrice.getMarketPriceF() + "");
					ctpList.add(ctp);
				}

			}

		}

		return ctpList;

	}

	public void insertShareLog(String uid, String screenName,
			String shareChannel, String shareTarget) {
		ComClientLog ccl = new ComClientLog();
		ccl.setVisitTime(new Date());
		ccl.setDeviceName(shareTarget);
		ccl.setUserId(uid);
		ccl.setOsname(screenName);
		ccl.setChannel(shareChannel);
		ccl.setCreateTime(new Date());
		comClientService.insert(ccl);
	}

	private void covertToClientProduct(ClientProduct vps,
			ProdProductBranch prodBranches, ProdProduct prodProduct) {
		vps.setProductId(prodBranches.getProductId());
		vps.setProdBranchId(prodBranches.getProdBranchId());
		vps.setShortName(prodBranches.getBranchName());
		vps.setCouponAble(prodBranches.getProdProduct().getCouponAble());
		vps.setProductName(prodBranches.getProdProduct().getProductName());
		vps.setMinimum(prodBranches.getMinimum());
		vps.setMaximum(prodBranches.getMaximum());
		vps.setPayToSupplier(prodProduct.getPayToSupplier());
		vps.setPayToLvmama(prodProduct.getPayToLvmama());
		vps.setSellPrice(prodBranches.getSellPrice());
		vps.setMarketPrice(prodBranches.getMarketPrice());
		vps.setAdultQuantity(prodBranches.getAdultQuantity());
		vps.setChildQuantity(prodBranches.getChildQuantity());
		vps.setProductType(prodProduct.getProductType());
		vps.setSubProductType(prodProduct.getSubProductType());
		vps.setBranchId(prodBranches.getProdBranchId() + "");
		vps.setCanOrderToday(Boolean.valueOf(prodBranches.getTodayOrderAble()));
	}

	private void convertToClientProduct(ClientProduct vps,
			ProdProductRelation prodProductRelation, ProdProduct prodProduct) {
		vps.setProductId(prodProductRelation.getProductId());
		vps.setProdBranchId(prodProductRelation.getProdBranchId());
		vps.setShortName(prodProductRelation.getBranch().getBranchName());
		// vps.setProductName(prodProductRelation.get);
		vps.setMinimum(prodProductRelation.getBranch().getMinimum());
		vps.setMaximum(prodProductRelation.getBranch().getMaximum());
		vps.setPayToSupplier(prodProduct.getPayToSupplier());
		vps.setPayToLvmama(prodProduct.getPayToLvmama());
		vps.setSellPrice(prodProductRelation.getBranch().getSellPrice());
		vps.setMarketPrice(prodProductRelation.getBranch().getMarketPrice());
		vps.setAdultQuantity(prodProductRelation.getBranch().getAdultQuantity());
		vps.setChildQuantity(prodProductRelation.getBranch().getChildQuantity());
		vps.setSaleNumType(prodProductRelation.getSaleNumType());
		vps.setBranchId(prodProductRelation.getProdBranchId() + "");
		vps.setIsAddtional("true");
	}

	public List<ClientProduct> queryTimePriceByProductIdAndDateForTodayPhoneOrder(
			Long productId, Long branchId, Date visitDate) {
		List<ProdProductBranch> listBranches = prodProductService
				.getProductBranchByProductId(productId, "false");
		List<ClientProduct> vpsiList = new ArrayList<ClientProduct>();
		ProdProduct prodProduct = prodProductService
				.getProdProductById(productId);
		if (prodProduct != null) {
			for (ProdProductBranch branches : listBranches) {
				ProdProductBranch prodBranches = this.prodProductService
						.getPhoneProdBranchDetailByProdBranchId(
								branches.getProdBranchId(),
								DateUtil.getDayStart(new Date()), true);
				if (null == prodBranches) {
					continue;
				}
				boolean flag = prodProductBranchService
						.checkPhoneOrderTime(prodBranches.getProdBranchId());
				if (flag) {
					ClientProduct vps = new ClientProduct();
					this.covertToClientProduct(vps, prodBranches, prodProduct);
					vpsiList.add(vps);
				}
			}
		}
		return vpsiList;
	}

	public List<ClientProduct> querySingleRoomProductBranchByBrachId(
			ProdProduct prod, Long branchId, Date visitDate) {

		List<ClientProduct> vpsiList = new ArrayList<ClientProduct>();
		// List<ProdProductBranch> listBranches =
		// prodProductService.getProductBranchByProductId(prod.getProductId(),
		// "false");
		ProdProduct prodProduct = prodProductService.getProdProductById(prod
				.getProductId());

		ProdProductBranch ppb = prodProductBranchService
				.getProductBranchDetailByBranchId(branchId, visitDate, true);
		if (null == ppb) {
			return vpsiList;
		}

		ClientProduct vps = new ClientProduct();
		this.covertToClientProduct(vps, ppb, prodProduct);
		vpsiList.add(vps);

		// 获得附加产品
		List<ProdProductRelation> relateList = this.prodProductService
				.getRelatProduct(Long.valueOf(prod.getProductId()), visitDate);

		for (ProdProductRelation prodProductRelation : relateList) {
			ClientProduct advps = new ClientProduct();
			this.convertToClientProduct(advps, prodProductRelation, prod);
			advps.setAdditional(true);
			vpsiList.add(advps);
		}

		return vpsiList;
	}

	public List<ClientProduct> queryTimePriceByProductIdAndDate(
			ProdProduct prod, final Long branchId, Date visitDate, String udid) {
		List<ProdProductBranch> listBranches = prodProductService
				.getProductBranchByProductId(prod.getProductId(), "false");
		List<ClientProduct> vpsiList = new ArrayList<ClientProduct>();

		// 门票取类别
		if (prod != null
				&& Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(
						prod.getSubProductType())) {

			if (null != listBranches && !listBranches.isEmpty()) {

				ProdProductBranch prodBrancheVisit = this.prodProductService
						.getProdBranchDetailByProdBranchId(branchId, visitDate,
								true);

				if (prodBrancheVisit != null) {
					ClientProduct vps = new ClientProduct();
					this.covertToClientProduct(vps, prodBrancheVisit, prod);
					vpsiList.add(vps);

				}

			}
		} else {

			ProdHotel ph = null;

			// 如果是酒店套餐需要取套餐包含的days
			if (prod.isHotel()
					&& Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT.name().equals(
							prod.getSubProductType())) {
				ph = prodProductService.getProdHotelById(prod.getProductId());

			}

			for (ProdProductBranch branches : listBranches) {
				ProdProductBranch prodBranches = this.prodProductService
						.getProdBranchDetailByProdBranchId(
								branches.getProdBranchId(), visitDate, true);

				if (null == prodBranches) {
					continue;
				}

				ClientProduct vps = new ClientProduct();
				this.covertToClientProduct(vps, prodBranches, prod);
				if (ph != null) {
					vps.setDays(String.valueOf(ph.getDays()));
				}
				vpsiList.add(vps);
			}

		}
		// 获得附加产品
		List<ProdProductRelation> relateList = this.prodProductService
				.getRelatProduct(Long.valueOf(prod.getProductId()), visitDate);
		for (ProdProductRelation prodProductRelation : relateList) {
			ClientProduct vps = new ClientProduct();
			this.convertToClientProduct(vps, prodProductRelation, prod);
			vps.setAdditional(true);
			vpsiList.add(vps);
		}

		// //按照传入的brachId进行排序
		// Collections.sort(vpsiList,new Comparator<ClientProduct>(){
		// @Override
		// public int compare(ClientProduct arg0, ClientProduct arg1) {
		// if(!arg0.getBranchId().equals(Long.valueOf(branchId))){
		// return -1;
		// } else if(!arg1.getBranchId().equals(Long.valueOf(branchId))){
		// return 1;
		// } else {
		// return 0;
		// }
		//
		// }
		// });

		return vpsiList;

	}

	public Map<String, Object> defaultParamters(Long page) {
		HashMap<String, Object> paramters = new HashMap<String, Object>();
		paramters.put("pageSize", 10L);
		paramters.put("currentPage", page);
		return paramters;
	}

	public Map<String, Object> queryCommentByUserId(String userId, Long page,
			String cmtType) {
		Map<String, Object> paramters = this.defaultParamters(page);
		UserUser user = userUserProxy.getUserUserByUserNo(userId);
		String[] isAudits = {
				com.lvmama.comm.vo.Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS
						.name(),
				com.lvmama.comm.vo.Constant.CMT_AUDIT_STATUS.AUDIT_GOING.name(),
				com.lvmama.comm.vo.Constant.CMT_AUDIT_STATUS.AUDIT_FAILED
						.name() };
		paramters.put("isAudits", isAudits);

		paramters.put("cmtType", cmtType);
		paramters.put("userId", user.getId());
		paramters.put("createTime321", "true");
		Page<CommonCmtCommentVO> pageConfig = this.cmtCommentService
				.queryCmtCommentListForApp(paramters);
		List<ClientCmtPlace> cmtList = new ArrayList<ClientCmtPlace>();

		for (CommonCmtCommentVO cmtCommentVO : pageConfig.getItems()) {
			ClientCmtPlace ccp = new ClientCmtPlace();
			this.covertComment(ccp, cmtCommentVO);
			// 如果是取用户的点评直接去点评他自己评的总分
			if (cmtCommentVO.getSumaryLatitude() != null) {
				ccp.setAvgScore(cmtCommentVO.getSumaryLatitude().getScore()
						+ "");
				cmtList.add(ccp);
			}

		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("list", cmtList);
		resultMap.put("isLastPage", this.isLastPage(pageConfig));
		return resultMap;
	}

	private void copyGroupon(ProductSearchInfo psi, List<ClientGroupon2> cglist) {

		Map<String, Object> returnMap = groupDreamService
				.getTodayGroupProduct(psi.getProductId());
		if (returnMap != null) {

			ClientGroupon2 cg = new ClientGroupon2();
			cg.setProductId(psi.getProductId() + "");
			cg.setProductName(psi.getProductName());
			cg.setMarketPrice(psi.getMarketPrice() + "");
			cg.setSellPrice(psi.getSellPrice() + "");
			cg.setSmallImage(psi.getSmallImage());
			cg.setOfflineTime(DateUtil.getFormatDate(psi.getOfflineTime(),
					"yyyy-MM-dd HH:mm:ss"));
			cg.setOrderCount(returnMap.get("orderCount").toString());
			cg.setMinGroupSize(returnMap.get("MIN_GROUP_SIZE").toString());

			cg.setProductType(psi.getProductType());
			cg.setManagerRecommend(StringUtil.filterOutHTMLTags(String
					.valueOf(returnMap.get("MANAGERRECOMMEND"))));

			if (returnMap.get("pageId") != null) {

				List<ComPicture> list = comPictureService
						.getPictureByPageId((Long) returnMap.get("pageId"));
				List<ClientPicture> cpList = new ArrayList<ClientPicture>();
				for (ComPicture comPicture : list) {
					ClientPicture clientp = new ClientPicture();
					BeanUtils.copyProperties(comPicture, clientp);
					cpList.add(clientp);
				}
				cg.setPictureList(cpList);
			}
			cglist.add(cg);
		}
	}

	public List<ClientGroupon2> queryGrouponList() {
		List<RecommendInfo> intoList = recommendInfoService
				.getRecommendInfoByBlockId(Long.valueOf(ClutterConstant
						.getGroupOnRecommendIdForSh()), null);

		List<ProductSearchInfo> list = new ArrayList<ProductSearchInfo>();

		for (RecommendInfo recommendInfo : intoList) {
			if ("true".equals(recommendInfo.getStatus())) {
				Long productId = Long
						.valueOf(recommendInfo.getRecommObjectId());
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("productId", productId);

				List<ProductSearchInfo> returnlist = productSearchInfoService
						.queryProductSearchInfoByParam(param);
				if (!returnlist.isEmpty()) {
					list.addAll(returnlist);
				}

			}
		}

		List<ClientGroupon2> cglist = new ArrayList<ClientGroupon2>();
		for (ProductSearchInfo productSearchInfo : list) {
			this.copyGroupon(productSearchInfo, cglist);
		}
		return cglist;
	}

	public ClientCommitCmtResult commitComments(String userId,
			List<ClientCmtLatitude> ccList, String objectId, String content,
			String cmtType, String firstChannel) {
		try {
			String shareContent = "";
			UserUser user = userUserProxy.getUserUserByUserNo(userId);
			if (user != null) {
				CommonCmtCommentVO comment = new CommonCmtCommentVO();
				comment.setCmtType(Constant.EXPERIENCE_COMMENT_TYPE);
				comment.setContent(content);
				if (cmtType.equals(Constant.EXPERIENCE_COMMENT_TYPE)) {
					OrdOrder orderOrder = orderServiceProxy
							.queryOrdOrderByOrderId(Long.valueOf(objectId));
					Long productId = orderOrder.getMainProduct().getProductId();
					Long destId = prodProductPlaceService
							.selectDestByProductId(productId);
					comment.setProductId(productId);
					comment.setPlaceId(destId);
					comment.setOrderId(Long.valueOf(objectId));
					comment.setCmtType(com.lvmama.comm.vo.Constant.EXPERIENCE_COMMENT_TYPE);
					shareContent = String.format(COMMENT_SHARE_TEMPLATE,
							orderOrder.getMainProduct().getProductName(),
							content);
				} else {
					Place place = this.placeService.queryPlaceByPlaceId(Long
							.valueOf(objectId));
					shareContent = String.format(COMMENT_SHARE_TEMPLATE,
							place.getName(), content);
					comment.setPlaceId(Long.valueOf(objectId));
					comment.setCmtType(com.lvmama.comm.vo.Constant.COMMON_COMMENT_TYPE);
				}

				if (firstChannel != null) {
					comment.setChannel(firstChannel);
				}
				String commentId = this.commitComment(user, comment, ccList);
				shareContent = com.lvmama.comm.utils.StringUtil.subStringStr(
						shareContent, 140);
				String shareUrl = Constant.WWW_HOST + "/comment/" + commentId;

				ClientCommitCmtResult result = new ClientCommitCmtResult();
				result.setCommentId(commentId);
				result.setShareContent(shareContent);
				result.setShareUrl(shareUrl);
				return result;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public String commitComment(UserUser users, CommonCmtCommentVO comment,
			List<ClientCmtLatitude> cmtLatitudeList) {
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

	/**
	 * 根据用户ID 查询我点评的未审核的点评数据
	 * 
	 * @return
	 */
	private List<ClientCmtPlace> queryMyComment(UserUser user, Long placeId) {
		if (user == null) {
			return null;
		}
		Map<String, Object> paramters = this.defaultParamters(1L);
		paramters.put("pageSize", 100L);
		paramters.put("currentPage", "1");
		String[] isAudits = {
				com.lvmama.comm.vo.Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS
						.name(),
				com.lvmama.comm.vo.Constant.CMT_AUDIT_STATUS.AUDIT_GOING.name(),
				com.lvmama.comm.vo.Constant.CMT_AUDIT_STATUS.AUDIT_FAILED
						.name() };
		paramters.put("isAudits", isAudits);
		paramters.put("userId", user.getId());
		paramters.put("placeId", placeId);
		paramters.put("isHid", "N");
		paramters.put("createTime321", "true");
		Page<CommonCmtCommentVO> pageConfig = this.cmtCommentService
				.queryCmtCommentListForApp(paramters);
		List<ClientCmtPlace> cmtList = new ArrayList<ClientCmtPlace>();
		for (CommonCmtCommentVO cmtCommentVO : pageConfig.getItems()) {
			ClientCmtPlace ccp = new ClientCmtPlace();
			this.covertComment(ccp, cmtCommentVO);
			if (cmtCommentVO.getSumaryLatitude() == null) {
				continue;
			}
			ccp.setAvgScore(cmtCommentVO.getSumaryLatitude().getScore() + "");
			cmtList.add(ccp);
		}
		return cmtList;
	}

	protected boolean isLastPage(Page<?> pageConfig) {
		if (pageConfig == null) {
			return true;
		}

		if (pageConfig.getItems() == null || pageConfig.getItems().size() == 0) {
			return true;
		}

		if (pageConfig.getCurrentPage() == pageConfig.getTotalPages()) {
			return true;
		}
		return false;
	}

	public Map<String, Object> queryPlaceComments(String userId, Long placeId,
			Long page) {

		Map<String, Object> paramters = this.defaultParamters(page);
		String[] isAudits = { com.lvmama.comm.vo.Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS
				.name() };
		paramters.put("isAudits", isAudits);
		paramters.put("placeId", placeId);
		UserUser user = null;
		if (!StringUtil.isEmptyString(userId)) {
			user = userUserProxy.getUserUserByUserNo(userId);
			paramters.put("notEqualUserId", user.getId());
		}

		paramters.put("isHid", "N");
		paramters.put("createTime321", "true");
		Page<CommonCmtCommentVO> pageConfig = this.cmtCommentService
				.queryCmtCommentListForApp(paramters);
		List<ClientCmtPlace> cmtList = new ArrayList<ClientCmtPlace>();
		List<ClientCmtPlace> userAuditCmtList = this.queryMyComment(user,
				placeId);

		if (userAuditCmtList != null && userAuditCmtList.size() != 0
				&& page == 1L) {
			cmtList.addAll(userAuditCmtList);
		}

		for (CommonCmtCommentVO cmtCommentVO : pageConfig.getItems()) {
			ClientCmtPlace ccp = new ClientCmtPlace();
			this.covertComment(ccp, cmtCommentVO);
			if (null == cmtCommentVO.getSumaryLatitude()) {
				ccp.setAvgScore("0");
			} else {
				ccp.setAvgScore(cmtCommentVO.getSumaryLatitude().getScore()
						+ "");
			}
			cmtList.add(ccp);
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("list", cmtList);
		resultMap.put("isLastPage", this.isLastPage(pageConfig));
		return resultMap;
	}

	private void covertComment(ClientCmtPlace ccp,
			CommonCmtCommentVO commonCmtCommentVO) {
		ccp.setAvgScore(commonCmtCommentVO.getAvgScore() == null ? ""
				: commonCmtCommentVO.getAvgScore() + "");
		ccp.setContent(commonCmtCommentVO.getContent());
		ccp.setCreatedTimeStr(com.lvmama.comm.utils.DateUtil.formatDate(
				commonCmtCommentVO.getCreatedTime(), "yyyy-MM-dd HH:mm:ss"));
		ccp.setBest(Boolean.valueOf("Y".equals(commonCmtCommentVO.getIsBest())));
		ccp.setExperience(com.lvmama.comm.vo.Constant.EXPERIENCE_COMMENT_TYPE
				.equals(commonCmtCommentVO.getCmtType()) ? true : false);
		ccp.setCashRefund(commonCmtCommentVO.getCashRefund() == null ? ""
				: commonCmtCommentVO.getCashRefund() + "");
		ccp.setPoint(commonCmtCommentVO.getPoint() + "");
		ccp.setUserName(commonCmtCommentVO.getUserName());
		ccp.setAuditStatu(commonCmtCommentVO.getIsAudit());
		ccp.setChAudit(commonCmtCommentVO.getChAudit());

		if (commonCmtCommentVO.getCmtType().equals(
				Constant.EXPERIENCE_COMMENT_TYPE)) {
			ProdProduct product = prodProductService
					.getProdProductById(commonCmtCommentVO.getProductId());
			if (product != null) {
				ProductCmtCommentVO productCmtCommentVO = CommentUtil
						.composeProductComment(commonCmtCommentVO, product,
								null);
				ccp.setProductName(productCmtCommentVO.getProductName());
			}
		} else {
			Place place = placeService.queryPlaceByPlaceId(commonCmtCommentVO
					.getPlaceId());
			PlaceCmtCommentVO placeCmtCommentVO = CommentUtil
					.composePlaceComment(commonCmtCommentVO, place);
			if (placeCmtCommentVO != null) {
				ccp.setPlaceName(placeCmtCommentVO.getPlaceName());
			}
		}

		ccp.setPlaceId(commonCmtCommentVO.getPlaceId() + "");
		ccp.setCmtLatitudes(commonCmtCommentVO.getCmtLatitudes());
		ccp.setCmtType(commonCmtCommentVO.getCmtType());
		ccp.setOrderId(commonCmtCommentVO.getOrderId() + "");
	}

	public String commitOrder() {

		return "";
	}

	public void addfeedBack(String content, String email, String userId,
			String firstChannel) {
		ComUserFeedback fd = new ComUserFeedback();
		fd.setContent(content);
		fd.setType(firstChannel);
		fd.setStateId(Constant.FEED_BACK_STATE_ID.PENDING.name());
		fd.setEmail(email);
		fd.setCreateDate(new Date());
		UserUser user = userUserProxy.getUserUserByUserNo(userId);
		if (user != null) {
			fd.setUserId(user.getId());
			fd.setUserName(user.getUserName());
			fd.setMobile(user.getMobileNumber());
		}
		this.comUserFeedbackService.saveUserFeedBack(fd);
	}

	public List<ClientOrderCmt> getOrderWaitingComments(String userId, Long page) {
		List<OrderAndComment> canCommentOrderProductList = orderServiceProxy
				.selectCanCommentOrderProductByUserNo(userId);
		List<ClientOrderCmt> cocList = new ArrayList<ClientOrderCmt>();

		for (OrderAndComment orderAndComment : canCommentOrderProductList) {
			ClientOrderCmt coc = new ClientOrderCmt();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("orderId", orderAndComment.getOrderId());
			parameters.put("productId", orderAndComment.getProductId());
			parameters.put("isHide", "displayall");
			List<CommonCmtCommentVO> cmtCommentList = cmtCommentService
					.getCmtCommentList(parameters);
			if (cmtCommentList == null || cmtCommentList.size() == 0) {
				coc.setCashRefund(orderAndComment.getCashRefund() + "");
				coc.setOrderId(orderAndComment.getOrderId() + "");
				coc.setProductName(orderAndComment.getProductName());
				cocList.add(coc);
			}

		}
		return cocList;
	}

	private List<DicCommentLatitudeVO> getPlaceInfoLatitude(Place place) {
		List<DicCommentLatitudeVO> list = new ArrayList<DicCommentLatitudeVO>();
		Map<String, Object> paramters = new HashMap<String, Object>();
		paramters.put("placeId", place.getPlaceId());

		String subject = place.getCmtTitle();
		// 景点门票依主题取维度，目的地统一维度，酒店及产品也统一维度
		if ("3".equalsIgnoreCase(place.getStage())) {
			subject = "酒店和酒店产品";
		} else if ("1".equalsIgnoreCase(place.getStage())) {
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
		List<DicCommentLatitude> dicCommentLatitudeList = dicCommentLatitudeService
				.getDicCommentLatitudeList(paramters);
		for (DicCommentLatitude dicCommentLatitude : dicCommentLatitudeList) {
			DicCommentLatitudeVO dicCommentLatitudeVO = new DicCommentLatitudeVO();
			BeanUtils.copyProperties(dicCommentLatitude, dicCommentLatitudeVO);
			list.add(dicCommentLatitudeVO);

		}

		if (list.isEmpty()) {
			if ("3".equalsIgnoreCase(place.getStage())) {
				subject = "其他主题";
			} else {
				// place主题无对应的纬度和place无主题,默认主题为: 其它
				subject = "其它";
			}

			paramters.put("subject", subject);
			dicCommentLatitudeList = dicCommentLatitudeService
					.getDicCommentLatitudeList(paramters);
			for (DicCommentLatitude dicCommentLatitude : dicCommentLatitudeList) {
				DicCommentLatitudeVO dicCommentLatitudeVO = new DicCommentLatitudeVO();
				BeanUtils.copyProperties(dicCommentLatitude,
						dicCommentLatitudeVO);
				list.add(dicCommentLatitudeVO);
			}
		}

		return list;
	}

	public List<DicCommentLatitudeVO> getCommentLatitudeInfos(Long placeId,
			String orderId) {
		List<DicCommentLatitudeVO> list = null;
		if (placeId != null) {
			Place place = this.placeService.queryPlaceByPlaceId(Long
					.valueOf(placeId));
			list = this.getPlaceInfoLatitude(place);
		}

		if (!org.apache.commons.lang.StringUtils.isEmpty(orderId)) {

			OrdOrder orderOrder = orderServiceProxy.queryOrdOrderByOrderId(Long
					.valueOf(orderId));
			Long destId = prodProductPlaceService
					.selectDestByProductId(orderOrder.getMainProduct()
							.getProductId());
			Place place = this.placeService.queryPlaceByPlaceId(Long
					.valueOf(destId));

			if (place != null) {

				String productType = orderOrder.getMainProduct()
						.getProductType();

				if (!StringUtil.isEmptyString(place.getCmtTitle())
						&& !StringUtil.isEmptyString(place.getStage())) {
					// 酒店产品和酒店景点维度一致(取酒店统一的维度)，门票和目的景点维度一致(依placeId主题取维度)
					if ((Constant.PRODUCT_TYPE.HOTEL.name().equalsIgnoreCase(
							productType) || Constant.PRODUCT_TYPE.TICKET.name()
							.equalsIgnoreCase(productType)))
						list = this.getPlaceInfoLatitude(place);
				} else {
					// 线路产品
					list = new ArrayList<DicCommentLatitudeVO>();
					Constant.PRODUCT_LATITUDE productLatitude = Constant.PRODUCT_LATITUDE
							.getProductLatitude(productType);
					String subject = productLatitude.getChSubject();

					Map<String, Object> parames = new HashMap<String, Object>();
					parames.put("subject", subject);
					List<DicCommentLatitude> dicCommentLatitudeList = dicCommentLatitudeService
							.getDicCommentLatitudeList(parames);
					for (DicCommentLatitude dicCommentLatitude : dicCommentLatitudeList) {
						DicCommentLatitudeVO dicCommentLatitudeVO = new DicCommentLatitudeVO();
						BeanUtils.copyProperties(dicCommentLatitude,
								dicCommentLatitudeVO);
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

		if (list == null) {
			list = new ArrayList<DicCommentLatitudeVO>();
		}

		list.add(main);
		return list;
	}

	public List<ClientUserCouponInfo> queryUserCouponInfo(UserUser user,
			String state) {
		List<MarkCouponUserInfo> list = markCouponService
				.queryMobileUserCouponInfoByUserId(user.getId());
		List<ClientUserCouponInfo> couponList = CouponUtils.filterCouponInf(
				list, state);
		return couponList;
	}

	public Map<String, Object> getOrderListByOrderId(Long orderId,
			String userId, Long page) {

		return this.getOrderViewList(orderId, userId, page);
	}

	public ViewClientOrder getOrderByOrderId(Long orderId, String userId) {
		Map<String, Object> map = this.getOrderViewList(orderId, userId, 1L);
		List<ViewClientOrder> orderList = (List<ViewClientOrder>) map
				.get("list");
		if (!orderList.isEmpty()) {
			return orderList.get(0);
		}
		return null;
	}

	private Map<String, Object> getOrderViewList(Long orderId, String userId,
			Long page) {
		CompositeQuery compositeQuery = new CompositeQuery();
		OrderIdentity orderIdentity = new OrderIdentity();
		orderIdentity.setUserId(userId);
		if (orderId != null) {
			orderIdentity.setOrderId(orderId);
		}
		compositeQuery.setOrderIdentity(orderIdentity);

		Long totalRecords = orderServiceProxy
				.compositeQueryOrdOrderCount(compositeQuery);

		Page pageConfig = Page.page(totalRecords, 5L, page);
		PageIndex pageIndex = new PageIndex();
		pageIndex.setBeginIndex(new Integer("" + pageConfig.getStartRows()));
		pageIndex.setEndIndex(new Integer("" + pageConfig.getEndRows()));
		compositeQuery.setPageIndex(pageIndex);
		List<OrdOrder> ordersList = orderServiceProxy
				.compositeQueryOrdOrder(compositeQuery);

		List<ViewClientOrder> vcoList = new ArrayList<ViewClientOrder>();
		if (ordersList != null && !ordersList.isEmpty()) {
			for (OrdOrder ordOrder : ordersList) {
				if (ordOrder == null) {
					continue;
				}
				if (ordOrder.getMainProduct() == null) {
					continue;
				}
				getClientOrder(ordOrder, vcoList);
			}
		}
		pageConfig.setItems(vcoList);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("list", vcoList);
		resultMap.put("isLastPage", this.isLastPage(pageConfig));
		return resultMap;
	}

	private void getClientOrder(OrdOrder ordOrder, List<ViewClientOrder> vcoList) {
		Long marketPrice = new Long(0);
		for (Iterator<OrdOrderItemProd> it = ordOrder.getOrdOrderItemProds()
				.iterator(); it.hasNext();) {
			OrdOrderItemProd prod = it.next();
			marketPrice += prod.getMarketPrice() * prod.getQuantity();
		}

		ViewClientOrder vco = new ViewClientOrder();
		if (ordOrder.getOughtPay() != null) {
			vco.setJieshen(Long.valueOf((marketPrice - ordOrder.getOughtPay()) / 100)
					+ "");
		}

		vco.setCreateTime(DateUtil.getFormatDate(ordOrder.getCreateTime(),
				"yyyy-MM-dd"));

		vco.setOrderId(ordOrder.getOrderId());
		vco.setOrderViewStatus(ordOrder.getOrderViewStatus());
		vco.setPaymentTarget(ordOrder.getPaymentTarget());
		vco.setProductName(ordOrder.getMainProduct().getProductName());
		vco.setTitleName(ordOrder.getMainProduct().getShortName());
		vco.setAmount(Long.valueOf(ordOrder.getOughtPay() / 100) + "");
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
			vco.setQuantity(ordOrder.getMainProduct().getQuantity() + "");
		} else if (Constant.PRODUCT_TYPE.TICKET.name().equals(
				ordOrder.calcOrderType())) {
			vco.setVisitTime(ordOrder.getZhVisitTime());
			vco.setQuantity(ordOrder.getMainProduct().getQuantity() + "");
		} else if (Constant.PRODUCT_TYPE.HOTEL.name().equals(
				ordOrder.calcOrderType())) {
			if (!Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT.equals(ordOrder
					.getMainProduct().getSubProductType())) {
				vco.setVisitTime(ordOrder.getMainProduct().getDateRange());
				vco.setQuantity(ordOrder.getMainProduct().getQuantity() + "");
			} else {
				vco.setVisitTime(ordOrder.getZhVisitTime());
				vco.setQuantity(ordOrder.getMainProduct().getHotelQuantity());
			}

		} else if (Constant.PRODUCT_TYPE.OTHER.name().equals(
				ordOrder.calcOrderType())) {
			vco.setVisitTime(ordOrder.getZhVisitTime());
			vco.setQuantity(ordOrder.getMainProduct().getQuantity() + "");
		}
		vco.setMainProductType(ordOrder.getMainProduct().getProductType());
		vco.setApproveStatus(ordOrder.getApproveStatus());

		if (ordOrder.isHotel()) {
			String[] range = ordOrder.getMainProduct().getDateRange()
					.split("至");
			if (range.length > 1) {
				vco.setVisitTime(range[0]);
				vco.setLeaveTime(range[1]);
			}
			vco.setQuantity(ordOrder.getMainProduct().getHotelQuantity());
		}
		List<String> additionalProdList = new ArrayList<String>();
		List<OrdOrderItemProd> ordOrderItemProdList = ordOrder
				.getOrdOrderItemProds();
		for (OrdOrderItemProd ordOrderItemProd : ordOrderItemProdList) {
			if (ordOrderItemProd.isAdditionalProduct()) {
				additionalProdList.add(ordOrderItemProd.getShortName() + "x"
						+ ordOrderItemProd.getQuantity());

			}
		}
		vco.setAdditionList(additionalProdList);
		List<ViewOrdPerson> vopList = new ArrayList<ViewOrdPerson>();
		for (OrdPerson op : ordOrder.getPersonList()) {
			ViewOrdPerson vop = new ViewOrdPerson();
			BeanUtils.copyProperties(op, vop);
			if (!StringUtil.isEmptyString(vop.getCertNo())
					&& vop.getCertType().equals(
							Constant.CERT_TYPE.ID_CARD.name())) {
				if (vop.getCertNo().length() == 18) {
					vop.setCertNo("**************"
							+ vop.getCertNo().substring(
									vop.getCertNo().length() - 4,
									vop.getCertNo().length()));
				} else if (vop.getCertNo().length() == 15) {
					vop.setCertNo("***********"
							+ vop.getCertNo().substring(
									vop.getCertNo().length() - 4,
									vop.getCertNo().length()));
				}

			}
			vopList.add(vop);
		}

		if ("true".equals(ordOrder.getTodayOrder())) {
			vco.setLatestPassTime(DateUtil.formatDate(
					ordOrder.getLatestUseTime(), "HH:mm"));
			vco.setEarliestPassTime(DateUtil.formatDate(
					ordOrder.getVisitTime(), "HH:mm"));
			// 今日预订 精确到时分秒
			vco.setVisitTime(DateUtil.formatDate(ordOrder.getVisitTime(),
					"yyyy-MM-dd HH:mm:ss"));
		}

		vco.setTodayOrder(Boolean.valueOf(ordOrder.getTodayOrder()));

		vco.setListPerson(vopList);

		vcoList.add(vco);
	}

	public ClientPlace getPlaceByName(String keyword) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("stage", "1");
		param.put("name", keyword);
		List<Place> list = placeService.queryPlaceListByParam(param);
		if (!list.isEmpty()) {
			Place p = list.get(0);
			ClientPlace cp = new ClientPlace();
			BeanUtils.copyProperties(p, cp);
			cp.setId(p.getPlaceId());
			return cp;
		}
		return null;
	}

	public Place queryPlaceByName(String keyword) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("stage", "1");
		// qin 
		if(null == keyword) {
			keyword = "";
		}
		if (StringUtil.isPinyin(keyword)) {
			param.put("pinYin", keyword.toLowerCase());
		} else {
			param.put("name", keyword);
		}
		param.put("isValid", "Y");
		List<Place> list = placeService.queryPlaceListByParam(param);
		if (!list.isEmpty()) {
			Place p = list.get(0);
			return p;
		}
		return null;
	}

	protected void saveMobileLog(Map<String, Object> param, Long objectId,
			String objectType) {
		List<MobilePersistanceLog> logs = mobileClientService
				.selectListbyPersistanceobjectId(objectId, objectType);
		if (logs == null || logs.isEmpty()) {
			mobileClientService.insertMobilePersistanceLog(
					String.valueOf(param.get("firstChannel")),
					String.valueOf(param.get("secondChannel")),
					String.valueOf(param.get("udid")),
					param.get("appVersion") == null ? 0L : Long.valueOf(String
							.valueOf(param.get("appVersion"))), objectId,
					objectType, String.valueOf(param.get("osVersion")), String
							.valueOf(param.get("userAgent")));
			log.info("persistanceLog objectId " + objectId + " objectType "
					+ objectType);
		} else {
			log.info("persistanceLog objectId " + objectId + " objectType "
					+ objectType + " exsist");
		}
	}
	
	
	protected void saveMobileLogWithUdid(Map<String, Object> param, Long objectId,
			String objectType) {
		String udid = param.get("udid").toString();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("objectId", objectId);
		params.put("objectType", objectType);
		params.put("deviceId", udid);
		List<MobilePersistanceLog> list = mobileClientService.queryMobilePersistanceLogByParam(params);
		if (list == null || list.isEmpty()) {
			mobileClientService.insertMobilePersistanceLog(
					String.valueOf(param.get("firstChannel")),
					String.valueOf(param.get("secondChannel")),
					String.valueOf(param.get("udid")),
					param.get("appVersion") == null ? 0L : Long.valueOf(String
							.valueOf(param.get("appVersion"))), objectId,
					objectType, String.valueOf(param.get("osVersion")), String
							.valueOf(param.get("userAgent")));
			log.info("persistanceLog objectId " + objectId + " objectType "
					+ objectType);
		} else {
			log.info("persistanceLog objectId " + objectId + " objectType "
					+ objectType + " exsist");
		}
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	public void setPlacePhotoService(PlacePhotoService placePhotoService) {
		this.placePhotoService = placePhotoService;
	}

	protected boolean isLowerIOS7(Map<String, Object> param, boolean rebuildUdid) {
		String osVersion = (String) param.get("osVersion");
		String channel = (String) param.get("firstChannel");
		boolean isIos = Constant.MOBILE_PLATFORM.IPHONE.name().equals(channel)
				|| Constant.MOBILE_PLATFORM.IPAD.name().equals(channel);
		if (Constant.MOBILE_PLATFORM.ANDROID.name().equals(channel)) {
			return true;
		}
		if (isIos && osVersion != null) {
			String firstChar = osVersion.substring(0, 1);
			if (Integer.parseInt(firstChar) < 7) {
				return true;
			} else {
				try {
					if (rebuildUdid) {
						param.remove("method");
						param.put("udid", MD5.encode(param.toString()));
					}
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public void setRecommendInfoClient(RecommendInfoClient recommendInfoClient) {
		this.recommendInfoClient = recommendInfoClient;
	}

	public void setProductSearchInfoService(
			ProductSearchInfoService productSearchInfoService) {
		this.productSearchInfoService = productSearchInfoService;
	}

	public void setViewPageService(ViewPageService viewPageService) {
		this.viewPageService = viewPageService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setViewPageJourneyService(
			ViewPageJourneyService viewPageJourneyService) {
		this.viewPageJourneyService = viewPageJourneyService;
	}

	public void setCmtCommentService(CmtCommentService cmtCommentService) {
		this.cmtCommentService = cmtCommentService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setProdProductPlaceService(
			ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}

	public void setPlaceSearchInfoService(
			PlaceSearchInfoService placeSearchInfoService) {
		this.placeSearchInfoService = placeSearchInfoService;
	}

	public void setCmtLatitudeStatistisService(
			CmtLatitudeStatistisService cmtLatitudeStatistisService) {
		this.cmtLatitudeStatistisService = cmtLatitudeStatistisService;
	}

	public void setComUserFeedbackService(
			ComUserFeedbackService comUserFeedbackService) {
		this.comUserFeedbackService = comUserFeedbackService;
	}

	public void setDicCommentLatitudeService(
			DicCommentLatitudeService dicCommentLatitudeService) {
		this.dicCommentLatitudeService = dicCommentLatitudeService;
	}

	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}

	public void setGroupDreamService(GroupDreamService groupDreamService) {
		this.groupDreamService = groupDreamService;
	}

	public void setComClientService(ComClientService comClientService) {
		this.comClientService = comClientService;
	}

	public void setPlaceCoordinateBaiduService(
			PlaceCoordinateBaiduService placeCoordinateBaiduService) {
		this.placeCoordinateBaiduService = placeCoordinateBaiduService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public void setRecommendInfoService(
			RecommendInfoService recommendInfoService) {
		this.recommendInfoService = recommendInfoService;
	}

	public void setCmtTitleStatistisService(
			CmtTitleStatistisService cmtTitleStatistisService) {
		this.cmtTitleStatistisService = cmtTitleStatistisService;
	}

	public void setFavorService(FavorService favorService) {
		this.favorService = favorService;
	}

	public void setProductServiceProxy(ProductServiceProxy productServiceProxy) {
		this.productServiceProxy = productServiceProxy;
	}

	public void setPlaceCoordinateGoogleService(
			PlaceCoordinateGoogleService placeCoordinateGoogleService) {
		this.placeCoordinateGoogleService = placeCoordinateGoogleService;
	}

	public void setReceiverUserService(IReceiverUserService receiverUserService) {
		this.receiverUserService = receiverUserService;
	}

	public void setVstClientProductService(
			VstClientProductService vstClientProductService) {
		this.vstClientProductService = vstClientProductService;
	}

	public void setVstClientPlaceService(VstClientPlaceService vstClientPlaceService) {
		this.vstClientPlaceService = vstClientPlaceService;
	}

	public void setPlacePlaceDestService(
			PlacePlaceDestService placePlaceDestService) {
		this.placePlaceDestService = placePlaceDestService;
	}

	public void setOrdEContractService(OrdEContractService ordEContractService) {
		this.ordEContractService = ordEContractService;
	}

	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}

	protected boolean isFreness(Map<String, Object> params) {
		if (params.get("subProductType") != null) {
			if (params.get("subProductType").toString()
					.contains(Constant.SUB_PRODUCT_TYPE.FREENESS.name())
					|| params
							.get("subProductType")
							.toString()
							.contains(
									Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN
											.name())
					|| params
							.get("subProductType")
							.toString()
							.contains(
									Constant.SUB_PRODUCT_TYPE.FREENESS_LONG
											.name())) {
				return true;
			}
		}
		return false;
	}
	
	protected boolean isSearchKeyword(Map<String, Object> params){
		return params.get("keyword")!=null;
	}
	
	protected boolean isSearchIds(Map<String, Object> params){
		return params.get("ids")!=null;
	}
	

	protected void setTodayOrderTips2(MobileBranch branch,
			boolean isBeforeCommitOrder) {
		// boolean flag2 =
		// prodProductBranchService.checkPhoneOrderTime(Long.valueOf(branch.getBranchId()));
		TimePrice tp = prodProductBranchService.calcCurrentProdTimePric(
				Long.valueOf(branch.getBranchId()),
				DateUtil.getDayStart(new Date()));
		if (tp != null && branch.isCanOrderToday()) {
			List<MetaProductBranch> mpbList = metaProductBranchService
					.getMetaProductBranchByProdBranchId(Long.valueOf(branch
							.getBranchId()));

			Long lastTicketTime = null;

			boolean isPayToSupplier = false;

			for (MetaProductBranch metaProductBranch : mpbList) {
				MetaProductBranch mpb = metaProductBranch;
				MetaProductTicket mp = (MetaProductTicket) metaProductService
						.getMetaProduct(mpb.getMetaProductId(),
								Constant.PRODUCT_TYPE.TICKET.name());
				if (mp == null) {
					continue;
				}
				isPayToSupplier = mp.isPaymentToSupplier();
				if (lastTicketTime != null) {
					if (mp.getLastTicketTime() >= lastTicketTime) {
						lastTicketTime = mp.getLastTicketTime();
					}
				} else {
					lastTicketTime = mp.getLastTicketTime();
				}

			}
			if (lastTicketTime != null && lastTicketTime != null) {
				Float lastTicketTimeHour = lastTicketTime / 60f;
				String todayOrderTips = "";
				if (lastTicketTimeHour != 0.0f) {
					todayOrderTips = String.format(
							"① 成功预订后%s个小时方可入园  ② 最晚%s点前预订", lastTicketTimeHour,
							DateUtil.formatDate(
									branch.getTodayOrderLastOrderTime(),
									"HH:mm"));
					if (isBeforeCommitOrder) {
						todayOrderTips += "③ 当日票不退不改 ④ 订单提交成功后，驴妈妈将会发确认订单短信到你的手机，凭短信可顺利入园。";
					}
					// } else if(lastTicketTimeHour==0.0f){
					// todayOrderTips =String.format("成功预订后即可入园;最晚%s点前预订",
					// DateUtil.formatDate(branch.getTodayOrderLastOrderTime(),
					// "HH:mm"));
					// }
				} else {
					// todayOrderTips =String.format("成功预订后即可入园;最晚%s点前预订",
					// DateUtil.formatDate(branch.getTodayOrderLastOrderTime(),
					// "HH:mm"));
					todayOrderTips = String.format("最晚%s点前预订", DateUtil
							.formatDate(branch.getTodayOrderLastOrderTime(),
									"HH:mm"));
					if (isBeforeCommitOrder) {
						todayOrderTips = "①" + todayOrderTips;
						todayOrderTips += "② 当日票不退不改 ③订单提交成功后，驴妈妈将会发确认订单短信到你的手机，凭短信可顺利入园。";
					}
				}

				branch.setTodayOrderTips(todayOrderTips);
			}
		}
	}

	protected void setTodayOrderTips(MobileBranch branch) {

		boolean flag2 = prodProductBranchService.checkPhoneOrderTime(Long
				.valueOf(branch.getBranchId()));
		TimePrice tp = prodProductBranchService.calcCurrentProdTimePric(
				Long.valueOf(branch.getBranchId()),
				DateUtil.getDayStart(new Date()));
		if (tp != null) {
			branch.setCanOrderTodayCurrentTime(flag2);

			List<MetaProductBranch> mpbList = metaProductBranchService
					.getMetaProductBranchByProdBranchId(Long.valueOf(branch
							.getBranchId()));

			Long lastTicketTime = null;
			Long LastPassTime = null;

			boolean isPayToLvmm = false;
			boolean isPayToSupplier = false;

			for (MetaProductBranch metaProductBranch : mpbList) {
				MetaProductBranch mpb = metaProductBranch;
				MetaProductTicket mp = (MetaProductTicket) metaProductService
						.getMetaProduct(mpb.getMetaProductId(),
								Constant.PRODUCT_TYPE.TICKET.name());
				if (mp == null) {
					continue;
				}
				isPayToLvmm = mp.isPaymentToLvmama();
				isPayToSupplier = mp.isPaymentToSupplier();

				if (lastTicketTime != null) {
					if (mp.getLastTicketTime() >= lastTicketTime) {
						lastTicketTime = mp.getLastTicketTime();
					}
				} else {
					lastTicketTime = mp.getLastTicketTime();
				}

				if (LastPassTime != null) {
					if (mp.getLastPassTime() >= LastPassTime) {
						LastPassTime = mp.getLastPassTime();
					}
				} else {
					LastPassTime = mp.getLastPassTime();
				}
			}
			if (lastTicketTime != null && lastTicketTime != null) {
				String leatestOrderTime = "";

				Float lastTicketTimeHour = lastTicketTime / 60f;
				String todayOrderTips = "";
				if (isPayToLvmm == false && isPayToSupplier == true
						&& lastTicketTimeHour == 0.0f) {
					leatestOrderTime = ClientUtils.getLeatestOrder(
							tp.getLatestUseTimeDate(), lastTicketTime,
							LastPassTime, 0L);
					todayOrderTips = String.format("成功预订后即可入园;最晚%s点前预订",
							leatestOrderTime);
				} else if (isPayToLvmm == false && isPayToSupplier == true) {
					leatestOrderTime = ClientUtils.getLeatestOrder(
							tp.getLatestUseTimeDate(), lastTicketTime,
							LastPassTime, 0L);
					todayOrderTips = String.format(
							"① 成功预订后%s个小时方可入园  ② 最晚%s点前预订", lastTicketTimeHour,
							leatestOrderTime);
				} else if (isPayToLvmm == true) {
					leatestOrderTime = ClientUtils.getLeatestOrder(
							tp.getLatestUseTimeDate(), lastTicketTime,
							LastPassTime, 60 * 30);

					todayOrderTips = String.format(
							"① 成功预订后%s个小时方可入园  ② 最晚%s点前预订", lastTicketTimeHour,
							leatestOrderTime);
				}
				branch.setTodayOrderTips(todayOrderTips);
			}
		} else {
			/**
			 * 当查询当天日期没有价格的时候 默认不支持当天预定
			 */
			branch.setCanOrderToday(false);
		}

	}

	public Map<String, MobileDest> initCityTree() {
		ClientPlaceSearchVO cpvo = new ClientPlaceSearchVO();
		List<String> productTypes = new ArrayList<String>();
		productTypes.add("TICKET");
		List<String> stages = new ArrayList<String>();
		stages.add("1");
		cpvo.setStage(stages);
		cpvo.setProductType(productTypes);
		return treeCache(cpvo);
	}

	private Map<String, MobileDest> treeCache(ClientPlaceSearchVO cpvo) {
		Object obj = MemcachedUtil.getInstance().get(
				CLUTTER_MOBILE_OFFLINE_CITY_TREE_CACHE);
		if (obj != null) {
			return (Map<String, MobileDest>) obj;
		}
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("hasTicket", "0");
		Map<Long, List<String>> resultSubjects = vstClientPlaceService
				.getCitiesSubjects(queryMap);
		TreeBean<PlaceBean> treeBean = vstClientPlaceService
				.getChinaTreeByHasProduct(cpvo);
		Map<String, MobileDest> result = new HashMap<String, MobileDest>();
		List<MobileDest> mdList = new ArrayList<MobileDest>();

		for (TreeBean<PlaceBean> provinceBean : treeBean.getSubNode()) {
			MobileDest mdProvince = new MobileDest();

			PlaceBean pb = (PlaceBean) provinceBean.getNode();
			mdProvince.setId(pb.getId());
			mdProvince.setName(pb.getName());
			mdProvince.setPinyin(pb.getPinYin());
			List<TreeBean> citylist = provinceBean.getSubNode();

			List<MobileDest> mdCities = new ArrayList<MobileDest>();
			if (citylist != null && citylist.size() != 0) {
				for (TreeBean cityBean : citylist) {
					PlaceBean pb2 = (PlaceBean) cityBean.getNode();
					result.put(pb2.getId(), mdProvince);
				}
			} else {
				result.put(pb.getId(), mdProvince);
			}
			;
		}
		MemcachedUtil.getInstance().set(CLUTTER_MOBILE_OFFLINE_CITY_TREE_CACHE,
				60 * 60 * 6, result);
		return result;
	}

	/**
	 * 获取参数
	 * 
	 * @param param
	 * @param key
	 *            参数关键字
	 * @param clazz
	 *            转换对象类型
	 * @param isNotNull
	 *            可否为空
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <T> T getT(Map<String, Object> param, String key, Class<T> clazz,
			boolean isNotNull) throws Exception {
		Object value = param.get(key);
		T t = null;
		if (null != value) {
			if (Date.class.equals(clazz)) {
				return (T) DateUtils.parseDate((String) value, "yyyy-MM-dd");
			} else if (Integer.class.equals(clazz)) {
				return (T) Integer.valueOf((String) value);
			} else if (Long.class.equals(clazz)) {
				return (T) Long.valueOf((String) value);
			} else if (Double.class.equals(clazz)) {
				return (T) Double.valueOf((String) value);
			} else if (BigDecimal.class.equals(clazz)) {
				return (T) BigDecimal.valueOf(Double.valueOf((String) value));
			} else if (Boolean.class.equals(clazz)) {
				return (T) Boolean.valueOf((String) value);
			}
			t = (T) value;
		} else {
			if (isNotNull) {
				log.error(MessageFormat.format("[{0}]参数不能为空！", key));
				throw new Exception(MessageFormat.format("[{0}]参数不能为空！", key));
			}
		}
		return t;
	}

	protected void setSortDatas(Map<String, Object> resultMap) {
		List<Map<String, Object>> sortFilters = new ArrayList<Map<String, Object>>();

		Map<String, Object> sortLvmamaSeqMap = new HashMap<String, Object>();
		sortLvmamaSeqMap.put("title", "驴妈妈推荐");
		sortLvmamaSeqMap.put("value", "seq");
		sortFilters.add(sortLvmamaSeqMap);

		Map<String, Object> sortPriceDescMap = new HashMap<String, Object>();
		sortPriceDescMap.put("title", "价格从高到低");
		sortPriceDescMap.put("value", "pricedesc");
		sortFilters.add(sortPriceDescMap);

		Map<String, Object> sortPriceAscMap = new HashMap<String, Object>();
		sortPriceAscMap.put("title", "价格从低到高");
		sortPriceAscMap.put("value", "priceasc");
		sortFilters.add(sortPriceAscMap);

		Map<String, Object> sortCmtStarMap = new HashMap<String, Object>();
		sortCmtStarMap.put("title", "星级");
		sortCmtStarMap.put("value", "cmtavg");
		sortFilters.add(sortCmtStarMap);

		Map<String, Object> sortCmtNumMap = new HashMap<String, Object>();
		sortCmtNumMap.put("title", "点评数");
		sortCmtNumMap.put("value", "cmtnum");
		sortFilters.add(sortCmtNumMap);

		resultMap.put("sortTypes", sortFilters);
	}

	/**
	 * 增加version判断 .
	 * 
	 * @param map
	 *            源map.
	 * @param params
	 *            参数.
	 * @return
	 */
	public Map<String, Object> initVersion(Map<String, Object> map,
			Map<String, Object> params) {
		JSONObject json = JSONObject.fromObject(map);
		try {
			String version = MD5.encode(json.toString());
			map.put("version", version);
			if (params.get("checkVersion") != null) {
				Map<String, Object> versionMap = new HashMap<String, Object>();
				versionMap.put("version", version);
				return versionMap;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 持久化入住人道常用游玩人列表
	 * @param name
	 * @param mobile
	 * @param userNo
	 */
	protected void saveHotelVisitorToReceiver(String name,String mobile,String userNo){
		List<UsrReceivers> receiversList = this.receiverUserService.loadUserReceiversByUserId(userNo);
		Map<String,List<UsrReceivers>> mapReceiver =  new HashMap<String, List<UsrReceivers>>();
		for (UsrReceivers usrReceivers : receiversList) {
			List<UsrReceivers> list = mapReceiver.get(usrReceivers.getReceiverName());
			if(list==null){
				list = new ArrayList<UsrReceivers>();
			}
			list.add(usrReceivers);
			mapReceiver.put(usrReceivers.getReceiverName(), list);
		}
		
		if(mapReceiver.get(name)==null){
			Person p = new Person();
			p.setName(name);
			p.setMobile(mobile);
			UsrReceivers ur = this.buildNewReceiver(p, userNo);
			List<UsrReceivers> list = new ArrayList<UsrReceivers>();
			list.add(ur);
			this.receiverUserService.createContact(list, userNo);
			
		}
	}
	
	protected UsrReceivers buildNewReceiver(Person person,String userNo){
		UsrReceivers u = new UsrReceivers();
		u.setCardType(person.getCertType());
		u.setCardNum(person.getCertNo());
		u.setReceiverName(person.getName());
		u.setMobileNumber(person.getMobile());
		u.setReceiversType(Constant.RECEIVERS_TYPE.CONTACT.name());
		u.setUserId(userNo);
		u.setIsValid("Y");
		u.setUseOffen("true");
		u.setBrithday(person.getBrithday());
		u.setGender(person.getGender());
		return u;
	}

	protected boolean hasWeiXinShared(Map<String,Object> param,Long branchId){
		if(param.get("udid")!=null){
			String udid = param.get("udid").toString();
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("objectId", branchId);
			params.put("objectType", ClutterConstant.WEIXIN_SHARE_KEYS.MOBILE_WEIXIN_SHARE_BRANCH_ID.name());
			params.put("deviceId", udid);
			List<MobilePersistanceLog> list = mobileClientService.queryMobilePersistanceLogByParam(params);
			return list!=null&&!list.isEmpty();
		}
		return false;
		
	}
	
	
	protected boolean hasWeiXinOrder(String udid,Long branchId){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("objectId", branchId);
			params.put("objectType", ClutterConstant.WEIXIN_SHARE_KEYS.MOBILE_WEIXIN_SHARE_ORDER_ID.name());
			params.put("deviceId", udid);
			List<MobilePersistanceLog> list = mobileClientService.queryMobilePersistanceLogByParam(params);
			return list!=null&&!list.isEmpty();
	
	}
	
	protected void addWexinOrderLog(Map<String,Object> param,Long branchId){
		this.saveMobileLogWithUdid(param, branchId, ClutterConstant.WEIXIN_SHARE_KEYS.MOBILE_WEIXIN_SHARE_ORDER_ID.name());
	}
	
	
	public void setFavorOrderService(FavorOrderService favorOrderService) {
		this.favorOrderService = favorOrderService;
	}

	public void setUserCooperationUserService(
			UserCooperationUserService userCooperationUserService) {
		this.userCooperationUserService = userCooperationUserService;
	}

	public void setMobileClientService(MobileClientService mobileClientService) {
		this.mobileClientService = mobileClientService;
	}

	public void setMobileHotelService(MobileHotelService mobileHotelService) {
		this.mobileHotelService = mobileHotelService;
	}

	public void setMobileTrainService(MobileTrainService mobileTrainService) {
		this.mobileTrainService = mobileTrainService;
	}

	public void setProdTrainCacheService(ProdTrainCacheService prodTrainCacheService) {
		this.prodTrainCacheService = prodTrainCacheService;
	}

	public void setProdTrainService(ProdTrainService prodTrainService) {
		this.prodTrainService = prodTrainService;
	}

	@Override
	public Map<String, Object> fastLogin(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPlaceCityService(PlaceCityService placeCityService) {
		this.placeCityService = placeCityService;
	}

	public void setProdProductRoyaltyService(
			ProdProductRoyaltyService prodProductRoyaltyService) {
		this.prodProductRoyaltyService = prodProductRoyaltyService;
	}
}
