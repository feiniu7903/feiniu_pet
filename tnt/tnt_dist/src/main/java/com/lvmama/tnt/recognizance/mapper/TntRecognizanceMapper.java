package com.lvmama.tnt.recognizance.mapper;

import java.util.List;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.recognizance.po.TntRecognizance;

/**
 * 保证金账户
 * 
 * @author gaoxin
 * @version 1.0
 */
public interface TntRecognizanceMapper {

	public int insert(TntRecognizance entity);

	public List<TntRecognizance> findPage(Page<TntRecognizance> page);

	public int count(TntRecognizance entity);

	public int update(TntRecognizance tntRecognizance);

	public int delete(Long tntRecognizanceId);

	public TntRecognizance getById(Long recognizanceId);

	public TntRecognizance getByUserId(Long userId);

	public int appendBalance(TntRecognizance tntRecognizance);

}
