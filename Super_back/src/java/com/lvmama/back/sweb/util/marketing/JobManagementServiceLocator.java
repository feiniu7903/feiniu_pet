package com.lvmama.back.sweb.util.marketing;
/**
 * JobManagementServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class JobManagementServiceLocator extends org.apache.axis.client.Service implements JobManagementService {

    public JobManagementServiceLocator() {
    }


    public JobManagementServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public JobManagementServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for JobManagementServiceSoap
    private java.lang.String JobManagementServiceSoap_address = "http://192.168.0.26:7001/analytics-ws/saw.dll?SoapImpl=jobManagementService";

    public java.lang.String getJobManagementServiceSoapAddress() {
        return JobManagementServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String JobManagementServiceSoapWSDDServiceName = "JobManagementServiceSoap";

    public java.lang.String getJobManagementServiceSoapWSDDServiceName() {
        return JobManagementServiceSoapWSDDServiceName;
    }

    public void setJobManagementServiceSoapWSDDServiceName(java.lang.String name) {
        JobManagementServiceSoapWSDDServiceName = name;
    }

    public JobManagementServiceSoap_PortType getJobManagementServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(JobManagementServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getJobManagementServiceSoap(endpoint);
    }

    public JobManagementServiceSoap_PortType getJobManagementServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            JobManagementServiceStub _stub = new JobManagementServiceStub(portAddress, this);
            _stub.setPortName(getJobManagementServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setJobManagementServiceSoapEndpointAddress(java.lang.String address) {
        JobManagementServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (JobManagementServiceSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                JobManagementServiceStub _stub = new JobManagementServiceStub(new java.net.URL(JobManagementServiceSoap_address), this);
                _stub.setPortName(getJobManagementServiceSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
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
        java.lang.String inputPortName = portName.getLocalPart();
        if ("JobManagementServiceSoap".equals(inputPortName)) {
            return getJobManagementServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "JobManagementService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "JobManagementServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("JobManagementServiceSoap".equals(portName)) {
            setJobManagementServiceSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
