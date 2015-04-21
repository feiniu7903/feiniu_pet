package com.lvmama.com.dao;

import java.util.HashMap;

import com.lvmama.comm.BaseIbatisDAO;

public class ComSeqNoDAO extends BaseIbatisDAO {

	public String getSeqNo(String type) {
		HashMap map = new HashMap();
		map.put("type", type);
		super.update("COM_SEQ_NO.getSeqNo", map);
		return (String) map.get("seqNo");
	}

}
