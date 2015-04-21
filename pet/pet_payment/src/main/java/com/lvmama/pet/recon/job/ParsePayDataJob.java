//package com.lvmama.pet.recon.job;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.lvmama.comm.pet.client.FSClient;
//import com.lvmama.comm.pet.po.pay.FinReconFile;
//import com.lvmama.comm.pet.service.pay.FinReconFileService;
//import com.lvmama.comm.pet.vo.ComFile;
//import com.lvmama.comm.pet.vo.PayDataImportBean;
//import com.lvmama.comm.vo.Constant;
//import com.lvmama.pet.recon.service.ParsePayDataService;
//import com.lvmama.pet.recon.utils.AlipayParsePayData;
//import com.lvmama.pet.recon.utils.BOCParsePayData;
//import com.lvmama.pet.recon.utils.ByPayParsePayData;
//import com.lvmama.pet.recon.utils.CMBParsePayData;
//import com.lvmama.pet.recon.utils.COMMParsePayData;
//import com.lvmama.pet.recon.utils.ChinapayParsePayData;
//import com.lvmama.pet.recon.utils.ChinapayPreParsePayData;
//import com.lvmama.pet.recon.utils.ParsePayData;
//import com.lvmama.pet.recon.utils.UnionpayParsePayData;
//
//public class ParsePayDataJob implements Runnable {
//	
//	protected final Log log =LogFactory.getLog(this.getClass().getName());
//	
//	private ParsePayDataService parsePayDataService;
//
//	private FinReconFileService finReconFileService;
//	
//	private FSClient fsClient;
//	
//	@Override
//	public void run() {
//		if (!Constant.getInstance().isJobRunnable()) {
//			return;
//		}
//		log.info("ParsePayDataJob starting");
//		try {
//			download();
//			log.info("ParsePayDataJob completed");
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.info("ParsePayDataJob error");
//		}
//	}
//	
//	/**
//	 * 对账文件解析入库过程
//	 * 
//	 * @author: ranlongfei 2012-7-2 下午04:52:45
//	 * @return
//	 * @throws Exception
//	 */
//	private void download() throws Exception {
//		Map<String, Object> paraMap = new HashMap<String, Object>();
//		paraMap.put("status", Constant.RECON_PAYMENT_TYPE.UNRECON.getCode());
//		List<FinReconFile> files = finReconFileService.selectByExample(paraMap);
//		
//		if(files == null) {
//			log.info("ParsePayDataJob no file.");
//			return;
//		}
//		log.info(files.size());
//		for(FinReconFile f : files) {
//			try{
//				List<PayDataImportBean> result = parseData(f);
//				parsePayDataService.downloadPayData(result);
//				f.setStatus(Constant.RECON_PAYMENT_TYPE.RECONED.getCode());
//				finReconFileService.update(f);
//			} catch(Exception e) {
//				log.info("ParsePayDataJob parse file has errors : " + f.getFileName());
//				e.printStackTrace();
//			}
//		}
//	}
//	/**
//	 * 解析得到数据
//	 * 
//	 * @author: ranlongfei 2012-10-10 下午3:39:25
//	 * @param f
//	 * @return
//	 * @throws Exception
//	 */
//	private List<PayDataImportBean> parseData(FinReconFile f) throws Exception {
//		Long fileId = new Long(f.getFileId());
//		String data = downloadFile(fileId);
//		ParsePayData parser = getParse(f.getGwName());
//		if(parser ==null) {
//			log.info("ParsePayData： No parser.");
//			return null;
//		}
//		// 得到数据
//		List<PayDataImportBean> result = parser.parseData(data);
//		// 补全文件与平台信息
//		for(PayDataImportBean pay : result) {
//			pay.setFileId(f.getReconFileId());
//			pay.setGwName(f.getGwName());
//		}
//		return result;
//	}
//	/**
//	 * 从FTP上下载数据
//	 * 
//	 * @author: ranlongfei 2012-10-10 下午3:22:15
//	 * @param f
//	 * @return
//	 */
//	private String downloadFile(Long fileId) {
//		ComFile file = fsClient.downloadFile(fileId);
//		return new String(file.getFileData());
//	}
//
//	/**
//	 * 获得解析器
//	 * 
//	 * @author: ranlongfei 2012-10-10 下午3:15:11
//	 * @param gwName
//	 * @return
//	 */
//	private ParsePayData getParse(String gwName) {
//		if(Constant.RECON_GW_TYPE.ALIPAY.getCode().equals(gwName)) {
//			return new AlipayParsePayData();
//		}
//		if(Constant.RECON_GW_TYPE.COMM.getCode().equals(gwName)) {
//			return new COMMParsePayData();
//		}
//		if(Constant.RECON_GW_TYPE.CMB.getCode().equals(gwName)) {
//			return new CMBParsePayData();
//		}
//		if(Constant.RECON_GW_TYPE.CHINAPAY.getCode().equals(gwName)) {
//			return new ChinapayParsePayData();
//		}
//		if(Constant.RECON_GW_TYPE.BOC.getCode().equals(gwName)) {
//			return new BOCParsePayData();
//		}
//		if(Constant.RECON_GW_TYPE.TELBYPAY.getCode().equals(gwName)) {
//			return new ByPayParsePayData();
//		}
//		if(Constant.RECON_GW_TYPE.UNIONPAY.getCode().equals(gwName)) {
//			return new UnionpayParsePayData();
//		}
//		if(Constant.RECON_GW_TYPE.CHINAPAY_PRE.getCode().equals(gwName)) {
//			return new ChinapayPreParsePayData();
//		}
//		return null;
//	}
//	public ParsePayDataService getParsePayDataService() {
//		return parsePayDataService;
//	}
//
//	public void setParsePayDataService(ParsePayDataService parsePayDataService) {
//		this.parsePayDataService = parsePayDataService;
//	}
//
//	public FinReconFileService getFinReconFileService() {
//		return finReconFileService;
//	}
//
//	public void setFinReconFileService(FinReconFileService finReconFileService) {
//		this.finReconFileService = finReconFileService;
//	}
//
//	public FSClient getFsClient() {
//		return fsClient;
//	}
//
//	public void setFsClient(FSClient fsClient) {
//		this.fsClient = fsClient;
//	}
//}
