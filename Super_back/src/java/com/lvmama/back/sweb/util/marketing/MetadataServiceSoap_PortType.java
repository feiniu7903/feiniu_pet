package com.lvmama.back.sweb.util.marketing;
/**
 * MetadataServiceSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public interface MetadataServiceSoap_PortType extends java.rmi.Remote {
    public boolean clearQueryCache(java.lang.String sessionID) throws java.rmi.RemoteException;
    public SASubjectArea[] getSubjectAreas(java.lang.String sessionID) throws java.rmi.RemoteException;
    public SASubjectArea describeSubjectArea(java.lang.String subjectAreaName, SASubjectAreaDetails detailsLevel, java.lang.String sessionID) throws java.rmi.RemoteException;
    public SATable describeTable(java.lang.String subjectAreaName, java.lang.String tableName, SATableDetails detailsLevel, java.lang.String sessionID) throws java.rmi.RemoteException;
    public SAColumn describeColumn(java.lang.String subjectAreaName, java.lang.String tableName, java.lang.String columnName, java.lang.String sessionID) throws java.rmi.RemoteException;
}
