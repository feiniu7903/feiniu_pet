package com.lvmama.comm.utils;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.MethodBeforeAdvice;

public class ClusterCut implements MethodBeforeAdvice {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(ClusterCut.class);

	/**
	 * before.
	 *
	 * <pre>
	 * Callback before a given method is invoked.
	 * </pre>
	 *
	 * @param arg0
	 *            method being invoked
	 * @param arg1
	 *            arguments to the method
	 * @param arg2
	 *            target of the method invocation. May be null
	 * @throws Throwable
	 *             if this object wishes to abort the call. Any exception thrown
	 *             will be returned to the caller if it's allowed by the method
	 *             signature. Otherwise the exception will be wrapped as a
	 *             runtime exception.
	 */
	public void before(final Method arg0, final Object[] arg1, final Object arg2)
			throws Throwable {
		String oClass = arg2.toString().split("@")[0];
		System.out.println(oClass);
	}

}
