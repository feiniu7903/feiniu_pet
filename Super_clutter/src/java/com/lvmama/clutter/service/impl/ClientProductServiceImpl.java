package com.lvmama.clutter.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.exception.NotFoundException;
import com.lvmama.clutter.model.MobileBranch;
import com.lvmama.clutter.model.MobileCouponPoint;
import com.lvmama.clutter.model.MobileGroupOn;
import com.lvmama.clutter.model.MobileGrouponContent;
import com.lvmama.clutter.model.MobilePersonItem;
import com.lvmama.clutter.model.MobileProduct;
import com.lvmama.clutter.model.MobileProductRoute;
import com.lvmama.clutter.model.MobileProductTitle;
import com.lvmama.clutter.model.MobileTimePrice;
import com.lvmama.clutter.model.MobileTrainSeatType;
import com.lvmama.clutter.service.IClientProductService;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.EnumSeckillStatus;
import com.lvmama.clutter.utils.SeckillUtils;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.po.comment.CmtLatitudeStatistics;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.mark.MarkCouponPointChange;
import com.lvmama.comm.pet.po.mobile.MobileFavorite;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdAssemblyPoint;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.po.visa.VisaApplicationDocument;
import com.lvmama.comm.pet.po.visa.VisaApplicationDocumentDetails;
import com.lvmama.comm.pet.service.mark.MarkCouponPointChangeService;
import com.lvmama.comm.pet.service.mobile.ClientSeckillService;
import com.lvmama.comm.pet.service.mobile.MobileFavoriteService;
import com.lvmama.comm.pet.service.visa.VisaApplicationDocumentService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.pet.vo.ProductList;
import com.lvmama.comm.search.vo.ClientRouteSearchVO;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ProdCProduct;
import com.lvmama.comm.vo.ProductResult;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
import com.lvmama.comm.vo.comment.PlaceCmtScoreVO;
import com.lvmama.comm.vo.visa.VisaVO;

/**
 * 产品相关.
 * 
 * @author
 * 
 */
