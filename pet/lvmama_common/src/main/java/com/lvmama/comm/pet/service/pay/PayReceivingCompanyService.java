package com.lvmama.comm.pet.service.pay;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pay.PayReceivingCompany;

public interface PayReceivingCompanyService {
	/**
	 * 新增一个收款公司
	 * @author ZHANG Nan
	 * @param payReceivingCompany 收款公司对象
	 * @return 主键
	 */
	public Long savePayReceivingCompany(PayReceivingCompany payReceivingCompany);
	/**
	 * 根据主键查询一个收款公司
	 * @author ZHANG Nan
	 * @param receivingCompanyId 主键
	 * @return 收款公司对象
	 */
	public PayReceivingCompany selectReceivingCompanyByPK(Long receivingCompanyId) ;
	/**
	 * 根据参数查询收款公司
	 * @author ZHANG Nan
	 * @param paramMap 参数
	 * @return 收款公司对象集合
	 */
	public List<PayReceivingCompany> selectPayReceivingCompanyByParamMap(Map<String, String> paramMap);
	/**
	 * 根据参数查询收款公司 -总数
	 * @author ZHANG Nan
	 * @param paramMap 参数
	 * @return 收款公司对象集合-总数
	 */
	public Long selectPayReceivingCompanyByParamMapCount(Map<String, String> paramMap);
}