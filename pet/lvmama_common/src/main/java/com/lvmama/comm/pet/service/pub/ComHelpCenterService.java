/**
 * 
 */
package com.lvmama.comm.pet.service.pub;

import java.util.List;

import com.lvmama.comm.pet.po.info.InfoHelpCenter;
import com.lvmama.comm.pet.po.info.InfoQuesTypeHierarchy;

/**
 * @author liuyi
 *
 */
public interface ComHelpCenterService {
	
	public InfoHelpCenter getHelpInfoByPrimaryId(Long id);
	
	public List<InfoQuesTypeHierarchy> getTwoLevelHelpCenterCategory();
	
	public List<InfoHelpCenter> getHelpInfoList(Long categoryTypeId);
	
	//public InfoHelpCenter getDetailHelpInfo(Long helpInfoId);

}
