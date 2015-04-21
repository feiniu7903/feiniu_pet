package com.lvmama.front.web.edm;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.edm.EdmSubscribe;
import com.lvmama.comm.pet.po.edm.EdmSubscribeInfo;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComIps;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.service.edm.EdmSubscribeInfoService;
import com.lvmama.comm.pet.service.edm.EdmSubscribeService;
import com.lvmama.comm.pet.service.pub.ComIpsService;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StackOverFlowUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.front.web.BaseAction;
import com.lvmama.front.web.ajax.AjaxRtnBean;
@Results({ 
	@Result(name = "showSubscribeEmail", location = "/WEB-INF/pages/edm/subscribe.ftl", type = "freemarker"),
	@Result(name = "showUnSubscribeEmail", location = "/WEB-INF/pages/edm/email_unsubscribe.ftl", type = "freemarker")
}) 
public class EdmSubscribeAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1212449787607793299L;
	
	private static final Logger LOG = Logger.getLogger(EdmSubscribeAction.class);
	
	/**
	 * 邮件订阅类型列表
	 */
	private static final String[] EDM_SUBSCRIBE_TYPE={"MARKETING_EMAIL","GROUP_PROCUREMENT_EMAIL","PRODUCT_EMAIL","REBATE_TICKET_EMAIL","SELF_HELP_EMAIL"};
	
	private EdmSubscribeService edmSubscribeService;
	private EdmSubscribeInfoService edmSubscribeInfoService;
	private PlaceCityService placeCityService;
	private EdmSubscribe subscribe;
	private ComIpsService comIpsRemoteBean;
	
	/**
	 * 订阅邮箱地址
	 */
	private String email;
	/**
	 * 会员邮箱地址
	 */
	private String oldEmail;
	/**
	 * 订阅邮件类型
	 */
	private String[] type;
	/**
	 * 退订原因
	 */
	private String[] cancelRemark;
	
	/**
	 * 退订其它原因
	 */
	private String otherCancelRemark;
	
	/**
	 * 退订原因MAP
	 */
	private Map<String,String> cancelMap;
	
	/**
	 * 退订类型
	 */
	private String regEdmType;
	
	private List<ComProvince> provinceList;
	
	private List<ComCity> placeList = new ArrayList<ComCity>();
	
	private List<ComCity> cityList = new ArrayList<ComCity>();
	/**
	 * 转到订阅邮件页面
	 */
	@Action("/edm/showSubscribeEmail")
	public String showSubscribeEmail(){
		if(isLogin()){
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("userId", getUserId());
			subscribe = edmSubscribeService.searchSubscribe(parameters);
			if(null !=subscribe){
				email = subscribe.getEmail();
				return showUpdateSubscribeEmail();
			}
		}
		provinceList = placeCityService.getProvinceList();
		
		return "showSubscribeEmail";
	}
	/**
	 * 转到退订邮件页面
	 */
	@Action("/edm/showUnSubscribeEmail")
	public String showUnSubscribeEmail(){
		getUserEmail();
		putCancelOption();
		if(type!=null && type.length>0){
			regEdmType = type[0];
		}
		return "showUnSubscribeEmail";
	}
	/**
	 * 转到修改订阅邮件页面
	 */
	@Action("/edm/showUpdateSubscribeEmail")
	public String showUpdateSubscribeEmail(){
		//1.取得订阅邮箱
		getUserEmail();
		if(null!=email){
			//2.根据邮箱地址查询订阅用户信息
			subscribe = edmSubscribeService.searchEmailIsSubscribe(email);
			if(null != subscribe){
				//3.查询用户订阅类型信息
				List<EdmSubscribeInfo> infoList =getEdmSubInfoList(subscribe.getId());
				subscribe.setInfoList(infoList);
				String mustWantedTravel = subscribe.getMustWantedTravel();
				if(!StringUtil.isEmptyString(mustWantedTravel)){
					String[] mustPlace = mustWantedTravel.split(",");
					for(int i=0;i<mustPlace.length;i++){
						String place = mustPlace[i];
						ComCity comCity = getCityNameById(place.split("-")[0],place.split("-")[1]);
						if(null != comCity){
							placeList.add(comCity);
						}
					}
				}
				cityList = getComCityList(subscribe.getProvince(),subscribe.getCity());
			}
		}
		if(null==subscribe){
			LOG.debug(email+"  "+type+" 驴妈妈邮件 修改页面没有Email");
		}
		provinceList = placeCityService.getProvinceList();
		for(ComProvince province: provinceList){
			if(null!= subscribe && null != province.getProvinceId() && province.getProvinceId().equals(subscribe.getProvince())){
				province.setChecked("selected");
			}
		}
		return "showSubscribeEmail";
	}
	/**
	 * 订阅驴妈妈推广邮件
	 */
	@Action("/edm/subscribeEmail")
	public void subscribeEmail() throws Exception {
		if(checkEmail(subscribe,false) && checkType(Boolean.TRUE)){
			subScribe();
		}
	}
	/**
	 * 退订驴妈妈推广邮件
	 */
	@Action("/edm/unSubscribeEmail")
	public void unSubscribeEmail() throws Exception {
		if(StringUtil.isEmptyString(email)){
			printRtn(getRequest(), getResponse(),new AjaxRtnBean(false,"A"));
			return;
		}
		if(checkType()){
			EdmSubscribeInfo subscribeInfo = getRegEdmSubInfo();
			try{
				edmSubscribeInfoService.update(subscribeInfo);
				LOG.debug(email+" 用户退订 "+type[0]+" 驴妈妈邮件 成功");
				printRtn(getRequest(), getResponse(),new AjaxRtnBean(true,""));
			}catch(RuntimeException e){
				LOG.error("用户退订驴妈妈邮件失败 失败原因 \r\n"+e);
				printRtn(getRequest(), getResponse(),new AjaxRtnBean(false,"对不起,您退订驴妈妈邮件失败,请稍后重试"));
			}
		}
	}
	/**
	 * 修改驴妈妈推广邮件
	 */
	@Action("/edm/updateSubscribeEmail")
	public void updateSubscribeEmail() throws Exception{
		if(checkEmail(subscribe,false) && checkIsOtherEmail() && checkType(Boolean.FALSE)){
			subScribe();
		}
	}
	/**
	 * 检查邮箱是否为空，有效，是否已订阅邮件
	 * 输出错误信息A:为空,B:验证不过,C:已订阅
	 */
	@Action("/edm/checkEmailIsSubscribe")
	public void checkEmailIsSubscribe() throws Exception{
		String isUpdate = getRequest().getParameter("isUpdate");
		if(StringUtil.isEmptyString(isUpdate)){
			isUpdate = "false";
		}
		if(checkEmail(subscribe,"true".equals(isUpdate)?true:false)){
			printRtn(getRequest(), getResponse(),new AjaxRtnBean(true, ""));
		}
	}

	/**
	 * 根据省份ID取得城市列表
	 */
	@Action("/edm/getCityByProvinceId")
	public void getCityByProvince(){
		String provinceId = getRequest().getParameter("provinceId");
		if(StringUtil.isEmptyString(provinceId)){
			printRtn(getRequest(), getResponse(),new ArrayList<ComCity>());	
			return;
		}
		List<ComCity> cityList = getComCityList(provinceId);
		printRtn(getRequest(), getResponse(),cityList);
	}
	
	/**
	 * 根据省份ID取得城市列表
	 * @param provinceId
	 * @param cityId
	 * @return
	 */
	private List<ComCity> getComCityList(final String provinceId, final String cityId){
		if(StringUtil.isEmptyString(cityId)){
			return null;
		}
		List<ComCity> cityList = getComCityList(provinceId);
		for(ComCity city: cityList){
			if(cityId.equals(city.getCityId())){
				city.setChecked("selected");
			}
		}
		return cityList;
	}
	private ComCity getCityNameById(final String provinceId, final String cityId){
		if(StringUtil.isEmptyString(cityId)){
			return null;
		}
		List<ComCity> cityList = getComCityList(provinceId);
		for(ComCity city: cityList){
			if(cityId.equals(city.getCityId())){
				return city;
			}
		}
		return null;
	}
	private List<ComCity> getComCityList(final String provinceId){
		List<ComCity> cityList = (List<ComCity>)MemcachedUtil.getInstance().get("COM_CITY_BY_PROVINCEID_"+provinceId);
		if(null == cityList){
		   cityList = placeCityService.getCityListByProvinceId(provinceId);
		   MemcachedUtil.getInstance().set("COM_CITY_BY_PROVINCEID_"+provinceId,60*60*24*30, cityList);
		}
		return cityList;
	}
	private boolean insert(){
		if(null ==subscribe){
			return Boolean.FALSE;
		}
		if(StringUtil.isEmptyString(subscribe.getCity())){
			String ip =  getIpAddr();
			if(!StringUtil.isEmptyString(ip)){
				ComIps comIps = comIpsRemoteBean.query(ip);
				if(null !=comIps){
					subscribe.setProvince(comIps.getProvinceId());
					subscribe.setCity(comIps.getCityId());
				}else{
					LOG.info("subscriber ip is "+ip +", not find city!");
				}
			}
		}
		subscribe = edmSubscribeService.insert(subscribe);
		if(null != subscribe.getId()){
			for(EdmSubscribeInfo info:subscribe.getInfoList()){
				info.setEdmUserId(subscribe.getId());
				info = edmSubscribeInfoService.insert(info);
			}
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	private boolean update(final EdmSubscribe edm){
		copyParams(subscribe,edm);
		try{
			edmSubscribeService.update(edm);
			EdmSubscribeInfo info = new EdmSubscribeInfo();
			info.setEdmUserId(edm.getId());
			info.setIsValid("N");
			info.setCancelRemark("修改订阅信息删除");
			edmSubscribeInfoService.update(info);
			for(int i=0;i<subscribe.getInfoList().size();i++){
				info = subscribe.getInfoList().get(i);
				info.setEdmUserId(edm.getId());
				edmSubscribeInfoService.insert(info);
			}
			return Boolean.TRUE;
		}catch(Exception e){
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
		}
		return Boolean.FALSE;
	}
	
	
	private void subScribe(){
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("email", subscribe.getEmail());
		EdmSubscribe edm = edmSubscribeService.searchSubscribe(parameters);
		boolean isSuccess = Boolean.FALSE;
		if(null!=edm){
			isSuccess = update(edm);
		}else{
			isSuccess = insert();
		}
		printRtn(getRequest(), getResponse(),new AjaxRtnBean(isSuccess, "F"));
	}
	private void copyParams(final EdmSubscribe source,final EdmSubscribe target){
		target.setEmail(source.getEmail());
		target.setCity(source.getCity());
		target.setProvince(source.getProvince());
		target.setTravelTime(source.getTravelTime());
		target.setMustWantedTravel(source.getMustWantedTravel());
	}
	private boolean checkIsOtherEmail(){
		if(!oldEmail.equalsIgnoreCase(email)){
			EdmSubscribe edm = edmSubscribeService.searchEmailIsSubscribe(email.toLowerCase());
			if(null != edm){
				return subValid("C",email + "已订阅邮件，将返回false");
			}
		}
		return Boolean.TRUE;
	}
	private boolean checkType() throws Exception{
		if(null==type || (null!=type&&type.length==0)){
			printRtn(getRequest(), getResponse(),new AjaxRtnBean(false, "请选择订阅邮件类型"));
			return false;
		}
		return true;
	}
	private boolean checkEmail(final EdmSubscribe emailSubscribe,final boolean isCheckExist){
		if(null == emailSubscribe){
			return subValid("A","提供了一个空的Email地址，无法完成邮箱地址验证");
		}
		String email = emailSubscribe.getEmail();
		if (StringUtils.isEmpty(email)) {
			return subValid("A","提供了一个空的Email地址，无法完成邮箱地址验证");
		}
		if(!StringUtil.validEmail(email)){
			return subValid("B",email + "未能通过正则表达式检验，将返回false");
		}
		if(isCheckExist){
			EdmSubscribe edm = edmSubscribeService.searchEmailIsSubscribe(email.toLowerCase());
			if (null != edm) {
				return subValid("C",email + "已订阅邮件，将返回false");
			}
		}
		return true;
	}
	private boolean checkType(final boolean isCheckType){
		if(regEdmType!=null){
			type = regEdmType.split(",");
		}
		if(isCheckType && (null==type || (null!=type && type.length == 0))){
			return subValid("D",email + "没有订阅邮件信息类型，将返回false");
		}
		List<EdmSubscribeInfo> infoList = new ArrayList<EdmSubscribeInfo>();
		for(int i=0;i<type.length;i++){
			EdmSubscribeInfo info = new EdmSubscribeInfo();
			info.setType(type[i]);
			infoList.add(info);
		}
		subscribe.setInfoList(infoList);
		return Boolean.TRUE;
	}
	private boolean subValid(String key,String msg){
		AjaxRtnBean _rtn = new AjaxRtnBean(true, "");
		LOG.debug(msg);
		_rtn.setSuccess(false);
		_rtn.setMessage(key);
		printRtn(getRequest(), getResponse(), _rtn);
		return false;
	}
	/**
	 * 根据订阅ID取得订阅列表
	 * @param edmUserId
	 * @return
	 */
	private List<EdmSubscribeInfo> getEdmSubInfoList(Long edmUserId){
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("isValid", "Y");
		parameters.put("edmUserId", edmUserId);
		List<EdmSubscribeInfo> infoList = edmSubscribeInfoService.searchEdmInfo(parameters);
		return infoList;
	}
	/**
	 *  取得退订信息
	 * @return
	 */
	private EdmSubscribeInfo getRegEdmSubInfo(){
		EdmSubscribeInfo subscribeInfo = new EdmSubscribeInfo();
		subscribeInfo.setEmail(email); //订阅EMAIL
		subscribeInfo.setType(regEdmType);		//订阅类型
		subscribeInfo.setCancelDate(new Date()); //退订日期
		StringBuffer remark = new StringBuffer(""); //退订原因
		putCancelOption();
		if(null!=cancelRemark&&cancelRemark.length>0){
			cancelRemark = cancelRemark[0].split(",");
		}
		for(int i=0;null!=cancelRemark && i<cancelRemark.length;i++){
			remark.append(cancelMap.get(cancelRemark[i]));
			remark.append(";");
		}
		if(!StringUtil.isEmptyString(otherCancelRemark)){
			remark.append(otherCancelRemark);
		}
		subscribeInfo.setCancelRemark(remark.toString());
		return subscribeInfo;
	}
	/**
	 * 设置EMAIL，并且设置订阅类型
	 */
	private void getUserEmail(){
		if(null != email && ((type != null && (type.length == 0 || StringUtil.isEmptyString(type[0]))) || null == type)){
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("isValid", "Y");
			parameters.put("email", email);
			List<EdmSubscribeInfo> infoList = edmSubscribeInfoService.searchEdmInfo(parameters);
			if(null != infoList && infoList.size() > 0){
				type = new String[infoList.size()];
				for(int i=0;i<infoList.size();i++){
					type[i] = infoList.get(i).getType();
				}
			}else{
				type = EDM_SUBSCRIBE_TYPE;
			}
		}
	}
	/**
	 * 输出返回码
	 * @param request 
	 * @param response
	 * @param bean
	 * @throws IOException
	 */
	private void printRtn(HttpServletRequest request, HttpServletResponse response, AjaxRtnBean bean){
		response.setContentType("text/json; charset=UTF-8");
		try{
			if (request.getParameter("jsoncallback") == null) {
				response.getWriter().print(JSONObject.fromObject(bean));
			} else {
				getResponse().getWriter().print(getRequest().getParameter("jsoncallback") + "(" + JSONObject.fromObject(bean) + ")");
			}
		}catch(IOException e){
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
		}
	}
	
	/**
	 * 输出城市列表
	 * @param request 
	 * @param response
	 * @param bean
	 * @throws IOException
	 */
	private void printRtn(HttpServletRequest request, HttpServletResponse response, List<ComCity> bean){
		response.setContentType("text/json; charset=UTF-8");
		StringBuffer sb = new StringBuffer("{list:[");
		for(ComCity city:bean){
			sb.append("{\"cityId\":\""+city.getCityId()+"\",\"cityName\":\""+city.getCityName()+"\"},");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("]}");
		try{
			if (request.getParameter("jsoncallback") == null) {
				response.getWriter().print(sb);
			} else {
				getResponse().getWriter().print(getRequest().getParameter("jsoncallback") + "(" +sb + ")");
			}
		}catch(IOException e){
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
		}
	}
	private void putCancelOption(){
		cancelMap = new HashMap<String,String>();
		cancelMap.put("A", "这个邮箱不方便收驴妈妈邮件");
		cancelMap.put("B", "收到好多邮件，太多了看不过来");
		cancelMap.put("C", "对驴妈妈的产品不敢兴趣");
		cancelMap.put("D", "对旅游不感兴趣，或最近没旅游打算");
		cancelMap.put("E", "老是进垃圾箱，还不如不订了");
		cancelMap.put("F", "邮件没有吸引力");
	}
	private String getIpAddr() {
		String ipAddress =  InternetProtocol.getRemoteAddr(this.getRequest());
		if (ipAddress.equals("127.0.0.1")) {
			// 根据网卡取本机配置的IP
			InetAddress inet = null;
			try {
				inet = InetAddress.getLocalHost();
				ipAddress = inet.getHostAddress();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ipAddress;
	}
	public EdmSubscribeService getEdmSubscribeService() {
		return edmSubscribeService;
	}
	public void setEdmSubscribeService(EdmSubscribeService edmSubscribeService) {
		this.edmSubscribeService = edmSubscribeService;
	}
	public EdmSubscribeInfoService getEdmSubscribeInfoService() {
		return edmSubscribeInfoService;
	}
	public void setEdmSubscribeInfoService(
			EdmSubscribeInfoService edmSubscribeInfoService) {
		this.edmSubscribeInfoService = edmSubscribeInfoService;
	}
	public EdmSubscribe getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(EdmSubscribe subscribe) {
		this.subscribe = subscribe;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOldEmail() {
		return oldEmail;
	}
	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
	}
	public String[] getType() {
		return type;
	}
	public void setType(String[] type) {
		this.type = type;
	}
	public String[] getCancelRemark() {
		return cancelRemark;
	}
	public void setCancelRemark(String[] cancelRemark) {
		this.cancelRemark = cancelRemark;
	}
	public String getOtherCancelRemark() {
		return otherCancelRemark;
	}
	public void setOtherCancelRemark(String otherCancelRemark) {
		this.otherCancelRemark = otherCancelRemark;
	}
	public Map<String, String> getCancelMap() {
		return cancelMap;
	}
	public void setCancelMap(Map<String, String> cancelMap) {
		this.cancelMap = cancelMap;
	}
	public String getRegEdmType() {
		return regEdmType;
	}
	public void setRegEdmType(String regEdmType) {
		this.regEdmType = regEdmType;
	}
	public PlaceCityService getPlaceCityService() {
		return placeCityService;
	}
	public void setPlaceCityService(PlaceCityService placeCityService) {
		this.placeCityService = placeCityService;
	}
	public List<ComProvince> getProvinceList() {
		return provinceList;
	}
	public void setProvinceList(List<ComProvince> provinceList) {
		this.provinceList = provinceList;
	}
	public List<ComCity> getPlaceList(){
		return placeList;
	}
	public List<ComCity> getCityList(){
		return cityList;
	}
	public ComIpsService getComIpsRemoteBean() {
		return comIpsRemoteBean;
	}
	public void setComIpsRemoteBean(ComIpsService comIpsRemoteBean) {
		this.comIpsRemoteBean = comIpsRemoteBean;
	}
}
