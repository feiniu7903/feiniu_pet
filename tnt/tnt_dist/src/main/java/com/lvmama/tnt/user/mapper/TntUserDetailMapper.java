package com.lvmama.tnt.user.mapper;

import java.util.List;

import com.lvmama.tnt.user.po.TntUserDetail;

public interface TntUserDetailMapper {

	public int insert(TntUserDetail entity);

	public int update(TntUserDetail tntUserDetail);

	public TntUserDetail getById(Long userDetailId);

	public TntUserDetail getByUserId(Long userId);

	public List<TntUserDetail> selectCompanyTypeTotalList(TntUserDetail entity);

	public List<Long> containTypeUsers(TntUserDetail entity);

}
