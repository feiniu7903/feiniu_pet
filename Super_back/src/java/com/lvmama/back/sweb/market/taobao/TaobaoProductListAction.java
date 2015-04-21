package com.lvmama.back.sweb.market.taobao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.json.JSONArray;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.Pagination;
import com.lvmama.back.utils.TaobaoUtils;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.market.TaobaoProduct;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.market.TaobaoProductService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceCoordinateBaidu;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.service.perm.PermRoleService;
import com.lvmama.comm.pet.service.place.PlaceCoordinateBaiduService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.vo.Constant;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.ItemCat;
import com.taobao.api.domain.ItemProp;
import com.taobao.api.domain.PropValue;
import com.taobao.api.request.ItemAddRequest;
import com.taobao.api.request.ItemcatsAuthorizeGetRequest;
import com.taobao.api.request.ItemcatsGetRequest;
import com.taobao.api.request.ItempropsGetRequest;
import com.taobao.api.request.ItemsListGetRequest;
import com.taobao.api.response.ItemAddResponse;
import com.taobao.api.response.ItemcatsAuthorizeGetResponse;
import com.taobao.api.response.ItemcatsGetResponse;
import com.taobao.api.response.ItempropsGetResponse;
import com.taobao.api.response.ItemsListGetResponse;

/**
 * 
 * @author zhangwengang
 * 
 */
@Results({
		@Result(name = "toProductList", location = "/WEB-INF/pages/back/market/taobao/product_list.jsp"),
		@Result(name = "toCate", location = "/WEB-INF/pages/back/market/taobao/push_prod.jsp") })
