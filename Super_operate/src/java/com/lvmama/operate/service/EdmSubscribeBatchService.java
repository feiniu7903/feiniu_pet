package com.lvmama.operate.service;

import com.lvmama.comm.pet.po.edm.EdmSubscribeBatch;
import com.lvmama.operate.job.model.UserEmailModel;

/**
 * EDM邮件发送批次服务接口
 */

public interface EdmSubscribeBatchService {
	/**
	 * 汉启发送通道
	 * 
	 * @param obj
	 * @param emailModel
	 * @param isAddJob
	 * @return
	 * @throws Exception
	 */
	EdmSubscribeBatch insert(EdmSubscribeBatch obj, UserEmailModel emailModel,
			boolean isAddJob) throws Exception;

	/**
	 * 锐致发送通道
	 * 
	 * @param obj
	 * @return
	 */
	EdmSubscribeBatch insert(EdmSubscribeBatch obj);
}
