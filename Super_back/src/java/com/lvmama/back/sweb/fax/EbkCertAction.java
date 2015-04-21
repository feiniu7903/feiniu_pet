/**
 * 
 */
package com.lvmama.back.sweb.fax;

import java.io.ByteArrayOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkFaxTask;
import com.lvmama.comm.bee.po.ebooking.EbkTask;
import com.lvmama.comm.bee.service.ebooking.EbkCertificateService;
import com.lvmama.comm.bee.service.ebooking.EbkFaxTaskService;
import com.lvmama.comm.bee.service.ebooking.EbkTaskService;
import com.lvmama.comm.bee.service.fax.FaxService;
import com.lvmama.comm.bee.vo.ord.Fax;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.pdf.PdfUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.cert.builder.EbkCertBuilder;
import com.lvmama.comm.vo.cert.builder.EbkCertBuilderFactory;
import com.lvmama.comm.work.builder.WorkOrderFinishedBiz;

/**
 * @author yangbin
 * 
 */
@Results({ @Result(name = "showFaxPage", location = "/WEB-INF/pages/back/fax/faxCert.jsp") })
public class EbkCertAction extends BackBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8985471463271293604L;
	private EbkCertificateService ebkCertificateService;
	private Long faxTaskId;
	private Long[] faxTaskIds;
	private EbkFaxTask ebkFaxTask;
	private EbkCertificate ebkCertificate;
	private EbkFaxTaskService ebkFaxTaskService;
	private FaxService faxServiceProxy;
	private WorkOrderFinishedBiz workOrderFinishedProxy;
	private Fax fax;
	private EbkTaskService ebkTaskService;

	@Action("/fax/showFax")
	public String doShow() {
		ebkFaxTask = ebkFaxTaskService.getByEbkFaxTaskId(faxTaskId);
		ebkCertificate = ebkCertificateService
				.selectEbkCertificateDetailByPrimaryKeyAndValid(ebkFaxTask
						.getEbkCertificateId());
		EbkTask task = ebkTaskService.selectByEbkCertificateId(ebkFaxTask
				.getEbkCertificateId());
		if (task != null) {
			ebkCertificate.setEbkTask(task);
		}
		return "showFaxPage";
	}

	/**
	 * 显示凭证内容
	 * 
	 * @return
	 */
	public String getFaxCertContent() {
		try {
			EbkCertBuilder builder = EbkCertBuilderFactory
					.create(ebkCertificate);
			return builder.makeCertContent(ebkCertificate);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	/**
	 * 下载传真为pdf文档
	 */
	@Action("/fax/downloadPdf")
	public void downloadPdf() {
		try {
			getResponse().reset();
			getResponse().setContentType("application/pdf");
			getResponse().setHeader("content-disposition",
					"attachment; filename=" + faxTaskId + ".pdf");
			ByteArrayOutputStream baos = null;
			this.doShow();
			String faxContent = this.getFaxCertContent();
			baos = PdfUtil.createPdfFile(faxContent);
			if (baos == null) {
				baos = new ByteArrayOutputStream();
			}
			baos.writeTo(getResponse().getOutputStream());
			baos.close();
		} catch (Exception e) {
			log.info("downloadPdf:" + e);
			e.printStackTrace();
		}
	}

	/**
	 * 操作发送传真
	 * 
	 * @throws Exception
	 */
	@Action("/fax/sendFax")
	public void doSend() throws Exception {
		JSONResult result = new JSONResult();
		String info = "";
		try {
			EbkFaxTask faxTask = null;
			for (Long ebkFaxTaskId : faxTaskIds) {
				faxTask = ebkFaxTaskService.getByEbkFaxTaskId(ebkFaxTaskId);
				if (!faxTask.hasAgainSend()) {
					EbkCertificate ebkCertificate = ebkCertificateService.selectEbkCertificateDetailByPrimaryKeyAndValid(faxTask.getEbkCertificateId());
					fax = (fax == null) ? new Fax() : fax;
					if (StringUtils.isNotEmpty(fax.getToFax())) {
						ebkCertificate.setToFax(fax.getToFax());
						ebkCertificateService.update(ebkCertificate);
						faxTask.setAgainSend("true");
						faxTask.setSendUser(this.getSessionUserName());
						ebkFaxTaskService.updateEbkFaxTask(faxTask, this.getSessionUserName(), "修改传真任务 改为立即发送");

						// add by zhangwengang 2013/06/03 支付后传真处理（出境计调）自动完成任务 start
						if (null != getSession().getAttribute("workTaskId")) {
							String paramId = getSession().getAttribute("workTaskId").toString();
							workOrderFinishedProxy.finishWorkOrder(Long.valueOf(paramId), "", getSessionUser().getUserName());
							getSession().removeAttribute("workTaskId");
						}
						// add by zhangwengang 2013/06/03 支付后传真处理（出境计调）自动完成任务 end
					}
				} else {
					info += ebkFaxTaskId + ";";
				}
			}
			if (StringUtils.isNotEmpty(info)) {
				result.raise("编号：" + info + "已立即发送");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			result.raise(ex);
		}
		result.output(getResponse());
	}

	private Long ebkFaxTaskId;

	public Long getEbkFaxTaskId() {
		return ebkFaxTaskId;
	}

	public void setEbkFaxTaskId(Long ebkFaxTaskId) {
		this.ebkFaxTaskId = ebkFaxTaskId;
	}

	@Action("/fax/sendFaxOver")
	public void sendFaxOver() {
		JSONResult result = new JSONResult();
		try {
			// Assert.notNull(ebkFaxTask);
			Assert.notNull(ebkFaxTaskId, "传真不存在");
			EbkFaxTask faxTask = ebkFaxTaskService
					.getByEbkFaxTaskId(ebkFaxTaskId);

			fax = new Fax();
			fax.setSendStatus(Constant.EBK_FAX_TASK_STATUS.FAX_SEND_STATUS_MANUAL
					.getStatus());

			Assert.notNull(faxTask, "传真不存在");
			Long faxSendId = faxServiceProxy.addOrdFaxSend(fax, faxTask,
					getSessionUserNameAndCheck());
			if (faxSendId < 1) {
				result.raise("发送操作失败");
			}
		} catch (Exception ex) {
			result.raise(ex);
		}
		result.output(getResponse());
	}

	public void setFaxTaskId(Long faxTaskId) {
		this.faxTaskId = faxTaskId;
	}

	public void setEbkCertificate(EbkCertificate ebkCertificate) {
		this.ebkCertificate = ebkCertificate;
	}

	public EbkFaxTask getEbkFaxTask() {
		return ebkFaxTask;
	}

	public EbkCertificate getEbkCertificate() {
		return ebkCertificate;
	}

	public void setEbkCertificateService(
			EbkCertificateService ebkCertificateService) {
		this.ebkCertificateService = ebkCertificateService;
	}

	public void setEbkFaxTaskService(EbkFaxTaskService ebkFaxTaskService) {
		this.ebkFaxTaskService = ebkFaxTaskService;
	}

	public Fax getFax() {
		return fax;
	}

	public void setFax(Fax fax) {
		this.fax = fax;
	}

	public void setFaxTaskIds(Long[] faxTaskIds) {
		this.faxTaskIds = faxTaskIds;
	}

	public void setFaxServiceProxy(FaxService faxServiceProxy) {
		this.faxServiceProxy = faxServiceProxy;
	}

	public void setEbkTaskService(EbkTaskService ebkTaskService) {
		this.ebkTaskService = ebkTaskService;
	}

	public WorkOrderFinishedBiz getWorkOrderFinishedProxy() {
		return workOrderFinishedProxy;
	}

	public void setWorkOrderFinishedProxy(
			WorkOrderFinishedBiz workOrderFinishedProxy) {
		this.workOrderFinishedProxy = workOrderFinishedProxy;
	}

}
