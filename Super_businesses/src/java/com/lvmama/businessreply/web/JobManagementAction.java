package com.lvmama.businessreply.web;

import com.lvmama.businessreply.job.RuichuangBuildXmlJob;
import com.lvmama.businessreply.job.RuichuangTicketsXmlJob;
import com.lvmama.businessreply.job.VipShop123Job;

@SuppressWarnings("serial")
public class JobManagementAction extends BaseAction {
	private VipShop123Job vipShopBuildXmlJob;
	private RuichuangBuildXmlJob ruichuangBuildXmlJob;
	private RuichuangTicketsXmlJob ruichuangTicketsXmlJob;
	private String serviceName;
	




	public void doVipShop123Job() throws Exception {
		if ("vipShopBuildXmlJob".equals(serviceName)) {
			vipShopBuildXmlJob.run();
		}
		if ("ruichuangBuildXmlJob".equals(serviceName)) {
			ruichuangBuildXmlJob.run();
		}
		if("ruichuangTicketsJob".equals(serviceName)){
			ruichuangTicketsXmlJob.run();
		}
	}
	


	public String getServiceName() {
		return serviceName;
	}



	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}



	public void setRuichuangBuildXmlJob(RuichuangBuildXmlJob ruichuangBuildXmlJob) {
		this.ruichuangBuildXmlJob = ruichuangBuildXmlJob;
	}



	public void setVipShopBuildXmlJob(VipShop123Job vipShopBuildXmlJob) {
		this.vipShopBuildXmlJob = vipShopBuildXmlJob;
	}



	public void setRuichuangTicketsXmlJob(RuichuangTicketsXmlJob ruichuangTicketsXmlJob) {
		this.ruichuangTicketsXmlJob = ruichuangTicketsXmlJob;
	}
	
}
