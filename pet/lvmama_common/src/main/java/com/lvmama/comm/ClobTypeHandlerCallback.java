/**
 * 
 */
package com.lvmama.comm;

import java.sql.SQLException;

import oracle.sql.CLOB;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

/**
 * clob字段类型读写
 * @author yangbin
 *
 */
public class ClobTypeHandlerCallback implements TypeHandlerCallback{

	@Override
	public Object getResult(ResultGetter getter) throws SQLException {
		CLOB clob = (CLOB) getter.getClob();
		return (clob == null || clob.length() == 0) ? null : clob.getSubString(
				(long) 1, (int) clob.length()); 
	}

	@Override
	public void setParameter(ParameterSetter setter, Object obj)
			throws SQLException {
		CLOB clob = CLOB.getEmptyCLOB();  
        clob.setString(1, (String)obj);  
        setter.setClob(clob);  
	}

	@Override
	public Object valueOf(String arg0) {
		return null;
	}

}
