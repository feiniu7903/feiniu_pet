package com.lvmama.back.job;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.PageSize;
import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkFaxTask;
import com.lvmama.comm.bee.service.ebooking.EbkCertificateService;
import com.lvmama.comm.bee.service.ebooking.EbkFaxTaskService;
import com.lvmama.comm.bee.service.fax.FaxService;
import com.lvmama.comm.bee.vo.ord.Fax;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.fax.FaxConvertHelper;
import com.lvmama.comm.utils.fax.FaxSender;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.cert.builder.EbkCertBuilder;
import com.lvmama.comm.vo.cert.builder.EbkCertBuilderFactory;

/**
 * 自动发送传真job
 * @author yangbin
 *
 */
public class FaxSendJob implements Runnable {
	Log log = LogFactory.getLog(this.getClass());
	private EbkFaxTaskService ebkFaxTaskService;
	private EbkCertificateService ebkCertificateService;
	private BCertificateTargetService bCertificateTargetService;
	private FaxService faxServiceProxy;
	
	public void setFaxServiceProxy(FaxService faxServiceProxy) {
		this.faxServiceProxy = faxServiceProxy;
	}

	@Override
	public void run() {
		if(Constant.getInstance().isJobRunnable() && Constant.getInstance().getFaxSenderEnabled()){
				autoSendFax();
		}
	}
	
	private void autoSendFax(){
		List<EbkFaxTask> list = ebkFaxTaskService.selectSendFaxTask();
		log.info("ebkfaxTask size:"+list.size());
		File file = null;
		if(!list.isEmpty()){
			for(EbkFaxTask task:list){
				try {
					EbkCertificate ebkCertificate = ebkCertificateService.selectEbkCertificateDetailByPrimaryKey(task.getEbkCertificateId());
					EbkCertBuilder builder = EbkCertBuilderFactory.create(ebkCertificate);
					String path = Constant.getTempDir()+"/"+ebkCertificate.getEbkCertificateId()+new Date().getTime()+".html";
					file = new File(path);
					FileWriter writer = null;
				
					writer = new FileWriter(file);
					builder.makeCertContent(ebkCertificate,writer);
					Fax fax = new Fax();
					SupBCertificateTarget target = bCertificateTargetService
							.getBCertificateTargetByTargetId(ebkCertificate
									.getTargetId());
					FaxConvertHelper.initFax(ebkCertificate, fax, target);
					String operateName="SystemJob";
					if(!StringUtil.isEmptyString(task.getSendUser())){
						operateName=task.getSendUser();
					}
					Long faxSendId = faxServiceProxy.addOrdFaxSend(fax,task,operateName );
					log.info("faxSendId::::"+faxSendId+"    certificate:"+ebkCertificate.getEbkCertificateId());
					if(faxSendId>0){
						FaxSender sender = new FaxSender(fax.getToFax(), String.valueOf(faxSendId), path,FaxSender.PNG);
						//门票需要横着放
						if(ebkCertificate.isTicket()){
							sender.setWidth((long)PageSize.A4.getHeight());
						}
						boolean result = sender.send();
						//发送失败，还原任务
						if(!result) {
							task.setSendCount(task.getSendCount()+1L);
							ebkFaxTaskService.updateEbkFaxTask(task, operateName, "发送失败 待再次发送");
						}
					}
				} catch (Exception e) {
					log.error("ebkFaxTaskId::::"+task.getEbkFaxTaskId());
					e.printStackTrace();
				} 
			}
		}
	}

	
	public void setEbkFaxTaskService(EbkFaxTaskService ebkFaxTaskService) {
		this.ebkFaxTaskService = ebkFaxTaskService;
	}
	
	

	public void setEbkCertificateService(EbkCertificateService ebkCertificateService) {
		this.ebkCertificateService = ebkCertificateService;
	}
	

	public void setbCertificateTargetService(
			BCertificateTargetService bCertificateTargetService) {
		this.bCertificateTargetService = bCertificateTargetService;
	}
 
}
