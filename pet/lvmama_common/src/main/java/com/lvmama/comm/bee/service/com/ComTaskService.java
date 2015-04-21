package com.lvmama.comm.bee.service.com;

import com.lvmama.comm.pet.po.pub.ComTask;
import com.lvmama.comm.pet.po.pub.ComTaskLog;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 14-1-3<p/>
 * Time: 上午10:52<p/>
 * Email:kouhongyu@163.com<p/>
 */
public interface ComTaskService {

    public Long getComTaskCount(Map<String, Object> paramMap);

    public List<ComTask> getComTaskList(Map<String, Object> paramMap);

    public void saveComTask(ComTask comTask);

    public ComTask getComTask(Long taskId);

    public void delComTask(String taskIds);

    public Date calculateNextRunTime(ComTask comTask);

    public ComTaskLog addComTaskLog(ComTaskLog comTaskLog);

    public void updateComTaskLog(ComTaskLog comTaskLog);

    public ComTaskLog getLastComTaskLog(Long taskId);

    public Long getComTaskLogCount(Map<String, Object> paramMap);

    public List<ComTaskLog> getComTaskLogList(Map<String, Object> paramMap);

    public ComTaskLog getLastComTaskLogByLogId(Long logId);
}
