/**
 * 
 */
package com.lvmama.bee.web.ebooking;

import java.io.ByteArrayOutputStream;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.bee.web.EbkBaseAction;
import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkTask;
import com.lvmama.comm.bee.service.ebooking.EbkCertificateService;
import com.lvmama.comm.bee.service.ebooking.EbkTaskService;
import com.lvmama.comm.utils.pdf.PdfUtil;
import com.lvmama.comm.vo.cert.builder.EbkCertBuilder;
import com.lvmama.comm.vo.cert.builder.EbkCertBuilderFactory;

/**
 * @author yangbin
 *
 */
@Results({
	@Result(name="showCert",location="/WEB-INF/pages/ebooking/task/showCert.jsp")
})
public class EbkCertAction extends EbkBaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5218480949972399130L;
	private Long certificateId;
	private EbkCertificateService ebkCertificateService;
	private EbkTaskService ebkTaskService;
	private EbkCertificate ebkCertificate;

	/**
	 * 显示一个凭证的内容
	 */
	@Action("/ebooking/task/showCert")
	public String doShow()throws Exception{
		//权限添加
		ebkCertificate = ebkCertificateService.selectEbkCertificateDetailByPrimaryKey(certificateId);
		if(ebkCertificate==null||!this.getSessionUser().getSupplierId().equals(ebkCertificate.getSupplierId())) {
			this.sendAjaxMsg("没有此订单");
			return "";
		}
		EbkTask task = ebkTaskService.selectByEbkCertificateId(certificateId);
		if(task != null) {
			ebkCertificate.setEbkTask(task);
		}
		return "showCert";
	}
	/**
	 * 显示凭证内容
	 * @return
	 */
	public String getCertContent(){
		try{
			EbkCertBuilder builder = EbkCertBuilderFactory.create(ebkCertificate);
			return builder.makeCertContent(ebkCertificate);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "";
	}
	/**
	 * 下载传真为pdf文档
	 */
	@Action("/ebooking/task/downloadPdf")
	public void downloadPdf(){
		try {
			this.doShow();
			if(ebkCertificate == null || ebkCertificate.getEbkTask() == null) {
				return;
			}
			getResponse().reset();
			getResponse().setContentType("application/pdf");
			getResponse().setHeader("content-disposition", "attachment; filename="+ebkCertificate.getEbkTask().getOrderId()+"_"+ebkCertificate.getEbkCertificateId()+".pdf");
			ByteArrayOutputStream baos = null;
			this.doShow();
			String faxContent = this.getCertContent();
			baos = PdfUtil.createPdfFile(faxContent);
			if(baos == null){
			  baos = new ByteArrayOutputStream(); 
			}
			baos.writeTo(getResponse().getOutputStream());
			baos.close();
		} catch (Exception e) {
			log.info("downloadPdf:"+e);
			e.printStackTrace();
		}
	}
	public void setEbkCertificateService(EbkCertificateService ebkCertificateService) {
		this.ebkCertificateService = ebkCertificateService;
	}

	public void setCertificateId(Long certificateId) {
		this.certificateId = certificateId;
	}

	public EbkTaskService getEbkTaskService() {
		return ebkTaskService;
	}

	public void setEbkTaskService(EbkTaskService ebkTaskService) {
		this.ebkTaskService = ebkTaskService;
	}
	public EbkCertificate getEbkCertificate() {
		return ebkCertificate;
	}
	public void setEbkCertificate(EbkCertificate ebkCertificate) {
		this.ebkCertificate = ebkCertificate;
	}
	
}
