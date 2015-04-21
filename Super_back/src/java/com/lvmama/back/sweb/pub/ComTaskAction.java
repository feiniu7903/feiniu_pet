package com.lvmama.back.sweb.pub;

import com.lvmama.back.job.ComTaskThreadPoolFactory;
import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.service.com.ComTaskService;
import com.lvmama.comm.pet.po.pub.ComTask;
import com.lvmama.comm.pet.po.pub.ComTaskLog;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import java.util.*;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 14-1-3<p/>
 * Time: 上午10:41<p/>
 * Email:kouhongyu@163.com<p/>
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Results({
        @Result(name = "showComTaskLogInfoDialog", location = "/WEB-INF/pages/back/pub/com_task_log_info.jsp"),
        @Result(name = "showComTaskLogDialog", location = "/WEB-INF/pages/back/pub/com_task_log_dialog.jsp"),
        @Result(name = "toComTaskLogList", location = "/WEB-INF/pages/back/pub/com_task_log_list.jsp"),
        @Result(name = "toComTaskList", location = "/WEB-INF/pages/back/pub/com_task_list.jsp"),
        @Result(name = "editComTask", location = "/WEB-INF/pages/back/pub/edit_com_task.jsp")
})
public class ComTaskAction extends BaseAction {

    private String taskName;
    private String status;
    private String available;
    private String taskId;
    private String taskIds;
    private ComTask comTask;
    private ComTaskLog comTaskLog;

    private Map<String, Object> jsonMap = new HashMap<String, Object>();
    private List<ComTask> comTaskList;
    private List<ComTaskLog> comTaskLogList;
    private List<Map> comTaskMapList;
    private List<String> weeks;

    private ComTaskService comTaskService;

    @Action("/pub/showComTaskLogInfoDialog")
    public String showComTaskLogInfoDialog() {
        comTaskLog = comTaskService.getLastComTaskLogByLogId(comTaskLog.getLogId());

        return "showComTaskLogInfoDialog";
    }

    @Action("/pub/showComTaskLogListDialog")
    public String showComTaskLogListDialog() {

        return "showComTaskLogListDialog";
    }

    @Action("/pub/showComTaskLogDialog")
    public String showComTaskLogDialog() {

        return "showComTaskLogDialog";
    }

    @Action("/pub/toComTaskLogList")
    public String toComTaskLogList() {

        Map<String, Object> paramMap = new HashMap<String, Object>();


        if (StringUtils.isNotBlank(taskId)) {
            paramMap.put("taskId", taskId);
        }
        pagination = super.initPagination();

        Long totalRowCount = comTaskService.getComTaskLogCount(paramMap);

        paramMap.put("_startRow", pagination.getFirstRow());
        paramMap.put("_endRow", pagination.getLastRow());

        comTaskLogList = comTaskService.getComTaskLogList(paramMap);

        pagination.setTotalRecords(totalRowCount);
        pagination.setRecords(comTaskLogList);
        pagination.setActionUrl(WebUtils.getUrl("pub/toComTaskLogList.do", true, paramMap));

        return "toComTaskLogList";
    }

    @Action("/pub/toComTaskList")
    public String toComTaskList() {

        Map<String, Object> paramMap = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(taskName)) {
            paramMap.put("taskName", taskName);
        }
        if (StringUtils.isNotBlank(status)) {
            paramMap.put("status", status);
        }
        if (StringUtils.isNotBlank(available)) {
            paramMap.put("available", available);
        }
        pagination = super.initPagination();

        Long totalRowCount = comTaskService.getComTaskCount(paramMap);

        paramMap.put("_startRow", pagination.getFirstRow());
        paramMap.put("_endRow", pagination.getLastRow());

        comTaskList = comTaskService.getComTaskList(paramMap);

        if (comTaskList != null && comTaskList.size() > 0) {
            comTaskMapList = new ArrayList<Map>();
            for (ComTask taskObj : comTaskList) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("comTask", taskObj);

                ComTaskLog comTaskLog = comTaskService.getLastComTaskLog(taskObj.getTaskId());
                if (comTaskLog != null) {
                    if (comTaskLog.getResultStatus() == 1) {
                        map.put("lastStatus", "完成");
                    } else if (comTaskLog.getResultStatus() == 2) {
                        map.put("lastStatus", "部分完成");
                    } else if (comTaskLog.getResultStatus() == 3) {
                        map.put("lastStatus", "未完成");
                    }

                    map.put("lastTime", DateUtil.formatDate(comTaskLog.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
                }
                comTaskMapList.add(map);
            }
        }


        pagination.setTotalRecords(totalRowCount);
        pagination.setRecords(comTaskList);
        pagination.setActionUrl(WebUtils.getUrl("/pub/toComTaskList.do", true, paramMap));

        return "toComTaskList";
    }

