package com.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.lvmama.comm.utils.HttpsUtil;

public class Test2 {
	public static void main(String[] args) {
		Map<Long,Long> idsmap = new HashMap<Long,Long>();
		String json = HttpsUtil.requestGet("http://www.lvmama.com/client/datas/filter_v2.json");
		JSONObject obj =JSONObject.fromObject(json);
		Map<String,Object> map = (Map<String,Object>)obj;
		List<Integer> list  = (List<Integer>)map.get("datas");
		if(list!=null&&list.size()>0){
			for (Integer id : list) {
				idsmap.put(Long.valueOf(id.toString()), Long.valueOf(id.toString()));
			}
		}
	}
}	
