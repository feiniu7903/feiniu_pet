package com.lvmama.comm.bee.service.market;

import com.lvmama.comm.pet.po.mark.MarkActivity;
import com.lvmama.comm.pet.po.mark.MarkActivityDataModel;
import com.lvmama.comm.pet.po.mark.MarkActivityItem;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-12-12<p/>
 * Time: 上午11:02<p/>
 * Email:kouhongyu@163.com<p/>
 */
public interface MarkActivityService {

    public List<MarkActivity> getMarkActivityList(Map<String, Object> paramMap);

    public MarkActivityItem getMarkActivityItemEmail(Long actId);

    public void saveMarkActivity(MarkActivity markActivity);

    public void delMarkActivity(Long actId);

    public void delMarkActivity(String actIds);

    public Long getMarkActivityCount(Map<String, Object> paramMap);

    public MarkActivity getMarkActivity(Long actId);

    List<MarkActivity> getNextDaySendList();

    public int sendMail(Long actId) throws Exception;

    public void sendMailBatch();

    public int cleanupSendLog(Long cleanupDays);

    public List<MarkActivityDataModel> getMarkActivityDataModelList();


    public Long getDataModelTotal(String dataModel);

    public Long getSendAmountBySendOffTimes(Map<String, Object> prarm);
}
