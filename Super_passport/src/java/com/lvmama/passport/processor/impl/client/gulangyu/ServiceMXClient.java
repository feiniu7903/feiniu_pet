
package com.lvmama.passport.processor.impl.client.gulangyu;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.namespace.QName;

import org.codehaus.xfire.XFireRuntimeException;
import org.codehaus.xfire.aegis.AegisBindingProvider;
import org.codehaus.xfire.annotations.AnnotationServiceFactory;
import org.codehaus.xfire.annotations.jsr181.Jsr181WebAnnotations;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.jaxb2.JaxbTypeRegistry;
import org.codehaus.xfire.service.Endpoint;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.soap.AbstractSoapBinding;
import org.codehaus.xfire.transport.TransportManager;

public class ServiceMXClient {

    private static XFireProxyFactory proxyFactory = new XFireProxyFactory();
    private HashMap endpoints = new HashMap();
    private Service service0;

    public ServiceMXClient() {
        create0();
        Endpoint serviceMXPortLocalEndpointEP = service0 .addEndpoint(new QName("urn:serviceMX", "serviceMXPortLocalEndpoint"), new QName("urn:serviceMX", "serviceMXPortLocalBinding"), "xfire.local://serviceMX");
        endpoints.put(new QName("urn:serviceMX", "serviceMXPortLocalEndpoint"), serviceMXPortLocalEndpointEP);
        Endpoint serviceMXPortEP = service0 .addEndpoint(new QName("urn:serviceMX", "serviceMXPort"), new QName("urn:serviceMX", "serviceMXBinding"), "http://open.16u.com/openService/serviceMX.php");
        endpoints.put(new QName("urn:serviceMX", "serviceMXPort"), serviceMXPortEP);
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

    public Collection getEndpoints() {
        return endpoints.values();
    }

    private void create0() {
        TransportManager tm = (org.codehaus.xfire.XFireFactory.newInstance().getXFire().getTransportManager());
        HashMap props = new HashMap();
        props.put("annotations.allow.interface", true);
        AnnotationServiceFactory asf = new AnnotationServiceFactory(new Jsr181WebAnnotations(), tm, new AegisBindingProvider(new JaxbTypeRegistry()));
        asf.setBindingCreationEnabled(false);
        service0 = asf.create((com.lvmama.passport.processor.impl.client.gulangyu.ServiceMXPort.class), props);
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("urn:serviceMX", "serviceMXBinding"), "http://schemas.xmlsoap.org/soap/http");
        }
        {
            AbstractSoapBinding soapBinding = asf.createSoap11Binding(service0, new QName("urn:serviceMX", "serviceMXPortLocalBinding"), "urn:xfire:transport:local");
        }
    }

    public ServiceMXPort getserviceMXPortLocalEndpoint() {
        return ((ServiceMXPort)(this).getEndpoint(new QName("urn:serviceMX", "serviceMXPortLocalEndpoint")));
    }

    public ServiceMXPort getserviceMXPortLocalEndpoint(String url) {
        ServiceMXPort var = getserviceMXPortLocalEndpoint();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public ServiceMXPort getserviceMXPort() {
        return ((ServiceMXPort)(this).getEndpoint(new QName("urn:serviceMX", "serviceMXPort")));
    }

    public ServiceMXPort getserviceMXPort(String url) {
        ServiceMXPort var = getserviceMXPort();
        org.codehaus.xfire.client.Client.getInstance(var).setUrl(url);
        return var;
    }

    public static void main(String[] args) {
        

        ServiceMXClient client = new ServiceMXClient();
        
		//create a default service endpoint
        ServiceMXPort service = client.getserviceMXPort();
        String s=service.get_ScenicSpot_List("lvmama", "74170b757fa262bfaea564264c030229", "");
        
		//TODO: Add custom client code here
        		//
        		//service.yourServiceOperationHere();
        
		System.out.println(s);
        		System.exit(0);
    }

}
