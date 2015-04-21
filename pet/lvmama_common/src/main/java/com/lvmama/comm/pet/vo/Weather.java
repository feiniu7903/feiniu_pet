package com.lvmama.comm.pet.vo;
 
import java.io.Serializable;
/**
 * @author yuzhibing
 * @author qiuguobin
 *
<?xml version="1.0"?>
<xml_api_reply version="1">
	<weather module_id="0" tab_id="0">
		<forecast_information>
			<city data=""/>
			<postal_code data=""/>
			<latitude_e6 data="31290965"/>
			<longitude_e6 data="120612974"/>
			<forecast_date data="2008-10-09"/>
			<current_date_time data="2008-10-10 00:00:00 +0000"/>
			<unit_system data="SI"/>
		</forecast_information>
		<current_conditions>
			<condition data="晴"/>
			<temp_f data="75"/>
			<temp_c data="24"/>
			<humidity data="湿度： 73%"/>
			<icon data="/images/weather/sunny.gif"/>
			<wind_condition data="&nbsp; 风向: 东、风速:18 (公里/小时）"/>
		</current_conditions>
		<forecast_conditions>
			<day_of_week data="今天"/>
			<low data="21"/>
			<high data="25"/>
			<icon data="/images/weather/chance_of_rain.gif"/>
			<condition data="可能有雨"/>
		</forecast_conditions>
		<forecast_conditions>
			<day_of_week data="周五"/>
			<low data="20"/>
			<high data="23"/>
			<icon data="/images/weather/mostly_sunny.gif"/>
			<condition data="晴间多云"/>
		</forecast_conditions>
		<forecast_conditions>
			<day_of_week data="周六"/>
			<low data="16"/>
			<high data="21"/>
			<icon data="/images/weather/mostly_sunny.gif"/>
			<condition data="以晴为主"/>
		</forecast_conditions>
		<forecast_conditions>
			<day_of_week data="周日"/>
			<low data="15"/>
			<high data="22"/>
			<icon data="/images/weather/sunny.gif"/>
			<condition data="晴"/>
		</forecast_conditions>
	</weather>
</xml_api_reply>
 */
public class Weather implements Serializable {
	private Long weatherId;
	private Long placeId;
	private String condition;
	private String weatherImage;
	private String dayOfWeek;
	private String low;
	private String high;
	private Integer seq;
	private Integer numericDayOfWeek;
	
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	
	public Long getPlaceId() {
		return placeId;
	}
	
	public Long getWeatherId() {
		return weatherId;
	}
	
	public void setWeatherId(Long weatherId) {
		this.weatherId = weatherId;
	}
	
	public String getCondition() {
		return condition;
	}
	
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	public String getWeatherImage() {
		return weatherImage;
	}
	
	public void setWeatherImage(String weatherImage) {
		this.weatherImage = weatherImage;
	}
	
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	
	public String getLow() {
		return low;
	}
	
	public void setLow(String low) {
		this.low = low;
	}
	
	public String getHigh() {
		return high;
	}
	
	public void setHigh(String high) {
		this.high = high;
	}
	
	public Integer getSeq() {
		return seq;
	}
	
	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getNumericDayOfWeek() {
		return numericDayOfWeek;
	}

	public void setNumericDayOfWeek(Integer numericDayOfWeek) {
		this.numericDayOfWeek = numericDayOfWeek;
	}

	@Override
	public String toString() {
		return "Weather [condition=" + condition + ", dayOfWeek=" + dayOfWeek + ", high=" + high + ", low=" + low + ", numericDayOfWeek="
				+ numericDayOfWeek + ", placeId=" + placeId + ", seq=" + seq + ", weatherId=" + weatherId + ", weatherImage=" + weatherImage + "]";
	}
}
