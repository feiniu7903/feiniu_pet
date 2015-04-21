package com.lvmama.back.sweb.market.juhuasuan;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.json.JSONException;
import org.json.JSONObject;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.Pagination;
import com.lvmama.back.utils.TaobaoUtils;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.market.ApplyCity;
import com.lvmama.comm.bee.po.market.TaobaoProduct;
import com.lvmama.comm.bee.po.market.TaobaoProductDetail;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.market.ApplyCityService;
import com.lvmama.comm.bee.service.market.TaobaoProductDetailService;
import com.lvmama.comm.bee.service.market.TaobaoProductService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.pub.Cate;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.service.perm.PermRoleService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.vo.Constant;
import com.taobao.api.ApiException;
import com.taobao.api.FileItem;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.JuCnareaGetRequest;
import com.taobao.api.request.JuItemAddRequest;
import com.taobao.api.response.JuCnareaGetResponse;
import com.taobao.api.response.JuItemAddResponse;

/**
 * 聚划算产品管理
 * 
 * @author zhushuying
 * 
 */
@Results({
		@Result(name = "toProductList", location = "/WEB-INF/pages/back/market/juhuasuan/product_list.jsp"),
		// @Result(name = "toProductList", location
		// ="/WEB-INF/pages/back/market/juhuasuan/mainPic.jsp"),
		@Result(name = "toCate", location = "/WEB-INF/pages/back/market/juhuasuan/push_prod.jsp") })
