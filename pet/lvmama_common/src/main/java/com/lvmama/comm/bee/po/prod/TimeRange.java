/**
 * 
 */
package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.Assert;

/**
 * @author yangbin
 *
 */
public class TimeRange implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6015167975931782049L;
	private Date start;
	private Date end;
	
	
	public TimeRange(Date start, Date end) {
		super();
		this.start = start;
		this.end = end;
	}
	
	TimeRange(){
		super();
	}
	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}
	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}
	
	public static class TimeRangePropertEditor extends java.beans.PropertyEditorSupport{

		
		/* (non-Javadoc)
		 * @see java.beans.PropertyEditorSupport#getAsText()
		 */
		@Override
		public String getAsText() {
			TimeRange timeRange=(TimeRange)super.getValue();
			long start=timeRange.getStart().getTime();
			long end=timeRange.getEnd().getTime();			
			return new StringBuffer().append(start).append("-").append(end).toString();
		}

		/* (non-Javadoc)
		 * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
		 */
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			Assert.hasText(text);
			String arr[]=StringUtils.split(text,"-");
			if(arr.length!=2){
				throw new IllegalArgumentException("input txt format error.");
			}
			Date start=new Date(NumberUtils.toLong(arr[0]));
			Date end=new Date(NumberUtils.toLong(arr[1]));
			
			setValue(new TimeRange(start,end));			
		}

		
	}
	
	
}
