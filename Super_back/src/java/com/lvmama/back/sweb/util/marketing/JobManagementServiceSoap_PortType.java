package com.lvmama.back.sweb.util.marketing;
/**
 * JobManagementServiceSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public interface JobManagementServiceSoap_PortType extends java.rmi.Remote {
    public JobInfo writeListFiles(ReportRef report, ReportParams reportParams, java.lang.String segmentPath, TreeNodePath treeNodePath, SegmentationOptions segmentationOptions, java.lang.String filesystem, java.math.BigInteger timeout, java.lang.String sessionID) throws java.rmi.RemoteException;
    public JobInfo getJobInfo(java.math.BigInteger jobID, java.lang.String sessionID) throws java.rmi.RemoteException;
    public JobInfo cancelJob(java.math.BigInteger jobID, java.lang.String sessionID) throws java.rmi.RemoteException;
    public JobInfo getCounts(java.lang.String segmentPath, java.lang.String treePath, SegmentationOptions segmentationOptions, java.lang.String sessionID) throws java.rmi.RemoteException;
    public java.lang.String[] getPromptedColumns(java.lang.String segmentPath, java.lang.String treePath, java.lang.String sessionID) throws java.rmi.RemoteException;
    public JobInfo purgeCache(java.lang.String segmentPath, java.lang.String treePath, java.lang.Boolean ignoreCacheRef, java.lang.String sessionID) throws java.rmi.RemoteException;
    public JobInfo prepareCache(java.lang.String segmentPath, java.lang.String treePath, java.lang.Boolean refresh, java.lang.String sessionID) throws java.rmi.RemoteException;
    public JobInfo saveResultSet(java.lang.String segmentPath, TreeNodePath treeNodePath, java.lang.String savedSegmentPath, SegmentationOptions segmentationOptions, java.lang.String SRCustomLabel, java.lang.Boolean appendStaticSegment, java.lang.String sessionID) throws java.rmi.RemoteException;
    public JobInfo deleteResultSet(java.lang.String targetLevel, java.lang.String[] GUIDs, java.lang.String segmentPath, java.lang.String sessionID) throws java.rmi.RemoteException;
}
