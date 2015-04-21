/**
 * 
 */
package com.lvmama.pet.pub.service;

import com.lvmama.comm.pet.po.pub.ComMobileArea;
import com.lvmama.comm.pet.service.pub.ComMobileAreaService;
import com.lvmama.pet.pub.dao.ComMobileAreaDAO;

/**
 * @author yangbin
 *
 */
public class ComMobileAreaServiceImpl implements ComMobileAreaService {
	private ComMobileAreaDAO comMobileAreaDAO;
	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.pub.ComMobileAreaService#findMobileArea(com.lvmama.comm.pet.po.pub.ComMobileArea)
	 */
	@Override
	public ComMobileArea findMobileArea(ComMobileArea comMobileArea) {
		return comMobileAreaDAO.findMobileArea(comMobileArea);
	}
	public void setComMobileAreaDAO(ComMobileAreaDAO comMobileAreaDAO) {
		this.comMobileAreaDAO = comMobileAreaDAO;
	}

}
