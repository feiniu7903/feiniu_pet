package com.lvmama.pet.sweb.pub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pub.ComKeyDesc;
import com.lvmama.comm.pet.service.pub.ComKeyDescService;
import com.lvmama.comm.utils.MemcachedSeckillUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.homePage.PindaoPageUtils;

@Results( {
	@Result(name = "success", location = "/WEB-INF/pages/back/memcached/memcachedTool.jsp", type = "dispatcher")
})
public class MemcachedToolAction  extends BackBaseAction {
	private static final Log log = LogFactory
			.getLog(MemcachedToolAction.class);
	private static final long serialVersionUID = 3021868538356277432L;
	private String memcachedKey;
	private List<ComKeyDesc> memcacheMap;
	private Map<String,Object>  memcachedPrefixKeyPindaoMap;
	private Map<String,Object>  placeIdMap;
	
	private ComKeyDescService comKeyDescService;

	@Action("/pub/memcachedTool")
	public String initMemcached(){
		memcachedPrefixKeyPindaoMap=new HashMap<String, Object>();
		memcachedPrefixKeyPindaoMap.put("门票频道",PindaoPageUtils.MEMCACHED_TICKET_PREFIXKEY);
		memcachedPrefixKeyPindaoMap.put("周边频道",PindaoPageUtils.MEMCACHED_FREETOUR_PREFIXKEY);
		memcachedPrefixKeyPindaoMap.put("国内频道",PindaoPageUtils.MEMCACHED_DESTROUTE_PREFIXKEY);
		memcachedPrefixKeyPindaoMap.put("出境频道",PindaoPageUtils.MEMCACHED_ABROAD_PREFIXKEY);
		memcachedPrefixKeyPindaoMap.put("酒店频道",PindaoPageUtils.MEMCACHED_HOTEL_PREFIXKEY);
		memcachedPrefixKeyPindaoMap.put("首页", PindaoPageUtils.MEMCACHED_HOME_PREFIXKEY);
		memcachedPrefixKeyPindaoMap.put("新版首页",PindaoPageUtils.MEMCACHED_HOME_PREFIXKEY2);
 		placeIdMap=PindaoPageUtils.PLACEID_PLACECODE.getPlaceIdAndNameMap();
	   return SUCCESS;
	}
	
	/**
	 * 
	 * 根据key删除缓存
	 * @author:nixianjun 2013-6-26
	 */
	@Action("/pub/deleteMemcached")
	public void deleteMemcached(){
		String json = "{\"flag\":\"false\"}";
		if(MemcachedUtil.getInstance().get(memcachedKey)==null){
			json = "{\"flag\":\"none\"}";
		}else{
			Boolean clear=MemcachedUtil.getInstance().remove(memcachedKey);
			if(clear){
				json = "{\"flag\":\"true\"}";
	 		}else{
	 			json = "{\"flag\":\"false\"}";
	 		}
		}
		super.sendAjaxResultByJson(json);
	}
	
	/**
	 * 根据key删除秒杀缓存
	 * 
	 * @author zenglei
	 */
	@Action("/pub/deleteSeckillMemcached")
	public void deleteSeckillMecached(){
	    String json = "{\"flag\":\"false\"}";
        if(MemcachedSeckillUtil.getInstance().get(memcachedKey)==null){
            json = "{\"flag\":\"none\"}";
        }else{
            Boolean clear=MemcachedSeckillUtil.getInstance().remove(memcachedKey);
            if(clear){
                json = "{\"flag\":\"true\"}";
            }else{
                json = "{\"flag\":\"false\"}";
            }
        }
	    super.sendAjaxResultByJson(json);
	}
	
	public void clearMemcached(){
		String json = "{\"flag\":\"false\"}";
		initMemcached();
		Boolean clear=MemcachedUtil.getInstance().remove(memcachedKey);
		if(clear){
			json = "{\"flag\":\"true\"}";
			comKeyDescService.remove(memcachedKey);
		}
		
		this.responseWrite(json);
	}
	
	private void responseWrite(String info){
		try {
			this.getResponse().setContentType("text/html; charset=utf-8");
			this.getResponse().getWriter().write(info);
		} catch (Exception e) {
			log.info("com.lvmama.pet.sweb.seo:"+e.getMessage());
		}
	}

	public List<ComKeyDesc> getMemcacheMap() {
		return memcacheMap;
	}

	public String getMemcachedKey() {
		return memcachedKey;
	}

	public void setMemcachedKey(String memcachedKey) {
		this.memcachedKey = memcachedKey;
	}

	public void setComKeyDescService(ComKeyDescService comKeyDescService) {
		this.comKeyDescService = comKeyDescService;
	}

	public Map<String, Object> getMemcachedPrefixKeyPindaoMap() {
		return memcachedPrefixKeyPindaoMap;
	}

	public Map<String, Object> getPlaceIdMap() {
		return placeIdMap;
	}

}