public class JuHuaSuanProductListAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1219012267482045364L;

	private ProdProductService prodProductService;
	private TaobaoProductService taobaoProductService;
	private TaobaoProductDetailService taobaoProductDetailService;
	private ApplyCityService applyCityService;
	private PermRoleService permRoleService;
	private String productName;
	private String onLine;
	private String bizcode;
	private String productId;
	private Long placeId;
	private String prodBranchId;
	private String onlineStatus;
	private String filialeName;
	private String subProductType;
	private String placeName;
	private String productType;
	private String selfPack;
	private String city;
	private String country;
	private Long tbProductDetailId;
	private String destination; // 国内省市景区

	private Long jhsReturnId = 0L;
	private String isStorage;// 是否入库
	private String applyCityId;
	private String mainPic;
	private String shortName;
	private String longName;
	private String originalPrice;
	private String activityPrice;
	private float discount;
	private int itemCount;
	private int limitNum;
	private String locCity;
	private String tripTypeValue;
	// 出发地和目的地
	private String startArea;
	private String startArea2;
	private String endArea;
	private String endArea2;
	private String endArea3;
	private String optinion;
	private String multiCityReason;
	private String scheduleAdvice;
	private String tgHistory;
	private String strength;

	private List<Cate> cateList1;
	private List<ApplyCity> applyCityList;

	private String tbProductInterfaceId;
	/** 当前页 **/
	private String isAperiodic;
	private String tbStatus; // 淘宝状态
	private String startTime; // 上传淘宝时间
	private String endTime;
	private String uploadJhsTimeStart; // 上传聚划算时间
	private String uploadJhsTimeEnd;

	private File uploadFile;// 上传图片
	private String uploadFileFileName;
	private String uploadFileContentType;

	@Action("/juhuasuanProd/toProductList")
	public String toProductList() {
		return "toProductList";
	}

	@Action("/juhuasuanProd/queryProductList")
	public String queryProductList() {
		Map<String, Object> searchConds = initParam();
		String success = toResult(searchConds, true);

		searchConds.put("placeName", placeName);
		searchConds.put("page", pagination.getPage());
		searchConds.put("perPageRecord", pagination.getPerPageRecord());
		getSession().setAttribute("PROD_GO_UPSTEP_URL", searchConds);

		return success;
	}

	public void getAllApplyCity() {
		applyCityList = applyCityService.selectAllApplyCity();
	}

	// 发布到聚划算
	@Action("/juhuasuanProd/toCate")
	public String toCate() {
		getSession().removeAttribute("taobaoProduct");// 清除session
		getSession().removeAttribute("detailProd");
		// 查询淘宝产品信息
		TaobaoProduct taobaoProduct = taobaoProductService
				.selectProductById(Long.valueOf(tbProductInterfaceId));
		// 查询产品信息
		ProdProduct prodProduct = prodProductService
				.getProdProduct(taobaoProduct.getProductId());
		taobaoProduct.setProdProduct(prodProduct);
		getSession().setAttribute("taobaoProduct", taobaoProduct);
		// 查询淘宝产品详细信息
		TaobaoProductDetail detailProd = taobaoProductDetailService
				.selectDetailProductById(taobaoProduct
						.getTbProductInterfaceId());
		if (null != detailProd) {
			applyCityId = detailProd.getApplyCityId().toString();
		} else {
			detailProd = new TaobaoProductDetail();
		}
		detailProd.setTaobaoProduct(taobaoProduct);
		getSession().setAttribute("detailProd", detailProd);

		getArea();
		getAllApplyCity();
		return "toCate";
	}

	// 获取开放平台上目的地信息
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getArea() {
		try {
			TaobaoClient client = TaobaoUtils.init();
			JuCnareaGetRequest req = new JuCnareaGetRequest();
			JuCnareaGetResponse response = client.execute(req);
			destination = response.getCnArea();
			JSONArray jsonArray = JSONArray.fromObject(destination);
			JsonConfig config = new JsonConfig();
			Map<String, Class> classMap = new HashMap<String, Class>();
			classMap.put("child", Cate.class);
			config.setClassMap(classMap);
			config.setCollectionType(List.class);
			config.setRootClass(Cate.class);
			List<Cate> cateList = (List<Cate>) JSONArray.toCollection(
					jsonArray, config);

			cateList1 = new ArrayList<Cate>();
			for (Cate cate : cateList) {
				cateList1.add(cate);
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

	private Long saveRecord() throws IOException {
		Long res = 0L;
		// 根据id查询信息
		TaobaoProductDetail detailProd = taobaoProductDetailService
				.selectDetailProductById(Long.valueOf(tbProductInterfaceId));
		// 判断该产品信息是否已存在数据库中，如果存在就修改该产品信息内容
		if (detailProd != null) {
			TaobaoProductDetail tpd = saveMsg();
			tpd.setTbProductDetailsId(detailProd.getTbProductDetailsId());
			tpd.setModifyDate(new Date());
			res = (long) taobaoProductDetailService.updateProductDetail(tpd);
		} else {
			TaobaoProductDetail tpd = saveMsg();
			tpd.setTbProductInterfaceId(Long.valueOf(tbProductInterfaceId));
			tpd.setPlatFormId(1001L);
			tpd.setIsStorage(isStorage);
			tpd.setTripFormType(tripTypeValue);
			tpd.setActivityEnterId(66625L);
			tpd.setCreateDate(new Date());
			if (jhsReturnId != 0) {
				tpd.setTbJhsReturnId(jhsReturnId);
			}
			res = taobaoProductDetailService.addProductDetail(tpd);
		}
		return res;
	}

	// 保存信息到数据库
	@Action("/juhuasuanProd/save")
	public void save() {
		JSONObject json = new JSONObject();
		Long res = 0L;
		try {
			res = saveRecord();
			if (res > 0) {
				json.put("flag", "success");
			} else {
				json.put("flag", "fail");
				json.put("msg", "数据库保存异常");
			}
		} catch (JSONException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
			try {
				json.put("flag", "fail");
				json.put("msg", e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		sendAjaxMsg(json.toString());
	}

	/**
	 * 保存并发布到聚划算
	 * 
	 * @throws ApiException
	 * @throws IOException
	 */
	@Action("/juhuasuanProd/saveAndPush")
	public void pushToJuhuasuan() {
		JSONObject json = new JSONObject();
		try {
			// 查询城市详细信息
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cityId", applyCityId);
			// 获取城市信息
			ApplyCity applyCity = applyCityService.selectApplyCityBy(map);
			TaobaoClient client = TaobaoUtils.init();
			JuItemAddRequest req = new JuItemAddRequest();
			req.setRelatedCity(applyCity.getCityPY());
			// 调用方法
			checkImage();
			// 转化图片格式
			byte[] img = getImage(uploadFile);
			FileItem fItem = new FileItem(uploadFileFileName, img);
			req.setMainPic(fItem);
			req.setLongName(longName);
			req.setShortName(shortName);
			req.setActivityPrice(activityPrice);
			req.setItemCount(Long.valueOf(itemCount));
			req.setLimitNum((long) limitNum);
			if (locCity != null && locCity.equals("上海")) {
				locCity = "shanghai";
			}
			req.setCity(locCity);
			req.setTripTypeValue(Long.valueOf(tripTypeValue));
			req.setStartArea(startArea2);
			req.setDestinyArea(endArea2);
			req.setOpinion(optinion);
			req.setMultiCityReason(multiCityReason);
			req.setScheduleAdvice(scheduleAdvice);
			req.setTgHistory(tgHistory);
			req.setStrength(strength);
			req.setPlatformId(1001L);
			// 获取淘宝产品信息
			TaobaoProduct taobaoProduct = taobaoProductService
					.selectProductById(Long.valueOf(tbProductInterfaceId));
			// 淘宝返回的id
			req.setItemId(taobaoProduct.getTbProductReturnId());
			req.setActivityEnterId(66625L);
			
			// 保存到数据库
			Long res = saveRecord();
			if (res == 0L) {
				json.put("flag", "fail");
				json.put("msg", "数据库保存异常");
			} else {
				JuItemAddResponse response = client.execute(req,
						TaobaoUtils.sessionkey);
				if (null == response.getErrorCode()) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("tbProductInterfaceId", tbProductInterfaceId);
					// 发布到聚划算时间
					param.put("jhsPushDate", response.getJuItemData().getCreated());
					taobaoProductService.updateProduct(param);
					// 获取返回的聚划算产品id
					jhsReturnId = response.getJuItemData().getJuId();
					// 更新聚划算id
					Long updateRes = saveRecord();
					if(updateRes!=0L){
						json.put("flag", "success");
					}
				} else {
					json.put("flag", "fail");
					json.put("msg", response.getSubMsg());
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				json.put("flag", "fail");
				json.put("msg", e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		sendAjaxMsg(json.toString());
	}

	// 将上传的图片转化成二进制
	private byte[] getImage(File file) {
		byte[] img = null;
		FileInputStream fis = null;
		if (file != null) {
			try {
				fis = new FileInputStream(file);
				if (fis != null) {
					int len;
					try {
						len = fis.available();
						img = new byte[len];
						fis.read(img);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return img;
	}

	// 保存信息
	private TaobaoProductDetail saveMsg() throws IOException {
		TaobaoProductDetail tpd = new TaobaoProductDetail();
		tpd.setApplyCityId(Long.valueOf(applyCityId));
		// tpd.setMainPic(inputStreamToByte(new FileInputStream(uploadFile)));
		// 验证图片大小
		checkImage();
		tpd.setMainPic(getImage(uploadFile));
		tpd.setLongName(longName);
		tpd.setShortName(shortName);
		tpd.setOriginalPrice(Float.valueOf(originalPrice));
		tpd.setActivityPrice(Float.valueOf(activityPrice));
		tpd.setDiscount(discount);
		tpd.setItemCount(itemCount);
		tpd.setLimitNum(limitNum);
		if (locCity != null && locCity.equals("上海")) {
			locCity = "shanghai";
		}
		tpd.setLocCity(locCity);
		tpd.setStartArea(startArea2);
		tpd.setDestinyArea(endArea2);
		tpd.setOptinion(optinion);
		tpd.setMultiCityReason(multiCityReason);
		tpd.setScheduleAdvice(scheduleAdvice);
		tpd.setTgHistory(tgHistory);
		tpd.setStrength(strength);
		return tpd;
	}

	// 验证上传的图片大小
	private void checkImage() throws IOException {
		JSONObject json = new JSONObject();
		Long size = uploadFile.length() / 1024;
		BufferedImage buff = ImageIO.read(uploadFile);
		buff.getWidth();
		buff.getHeight();
		if (size > 1024 || (buff.getWidth() < 960 && buff.getHeight() < 640)) {
			throw new IOException("图片容量过大");
		}
	}

	private String toResult(Map<String, Object> searchConds, boolean autoReq) {
		Integer totalRowCount = taobaoProductService
				.selectRowCount(searchConds);
		if (autoReq) {
			pagination = super.initPagination();
		} else {
			pagination = new Pagination();
			pagination.setPage((Integer) searchConds.get("page"));
			pagination.setPerPageRecord((Integer) searchConds
					.get("perPageRecord"));
		}
		pagination.setTotalRecords(totalRowCount);
		searchConds.put("_startRow", pagination.getFirstRow());
		searchConds.put("_endRow", pagination.getLastRow());
		searchConds.put("sort2", "true");
		List<TaobaoProduct> productList = taobaoProductService
				.selectProductByParms(searchConds);
		pagination.setRecords(productList);
		if (autoReq) {
			pagination.setActionUrl(WebUtils.getUrl(getRequest()));
		} else {
			if (searchConds.containsKey("sProductName")) {
				productName = (String) searchConds.get("sProductName");
				searchConds.put("productName", productName);
			}
			pagination.setActionUrl(WebUtils.getUrl(
					"/juhuasuanProd/queryProductList.do", true, searchConds));
		}
		return "toProductList";
	}

	private Map<String, Object> initParam() {
		Map<String, Object> searchConds = new HashMap<String, Object>();
		searchConds.put("isTbOnline", "Y");// 已上传淘宝上的门票

		if (StringUtils.isNotEmpty(productName)) {
			searchConds.put("sProductName", productName);
		}

		if (StringUtils.isNotEmpty(this.bizcode)) {
			searchConds.put("bizcode", bizcode);
		}

		if (StringUtils.isNotEmpty(this.productId)) {
			if (NumberUtils.toLong(productId) > 0) {
				searchConds.put("productId", productId);
			}
		}
		if (StringUtils.isNotEmpty(prodBranchId)) {
			if (NumberUtils.toLong(prodBranchId.trim()) > 0) {
				searchConds.put("prodBranchId", prodBranchId.trim());
			}
		}
		if (StringUtils.isNotEmpty(this.filialeName)) {
			searchConds.put("filialeName", filialeName);
		}
		if (StringUtils.isNotEmpty(this.onlineStatus)) {
			searchConds.put("onlineStatus", onlineStatus);
		}
		if (StringUtils.isNotEmpty(this.onLine)) {
			searchConds.put("onLine", onLine);
		}
		if (StringUtils.isNotEmpty(this.productType)) {
			searchConds.put("productType", productType);
		}
		if (placeId != null && placeId > 0) {
			searchConds.put("placeId", placeId);
		}
		if (StringUtils.equals(Constant.PRODUCT_TYPE.ROUTE.name(), productType)
				&& StringUtils.isNotEmpty(selfPack)) {
			searchConds.put("selfPack", selfPack);
		}
		if (isProdNotSeeAllData()) {
			searchConds.put("managerIds", permRoleService
					.getParamManagerIdsByRoleId(getSessionUser(),
							Constant.PERM_ROLE_PROD_MANAGER));
			if (permRoleService.checkUserRole(getSessionUser().getUserId(),
					Constant.PERM_ROLE_CREATE)
					|| permRoleService.checkUserRole(getSessionUser()
							.getUserId(), Constant.PERM_ROLE_FIRST_AUDIT)) {
				searchConds.put("orgId", getSessionUserDepartmentId());
			}
			if (permRoleService.checkUserRole(getSessionUser().getUserId(),
					Constant.PERM_ROLE_PROD_MANAGER)
					&& (permRoleService.checkUserRole(getSessionUser()
							.getUserId(), Constant.PERM_ROLE_CREATE) || permRoleService
							.checkUserRole(getSessionUser().getUserId(),
									Constant.PERM_ROLE_FIRST_AUDIT))) {
				searchConds.put("twoRole", Boolean.TRUE);
			}
		}
		if (StringUtils.isNotEmpty(this.country)) {
			searchConds.put("country", country);
		}
		if (StringUtils.isNotEmpty(this.city)) {
			searchConds.put("city", city);
		}
		// 淘宝状态
		if (StringUtils.isNotEmpty(this.tbStatus)) {
			searchConds.put("tbStatus", tbStatus);
		}
		// 上传淘宝时间
		if (StringUtils.isNotEmpty(this.startTime)) {
			searchConds.put("startTime", DateUtil.stringToDate(startTime
					+ " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		if (StringUtils.isNotEmpty(this.endTime)) {
			searchConds.put("endTime", DateUtil.stringToDate(endTime
					+ " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		// 上传聚划算时间
		if (StringUtils.isNotEmpty(this.uploadJhsTimeStart)) {
			searchConds.put("uploadJhsTimeStart", DateUtil.stringToDate(
					uploadJhsTimeStart + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		if (StringUtils.isNotEmpty(this.uploadJhsTimeEnd)) {
			searchConds.put("uploadJhsTimeEnd", DateUtil.stringToDate(
					uploadJhsTimeEnd + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		// 是否为期票
		if (StringUtils.isNotEmpty(isAperiodic)) {
			searchConds.put("isAperiodic", isAperiodic);
		}
		return searchConds;
	}

	/*
	 * // 将图片转化为二进制 private static byte[] inputStreamToByte(InputStream is)
	 * throws IOException { ByteArrayOutputStream bAOutputStream = new
	 * ByteArrayOutputStream(); int ch; while ((ch = is.read()) != -1) {
	 * bAOutputStream.write(ch); } byte data[] = bAOutputStream.toByteArray();
	 * bAOutputStream.close(); return data; }
	 */

	/**
	 * 是否可以查看所有数据(true:不能看所有数据；false:可以看所有数据)
	 * 
	 * @param hasAllDataRole
	 * @return
	 */
	private boolean isProdNotSeeAllData() {
		Boolean hasAllDataRole = permRoleService.checkUserRole(getSessionUser()
				.getUserId(), Constant.PERM_ROLE_ALLDATA);

		return !getSessionUser().isAdministrator() && !hasAllDataRole;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName.trim();
	}

	public String getBizcode() {
		return bizcode;
	}

	public void setBizcode(String bizcode) {
		this.bizcode = bizcode.trim();
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId.trim();
	}

	public List<CodeItem> getFilialeNameList() {
		return CodeSet.getInstance().getCodeListAndBlank(
				Constant.CODE_TYPE.FILIALE_NAME.name());
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public String getFilialeName() {
		return filialeName;
	}

	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public List<CodeItem> getSubProductTypeList() {
		return ProductUtil.getSubProductTypeList(productType, true);
	}

	/**
	 * @return the selfPack
	 */
	public String getSelfPack() {
		return selfPack;
	}

	/**
	 * @param selfPack
	 *            the selfPack to set
	 */
	public void setSelfPack(String selfPack) {
		this.selfPack = selfPack;
	}

	public void setPermRoleService(PermRoleService permRoleService) {
		this.permRoleService = permRoleService;
	}

	public String getOnLine() {
		return onLine;
	}

	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}

	/**
	 * @return the prodBranchId
	 */
	public String getProdBranchId() {
		return prodBranchId;
	}

	/**
	 * @param prodBranchId
	 *            the prodBranchId to set
	 */
	public void setProdBranchId(String prodBranchId) {
		this.prodBranchId = prodBranchId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIsAperiodic() {
		return isAperiodic;
	}

	public void setIsAperiodic(String isAperiodic) {
		this.isAperiodic = isAperiodic;
	}

	public Long getTbProductDetailId() {
		return tbProductDetailId;
	}

	public void setTbProductDetailId(Long tbProductDetailId) {
		this.tbProductDetailId = tbProductDetailId;
	}

	public String getTbStatus() {
		return tbStatus;
	}

	public void setTbStatus(String tbStatus) {
		this.tbStatus = tbStatus;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getUploadJhsTimeStart() {
		return uploadJhsTimeStart;
	}

	public void setUploadJhsTimeStart(String uploadJhsTimeStart) {
		this.uploadJhsTimeStart = uploadJhsTimeStart;
	}

	public String getUploadJhsTimeEnd() {
		return uploadJhsTimeEnd;
	}

	public void setUploadJhsTimeEnd(String uploadJhsTimeEnd) {
		this.uploadJhsTimeEnd = uploadJhsTimeEnd;
	}

	public void setTaobaoProductService(
			TaobaoProductService taobaoProductService) {
		this.taobaoProductService = taobaoProductService;
	}

	public void setTaobaoProductDetailService(
			TaobaoProductDetailService taobaoProductDetailService) {
		this.taobaoProductDetailService = taobaoProductDetailService;
	}

	public String getTbProductInterfaceId() {
		return tbProductInterfaceId;
	}

	public void setTbProductInterfaceId(String tbProductInterfaceId) {
		this.tbProductInterfaceId = tbProductInterfaceId;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestinationL(String destination) {
		this.destination = destination;
	}

	public List<Cate> getCateList1() {
		return cateList1;
	}

	public void setCateList1(List<Cate> cateList1) {
		this.cateList1 = cateList1;
	}

	public List<ApplyCity> getApplyCityList() {
		return applyCityList;
	}

	public void setApplyCitiyList(List<ApplyCity> applyCityList) {
		this.applyCityList = applyCityList;
	}

	public String getApplyCityId() {
		return applyCityId;
	}

	public void setApplyCityId(String applyCityId) {
		this.applyCityId = applyCityId;
	}

	public String getMainPic() {
		return mainPic;
	}

	public void setMainPic(String mainPic) {
		this.mainPic = mainPic;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getActivityPrice() {
		return activityPrice;
	}

	public void setActivityPrice(String activityPrice) {
		this.activityPrice = activityPrice;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public int getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(int limitNum) {
		this.limitNum = limitNum;
	}

	public String getLocCity() {
		return locCity;
	}

	public void setLocCity(String locCity) {
		this.locCity = locCity;
	}

	public String getTripTypeValue() {
		return tripTypeValue;
	}

	public void setTripTypeValue(String tripTypeValue) {
		this.tripTypeValue = tripTypeValue;
	}

	public String getStartArea() {
		return startArea;
	}

	public void setStartArea(String startArea) {
		this.startArea = startArea;
	}

	public String getStartArea2() {
		return startArea2;
	}

	public void setStartArea2(String startArea2) {
		this.startArea2 = startArea2;
	}

	public String getEndArea() {
		return endArea;
	}

	public void setEndArea(String endArea) {
		this.endArea = endArea;
	}

	public String getEndArea2() {
		return endArea2;
	}

	public void setEndArea2(String endArea2) {
		this.endArea2 = endArea2;
	}

	public String getEndArea3() {
		return endArea3;
	}

	public void setEndArea3(String endArea3) {
		this.endArea3 = endArea3;
	}

	public String getOptinion() {
		return optinion;
	}

	public void setOptinion(String optinion) {
		this.optinion = optinion;
	}

	public String getMultiCityReason() {
		return multiCityReason;
	}

	public void setMultiCityReason(String multiCityReason) {
		this.multiCityReason = multiCityReason;
	}

	public String getScheduleAdvice() {
		return scheduleAdvice;
	}

	public void setScheduleAdvice(String scheduleAdvice) {
		this.scheduleAdvice = scheduleAdvice;
	}

	public String getTgHistory() {
		return tgHistory;
	}

	public void setTgHistory(String tgHistory) {
		this.tgHistory = tgHistory;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getIsStorage() {
		return isStorage;
	}

	public void setIsStorage(String isStorage) {
		this.isStorage = isStorage;
	}

	public void setApplyCityService(ApplyCityService applyCityService) {
		this.applyCityService = applyCityService;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public Long getJhsReturnId() {
		return jhsReturnId;
	}

	public void setJhsReturnId(Long jhsReturnId) {
		this.jhsReturnId = jhsReturnId;
	}

	public String getUploadFileContentType() {
		return uploadFileContentType;
	}

	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}
}
