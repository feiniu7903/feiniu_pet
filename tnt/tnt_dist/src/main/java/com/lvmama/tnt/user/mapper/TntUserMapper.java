package com.lvmama.tnt.user.mapper;

import java.util.List;
import java.util.Map;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.po.TntUser;

/**
 * 分销商
 * 
 * @author gaoxin
 * 
 */

public interface TntUserMapper {

	public int insert(TntUser tntUser);

	public List<TntUser> query(Map<String, Object> map);

	public int queryCount(Map<String, Object> map);

	public TntUser getUserByUserName(String userName);

	public TntUser selectOneWithDetail(TntUser tntUser);

	public List<TntUser> selectListWithDetail(TntUser tntUser);

	public List<TntUser> fetchPageWithDetail(Page<TntUser> page);

	public int countWithDetail(TntUser tntUser);

	public int update(TntUser tntUser);

	public List<TntUser> queryUserByMobileOrEmail(TntUser tntUser);

	public int checkUnique(Map<String, Object> map);

	public TntUser getByUserId(Long userId);

}
