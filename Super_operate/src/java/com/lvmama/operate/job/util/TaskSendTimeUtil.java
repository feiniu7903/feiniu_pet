package com.lvmama.operate.job.util;

import java.util.Calendar;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lvmama.operate.job.model.TaskSendTime;
import com.lvmama.operate.job.model.TaskSendTime.TaskSendCycle;

public class TaskSendTimeUtil {

	public static void main(String[] args) {
		// System.out.println(DateUtil.getDayOfWeek(Calendar.getInstance().getTime()));
		testOneDay();
		// testOneWeek();
		// testTwoWeek();
		// testOneMonth();
	}
	
	public static void testOneDay() {
		TaskSendTime sendTime = new TaskSendTime();
		Date currentDate = Calendar.getInstance().getTime();
		sendTime.setCurrentDate(currentDate);
		sendTime.setTaskSendCycle(TaskSendCycle.ONE_DAY);
		sendTime.setSendTime("17:43:00");
		calSendTime(sendTime);
		print(sendTime);
		execute(sendTime);
		print(sendTime);
		execute(sendTime);
		print(sendTime);
		execute(sendTime);
		print(sendTime);
	}

	public static void testOneWeek() {
		TaskSendTime sendTime = new TaskSendTime();
		Date currentDate = Calendar.getInstance().getTime();
		sendTime.setCurrentDate(currentDate);
		sendTime.setTaskSendCycle(TaskSendCycle.ONE_WEEK);
		sendTime.setSendDate("5");
		sendTime.setSendTime("11:49:00");
		calSendTime(sendTime);
		print(sendTime);
		execute(sendTime);
		print(sendTime);
		execute(sendTime);
		print(sendTime);
		execute(sendTime);
		print(sendTime);
	}

	public static void testTwoWeek() {
		TaskSendTime sendTime = new TaskSendTime();
		Date currentDate = Calendar.getInstance().getTime();
		sendTime.setCurrentDate(currentDate);
		sendTime.setTaskSendCycle(TaskSendCycle.TWO_WEEK);
		sendTime.setSendDate("5");
		sendTime.setSendTime("11:40:00");
		calSendTime(sendTime);
		print(sendTime);
		execute(sendTime);
		print(sendTime);
		execute(sendTime);
		print(sendTime);
		execute(sendTime);
		print(sendTime);
	}

	public static void testOneMonth() {
		TaskSendTime sendTime = new TaskSendTime();
		Date currentDate = Calendar.getInstance().getTime();
		sendTime.setCurrentDate(currentDate);
		sendTime.setTaskSendCycle(TaskSendCycle.ONE_MONTH);
		sendTime.setSendDate("10");
		sendTime.setSendTime("14:49:00");
		calSendTime(sendTime);
		print(sendTime);
		execute(sendTime);
		print(sendTime);
		execute(sendTime);
		print(sendTime);
		execute(sendTime);
		print(sendTime);
		execute(sendTime);
		print(sendTime);
		execute(sendTime);
		print(sendTime);
	}

	public static void print(TaskSendTime sendTime) {
		System.out.println(JSON.toJSONString(sendTime,
				SerializerFeature.WriteMapNullValue,
				SerializerFeature.PrettyFormat));
	}

	/**
	 * 执行一次，本次应该执行时间为下次执行的视频。然后计算下次执行时间
	 * 
	 * @param sendTime
	 */
	public static void execute(TaskSendTime sendTime) {
		sendTime.setExecuteDate(sendTime.getNextExecuteDate());
		calSendTime(sendTime);
	}

	/**
	 * 计算下次执行日期
	 * 
	 * @param sendTime
	 */
	public static void calSendTime(TaskSendTime sendTime) {
		TaskSendCycle sendCycle = sendTime.getTaskSendCycle();
		if (sendCycle == TaskSendCycle.ONE_DAY) {
			// 每天一次
			calSendTimeOneDay(sendTime);
		} else if (sendCycle == TaskSendCycle.ONE_WEEK) {
			// 每周一次
			calSendTimeOneWeek(sendTime);
		} else if (sendCycle == TaskSendCycle.TWO_WEEK) {
			// 每两周一次
			calSendTimeTwoWeek(sendTime);
		} else if (sendCycle == TaskSendCycle.ONE_MONTH) {
			// 每月一次
			calSendTimeOneMonth(sendTime);
		}
	}

