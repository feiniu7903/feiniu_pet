package com.lvmama.order.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.order.po.SQlBuilderMaterial;

/**
 * buildMaterial方法输入参数校验.
 * 
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.UtilityTool#isNotNull(Object)
 * @see org.aopalliance.intercept.MethodInterceptor
 * @see org.aopalliance.intercept.MethodInterceptor
 * @see org.aopalliance.intercept.MethodInvocation
 */
public class MaterialAround implements MethodInterceptor {
	/**
	 * invoke.<br>
	 * Implement this method to perform extra treatments before and after the
	 * invocation. Polite implementations would certainly like to invoke
	 * Joinpoint.proceed().
	 * 
	 * @param invocation
	 *            the method invocation joinpoint
	 * @return the result of the call to Joinpoint.proceed(), might be
	 *         intercepted by the interceptor.
	 * @throws java.lang.Throwable
	 *             - if the interceptors or the target-object throws an
	 *             exception.
	 */
	public Object invoke(final MethodInvocation invocation) throws Throwable {
		Object rval = null;
		if (invocation.getMethod().getName().equalsIgnoreCase("buildMaterial")) {
			final Object obj = invocation.getArguments()[0];
			final SQlBuilderMaterial material = (SQlBuilderMaterial) invocation
					.getArguments()[1];
			if (UtilityTool.isNotNull(obj)) {
				rval = invocation.proceed();
			} else {
				rval = material;
			}
		} else {
			rval = invocation.proceed();
		}
		return rval;
	}
}
