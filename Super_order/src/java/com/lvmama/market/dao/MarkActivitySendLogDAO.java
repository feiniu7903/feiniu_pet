package com.lvmama.market.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mark.MarkActivitySendLog;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-12-19<p/>
 * Time: 下午3:26<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class MarkActivitySendLogDAO extends BaseIbatisDAO {
    public Long insertMarkActivitySendLog(MarkActivitySendLog markActivitySendLog) {
        return (Long) super.insert("MARK_ACTIVITY_SEND_LOG.insertMarkActivitySendLog", markActivitySendLog);
    }
    public List<String> getExcludeEmail(Map<String, Object> params){
        return super.queryForList("MARK_ACTIVITY_SEND_LOG.selectExcludeEmail",params,true);
    }

    public int cleanupSendLog(Long cleanupDays) {
        return super.delete("MARK_ACTIVITY_SEND_LOG.cleanupSendLog", cleanupDays);

    }

    public Long getSendAmountBySendOffTimes(Map<String, Object> prarm) {
        return (Long) super.queryForObject("MARK_ACTIVITY_SEND_LOG.selectSendAmountBySendOffTimes", prarm);
    }
}
