
package com.lvmama.passport.processor.impl.client.dinosaurtown;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.transport.TransportManager;

import com.lvmama.comm.bee.po.pass.ScenicProduct;
import com.lvmama.passport.utils.WebServiceClient;
import com.lvmama.passport.utils.WebServiceConstant;

public class KLCServiceClient {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    private HashMap<QName, Endpoint> endpoints = new HashMap<QName, Endpoint>();
    private Service service0;
    public KLCServiceClient() {
        create0();
        Endpoint KLCServiceSoapEP = service0 .addEndpoint(new QName("http://www.konglongcheng.com/", "KLCServiceSoap"), new QName("http://www.konglongcheng.com/", "KLCServiceSoap"), 
        		WebServiceConstant.getProperties("dinosaurtown_url"));
        endpoints.put(new QName("http://www.konglongcheng.com/", "KLCServiceSoap"), KLCServiceSoapEP);
        Endpoint KLCServiceSoapLocalEndpointEP = service0 .addEndpoint(new QName("http://www.konglongcheng.com/", "KLCServiceSoapLocalEndpoint"), new QName("http://www.konglongcheng.com/", "KLCServiceSoapLocalBinding"), "xfire.local://KLCService");
        endpoints.put(new QName("http://www.konglongcheng.com/", "KLCServiceSoapLocalEndpoint"), KLCServiceSoapLocalEndpointEP);
    }

    public Object getEndpoint(Endpoint endpoint) {
        try {
            return proxyFactory.create((endpoint).getBinding(), (endpoint).getUrl());
        } catch (MalformedURLException e) {
            throw new XFireRuntimeException("Invalid URL", e);
        }
    }

    public Object getEndpoint(QName name) {
        Endpoint endpoint = ((Endpoint) endpoints.get((name)));
        if ((endpoint) == null) {
            throw new IllegalStateException("No such endpoint!");
        }
        return getEndpoint((endpoint));
    }

    public Collection<Endpoint> getEndpoints() {
        return endpoints.values();
    }

    private void create0() {
        TransportManager tm = (org.codehaus.xfire.XFireFactory.newInstance().getXFire().getTransportManager());
        HashMap<String, Boolean> props = new HashMap<String, Boolean>();
        props.put("annotations.allow.interface", true);
        AnnotationServiceFactory asf = new AnnotationServiceFactory(new Jsr181WebAnnotations(), tm, new AegisBindingProvider(new JaxbTypeRegistry()));
        asf.setBindingCreationEnabled(false);
        service0 = asf.create((com.lvmama.passport.processor.impl.client.dinosaurtown.KLCServiceSoap.class), props);
        {
            asf.createSoap11Binding(service0, new QName("http://www.konglongcheng.com/", "KLCServiceSoapLocalBinding"), "urn:xfire:transport:local");
        }
        {
            asf.createSoap11Binding(service0, new QName("http://www.konglongcheng.com/", "KLCServiceSoap"), "http://schemas.xmlsoap.org/soap/http");
        }
    }

