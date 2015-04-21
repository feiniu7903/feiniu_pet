package com.lvmama.comm.pet.service.fin;

import java.util.List;
import java.util.Map;

public interface SetTransferTaskService {
	public Object insert(Long orderId);
	public List<Map<String,Object>> select();
	public int delete(Long orderId);
}
