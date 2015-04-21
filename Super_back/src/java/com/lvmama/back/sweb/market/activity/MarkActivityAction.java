package com.lvmama.back.sweb.market.activity;


import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.service.market.MarkActivityService;
import com.lvmama.comm.pet.po.mark.MarkActivity;
import com.lvmama.comm.pet.po.mark.MarkActivityDataModel;
import com.lvmama.comm.pet.service.perm.PermUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import java.util.*;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-12-11<p/>
 * Time: 下午4:36<p/>
 * Email:kouhongyu@163.com<p/>
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Results({
        @Result(name = "toMarkActivityList", location = "/WEB-INF/pages/back/market/activity/mark_activity_list.jsp"),
        @Result(name = "editMarkActivity", location = "/WEB-INF/pages/back/market/activity/edit_mark_activity.jsp")
})
public class MarkActivityAction extends BaseAction {

    private static final long serialVersionUID = -9192733019093793570L;

    private Map<String, Object> jsonMap = new HashMap<String, Object>();
    private String channel;
    private String keyword;
    private String status;
    private String actIds;

    private MarkActivity markActivity;

    private MarkActivityService markActivityService;
    private PermUserService permUserService;

    private List<MarkActivity> markActivityList;
    private List<String> weeks;
    private HashMap<Object, Object> dataModelMap;


