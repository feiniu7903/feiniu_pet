/**
 * 
 */
package com.lvmama.comm.vo.cert.builder;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;

/**
 * @author yangbin
 *
 */
public class IdentityUtil {

	public static String make(EbkCertificateItem item){
		OrdOrderItemMeta meta = item.getOrderItemMeta();
		StringBuffer sb=new StringBuffer();
		sb.append("item_");
		sb.append(meta.getOrderItemMetaId());
		return sb.toString();
	}
	
	public static String makePersonId(OrdPerson person){
		StringBuffer sb=new StringBuffer();
		sb.append("person_");
		sb.append(person.getPersonId());
		return sb.toString();
	}
}
