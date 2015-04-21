//package com.lvmama.pet.recon.job;
//
//import java.io.ByteArrayInputStream;
//import java.util.Date;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.lvmama.comm.utils.DateUtil;
//import com.lvmama.comm.vo.Constant;
//import com.lvmama.pet.recon.service.UploadPayDataFileService;
//import com.lvmama.pet.utils.COMMUtil;
//
//public class COMMDownPayDataJob implements Runnable {
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
//		log.info("COMMDownPayDataJob starting");
//		Date date = new Date();
//		// 前一天0时
//		startDate = DateUtil.accurateToDay(DateUtil.getDateAfterDays(date, -2));
//		// 前一天23时
//		endDate = new Date(DateUtil.accurateToDay(date).getTime() - 1);
//		try {
//			download();
//			log.info("COMMDownPayDataJob completed");
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.info("COMMDownPayDataJob error");
//		}
//	}
//
//	/**
//	 * 下载并上传
//	 * 
//	 * @author: ranlongfei 2012-10-9 下午4:30:42
//	 * @throws Exception
//	 */
//	public void download() throws Exception {
//		startDate = DateUtil.accurateToDay(startDate);
//		endDate = DateUtil.accurateToDay(endDate);
//		while(true) {
//			if((endDate.getTime() - startDate.getTime() < 0)) {
//				break;
//			}
//			Date endTemp = endDate;
//			if((endTemp.getTime() - startDate.getTime() > 1000*60*60*24)) {
//				endTemp = DateUtil.getDateAfterDays(startDate, 1);
//			}
//			String data = COMMUtil.downLoadSettlement(DateUtil.getFormatDate(startDate, "yyyyMMdd"));
//			if(data != null && !"".equals(data)) {
//				uploadPayDataFileService.uploadFileToServer(Constant.RECON_GW_TYPE.COMM.getCode(), new ByteArrayInputStream(data.getBytes()));
//			}
//			if((endDate.getTime() - startDate.getTime() == 0)) {
//				break;
//			}
//			startDate = endTemp;
//		}
//	}
//
//	public UploadPayDataFileService getUploadPayDataFileService() {
//		return uploadPayDataFileService;
//	}
//
//	public void setUploadPayDataFileService(UploadPayDataFileService uploadPayDataFileService) {
//		this.uploadPayDataFileService = uploadPayDataFileService;
//	}
//	
//}
