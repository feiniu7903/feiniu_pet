
package com.lvmama.passport.processor.impl.client.dinosaurtown;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "KLCServiceSoap", targetNamespace = "http://www.konglongcheng.com/")
@SOAPBinding(use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface KLCServiceSoap {


    @WebMethod(operationName = "PostOrdersProducts", action = "http://www.konglongcheng.com/PostOrdersProducts")
    @WebResult(name = "PostOrdersProductsResult", targetNamespace = "http://www.konglongcheng.com/")
    public String postOrdersProducts(
        @WebParam(name = "MerchantCode", targetNamespace = "http://www.konglongcheng.com/")
        String MerchantCode,
        @WebParam(name = "RefNo", targetNamespace = "http://www.konglongcheng.com/")
        String RefNo,
        @WebParam(name = "Customer", targetNamespace = "http://www.konglongcheng.com/")
        CustomerInfo Customer,
        @WebParam(name = "OrderProducts", targetNamespace = "http://www.konglongcheng.com/")
        ArrayOfOrderProduct OrderProducts,
        @WebParam(name = "Signature", targetNamespace = "http://www.konglongcheng.com/")
        String Signature);

    @WebMethod(operationName = "testSignature", action = "http://www.konglongcheng.com/testSignature")
    @WebResult(name = "testSignatureResult", targetNamespace = "http://www.konglongcheng.com/")
    public String testSignature(
        @WebParam(name = "MerchantKey", targetNamespace = "http://www.konglongcheng.com/")
        String MerchantKey,
        @WebParam(name = "var1", targetNamespace = "http://www.konglongcheng.com/")
        String var1,
        @WebParam(name = "var2", targetNamespace = "http://www.konglongcheng.com/")
        String var2,
        @WebParam(name = "var3", targetNamespace = "http://www.konglongcheng.com/")
        String var3,
        @WebParam(name = "var4", targetNamespace = "http://www.konglongcheng.com/")
        String var4,
        @WebParam(name = "var5", targetNamespace = "http://www.konglongcheng.com/")
        String var5,
        @WebParam(name = "var6", targetNamespace = "http://www.konglongcheng.com/")
        String var6,
        @WebParam(name = "var7", targetNamespace = "http://www.konglongcheng.com/")
        String var7,
        @WebParam(name = "var8", targetNamespace = "http://www.konglongcheng.com/")
        String var8,
        @WebParam(name = "var9", targetNamespace = "http://www.konglongcheng.com/")
        String var9);

    @WebMethod(operationName = "GetOrdersStatus", action = "http://www.konglongcheng.com/GetOrdersStatus")
    @WebResult(name = "GetOrdersStatusResult", targetNamespace = "http://www.konglongcheng.com/")
    public ArrayOfOrderStatus getOrdersStatus(
        @WebParam(name = "MerchantCode", targetNamespace = "http://www.konglongcheng.com/")
        String MerchantCode,
        @WebParam(name = "StartTime", targetNamespace = "http://www.konglongcheng.com/")
        String StartTime,
        @WebParam(name = "EndTime", targetNamespace = "http://www.konglongcheng.com/")
        String EndTime,
        @WebParam(name = "Signature", targetNamespace = "http://www.konglongcheng.com/")
        String Signature);

    @WebMethod(operationName = "GetProductsInfoByTime", action = "http://www.konglongcheng.com/GetProductsInfoByTime")
    @WebResult(name = "GetProductsInfoByTimeResult", targetNamespace = "http://www.konglongcheng.com/")
    public ArrayOfProductsInfo getProductsInfoByTime(
        @WebParam(name = "MerchantCode", targetNamespace = "http://www.konglongcheng.com/")
        String MerchantCode,
        @WebParam(name = "StartTime", targetNamespace = "http://www.konglongcheng.com/")
        String StartTime,
        @WebParam(name = "EndTime", targetNamespace = "http://www.konglongcheng.com/")
        String EndTime,
        @WebParam(name = "Signature", targetNamespace = "http://www.konglongcheng.com/")
        String Signature);

    @WebMethod(operationName = "EditOrders", action = "http://www.konglongcheng.com/EditOrders")
    @WebResult(name = "EditOrdersResult", targetNamespace = "http://www.konglongcheng.com/")
    public String editOrders(
        @WebParam(name = "MerchantCode", targetNamespace = "http://www.konglongcheng.com/")
        String MerchantCode,
        @WebParam(name = "RefNo", targetNamespace = "http://www.konglongcheng.com/")
        String RefNo,
        @WebParam(name = "OrderNo", targetNamespace = "http://www.konglongcheng.com/")
        String OrderNo,
        @WebParam(name = "Customer", targetNamespace = "http://www.konglongcheng.com/")
        CustomerInfo Customer,
        @WebParam(name = "OrderProducts", targetNamespace = "http://www.konglongcheng.com/")
        ArrayOfOrderProduct OrderProducts,
        @WebParam(name = "SetFlag", targetNamespace = "http://www.konglongcheng.com/")
        int SetFlag,
        @WebParam(name = "Signature", targetNamespace = "http://www.konglongcheng.com/")
        String Signature);

}
