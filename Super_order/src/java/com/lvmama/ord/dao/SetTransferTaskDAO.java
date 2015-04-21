package com.lvmama.ord.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;

public class SetTransferTaskDAO extends BaseIbatisDAO {
	public Object insert(Long orderId) {
		return insert("SET_TRANSFER_TASK.insert",orderId);
	}

	public List<Map<String,Object>> select() {
		return (List<Map<String,Object>>)this.queryForList("SET_TRANSFER_TASK.select");
	}

	public int delete(Long orderId) {
		return delete("SET_TRANSFER_TASK.delete",orderId);
	}
}
