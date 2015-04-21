package com.lvmama.order.dao.impl;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.NcComplaintDuty;
import com.lvmama.order.dao.NcComplaintDutyDAO;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-11-6<p/>
 * Time: 上午11:54<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class NcComplaintDutyDAOImpl extends BaseIbatisDAO implements NcComplaintDutyDAO {

    public Long insertNcComplaintDuty(NcComplaintDuty ncComplaintDuty) {
        return (Long) super.insert("NC_COMPLAINT_DUTY.insertNcComplaintDuty", ncComplaintDuty);
    }

    public int updateNcComplaintDuty(NcComplaintDuty ncComplaintDuty) {
        return super.update("NC_COMPLAINT_DUTY.updateNcComplaintDuty", ncComplaintDuty);
    }

    public NcComplaintDuty getNcComplaintDutyByComplaintId(Long complaintId) {
        return (NcComplaintDuty) super.queryForObject("NC_COMPLAINT_DUTY.selectNcComplaintDutyByComplaintId", complaintId);
    }
    public NcComplaintDuty getNcComplaintDutyByDutyId(Long dutyId) {
        return (NcComplaintDuty) super.queryForObject("NC_COMPLAINT_DUTY.selectNcComplaintDutyByDutyId", dutyId);
    }

    public NcComplaintDuty getNcComplaintDuty(Map<String, Object> params) {
        return (NcComplaintDuty) super.queryForObject("NC_COMPLAINT_DUTY.selectNcComplaintDuty", params);
    }
}
