package com.lvmama.comm.bee.proxy;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ebooking.EbkFaxSend;
import com.lvmama.comm.bee.po.ebooking.EbkFaxTask;
import com.lvmama.comm.bee.service.ebooking.EbkFaxTaskService;
import com.lvmama.comm.bee.service.fax.FaxService;
import com.lvmama.comm.bee.vo.ord.Fax;
import com.lvmama.comm.vo.Constant;

/**
 * 传真业务
 * 
 * @author clj
 * 
 */
public class FaxServiceProxy implements FaxService {
	private static final Log log = LogFactory.getLog(FaxServiceProxy.class);
	private EbkFaxTaskService ebkFaxTaskService;
	
	/**
	 * 保存传真事件数据
	 */
	public Long addOrdFaxSend(Fax fax,EbkFaxTask ebkFaxTask, String operatorName) {
		Date sendTime = new Date();
		EbkFaxSend send = new EbkFaxSend();
		send.setToFax(fax.getToFax());
		send.setSendStatus(fax.getSendStatus());
		send.setOperatorName(send.getOperatorName());
		send.setCreateTime(sendTime);
		send.setOperatorName(operatorName);
		send.setEbkFaxTaskId(ebkFaxTask.getEbkFaxTaskId());
		//send.setSendTime(new Date());回调时填充
		if (StringUtils.isEmpty(send.getSendStatus())) {
			send.setSendStatus(Constant.EBK_FAX_TASK_STATUS.FAX_TASK_STATUS_SENT.getStatus());
		}
		Long faxSendId = ebkFaxTaskService.insertOrdFaxSend(send);
		return faxSendId;
	}

	@Override
	public boolean updateOrdFaxSendStatus(EbkFaxSend ebkFaxSend, String logContent) {
		return ebkFaxTaskService.updateEbkFaxSend(ebkFaxSend, logContent);
	}

	public void setEbkFaxTaskService(EbkFaxTaskService ebkFaxTaskService) {
		this.ebkFaxTaskService = ebkFaxTaskService;
	}

	@Override
	public boolean updateUserMemoStatus(Long orderId,
			String updateUserMemoStatus) {
		Integer row = this.ebkFaxTaskService.updateUserMemoStatus(orderId, updateUserMemoStatus);
		return row > 0;
	}
       
}
