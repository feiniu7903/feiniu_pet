package com.lvmama.pet.pub.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pub.ComParttimeUser;
import com.lvmama.comm.pet.service.pub.ComParttimeUserService;
import com.lvmama.pet.pub.dao.ComParttimeUserDAO;

public class ComParttimeUserServiceImpl implements ComParttimeUserService {
	private ComParttimeUserDAO comParttimeUserDAO;
	
	@Override
	public List<ComParttimeUser> query(Map<String, Object> parameters) {
		List<ComParttimeUser> comParttimeUsers = comParttimeUserDAO.query(parameters);
		for (int i = 0 ; i < comParttimeUsers.size() ; i++) {
			ComParttimeUser user = comParttimeUsers.get(i);
			if (null != user) {
				user.setChannelName(comParttimeUserDAO.getChannelName(user.getChannelId()));
			}
		}
		return comParttimeUsers;
	}
	@Override
	public long count(Map<String, Object> parameters) {
		return comParttimeUserDAO.count(parameters);
	}
	@Override
	public Long insert(ComParttimeUser user) {
		comParttimeUserDAO.insert(user);
		return user.getId();
	}
	@Override
	public void update(ComParttimeUser user) {
		comParttimeUserDAO.update(user);	
	}
	
	@Override
	public ComParttimeUser login(String userName, String password) {
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("userName", userName);
		parm.put("passWord", password);
		parm.put("isValid", "Y");
		List<ComParttimeUser> comParttimeUsers = comParttimeUserDAO.query(parm);
		if (comParttimeUsers.size() > 0) {
			return comParttimeUsers.get(0);
		} else {
			return null;
		}
	}
	
    @Override
    public ComParttimeUser queryByPk(Serializable id) {
    	Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("id", id);
		List<ComParttimeUser> comParttimeUsers = comParttimeUserDAO.query(parm);
		if (comParttimeUsers.size() > 0) {
			return comParttimeUsers.get(0);
		} else {
			return null;
		}  	
    }

	public ComParttimeUserDAO getComParttimeUserDAO() {
		return comParttimeUserDAO;
	}
	public void setComParttimeUserDAO(ComParttimeUserDAO comParttimeUserDAO) {
		this.comParttimeUserDAO = comParttimeUserDAO;
	}


}
