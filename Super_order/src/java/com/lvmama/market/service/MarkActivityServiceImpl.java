package com.lvmama.market.service;

import com.lvmama.comm.bee.service.market.MarkActivityService;
import com.lvmama.comm.edm.IHqEMailSenderService;
import com.lvmama.comm.edm.TaskDTO;
import com.lvmama.comm.edm.TemplateDTO;
import com.lvmama.comm.pet.client.EmailClient;
import com.lvmama.comm.pet.po.mark.*;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.market.dao.*;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-12-12<p/>
 * Time: 上午11:03<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class MarkActivityServiceImpl implements MarkActivityService {

    private MarkActivityDAO markActivityDAO;
    private MarkActivityItemDAO markActivityItemDAO;
    private MarkActivitySendLogDAO markActivitySendLogDAO;
    private MarkActivityDataModelDAO markActivityDataModelDAO;
    private MarkActivityBlacklistDAO markActivityBlacklistDAO;
    private EmailClient emailClient;

    private IHqEMailSenderService hqEMailSenderService;


    public List<MarkActivity> getMarkActivityList(Map<String, Object> paramMap) {

        List<MarkActivity> markActivityList = markActivityDAO.getMarkActivityList(paramMap);

        return markActivityList;
    }

    public MarkActivityItem getMarkActivityItemEmail(Long actId) {
        return markActivityItemDAO.getMarkActivityItemEmail(actId);
    }

    public void saveMarkActivity(MarkActivity markActivity) {
        if (markActivity.getActId() == null) {
            markActivityDAO.saveMarkActivity(markActivity);
            markActivity.getMarkActivityItemEmail().setActId(markActivity.getActId());
            markActivityItemDAO.saveMarkActivityItem(markActivity.getMarkActivityItemEmail());
        } else {
            markActivityDAO.updateMarkActivity(markActivity);
            markActivityItemDAO.updateMarkActivityItem(markActivity.getMarkActivityItemEmail());
        }
    }

    public void delMarkActivity(Long actId) {
        markActivityItemDAO.deleteMarkActivityItem(actId);
        markActivityDAO.deleteMarkActivity(actId);
    }

    public void delMarkActivity(String actIds) {
        String[] ids = actIds.split(",");
        for (String id : ids) {
            delMarkActivity(Long.valueOf(id));
        }
    }

    public Long getMarkActivityCount(Map<String, Object> paramMap) {
        return markActivityDAO.getMarkActivityCount(paramMap);
    }

    public MarkActivity getMarkActivity(Long actId) {
        return markActivityDAO.getMarkActivity(actId);
    }

    public List<MarkActivity> getNextDaySendList() {
        return markActivityDAO.getSendList();
    }

    public int sendMail(Long actId) throws Exception {
        return sendMail(actId, getMarkActivityBlacklistList());
    }

    /**
     * 邮件发送主方法，重要
     *
     * @param actId
     * @param markActivityBlacklistList 有问题找寇宏宇
     */
    private int sendMail(Long actId, List<MarkActivityBlacklist> markActivityBlacklistList) throws Exception {

        int sendAmount = 0;

        MarkActivity markActivity = markActivityDAO.getMarkActivity(actId);
        markActivity.setMarkActivityItemEmail(markActivityItemDAO.getMarkActivityItemEmail(actId));

        long sendOffTimes = markActivity.getMarkActivityItemEmail().getSendOffTimes();

        String dataModel = markActivity.getMarkActivityItemEmail().getDataModel();
        String guid = markActivityDataModelDAO.getDataModelLastGuid(dataModel);

        long total = markActivityDataModelDAO.getDataModelTotal(guid);
        long pageSize = 10000;
        long pageTotal = getPageTotal(total, pageSize);


        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("guid" , guid);

        //分页执行
        for (int page = 1; page <= pageTotal; page++) {

            long[] interval = pageStartEnd(page, pageSize);
            paramMap.put("startRow" , interval[0]);
            paramMap.put("endRow" , interval[1]);

            List<MarkActivityUserData> emailList = markActivityDataModelDAO.getMailListByGuid(paramMap);
            //排除黑名单邮件
            emailList = excludeBlacklist(emailList, markActivityBlacklistList);
            //排除活动规则中，对发送次数限制的邮件
            emailList = excludeEmail(emailList, markActivity.getMarkActivityItemEmail());

//            emailList = getTestInfo();    //测试代码  ########################################

            if (emailList != null && emailList.size() > 0) {//有邮件才执行
                sendAmount += emailList.size();

                final Date sendTime = getSendTime(markActivity.getMarkActivityItemEmail().getSendTime());
                final Date _now = new Date();

                //汉起邮件接口
                hqSendEmail(emailList, markActivity, sendTime);

                for (MarkActivityUserData userData : emailList) {

                    MarkActivitySendLog markActivitySendLog = new MarkActivitySendLog();

                    markActivitySendLog.setTarget(userData.getEmail());
                    markActivitySendLog.setType(markActivity.getMarkActivityItemEmail().getChannel());
                    markActivitySendLog.setSendTimes(sendOffTimes + 1);
                    markActivitySendLog.setSendTime(sendTime);
                    markActivitySendLog.setActItemId(markActivity.getMarkActivityItemEmail().getActItemId());
                    markActivitySendLog.setCreateTime(_now);
                    //记录邮件发送日志
                    markActivitySendLogDAO.insertMarkActivitySendLog(markActivitySendLog);
                }
            }
        }
        if (sendAmount > 0) {
            //更新发送批次
            markActivity.getMarkActivityItemEmail().setSendOffTimes(sendOffTimes + 1);
            markActivity.getMarkActivityItemEmail().setLastSendTime(new Date());
            markActivityItemDAO.updateMarkActivityItem(markActivity.getMarkActivityItemEmail());
        }

        return sendAmount;
    }

    private List<MarkActivityUserData> getTestInfo() {
        List<MarkActivityUserData> testEmailList = new ArrayList<MarkActivityUserData>();
        MarkActivityUserData testUserData = new MarkActivityUserData();
        testUserData.setEmail("kouhongyu@gmail.com");

        testUserData.setUserId("1");
        testUserData.setMobile("2");
        testUserData.setRegisterDate("3");
        testUserData.setIntegral("4");
        testUserData.setBonus("5");
        testUserData.setGrade("6");

        testUserData.setBirthday("7");
        testUserData.setCouponAmount("8");
        testUserData.setProductPrice("9");
        testUserData.setProductName("10");
        testUserData.setCouponEndTime("11");
        testUserData.setProductCashRefund("12");
        testUserData.setIntegralEndTime("13");
        testUserData.setBonusEndTime("14");
        testUserData.setProductSaleAmount("15");

        testEmailList.add(testUserData);
        return testEmailList;
    }

    private void hqSendEmail(List<MarkActivityUserData> emailList, MarkActivity markActivity, Date sendTime) throws Exception {

        //组装任务对象
        TaskDTO task = new TaskDTO();
        task.setTaskGroupId(Constant.getInstance().getProperty("HANQI_TASK_GROUP_ID"));
        task.setTaskName("任务:会员营销活动:" + markActivity.getActivityName() + "_" + UUID.randomUUID().toString());
        task.setEmailColumnName(MarkActivityUserData.getColumnNames());

        //组装模板对象
        TemplateDTO template = new TemplateDTO();
        template.setFromName("驴妈妈旅游网");
        template.setSenderEmail("info@mailer.lvmama.com");
        template.setSubject(markActivity.getActivityName());
        template.setTempName(markActivity.getActivityName() + "_Template_" + UUID.randomUUID().toString());

        String content = HttpsUtil.requestGet(markActivity.getMarkActivityItemEmail().getContent());
//        content = getTestTemplate();//测试代码  ########################################
        template.setTemplateContent(content);

        hqEMailSenderService.sendEmail(task, template, toStringArray(emailList), true);

    }

    private String getTestTemplate() {
        StringBuilder content = new StringBuilder();

        content.append("<p>").append("用户ID:").append("$$data.customer_id$$").append("</p>");
        content.append("<p>").append("手机号:").append("$$data.mobile$$").append("</p>");
        content.append("<p>").append("注册日期:").append("$$data.createdate$$").append("</p>");
        content.append("<p>").append("积分:").append("$$data.integral$$").append("</p>");
        content.append("<p>").append("奖金:").append("$$data.bonusamount$$").append("</p>");
        content.append("<p>").append("会员等级:").append("$$data.grade$$").append("</p>");
        content.append("<p>").append("生日:").append("$$data.birthday$$").append("</p>");
        content.append("<p>").append("优惠券金额:").append("$$data.couponamount$$").append("</p>");
        content.append("<p>").append("产品价格:").append("$$data.productprice$$").append("</p>");
        content.append("<p>").append("产品名称:").append("$$data.productname$$").append("</p>");
        content.append("<p>").append("优惠券到期时间:").append("$$data.couponendtime$$").append("</p>");
        content.append("<p>").append("产品返现价格:").append("$$data.productcashrefund$$").append("</p>");
        content.append("<p>").append("积分到期时间:").append("$$data.integralendtime$$").append("</p>");
        content.append("<p>").append("奖金有效期:").append("$$data.bonusendtime$$").append("</p>");
        content.append("<p>").append("产品销售数量:").append("$$data.productsaleamount$$").append("</p>");
        return content.toString();
    }

    private String[] toStringArray(List<MarkActivityUserData> userDataList) {
        String[] emailArray = new String[userDataList.size()];
        for (int i = 0; i < emailArray.length; i++) {
            MarkActivityUserData userData = userDataList.get(i);
            if (StringUtils.isNotBlank(userData.getEmail())) {
                emailArray[i] = userData.getColumnValues();
            }
        }
        return emailArray;
    }

    private long getPageTotal(long total, long size) {
        return total % size == 0 ? total / size : total / size + 1;
    }

    private long[] pageStartEnd(long page, long pageSize) {

        long[] interval = new long[2];
        interval[0] = page == 1 ? 1 : page * pageSize - pageSize + 1;
        interval[1] = page == 1 ? pageSize : page * pageSize;
        return interval;
    }

    private Date getSendTime(Date sendTime) {
        Calendar c = Calendar.getInstance();
        c.setTime((Date) sendTime.clone());

        Calendar cSend = Calendar.getInstance();
        cSend.setTime(new Date());
        cSend.add(Calendar.DAY_OF_YEAR, 1);
        cSend.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY));
        cSend.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
        cSend.set(Calendar.SECOND, c.get(Calendar.SECOND));

        return cSend.getTime();
    }

    /**
     * 排除活动规则中，对发送次数限制的邮件
     *
     * @param emailList
     * @param markActivityItemEmail
     * @return
     */
    private List<MarkActivityUserData> excludeEmail(List<MarkActivityUserData> emailList, MarkActivityItem markActivityItemEmail) {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put(markActivityItemEmail.getExcludeScope(), true);
        params.put(markActivityItemEmail.getExcludeSymbol(), true);
        params.put("excludeTimes" , markActivityItemEmail.getExcludeTimes());

        List<String> excludeEmailList = markActivitySendLogDAO.getExcludeEmail(params);
        if (excludeEmailList != null && excludeEmailList.size() > 0) {
            emailList.removeAll(assembleMarkActivityUserDataList(excludeEmailList));
        }

        return emailList;
    }

    private List<MarkActivityUserData> assembleMarkActivityUserDataList(List<String> excludeEmailList) {
        List<MarkActivityUserData> list = new ArrayList<MarkActivityUserData>();
        for (String email : excludeEmailList) {
            MarkActivityUserData userData = new MarkActivityUserData();
            userData.setEmail(email);
            list.add(userData);
        }
        return list;
    }

    /**
     * 排除黑名单
     *
     * @param emailList
     * @param markActivityBlacklistList
     * @return
     */
    private List<MarkActivityUserData> excludeBlacklist(List<MarkActivityUserData> emailList, List<MarkActivityBlacklist> markActivityBlacklistList) {
        for (MarkActivityBlacklist blacklist : markActivityBlacklistList) {
            if (StringUtils.isNotBlank(blacklist.getEmail())) {
                MarkActivityUserData userData = new MarkActivityUserData();
                userData.setEmail(blacklist.getEmail());
                emailList.remove(userData);
            }
        }
        return emailList;
    }

    public void sendMailBatch() {
        List<MarkActivity> nextDaySendList = markActivityDAO.getSendList();
        for (MarkActivity activity : nextDaySendList) {
            try {
                sendMail(activity.getActId(), getMarkActivityBlacklistList());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int cleanupSendLog(Long cleanupDays) {
        return markActivitySendLogDAO.cleanupSendLog(cleanupDays);
    }

    public List<MarkActivityDataModel> getMarkActivityDataModelList() {
        return markActivityDataModelDAO.getMarkActivityDataModelList();
    }

    public Long getDataModelTotal(String dataModel) {
        String guid = markActivityDataModelDAO.getDataModelLastGuid(dataModel);

        return markActivityDataModelDAO.getDataModelTotal(guid);
    }

    public Long getSendAmountBySendOffTimes(Map<String, Object> prarm) {
        return markActivitySendLogDAO.getSendAmountBySendOffTimes(prarm);
    }

    /**
     * 获取邮件黑名单
     *
     * @return
     */
    private List<MarkActivityBlacklist> getMarkActivityBlacklistList() {

        List<MarkActivityBlacklist> list = new ArrayList<MarkActivityBlacklist>();

        long total = markActivityBlacklistDAO.getMarkActivityBlacklistCount(null);
        long pageSize = 10000;
        long pageTotal = getPageTotal(total, pageSize);

        for (int page = 1; page <= pageTotal; page++) {

            Map<String, Object> paramMap = new HashMap<String, Object>();

            long[] interval = pageStartEnd(page, pageSize);
            paramMap.put("_startRow" , interval[0]);
            paramMap.put("_endRow" , interval[1]);

            list.addAll(markActivityBlacklistDAO.getMarkActivityBlacklist(paramMap));
        }
        return list;
    }

    public MarkActivityDAO getMarkActivityDAO() {
        return markActivityDAO;
    }

    public void setMarkActivityDAO(MarkActivityDAO markActivityDAO) {
        this.markActivityDAO = markActivityDAO;
    }

    public MarkActivityItemDAO getMarkActivityItemDAO() {
        return markActivityItemDAO;
    }

    public void setMarkActivityItemDAO(MarkActivityItemDAO markActivityItemDAO) {
        this.markActivityItemDAO = markActivityItemDAO;
    }

    public EmailClient getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(EmailClient emailClient) {
        this.emailClient = emailClient;
    }

    public MarkActivitySendLogDAO getMarkActivitySendLogDAO() {
        return markActivitySendLogDAO;
    }

    public void setMarkActivitySendLogDAO(MarkActivitySendLogDAO markActivitySendLogDAO) {
        this.markActivitySendLogDAO = markActivitySendLogDAO;
    }

    public MarkActivityBlacklistDAO getMarkActivityBlacklistDAO() {
        return markActivityBlacklistDAO;
    }

    public void setMarkActivityBlacklistDAO(MarkActivityBlacklistDAO markActivityBlacklistDAO) {
        this.markActivityBlacklistDAO = markActivityBlacklistDAO;
    }

    public MarkActivityDataModelDAO getMarkActivityDataModelDAO() {
        return markActivityDataModelDAO;
    }

    public void setMarkActivityDataModelDAO(MarkActivityDataModelDAO markActivityDataModelDAO) {
        this.markActivityDataModelDAO = markActivityDataModelDAO;
    }

    public IHqEMailSenderService getHqEMailSenderService() {
        return hqEMailSenderService;
    }

    public void setHqEMailSenderService(IHqEMailSenderService hqEMailSenderService) {
        this.hqEMailSenderService = hqEMailSenderService;
    }
}
