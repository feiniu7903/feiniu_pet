
/**
 * 
 */
package com.lvmama.comm.utils.ord;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.Assert;

import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.vo.Constant;

/**
 * @author yangbin
 *
 */
public abstract class TimePriceUtil{



	/**
	 * 计算销售时间价格实际价
	 * @param prodTimePrice
	 * @param metaTimePrice
	 * @return 返回计算后的销售时间价格
	 */
	public static TimePrice calcPrice(TimePrice prodTimePrice, TimePrice metaTimePrice){
		Assert.hasText(prodTimePrice.getPriceType());
		if(Constant.PRICE_TYPE.RATE_PRICE.name().equals(prodTimePrice.getPriceType())){
			prodTimePrice.setPrice(metaTimePrice.getSettlementPrice()+metaTimePrice.getSettlementPrice()*prodTimePrice.getRatePrice()/100);//实际为xx/100
			//if(prodTimePrice.getPrice()%100!=0){//全部改成尾数是9
			prodTimePrice.setPrice(conver9(prodTimePrice.getPrice()));
			//}
			prodTimePrice.setFixedAddPrice(null);
		}else if(Constant.PRICE_TYPE.FIXED_ADD_PRICE.name().equals(prodTimePrice.getPriceType())){
			prodTimePrice.setPrice(metaTimePrice.getSettlementPrice()+prodTimePrice.getFixedAddPrice());			
			prodTimePrice.setRatePrice(null);
		}else{
			prodTimePrice.setRatePrice(null);
			prodTimePrice.setFixedAddPrice(null);
		}
		prodTimePrice.setAheadHour(metaTimePrice.getAheadHour());
		prodTimePrice.setCancelHour(metaTimePrice.getCancelHour());
		prodTimePrice.setCancelStrategy(metaTimePrice.getCancelStrategy());
		return prodTimePrice;
	}
	
	/**
	 * 
	 * @param timePrice
	 * @return true时间价格表为非必须使用预售权，否则反之;
	 */
	public static boolean checkLastCancel(TimePrice timePrice){
		boolean flag=true;
		if(timePrice.isAble()) {
			if(timePrice.getCancelHour()!=null){
				Date tmp=DateUtils.addMinutes(timePrice.getSpecDate(), -(timePrice.getCancelHour().intValue()+60));
				if(tmp.before(new Date())){
					return false;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 数字转900结尾
	 * @param x
	 * @return
	 */
	public static long conver9(long x) {
		return mul1000((long)div1000(x)+1)-100;
	}
	
	/**
	 * 数字转以100为单位的取整
	 * @param x
	 * @return
	 */
	public static long conver0(final long x){
		final long tmp=x%100;
		long tmp_x=x;
		if(tmp>0){
			tmp_x=x-tmp;
		}
		return tmp_x;
	}
	
	
	
	/**
	 * 除以1000
	 * @param x
	 * @return
	 */
	static float div1000(long x){
		BigDecimal p = new BigDecimal(x);
		return p.divide(new BigDecimal(1000),3,BigDecimal.ROUND_UP).floatValue();
	}
	
	/**
	 * 乘以1000
	 * @param x
	 * @return
	 */
	static long mul1000(long x){
		return x*1000;
	}
	
	
	/**
	 * 转换一个日期的时间,转换成 日期+时间的方式 
	 * @param date
	 * @param timeStr
	 * @return
	 */
	public static Date converTime(Date date,String timeStr){
		if(StringUtils.isEmpty(timeStr)||!timeStr.matches("\\d{1,2}:\\d{1,2}")){
			return null;//如果timeStr不正确的情况下返回date值
		}
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		String[] arr=timeStr.split(":");
		
		c.set(Calendar.HOUR_OF_DAY, NumberUtils.toInt(arr[0]));
		c.set(Calendar.MINUTE, NumberUtils.toInt(arr[1]));
		c.set(Calendar.SECOND,0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	
	/**
	 * 将hh:mm转换成整型，方法后面的范围取值
	 * @param time
	 * @return
	 */
	public static long converHHmm(String time){
		String array[]=time.split(":");
		if(array.length==2){
			StringBuffer sb =new StringBuffer();
			sb.append(array[0]);
			if(array[1].length()==1){
				sb.append("0");
			}
			sb.append(array[1]);
			return NumberUtils.toLong(sb.toString());
		}
		return -1;
	}
	public static String formatTime(long time){
		if(time<1){
			return "-";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(time/100);
		sb.append(":");
		long t=time%100;
		if(t<10){
			sb.append("0");
		}
		sb.append(t);
		return sb.toString();
	}
	
	/**
	 * 
	 * @param time
	 * @return
	 */
	public static String formatSec(long takeTime){
		long tmp=takeTime;
		long day=0;
		long hour=0;
		
		if(tmp>=24*60){
			day=tmp/(24*60);
			tmp = tmp-day*24*60;
		}
		if(tmp>=60){
			hour = tmp/60;
			tmp=tmp-hour*60;
		}
		StringBuffer sb = new StringBuffer();
		if(day>0){
			sb.append(day);
			sb.append("天");
		}
		if(hour>0){
			sb.append(hour);
			sb.append("时");
		}
		
		if(tmp>0){
			sb.append(tmp);
			sb.append("分");
		}
		return sb.toString();
	}
	/**
	 * 火车票禁售时间
	 * @return
	 */
	public static boolean hasTrainSoldout(){
		boolean flag=false;
		Calendar c = Calendar.getInstance();
		int hour= c.get(Calendar.HOUR_OF_DAY);
		
		if(hour<7||hour>21){
			flag=true;
		}
		if(hour==21){
			int min=c.get(Calendar.MINUTE);
			if(min>30){
				flag=true;
			}
		}
		return flag;
	}
	
	public static long getLongTime(String str){
		if(StringUtils.isEmpty(str)){
			return 0L;
		}
		if(str.startsWith("--")){
			return -1;
		}
		return NumberUtils.toLong(str.replace(":", ""));
	}
	
}