    @Action("/pub/editComTask")
    public String editComTask() {

        if (comTask != null && comTask.getTaskId() != null) {
            comTask = comTaskService.getComTask(comTask.getTaskId());
            weeks = new ArrayList<String>();
            if (comTask.getWeek() != null) {
                weeks.addAll(Arrays.asList(comTask.getWeek().split(",")));
            }
        } else {
            comTask = new ComTask();
            comTask.setCycleDimension(1L);
        }

        return "editComTask";
    }


    @Action(value = "/pub/saveComTask",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String saveComTask() {
        try {

            boolean isToRun = false;//从非运行状态改为运行状态

            if (comTask.getTaskId() == null) {
                comTask.setCreateTime(new Date(System.currentTimeMillis()));
                comTask.setStatus(Constant.COM_TASK_STATUS.END.getCode());
            } else {
                ComTask dbComTask = comTaskService.getComTask(comTask.getTaskId());

                if (Constant.COM_TASK_STATUS.RUN.getCode().equals(comTask.getStatus())
                        && !Constant.COM_TASK_STATUS.RUN.getCode().equals(dbComTask.getStatus())
                        && !ComTaskThreadPoolFactory.getInstance().isExist(comTask.getTaskId())) {
                    isToRun = true;
                }
            }

            if (weeks != null) {
                String week_str = "";
                for (String week : weeks) {
                    week_str += week + ",";
                }
                if (week_str.length() > 0) {
                    week_str = week_str.substring(0, week_str.length() - 1);
                    comTask.setWeek(week_str);
                }
            }

//            if (StringUtils.isNotBlank(comTask.getParameter()) && comTask.getParameter().length() > 800) {
//                throw new Exception("“参数”不能超过800个字符");
//            }
            if (StringUtils.isNotBlank(comTask.getDescription()) && comTask.getDescription().length() > 800) {
                throw new Exception("“任务描述”不能超过800个字符");
            }

            //如果状态为“等待”，计算下次运行时间
            if (comTask.getStatus().equals(Constant.COM_TASK_STATUS.WAIT.getCode())) {
                comTask.setNextRunTime(comTaskService.calculateNextRunTime(comTask));

            } else if (comTask.getAvailable().equals(Constant.COM_TASK_AVAILABLE.ENABLE.getCode())
                    && comTask.getStatus().equals(Constant.COM_TASK_STATUS.RUN.getCode())) {

                //如果从非运行状态改为运行状态，将下次执行时间改为当前时间，状态改为“等待”，下次轮询时运行
                if (isToRun) {
                    comTask.setStatus(Constant.COM_TASK_STATUS.WAIT.getCode());
                    comTask.setNextRunTime(new Date(System.currentTimeMillis()));
                }
            }
            comTaskService.saveComTask(comTask);

            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    @Action(value = "/pub/delComTask",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String delComTask() {
        try {
            if (StringUtils.isBlank(taskIds)) {
                throw new Exception("任务ID为空");
            }
            comTaskService.delComTask(taskIds);

            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public List<ComTask> getComTaskList() {
        return comTaskList;
    }

    public void setComTaskList(List<ComTask> comTaskList) {
        this.comTaskList = comTaskList;
    }

    public ComTaskService getComTaskService() {
        return comTaskService;
    }

    public void setComTaskService(ComTaskService comTaskService) {
        this.comTaskService = comTaskService;
    }

    public ComTask getComTask() {
        return comTask;
    }

    public void setComTask(ComTask comTask) {
        this.comTask = comTask;
    }

    public Map<String, Object> getJsonMap() {
        return jsonMap;
    }

    public void setJsonMap(Map<String, Object> jsonMap) {
        this.jsonMap = jsonMap;
    }

    public List<String> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<String> weeks) {
        this.weeks = weeks;
    }

    public String getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(String taskIds) {
        this.taskIds = taskIds;
    }

    public List<Map> getComTaskMapList() {
        return comTaskMapList;
    }

    public void setComTaskMapList(List<Map> comTaskMapList) {
        this.comTaskMapList = comTaskMapList;
    }

    public List<ComTaskLog> getComTaskLogList() {
        return comTaskLogList;
    }

    public void setComTaskLogList(List<ComTaskLog> comTaskLogList) {
        this.comTaskLogList = comTaskLogList;
    }

    public ComTaskLog getComTaskLog() {
        return comTaskLog;
    }

    public void setComTaskLog(ComTaskLog comTaskLog) {
        this.comTaskLog = comTaskLog;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
