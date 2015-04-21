package com.lvmama.front.web.myspace;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

/**
 * “我的驴妈妈”----"个人信息"
 */
@Results({
	@Result(name = "success", location = "/WEB-INF/pages/myspace/sub/personalInformation/index.ftl", type = "freemarker")
})
public class PersonalInformationAction extends SpaceBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -1918718715929807066L;
	
	private UserUserProxy userUserProxy;
	
	private PlaceCityService placeCityService;
	
	private String realName; //真实姓名
	private String gender; //性别
	private String year;  //出生年份
	private String month; //出生月份
	private String day;  //出生日期
	private String cityId; //所在城市
	private String provinceId = ""; //所在省份

	/**
	 * 展示个人信息
	 */
	@Action(value="/myspace/userinfo")
	public String execute(){
		UserUser user = getUser();
		if (null != user) {
			ComCity city = null;
			if (null != user.getCityId()) {
				city = placeCityService.selectCityByPrimaryKey(user.getCityId());
				if (null != city) {
					provinceId = city.getProvinceId();
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 更新个人信息
	 * @return
	 */
	@Action(value="/myspace/updatePersonalInformation")
	public String updatePersonalInformationAction() {
		UserUser user = getUser();
		if (null == user) {
			return ERROR;
		}
		
		if (StringUtils.isNotBlank(realName)) {
			user.setRealName(realName);
		}
		
		if (StringUtils.isNotBlank(gender) 
				&& ("M".equals(gender) || "F".equals(gender))) {
			user.setGender(gender);
		}
		
		if (StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month) && StringUtils.isNotBlank(day)) {
			try {
				user.setBirthday(DateUtil.stringToDate(year + "-" + month + "-" + day,"yyyy-MM-dd"));
			} catch (Exception e) {
				
			}
		}
		
		if (StringUtils.isNotBlank(cityId)) {
			user.setCityId(cityId);
		}
		userUserProxy.update(user);
		putSession(Constant.SESSION_FRONT_USER, user);
		return execute();
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getYear() {
		if (null != getUser() && null != getUser().getBirthday()) {
			return DateUtil.getFormatDate(getUser().getBirthday(), "yyyy");
		}
		return null;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		if (null != getUser() && null != getUser().getBirthday()) {
			return DateUtil.getFormatDate(getUser().getBirthday(), "MM");
		}
		return null;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		if (null != getUser() && null != getUser().getBirthday()) {
			return DateUtil.getFormatDate(getUser().getBirthday(), "dd");
		}
		return null;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setPlaceCityService(PlaceCityService placeCityService) {
		this.placeCityService = placeCityService;
	}

}
