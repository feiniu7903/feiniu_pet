package com.lvmama.job;

import com.lvmama.comm.TaskServiceInterface;
import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.jinjiang.JinjiangClient;
import com.lvmama.jinjiang.service.JinjiangProductService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.Date;

/**
 * 锦江之星 更新产品价格日历
 */
public class JinjiangProductUpdateJob implements TaskServiceInterface, Serializable {
	private static final Log log = LogFactory.getLog(JinjiangProductUpdateJob.class);
	
	private JinjiangProductService  jinjiangProductService;

    public TaskResult execute(Long logId,String parameter) throws Exception {
        TaskResult result = new TaskResult();
        run();
        result.setStatus(1);
        return result;
    }

	public void run() {
//		if(Constant.getInstance().isJobRunnable()){
            log.info("JinJiangProductSycJob started!");
            long runTime = System.currentTimeMillis();
            if (jinjiangProductService == null) {
                jinjiangProductService = (JinjiangProductService)SpringBeanProxy.getBean("jinjiangProductService");
            }
            try {
                Date updateTimeStart = jinjiangProductService.getSycTime(JinjiangClient.keyPrice);
                Date updateTimeEnd = new Date();
                jinjiangProductService.updateAllProductTimePrices(updateTimeStart, updateTimeEnd);
            } catch (Exception e) {
                log.error("JinJiangProductSycJob Exception:", e);
            }
            runTime = (System.currentTimeMillis() - runTime) / 1000;
            log.info("JinJiangProductSycJob ended! runTime:" + runTime);
//        }
	}

	public void setJinjiangProductService(
			JinjiangProductService jinjiangProductService) {
		this.jinjiangProductService = jinjiangProductService;
	}
	
}
