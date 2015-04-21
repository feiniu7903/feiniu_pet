package com.lvmama.tnt.comm.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;

import com.lvmama.comm.utils.WebUtils;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.RequestKV;

public abstract class WebFetchPager<T> extends FetchPager<T> {

	private HttpServletRequest request;

	public WebFetchPager(Page<T> page, HttpServletRequest request) {
		this(page);
		this.request = request;
	}

	public WebFetchPager(Page<T> page) {
		super(page);
	}

	@Override
	protected String getPageUrl(Page<T> page) {
		return WebUtils.getPageUrl(request, initSkipParam(page));
	}

	@Override
	protected void initUrl(Page<T> page) {
		List<RequestKV> params = getParameterMap(request, initSkipParam(page));
		page.setParams(params);
	}

	private Map<String, String> initSkipParam(Page<T> page) {
		Map<String, String> skipParam = new HashMap<String, String>();
		skipParam.put(page.getCurrentPageParamName(),
				page.getCurrentPageParamName());
		skipParam.put("perPageRecord", "perPageRecord");
		skipParam.put("gogo", "gogo");
		if (page.isAjaxFlag()) {
			skipParam.put("useAjax", "useAjax");
		}
		return skipParam;
	}

	private List<RequestKV> getParameterMap(HttpServletRequest req,
			Map<String, String> skipParam) {
		Enumeration<String> it = req.getParameterNames();
		Set<String> keys = new HashSet<String>();
		List<RequestKV> list = new ArrayList<RequestKV>();
		while (it.hasMoreElements()) {
			String key = it.nextElement();
			if ((skipParam != null) && skipParam.containsKey(key)) {
				continue;
			}
			if (keys.contains(key)) {
				continue;
			}
			keys.add(key);
			String values[] = req.getParameterValues(key);
			if (!ArrayUtils.isEmpty(values)) {
				for (String v : values) {
					RequestKV kv = new RequestKV(key, v);
					list.add(kv);
				}
			}
		}
		return list;
	}

}
