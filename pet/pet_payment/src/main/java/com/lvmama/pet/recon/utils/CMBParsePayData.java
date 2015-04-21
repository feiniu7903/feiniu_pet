//package com.lvmama.pet.recon.utils;
//
//import java.io.BufferedReader;
//import java.io.StringReader;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.lvmama.comm.pet.vo.PayDataImportBean;
//import com.lvmama.comm.vo.Constant;
///**
// * 招商银行的对账数据下载
// * @author ranlongfei 2012-6-28
// * @version
// */
//public class CMBParsePayData implements ParsePayData {
//
//	public boolean checkUseful(String[] csv) {
//		// 4, 定单状态 为0正常
//		if(!"0".equals(csv[4])) {
//			return false;
//		}
//		return true;
//	}
//
//	public PayDataImportBean convertBean(String[] csv) {
//		// 0, 交易日期 1, 处理日期 2, 金额 3, 定单号 4, 定单状态
//		PayDataImportBean pay = new PayDataImportBean();
//		pay.setGwSn(csv[3].trim());
//		pay.setOrderNo(csv[3].trim());
//		// 将金额转为分单位
//		pay.setGwAmount(Float.valueOf(csv[2].trim()) * 100);
//		pay.setPaymentTime(csv[0].replaceAll("\\D+", ""));
//		return pay;
//	}
//
//	@Override
//	public List<PayDataImportBean> parseData(String data) throws Exception {
//		List<PayDataImportBean> payList = new ArrayList<PayDataImportBean>();
//		if (data == null || "".equals(data)) {
//			return payList;
//		}
//		BufferedReader br = new BufferedReader(new StringReader(data));
//		String dataTmp = null;
//		// 解析文件
//		String[] csv = new String[5];
//		for (int i = 0; (dataTmp = br.readLine()) != null; i++) {
//			int index = i % 5;
//			csv[index] = dataTmp;
//			// 5个一读
//			if(index == 4) {
//				// 组装
//				if(checkUseful(csv)) {
//					payList.add(convertBean(csv));
//				}
//				csv = new String[5];
//			}
//		}
//		return payList;
//	}
//
//	@Override
//	public String getReconGwType() {
//		return Constant.RECON_GW_TYPE.CMB.getCode();
//	}
//
//	/**
//	 * 
//	 * @author: ranlongfei 2012-6-27 下午05:07:00
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		try {
//			CMBParsePayData paydata = new CMBParsePayData();
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
