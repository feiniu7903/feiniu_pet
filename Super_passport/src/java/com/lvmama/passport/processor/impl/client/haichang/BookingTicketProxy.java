package com.lvmama.passport.processor.impl.client.haichang;

public class BookingTicketProxy implements com.lvmama.passport.processor.impl.client.haichang.BookingTicket {
  private String _endpoint = null;
  private com.lvmama.passport.processor.impl.client.haichang.BookingTicket bookingTicket = null;
  
  public BookingTicketProxy() {
    _initBookingTicketProxy();
  }
  
  public BookingTicketProxy(String endpoint) {
    _endpoint = endpoint;
    _initBookingTicketProxy();
  }
  
  private void _initBookingTicketProxy() {
    try {
      bookingTicket = (new com.lvmama.passport.processor.impl.client.haichang.BookingTicketImplServiceLocator()).getBookingTicketImplPort();
      if (bookingTicket != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)bookingTicket)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)bookingTicket)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (bookingTicket != null)
      ((javax.xml.rpc.Stub)bookingTicket)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.lvmama.passport.processor.impl.client.haichang.BookingTicket getBookingTicket() {
    if (bookingTicket == null)
      _initBookingTicketProxy();
    return bookingTicket;
  }
  
  public com.lvmama.passport.processor.impl.client.haichang.ResultBookingTicket bookingTicket(java.lang.String agentOrderID, java.lang.String agentID, java.lang.String agentPassword, java.lang.String companyID, java.lang.String visitorName, java.lang.String visitorPhoneNumber, java.lang.String identificationCardNumber, java.lang.String city, java.lang.String profession, java.lang.String mail, java.lang.String QQ, java.lang.String blog, java.lang.String weChat, java.lang.String blogAddress, java.lang.String sendAddress, java.lang.String ticketType, java.lang.String ticketNumber, java.lang.String ticketPrice, java.lang.String timeStart, java.lang.String timeEnd, java.lang.String flagPayOffline, java.lang.String flagPayOnline, java.lang.String visitorIP) throws java.rmi.RemoteException{
    if (bookingTicket == null)
      _initBookingTicketProxy();
    return bookingTicket.bookingTicket(agentOrderID, agentID, agentPassword, companyID, visitorName, visitorPhoneNumber, identificationCardNumber, city, profession, mail, QQ, blog, weChat, blogAddress, sendAddress, ticketType, ticketNumber, ticketPrice, timeStart, timeEnd, flagPayOffline, flagPayOnline, visitorIP);
  }
  
  
}