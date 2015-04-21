/**
 * EctripOTAServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.jiuwang.EctripOTAervice;

public class EctripOTAServiceServiceLocator extends org.apache.axis.client.Service implements EctripOTAServiceService {

    public EctripOTAServiceServiceLocator() {
    }


    public EctripOTAServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EctripOTAServiceServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ectripOTAService
    private String ectripOTAService_address = "http://localhost:8091/services/ectripOTAService";

    public String getectripOTAServiceAddress() {
        return ectripOTAService_address;
    }

    // The WSDD service name defaults to the port name.
    private String ectripOTAServiceWSDDServiceName = "ectripOTAService";

    public String getectripOTAServiceWSDDServiceName() {
        return ectripOTAServiceWSDDServiceName;
    }

    public void setectripOTAServiceWSDDServiceName(String name) {
        ectripOTAServiceWSDDServiceName = name;
    }

    public EctripOTAService getectripOTAService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ectripOTAService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getectripOTAService(endpoint);
    }

    public EctripOTAService getectripOTAService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            EctripOTAServiceSoapBindingStub _stub = new EctripOTAServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getectripOTAServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setectripOTAServiceEndpointAddress(String address) {
        ectripOTAService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (EctripOTAService.class.isAssignableFrom(serviceEndpointInterface)) {
                EctripOTAServiceSoapBindingStub _stub = new EctripOTAServiceSoapBindingStub(new java.net.URL(ectripOTAService_address), this);
                _stub.setPortName(getectripOTAServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("ectripOTAService".equals(inputPortName)) {
            return getectripOTAService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://localhost:8091/services/ectripOTAService", "EctripOTAServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://localhost:8091/services/ectripOTAService", "ectripOTAService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {

if ("ectripOTAService".equals(portName)) {
            setectripOTAServiceEndpointAddress(address);
        }
        else
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
