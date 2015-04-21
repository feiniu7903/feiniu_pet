package com.lvmama.pet.web.place.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lvmama.comm.pet.vo.Weather;


public class WeatherUtil {
	private static final Log LOG = LogFactory.getLog(WeatherUtil.class);
	
	// http://www.google.com/ig/api?hl=zh-cn&weather=,,,31290965,120612974
	private static String googleUrl = "http://www.google.com/ig/api?hl=zh-cn&weather=,,,";

	public List<Weather> getWeather(Long placeId, String latitude,
			String longitude) {
		try {
			StringBuffer sb = this.getGoogleWeather(latitude, longitude);
			Document doc = DocumentHelper.parseText(sb.toString()
					.replace("&nbsp;", "").replace("锛�", ")"));
			Element root = doc.getRootElement();
			Element weather = root.element("weather");
			int i = 1;
			List<Weather> weatherList = new ArrayList<Weather>();
			for (Iterator iter = weather.elementIterator("forecast_conditions"); iter
					.hasNext();) {
				Weather w = new Weather();
				Element forecast_conditions = (Element) iter.next();
				w.setPlaceId(placeId);
				w.setDayOfWeek(forecast_conditions.element("day_of_week")
						.attributeValue("data"));
				w.setLow(forecast_conditions.element("low").attributeValue(
						"data"));
				w.setHigh(forecast_conditions.element("high").attributeValue(
						"data"));
				w.setWeatherImage(forecast_conditions.element("icon")
						.attributeValue("data"));
				w.setCondition(forecast_conditions.element("condition")
						.attributeValue("data"));
				w.setSeq(i);
				weatherList.add(w);
				i++;
			}
			return weatherList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private StringBuffer getGoogleWeather(String latitude, String longitude) {
		try {
			String strUrl = googleUrl + latitude + "," + longitude;
			URL url = new URL(strUrl);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					url.openStream(), "gb2312"));
			String s = "";
			StringBuffer sb = new StringBuffer("");
			while ((s = br.readLine()) != null) {
				sb.append(s + "\r\n");
			}
			br.close();
			return sb;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取天气 error：latitude:" + latitude + "longitude:" + longitude);
		}
		return null;
	}

	/**
	 * 根据省份取相应的天气预报
	 * 
	 * @return
	 */
	public static List<WeatherInfoDaily> getWeather(String cityName) {
		String cityCode  = WeatherCityMap.getWeatherCityId(cityName);
		if (cityCode == null) {
			return null;
		}

		try {
			String strUrl = "http://m.weather.com.cn/data/" + cityCode + ".html";
			URL url = new URL(strUrl);
			URLConnection con = url.openConnection();
			con.setConnectTimeout(5000);
			con.setReadTimeout(10000);
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String s = "";
			StringBuffer sb = new StringBuffer("");
			while ((s = br.readLine()) != null) {
				sb.append(s + "\r\n");
			}
			br.close();

			JSONObject json = JSONObject.fromObject(sb.toString());
			JSONObject result = json.getJSONObject("weatherinfo");
			WeatherInfo weatherInfo = (WeatherInfo) JSONObject.toBean(result, WeatherInfo.class);
			
			return getWeeklyWeatherList(weatherInfo);
		} catch (Exception e) {
			e.printStackTrace();
			if (LOG.isInfoEnabled()) {
				LOG.info(e.getMessage());
			}
		}
		return null;
	}
	
