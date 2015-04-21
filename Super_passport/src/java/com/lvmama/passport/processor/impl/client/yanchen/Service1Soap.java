
package com.lvmama.passport.processor.impl.client.yanchen;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name = "Service1Soap", targetNamespace = "http://my0519.com/")
@SOAPBinding(use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface Service1Soap {


    @WebMethod(operationName = "AddData", action = "http://my0519.com/AddData")
    @WebResult(name = "AddDataResult", targetNamespace = "http://my0519.com/")
    public int addData(
        @WebParam(name = "DataList", targetNamespace = "http://my0519.com/")
        Reserve DataList);

    @WebMethod(operationName = "AddData2", action = "http://my0519.com/AddData2")
    @WebResult(name = "AddData2Result", targetNamespace = "http://my0519.com/")
    public int addData2(
        @WebParam(name = "DataList", targetNamespace = "http://my0519.com/")
        Reserve2 DataList);

}
