/**
 * 
 */
package com.lvmama.order.service.impl.builder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 构造
 * @author yangbin
 *
 */
public class SQLBuilderSumImpl extends AbstractSQLBuilderCount {

	private Log log=LogFactory.getLog(SQLBuilderSumImpl.class);
	@Override
	public Log getLog() {
		// TODO Auto-generated method stub
		return log;
	}
	
	private static final String SQL="SELECT nvl(SUM(ORD_ORDER.ORDER_PAY),0) as ORDER_PAY, nvl(SUM(ORD_ORDER.OUGHT_PAY),0) as OUGHT_PAY,nvl(SUM(ORD_ORDER.ACTUAL_PAY),0) as ACTUAL_PAY";

	@Override
	public String getSelect() {
		// TODO Auto-generated method stub
		return SQL;
	}

}
