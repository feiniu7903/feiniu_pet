/**
 * 
 */
package com.lvmama.comm.vo.cert;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkOrderDataRev;

/**
 * 
 * 凭证生成基础信息类，
 * @author yangbin
 *
 */
public class CertificateVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3303474511031071487L;
	private EbkCertificate entity;//模板上面的头部信息的展标位置
	private Map<String,Object> baseInfo;
	private List<Map<String,Object>> travellerList;
	
	public EbkCertificate getEntity() {
		return entity;
	}


	public void setEntity(EbkCertificate entity) {
		this.entity = entity;
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


	public static void main(String[] args) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("aa", 111);
		map.put("bb", "abc");
		JSONObject obj = JSONObject.fromObject(map);
		System.out.println(obj.toString());
	}
}
