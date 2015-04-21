
package com.lvmama.passport.processor.impl.client.dinosaurtown;

import javax.jws.WebService;

@WebService(serviceName = "KLCService", targetNamespace = "http://www.konglongcheng.com/", endpointInterface = "com.konglongcheng.KLCServiceSoap")
public class KLCServiceImpl
    implements KLCServiceSoap
{


    public String postOrdersProducts(String MerchantCode, String RefNo, CustomerInfo Customer, ArrayOfOrderProduct OrderProducts, String Signature) {
        throw new UnsupportedOperationException();
    }

    public String testSignature(String MerchantKey, String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9) {
        throw new UnsupportedOperationException();
    }

    public ArrayOfOrderStatus getOrdersStatus(String MerchantCode, String StartTime, String EndTime, String Signature) {
        throw new UnsupportedOperationException();
    }

    public ArrayOfProductsInfo getProductsInfoByTime(String MerchantCode, String StartTime, String EndTime, String Signature) {
        throw new UnsupportedOperationException();
    }

    public String editOrders(String MerchantCode, String RefNo, String OrderNo, CustomerInfo Customer, ArrayOfOrderProduct OrderProducts, int SetFlag, String Signature) {
        throw new UnsupportedOperationException();
    }

}
