package com.lvmama.order.dao.impl;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.NcComplaintRemind;
import com.lvmama.order.dao.NcComplaintRemindDAO;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-11-4<p/>
 * Time: 上午11:56<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class NcComplaintRemindDAOImpl extends BaseIbatisDAO implements NcComplaintRemindDAO {


    public Long insertNcComplaintRemind(NcComplaintRemind ncComplaintRemind) {
        return (Long) super.insert("NC_COMPLAINT_REMIND.insertNcComplaintRemind", ncComplaintRemind);
    }

    public int updateNcComplaintRemind(NcComplaintRemind ncComplaintRemind) {
        return super.update("NC_COMPLAINT_REMIND.updateNcComplaintRemind", ncComplaintRemind);
    }


    public List<NcComplaintRemind> selectNcComplaintRemindList(Map<String,Object> params) {
        return super.queryForList("NC_COMPLAINT_REMIND.selectNcComplaintRemindList",params);
    }

    public NcComplaintRemind selectNcComplaintRemindByRemindId(Long remindId){
        return (NcComplaintRemind) super.queryForObject("NC_COMPLAINT_REMIND.selectNcComplaintRemindByRemindId", remindId);
    }

    public int deleteNcComplaintRemind(Long remindId) {
       return super.delete("NC_COMPLAINT_REMIND.deleteNcComplaintRemind",remindId);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<NcComplaintRemind> selectNcComplaintRemindByComplaintId( Long complaintId) {
		return super.queryForList("NC_COMPLAINT_REMIND.selectNcComplaintRemindByComplaintId", complaintId);
	}
}
