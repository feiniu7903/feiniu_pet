
package com.lvmama.passport.processor.impl.client.yanchen;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.namespace.QName;

import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.transport.TransportManager;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;

import com.lvmama.passport.utils.WebServiceClient;

public class Service1Client {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    private HashMap<QName, Endpoint> endpoints = new HashMap<QName, Endpoint>();
    private Service service0;

    public Service1Client() {
        create0();
        Endpoint Service1SoapEP = service0 .addEndpoint(new QName("http://my0519.com/", "Service1Soap"), new QName("http://my0519.com/", "Service1Soap"), "http://58.216.164.68/webservic/AddService.asmx");
        endpoints.put(new QName("http://my0519.com/", "Service1Soap"), Service1SoapEP);
        Endpoint Service1SoapLocalEndpointEP = service0 .addEndpoint(new QName("http://my0519.com/", "Service1SoapLocalEndpoint"), new QName("http://my0519.com/", "Service1SoapLocalBinding"), "xfire.local://Service1");
        endpoints.put(new QName("http://my0519.com/", "Service1SoapLocalEndpoint"), Service1SoapLocalEndpointEP);
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
        service0 = asf.create((com.lvmama.passport.processor.impl.client.yanchen.Service1Soap.class), props);
        {
            asf.createSoap11Binding(service0, new QName("http://my0519.com/", "Service1SoapLocalBinding"), "urn:xfire:transport:local");
        }
        {
            asf.createSoap11Binding(service0, new QName("http://my0519.com/", "Service1Soap"), "http://schemas.xmlsoap.org/soap/http");
        }
    }

    public Service1Soap getService1Soap() {
    	Service1Soap var = ((Service1Soap)(this).getEndpoint(new QName("http://my0519.com/", "Service1Soap")));
        WebServiceClient.getClientInstance().initXfireClient(var);
        return var;
    }
//
//    public Service1Soap getService1Soap(String url) {
//        Service1Soap var = getService1Soap();
//        Client client = Client.getInstance(var);
//        client.setUrl(url);
//        client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, "60");
//        return var;
//    }
//
//    public Service1Soap getService1SoapLocalEndpoint() {
//        return ((Service1Soap)(this).getEndpoint(new QName("http://my0519.com/", "Service1SoapLocalEndpoint")));
//    }
//
//    public Service1Soap getService1SoapLocalEndpoint(String url) {
//        Service1Soap var = getService1SoapLocalEndpoint();
//        Client client = Client.getInstance(var);
//        client.setUrl(url);
//        client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, "60");
//        return var;
//    }

    public static void main(String[] args) {
        

        Service1Client client = new Service1Client();
        
		//create a default service endpoint
        Service1Soap service = client.getService1Soap();
        Reserve2 dataList = new Reserve2();
		int i = service.addData2(dataList);
		//
		//service.yourServiceOperationHere();
        
		System.out.println("test client completed" + service + "\n result:" + i);
        		System.exit(0);
    }

}
