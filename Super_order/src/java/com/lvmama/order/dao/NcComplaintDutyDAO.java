package com.lvmama.order.dao;

import com.lvmama.comm.bee.po.ord.NcComplaintDuty;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-11-6<p/>
 * Time: 上午11:52<p/>
 * Email:kouhongyu@163.com<p/>
 */
public interface NcComplaintDutyDAO {

    public Long insertNcComplaintDuty(NcComplaintDuty ncComplaintDuty);

    public int updateNcComplaintDuty(NcComplaintDuty ncComplaintDuty);

    public NcComplaintDuty getNcComplaintDutyByComplaintId(Long complaintId);

    public NcComplaintDuty getNcComplaintDutyByDutyId(Long dutyId);

    public NcComplaintDuty getNcComplaintDuty(Map<String, Object> params);
}