public class TaobaoProductListAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1219012267482045364L;

	private ProdProductService prodProductService;
	private TaobaoProductService taobaoProductService;
	private PermRoleService permRoleService;
	private PlaceService placeService;
	private PlaceCoordinateBaiduService placeCoordinateBaiduService;
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
	private String taobaoId;
	private List<ItemCat> cateList1;
	private List<ItemCat> cateList2;
	private List<ItemCat> cateList3;
	private List<ItemCat> cateList4;
	private List<ItemCat> cateList5;
	private String cid;
	private String pidvid;
	private String cateName;

	/** 当前页 **/
	private Integer currentPage = 1;
	private String isAperiodic;
	private String isTbOnline;
	private String tbStatus;
	private String startTime;
	private String endTime;

	@Action("/taobaoProd/toProductList")
	public String toProductList() {
		return "toProductList";
	}

	/**
	 * 查询产品
	 */
	@Action("/taobaoProd/queryProductList")
	public String queryProductList() {
		Map<String, Object> searchConds = initParam();
		String success = toResult(searchConds, true);

		searchConds.put("placeName", placeName);
		searchConds.put("page", pagination.getPage());
		searchConds.put("perPageRecord", pagination.getPerPageRecord());
		getSession().setAttribute("PROD_GO_UPSTEP_URL", searchConds);

		return success;
	}

	/**
	 * 进入选择目录页面
	 */
	@Action("/taobaoProd/toCate")
	public String toCate() {
		// 初始化开放平台上传目录
		try {
			TaobaoClient client = TaobaoUtils.init();
			ItemcatsAuthorizeGetRequest req = new ItemcatsAuthorizeGetRequest();
			req.setFields("brand.vid,brand.name,item_cat.cid,item_cat.name,item_cat.status,item_cat.sort_order,item_cat.parent_cid,item_cat.is_parent");
			ItemcatsAuthorizeGetResponse response = client.execute(req,
					TaobaoUtils.sessionkey);
			cateList1 = response.getSellerAuthorize().getItemCats();
			cateList2 = new ArrayList<ItemCat>();
			cateList3 = new ArrayList<ItemCat>();
			cateList4 = new ArrayList<ItemCat>();
			cateList5 = new ArrayList<ItemCat>();
			// getSession().setAttribute("cateList", cateList);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		ProdProduct prodProduct = prodProductService.getProdProduct(Long
				.valueOf(productId));
		if (null != prodProduct) {
			getSession().setAttribute("prodProduct", prodProduct);
		}
		return "toCate";
	}

	/**
	 * 选择上传目录
	 */
	@Action("/taobaoProd/selectCate")
	public void selectCate() {
		JSONArray json = new JSONArray();
		try {
			TaobaoClient client = TaobaoUtils.init();
			ItemcatsGetRequest req = new ItemcatsGetRequest();
			req.setFields("cid,parent_cid,name,is_parent");
			if (StringUtils.isNotEmpty(cid)) {
				req.setParentCid(Long.valueOf(cid));
			}
			ItemcatsGetResponse response = client.execute(req);
			List<ItemCat> cateList = response.getItemCats();
			JSONObject jo = null;
			if (null != cateList) {
				for (ItemCat ic : cateList) {
					jo = new JSONObject();
					jo.put("cid", ic.getCid());
					jo.put("name", ic.getName());
					jo.put("isParent", ic.getIsParent());
					json.put(jo);
				}
			} else {
				req = new ItemcatsGetRequest();
				req.setFields("cid,parent_cid,name,is_parent");
				if (StringUtils.isNotEmpty(cid)) {
					req.setCids(cid);
				}
				response = client.execute(req);
				cateList = response.getItemCats();
				if (null != cateList) {
					for (ItemCat ic : cateList) {
						jo = new JSONObject();
						jo.put("cid", ic.getCid());
						jo.put("name", ic.getName());
						jo.put("isParent", ic.getIsParent());
						json.put(jo);
					}
				}
			}
			sendAjaxMsg(json.toString());
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 选择上传景区
	 */
	@Action("/taobaoProd/selectRegion")
	public void selectRegion() {
		JSONArray json = new JSONArray();
		try {
			TaobaoClient client = TaobaoUtils.init();
			ItempropsGetRequest req = new ItempropsGetRequest();
			req.setFields("pid,name,must,multi,prop_values");
			req.setIsKeyProp(true);

			if (StringUtils.isNotEmpty(cid)) {
				req.setCid(Long.valueOf(cid));
			}
			ItempropsGetResponse response = client.execute(req);
			List<ItemProp> propsList = response.getItemProps();
			JSONObject jo = null;
			if (null != propsList) {
				for (ItemProp ip : propsList) {
					Long pid = ip.getPid();
					for (PropValue pv : ip.getPropValues()) {
						jo = new JSONObject();
						jo.put("pid", pid);
						jo.put("vid", pv.getVid());
						jo.put("name", pv.getName());
						json.put(jo);
					}
				}
			}
			sendAjaxMsg(json.toString());
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
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
		searchConds.put("sort1", "true");
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
					"/taobaoProd/queryProductList.do", true, searchConds));
		}
		return "toProductList";
	}

	/**
	 * 上传淘宝
	 */
	@Action("/taobaoProd/push")
	public void push() {
		JSONObject json = new JSONObject();
		try {
			ProdProductBranch prodProductBranch = prodProductService
					.getProductDefaultBranchByProductId(Long.valueOf(productId));
			if (null != prodProductBranch) {
				ProdProduct prodProduct = prodProductBranch.getProdProduct();
				TaobaoClient client = TaobaoUtils.init();
				ItemAddRequest req = new ItemAddRequest();
				req.setNum(prodProductBranch.getMaximum());
				req.setPrice(String.valueOf(prodProduct.getMarketPriceYuan()));// 门市价
				req.setType("fixed");
				req.setStuffStatus("new");
				req.setTitle(prodProduct.getProductName());
				if (StringUtils.isNotEmpty(prodProduct.getDescription())) {
					req.setDesc(prodProduct.getDescription());
				} else {
					req.setDesc(prodProduct.getProductName());
				}
				req.setLocationState("上海");
				req.setLocationCity("上海");
				req.setCid(Long.valueOf(cid));// 50017766L
				req.setProps(pidvid);
				req.setAuctionPoint(5L);// 默认返利0.5%
				req.setHasInvoice(true);// 默认有发票
				req.setApproveStatus("instock");// 默认在库中
				req.setSubStock(1L);// 支持拍下减库存
				ItemAddResponse response = client.execute(req,
						TaobaoUtils.sessionkey);
				if (null == response.getErrorCode()) {
					Long taobaoReturnId = response.getItem().getNumIid();
					// 入库
					TaobaoProduct tp = new TaobaoProduct();
					tp.setTbProductReturnId(taobaoReturnId);
					tp.setCateName(java.net.URLDecoder
							.decode(cateName, "UTF-8"));
					tp.setCateId(Long.valueOf(cid));
					tp.setPidVid(pidvid);
					tp.setProductId(Long.valueOf(productId));
					Date date = new Date();
					tp.setTbPushDate(date);
					tp.setIsTbOnline("Y");
					tp.setCreateDate(date);
					taobaoProductService.addProduct(tp);
					json.put("flag", "success");
				} else {
					json.put("flag", "fail");
					json.put("msg",
							response.getMsg() + " " + response.getSubMsg());
				}
			} else {
				json.put("flag", "fail");
				json.put("msg", "产品详细信息没找到");
			}
			sendAjaxMsg(json.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新淘宝状态
	 */
	@Action("/taobaoProd/updateTaobaoStatus")
	public void updateTaobaoStatus() {
		JSONObject json = new JSONObject();
		try {
			Map<String, Object> searchConds = initParam();
			toResult(searchConds, true);
			List<TaobaoProduct> productList = pagination.getRecords();
			if (null != productList && productList.size() > 0) {
				String paraIds = "";
				for (int i = 0; i < productList.size(); i++) {
					TaobaoProduct tp = productList.get(i);
					if (null != tp.getTbProductReturnId()) {
						paraIds += tp.getTbProductReturnId() + ",";
					}
				}
				if (StringUtils.isNotBlank(paraIds)) {
					TaobaoClient client = TaobaoUtils.init();
					ItemsListGetRequest req = new ItemsListGetRequest();
					req.setFields("num_iid,approve_status");
					req.setNumIids(paraIds);
					ItemsListGetResponse response = client.execute(req, TaobaoUtils.sessionkey);
					if (null == response.getErrorCode()) {
						Map<String, Object> paraMap = new HashMap<String, Object>();
						List<Item> itemList = response.getItems();
						for (Item item : itemList) {
							paraMap.put("taobaoId", item.getNumIid());
							paraMap.put("status", item.getApproveStatus());
							taobaoProductService.updateProduct(paraMap);
						}
						json.put("flag", "success");
					} else {
						json.put("flag", "fail");
						json.put("msg",
								response.getMsg() + " " + response.getSubMsg());
					}
				} else {
					json.put("flag", "success");
				}
			}
			sendAjaxMsg(json.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 上传地图
	 */
	@Action("/taobaoProd/uploadMap")
	public void uploadMap() {
		JSONObject json = new JSONObject();
		try {
			boolean flag = false;
			List<Place> placeList = placeService.getPlaceByProductIdAndStage(Long.valueOf(productId), 2L);
			for (Place place : placeList) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("placeId", place.getPlaceId());
				List<PlaceCoordinateBaidu> pcList = placeCoordinateBaiduService.getPlaceCoordinateBaiduByParam(params);
				if (null != pcList && pcList.size() == 1) {
					PlaceCoordinateBaidu pc = pcList.get(0);
					String username = TaobaoUtils.username;
					String userkey = TaobaoUtils.userkey;
					String para = "?itemid=" + URLEncoder.encode(taobaoId, "utf8");;
					para += "&username=" + URLEncoder.encode(username, "utf8");
					para += "&userkey=" + userkey;
					JSONObject jo = new JSONObject();
					jo.put("name", place.getName());
					jo.put("address", place.getAddress());
					jo.put("city", place.getCity());
					jo.put("lng", pc.getLongitude().toString());
					jo.put("lat", pc.getLatitude().toString());
					JSONArray jsonArray = new JSONArray();
					jsonArray.put(jo);
					para += "&storeinfo=" + URLEncoder.encode(jsonArray.toString(), "utf8");
					para += "&key=" + TaobaoUtils.StringMD5(userkey + taobaoId + username + jsonArray.toString(), "utf8");
					para += "&_input_charset=utf8";
					String result = HttpsUtil.requestGet(TaobaoUtils.map_url + para);
					JSONObject rst = JSONObject.fromObject(result);
					if ("NO_ERROR".equals(rst.get("errortype"))) {
						// 更新状态
						params.clear();
						params.put("taobaoId", taobaoId);
						params.put("isMaped", "Y");
						taobaoProductService.updateProduct(params);
						json.put("flag", "success");
					} else {
						json.put("flag", "fail");
						json.put("msg", rst.get("error"));
					}
					flag = true;
					break;
				}
			}
			
			if (!flag) {
				json.put("flag", "fail");
				json.put("msg", "没有找到该产品地图信息");
			}
			sendAjaxMsg(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<String, Object> initParam() {
		Map<String, Object> searchConds = new HashMap<String, Object>();
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
		if (StringUtils.isNotEmpty(this.subProductType)) {
			searchConds.put("subProductType", subProductType);
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
		// 是否为期票
		if (StringUtils.isNotEmpty(isAperiodic)) {
			searchConds.put("isAperiodic", isAperiodic);
		}
		// 已上传淘宝
		if (StringUtils.isNotEmpty(isTbOnline)) {
			searchConds.put("isTbOnline", isTbOnline);
		}
		// 淘宝状态
		if (StringUtils.isNotEmpty(tbStatus)) {
			searchConds.put("tbStatus", tbStatus);
		}
		// 开始时间
		if (StringUtils.isNotEmpty(startTime)) {
			searchConds.put("startTime", DateUtil.stringToDate(startTime
					+ " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		}
		// 结束时间
		if (StringUtils.isNotEmpty(endTime)) {
			searchConds.put("endTime", DateUtil.stringToDate(endTime
					+ " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		return searchConds;
	}

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

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public void setTaobaoProductService(
			TaobaoProductService taobaoProductService) {
		this.taobaoProductService = taobaoProductService;
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

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	public void setPlaceCoordinateBaiduService(
			PlaceCoordinateBaiduService placeCoordinateBaiduService) {
		this.placeCoordinateBaiduService = placeCoordinateBaiduService;
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

	public String getTaobaoId() {
		return taobaoId;
	}

	public void setTaobaoId(String taobaoId) {
		this.taobaoId = taobaoId;
	}

	public List<ItemCat> getCateList1() {
		return cateList1;
	}

	public void setCateList1(List<ItemCat> cateList1) {
		this.cateList1 = cateList1;
	}

	public List<ItemCat> getCateList2() {
		return cateList2;
	}

	public void setCateList2(List<ItemCat> cateList2) {
		this.cateList2 = cateList2;
	}

	public List<ItemCat> getCateList3() {
		return cateList3;
	}

	public void setCateList3(List<ItemCat> cateList3) {
		this.cateList3 = cateList3;
	}

	public List<ItemCat> getCateList4() {
		return cateList4;
	}

	public void setCateList4(List<ItemCat> cateList4) {
		this.cateList4 = cateList4;
	}

	public List<ItemCat> getCateList5() {
		return cateList5;
	}

	public void setCateList5(List<ItemCat> cateList5) {
		this.cateList5 = cateList5;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getPidvid() {
		return pidvid;
	}

	public void setPidvid(String pidvid) {
		this.pidvid = pidvid;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getIsTbOnline() {
		return isTbOnline;
	}

	public void setIsTbOnline(String isTbOnline) {
		this.isTbOnline = isTbOnline;
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

}
