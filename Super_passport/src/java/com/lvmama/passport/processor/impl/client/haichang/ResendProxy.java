package com.lvmama.passport.processor.impl.client.haichang;

public class ResendProxy implements com.lvmama.passport.processor.impl.client.haichang.Resend {
  private String _endpoint = null;
  private com.lvmama.passport.processor.impl.client.haichang.Resend resend = null;
  
  public ResendProxy() {
    _initResendProxy();
  }
  
  public ResendProxy(String endpoint) {
    _endpoint = endpoint;
    _initResendProxy();
  }
  
  private void _initResendProxy() {
    try {
      resend = (new com.lvmama.passport.processor.impl.client.haichang.ResendImplServiceLocator()).getResendImplPort();
      if (resend != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)resend)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)resend)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (resend != null)
      ((javax.xml.rpc.Stub)resend)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.lvmama.passport.processor.impl.client.haichang.Resend getResend() {
    if (resend == null)
      _initResendProxy();
    return resend;
  }
  
  public com.lvmama.passport.processor.impl.client.haichang.ResultResend resend(java.lang.String agentOrderID, java.lang.String agentID, java.lang.String agentPassword, java.lang.String visitorPhoneNumber) throws java.rmi.RemoteException{
    if (resend == null)
      _initResendProxy();
    return resend.resend(agentOrderID, agentID, agentPassword, visitorPhoneNumber);
  }
  
  
}