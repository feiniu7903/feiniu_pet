package com.lvmama.pet.sweb.prod;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;

import com.lvmama.comm.BackBaseAction;

/**
 * <p>pet项目对于销售产品操作的父类Action,所有pet系统中对销售产品的操作管理界面都应该继承此类，以便统一管理.
 */
public abstract class ProdProductAction extends BackBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7718681700744201560L;
	
	/**
	 * 检查对象是否能够合理
	 * @param object 需要被检查的对象
	 * @return 是否合理
	 * <p>各实现类应该自行实现所需保存的对象是否符合保存的要求</p>
	 */
	protected abstract boolean checkProduct(Object object);
	
	/**
	 * 输出调试信息
	 * @param log 日志输出器
	 * @param message 调试信息
	 * <p>当日志输出器的打印级别是DEBUG时，输出需要打印的调试信息.</p>
	 */
	protected void debug(final Log log, final String message) {
		if (null != log && log.isDebugEnabled() && StringUtils.isNotBlank(message)) {
			log.debug(message);
		}
	}

}
