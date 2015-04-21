//package com.lvmama.pet.recon.job;
//
//import java.io.ByteArrayInputStream;
//import java.util.Date;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.lvmama.comm.vo.Constant;
//import com.lvmama.pet.recon.service.UploadPayDataFileService;
//import com.lvmama.pet.recon.utils.ByPayReconUtil;
//import com.lvmama.comm.utils.DateUtil;
//
///**
// * 百付电话预授权对账——文件下载Job
// * 
// * @author fengyu
// * 
// */
//public class ByPayDownPayDataJob implements Runnable {
//
//	protected final Log log = LogFactory.getLog(this.getClass().getName());
//
//	private UploadPayDataFileService uploadPayDataFileService;
//
//	private Date startDate;
//
//	private Date endDate;
//
//	private static String DATA_HEAD = "商户号\\|业务类型\\|支付通道\\|商户订单号\\|交易金额\\(元\\)\\|商户交易时间\\|交易状态\\|交易类型\\|卡号\\|支付流水号\\|交易时间\\|交易手续费\\(元\\)\\|清算日期\\|订单标识\\n";
//	
//	@Override
//	public void run() {
//		if (!Constant.getInstance().isJobRunnable()) {
//			return;
//		}
//		log.info("ByPayDownPayDataJob starting");
//		Date date = new Date();
//		// 前两天0时
//		startDate = DateUtil.accurateToDay(DateUtil.getDateAfterDays(date, -2));
//		// 前一天23时
//		endDate = DateUtil.accurateToDay(DateUtil.getDateAfterDays(date, -1));
//		try {
//			download();
//			log.info("ByPayDownPayDataJob completed");
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.info("ByPayDownPayDataJob error");
//		}
//	}
//
//	private void download() throws Exception {
//		String data = "";
//		data += download(startDate);
//		data += download(endDate);
//
//		data = data.replaceAll(DATA_HEAD, "");
//		
//		if(data == null || "".equals(data)) {
//			return;
//		}
//
//		// 文件上传
//		uploadPayDataFileService.uploadFileToServer(
//				Constant.RECON_GW_TYPE.TELBYPAY.getCode(),
//				new ByteArrayInputStream(data.getBytes()));
//	}
//
//	/**
//	 * 百付电话预授权接口解析过程
//	 * 
//	 * @author: fengyu
//	 * @return
//	 * @throws Exception
//	 */
//	private String download(Date date) throws Exception {
//		// 请求数据
//		String data = ByPayReconUtil.queryReconData(date);
//		if (data == null || "".equals(data)) {
//			return "";
//		}
//
//		return data;
//	}
//
//	public UploadPayDataFileService getUploadPayDataFileService() {
//		return uploadPayDataFileService;
//	}
//
//	public void setUploadPayDataFileService(
//			UploadPayDataFileService uploadPayDataFileService) {
//		this.uploadPayDataFileService = uploadPayDataFileService;
//	}
//
//	
//}
