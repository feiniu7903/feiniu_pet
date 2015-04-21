package com.lvmama.passport.processor.impl.client.jiuwang.client.v1.asOTA;

import com.lvmama.passport.processor.impl.client.jiuwang.help.JiuwangCodeTable;
import com.lvmama.passport.processor.impl.client.jiuwang.model.v1.request.CreateOrderRequestBody;
import com.lvmama.passport.processor.impl.client.jiuwang.model.v1.request.CreatePaymentOrderRequestBody;
import com.lvmama.passport.processor.impl.client.jiuwang.model.v1.response.CreatePaymentOrderResponseBody;
import com.lvmama.passport.processor.impl.client.jiuwang.model.v1.response.GetProductByOTAResponseBody;
import com.lvmama.passport.processor.impl.client.jiuwang.model.v1.response.Response;
import org.junit.Test;

import java.util.List;

public class JiuwangOTAClientV1Test {

    /**
     * 查找单个产品
     */
    @Test
    public void testGetProductByResourceId() throws Exception {
        // 2711
        String resourceId = "2711";
        GetProductByOTAResponseBody.ProductInfos.ProductInfo productInfo = OTAClientV1.getProductByResourceId(resourceId);
        System.out.println("产品ID :" + productInfo.getBaseInfo().getResourceId());
        System.out.println("产品名称 :" + productInfo.getBaseInfo().getProductName());
        System.out.println("BookPersonType :" + productInfo.getBookConfig().getBookPersonType());
        // 价格日历
        System.out.println("---------------价格日历----------------");
        List<GetProductByOTAResponseBody.ProductInfos.ProductInfo.PriceConfig.CalendarPrices.CalendarPrice> calendarPrices = productInfo.getPriceConfig().getCalendarPrices().getCalendarPrice();
        for (GetProductByOTAResponseBody.ProductInfos.ProductInfo.PriceConfig.CalendarPrices.CalendarPrice calendarPrice : calendarPrices) {
            System.out.println("日期 ：" + calendarPrice.getUseDate() + "    单价 ：" + calendarPrice.getSellPrice());
        }
    }

    /**
     * 查找所有产品
     */
    @Test
    public void testGetProduct() throws Exception {
        /*
            产品ID ： 428   产品名称 ：景区成人套票-成人票
            产品ID ： 2711   产品名称 ：测试门票-成人票
            产品ID ： 2720   产品名称 ：景区成人门票-学生票
        */
        Response response = OTAClientV1.getProduct("ALL", "1", "100", "");
        GetProductByOTAResponseBody body = (GetProductByOTAResponseBody) response.getBody();
        System.out.println("数量 ：" + body.getCount());
        for (GetProductByOTAResponseBody.ProductInfos.ProductInfo productInfo : body.getProductInfos().getProductInfo()) {
            System.out.println("产品ID ： " + productInfo.getBaseInfo().getResourceId() + "   "
                    + "产品名称 ：" + productInfo.getBaseInfo().getProductName());
        }
    }

    /**
     * 创建订单 （目前已经不用，改用创建支付订单）
     */
    @Test
    public void testCreateOrder() throws Exception {
        String resourceId = "3";
        String productName = "武当山门票-游客";
        String userDate = "2014-05-22";
        String orderQuantity = "4";
        String sellPrice = "1";
        System.out.println(String.valueOf(Long.parseLong(orderQuantity) * Long.parseLong(sellPrice)));

        String orderId = "201405220001";
        // ---------------------创建订单-----------------------
        CreateOrderRequestBody body = getOrderBody(resourceId, productName, userDate, orderQuantity, sellPrice, orderId);

        /*
            创建订单成功
            20140520999000001  201405220001  3 4 30000 120000
        */
        Response response = OTAClientV1.createOrder(body);
        // 打印错误消息
        printFailed(response);
    }