	private static List<WeatherInfoDaily> getWeeklyWeatherList(WeatherInfo weatherInfo) {
		List<WeatherInfoDaily> days = new ArrayList<WeatherInfoDaily>();
		int weekNum = getWeekNum(weatherInfo.getWeek());
		String date = weatherInfo.getDate_y();
		
		WeatherInfoDaily day1 = new WeatherInfoDaily();
		day1.setCity(weatherInfo.getCity());
		day1.setCityId(weatherInfo.getCityid());
		day1.setImgDay(weatherInfo.getImg1());
		day1.setImgNight(weatherInfo.getImg2());
		day1.setImgTitleDay(weatherInfo.getImg_title1());
		day1.setImgTitleNight(weatherInfo.getImg_title2());
		day1.setTemp(weatherInfo.getTemp1());
		day1.setWeather(weatherInfo.getWeather1());
		day1.setWind(weatherInfo.getWind1());
		day1.setWeek(getWeekZh(weekNum));
		day1.setDate(getStrDate(date, 0, true));
		days.add(day1);
		
		WeatherInfoDaily day2 = new WeatherInfoDaily();
		day2.setCity(weatherInfo.getCity());
		day2.setCityId(weatherInfo.getCityid());
		day2.setImgDay(weatherInfo.getImg3());
		day2.setImgNight(weatherInfo.getImg4());
		day2.setImgTitleDay(weatherInfo.getImg_title3());
		day2.setImgTitleNight(weatherInfo.getImg_title4());
		day2.setTemp(weatherInfo.getTemp2());
		day2.setWeather(weatherInfo.getWeather2());
		day2.setWind(weatherInfo.getWind2());
		day2.setWeek(getWeekZh(weekNum + 1));
		day2.setDate(getStrDate(date, 1, false));
		days.add(day2);
		
		WeatherInfoDaily day3 = new WeatherInfoDaily();
		day3.setCity(weatherInfo.getCity());
		day3.setCityId(weatherInfo.getCityid());
		day3.setImgDay(weatherInfo.getImg5());
		day3.setImgNight(weatherInfo.getImg6());
		day3.setImgTitleDay(weatherInfo.getImg_title5());
		day3.setImgTitleNight(weatherInfo.getImg_title6());
		day3.setTemp(weatherInfo.getTemp3());
		day3.setWeather(weatherInfo.getWeather3());
		day3.setWind(weatherInfo.getWind3());
		day3.setWeek(getWeekZh(weekNum + 2));
		day3.setDate(getStrDate(date, 2, false));
		days.add(day3);
		
		WeatherInfoDaily day4 = new WeatherInfoDaily();
		day4.setCity(weatherInfo.getCity());
		day4.setCityId(weatherInfo.getCityid());
		day4.setImgDay(weatherInfo.getImg7());
		day4.setImgNight(weatherInfo.getImg8());
		day4.setImgTitleDay(weatherInfo.getImg_title7());
		day4.setImgTitleNight(weatherInfo.getImg_title8());
		day4.setTemp(weatherInfo.getTemp4());
		day4.setWeather(weatherInfo.getWeather4());
		day4.setWind(weatherInfo.getWind4());
		day4.setWeek(getWeekZh(weekNum + 3));
		day4.setDate(getStrDate(date, 3, false));
		days.add(day4);
		
		WeatherInfoDaily day5 = new WeatherInfoDaily();
		day5.setCity(weatherInfo.getCity());
		day5.setCityId(weatherInfo.getCityid());
		day5.setImgDay(weatherInfo.getImg9());
		day5.setImgNight(weatherInfo.getImg10());
		day5.setImgTitleDay(weatherInfo.getImg_title9());
		day5.setImgTitleNight(weatherInfo.getImg_title10());
		day5.setTemp(weatherInfo.getTemp5());
		day5.setWeather(weatherInfo.getWeather5());
		day5.setWind(weatherInfo.getWind5());
		day5.setWeek(getWeekZh(weekNum + 4));
		day5.setDate(getStrDate(date, 4, false));
		days.add(day5);
		
		WeatherInfoDaily day6 = new WeatherInfoDaily();
		day6.setCity(weatherInfo.getCity());
		day6.setCityId(weatherInfo.getCityid());
		day6.setImgDay(weatherInfo.getImg11());
		day6.setImgNight(weatherInfo.getImg12());
		day6.setImgTitleDay(weatherInfo.getImg_title11());
		day6.setImgTitleNight(weatherInfo.getImg_title12());
		day6.setTemp(weatherInfo.getTemp6());
		day6.setWeather(weatherInfo.getWeather6());
		day6.setWind(weatherInfo.getWind6());
		day6.setWeek(getWeekZh(weekNum + 5));
		day6.setDate(getStrDate(date, 5, false));
		days.add(day6);
		
		return days;
	}

	public static String getWeekZh(int weekNum) {
		if (weekNum > 7) {
			weekNum = weekNum - 7;
		}
		switch(weekNum) {
			case 1:
				return "星期日";
			case 2:
				return "星期一";
			case 3:
				return "星期二";
			case 4:
				return "星期三";
			case 5:
				return "星期四";
			case 6:
				return "星期五";
			case 7:
				return "星期六";
		}
		return "";
	}
	
	public static int getWeekNum(String weekZh) {
		if (weekZh.indexOf("日") > 0) {
			return 1;
		}
		if (weekZh.indexOf("一") > 0) {
			return 2;
		}
		if (weekZh.indexOf("二") > 0) {
			return 3;
		}
		if (weekZh.indexOf("三") > 0) {
			return 4;
		}
		if (weekZh.indexOf("四") > 0) {
			return 5;
		}
		if (weekZh.indexOf("五") > 0) {
			return 6;
		}
		if (weekZh.indexOf("六") > 0) {
			return 7;
		}
		return 0;
	}
	
	public static String getStrDate(String curDate, int days, boolean showYear) {
		int y = Integer.parseInt(curDate.substring(0, curDate.indexOf("年")));
		int m = Integer.parseInt(curDate.substring(curDate.indexOf("年") + 1, curDate.indexOf("月"))) - 1;
		int d = Integer.parseInt(curDate.substring(curDate.indexOf("月") + 1, curDate.indexOf("日")));
		Calendar c = Calendar.getInstance();
		c.set(y, m, d);
		c.add(Calendar.DATE, days);
		if (!showYear) {
			return (c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DAY_OF_MONTH) + "日";
		}
		return c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DAY_OF_MONTH) + "日";
	}
}
