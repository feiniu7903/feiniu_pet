/**
 * 
 */
package com.lvmama.comm.vo.cert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 该类当中包含多个子项
 * @author yangbin
 *
 */
public class TicketCertificateItemList extends CertificateItemVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6362546668605183925L;
	private List<CertificateItemVo> itemVoList=new ArrayList<CertificateItemVo>(0);

	public List<CertificateItemVo> getItemVoList() {
		return itemVoList;
	}

	public void setItemVoList(List<CertificateItemVo> itemVoList) {
		this.itemVoList = itemVoList;
	}

	@Override
	public Map<String, Object> getBaseInfo() {
		return itemVoList.get(0).getBaseInfo();
	}

	@Override
	public List<Map<String, Object>> getTravellerList() {
		return itemVoList.get(0).getTravellerList();
	}

	@Override
	public Map<String, Object> getFirstTraveller() {
		return itemVoList.get(0).getFirstTraveller();
	}

	@Override
	public long getSize() {
		return itemVoList.size();
	}
	
	
}
