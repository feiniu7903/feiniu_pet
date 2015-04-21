package com.lvmama.comm.bee.service.eplace;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.eplace.EbkUserTarget;

public interface EbkUserTargetService {
	Long insert(EbkUserTarget ebkUserTarget);
	void delete(EbkUserTarget ebkUserTarget);
	List<EbkUserTarget> getEbkUserTargetList(Map<String,Object> params);
}
