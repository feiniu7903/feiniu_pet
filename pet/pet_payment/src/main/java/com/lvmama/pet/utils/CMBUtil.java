package com.lvmama.pet.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cmb.netpayment.Settle;

import com.alipay.util.UtilDate;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.PaymentConstant;

/**
 * 招商银行对账平台工具类
 * 
 * @author ranlongfei 2012-6-28
 * @version
 */
public class CMBUtil {
	/**
	 * 退款选项.
	 */
	private final static String CMB_OPTION = PaymentConstant.getInstance().getProperty("CMB_OPTION");
	/**
	 * 分行ID
	 */
	private final static String CMB_BRANCHID = PaymentConstant.getInstance().getProperty("CMB_BRANCHID");

	/**
	 * 用户号
	 */
	private final static String CMB_CONO = PaymentConstant.getInstance().getProperty("CMB_CONO");
	/**
	 * 密码
	 */
	private final static String CMB_PWD = PaymentConstant.getInstance().getProperty("CMB_PWD");
	/**
	 * 
	 * @author: ranlongfei 2012-6-28 上午09:42:46
	 * @param args
	 */
	public static void main(String[] args) {
		Date startDate = DateUtil.toDate("2012-06-19 00:00:00", UtilDate.simple);
		Date endDate = DateUtil.toDate("2012-06-20 00:00:00", UtilDate.simple);
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("startDate", DateUtil.getFormatDate(startDate, UtilDate.dtShort));
		sParaTemp.put("endDate", DateUtil.getFormatDate(endDate, UtilDate.dtShort));
		try {
			String result = querySettledOrderByPage(sParaTemp);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得Settle
	 * 
	 * @author: ranlongfei 2012-6-28 上午09:59:00
	 * @return
	 * @throws Exception
	 */
	private static Settle getSettle() throws Exception {
		Settle settle = new Settle();
		int iRet = settle.SetOptions(CMB_OPTION);
		if (iRet != 0) {
			throw new Exception(settle.GetLastErr(iRet));
		}
		return settle;
	}

	/**
	 * 登陆招商银行平台 <br>
	 * 任务操作，都需要先登陆
	 * 
	 * @author: ranlongfei 2012-6-28 上午10:04:23
	 * @param settle
	 * @throws Exception
	 */
	private static void loginCMB(Settle settle) throws Exception {
		int iRet = settle.LoginC(CMB_BRANCHID, CMB_CONO, CMB_PWD);
		if (iRet != 0) {
			throw new Exception(settle.GetLastErr(iRet));
		}
	}

	/**
	 * 退出招商银行平台 <br>
	 * 任务操作，最后都需要退出
	 * 
	 * @author: ranlongfei 2012-6-28 上午10:04:23
	 * @param settle
	 * @throws Exception
	 */
	private static void logoutCMB(Settle settle) throws Exception {
		int iRet = settle.Logout();
		if (iRet != 0) {
			throw new Exception(settle.GetLastErr(iRet));
		}
	}

	/**
	 * 按结算日查询平台交易记录
	 * 
	 * @author: ranlongfei 2012-6-28 上午10:20:58
	 * @param settle
	 * @return
	 * @throws Exception
	 */
	public static String querySettledOrderByPage(Map<String, String> paraTemp) throws Exception {
		Settle settle = getSettle();
		loginCMB(settle);
		try {
			settle.PageReset();
			StringBuffer strbuf = new StringBuffer();
			int iRet = 0;
			do {
				iRet = settle.QuerySettledOrderByPage(paraTemp.get("startDate"), paraTemp.get("endDate"), 100, strbuf);
			} while (iRet == 0 && !settle.m_bIsLastPage); 
			if (iRet != 0) {
				throw new Exception(settle.GetLastErr(iRet));
			}
			return strbuf.toString();
		} catch (Exception e) {
			throw e;
		} finally {
			logoutCMB(settle);
		}
	}
}
