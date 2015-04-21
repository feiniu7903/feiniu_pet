
package com.lvmama.passport.processor.impl.client.chimelong.util;

import javax.xml.namespace.QName;

import org.codehaus.xfire.fault.FaultInfoException;

public class FaultException
    extends FaultInfoException
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1441403353777642502L;
	private com.lvmama.passport.processor.impl.client.chimelong.FaultException faultInfo;

    public FaultException(String message, com.lvmama.passport.processor.impl.client.chimelong.FaultException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public FaultException(String message, Throwable t, com.lvmama.passport.processor.impl.client.chimelong.FaultException faultInfo) {
        super(message, t);
        this.faultInfo = faultInfo;
    }

    public com.lvmama.passport.processor.impl.client.chimelong.FaultException getFaultInfo() {
        return faultInfo;
    }

    public static QName getFaultName() {
        return new QName("http://ws.agent.chimelong.cn", "FaultException");
    }

}
