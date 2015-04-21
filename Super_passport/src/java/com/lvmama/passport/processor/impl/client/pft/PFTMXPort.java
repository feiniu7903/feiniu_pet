/**
 * PFTMXPort.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.pft;

public interface PFTMXPort extends java.rmi.Remote {
    public java.lang.String authrize(java.lang.String ac, java.lang.String pw) throws java.rmi.RemoteException;
    public java.lang.String get_ScenicSpot_List(java.lang.String ac, java.lang.String pw, java.lang.String n, java.lang.String m) throws java.rmi.RemoteException;
    public java.lang.String get_ScenicSpot_Info(java.lang.String ac, java.lang.String pw, java.lang.String n) throws java.rmi.RemoteException;
    public java.lang.String get_Ticket_List(java.lang.String ac, java.lang.String pw, java.lang.String n, java.lang.String m) throws java.rmi.RemoteException;
    public java.lang.String dynamic_Price_And_Storage(java.lang.String ac, java.lang.String pw, java.lang.String pid, java.lang.String date, java.lang.String mode, java.lang.String ptype, java.lang.String get_storage, java.lang.String m) throws java.rmi.RemoteException;
    public java.lang.String PFT_Order_Submit(java.lang.String ac, java.lang.String pw, java.lang.String lid, java.lang.String tid, java.lang.String remotenum, java.lang.String tprice, java.lang.String tnum, java.lang.String playtime, java.lang.String ordername, java.lang.String ordertel, java.lang.String contactTEL, java.lang.String smsSend, java.lang.String paymode, java.lang.String ordermode, java.lang.String assembly, java.lang.String series, java.lang.String concatID, java.lang.String pCode, java.lang.String m) throws java.rmi.RemoteException;
    public java.lang.String order_Globle_Search(java.lang.String ac, java.lang.String pw, java.lang.String sid, java.lang.String mid, java.lang.String aid, java.lang.String tid, java.lang.String ltitle, java.lang.String ttitle, java.lang.String btime1, java.lang.String etime1, java.lang.String btime2, java.lang.String etime2, java.lang.String btime3, java.lang.String etime3, java.lang.String ordernum, java.lang.String remotenum, java.lang.String oname, java.lang.String otel, java.lang.String status, java.lang.String pays, java.lang.String orderby, java.lang.String sort, java.lang.String rstart, java.lang.String n, java.lang.String c, java.lang.String contactTEL, java.lang.String payinfo, java.lang.String p_type, java.lang.String ordertype, java.lang.String concat, java.lang.String ifpack, java.lang.String m) throws java.rmi.RemoteException;
    public java.lang.String reSend_SMS_Global_PL(java.lang.String ac, java.lang.String pw, java.lang.String ordern, java.lang.String m) throws java.rmi.RemoteException;
    public java.lang.String order_Change_Pro(java.lang.String ac, java.lang.String pw, java.lang.String ordern, java.lang.String num, java.lang.String ordertel, java.lang.String m) throws java.rmi.RemoteException;
    public java.lang.String send_SMS_System_Fee(java.lang.String ac, java.lang.String pw, java.lang.String tel, java.lang.String msg, java.lang.String m) throws java.rmi.RemoteException;
    public java.lang.String terminal_Code_Verify(java.lang.String ac, java.lang.String pw, java.lang.String ordern, java.lang.String m) throws java.rmi.RemoteException;
    public java.lang.String PFT_Member_Fund(java.lang.String ac, java.lang.String pw, java.lang.String dtype, java.lang.String aid) throws java.rmi.RemoteException;
    public java.lang.String PFT_Member_Relationship(java.lang.String ac, java.lang.String pw, java.lang.String n, java.lang.String m) throws java.rmi.RemoteException;
}
