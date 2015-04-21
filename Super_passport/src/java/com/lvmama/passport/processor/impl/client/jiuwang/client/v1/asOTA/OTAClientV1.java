package com.lvmama.passport.processor.impl.client.jiuwang.client.v1.asOTA;

import com.lvmama.passport.processor.impl.client.jiuwang.EctripOTAervice.EctripOTAServiceSoapBindingStub;
import com.lvmama.passport.processor.impl.client.jiuwang.common.util.DateUtils;
import com.lvmama.passport.processor.impl.client.jiuwang.common.util.EncryptUtil;
import com.lvmama.passport.processor.impl.client.jiuwang.common.util.JsonUtils;
import com.lvmama.passport.processor.impl.client.jiuwang.help.JiuwangCodeTable;
import com.lvmama.passport.processor.impl.client.jiuwang.model.v1.Base64Util;
import com.lvmama.passport.processor.impl.client.jiuwang.model.v1.request.*;
import com.lvmama.passport.processor.impl.client.jiuwang.model.v1.response.*;
import com.lvmama.passport.utils.WebServiceConstant;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 * web service client 基础web服务调用Util
 */
public class OTAClientV1 {
    private static Log log = LogFactory.getLog(OTAClientV1.class);
    // 方法名
    private final static String GETPRODUCtBYOTA = "getProductByOTA";    // OTA 获取产品信息
    private final static String CREATEORDER = "createOrder";    // 创建订单
    private final static String CREATEPAYMENTORDER = "createPaymentOrder";    // 创建支付订单
    private final static String PAYORDER = "payOrder";          // 支付订单
    private final static String PUSHORDER = "pushOrde";         // 同步订单
    private final static String GETORDERBYOTA = "getOrderByOTA";    // OTA获取订单信息
    private final static String SENDORDERETICKET = "sendOrderEticket";  // （重）发入园凭证
    private final static String NOTICEORDERREFUNDEDBYOTA = "noticeOrderRefundedByOTA";  // OTA退款通知
    private final static String APPLYORDERREFUNDBYUSER = "applyOrderRefundByUser";  // 用户向供应商申请退款

    // 接口访问地址
    public static String wsdl = WebServiceConstant.getProperties("jiuwang.wsdl");
    public static String supplierIdentity = WebServiceConstant.getProperties("jiuwang.supplierIdentity");// 供应商ID
    public static String OTASignKey = WebServiceConstant.getProperties("jiuwang.OTASignKey"); // 调试用key
    public static String ticketSystem = WebServiceConstant.getProperties("jiuwang.ticketSystem"); //OTA标识
    public static String application = WebServiceConstant.getProperties("jiuwang.application");
    public static String processor = WebServiceConstant.getProperties("jiuwang.processor");
    public static String version = WebServiceConstant.getProperties("jiuwang.version");

    // 返回成功
    public static final String SUCESS_CODE = JiuwangCodeTable.RESULT_CODE.CODE_1000.getCode();

    /**
     * 发送消息
     *
     * @param body   请求内容
     * @param method 方法名
     * @return 返回结果
     */
    private static Response sendData(RequestBody body, String method) throws Exception {
        // 获取request
        Request request = getRequest(body);
        Response response;

        try {
            // 将request对象 转换 为Json格式数据
            String requestString = marshal(request, Request.class);
            log.info("method : " + method + "  requestData : " + requestString);
            // BASE64编码
            requestString = Base64Util.getBASE64(requestString);

            // 将数据打包为JsonObject
            AgentStandardExchangeJsonObject requsetJson = getJsonObject(requestString);
            requestString = JsonUtils.marshalToString(requsetJson);
            // 发送消息
            log.info("requset method : " + method + "  requsetJson : " + requestString);
            String responseString = send(method, requestString);
            log.info("response method : " + method + "  responseJson : " + responseString);
            if (null == responseString) {
                //畅游通交互失败
                throw new Exception("Response is empty!");
            }
            // 将json 格式化 为 AgentStandardExchangeJsonObject对象
            AgentStandardExchangeJsonObject responseJson = JsonUtils.unmarshalFromString(responseString, AgentStandardExchangeJsonObject.class);
            // 校验安全密文
            if (!checkData(responseJson)) {
                log.info("security cipher check failed! method : " + method + "  responseData : " + responseJson.getData());
                throw new Exception("security cipher check failed!");
            }
            // 获取 数据
            String responseData = responseJson.getData();
            // BASE64解码
            responseData = Base64Util.getFromBASE64(responseData);
            log.info("method : " + method + "  responseData : " + responseData);
            // 将Json格式数据 转换 Response对象
            response = (Response) unmarshal(responseData, Response.class);
            // 判断是否成功
            if (isSuccess(response)) {
                return response;
            } else {
                log.info("response data failed! method : " + method + "  cause ---  Code:" + response.getHeader().getCode() + "  Describe:" + response.getHeader().getDescribe());
            }
        } catch (Exception e) {
            log.error("sendData error! method : " + method, e);
            e.printStackTrace();
            throw e;
        }
        return response;
    }