public class ClientProductServiceImpl extends AppServiceImpl implements
		IClientProductService {
	private static final Log log = LogFactory
			.getLog(ClientProductServiceImpl.class);
	/**
	 * 我的收藏服务.
	 */
	protected MobileFavoriteService mobileFavoriteService;
	// 积分兑换优惠券服务
	protected MarkCouponPointChangeService markCouponPointChangeService;

	/**
	 * 秒杀
	 */
	@Autowired
	protected ClientSeckillService clientSeckillService;

	/**
	 * 签证材料远程服务
	 */
	protected VisaApplicationDocumentService visaApplicationDocumentService;

	private List<String> contentKeys = new ArrayList<String>();

	/**
	 * 初始化需要显示的key
	 * 
	 * @return
	 */
	private List<String> getContentKeys() {
		if (contentKeys.size() > 0) {
			return contentKeys;
		} else {
			// 参考Constant.VIEW_CONTENT_TYPE
			contentKeys.add("MANAGERRECOMMEND");
			contentKeys.add("ACITONTOKNOW");
			contentKeys.add("ANNOUNCEMENT");
			contentKeys.add("FEATURES");
			contentKeys.add("TRAFFICINFO");
			contentKeys.add("COSTCONTAIN");
			contentKeys.add("ORDERTOKNOWN");
			//contentKeys.add("VISA");
			return contentKeys;
		}
	}

	@Override
	public Map<String, Object> getStatusById(Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String productIdStr = (String) param.get("productId");
		String branckIdStr = (String) param.get("branchId");
//		Long productId = null;
//		if (!StringUtils.isEmpty(productIdStr)) {
//			productId = Long.parseLong(productIdStr);
//		} else {
//			throw new LogicException("产品编号不能为空");
//		}
		Long branchId = null;
		if (!StringUtils.isEmpty(branckIdStr)) {
			branchId = Long.parseLong(branckIdStr);
		} else {
			throw new LogicException("类别编号不能为空");
		}
		ProdSeckillRule prodSeckillRule = clientSeckillService.getSeckillRuleByBranchId(branchId);
		if(null!=prodSeckillRule){
			Date startTime = prodSeckillRule.getStartTime();// 秒杀开始时间
			//Date endTime = prodSeckillRule.getEndTime();// 秒杀开始时间
			Date currentTime = Calendar.getInstance().getTime();
			String seckillStatus = SeckillUtils.getSeckillStatus(prodSeckillRule);
			resultMap.put("seckillStatus", seckillStatus);
			if (EnumSeckillStatus.SECKILL_BEFORE.name().equals(seckillStatus)) {
				resultMap.put("seckillTips", "距离秒杀还有段时间哦，请稍后再抢！ ");
			} else if (EnumSeckillStatus.SECKILL_AFTER.name().equals(seckillStatus)
					|| EnumSeckillStatus.SECKILL_FINISHED.name().equals(
							seckillStatus)) {
				resultMap.put("seckillTips", "秒杀已结束，下次早点来哦！");
			} else {
				resultMap.put("seckillTips", "立即抢！");
			}

			// 解决1000毫秒内客户端倒计时结束，但状态还是不能秒杀
			Date currentDate = null;
			Date startDate = null;
			try {
				String pattern = "yyyy-MM-dd HH:mm:ss";
				currentDate = DateUtils.parseDate(
						DateFormatUtils.format(currentTime, pattern), pattern);
				startDate = DateUtils.parseDate(
						DateFormatUtils.format(startTime, pattern), pattern);
			} catch (ParseException e) {
				log.error(e);
				e.printStackTrace();
			}
			// if (currentTime.before(startTime)) {// 老代码，毫秒级有问题。原因客户端倒计时精确到秒
			if (currentDate.before(startDate)) {// 即将开始
				long millisBetween = DateUtil.getMillisBetween(
						currentTime, startTime);
				if(millisBetween==0){//解决毫秒问题，提前1秒
					resultMap.put("seckillStatus", EnumSeckillStatus.SECKILL_BEING.name());
				}
				resultMap.put("seckillMillis",millisBetween);// 秒杀时长
			} else {
				resultMap.put("seckillMillis", 0);// 秒杀开始，结束，售罄
			}
		}else{
			throw new LogicException("距离秒杀还有段时间哦，请稍后再抢！ ");
		}
		
		return resultMap;
	}

	@Override
	public Map<String, Object> listSeckilles(Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Date startTime = (Date) param.get("startTime");// 秒杀开始时间
		Date endTime = (Date) param.get("endTime");// 秒杀开始时间
		String productIdStr = (String) param.get("productId");
		String branckIdStr = (String) param.get("branchId");
		Long productId = null;
		if (!StringUtils.isEmpty(productIdStr)) {
			productId = Long.parseLong(productIdStr);
		}
		Long branckId = null;
		if (!StringUtils.isEmpty(branckIdStr)) {
			branckId = Long.parseLong(branckIdStr);
		}
		Page<ProdSeckillRule> page = clientSeckillService.seckillSearch(1,
				1000, startTime, endTime, productId, branckId);
		List<ProdSeckillRule> prodSeckillRules = page.getItems();
		List<MobileGroupOn> mobileGroupOns = new ArrayList<MobileGroupOn>();
		for (ProdSeckillRule prodSeckillRule : prodSeckillRules) {
			Long productIdSeckill = prodSeckillRule.getProductId();
			ProductSearchInfo productSearchInfo = productSearchInfoService
					.queryProductSearchInfoByProductId(productIdSeckill);
			MobileGroupOn mobileGroupOn = this
					.copyMobileGroupon(productSearchInfo);
			mobileGroupOn.setSellPriceYuan(PriceUtil.convertToYuan(
					productSearchInfo.getSellPrice()-prodSeckillRule.getReducePrice()));//秒杀价格
			mobileGroupOn.setSeckillStatus(SeckillUtils
					.getSeckillStatus(prodSeckillRule));
			mobileGroupOn.setSeckillStartTime(DateFormatUtils.format(
					prodSeckillRule.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
			mobileGroupOn.setSeckillEndTime(DateFormatUtils.format(
					prodSeckillRule.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
			mobileGroupOns.add(mobileGroupOn);
		}
		resultMap.put("seckilles", mobileGroupOns);
		return resultMap;
	}

	@Override
	public MobileGroupOn detailSeckill(Map<String, Object> param) {
		String productIdStr = (String) param.get("productId");
		String branckIdStr = (String) param.get("branchId");
		Long productId = null;
		if (!StringUtils.isEmpty(productIdStr)) {
			productId = Long.parseLong(productIdStr);
		} else {
			throw new LogicException("产品编号不能为空");
		}
		Long branchId = null;
		if (!StringUtils.isEmpty(branckIdStr)) {
			branchId = Long.parseLong(branckIdStr);
		} else {
			throw new LogicException("类别编号不能为空");
		}
		ProdSeckillRule prodSeckillRule = clientSeckillService.getSeckillRuleByBranchId(branchId);
		if (null!=prodSeckillRule) {
			if (prodSeckillRule.getProductId().equals(productId)) {
				Date startTime = prodSeckillRule.getStartTime();// 秒杀开始时间
				Date endTime = prodSeckillRule.getEndTime();// 秒杀开始时间
				Date currentTime = Calendar.getInstance().getTime();

				ProductSearchInfo productSearchInfo = productSearchInfoService
						.queryProductSearchInfoByProductId(productId);
				MobileGroupOn mobileGroupOn = this
						.copyMobileGroupon(productSearchInfo);
				mobileGroupOn.setSellPriceYuan(PriceUtil.convertToYuan(
						productSearchInfo.getSellPrice()-prodSeckillRule.getReducePrice()));//秒杀价格
				mobileGroupOn.setSeckillStartTime(DateFormatUtils.format(
						startTime, "yyyy-MM-dd HH:mm:ss"));
				mobileGroupOn.setSeckillEndTime(DateFormatUtils.format(endTime,
						"yyyy-MM-dd HH:mm:ss"));
				mobileGroupOn.setSeckillStatus(SeckillUtils
						.getSeckillStatus(prodSeckillRule));

				// 解决1000毫秒内客户端倒计时结束，但状态还是不能秒杀
				Date currentDate = null;
				Date startDate = null;
				try {
					String pattern = "yyyy-MM-dd HH:mm:ss";
					currentDate = DateUtils.parseDate(
							DateFormatUtils.format(currentTime, pattern),
							pattern);
					startDate = DateUtils
							.parseDate(
									DateFormatUtils.format(startTime, pattern),
									pattern);
				} catch (ParseException e) {
					log.error(e);
					e.printStackTrace();
				}
				
				if (currentDate.before(startDate)) {// 即将开始
					long millisBetween = DateUtil.getMillisBetween(
							currentTime, startTime);
					if(millisBetween==0){//解决毫秒问题，提前1秒
						mobileGroupOn.setSeckillStatus(EnumSeckillStatus.SECKILL_BEING.name());
					}
					mobileGroupOn.setSeckillMillis(millisBetween);// 秒杀时长
				} else {
					mobileGroupOn.setSeckillMillis(0);// 秒杀开始，结束，售罄
				}
				return mobileGroupOn;
			}
		}else{
			throw new LogicException("距离秒杀还有段时间哦，请稍后再抢！ ");
		}
		return null;
	}

	/**
	 * 获得产品详情 .
	 */
	@Override
	public MobileProduct getProduct(Map<String, Object> params) {
		ArgCheckUtils.validataRequiredArgs("productId", params);
		Long productId = Long.valueOf(params.get("productId") + "");
		// 判断产品是否存在 .
		ViewPage viewPage = viewPageService.selectByProductId(productId);
		if (viewPage == null) {
			return null;
		}
		ViewPage vp = viewPageService.getViewPage(viewPage.getPageId());
		if (vp == null) {
			return null;
		}

		// 初始化产品信息 .
		ProdProduct pp = prodProductService.getProdProductById(productId);
		MobileProduct cp = new MobileProduct();
		BeanUtils.copyProperties(pp, cp);
		// 设置branchId
		ProdProductBranch defaultBranch = prodProductService
				.getProductDefaultBranchByProductId(productId);
		if (defaultBranch != null) {
			cp.setBranchId(defaultBranch.getProdBranchId());
		}
		// 如果登陆判断该产品是否被收藏.
		cp.setHasIn(false); // 默认false
		if (params.get("userNo") != null) {
			String userId = params.get("userNo").toString();
			if (!StringUtil.isEmptyString(userId)) {
				UserUser user = userUserProxy.getUserUserByUserNo(userId);
				if (user != null) {
					Map<String, Object> p = new HashMap<String, Object>();
					p.put("objectId", productId);
					p.put("userId", user.getId());
					List<MobileFavorite> queryMobileFavoriteLis = mobileFavoriteService
							.queryMobileFavoriteList(p);
					if (null != queryMobileFavoriteLis
							&& queryMobileFavoriteLis.size() > 0) {
						cp.setHasIn(true);
					}
				}
			}
		}

		for (int i = 0; i < vp.getContentList().size(); i++) {
			ViewContent viewContent = vp.getContentList().get(i);
			if (viewContent.getContentType().equals(
					Constant.VIEW_CONTENT_TYPE.MANAGERRECOMMEND.name())) {
				// cp.setManagerRecommend(StringUtil.filterOutHTMLTags(viewContent.getContent()));
				cp.setManagerRecommend(viewContent.getContent());
			}
		}

		// 图片列表.
		List<ComPicture> list = this.comPictureService.getPictureByPageId(vp
				.getPageId());
		List<String> imageList = new ArrayList<String>();
		if (list != null) {
			for (ComPicture comPicture : list) {
				imageList.add(comPicture.getPictureUrl());
			}
		}
		cp.setImageList(imageList);

		// 点评信息
		CmtTitleStatisticsVO productCommentStatistics = cmtTitleStatistisService
				.getCmtTitleStatisticsByProductId(productId);
		if (null != productCommentStatistics) {
			cp.setAvgScore(Float.valueOf(productCommentStatistics
					.getAvgScoreStr()));
			cp.setCmtNum(Integer.valueOf(null == productCommentStatistics
					.getCommentCount() ? "" : productCommentStatistics
					.getCommentCount() + ""));
		}

		// 获得某个产品的点评维度统计平均分
		/*
		 * Map<String,Object> parameters = new HashMap<String, Object>();
		 * parameters.put("productId", productId); List<CmtLatitudeStatistics>
		 * cmtLatitudeStatisticsList =
		 * cmtLatitudeStatistisService.getLatitudeStatisticsList(parameters);
		 * List<PlaceCmtScoreVO> pcsVoList = new ArrayList<PlaceCmtScoreVO>();
		 * for (CmtLatitudeStatistics cmtLatitudeStatistics :
		 * cmtLatitudeStatisticsList) { PlaceCmtScoreVO pcv = new
		 * PlaceCmtScoreVO();
		 * pcv.setName(cmtLatitudeStatistics.getLatitudeName());
		 * pcv.setScore(cmtLatitudeStatistics.getAvgScore()+"");
		 * if(cmtLatitudeStatistics
		 * .getLatitudeId().equals("FFFFFFFFFFFFFFFFFFFFFFFFFFFF")){
		 * pcv.setMain(true); } pcsVoList.add(pcv); }
		 * cp.setPlaceCmtScoreList(pcsVoList);
		 */

		return cp;
	}

	/**
	 * 门票和酒店需要brachId 线路类型需要productId
	 */
	@Override
	public List<MobileTimePrice> timePrice(Map<String, Object> params) {
		ArgCheckUtils.validataRequiredArgs("branchId", params);
		Long productId = null;
		Object branchId = params.get("branchId");
		if (params.get("productId") != null) {
			productId = Long.valueOf(params.get("productId").toString());
		}
		List<MobileTimePrice> mtpList = new ArrayList<MobileTimePrice>();
		if (branchId == null) {
			List<ProdProductBranch> list = prodProductService
					.getProductBranchByProductId(productId, "false");
			ProdProductBranch mainProductBranch = list.get(0);
			branchId = mainProductBranch.getProdBranchId();
		}

		ProdProductBranch ppb = prodProductBranchService
				.getProductBranchDetailByBranchId(
						Long.valueOf(branchId.toString()), null, true);
		if (ppb == null) {
			throw new NotFoundException("产品不可售");
		}
		List<TimePrice> timePriceList = prodProductService
				.getMainProdTimePrice(ppb.getProdProduct().getProductId(),
						Long.valueOf(branchId.toString()));

		if (timePriceList != null && !timePriceList.isEmpty()) {
			for (TimePrice timePrice : timePriceList) {
				if (timePrice.isSellable(1)) {
					MobileTimePrice mtp = new MobileTimePrice();
					mtp.setDate(DateUtil.getFormatDate(timePrice.getSpecDate(),
							"yyyy-MM-dd"));
					mtp.setSellPriceYuan(timePrice.getSellPriceFloat());
					mtp.setMarketPriceYuan(timePrice.getMarketPriceFloat());
					mtp.setSellable(timePrice.isSellable(1));
					mtpList.add(mtp);
				}

			}
		} else {
			throw new LogicException("无可预订日期");
		}

		return mtpList;
	}

	@Override
	public Map<String, Object> getGroupOnList(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("page", param);

		Map<String, Object> resultMap = new HashMap<String, Object>();

		ClientRouteSearchVO searchVo = new ClientRouteSearchVO();
		searchVo.setChannel(Constant.CHANNEL.TUANGOU.name());
		List<String> productTypes = new ArrayList<String>();
		productTypes.add(Constant.PRODUCT_TYPE.TICKET.name());
		productTypes.add(Constant.PRODUCT_TYPE.ROUTE.name());
		searchVo.setProductType(productTypes);
		searchVo.setPage(Integer.valueOf(param.get("page").toString()));

		if (param.get("productType") != null) {
			searchVo.setProductType(ClientUtils.convetToList(param.get(
					"productType").toString()));
		}

		if (param.get("sort") != null) {
			searchVo.setSort(param.get("sort").toString());
		}

		if (param.get("toDest") != null) {
			searchVo.setToDest(param.get("toDest").toString());
		}

		Page<ProductBean> pageConfig = vstClientProductService
				.newRouteSearch(searchVo);
		List<MobileGroupOn> cglist = new ArrayList<MobileGroupOn>();
		List<ProductSearchInfo> list = new ArrayList<ProductSearchInfo>();
		for (ProductBean bean : pageConfig.getItems()) {

			Long productId = bean.getProductId();
			Map<String, Object> queryParam = new HashMap<String, Object>();
			queryParam.put("productId", productId);

			List<ProductSearchInfo> returnlist = productSearchInfoService
					.queryProductSearchInfoByParam(queryParam);
			if (!returnlist.isEmpty()) {
				list.addAll(returnlist);
			}
		}

		for (ProductSearchInfo productSearchInfo : list) {
			// this.copyMobileGroupon(productSearchInfo, cglist);
			MobileGroupOn mobileGroupOn = this
					.copyMobileGroupon(productSearchInfo);
			cglist.add(mobileGroupOn);
		}

		resultMap.put("isLastPage", isLastPage(pageConfig));
		if (param.get("sort") != null) {
			if ("priceDesc".equals(param.get("sort"))) {
				Collections.sort(cglist, new Comparator<MobileGroupOn>() {

					@Override
					public int compare(MobileGroupOn o1, MobileGroupOn o2) {
						if (o1.getSellPriceYuan() < o2.getSellPriceYuan()) {
							return 1;
						} else if (o1.getSellPriceYuan() > o2
								.getSellPriceYuan()) {
							return -1;
						}
						return 0;
					}

				});
			} else if ("priceAsc".equals(param.get("sort"))) {
				Collections.sort(cglist, new Comparator<MobileGroupOn>() {
					@Override
					public int compare(MobileGroupOn o1, MobileGroupOn o2) {
						if (o1.getSellPriceYuan() < o2.getSellPriceYuan()) {
							return -1;
						} else if (o1.getSellPriceYuan() > o2
								.getSellPriceYuan()) {
							return 1;
						}
						return 0;
					}

				});

			} else if ("orderCount".equals(param.get("sort"))) {
				Collections.sort(cglist, new Comparator<MobileGroupOn>() {

					@Override
					public int compare(MobileGroupOn o1, MobileGroupOn o2) {
						// TODO Auto-generated method stub
						if (Long.valueOf(o1.getOrderCount()) > Long.valueOf(o2
								.getOrderCount())) {
							return -1;
						} else if (Long.valueOf(o1.getOrderCount()) < Long
								.valueOf(o2.getOrderCount())) {
							return 1;
						}
						return 0;

					}

				});
			}
		}
		resultMap.put("datas", cglist);
		return resultMap;

	}

	@Override
	public MobileGroupOn getGroupOnDetail(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("productId", param);
		Map<String, Object> queryParam = new HashMap<String, Object>();
		queryParam.put("productId", param.get("productId"));
		List<ProductSearchInfo> returnlist = productSearchInfoService
				.queryProductSearchInfoByParam(queryParam);
		if (null != returnlist && returnlist.size() > 0) {
			// this.copyMobileGroupon(returnlist.get(0), cglist);
			MobileGroupOn mobileGroupOn = this.copyMobileGroupon(returnlist
					.get(0));
			if (null != mobileGroupOn) {
				return mobileGroupOn;
			} else {
				throw new NotFoundException("产品已下架");
			}
		} else {
			throw new NotFoundException("产品已下架");
		}
	}

	/**
	 * 重写ClientProductServiceImpl#copyMobileGroupon(ProductSearchInfo, List);
	 * 
	 * @see ClientProductServiceImpl#copyMobileGroupon(ProductSearchInfo, List);
	 * @param psi
	 * @return
	 */
	private MobileGroupOn copyMobileGroupon(ProductSearchInfo psi) {
		Map<String, Object> returnMap = groupDreamService
				.getTodayGroupProduct(psi.getProductId());
		MobileGroupOn cg = new MobileGroupOn();
		if (returnMap != null) {
			cg.setOrderCount(returnMap.get("orderCount").toString());
			cg.setMinGroupSize(returnMap.get("MIN_GROUP_SIZE").toString());
			cg.setManagerRecommend(StringUtil.filterOutHTMLTags(String
					.valueOf(returnMap.get("MANAGERRECOMMEND") == null ? ""
							: returnMap.get("MANAGERRECOMMEND"))));
			if (returnMap.get("pageId") != null) {
				List<ComPicture> list = comPictureService
						.getPictureByPageId((Long) returnMap.get("pageId"));
				List<String> cpList = new ArrayList<String>();
				for (ComPicture comPicture : list) {
					if (null != comPicture.getPictureUrl()) {
						cpList.add(comPicture.getPictureUrl());
					}
				}
				cg.setImageList(cpList);
			}
		}

		cg.setProductId(psi.getProductId());
		cg.setProductName(psi.getProductName());
		cg.setMarketPriceYuan(PriceUtil.convertToYuan(psi.getMarketPrice()));
		cg.setSellPriceYuan(PriceUtil.convertToYuan(psi.getSellPrice()));
		cg.setSmallImage(psi.getSmallImage());
		cg.setLargeImage(psi.getLargeImage());
		cg.setOfflineTime(DateUtil.getFormatDate(psi.getOfflineTime(),
				"yyyy-MM-dd HH:mm:ss"));
		ProdProductBranch ppb = prodProductService
				.getProductDefaultBranchByProductId(psi.getProductId());
		cg.setBranchId(String.valueOf(null == ppb.getProdBranchId() ? "" : ppb
				.getProdBranchId()));
		cg.setProductType(psi.getProductType());
		cg.setSubProductType(psi.getSubProductType());
		cg.setRemainTime(com.lvmama.clutter.utils.DateUtil
				.getRemainTimeByCurrentDate(psi.getOfflineTime()));

		/******************* V3.1 ********************/
		// 团购优惠标签
		ClientUtils.getGouponBuyPreferInfo(cg, psi);
		cg.setMaxCashRefund(null == psi.getCashRefund() ? 0l : PriceUtil
				.convertToFen(psi.getCashRefund()));
		// 签证
		ProdCProduct prodCProduct = pageService.getProdCProduct(psi
				.getProductId());
		cg.setVisa(ClientUtils.needVisa(prodCProduct));

		Map<String, Object> prodCProductInfoMap = pageService
				.getProdCProductInfo(psi.getProductId(), true);
		ViewPage viewPage = (ViewPage) prodCProductInfoMap.get("viewPage");

		// 推荐理由
		Set<MobileGrouponContent> viewContents = new TreeSet<MobileGrouponContent>();

		// 行程说明
		@SuppressWarnings("unchecked")
		List<ViewJourney> viewJourneys = (List<ViewJourney>) prodCProductInfoMap
				.get("viewJourneyList");
		if (viewJourneys != null && viewJourneys.size() > 0) {
			cg.setViewJourneys(viewJourneys);
			cg.setViewJourneyUrl("/html5/index.htm?productId="
					+ psi.getProductId() + "&type=JOURNEY");
		}

		/*
		 * １） 推荐理由　 ２） 行程说明 ３） 用户点评 ４） 产品特色 ５） 交通信息 ６） 费用说明 ７） 预订须知 ８） 签证说明　
		 */
		Set<MobileGrouponContent> tagContents = new TreeSet<MobileGrouponContent>();
		if (null != viewPage) {
			Map<String, Object> contents = viewPage.getContents();
			// 初始化需要显示的key
			getContentKeys();
			for (Entry<String, Object> entry : contents.entrySet()) {
				String key = entry.getKey();
				if (!cg.isVisa()) {// 判断是否有签证
					//contentKeys.remove("VISA");
				} else {//签证来源和签证url不相同
					MobileGrouponContent grouponContent = new MobileGrouponContent();
					grouponContent.setSort(7);
					// grouponContent.setContentId(viewContent.getContentId());
					grouponContent.setContentTag(Constant.VIEW_CONTENT_TYPE
							.getCnName(key));
					grouponContent.setContentType(key);
					grouponContent.setContentUrl("/html5/visa.htm?productId="
							+ psi.getProductId());
					tagContents.add(grouponContent);
				}
				// 是否显示标签，内容
				if (contentKeys.contains(key) && viewPage.hasContent(key)) {
					ViewContent viewContent = (ViewContent) entry.getValue();
					if (viewContent != null && viewContent.getContent() != null) {
						MobileGrouponContent grouponContent = new MobileGrouponContent();

						if ("MANAGERRECOMMEND".equals(key)) {// 需要更改tag和输出content
							grouponContent.setSort(1);// 排序的顺序
							grouponContent.setContentId(viewContent
									.getContentId());
							grouponContent.setContentTag("推荐理由");
							grouponContent.setContentType(key);
							grouponContent
									.setContent(viewContent.getContent() == null ? ""
											: ClientUtils
													.filterOutHTMLTags(viewContent
															.getContent()));
							viewContents.add(grouponContent);
						} else if ("ANNOUNCEMENT".equals(key)) {
							grouponContent.setSort(2);// 排序的顺序
							grouponContent.setContentId(viewContent
									.getContentId());
							grouponContent.setContentTag("公告");
							grouponContent.setContentType(key);
							grouponContent
									.setContent(viewContent.getContent() == null ? ""
											: ClientUtils
													.filterOutHTMLTags(viewContent
															.getContent()));
							viewContents.add(grouponContent);
						} else {
							if (false) {// 暂时没有点评，用户点评
								// grouponContent.setContentTag("用户点评");
								// grouponContent.setSort(1);//排序
							} else if ("FEATURES".equals(key)) {
								grouponContent.setSort(2);
							} else if ("TRAFFICINFO".equals(key)) {
								grouponContent.setSort(3);
							} else if ("COSTCONTAIN".equals(key)) {
								grouponContent.setContentTag("费用说明");
								grouponContent.setSort(4);
							} else if ("ORDERTOKNOWN".equals(key)) {
								grouponContent.setSort(5);
//							} else if ("ACITONTOKNOW".equals(key)) {
//								grouponContent.setSort(6);
								// }else if ("VISA".equals(key)) {
								// grouponContent.setSort(7);
							} else {

							}
							grouponContent.setContentId(viewContent
									.getContentId());
							grouponContent
									.setContentTag(Constant.VIEW_CONTENT_TYPE
											.getCnName(key));
							grouponContent.setContentType(key);
							grouponContent
									.setContentUrl("/html5/index.htm?productId="
											+ psi.getProductId()
											+ "&type="
											+ key);
							tagContents.add(grouponContent);
						}
					}
				}
			}
		}
		cg.setViewContents(viewContents);
		cg.setTagContents(tagContents);

		return cg;
	}

	/**
	 * 被重写 ClientProductServiceImpl#copyMobileGroupon(ProductSearchInfo);
	 * 
	 * @see ClientProductServiceImpl#copyMobileGroupon(ProductSearchInfo);
	 * @param psi
	 * @param cglist
	 */
	@Deprecated
	private void copyMobileGroupon(ProductSearchInfo psi,
			List<MobileGroupOn> cglist) {
		Map<String, Object> returnMap = groupDreamService
				.getTodayGroupProduct(psi.getProductId());
		MobileGroupOn cg = new MobileGroupOn();
		if (returnMap != null) {
			// MobileGroupOn cg = new MobileGroupOn();
			cg.setProductId(psi.getProductId());
			cg.setProductName(psi.getProductName());
			cg.setMarketPriceYuan(PriceUtil.convertToYuan(psi.getMarketPrice()));
			cg.setSellPriceYuan(PriceUtil.convertToYuan(psi.getSellPrice()));
			cg.setSmallImage(psi.getSmallImage());
			cg.setLargeImage(psi.getLargeImage());
			cg.setOfflineTime(DateUtil.getFormatDate(psi.getOfflineTime(),
					"yyyy-MM-dd HH:mm:ss"));
			cg.setOrderCount(returnMap.get("orderCount").toString());
			cg.setMinGroupSize(returnMap.get("MIN_GROUP_SIZE").toString());
			ProdProductBranch ppb = prodProductService
					.getProductDefaultBranchByProductId(psi.getProductId());
			cg.setBranchId(String.valueOf(null == ppb.getProdBranchId() ? ""
					: ppb.getProdBranchId()));
			cg.setProductType(psi.getProductType());
			cg.setSubProductType(psi.getSubProductType());
			cg.setManagerRecommend(StringUtil.filterOutHTMLTags(String
					.valueOf(returnMap.get("MANAGERRECOMMEND") == null ? ""
							: returnMap.get("MANAGERRECOMMEND"))));
			if (returnMap.get("pageId") != null) {
				List<ComPicture> list = comPictureService
						.getPictureByPageId((Long) returnMap.get("pageId"));
				List<String> cpList = new ArrayList<String>();
				for (ComPicture comPicture : list) {
					if (null != comPicture.getPictureUrl()) {
						cpList.add(comPicture.getPictureUrl());
					}
				}
				cg.setImageList(cpList);
			}

			cg.setRemainTime(com.lvmama.clutter.utils.DateUtil
					.getRemainTimeByCurrentDate(psi.getOfflineTime()));

			/******************* V3.1 ********************/
			// 团购优惠标签
			ClientUtils.getGouponBuyPreferInfo(cg, psi);
			cg.setMaxCashRefund(null == psi.getCashRefund() ? 0l : PriceUtil
					.convertToFen(psi.getCashRefund()));
			// 签证
			ProdCProduct prodCProduct = pageService.getProdCProduct(psi
					.getProductId());
			cg.setVisa(ClientUtils.needVisa(prodCProduct));

			cglist.add(cg);
		}

		Map<String, Object> prodCProductInfoMap = pageService
				.getProdCProductInfo(psi.getProductId(), true);
		log.info(JSONObject.toJSON(prodCProductInfoMap));
		ViewPage viewPage = (ViewPage) prodCProductInfoMap.get("viewPage");

		// 推荐理由
		Set<MobileGrouponContent> viewContents = new TreeSet<MobileGrouponContent>();

		// 行程说明
		@SuppressWarnings("unchecked")
		List<ViewJourney> viewJourneys = (List<ViewJourney>) prodCProductInfoMap
				.get("viewJourneyList");
		if (viewJourneys != null && viewJourneys.size() > 0) {
			cg.setViewJourneys(viewJourneys);
			cg.setViewJourneyUrl("/html5/index.htm?productId="
					+ psi.getProductId() + "&type=JOURNEY");
		}

		/*
		 * １） 推荐理由　 ２） 行程说明 ３） 用户点评 ４） 产品特色 ５） 交通信息 ６） 费用说明 ７） 预订须知 ８） 签证说明　
		 */
		Set<MobileGrouponContent> tagContents = new TreeSet<MobileGrouponContent>();
		if (null != viewPage) {
			Map<String, Object> contents = viewPage.getContents();
			// 初始化需要显示的key
			getContentKeys();
			for (Entry<String, Object> entry : contents.entrySet()) {
				String key = entry.getKey();
				// 是否显示标签，内容
				if (contentKeys.contains(key) && viewPage.hasContent(key)) {
					ViewContent viewContent = (ViewContent) entry.getValue();
					if (viewContent != null && viewContent.getContent() != null) {
						MobileGrouponContent grouponContent = new MobileGrouponContent();

						if ("MANAGERRECOMMEND".equals(key)) {// 需要更改tag和输出content
							grouponContent.setSort(1);// 排序的顺序
							grouponContent.setContentId(viewContent
									.getContentId());
							grouponContent.setContentTag("推荐理由");
							grouponContent.setContentType(key);
							grouponContent
									.setContent(viewContent.getContent() == null ? ""
											: ClientUtils
													.filterOutHTMLTags(viewContent
															.getContent()));
							viewContents.add(grouponContent);
						} else if ("ANNOUNCEMENT".equals(key)) {
							grouponContent.setSort(2);// 排序的顺序
							grouponContent.setContentId(viewContent
									.getContentId());
							grouponContent.setContentTag("公告");
							grouponContent.setContentType(key);
							grouponContent
									.setContent(viewContent.getContent() == null ? ""
											: ClientUtils
													.filterOutHTMLTags(viewContent
															.getContent()));
							viewContents.add(grouponContent);
						} else {
							if (false) {// 暂时没有点评，用户点评
								// grouponContent.setContentTag("用户点评");
								// grouponContent.setSort(1);//排序
							} else if ("FEATURES".equals(key)) {
								grouponContent.setSort(2);
							} else if ("TRAFFICINFO".equals(key)) {
								grouponContent.setSort(3);
							} else if ("COSTCONTAIN".equals(key)) {
								grouponContent.setContentTag("费用说明");
								grouponContent.setSort(4);
							} else if ("ORDERTOKNOWN".equals(key)) {
								grouponContent.setSort(5);
							} else if ("ACITONTOKNOW".equals(key)) {
								grouponContent.setSort(6);
							} else if ("VISA".equals(key)) {
								grouponContent.setSort(7);
							} else {

							}
							grouponContent.setContentId(viewContent
									.getContentId());
							grouponContent
									.setContentTag(Constant.VIEW_CONTENT_TYPE
											.getCnName(key));
							grouponContent.setContentType(key);
							grouponContent
									.setContentUrl("/html5/index.htm?productId="
											+ psi.getProductId()
											+ "&type="
											+ key);
							tagContents.add(grouponContent);
						}
					}
				}
			}
		}
		cg.setViewContents(viewContents);
		cg.setTagContents(tagContents);
	}

	@Override
	public Map<String, Object> getBranches(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("placeId", param);
		Place place = this.placeService.queryPlaceByPlaceId(Long.valueOf(param
				.get("placeId").toString()));

		Map<String, Object> allElementMap = new HashMap<String, Object>();
		List<Map<String, Object>> singleList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> suitList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> unionList = new ArrayList<Map<String, Object>>();

		ProductList productList = this.productSearchInfoService
				.getIndexProductByPlaceIdAnd4TypeAndTicketBranch(
						place.getPlaceId(), 1000,
						Constant.CHANNEL.CLIENT.name());

		List<ProdBranchSearchInfo> list = productList.getProdBranchTicketList();
		Map<Long, List<ProdBranchSearchInfo>> groupMap = new LinkedHashMap<Long, List<ProdBranchSearchInfo>>();

		for (int i = 0; i < list.size(); i++) {
			ProdBranchSearchInfo pbsi = list.get(i);
			List<ProdBranchSearchInfo> tempList = groupMap.get(pbsi
					.getProductId());
			if (tempList == null) {
				tempList = new ArrayList<ProdBranchSearchInfo>();
				tempList.add(pbsi);
				groupMap.put(pbsi.getProductId(), tempList);
			} else {
				tempList.add(pbsi);
			}
		}
		Iterator<Entry<Long, List<ProdBranchSearchInfo>>> it = groupMap
				.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Long, List<ProdBranchSearchInfo>> entry = it.next();
			Long productId = entry.getKey();

			// ProdProduct prod =
			// this.prodProductService.getProdProduct(productId);
			ProductSearchInfo prod = this.productSearchInfoService
					.queryProductSearchInfoByProductId(productId);
			if ("true".equals(prod.getIsAperiodic())) {
				continue;
			}

			List<ProdBranchSearchInfo> pbsiList = entry.getValue();
			if (prod.getSubProductType().equals(
					Constant.SUB_PRODUCT_TYPE.SINGLE.name())
					|| prod.getSubProductType().equals(
							Constant.SUB_PRODUCT_TYPE.WHOLE.name())) {
				List<MobileBranch> tempList = new ArrayList<MobileBranch>();
				for (ProdBranchSearchInfo pbsi : pbsiList) {
					MobileBranch mb = ClientUtils.copyTicketBranch(pbsi, prod);
					mb.setHasShareToWeixin(super.hasWeiXinShared(param,
							mb.getBranchId()));
					this.setTodayOrderTips2(mb, false);
					tempList.add(mb);
				}
				Map<String, Object> obj = new HashMap<String, Object>();
				obj.put("productName", prod.getProductName());
				obj.put("datas", tempList);
				singleList.add(obj);
			}

			if (prod.getSubProductType().equals(
					Constant.SUB_PRODUCT_TYPE.UNION.name())) {
				List<MobileBranch> tempList = new ArrayList<MobileBranch>();
				for (ProdBranchSearchInfo pbsi : pbsiList) {
					MobileBranch mb = ClientUtils.copyTicketBranch(pbsi, prod);
					mb.setHasShareToWeixin(super.hasWeiXinShared(param,
							mb.getBranchId()));
					this.setTodayOrderTips2(mb, false);
					tempList.add(mb);

				}
				Map<String, Object> obj = new HashMap<String, Object>();
				obj.put("productName", prod.getProductName());
				obj.put("datas", tempList);
				unionList.add(obj);
			}

			if (prod.getSubProductType().equals(
					Constant.SUB_PRODUCT_TYPE.SUIT.name())) {
				List<MobileBranch> tempList = new ArrayList<MobileBranch>();
				for (ProdBranchSearchInfo pbsi : pbsiList) {
					MobileBranch mb = ClientUtils.copyTicketBranch(pbsi, prod);
					mb.setHasShareToWeixin(super.hasWeiXinShared(param,
							mb.getBranchId()));
					this.setTodayOrderTips2(mb, false);
					tempList.add(mb);
				}
				Map<String, Object> obj = new HashMap<String, Object>();
				obj.put("productName", prod.getProductName());
				obj.put("datas", tempList);
				suitList.add(obj);
			}
		}

		allElementMap.put("single", singleList);
		allElementMap.put("union", unionList);
		allElementMap.put("suit", suitList);
		return allElementMap;
	}

	@Override
	public List<MobileProductTitle> getPlaceRoutes(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("placeId", param);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("size", 1000);
		queryMap.put("placeId", param.get("placeId"));
		queryMap.put("channel", Constant.CHANNEL.CLIENT.name());
		List<ProductSearchInfo> searchInfoList = productSearchInfoService
				.getProductByFromPlaceIdAndDestId(param);
		List<MobileProductTitle> mpList = new ArrayList<MobileProductTitle>();

		for (ProductSearchInfo productSearchInfo : searchInfoList) {
			if (productSearchInfo.hasAperiodic()) {
				/**
				 * 过滤期票
				 */
				continue;
			}
			if ("true".equals(productSearchInfo.getSelfPack())) {
				continue;
			}
			MobileProductTitle mp = new MobileProductTitle();

			BeanUtils.copyProperties(productSearchInfo, mp);
			mp.setMarketPriceYuan(PriceUtil.convertToYuan(productSearchInfo
					.getMarketPrice()));
			mp.setSellPriceYuan(PriceUtil.convertToYuan(productSearchInfo
					.getSellPrice()));
			mp.setSmallImage(productSearchInfo.getSmallImage());
			mp.setProductId(productSearchInfo.getProductId());

			/***** V3.1 *****/
			mp.setMaxCashRefund(StringUtils.isEmpty(productSearchInfo
					.getCashRefund()) ? 0l : PriceUtil
					.convertToFen(productSearchInfo.getCashRefund()));
			mp.setHasBusinessCoupon(ClientUtils
					.hasBusinessCoupon(productSearchInfo)); // 优惠
			mpList.add(mp);
		}

		return mpList;
	}

	@Override
	public Map<String, Object> getProductItems(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("branchId", "udid", param);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<MobileBranch> branchList = new ArrayList<MobileBranch>();
		Long branchId = Long.valueOf(param.get("branchId").toString());

		// 秒杀产品校验缓存里的库存
		ProdSeckillRule prodSeckillRule = clientSeckillService
				.getSeckillRuleByBranchId(branchId);
		if (prodSeckillRule != null) {// 是否秒杀产品
			String seckillStatus = SeckillUtils.getSeckillStatus(prodSeckillRule);
			resultMap.put("seckillStatus", seckillStatus);
			if (EnumSeckillStatus.SECKILL_BEFORE.name().equals(seckillStatus)) {
				throw new NotFoundException("距离秒杀还有段时间哦，请稍后再抢！ ");
			} else if (EnumSeckillStatus.SECKILL_AFTER.name().equals(seckillStatus)
					|| EnumSeckillStatus.SECKILL_FINISHED.name().equals(
							seckillStatus)) {
				throw new NotFoundException("秒杀已结束，下次早点来哦！");
			} else {
				long waitPeopleCount = clientSeckillService
						.getWaitPeopleByMemcached(branchId, true, 1L);
				// 缓存是否有库存
				if (waitPeopleCount < 1) {
					throw new NotFoundException("秒杀已结束，下次早点来哦！");
				}
			}
		}

		Date visitDate = null;
		boolean isTodayOrder = false;
		if (param.get("todayOrder") != null) {
			isTodayOrder = Boolean.valueOf(param.get("todayOrder").toString());

		}
		// DateUtil.getDateByStr(visitTime, "yyyy-MM-dd")
		ProdProductBranch ppb = prodProductBranchService
				.getProductBranchDetailByBranchId(branchId, null, true);

		if (null == ppb || ppb.getProdProduct() == null || !ppb.hasOnline()) {
			throw new NotFoundException("产品不可售");
		}
		Boolean isPhysical = Boolean
				.valueOf(ppb.getProdProduct().getPhysical());

		// 增加productName逻辑
		ProductSearchInfo productSearchInfo = productSearchInfoService
				.queryProductSearchInfoByProductId(ppb.getProdProduct()
						.getProductId());

		String productName = productSearchInfo.getProductName();
		String branchName = ppb.getBranchName();
		String fullProductName = "";
		StringBuffer buffer = new StringBuffer();
		if (!StringUtils.isEmpty(productName)
				&& !StringUtils.isEmpty(branchName)) {
			buffer.append(productName).append("（").append(branchName)
					.append("）");
		}
		fullProductName = buffer.toString();
		resultMap.put("productName", fullProductName);
		resultMap.put("largeImage", productSearchInfo.getLargeImageUrl());

		resultMap.put("physical", isPhysical);
		resultMap.put("smallImage", ppb.getProdProduct().getSmallImage());
		Place place = prodProductPlaceService.getToDestByProductId(ppb
				.getProdProduct().getProductId());
		resultMap.put("placeName", place != null ? place.getName() : "");

		resultMap.put("protocolChecked", true);
		/**
		 * 当天预订
		 */
		if (isTodayOrder) {
			visitDate = new Date();
		} else {
			if (param.get("visitTime") != null) {
				String visitTimeStr = param.get("visitTime").toString();
				visitDate = DateUtil.getDateByStr(visitTimeStr, "yyyy-MM-dd");
			}
			/**
			 * 获得最近一天可预订日期
			 */
			if (visitDate == null) {
				List<TimePrice> timePriceList = null;

				timePriceList = prodProductService.getMainProdTimePrice(ppb
						.getProdProduct().getProductId(), Long.valueOf(branchId
						.toString()));

				if (timePriceList == null || timePriceList.isEmpty()) {
					throw new NotFoundException("产品不可售");
				}

				Iterator<TimePrice> it = timePriceList.iterator();
				while (it.hasNext()) {
					TimePrice cTimePrice = it.next();
					if (cTimePrice.isSellable(1)) {
						visitDate = cTimePrice.getSpecDate();
						break;
					}
				}
				if (visitDate == null) {
					throw new NotFoundException("产品不可售");
				}
			}
		}

		resultMap.put("couponAble",
				"true".equals(ppb.getProdProduct().getCouponAble()));

		if (ppb.getProdProduct().isPaymentToSupplier()) {
			resultMap.put("couponAble", false);
		}

		if (isTodayOrder) {
			MobileBranch mb = new MobileBranch();
			ProdBranchSearchInfo pbsi = productSearchInfoService
					.getProdBranchSearchInfoByBranchId(branchId);
			mb.setBranchId(branchId);
			mb.setCanOrderToday(pbsi.todayOrderAble());
			mb.setTodayOrderLastOrderTime(pbsi.getTodayOrderAbleTime());
			this.setTodayOrderTips2(mb, true);

			resultMap.put("cancelStategy", mb.getTodayOrderTips() == null ? ""
					: mb.getTodayOrderTips());
			resultMap.put(
					"orderTips",
					mb.getTodayOrderTips() == null ? "" : mb
							.getTodayOrderTips());

			if (!pbsi.canOrderTodayCurrentTime()) {
				throw new LogicException("已超过最晚可预订时间");
			}
		} else {
			if (ppb.getProdProduct().isTicket()) {
				if (isPhysical) {
					resultMap.put("orderTips",
							"① 此门票为实体票，需选快递方式支付对应费用和填写收件地址。 ");
				} else {
					resultMap.put("orderTips",
							"① 订单提交成功后，驴妈妈将会发订单确认短信到你的手机，凭短信可顺利入园。 ");
				}
			} else if (ppb.getProdProduct().isRoute()) {
				resultMap.put("orderTips", "① 订单提交成功后，驴妈妈将会发订单确认短信到你的手机。 ");
			}
		}

		/**
		 * 处理门票类型的关联产品 对于每一种类型的产品返回的关联产品和附加产品部一致需要单独处理
		 */
		if (ppb.getProdProduct().isTicket() || ppb.getProdProduct().isRoute()
				|| ppb.getProdProduct().isTrain()) {

			/**
			 * 3.0.1 版本增加的度假线路预定提示。
			 */

			ViewPage viewPage = viewPageService.selectByProductId(ppb
					.getProdProduct().getProductId());

			if (viewPage != null && viewPage.getPageId() != null) {

				viewPage = viewPageService.getViewPage(viewPage.getPageId());
				List<ViewContent> contentList = viewPage.getContentList();
				for (int i = 0; i < contentList.size(); i++) {
					ViewContent vc = contentList.get(i);
					/**
					 * 费用包含
					 */
					if (Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name().equals(
							vc.getContentType())) {
						resultMap.put(
								"constcontain",
								vc.getContent() == null ? "" : ClientUtils
										.filterOutHTMLTags(vc.getContent()));
					}
					/**
					 * 退款说明
					 */
					if (Constant.VIEW_CONTENT_TYPE.REFUNDSEXPLANATION.name()
							.equals(vc.getContentType())) {
						resultMap.put(
								"refundsExplanation",
								vc.getContent() == null ? "" : ClientUtils
										.filterOutHTMLTags(vc.getContent()));
					}

					/**
					 * 预定须知
					 */
					if (Constant.VIEW_CONTENT_TYPE.ORDERTOKNOWN.name().equals(
							vc.getContentType())
							&& !StringUtils.isEmpty(vc.getContent())) {
						if (null == resultMap.get("orderTips")
								|| StringUtils.isEmpty(resultMap.get(
										"orderTips").toString())) {
							resultMap.put(
									"orderTips",
									"①"
											+ ClientUtils.filterOutHTMLTags(vc
													.getContent()));
						} else {
							resultMap.put(
									"orderTips",
									resultMap.get("orderTips")
											+ ClientUtils.contentTag(resultMap
													.get("orderTips")
													.toString())
											+ ClientUtils.filterOutHTMLTags(vc
													.getContent()));
						}
					}
				}
			}

			Long productId = ppb.getProdProduct().getProductId();
			List<ProdProductBranch> listBranches = null;
			if (isTodayOrder) {
				listBranches = prodProductService.getProductBranchByProductId(
						productId, "false");
			} else {
				listBranches = this.productServiceProxy.getProdBranchList(
						productId, null, visitDate);
			}

			if (listBranches == null || listBranches.isEmpty()) {
				throw new NotFoundException("产品不可售");
			}

			for (ProdProductBranch prodProductBranch : listBranches) {
				MobileBranch mb = new MobileBranch();
				ProdProductBranch prodBrancheVisit = null;

				TimePrice tp = null;
				if (isTodayOrder) {
					/**
					 * 验证是否当天预订同一种门票预订了1次以上 ios 7 不处理这个逻辑
					 */

					/*
					 * if(isLowerIOS7(param, false)){ List<ClientOrderReport>
					 * reportList =
					 * comClientService.getTodayOrderByUdid(param.get
					 * ("udid").toString()); if(reportList!=null
					 * &&reportList.size()>0){ for (ClientOrderReport
					 * clientOrderReport : reportList) { OrdOrder order =
					 * orderServiceProxy
					 * .queryOrdOrderByOrderId(clientOrderReport.getOrderId());
					 * if(order!=null && order.getMainProduct()!=null){
					 * if(!order
					 * .isCanceled()&&prodProductBranch.getProdBranchId(
					 * )==order.getMainProduct().getProdBranchId().longValue()){
					 * throw new LogicException("您超过了今日预订门票限额"); } } } }
					 * 
					 * }
					 */

					prodBrancheVisit = this.prodProductService
							.getPhoneProdBranchDetailByProdBranchId(
									prodProductBranch.getProdBranchId(),
									DateUtil.getDayStart(visitDate), true);
					/**
					 * 验证今日票是否可售
					 */
					tp = prodProductBranchService.calcCurrentProdTimePric(
							Long.valueOf(prodProductBranch.getProdBranchId()),
							DateUtil.getDayStart(visitDate));

				} else {
					prodBrancheVisit = this.prodProductService
							.getProdBranchDetailByProdBranchId(
									prodProductBranch.getProdBranchId(),
									visitDate, true);
					/**
					 * 验证库存
					 */
					if (param.get("visitTime") != null) {
						tp = prodProductBranchService.getProdTimePrice(
								prodProductBranch.getProdBranchId(), visitDate);
					}

				}

				if (param.get("visitTime") != null || isTodayOrder) {
					/**
					 * 判断是否是点击的类别
					 */
					if (branchId.equals(prodProductBranch.getProdBranchId())) {
						if (prodBrancheVisit == null || tp == null) {
							throw new NotFoundException("产品不可售");
						}

						if (!tp.isSellable(1)) {
							throw new LogicException("产品库存不足");
						}
					}

					if (prodBrancheVisit == null || tp == null) {
						continue;
					}

				}

				// 转换成mobile 对象
				// 处理附加产品
				mb.setMaximum(prodBrancheVisit.getMaximum());
				mb.setShortName(prodBrancheVisit.getBranchName());
				mb.setMinimum(prodBrancheVisit.getMinimum());
				mb.setSellPriceYuan(prodBrancheVisit.getSellPriceYuan());
				mb.setMarketPriceYuan(prodBrancheVisit.getMarketPriceYuan());
				mb.setChildQuantity(prodBrancheVisit.getChildQuantity());
				mb.setAdultQuantity(prodBrancheVisit.getAdultQuantity());
				mb.setBranchId(prodBrancheVisit.getProdBranchId());
				mb.setProductId(prodBrancheVisit.getProductId());
				mb.setDescription(prodBrancheVisit.getDescription());

				if (null != prodBrancheVisit.getProdProduct()) {
					mb.setProductType(prodBrancheVisit.getProdProduct()
							.getProductType());
					mb.setSubProductType(prodBrancheVisit.getProdProduct()
							.getSubProductType());
				}
				branchList.add(mb);
			}
		}

		resultMap.put("isEContract", ppb.getProdProduct().isEContract());

		/**
		 * 处理协议url
		 */
		if (ppb.getProdProduct().isFreeness()) {
			resultMap.put("xieyiUrl", "/app/xieyi_freeness.html");
			resultMap.put("xieyiName", "驴妈妈自由行预订协议");
		} else if (ppb.getProdProduct().isForeign()) {
			resultMap.put("xieyiUrl", "/app/xieyi_chujin.html");
			resultMap.put("xieyiName", "驴妈妈出境游预订协议");
		} else if (ppb.getProdProduct().isTicket()) {
			resultMap.put("xieyiUrl", "/app/xieyi_ticket.html");
			resultMap.put("xieyiName", "驴妈妈票务预订协议");
		} else if (ppb.getProdProduct().isGroup()) {
			resultMap.put("xieyiUrl", "/app/xieyi_gentuan.html");
			resultMap.put("xieyiName", "驴妈妈跟团游预订协议");
		}

		resultMap.put("isRoute", ppb.getProdProduct().isRoute());
		resultMap.put("isTicket", ppb.getProdProduct().isTicket());
		resultMap.put("isEContract", ppb.getProdProduct().isEContract());

		if (ppb.getProdProduct().isHotel()) {
			MobileBranch mb = new MobileBranch();
			// 转换成mobile 对象
			// 处理附加产品
			mb.setMaximum(ppb.getMaximum());
			mb.setShortName(ppb.getBranchName());
			mb.setMinimum(ppb.getMinimum());
			mb.setSellPriceYuan(ppb.getSellPriceYuan());
			mb.setMarketPriceYuan(ppb.getMarketPriceYuan());
			mb.setBranchId(ppb.getProdBranchId());
			mb.setProductId(ppb.getProductId());
			branchList.add(mb);

		}

		/**
		 * 对于门票过滤掉附加产品 当是门票实体票的时候 需要附加产品
		 */
		if (null != param.get("appVersion")
				&& !StringUtils.isEmpty(param.get("appVersion").toString())) {
			Long appVersion = Long.valueOf(param.get("appVersion").toString());
			if (appVersion < 312L) {
				isPhysical = false;
			}
		}
		if (!ppb.getProdProduct().isTicket() || isPhysical) {

			/**
			 * 处理产品的附加产品。
			 */
			List<ProdProductRelation> relateList = productServiceProxy
					.getRelatProduct(ppb.getProdProduct().getProductId(),
							visitDate);

			for (ProdProductRelation prodProductRelation : relateList) {
				MobileBranch mb = new MobileBranch();
				// 获得附加产品的默认类别
				ProdProductBranch prodBrancheVisit = this.prodProductService
						.getProdBranchDetailByProdBranchId(
								prodProductRelation.getProdBranchId(),
								visitDate, true);
				if (prodBrancheVisit == null) {
					continue;
				}
				mb.setMaximum(prodBrancheVisit.getMaximum());
				mb.setShortName(prodBrancheVisit.getBranchName()
						+ "("
						+ prodBrancheVisit.getProdProduct()
								.getZhSubProductType() + ")");
				mb.setMinimum(prodBrancheVisit.getMinimum());
				mb.setSellPriceYuan(prodBrancheVisit.getSellPriceYuan());
				mb.setMarketPriceYuan(prodBrancheVisit.getMarketPriceYuan());
				mb.setBranchId(prodBrancheVisit.getProdBranchId());
				mb.setSaleNumType(prodProductRelation.getSaleNumType());
				mb.setAdditional(true);
				mb.setDescription(ClientUtils.spliteDescByStr(prodBrancheVisit
						.getDescription())); // 过滤掉 “详细信息请见”以后的字符串
				mb.setFullName(prodBrancheVisit.getFullName());
				mb.setBranchType(prodBrancheVisit.getBranchType());
				// qin20130905 add附加产品类型
				if (null != prodBrancheVisit
						&& null != prodBrancheVisit.getProdProduct()) {
					mb.setProductType(prodBrancheVisit.getProdProduct()
							.getProductType());
					mb.setSubProductType(prodBrancheVisit.getProdProduct()
							.getSubProductType());
					if (isPhysical
							&& !Constant.SUB_PRODUCT_TYPE.EXPRESS.name()
									.equals(prodBrancheVisit.getProdProduct()
											.getSubProductType())) {
						continue;
					}

					/**
					 * 目的地自由行 默认不让选择保险
					 */
					if (ppb.getProdProduct().isFreeness()
							&& Constant.SUB_PRODUCT_TYPE.INSURANCE.name()
									.equals(prodBrancheVisit.getProdProduct()
											.getSubProductType())) {
						continue;
					}
				}

				branchList.add(mb);
			}
		}
		/**
		 * 是否需要门票需要输入身份证以及自由行需要填写游玩人 这里的逻辑需要调整 根据travellerInfo 来判断联系人 游玩人 是否需要填写
		 * 下个版本修改。
		 */

		// v3.1 qin 线路也要身份证 第一游玩人的必填信息 CARD_NUMBER身份证 MOBILE手机号 NAME用户名pinyi
		List<String> list = ppb.getProdProduct().getContactInfoOptionsList();
		List<String> travelOptions = ppb.getProdProduct()
				.getFirstTravellerInfoOptionsList();
		resultMap.put("needIdCard",
				list != null
						&& (list.isEmpty() || list.contains("CARD_NUMBER")));
		/**
		 * 处理门票需要游玩人的情况
		 */
		if (travelOptions != null && !travelOptions.isEmpty()) {
			if (ppb.getProdProduct().isTicket()
					&& travelOptions.contains("CARD_NUMBER")) {
				resultMap.put("needIdCard", true);
			}
		}
		/**
		 * 目的地自由行过滤紧急联系人
		 */
		if (Constant.SUB_PRODUCT_TYPE.FREENESS.name().equals(
				ppb.getProdProduct().getSubProductType())) {
			resultMap.put("noEmergencyContact", true);
		}
		resultMap.put("nearDate", DateUtil.formatDate(visitDate, "yyyy-MM-dd"));

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		if (DateUtils.isSameDay(new Date(), visitDate)) {
			resultMap.put("timeHolder", "今天");
		} else {
			c.add(Calendar.DATE, 1);
			if (DateUtils.isSameDay(c.getTime(), visitDate)) {
				resultMap.put("timeHolder", "明天");
			} else {
				c.add(Calendar.DATE, 1);
				if (DateUtils.isSameDay(c.getTime(), visitDate)) {
					resultMap.put("timeHolder", "后天");
				} else {
					resultMap.put("timeHolder",
							DateUtil.formatDate(visitDate, "yyyy-MM-dd"));
				}
			}
		}

		resultMap.put("weekOfDay", DateUtil.getZHDay(visitDate));
		resultMap.put(
				"personList",
				getLatestPersonList(null == param.get("userNo") ? "" : param
						.get("userNo").toString(), ppb.getProdProduct()
						.getProductType()));
		resultMap.put("datas", branchList);

		// 积分兑换优惠券
		MobileCouponPoint mpoint = new MobileCouponPoint();
		if (Boolean.valueOf(resultMap.get("couponAble").toString())) { // 如果支持优惠券
			// resultMap.put("couponPointInfo",
			// getCouponByPoint(ppb.getProdProduct().getSubProductType(),String.valueOf(param.get("userNo")),resultMap));
			getCouponByPoint(ppb.getProdProduct().getSubProductType(),
					String.valueOf(param.get("userNo")), mpoint);
		}
		resultMap.put("couponPoint", mpoint);

		// 优惠活动.
		resultMap.put("couponList", new ArrayList<Map<String, Object>>());
		// 是否支持多顶多惠,早定早恵
		resultMap.put("hasBusinessCoupon", false);
		try {
			// 如果是支付给驴妈妈
			if (ppb.getProdProduct().isPaymentToLvmama()) {
				couponActivities(resultMap,
						"true".equals(ppb.getProdProduct().getIsAperiodic()),
						branchId, ppb.getValidEndTime(), visitDate,
						isTodayOrder);
			}

			ProductSearchInfo psi = this.productSearchInfoService
					.queryProductSearchInfoByProductId(ppb.getProdProduct()
							.getProductId());
			resultMap.put("hasBusinessCoupon",
					ClientUtils.hasBusinessCoupon(psi));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 获取自助巴士班的上车地点
		if (null != ppb && null != ppb.getProdProduct()) {
			ProdProduct prodProduct = ppb.getProdProduct();
			String subProductType = prodProduct.getSubProductType();
			if ("SELFHELP_BUS".equals(subProductType)) {
				Long productId = ppb.getProdProduct().getProductId();
				List<ProdAssemblyPoint> prodAssemblyPointList = prodProductService
						.queryAssembly(productId);
				resultMap.put("prodAssemblyPoint", prodAssemblyPointList);
			}
		}

		return resultMap;
	}

	/**
	 * 获取优惠信息 和 优惠券 .
	 * 
	 * @param resultMap
	 * @param isAperiodic
	 *            是否定期产品
	 * @param userNo
	 * @param brancId
	 * @param validEndTime
	 * @param visitDate
	 * 
	 */
	public void couponActivities(Map<String, Object> resultMap,
			boolean isAperiodic, Long brancId, Date validEndTime,
			Date visitDate, boolean isTodayOrder) {
		// 不定期产品用最后一天有效期做校验
		ProdProductBranch mainProdBranch = null;
		if (isAperiodic) {
			mainProdBranch = productServiceProxy
					.getProdBranchDetailByProdBranchId(brancId, validEndTime);
		} else {
			// 现在传入的为产品类别ID.为保证前台参数传的简单性,自己计算对应的产品ID.
			if (isTodayOrder) {
				mainProdBranch = prodProductService
						.getPhoneProdBranchDetailByProdBranchId(brancId,
								DateUtil.getDayStart(visitDate), true);
			} else {
				mainProdBranch = productServiceProxy
						.getProdBranchDetailByProdBranchId(brancId, visitDate);
			}
		}

		// 如果找不到相关产品
		if (null == mainProdBranch || null == mainProdBranch.getProdProduct()) {
			return;
		}
		// 如果是支付给驴妈妈 且 可以参加活动
		if ("true".equals(mainProdBranch.getProdProduct().getPayToLvmama())
				&& "true".equals(mainProdBranch.getProdProduct()
						.getCouponActivity())) {
			// 关联的附加商品.
			List<ProdProductRelation> relateList = null;
			// 不定期暂时不做附加产品
			if (!mainProdBranch.getProdProduct().IsAperiodic()) {
				// 关联的附加商品.
				relateList = this.productServiceProxy.getRelatProduct(
						mainProdBranch.getProductId(), visitDate);
			}

			// 查询可以使用优惠活动的附加(关联)产品
			List<Long> idsList = new ArrayList<Long>();
			List<String> subProductTypes = new ArrayList<String>();
			idsList.add(mainProdBranch.getProductId());
			subProductTypes.add(mainProdBranch.getProdProduct()
					.getSubProductType());
			if (!mainProdBranch.getProdProduct().IsAperiodic()) {
				for (ProdProductRelation relateProduct : relateList) {
					if (idsList.contains(relateProduct.getRelatProductId()
							.toString())) {
						idsList.add(relateProduct.getRelatProductId());
						subProductTypes.add(relateProduct.getRelationProduct()
								.getSubProductType());
					}
				}
			}

			// 超级自由行不使用优惠券
			if (!mainProdBranch.getProdProduct().hasSelfPack()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("productIds", idsList);
				map.put("subProductTypes", subProductTypes);
				map.put("withCode", "false");// 只取优惠活动
				List<MarkCoupon> orderCouponList = markCouponService
						.selectAllCanUseAndProductCanUseMarkCoupon(map);

				List<Map<String, Object>> couponList = new ArrayList<Map<String, Object>>();
				if (null != orderCouponList && orderCouponList.size() > 0) {
					for (int i = 0; i < orderCouponList.size(); i++) {
						MarkCoupon mc = orderCouponList.get(i);
						Map<String, Object> m = new HashMap<String, Object>();
						// couponId
						MarkCouponCode markCouponCode = markCouponService
								.generateSingleMarkCouponCodeByCouponId(mc
										.getCouponId());
						/**
						 * 老版本过滤微信立减
						 */
						Long weixinActivityId = Long.valueOf(ClutterConstant
								.getProperty("weixinActiviyCouponId"));
						if(weixinActivityId.intValue()==mc.getCouponId().intValue()){
							continue;
						}
						m.put("code", markCouponCode.getCouponCode());
						m.put("desc", mc.getFavorTypeDescription());
						m.put("title", "优惠活动");
						// 优惠金额
						m.put("amountYuan", ClientUtils.getFavorTypeAmount(mc));
						couponList.add(m);
					}
				}
				resultMap.put("couponList", couponList);
			}
		}
	}

	/**
	 * 根据产品的subProudctType查询可以兑换的优惠券。
	 * 
	 * @param subProductType
	 * @return
	 */
	public void getCouponByPoint(String subProductType, String userNo,
			MobileCouponPoint mpoint) {
		// 获取积分兑换优惠券规则
		MarkCouponPointChange markCouponPointChange = markCouponPointChangeService
				.selectBySubProductType(subProductType);
		if (null != markCouponPointChange) {
			MarkCoupon markCoupon = markCouponService
					.selectMarkCouponByPk(markCouponPointChange.getCouponId());
			if (null == markCoupon
					|| !"true".equals(markCoupon.getValid())
					|| ("FIXED".equals(markCoupon.getValidType()) && markCoupon
							.isOverDue())) {
				markCouponPointChange = null;
			} else {
				mpoint.setCouponId(markCoupon.getCouponId());
				mpoint.setNeedPoint(markCouponPointChange.getPoint());
				if (!"null".equals(userNo) && !StringUtils.isEmpty(userNo)) {
					UserUser user = userUserProxy.getUserUserByUserNo(userNo);
					if (null != user && null != user.getPoint()) {
						// 如果用户积分 大于 优惠券所需要的积分
						mpoint.setUserPoint(user.getPoint());
						if (user.getPoint() >= markCouponPointChange.getPoint()) {
							mpoint.setCouponYuan(PriceUtil
									.convertToYuan(markCoupon
											.getFavorTypeAmount()));
							mpoint.setTitle("积分抵扣你的积分可换￥"
									+ markCouponPointChange.getCouponName());
						}
					}
				}
			}
		}
	}

	/**
	 * 查询线路（自由行）详情 .
	 * 
	 * @throws Exception
	 */
	@Override
	public MobileProductRoute getRouteDetail(Map<String, Object> params)
			throws Exception {
		ArgCheckUtils.validataRequiredArgs("productId", params);
		Long productId = Long.valueOf(params.get("productId") + "");
		MobileProductRoute mpr = new MobileProductRoute(); // 自由行线路.
		// 检测产品是否存在.
		ProductResult pr = pageService.findProduct(productId);
		if (!pr.isExists()) {
			throw new NotFoundException("产品不可售");
		}
		// 获取产品信息.
		Map<String, Object> data = pageService.getProdCProductInfo(productId,
				false);

		// 加载产品图片.
		if (data != null && data.get("viewPage") != null) {
			ViewPage record = (ViewPage) data.get("viewPage");
			// 图片列表.
			List<ComPicture> list = this.comPictureService
					.getPictureByPageId(record.getPageId());
			List<String> imageList = new ArrayList<String>();
			if (list != null) {
				for (ComPicture comPicture : list) {
					imageList.add(comPicture.getPictureUrl());
				}
			}
			mpr.setImageList(imageList);

			// 产品经理推荐
			for (int i = 0; i < record.getContentList().size(); i++) {
				ViewContent viewContent = record.getContentList().get(i);
				if (viewContent.getContentType().equals(
						Constant.VIEW_CONTENT_TYPE.MANAGERRECOMMEND.name())) {
					// mpr.setManagerRecommend(StringUtil.filterOutHTMLTags(viewContent.getContent()));
					mpr.setManagerRecommend(viewContent.getContent());
				} else if (viewContent.getContentType().equals(
						Constant.VIEW_CONTENT_TYPE.ANNOUNCEMENT.name())) {
					mpr.setAnnouncement(StringUtil
							.filterOutHTMLTags(viewContent.getContent()));
				}
			}
		}

		// 相关产品推荐信息
		ProdCProduct prodCProduct = (ProdCProduct) data.get("prodCProduct");

		if (!prodCProduct.getProdProduct().isOnLine()) {
			throw new NotFoundException("产品不可售");
		}

		ProdRoute route = prodCProduct.getProdRoute(); // 路线
		// 出发地 /目的地
		if (null != route) {
			mpr.setVisitDay(null == route.getDays() ? "" : route.getDays() + ""); // 天数
			Place from = prodCProduct.getFrom(); // 出发地
			if (null != prodCProduct.getProdProduct()
					&& (Constant.SUB_PRODUCT_TYPE.FREENESS.getCode().equals(
							prodCProduct.getProdProduct().getSubProductType())
							|| Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN
									.getCode().equals(
											prodCProduct.getProdProduct()
													.getSubProductType()) || Constant.SUB_PRODUCT_TYPE.FREENESS_LONG
							.getCode().equals(
									prodCProduct.getProdProduct()
											.getSubProductType()))) {
				mpr.setFromDest("各地");
			} else {
				mpr.setFromDest(route.getRouteFrom());
				if (StringUtils.isEmpty(mpr.getFromDest()) && null != from) {
					mpr.setFromDest(from.getName());
				}
			}

			from = prodCProduct.getTo(); // 目的 地
			mpr.setToDest(route.getRouteTo());
			if (StringUtils.isEmpty(mpr.getToDest()) && null != from) {
				mpr.setToDest(from.getName());
			}
		}
		// 产品
		ProdProduct product = prodCProduct.getProdProduct(); // 产品
		if (null != product) {
			mpr.setProductId(product.getProductId());
			mpr.setProductName(product.getProductName());
			mpr.setMarketPrice(product.getMarketPrice());
			mpr.setSellPrice(product.getSellPrice());
			// mpr.setManagerRecommend(product.getManagerRecommend());;
			mpr.setSmallImage(product.getSmallImage());
			// 是否收藏.
			mpr.setHasIn(false); // 默认false

			// V3.1.0
			mpr.setSubProductType(product.getSubProductType());// 主题类型

			if (params.get("userNo") != null) {
				String userId = params.get("userNo").toString();
				if (!StringUtil.isEmptyString(userId)) {
					UserUser user = userUserProxy.getUserUserByUserNo(userId);
					if (user != null) {
						Map<String, Object> p = new HashMap<String, Object>();
						p.put("objectId", product.getProductId());
						p.put("userId", user.getId());
						List<MobileFavorite> queryMobileFavoriteLis = mobileFavoriteService
								.queryMobileFavoriteList(p);
						if (null != queryMobileFavoriteLis
								&& queryMobileFavoriteLis.size() > 0) {
							mpr.setHasIn(true);
						}
					}
				}
			}
		}
		// 点评信息
		CmtTitleStatisticsVO productCommentStatistics = cmtTitleStatistisService
				.getCmtTitleStatisticsByProductId(productId);
		if (null != productCommentStatistics) {
			mpr.setAvgScore(Float.valueOf(productCommentStatistics
					.getAvgScoreStr()));
			mpr.setCmtNum(Integer.valueOf(null == productCommentStatistics
					.getCommentCount() ? "" : productCommentStatistics
					.getCommentCount() + ""));
		}

		// 获得某个产品的点评维度统计平均分
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productId", productId);
		List<CmtLatitudeStatistics> cmtLatitudeStatisticsList = cmtLatitudeStatistisService
				.getLatitudeStatisticsList(parameters);
		List<PlaceCmtScoreVO> pcsVoList = new ArrayList<PlaceCmtScoreVO>();
		for (CmtLatitudeStatistics cmtLatitudeStatistics : cmtLatitudeStatisticsList) {
			PlaceCmtScoreVO pcv = new PlaceCmtScoreVO();
			pcv.setName(cmtLatitudeStatistics.getLatitudeName());
			pcv.setScore(null == cmtLatitudeStatistics.getAvgScore() ? ""
					: cmtLatitudeStatistics.getAvgScore() + "");
			if (cmtLatitudeStatistics.getLatitudeId().equals(
					"FFFFFFFFFFFFFFFFFFFFFFFFFFFF")) {
				pcv.setMain(true);
			}
			pcsVoList.add(pcv);
		}
		mpr.setPlaceCmtScoreList(pcsVoList);
		ProdProductBranch ppb = prodProductService
				.getProductDefaultBranchByProductId(productId);
		mpr.setBranchId(ppb.getProdBranchId());
		/********** v3.1 ******************/
		// 初始化tag标签信息
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("productId", productId);
		List<ProductSearchInfo> productSearchInfoList = productSearchInfoService
				.queryProductSearchInfoByParam(param);
		// 优惠信息
		ClientUtils.initProductSearchInfos(productSearchInfoList, mpr);
		// 返现信息
		mpr.setMaxCashRefund(product.getMaxCashRefund());
		// 是否需要签证 - 只对境外线路有效
		mpr.setVisa(ClientUtils.needVisa(prodCProduct));
		mpr.setProductType(product.getProductType());
		return mpr;
	}

	@Override
	public Map<String, Object> getRouteDetail4Wap(Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		ArgCheckUtils.validataRequiredArgs("productId", params);
		Long productId = Long.valueOf(params.get("productId") + "");
		Map<String, Object> data = new HashMap<String, Object>();
		// 检测产品是否存在.
		ProductResult pr = pageService.findProduct(productId);
		if (!pr.isExists()) {
			throw new Exception("can not find product by productId:"
					+ productId);
		}
		// 获取产品信息.
		data = pageService.getProdCProductInfo(productId, false);

		// 线路信息.
		if (data != null && data.get("viewJourneyList") != null) {
			List<ViewJourney> viewJourneyList = (List<ViewJourney>) data
					.get("viewJourneyList");
			for (ViewJourney vj : viewJourneyList) {
				vj.setJourneyPictureList(comPictureService
						.getPictureByObjectIdAndType(vj.getJourneyId(),
								"VIEW_JOURNEY"));
			}
		}

		// 加载产品图片.
		if (data != null && data.get("viewPage") != null) {
			ViewPage record = (ViewPage) data.get("viewPage");
			record.setPictureList(comPictureService.getPictureByPageId(record
					.getPageId()));
			data.put("comPictureList", record.getPictureList());
		}

		// 相关产品推荐信息
		ProdCProduct prodCProduct = (ProdCProduct) data.get("prodCProduct");
		if (prodCProduct != null && prodCProduct.getTo() != null) {
			// 相关产品推荐
			if (!"1".equals(prodCProduct.getTo().getStage())
					&& prodCProduct.getTo().getParentPlaceId() != null) {
				ProductList productList = recommendInfoClient
						.getProductByPlaceIdAnd4Type(prodCProduct.getTo()
								.getParentPlaceId(), "1", 6,
								Constant.CHANNEL.CLIENT.name());
				data.put("guestProductList", productList);
			}
		}

		// 获得产品点评相关信息
		List<CommonCmtCommentVO> productComments = cmtCommentService
				.getNewestCommentByProductID(productId, 5);
		productComments = cmtCommentService
				.composeUserImagOfComment(productComments);

		// 点评信息
		CmtTitleStatisticsVO productCommentStatistics = cmtTitleStatistisService
				.getCmtTitleStatisticsByProductId(productId);

		// 点评维度信息
		Map<String, Object> params2 = new HashMap<String, Object>();
		params2.put("productId", productId);
		List<CmtLatitudeStatistics> productCmtLatitudeStatisticsVOList = cmtLatitudeStatistisService
				.getFourAvgLatitudeScoreList(params2);

		// 获取产品目的地景区点评
		if (prodCProduct != null && prodCProduct.getTo() != null) {
			Place comPlace = prodCProduct.getTo();
			if (null != comPlace) {
				List<CommonCmtCommentVO> comments = cmtCommentService
						.getNewestCommentByPlaceId(comPlace.getPlaceId(), 6);
				CmtTitleStatisticsVO commentStatistics = cmtTitleStatistisService
						.getCmtTitleStatisticsByPlaceId(comPlace.getPlaceId());
			}
		}

		return data;
	}

	/**
	 * 获取当前用户最近一次的联系人列表.
	 * 
	 * @param userNo
	 * @return
	 */
	public List<MobilePersonItem> getLatestPersonList(String userNo,
			String productType) {
		List<MobilePersonItem> vopList = new ArrayList<MobilePersonItem>();
		if (StringUtils.isEmpty(userNo)) {
			return vopList;
		}
		// 综合查询
		CompositeQuery compositeQuery = new CompositeQuery();
		// id相关
		OrderIdentity orderIdentity = new OrderIdentity();
		orderIdentity.setUserId(userNo);
		compositeQuery.setOrderIdentity(orderIdentity);
		// 分页相关
		PageIndex pageIndex = new PageIndex();
		pageIndex.setBeginIndex(0);
		pageIndex.setEndIndex(1);
		compositeQuery.setPageIndex(pageIndex);

		// 订单类别相关
		OrderContent content = new OrderContent();

		compositeQuery.setContent(content);

		/**
		 * 联系人取最近一次的订单
		 */
		content.setProductType(null);
		List<OrdOrder> ordersList = orderServiceProxy
				.compositeQueryOrdOrder(compositeQuery);
		OrdPerson contactPerson = null;
		MobilePersonItem contactMobilePerson = null;
		if (ordersList != null && ordersList.size() > 0) {
			OrdOrder ord = ordersList.get(0);
			for (OrdPerson op : ord.getPersonList()) {
				if (Constant.ORD_PERSON_TYPE.CONTACT.name().equals(
						op.getPersonType())) {
					contactPerson = op;
					contactMobilePerson = new MobilePersonItem();
					this.copyPersonToMobileItem(contactMobilePerson,
							contactPerson);
				}
			}

		}
		content.setProductType(productType);
		compositeQuery.setContent(content);
		// 订单列表
		ordersList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
		if (null != ordersList && ordersList.size() > 0) {
			OrdOrder ord = ordersList.get(0);
			if (null != ord) {
				// 人员信息
				for (OrdPerson op : ord.getPersonList()) {
					MobilePersonItem vop = new MobilePersonItem();
					this.copyPersonToMobileItem(vop, op);
					if (Constant.ORD_PERSON_TYPE.CONTACT.name().equals(
							op.getPersonType())) {
						if (contactMobilePerson != null) {
							vopList.add(contactMobilePerson);
						} else {
							vopList.add(vop);
						}
					} else {
						vopList.add(vop);
					}
				}
			}
		}

		// 如果没有预订过 ,则获取当前登录者信息
		if (ordersList.size() < 1) {
			UserUser user = userUserProxy.getUserUserByUserNo(userNo);
			if (null != user) {
				MobilePersonItem vop = new MobilePersonItem();
				vop.setPersonMobile(user.getMobileNumber());
				vop.setPersonType(Constant.ORD_PERSON_TYPE.CONTACT.getCode());
				vopList.add(vop);
			}
		}

		return vopList;
	}

	private void copyPersonToMobileItem(MobilePersonItem vop, OrdPerson op) {
		if (!StringUtil.isEmptyString(op.getCertNo())
				&& op.getCertType() != null
				&& op.getCertType().equals(Constant.CERT_TYPE.ID_CARD.name())) {
			if (op.getCertNo() != null) {
				// v3.1 放开身份证
				/*
				 * if (op.getCertNo().length() == 18) {
				 * vop.setCertNo("**************" +
				 * op.getCertNo().substring(op.getCertNo().length() -
				 * 4,op.getCertNo().length())); } else if
				 * (op.getCertNo().length() == 15) { vop.setCertNo("***********"
				 * + op.getCertNo().substring(op.getCertNo().length() -
				 * 4,op.getCertNo().length())); }
				 */
				vop.setCertNo(op.getCertNo());
			}

		}
		vop.setPersonMobile(op.getMobile());
		vop.setPersonName(op.getName());
		vop.setPersonType(op.getPersonType());
		vop.setEmail(op.getEmail());
		if (op.getBrithday() != null) {
			vop.setBirthday(DateUtil.formatDate(op.getBrithday(), "yyyy-MM-dd"));
		}
		vop.setGender(op.getGender());
		vop.setCertType(op.getCertType());
	}

	public void setMobileFavoriteService(
			MobileFavoriteService mobileFavoriteService) {
		this.mobileFavoriteService = mobileFavoriteService;
	}

	@Override
	public List<ViewJourney> getViewJourneyList(Map<String, Object> param)
			throws Exception {
		List<ViewJourney> viewJourneyList = new ArrayList<ViewJourney>();
		try {
			ArgCheckUtils.validataRequiredArgs("productId", param);
			Map<String, Object> data = pageService.getProdCProductInfo(
					Long.valueOf(param.get("productId").toString()), false);
			// 行程说明
			if (null != data && data.get("viewJourneyList") != null) {
				viewJourneyList = (List<ViewJourney>) data
						.get("viewJourneyList");
				for (ViewJourney vj : viewJourneyList) {
					vj.setPlaceList(new ArrayList());// 清空placeList
					vj.setJourneyPictureList(comPictureService
							.getPictureByObjectIdAndType(vj.getJourneyId(),
									"VIEW_JOURNEY"));
					if (!StringUtils.isEmpty(vj.getContent())) {
						vj.setContent(ClientUtils.filterOutHTMLTags(vj
								.getContent()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("...getViewJourneyList error...");
		}

		return viewJourneyList;
	}

	public Map<String, Object> checkStock(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("productId", "udid", param);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long productId = Long.valueOf(param.get("productId").toString());
		ProdProductBranch ppb = prodProductService
				.getProductDefaultBranchByProductId(productId);
		List<TimePrice> timePriceList = null;
		boolean sellAble = false;
		timePriceList = prodProductService.getMainProdTimePrice(productId,
				ppb.getProdBranchId());
		if (timePriceList != null && !timePriceList.isEmpty()) {
			TimePrice tp = timePriceList.get(0);
			sellAble = tp.isSellable(1);
		}
		resultMap.put("sellAble", sellAble);
		return resultMap;
	}

	public void setMarkCouponPointChangeService(
			MarkCouponPointChangeService markCouponPointChangeService) {
		this.markCouponPointChangeService = markCouponPointChangeService;
	}

	@Override
	public List<VisaVO> getVisaList(Map<String, Object> params) {
		ArgCheckUtils.validataRequiredArgs("productId", params);
		ProdCProduct prodCProduct = pageService.getProdCProduct(Long
				.valueOf(params.get("productId").toString()));
		List<VisaVO> visaVOList = new ArrayList<VisaVO>();
		try {
			// 签证内容

			if (prodCProduct != null
					&& null != prodCProduct.getProdRoute()
					&& StringUtils.isNotBlank(prodCProduct.getProdRoute()
							.getCountry())
					&& StringUtils.isNotBlank(prodCProduct.getProdRoute()
							.getCity())
					&& StringUtils.isNotBlank(prodCProduct.getProdRoute()
							.getVisaType())) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("country", prodCProduct.getProdRoute().getCountry());
				param.put("visaType", prodCProduct.getProdRoute().getVisaType());
				param.put("city", prodCProduct.getProdRoute().getCity());
				// 文档
				List<VisaApplicationDocument> visaApplicationDocumentList = visaApplicationDocumentService
						.query(param);
				for (VisaApplicationDocument v : visaApplicationDocumentList) {
					List<VisaApplicationDocumentDetails> vList = new ArrayList<VisaApplicationDocumentDetails>();
					VisaVO visaVo = new VisaVO();
					// 文档详情
					vList = visaApplicationDocumentService
							.queryDetailsByDocumentId(v.getDocumentId());
					visaVo.setDocumentId(v.getDocumentId());
					visaVo.setOccupation(v.getOccupation());
					// 过滤html标签
					if (!vList.isEmpty()) {
						for (VisaApplicationDocumentDetails vad : vList) {
							if (null != vad) {
								vad.setContent(ClientUtils
										.filterOutHTMLTags(vad.getContent()));
							}
						}
					}
					visaVo.setVisaApplicationDocumentDetailsList(vList);
					visaVOList.add(visaVo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return visaVOList;
	}

	public void setVisaApplicationDocumentService(
			VisaApplicationDocumentService visaApplicationDocumentService) {
		this.visaApplicationDocumentService = visaApplicationDocumentService;
	}

	@Override
	public List<MobileTrainSeatType> getTrainSeatTypes(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}
}
