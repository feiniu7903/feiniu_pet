package com.lvmama.passport.processor.impl;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.ApplyCodeProcessor;
import com.lvmama.passport.processor.DestroyCodeProcessor;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.ResendCodeProcessor;
import com.lvmama.passport.processor.impl.client.jiuwang.client.v1.asOTA.OTAClientV1;
import com.lvmama.passport.processor.impl.client.jiuwang.help.JiuwangCodeTable;
import com.lvmama.passport.processor.impl.client.jiuwang.help.JiuwangCreateOrderHelp;
import com.lvmama.passport.processor.impl.client.jiuwang.model.v1.request.CreatePaymentOrderRequestBody;
import com.lvmama.passport.processor.impl.client.jiuwang.model.v1.response.CreatePaymentOrderResponseBody;
import com.lvmama.passport.processor.impl.client.jiuwang.model.v1.response.GetOrderByOTAResponseBody;
import com.lvmama.passport.processor.impl.client.jiuwang.model.v1.response.NoticeOrderRefundedByOTAResponseBody;
import com.lvmama.passport.processor.impl.client.jiuwang.model.v1.response.Response;
import com.lvmama.passport.processor.impl.util.OrderUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 九网  深圳鼎游-九寨沟景点
 *
 * Created by linkai on 2014/5/7.
 */
public class JiuwangProcessorImpl implements ApplyCodeProcessor, DestroyCodeProcessor, OrderPerformProcessor, ResendCodeProcessor {
    private static final Log log = LogFactory.getLog(JiuwangProcessorImpl.class);
    private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
    private ComLogService comLogService = (ComLogService) SpringBeanProxy.getBean("comLogService");
//    private PayPaymentService payPaymentService = (PayPaymentService) SpringBeanProxy.getBean("payPaymentService");

    private static Set<PassCode> unusedList = new HashSet<PassCode>();
    private static Set<PassCode> usedList = new HashSet<PassCode>();


