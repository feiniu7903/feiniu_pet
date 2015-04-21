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
 * 线路凭证生成
 * 线路子项当中只记录子项数据，不记录总的结算单价
 * @author yangbin
 *
 */
public class EbkCertRouteBuilder extends EbkCertBuilder{

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
			list.add(vo);
		}
		return list;
	}
}
