package com.lvmama.back.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.service.prod.ProdPlaceTagService;
import com.lvmama.comm.bee.service.prod.ProdProductTagService;
import com.lvmama.comm.vo.Constant;
public class ProdTagDeleteTimeOutJob {
	
	private static final Log log = LogFactory.getLog(ProdTagDeleteTimeOutJob.class);
	private ProdProductTagService prodProductTagService;
	private ProdPlaceTagService prodPlaceTagService;
	
	public void run(){
		if (Constant.getInstance().isJobRunnable()) {
			long begin = System.currentTimeMillis();
			/**
			 * 删除过了今天有效期的产品标签
			 */
			this.prodProductTagService.deleteProdProductTagTimeOut();
			/**
			 * 删除过了今天有效期的目的地标签
			 */
			this.prodPlaceTagService.deleteProdPlaceTagTimeOut();
		}
	}

	public void setProdProductTagService(ProdProductTagService prodProductTagService) {
		this.prodProductTagService = prodProductTagService;
	}

	public void setProdPlaceTagService(ProdPlaceTagService prodPlaceTagService) {
		this.prodPlaceTagService = prodPlaceTagService;
	}

}
