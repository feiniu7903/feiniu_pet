package com.lvmama.comm.bee.service.fax;

import com.lvmama.comm.bee.po.ebooking.EbkFaxSend;
import com.lvmama.comm.bee.po.ebooking.EbkFaxTask;
import com.lvmama.comm.bee.vo.ord.Fax;

/**
 * 传真业务
 * 
 * @author clj
 * 
 */
public interface FaxService {
	 
	/**
	 * 保存传真事件数据
	 */
	public Long addOrdFaxSend(Fax fax,EbkFaxTask ebkFaxTask, String operatorName);
 
	public boolean updateOrdFaxSendStatus(EbkFaxSend ebkFaxSend, String logContent);
	public boolean updateUserMemoStatus(Long orderId,String updateUserMemoStatus);
}
