package com.lvmama.order.dao.impl;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.NcComplaintTracking;
import com.lvmama.order.dao.NcComplaintTrackingDAO;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-10-31<p/>
 * Time: 上午11:16<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class NcComplaintTrackingDAOImpl extends BaseIbatisDAO implements NcComplaintTrackingDAO {

    public Long insertNcComplaintTracking(NcComplaintTracking ncComplaintTracking) {
        return (Long) super.insert("NC_COMPLAINT_TRACKING.insertNcComplaintTracking", ncComplaintTracking);
    }



    public List<NcComplaintTracking> selectNcComplaintTrackingList(Map<String,Object> params) {
        return super.queryForList("NC_COMPLAINT_TRACKING.selectNcComplaintTrackingList",params);
    }

    public Long getNcComplaintTrackingSequence() {
        return (Long)queryForObject("NC_COMPLAINT_TRACKING.selectNcComplaintTrackingSequence");
    }
}
