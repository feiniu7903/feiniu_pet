package com.lvmama.sms;
import java.io.File;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.pet.po.pub.ComSmsHistory;
import com.lvmama.comm.pet.po.sms.SmsMMS;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.utils.UploadUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.sms.dao.ComSmsDAO;
import com.lvmama.sms.dao.ComSmsHistoryDAO;

public class SmsSender {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(SmsSender.class);
	
	private ComSmsDAO comSmsDAO;
	private ComSmsHistoryDAO comSmsHistoryDAO;
	private SmsRemoteService smsRemoteService;
	private PassCodeService passCodeService;

	public boolean sendSms(ComSms sms, String type){
		if(sms!=null && sms.isValid()) {
			try{
				if (sms.isMmsMode()){
					smsRemoteService.sendMMSms(new String[]{sms.getContent()}, new byte[][]{sms.getCodeImage()}, null, sms.getMobile(), "SUZHOULEYUAN");
				}else{
					smsRemoteService.sendSmsWithType(sms.getContent(), sms.getMobile(), type);
				}
			}catch(Exception e){
				e.printStackTrace();
				sms.setDescription("发送失败");
				sms.setStatus(Constant.SMS_STATUS.FAIL.name());
				comSmsDAO.insert(sms);
				return false;
			}
			ComSmsHistory smsHistory = new ComSmsHistory();
			BeanUtils.copyProperties(sms, smsHistory);
			smsHistory.setCodeImage(sms.getCodeImage());
			smsHistory.setSendTime(new Date());
			comSmsHistoryDAO.insert(smsHistory);
			return true;
		}else{
			LOG.error("发送失败 sms " + sms);
			return false;
		}
	}
	
	public boolean sendSms(ComSms sms){
		/**
		 * 此处代码应该修改为：
		 * sendSms(sms, null);
		 */
		if(sms!=null && sms.isValid()) {
			try{
				if (sms.isMmsMode()){
				    smsRemoteService.sendMMSms(new String[]{sms.getContent()}, new byte[][]{sms.getCodeImage()}, null, sms.getMobile(), "SUZHOULEYUAN");
				}else{
					smsRemoteService.sendSms(sms.getContent(), sms.getMobile());
				}
			}catch(Exception e){
				e.printStackTrace();
				sms.setDescription("发送失败");
				sms.setStatus(Constant.SMS_STATUS.FAIL.name());
				comSmsDAO.insert(sms);
				return false;
			}
			ComSmsHistory smsHistory = new ComSmsHistory();
			BeanUtils.copyProperties(sms, smsHistory);
			smsHistory.setCodeImage(sms.getCodeImage());
			smsHistory.setSendTime(new Date());
			comSmsHistoryDAO.insert(smsHistory);
			return true;
		}else{
			LOG.error("发送失败 sms " + sms);
			return false;
		}
	}
	
	public boolean sendSms(ComSms sms,Long codeId){
		if(sms!=null && sms.isValid()) {
			try{
				if (sms.isMmsMode()){
					LOG.info("codeId:"+codeId);
					smsRemoteService.sendMMSms(new SmsMMS(sms.getContent(), passCodeService.getPassCodeByCodeId(codeId).getCodeImage(), null),sms.getMobile(),"SUZHOULEYUAN");
				}else{
					smsRemoteService.sendSms(sms.getContent(), sms.getMobile());
				}
			}catch(Exception e){
				e.printStackTrace();
				sms.setDescription("发送失败");
				sms.setStatus(Constant.SMS_STATUS.FAIL.name());
				comSmsDAO.insert(sms);
				return false;
			}
			ComSmsHistory smsHistory = new ComSmsHistory();
			BeanUtils.copyProperties(sms, smsHistory);
			smsHistory.setCodeImage(sms.getCodeImage());
			smsHistory.setSendTime(new Date());
			comSmsHistoryDAO.insert(smsHistory);
			return true;
		}else{
			LOG.error("发送失败 sms " + sms);
			return false;
		}
	}
	
	public boolean saveSms(ComSms sms) {
		if(sms.isValid() && sms.getSendTime()!=null) {
			comSmsDAO.insert(sms);
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 上传凭证图片
	 * @param b
	 * @param fileName
	 * @return
	 * @author dengcheng
	 */
	private String uploadCode(byte[] b,String fileName){
		File file = UploadUtil.getFileFromBytes(b, "code_image.jpg");
		String url = UploadUtil.uploadFile(file, fileName);
		return url;
	}
	
	

	public void setComSmsDAO(ComSmsDAO comSmsDAO) {
		this.comSmsDAO = comSmsDAO;
	}

	public void setComSmsHistoryDAO(ComSmsHistoryDAO comSmsHistoryDAO) {
		this.comSmsHistoryDAO = comSmsHistoryDAO;
	}

	public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}
}
