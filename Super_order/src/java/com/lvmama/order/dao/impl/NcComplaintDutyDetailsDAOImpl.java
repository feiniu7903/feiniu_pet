package com.lvmama.order.dao.impl;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.NcComplaintDutyDetails;
import com.lvmama.order.dao.NcComplaintDutyDetailsDAO;

import java.util.List;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-11-6<p/>
 * Time: 下午4:37<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class NcComplaintDutyDetailsDAOImpl extends BaseIbatisDAO implements NcComplaintDutyDetailsDAO {


    public Long insertNcComplaintDutyDetails(NcComplaintDutyDetails ncComplaintDutyDetails) {
        return (Long) super.insert("NC_COMPLAINT_DUTY_DETAILS.insertNcComplaintDutyDetails", ncComplaintDutyDetails);
    }

    public List<NcComplaintDutyDetails> selectNcComplaintDutyDetailsByDutyId(Long dutyId) {
        return super.queryForList("NC_COMPLAINT_DUTY_DETAILS.selectNcComplaintDutyDetailsList",dutyId);
    }

    public int deleteNcComplaintDutyDetails(Long dutyId) {
        return super.delete("NC_COMPLAINT_DUTY_DETAILS.deleteNcComplaintDutyDetails",dutyId);
    }
}
