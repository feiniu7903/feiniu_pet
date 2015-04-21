package com.lvmama.tnt.recognizance.mapper;

import java.util.List;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.recognizance.po.TntRecognizanceChange;

/**
 * 现金账户变化
 * 
 * @author gaoxin
 * @version 1.0
 */
public interface TntRecognizanceChangeMapper {

	public int insert(TntRecognizanceChange entity);

	public List<TntRecognizanceChange> findPage(Page<TntRecognizanceChange> page);

	public int count(TntRecognizanceChange entity);

	public int update(TntRecognizanceChange tntRecognizanceChange);

	public int delete(Long tntRecognizanceChangeId);

	public TntRecognizanceChange getById(Long tntRecognizanceChangeId);

	public TntRecognizanceChange getWithUser(
			TntRecognizanceChange tntRecognizanceChangeId);

	public int approve(TntRecognizanceChange t);

	public List<TntRecognizanceChange> findWithUserPage(
			Page<TntRecognizanceChange> page);

	public int withUserCount(TntRecognizanceChange entity);
}
