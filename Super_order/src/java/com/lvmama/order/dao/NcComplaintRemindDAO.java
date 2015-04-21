package com.lvmama.order.dao;

import com.lvmama.comm.bee.po.ord.NcComplaintRemind;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-11-4<p/>
 * Time: 上午11:55<p/>
 * Email:kouhongyu@163.com<p/>
 */
public interface NcComplaintRemindDAO {
    public Long insertNcComplaintRemind(NcComplaintRemind ncComplaintRemind);

    public int updateNcComplaintRemind(NcComplaintRemind ncComplaintRemind);

    public List<NcComplaintRemind> selectNcComplaintRemindList(Map<String, Object> params);

    public NcComplaintRemind selectNcComplaintRemindByRemindId(Long remindId);

    public int deleteNcComplaintRemind(Long remindId);
    
    /**
     * 根据id查询提醒信息
     * @param complaintId
     * @return
     */
    public List<NcComplaintRemind> selectNcComplaintRemindByComplaintId(Long complaintId);
}
