package com.lvmama.order.dao.impl;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.NcComplaint;
import com.lvmama.comm.bee.po.ord.NcComplaintResult;
import com.lvmama.order.dao.NcComplaintDAO;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-10-29<p/>
 * Time: 下午2:30<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class NcComplaintDAOImpl extends BaseIbatisDAO implements NcComplaintDAO{

    public NcComplaintDAOImpl() {
        super();
    }


    public NcComplaint getNcComplaintByComplaintId(Long complaintId) {
        return (NcComplaint) super.queryForObject("NC_COMPLAINT.selectNcComplaintBycomplaintId", complaintId);
    }

    public Long insert(NcComplaint ncComplaint) {
        return (Long) super.insert("NC_COMPLAINT.insert", ncComplaint);
    }

    public Integer updateNcComplaint(NcComplaint ncComplaint) {
        return super.update("NC_COMPLAINT.updateNcComplaint", ncComplaint);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<NcComplaint> queryComplaintByParams(Map<String, Object> params) {
		return super.queryForList("NC_COMPLAINT.selectComplaintByCondition", params);
	}

	@Override
	public Long queryComplaintCount(Map<String, Object> params) {
		return (Long)queryForObject("NC_COMPLAINT.selectComplaintCount",params);
	}

	@Override
	public NcComplaintResult selectComplaintReusltByComplaintId(Long complaintId) {
		return (NcComplaintResult) super.queryForObject("NC_COMPLAINT_RESULT.selectComplaintResultBycomplaintId", complaintId);
	}

    public Integer updateNcComplaintAll(NcComplaint ncComplaint) {
        return super.update("NC_COMPLAINT.updateNcComplaintAll", ncComplaint);
    }
}
