/**
 * 
 */
package com.lvmama.pet.info.service;

import java.util.List;

import com.lvmama.comm.pet.po.info.InfoHelpCenter;
import com.lvmama.comm.pet.po.info.InfoQuesType;
import com.lvmama.comm.pet.po.info.InfoQuesTypeHierarchy;
import com.lvmama.comm.pet.service.info.InfoService;
import com.lvmama.pet.info.dao.InfoHelpCenterDAO;
import com.lvmama.pet.info.dao.InfoQuesTypeDAO;
import com.lvmama.pet.info.dao.InfoQuesTypeHierarchyDAO;

/**
 * 帮助中心实现.
 * @author yangbin
 *
 */
public class InfoServiceImpl implements InfoService {

	private InfoHelpCenterDAO infoHelpCenterDAO;
	private InfoQuesTypeHierarchyDAO infoQuesTypeHierarchyDAO;
	private InfoQuesTypeDAO infoQuesTypeDAO;
	
	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.info.HelpCenterService#save(com.lvmama.comm.pet.po.info.InfoHelpCenter)
	 */
	@Override
	public void saveHelp(InfoHelpCenter info) {
		if(info.getId()==null){
			infoHelpCenterDAO.insert(info);
		}else{
			infoHelpCenterDAO.updateByPrimaryKeySelective(info);
		}
	}

	@Override
	public List<InfoQuesTypeHierarchy> queryInfoQuesTypeHierarchyByObjectType(String objectType) {
		return queryInfoQuesTypeHierarchyByObjectType(objectType,-1L);
	}

	@Override
	public List<InfoQuesTypeHierarchy> queryInfoQuesTypeHierarchyByObjectType(
			String objectType, Long parentTypeId) {
		InfoQuesTypeHierarchy infoQuesTypeHierarchy=new InfoQuesTypeHierarchy();
		infoQuesTypeHierarchy.setTypeId(parentTypeId);
		infoQuesTypeHierarchy.setObjectType(objectType);
		
		List<InfoQuesTypeHierarchy> list=infoQuesTypeHierarchyDAO.queryValidQueTypeListByTypeAndParentId(infoQuesTypeHierarchy);
		if(parentTypeId>0){
			InfoQuesTypeHierarchy parent=infoQuesTypeHierarchyDAO.selectValidTypeByPrimaryKey(parentTypeId);
			for(InfoQuesTypeHierarchy iq:list){
				iq.setParentTypeName(parent.getTypeName());
			}
			
		}
		return list;
	}

	@Override
	public void saveQuestionTypeInHierarchy(
			InfoQuesTypeHierarchy infoQuesTypeHierarchy) {
		if(infoQuesTypeHierarchy.getTypeId()!=null&&infoQuesTypeHierarchy.getTypeId()>0){
			infoQuesTypeDAO.updateByPrimaryKeySelective(infoQuesTypeHierarchy);
		}else{			
			infoQuesTypeHierarchy.setTypeId(infoQuesTypeDAO.insert(infoQuesTypeHierarchy));
			infoQuesTypeHierarchyDAO.insertHierarchy(infoQuesTypeHierarchy);
		}
	}
	
	
	@Override
	public Long saveInfoType(InfoQuesType type) {
		if(type.getTypeId()!=null){
			infoQuesTypeDAO.updateByPrimaryKeySelective(type);
			return type.getTypeId();
		}else{
			return infoQuesTypeDAO.insert(type);
		}
	}

	
	@Override
	public void removeQuestionType(Long typeId) {
		infoQuesTypeDAO.markValid(typeId);
	}

	@Override
	public List<InfoHelpCenter> queryInfoHelpList(InfoHelpCenter infoHelpCenter) {
		return infoHelpCenterDAO.selectByTitleAndType(infoHelpCenter);
	}

	@Override
	public void removeHelp(Long infoHelpId) {
		infoHelpCenterDAO.deleteByPrimaryKey(infoHelpId);
	}

	public void setInfoQuesTypeDAO(InfoQuesTypeDAO infoQuesTypeDAO) {
		this.infoQuesTypeDAO = infoQuesTypeDAO;
	}

	public void setInfoQuesTypeHierarchyDAO(
			InfoQuesTypeHierarchyDAO infoQuesTypeHierarchyDAO) {
		this.infoQuesTypeHierarchyDAO = infoQuesTypeHierarchyDAO;
	}

	public void setInfoHelpCenterDAO(InfoHelpCenterDAO infoHelpCenterDAO) {
		this.infoHelpCenterDAO = infoHelpCenterDAO;
	}

	@Override
	public InfoHelpCenter getInfoHelpById(Long pk) {
		return this.infoHelpCenterDAO.selectByPrimaryKey(pk);
	}

	@Override
	public List<InfoQuesType> queryInfoQuesTypeList(String objectType) {
		return infoQuesTypeDAO.queryQuesByType(objectType);
	}

	@Override
	public InfoQuesType queryInfoQuestByPK(Long typeId) {
		return infoQuesTypeDAO.selectByPrimaryKey(typeId);
	}
	
	/*public void updateFAQ(InfoNormalQues faq){
		this.infoNormalQuesDAO.updateByPrimaryKey(faq);
	}
	
	public void insertFAQ(InfoNormalQues faq) {
		this.infoNormalQuesDAO.insert(faq);
	}
	
	public void removeFAQ(Long id){
		infoNormalQuesDAO.deleteByPrimaryKey(id);
	}
	
	public InfoNormalQues getFAQByPk(Long id){
		return infoNormalQuesDAO.selectByPrimaryKey(id);
	}*/
	
}
