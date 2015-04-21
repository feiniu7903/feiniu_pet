package com.lvmama.comm.bee.service.ord;
/**
 * @author shangzhengyuan
 * @description 发送合同及短信接口
 */
import com.lvmama.comm.bee.po.ord.OrdOrder;

public interface SendContractEmailService {
	 
	public void sendCancelContractSms(OrdOrder order) ;
}
