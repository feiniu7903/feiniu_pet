package com.lvmama.prd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.meta.ProductControlRole;
import com.lvmama.comm.bee.service.meta.ProductControlRoleService;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.prd.dao.ProductControlRoleDAO;

public class ProductControlRoleServiceImpl implements ProductControlRoleService {

	private ProductControlRoleDAO productControlRoleDAO;
	
	@Override
	public List<ProductControlRole> getRoleListByCondition(
			Map<String, Object> condition) {
		return productControlRoleDAO.getListByCondition(condition);
	}

	@Override
	public Long countRoleByCondition(Map<String, Object> condition) {
		return productControlRoleDAO.countByCondition(condition);
	}
	
	@Override
	public void saveControlRole(ProductControlRole role) {
		if (role.getProductControlRoleId() == null) {
			List<ProductControlRole> rList = productControlRoleDAO.findListByUserId(role);
			if (rList.size() != 0) {
				throw new RuntimeException("[" + rList.get(0).getUserName() + "]的权限已经存在,不能重复创建!");
			}
			productControlRoleDAO.save(role);
		} else {
			productControlRoleDAO.update(role);
		}
	}

	@Override
	public ProductControlRole getControlRoleByUserId(Long userId) {
		ProductControlRole role = new ProductControlRole();
		role.setUserId(userId);
		List<ProductControlRole> rList = productControlRoleDAO.findListByUserId(role);
		if (rList.size() == 0) {
			return null;
		}
		return rList.get(0);
	}

	@Override
	public ProductControlRole getRoleByPrimaryKey(ProductControlRole role) {
		return productControlRoleDAO.find(role);
	}
	
	@Override
	public List<PermUser> getUserListByRole(ProductControlRole role) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userList", role.getManagerIdList().replace("[", "").replace("]", ""));
		return productControlRoleDAO.getUserListByMap(map);
	}
	
	@Override
	public void deleteControlRole(ProductControlRole role) {
		productControlRoleDAO.remove(role);
	}

	public ProductControlRoleDAO getProductControlRoleDAO() {
		return productControlRoleDAO;
	}

	public void setProductControlRoleDAO(ProductControlRoleDAO productControlRoleDAO) {
		this.productControlRoleDAO = productControlRoleDAO;
	}
}
