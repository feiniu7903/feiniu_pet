package com.lvmama.com.service;

import com.lvmama.com.dao.ComTaskDAO;
import com.lvmama.com.dao.ComTaskLogDAO;
import com.lvmama.comm.bee.service.com.ComTaskService;
import com.lvmama.comm.pet.po.pub.ComTask;
import com.lvmama.comm.pet.po.pub.ComTaskLog;
import com.lvmama.comm.vo.Constant;

import java.util.*;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 14-1-3<p/>
 * Time: 上午10:52<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class ComTaskServiceImpl implements ComTaskService {

    private ComTaskDAO comTaskDAO;
    private ComTaskLogDAO comTaskLogDAO;

    public Long getComTaskCount(Map<String, Object> paramMap) {
        return comTaskDAO.getComTaskCount(paramMap);
    }

    public List<ComTask> getComTaskList(Map<String, Object> paramMap) {
        return comTaskDAO.getComTaskList(paramMap);
    }

    public void saveComTask(ComTask comTask) {

        if (comTask.getTaskId() == null) {
            comTaskDAO.insertComTask(comTask);
        } else {
            comTaskDAO.updateComTask(comTask);
        }
    }

    public ComTask getComTask(Long taskId) {
        return comTaskDAO.getComTask(taskId);
    }

    public void delComTask(String taskIds) {
        String[] ids = taskIds.split(",");
        for (String id : ids) {
            delComTask(Long.valueOf(id));
        }
    }

    public Date calculateNextRunTime(ComTask comTask) {

        Calendar nowTime = Calendar.getInstance();
        nowTime.setTime(new Date(System.currentTimeMillis()));

        //运行时间
        Calendar nextTime = Calendar.getInstance();
        nextTime.setTime((Date) comTask.getPlanTime().clone());

        //分钟
        if (comTask.getCycle().equals(Constant.COM_TASK_CYCLE.MINUTE.getCode())) {

            nextTime.set(Calendar.YEAR, nowTime.get(Calendar.YEAR));
            nextTime.set(Calendar.MONTH, nowTime.get(Calendar.MONTH));
            nextTime.set(Calendar.DAY_OF_MONTH, nowTime.get(Calendar.DAY_OF_MONTH));
            nextTime.set(Calendar.HOUR_OF_DAY, nowTime.get(Calendar.HOUR_OF_DAY));
            nextTime.set(Calendar.MINUTE, nowTime.get(Calendar.MINUTE));

            nextTime.add(Calendar.MINUTE, comTask.getCycleDimension().intValue());
        }
        //小时
        if (comTask.getCycle().equals(Constant.COM_TASK_CYCLE.HOURS.getCode())) {

            nextTime.set(Calendar.YEAR, nowTime.get(Calendar.YEAR));
            nextTime.set(Calendar.MONTH, nowTime.get(Calendar.MONTH));
            nextTime.set(Calendar.DAY_OF_MONTH, nowTime.get(Calendar.DAY_OF_MONTH));
            nextTime.set(Calendar.HOUR_OF_DAY, nowTime.get(Calendar.HOUR_OF_DAY));

            nextTime.add(Calendar.HOUR_OF_DAY, comTask.getCycleDimension().intValue());
        }
        //天
        if (comTask.getCycle().equals(Constant.COM_TASK_CYCLE.DAY.getCode())) {

            nextTime.set(Calendar.YEAR, nowTime.get(Calendar.YEAR));
            nextTime.set(Calendar.MONTH, nowTime.get(Calendar.MONTH));
            nextTime.set(Calendar.DAY_OF_MONTH, nowTime.get(Calendar.DAY_OF_MONTH));

            nextTime.add(Calendar.DAY_OF_MONTH, comTask.getCycleDimension().intValue());
        }
        //月
        if (comTask.getCycle().equals(Constant.COM_TASK_CYCLE.MONTH.getCode())) {
            nextTime.set(Calendar.YEAR, nowTime.get(Calendar.YEAR));
            nextTime.set(Calendar.MONTH, nowTime.get(Calendar.MONTH));

            nextTime.add(Calendar.MONTH, comTask.getCycleDimension().intValue());
        }
        //年
        if (comTask.getCycle().equals(Constant.COM_TASK_CYCLE.YEAR.getCode())) {
            nextTime.set(Calendar.YEAR, nowTime.get(Calendar.YEAR));

            nextTime.add(Calendar.YEAR, comTask.getCycleDimension().intValue());
        }
        //每周几运行
        if (comTask.getCycle().equals(Constant.COM_TASK_CYCLE.WEEK.getCode())) {
            //先把日期设置为今天，时间不动
            nextTime.set(Calendar.YEAR, nowTime.get(Calendar.YEAR));
            nextTime.set(Calendar.MONTH, nowTime.get(Calendar.MONTH));
            nextTime.set(Calendar.DAY_OF_MONTH, nowTime.get(Calendar.DAY_OF_MONTH));

            //一周中每一天对应的数字
            Map<Integer,Constant.WEEK> weekMap = new HashMap<Integer, Constant.WEEK>();
            weekMap.put(1,Constant.WEEK.SUNDAY);
            weekMap.put(2,Constant.WEEK.MONDAY);
            weekMap.put(3,Constant.WEEK.TUESDAY);
            weekMap.put(4,Constant.WEEK.WEDNESDAY);
            weekMap.put(5,Constant.WEEK.THURSDAY);
            weekMap.put(6,Constant.WEEK.FRIDAY);
            weekMap.put(7, Constant.WEEK.SATURDAY);

            boolean ok = false;

            while (!ok) {
                //看看这一天是不是要运行
                if (comTask.getWeek().contains(weekMap.get(nextTime.get(Calendar.DAY_OF_WEEK)).getCode())) {
                    //如果今天的运行时间已经过了，日期加一天
                    if (nowTime.getTimeInMillis() > nextTime.getTimeInMillis()) {
                        nextTime.add(Calendar.DAY_OF_MONTH, 1);
                    }else{//刚好今天运行，时间还没过，那么就是这个时间了
                        ok = true;
                    }
                }else{//今天不运行，日期加一天
                    nextTime.add(Calendar.DAY_OF_MONTH, 1);
                }
            }
        }

        return nextTime.getTime();
    }

    public ComTaskLog addComTaskLog(ComTaskLog comTaskLog) {
        comTaskLogDAO.insertComTaskLog(comTaskLog);
        return comTaskLog;
    }

    public void updateComTaskLog(ComTaskLog comTaskLog) {
        comTaskLogDAO.updateComTaskLog(comTaskLog);
    }

    public ComTaskLog getLastComTaskLog(Long taskId) {
        return comTaskLogDAO.getLastComTaskLog(taskId);
    }

    public Long getComTaskLogCount(Map<String, Object> paramMap) {
        return comTaskLogDAO.getComTaskLogCount(paramMap);
    }

    public List<ComTaskLog> getComTaskLogList(Map<String, Object> paramMap) {
        return comTaskLogDAO.getComTaskLogList(paramMap);
    }

    public ComTaskLog getLastComTaskLogByLogId(Long logId) {
        return comTaskLogDAO.getLastComTaskLogByLogId(logId);
    }

    private void delComTask(Long taskId) {
        comTaskDAO.delComTask(taskId);
    }


    public ComTaskDAO getComTaskDAO() {
        return comTaskDAO;
    }

    public void setComTaskDAO(ComTaskDAO comTaskDAO) {
        this.comTaskDAO = comTaskDAO;
    }

    public ComTaskLogDAO getComTaskLogDAO() {
        return comTaskLogDAO;
    }

    public void setComTaskLogDAO(ComTaskLogDAO comTaskLogDAO) {
        this.comTaskLogDAO = comTaskLogDAO;
    }
}
