/**
 * 
 */
package com.lvmama.comm.vo.cert.builder.content;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ebooking.EbkOrderDataRev;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.vo.cert.CertificateItemVo;
import com.lvmama.comm.vo.cert.TicketCertificateItemList;
import com.lvmama.comm.vo.cert.builder.EbkCertBuilder;

/**
 * @author yangbin
 *
 */
public class EbkCertTicketBuilder extends EbkCertBuilder{

	@Override
	protected List<CertificateItemVo> getCertificateItemList(
			EbkCertificate ebkCertificate) {
		orderMap = new LinkedHashMap<Long, TicketCertificateItemList>();
		for(EbkCertificateItem item:ebkCertificate.getEbkCertificateItemList()){
			CertificateItemVo vo = conver(ebkCertificate,item);
			TicketCertificateItemList tl = null;
			Long orderId=NumberUtils.toLong(vo.getBaseInfo().get("orderId").toString());

//			Long orderId=(Long)vo.getBaseInfo().get("orderId");
			if(orderMap.containsKey(orderId)){
				tl = orderMap.get(orderId);
			}else{
				tl = new TicketCertificateItemList();
				orderMap.put(orderId, tl);
			}
			tl.getItemVoList().add(vo);
		}
		return new ArrayList<CertificateItemVo>(orderMap.values());
	}
	
	private CertificateItemVo conver(EbkCertificate ebkCertificate, EbkCertificateItem item){
		CertificateItemVo vo = new CertificateItemVo();
		
		List<EbkOrderDataRev> revs = item.getEbkOrderDataRevList();
		List<Map<String,Object>> ordPerson = new ArrayList<Map<String,Object>>();
		for(EbkOrderDataRev rev:revs){
			if(rev.hasCertificateItem()){
				JSONObject obj = JSONObject.fromObject(rev.getValue());
				Map<String,Object> map = (Map<String,Object>)JSONObject.toBean(obj,getConfig());
				if(map.get("faxMemo")==null){
					map.put("faxMemo", item.getFaxMemo());
				}
				vo.setBaseInfo(map);
			}else if(rev.hasPerson()){				
				ordPerson.add(converJsonToMap(rev.getValue()));
			}
		}
		vo.setTravellerList(ordPerson);
		return vo;
	}

	private Map<Long,TicketCertificateItemList> orderMap;
}
