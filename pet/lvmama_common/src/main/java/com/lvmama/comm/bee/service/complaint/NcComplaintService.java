package com.lvmama.comm.bee.service.complaint;

import com.lvmama.comm.bee.po.ord.*;
import com.lvmama.comm.bee.po.prod.ProdProduct;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-10-29<p/>
 * Time: 下午2:24<p/>
 * Email:kouhongyu@163.com<p/>
 */
public interface NcComplaintService {
    public NcComplaint getNcComplaintByComplaintId(Long complaintId);

    public int updateNcComplaint(NcComplaint ncComplaint);

    public Long createNcComplaint(NcComplaint ncComplaint);

    public Long createNcComplaintTracking(NcComplaintTracking ncComplaintTracking);

    public List<NcComplaintTracking> getNcComplaintTrackingList(Map<String, Object> params);
    
    /**
     * 查询投诉信息
     * @param params
     * @return
     * @author zhushuying
     */
    public List<NcComplaint> queryComplaintByParams(Map<String, Object> params);
    /**
     * 查询投诉信息数量
     * @param params
     * @return
     * @author zhushuying
     */
    public Long queryComplaintCount(Map<String, Object> params);

    public ProdProduct getProdProductByOrderId(Long orderId);

    public void createNcComplaintTracking(NcComplaintTracking ncComplaintTracking, NcComplaint ncComplaint);

    public Long getNcComplaintTrackingSequence();

    public Long createNcComplaintRemind(NcComplaintRemind ncComplaintRemind);

    public int updateNcComplaintRemind(NcComplaintRemind ncComplaintRemind);

    public List<NcComplaintRemind> getNcComplaintRemindList(Map<String, Object> params);

    public void deleteNcComplaintRemind(Long remindId);

    public NcComplaintRemind getNcComplaintRemindByRemindId(Long remindId);

    public void transferComplaint(String complaintIds, String targetUserName);

    /**
     * 根据id查询现金赔偿信息
     * @param complaintId
     * @return
     */
    public NcComplaintResult getComplaintResultByComplaintId(Long complaintId);
    
    /**
     * 根据id查询提醒信息
     * @param complaintId
     * @return
     */
    public List<NcComplaintRemind> getNcComplaintRemindByComplaintId(Long complaintId);

    public void saveNcComplaintDuty(NcComplaintDuty ncComplaintDuty, List<NcComplaintDutyDetails> ncComplaintDutyDetailsList);

    public NcComplaintDuty getNcComplaintDutyByComplaintId(Long complaintId);

    public List<NcComplaintDutyDetails> getNcComplaintDutyDetailsList(Long complaintId);

    public Long createNcComplaintResult(NcComplaintResult ncComplaintResult);

    public int updateNcComplaintResult(NcComplaintResult ncComplaintResult);

    public void updateRelatedComplaint(Long complaintId, String relatedComplaint);

    public void updateNcComplaintAll(NcComplaint ncComplaint);

    public NcComplaintDuty getNcComplaintDuty(Map<String, Object> params);
}
