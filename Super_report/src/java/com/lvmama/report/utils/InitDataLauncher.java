package com.lvmama.report.utils;

import java.util.Calendar;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lvmama.report.jobs.ReportOrderRefreshJob;

public class InitDataLauncher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String days = args[0];
		int day = Integer.valueOf(days).intValue();
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-report-beans.xml");
		ReportOrderRefreshJob job = (ReportOrderRefreshJob)context.getBean("reportOrderRefreshJob");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -day);
		job.setTheTime(cal.getTime());
		job.run();
	}

}
