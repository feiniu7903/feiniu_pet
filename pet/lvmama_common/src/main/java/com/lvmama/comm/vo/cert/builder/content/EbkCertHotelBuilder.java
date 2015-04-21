/**
 * 
 */
package com.lvmama.comm.vo.cert.builder.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ebooking.EbkOrderDataRev;
import com.lvmama.comm.vo.cert.CertificateItemVo;
import com.lvmama.comm.vo.cert.builder.EbkCertBuilder;

/**
 * 酒店凭证生成内容
 * @author yangbin
 *
 */
public class EbkCertHotelBuilder extends EbkCertBuilder {

	/* (non-Javadoc)
	 * @see com.lvmama.comm.vo.cert.builder.EbkCertBuilder#getCertificateItemList(com.lvmama.comm.bee.po.ebooking.EbkCertificate)
	 */
	@Override
	protected List<CertificateItemVo> getCertificateItemList(
			EbkCertificate ebkCertificate) {
		List<CertificateItemVo> list = new ArrayList<CertificateItemVo>();
		for(EbkCertificateItem item:ebkCertificate.getEbkCertificateItemList()){
			CertificateItemVo vo = new CertificateItemVo();
			List<Map<String,Object>> nights = new ArrayList<Map<String,Object>>();
			for(EbkOrderDataRev rev:item.getEbkOrderDataRevList()){
				if(rev != null){
					if(rev.hasCertificateItem()){
						Map<String, Object> baseInfo = converJsonToMap(rev.getValue());
						vo.setBaseInfo(baseInfo);
					}else if(rev.hasCertificateItemDay()){
						nights.add(converJsonToMap(rev.getValue()));
					}
				}
			}
			if(!nights.isEmpty()){
				if(nights.size()>1){//超过一晚才做排序
					Collections.sort(nights,new MapComparator("visitTime"));
				}
				vo.getBaseInfo().put("nights", nights);
			}
			vo.setCertificateItem(item);
			list.add(vo);
		}
		return list;
	}
	
}
