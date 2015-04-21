package com.lvmama.comm.bee.service.eplace;

import java.util.List;

import com.lvmama.comm.bee.po.eplace.EbkUserPermission;

public interface EbkUserPermissionService {
	Long insert(EbkUserPermission up);
	void update(Long userId,List<Long> permIdList);
}
