package com.lvmama.order.dao;

import com.lvmama.comm.bee.po.ord.NcComplaintDutyDetails;

import java.util.List;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-11-6<p/>
 * Time: 下午4:33<p/>
 * Email:kouhongyu@163.com<p/>
 */
public interface NcComplaintDutyDetailsDAO {

    public Long insertNcComplaintDutyDetails(NcComplaintDutyDetails ncComplaintDutyDetails);

    public List selectNcComplaintDutyDetailsByDutyId(Long dutyId);

    public int deleteNcComplaintDutyDetails(Long dutyId);
}
