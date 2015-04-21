package com.lvmama.pet.web.place.weather;

import java.io.Serializable;

public class WeatherInfoDaily implements Serializable {
	private static final long serialVersionUID = -5824887407692051272L;
	private static final String IMG_BASE_PATH = "http://www.weather.com.cn/m2/i/icon_weather/29x20/";
	
	private String city;
	private String cityId;
	private String week;
	private String date;
	
	private String imgDay;
	private String imgNight;
	
	private String imgTitleDay;
	private String imgTitleNight;
	
	private String imgPathDay;
	private String imgPathNight;
	
	private String weather;
	private String temp;
	private String wind;

	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getImgDay() {
		return imgDay;
	}

	public void setImgDay(String imgDay) {
		this.imgDay = imgDay;
	}

	public String getImgNight() {
		return imgNight;
	}

	public void setImgNight(String imgNight) {
		this.imgNight = imgNight;
	}

	public String getImgTitleDay() {
		return imgTitleDay;
	}

	public void setImgTitleDay(String imgTitleDay) {
		this.imgTitleDay = imgTitleDay;
	}

	public String getImgTitleNight() {
		return imgTitleNight;
	}

	public void setImgTitleNight(String imgTitleNight) {
		this.imgTitleNight = imgTitleNight;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public String getImgPathDay() {
		this.imgPathDay = IMG_BASE_PATH + "d";
		if (imgDay.length() == 1) {
			this.imgPathDay = this.imgPathDay + "0";
		}
		this.imgPathDay = this.imgPathDay + imgDay + ".gif";
		return imgPathDay;
	}
	
	public String getImgPathNight() {
		this.imgPathNight = IMG_BASE_PATH + "n";
		if ("99".equals(imgNight)) {
			if (imgDay.length() == 1) {
				this.imgPathNight = this.imgPathNight + "0";
			}
			this.imgPathNight = this.imgPathNight + imgDay + ".gif";
		} else {
			if (imgNight.length() == 1) {
				this.imgPathNight = this.imgPathNight + "0";
			}
			this.imgPathNight = this.imgPathNight + imgNight + ".gif";
		}
		return imgPathNight;
	}
}
