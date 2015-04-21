package com.lvmama.passport.processor.impl.client.time100;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.spi.Provider;
import javax.xml.ws.spi.ServiceDelegate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyService {
	private static final Log log = LogFactory.getLog(MyService.class);
	private ServiceDelegate delegate;

	public MyService(URL wsdlDocumentLocation, QName serviceName) {
		String className = "com.sun.xml.internal.ws.spi.ProviderImpl";
		Provider provider = null;
		try {
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			if (classLoader == null) {
				provider = (Provider) Class.forName(className).newInstance();
			} else {
				provider = (Provider) classLoader.loadClass(className)
						.newInstance();
			}
		} catch (Exception e) {
			log.error(e);
		}
		delegate = provider.createServiceDelegate(wsdlDocumentLocation,
				serviceName, this.getClass());
	}

	public <T> T getPort(QName portName, Class<T> serviceEndpointInterface) {
		return delegate.getPort(portName, serviceEndpointInterface);
	}
}
