package com.ejingtong.log;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import com.ejingtong.common.Constans;
import com.ejingtong.http.ResClient;

/**
 * 日志管理
 * @author xuqun
 */
public class LogManager {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	// 获取所有日志
	public List<MLog> getLogList() {
		// TODO 返回SD卡里所有的日志
		List<MLog> mLogs = new ArrayList<MLog>();
		return mLogs;
	}

	// 返回指定时间段类的日志，以天为单位
	public List<MLog> getLogListByDate(String startTime, String endTime) {
		// TODO 返回SD卡里指定时间段内的日志
		List<MLog> mLogs = new ArrayList<MLog>();
		return mLogs;
	}

	// 获取某一天的日志
	public MLog getLogByDay(String time) {
		// TODO 返回某一天的日志
		MLog mLog = new MLog(time);
		return mLog;
	}

	// 获取当天的日志
	public MLog getLogCurrentDay() {
		// TODO 获取当天的日志
		String currentDay = "";
		MLog mLog = new MLog(currentDay);
		return mLog;
	}

	// 删除所有日志
	public void deleteLog() {
		// TODO 删除SD卡里所有的日志

	}

	// 删除指定时间段类的日志，以天为单位
	public void deleteLogByDate(String startTime, String endTime) {
		// TODO 删除SD卡里指定时间段内的日志

	}

	// 删除某一天的日志
	public void deleteLogByDay(String day) {
		// TODO 删除某一天的日志

	}

	// 删除指定日期之前的所有日志
	public void deleteLogBeforeDate(String lastTime) {
		// TODO 删除指定日期之前的所有日志

	}

	// 写日志
	public static void log(final String strLog) {
		new Thread() {
			@Override
			public void run() {
				MLog mLog = new MLog(getLogFilePath());
				SimpleDateFormat sf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				mLog.write(sf.format(new Date()) + ":" + strLog);
				super.run();
			}
		}.start();
	}

	// 获取当天的日志文件路径
	public static String getLogFilePath() {
		Calendar mCalendar = Calendar.getInstance();
		return getLogFilePath(mCalendar);
	}

	// 获取指定时间的日志路径
	public static String getLogFilePath(String time) {
		String path = null;
		try {
			Calendar mCalendar = Calendar.getInstance();
			mCalendar.setTime(sdf.parse(time));
			path = getLogFilePath(mCalendar);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return path;
	}

	// 获取指定日期的日志文件路径
	public static String getLogFilePath(Calendar mCalendar) {
		String logFilePath = Constans.sdPath + Constans.logPath;
		int year = mCalendar.get(Calendar.YEAR);
		int month = mCalendar.get(Calendar.MONTH) + 1;
		int day = mCalendar.get(Calendar.DAY_OF_MONTH);
		logFilePath = logFilePath + year + "/" + month + "/" + year + "_"
				+ month + "_" + day + ".text";
		return logFilePath;
	}

	// 获取指定时间段内的所有日志文件路径 以天为单位
	public static LinkedList<String> getLogFilePath(String startTime,
			String endTime) {
		LinkedList<String> filesPath = new LinkedList<String>();
		try {
			Calendar startCalendar = Calendar.getInstance();
			startCalendar.setTime(sdf.parse(startTime));

			Calendar endCalendar = Calendar.getInstance();
			endCalendar.setTime(sdf.parse(endTime));

			Calendar currentCalendar = Calendar.getInstance();

			if (endCalendar.after(currentCalendar)) {
				endCalendar = currentCalendar;
			}

			while (startCalendar.before(endCalendar)) {
				filesPath.offer(getLogFilePath(startCalendar));

				startCalendar.add(Calendar.DAY_OF_MONTH, 1);

				// uploadFilesPath.offer(getPath(startCalendar));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return filesPath;
	}

	/**
	 * 上传指定时间的日志 以天为单位
	 * @param time
	 */
	public static void uploadFiles(String time, Map<String, String> params) {
		String path = getLogFilePath(time);
		if (null != path) {
			LinkedList<String> filesPath = new LinkedList<String>();
			filesPath.offer(path);
			upload(filesPath, params);
		}
	}

	// 上传指定时间段内的日志 以天为单位
	public static void uploadFiles(String startTime, String endTime,
			Map<String, String> params) {
		upload(getLogFilePath(startTime, endTime), params);
	}

	// 日志上传
	public static void upload(final LinkedList<String> filesPath,
			final Map<String, String> params) {
		if (filesPath == null) {
			return;
		}
		new Thread() {
			@Override
			public void run() {
				while (filesPath.size() > 0) {
					try {
						ResClient.uploadFile(filesPath.poll(), params);
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

}
