package com.lvmama.order.dao;

import com.lvmama.comm.bee.po.ord.NcComplaint;
import com.lvmama.comm.bee.po.ord.NcComplaintResult;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-10-29<p/>
 * Time: 下午2:35<p/>
 * Email:kouhongyu@163.com<p/>
 */
public interface NcComplaintDAO {
    public NcComplaint getNcComplaintByComplaintId(Long complaintId);

    public Long insert(NcComplaint ncComplaint);

    public Integer updateNcComplaint(NcComplaint ncComplaint);
    /**
     * 按条件查询投诉信息
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

    /**
     * 根据id查询处理结果信息
     * @param complaintId
     * @return
     */
    public NcComplaintResult selectComplaintReusltByComplaintId(Long complaintId);

    public Integer updateNcComplaintAll(NcComplaint ncComplaint);
}
