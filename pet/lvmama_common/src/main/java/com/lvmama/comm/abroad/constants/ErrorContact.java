package com.lvmama.comm.abroad.constants;

public class ErrorContact {
	/**系统错误*/
	public final static String SYSTEM_ERROR_CODE="99";
	public final static String SYSTEM_ERROR_MESSAGE="SYSTEM ERROR";
	/**远程调用错误*/
	public final static String REMOTE_ERROR_CODE="98";
	public final static String REMOTE_ERROR_MESSAGE="REMOTE ACCESS ERROR!";
	/**参数错误*/
	public final static String PARAMS_ERROR_CODE="97";
	public final static String PARAMS_ERROR_MESSAGE="PARAMS ERROR!";
	/**业务错误*/
	public final static String BUSINESS_ERROR_CODE="96";
	public final static String BUSINESS_ERROR_MESSAGE="BUSINESS ERROR!";
	
	/**IDsession失效返回sub_error_id=10000，返回其他sub_error_id为接口返回错误*/
	public final static String SUB_ERROR_ID_INVALID_SESSION="10000";
	
}
