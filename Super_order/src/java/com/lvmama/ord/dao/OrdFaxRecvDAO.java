package com.lvmama.ord.dao;


import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.fax.OrdFaxRecv;

public class OrdFaxRecvDAO extends BaseIbatisDAO {

    public OrdFaxRecvDAO() {
        super();
    }
    
    public Long insertOrdFaxRecv(OrdFaxRecv record) {
    	record.setValid("true");
        return (Long) super.insert("ORD_FAX_RECV.insert", record);
    }
    public OrdFaxRecv selectByPrimaryKey(Long ordFaxRecvId) {
        OrdFaxRecv key = new OrdFaxRecv();
        key.setOrdFaxRecvId(ordFaxRecvId);
        OrdFaxRecv record = (OrdFaxRecv) super.queryForObject("ORD_FAX_RECV.selectByPrimaryKey", key);
        return record;
    }
    
    public List<OrdFaxRecv> selectByParam(Map param){
    	List<OrdFaxRecv> list = super.queryForList("ORD_FAX_RECV.selectByParam",param);
    	return list;
    }
    
    public Long selectByParamCount(Map param){
    	return (Long)super.queryForObject("ORD_FAX_RECV.selectByParamCount",param);
    }
	 
	public void updateOrdFaxRecvStatus(Map map) {
		super.update("ORD_FAX_RECV.updateFaxRecvStatus", map);
	}
	 
	
	public void updateOrdFaxRecvValidToFalse(Map<String,List<Long>>  recvIdMapList) {
		super.update("ORD_FAX_RECV.updateOrdFaxRecvValidToFalse", recvIdMapList);
	}
}