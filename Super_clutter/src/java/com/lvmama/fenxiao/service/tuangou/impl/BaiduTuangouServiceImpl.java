package com.lvmama.fenxiao.service.tuangou.impl;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.clutter.utils.DistributionParseUtil;
import com.lvmama.comm.bee.po.distribution.DistributionBaiduTuangou;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.GroupDreamService;
import com.lvmama.comm.bee.service.distribution.DistributionService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.view.ViewPageService;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdProductChannel;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.service.place.PlaceCoordinateBaiduService;
import com.lvmama.comm.pet.service.place.PlacePlaceDestService;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.pet.vo.PlaceCoordinateVo;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.fenxiao.model.tuangou.BaiduAccessToken;
import com.lvmama.fenxiao.model.tuangou.BaiduOrder;
import com.lvmama.fenxiao.model.tuangou.BaiduUser;
import com.lvmama.fenxiao.service.tuangou.BaiduTuangouService;

import freemarker.template.TemplateException;

public class BaiduTuangouServiceImpl implements BaiduTuangouService {
	private static final Log log = LogFactory.getLog(BaiduTuangouServiceImpl.class);

	private static final int CACHE_SECONDS_LONG = 60 * 60 * 24 * 30;// 长缓存30天
	private static final String CACHE_KEY_AUTHORIZATION_CODE_LONG = "cache_key_authorization_code_long";
	private static final String CACHE_KEY_CLIENT_CREDENTIALS_LONG = "cache_key_client_credentials_long";

	private static final int CACHE_SECONDS_SHORT = 60 * 60 * 24 * 5;// 短缓存5天
	private static final String CACHE_KEY_AUTHORIZATION_CODE_SHORT = "cache_key_authorization_code_short";
	private static final String CACHE_KEY_CLIENT_CREDENTIALS_SHORT = "cache_key_client_credentials_short";

	private enum AccessTokenType {
		AUTHORIZATION_CODE, CLIENT_CREDENTIALS;
	}

	private static final String PRODUCT_URL_PREFIX = "http://www.lvmama.com/product/";
	private static final String IMAGE_URL_PREFIX = "http://pic.lvmama.com/290x190/pics/";

	private static final String SUBCATEGORY_ROUTE = "旅游";
	private static final String SUBCATEGORY_HOTEL = "酒店";
	private static final String SUBCATEGORY_TICKET = "景点公园";

	private GroupDreamService groupDreamService;
	private ProdProductService prodProductService;
	private ViewPageService viewPageService;
	private ComPictureService comPictureService;
	private ProdProductPlaceService prodProductPlaceService;
	private PlaceCoordinateBaiduService placeCoordinateBaiduService;
	private PlacePlaceDestService placePlaceDestService;
	private DistributionService distributionService;
	private OrderService orderServiceProxy;