    // 获取封装对象
    public static AgentStandardExchangeJsonObject getJsonObject(String data) {
        AgentStandardExchangeJsonObject requsetJson = new AgentStandardExchangeJsonObject();
        requsetJson.setData(data);
        requsetJson.setSecurityType("MD5");
        requsetJson.setSigned(EncryptUtil.MD5Hex(OTASignKey + data).toUpperCase());
        return requsetJson;
    }

    public static String send(String method, String sendStr) throws MalformedURLException, RemoteException {
        //            javax.xml.rpc.Service service = null;
        java.net.URL endpointURL = new java.net.URL(wsdl);
        EctripOTAServiceSoapBindingStub ssl = new EctripOTAServiceSoapBindingStub(endpointURL, null);
        ssl.setMaintainSession(true);
        return ssl.doOTARequest(method, sendStr);
    }

    /**
     * 校验数据
     */
    public static boolean checkData(AgentStandardExchangeJsonObject dataJson) {
        return StringUtils.equals(dataJson.getSigned().toUpperCase(),
                EncryptUtil.MD5Hex(OTASignKey + dataJson.getData()).toUpperCase());

    }

    /**
     * 将json格式数据 转换为 对象
     */
    public static Object unmarshal(String jsonData, Class<?> c) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(c);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        StringReader reader = new StringReader(jsonData);
        return unmarshaller.unmarshal(reader);
    }

    /**
     * 将对象 转换为 json格式数据
     */
    public static String marshal(Object obj, Class<?> c) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(c);
        Marshaller requestMarshaller = jaxbContext.createMarshaller();

        StringWriter sw = new StringWriter();
        requestMarshaller.marshal(obj, sw);
        return sw.toString();
    }

    /**
     * 获取Reuquest，组装方法头
     *
     * @param body 方法体
     * @return Reuquest
     */
    private static Request getRequest(RequestBody body) {
        Request request = new Request();
        request.setBody(body);
        request.setHeader(new RequestHeader());
        request.getHeader().setApplication(application);
        request.getHeader().setBodyType(request.getBody().getClass().getSimpleName());
        request.getHeader().setCreateTime(DateUtils.formatDateTime(new Date()));
        request.getHeader().setCreateUser(ticketSystem);
        request.getHeader().setVersion(version);
        request.getHeader().setProcessor(processor);
        request.getHeader().setSupplierIdentity(supplierIdentity);
        return request;
    }

    /**
     * 判断处理是否成功
     */
    public static boolean isSuccess(Response response) {
        return response.getHeader().getCode().equals(SUCESS_CODE);
    }


    /**
     * 获取产品信息
     *
     * @param resourceId  供应商的产品 ID号，当method=SINGLE有效
     * @return 返回产品信息
     */
    public static GetProductByOTAResponseBody.ProductInfos.ProductInfo getProductByResourceId(String resourceId) throws Exception {
        // 请求体
        // --------------------获取产品信息--------------------
        GetProductByOTARequestBody body = new GetProductByOTARequestBody();
        body.setMethod("SINGLE");
        body.setCurrentPage("");
        body.setPageSize("");
        body.setResourceId(resourceId);

        // 获取产品信息
        Response response = sendData(body, GETPRODUCtBYOTA);
        if (isSuccess(response)) {
            GetProductByOTAResponseBody responseBody = (GetProductByOTAResponseBody) response.getBody();
            List<GetProductByOTAResponseBody.ProductInfos.ProductInfo> productInfos = responseBody.getProductInfos().getProductInfo();
            if (productInfos.size() > 0) {
                return productInfos.get(0);
            }
        }
        return null;
    }

    /**
     * 获取产品信息
     *
     * @param method      ALL：获取全部产品信息； SINGLE：获取单个产品信息
     * @param currentPage 当前页数，当method=ALL 时有效
     * @param pageSize    每页记录数，当method=ALL 时有效
     * @param resourceId  供应商的产品 ID号，当method=SINGLE有效
     * @return 返回产品信息
     */
    public static Response getProduct(String method, String currentPage, String pageSize, String resourceId) throws Exception {
        // 请求体
        // --------------------获取产品信息--------------------
        GetProductByOTARequestBody body = new GetProductByOTARequestBody();
        body.setMethod(method);
        body.setCurrentPage(currentPage);
        body.setPageSize(pageSize);
        body.setResourceId(resourceId);

        Response response = sendData(body, GETPRODUCtBYOTA);
        if (response != null && isSuccess(response)) {
            GetProductByOTAResponseBody responseBody = (GetProductByOTAResponseBody) response.getBody();
            System.out.println("--------------------------------创建订单--------------------------------");
            System.out.println("产品数量 : " + responseBody.getCount());
        }
        return sendData(body, GETPRODUCtBYOTA);
    }


    /**
     * 创建订单
     *
     * @param orderBody 订单内容
     */
    public static Response createOrder(CreateOrderRequestBody orderBody) throws Exception {
        /*// ---------------------创建订单-----------------------
        CreateOrderRequestBody body = new CreateOrderRequestBody();
        CreateOrderRequestBody.OrderInfo o = new CreateOrderRequestBody.OrderInfo();
        CreateOrderRequestBody.OrderInfo.Product product = new CreateOrderRequestBody.OrderInfo.Product();

        //产品信息
        product.setResourceId("1");//产品ID
        product.setProductName("武当山旺季票");//票名
        product.setVisitDate("2014-05-06");//游览日期，yyyy-MM-dd
        product.setSellPrice("20000");//单价，单位：分

        //取票人信息
        CreateOrderRequestBody.OrderInfo.ContactPerson contactPerson = new CreateOrderRequestBody.OrderInfo.ContactPerson();
        contactPerson.setName("AAA");//取票人姓名
        contactPerson.setMobile("13312343678");//取票人电话
        contactPerson.setCredentialsType("ID_CARD");//取票人证件类型
        contactPerson.setCredentials("433333333333333333");//取票人证件号

        //游客信息，根据产品的实际情况处理
        //产品bookPersonType为CONTACT_PERSON时，person节点赋空值即可
        o.setVisitPerson(new CreateOrderRequestBody.OrderInfo.VisitPerson());

        //OTA订单ID
        o.setOrderId("OTA_Order_ID");
        //产品信息
        o.setProduct(product);
        //取票人信息
        o.setContactPerson(contactPerson);
        //订单数量
        o.setOrderQuantity("5");
        //订单金额
        o.setOrderPrice("100000");
        //订单状态
        o.setOrderStatus("PREPAY_ORDER_INIT");

        body.setOrderInfo(o);*/

        Response response = sendData(orderBody, CREATEORDER);
        if (response != null && isSuccess(response)) {
            CreateOrderResponseBody responseBody = (CreateOrderResponseBody) response.getBody();
            System.out.println("--------------------------------创建订单--------------------------------");
            System.out.println("供应商订单号 : " + responseBody.getOrderInfo().getPartnerorderId());
            System.out.println("供应商订单状态 : " + responseBody.getOrderInfo().getOrderStatus());
        }
        return response;
    }

    /**
     * 创建支付订单
     *
     * @param orderBody 订单内容
     */
    public static Response createPaymentOrder(CreatePaymentOrderRequestBody orderBody) throws Exception {
        Response response = sendData(orderBody, CREATEPAYMENTORDER);
        if (response != null && isSuccess(response)) {
            CreatePaymentOrderResponseBody responseBody = (CreatePaymentOrderResponseBody) response.getBody();
            System.out.println("--------------------------------创建支付订单--------------------------------");
            System.out.println("供应商订单号 : " + responseBody.getOrderInfo().getPartnerorderId());
            System.out.println("供应商订单状态 : " + responseBody.getOrderInfo().getOrderStatus());
        }
        return response;
    }

    /**
     * 获取订单信息
     *
     * @param partnerOrderId 订单ID
     * @return 订单信息
     */
    public static Response getOrder(String partnerOrderId) throws Exception {
        GetOrderByOTARequestBody body = new GetOrderByOTARequestBody();
        //畅游通生成的订单ID：
        body.setPartnerOrderId(partnerOrderId);
        Response response = sendData(body, GETORDERBYOTA);
        if (response != null && isSuccess(response)) {
            GetOrderByOTAResponseBody responseBody = (GetOrderByOTAResponseBody) response.getBody();
            System.out.println("--------------------------------获取订单消息--------------------------------");
            System.out.println("供应商订单号 : " + responseBody.getOrderInfo().getPartnerorderId());
            System.out.println("订单状态 : " + responseBody.getOrderInfo().getOrderStatus());
            System.out.println("订单总票数 : " + responseBody.getOrderInfo().getOrderQuantity());
            System.out.println("电子票号 : " + responseBody.getOrderInfo().getEticketNo());
            System.out.println("电子票发送状态 : " + responseBody.getOrderInfo().getEticketSended());
            System.out.println("已消费票数 : " + responseBody.getOrderInfo().getUseQuantity());
            System.out.println("消费信息 : " + responseBody.getOrderInfo().getConsumeInfo());
        }
        return response;
    }


    /**
     * （重）发入园凭证
     *
     * @param orderId     订单ID
     * @param phoneNumber 手机号
     * @return body
     */
    public static Response sendOrderEticket(String orderId, String phoneNumber) throws Exception {
        SendOrderEticketRequestBody body = new SendOrderEticketRequestBody();
        SendOrderEticketRequestBody.OrderInfo o = new SendOrderEticketRequestBody.OrderInfo();
        // 已支付订单 畅游通订单号
        o.setPartnerOrderId(orderId);
        // 手机号码
        o.setPhoneNumber(phoneNumber);

        body.setOrderInfo(o);

        Response response = sendData(body, SENDORDERETICKET);
        if (response != null && isSuccess(response)) {
            SendOrderEticketResponseBody responseBody = (SendOrderEticketResponseBody) response.getBody();
            System.out.println("--------------------------------（重）发入园凭证--------------------------------");
            System.out.println("消息 : " + responseBody.getMessage());
        }
        return response;
    }

    /**
     * OTA退款通知
     *
     * @return body
     */
    public static Response noticeOrderRefund(String partnerorderId, String refundSeq, String orderPrice, String orderQuantity, String orderRefundPrice, String orderRefundCharge, String refundExplain) throws Exception {
        NoticeOrderRefundedByOTARequestBody body = new NoticeOrderRefundedByOTARequestBody();
        NoticeOrderRefundedByOTARequestBody.OrderInfo o = new NoticeOrderRefundedByOTARequestBody.OrderInfo();
        //订单ID
        o.setPartnerorderId(partnerorderId);
        //OTA退订流水号
        o.setRefundSeq(refundSeq);
        //订单数量
        o.setOrderQuantity(orderQuantity);
        //订单金额 单位：分
        o.setOrderPrice(orderPrice);
        //退订数量
        o.setRefundQuantity(orderQuantity);
        //退订金额
        o.setOrderRefundPrice(orderRefundPrice);
        //退订手续费
        o.setOrderRefundCharge(orderRefundCharge);
        //退款说明
        o.setRefundExplain(refundExplain);

        body.setOrderInfo(o);

        Response response = sendData(body, NOTICEORDERREFUNDEDBYOTA);
        if (response != null && isSuccess(response)) {
            NoticeOrderRefundedByOTAResponseBody responseBody = (NoticeOrderRefundedByOTAResponseBody) response.getBody();
            System.out.println("--------------------------------OTA退款通知--------------------------------");
            System.out.println("畅游通生成的订单ID : " + responseBody.getOrderInfo().getPartnerorderId());
            System.out.println("退款流水号 : " + responseBody.getOrderInfo().getRefundSeq());
            System.out.println("退款审核结果 : " + responseBody.getOrderInfo().getRefundResult());
            System.out.println("退款票数 : " + responseBody.getOrderInfo().getRefundQuantity());
            System.out.println("退款金额 : " + responseBody.getOrderInfo().getOrderRefundPrice());
            System.out.println("退款手续费 : " + responseBody.getOrderInfo().getOrderRefundCharge());
        }
        return response;
    }

    /**
     * 用户向供应商申请退款
     */
    public static Response refundByUser(String partnerorderId, String refundSeq, String orderPrice, String orderQuantity, String refundExplain) throws Exception {
        ApplyOrderRefundByUserRequestBody body = new ApplyOrderRefundByUserRequestBody();
        ApplyOrderRefundByUserRequestBody.OrderInfo refund = new ApplyOrderRefundByUserRequestBody.OrderInfo();

        //畅游通订单ID
        refund.setPartnerorderId(partnerorderId);
        //分销商退款流水号ID
        refund.setRefundSeq(refundSeq);
        //订单金额
        refund.setOrderPrice(orderPrice);
        //订单票张数
        refund.setOrderQuantity(orderQuantity);
        //退订金额
        refund.setOrderRefundPrice(orderPrice);
        //退订张数
        refund.setRefundQuantity(orderQuantity);
        //退订手续费
        refund.setOrderRefundCharge("0");
        //退订说明
        refund.setRefundExplain(refundExplain);

        body.setOrderInfo(refund);

        Response response = sendData(body, APPLYORDERREFUNDBYUSER);
        if (response != null && isSuccess(response)) {
            ApplyOrderRefundByUserResponseBody applyOrderRefundResponse = (ApplyOrderRefundByUserResponseBody) response.getBody();
            System.out.println("--------------------------------用户申请退款--------------------------------");
            System.out.println("退款返回信息 : " + applyOrderRefundResponse.getMessage());
        }
        return response;
    }

    /**
     * 同步订单
     *
     * @return body
     */
    public static Response pushOrder() throws Exception {
        String partnerorderId = "20140415999000108";//测试用订单ID

        PushOrderRequestBody body = new PushOrderRequestBody();

        PushOrderRequestBody.OrderInfo o = new PushOrderRequestBody.OrderInfo();

        PushOrderRequestBody.OrderInfo.ContactPerson contactPerson = new PushOrderRequestBody.OrderInfo.ContactPerson();

//        PushOrderRequestBody.OrderInfo.VisitPerson visitPerson = new PushOrderRequestBody.OrderInfo.VisitPerson();

        //取票人信息
        contactPerson.setName("路人哈哈哈");
        contactPerson.setMobile("13333333333");
        contactPerson.setCredentialsType("ID_CARD");
        contactPerson.setCredentials("411133333333333333");


        //畅游通订单号
        o.setPartnerOrderId(partnerorderId);

        //取票人信息
        o.setContactPerson(contactPerson);

        //游客信息，根据产品的实际情况
        //产品bookPersonType为CONTACT_PERSON时，赋空值即可
        o.setVisitPerson(new PushOrderRequestBody.OrderInfo.VisitPerson());

        body.setOrderInfo(o);

        Response response = sendData(body, PUSHORDER);
        if (response != null && isSuccess(response)) {
            PushOrderResponseBody responseBody = (PushOrderResponseBody) response.getBody();
            System.out.println("--------------------------------同步订单--------------------------------");
            System.out.println("供应商订单号 : " + responseBody.getOrderInfo().getPartnerorderId());
        }
        return response;
    }

    /**
     * 支付订单
     *
     * @param partnerOrderId 畅游通订单号
     * @param orderStatus    订单状态， PREPAY_ORDER_PRINTING   预付：已付款，出票中
     * @param orderPrice     订单金额， 单位：分
     * @return body
     */
    public static Response payOrder(String partnerOrderId, String orderStatus, String orderPrice) throws Exception {
        PayOrderRequestBody body = new PayOrderRequestBody();
        PayOrderRequestBody.OrderInfo o = new PayOrderRequestBody.OrderInfo();
        //畅游通订单ID
        o.setPartnerOrderId(partnerOrderId);
        //订单状态
        o.setOrderStatus(orderStatus);
        //订单总金额
        o.setOrderPrice(orderPrice);
        body.setOrderInfo(o);

        Response response = sendData(body, PAYORDER);
        if (response != null && isSuccess(response)) {
            PayOrderResponseBody responseBody = (PayOrderResponseBody) response.getBody();
            System.out.println("--------------------------------支付订单--------------------------------");
            System.out.println("供应商订单号 : " + responseBody.getOrderInfo().getPartnerorderId());
//        订单状态
//        PREPAY_ORDER_PRINT_FAILED 预付：出票失败
//        PREPAY_ORDER_PRINT_SUCCESS 预付：出票成功
            System.out.println("订单状态: " + responseBody.getOrderInfo().getOrderStatus());
        }

        return response;
    }
}

