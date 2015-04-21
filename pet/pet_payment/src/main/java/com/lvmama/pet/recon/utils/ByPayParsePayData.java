//package com.lvmama.pet.recon.utils;
//
//import java.io.BufferedReader;
//import java.io.StringReader;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.lvmama.comm.pet.vo.PayDataImportBean;
//import com.lvmama.comm.utils.DateUtil;
//import com.lvmama.comm.vo.Constant;
//
///**
// * 银联对账文件解析类
// * @author fengyu
// * @version
// */
//public class ByPayParsePayData implements ParsePayData {
//	protected final Log log = LogFactory.getLog(this.getClass().getName());
//
//	@Override
//	public String getReconGwType() {
//		return Constant.RECON_GW_TYPE.TELBYPAY.getCode();
//	}
//
//	@Override
//	public List<PayDataImportBean> parseData(String data) throws Exception {
//		List<PayDataImportBean> list = new ArrayList<PayDataImportBean>();
//		if (data == null || "".equals(data)) {
//			return list;
//		}
//		BufferedReader br = new BufferedReader(new StringReader(data.trim()));
//		String dataTmp = null;
//		// 解析文件
//		while ((dataTmp = br.readLine()) != null) {
//			String[] csv = dataTmp.trim().split("\\|");
//
//			if (isPayment(csv)) {
//				list.add(convertBean(csv));
//			}
//		}
//
//		return list;
//	}
//	
//	public PayDataImportBean convertBean(String[] csv) {
//		PayDataImportBean pay = new PayDataImportBean();
//		pay.setGwSn(csv[9].trim());
//		pay.setOrderNo(csv[3].trim());
//		// 将金额转为分单位
//		pay.setGwAmount(Float.valueOf(csv[4].trim()) * 100);
//
//		String payTime = csv[10];
//		
//		//2012-12-01 01:10:45
//		Date date = DateUtil.stringToDate(payTime, "yyyy-MM-dd HH:mm:ss");
//
//		//20121201011045
//		pay.setPaymentTime(DateUtil.formatDate(date, "yyyyMMddHHmmss"));
//		return pay;
//	}
//
//	private boolean isPayment(String[] csv) {
//		if ("有密支付".equals(csv[7])) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//	/**
//	 * 
//	 * @author: ranlongfei 2012-6-28 下午05:41:36
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		ByPayParsePayData paydata = new ByPayParsePayData();
//		try {
//			List<PayDataImportBean> result = paydata.parseData("");
//			for(PayDataImportBean bean : result) {
//				System.out.println(bean);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//}