    @Action(value = "/mark_activity/getMarkActivityInfo",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String getMarkActivityInfo() {
        try {

            markActivity = markActivityService.getMarkActivity(markActivity.getActId());
            jsonMap.put("sendAmount", 0);

            if (markActivity != null) {
                markActivity.setMarkActivityItemEmail(markActivityService.getMarkActivityItemEmail(markActivity.getActId()));

                if (markActivity.getMarkActivityItemEmail() != null && markActivity.getMarkActivityItemEmail().getSendOffTimes() > 0) {
                    Map<String,Object> prarm = new HashMap<String, Object>();
                    prarm.put("actItemId",markActivity.getMarkActivityItemEmail().getActItemId());
                    prarm.put("sendTimes",markActivity.getMarkActivityItemEmail().getSendOffTimes());

                    jsonMap.put("sendAmount", markActivityService.getSendAmountBySendOffTimes(prarm));
                }

            }

            jsonMap.put("markActivity", markActivity);
            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    @Action(value = "/mark_activity/send",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String send() {
        try {

            int sendAmount = markActivityService.sendMail(markActivity.getActId());

            if (sendAmount == 0) {
                throw new Exception("没有可发送的邮箱地址");
            }

            jsonMap.put("sendAmount", sendAmount);
            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
//            e.printStackTrace();
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    @Action(value = "/mark_activity/saveMarkActivity",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String saveMarkActivity() {
        try {
            if (markActivity.getActId() == null) {
                markActivity.setCreateTime(new Date(System.currentTimeMillis()));
                markActivity.getMarkActivityItemEmail().setSendOffTimes(0L);
            }
            if (weeks != null) {
                String week_str = "";
                for (String week : weeks) {
                    week_str += week + ",";
                }
                if (week_str.length() > 0) {
                    week_str = week_str.substring(0, week_str.length() - 1);
                    markActivity.getMarkActivityItemEmail().setWeek(week_str);
                }
            }
//            PermUser permUser = permUserService.getPermUserByUserName(markActivity.getPersonCharge());
//            if (permUser == null) {
//                throw new Exception("找不到“负责人”");
//            }

            markActivityService.saveMarkActivity(markActivity);

            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    @Action(value = "/mark_activity/delMarkActivity",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String delMarkActivity() {
        try {
            if (StringUtils.isBlank(actIds)) {
                throw new Exception("活动ID为空");
            }
            markActivityService.delMarkActivity(actIds);

            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    @Action(value = "/mark_activity/getDataModelTotal",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String getDataModelTotal() {

        try {
            if (markActivity == null) {
                throw new Exception("数据模型ID为空");
            }
            long total = markActivityService.getDataModelTotal(markActivity.getMarkActivityItemEmail().getDataModel());

            jsonMap.put("total", total);
            jsonMap.put("status", SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }


    @Action("/mark_activity/editMarkActivity")
    public String editMarkActivity() {

        if (markActivity != null) {
            markActivity = markActivityService.getMarkActivity(markActivity.getActId());
            markActivity.setMarkActivityItemEmail(markActivityService.getMarkActivityItemEmail(markActivity.getActId()));

            String week_str = markActivity.getMarkActivityItemEmail().getWeek();
            if (StringUtils.isNotBlank(week_str)) {
                weeks = new ArrayList<String>();
                weeks.addAll(Arrays.asList(week_str.split(",")));
            }
        }
        dataModelMap = initDataModelMap();

        return "editMarkActivity";
    }

    private HashMap<Object, Object> initDataModelMap() {
        HashMap<Object, Object> map = new HashMap<Object, Object>();

        List<MarkActivityDataModel> markActivityDataModelList = markActivityService.getMarkActivityDataModelList();
        for (MarkActivityDataModel model : markActivityDataModelList) {

            String[] ss = model.getSegmentPath().split("/");
            String v = ss[ss.length - 1];

            map.put(model.getSegmentPath(), v);
        }
//        map.put(1, "游玩前");
//        map.put(2, "游玩归来");
//        map.put(3, "生日问候   ");
//        map.put(4, "注册周年");
//        map.put(5, "搜索关键字");
//        map.put(6, "长期未登录提醒");
//        map.put(7, "注册初次关怀");

        return map;
    }

    @Action("/mark_activity/toMarkActivityList")
    public String toMarkActivityList() {

        Map<String, Object> paramMap = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(keyword)) {
            paramMap.put("keyword", keyword);
        }
        if (StringUtils.isNotBlank(status)) {
            paramMap.put("status", status);
        }
        pagination = super.initPagination();

        Long totalRowCount = markActivityService.getMarkActivityCount(paramMap);

        paramMap.put("_startRow", pagination.getFirstRow());
        paramMap.put("_endRow", pagination.getLastRow());

        markActivityList = markActivityService.getMarkActivityList(paramMap);

        for (MarkActivity activity : markActivityList) {
            activity.setMarkActivityItemEmail(markActivityService.getMarkActivityItemEmail(activity.getActId()));
        }

        pagination.setTotalRecords(totalRowCount);
        pagination.setRecords(markActivityList);
        pagination.setActionUrl(WebUtils.getUrl("/mark_activity/toMarkActivityList.do", true, paramMap));

        return "toMarkActivityList";
    }

    public MarkActivityService getMarkActivityService() {
        return markActivityService;
    }

    public void setMarkActivityService(MarkActivityService markActivityService) {
        this.markActivityService = markActivityService;
    }

    public List<MarkActivity> getMarkActivityList() {
        return markActivityList;
    }

    public void setMarkActivityList(List<MarkActivity> markActivityList) {
        this.markActivityList = markActivityList;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MarkActivity getMarkActivity() {
        return markActivity;
    }

    public void setMarkActivity(MarkActivity markActivity) {
        this.markActivity = markActivity;
    }

    public Map<String, Object> getJsonMap() {
        return jsonMap;
    }

    public void setJsonMap(Map<String, Object> jsonMap) {
        this.jsonMap = jsonMap;
    }

    public String getActIds() {
        return actIds;
    }

    public void setActIds(String actIds) {
        this.actIds = actIds;
    }

    public List<String> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<String> weeks) {
        this.weeks = weeks;
    }

    public PermUserService getPermUserService() {
        return permUserService;
    }

    public void setPermUserService(PermUserService permUserService) {
        this.permUserService = permUserService;
    }

    public HashMap<Object, Object> getDataModelMap() {
        return dataModelMap;
    }

    public void setDataModelMap(HashMap<Object, Object> dataModelMap) {
        this.dataModelMap = dataModelMap;
    }
}
