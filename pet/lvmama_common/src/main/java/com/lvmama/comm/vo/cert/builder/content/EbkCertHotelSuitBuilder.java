/**
 * 
 */
package com.lvmama.comm.vo.cert.builder.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ebooking.EbkOrderDataRev;
import com.lvmama.comm.vo.cert.CertificateItemVo;
import com.lvmama.comm.vo.cert.builder.EbkCertBuilder;

/**
 * @author yangbin
 *
 */
public class EbkCertHotelSuitBuilder extends EbkCertBuilder{

	@Override
	protected List<CertificateItemVo> getCertificateItemList(
			EbkCertificate ebkCertificate) {
		List<CertificateItemVo> list = new ArrayList<CertificateItemVo>();
		for(EbkCertificateItem item:ebkCertificate.getEbkCertificateItemList()){
			CertificateItemVo vo = new CertificateItemVo();
			for(EbkOrderDataRev rev:item.getEbkOrderDataRevList()){
				if(rev.hasCertificateItem()){
					Map<String,Object> map =(Map<String,Object>)JSONObject.fromObject(rev.getValue());
					vo.setBaseInfo(map);
				}
			}
			vo.setCertificateItem(item);
			list.add(vo);
		}
		return list;
	}

}
