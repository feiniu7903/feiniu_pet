package com.lvmama.pet.job.quartz;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.money.StoredCard;
import com.lvmama.comm.pet.service.money.StoredCardService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 处理过期的储值卡.
 * @author Libo Wang
 *
 */
public class AutoFinishedStoredCardJob implements Runnable {

	private static final Log log = LogFactory.getLog(AutoFinishedStoredCardJob.class);
	/**
	 * 储值卡Service.
	 */
	private StoredCardService storedCardService;
	
	@Override
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("Auto StoredCard finishd launched.");
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("endOverTime",DateUtil.getDayStart(new Date()));
			List<StoredCard> list=storedCardService.queryCardListByParam(map);
			if (list != null && list.size() > 0) {
				log.info("########## Run AutoFinishedCardJob");
				for (StoredCard card : list) {
					card.setStatus(Constant.STORED_CARD_ENUM.FINISHED.name());
					storedCardService.updateStoredCard(card,"SYSTEM","STATUS");
				}
			}
		}
	}

	public StoredCardService getStoredCardService() {
		return storedCardService;
	}

	public void setStoredCardService(StoredCardService storedCardService) {
		this.storedCardService = storedCardService;
	}

}
