package com.lvmama.pet.job.quartz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.lvmamacard.LvmamaStoredCard;
import com.lvmama.comm.pet.service.lvmamacard.LvmamacardService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class LvmamaCardJob implements Runnable{
	private static final Logger LOG = Logger.getLogger(LvmamaCardJob.class);

 	private LvmamacardService lvmamacardService;
	@Override
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			LOG.info("LvmamaCardJob Begin:===>");
			//获取可点评的订单信息
			List<LvmamaStoredCard>	overLvmamacardList=lvmamacardService.getOverTimeStoredCard(new Date());
			List<String> cardNoArray=new ArrayList<String>() ;
			for(LvmamaStoredCard lvmama:overLvmamacardList){
				if(StringUtil.isNotEmptyString(lvmama.getCardNo())){
					cardNoArray.add(lvmama.getCardNo());
				}
			}
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("cardNoArray", cardNoArray);
			map.put("status", Constant.CARD_STATUS.FINISHED.getCode());
			lvmamacardService.updateByParamForLvmamaStoredCard(map);
			LOG.info("LvmamaCardJob End");
		}
		
	}
	public void setLvmamacardService(LvmamacardService lvmamacardService) {
		this.lvmamacardService = lvmamacardService;
	}
	
}
