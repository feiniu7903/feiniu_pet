package com.lvmama.back.sweb.callback;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ebooking.EbkFaxSend;
import com.lvmama.comm.bee.po.ebooking.EbkFaxTask;
import com.lvmama.comm.bee.po.fax.OrdFaxRecv;
import com.lvmama.comm.bee.proxy.FaxServiceProxy;
import com.lvmama.comm.bee.service.ebooking.EbkFaxTaskService;
import com.lvmama.comm.bee.service.fax.OrdFaxRecvService;
import com.lvmama.comm.vo.Constant;


@Results({
	@Result(name = "success", type="freemarker", location = "/WEB-INF/pages/back/success.ftl"),
	@Result(name = "error", type="freemarker", location = "/WEB-INF/pages/back/error.ftl")
	})
public class FaxCallbackAction extends BaseAction {
	private static final long serialVersionUID = -5583242083359125655L;
	private static final Logger logger = Logger.getLogger(FaxCallbackAction.class);
	private FaxServiceProxy faxServiceProxy;
	//发送序列号,对应OrdFaxSend的faxSendId.
	private String serialno;
	//发送状态.
	private String status;
	//二维码.
	private String barcode;
	//接收时间.
	private Long recvtime;
	//主叫号码,
	private String callerid;
	
	private String pageNum;
	//回传传真文件路径.
	private String url;
	private OrdFaxRecvService ordFaxRecvService;

	private EbkFaxTaskService ebkFaxTaskService;

	@Action("/faxStatus")
	public String execute() throws Exception {
		logger.info("FaxCallback faxSendId:"+serialno+",sendStatus:"+status);
		if (StringUtils.isNotEmpty(serialno) && StringUtils.isNotEmpty(status)) {
			EbkFaxSend orderFaxSend = new EbkFaxSend();
			orderFaxSend.setEbkFaxSendId(Long.parseLong(serialno));
			orderFaxSend.setSendStatus(status);
			orderFaxSend.setSendTime(new Date());
			orderFaxSend.setOperatorName("SYSTEM");
			faxServiceProxy.updateOrdFaxSendStatus(orderFaxSend, "回传发送结果["+Constant.EBK_FAX_TASK_STATUS.getCnNameByStatus(status)+"]");
			resendFax(orderFaxSend.getEbkFaxSendId(), status);
			return SUCCESS;
		}
		logger.error("faxStatus Error：parameters is invalid");
		return ERROR;
	}

	/**
	 * 重发
	 * @author: ranlongfei 2014-1-23 下午2:09:34
	 * @param sendId
	 * @param taskSendStatus
	 */
	private void resendFax(Long sendId, String taskSendStatus) {
		try {
			if(sendId == null) {
				return;
			}
			if(StringUtils.isEmpty(taskSendStatus)) {
				return;
			}
			if(Constant.EBK_FAX_TASK_STATUS.FAX_TASK_STATUS_DIALTONE.getStatus().equals(taskSendStatus)
					|| Constant.EBK_FAX_TASK_STATUS.FAX_TASK_STATUS_BACKTONE.getStatus().equals(taskSendStatus)
					|| Constant.EBK_FAX_TASK_STATUS.FAX_TASK_STATUS_NOTONE.getStatus().equals(taskSendStatus)
					|| Constant.EBK_FAX_TASK_STATUS.FAX_TASK_STATUS_BUSYLINE.getStatus().equals(taskSendStatus)
					) {
				EbkFaxTask t = ebkFaxTaskService.selectEbkFaxTaskByEbkFaxSendId(sendId);
				if(t == null) {
					return ;
				}
				if("true".equals(t.getDisableSend())) {
					return;
				}
				if(t.getSendCount() != null && t.getSendCount() >= 3) {
					return;
				}
				EbkFaxTask taskTmp = new EbkFaxTask();
				taskTmp.setEbkFaxTaskId(t.getEbkFaxTaskId());
				taskTmp.setAgainSend("true");
				ebkFaxTaskService.updateEbkFaxTask(taskTmp, "SYSTEM", "传真发送失败，自动设置为再次发送");
			}
		} catch(Exception e) {
			logger.error("faxStatus callback -> resend Error " + e.getMessage());
		}
	}
	
	@Action("/faxRecv")
	public String faxRecv() throws Exception{
		logger.info("FaxRecvCallback callerid:"+callerid+",barcode:"+barcode);
		if (recvtime!=null && StringUtils.isNotEmpty(callerid) && StringUtils.isNotEmpty(url)) {
			OrdFaxRecv ordFaxRecv = new OrdFaxRecv();
			ordFaxRecv.setCallerId(callerid);
			ordFaxRecv.setRecvTime(new Date(recvtime));
			ordFaxRecv.setFileUrl(url);
			ordFaxRecv.setCreateTime(new Date());
			ordFaxRecv.setBarcode(barcode);
			ordFaxRecv.setRecvStatus(Constant.FAX_RECV_STATUS.NOT_LINKED.name());
			ordFaxRecv.setPageNum(pageNum);
			ordFaxRecv.setOperatorName("SYSTEM");
			ordFaxRecvService.receiveOrdFaxRecv(ordFaxRecv);
			return SUCCESS;
		}
		logger.error("faxRecv Error：parameters is invalid");
		return ERROR;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCallerid() {
		return callerid;
	}

	public void setCallerid(String callerid) {
		this.callerid = callerid;
	}

	public OrdFaxRecvService getOrdFaxRecvService() {
		return ordFaxRecvService;
	}

	public void setOrdFaxRecvService(OrdFaxRecvService ordFaxRecvService) {
		this.ordFaxRecvService = ordFaxRecvService;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getRecvtime() {
		return recvtime;
	}

	public void setRecvtime(Long recvtime) {
		this.recvtime = recvtime;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	
	public void setFaxServiceProxy(FaxServiceProxy faxServiceProxy) {
		this.faxServiceProxy = faxServiceProxy;
	}

	public EbkFaxTaskService getEbkFaxTaskService() {
		return ebkFaxTaskService;
	}

	public void setEbkFaxTaskService(EbkFaxTaskService ebkFaxTaskService) {
		this.ebkFaxTaskService = ebkFaxTaskService;
	}

}
