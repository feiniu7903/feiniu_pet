package com.lvmama.comm.pet.service.pay;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pay.PayPosCommercial;
import com.lvmama.comm.pet.vo.Page;

/**
 * 交通银行POS机商户服务接口.
 * @author huyunyan
 *
 */
public interface PayPosCommercialService {
	
	
	/**
	 * POS商户数据列表.
	 * 
	 * @param parameter
	 * @return
	 */
	Page<PayPosCommercial> selectPayPosCommercialByParam(Map<String, String> parameter,Long pageSize, Long page);
	/**
	 * 新增POS商户.
	 */
	Long insert(PayPosCommercial commPosCommercial,  String operatorName);
	/**
	 * 更改POS商户状态.
	 * @param commPos
	 */
	void modifyStatus(PayPosCommercial commPosCommercial,String operatorName,String type);
	/**
	 * 更新POS商户信息.
	 * @param param
	 */
	void update(PayPosCommercial oldCommPosCommercial,PayPosCommercial commPosCommercial,String operatorName);
	/**
	 * 得到单个POS商户.
	 * @param param
	 * @return
	 */
	PayPosCommercial selectById(Long param);
	/**
	 * 查询POS商户.
	 * @param param
	 * @return
	 */
	List<PayPosCommercial> select(Map param);
	/**
	 * 得到POS商户列表.
	 * @param param
	 * @return
	 */
	Long getSelectCount(Map param);
	
	/**
	 * 检查商户号是否正确
	 * @return
	 */
	String login(String commercialNo, String terminalNo, String empNo, String empPasswd);

	/**
	 * 更改POS商户供应商.
	 * @param commPos
	 */
	void modifySupplier(PayPosCommercial payPosCommercial,PayPosCommercial oldPayPosCommercial,
			String operatorName, String supplier);
	
	/**
     * 杉德POS机商户号和终端号判断.
     * @param commercialNo
     * @param terminalNo
     * @param empNo
     * @param empPasswd
     * @return
     */
    String isPosSuccess(String commercialNo, String terminalNo);
	/**
     * 杉德POS机登陆.
     * @param commercialNo
     * @param terminalNo
     * @param empNo
     * @param empPasswd
     * @return
     */
    String isSandLogin(String commercialNo, String terminalNo, String empNo,
			String empPasswd);
	
}
