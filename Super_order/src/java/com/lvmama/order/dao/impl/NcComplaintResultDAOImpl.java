package com.lvmama.order.dao.impl;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.NcComplaintResult;
import com.lvmama.order.dao.NcComplaintResultDAO;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-11-7<p/>
 * Time: 下午5:58<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class NcComplaintResultDAOImpl extends BaseIbatisDAO implements NcComplaintResultDAO {


    public Long insertNcComplaintResult(NcComplaintResult ncComplaintResult) {
        return (Long) super.insert("NC_COMPLAINT_RESULT.insertNcComplaintResult", ncComplaintResult);
    }

    public int updateNcComplaintResult(NcComplaintResult ncComplaintResult) {
        return super.update("NC_COMPLAINT_RESULT.updateNcComplaintResult", ncComplaintResult);
    }
    public NcComplaintResult selectNcComplaintResultByComplaintId(Long complaintId) {
        return (NcComplaintResult) super.queryForObject("NC_COMPLAINT_RESULT.selectComplaintResultBycomplaintId", complaintId);
    }



}
