package com.lvmama.distribution.client.jinzonglv;

public class WebServiceLvmamaProxy implements WebServiceLvmama {
  private String _endpoint = null;
  private WebServiceLvmama webServiceLvmama = null;
  
  public WebServiceLvmamaProxy() {
    _initWebServiceLvmamaProxy();
  }
  
  public WebServiceLvmamaProxy(String endpoint) {
    _endpoint = endpoint;
    _initWebServiceLvmamaProxy();
  }
  
  private void _initWebServiceLvmamaProxy() {
    try {
      webServiceLvmama = (new WebServiceLvmamaImplServiceLocator()).getWebServiceLvmamaImplPort();
      if (webServiceLvmama != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)webServiceLvmama)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)webServiceLvmama)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (webServiceLvmama != null)
      ((javax.xml.rpc.Stub)webServiceLvmama)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public WebServiceLvmama getWebServiceLvmama() {
    if (webServiceLvmama == null)
      _initWebServiceLvmamaProxy();
    return webServiceLvmama;
  }
  
  public java.lang.String refund(java.lang.String arg0) throws java.rmi.RemoteException{
    if (webServiceLvmama == null)
      _initWebServiceLvmamaProxy();
    return webServiceLvmama.refund(arg0);
  }
  
  
}