    /**
     * 申码
     * @param passCode PassCode
     * @return Passport
     */
    @Override
    public Passport apply(PassCode passCode) {
        log.info("JiuwangProcessorImpl Apply Code: " + passCode.getSerialNo());
        Passport passport = new Passport();
        passport.setSerialno(passCode.getSerialNo());
        passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
        // 设置谁发短信
        passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name());
        try {
            // 获取帮助类
            JiuwangCreateOrderHelp help = JiuwangCreateOrderHelp.getHelp(passCode);
            // 获取requestBody
            CreatePaymentOrderRequestBody orderBody = help.createPaymentOrderBody();
            // 提交下单
            Response response = OTAClientV1.createPaymentOrder(orderBody);
            // 判断下单是否成功
            if (OTAClientV1.isSuccess(response)) {
                // 获取 申码返回的ResponseBody
                CreatePaymentOrderResponseBody responseBody = (CreatePaymentOrderResponseBody) response.getBody();
                // 获取 提交订单的返回状态
                String orderStatus = responseBody.getOrderInfo().getOrderStatus();
                String paySuccess = JiuwangCodeTable.ORDER_STATUS.PREPAY_ORDER_PRINT_SUCCESS.toString();
                // 判断出票是否成功
//                if (StringUtils.equals(orderStatus, JiuwangCodeTable.ORDER_STATUS.PREPAY_ORDER_PRINT_SUCCESS.toString())) {
                if (paySuccess.startsWith(orderStatus)) {
                    // 出票成功
                    // 供应商订单号
                    String partnerorderId = responseBody.getOrderInfo().getPartnerorderId();
                    // 设置供应商订单号
                    passport.setExtId(partnerorderId);// 供应商订单号
                    passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
                    passport.setCode(partnerorderId);
                } else {
                    // 返回支付状态失败，预付：出票失败
                    passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
                    passport.setComLogContent("供应商返回异常：" + JiuwangCodeTable.ORDER_STATUS.getCnName(orderStatus));
                    reapplySet(passport, passCode.getReapplyCount());
                }
            } else {
                // 下单失败
                passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
                passport.setComLogContent("供应商返回异常：" + response.getHeader().getDescribe());
                reapplySet(passport, passCode.getReapplyCount());
            }
        } catch (Exception e) {
            log.error("JiuwangProcessorImpl Apply serialNo Error :" + passCode.getSerialNo());
            passport.setComLogContent(e.toString());
            passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
            this.reapplySet(passport, passCode.getReapplyCount());
            log.error("JiuwangProcessorImpl apply error message", e);
        }
        return passport;
    }

    /**
     * 申码
     * @param passCode PassCode
     * @return Passport
     */
    /* 这个方法使用 3.2 【订单】创建订单 以及 3.3 【订单】支付订单 来完成申码， 目前已废除
    @Override
    public Passport apply(PassCode passCode) {
        log.info("JiuwangProcessorImpl Apply Code: " + passCode.getSerialNo());
        Passport passport = new Passport();
        passport.setSerialno(passCode.getSerialNo());
        passport.setEventType(PassportConstant.PASSCODE_TYPE.APPLAYCODE.name());
        // 设置谁发短信
        passport.setSendSms(PassportConstant.PASSCODE_SMS_SENDER.PARTNER.name());
        try {
            // 获取帮助类
            JiuwangCreateOrderHelp help = JiuwangCreateOrderHelp.getHelp(passCode);
            // 判断是否下单
            if (StringUtils.isNotEmpty(passCode.getExtId())) {
                // 已经下单
                // 供应商订单号
                String partnerorderId = passCode.getExtId();
                // 设置供应商订单号
                passport.setExtId(partnerorderId);// 供应商订单号
                // 订单金额
                String orderPrice = String.valueOf(help.getOrderPrice());
                // 支付订单
                payOrder(passCode, passport, orderPrice);

            } else {
                // 没有下单
                // 获取requestBody
                CreateOrderRequestBody orderBody = help.createOrderBody();
                // 提交下单
                Response response = OTAClientV1.createOrder(orderBody);
                // 判断下单是否成功
                if (OTAClientV1.isSuccess(response)) {
                    // 获取 提交订单返回的ResponseBody
                    CreateOrderResponseBody responseBody = (CreateOrderResponseBody) response.getBody();
                    // 获取 提交订单的返回状态
                    String orderStatus = responseBody.getOrderInfo().getOrderStatus();
                    // 判断下单返回状态是否正确
                    if (StringUtils.equals(orderStatus, JiuwangCodeTable.ORDER_STATUS.PREPAY_ORDER_NOT_PAYED.toString())) {
                        // 下单成功
                        // 供应商订单号
                        String partnerorderId = responseBody.getOrderInfo().getPartnerorderId();
                        // 设置供应商订单号
                        passport.setExtId(partnerorderId);// 供应商订单号
                        // 订单金额
                        String orderPrice = orderBody.getOrderInfo().getOrderPrice();
                        // ---------------------- 支付订单 --------------------
                        payOrder(passCode, passport, orderPrice);
                    } else {
                        // 返回下单状态失败，预付：预订失败
                        passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
                        passport.setComLogContent("供应商返回异常：" + JiuwangCodeTable.ORDER_STATUS.getCnName(orderStatus));
                        reapplySet(passport, passCode.getReapplyCount());
                    }
                } else {
                    // 下单失败
                    passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
                    passport.setComLogContent("供应商返回异常：" + response.getHeader().getDescribe());
                    reapplySet(passport, passCode.getReapplyCount());
                }
            }
        } catch (Exception e) {
            log.error("JiuwangProcessorImpl Apply serialNo Error :" + passCode.getSerialNo());
            passport.setComLogContent(e.toString());
            passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
            this.reapplySet(passport, passCode.getReapplyCount());
            log.error("JiuwangProcessorImpl apply error message", e);
        }
        return passport;
    }*/

    /**
     * 支付订单
     */
