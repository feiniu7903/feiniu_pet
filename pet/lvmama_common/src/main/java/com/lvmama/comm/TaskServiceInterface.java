package com.lvmama.comm;

import com.lvmama.comm.pet.po.pub.TaskResult;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 14-1-2<p/>
 * Time: 下午5:58<p/>
 * Email:kouhongyu@163.com<p/>
 */
public interface TaskServiceInterface {

    public TaskResult execute(Long logId,String parameter) throws Exception;
}
