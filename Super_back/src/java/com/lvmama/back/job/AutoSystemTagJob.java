package com.lvmama.back.job;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.service.prod.ProdProductTagService;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.vo.Constant;

public class AutoSystemTagJob {
	/**
	 * Tag Service
	 */
	private ProdProductTagService prodProductTagService;
	/**
	 * 优惠券批次远程服务
	 */
	protected MarkCouponService markCouponService;
	
	private static final Log logger = LogFactory.getLog(AutoSystemTagJob.class);
	
	public void run(){
		if (Constant.getInstance().isJobRunnable()) {
			logger.info("begin system tag.");
			//系统自动添加/删除银行活动Tag==========Begin==========
			List<ProdProductTag> prodProductTags=markCouponService.checkProductTag(null);
			List<Long> productIds =new ArrayList<Long>();
			if(prodProductTags!=null){
				for(ProdProductTag prodProductTag:prodProductTags){
					productIds.add(prodProductTag.getProductId());
				}
			}
			prodProductTagService.addSystemProgProductTags(prodProductTags, productIds, Constant.PROD_TAG_NAME.BANK_ACTIVITY.getCnName());
			//系统自动添加/删除银行活动Tag==========End==========
			logger.info("end system tag.");
		}
	}

	public void setProdProductTagService(ProdProductTagService prodProductTagService) {
		this.prodProductTagService = prodProductTagService;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}
}
