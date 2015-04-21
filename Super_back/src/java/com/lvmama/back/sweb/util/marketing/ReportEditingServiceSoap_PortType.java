package com.lvmama.back.sweb.util.marketing;
/**
 * ReportEditingServiceSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public interface ReportEditingServiceSoap_PortType extends java.rmi.Remote {
    public java.lang.Object applyReportParams(ReportRef reportRef, ReportParams reportParams, boolean encodeInString, java.lang.String sessionID) throws java.rmi.RemoteException;
    public java.lang.String generateReportSQL(ReportRef reportRef, ReportParams reportParams, java.lang.String sessionID) throws java.rmi.RemoteException;
    public java.lang.String applyReportDefaults(ReportRef reportRefs, java.lang.String sessionID) throws java.rmi.RemoteException;
}