    /**
     * 创建支付订单
     */
    @Test
    public void testCreatePaymentOrder() throws Exception {
        String resourceId = "2711";
        String productName = "测试门票-成人票";
        String userDate = "2014-05-24";
        String orderQuantity = "5";
        String sellPrice = "1";

        String orderId = "201405200005";
        // ---------------------创建支付订单-----------------------
        CreatePaymentOrderRequestBody body = getOrderPaymentBody(resourceId, productName, userDate, orderQuantity, sellPrice, orderId);
        System.out.println("总金额：" + String.valueOf(Long.parseLong(orderQuantity) * Long.parseLong(sellPrice)));
        /*
            创建支付订单成功
            20140520999000009  201405200004  2711  测试门票-成人票 4 4
            20140520999000013  201405200005  2711  测试门票-成人票 5 5

        */
        Response response = OTAClientV1.createPaymentOrder(body);
        CreatePaymentOrderResponseBody responseBody = (CreatePaymentOrderResponseBody) response.getBody();
        // 获取 提交订单的返回状态
        String orderStatus = responseBody.getOrderInfo().getOrderStatus();
        String paySuccess = JiuwangCodeTable.ORDER_STATUS.PREPAY_ORDER_PRINT_SUCCESS.toString();
        // 判断出票是否成功
//                if (StringUtils.equals(orderStatus, JiuwangCodeTable.ORDER_STATUS.PREPAY_ORDER_PRINT_SUCCESS.toString())) {
        if (paySuccess.startsWith(orderStatus)) {
            System.out.println("申码成功！");
        } else {
            System.out.println("申码失败！");
        }
        // 打印错误消息
        printFailed(response);
    }

    /**
     * 获取订单详情
     */
    @Test
    public void testGetOrder() throws Exception {
        String partnerOrderId = "20140521999000044";

        Response response = OTAClientV1.getOrder(partnerOrderId);
        // 打印错误消息
        printFailed(response);
    }

    /**
     * 支付订单
     */
    @Test
    public void testPayOrder() throws Exception {
        String partnerOrderId = "20140520999000001";
        String orderStatus = JiuwangCodeTable.ORDER_STATUS.PREPAY_ORDER_PRINTING.toString();
        String orderPrice = "120000";
        Response response = OTAClientV1.payOrder(partnerOrderId, orderStatus, orderPrice);
        // 打印错误消息
        printFailed(response);
    }

    /**
     * 用户向供应商申请退款
     */
    @Test
    public void testRefundByUser() throws Exception {
        String orderStatus = "PREPAY_ORDER_PRINT_SUCCE";
        // 判断出票是否成功
//                if (StringUtils.equals(orderStatus, JiuwangCodeTable.ORDER_STATUS.PREPAY_ORDER_PRINT_SUCCESS.toString())) {
        if ("PREPAY_ORDER_PRINT_SUCCESS".startsWith(orderStatus)) {
            System.out.println("申码成功！");
        } else {
            System.out.println("申码失败！");
        }
    }

    /**
     * 发送短信消息
     */
    @Test
    public void testSendOrderEticket() throws Exception {
        // 获取供应商订单号
        String partnerOrderId = "20140520999000013";
        // 获取联系人手机号
        String phoneNumber = "15317713190";
        // 发送入园凭证
        Response response = OTAClientV1.sendOrderEticket(partnerOrderId, phoneNumber);
        printFailed(response);
    }

    /**
     * OTA退款通知
     */
    @Test
    public void testNoticeOrderRefund() throws Exception {
        String partnerorderId, refundSeq, orderPrice, orderQuantity, orderRefundPrice, orderRefundCharge, refundExplain;

        partnerorderId = "20140520999000013";    // 供应商订单ID
        orderPrice = "5";        // 销售金额
        orderQuantity = "5";     // 销售数量
        refundSeq = "201405200005";         // 退款流水号（目前填写 订单流水号）
        orderRefundPrice = "5";  // 退订金额
        orderRefundCharge = "0"; // 退订手续费
        refundExplain = "测试。。。";     // 退款说明(目前不填)
        Response response = OTAClientV1.noticeOrderRefund(partnerorderId, refundSeq, orderPrice, orderQuantity, orderRefundPrice, orderRefundCharge, refundExplain);
        printFailed(response);
    }

    /**
     * 同步订单
     */
    @Test
    public void testPushOrder() throws Exception {
        // 不做测试，该接口可以不用做实现
    }

