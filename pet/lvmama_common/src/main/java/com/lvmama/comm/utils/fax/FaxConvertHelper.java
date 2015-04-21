package com.lvmama.comm.utils.fax;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkFaxSend;
import com.lvmama.comm.bee.vo.ord.Fax;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;

public class FaxConvertHelper {
	
	
	/**
	 * 根据凭证对象获取传真头
	 * @param faxHead
	 * @param target
	 */
	public static void convertBCertToFaxHead(Fax faxHead,SupBCertificateTarget target){
		faxHead.setToName(target.getName());
		faxHead.setToFax(target.getFaxNo());
		faxHead.setToTel(target.getFaxNo());
	}
	
	 
	
	public static void initFax(EbkCertificate certificate,Fax fax,SupBCertificateTarget target){
		fax.setToFax(certificate.getToFax());
		fax.setToTel(certificate.getToTel());
		fax.setToName(certificate.getToName());		
		fax.setBcertTarget(target);
	}

}