/*    private void payOrder(PassCode passCode, Passport passport, String orderPrice) throws Exception {
        String orderStatus1 = JiuwangCodeTable.ORDER_STATUS.PREPAY_ORDER_PRINTING.toString();
        // 提交 支付订单
        Response payResponse = OTAClientV1.payOrder(passport.getExtId(), orderStatus1, orderPrice);
        // 判断支付是否成功
        if (OTAClientV1.isSuccess(payResponse)) {
            // 获取 支付订单返回的ResponseBody
            PayOrderResponseBody payResponseBody = (PayOrderResponseBody) payResponse.getBody();
            // 返回的支付状态
            String payOrderStatus = payResponseBody.getOrderInfo().getOrderStatus();
            // 判断支付返回状态是否正确
            if (StringUtils.equals(payOrderStatus, JiuwangCodeTable.ORDER_STATUS.PREPAY_ORDER_PRINT_SUCCESS.toString())) {
                passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
                passport.setCode(payResponseBody.getOrderInfo().getPartnerorderId());
            } else {
                // 返回支付状态失败，预付：出票失败
                passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
                passport.setComLogContent("供应商返回异常：" + JiuwangCodeTable.ORDER_STATUS.getCnName(payOrderStatus));
                reapplySet(passport, passCode.getReapplyCount());
            }
        } else {
            // 支付订单失败
            passport.setErrorNO(PassportConstant.PASSCODE_ERROR.APPLY.name());
            passport.setComLogContent("供应商返回异常：" + payResponse.getHeader().getDescribe());
            reapplySet(passport, passCode.getReapplyCount());
        }
    }*/

    /**
     * 废码
     * @param passCode PassCode
     * @return Passport
     */
    /* 使用 3.8接口退款，这个需要对方回调 我们的webservice接口 EctripOTAService
    @Override
    public Passport destroy(PassCode passCode) {
        log.info("JiuwangProcessorImpl Destroy Code: " + passCode.getSerialNo());
        Passport passport = new Passport();
        passport.setSerialno(passCode.getSerialNo());
        passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
        long startTime = System.currentTimeMillis();
        try {
            // 获取帮助类
            JiuwangCreateOrderHelp help = JiuwangCreateOrderHelp.getHelp(passCode);
            String partnerorderId, refundSeq, orderPrice, orderQuantity, refundExplain;

            partnerorderId = help.getPartnerorderId();  // 供应商订单ID
            orderPrice = String.valueOf(help.getQuantity());  // 销售金额
            orderQuantity = String.valueOf(help.getOrderPrice());  // 销售数量
            refundSeq = passCode.getSerialNo();  // 退款流水号（目前填写 订单流水号）
            refundExplain = ""; // 退款说明(目前不填)
            Response response = OTAClientV1.refundByUser(partnerorderId, refundSeq, orderPrice, orderQuantity, refundExplain);
            if (OTAClientV1.isSuccess(response)) {
                passport.setComLogContent("废码申请已提交，等待供应商审核中");
                passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
                passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
            } else {
                passport.setComLogContent("供应商返回异常：" + response.getHeader().getDescribe());
                passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
                passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
            }
        } catch (Exception e) {
            log.error("JiuwangProcessorImpl Destroy serialNo Error :" + passCode.getSerialNo() + " UseTime:" + (System.currentTimeMillis() - startTime) / 1000);
            passport.setComLogContent(e.getMessage());
            passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
            passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
            log.error("JiuwangProcessorImpl Destroy error message", e);
        }
        return passport;
    }*/


    /**
     * 废码
     * @param passCode PassCode
     * @return Passport
     */
    @Override
    public Passport destroy(PassCode passCode) {
        log.info("JiuwangProcessorImpl Destroy Code: " + passCode.getSerialNo());
        Passport passport = new Passport();
        passport.setSerialno(passCode.getSerialNo());
        passport.setEventType(PassportConstant.PASSCODE_TYPE.DESTROYCODE.name());
        long startTime = System.currentTimeMillis();
        try {
            // 获取帮助类
            JiuwangCreateOrderHelp help = JiuwangCreateOrderHelp.getHelp(passCode);
            String partnerorderId, refundSeq, orderPrice, orderQuantity, orderRefundPrice, orderRefundCharge, refundExplain;

            partnerorderId = help.getPartnerorderId();  // 供应商订单ID
            orderPrice = String.valueOf(help.getOrderPrice());  // 销售金额
            orderQuantity = String.valueOf(help.getQuantity());  // 销售数量
            refundSeq = passCode.getSerialNo(); // 退款流水号（目前填写 订单流水号）
            orderRefundPrice = String.valueOf(help.getOrderRefundPrice());       // 退订金额
            orderRefundCharge = String.valueOf(help.getOrderRefundCharge());     // 退订手续费
            refundExplain = ""; // 退款说明(目前不填)
            Response response = OTAClientV1.noticeOrderRefund(partnerorderId, refundSeq, orderPrice, orderQuantity, orderRefundPrice, orderRefundCharge, refundExplain);
            if (OTAClientV1.isSuccess(response)) {
                // 获取body
                NoticeOrderRefundedByOTAResponseBody body = (NoticeOrderRefundedByOTAResponseBody) response.getBody();
                partnerorderId = body.getOrderInfo().getPartnerorderId();    // 畅游通生成的订单ID
                refundSeq = body.getOrderInfo().getRefundSeq();              // 退款流水号
                String refundResult = body.getOrderInfo().getRefundResult();        // 退款审核结果
                String refundQuantity = body.getOrderInfo().getRefundQuantity();    // 退款票数
                orderRefundPrice = body.getOrderInfo().getOrderRefundPrice();// 退款金额
                orderRefundCharge = body.getOrderInfo().getOrderRefundCharge();  // 退款手续费
                // 打印日志
                StringBuilder sb = new StringBuilder();
                sb.append("畅游通生成的订单ID：").append(partnerorderId);
                sb.append("，退款流水号：").append(refundSeq);
                sb.append("，退款审核结果：").append(refundResult);
                sb.append("，退款票数：").append(refundQuantity);
                sb.append("，退款金额：").append(orderRefundPrice);
                sb.append("，退款手续费：").append(orderRefundCharge);
                log.info("JiuwangProcessorImpl Destroy process success! requestData : " + sb.toString());

                // 判断是否退款成功
                if (StringUtils.equals(refundResult, JiuwangCodeTable.REFUND_RESULT.APPROVE.getCode())) {
                    // 同意退款
                    passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
                    String mgm;
                    // 判断是否全额退款
                    if (help.getOrderRefundPrice().longValue() == Long.valueOf(orderRefundPrice).longValue()) {
                        mgm = "全额退款，" + sb.toString();
                        addComLog(passCode, mgm, "废除通关码成功");
                    } else {
                        mgm = "部分退款，" + sb.toString();
                        addComLog(passCode, mgm, "废除通关码成功");
                    }
                    log.info("JiuwangProcessorImpl Destoy Success message: " + mgm);
                } else if (StringUtils.equals(refundResult, JiuwangCodeTable.REFUND_RESULT.RETRY.getCode())) {
                    // 重试退款
                    passport.setComLogContent("供应商返回信息：重试退款");
                    passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
                    passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
                    log.info("JiuwangProcessorImpl Destoy Failed message: 供应商返回信息：重试退款");
                } else {
                    // 拒绝退款
                    passport.setComLogContent("供应商返回信息：拒绝退款");
                    passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
                    passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
                    log.info("JiuwangProcessorImpl Destoy Error message: 供应商返回信息：拒绝退款");

                }
            } else {
                passport.setComLogContent("供应商返回异常：" + response.getHeader().getDescribe());
                passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
                passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
                log.info("JiuwangProcessorImpl Destoy Error message: 供应商返回异常：" + response.getHeader().getDescribe());
            }
        } catch (Exception e) {
            log.error("JiuwangProcessorImpl Destroy serialNo Error :" + passCode.getSerialNo() + " UseTime:" + (System.currentTimeMillis() - startTime) / 1000);
            passport.setComLogContent(e.getMessage());
            passport.setErrorNO(PassportConstant.PASSCODE_ERROR.DESTROY.name());
            passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
            log.error("JiuwangProcessorImpl Destroy error message", e);
        }
        return passport;
    }

    /**
     * 履行
     * @param passCode PassCode
     * @return Passport
     */
    @Override
    public Passport perform(PassCode passCode) {
        log.info("JiuwangProcessorImpl Perform Code: " + passCode.getSerialNo());
        Passport passport = null;
        if (isNeedCheckout(passCode)) {
            try {
                // 获取供应商订单号
                String partnerOrderId = passCode.getExtId();
                // 查询订单信息
                Response response = OTAClientV1.getOrder(partnerOrderId);
                // 判断是否查询成功
                if (OTAClientV1.isSuccess(response)) {
                    GetOrderByOTAResponseBody responseBody = (GetOrderByOTAResponseBody) response.getBody();
                    String orderStatus = responseBody.getOrderInfo().getOrderStatus();
                    // 判断状态是否已消费
                    if (StringUtils.equals(orderStatus, JiuwangCodeTable.ORDER_STATUS.PREPAY_ORDER_CONSUMED.toString())) {
                        passport = new Passport();
                        passport.setChild("0");
                        passport.setAdult("0");
                        passport.setUsedDate(new Date());
                        passport.setDeviceId("jiuzhaigou");
                        stopCheckout(passCode);
                    }
                } else {
                    stopCheckout(passCode);
                    this.addComLog(passCode, response.getHeader().getDescribe(), "查询履行状态失败");
                    log.info("JiuwangProcessorImpl perform fail message: " + response.getHeader().getCode() + " " + response.getHeader().getDescribe());
                }
            } catch (Exception e) {
                log.error("JiuwangProcessorImpl Perform error message!", e);
            }
        }
        return passport;
    }

    /**
     * 重新发码
     * @param passCode PassCode
     * @return Passport
     */
    @Override
    public Passport resend(PassCode passCode) {
        log.info("JiuwangProcessorImpl Resend SMS ：" + passCode.getSerialNo());
        Passport passport = new Passport();
        passport.setSerialno(passCode.getSerialNo());
        passport.setEventType(PassportConstant.PASSCODE_TYPE.RESEND.name());
        try {
            // 获取订单
            OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
            // 获取联系人
            OrdPerson contact = OrderUtil.init().getContract(ordOrder);
            // 获取供应商订单号
            String partnerOrderId = passCode.getExtId();
            // 获取联系人手机号
            String phoneNumber = contact.getMobile();
            // 发送入园凭证
            Response response = OTAClientV1.sendOrderEticket(partnerOrderId, phoneNumber);
            // 判断是否发送成功
            if (OTAClientV1.isSuccess(response)) {
                // 发码成功
                passport.setStatus(PassportConstant.PASSCODE_STATUS.SUCCESS.name());
            } else {
                // 发码失败
                passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
                passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
                passport.setComLogContent("供应商返回异常：" + response.getHeader().getDescribe());
            }
        } catch (Exception e) {
            passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
            passport.setErrorNO(PassportConstant.PASSCODE_ERROR.RESEND.name());
            passport.setComLogContent(e.getMessage());
            log.error("JiuwangProcessorImpl resend Error", e);
        }
        return passport;
    }

    private boolean isNeedCheckout(PassCode passCode) {
        return "SUCCESS".equals(passCode.getStatus()) && isPassCodeUnused(passCode);
    }

    private boolean isPassCodeUnused(PassCode passCode) {
        if (!usedList.contains(passCode)) {
            unusedList.add(passCode);
            return true;
        }
        return false;
    }

    private void stopCheckout(PassCode passCode) {
        unusedList.remove(passCode);
        usedList.add(passCode);
    }

    /**
     * 添加日志
     */
    private void addComLog(PassCode passCode, String logContent, String logName) {
        ComLog log = new ComLog();
        log.setObjectType("PASS_CODE");
        log.setParentId(passCode.getOrderId());
        log.setObjectId(passCode.getCodeId());
        log.setOperatorName("SYSTEM");
        log.setLogType(Constant.COM_LOG_ORDER_EVENT.systemApprovePass.name());
        log.setLogName(logName);
        log.setContent(logContent);
        comLogService.addComLog(log);
    }

    /**
     * 重新申请码处理
     *
     * @param passport Passport
     * @param times    Long
     */
    public void reapplySet(Passport passport, long times) {
        int reapplyMinute = 5;
        if (times < 3) {
            passport.setReapplyTime(DateUtils.addMinutes(new Date(), reapplyMinute));
            passport.setStatus(PassportConstant.PASSCODE_STATUS.TEMP_FAILED.name());
        } else {
            passport.setStatus(PassportConstant.PASSCODE_STATUS.FAILED.name());
        }
    }
}
