/**
 * 
 */
package com.lvmama.comm.vo.cert;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;

/**
 * @author yangbin
 *
 */

public class CertificateItemVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4331914367525138673L;
	private EbkCertificateItem certificateItem;
	private Map<String,Object> baseInfo;//表示数据存储表当中的一个子项的内容
	private List<Map<String,Object>> travellerList;
	public EbkCertificateItem getCertificateItem() {
		return certificateItem;
	}
	public void setCertificateItem(EbkCertificateItem certificateItem) {
		this.certificateItem = certificateItem;
	}
	
	public Map<String, Object> getBaseInfo() {
		return baseInfo;
	}
	public void setBaseInfo(Map<String, Object> baseInfo) {
		this.baseInfo = baseInfo;
	}
	
	public List<Map<String, Object>> getTravellerList() {
		return travellerList;
	}
	public void setTravellerList(List<Map<String, Object>> travellerList) {
		this.travellerList = travellerList;
	}
	public Map<String, Object> getFirstTraveller() {
		return travellerList.get(0);
	}
	
	public long getSize(){
		return 1L;
	}
}
