package com.lvmama.back.remote.impl;

import com.lvmama.back.remote.MarkActivityDataModelJobService;
import com.lvmama.back.sweb.util.marketing.ItemInfo;
import com.lvmama.back.sweb.util.marketing.MarketingBiLogic;
import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 14-1-2<p/>
 * Time: 下午4:10<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class MarkActivityDataModelJobServiceImpl implements MarkActivityDataModelJobService, Serializable {

    private static final long serialVersionUID = 3447543756084801777L;
    protected final Log log = LogFactory.getLog(MarkActivityDataModelJobServiceImpl.class);
    public TaskResult execute(Long logId,String parameter) throws Exception {
        TaskResult  result = new TaskResult();
        ItemInfo[] itemInfos = MarketingBiLogic.getMarkModelDatas();

        for (ItemInfo info : itemInfos) {
            String nowTimeStr = DateUtil.formatDate(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss");

            String[] paths = info.getPath().split("/");
            String name = paths[paths.length - 1];

            if (!"SRS_UserID".equals(name)) {
                if(StringUtils.isNotBlank(parameter) && parameter.contains(info.getPath())){
                    log.info("#### path:" + info.getPath() + "," + nowTimeStr);
                    MarketingBiLogic.saveResultSet(info.getPath(), name + "_" + nowTimeStr);
                }
            }
        }
        result.setStatus(1);//完成

        return result;
    }
}
