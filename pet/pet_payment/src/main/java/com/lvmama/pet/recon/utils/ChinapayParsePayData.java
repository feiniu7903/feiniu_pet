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
//
//
///**
// * 银联平台的对账数据下载
// * @author ranlongfei 2012-6-28
// * @version
// */
//public class ChinapayParsePayData implements ParsePayData {
//	
//	@Override
//	public String getReconGwType() {
//		return Constant.RECON_GW_TYPE.CHINAPAY.getCode();
//	}
//	/**
//	 * 
//	 * @author: ranlongfei 2012-6-25 上午11:24:51
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		try {
//			ChinapayParsePayData paydata = new ChinapayParsePayData();
//			List<PayDataImportBean> result = paydata.parseData("");
//			for(PayDataImportBean bean : result) {
//				System.out.println(bean);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	/**
//	 * 检查数据是否有用
//	 * 
//	 * @author: ranlongfei 2012-6-25 下午03:32:01
//	 * @param csv
//	 * @return
//	 */
//	public boolean checkUseful(String[] csv) {
//		//交易类型[3]	0001-消费交易，0002-退款，0005-退款撤销
//		//交易状态[5]	当TransType=0001 时，该域为1001的表示成功支付,1111表示支付未成功；
//		if(!"0001".equals(csv[3]) || !"1001".equals(csv[5])) {
//			return false;
//		}
//		return true;
//	}
//	/**
//	 * 组装数据
//	 * 
//	 * @author: ranlongfei 2012-6-25 下午02:40:51
//	 * @param csv
//	 * @return
//	 */
//	public PayDataImportBean convertBean(String[] csv) {
//		//cvsLine[10],cvsLine[2],cvsLine[5],cvsLine[0]
//		PayDataImportBean pay = new PayDataImportBean();
//		pay.setGwSn(csv[2].trim()+csv[3].trim()+csv[6].trim());
//		pay.setOrderNo(csv[2].trim());
//		// 将金额转为分单位:本来就是分单位
//		pay.setGwAmount(Float.valueOf(csv[4].trim()));
//		pay.setPaymentTime(csv[0].replaceAll("\\D+", ""));
//		return pay;
//	}
//	/**
//	 * 下载数据 <br>
//	 * 从sParaTemp中取得filePath，然后解析。
//	 * 
//	 * @author: ranlongfei 2012-6-25 上午11:28:47
//	 * @param sParaTemp
//	 * @return
//	 * @throws Exception
//	 */
//	@Override
//	public List<PayDataImportBean> parseData(String data) throws Exception {
//		List<PayDataImportBean> result = new ArrayList<PayDataImportBean>();
//		if (data == null || "".equals(data)) {
//			return result;
//		}
//		// 下载文件
//		BufferedReader br = new BufferedReader(new StringReader(data));
//		String dataTmp = null;
//		// 解析文件
//		for (int i = 0; (dataTmp = br.readLine()) != null; i++) {
//			// 第一行不读
//			if (i == 0) {
//				continue;
//			}
//			String[] csv = dataTmp.split("\\|");
//			// 组装
//			if(checkUseful(csv)) {
//				result.add(convertBean(csv));
//			}
//		}
//		return result;
//	}
//}
