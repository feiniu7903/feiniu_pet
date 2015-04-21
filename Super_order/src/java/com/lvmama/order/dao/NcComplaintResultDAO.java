package com.lvmama.order.dao;

import com.lvmama.comm.bee.po.ord.NcComplaintResult;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-11-7<p/>
 * Time: 下午5:57<p/>
 * Email:kouhongyu@163.com<p/>
 */
public interface NcComplaintResultDAO {
    public Long insertNcComplaintResult(NcComplaintResult ncComplaintResult);

    public int updateNcComplaintResult(NcComplaintResult ncComplaintResult);

    public NcComplaintResult selectNcComplaintResultByComplaintId(Long complaintId);

}
