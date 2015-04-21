package com.lvmama.market.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mark.MarkActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-12-12<p/>
 * Time: 上午11:05<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class MarkActivityDAO extends BaseIbatisDAO {

    @SuppressWarnings("unchecked")
    public List<MarkActivity> getMarkActivityList(Map<String, Object> paramMap) {

        return super.queryForList("MARK_ACTIVITY.selectMarkActivityList",paramMap);
    }

    public Long saveMarkActivity(MarkActivity markActivity) {
        return (Long) super.insert("MARK_ACTIVITY.insertMarkActivity", markActivity);
    }

    public int deleteMarkActivity(Long actId) {
        return super.delete("MARK_ACTIVITY.deleteMarkActivity", actId);
    }

    public Long getMarkActivityCount(Map<String, Object> paramMap) {
        return (Long)super.queryForObject("MARK_ACTIVITY.selectMarkActivityCount",paramMap);
    }

    public MarkActivity getMarkActivity(Long actId) {
        return (MarkActivity)super.queryForObject("MARK_ACTIVITY.selectMarkActivity",actId);
    }

    public void updateMarkActivity(MarkActivity markActivity) {
        super.update("MARK_ACTIVITY.updateMarkActivity",markActivity);

    }

    public List<MarkActivity> getSendList() {
        return super.queryForList("MARK_ACTIVITY.selectSendList");
    }
}
