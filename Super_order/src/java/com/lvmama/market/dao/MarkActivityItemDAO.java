package com.lvmama.market.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mark.MarkActivityItem;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-12-12<p/>
 * Time: 下午6:47<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class MarkActivityItemDAO extends BaseIbatisDAO {

    public MarkActivityItem getMarkActivityItemEmail(Long actId) {
        return (MarkActivityItem) super.queryForObject("MARK_ACTIVITY_ITEM.selectMarkActivityItemEmail",actId);

    }

    public Long saveMarkActivityItem(MarkActivityItem markActivityItem) {
        return (Long) super.insert("MARK_ACTIVITY_ITEM.insertMarkActivityItem", markActivityItem);
    }

    public int deleteMarkActivityItem(Long actId) {
        return super.delete("MARK_ACTIVITY_ITEM.deleteMarkActivityItem", actId);
    }

    public void updateMarkActivityItem(MarkActivityItem markActivityItem) {
        super.update("MARK_ACTIVITY_ITEM.updateMarkActivityItem",markActivityItem);

    }
}