	/**
	 * 每天一次,计算下次执行时间
	 * 
	 * @param sendTime
	 */
	public static void calSendTimeOneDay(TaskSendTime sendTime) {
		if (sendTime.getExecuteDate() != null) {
			sendTime.setNextExecuteDate(DateUtil.addDay(DateUtil.parseDate(
					DateUtil.format(sendTime.getExecuteDate(),
							DateUtil.PATTERN_yyyy_MM_dd)
							+ " "
							+ sendTime.getSendTime(),
					DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss), 1));
		} else {
			Date executeTime = DateUtil.parseDate(
					DateUtil.format(sendTime.getCurrentDate(),
							DateUtil.PATTERN_yyyy_MM_dd)
							+ sendTime.getSendTime(),
					DateUtil.PATTERN_yyyy_MM_dd + DateUtil.PATTERN_HH_mm_ss);
			// 如果计算的执行时间大于等于当前日期，那么下次执行时间就是executeTime，否知执行是在为executeTime加1天
			if (DateUtil.compareDate(executeTime, sendTime.getCurrentDate()) >= 0) {
				sendTime.setNextExecuteDate(executeTime);
			} else {
				sendTime.setNextExecuteDate(DateUtil.addDay(executeTime, 1));
			}
		}
	}

	/**
	 * 每周一次,计算下次执行时间
	 * 
	 * @param sendTime
	 */
	public static void calSendTimeOneWeek(TaskSendTime sendTime) {
		if (sendTime.getExecuteDate() != null) {
			sendTime.setNextExecuteDate(DateUtil.addWeek(DateUtil.parseDate(
					DateUtil.format(sendTime.getExecuteDate(),
							DateUtil.PATTERN_yyyy_MM_dd)
							+ " "
							+ sendTime.getSendTime(),
					DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss), 1));
		} else {
			int week = DateUtil.getDayOfWeek(sendTime.getCurrentDate());
			int sendWeek = Integer.parseInt(sendTime.getSendDate());
			int dayPlus = sendWeek - week;
			String nextTimeStr = DateUtil.format(
					DateUtil.addDay(sendTime.getCurrentDate(), dayPlus),
					DateUtil.PATTERN_yyyy_MM_dd) + sendTime.getSendTime();
			Date executeTime = DateUtil.parseDate(nextTimeStr,
					DateUtil.PATTERN_yyyy_MM_dd + DateUtil.PATTERN_HH_mm_ss);
			if (DateUtil.compareDate(executeTime, sendTime.getCurrentDate()) >= 0) {
				sendTime.setNextExecuteDate(executeTime);
			} else {
				sendTime.setNextExecuteDate(DateUtil.addWeek(executeTime, 1));
			}
		}
	}

	/**
	 * 每两周一次,计算下次执行时间
	 * 
	 * @param sendTime
	 */
	public static void calSendTimeTwoWeek(TaskSendTime sendTime) {
		if (sendTime.getExecuteDate() != null) {
			sendTime.setNextExecuteDate(DateUtil.addWeek(DateUtil.parseDate(
					DateUtil.format(sendTime.getExecuteDate(),
							DateUtil.PATTERN_yyyy_MM_dd)
							+ " "
							+ sendTime.getSendTime(),
					DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss), 2));
		} else {
			int week = DateUtil.getDayOfWeek(sendTime.getCurrentDate());
			int sendDay = Integer.parseInt(sendTime.getSendDate());
			int dayPlus = sendDay - week;
			String nextTimeStr = DateUtil.format(
					DateUtil.addDay(sendTime.getCurrentDate(), dayPlus),
					DateUtil.PATTERN_yyyy_MM_dd) + sendTime.getSendTime();
			Date executeTime = DateUtil.parseDate(nextTimeStr,
					DateUtil.PATTERN_yyyy_MM_dd + DateUtil.PATTERN_HH_mm_ss);
			if (DateUtil.compareDate(executeTime, sendTime.getCurrentDate()) >= 0) {
				sendTime.setNextExecuteDate(executeTime);
			} else {
				sendTime.setNextExecuteDate(DateUtil.addWeek(executeTime, 1));
			}
		}
	}

	/**
	 * 每月一次,计算下次执行时间
	 * 
	 * @param sendTime
	 */
	public static void calSendTimeOneMonth(TaskSendTime sendTime) {
		if (sendTime.getExecuteDate() != null) {
			sendTime.setNextExecuteDate(DateUtil.getNextSameDayOfMonth(DateUtil
					.parseDate(
							DateUtil.format(sendTime.getExecuteDate(),
									DateUtil.PATTERN_yyyy_MM_dd)
									+ " "
									+ sendTime.getSendTime(),
							DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss), DateUtil
					.getDayOfMonth(sendTime.getExecuteDate())));
		} else {
			int sendDay = Integer.parseInt(sendTime.getSendDate());
			String nextTimeStr = DateUtil.format(sendTime.getCurrentDate(),
					DateUtil.PATTERN_yyyy_MM_dd) + sendTime.getSendTime();
			Date executeTime = DateUtil.parseDate(nextTimeStr,
					DateUtil.PATTERN_yyyy_MM_dd + DateUtil.PATTERN_HH_mm_ss);
			Date date = DateUtil.getMinDinSameDayOfMonth(executeTime, sendDay);
			if (DateUtil.compareDate(date, sendTime.getCurrentDate()) >= 0) {
				sendTime.setNextExecuteDate(date);
			} else {
				sendTime.setNextExecuteDate(DateUtil.getNextSameDayOfMonth(
						date, sendDay));
			}

		}
	}

}
