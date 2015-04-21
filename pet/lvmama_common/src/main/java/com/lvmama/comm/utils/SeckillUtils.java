package com.lvmama.comm.utils;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

	public class SeckillUtils<T> {
		private final static int THIRTY_MINUTES=30*60*1000;
		/**
	     * 根据集合对象指定字段对集合进行排序
	     * @param list
	     * @param method <指定字段的get方法，如getStartTime>
	     * @param reverseFlag 倒序
	     * @author liudong
	     */
		public  void sortByMethod(List<T> list,final String method,final boolean reverseFlag){
		
			Collections.sort(list,new Comparator<T>(){
				@Override
				public int compare(T arg0, T arg1) {
					int result =0;
					try {
						Method m0 = arg0.getClass().getMethod(method, null);
						Method m1 = arg1.getClass().getMethod(method, null);
						Object obj0 = m0.invoke(((T)arg0),null);
						Object obj1 = m1.invoke(((T)arg1),null);
						//根据date类型比较
						if(obj1 instanceof Date){
							long flag = ((Date) obj0).getTime()-((Date) obj1).getTime();
							if(flag>0){
								result= 1;
							}else if(flag<0){
								result= -1;
							}else{
								result= 0;
							}
						}
						if(reverseFlag){
							result = -result;
						}
					} catch (Exception e) {
						
					}
					return result;
				}
			});
		
		}
		
		/**
		 * 比较相差相差时间，nextDate-prevDate>THIRTY_MINUTES
		 * @param prevDate 
		 * @param nextDate
		 * @return 
		 * @author liudong
		 */
		public static boolean compare2Time(Date prevDate,Date nextDate){
			if((nextDate.getTime()-prevDate.getTime())>=THIRTY_MINUTES){
				return true;
			}
			return false;
		}
		
		
		public static Date toDate(String sdate, String fmString) {
			DateFormat df = new SimpleDateFormat(fmString);
			try {
				return df.parse(sdate);
			} catch (ParseException e) {
				throw new RuntimeException("日期格式不正确 ");
			}
		}
		
		public static void main(String[] args) {
			for (int i = 0; i <3; i++) {
				
			}
		}
}
