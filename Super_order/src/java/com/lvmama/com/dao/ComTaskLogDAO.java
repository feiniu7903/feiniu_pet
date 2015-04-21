package com.lvmama.com.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComTaskLog;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 14-1-7<p/>
 * Time: 下午5:32<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class ComTaskLogDAO extends BaseIbatisDAO {

    public void insertComTaskLog(ComTaskLog comTaskLog) {
        super.insert("COM_TASK_LOG.insertComTaskLog", comTaskLog);
    }

    public void updateComTaskLog(ComTaskLog comTaskLog) {
        super.update("COM_TASK_LOG.updateComTaskLog",comTaskLog);
    }

    public ComTaskLog getLastComTaskLog(Long taskId) {
        return (ComTaskLog)super.queryForObject("COM_TASK_LOG.selectLastComTaskLog",taskId);
    }

    public Long getComTaskLogCount(Map<String, Object> paramMap) {
        return (Long)super.queryForObject("COM_TASK_LOG.selectComTaskLogCount", paramMap);
    }

    public List<ComTaskLog> getComTaskLogList(Map<String, Object> paramMap) {
        return super.queryForList("COM_TASK_LOG.selectComTaskLogList",paramMap);
    }

    public ComTaskLog getLastComTaskLogByLogId(Long logId) {
        return (ComTaskLog)super.queryForObject("COM_TASK_LOG.selectComTaskLog",logId);
    }
}
