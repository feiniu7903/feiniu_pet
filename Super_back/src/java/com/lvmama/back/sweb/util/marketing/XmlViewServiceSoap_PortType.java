package com.lvmama.back.sweb.util.marketing;
/**
 * XmlViewServiceSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public interface XmlViewServiceSoap_PortType extends java.rmi.Remote {
    public QueryResults executeXMLQuery(ReportRef report, XMLQueryOutputFormat outputFormat, XMLQueryExecutionOptions executionOptions, ReportParams reportParams, java.lang.String sessionID) throws java.rmi.RemoteException;
    public java.lang.String upgradeXML(java.lang.String xml, java.lang.String sessionID) throws java.rmi.RemoteException;
    public QueryResults executeSQLQuery(java.lang.String sql, XMLQueryOutputFormat outputFormat, XMLQueryExecutionOptions executionOptions, java.lang.String sessionID) throws java.rmi.RemoteException;
    public QueryResults fetchNext(java.lang.String queryID, java.lang.String sessionID) throws java.rmi.RemoteException;
    public void cancelQuery(java.lang.String queryID, java.lang.String sessionID) throws java.rmi.RemoteException;
    public java.lang.String[] getPromptedFilters(ReportRef report, java.lang.String sessionID) throws java.rmi.RemoteException;
}
