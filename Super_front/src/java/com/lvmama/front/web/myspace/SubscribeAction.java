package com.lvmama.front.web.myspace;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.client.EmailClient;
import com.lvmama.comm.pet.po.edm.EdmSubscribe;
import com.lvmama.comm.pet.po.edm.EdmSubscribeInfo;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.edm.EdmSubscribeInfoService;
import com.lvmama.comm.pet.service.edm.EdmSubscribeService;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.front.web.ajax.AjaxRtnBean;
/**
 * 
 * @author shangzhengyuan
 * @description 邮件订阅Action
 */
@Results({
	@Result(name = "success", location = "/WEB-INF/pages/myspace/sub/subscribe.ftl", type = "freemarker")
})
public class SubscribeAction extends SpaceBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(SubscribeAction.class);
	/**
	 * 订阅邮件服务
	 */
	private EdmSubscribeService edmSubscribeService;
	
	private EdmSubscribeInfoService  edmSubscribeInfoService;
	
	private PlaceCityService placeCityService;
	
	private EdmSubscribe edmSubscribe;
	
	private EdmSubscribeInfo edmSubscribeInfo;
	
	private EmailClient emailClient;
	
	private String email;
	private String emailValidCode;
	private String edmUserId;
	
	/**
	 * 类型
	 */
	private String regEdmType;
	
	private List<ComProvince> provinceList;
	
	private List<ComCity> placeList = new ArrayList<ComCity>();
	
	private List<ComCity> cityList = new ArrayList<ComCity>();
	
	@Action("/myspace/userinfo/email")
	public String execute(){
		UserUser user= getUser();
		if(!isLogin()){
			return LOGIN;
		}
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("userId", getUserId());
		edmSubscribe = edmSubscribeService.searchSubscribe(parameters);
		if(null==edmSubscribe && null != user && !StringUtil.isEmptyString(user.getEmail())){
			parameters.clear();
			parameters.put("email", user.getEmail());
			edmSubscribe = edmSubscribeService.searchSubscribe(parameters);
		}
		if(null==edmSubscribe && null != user){
			edmSubscribe = new EdmSubscribe();
			edmSubscribe.setEmail(user.getEmail());
			edmSubscribe.setCity(user.getCityId());
		}else{
			edmSubscribe.setUserId(getUserId());
			if(!StringUtil.isEmptyString(edmSubscribe.getCity())){
				cityList = getComCityList(edmSubscribe.getProvince(),edmSubscribe.getCity());
			}
			String mustWantedTravel = edmSubscribe.getMustWantedTravel();
			LOG.info("mustWantedTravel="+mustWantedTravel);
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
		}
		provinceList = placeCityService.getProvinceList();
		for(ComProvince province: provinceList){
			if(null != province.getProvinceId() && province.getProvinceId().equals(edmSubscribe.getProvince())){
				province.setChecked("selected");
			}
		}
		return SUCCESS;
	}
	/**
	 * 订阅邮箱
	 * @throws Exception
	 */
	@Action("/myspace/ajax/subscribeEmail")
	public void subscribeEmail(){
		if(!isLogin()){
			sendAjaxMsg(new AjaxRtnBean(Boolean.FALSE, "您未登录，请重新登录后再试"));
			return ;
		}
		if(null == edmSubscribe){
			sendAjaxMsg(new AjaxRtnBean(Boolean.FALSE, "订阅信息不存在"));
			return;
		}
		if(!StringUtil.validEmail(edmSubscribe.getEmail())){
			sendAjaxMsg(new AjaxRtnBean(Boolean.FALSE, "您输入的邮箱不是有效的邮箱"));
			return;
		}
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("email", edmSubscribe.getEmail());
		EdmSubscribe edm = edmSubscribeService.searchSubscribe(parameters);
		String userId = getUser().getUserNo();
		if(null!=edm && !StringUtil.isEmptyString(getUser().getEmail()) && !getUser().getEmail().equalsIgnoreCase(edm.getEmail()) ){
			sendAjaxMsg(new AjaxRtnBean(Boolean.FALSE, "邮箱已存在，请输入其它有效邮箱"));
			return;
		}else{
			if(null == edm){
				parameters.clear();
				parameters.put("userId", userId);
				edm = edmSubscribeService.searchSubscribe(parameters);
			}
			edmSubscribe.setUserId(userId);
			if(null == edm){
				edmSubscribe = edmSubscribeService.insert(edmSubscribe);
			}else{
				edmSubscribe.setId(edm.getId());
				LOG.info("mustWantedTravel="+edmSubscribe.getMustWantedTravel());
				edmSubscribeService.update(edmSubscribe);
				
			}
			if(null != edmSubscribe && null != edmSubscribe.getId()){
				sendAjaxMsg(new AjaxRtnBean(Boolean.TRUE, edmSubscribe.getId()+""));
				return;
			}
		}
		sendAjaxMsg(new AjaxRtnBean(Boolean.FALSE, "订阅邮件保存未成功"));
	}
	/**
	 * 发送验证邮件
	 */
	@Action("/myspace/ajax/sendValidateEmail")
	public void sendValidateEmail(){
		if(!StringUtil.validEmail(email)){
			sendAjaxMsg(new AjaxRtnBean(Boolean.FALSE, "您输入的邮箱不是有效的邮箱"));
			return;
		}
		String validCode = validCode(email);
		if(StringUtil.isEmptyString(validCode)){
			sendAjaxMsg(new AjaxRtnBean(Boolean.FALSE,  "发送验证邮件出错"));
			return;
		}
		EmailContent emailContent =new EmailContent();
		emailContent.setSubject("来自驴妈妈旅游网的邮件");
		emailContent.setContentText("http://www.lvmama.com/edm/validEmail/"+edmUserId+"/"+validCode);
		try{
			edmSubscribe = edmSubscribeService.insert(edmSubscribe);
			emailClient.sendEmailDirect(emailContent);
			sendAjaxMsg(new AjaxRtnBean(Boolean.TRUE,  "发送验证邮件成功"));
		}catch(Exception e){
			LOG.warn("发送订阅邮箱验证邮件失败：\r\n"+e);
			sendAjaxMsg(new AjaxRtnBean(Boolean.FALSE,  "发送验证邮件出错"));
		}
	}
	@Action("/myspace/edm/validEmail")
	public String validEmail(){
		try{
			if(StringUtil.isEmptyString(emailValidCode) || StringUtil.isEmptyString(edmUserId)){
				LOG.info("用户验证邮箱是否有效失败 emailValidCode="+emailValidCode+" edmUserId="+edmUserId);
				return ERROR;
			}
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("id", edmUserId);
			edmSubscribe = edmSubscribeService.searchSubscribe(parameters);
			if(null==edmSubscribe){
				LOG.info("用户验证邮箱是否有效失败 emailValidCode="+emailValidCode+" edmUserId="+edmUserId);
				return ERROR;
			}
			String email = edmSubscribe.getEmail();
			String validCode = validCode(email);
			if(StringUtil.isEmptyString(validCode)){
				LOG.info("用户验证邮箱是否有效失败 emailValidCode="+emailValidCode+" edmUserId="+edmUserId +" validCode="+validCode);
				return ERROR;
			}
			if(validCode.equals(emailValidCode)){
				edmSubscribeService.update(edmSubscribe);
			}
			return SUCCESS;
		}catch(Exception e){
			LOG.warn("用户验证邮箱是否有效失败:\r\n"+e);
			return ERROR;
		}
	}
	/**
	 * 订阅邮件
	 */
	@Action("/myspace/ajax/subscribeMail")
	public void subscribeMail(){
		if(null == edmSubscribeInfo || (null != edmSubscribeInfo && null == edmSubscribeInfo.getEdmUserId())){
			sendAjaxMsg(new AjaxRtnBean(Boolean.FALSE, "订立邮箱信息不存在，请输入邮箱信息"));
			return;
		}
		try{
			Map<String, Object> params = new HashMap<String,Object>();
			params.put("isValid", "Y");
			params.put("type", edmSubscribeInfo.getType());
			params.put("edmUserId", edmSubscribeInfo.getEdmUserId());
			List<EdmSubscribeInfo> info = edmSubscribeInfoService.query(params);
			if(null !=info && info.size()>0){
				sendAjaxMsg(new AjaxRtnBean(Boolean.TRUE, "邮件订阅成功"));
				return;
			}
			edmSubscribeInfo = edmSubscribeInfoService.insert(edmSubscribeInfo);
			sendAjaxMsg(new AjaxRtnBean(Boolean.TRUE, "邮件订阅成功"));
		}catch(Exception e){
			LOG.warn("用户订阅邮件失败：\r\n"+e);
			sendAjaxMsg(new AjaxRtnBean(Boolean.FALSE, "很抱歉，您订阅邮件失败"));
		}
	}
	/**
	 * 退订邮件
	 */
	@Action("/myspace/ajax/cancelSubscribe")
	public void cancelSubscribe(){
		try{
			edmSubscribeInfoService.update(edmSubscribeInfo);
			sendAjaxMsg(new AjaxRtnBean(Boolean.TRUE, "退订成功"));
		}catch(Exception e){
			LOG.warn("用户退订邮箱失败:"+e);
			sendAjaxMsg(new AjaxRtnBean(Boolean.FALSE,"很抱歉，退订失败"));
		}
	}
	
	private String validCode(final String str){
		MD5 md5 = new MD5();
		try {
			return md5.code(str.toUpperCase());
		} catch (NoSuchAlgorithmException e) {
			LOG.warn("验证用户订阅邮箱MD5加密失败：\r\n"+e);
			return null;
		}
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
	private List<ComCity> getComCityList(final String provinceId){
		List<ComCity> cityList = (List<ComCity>)MemcachedUtil.getInstance().get("COM_CITY_BY_PROVINCEID_"+provinceId);
		if(null == cityList){
		   cityList = placeCityService.getCityListByProvinceId(provinceId);
		   MemcachedUtil.getInstance().set("COM_CITY_BY_PROVINCEID_"+provinceId,60*60*24*30, cityList);
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
	public EdmSubscribe getEdmSubscribe() {
		return edmSubscribe;
	}
	public void setEdmSubscribe(EdmSubscribe edmSubscribe) {
		this.edmSubscribe = edmSubscribe;
	}
	public EdmSubscribeInfo getEdmSubscribeInfo() {
		return edmSubscribeInfo;
	}
	public void setEdmSubscribeInfo(EdmSubscribeInfo edmSubscribeInfo) {
		this.edmSubscribeInfo = edmSubscribeInfo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmailValidCode() {
		return emailValidCode;
	}
	public void setEmailValidCode(String emailValidCode) {
		this.emailValidCode = emailValidCode;
	}
	public String getEdmUserId() {
		return edmUserId;
	}
	public void setEdmUserId(String edmUserId) {
		this.edmUserId = edmUserId;
	}
	public void setEdmSubscribeService(EdmSubscribeService edmSubscribeService) {
		this.edmSubscribeService = edmSubscribeService;
	}
	public void setEdmSubscribeInfoService(
			EdmSubscribeInfoService edmSubscribeInfoService) {
		this.edmSubscribeInfoService = edmSubscribeInfoService;
	}
	public boolean getContentFooter() {
		return Boolean.TRUE;
	}
	public void setEmailClient(EmailClient emailClient) {
		this.emailClient = emailClient;
	}
	public PlaceCityService getPlaceCityService() {
		return placeCityService;
	}
	public void setPlaceCityService(PlaceCityService placeCityService) {
		this.placeCityService = placeCityService;
	}
	public String getRegEdmType() {
		return regEdmType;
	}
	public void setRegEdmType(String regEdmType) {
		this.regEdmType = regEdmType;
	}
	public List<ComProvince> getProvinceList() {
		return provinceList;
	}
	public void setProvinceList(List<ComProvince> provinceList) {
		this.provinceList = provinceList;
	}
	public List<ComCity> getPlaceList() {
		return placeList;
	}
	public void setPlaceList(List<ComCity> placeList) {
		this.placeList = placeList;
	}
	public List<ComCity> getCityList() {
		return cityList;
	}
	public void setCityList(List<ComCity> cityList) {
		this.cityList = cityList;
	}
}
