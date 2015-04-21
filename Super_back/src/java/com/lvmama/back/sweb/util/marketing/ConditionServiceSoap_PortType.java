package com.lvmama.back.sweb.util.marketing;
/**
 * ConditionServiceSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public interface ConditionServiceSoap_PortType extends java.rmi.Remote {
    public boolean evaluateCondition(java.lang.String path, java.lang.String[] reportCustomizationParameters, java.lang.String sessionID) throws java.rmi.RemoteException;
    public boolean evaluateInlineCondition(java.lang.String conditionXML, java.lang.String[] reportCustomizationParameters, java.lang.String sessionID) throws java.rmi.RemoteException;
    public java.lang.String[] getConditionCustomizableReportElements(java.lang.String path, java.lang.String sessionID) throws java.rmi.RemoteException;
}
