package com.lvmama.comm.bee.service.pass;

import java.util.List;

import com.lvmama.comm.bee.po.pass.PassCode;

/**
 * 通关产品自动履行服务
 * 
 * @author lipengcheng
 * 
 */
public interface OrderAutoPerformService {
	/**
	 * 通关产品订单自动履行方法
	 */
	public void autoPerform();
	
	public void autoPerform(List<PassCode> passCodeList);
}
