package com.lvmama.pet.pay.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.pay.PayPos;
import com.lvmama.comm.pet.po.pay.PayPosCommercial;
import com.lvmama.comm.pet.po.pay.PayPosUser;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pay.PayPosCommercialService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.pay.dao.PayPosCommercialDAO;
import com.lvmama.pet.pay.dao.PayPosDAO;
import com.lvmama.pet.pay.dao.PayPosUserDAO;
import com.lvmama.pet.pub.dao.ComLogDAO;



/**
 * 交通银行POS机商户服务实现.
 * @author liwenzhan
 *
 */
public class PayPosCommercialServiceImpl implements PayPosCommercialService{
	
	/**
	 * LOG.
	 */
	private static final Logger log = Logger.getLogger(PayPosCommercialServiceImpl.class);
	/**
	 * POS商户DAO.
	 */
	private PayPosCommercialDAO payPosCommercialDAO;
	/**
	 * POS终端DAO.
	 */
	private PayPosDAO payPosDAO;
	/**
	 * POS员工DAO.
	 */
	private static PayPosUserDAO payPosUserDAO;
	/**
	 * 日志DAO.
	 */
	protected ComLogDAO comLogDAO;
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
			final String operatorName, final String logType,
			final String logName, final String content) {
		final ComLog log = new ComLog();
		log.setObjectType(objectType);
		log.setObjectId(objectId);
		log.setOperatorName(operatorName);
		log.setLogType(logType);
		log.setLogName(logName);
		log.setContent(content);
		comLogDAO.insert(log);
	}
	
	/**
	 * POS商户数据列表.
	 * 
	 * @param parameter
	 * @return
	 */
	@Override
	public Page<PayPosCommercial> selectPayPosCommercialByParam(Map<String, String> parameter,
			Long pageSize, Long page) {
		Page<PayPosCommercial> pageConfig = Page.page(pageSize, page);
		return this.payPosCommercialDAO.selectByParam(parameter, pageConfig);
	}
	
	
	/**
	 * 新增POS商户.
	 */
	@Override
	public Long insert(PayPosCommercial commPosCommercial, String operatorName) {
		Long commPosId = payPosCommercialDAO.insert(commPosCommercial);
		this.saveComLog("COMM_POS_COMMERCIAL", commPosCommercial.getCommercialId(),
				operatorName, Constant.PAY_POS_ENUM.CREATE.name(), "新增POS机商户", "新增POS机商户");
		return commPosId;
	}
	/**
	 * 更改POS商户状态.
	 * @param commPos
	 */
	@Override
	public void modifyStatus(PayPosCommercial payPosCommercial,
			String operatorName, String type) {
		payPosCommercialDAO.updateStatus(payPosCommercial);
		Map<String,Object> params=new HashMap<String, Object>();
		Long _commercialId=payPosCommercial.getCommercialId();
		if (_commercialId!=null) {
			params.put("commercialId", _commercialId);
		}
		List<PayPos> payPosList = payPosDAO.queryPayPosList(params);
		List<PayPosUser> payPosUserList=payPosUserDAO.queryByParam(params);
		if("NORMAL".equals(type)){
			this.saveComLog("PAY_POS_COMMERCIAL", payPosCommercial.getCommercialId(), operatorName,
					Constant.PAY_POS_ENUM.ACTIVE.name(), "POS机商户被启用", "POS机商户被启用");
		}
		else if("CANCEL".equals(type)){
			this.saveComLog("PAY_POS_COMMERCIAL", payPosCommercial.getCommercialId(), operatorName,
					Constant.PAY_POS_ENUM.UNACTIVE.name(), "POS机商户被停用", "POS机商户被停用");
			for(PayPos item:payPosList){
				item.setStatus(type);
				payPosDAO.update(item);
				this.saveComLog("PAY_POS", item.getPosId(),
						operatorName, Constant.PAY_POS_ENUM.COMMERCIAL_UNACTIVE.name(), "POS机终端所属商户被停用",  "POS机终端所属商户被停用");
			}
			for(PayPosUser item:payPosUserList){
				item.setEmpStatus(type);
				payPosUserDAO.update(item);
				saveComLog("COMM_POS_USER", item.getPosUserId(), operatorName,
						Constant.PAY_POS_ENUM.COMMERCIAL_UNACTIVE.name(), "POS机终端员工所属商户被停用","POS机终端员工所属商户被停用");			
			}
		}
	}
	
	
	/**
	 * 更改POS商户供应商.
	 * @param commPos
	 */
	@Override
	public void modifySupplier(PayPosCommercial payPosCommercial,PayPosCommercial oldPayPosCommercial,
			String operatorName, String supplier) {
		payPosCommercialDAO.updateSupplier(payPosCommercial);
		if(!oldPayPosCommercial.getSupplier().equals(payPosCommercial.getSupplier())){
	    this.saveComLog("PAY_POS_COMMERCIAL", payPosCommercial.getCommercialId(), operatorName,supplier,
	         "POS机商户号供应商被修改", "原:"+oldPayPosCommercial.getSupplier()+",被修改为:"+payPosCommercial.getSupplier());
		}
	}

	
	/**
	 * 更新POS商户信息.
	 * @param param
	 */
	@Override
	public void update(PayPosCommercial oldCommPosCommercial,PayPosCommercial commPosCommercial,String operatorName) {
		payPosCommercialDAO.update(commPosCommercial);
		if(!oldCommPosCommercial.getCommercialNo().equals(commPosCommercial.getCommercialNo())){
			this.saveComLog("COMM_POS_COMMERCIAL", commPosCommercial.getCommercialId(), operatorName,
					Constant.PAY_POS_ENUM.UPDATE.name(), "POS机商户号被修改", "原:"+oldCommPosCommercial.getCommercialNo()+",被修改为:"+commPosCommercial.getCommercialNo());
		}
		if(!oldCommPosCommercial.getCommercialName().equals(commPosCommercial.getCommercialName())){
			this.saveComLog("COMM_POS_COMMERCIAL", commPosCommercial.getCommercialId(), operatorName,
					Constant.PAY_POS_ENUM.UPDATE.name(), "POS机商户名被修改", "原:"+oldCommPosCommercial.getCommercialName()+",被修改为:"+commPosCommercial.getCommercialName());
		}
	}
	
	
	/**
	 * 得到单个POS商户.
	 * @param param
	 * @return
	 */
	@Override
	public PayPosCommercial selectById(Long param) {
		return payPosCommercialDAO.selectById(param);
	}
	/**
	 * 查询POS商户.
	 * @param param
	 * @return
	 */
	@Override
	public List<PayPosCommercial> select(Map param) {
		return payPosCommercialDAO.select(param);
	}
	/**
	 * 查询POS商户.
	 * @param param
	 * @return
	 */
	//@Override
	public List<PayPosCommercial> queryByParam(Map param) {
		return payPosCommercialDAO.queryByParam(param);
	}
	/**
	 * 得到POS商户列表.
	 * @param param
	 * @return
	 */
	@Override
	public Long getSelectCount(Map param) {
		return payPosCommercialDAO.getSelectCount(param);
	}

	public void setPayPosCommercialDAO(PayPosCommercialDAO payPosCommercialDAO) {
		this.payPosCommercialDAO = payPosCommercialDAO;
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

	@Override
	public String login(String commercialNo, String terminalNo, String empNo,
			String empPasswd) {
		if (StringUtils.isEmpty(commercialNo)) {
//			return "0021"; // 9101 商户号缺少
			return Constant.PAY_POS_STATUS.NO_COMMERCIAL_NO.getAttr1();
		}
		if (StringUtils.isEmpty(terminalNo)){
//			return "0022";// 9102 终端号缺少
			return Constant.PAY_POS_STATUS.NO_TERMINAL_NO.getAttr1();
		}
		if (StringUtils.isEmpty(empNo)){
//			return "0023";// 9103 员工号缺少
			return Constant.PAY_POS_STATUS.NO_USER_ID.getAttr1();
		}
		if (StringUtils.isEmpty(empPasswd)){
//			return "0024";// 9104 员工登录密码缺少
			return Constant.PAY_POS_STATUS.NO_USER_PASSWORD.getAttr1();
		}

		if (!checkPosCommercial(commercialNo,Constant.PAY_POS_SUPPLIER_TYPE.COMM.name())) {
//			return "0011"; //商户号停用.
			return Constant.PAY_POS_STATUS.COMMERCIAL_NO_ABOLISHED.getAttr1();
		}
		if (!checkPos(commercialNo,terminalNo)) {
//			return "0012"; // 终端号停用.
			return Constant.PAY_POS_STATUS.TERMINAL_NO_ABOLISHED.getAttr1();
		}
		if (!checkPosUser(commercialNo,terminalNo,empNo, empPasswd)) {
//			return "0013"; // 员工不存在或者已停用.
			return Constant.PAY_POS_STATUS.NO_USER.getAttr1();
		}
		return Constant.PAY_POS_STATUS.SUCCESS.getAttr1();

	}

	/**
	 * 判断该POS用户是否有效.
	 * 
	 * @param empNo
	 * @param empPasswd
	 * @return
	 */
	private static boolean checkPosUser(String commercialNo,String terminalNo,String empNo,String empPasswd){
		boolean successFlag = false;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("empNo", empNo);
		param.put("empPasswd", empPasswd.toLowerCase());
		param.put("commercialNo", commercialNo.trim());
		param.put("terminalNo", terminalNo.trim());
		PayPosUser payPosUser = payPosUserDAO.queryPosUserBy(param);
		if (payPosUser != null
				&& Constant.PAY_POS_ENUM.NORMAL.name().equals(
						payPosUser.getEmpStatus())) {
			successFlag = true;
			log.info("payPosUser  EmpStatus="+payPosUser.getEmpStatus());
		}
		return successFlag;
	}

	/**
	 * 判断该商户是否有效.
	 * @param commercialNo
	 * @return
	 */
	private boolean checkPosCommercial(String commercialNo,String supplierType) {
		boolean successFlag = false;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("commercialNo", commercialNo);
		param.put("supplier", supplierType);
		List<PayPosCommercial> list = payPosCommercialDAO.queryByParam(param);
		PayPosCommercial payPosCommercial = new PayPosCommercial();
		if (list != null && list.size() > 0) {
			payPosCommercial = list.get(0);
			if (payPosCommercial != null
					&& Constant.PAY_POS_ENUM.NORMAL.name().equals(
							payPosCommercial.getStatus())) {
				successFlag = true;
			}
		}
		return successFlag;
	}

	/**
	 * 判断该POS终端是否有效.
	 * @param commercialNo
	 * @return
	 */
	private boolean checkPos(String commercialNo ,String terminalNo) {
		boolean successFlag = false;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("commercialNo", commercialNo.trim());
		param.put("terminalNo", terminalNo.trim());
		PayPos payPos = payPosDAO.queryPayPosBy(param);
		if (payPos != null && Constant.PAY_POS_ENUM.NORMAL.name().equals(payPos.getStatus())) {
			successFlag = true;
			log.info("payPos Status="+payPos.getStatus());
		}
		return successFlag;
	}    

    
    /**
     * 杉德POS机商户号和终端号判断.
     * @param commercialNo
     * @param terminalNo
     * @param empNo
     * @param empPasswd
     * @return
     */
    @Override
	public String isPosSuccess(String commercialNo, String terminalNo) {
		if (StringUtils.isEmpty(commercialNo)) {
//			return 21 商户号缺少
			return Constant.SAND_PAY_POS_STATUS.NO_COMMERCIAL_NO.name();
		}
		if (StringUtils.isEmpty(terminalNo)){
//			return 22 终端号缺少
			return Constant.SAND_PAY_POS_STATUS.NO_TERMINAL_NO.name();
		}

		if (!checkPosCommercial(commercialNo,Constant.PAY_POS_SUPPLIER_TYPE.SAND.name())) {
//			return 11 商户号停用.
			return Constant.SAND_PAY_POS_STATUS.COMMERCIAL_NO_ABOLISHED.name();
		}
		if (!checkPos(commercialNo,terminalNo)) {
//			return  12   终端号停用.
			return Constant.SAND_PAY_POS_STATUS.TERMINAL_NO_ABOLISHED.name();
		}
		return Constant.SAND_PAY_POS_STATUS.SUCCESS.name();
	}
    
    
    /**
     * 杉德POS机登陆.
     * @param commercialNo
     * @param terminalNo
     * @param empNo
     * @param empPasswd
     * @return
     */
    @Override
	public String isSandLogin(String commercialNo, String terminalNo, String empNo,
			String empPasswd) {
		if (StringUtils.isEmpty(commercialNo)) {
//			return 21 商户号缺少
			return Constant.SAND_PAY_POS_STATUS.NO_COMMERCIAL_NO.name();
		}
		if (StringUtils.isEmpty(terminalNo)){
//			return 22 终端号缺少
			return Constant.SAND_PAY_POS_STATUS.NO_TERMINAL_NO.name();
		}
		if (StringUtils.isEmpty(empNo)){
//			return 23 员工号缺少
			return Constant.SAND_PAY_POS_STATUS.NO_USER_ID.name();
		}
		if (StringUtils.isEmpty(empPasswd)){
//			return 24  员工登录密码缺少
			return Constant.SAND_PAY_POS_STATUS.NO_USER_PASSWORD.name();
		}

		if (!checkPosCommercial(commercialNo,Constant.PAY_POS_SUPPLIER_TYPE.SAND.name())) {
//			return 11 商户号停用.
			return Constant.SAND_PAY_POS_STATUS.COMMERCIAL_NO_ABOLISHED.name();
		}
		if (!checkPos(commercialNo,terminalNo)) {
//			return  12   终端号停用.
			return Constant.SAND_PAY_POS_STATUS.TERMINAL_NO_ABOLISHED.name();
		}
		if (!checkPosUser(commercialNo,terminalNo,empNo, empPasswd)) {
//			return  13  员工不存在或停用.
			return Constant.SAND_PAY_POS_STATUS.NO_USER.name();
		}
		return Constant.SAND_PAY_POS_STATUS.SUCCESS.name();
	}
}
