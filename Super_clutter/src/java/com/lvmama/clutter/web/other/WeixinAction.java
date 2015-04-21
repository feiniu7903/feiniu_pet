package com.lvmama.clutter.web.other;

import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.lvmama.clutter.model.MobilePlace;
import com.lvmama.clutter.service.IClientSearchService;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.search.vo.ClientRouteSearchVO;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vst.service.search.VstClientPlaceService;
import com.lvmama.comm.vst.service.search.VstClientProductService;

/**
 * 微信 专用 
 *
 */
@Namespace("/mobile/wx")
public class WeixinAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String signKey = "PrtiSMvRqkOdycjjNXzxd+yiXX5/6b0shyHxp9CNaD0b/s3qUmIOQsOC7lqMfjYqxY+";
	private String openId; // 
	private String couponType;// 1：签到满月送8元优惠券 
	private String sign; // 签名
	private String time; // 时间戳 
	
	
	private int page = 1; // 第几页 
	private String sort;  // 排序
	private String toDest=""; // 目的地
	private String stage;  // 1:目的地（度假）；2：景区；3：酒店；
	private String subjects=" "; // 主题乐园','田园度假', '山水景观', '都市观光'.........
	private String keyword="" ; // 查询关键字 
	private String pageSize="5";//一页显示多少条
	private int windage; // 距离
	private String longitude; // 经度
	private String latitude; // 维度
	
    private static  List<Long> couponIdList;
	static{
		couponIdList=new ArrayList<Long>();
		couponIdList.add(3704L);
	}
	
	protected UserUserProxy userUserProxy;
	private MarkCouponService markCouponService;
	
	/**
	 * 线路
	 */
	protected VstClientProductService vstClientProductService;
	
	/**
	 * 景点 
	 */
	protected VstClientPlaceService vstClientPlaceService;
	
	
	/**
	 * 搜索服务 
	 */
	IClientSearchService mobileSearchService;
	/**
	 * 微信发送优惠券
	 */
	@Action("sendCoupon4Weixin")
	public void sendCoupon4Weixin() {
		Map<String,Object> resultMap = super.resultMapCreator();
		if(this.canSendCoupon() && StringUtils.isNotEmpty(this.openId)) {
			UserUser user = userUserProxy.getUsersByMobOrNameOrEmailOrCard(openId);
			// 如果关注驴妈妈企业微信账号；1：是
			if(null != user && "1".equals(user.getSubScribe())) {
				List<Long> list = null;
				// 签到满月赠送优惠券 
				if("1".equals(couponType)) {
					list = couponIdList;
				}
				if(null != list && list.size() > 0) {
					try {
						for(Long couponId:list) {
							MarkCouponCode couponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(couponId);
							markCouponService.bindingUserAndCouponCode(user, couponCode.getCouponCode());
							LOG.info(user.getUserName()+",send coupon4weixin signin all month coupon id=="+couponCode.getCouponId()+";");
						}
					}catch(Exception e) {
						resultMap = super.putErrorMessage(resultMap, "发送优惠券失败!");
						e.printStackTrace();
					}
				} else {
					resultMap = super.putErrorMessage(resultMap, "优惠券批次号不存在!");
				}
			} else {
				resultMap = super.putErrorMessage(resultMap, "没有找到用户!");
			}
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(resultMap).toString());
	}
	
	/**
	 * 景点查询 
	 * @return
	 * @throws Exception
	 */
	private Map<String,Object> getPlaceMap() throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("stage", StringUtil.isEmptyString(stage)?"2":stage); // 默认stage=2； 
		subjects = URLDecoder.decode(subjects,"UTF-8");
		if(!StringUtils.isEmpty(keyword)) {
			keyword = URLDecoder.decode(keyword,"UTF-8");
			param.put("keyword", keyword);
		} else {
			param.put("windage", this.windage);
			param.put("latitude", this.latitude);
			param.put("longitude", this.longitude);
		}
		param.put("page", page);
		param.put("pageSize", pageSize);
		param.put("sort", sort);
		param.put("subject", subjects);
		param.put("toDest","");
		
		// stage 默认为2
		if(StringUtils.isEmpty(stage)) {
			stage = "2";
		}
		Map<String,Object> map = mobileSearchService.placeSearch(param);
		if(null != map && null != map.get("datas")) {
			return map ;
		} else {
			return  null;
		}
	}
	
	/**
	 * 获取线路数据 
	 */
	private List<ProductBean> getRouteList() throws Exception {
		ClientRouteSearchVO searchVo = new ClientRouteSearchVO();
		searchVo.setChannel(Constant.CHANNEL.CLIENT.name());
		List<String> productTypes = new ArrayList<String>();
		productTypes.add(Constant.PRODUCT_TYPE.ROUTE.name());
		searchVo.setProductType(productTypes);
		searchVo.setPageSize(Integer.parseInt(pageSize));
		searchVo.setPage(page);
		if(!StringUtils.isEmpty(keyword)) {
			keyword = URLDecoder.decode(keyword,"UTF-8");
			searchVo.setKeyword(keyword);
		} else {
			searchVo.setKeyword(" ");
		}
		
		List<ProductBean> listBean = new ArrayList<ProductBean>();
		Page<ProductBean> pageConfig = vstClientProductService.newRouteSearch(searchVo);
		if(null != pageConfig && null != pageConfig.getItems()) {
			//listBean  = pageConfig.getAllItems().subList((page-1)*Integer.parseInt(pageSize), Integer.parseInt(pageSize));
			listBean  = pageConfig.getItems();
		}
		return listBean;
	}
	
	
	
	/**
	 * 景点搜索 
	 */
	@Action("placeSearch4Weixin")
	public void placeSearch4Weixin() {
		Map<String,Object> resultMap = super.resultMapCreator();
		try {
			// 获取数据
			Map<String,Object> map = this.getPlaceMap();
			if(null != map && null != map.get("datas")) {
				resultMap.put("datas", map.get("datas")) ;
			}
		}catch(Exception e ) {
			resultMap = super.putErrorMessage(resultMap, "查询数据失败!");
			e.printStackTrace();
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(resultMap).toString());
	}
	
	/**
	 * 线路搜索 
	 */
	@Action("routeSearch4Weixin")
	public void routeSearch4Weixin() {
		Map<String,Object> resultMap = super.resultMapCreator();
		try {
			List<ProductBean> listBean = this.getRouteList();
			resultMap.put("datas", listBean) ;
		}catch(Exception e ) {
			resultMap = super.putErrorMessage(resultMap, "查询数据失败!");
			e.printStackTrace();
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(resultMap).toString());
	}
	
	/**
	 * 更加关键字查询，判断是否有查询结果。  
	 */
	@Action("hasProduct4Weixin")
	public void hasProduct4Weixin() {
		Map<String,Object> resultMap = super.resultMapCreator();
		int i = 0;
		try {
			// 查询景点
			Map<String,Object> map = this.getPlaceMap();
			if(null != map && null != map.get("datas")) {
				List<MobilePlace> list = (List<MobilePlace>) map.get("datas");
				if(null != list && list.size() > 0) {
					i = 1; // 只有景点
				}
			}
			List<ProductBean> listBean = this.getRouteList();
			if(null != listBean && listBean.size() > 0) {
				if(i == 1) {
					i = 3; // 景点线路都有 
				} else {
					i = 2; // 只有线路
				}
			}
		}catch(Exception e ) {
			resultMap = super.putErrorMessage(resultMap, "查询数据失败!");
			e.printStackTrace();
		}
		resultMap.put("result", i) ;
		this.sendAjaxResultByJson(JSONObject.fromObject(resultMap).toString());
	}
	
	
	/**
	 * 判断是否合法的请求 
	 * @return
	 */
	private boolean canSendCoupon() {
		boolean b = false;
		
		try {
			if(null != this.sign && this.sign.equalsIgnoreCase(MD5.encode(signKey + this.time + this.openId))) {
				b = true;
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	
	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	
	public String getOpenId() {
		return openId;
	}


	public void setOpenId(String openId) {
		this.openId = openId;
	}


	public String getCouponType() {
		return couponType;
	}


	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}


	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public void setMobileSearchService(IClientSearchService mobileSearchService) {
		this.mobileSearchService = mobileSearchService;
	}


	public String getSign() {
		return sign;
	}


	public void setSign(String sign) {
		this.sign = sign;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getToDest() {
		return toDest;
	}

	public void setToDest(String toDest) {
		this.toDest = toDest;
	}
	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getSubjects() {
		return subjects;
	}

	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public int getWindage() {
		return windage;
	}

	public void setWindage(int windage) {
		this.windage = windage;
	}

	public void setVstClientProductService(
			VstClientProductService vstClientProductService) {
		this.vstClientProductService = vstClientProductService;
	}

	public void setVstClientPlaceService(VstClientPlaceService vstClientPlaceService) {
		this.vstClientPlaceService = vstClientPlaceService;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


}
