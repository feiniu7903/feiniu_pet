package com.lvmama.pet.job.quartz;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.service.prod.ProdContainerProductService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ContainerProductVO;

public class InsertContainerProductJob implements Runnable {
	private static Log LOG = LogFactory.getLog(InsertContainerProductJob.class);
	private ProdContainerProductService prodContainerProductService;

	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			LOG.info("Auto InsertContainerProductJob running.....");
			List<Long> productIds = prodContainerProductService.queryProductIdList();
			if (productIds != null && productIds.size() > 0) {
				StringBuffer sb = new StringBuffer("");
				for (Long productId : productIds) {
					try {
						ContainerProductVO containerProductVO = prodContainerProductService
								.createContainerProductVO(productId);
						prodContainerProductService
								.deleteNoUsedContainerProducts(
										containerProductVO
												.getContainerIdListAfter(),
										productId);
						prodContainerProductService
								.insertIntoCorrespondingContainers(
										containerProductVO, true);
					} catch (Exception ex) {
						sb.append(productId + ",");
					}
				}
				if (sb != null && sb.length() > 0) {
					LOG.error("错误的产品ID:" + sb.toString());
				}
			}
			prodContainerProductService.deleteContainerProductsByTagId(
					Constant.CONTAINER_ID_ON_SALE, Constant.TAG_ID_ON_SALE);
			prodContainerProductService.deleteContainerProductsByTagId(
					Constant.CONTAINER_ID_NEW_ARRIVAL,
					Constant.TAG_ID_NEW_ARRIVAL);
			prodContainerProductService
					.batchInsertTagContainerProduct(Constant.TAG_ID_ON_SALE);
			prodContainerProductService
					.batchInsertTagContainerProduct(Constant.TAG_ID_NEW_ARRIVAL);
			
			prodContainerProductService.deleteInvalidContainerProducts();
			LOG.info("Auto InsertContainerProductJob end.");
		}
	}

	public void setProdContainerProductService(
			ProdContainerProductService prodContainerProductService) {
		this.prodContainerProductService = prodContainerProductService;
	}

}
