package com.lvmama.back.job;

import com.lvmama.comm.bee.service.market.MarkActivityService;
import com.lvmama.comm.vo.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-12-18<p/>
 * Time: 下午5:44<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class MarkActivitySendMailJob implements Runnable {

    Log log = LogFactory.getLog(this.getClass());
    private MarkActivityService markActivityService;
    private Long cleanupDays;

    /**
     * 每天零晨3点，把第二天发要送的邮件放到发送队列中
     * 清理cleanupDays天以前的发送日志
     */
    public void run() {
        if (Constant.getInstance().isJobRunnable()) {
            markActivityService.sendMailBatch();
            markActivityService.cleanupSendLog(cleanupDays);
        }
    }

    public MarkActivityService getMarkActivityService() {
        return markActivityService;
    }

    public void setMarkActivityService(MarkActivityService markActivityService) {
        this.markActivityService = markActivityService;
    }

    public Long getCleanupDays() {
        return cleanupDays;
    }

    public void setCleanupDays(Long cleanupDays) {
        this.cleanupDays = cleanupDays;
    }
}
