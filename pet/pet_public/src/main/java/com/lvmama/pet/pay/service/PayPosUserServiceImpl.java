package com.lvmama.pet.pay.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pay.PayPosUser;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pay.PayPosUserService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.pay.dao.PayPosUserDAO;
import com.lvmama.pet.pub.dao.ComLogDAO;

/**
 *  交通银行Pos用户接口实现.
 * @author huyunyan.
 *
 */

public class PayPosUserServiceImpl implements PayPosUserService{
	/**
	 * POS用户DAO.
	 */
	private PayPosUserDAO payPosUserDAO;
	/**
	 * 日志DAO.
	 */
	protected ComLogDAO comLogDAO;
	/**
	 * 新增POS员工.
	 * @param payPosUser
	 */
	@Override
	public Long insert(PayPosUser payPosUser, String operatorName) {
		Long posUserId = payPosUserDAO.insert(payPosUser);
		saveComLog("PAY_POS_USER", payPosUser.getPosUserId(), operatorName,
				Constant.PAY_POS_ENUM.CREATE.name(), "新增POS机员工","新增POS机员工");
		return posUserId;
	}
	/**
	 * 更改POS员工状态.
	 * @param payPosUser
	 */
	@Override
	public void modifyStatus(PayPosUser payPosUser,String operatorName, String type) {
		//insert COM_LOG
		if("NORMAL".equals(type)){
			 saveComLog("PAY_POS_USER", payPosUser.getPosUserId(), operatorName,
						Constant.PAY_POS_ENUM.ACTIVE.name(), "POS机员工被启用","POS机员工被启用");
		}if("CANCEL".equals(type)){
			 saveComLog("PAY_POS_USER", payPosUser.getPosUserId(), operatorName,
						Constant.PAY_POS_ENUM.UNACTIVE.name(), "POS机员工被停用","POS机员工被停用");
		}
		payPosUserDAO.update(payPosUser);
	}
	
	/**
	 * 更改POS员工密码.
	 * @param payPosUser
	 */
	@Override
	public void modifyPassword(PayPosUser payPosUser,String operatorName, String password) {
         saveComLog("PAY_POS_USER", payPosUser.getPosUserId(), operatorName,
						"", "POS机员工密码被重设","POS机员工密码被重设");
		payPosUserDAO.update(payPosUser);
	}
	
	/**
	 * 更新POS员工信息.
	 */
	@Override
	public void update(PayPosUser oldPayPosUser,PayPosUser payPosUser,String operatorName){
		payPosUserDAO.update(payPosUser);
		if(!oldPayPosUser.getEmpNo().equals(payPosUser.getEmpNo())){
			this.saveComLog("PAY_POS_USER",payPosUser.getPosUserId(), operatorName,
					Constant.PAY_POS_ENUM.UPDATE.name(), "POS机员工号被修改", "原:"+oldPayPosUser.getEmpNo()+",被修改为:"+payPosUser.getEmpNo());
		}
		if(!oldPayPosUser.getEmpName().equals(payPosUser.getEmpName())){
			this.saveComLog("PAY_POS_USER", payPosUser.getPosUserId(), operatorName,
					Constant.PAY_POS_ENUM.UPDATE.name(), "POS机员工名字被修改", "原:"+oldPayPosUser.getEmpName()+",被修改为:"+payPosUser.getEmpName());
		}
		if(!oldPayPosUser.getCommercialId().equals(payPosUser.getCommercialId())){
			this.saveComLog("PAY_POS_USER", payPosUser.getPosUserId(), operatorName,
					Constant.PAY_POS_ENUM.UPDATE.name(), "POS机商户被修改", "原:"+oldPayPosUser.getCommercialId()+",被修改为:"+payPosUser.getCommercialId());
		}
		if(!oldPayPosUser.getCommPosId().equals(payPosUser.getCommPosId())){
			this.saveComLog("PAY_POS_USER", payPosUser.getPosUserId(), operatorName,
					Constant.PAY_POS_ENUM.UPDATE.name(), "POS机终端被修改", "原:"+oldPayPosUser.getCommPosId()+",被修改为:"+payPosUser.getCommPosId());
		}
		
	}	
	/**
	 * 记录日志.
	 * @param objectType
	 * @param objectId
	 * @param operatorName
	 * @param logType
	 * @param content
	 * @param logName
	 */
	private void saveComLog(final String objectType, final Long objectId,
			final String operatorName, final String logType,final String content,
			final String logName) {
			final ComLog log = new ComLog();
			log.setObjectType(objectType);
			log.setObjectId(objectId);
			log.setOperatorName(operatorName);
			log.setLogType(logType);
			log.setContent(content);
			log.setLogName(logName);
			comLogDAO.insert(log);
	}
	
	
	/**
	 * POS用户列表.
	 * 
	 * @param parameter
	 * @return
	 */
	@Override
	public Page<PayPosUser> selectPayPosUserPageByParam(Map<String, String> parameter,
			Long pageSize, Long page) {
		Page pageConfig = Page.page(pageSize, page);
		return this.payPosUserDAO.selectPosUserPageByParam(parameter, pageConfig);
	}
	
	/**
	 * 查询POS员工列表.
	 * @param param
	 * @return
	 */
	@Override
	public List<PayPosUser> selectListByParam( Map param) {
		return payPosUserDAO.queryByParam(param);
	}
	/**
	 * 得到查询POS员工数量.
	 * @param param
	 * @return
	 */
	@Override
	public Long selectListCountByParam(Map param) {
		return payPosUserDAO.selectByParamCount(param);
	}
	/**
	 *  得到单个POS员工.
	 * @param param
	 * @return
	 */
	@Override
	public PayPosUser selectById(Long param) {
		return payPosUserDAO.selectById(param);
	}
	/**
	 * 根据用户的用户名和登陆密码取用户的响应信息.
	 * @param param
	 * @return
	 */
	//@Override
	public PayPosUser queryPosUserBy(Map param) {
		return payPosUserDAO.queryPosUserBy(param);
	}
	/**
	 * 得到查询页面POS员工列表.
	 * @param param
	 * @return
	 */
	public List<HashMap> selectQueryList(Map param){
		return payPosUserDAO.selectQueryList(param);
	}
	public PayPosUser selectByEmpNo(String empNo) {
		return payPosUserDAO.selectByEmpNo(empNo);
	}
	
	
	public void setPayPosUserDAO(PayPosUserDAO payPosUserDAO) {
		this.payPosUserDAO = payPosUserDAO;
	}
	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}
}
