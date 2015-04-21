package com.lvmama.tnt.prod.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.tnt.comm.vo.Page;

public abstract class InterfaceVersionService<F, T> {

	public abstract F reserve(T t);

	public abstract T translate(F t);

	public List<F> reserve(List<T> list) {
		List<F> l = null;
		if (list != null && !list.isEmpty()) {
			for (T t : list) {
				l = new ArrayList<F>();
				l.add(reserve(t));
			}
		}
		return l;
	}

	public List<T> selectList(T t) {
		Map<String, Object> map = toMap(t);
		return selectList(map);
	}

	public List<T> selectList(Page<F> page) {
		Map<String, Object> map = toMap(page);
		return selectList(map);
	}

	public T selectOne(T t) {
		Map<String, Object> map = toMap(t);
		return selectOne(map);
	}

	public abstract List<T> selectList(Map<String, Object> map);

	public abstract T selectOne(Map<String, Object> map);

	public abstract long count(Map<String, Object> map);

	protected abstract Map<String, Object> toMap(T t);

	protected Map<String, Object> toMap(Page<F> page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startRows", page.getStartRows());
		map.put("endRows", page.getEndRows());
		T t = translate(page.getParam());
		Map<String, Object> map2 = toMap(t);
		if (map2 != null)
			map.putAll(map2);
		return map;
	}

	public List<F> search(Page<F> page) {
		List<F> lst = null;
		if (page != null) {
			List<T> list = selectList(page);
			if (list != null) {
				lst = new ArrayList<F>();
				for (T l : list) {
					lst.add(reserve(l));
				}
			}
		}
		return lst;
	}

	public F get(F f) {
		T prod = translate(f);
		prod = selectOne(prod);
		return reserve(prod);
	}

	public long count(F f) {
		T prod = translate(f);
		Map<String, Object> map = toMap(prod);
		return count(map);
	}
}
