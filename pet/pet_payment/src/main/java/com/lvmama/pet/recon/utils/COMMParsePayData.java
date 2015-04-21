//package com.lvmama.pet.recon.utils;
//
//import java.io.BufferedReader;
//import java.io.StringReader;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.lvmama.comm.pet.vo.PayDataImportBean;
//import com.lvmama.comm.vo.Constant;
//
///**
// * 交通银行平台交易数据下载
// * @author ranlongfei 2012-6-28
// * @version
// */
//public class COMMParsePayData implements ParsePayData {
//
//	@Override
//	public String getReconGwType() {
//		return Constant.RECON_GW_TYPE.COMM.getCode();
//	}
//
//	@Override
//	public List<PayDataImportBean> parseData(String data) throws Exception {
//		List<PayDataImportBean> list = new ArrayList<PayDataImportBean>();
//		if (data == null || "".equals(data)) {
//			return list;
//		}
//		BufferedReader br = new BufferedReader(new StringReader(data));
//		String dataTmp = null;
//		// 解析文件
//		for (int i = 0; (dataTmp = br.readLine()) != null; i++) {
//			// 跳过10行表头信息
//			if(i < 10) {
//				continue;
//			}
//			String[] csv = dataTmp.trim().split("\\s+");
//			// 不是9列的数据为异常
//			if(csv.length != 9) {
//				break;
//			}
//			// 组装
//			if(checkUseful(csv)) {
//				list.add(convertBean(csv));
//			}
//		}
//		return list;
//	}
//	
//	public PayDataImportBean convertBean(String[] csv) {
//		PayDataImportBean pay = new PayDataImportBean();
//		pay.setGwSn(csv[2].trim()+csv[3].trim());
//		pay.setOrderNo(csv[0].trim());
//		// 将金额转为分单位
//		pay.setGwAmount(Float.valueOf(csv[5].trim().replace(",", "")) * 100);
//		pay.setPaymentTime(csv[2].replaceAll("\\D+", ""));
//		return pay;
//	}
//
//	/**
//	 * 检查有效数据
//	 * 
//	 * @author: ranlongfei 2012-9-20 下午8:09:55
//	 * @param csv
//	 * @return
//	 */
//	public boolean checkUseful(String[] csv) {
//		return true;
//	}
//	/**
//	 * 
//	 * @author: ranlongfei 2012-6-28 下午05:41:36
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		COMMParsePayData paydata = new COMMParsePayData();
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
