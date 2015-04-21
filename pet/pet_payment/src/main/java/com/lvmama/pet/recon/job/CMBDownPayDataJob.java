//package com.lvmama.pet.recon.job;
//
//import java.io.ByteArrayInputStream;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.lvmama.comm.utils.DateUtil;
//import com.lvmama.comm.vo.Constant;
//import com.lvmama.pet.recon.service.UploadPayDataFileService;
//import com.lvmama.pet.utils.CMBUtil;
//
//public class CMBDownPayDataJob implements Runnable {
//	protected final Log log =LogFactory.getLog(this.getClass().getName());
//	
//	private Date startDate;
//	
//	private Date endDate;
//
//	private UploadPayDataFileService uploadPayDataFileService;
//	
//	@Override
//	public void run() {
//		if (!Constant.getInstance().isJobRunnable()) {
//			return;
//		}
//		log.info("CMBDownPayDataJob starting");
//		Date date = new Date();
//		// 前一天0时
//		startDate = DateUtil.accurateToDay(DateUtil.getDateAfterDays(date, -2));
//		// 前一天23时
//		endDate = DateUtil.accurateToDay(date);
//		try {
//			download();
//			log.info("CMBDownPayDataJob completed");
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.info("CMBDownPayDataJob error");
//		}
//	}
//	/**
//	 * 下载数据， 并上传
//	 * 
//	 * @author: ranlongfei 2012-10-9 下午4:26:55
//	 * @throws Exception
//	 */
//	public void download() throws Exception {
//		Map<String, String> paraTemp = new HashMap<String, String>();
//		paraTemp.put("startDate", DateUtil.getFormatDate(startDate, "yyyyMMdd"));
//		paraTemp.put("endDate", DateUtil.getFormatDate(endDate, "yyyyMMdd"));
//		String data = CMBUtil.querySettledOrderByPage(paraTemp);
//		if(data == null || "".equals(data)) {
//			return;
//		}
//		uploadPayDataFileService.uploadFileToServer(Constant.RECON_GW_TYPE.CMB.getCode(), new ByteArrayInputStream(data.getBytes()));
//	}
//	public UploadPayDataFileService getUploadPayDataFileService() {
//		return uploadPayDataFileService;
//	}
//	public void setUploadPayDataFileService(UploadPayDataFileService uploadPayDataFileService) {
//		this.uploadPayDataFileService = uploadPayDataFileService;
//	}
//
//}
