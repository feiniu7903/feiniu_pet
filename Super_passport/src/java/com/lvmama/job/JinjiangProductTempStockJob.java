package com.lvmama.job;

import com.lvmama.comm.TaskServiceInterface;
import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.jinjiang.service.JinjiangProductService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;

/**
 * 锦江之星 对接产品临时入表定时任务
 *
 * @author yanzhirong
 */
public class JinjiangProductTempStockJob implements TaskServiceInterface, Serializable {
    private static final Log log = LogFactory.getLog(JinjiangProductTempStockJob.class);
    private JinjiangProductService jinjiangProductService;

    public TaskResult execute(Long logId,String parameter) throws Exception {
        TaskResult result = new TaskResult();
        run();
        result.setStatus(1);
        return result;
    }

    public void run() {
//        if (Constant.getInstance().isJobRunnable()) {
            log.info("JinjiangProductTempStockJob started!");
            long runTime = System.currentTimeMillis();
            if (jinjiangProductService == null) {
                jinjiangProductService = (JinjiangProductService) SpringBeanProxy.getBean("jinjiangProductService");
            }
            try {
                jinjiangProductService.saveTempStockProduct();
            } catch (Exception e) {
                log.error("JinjiangProductTempStockJob Exception", e);
            }
            runTime = (System.currentTimeMillis() - runTime) / 1000;
            log.info("JinjiangProductTempStockJob ended! runTime:" + runTime);
//        }
    }

    public void setJinjiangProductService(
            JinjiangProductService jinjiangProductService) {
        this.jinjiangProductService = jinjiangProductService;
    }
}