	/**
	 * 通过Authorization Code获取的Access Token使用双缓存保存起来
	 */
	@Override
	public BaiduAccessToken getAccessTokenByAuthorizationCode(String authorizationCode) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", "authorization_code");
		params.put("code", authorizationCode);
		params.put("client_id", DistributionParseUtil.getPropertiesByKey("tuangou.baidu.apiKey"));
		params.put("client_secret", DistributionParseUtil.getPropertiesByKey("tuangou.baidu.secretKey"));
		params.put("redirect_uri", DistributionParseUtil.getPropertiesByKey("tuangou.baidu.authorizationCode.callbackUrl"));
		String json = HttpsUtil.requestPostForm(DistributionParseUtil.getPropertiesByKey("tuangou.baidu.accessToken.url"), params);
		log.info("getAccessTokenByAuthorizationCode accessToken=" + json);
		JSONObject obj = JSONObject.fromObject(json);
		BaiduAccessToken accessToken = (BaiduAccessToken) JSONObject.toBean(obj, BaiduAccessToken.class);
		if (StringUtils.isBlank(accessToken.getError())) {
			saveAccessTokenToDoubleCache(accessToken, AccessTokenType.AUTHORIZATION_CODE);
		}
		return accessToken;
	}

	/**
	 * 保存到百度团购产品信息表
	 */
	@Override
	public void saveBaiduTuangouProduct(Long productId) {
		ViewPage viewPage = viewPageService.getViewPageByProductId(productId);
		ProdProduct prodProduct = prodProductService.getProdProductById(productId);
		Place toDest = prodProductPlaceService.getToDestByProductId(prodProduct.getProductId());
		DistributionBaiduTuangou tuangouProduct = null;
		if (toDest != null && viewPage != null) {
			tuangouProduct = buildProduct(prodProduct, toDest, viewPage);
			if (prodProduct.isHotel()) {
				tuangouProduct = buildHotelProduct(tuangouProduct, prodProduct, toDest);
			} else if (prodProduct.isTicket()) {
				tuangouProduct = buildTicketProduct(tuangouProduct, prodProduct, toDest);
			} else if (prodProduct.isRoute()) {
				tuangouProduct = buildRouteProduct(tuangouProduct, prodProduct, toDest);
			}
			log.info("tuangouProduct: " + tuangouProduct.toString());
		}
		if (tuangouProduct != null && isPassedNonNullCheck(tuangouProduct)) {
			distributionService.deleteBaiduTuangouProductByProductId(productId);
			distributionService.insertBaiduTuangouProduct(tuangouProduct);
		}
	}

	/**
	 * 以XML格式输出百度团购产品信息
	 */
	@Override
	public void outputBaiduTuangouXML(Writer out) throws IOException, TemplateException {
		String templateDir = "/com/lvmama/fenxiao/template/tuangou";
		String templateFilename = "distributionBaiduTuangou.xml";
		Long totalCount = distributionService.getDistributionBaiduTuangouTotalCount();
		Long pageSize = 1000L;
		Long pageNum = totalCount % pageSize > 0 ? (totalCount / pageSize) + 1 : totalCount / pageSize;
		out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		out.write("<urlset>");
		for (int pageNo = 0; pageNo < pageNum; pageNo++) {
			Long startRow = pageNo * pageSize + 1;
			Long endRow = (pageNo + 1) * pageSize;
			List<DistributionBaiduTuangou> baiduTuangouProducts = distributionService.selectBaiduTuangouProducts(startRow, endRow);
			for (DistributionBaiduTuangou tuangouProduct : baiduTuangouProducts) {
				String baiduTuangouXML = TemplateUtils.fillFileTemplate(templateDir, templateFilename, tuangouProduct);
				out.write(baiduTuangouXML);
				out.flush();
			}
		}
		out.write("</urlset>");
	}

	/**
	 * 填充百度订单对象
	 */
	private BaiduOrder fillBaiduOrder(Long orderId, String tn, String baiduid) {
		BaiduUser baiduUser = this.getLoggedInUser();
		if (baiduUser == null) {
			return null;
		}
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		OrdOrderItemProd prod = this.getDefaultOrderItemProduct(ordOrder);
		if (prod == null) {
			return null;
		}
		DistributionBaiduTuangou tuangouProduct = distributionService.selectBaiduTuangouProductByProductId(prod.getProductId());
		if (tuangouProduct == null) {
			return null;
		}
		String totalPrice = String.valueOf(prod.getPrice() * prod.getQuantity());
		String goodsNum = String.valueOf(prod.getQuantity());
		String price = String.valueOf(prod.getPrice());
		String createTime = String.valueOf(ordOrder.getCreateTime().getTime());
		BaiduOrder baiduOrder = new BaiduOrder();
		baiduOrder.setOrder_id(orderId.toString());
		baiduOrder.setOrder_time(createTime);
		baiduOrder.setOrder_city(tuangouProduct.getCity());
		baiduOrder.setTitle(tuangouProduct.getTitle());
		baiduOrder.setLogo(tuangouProduct.getImage());
		baiduOrder.setUrl(tuangouProduct.getLoc());
		baiduOrder.setPrice(price);
		baiduOrder.setGoods_num(goodsNum);
		baiduOrder.setSum_price(totalPrice);
		baiduOrder.setSummary(tuangouProduct.getTips());
		baiduOrder.setExpire("0");
		baiduOrder.setAddr(tuangouProduct.getAddress());
		baiduOrder.setUid(baiduUser.getUid());
		baiduOrder.setMobile(ordOrder.getMobileNumber());
		baiduOrder.setTn(tn);
		baiduOrder.setBaiduid(baiduid);
		baiduOrder.setBonus("0");
		return baiduOrder;
	}

	/**
	 * 获得订单默认产品
	 */
	private OrdOrderItemProd getDefaultOrderItemProduct(OrdOrder ordOrder) {
		List<OrdOrderItemProd> ordOrderItemProds = ordOrder.getOrdOrderItemProds();
		for (OrdOrderItemProd prod : ordOrderItemProds) {
			if (prod.hasDefault()) {
				return prod;
			}
		}
		return null;
	}

	/**
	 * 团购订单信息提交（在付款成功后调用）
	 */
	@Override
	public void saveOrder(Long orderId, String tn, String baiduid) {
		BaiduOrder baiduOrder = this.fillBaiduOrder(orderId, tn, baiduid);
		log.info(baiduOrder);
		if (baiduOrder != null) {
			BaiduAccessToken accessToken = this.getAccessTokenByClientCredentials();
			Map<String, String> params = new HashMap<String, String>();
			params.put("session_key", accessToken.getSession_key());
			params.put("timestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
			params.put("order_id", baiduOrder.getOrder_id());
			params.put("order_time", baiduOrder.getOrder_time());
			params.put("order_city", baiduOrder.getOrder_city());
			params.put("title", baiduOrder.getTitle());
			params.put("logo", baiduOrder.getLogo());
			params.put("url", baiduOrder.getUrl());
			params.put("price", baiduOrder.getPrice());
			params.put("goods_num", baiduOrder.getGoods_num());
			params.put("sum_price", baiduOrder.getSum_price());
			params.put("summary", baiduOrder.getSummary());
			params.put("expire", baiduOrder.getExpire());
			params.put("addr", baiduOrder.getAddr());
			params.put("uid", baiduOrder.getUid());
			params.put("mobile", baiduOrder.getMobile());
			params.put("tn", baiduOrder.getTn());
			params.put("baiduid", baiduOrder.getBaiduid());
			params.put("bonus", baiduOrder.getBonus());
			params.put("sign", getSignature(params, accessToken.getSession_secret()));
			String response = HttpsUtil.requestPostForm(DistributionParseUtil.getPropertiesByKey("tuangou.baidu.saveOrder.url"), params);
			log.info("saveOrder orderId=" + orderId + ", response: " + response);
		}
	}

	/**
	 * 查询所有团购产品
	 */
	@Override
	public List<Long> selectAllBaiduTuangouProductIds(Long startRow, Long endRow) {
		return prodProductService.getAllGroupProdIds(startRow, endRow);
	}

	/**
	 * 查询团购产品数量
	 */
	@Override
	public Long getGroupProdIdCount() {
		return prodProductService.getGroupProdIdCount();
	}

	/**
	 * 删除所有团购产品
	 */
	@Override
	public void deleteAllBaiduTuangouProducts() {
		distributionService.deleteAllBaiduTuangouProducts();
	}

	/**
	 * 判断产品是否属于团购类产品
	 */
	@Override
	public boolean isTuangouProduct(Long productId) {
		List<ProdProductChannel> channelList = prodProductService.selectChannelByProductId(productId);
		for (ProdProductChannel channel : channelList) {
			if (Constant.CHANNEL.TUANGOU.name().equals(channel.getProductChannel())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 使用应用公钥、密钥获取Access Token
	 */
	private BaiduAccessToken getAccessTokenByClientCredentials() {
		BaiduAccessToken accessToken = this.getAccessTokenFromDoubleCache(AccessTokenType.CLIENT_CREDENTIALS);
		if (accessToken == null) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("grant_type", "client_credentials");
			params.put("client_id", DistributionParseUtil.getPropertiesByKey("tuangou.baidu.apiKey"));
			params.put("client_secret", DistributionParseUtil.getPropertiesByKey("tuangou.baidu.secretKey"));
			String json = HttpsUtil.requestPostForm(DistributionParseUtil.getPropertiesByKey("tuangou.baidu.accessToken.url"), params);
			log.info("getAccessTokenByClientCredentials accessToken=" + json);
			JSONObject obj = JSONObject.fromObject(json);
			accessToken = (BaiduAccessToken) JSONObject.toBean(obj, BaiduAccessToken.class);
		}
		if (StringUtils.isBlank(accessToken.getError())) {
			saveAccessTokenToDoubleCache(accessToken, AccessTokenType.CLIENT_CREDENTIALS);
		}
		return accessToken;
	}

	/**
	 * 使用Refresh Token获取Access Token
	 */
	private BaiduAccessToken getAccessTokenByRefreshToken(AccessTokenType accessTokenMode) {
		BaiduAccessToken newAccessToken = null;
		BaiduAccessToken oldAccessToken = this.getAccessTokenFromDoubleCache(accessTokenMode);
		log.info("getAccessTokenByRefreshToken oldAccessToken=" + oldAccessToken);
		if (oldAccessToken != null) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("grant_type", "refresh_token");
			params.put("refresh_token", oldAccessToken.getRefresh_token());
			params.put("client_id", DistributionParseUtil.getPropertiesByKey("tuangou.baidu.apiKey"));
			params.put("client_secret", DistributionParseUtil.getPropertiesByKey("tuangou.baidu.secretKey"));
			String json = HttpsUtil.requestPostForm(DistributionParseUtil.getPropertiesByKey("tuangou.baidu.accessToken.url"), params);
			log.info("getAccessTokenByRefreshToken newAccessToken=" + json);
			JSONObject obj = JSONObject.fromObject(json);
			newAccessToken = (BaiduAccessToken) JSONObject.toBean(obj, BaiduAccessToken.class);
		}
		if (StringUtils.isBlank(newAccessToken.getError())) {
			saveAccessTokenToDoubleCache(newAccessToken, accessTokenMode);
		}
		return newAccessToken;
	}

	/**
	 * 获取当前登录用户的用户uid、用户名和头像
	 */
	private BaiduUser getLoggedInUser() {
		BaiduAccessToken accessToken = this.getAccessTokenFromDoubleCache(AccessTokenType.AUTHORIZATION_CODE);
		if (accessToken != null) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("session_key", accessToken.getSession_key());
			params.put("timestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
			params.put("sign", getSignature(params, accessToken.getSession_secret()));
			String json = HttpsUtil.requestPostForm(DistributionParseUtil.getPropertiesByKey("tuangou.baidu.getLoggedInUser.url"), params);
			log.info("getLoggedInUser baiduUser=" + json);
			JSONObject obj = JSONObject.fromObject(json);
			BaiduUser baiduUser = (BaiduUser) JSONObject.toBean(obj, BaiduUser.class);
			return baiduUser;
		} else {
			log.error("Please visit index.jsp and click getAccessTokenByAuthorizationCode link");
		}
		return null;
	}

	/**
	 * Access Token放入双缓存
	 */
	private void saveAccessTokenToDoubleCache(BaiduAccessToken accessToken, AccessTokenType accessTokenMode) {
		switch (accessTokenMode) {
		case AUTHORIZATION_CODE:
			MemcachedUtil.getInstance().set(CACHE_KEY_AUTHORIZATION_CODE_LONG, CACHE_SECONDS_LONG, accessToken);
			MemcachedUtil.getInstance().set(CACHE_KEY_AUTHORIZATION_CODE_SHORT, CACHE_SECONDS_SHORT, accessToken);
			break;
		case CLIENT_CREDENTIALS:
			MemcachedUtil.getInstance().set(CACHE_KEY_CLIENT_CREDENTIALS_LONG, CACHE_SECONDS_LONG, accessToken);
			MemcachedUtil.getInstance().set(CACHE_KEY_CLIENT_CREDENTIALS_SHORT, CACHE_SECONDS_SHORT, accessToken);
			break;
		}
	}

	/**
	 * 从双缓存里取出Access Token
	 */
	private BaiduAccessToken getAccessTokenFromDoubleCache(AccessTokenType accessTokenMode) {
		BaiduAccessToken accessToken = null;
		switch (accessTokenMode) {
		case AUTHORIZATION_CODE:
			accessToken = (BaiduAccessToken) MemcachedUtil.getInstance().get(CACHE_KEY_AUTHORIZATION_CODE_SHORT);
			if (accessToken == null) {
				accessToken = (BaiduAccessToken) MemcachedUtil.getInstance().get(CACHE_KEY_AUTHORIZATION_CODE_LONG);
				if (accessToken != null) {
					accessToken = getAccessTokenByRefreshToken(AccessTokenType.AUTHORIZATION_CODE);
				}
			}
			break;
		case CLIENT_CREDENTIALS:
			accessToken = (BaiduAccessToken) MemcachedUtil.getInstance().get(CACHE_KEY_CLIENT_CREDENTIALS_SHORT);
			if (accessToken == null) {
				accessToken = (BaiduAccessToken) MemcachedUtil.getInstance().get(CACHE_KEY_CLIENT_CREDENTIALS_LONG);
				if (accessToken != null) {
					accessToken = getAccessTokenByRefreshToken(AccessTokenType.CLIENT_CREDENTIALS);
				}
			}
			break;
		}
		return accessToken;
	}

	/**
	 * 从双缓存里清除Authorization Code类型的Access Token
	 */
	@Override
	public void removeAuthorizationCodeAccessToken() {
		MemcachedUtil.getInstance().remove(CACHE_KEY_AUTHORIZATION_CODE_SHORT);
		MemcachedUtil.getInstance().remove(CACHE_KEY_AUTHORIZATION_CODE_LONG);
	}

	/**
	 * 从双缓存里清除Client Credentials类型的Access Token
	 */
	@Override
	public void removeClientCredentialsAccessToken() {
		MemcachedUtil.getInstance().remove(CACHE_KEY_CLIENT_CREDENTIALS_SHORT);
		MemcachedUtil.getInstance().remove(CACHE_KEY_CLIENT_CREDENTIALS_LONG);
	}

	/**
	 * 签名生成算法
	 */
	private String getSignature(Map<String, String> params, String secret) {
		// 先将参数以其参数名的字典序升序进行排序
		Map<String, String> sortedParams = new TreeMap<String, String>(params);
		Set<Entry<String, String>> entrys = sortedParams.entrySet();

		// 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
		StringBuilder basestring = new StringBuilder();
		for (Entry<String, String> param : entrys) {
			basestring.append(param.getKey()).append("=").append(param.getValue());
		}
		basestring.append(secret);

		// 使用MD5对待签名串求签
		byte[] bytes = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
		} catch (Exception ex) {
			log.error(ex);
		}

		// 将MD5输出的二进制结果转换为小写的十六进制
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex);
		}
		return sign.toString();
	}

	private DistributionBaiduTuangou buildProduct(ProdProduct prodProduct, Place toDest, ViewPage viewPage) {
		String loc = PRODUCT_URL_PREFIX + prodProduct.getProductId();
		String productName = prodProduct.getProductName();
		String shortTitle = productName;
		if (productName.length() > 15) {
			shortTitle = productName.substring(0, 15);
		}
		String shopSeller = productName;
		if (productName.length() > 50) {
			shopSeller = productName.substring(0, 50);
		}
		PlaceCoordinateVo placeCoordinateVo = getBaiduMapByPlaceId(toDest.getPlaceId());
		String shopAdress = placeCoordinateVo.getPlaceAddress();
		if (shopAdress != null && shopAdress.length() > 50) {
			shopAdress = shopAdress.substring(0, 50);
		}
		Double latitude = placeCoordinateVo.getLatitude();// 纬度
		Double longitude = placeCoordinateVo.getLongitude(); // 经度
		Map<String, Object> viewPageMap = viewPage.getContents();
		ViewContent viewContent = (ViewContent) viewPageMap.get(Constant.VIEW_CONTENT_TYPE.MANAGERRECOMMEND.name());
		String tips = viewContent.getContent();
		if (tips != null) {
			tips = replaceHtml(tips);
			if (tips.length() > 512) {
				tips = tips.substring(0, 512);
			}
		}
		String startTime = String.valueOf(prodProduct.getOnlineTime().getTime() / 1000);
		String endTime = String.valueOf(prodProduct.getOfflineTime().getTime() / 1000);
		String value = String.valueOf(prodProduct.getMarketPriceYuan());
		String price = String.valueOf(prodProduct.getSellPriceYuan());
		Long orderCount = groupDreamService.countOrderByProduct(prodProduct.getProductId());// 获取产团购产品已购买人数
		String bought = String.valueOf(orderCount);
		String spendEndTime = endTime;// 团购券商品消费结束时间
		String coords = null;
		if (longitude != null && latitude != null) {
			coords = latitude + "," + longitude;
		}
		String image = null;
		List<ComPicture> comPicList = comPictureService.getPictureByPageId(viewPage.getPageId());
		if (comPicList != null && !comPicList.isEmpty()) {
			image = IMAGE_URL_PREFIX + comPicList.get(0).getPictureUrl();
		}
		String rebate = null;
		if (prodProduct.getMarketPriceYuan() > 0) {
			double discount = new BigDecimal(prodProduct.getSellPriceYuan() / prodProduct.getMarketPriceYuan() * 10).setScale(1, BigDecimal.ROUND_FLOOR).doubleValue();
			rebate = String.valueOf(discount);
		}
		DistributionBaiduTuangou tuangouProduct = new DistributionBaiduTuangou();
		tuangouProduct.setLoc(loc);
		tuangouProduct.setWaploc(loc);
		tuangouProduct.setWebsite("驴妈妈团购");
		tuangouProduct.setSiteurl("http://www.lvmama.com/tuangou");
		tuangouProduct.setCity("");
		tuangouProduct.setCategory("5"); // 一级分类 5代表“旅游住宿"
		tuangouProduct.setSubcategory("");
		tuangouProduct.setThrcategory(""); // 三级分类
		tuangouProduct.setCharacteristic("");
		tuangouProduct.setDestination("");
		tuangouProduct.setMajor("0");// 是否主打？
		tuangouProduct.setTitle(productName);
		tuangouProduct.setShorttitle(shortTitle);
		tuangouProduct.setImage(image);
		tuangouProduct.setStarttime(startTime);
		tuangouProduct.setEndtime(endTime);
		tuangouProduct.setValue(value);
		tuangouProduct.setPrice(price);
		tuangouProduct.setRebate(rebate);// 折扣
		tuangouProduct.setBought(bought);
		tuangouProduct.setName(productName);
		tuangouProduct.setSpendendtime(spendEndTime);
		tuangouProduct.setReservation("1");// 是否需要预约
		tuangouProduct.setTips(tips);
		tuangouProduct.setSeller(productName);
		tuangouProduct.setPhone("1010-6060");
		tuangouProduct.setAddress(shopAdress);
		tuangouProduct.setCoords(coords);
		tuangouProduct.setRange("");
		tuangouProduct.setDpshopid("");// 大众点评Id 无
		tuangouProduct.setShopseller(shopSeller);
		tuangouProduct.setShopphone("1010-6060");
		tuangouProduct.setShopaddress(shopAdress);
		tuangouProduct.setShopcoords(coords);
		tuangouProduct.setShoprange("");
		tuangouProduct.setShopdpshopid("");// 大众点评Id 无
		tuangouProduct.setOpentime("0:00–24:00"); // 营业时间
		tuangouProduct.setTrafficinfo("暂无");// 公交信息
		tuangouProduct.setProductId(prodProduct.getProductId());
		return tuangouProduct;
	}

	/**
	 * 构造线路产品
	 */
	private DistributionBaiduTuangou buildRouteProduct(DistributionBaiduTuangou tuangouProduct, ProdProduct prodProduct, Place toDest) {
		String city = null;
		Place fromDest = prodProductPlaceService.getFromDestByProductId(prodProduct.getProductId());
		if (fromDest == null) {
			city = "全国";
		} else {
			city = getBaiduCityName(fromDest.getPlaceId());
		}
		String destination = getBaiduCityName(toDest.getPlaceId());
		if (prodProduct.isForeign()) {
			destination = "境外";
		}
		tuangouProduct.setCity(city);
		tuangouProduct.setDestination(destination);
		tuangouProduct.setSubcategory(SUBCATEGORY_ROUTE);
		return tuangouProduct;
	}

	/**
	 * 构造酒店产品
	 */
	private DistributionBaiduTuangou buildHotelProduct(DistributionBaiduTuangou tuangouProduct, ProdProduct prodProduct, Place toDest) {
		String city = getBaiduCityName(toDest.getPlaceId());
		if (prodProduct.isForeign()) {
			Place fromDest = prodProductPlaceService.getFromDestByProductId(prodProduct.getProductId());
			city = getBaiduCityName(fromDest.getPlaceId());
		}
		tuangouProduct.setCity(city);
		tuangouProduct.setSubcategory(SUBCATEGORY_HOTEL);
		return tuangouProduct;
	}

	/**
	 * 构造门票产品
	 */
	private DistributionBaiduTuangou buildTicketProduct(DistributionBaiduTuangou tuangouProduct, ProdProduct prodProduct, Place toDest) {
		String city = getBaiduCityName(toDest.getPlaceId());
		if (prodProduct.isForeign()) {
			Place fromDest = prodProductPlaceService.getFromDestByProductId(prodProduct.getProductId());
			city = getBaiduCityName(fromDest.getPlaceId());
		}
		tuangouProduct.setCity(city);
		tuangouProduct.setSubcategory(SUBCATEGORY_TICKET);
		return tuangouProduct;
	}

	private String getBaiduCityName(Long placeId) {
		List<Long> parentPlaceList = placePlaceDestService.selectParentPlaceIdList(placeId);
		if (!parentPlaceList.isEmpty()) {
			List<String> cityList = distributionService.getBaiduCityNameByPlaceIds(parentPlaceList);
			if (!cityList.isEmpty()) {
				return cityList.get(0);
			}
		}
		return null;
	}

	private PlaceCoordinateVo getBaiduMapByPlaceId(Long placeId) {
		PlaceCoordinateVo placeCoordinateVo = new PlaceCoordinateVo();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("placeId", placeId);
		params.put("_startRow", 0);
		params.put("_endRow", 1);
		List<PlaceCoordinateVo> placeCoordinateVoList = placeCoordinateBaiduService.getBaiduMapListByParams(params);
		if (placeCoordinateVoList.size() > 0) {
			placeCoordinateVo = placeCoordinateVoList.get(0);
		}
		return placeCoordinateVo;
	}

	private String replaceHtml(String tips) {
		Pattern patternStr;
		Matcher matcherStr;
		try {
			String regEx_Str = "<[^>]+>"; // 定义HTML标签的正则表达式
			patternStr = Pattern.compile(regEx_Str, Pattern.CASE_INSENSITIVE);
			matcherStr = patternStr.matcher(tips);
			tips = matcherStr.replaceAll(""); // 过滤html标签
		} catch (Exception e) {
			log.error("过滤html标签出错 ", e);
		}
		Pattern p_html;
		Matcher m_html;
		String regEx_html = "<[^>]+>";
		p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		m_html = p_html.matcher(tips);
		tips = m_html.replaceAll("");
		return tips;
	}

	/**
	 * 非空检查
	 */
	private boolean isPassedNonNullCheck(DistributionBaiduTuangou tuangouProduct) {
		boolean flag = StringUtils.isNotBlank(tuangouProduct.getLoc()) && StringUtils.isNotBlank(tuangouProduct.getWaploc()) && StringUtils.isNotBlank(tuangouProduct.getWebsite()) && StringUtils.isNotBlank(tuangouProduct.getCity())
				&& StringUtils.isNotBlank(tuangouProduct.getCategory()) && StringUtils.isNotBlank(tuangouProduct.getSubcategory()) && StringUtils.isNotBlank(tuangouProduct.getMajor()) && StringUtils.isNotBlank(tuangouProduct.getTitle())
				&& StringUtils.isNotBlank(tuangouProduct.getShorttitle()) && StringUtils.isNotBlank(tuangouProduct.getImage()) && StringUtils.isNotBlank(tuangouProduct.getStarttime()) && StringUtils.isNotBlank(tuangouProduct.getEndtime())
				&& StringUtils.isNotBlank(tuangouProduct.getValue()) && StringUtils.isNotBlank(tuangouProduct.getPrice()) && StringUtils.isNotBlank(tuangouProduct.getRebate()) && StringUtils.isNotBlank(tuangouProduct.getBought())
				&& StringUtils.isNotBlank(tuangouProduct.getName()) && StringUtils.isNotBlank(tuangouProduct.getSpendendtime()) && StringUtils.isNotBlank(tuangouProduct.getReservation()) && StringUtils.isNotBlank(tuangouProduct.getTips())
				&& StringUtils.isNotBlank(tuangouProduct.getSeller()) && StringUtils.isNotBlank(tuangouProduct.getPhone()) && StringUtils.isNotBlank(tuangouProduct.getAddress()) && StringUtils.isNotBlank(tuangouProduct.getCoords())
				&& StringUtils.isNotBlank(tuangouProduct.getShopseller()) && StringUtils.isNotBlank(tuangouProduct.getShopphone()) && StringUtils.isNotBlank(tuangouProduct.getShopaddress()) && StringUtils.isNotBlank(tuangouProduct.getShopcoords())
				&& StringUtils.isNotBlank(tuangouProduct.getOpentime()) && StringUtils.isNotBlank(tuangouProduct.getTrafficinfo());
		if (SUBCATEGORY_ROUTE.equals(tuangouProduct.getSubcategory())) {
			flag = flag && StringUtils.isNotBlank(tuangouProduct.getDestination());
		}
		return flag;
	}

	public void setGroupDreamService(GroupDreamService groupDreamService) {
		this.groupDreamService = groupDreamService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setViewPageService(ViewPageService viewPageService) {
		this.viewPageService = viewPageService;
	}

	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}

	public void setProdProductPlaceService(ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}

	public void setPlaceCoordinateBaiduService(PlaceCoordinateBaiduService placeCoordinateBaiduService) {
		this.placeCoordinateBaiduService = placeCoordinateBaiduService;
	}

	public void setPlacePlaceDestService(PlacePlaceDestService placePlaceDestService) {
		this.placePlaceDestService = placePlaceDestService;
	}

	public void setDistributionService(DistributionService distributionService) {
		this.distributionService = distributionService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
}
