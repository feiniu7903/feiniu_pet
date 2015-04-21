package com.lvmama.comm.pet.service.pay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pay.PayPosUser;
import com.lvmama.comm.pet.vo.Page;


/**
 * 交行POS员工接口.
 * @author huyunyan
 *
 */
public interface PayPosUserService {
	
	/**
	 * POS用户列表.
	 * 
	 * @param parameter
	 * @return
	 */
	Page<PayPosUser> selectPayPosUserPageByParam(Map<String, String> parameter,
			Long pageSize, Long page);
	/**
	 * 新增POS员工.
	 * @param commPosUser
	 */
	Long insert(final PayPosUser commPosUser, String operatorName);
	
	/**
	 * 更改POS员工状态.
	 * @param commPosUser
	 */
	void modifyStatus(PayPosUser commPosUser,String operatorName, String type);
	
	/**
	 * 更改POS员工密码.
	 * @param commPosUser
	 */
	void modifyPassword(PayPosUser commPosUser,String operatorName, String password);
	/**
	 * 更新POS员工信息.
	 */
	void update(PayPosUser oldPayPosUser,PayPosUser payPosUser,String operatorName);
	/**
	 * 查询POS员工列表.
	 * @param param
	 * @return
	 */
	List<PayPosUser> selectListByParam(Map param);
	/**
	 * 得到查询POS员工数量.
	 * @param param
	 * @return
	 */
	Long selectListCountByParam(Map param);
	/**
	 *  得到单个POS员工.
	 * @param param
	 * @return
	 */
	PayPosUser selectById(Long param);
	/**
	 * 得到查询页面POS员工列表MAP.
	 * @param param
	 * @return
	 */
	public List<HashMap> selectQueryList(Map param);
	/**
	 * 根据POS机员工号查询POS机员工
	 * @author ZHANG Nan
	 * @param empNo POS机员工号
	 * @return POS机员工对象
	 */
	public PayPosUser selectByEmpNo(String empNo) ;
}
