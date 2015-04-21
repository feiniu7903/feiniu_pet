package com.lvmama.pet.recon.service;

import java.util.Date;

import com.lvmama.comm.pet.po.pay.FinReconBankStatement;

/**
 * 下载账务明细接口
 * @author ZHANG Nan
 *
 */
public interface GatewayAccountService {
	/**
	 * 默认处理前天账务明细
	 * @author ZHANG Nan
	 * @return 是否成功
	 */
	public String processAccount();
	/**
	 * 指定开始和结束日期处理账务明细
	 * @author ZHANG Nan
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param reconStatus 重新对账时根据状态来删除结果表老数据
	 * @return 是否成功
	 */
	public String processAccount(Date startDate, Date endDate,String reconStatus);
	/**
	 * 对账逻辑(单条对账)
	 * @author ZHANG Nan
	 * @param finReconBankStatement 银行源数据对象
	 * @param processUnneedRecon 是否处理免对账数据(对于手工重新对账而言不需要重新处理免对账数据)
	 * @return 是否成功
	 */
	public void processRecon(FinReconBankStatement finReconBankStatement,boolean processUnneedRecon);
}
