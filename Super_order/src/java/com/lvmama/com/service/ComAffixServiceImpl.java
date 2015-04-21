/**
 * 
 */
package com.lvmama.com.service;

import java.util.List;
import java.util.Map;

import com.lvmama.com.dao.ComAffixDAO;
import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.pet.po.pub.ComAffix;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComAffixService;

/**
 * @author yangbin
 *
 */
public final class ComAffixServiceImpl implements ComAffixService {

	/* (non-Javadoc)
	 */
	@Override
	public void addAffix(final ComAffix affix, final String operator) {
		Long id = comAffixDAO.insert(affix);
		ComLog log = new ComLog();
		log.setParentId(affix.getObjectId());
		log.setParentType(affix.getObjectType());
		log.setObjectId(id);
		log.setObjectType("COM_AFFIX");
		log.setLogType("insert");
		log.setLogName("上传文件");		
		log.setContent("上传文件 名称"+affix.getName()+"("+id+")");
		log.setOperatorName(operator);		
		comLogDAO.insert(log);
	}

	/* (non-Javadoc) 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public long selectCountByParam(final Map parameter) {
		return comAffixDAO.selectCountByParam(parameter);
	}

	/* (non-Javadoc)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ComAffix> selectListByParam(final Map parameter) {
		return comAffixDAO.selectByParam(parameter);
	}

	/**
	 * 附件DAO.
	 */
	private ComAffixDAO comAffixDAO;
	
	/**
	 * 日志DAO.
	 */
	private ComLogDAO comLogDAO;
	
	/**
	 * 附件DAO.
	 * @param comAffixDAO 附件DAO 
	 */
	public void setComAffixDAO(final ComAffixDAO pComAffixDAO) {
		this.comAffixDAO = pComAffixDAO;
	}

	@Override
	public ComAffix getAffix(final Long affixId) {
		return comAffixDAO.selectByPrimary(affixId);
	}

	@Override
	public void removeAffix(final ComAffix affix,String operator) {
		// TODO Auto-generated method stub		
		comAffixDAO.delete(affix);

		ComLog log = new ComLog();
		log.setParentId(affix.getObjectId());
		log.setParentType(affix.getObjectType());
		log.setObjectId(affix.getAffixId());
		log.setObjectType("COM_AFFIX");
		log.setLogType("delete");
		log.setLogName("删除上传的文件");
		log.setContent("文件 "+affix.getName()+"("+affix.getAffixId()+") 被删除");
		log.setOperatorName(operator);
		comLogDAO.insert(log);		
	}

	/**
	 * 日志DAO.
	 * @param comLogDAO
	 */
	public void setComLogDAO(final ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}

	
	@Override
	public ComAffix selectLatestRecordByParam(Map<String, Object> parameter) {
		return comAffixDAO.selectLatestRecordByParam(parameter);
	}

	@Override
	public List selectListForTimeDescByParam(Map<String, Object> parameter) {
		return comAffixDAO.selectForTimeDescByParam(parameter);
	}

	@Override
	public void addAffixForGroupAdvice(final ComAffix affix, final String operatorName) {
		Long id = comAffixDAO.insertAll(affix);
		ComLog log = new ComLog();
		log.setParentId(affix.getObjectId());
		log.setParentType(affix.getObjectType());
		log.setObjectId(id);
		log.setObjectType("COM_AFFIX");
		log.setLogType("insert");
		log.setLogName("上传文件");		
		log.setContent("上传文件 名称"+affix.getName()+"("+id+")");
		log.setOperatorName(operatorName);		
		comLogDAO.insert(log);
	}

	@Override
	public List selectListByObjectIds(Map<String, Object> parameter) {
		return this.comAffixDAO.selectListByObjectIds(parameter);
	}
	
}
