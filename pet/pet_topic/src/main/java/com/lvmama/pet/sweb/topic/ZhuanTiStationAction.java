package com.lvmama.pet.sweb.topic;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.client.RecommendInfoClient;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MemcachedUtil;

@Results({
	@Result(name = "success", location = "/WEB-INF/pages/zhuanti/${station}/${ztname}/${fname}.ftl")
	 })
public class ZhuanTiStationAction extends BaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -6392921717994038866L;

	private RecommendInfoClient recommendInfoClient;
	
	private Map<String,List<RecommendInfo>> map;

	private Long blockId;	//主块ID
	private String station;	//站点
	private String fname;	//模板文件名
	private String ztname;  //专题名字

    @Action(value = "/indexPakage/ztStationAction")
	public String index(){
		map = recommendInfoClient.getRecommendProductByBlockIdAndStation(blockId, station);
		return "success";
	}
    
    @Action(value = "/indexPakage/ztRecommendInfoJson")
	public void getRecommendInfoJson() {
		map=recommendInfoClient.getRecommendProductByBlockIdAndStation(blockId, station);
		String json = net.sf.json.JSONArray.fromObject(map).toString();
		outputJsonToClient("callback",json);
	}
	
    @SuppressWarnings("unchecked")
	public Map<String, List<RecommendInfo>> getRecommendProductByBlockIdAndStation(Long blockId, String station) {
		Map<String, List<RecommendInfo>> result = null;
		String key = "RecommendProductByBlockIdAndStation" + "_" + blockId + "_" + station;
		Object obj = MemcachedUtil.getInstance().get(key);
		if (obj == null) {
			result = recommendInfoClient.getRecommendProductByBlockIdAndStation(blockId, station);
			if(result != null && result.size() > 0){
				MemcachedUtil.getInstance().set(key, 120, result);
			}
		} else {
			result = (Map<String, List<RecommendInfo>>) obj;
		}
		return result;
	}
    
    
    
    /**
     * 漂流 截取服务器时间
     * @throws IOException
     */
    @Action(value = "/piaoliu/ajaxgetDate")
	public void ajaxgetDate() throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
	    int day = cal.get(Calendar.DATE);
	    int month = cal.get(Calendar.MONTH) + 1;
	    int year = cal.get(Calendar.YEAR);
	    int hour=cal.get(Calendar.HOUR_OF_DAY);
	    int minute=cal.get(Calendar.MINUTE);
	    int second=cal.get(Calendar.SECOND);
	    map.put("day", day);
	    map.put("month", month);
	    map.put("year", year);
	    map.put("hour", hour);
	    map.put("minute", minute);
	    map.put("second", second);
	   printRtn(map);
	}
	/**
	 * 输出json数据到客户端
	 * 
	 * @param param
	 * @param jsonMsg
	 */
	private void outputJsonToClient(String param, String jsonMsg) {
		String jsoncallback = this.getRequest().getParameter(param);// 必要
		getResponse().setContentType("application/json; charset=UTF-8");
		try {
			getResponse().getWriter().write(jsoncallback + "(" + jsonMsg + ")");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 输出返回码
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param object
	 *            Ajax返回的对象
	 * @throws IOException
	 *             IOException
	 */
	protected void printRtn(final Object object) throws IOException  {
		String json = null;
		getResponse().setContentType("text/json; charset=utf-8");
		if (null == object) {
			return;
		} else {
			if (object instanceof java.util.Collection) {
				json = JSONArray.fromObject(object).toString();
			} else {
				json = JSONObject.fromObject(object).toString();
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("返回对象:" + json);
			}
		}
		if (getRequest().getParameter("jsoncallback") == null) {
			getResponse().getWriter().print(json);
		} else {
			getResponse().getWriter().print(
					getRequest().getParameter("jsoncallback") + "(" + json
							+ ")");
		}
	}
    
	public Map<String, List<RecommendInfo>> getMap() {
		return map;
	}
 
	public Long getBlockId() {
		return blockId;
	}

	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getZtname() {
		return ztname;
	}

	public void setZtname(String ztname) {
		this.ztname = ztname;
	}

	public void setRecommendInfoClient(RecommendInfoClient recommendInfoClient) {
		this.recommendInfoClient = recommendInfoClient;
	}
	
}
