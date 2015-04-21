package com.lvmama.comm.pet.service.pay;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pay.PayReceivingBank;

public interface PayReceivingBankService {
	/**
	 * 根据主键查询收款银行
	 * @author ZHANG Nan
	 * @param receivingBankId 主键
	 * @return 收款银行
	 */
	public PayReceivingBank selectReceivingBankByPK(Long receivingBankId);
	/**
	 * 根据银行卡号查询收款银行
	 * @author ZHANG Nan
	 * @param bankCardNo 银行卡号
	 * @return 收款银行
	 */
	public PayReceivingBank selectReceivingBankByBankCardNo(String bankCardNo);
	/**
	 * 根据参数查询收款银行
	 * @author ZHANG Nan
	 * @param paramMap 参数
	 * @return 收款银行集合
	 */
	public List<PayReceivingBank> selectPayReceivingBankByParamMap(Map<String, String> paramMap);
	/**
	 * 根据参数查询收款银行-总数
	 * @author ZHANG Nan
	 * @param paramMap 参数
	 * @return 收款银行集合-总数
	 */
	public Long selectPayReceivingBankByParamMapCount(Map<String, String> paramMap);
	/**
	 * 获取我方启用的收款银行
	 * @author ZHANG Nan
	 * @return
	 */
	public List<String> selectEnableReceivingAccount();
	/**
	 * 根据户名获取银行名称
	 * @author ZHANG Nan
	 * @param receivingAccount 户名
	 * @return 银行名称集合
	 */
	public List<String> selectBankName(String receivingAccount);
}