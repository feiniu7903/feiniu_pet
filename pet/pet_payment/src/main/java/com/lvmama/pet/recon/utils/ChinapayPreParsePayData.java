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
// * 银联预授权对账文件解析类
// * @author fengyu
// * @version
// */
//public class ChinapayPreParsePayData implements ParsePayData {
//	protected final Log log = LogFactory.getLog(this.getClass().getName());
//
//	@Override
//	public String getReconGwType() {
//		return Constant.RECON_GW_TYPE.CHINAPAY_PRE.getCode();
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
//			String[] csv = dataTmp.trim().split(",");
//
//			if (checkUseful(csv)) {
//				list.add(convertBean(csv));
//			}
//		}
//
//		return list;
//	}
//	
//	public PayDataImportBean convertBean(String[] csv) {
//		PayDataImportBean pay = new PayDataImportBean();
//		pay.setGwSn(csv[0].trim());
//		pay.setOrderNo(csv[4].trim());
//
//		String gwAmountStr = csv[7].trim();
//		gwAmountStr = gwAmountStr.replace("\\.", "");
//		gwAmountStr = gwAmountStr.replace("\\+", "");
//		pay.setGwAmount(Float.valueOf(gwAmountStr));
//
//		String payTimeStr = csv[6];
//		Date payTime = DateUtil.stringToDate(payTimeStr, "yyyyMMdd HH:mm:ss");
//		pay.setPaymentTime(DateUtil.formatDate(payTime, "yyyyMMddHHmmss"));
//		return pay;
//	}
//
//	/**
//	 * 检测是否是预授权完成
//	 * @param csv
//	 * @return
//	 */
//	private boolean checkUseful(String[] csv) {
//		if ("03".equals(csv[1])) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//}
