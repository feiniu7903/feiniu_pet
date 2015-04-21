/**
 * 
 */
package com.lvmama.back.sweb.prod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.prod.LineStation;
import com.lvmama.comm.bee.service.prod.ProdTrainService;
import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.utils.json.JSONOutput;

/**
 * @author yangbin
 *
 */
public class ProdTrainStationAction extends BaseAction{

	private ProdTrainService prodTrainService;
	
	private String search;
	
	@Action("/prod/searchStationJSON")
	public void getStationJSON(){
		Map<String, Object> param = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(search)) {
			param.put("stationSearch", search);
		}
		List<LineStation> list = prodTrainService.selectLineStationByParam(param);
		JSONArray array=new JSONArray();
		for(LineStation ls:list){
			JSONObject obj=new JSONObject();
			obj.put("id", ls.getStationPinyin());
			obj.put("text", ls.getStationName());
			array.add(obj);
		}
		JSONOutput.writeJSON(getResponse(), array);
	}
	public void setProdTrainService(ProdTrainService prodTrainService) {
		this.prodTrainService = prodTrainService;
	}
	public void setSearch(String search) {
		this.search = search;
	}
}
