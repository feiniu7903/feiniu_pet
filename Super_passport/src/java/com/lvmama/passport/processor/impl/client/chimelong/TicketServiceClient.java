
package com.lvmama.passport.processor.impl.client.chimelong;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.transport.TransportManager;
import com.lvmama.passport.processor.impl.client.chimelong.model.ArrayOfMaXiActTime;
import com.lvmama.passport.utils.WebServiceClient;
import com.lvmama.passport.utils.WebServiceConstant;

public class TicketServiceClient {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    private HashMap<QName, Endpoint> endpoints = new HashMap<QName, Endpoint>();
    private Service service0;

    public TicketServiceClient() {
        create0();
        Endpoint TicketServiceHttpPortEP = service0 .addEndpoint(new QName("http://ws.agent.chimelong.cn", "TicketServiceHttpPort"), new QName("http://ws.agent.chimelong.cn", "TicketServiceHttpBinding"), WebServiceConstant.getProperties("chimelong"));
        endpoints.put(new QName("http://ws.agent.chimelong.cn", "TicketServiceHttpPort"), TicketServiceHttpPortEP);
        Endpoint TicketServicePortTypeLocalEndpointEP = service0 .addEndpoint(new QName("http://ws.agent.chimelong.cn", "TicketServicePortTypeLocalEndpoint"), new QName("http://ws.agent.chimelong.cn", "TicketServicePortTypeLocalBinding"), "xfire.local://TicketService");
        endpoints.put(new QName("http://ws.agent.chimelong.cn", "TicketServicePortTypeLocalEndpoint"), TicketServicePortTypeLocalEndpointEP);
    }
    
    public TicketServiceClient(String resouce) {
    	create0();
    	Endpoint TicketServiceHttpPortEP=null;
    	if(StringUtils.equals(resouce,"zh")){
    		TicketServiceHttpPortEP=service0 .addEndpoint(new QName("http://ws.agent.chimelong.cn", "TicketServiceHttpPort"), new QName("http://ws.agent.chimelong.cn", "TicketServiceHttpBinding"), WebServiceConstant.getProperties("zh_chimelong"));
        }else{
        	TicketServiceHttpPortEP=service0 .addEndpoint(new QName("http://ws.agent.chimelong.cn", "TicketServiceHttpPort"), new QName("http://ws.agent.chimelong.cn", "TicketServiceHttpBinding"), WebServiceConstant.getProperties("chimelong"));
        }
    	endpoints.put(new QName("http://ws.agent.chimelong.cn", "TicketServiceHttpPort"), TicketServiceHttpPortEP);
        Endpoint TicketServicePortTypeLocalEndpointEP = service0 .addEndpoint(new QName("http://ws.agent.chimelong.cn", "TicketServicePortTypeLocalEndpoint"), new QName("http://ws.agent.chimelong.cn", "TicketServicePortTypeLocalBinding"), "xfire.local://TicketService");
        endpoints.put(new QName("http://ws.agent.chimelong.cn", "TicketServicePortTypeLocalEndpoint"), TicketServicePortTypeLocalEndpointEP);
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
        service0 = asf.create((com.lvmama.passport.processor.impl.client.chimelong.TicketServicePortType.class), props);
        {
            asf.createSoap11Binding(service0, new QName("http://ws.agent.chimelong.cn", "TicketServiceHttpBinding"), "http://schemas.xmlsoap.org/soap/http");
        }
        {
            asf.createSoap11Binding(service0, new QName("http://ws.agent.chimelong.cn", "TicketServicePortTypeLocalBinding"), "urn:xfire:transport:local");
        }
    }

    public TicketServicePortType getTicketServiceHttpPort() {
        TicketServicePortType var = ((TicketServicePortType)(this).getEndpoint(new QName("http://ws.agent.chimelong.cn", "TicketServiceHttpPort")));
        WebServiceClient.getClientInstance().initXfireClient(var,WebServiceClient.NEED_LONG_TIME);
        return var;
    }
//
//    public TicketServicePortType getTicketServiceHttpPort(String url) {
//        TicketServicePortType var = getTicketServiceHttpPort();
//        Client client = Client.getInstance(var);
//        client.setUrl(url);
//        client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, "30000");//设置发送的超时限制,单位是毫秒;
//        client.setProperty(CommonsHttpMessageSender.DISABLE_KEEP_ALIVE, "true");
//        client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "true");
//        return var;
//    }
//
//    public TicketServicePortType getTicketServicePortTypeLocalEndpoint() {
//        return ((TicketServicePortType)(this).getEndpoint(new QName("http://ws.agent.chimelong.cn", "TicketServicePortTypeLocalEndpoint")));
//    }
//
//    public TicketServicePortType getTicketServicePortTypeLocalEndpoint(String url) {
//        TicketServicePortType var = getTicketServicePortTypeLocalEndpoint();
//        Client client = Client.getInstance(var);
//        client.setUrl(url);
//        client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, "30000");//设置发送的超时限制,单位是毫秒;
//        client.setProperty(CommonsHttpMessageSender.DISABLE_KEEP_ALIVE, "true");
//        client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "true");
//        return var;
//    }

    public  static void  main(String[] arg)throws Exception{
    	TicketServiceClient client = new TicketServiceClient();
        
		//create a default service endpoint
    	TicketServicePortType service = client.getTicketServiceHttpPort();
    	ArrayOfMaXiActTime t=service.findMaXiActTime("2014-02-20");
    	System.out.println(t.getMaXiActTime().get(0).getActId().getValue());
    	System.out.println(t.getMaXiActTime().get(0).getActTimeBegin());
    	System.out.println(t.getMaXiActTime().get(0).getActTimeEnd());
    }
}
