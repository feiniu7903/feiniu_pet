package com.lvmama.comm.pet.service.fin;

/**
 * 提现单服务接口.
 * 
 * @author tom
 * @version Super二期 10/12/10
 * @since Super二期
 */
public interface FinMoneyDrawService {
	/**
	 * 手工处理.
	 * 
	 * @param manualHandleFlag
	 *            true代表手工成功，false代表手工失败
	 * @param moneyDrawId
	 *            提现单ID
	 * @param userName
	 *            userName
	 * @return true代表成功，false代表失败
	 */
	boolean manualHandle(boolean manualHandleFlag, Long moneyDrawId,
			String userName);
}
