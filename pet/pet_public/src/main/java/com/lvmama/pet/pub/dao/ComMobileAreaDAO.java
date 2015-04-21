/**
 * 
 */
package com.lvmama.pet.pub.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComMobileArea;

/**
 * @author yangbin
 *
 */
public class ComMobileAreaDAO extends BaseIbatisDAO{

	public ComMobileArea findMobileArea(ComMobileArea mobileArea) {
		List<ComMobileArea> list = super.queryForList("COM_MOBILE_AREA.selectMobileArea", mobileArea);
		if (list != null&& (list.size() > 0)) {
			return list.get(0);
		}
		return null;
	}
}