    public KLCServiceSoap getKLCServiceSoap() {
    	KLCServiceSoap var = ((KLCServiceSoap)(this).getEndpoint(new QName("http://www.konglongcheng.com/", "KLCServiceSoap")));
    	WebServiceClient.getClientInstance().initXfireClient(var,WebServiceClient.NEED_LONG_TIME);
    	return var;
    }
//
//    public KLCServiceSoap getKLCServiceSoap(String url) {
//        KLCServiceSoap var = getKLCServiceSoap();
//        Client client = Client.getInstance(var);
//        client.setUrl(url);
//        client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, "30000");//设置发送的超时限制,单位是毫秒;
//        client.setProperty(CommonsHttpMessageSender.DISABLE_KEEP_ALIVE, "true");
//        client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "true");
//        return var;
//    }
//
//    public KLCServiceSoap getKLCServiceSoapLocalEndpoint() {
//        return ((KLCServiceSoap)(this).getEndpoint(new QName("http://www.konglongcheng.com/", "KLCServiceSoapLocalEndpoint")));
//    }
//
//    public KLCServiceSoap getKLCServiceSoapLocalEndpoint(String url) {
//        KLCServiceSoap var = getKLCServiceSoapLocalEndpoint();
//        Client client = Client.getInstance(var);
//        client.setUrl(url);
//        client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, "30000");//设置发送的超时限制,单位是毫秒;
//        client.setProperty(CommonsHttpMessageSender.DISABLE_KEEP_ALIVE, "true");
//        client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "true");
//        return var;
//    }
    public static void main(String[] arg){
    	String MerchantCode="LXS001936";
    	Map<String,Object> queryOption = new HashMap<String, Object>();
    	queryOption.put("SupplierUid", MerchantCode);
    	getTestProducts(queryOption);
//    	getTestSignature();
    }

//    	String MerchantCode="LXS001180";
//		String MerchantKey="11111111";
//    	String MerchantKey="A1B9c58Z";
//    	String MerchantCode="LXS002058";
    public static List<ScenicProduct> getTestProducts(Map<String,Object> queryOption){
    	Date startDate =(Date)queryOption.get("startDate");
    	Date endDate = (Date)queryOption.get("endDate");
    	if(startDate == null){
    		startDate = new Date();
    	}
    	if(endDate == null){
    		endDate = DateUtils.addYears(startDate, 3);
    	}
    	List<ScenicProduct> scenicProducts = new ArrayList<ScenicProduct>();
    	String merchantCode = String.valueOf(queryOption.get("SupplierUid"));
    	String MerchantKey="A1B9c58Z";
    	String startTime = DateFormatUtils.format(startDate, "yyyy-MM-dd hh:mm:ss");
    	String endTime = DateFormatUtils.format(endDate, "yyyy-MM-dd hh:mm:ss");
    	KLCServiceClient client=new KLCServiceClient();
    	String signature=client.getKLCServiceSoap().testSignature(MerchantKey, merchantCode, startTime, endTime, "", "", "", "", "", "");
    	ArrayOfProductsInfo productsInfo=client.getKLCServiceSoap().getProductsInfoByTime(merchantCode, startTime, endTime, signature);
    	List<ProductsInfo> list=productsInfo.getProductsInfo();
    	for(ProductsInfo prodInfo : list){
    		int price = (int)prodInfo.getPrice();
    		System.out.println(prodInfo.getProductNo()+"	"+prodInfo.getProductName()+"	"+prodInfo.getStartTime()+"		"+prodInfo.getEndTime()+"	价格="+prodInfo.getPrice()+"	是否可售:"+prodInfo.getProductFlag());
    		ScenicProduct scenicProd = new ScenicProduct();
    		scenicProd.setProductIdSupplier(prodInfo.getProductNo());
    		scenicProd.setProductName(prodInfo.getProductName());
    		scenicProd.setStartDate(prodInfo.getStartTime());
    		scenicProd.setEndDate(prodInfo.getEndTime());
    		scenicProd.setPrice(String.valueOf(price));
    		scenicProd.setMerchantType(merchantCode);
    		scenicProducts.add(scenicProd);
    	}
    	return scenicProducts;
    }
	private static void getTestSignature() {
		try {
    		String MerchantCode="LXS001214";
			String MerchantKey="A1B9c58Z";
			String RefNo="320282";
			
			CustomerInfo customer=new CustomerInfo();
			customer.setLinkAddr("上海金沙江路3131");
			customer.setLinkCard("31010419820827401X");
			customer.setLinkMan("黄小姐");
			customer.setLinkMobile("15900615050");
			customer.setLinkSex("女");
			
			List<OrderProduct> orderProductsList=new ArrayList<OrderProduct>();
			OrderProduct orderProduct=new OrderProduct();
			orderProduct.setCreateDate("2011-04-28 00:00:00");
			orderProduct.setInTime("2011-05-01 00:00:00");
			orderProduct.setPrice(200d);
			orderProduct.setProductName("恐龙园+温泉联票（网订）");
			orderProduct.setProductNo("2060021");
			orderProduct.setQuantity(1);
			orderProductsList.add(orderProduct);
			
			ArrayOfOrderProduct orderPrdouct=new ArrayOfOrderProduct();
			orderPrdouct.orderProduct=orderProductsList;
			KLCServiceClient client=new KLCServiceClient();
			String Signature=client.getKLCServiceSoap().testSignature(MerchantKey,MerchantCode, RefNo, customer.getLinkMan(), customer.getLinkSex(),customer.getLinkMobile(),customer.getLinkCard(), customer.getLinkAddr(), "", "");
			System.out.println("Signature........"+Signature);
//			String result=client.getKLCServiceSoap().postOrdersProducts(MerchantCode, RefNo, customer,orderPrdouct, Signature);
//			System.out.println("result........"+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
