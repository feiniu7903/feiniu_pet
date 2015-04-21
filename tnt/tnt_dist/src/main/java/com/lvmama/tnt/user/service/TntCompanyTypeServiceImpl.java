package com.lvmama.tnt.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.tnt.user.mapper.TntCompanyTypeMapper;
import com.lvmama.tnt.user.po.TntCompanyType;

/**
 * 分销商类型
 * 
 * @author gaoxin
 * 
 */
@Repository("tntCompanyTypeService")
public class TntCompanyTypeServiceImpl implements TntCompanyTypeService {

	@Autowired
	private TntCompanyTypeMapper tntCompanyTypeMapper;

	@Override
	public boolean insert(TntCompanyType tntCompanyType) {
		return tntCompanyTypeMapper.insert(tntCompanyType) > 0;
	}

	@Override
	public boolean update(TntCompanyType tntCompanyType) {
		return tntCompanyTypeMapper.update(tntCompanyType) > 0;
	}

	public List<TntCompanyType> query(TntCompanyType tntCompanyType){
		return tntCompanyTypeMapper.selectList(tntCompanyType);
	}
	@Override
	public Map<String, String> map(TntCompanyType tntCompanyType) {
		List<TntCompanyType> list = tntCompanyTypeMapper
				.selectList(tntCompanyType);
		Map<String, String> map = null;
		if (list != null && !list.isEmpty()) {
			map = new HashMap<String, String>();
			for (TntCompanyType t : list) {
				map.put(t.getCompanyTypeId() + "", t.getCompanyTypeName());
			}
		}
		return map;
	}

	@Override
	public TntCompanyType get(Long companyTypeId) {
		if (companyTypeId != null)
			return tntCompanyTypeMapper.getById(companyTypeId);
		return null;
	}

	@Override
	public boolean delete(Long companyTypeId) {
		if (companyTypeId != null)
			return tntCompanyTypeMapper.delete(companyTypeId) > 0;
		return false;
	}

	@Override
	public TntCompanyType get(TntCompanyType tntCompanyType) {
		return tntCompanyTypeMapper.selectOne(tntCompanyType);
	}

	@Override
	public List<String> getTypeIdANdNameList(TntCompanyType tntCompanyType) {
		List<String> list = null;
		Map<String, String> map = this.map(tntCompanyType);
		if (map != null && !map.isEmpty()) {
			list = new ArrayList<String>();
			Iterator<Map.Entry<String, String>> iter = map.entrySet()
					.iterator();
			while (iter.hasNext()) {
				Map.Entry<String, String> entry = iter.next();
				list.add(entry.getKey() + ":" + entry.getValue());
			}
		}
		return list;
	}

	@Override
	public int count(TntCompanyType tntCompanyType) {
		return tntCompanyTypeMapper.findCount(tntCompanyType);
	}

	@Override
	public Long getPersonType() {
		return getByCode(TntCompanyType.PERSON_TYPE_CODE);
	}
	
	private Long getByCode(String code){
		TntCompanyType t = new TntCompanyType();
		t.setCompanyTypeCode(code);
		t = tntCompanyTypeMapper.selectOne(t);
		return t!=null?t.getCompanyTypeId():null;
	}
}
