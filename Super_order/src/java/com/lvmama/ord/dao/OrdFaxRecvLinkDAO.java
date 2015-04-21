package com.lvmama.ord.dao;


import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.fax.OrdFaxRecv;
import com.lvmama.comm.bee.po.fax.OrdFaxRecvLink;

public class OrdFaxRecvLinkDAO extends BaseIbatisDAO {

    public Long insertOrdFaxRecvLink(OrdFaxRecvLink ordFaxRecvLink) {
       return (Long) super.insert("ORD_FAX_RECV_LINK.insertLink", ordFaxRecvLink);
    }
    
    public List<OrdFaxRecvLink> selectLinkByRecvId(Long recvId){
    	OrdFaxRecvLink key = new OrdFaxRecvLink();
        key.setOrdFaxRecvId(recvId);
    	return super.queryForList("ORD_FAX_RECV_LINK.selectLinkByRecvId",key);
    }
    
    @SuppressWarnings("unchecked")
	public List<OrdFaxRecvLink> queryLinkByEbkCertificateId(Long ebkCertificateId){
    	OrdFaxRecvLink key = new OrdFaxRecvLink();
    	key.setEbkCertificateId(ebkCertificateId);
    	return (List<OrdFaxRecvLink>) super.queryForList("ORD_FAX_RECV_LINK.queryLinkByEbkCertificateId",key);
    }
    
    public List<OrdFaxRecvLink> selectLinksByParams(Map<String, Object> params){
    	return super.queryForList("ORD_FAX_RECV_LINK.selectLinksByParams",params);
    }
    
    public Long selectLinksCountByParams(Map<String, Object> params){
    	return (Long) super.queryForObject("ORD_FAX_RECV_LINK.selectLinksCountByParams",params);
    }
    
    public int deleteByLinkId(Long ordFaxRecvLinkId) {
        OrdFaxRecvLink key = new OrdFaxRecvLink();
        key.setOrdFaxRecvLinkId(ordFaxRecvLinkId);
        int rows = super.delete("ORD_FAX_RECV_LINK.deleteByOrdFaxRecvLinkId", key);
        return rows;
    }
    public void updateOrdFaxRecvLinkResultStatus(OrdFaxRecvLink ordFaxRecvLink) {
    	 super.update("ORD_FAX_RECV_LINK.updateOrdFaxRecvLinkResultStatus", ordFaxRecvLink);
    }
	
	public List<Long> selectLinkCertificateIdsByRecvId(Long recvId) {
		return super.queryForList("ORD_FAX_RECV_LINK.selectLinkCertificateIdsByRecvId", recvId);
	}
	
	public List<OrdFaxRecvLink> selectLinkAndCertificateByRecvId(Long ordFaxRecvId) {
		return super.queryForList("ORD_FAX_RECV_LINK.selectLinkAndCertificateByRecvId",ordFaxRecvId);
	}
}