package com.lvmama.comm.bee.service.meta;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.meta.ProductControlRole;
import com.lvmama.comm.pet.po.perm.PermUser;



public interface ProductControlRoleService {
	List<ProductControlRole> getRoleListByCondition(Map<String, Object> condition);
	
	Long countRoleByCondition(Map<String, Object> condition);

	void saveControlRole(ProductControlRole role);

	ProductControlRole getRoleByPrimaryKey(ProductControlRole role);

	List<PermUser> getUserListByRole(ProductControlRole role);

	void deleteControlRole(ProductControlRole role);

	ProductControlRole getControlRoleByUserId(Long userId);
}
