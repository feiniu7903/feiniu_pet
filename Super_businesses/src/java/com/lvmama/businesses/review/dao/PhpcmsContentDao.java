package com.lvmama.businesses.review.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.businesses.po.review.PhpcmsComment;
import com.lvmama.comm.businesses.po.review.PhpcmsContent;

public class PhpcmsContentDao extends BaseIbatisDAO{

	/**
	 * 更改状态
	 * @param param
	 * @author nixianjun 2013-10-8
	 */
	public void updateForPhpcmsContent(Map<String, Object> param) {
		super.update("PHPCMSCONTENT.updateForPhpcmsContent",param);
	}

	public List<PhpcmsContent> queryPhpcmsCByParam(Map param) {
 		return super.queryForList("PHPCMSCONTENT.queryByParamForPhpcms_c",param);
	}

	public long countForPhpcmsC(Map param) {
 		return   (Long) super.queryForObject("PHPCMSCONTENT.countForPhpcms_c",param);
	}

	public void updateForPhpcmsC(Map<String, Object> param) {
		 super.update("PHPCMSCONTENT.updateForPhpcms_c",param);
	}

	public PhpcmsContent  queryForPhpcmsContentById(Integer contentid, String tablename) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("contentId", contentid);
		map.put("tableName", tablename);
 		return (PhpcmsContent) super.queryForObject("PHPCMSCONTENT.select",map);
	}

}
