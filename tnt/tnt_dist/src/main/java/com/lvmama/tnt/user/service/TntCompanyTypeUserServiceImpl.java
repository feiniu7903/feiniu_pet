package com.lvmama.tnt.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.mapper.TntCompanyTypeMapper;
import com.lvmama.tnt.user.mapper.TntUserDetailMapper;
import com.lvmama.tnt.user.po.TntCompanyType;
import com.lvmama.tnt.user.po.TntUserDetail;

@Repository("tntCompanyTypeUserService")
public class TntCompanyTypeUserServiceImpl implements TntCompanyTypeUserService {

	@Autowired
	private TntUserDetailMapper tntUserDetailMapper;

	@Autowired
	private TntCompanyTypeMapper tntCompanyTypeMapper;

	@Override
	public List<TntCompanyType> queryWithUserTotal(Page<TntCompanyType> page) {
		Map<Long, Integer> totalMap = getCompanyTypeTotalMap();
		List<TntCompanyType> list = tntCompanyTypeMapper.fetchPage(page);
		if (list != null && !list.isEmpty()) {
			for (TntCompanyType t : list) {
				Integer total = totalMap.get(t.getCompanyTypeId());
				t.setTotal(total != null ? total : 0);
			}
		}
		return list;
	}

	private Map<Long, Integer> getCompanyTypeTotalMap() {
		Map<Long, Integer> map = null;
		TntUserDetail t = new TntUserDetail();
		List<TntUserDetail> totalList = tntUserDetailMapper
				.selectCompanyTypeTotalList(t);
		if (totalList != null) {
			map = new HashMap<Long, Integer>();
			for (TntUserDetail d : totalList) {
				map.put(d.getCompanyTypeId(), d.getTotal());
			}
		}
		return map;
	}

	@Override
	public boolean isContainUser(Long companyTypeId) {
		TntUserDetail t = new TntUserDetail();
		t.setCompanyTypeId(companyTypeId);
		List<Long> list = tntUserDetailMapper.containTypeUsers(t);
		return list != null && !list.isEmpty();
	}

}
