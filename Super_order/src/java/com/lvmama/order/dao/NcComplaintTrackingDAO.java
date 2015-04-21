package com.lvmama.order.dao;

import com.lvmama.comm.bee.po.ord.NcComplaintTracking;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-10-31<p/>
 * Time: 上午11:10<p/>
 * Email:kouhongyu@163.com<p/>
 */
public interface NcComplaintTrackingDAO {

    public Long insertNcComplaintTracking(NcComplaintTracking ncComplaintTracking);


    public List<NcComplaintTracking> selectNcComplaintTrackingList(Map<String,Object> params);

    public Long getNcComplaintTrackingSequence();
}
