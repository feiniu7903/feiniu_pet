package com.lvmama.passport.processor.impl.client.shandong;

public class WebservicePortTypeProxy implements com.lvmama.passport.processor.impl.client.shandong.WebservicePortType {
  private String _endpoint = null;
  private com.lvmama.passport.processor.impl.client.shandong.WebservicePortType webservicePortType = null;
  
  public WebservicePortTypeProxy() {
    _initWebservicePortTypeProxy();
  }
  
  public WebservicePortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initWebservicePortTypeProxy();
  }
  
  private void _initWebservicePortTypeProxy() {
    try {
      webservicePortType = (new com.lvmama.passport.processor.impl.client.shandong.WebserviceLocator()).getwebserviceHttpPort();
      if (webservicePortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)webservicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)webservicePortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (webservicePortType != null)
      ((javax.xml.rpc.Stub)webservicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.lvmama.passport.processor.impl.client.shandong.WebservicePortType getWebservicePortType() {
    if (webservicePortType == null)
      _initWebservicePortTypeProxy();
    return webservicePortType;
  }
  
  public com.lvmama.passport.processor.impl.client.shandong.model.ScenicResult getScenic(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3, java.lang.String in4, java.lang.String in5) throws java.rmi.RemoteException{
    if (webservicePortType == null)
      _initWebservicePortTypeProxy();
    return webservicePortType.getScenic(in0, in1, in2, in3, in4, in5);
  }
  
  public com.lvmama.passport.processor.impl.client.shandong.model.VersionResult getVersion(java.lang.String in0, java.lang.String in1, java.lang.String in2) throws java.rmi.RemoteException{
    if (webservicePortType == null)
      _initWebservicePortTypeProxy();
    return webservicePortType.getVersion(in0, in1, in2);
  }
  
  public com.lvmama.passport.processor.impl.client.shandong.model.CancelResult cancellOrder(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3, java.lang.String in4) throws java.rmi.RemoteException{
    if (webservicePortType == null)
      _initWebservicePortTypeProxy();
    return webservicePortType.cancellOrder(in0, in1, in2, in3, in4);
  }
  
  public com.lvmama.passport.processor.impl.client.shandong.model.OrderResult saveOrder(java.lang.String in0, boolean in1, java.lang.String in2, java.lang.String in3, com.lvmama.passport.processor.impl.client.shandong.model.OrderBean in4, java.lang.String in5) throws java.rmi.RemoteException{
    if (webservicePortType == null)
      _initWebservicePortTypeProxy();
    return webservicePortType.saveOrder(in0, in1, in2, in3, in4, in5);
  }
  
  public com.lvmama.passport.processor.impl.client.shandong.model.ProductResult getTickInfo(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3, java.lang.String in4, java.lang.String in5) throws java.rmi.RemoteException{
    if (webservicePortType == null)
      _initWebservicePortTypeProxy();
    return webservicePortType.getTickInfo(in0, in1, in2, in3, in4, in5);
  }
  
  public java.lang.String getCode(java.lang.String in0, java.lang.String in1, java.lang.String in2, java.lang.String in3, java.lang.String in4) throws java.rmi.RemoteException{
    if (webservicePortType == null)
      _initWebservicePortTypeProxy();
    return webservicePortType.getCode(in0, in1, in2, in3, in4);
  }
  
  public com.lvmama.passport.processor.impl.client.shandong.model.OrderResult saveActivityOrder(java.lang.String in0, java.lang.String in1, java.lang.String in2, com.lvmama.passport.processor.impl.client.shandong.model.OrderBean in3, java.lang.String in4, com.lvmama.passport.processor.impl.client.shandong.model.DetailSourceBean in5) throws java.rmi.RemoteException{
    if (webservicePortType == null)
      _initWebservicePortTypeProxy();
    return webservicePortType.saveActivityOrder(in0, in1, in2, in3, in4, in5);
  }
  
  
}