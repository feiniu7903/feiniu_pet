package com.lvmama.comm.pet.service.fin;

public interface FinGLBizService {
	/**
	 * 触发对账操作
	 */
	public void send();
	
	public void receive(final String fileStr);
	
	public void initCode();
}
