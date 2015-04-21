package com.lvmama.com.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComTask;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 14-1-3<p/>
 * Time: 上午10:59<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class ComTaskDAO extends BaseIbatisDAO {

    public Long getComTaskCount(Map<String, Object> paramMap) {
        return (Long)super.queryForObject("COM_TASK.selectComTaskCount", paramMap);
    }

    public List<ComTask> getComTaskList(Map<String, Object> paramMap) {
        return super.queryForList("COM_TASK.selectComTaskList", paramMap);
    }

    public Long insertComTask(ComTask comTask) {
        return (Long) super.insert("COM_TASK.insertComTask", comTask);
    }

    public int updateComTask(ComTask comTask) {
        return super.update("COM_TASK.updateComTask", comTask);
    }

    public ComTask getComTask(Long taskId) {
        return (ComTask)super.queryForObject("COM_TASK.selectComTask", taskId);
    }

    public void delComTask(Long taskId) {
        super.delete("COM_TASK.delete",taskId);
    }
}
