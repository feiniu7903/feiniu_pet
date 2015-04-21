package com.lvmama.pet.pay.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pay.PayPos;
import com.lvmama.comm.pet.po.pay.PayPosUser;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pay.PayPosService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.pay.dao.PayPosDAO;
import com.lvmama.pet.pay.dao.PayPosUserDAO;
import com.lvmama.pet.pub.dao.ComLogDAO;

/**
 * 交通银行POS机终端服务实现.
 * 
 * @author huyunyan
 * 
 */
public class PayPosServiceImpl implements PayPosService {

	/**
	 * POS终端DAO.
	 * 
	 */
	PayPosDAO payPosDAO;
	/**
	 * POS员工DAO.
	 */
	PayPosUserDAO payPosUserDAO;
	/**
	 * 日志DAO.
	 */
	protected ComLogDAO comLogDAO;

	/**
	 * 插入POS员工.
	 */
	@Override
	public Long insert(PayPos payPos, String operatorName) {
		Long posId = payPosDAO.insert(payPos);
		saveComLog("PAY_POS", payPos.getPosId(), operatorName,
				Constant.PAY_POS_ENUM.CREATE.name(), "新增POS机",
				"新增POS机");
	     return posId;
	}

	/**
	 * 更新状态.
	 */
	@Override
	public void modifyStatus(PayPos payPos, String operatorName, String type) {
		payPosDAO.updateStatus(payPos);
		Map<String, Object> params = new HashMap<String, Object>();
		Long _payPosId = payPos.getPosId();
		if (_payPosId != null) {
			params.put("commPosId", _payPosId);
		}
		List<PayPosUser> listUser = payPosUserDAO.queryByParam(params);
		if ("NORMAL".equals(type)) {
			saveComLog("PAY_POS", payPos.getPosId(), operatorName,Constant.PAY_POS_ENUM.ACTIVE.name(), "POS机终端被启用","POS机终端被启用");
		}
		if ("CANCEL".equals(type)) {
			saveComLog("PAY_POS", payPos.getPosId(), operatorName,Constant.PAY_POS_ENUM.UNACTIVE.name(), "POS机终端被停用","POS机终端被停用");
			for (PayPosUser itemUser : listUser) {
				itemUser.setEmpStatus(type);
				payPosUserDAO.update(itemUser);
				saveComLog("PAY_POS_USER", itemUser.getPosUserId(),operatorName,Constant.PAY_POS_ENUM.POS_UNACTIVE.name(),"POS机终端员工所属终端被停用", "POS机终端员工所属终端被停用");
			}
		}
	}

	/**
	 * 更新.
	 */
	@Override
	public void update(PayPos oldPayPos,PayPos payPos,String operatorName) {
		payPosDAO.update(payPos);
		if(!oldPayPos.getTerminalNo().equals(payPos.getTerminalNo())){
			this.saveComLog("PAY_POS",payPos.getPosId(), operatorName,
					Constant.PAY_POS_ENUM.UPDATE.name(), "POS机终端号", "原:"+oldPayPos.getTerminalNo()+",被修改为:"+payPos.getTerminalNo());
		}
		if(!oldPayPos.getMemo().equals(payPos.getMemo())){
			this.saveComLog("PAY_POS", payPos.getPosId(), operatorName,
					Constant.PAY_POS_ENUM.UPDATE.name(), "POS机备注被修改", "原:"+oldPayPos.getMemo()+",被修改为:"+payPos.getMemo());
		}
	}

	/**
	 * 
	 * @param objectType
	 * @param objectId
	 * @param operatorName
	 * @param logType
	 * @param logName
	 */
	private void saveComLog(final String objectType, final Long objectId,
			final String operatorName, final String logType,
			final String content, final String logName) {
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
	 * 根据ID得到POS终端.
	 */
	@Override
	public PayPos selectById(Long param) {
		return (PayPos) payPosDAO.selectById(param);
	}
	/**
	 * 根据组装的条件返回POS机信息.
	 * @param param
	 * @return
	 */
	//@Override
	public PayPos queryCommPosBy(Map param) {
		return (PayPos) payPosDAO.queryPayPosBy(param);
	}
	/**
	 * 查询POS终端.
	 */
	@Override
	public List<PayPos> select(Map param) {
		return payPosDAO.queryPayPosList(param);
	}

	/**
	 * 得到POS终端查询数目.
	 */
	@Override
	public Long getSelectCount(Map param) {
		return payPosDAO.getSelectCount(param);
	}

	/**
	 * POS商户数据列表.
	 * 
	 * @param parameter
	 * @return
	 */
	@Override
	public Page<PayPos> selectPayPosPageByParam(Map<String, String> parameter,
			Long pageSize, Long page) {
		Page pageConfig = Page.page(pageSize, page);
		return this.payPosDAO.selectPosPageByParam(parameter, pageConfig);
	}
	
	/**
	 * 得到查询POS用户查询页结果
	 * 
	 * @param param
	 * @return
	 */
	@Override
	public List<HashMap> selectQueryList(Map param) {
		return payPosDAO.selectQueryList(param);
	}
	/**
	 * 用终端号模糊查询
	 * @author ZHANG Nan
	 * @param terminalNo
	 * @return
	 */
	public List<PayPos> selectLikeTerminalNo(String search){
		return payPosDAO.selectLikeTerminalNo(search);
	}
	
	
	

	public void setPayPosDAO(PayPosDAO payPosDAO) {
		this.payPosDAO = payPosDAO;
	}

	public void setPayPosUserDAO(PayPosUserDAO payPosUserDAO) {
		this.payPosUserDAO = payPosUserDAO;
	}

	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}
}
