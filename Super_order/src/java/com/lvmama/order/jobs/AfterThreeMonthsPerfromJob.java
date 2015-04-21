package com.lvmama.order.jobs;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.bee.service.SmsService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdChannelService;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;

public class AfterThreeMonthsPerfromJob implements Runnable {
    private static final Log log = LogFactory.getLog(AfterThreeMonthsPerfromJob.class);
    OrderService orderServiceProxy;
    ProdChannelService prodChannelService;
    SmsService smsService;
    CmtCommentService cmtCommentService;
    
    @Override
    public void run() {
        if (Constant.getInstance().isJobRunnable()) {
            try{
                log.info("AfterThreeMonthsPerfromJob run...");
                List<OrdOrder> ordList = queryOrderByThreeMonths();
                ComSms comSms ;
                Map<String,Object> parameters ;
                for (OrdOrder ordOrder :  ordList){
                    parameters = new HashMap<String, Object>();
                    parameters.put("orderId", ordOrder.getOrderId());
                    log.info("AfterThreeMonthsPerfromJob put parameters orderId :"+ ordOrder.getOrderId());
                    OrdOrder ord = orderServiceProxy.queryOrdOrderByOrderId(ordOrder.getOrderId());
                    if(null != ord.getMainProduct()){
                        parameters.put("productId", ord.getMainProduct().getProductId());
                        List<CommonCmtCommentVO> cmtCommentList = cmtCommentService.getCmtCommentList(parameters);
                        if(cmtCommentList == null || cmtCommentList.size() == 0)//该订单产品未被点评过，可以点评
                        {
                            comSms = new ComSms();
                            createComSms(comSms,ord);
                        }  
                    }else{
                        log.info("AfterThreeMonthsPerfromJob order.getMainProduct() is null .");
                    }
                }
            }catch(Exception e){
                log.error("AfterThreeMonthsPerfromJob run... Exception "+e);
                e.printStackTrace();
            }
            log.info("AfterThreeMonthsPerfromJob run end...");
        }
    }
    
    public void createComSms(ComSms comSms,OrdOrder ordOrder){
        ProdChannelSms smsT = prodChannelService.getChannelSmsTemplate(ordOrder.getChannel(),Constant.SMS_TEMPLATE.AFTER_THREEMONTHS_PERFROM.name());
        if(null != smsT){
            comSms.setTemplateId(Constant.SMS_TEMPLATE.AFTER_THREEMONTHS_PERFROM.name());
            comSms.setContent(smsT.getContent());
            comSms.setCreateTime(new Date());
            comSms.setSendTime(getSendDate());
            comSms.setMobile(ordOrder.getBooker().getMobile());
            comSms.setObjectId(ordOrder.getOrderId());
            comSms.setMms("false");
            smsService.insertComSms(comSms);
            log.info("create sms sendTime AfterThreeMonthsPerfromJob orderId:"+comSms.getObjectId());
        }else{
            log.info("create sms sendTime AfterThreeMonthsPerfromJob orderId:"+ordOrder.getOrderId()+" and orderChannel:"+ordOrder.getChannel()+" and templateId:"+Constant.SMS_TEMPLATE.AFTER_THREEMONTHS_PERFROM.name()+" is empty...");
        }
    }
    
    
    public List<OrdOrder> queryOrderByThreeMonths(){
        /**
         * 得到两个月前的后一个星期 即订单的游玩时间到现在距离点评有效时间只剩一个星期
         */
        List<OrdOrder> list = orderServiceProxy.queryOrderByThreeMonthsAgoWeek(DateUtil.getTimesByTimes(-3,7));
        return list;
    }
 

    public Date getSendDate(){
        //订购游玩日前 三個月的前一個星期的中午10点发送
        String temp = DateUtil.getDateTime("yyyy-MM-dd",new Date());
        Date sendDate = DateUtil.toDate(temp+" 10:00:00","yyyy-MM-dd HH:mm:ss");
        return sendDate;
    }
    
    public void setOrderServiceProxy(OrderService orderServiceProxy) {
        this.orderServiceProxy = orderServiceProxy;
    }
    public ProdChannelService getProdChannelService() {
        return prodChannelService;
    }
    public void setProdChannelService(ProdChannelService prodChannelService) {
        this.prodChannelService = prodChannelService;
    }
    public SmsService getSmsService() {
        return smsService;
    }
    public void setSmsService(SmsService smsService) {
        this.smsService = smsService;
    }
    public OrderService getOrderServiceProxy() {
        return orderServiceProxy;
    }
    public CmtCommentService getCmtCommentService() {
        return cmtCommentService;
    }
    public void setCmtCommentService(CmtCommentService cmtCommentService) {
        this.cmtCommentService = cmtCommentService;
    }
    
}
