package com.lvmama.operate.job.model;

import java.util.Calendar;
import java.util.Date;

import com.lvmama.operate.job.util.DateUtil;

public class TaskSendTime {

	/**
	 * 发送时间
	 */
	private String sendDate;

	/**
	 * 发送
	 */
	private String sendTime;

	/**
	 * 发送频率
	 */
	private TaskSendCycle taskSendCycle;

	/**
	 * 当前时间
	 */
	private Date currentDate;
	/**
	 * 本次应执行时间
	 */
	private Date executeDate;
	/**
	 * 下次执行日期
	 */
	private Date nextExecuteDate;

	public Date getCurrentDate() {
		if (currentDate == null) {
			currentDate = Calendar.getInstance().getTime();
		}
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public TaskSendCycle getTaskSendCycle() {
		return taskSendCycle;
	}

	public void setTaskSendCycle(TaskSendCycle taskSendCycle) {
		this.taskSendCycle = taskSendCycle;
	}

	public Date getExecuteDate() {
		return executeDate;
	}
	
	public String getExecuteDateStr() {
		return DateUtil.format(this.executeDate,DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
	}
	

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public Date getNextExecuteDate() {
		return nextExecuteDate;
	}
	
	public String getNextExecuteDateStr()
	{
		return DateUtil.format(this.nextExecuteDate,DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
	}

	public void setNextExecuteDate(Date nextExecuteDate) {
		this.nextExecuteDate = nextExecuteDate;
	}

	/**
	 * 发送频率
	 * 
	 * @author likun
	 * 
	 */
	public static enum TaskSendCycle {
		ONE_DAY("ONE_DAY", "每天一次"), ONE_WEEK("ONE_WEEK", "每周一次"), TWO_WEEK(
				"TWO_WEEK", "二周一次"), ONE_MONTH("ONE_MONTH", "每月一次");
		/**
		 * 值
		 */
		private String value;
		/**
		 * 描述
		 */
		private String desc;

		private TaskSendCycle(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
}