    private CreatePaymentOrderRequestBody getOrderPaymentBody(String resourceId, String productName, String userDate, String orderQuantity, String sellPrice, String orderId) {
        // ---------------------创建订单-----------------------
        CreatePaymentOrderRequestBody body = new CreatePaymentOrderRequestBody();
        CreatePaymentOrderRequestBody.OrderInfo o = new CreatePaymentOrderRequestBody.OrderInfo();
        CreatePaymentOrderRequestBody.OrderInfo.Product product = new CreatePaymentOrderRequestBody.OrderInfo.Product();

        //产品信息
        product.setResourceId(resourceId);//产品ID
        product.setProductName(productName);//票名
        product.setVisitDate(userDate);//游览日期，yyyy-MM-dd
        product.setSellPrice(sellPrice);//单价，单位：分

        //取票人信息
        CreatePaymentOrderRequestBody.OrderInfo.ContactPerson contactPerson = new CreatePaymentOrderRequestBody.OrderInfo.ContactPerson();
        contactPerson.setName("林凯");//取票人姓名
        contactPerson.setMobile("15317713190");//取票人电话
        contactPerson.setCredentialsType("ID_CARD");//取票人证件类型
        contactPerson.setCredentials("42088119851016255X");//取票人证件号

        //游客信息，根据产品的实际情况处理
        //产品bookPersonType为CONTACT_PERSON时，person节点赋空值即可
        o.setVisitPerson(new CreatePaymentOrderRequestBody.OrderInfo.VisitPerson());

        //OTA订单ID
        o.setOrderId(orderId);
        //产品信息
        o.setProduct(product);
        //取票人信息
        o.setContactPerson(contactPerson);
        //订单数量
        o.setOrderQuantity(orderQuantity);
        //订单金额
        o.setOrderPrice(String.valueOf(Long.parseLong(orderQuantity) * Long.parseLong(sellPrice)));
        //订单状态  ---- 已付款
        o.setOrderStatus("PREPAY_ORDER_PRINTING");

        body.setOrderInfo(o);
        return body;
    }

    private CreateOrderRequestBody getOrderBody(String resourceId, String productName, String userDate, String orderQuantity, String sellPrice, String orderId) {
        // ---------------------创建订单-----------------------
        CreateOrderRequestBody body = new CreateOrderRequestBody();
        CreateOrderRequestBody.OrderInfo o = new CreateOrderRequestBody.OrderInfo();
        CreateOrderRequestBody.OrderInfo.Product product = new CreateOrderRequestBody.OrderInfo.Product();

        //产品信息
        product.setResourceId(resourceId);//产品ID
        product.setProductName(productName);//票名
        product.setVisitDate(userDate);//游览日期，yyyy-MM-dd
        product.setSellPrice(sellPrice);//单价，单位：分

        //取票人信息
        CreateOrderRequestBody.OrderInfo.ContactPerson contactPerson = new CreateOrderRequestBody.OrderInfo.ContactPerson();
        contactPerson.setName("林凯");//取票人姓名
        contactPerson.setMobile("15317713190");//取票人电话
        contactPerson.setCredentialsType("ID_CARD");//取票人证件类型
        contactPerson.setCredentials("42088119851016255X");//取票人证件号

        //游客信息，根据产品的实际情况处理
        //产品bookPersonType为CONTACT_PERSON时，person节点赋空值即可
        o.setVisitPerson(new CreateOrderRequestBody.OrderInfo.VisitPerson());

        //OTA订单ID
        o.setOrderId(orderId);
        //产品信息
        o.setProduct(product);
        //取票人信息
        o.setContactPerson(contactPerson);
        //订单数量
        o.setOrderQuantity(orderQuantity);
        //订单金额
        o.setOrderPrice(String.valueOf(Long.parseLong(orderQuantity) * Long.parseLong(sellPrice)));
        //订单状态
        o.setOrderStatus("PREPAY_ORDER_INIT");

        body.setOrderInfo(o);
        return body;
    }

    private void printFailed(Response response) {
        if (!OTAClientV1.isSuccess(response)) {
            System.out.println("错误消息 ： " + response.getHeader().getCode() + "  " + response.getHeader().getDescribe());
        }
    }
}